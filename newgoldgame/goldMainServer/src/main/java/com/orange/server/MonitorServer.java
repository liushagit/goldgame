package com.orange.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.orange.SysConfig;
import com.orange.core.monitor.HttpServerPipelineFactory;

/**
 * 监控服务器
 * @author hqch
 *
 */
public class MonitorServer {

	public void start(){
		ServerBootstrap bootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool()));
		ChannelPipelineFactory pipelineFactory = new HttpServerPipelineFactory();
		bootstrap.setPipelineFactory(pipelineFactory);
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
		// 创建服务器端channel的辅助类,接收connection请求
		bootstrap.bind(new InetSocketAddress(SysConfig.MONITOR_SERVER_PORT));
	}
}
