package com.orange.core.proxy;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.group.DefaultChannelGroup;

import com.orange.core.Message;
import com.orange.core.ServerManager;
import com.orange.log.LoggerFactory;
import com.orange.util.HttpClientUtil;

/**
 * 由目标服务器返回处理的结果，需转发到相应的客户端
 * 
 * @author hqch
 * 
 */
public class PushServerHandler extends SimpleChannelHandler {

	private static Logger logger = LoggerFactory
			.getLogger(PushServerHandler.class);

	private DefaultChannelGroup channelGroup;

	private static ServerManager serverManager = ServerManager.getInstance();
	
	public PushServerHandler(DefaultChannelGroup channelGroup) {
		this.channelGroup = channelGroup;
	}

	@Override
	public void channelDisconnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) throws Exception {
		super.channelDisconnected(ctx, e);
		channelGroup.remove(ctx.getChannel());
		serverManager.shutDownServer(ctx.getChannel().getId());
	}

	@Override
	public void channelConnected(final ChannelHandlerContext ctx,
			final ChannelStateEvent e) throws Exception {
		channelGroup.add(ctx.getChannel());
		ctx.sendUpstream(e);
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx,
			MessageEvent messageEvent) throws Exception {
		OutputMessage message = (OutputMessage)messageEvent.getMessage();
		int protocol = message.getProtocol();
		if(protocol == Message.CHAT_PROTOCOL){
			ChatMessage chat = new ChatMessage(message.getOriginalDatas());
			serverManager.broadcastMessage(chat.getUserID(),protocol,message.getCreateTime(),chat.getUserMsg());
		} else if(protocol == Message.RESPONSE_ALIPAY_PROTOCOL 
				|| protocol == Message.RESPONSE_BFB_PROTOCOL
				|| protocol == Message.RESPONSE_MOBELLMM_PROTOCOL
				|| protocol == Message.RESPONSE_WHEEL_PROTOCOL
				|| protocol == Message.RESPONSE_WEIMI_PROTOCOL){
			byte[] datas = message.getOriginalDatas();
			String channelID = message.getSessionID();
			MessageEvent client = serverManager.getChannelByID(Integer.valueOf(channelID));
			String msg = new String(Arrays.copyOfRange(datas, 4, datas.length));
			
			HttpClientUtil.writeReponse(client, msg);
		} else {
			String sessionID = message.getSessionID();
			serverManager.sendMessage(sessionID,protocol,message.getCreateTime(), message.getMsg());
		}
		
		logger.debug("received server messgae:" + message);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		if (e.getCause() instanceof ClosedChannelException) {
			e.getChannel().close();
		} else if (e.getCause() instanceof IOException) {
			e.getChannel().close();
		} else {
			logger.error("PushServerHandler",e.getCause());
		}
	}

}
