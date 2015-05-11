package com.orange.server;

import java.io.ByteArrayOutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import com.orange.core.chat.ChatServerPipelineFactory;
import com.orange.model.ChatMessage;

/**
 * 聊天服务器
 * @author hqch
 *
 */
public class ChatServer {

	private static final String IP = "127.0.0.1";
	
	private static final int PORT = 2222;
	
	private Channel channel;
	
	private static ChatServer instance = new ChatServer();
	
	private ChatServer(){}
	
	public static ChatServer getInstance(){
		return instance;
	}
	
	public void start(){
		ClientBootstrap bootstrap = new ClientBootstrap(
				new NioClientSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool()));

		ChannelPipelineFactory pipelineFactory = new ChatServerPipelineFactory();
		bootstrap.setPipelineFactory(pipelineFactory);

		// 创建无连接传输channel的辅助类(UDP),包括client和server
		ChannelFuture future = bootstrap.connect(new InetSocketAddress(IP,PORT));
		channel = future.awaitUninterruptibly().getChannel();
	}
	
	/**
	 * 向代理服务器发送消息
	 * @param msg
	 */
	public void sendMessage2Server(ChatMessage msg){
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		try {
			buf.write(msg.getMsg());
			ChannelBuffer buffer = ChannelBuffers.copiedBuffer(buf
					.toByteArray());
			channel.write(buffer);
			buf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
