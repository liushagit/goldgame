package com.orange.core.chat;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.orange.log.LoggerFactory;
import com.orange.model.ChatMessage;
import com.orange.server.ChatServer;

/**
 * 接受聊天服务器信息，广播给每个玩家
 * @author hqch
 */
public class ChatServerHandler extends SimpleChannelHandler {

	private static Logger logger = LoggerFactory
			.getLogger(ChatServerHandler.class);

	@Override
	public void channelDisconnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) throws Exception {
		super.channelDisconnected(ctx, e);
	}

	@Override
	public void channelConnected(final ChannelHandlerContext ctx,
			final ChannelStateEvent e) throws Exception {
		ctx.sendUpstream(e);
	}

	@Override
	public void messageReceived(ChannelHandlerContext channelHandlerContext,
			MessageEvent messageEvent) throws Exception {
		ChatMessage msg = (ChatMessage) messageEvent.getMessage();
		
		ChatServer.getInstance().sendMessage2Server(msg);
		
		logger.info("received clinet messgae:" + msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		if (e.getCause() instanceof ClosedChannelException) {
			e.getChannel().close();
		} else if (e.getCause() instanceof IOException) {
			e.getChannel().close();
		} else {
			e.getCause().printStackTrace();
			logger.error(e.getCause().getMessage());
		}
	}
}
