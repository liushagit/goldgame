package com.orange.goldgame.server.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.orange.goldgame.action.PlayerAction;
import com.orange.goldgame.action.PlayerShareAction;
import com.orange.goldgame.core.ErrorCode;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerShare;
import com.orange.goldgame.domain.PlayerShareInfo;
import com.orange.goldgame.domain.ShareReward;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.vo.OutShareIndexPlayerListVo;
import com.orange.goldgame.vo.OutShareIndexRewardListVo;

/**
 * 分享功能
 * @author guojiang
 *
 */
public class ShareService {

	public static final int SHARE_COPPER = 5000;
	
	
	/**
	 * 邀请好友
	 * @param player
	 */
	public static void share(Player player){
		
	}
	
	/**
	 * 填写邀请人
	 * @param player 被邀请玩家
	 * @param other_player_id 邀请玩家id
	 * @return true 填写成功 false 填写失败
	 */
	public static ErrorCode registerShare(Player player,int other_player_id){
		if (isShare(player)) {
			return new ErrorCode(-1, "已经填写过了，不能再填写了");
		}
		if (player.getId().intValue() == other_player_id) {
			return new ErrorCode(-1, "不能填写自己");
		}
		Player otherPlayer = PlayerService.getSimplePlayerByPlayerId(other_player_id);
		if (otherPlayer == null) {
			return new ErrorCode(-1, "填写推荐人不存在");
		}
		PlayerShare playerShare = otherPlayer.getPlayerShare();
		
		if (playerShare == null) {
			playerShare = createPlayerShare(otherPlayer,player);
		}
		if (playerShare == null) {
			return new ErrorCode(-1, "填写推荐错误，请联系官方");
		}
		
		Map<Integer, Integer> map = playerShare.getPlayerIds();
		if (map == null) {
			map = new HashMap<Integer, Integer>();
			playerShare.setPlayerIds(map);
		}
		map.put(player.getId(), player.getId());
		Map<Integer,PlayerShareInfo> playerShareInfo = playerShare.getPlayerShareInfo();
		if (playerShareInfo == null) {
			playerShareInfo = new HashMap<Integer, PlayerShareInfo>();
			playerShare.setPlayerShareInfo(playerShareInfo);
		}
		
		PlayerShareInfo psi = createPlayerShareInfo(player, otherPlayer);
		player.setShareTimes(player.getShareTimes() + 1);
		updatePlayerShare(otherPlayer);
		GoldService.addCopper(player, SHARE_COPPER);
		PlayerShareAction action = BaseEngine.getInstace().getPlayerShareAction();
		action.update(playerShare, psi);
		FriendService.addFriend(player, otherPlayer);
		return new ErrorCode(ErrorCode.SUCC,playerShare);
	}
	
	private static PlayerShareInfo createPlayerShareInfo(Player player ,Player self){
		PlayerShareInfo psi = new PlayerShareInfo();
		psi.setHeadImg(player.getHeadImage());
		psi.setOtherId(player.getId());
		psi.setOtherName(player.getNickname());
		psi.setPlayerId(self.getId());
		return psi;
	}
	
	public static boolean isShare(Player player){
		return player.getShareTimes() > 0;
	}
	private static void updatePlayerShare(Player player){
		PlayerShare playerShare = player.getPlayerShare();
		if (playerShare != null) {
			Map<Integer, Integer> map = playerShare.getPlayerIds();
			if (map != null) {
				StringBuffer sb = new StringBuffer();
				for (int id : map.keySet()) {
					sb.append(id).append("|");
				}
				playerShare.setSharePlayerIds(sb.toString());
			}
			map = playerShare.getShareRewards();
			if (map != null) {
				StringBuffer sb = new StringBuffer();
				for (int id : map.keySet()) {
					sb.append(id).append("|");
				}
				playerShare.setShareRewardIds(sb.toString());
			}
			
		}
	}
	
