package com.orange.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.MessageEvent;

import com.orange.SysConfig;
import com.orange.core.proxy.InputMessage;
import com.orange.core.proxy.SocketSession;
import com.orange.http.HttpHandler;
import com.orange.log.LoggerFactory;
import com.orange.server.ProxyServer;
import com.orange.task.BaseTask;

/**
 * 服务器管理
 * @author hqch
 *
 */
public class ServerManager {

	private static Logger logger = LoggerFactory.getLogger(ServerManager.class);
	
	/**服务器最大在线人数*/
	public static final int MAX_ONLINE = 2000;
	
	/**key channelID value domainID*/
	private Map<Integer,String> serverChannelMap;
	
	/**key domainID value 目标服务器*/
	private Map<String,ProxyServer> proxyServerMap;
	
	/**key domainID value 在线人数*/
	private Map<String,AtomicInteger> serverOnlineMap;
	
	/**key sessionID value session*/
	private Map<String,SocketSession> userSessionMap;
	
	private Map<String,BaseTask> taskMap;
	
	private Map<String,HttpHandler> httpHandlerMap;
	
	/**key channelID value MessageEvent*/
	private Map<Integer,MessageEvent> channelMap;
	
	private DomainManager domainManager = DomainManager.getInstance();
	
	private static ServerManager manager = new ServerManager();
	
	private ServerManager(){}
	
	public static ServerManager getInstance(){
		return manager;
	}
	
	public void init(){
		this.proxyServerMap = Collections.synchronizedMap(new HashMap<String,ProxyServer>());
		this.serverOnlineMap = Collections.synchronizedMap(new HashMap<String,AtomicInteger>());
		this.userSessionMap = Collections.synchronizedMap(new HashMap<String,SocketSession>());
		this.serverChannelMap = Collections.synchronizedMap(new HashMap<Integer,String>());
		this.taskMap = Collections.synchronizedMap(new HashMap<String, BaseTask>());
		this.httpHandlerMap = Collections.synchronizedMap(new HashMap<String, HttpHandler>());
		this.channelMap = Collections.synchronizedMap(new HashMap<Integer,MessageEvent>());
		
		List<Domain> domainList = domainManager.getAllDomain();
		for(Domain domain : domainList){
			createProxyServer(domain);
		}
	}
	
	/**
	 * 创建与目标服务器的连接
	 * @param domain
	 */
	public void createProxyServer(Domain domain) {
		new ProxyServer(domain);
	}
	
	public void addServer(ProxyServer server){
		Domain domain = server.getDomain();
		if(server.isConnected()){
			String domainID = domain.getDomainID();
			proxyServerMap.put(domain.getDomainID(), server);
			serverChannelMap.put(server.getChannelID(), domainID);
			AtomicInteger onlineCount = serverOnlineMap.get(domainID);
			if(onlineCount == null){
				onlineCount = new AtomicInteger(0);
				serverOnlineMap.put(domainID, onlineCount);
			}
			
			logger.info(domain.getDomainID() + " was connected,server ip:" 
					+ domain.getServerIP() + " port:" + domain.getServerPort());
		} else {
			logger.warn(domain.getDomainID() + " is shutdown,server ip:" 
					+ domain.getServerIP() + " port:" + domain.getServerPort());
		}
	}
	
	/**
	 * 判断目标服务器在线人数是否超出
	 * @param domainID
	 * @return
	 */
	private boolean isReachMaxOnline(String domainID,int maxOnline){
		AtomicInteger onlineCount = serverOnlineMap.get(domainID);
		if(onlineCount == null){
			return false;
		}

		maxOnline = maxOnline == 0 ? MAX_ONLINE : maxOnline;
		return onlineCount.intValue() >= maxOnline;
	}
	
	/**
	 * 检测玩家是否存在两个session，赋值session，并去除老session
	 * @param curSessionID
	 * @param userID
	 */
	public String copySession(String curSessionID,int userID){
		if(userID == 0){
			return null;
		}
		List<SocketSession> sessionList = getSessionByUserID(userID);
		if(sessionList.size() == 2){
			SocketSession session1 = sessionList.get(0);
			SocketSession session2 = sessionList.get(1);
			if(session1.getId().equals(curSessionID)){
				return setSessionValue(session1,session2);
			} else {
				return setSessionValue(session2,session1);
			}
		}
		return null;
	}
	
	/**
	 * 将老的session信息，赋值给新的session信息
	 * @param curSession
	 * @param oldSession
	 */
	private String setSessionValue(SocketSession curSession,SocketSession oldSession){
		curSession.setLastDomainID(oldSession.getLastDomainID());
		userSessionMap.remove(oldSession.getId());
		
		return curSession.getLastDomainID();
	}
	
