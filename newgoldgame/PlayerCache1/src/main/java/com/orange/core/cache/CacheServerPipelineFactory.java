package com.orange.core.cache;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

/**
 * netty 处理 聊天信息
 * @author hqch
 *
 */
public class CacheServerPipelineFactory implements ChannelPipelineFactory {

	@Override
	public final ChannelPipeline getPipeline() throws Exception {
		return Channels.pipeline(new CacheFrameDecoder(),
				new CacheServerHandler());
	}
}
