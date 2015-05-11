package com.orange.goldgame.server.engine;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.orange.goldgame.core.GiftVo;
import com.orange.goldgame.core.PropertyVo;
import com.orange.goldgame.domain.ExpectConsumeLog;
import com.orange.goldgame.domain.GoodsConfig;
import com.orange.goldgame.domain.PackageProps;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PropsConfig;
import com.orange.goldgame.server.manager.TaskManager;
import com.orange.goldgame.vo.ExchangeRecordVo;
import com.orange.goldgame.vo.MessageVo;
import com.orange.goldgame.vo.OrderGemstoneVo;
import com.orange.goldgame.vo.OrderVo;
import com.orange.goldgame.vo.OtherPlayerVo;
import com.orange.goldgame.vo.PlayerSelfVo;

/**
 * @author wuruihuang 2013.6.17
 * 此类是服务器总的处理引擎，可以调用其它的处理引擎：如游戏引擎GameEngine,ResponseEngine...
 */
public class HandlerEngine {

	/**
	 * 获取道具列表 即是用户进入商城后看到的商品列表 
	 * @return
	 */
	public static List<PackageProps> handlerPropList() {
		return BaseEngine.getInstace().getPropActionIpml().queryAllPackageProp();
	}
	
    
    //获取他人信息
    public static OtherPlayerVo getOtherPlayeVo(int userId1,int userId2){
        OtherPlayerVo playerInfo = new OtherPlayerVo();
        Player player = BaseEngine.getInstace().getPlayerActionIpml().getPlayerMsg(userId1).get(0);
        playerInfo.setPlayer(player);
        
//        Estate estate = estateActionIpml.getEstate(userId2);
//        playerInfo.setGemstone(estate.getGemstone());
        return playerInfo;
    }
    
    //获取礼物信息
    public static GiftVo getGiftVo(Player player){
        GiftVo giftInfo = new GiftVo();
        //giftInfo.setGift(GiftService.getInstance().getGiftMap(player));
       /* Gift gift = BaseEngine.getInstace().getGiftActionIpml(playerId).findGiftByPlayerId(playerId);
        giftInfo.setGift(gift);*/
        return giftInfo;
    }


    public static PlayerSelfVo getPlayerInfo(Player player) {
      //个人基本信息
        PlayerSelfVo playerInfo = new PlayerSelfVo();
        playerInfo.setPlayer(player);
        
        int sex = player.getSex();
        playerInfo.setSex((byte)sex);
        playerInfo.setGold(player.getCopper());
        return playerInfo;
    }

    public static PropertyVo getWealthInfo(Player player) {
      //个人财富信息
        PropertyVo  wealthInfo = new PropertyVo();
        wealthInfo.setPlayer(player);
        return wealthInfo;
    }

    public static short handlerUpdatePlayerInfo(Player player, int sex,
            String nickname, String tag, String head) {
        short flag = 0;
        try{
            player.setSex(sex);
            player.setNickname(nickname);
            player.setTag(tag);
            player.setHeadImage(head+"");
        	BaseEngine.getInstace().getPlayerActionIpml().modifyPlayer(player);
            flag = 1;
            //任务管理
            TaskManager.getInstance().drivePlayerInfoRate(player, 1);
        } catch(Exception e){
            e.printStackTrace();
            flag = -1;
        }
        return flag;
    }
 
	public static List<GoodsConfig> handlerGoodsList() {
//		List<Object> goodsList = new ArrayList<Object>();
		List<GoodsConfig> goodsConfigs = BaseEngine.getInstace().getGoodsActionIpml().findAllGoodsConfig();
//		for(int i=0;i<goodsConfigs.size();i++){
//			GoodsVo goodsVo = new GoodsVo();
//			GoodsConfig goodsConfig = goodsConfigs.get(i);
//			goodsVo.setGoodsId(goodsConfig.getId());
//			goodsVo.setGoodsName(goodsConfig.getGoodsname());
//			goodsVo.setExchangeVoucher(goodsConfig.getExchangvoucher());
//			goodsList.add(goodsVo);
//		}
		return goodsConfigs;
	}

	//处理玩家购买宝石的请求
	public static OrderGemstoneVo handlerBuyGemstone(int playerId, int num) {
		//表单号=系统时间+玩家Id
		OrderGemstoneVo order = new OrderGemstoneVo();
		//生成表单号
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR); 
		int month = c.get(Calendar.MONTH); 
		int date = c.get(Calendar.DATE); 
		StringBuilder strbuilder = new StringBuilder();
		String stryear = String.valueOf(year);
		String strmonth = String.valueOf(month);
		String strdate = String.valueOf(date);
		