	/**
	 * 根据协议号获取可通信的目标服务器
	 * @param protocol
	 * @return
	 */
	public ProxyServer getProxyServerByProtocol(String sessionID,String protocol) {
		String domainID = null;
		//协议号对应的目标服务器
		Domain domain = domainManager.getDefualtDomainMap4Protocol(protocol);
		//当前session是否访问过目标服务器，如果访问过，则直接返回之前访问的服务器，
		//如果没访问过，则按照分发策略选择目标服务器
		Domain visitedDomain = getUserDomain4ProtocolAndServerType(sessionID,domain);
		boolean isVisited = false;
		if(visitedDomain != null){
			domainID = visitedDomain.getDomainID();
			domain = visitedDomain;
			isVisited = true;
		}

		if(domainID == null){
			domainID = domain.getDomainID();
		}

		ProxyServer server = proxyServerMap.get(domainID);
		if(isVisited){
			return server;
		}
		if(server == null || !server.isConnected()){
			server = getIdleProxyServer(protocol, domainID);
		} else {
			if(isReachMaxOnline(domainID,domain.getMaxOnline())){
				server = getIdleProxyServer(protocol, domainID);
			}
		}
		return server;
	}
	
	/**
	 * 断线重连时，根据最后所在目标服务器返回，目标服务器宕机，或者不可用时，按策略返回一个
	 * @param sessionID
	 * @param protocol
	 * @return
	 */
	public ProxyServer getProxyServerByDomainID(String domainID,String protocol) {
		ProxyServer server = proxyServerMap.get(domainID);
		if(server == null || !server.isConnected()){
			server = getIdleProxyServer(protocol, domainID);
		} else {
			Domain domain = domainManager.getDomainByID(domainID);
			if(isReachMaxOnline(domainID,domain.getMaxOnline())){
				server = getIdleProxyServer(protocol, domainID);
			}
		}
		
		return server;
	}
	
