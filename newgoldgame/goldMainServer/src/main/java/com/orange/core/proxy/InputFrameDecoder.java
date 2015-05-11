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
 * @author hqch
 * 
 */
public class InputFrameDecoder extends FrameDecoder {
	private static Logger logger = LoggerFactory
			.getLogger(InputFrameDecoder.class);
	private static final int DEFAULT_HEAD_LENGTH = 2;
	private int headLength;

	public InputFrameDecoder() {
		this.headLength = DEFAULT_HEAD_LENGTH;
	}

	public InputFrameDecoder(int length) {
		this.headLength = length;
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			ChannelBuffer buffer) throws Exception {
		try {
			if (buffer.readableBytes() < 4) {
				return null;
			}
			int index = buffer.readerIndex();

			short head = buffer.getShort(index);
			short len = buffer.getShort(index + 2);
			if(buffer.readableBytes() < len){
				return null;
			}
			if (head != 1000) {
				throw new Exception("message head value is error!");
			}

			short flag = buffer.getShort(index + 4);
			String headers = String.valueOf(flag);
			//
			byte[] content = new byte[len - 4 - headLength - 2];
			buffer.getBytes(index + 4 + headLength, content);

			short last = buffer.getShort(index + len - 2);
			if (last != 2000) {
				throw new Exception("message last value is error!");
			}
			buffer.skipBytes(len);

			InputMessage message = new InputMessage(headers, content);
			return message;
		} catch (Exception e) {
			logger.error("InputFrameDecoder",e);
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