		strbuilder.append(stryear);
		strbuilder.append(strmonth);
		strbuilder.append(strdate);
		strbuilder.append(String.valueOf(playerId));
		String orderId = strbuilder.toString();
		order.setOrderId(orderId);
		order.setPlayerId(playerId);
		order.setGemstone(num);
		return order;
	}

	public static int handlerComfirmBuyGemstone(int playerId, int num) {
//		return estateActionIpml.getGemstone(playerId, num);
		return 0;
	}

	public static OrderVo handlerBuyProp(int playerId, int propsConfigId) {
		//表单号=系统时间+玩家Id+propId
		OrderVo orderInfo = new OrderVo();
		PropsConfig propsConfig = BaseEngine.getInstace().getPropActionIpml().queryPropsById(propsConfigId);
		PackageProps packageProps = BaseEngine.getInstace().getPropActionIpml().queryPackagePropsById(propsConfigId);
		//生成表单号
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR); 
		int month = c.get(Calendar.MONTH); 
		int date = c.get(Calendar.DATE); 
		StringBuilder strbuilder = new StringBuilder();
		String stryear = String.valueOf(year);
		String strmonth = String.valueOf(month);
		String strdate = String.valueOf(date);
		
		strbuilder.append(stryear);
		strbuilder.append(strmonth);
		strbuilder.append(strdate);
		strbuilder.append(String.valueOf(playerId));
		strbuilder.append(String.valueOf(propsConfigId));
		String orderId = strbuilder.toString();
		orderInfo.setOrderId(orderId);
		orderInfo.setPlayerId(playerId);
		orderInfo.setPropName(String.valueOf(propsConfig.getName()));
		orderInfo.setGemstone(packageProps.getPropsMoney());
		return orderInfo;
	}

//	public static Estate handlerComfirmBuyProp(SocketRequest request, int playerId, int propsId,int packagePropsId) {
//		//扣除玩家宝石数量
//		Player player = PlayerService.getPlayer(playerId, request.getSession());
//		Estate estateBuyBefore = player.getPlayerEstate();
//		
//		PackageProps packageProp = BaseEngine.getInstace().getPropActionIpml().queryPackagePropsById(packagePropsId);
//		int gamestone = estateBuyBefore.getGemstone()-packageProp.getPropsMoney();
//		estateBuyBefore.setGemstone(gamestone);
//		player.setPlayerEstate(estateBuyBefore);
//		//增加玩家的道具数量
//		BaseEngine.getInstace().getPropActionIpml().updatePropsNumberByTowId(playerId,propsId,packageProp.getPropsNumber());
//		//增加玩家的金币数量
//		estateBuyBefore.addGolds(packageProp.getPropsNumber());
//		BaseEngine.getInstace().getEstateActionIpml().updateEstate(estateBuyBefore);
//		//增加玩家的兑换券数量
////		getEstateActionIpml().modifyVourch(playerId, packageProp.get)
////		return estateActionIpml.getEstate(playerId);
//		return estateBuyBefore;
//	}

	public static List<Object> handlerRequestMessageInfo(int playerId,
			byte messageType) {
		List<Object> messageList = new ArrayList<Object>();
		if(messageType==1){
			//系统信息
			List<String> systemMessages = BaseEngine.getInstace().getMessageActionIpml().loadSystemMessage();
			for(String str:systemMessages){
				messageList.add(str);
			}
		}else if(messageType==2){
			//好友消息
			List<MessageVo> friendsMessages = new ArrayList<MessageVo>();
			friendsMessages = BaseEngine.getInstace().getMessageActionIpml().loadFriendsMessage(playerId);
			for(MessageVo msg:friendsMessages){
				messageList.add(msg);
			}
		}else if(messageType==3){
			List<ExchangeRecordVo> exchangeMessages = new ArrayList<ExchangeRecordVo>();
			exchangeMessages = BaseEngine.getInstace().getMessageActionIpml().loadGoodsExchangeMessage(playerId);
			for(ExchangeRecordVo msg:exchangeMessages){
				messageList.add(msg);
			}
		}
		return messageList;
	}

	public static byte handlerGoodsExchange(int playerId, int goodsId) {
		byte isExchangeable = 0;
//		int exchangeVour = estateActionIpml.getEstate(playerId).getExchangevoucher();
//		int goodsExchangeVour = goodsActionIpml.getExchangeVoucherById(goodsId);
//		if(exchangeVour>goodsExchangeVour){
//			isExchangeable = 1;
//		}
		return isExchangeable;
	}


	/**
	 * 处理用户确认兑换商品实物的请求
	 * @param goodsId
	 * @param goodsId 
	 * @param cellphone
	 * @return
	 */
	public static int handlerComfirmExchangeGoods(int playerId, int goodsId, String cellphone) {
//		int isOk = estateActionIpml.getGoods(playerId, goodsId);
//		if(isOk!=-1){
//			logActionIpml.addLog(playerId, goodsId, cellphone);
//		}
//		return estateActionIpml.getEstate(playerId).getExchangevoucher();
		return 0;
	}

    public static int addExpectConsumeLog(int playerId, int money, Date date) {
        ExpectConsumeLog log = new ExpectConsumeLog();
        log.setDate(date);
        log.setMoney(money);
        log.setPlayerId(playerId);
        
        BaseEngine.getInstace().getExpectConsumeLogAction().insertLog(log);
        return 1;
    }
}
