package com.orange.core.chat;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

/**
 * netty 处理 聊天信息
 * @author hqch
 *
 */
public class ChatServerPipelineFactory implements ChannelPipelineFactory {

	@Override
	public final ChannelPipeline getPipeline() throws Exception {
		return Channels.pipeline(new ChatFrameDecoder(),
				new ChatServerHandler());
	}
}
