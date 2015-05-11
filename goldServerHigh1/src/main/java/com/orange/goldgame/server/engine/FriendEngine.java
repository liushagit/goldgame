package com.orange.goldgame.server.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.juice.orange.game.cached.MemcachedResource;
import com.juice.orange.game.container.GameSession;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerFriend;
import com.orange.goldgame.domain.PropsConfig;
import com.orange.goldgame.exception.GoldException;
import com.orange.goldgame.server.exception.GoldenNOTEnoughException;
import com.orange.goldgame.server.manager.PlayerInfoCenterManager;
import com.orange.goldgame.server.manager.SessionManger;
import com.orange.goldgame.server.service.FriendService;
import com.orange.goldgame.server.service.GiftService;
import com.orange.goldgame.server.service.GoldService;
import com.orange.goldgame.server.service.PlayerService;
import com.orange.goldgame.vo.FriendVo;

public class FriendEngine {
	
    /**
     * 获取好友列表
     * @param playerId
     * @param request
     * @return
     */
    public static List<FriendVo> getFriendList(Player player){
    	
    	Map<Integer,PlayerFriend> friendList = FriendService.getPlayerFriendByPlayer(player);
    	List<FriendVo> friendVoList = new ArrayList<FriendVo>();
    	
    	for(PlayerFriend pf :friendList.values()){
    		FriendVo fo = new FriendVo();
    		fo.setFriend(pf);
    		
    		if(pf.getFriendId() > 0 && SessionManger.getInstance().getSession(pf.getFriendId())==null){
    		    fo.setIsOnline((byte)0);
    		}else{
    			Object object = MemcachedResource.get(Constants.PLAYER_STATUS_KEY + pf.getFriendId());
    			if (object != null) {
					fo.setIsOnline(Byte.parseByte(object.toString()));
				}else {
					fo.setIsOnline((byte)0);
				}
    		}
    		
    		friendVoList.add(fo);
    	}
        return friendVoList;
    }

    /***
     * 添加好友
     * @param player
     * @param friend
     * @param giftType
     * @throws GoldException
     */
	public static void addFriend(Player player, Player friend) throws GoldException {
	    
        /**
         * 添加好友阶段
         */
	    if(friend==null){
	        throw new GoldException("该玩家不存在！");
	    }
	    
        PlayerFriend temp = FriendService.getPlayerFriendByPlayer(player).get(friend.getId());
        if(temp!=null)
            throw new GoldException("好友已存在！");
        int size = FriendService.getPlayerFriendByPlayer(player).size();
        int numberLimit = Integer.parseInt(BaseEngine.getInstace()
                .getAppConfigActionImpl().findAppConfigByKey(Constants.FRIEND_NUMBER).getAppValue());
        if(size >= numberLimit){
            throw new GoldException("好友个数达到了上限，不能在添加好友了！");
        }
        
        FriendService.addFriend(player, friend);
        //发送好友记录
        String msg1 = "您添加" + friend.getNickname() + "为好友。"; 
        PlayerInfoCenterManager.getInstance().addPlayerInfoCenter(player.getId(),true,Constants.DEFALUT_MESSAGE, "",msg1);
        String msg2 = "玩家" + player.getNickname() + "加你为好友。"; 
        PlayerInfoCenterManager.getInstance().addPlayerInfoCenter(friend.getId(), true, Constants.ADD_FRIEND, player.getId()+"",msg2);
        GameSession session = SessionManger.getInstance().getSession(friend.getId());
        if (session != null) {
        	PlayerInfoCenterManager.getInstance().noticePlayerInfoCenter(session);
		}
	}

	/**
	 * 删除好友
	 * @param playerId
	 * @param friendId
	 * @return
	 */
	public static Byte removeFriend(Player player, int friendId) throws GoldException{
		PlayerFriend pf = null;
		pf = FriendService.getPlayerFriendByPlayer(player).get(friendId);
		if(pf == null){
		    throw new GoldException("好友不存在，删除失败！");
		}
		FriendService.deleteFriend(player, friendId);
		Player friend = PlayerService.getSimplePlayerByPlayerId(friendId);
		PlayerInfoCenterManager.getInstance().addPlayerInfoCenter( player.getId(),true,Constants.DELETE_FRIEND,"","您删除了好友:"+friend.getNickname());
		
		return 1;
	}

	/**
	 * 获取礼物列表
	 * @return
	 */
	public static List<PropsConfig> getGiftList() {
		List<PropsConfig> list = new ArrayList<PropsConfig>();
		Map<Integer, PropsConfig> map = GiftService.getInstance().getAllGiftConfig();
		for(PropsConfig pc : map.values()){
		    list.add(pc);
		}
		return list;
	}
	
	/**
	 * 玩家送礼
	 * @param player
	 * @param friend
	 * @param giftType
	 */
	public static void giveGift(Player player,Player friend,int giftType) throws GoldException,GoldenNOTEnoughException{
	    
        int giftId = (int)giftType;
        
        PropsConfig pc = GiftService.getInstance().getAllGiftConfig().get(giftId);
        if(pc == null){
            throw new GoldException("礼物类型错误！");
        }
        int giftValue = pc.getCopper();
        
        /**购买礼物阶段*/
        GoldService.consumeCopperAndUpdateGamer(player, giftValue);
        
        /****** 送礼阶段 ********/
        GiftService.getInstance().addGift(friend, giftId);
        
        /** 记录送礼信息 **/
        PlayerInfoCenterManager.getInstance().addPlayerInfoCenter(player.getId(),true,Constants.SEND_GIFT,"","您将"+pc.getName()+"送给"+friend.getNickname()+"!");
        
	}

}
