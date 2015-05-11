package com.orange.core.proxy;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;

import com.orange.log.LoggerFactory;

/**
 * 与目标服务器心跳包
 * 
 * @author hqch
 * 
 */
public class HeartbeatHandler extends IdleStateAwareChannelHandler {

	private static Logger logger = LoggerFactory
			.getLogger(HeartbeatHandler.class);
	
	private final String domainID;

	private static byte[] heartbeatMsg;
	private static ChannelBuffer buf;

	public HeartbeatHandler(String domainID) {
		this.domainID = domainID;
		heartbeatMsg = new HeartbeatMessage().getMsg();
		buf = ChannelBuffers.copiedBuffer(heartbeatMsg);
	}

	@Override
	public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e)
			throws Exception {

		logger.debug(domainID
				+ "'s heartbeat:"
				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new Date()));

		e.getChannel().write(buf);
	}
}
