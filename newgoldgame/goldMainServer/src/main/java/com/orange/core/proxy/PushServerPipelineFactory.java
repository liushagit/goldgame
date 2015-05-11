package com.orange.core.proxy;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.handler.execution.ExecutionHandler;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;

/**
 * netty 处理目标服务器返回处理的结果
 * @author hqch
 *
 */
public class PushServerPipelineFactory implements ChannelPipelineFactory {

	private final ExecutionHandler executionHandler;

	private DefaultChannelGroup channelGroup;

	private final PushServerHandler pushServerHandler;

	private final IdleStateHandler idleStateHandler;
	
	private final HeartbeatHandler heartbeatHandler;
	
	public PushServerPipelineFactory(String domainID,ExecutionHandler executionHandler,
			DefaultChannelGroup channelGroup) {
		this.channelGroup = channelGroup;
		this.executionHandler = executionHandler;
		this.pushServerHandler = new PushServerHandler(this.channelGroup);
		Timer timer = new HashedWheelTimer();
		this.idleStateHandler = new IdleStateHandler(timer, 60, 60,0);
		this.heartbeatHandler = new HeartbeatHandler(domainID);
	}

	@Override
	public final ChannelPipeline getPipeline() throws Exception {
		return Channels.pipeline(new OutPutFrameDecoder(), 
				executionHandler,
				pushServerHandler,
				idleStateHandler,
				heartbeatHandler);
	}
}
