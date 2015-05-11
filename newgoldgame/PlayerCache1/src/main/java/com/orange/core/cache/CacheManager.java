package com.orange.core.cache;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;

import com.orange.log.LoggerFactory;
import com.orange.model.CacheMessage;
import com.orange.model.UserCache;
import com.orange.util.BeanFactory;

/**
 * 缓存管理
 * @author hqch
 *
 */
public class CacheManager {

	private static Logger logger = LoggerFactory
			.getLogger(CacheManager.class);
	
	private Map<Integer,UserCache> cacheMap;

	private static CacheManager instance = new CacheManager();
	
	private CacheManager(){
		this.cacheMap = Collections.synchronizedMap(new HashMap<Integer,UserCache>());
	}
	
	public static CacheManager getInstance(){
		return instance;
	}

	/**
	 * 添加缓存
	 * @param msg
	 */
	public void addCache(CacheMessage msg) {
		cacheMap.put(msg.getUserID(), BeanFactory.createUserCacheByByteArray(msg.getMsg()));
	}

	/**
	 * 去除缓存
	 * @param msg
	 */
	public void removeCache(CacheMessage msg) {
		cacheMap.remove(msg.getUserID());
	}

	/**
	 * 获取缓存
	 * @param userID
	 */
	public void getCache(int userID,Channel channel) {
		UserCache cache = cacheMap.get(userID);
		if(cache == null){
			logger.warn("user have not cache.userID:" + userID);
			return;
		}
		
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		try {
			buf.write(cache.getMsg());
			ChannelBuffer buffer = ChannelBuffers.copiedBuffer(buf
					.toByteArray());
			channel.write(buffer);
			buf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
