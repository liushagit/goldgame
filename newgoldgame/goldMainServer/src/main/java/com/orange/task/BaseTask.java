package com.orange.task;

import org.apache.log4j.Logger;

import com.orange.log.LoggerFactory;

/**
 * 定时器
 * @author hqch
 *
 */
public abstract class BaseTask {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 执行Task的具体内容，由定时器循环调用，或者从web指定调用一次
	 */
	public abstract void startNoRepeat();
}
