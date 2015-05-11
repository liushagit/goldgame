package com.orange.core.proxy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;

import com.orange.core.Message;
import com.orange.log.LoggerFactory;

/**
 * 接受到客户端的消息，进行封装并转发到相应的目标服务器上
 * @author hqch
 *
 */
public class InputMessage extends Message {

	private static Logger logger = LoggerFactory
			.getLogger(InputMessage.class);
	
	private String header;
	private String sessionID;
	private byte[] msg;
	private long createTime;

	/**
	 * use for socket message
	 * 
	 * @param msg
	 * @param socketId
	 */
	public InputMessage(String header, byte[] msg) {
		this.header = header;
		this.msg = msg;
		createTime = new Date().getTime();
	}

	public String getHeader() {
		return header;
	}

	public byte[] getMsg() {
		return msg;
	}

	public void setMsg(byte[] msg) {
		this.msg = msg;
	}

	public void onHandler(Channel channel) {
		byte[] sessionByte = sessionID.getBytes();
		
		int len = 10 + msg.length + sessionByte.length;
		byte[] datas = new byte[len];
		int index = 0;
		index = putMsgHead(datas, index);
		index = putShort(datas, index, (short)len);
		index = putAgreeID(datas, index, (short)Integer.valueOf(header).intValue());
		
		index = putShort(datas, index, (short)sessionByte.length);
		System.arraycopy(sessionByte,0, datas, index, sessionByte.length);
		index += sessionByte.length;
		
		System.arraycopy(msg,0, datas, index, msg.length);
		index += msg.length;
		index = putMsgEnd(datas, index);

		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		try {
			buf.write(datas);
			
			ChannelBuffer buffer = ChannelBuffers.copiedBuffer(buf
					.toByteArray());
			channel.write(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}

		logger.debug("send server: protocol:" + header + ",len:" + datas.length 
				+ ",time:" + (System.currentTimeMillis() - createTime));
	}

	@Override
	public String toString() {
		return "InputMessage [header=" + header + ", sessionID=" + sessionID
				+ ", msg=" + msg.length + "]";
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

}