	private static PlayerShare createPlayerShare(Player player,Player self){
		PlayerShare playerShare = new PlayerShare();
		playerShare.setPlayerId(player.getId());
		playerShare.setSharePlayerIds("");
		playerShare.setShareRewardIds("");
		PlayerShareAction action = BaseEngine.getInstace().getPlayerShareAction();
		PlayerShareInfo psi = new PlayerShareInfo();
		psi.setHeadImg(self.getHeadImage());
		psi.setOtherId(self.getId());
		psi.setOtherName(self.getNickname());
		psi.setPlayerId(player.getId());
		
		int share_id = action.insert(playerShare,psi);
		
		if (share_id > 0) {
			playerShare.setId(share_id);
			player.setPlayerShare(playerShare);
			return playerShare;
		}
		return null;
	}
	
	/**
	 * 查看自己成功分享数量
	 * @param player
	 */
	public static int getSelfShare(Player player){
		PlayerShare playerShare = player.getPlayerShare();
		if (playerShare == null) {
			return 0;
		}
		return playerShare.getPlayerIds().size();
	}
	
	public static ErrorCode getShareReward(Player player,int index){
		ShareReward sr = ResourceCache.getInstance().getShareReMap().get(index);
		if (sr == null) {
			return new ErrorCode(-1, "领取错误，请与官府昂联系");
		}
		PlayerShare playerShare = player.getPlayerShare();
		if (playerShare == null) {
			return new ErrorCode(-1, "没有成功推荐不能领取奖励");
		}
		Map<Integer, Integer> shareRewards = playerShare.getShareRewards();
		if (shareRewards != null && shareRewards.containsKey(index)) {
			return new ErrorCode(-1, "已经领取过了，不能再领取了");
		}
		if (shareRewards == null) {
			shareRewards = new HashMap<Integer, Integer>();
			playerShare.setShareRewards(shareRewards);
		}
		shareRewards.put(index, index);
		updatePlayerShare(player);
		PlayerShareAction action = BaseEngine.getInstace().getPlayerShareAction();
		action.update(playerShare, null);
		GoldService.addCopper(player, sr.getCoppers());
		return new ErrorCode(ErrorCode.SUCC);
	}
	
	public static List<OutShareIndexRewardListVo> getIndexRewardListVos(Player player){
		List<OutShareIndexRewardListVo> list = new ArrayList<OutShareIndexRewardListVo>();
		Map<Integer, ShareReward> shareReMap = ResourceCache.getInstance().getShareReMap();
		OutShareIndexRewardListVo vo = null;
		for (ShareReward sr : shareReMap.values()) {
			vo = new OutShareIndexRewardListVo();
			vo.setaGoldId(sr.getId());
			vo.setbCopper(sr.getCoppers());
			vo.setcStatus((byte)getShareStatus(player, sr));
			vo.setdNum(sr.getShareNumber());
			list.add(vo);
		}
		return list;
	}
	
	private static int getShareStatus(Player player,ShareReward sr){
		PlayerShare playerShare = player.getPlayerShare();
		if (playerShare != null) {
			Map<Integer, Integer> shareRewards = playerShare.getShareRewards();
			if (shareRewards != null && shareRewards.containsKey(sr.getId())) {
				return 2;
			}
			Map<Integer, Integer> playerIds = playerShare.getPlayerIds();
			if (playerIds != null && playerIds.size() >= sr.getShareNumber()) {
				return 1;
			}
		}
		return 0;
	}
	private static final long updateTime=45*1000;
	private static long nowTime=0;
	public static List<OutShareIndexPlayerListVo> getSharePlayerList(Player player){
		PlayerShare playerShare = player.getPlayerShare();
		if(System.currentTimeMillis()-nowTime>updateTime){
			nowTime=System.currentTimeMillis();
			getSharePlayerList(PlayerService.quaryPlayerByid(player.getId()));
		}
		List<OutShareIndexPlayerListVo> getSharePlayerList = new ArrayList<OutShareIndexPlayerListVo>();
		if (playerShare != null) {
			Map<Integer,PlayerShareInfo> playerShareInfo = playerShare.getPlayerShareInfo();
			OutShareIndexPlayerListVo vo = null;
			for (PlayerShareInfo psi : playerShareInfo.values()) {
				vo = new OutShareIndexPlayerListVo();
				vo.setaPlayerId(psi.getOtherId());
				vo.setbHeadImgId(psi.getHeadImg());
				vo.setcPlayerName(psi.getOtherName());
				getSharePlayerList.add(vo);
			}
		}
		return getSharePlayerList;
	}
	
}
