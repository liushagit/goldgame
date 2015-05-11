package com.orange.util;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.DynamicChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;

public class HttpClientUtil {

	public static void writeReponse(MessageEvent e,String msg){
		HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1,
				HttpResponseStatus.OK);

		try {
			long length = msg.length();
			response.setHeader(HttpHeaders.Names.CONTENT_LENGTH,
					String.valueOf(length));
			response.setHeader(HttpHeaders.Names.CONTENT_TYPE,
					"text/html; charset=UTF-8");
			Channel ch = e.getChannel();
			ChannelBuffer buffer = new DynamicChannelBuffer(2048);
			buffer.writeBytes(msg.getBytes("UTF-8"));
			response.setContent(buffer);
			ch.write(response);
		} catch (Exception e2) {
			e2.printStackTrace();
		} finally {
			response.setHeader(HttpHeaders.Names.CONTENT_LENGTH, response
					.getContent().readableBytes());
		}
	}
}
