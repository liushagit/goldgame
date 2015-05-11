/**
 * SuperStarLogin
 * com.orange.superstar.login
 * Application.java
 */
package com.orange.goldgame.login;


import org.apache.log4j.Logger;

import com.juice.orange.game.container.Container;
import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.login.server.UserServer;
import com.orange.goldgame.protocol.Protocol;


/**
 * @author shaojieque
 * 2013-5-3
 */
public class Application {
	private static Logger logger = LoggerFactory.getLogger(Application.class);
	
	public void init() {
		initServer();
	}
	
	public void initServer() {
		logger.info("Init servers......");
		//
		Container.registerServer("userserver", new UserServer());
		Container.registerServerPath(String.valueOf(Protocol.REQUEST_LOGIN), "userserver/isExisted");

	}
}
