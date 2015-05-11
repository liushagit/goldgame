/**
 * SuperStarServer
 * com.orange.superstar.server
 * ActionAware.java
 */
package com.orange.goldgame.login;

import com.juice.orange.game.container.Container;
import com.orange.goldgame.action.ServerConfigAction;

/**
 * @author shaojieque
 * 2013-5-6
 */
public class ActionAware extends Application {
	public static final String REMOTE_PREFIX_SYSTEM = "GoldenFlowerSystemServer";
	
	protected ServerConfigAction serverInfoImpl = (ServerConfigAction) Container.
			createRemoteService(ServerConfigAction.class, REMOTE_PREFIX_SYSTEM);
	
	
}
