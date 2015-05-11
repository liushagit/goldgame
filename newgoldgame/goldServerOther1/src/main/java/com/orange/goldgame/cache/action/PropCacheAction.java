package com.orange.goldgame.cache.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.juice.orange.game.container.GameRoom;
import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.action.PlayerPropsAction;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.core.ErrorCode;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerProps;
import com.orange.goldgame.domain.PropsConfig;
import com.orange.goldgame.exception.GoldException;
import com.orange.goldgame.server.SynPlayerProps;
import com.orange.goldgame.server.domain.Card;
import com.orange.goldgame.server.domain.GameTable;
import com.orange.goldgame.server.domain.GameTableSeat;
import com.orange.goldgame.server.domain.Gamer;
import com.orange.goldgame.server.domain.HandCards;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.server.manager.GameRoomManager;
import com.orange.goldgame.server.manager.GamerSet;

/**
 * 道具service
 * @author guojiang
 *
 */
public class PropCacheAction {

	private static final Logger log = LoggerFactory.getLogger(PropCacheAction.class);
	/*** 换牌 ***/
	public static final int change_card = 5;
	/**
	 * 四倍卡
	 */
	public static final int four_card = 6;
	/**
	 * 8倍卡
	 */
	public static final int eight_card = 7;
	/**
	 * 禁比卡
	 */
	public static final int band_card = 8;
	
	/**
	 * 鲜花
	 */
	public static final int flower = 9;
	/**
	 * 鸡蛋
	 */
	public static final int egg = 10;
	/**
	 * 车
	 */
	public static final int car = 11;
	/**
	 * 房
	 */
	public static final int house = 12;
	/**
	 * 船
	 */
	public static final int boat = 13;
	
	/**
	 * 兑换券
	 */
	public static final int exchange_prop = 14;
	
	/**
	 * 道具
	 */
	public static final int PROP_ARM = 1;
	/**
	 * 礼物
	 */
	public static final int PROP_PRESS = 2;
	/**
	 * 兑换券
	 */
	public static final int PROP_EXCHANGE = 3;
	
	/**
	 * 根据道具id获取玩家道具
	 * @param player
	 * @param props_id
	 * @return
	 */
	public static PlayerProps getplayerPropByPropid(Player player ,int props_id){
		Map<Integer, PlayerProps> playerProps = player.getPlayerProps();
		if (playerProps == null || SynPlayerProps.isQuary(player)) {
			quaryAllPlayerProps(player);
			playerProps = player.getPlayerProps();
		}
		for (PlayerProps props : playerProps.values()) {
			if (props.getPropsConfigId().intValue() == props_id) {
				SynPlayerProps.checkPlayerPropsNum(player, props);
				return props;
			}
		}
		return null;
	}
	
	public static List<PlayerProps> quaryAllPlayerProps(Player player){
		Map<Integer, PlayerProps> playerProps = player.getPlayerProps();
		List<PlayerProps> list = new ArrayList<PlayerProps>();
		if (playerProps == null || SynPlayerProps.isQuary(player)) {
			PlayerPropsAction action = BaseEngine.getInstace().getPlayerPropsActionImpl();
			list = action.quaryAllPlayerProps(player);
			
			playerProps = new HashMap<Integer, PlayerProps>();
			if (list != null) {
				for (PlayerProps pp : list) {
					playerProps.put(pp.getId(), pp);
				}
			}
			player.setPlayerProps(playerProps);
			
		}
		playerProps = player.getPlayerProps();
		PropsConfig pc = null;
		list.clear();
		for (PlayerProps pp : playerProps.values()) {
			pc = ResourceCache.getInstance().getPropsConfigs().get(pp.getPropsConfigId());
			if (pc.getPropsType() == PROP_ARM) {
				SynPlayerProps.checkPlayerPropsNum(player, pp);
				list.add(pp);
			}
		}
		return list;
	}
	
	/**
	 * 根据道具类型获取数据
	 * @param player
	 * @param props_type
	 * @return
	 */
	public static List<PlayerProps> getPlayerPropByType(Player player ,int props_type){
		List<PlayerProps> list = new ArrayList<PlayerProps>();
		Map<Integer, PlayerProps> playerProps = player.getPlayerProps();
		for (PlayerProps props : playerProps.values()) {
			if (props.getPropsConfigId().intValue() == props_type) {
				SynPlayerProps.checkPlayerPropsNum(player, props);
				list.add(props);
			}
		}
		return list;
	}
	