	/**
	 * 根据协议号，获取指定session登录过的目标服务器
	 * @param sessionID
	 * @param curDomain 协议号对应的目标服务器
	 * @return
	 */
	private Domain getUserDomain4ProtocolAndServerType(String sessionID,Domain curDomain){
		SocketSession session = userSessionMap.get(sessionID);
		if(session != null){
			Set<String> domainSet = session.getDomainSet();
			Domain tmpDomain = null;
			for(String tmpDomainID : domainSet){
				tmpDomain = domainManager.getDomainByID(tmpDomainID);
				if(tmpDomain.getServerType().equals(curDomain.getServerType())){
					return tmpDomain;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 获取指定玩家的当前所在服务器，如果玩家所在服务器不允许跟踪，
	 * 则按原规则返回一个可用服务器
	 * @param userID
	 * @return
	 */
	public ProxyServer getProxyServerByUser(String sessionID,int traceUserID,String protocol) {
		SocketSession traceSession = getOnlineSessionByUserID(traceUserID);
		if(traceSession == null){
			return null;
		}
		String domainID = traceSession.getLastDomainID();
		Domain domain = domainManager.getDomainByID(domainID);
		
		ProxyServer server = null;
		if(domain.isTrace()){
			server = proxyServerMap.get(domainID);
		}
		
		if(server == null){
			server = getProxyServerByProtocol(sessionID,protocol);
		}
		return server;
	}
	
	/**
	 * 根据协议号，获取空闲目标服务器
	 * @param protocol
	 * @param reachMaxOnlineDomainID
	 * @return
	 */
	private ProxyServer getIdleProxyServer(String protocol,
			String reachMaxOnlineDomainID){
		ProxyServer server = null;
		List<Domain> domainList = domainManager.getDomainMap4Protocol(protocol);
		String domainID = null;
		for(Domain domain : domainList){
			domainID = domain.getDomainID();
			server = proxyServerMap.get(domainID);
			
			if(server == null){
				continue;
			}
			
			if(!reachMaxOnlineDomainID.equals(domainID)
					&& !isReachMaxOnline(domainID,domain.getMaxOnline())
					&& server.isConnected()){
				return server;
			}
		}
		
		logger.warn("not have idie server.");
		return server;
	}
	
	/**
	 * 玩家上线，统计在线
	 * @param domainID
	 * @param playerID
	 */
	public void addUserSession(String domainID,SocketSession session){
		if(session.isOnline()){
			String sessionID = session.getId();
			
			countOnline(domainID,session.getLastDomainID());
			
			session.getDomainSet().add(domainID);
			session.setLastDomainID(domainID);
			
			if(userSessionMap.get(sessionID) == null){
				userSessionMap.put(sessionID, session);
			}
		}
	}
	
	/**
	 * 统计在线人数
	 * @param domainID
	 */
	public void countOnline(String domainID,String lastDomainID){
		if(lastDomainID == null){
			AtomicInteger onlineCount = serverOnlineMap.get(domainID);
			onlineCount.incrementAndGet();
		} else if(domainID != null && !lastDomainID.equals(domainID)){
			AtomicInteger inOnlineCount = serverOnlineMap.get(domainID);
			inOnlineCount.incrementAndGet();		
			
			AtomicInteger deOnlineCount = serverOnlineMap.get(lastDomainID);
			if(deOnlineCount != null){
				deOnlineCount.decrementAndGet();
			}
		}
	}
	
	/**
	 * 玩家下线，统计在线
	 * @param domainID
	 * @param playerID
	 */
	public void removeUser(int channelID,SocketSession session){
		if(session.isOnline()){
			//在线人数减少
			AtomicInteger onlineCount = serverOnlineMap.get(session.getLastDomainID());
			if(onlineCount != null){
				onlineCount.decrementAndGet();
			}
			
			session.setDisConnectionTime(new Date().getTime());
			session.setOnline(false);
			
			logger.info("onlineCount decrement.sessionId:" + session.getId() 
					+ ",domains:" + session.getLastDomainID() + ",set:" + session.getDomainSet());
		}
		
		serverChannelMap.remove(channelID);
	}
	
	/**
	 * 各个目标服务器状态
	 * @return
	 */
	public String realTimeStatus(){
		StringBuilder sb = new StringBuilder("<table width='200px' border='1'>");
		if(serverOnlineMap.size() == 0){
			return "没有可用服务器";
		}
		
		sb.append("<tr><td>服务器名称</td><td>在线人数</td></tr>");
		
		int total = 0;
		for(Entry<String,AtomicInteger> entry : serverOnlineMap.entrySet()){
			total += entry.getValue().intValue();
			sb.append("<tr>");
			
			sb.append("<td>").append(domainManager.getDomainByID(entry.getKey())
					.getDomainName()).append("</td>");
			sb.append("<td>").append(entry.getValue().intValue()).append("</td>");
			sb.append("</tr>");
		}
		sb.append("<tr><td>合计</td><td>").append(total).append("</td></tr>");
		sb.append("</table>");
		
		return sb.toString();
	}
	
	/**
	 * 给客户端发送目标服务器返回的结果
	 * @param sessionID
	 * @param datas
	 */
	public void sendMessage(String sessionID,int protocol,long createTime,byte[] datas){
		SocketSession session = userSessionMap.get(sessionID);

		if(session != null && session.isOnline()){
			session.sendMessage(createTime,protocol,datas);
		}
	}
	
	/**
	 * 广播信息
	 * @param msg
	 */
	public void broadcastMessage(int userID,int protocol,long createTime,byte[] datas) {
		if(userSessionMap.size() == 0){
			logger.warn("sessionCount is zero");
			return;
		}
		
		logger.info("user: " + userID + ",broadcast:" + datas.length
				+ ",sessionCount:" + userSessionMap.size());
		SocketSession session = null;
		for(Entry<String, SocketSession> entry : userSessionMap.entrySet()){
			session = entry.getValue();
			if(session.isOnline()){
				session.sendMessage(createTime,protocol,datas);
			}
		}
	}

	
	/**
	 * 目标服务器运行过程中宕机
	 * @param sessionID
	 */
	public void shutDownServer(int channelID){
		String domainID = serverChannelMap.get(channelID);
		if(domainID != null){
			ProxyServer server = proxyServerMap.get(domainID);
			proxyServerMap.remove(domainID);
			serverOnlineMap.remove(domainID);
			serverChannelMap.remove(channelID);
			
			//去除当前服务器登陆过的玩家
			for(Entry<String,SocketSession> entry : userSessionMap.entrySet()){
				String tmp = entry.getValue().getLastDomainID();
				if(tmp != null && tmp.equals(domainID)){
					entry.getValue().setLastDomainID(null);
				}
			}
			
			String ip = server.getDomain().getServerIP();
			int port = server.getDomain().getServerPort();
			//发送邮件
//			MailUtil.sendServerShutDownMail(domainID,ip,port);
			
			logger.warn(domainID + " is shutdown.server ip:" + ip + ",port:" + port);
		}
	}

	/**
	 * 获取当前连接着的目标服务器
	 * @return
	 */
	public Set<String> getAliveServerByDomainID() {
		return proxyServerMap.keySet();
	}

	/**
	 * 发送玩家心跳包信息
	 * @param heartbeatMessage
	 */
	public void sendHeartbeat(InputMessage msg,String sessionID) {
		SocketSession session = userSessionMap.get(sessionID);
		if(session != null && session.isOnline()){
			ProxyServer server = proxyServerMap.get(session.getLastDomainID());
			session.setCurDate(new Date().getTime());
			if(server != null){
				server.getMsgManager().addSocketMessage(msg);
			}
		}
	}
	
	/***
	 * 根据用户ID获取session信息
	 * @param userID
	 * @return
	 */
	private List<SocketSession> getSessionByUserID(int userID){
		SocketSession retSession = null;
		List<SocketSession> sessionList = new ArrayList<SocketSession>();
		for(Entry<String, SocketSession> entry : userSessionMap.entrySet()){
			retSession = entry.getValue();
			if(retSession.getUserID() == userID){
				sessionList.add(retSession);
			}
		}
		
		return sessionList;
	}
	
	/**
	 * 获取玩家当前在线的session信息。当玩家断线重连时，有可能一个玩家同时有两个seesion
	 * @param userID
	 * @return
	 */
	public SocketSession getOnlineSessionByUserID(int userID){
		SocketSession retSession = null;
		for(Entry<String, SocketSession> entry : userSessionMap.entrySet()){
			retSession = entry.getValue();
			if(retSession.getUserID() == userID && retSession.isOnline()){
				return retSession;
			}
		}
		return null;
	}
	
	/**
	 * 检测可用目标服务器
	 */
	public void checkProxyServer(){
		logger.debug("checkSerever...");
		List<Domain> domainList = domainManager.getAllDomain();
		Set<String> aliveSet = getAliveServerByDomainID();
		if(aliveSet.size() == domainList.size()){
			return;
		}
		String domainID = null;
		for(Domain domain : domainList){
			domainID = domain.getDomainID();
			if(!aliveSet.contains(domainID)){
				createProxyServer(domain);
			}
		}
	}
	
	/**
	 * 检测服务器session失效，并清理
	 */
	public void clearSession(){
		SocketSession session = null;
		long now = new Date().getTime();
		List<String> removeSessionList = new ArrayList<String>();
		for(Entry<String, SocketSession> entry : userSessionMap.entrySet()){
			session = entry.getValue();
			if(!session.isOnline() && 
					now - session.getDisConnectionTime() >= SysConfig.TIME_OUT){
				removeSessionList.add(entry.getKey());
				session.getCtx().getChannel().close();
			} else if(session.getCurDate() > 0 && 
					now - session.getCurDate() >= SysConfig.TIME_OUT){
				removeSessionList.add(entry.getKey());
				session.getCtx().getChannel().close();
			}
		}
		
		for(String sessionID : removeSessionList){
			userSessionMap.remove(sessionID);
			logger.info("remove session:" + sessionID);
		}
	}
	
	/**
	 * 注册Task
	 * @param taskName
	 * @param task
	 */
	public void registerTask(String taskName,BaseTask task){
		taskMap.put(taskName, task);
	}
	
	/**
	 * 执行某一个TASK
	 * @param taskName
	 * @return
	 */
	public boolean execTask(String taskName){
		BaseTask task = taskMap.get(taskName);
		boolean retBool = false;
		if(task != null){
			task.startNoRepeat();
			retBool = true;
		}
		
		return retBool;
	}
	
	/**
	 * 注册http处理请求
	 * @param taskName
	 * @param task
	 */
	public void registerHandler(String handlerName,HttpHandler handler){
		httpHandlerMap.put(handlerName, handler);
	}
	
	/**
	 * 获取某一个http处理请求
	 * @param taskName
	 * @return
	 */
	public HttpHandler getHandlerByName(String handlerName){
		return httpHandlerMap.get(handlerName);
	}
	
	/**
	 * 保存支付宝客户端连接
	 * @param channel
	 */
	public void addChannel(MessageEvent e){
		channelMap.put(e.getChannel().getId(), e);
	}
	
	/**
	 * 保存支付宝客户端连接
	 * @param channel
	 */
	public MessageEvent getChannelByID(int channelID){
		MessageEvent retChannel = channelMap.get(channelID);
		channelMap.remove(channelID);
		return retChannel;
	}
}
