/**
 * SuperStarServer
 * com.orange.superstar.server
 * ActionAware.java
 */
package com.orange.goldgame.server;

import com.juice.orange.game.container.Container;
import com.orange.goldgame.action.AppConfigAction;
import com.orange.goldgame.action.ApplicationAction;
import com.orange.goldgame.action.FriendAction;
import com.orange.goldgame.action.GiftAction;
import com.orange.goldgame.action.PlayerAction;
import com.orange.goldgame.action.PropsAction;
import com.orange.goldgame.action.TaskAction;
/**
 * @author shaojieque
 * 2013-5-6
 */
public class ActionAware extends Application {
	public static final String REMOTE_PREFIX_SYSTEM = "GoldenFlowerSystemServer";
	
	protected static ApplicationAction applicationAction = (ApplicationAction)Container.
			createRemoteService(ApplicationAction.class, REMOTE_PREFIX_SYSTEM);
	
	protected static PlayerAction playerActionIpml = (PlayerAction)Container.
			createRemoteService(PlayerAction.class, REMOTE_PREFIX_SYSTEM);
	
			
	protected static GiftAction giftActionIpml = (GiftAction)Container.
			createRemoteService(GiftAction.class, REMOTE_PREFIX_SYSTEM);
	
	protected static FriendAction friendActionIpml = (FriendAction)Container.
			createRemoteService(FriendAction.class, REMOTE_PREFIX_SYSTEM);
	
	protected static TaskAction taskActionIpml = (TaskAction)Container.
			createRemoteService(TaskAction.class, REMOTE_PREFIX_SYSTEM);
	
	protected static PropsAction propActionIpml = (PropsAction)Container.
			createRemoteService(PropsAction.class, REMOTE_PREFIX_SYSTEM);

	protected static AppConfigAction appConfigActionImpl = (AppConfigAction)Container.
			createRemoteService(AppConfigAction.class, REMOTE_PREFIX_SYSTEM);
	

	
	
}
