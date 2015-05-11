/**
 * Juice
 * com.juice.orange.game.handler
 * DefaultStringHandler.java
 */
package com.orange.core.proxy;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import com.orange.log.LoggerFactory;

/**
 * 对客户端消息进行解码
 * 
 * @author hqch
 * 
 */
public class OutPutFrameDecoder extends FrameDecoder {
	private static Logger logger = LoggerFactory
			.getLogger(OutPutFrameDecoder.class);

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			ChannelBuffer buffer) throws Exception {
		try {
			if (buffer.readableBytes() < 4) {
				return null;
			}
			
			int index = buffer.readerIndex();
			short len = buffer.getShort(index + 2);
			if(buffer.readableBytes() < len){
				return null;
			}
			
			short head = buffer.getShort(index);
			if (head != 1000) {
				throw new Exception("message head value is error!");
			}
			
			short strLen = buffer.getShort(index + 4);
			byte[] sessionIDByte = new byte[strLen];
			buffer.getBytes(index + 6, sessionIDByte);
			//
			byte[] content = new byte[len - 6 - strLen - 2];
			buffer.getBytes(index + 6 + strLen, content);

			short last = buffer.getShort(index + len - 2);
			if (last != 2000) {
				throw new Exception("message last value is error!");
			}
			buffer.skipBytes(len);
			
			OutputMessage message = new OutputMessage(new String(sessionIDByte), content);
			return message;
		} catch (Exception e) {
			logger.error("OutPutFrameDecoder",e);
		}

		return null;
	}

	protected ChannelBuffer extractFrame(ChannelBuffer buffer, int index,
			int length) {
		ChannelBuffer frame = buffer.factory().getBuffer(length);
		frame.writeBytes(buffer, index, length);
		return frame;
	}
}
