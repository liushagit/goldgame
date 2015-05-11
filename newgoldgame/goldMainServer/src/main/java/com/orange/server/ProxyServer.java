package com.orange.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.execution.ExecutionHandler;
import org.jboss.netty.handler.execution.OrderedMemoryAwareThreadPoolExecutor;

import com.orange.core.Domain;
import com.orange.core.ServerManager;
import com.orange.core.proxy.MessageManager;
import com.orange.core.proxy.PushServerPipelineFactory;

public class ProxyServer {

	/** 转发请求包含数据大小 */
	private static final int MSG_SIZE = 1048576;

	/** 处理转发请求连接池大小 */
	private static final int POOL_SIZE = 100;

	private MessageManager msgManager;
	
	private ServerManager serverManager = ServerManager.getInstance();

	private Domain domain;
	
	private boolean isConnected;
	
	/**与目标服务器通信的标识*/
	private int channelID;
	
	private Channel channel;

	public ProxyServer(Domain domain) {
		this.domain = domain;
		ChannelFuture channelFuture = createServer();
		checkFutureState(channelFuture);
	}

	private ChannelFuture createServer() {
		ClientBootstrap bootstrap = new ClientBootstrap(
				new NioClientSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool()));
		DefaultChannelGroup allChannels = new DefaultChannelGroup(
				"pushServerChannelGroup");
		ExecutionHandler executionHandler = new ExecutionHandler(
				new OrderedMemoryAwareThreadPoolExecutor(POOL_SIZE, MSG_SIZE,
						MSG_SIZE));
		ChannelPipelineFactory pipelineFactory = new PushServerPipelineFactory(
				domain.getDomainID(),executionHandler, allChannels);
		bootstrap.setPipelineFactory(pipelineFactory);
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);

		// 创建无连接传输channel的辅助类(UDP),包括client和server
		ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress(domain
				.getServerIP(), domain.getServerPort()));

		return channelFuture;
	}
	
	public void checkFutureState(ChannelFuture channelFuture) {
		channelFuture.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture connectFuture) throws Exception {
				if (connectFuture.isSuccess()) {
					channel = connectFuture.getChannel();
					isConnected = channel.isConnected();
					if(isConnected){
						channelID = channel.getId();
						addServer4Manager();
					}
				}
			}
		});
	}
	
	/**将该server添加到管理中*/
	private void addServer4Manager(){
		this.msgManager = new MessageManager(channel);
		serverManager.addServer(this);
	}
	
	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	public MessageManager getMsgManager() {
		return msgManager;
	}

	public void setMsgManager(MessageManager msgManager) {
		this.msgManager = msgManager;
	}

	public boolean isConnected() {
		return isConnected;
	}

	public int getChannelID() {
		return channelID;
	}

	@Override
	public String toString() {
		return "ProxyServer [domainID= " + domain.getDomainID()
				+ ", isConnected=" + isConnected + ", channelID=" + channelID
				+ "]";
	}
}
