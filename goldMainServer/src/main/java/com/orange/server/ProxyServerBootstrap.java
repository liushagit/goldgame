/**
 * Juice
 * com.juice.orange.game.bootstrap
 * ServerBootstrap.java
 */
package com.orange.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.ServerSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.execution.ExecutionHandler;
import org.jboss.netty.handler.execution.OrderedMemoryAwareThreadPoolExecutor;

import com.orange.SysConfig;
import com.orange.core.DomainManager;
import com.orange.core.MemcachedResource;
import com.orange.core.ServerManager;
import com.orange.core.proxy.AcceptServerPipelineFactory;
import com.orange.http.HttpAlipayHandler;
import com.orange.http.HttpCaiFuTongPayHandler;
import com.orange.http.HttpDefaultHandler;
import com.orange.http.HttpMMHandler;
import com.orange.log.LoggerFactory;
import com.orange.task.BroadcastSystemMessageTask;
import com.orange.task.CheckDomainTask;
import com.orange.task.CheckProxyServerTask;
import com.orange.task.CheckSessionTask;
import com.orange.task.CheckSystemMessageTask;

/**
 * @author shaojieque 2013-4-18
 */
public class ProxyServerBootstrap {
	public static Logger logger = LoggerFactory
			.getLogger(ProxyServerBootstrap.class);

	/**转发请求包含数据大小*/
	private static final int MSG_SIZE = 1048576;

	/** 处理转发请求连接池大小 */
	private static final int POOL_SIZE = 100;

	/**
	 * 主函数
	 */
	public static void main(String[] args) throws Exception {
		logger.info("proxy server init......");
		DomainManager.getInstance().init(false);
		ServerManager serverManager = ServerManager.getInstance();
		serverManager.init();
		
		init();
		
		new MonitorServer().start();
		
		logger.info("memcached init......");
		MemcachedResource.buildMemcached(SysConfig.MEMCACHED_URL);

		logger.info("task init......");
		
		serverManager.registerTask("checkProxyServer", new CheckProxyServerTask());
		serverManager.registerTask("checkDomain", new CheckDomainTask());
		serverManager.registerTask("checkSystemMessage", new CheckSystemMessageTask());
		serverManager.registerTask("broadcastSystemMessage", new BroadcastSystemMessageTask());
		serverManager.registerTask("checkSession", new CheckSessionTask());
		
		logger.info("http handler init......");
		serverManager.registerHandler("default", new HttpDefaultHandler());
		serverManager.registerHandler("alipay", new HttpAlipayHandler());
		serverManager.registerHandler("baifubao", new HttpCaiFuTongPayHandler());
		serverManager.registerHandler("moblemm", new HttpMMHandler());
		
		logger.info("proxy server started......");
	}

	private static void init(){
		InetSocketAddress addr = new InetSocketAddress(SysConfig.PROXY_SERVER_PORT);// 需要监听的端口，即tcp连接建立的端口
		ServerSocketChannelFactory channelFactory = new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());
		DefaultChannelGroup allChannels = new DefaultChannelGroup(
				"accpetServerChannelGroup");

		ServerBootstrap bootstrap = new ServerBootstrap(channelFactory);

		ExecutionHandler executionHandler = new ExecutionHandler(
				new OrderedMemoryAwareThreadPoolExecutor(POOL_SIZE, MSG_SIZE,
						MSG_SIZE));
		ChannelPipelineFactory pipelineFactory = new AcceptServerPipelineFactory(
				executionHandler, allChannels);
		bootstrap.setPipelineFactory(pipelineFactory);

		Channel serverChannel = bootstrap.bind(addr);
		allChannels.add(serverChannel);
	}
}
