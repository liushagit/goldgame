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
import org.jboss.netty.channel.socket.ServerSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.orange.core.chat.ChatServerPipelineFactory;
import com.orange.log.LoggerFactory;

/**
 * @author shaojieque 2013-4-18
 */
public class CharServerBootstrap {
	public static Logger logger = LoggerFactory
			.getLogger(CharServerBootstrap.class);

	/**
	 * 主函数
	 */
	public static void main(String[] args) throws Exception {
		logger.info("chat server init......");
		init();
		
		ChatServer.getInstance().start();
		logger.info("chat server started......");
	}

	private static void init(){
		InetSocketAddress addr = new InetSocketAddress(2222);// 需要监听的端口，即tcp连接建立的端口
		ServerSocketChannelFactory channelFactory = new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());

		ServerBootstrap bootstrap = new ServerBootstrap(channelFactory);

		ChannelPipelineFactory pipelineFactory = new ChatServerPipelineFactory();
		bootstrap.setPipelineFactory(pipelineFactory);

		Channel channel = bootstrap.bind(addr);
		if(!channel.isConnected()){
			logger.error("proxy server is shutdown.");
			System.exit(0);
		}
	}
}
