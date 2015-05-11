package com.orange.goldgame.server.lover;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.orange.goldgame.core.ErrorCode;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerFriend;
import com.orange.goldgame.domain.PlayerMessage;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.server.service.FriendService;

public class LoverMessageService {

	private static Map<String, List<PlayerMessage>> playerMessageMap = 
			new ConcurrentHashMap<String, List<PlayerMessage>>();

	private static final int read = 0;
	private static final int unread = 1;
	
	
	/**
	 * 获取跟某个情人的聊天信息
	 * @param player
	 * @param lover
	 * @return
	 */
//	public static ErrorCode getLoverMessage(Player player,Player lover){
//		List<PlayerMessage> playerMessages = getAllLoveMessage(player, lover);
//		readLoverMessage(player, lover);
//		return new ErrorCode(ErrorCode.SUCC,playerMessages);
//	}
	private static List<PlayerMessage> getAllLoveMessage(int playerId,int loverId){
		String key = getKey(playerId, loverId);
		List<PlayerMessage> playerMessages = playerMessageMap.get(key);
		if (playerMessages == null) {
			playerMessages = BaseEngine.getInstace().getPlayerMessageAction().quaryAllMessage(playerId, loverId);
			if (playerMessages == null) {
				playerMessages = new ArrayList<PlayerMessage>();
			}
			playerMessageMap.put(key, playerMessages);
		}
		return playerMessages;
	}
	
	private static List<PlayerMessage> getAllLoveMessage(Player player,Player lover){
		return getAllLoveMessage(player.getId(), lover.getId());
	}
	
	/**
	 * 读取所有消息
	 * @param player
	 * @param lover
	 */
//	public static void readLoverMessage(Player player,Player lover){
//		String key = getKey(player.getId(), lover.getId());
//		List<PlayerMessage> playerMessages = playerMessageMap.get(key);
//		if (playerMessages == null) {
//			playerMessages = new ArrayList<PlayerMessage>();
//		}
//		List<PlayerMessage> messages = new ArrayList<PlayerMessage>();
//		for (PlayerMessage pm : playerMessages) {
//			if (pm.getOtherId().intValue() == player.getId().intValue() 
//					&& pm.getStatus().intValue() == unread) {
//				pm.setStatus(read);
//				messages.add(pm);
//			}
//		}
//		if (messages.size() > 0) {
//			BaseEngine.getInstace().getPlayerMessageAction().updateAllPlayerMessage(messages);
//		}
//	}
	/**
	 * 检查指定情人是否有未读消息
	 * @param player
	 * @param lover
	 * @return
	 */
	public static boolean checkNewMessage(int playerId,int loverId){
		List<PlayerMessage> playerMessages = getAllLoveMessage(playerId, loverId);
		for (PlayerMessage pm : playerMessages) {
			if (pm.getOtherId().intValue() == playerId && pm.getStatus().intValue() == unread) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 检查所有情人是否有未读消息
	 * @param player
	 * @return
	 */
	public static boolean checkNewMessage(Player player){
		Map<Integer, PlayerFriend> playerLovers = player.getFriendMap();
		for (PlayerFriend pl : playerLovers.values()) {
			if (pl.getFriendType().intValue() == FriendService.LOVERTYPE) {
				if (checkNewMessage(player.getId(), pl.getFriendId())) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 添加新聊天记录
	 * @param player
	 * @param lover
	 * @param msg
	 * @return
	 */
	public static ErrorCode addPlayerMessage(Player player,Player lover,String msg){
		PlayerMessage playerMessage = createPlayerMessage(player, lover, msg);
		if (playerMessage == null) {
			return new ErrorCode(-1,"发送聊天失败，请重新发送");
		}
		//自己与对方都应该增加
		String key = getKey(player.getId(), lover.getId());
		List<PlayerMessage> list = playerMessageMap.get(key);
		if (list == null) {
			list = new ArrayList<PlayerMessage>();
			playerMessageMap.put(key, list);
		}
		list.add(playerMessage);
		return new ErrorCode(ErrorCode.SUCC);
	}
	
	private static String getKey(int playerId,int loverId){
		int src = playerId;
		int dsc =loverId;
		if (src > dsc) {
			int tmp = src;
			src = dsc;
			dsc = tmp;
		}
		return src + "_" + dsc;
	}
	private static PlayerMessage createPlayerMessage(Player player,Player lover,String msg){
		PlayerMessage playerMessage = new PlayerMessage();
		playerMessage.setCreateTime(new Date());
		playerMessage.setMsg(msg);
		playerMessage.setOtherId(lover.getId());
		playerMessage.setPlayerId(player.getId());
		playerMessage.setStatus(unread);
		int id = BaseEngine.getInstace().getPlayerMessageAction().insert(playerMessage);
		if (id < 0) {
			return null;
		}
		playerMessage.setId(id);
		return playerMessage;
	}
}
