package com.orange.core.monitor;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.http.HttpContentCompressor;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;
import org.jboss.netty.handler.stream.ChunkedWriteHandler;

/**
 * netty 处理 wab 请求
 * @author hqch
 *
 */
public class HttpServerPipelineFactory implements ChannelPipelineFactory {

	@Override
	public ChannelPipeline getPipeline() throws Exception {
		return Channels.pipeline(
				new HttpRequestDecoder(), 
				new HttpResponseEncoder(),
				new ChunkedWriteHandler(),
				new HttpContentCompressor(),
				new MonitorServerHandler());
		}
}
