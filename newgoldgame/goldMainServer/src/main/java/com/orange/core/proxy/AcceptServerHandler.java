package com.orange.core.proxy;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.group.DefaultChannelGroup;

import com.orange.core.DomainManager;
import com.orange.core.Message;
import com.orange.core.ServerManager;
import com.orange.log.LoggerFactory;
import com.orange.server.ProxyServer;
import com.orange.util.Util;

/**
 * 接受客户端消息,并分发到各个 proxyserver,由server进行转发到目标服务器
 * 
 * @author hqch
 * 
 */
public class AcceptServerHandler extends SimpleChannelHandler {

	private static Logger logger = LoggerFactory
			.getLogger(AcceptServerHandler.class);

	private DefaultChannelGroup channelGroup;

	private static ServerManager serverManager = ServerManager.getInstance();
	
	private ProxyServer server;
	
	private Map<Integer,SocketSession> sessionMap;

	public AcceptServerHandler(DefaultChannelGroup channelGroup) {
		this.channelGroup = channelGroup;
		this.sessionMap = Collections.synchronizedMap(new HashMap<Integer,SocketSession>());
	}

	@Override
	public void channelDisconnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) throws Exception {
		super.channelDisconnected(ctx, e);
		channelGroup.remove(ctx.getChannel());
		
		int channelID = ctx.getChannel().getId();
		SocketSession session = sessionMap.get(channelID);
		sessionMap.remove(channelID);
		serverManager.removeUser(channelID,session);
	}

	@Override
	public void channelConnected(final ChannelHandlerContext ctx,
			final ChannelStateEvent e) throws Exception {
		channelGroup.add(ctx.getChannel());
		ctx.sendUpstream(e);

		SocketSession session = new SocketSession(ctx);
		session.setOnline(true);
		sessionMap.put(ctx.getChannel().getId(), session);
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx,
			MessageEvent messageEvent) throws Exception {
		InputMessage msg = (InputMessage) messageEvent.getMessage();
		SocketSession session = sessionMap.get(ctx.getChannel().getId());
		msg.setSessionID(session.getId());	

		try{
			session.lock();
			forwardServer(ctx,msg,session);
		} finally {
			session.unlock();
		}
		
		logger.debug("received clinet messgae:" + msg);
	}
	
	/**
	 * 根据客户端发送的信息，分发到不同的服务器
	 * @param msg
	 */
	private void forwardServer(ChannelHandlerContext ctx,InputMessage msg,
			SocketSession session){
		String protocol = msg.getHeader();
		byte[] datas = msg.getMsg();
		
		//心跳包
		if(protocol.equals(Message.HEARTBEAT_PROTOCOL)){
			int userID = Util.byteArrayToInt(new byte[]{datas[0],datas[1],
					datas[2],datas[3]});
			session.setUserID(userID);
			serverManager.sendHeartbeat(msg,session.getId());
		} else {
			int userID = 0;
			//获取USEID
			if(!protocol.equals(Message.NOT_ID_PROTOCOL)){
				userID = Util.byteArrayToInt(new byte[]{datas[0],datas[1],
						datas[2],datas[3]});
				session.setUserID(userID);
			}
			
			String domainID = null;
			if(protocol.equals(Message.RECONNECT_PROTOCOL)){
				domainID = serverManager.copySession(session.getId(), userID);
				serverManager.countOnline(domainID, DomainManager.FIRST_CONN_SERVER);
			}

			if(domainID != null){
				server = serverManager.getProxyServerByDomainID(domainID,protocol);
			} else if(protocol.equals(Message.TRACE_PROTOCOL)){//根据不用的协议获取目标服务器
				int traceUserID = Util.byteArrayToInt(new byte[]{datas[4],datas[5],
						datas[6],datas[7]});
				server = serverManager.getProxyServerByUser(session.getId(),traceUserID,protocol);
			} else {
				server = serverManager.getProxyServerByProtocol(session.getId(),protocol);
			}

			if(server == null){
				logger.warn("protocol:" + protocol + ",domainID:" + domainID + " have not server.");
				return;
			}
			
			server.getMsgManager().addSocketMessage(msg);
			domainID = server.getDomain().getDomainID();
			serverManager.addUserSession(domainID, session);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		if (e.getCause() instanceof ClosedChannelException) {
			e.getChannel().close();
		} else if (e.getCause() instanceof IOException) {
			e.getChannel().close();
		} else {
			logger.error("AcceptServerHandler",e.getCause());
		}
	}
}