	public static PlayerProps addProp(Player player,int prop_id)  throws GoldException{
		PropsConfig prop = ResourceCache.getInstance().getPropsConfigs().get(prop_id);
		return addProp(player,prop);
	}
	public static PlayerProps addProp(Player player,int prop_id,int num)  throws GoldException{
		PropsConfig prop = ResourceCache.getInstance().getPropsConfigs().get(prop_id);
		PlayerProps playerProp = null;
		if(num > 0){
			playerProp = addProp(player,prop,num);
		}else{
			playerProp = getplayerPropByPropid(player, prop_id);
			releasePlayerProp(player, playerProp, -num);
		}
		return playerProp;
	}
	public static PlayerProps addProp(Player player,PropsConfig prop)  throws GoldException{
		return addProp(player, prop, 1);
	}
	public static PlayerProps addProp(Player player,PropsConfig prop,int num)  throws GoldException{
		if (prop == null) {
			throw new GoldException("no props");
		}
		PlayerPropsAction action = BaseEngine.getInstace().getPlayerPropsActionImpl();
		PlayerProps pp = getplayerPropByPropid(player, prop.getId());
		if (pp == null) {
			pp = createPlayerProp(player, prop, num);
			int ppid = action.insertPlayerProp(pp);
			if (ppid < 0) {
				return null;
			}
			pp.setId(ppid);
			player.getPlayerProps().put(pp.getPropsConfigId(), pp);
		}else {
			pp.setNumber(pp.getNumber() + num);
			action.updatePlayerProps(pp);
		}
		SynPlayerProps.updatePlayerProps(player, pp);
		return pp;
	}
	
	private static PlayerProps createPlayerProp(Player player,PropsConfig pc,int num){
		PlayerProps pp = new PlayerProps();
		pp.setNumber(num);
		pp.setPlayerId(player.getId());
		pp.setPropsConfigId(pc.getId());
		return pp;
	}
	public static boolean releasePlayerProp(Player player,int prop_id)  throws GoldException{
		PlayerProps pp = getplayerPropByPropid(player, prop_id);
		return releasePlayerProp(player, pp);
	}
	
	/**
	 * 删除玩家道具
	 * @param player
	 * @param props
	 * @return
	 */
	public static boolean releasePlayerProp(Player player,PlayerProps props)  throws GoldException{
		return releasePlayerProp(player, props, 1);
	}
	public static boolean releasePlayerProp(Player player,PlayerProps props,int num)  throws GoldException{
		if (props == null || props.getNumber() < num) {
			return false;
		}
		if (props.getPlayerId().intValue() != player.getId().intValue()) {
			return false;
		}
		SynPlayerProps.checkPlayerPropsNum(player, props);
		PlayerPropsAction action = BaseEngine.getInstace().getPlayerPropsActionImpl();
		if (props.getNumber() - num <= 0) {
			action.deletePlayerProp(props);
			player.getPlayerProps().remove(props.getId());
			SynPlayerProps.deletePlayerProps(player, props);
			
		}else {
			props.setNumber(props.getNumber() - num);
			action.updatePlayerProps(props);
			SynPlayerProps.updatePlayerProps(player, props);
		}
		return true;
	}
	
	/**
	 * 使用换牌卡 消耗道具并获取新牌
	 * @param player
	 * @return
	 */
	private static ErrorCode useChangeCard(Player player,GameTable table)  throws GoldException{
		
		//使用换牌卡
		PropsConfig pc = ResourceCache.getInstance().getPropsConfigs().get(change_card);
		PlayerProps pp = getplayerPropByPropid(player, change_card);
		if (pp == null) {
			return new ErrorCode(1, "没有"+pc.getName()+"，不能使用！");
		}
		if (!table.getPoker().checkPoker()) {
			return new ErrorCode(1, "亲，已经不能再换牌了！");
		}
		Card card = table.getPoker().getOneCardRandom();
		if (card == null) {
			return new ErrorCode(1, pc.getName()+"获取新牌失败！");
		}
		boolean res = releasePlayerProp(player, pp);
		if (!res) {
			//成功获取牌后需要给玩家放在身上
			return new ErrorCode(1, pc.getName()+"使用失败！");
		}
		return new ErrorCode(ErrorCode.SUCC, card);
		
	}
	
	/**
	 * 使用换牌卡
	 * @param player
	 * @param card_id
	 * @return
	 */
	public static ErrorCode useExchangeProps(Player player,byte card_id)  throws GoldException{
		ErrorCode res = checkUseCard(player);
		if (res.getStatus() != ErrorCode.SUCC) {
			return res;
		}
		GameRoom room = GameRoomManager.getInstance().getRoomByPlayerId(player.getId());
		GameTable table = (GameTable) room.getObject(Constants.ROOM_TO_TABLE_KEY);
		Gamer gamer = GamerSet.getInstance().getGamerByPlayerId(player.getId());
		if (!gamer.isLookCard()) {
			return new ErrorCode(1, "没有看牌，不能使用换牌卡！");
		}
		res = useChangeCard(player,table);
		if (res.getStatus() != ErrorCode.SUCC) {
			return res;
		}
		Card card = (Card) res.getObject();
		//替换原有牌
		HandCards handCatds = gamer.getHandCards();
		Card[] cards = handCatds.getCards();
		for (int i = 0 ; i < cards.length ; i ++) {
			if (cards[i].getCardId() == card_id) {
				cards[i] = card;
				card.setChange(true);
				break;
			}
		}
		return new ErrorCode(ErrorCode.SUCC, card);
	}
	
