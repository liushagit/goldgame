package com.orange.core.proxy;

import java.io.ByteArrayOutputStream;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;

import com.google.code.yanf4j.util.ConcurrentHashSet;
import com.juice.orange.game.log.LoggerFactory;

/**
 * 客户端session
 * @author hqch
 *
 */
public class SocketSession {

	private Logger logger = LoggerFactory.getLogger(SocketSession.class);

	/**SESSION ID*/
	private String id;
	
	/**用户ID*/
	private int userID;
	
	/**最后在线目标服务器*/
	private String lastDomainID;
	
	/**在线过目标服务器*/
	private Set<String> domainSet;

	/**客户端链接*/
	private final ChannelHandlerContext ctx;
	
	/**是否在线*/
	private boolean isOnline;
	
	/**掉线时间*/
	private long disConnectionTime;
	
	private long curDate;
	
	/**不公平锁*/
	private Lock lock = new ReentrantLock();
	
	public SocketSession(ChannelHandlerContext ctx){
		this.ctx = ctx;
		this.id = UUID.randomUUID().toString();
		this.domainSet = new ConcurrentHashSet<String>();
	}

	public void sendMessage(long createTime,int protocol,byte[] datas){
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		try {
			buf.write(datas);
			ChannelBuffer buffer = ChannelBuffers.copiedBuffer(buf
					.toByteArray());
			ctx.getChannel().write(buffer);
			buf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.debug("response datas.protocol:"+protocol+",time:" 
				+ (System.currentTimeMillis() - createTime));
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public ChannelHandlerContext getCtx() {
		return ctx;
	}

	public Set<String> getDomainSet() {
		return domainSet;
	}

	public void setDomainSet(Set<String> domainSet) {
		this.domainSet = domainSet;
	}

	public String getLastDomainID() {
		return lastDomainID;
	}

	public void setLastDomainID(String lastDomainID) {
		this.lastDomainID = lastDomainID;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	public long getDisConnectionTime() {
		return disConnectionTime;
	}

	public void setDisConnectionTime(long disConnectionTime) {
		this.disConnectionTime = disConnectionTime;
	}
	
	public void lock() {
		lock.lock();
	}
	
	public void unlock() {
		lock.unlock();
	}

	public long getCurDate() {
		return curDate;
	}

	public void setCurDate(long curDate) {
		this.curDate = curDate;
	}

	@Override
	public String toString() {
		return "SocketSession [id=" + id + ", userID=" + userID
				+ ", lastDomainID=" + lastDomainID + ", domainSet=" + domainSet
				+ ", ctx=" + ctx + ", isOnline=" + isOnline
				+ ", disConnectionTime=" + disConnectionTime + ", curDate="
				+ curDate + ", lock=" + lock + "]";
	}
}
