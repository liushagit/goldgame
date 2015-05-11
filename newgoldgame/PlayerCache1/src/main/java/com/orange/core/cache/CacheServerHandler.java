package com.orange.core.cache;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.orange.log.LoggerFactory;
import com.orange.model.CacheMessage;
import com.orange.model.Message;

/**
 * 缓存信息处理
 * @author hqch
 */
public class CacheServerHandler extends SimpleChannelHandler {

	private static Logger logger = LoggerFactory
			.getLogger(CacheServerHandler.class);

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
		CacheMessage msg = (CacheMessage) messageEvent.getMessage();
		
		if(msg.getProtocol().equals(Message.ADD_CACHE)){
			CacheManager.getInstance().addCache(msg);
		} else if(msg.getProtocol().equals(Message.REMOVE_CACHE)){
			CacheManager.getInstance().removeCache(msg);
		} else if(msg.getProtocol().equals(Message.GET_CACHE)){
			CacheManager.getInstance().getCache(msg.getUserID(),channelHandlerContext.getChannel());
		} else {
			logger.info("protocor is error." + msg);
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
			e.getCause().printStackTrace();
			logger.error(e.getCause().getMessage());
		}
	}
}