	/**
	 * 使用n倍卡
	 * @param player
	 * @return
	 */
	public static ErrorCode useNCard(Player player,int prop_id)  throws GoldException{
		PropsConfig pc = ResourceCache.getInstance().getPropsConfigs().get(prop_id);
		PlayerProps pp = getplayerPropByPropid(player, prop_id);
		if (pp == null) {
			return new ErrorCode(1,"没有"+ pc.getName()+"，不能使用。");
		}
		
		ErrorCode res = checkUseCard(player);
		if (res.getStatus() != ErrorCode.SUCC) {
			return res;
		}
		Gamer gamer = GamerSet.getInstance().getGamerByPlayerId(player.getId());

		if(gamer.getUseNcard()==pc.getMultiple()){
			return new ErrorCode(1,"您已使用过"+ pc.getName() + ",无需再次使用!");
		}
		
		boolean canUse = releasePlayerProp(player, prop_id);
//		PropsConfig pc = ResourceCache.getInstance().getPropsConfigs().get(prop_id);
		if (!canUse) {
			return new ErrorCode(1,"使用"+ pc.getName() + "失败");
		}
		gamer.setUseNcard(pc.getMultiple());
		return new ErrorCode(ErrorCode.SUCC);
	}
	
	/**
	 * 使用禁比卡
	 * 禁比卡使用限制个回合
	 * @param player
	 * @return
	 */
	public static ErrorCode useNoCompareCard(Player player)  throws GoldException{
		PropsConfig pc = ResourceCache.getInstance().getPropsConfigs().get(band_card);
		PlayerProps pp = getplayerPropByPropid(player, band_card);
		if (pp == null) {
			return new ErrorCode(1,"没有"+ pc.getName()+"，不能使用。");
		}
		ErrorCode res = checkUseCard(player);
		if (res.getStatus() != ErrorCode.SUCC) {
			return res;
		}
		GameRoom room = GameRoomManager.getInstance().getRoomByPlayerId(player.getId());
		GameTable table = (GameTable) room.getObject(Constants.ROOM_TO_TABLE_KEY);
		GameTableSeat seats[] = table.getSeats();
		Gamer gamer = null;
		for (GameTableSeat seat : seats) {
			if (seat.getGamer() != null && seat.getGamer().getPlayerId() == player.getId()) {
				gamer = seat.getGamer();
				break;
			}
		}
		
		boolean canUse = releasePlayerProp(player, band_card);
//		PropsConfig pc = ResourceCache.getInstance().getPropsConfigs().get(band_card);
		if (!canUse) {
			return new ErrorCode(1,"使用"+ pc.getName() + "失败");
		}
		gamer.setUseBanCard(pc.getLeftTime());
		return new ErrorCode(ErrorCode.SUCC);
	}
	
	/**
	 * 检查是否可以使用各种卡
	 * @param player
	 * @return
	 */
	private static ErrorCode checkUseCard(Player player){
		GameRoom room = GameRoomManager.getInstance().getRoomByPlayerId(player.getId());
		if (room == null) {
			log.error("room|" + room);
			return new ErrorCode(1, "没有房间信息，不能使用换牌卡！");
		}
		GameTable table = (GameTable) room.getObject(Constants.ROOM_TO_TABLE_KEY);
		if (table == null) {
			return new ErrorCode(1, "没有房间信息，不能使用换牌卡！");
		}
		GameTableSeat seats[] = table.getSeats();
		Gamer gamer = null;
		for (GameTableSeat seat : seats) {
			if (seat.getGamer() != null && seat.getGamer().getPlayerId() == player.getId()) {
				gamer = seat.getGamer();
				break;
			}
		}
		if (gamer == null) {
			log.error("talbe|"+table+"|gamer|"+gamer);
			return new ErrorCode(1, "没有房间信息，不能使用换牌卡！");
		}
		LinkedBlockingQueue<Gamer> list = table.getGamingList();
		boolean gaming = false;
		for (Gamer g : list) {
			if (g.getPlayerId() == player.getId().intValue()) {
				gaming = true;
				break;
			}
		}
		if (!gaming) {
			return new ErrorCode(1, "不在游戏中，不能使用换牌卡！");
		}
		return new ErrorCode(ErrorCode.SUCC);
	}
}
