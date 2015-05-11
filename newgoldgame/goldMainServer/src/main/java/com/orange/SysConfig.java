package com.orange;

/**
 * 系统设置
 * @author hqch
 *
 */
public class SysConfig {

	/**分发server监听端口*/
	public static final int PROXY_SERVER_PORT = 11111;
	
	/**监控server端口*/
	public static final int MONITOR_SERVER_PORT = 9999;
	
	/**聊天server端口*/
	public static final int CHAT_SERVER_PORT = 22222;
	
	/**缓存地址*/
//	public static final String MEMCACHED_URL = "192.168.2.115:12000";
//	public static final String MEMCACHED_URL = "127.0.0.1:12000";
//	public static final String MEMCACHED_URL = "192.168.2.115:12000";
	public static final String MEMCACHED_URL = "112.124.37.93:12000";
	
	/**session 失效时间 10分钟*/
	public static final int TIME_OUT = 1000 * 60 * 10;
}
