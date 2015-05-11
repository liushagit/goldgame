package com.orange.core.proxy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;

import com.orange.log.LoggerFactory;

/**
 * 每个 proxyserver 拥有单独的消息管理器，负责消息发送
 * @author hqch
 * 
 */
public class MessageManager {

	private static Logger logger = LoggerFactory.getLogger(MessageManager.class);
	
	private LinkedBlockingQueue<InputMessage> receivedQueen = new LinkedBlockingQueue<InputMessage>(
			512);

	private ExecutorService pool;

	private int reStartThreadCount = 0;

	private Channel channel;

	public MessageManager(Channel channel) {
		this.channel = channel;
		start();
	}

	private void start() {
		this.pool = Executors.newCachedThreadPool();
		pool.submit(new PushRecvThread());
	}

	private class PushRecvThread implements Runnable {
		public void run() {
			while (true) {
				try{
					InputMessage message = waitForProcessMessage();
					if (message != null) {
						message.onHandler(channel);
					}
				} catch (Exception e){
					logger.error("PushRecvThread",e);
				}
			}
		}
	}

	public InputMessage waitForProcessMessage() {
		InputMessage message = null;
		while (message == null) {
			try {
				// 从队列中取继承MessagePack的实例
				message = receivedQueen.poll(10, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				logger.error("waitForProcessMessage",e);
			}
		}
		return message;
	}

	public void addSocketMessage(InputMessage message) {
		if (message != null) {
			try {
				boolean success = receivedQueen.offer(message, 15,
						TimeUnit.SECONDS);
				if (false == success) {
					// maybe PushRecvThread is break,restart the thread again
					if (reStartThreadCount < 10) {
						pool.submit(new PushRecvThread());
						reStartThreadCount++;
					}
				}
			} catch (InterruptedException e) {
				logger.error("addSocketMessage",e);
			}
			
			logger.debug("client msg len:" + receivedQueen.size());
		}
	}
}
