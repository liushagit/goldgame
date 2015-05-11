/**
 * Juice
 * com.juice.orange.game.handler
 * DefaultStringHandler.java
 */
package com.orange.core.cache;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import com.orange.log.LoggerFactory;
import com.orange.model.CacheMessage;

/**
 * 对聊天服务器消息进行解码
 * @author hqch
 * 
 */
public class CacheFrameDecoder extends FrameDecoder {
	private static Logger logger = LoggerFactory
			.getLogger(CacheFrameDecoder.class);
	private static final int DEFAULT_HEAD_LENGTH = 2;
	private int headLength;

	public CacheFrameDecoder() {
		this.headLength = DEFAULT_HEAD_LENGTH;
	}

	public CacheFrameDecoder(int length) {
		this.headLength = length;
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			ChannelBuffer buffer) throws Exception {
		try {
			if (!buffer.readable()) {
				return null;
			}
			int index = buffer.readerIndex();

			short head = buffer.getShort(index);
			if (head != 1000) {
				throw new Exception("message head value is error!");
			}
			short len = buffer.getShort(index + 2);

			short flag = buffer.getShort(index + 4);
			String headers = String.valueOf(flag);
			int userID = buffer.getInt(index + 6);
			//
			byte[] content = new byte[len - 6 - headLength - 2];
			buffer.getBytes(index + 6 + headLength, content);

			short last = buffer.getShort(index + len - 2);
			if (last != 2000) {
				throw new Exception("message last value is error!");
			}
			buffer.skipBytes(len);

			CacheMessage message = new CacheMessage(headers,userID, content);
			return message;
		} catch (Exception e) {
			logger.error(e);
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
