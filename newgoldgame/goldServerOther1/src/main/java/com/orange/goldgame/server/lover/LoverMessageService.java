package com.orange.goldgame.server.lover;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.juice.orange.game.container.GameSession;
import com.orange.goldgame.core.ErrorCode;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerFriend;
import com.orange.goldgame.domain.PlayerMessage;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.server.manager.SessionManger;
import com.orange.goldgame.server.service.BaseServer;
import com.orange.goldgame.server.service.FriendService;

public class LoverMessageService {

	private static Map<String, List<PlayerMessage>> playerMessageMap = 
			new ConcurrentHashMap<String, List<PlayerMessage>>();

	private static final int read = 0;
	private static final int unread = 1;
	private static final int deleteTime = 6 * 24 * 60 * 60 * 1000;
//	private static final int preLength = 20;
	private static final int preLength = 3;
	
	
	/**
	 * 获取跟某个情人的聊天信息
	 * @param player
	 * @param lover
	 * @return
	 */
	public static ErrorCode getLoverMessage(Player player,Player lover,int index,boolean read){
		if (index <= 0) {
			index = 1;
		}
		List<PlayerMessage> playerMessages = getAllLoveMessage(player, lover);
		int le = playerMessages.size();
		
		if (read) {
			readLoverMessage(player, lover);
		}
		//对消息进行排序
		Collections.sort(playerMessages, new Comparator<PlayerMessage>() {
			@Override
			public int compare(PlayerMessage o1, PlayerMessage o2) {
				return (int)(o2.getCreateTime().getTime() - o1.getCreateTime().getTime());
			}
		});
		
		List<PlayerMessage> playerMessages1 = playerMessages;
		if (le > index * preLength) {
			playerMessages1 = playerMessages.subList(0, index * preLength);
		}else {
			if (player.getLastGetLoverMessage() < index) {
				GameSession session = SessionManger.getInstance().getSession(player.getId());
				if (session != null) {
					BaseServer.sendErrorMsg(session, (short)-1, "已经没有更多消息了！");
				}
			}
		}
		Collections.sort(playerMessages1, new Comparator<PlayerMessage>() {
			@Override
			public int compare(PlayerMessage o1, PlayerMessage o2) {
				return (int)(o1.getCreateTime().getTime() - o2.getCreateTime().getTime());
			}
		});
		player.setLastGetLoverMessage(index);
		return new ErrorCode(ErrorCode.SUCC,playerMessages1);
	}
	private static List<PlayerMessage> getAllLoveMessage(int playerId,int loverId){
		String key = getKey(playerId, loverId);
		List<PlayerMessage> playerMessages = playerMessageMap.get(key);
		if (playerMessages == null || playerMessages.size() == 0) {
			playerMessages = BaseEngine.getInstace().getPlayerMessageAction().quaryAllMessage(playerId, loverId);
			if (playerMessages == null) {
				playerMessages = new ArrayList<PlayerMessage>();
			}
			if (playerMessages != null && playerMessages.size() > 0) {
				playerMessageMap.put(key, playerMessages);
			}
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
	public static void readLoverMessage(Player player,Player lover){
		String key = getKey(player.getId(), lover.getId());
		List<PlayerMessage> playerMessages = playerMessageMap.get(key);
		if (playerMessages == null) {
			playerMessages = new ArrayList<PlayerMessage>();
		}
		List<PlayerMessage> deleteList = new ArrayList<PlayerMessage>();
		List<PlayerMessage> updateList = new ArrayList<PlayerMessage>();
		for (PlayerMessage pm : playerMessages) {
			if (System.currentTimeMillis() - pm.getCreateTime().getTime() > deleteTime) {
				deleteList.add(pm);
				continue;
			}
			if (pm.getOtherId().intValue() == player.getId().intValue() 
					&& pm.getStatus().intValue() == unread) {
				pm.setStatus(read);
				updateList.add(pm);
			}
		}
		if (updateList.size() > 0 || deleteList.size() > 0) {
			BaseEngine.getInstace().getPlayerMessageAction().updateDeleteAllPlayerMessage(updateList,deleteList);
		}
	}
	/**
	 * 检查指定情人是否有未读消息
	 * @param player
	 * @param lover
	 * @return
	 */
	public static boolean checkNewMessage(int loverId,int playerId){
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
	public static int checkNewMessage(Player player){
		Map<Integer, PlayerFriend> playerLovers = player.getLoverMap();
		if (playerLovers == null) {
			FriendService.getPlayerFriendByPlayer(player, true, FriendService.LOVERTYPE);
		}
		playerLovers = player.getLoverMap();
		if (playerLovers == null) {
			return 0;
		}
		for (PlayerFriend pl : playerLovers.values()) {
			if (pl.getFriendType().intValue() == FriendService.LOVERTYPE) {
				if (checkNewMessage(pl.getFriendId(),player.getId())) {
					return pl.getFriendId();
				}
			}
		}
		return 0;
	}
	
	/**
	 * 添加新聊天记录
	 * @param player
	 * @param lover
	 * @param msg
	 * @return
	 */
	public static ErrorCode addPlayerMessage(Player player,Player lover,String msg){
		Map<Integer,PlayerFriend> loverMap = player.getLoverMap();
		if (loverMap == null) {
			FriendService.getPlayerFriendByPlayer(player,true,FriendService.LOVERTYPE);
		}
		PlayerFriend pfFriend = player.getLoverMap().get(lover.getId());
		if (pfFriend == null) {
			FriendService.getPlayerFriendByPlayer(player,true,FriendService.LOVERTYPE);
		}
		if (pfFriend.getFriendStatus() != FriendService.LOVERSTATUSSUCC) {
			return new ErrorCode(-1,"对方尚未同意，发送失败！");
		}
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
