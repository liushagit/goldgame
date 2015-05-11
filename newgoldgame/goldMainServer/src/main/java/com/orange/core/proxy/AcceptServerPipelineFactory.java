package com.orange.core.proxy;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.handler.execution.ExecutionHandler;

/**
 * netty 处理接受客户端消息
 * @author hqch
 *
 */
public class AcceptServerPipelineFactory implements ChannelPipelineFactory {

	private final ExecutionHandler executionHandler;

	private DefaultChannelGroup channelGroup;

	private final AcceptServerHandler acceptServerHandler;

	public AcceptServerPipelineFactory(ExecutionHandler executionHandler,
			DefaultChannelGroup channelGroup) {
		this.channelGroup = channelGroup;
		this.executionHandler = executionHandler;
		this.acceptServerHandler = new AcceptServerHandler(this.channelGroup);
	}

	@Override
	public final ChannelPipeline getPipeline() throws Exception {
		return Channels.pipeline(new InputFrameDecoder(), executionHandler,
				acceptServerHandler);
	}
}
