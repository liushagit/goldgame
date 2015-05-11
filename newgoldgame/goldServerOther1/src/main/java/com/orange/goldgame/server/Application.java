/**
 * SuperStarLogin
 * com.orange.superstar.login
 * Application.java
 */
package com.orange.goldgame.server;


import org.apache.log4j.Logger;

import com.juice.orange.game.container.Container;
import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.server.help.HelpServer;
import com.orange.goldgame.server.lover.LoverServer;
import com.orange.goldgame.server.pay.PayNewServer;
import com.orange.goldgame.server.service.AdminServer;
import com.orange.goldgame.server.service.DailyAwardServer;
import com.orange.goldgame.server.service.FriendServer;
import com.orange.goldgame.server.service.GameServer;
import com.orange.goldgame.server.service.OnlineRewardService;
import com.orange.goldgame.server.service.PayService;
import com.orange.goldgame.server.service.PlayerPropsService;
import com.orange.goldgame.server.service.RankServer;
import com.orange.goldgame.server.service.ShareServer;
import com.orange.goldgame.server.service.ShopServer;
import com.orange.goldgame.server.service.SystemServer;
import com.orange.goldgame.server.service.TaskServer;
import com.orange.goldgame.server.service.TestServer;
import com.orange.goldgame.server.service.UserServer;
import com.orange.goldgame.server.wheel.WheelFortuneServer;

/**
 * @author wuruihuang 2013.5.20
 * 2013-5-3
 */
public class Application {
	private static Logger logger = LoggerFactory.getLogger(Application.class);
	//
	public void init() {
		initServer();
		initTimerServer();
		//
	}
	
	public void initServer() {
		logger.info("Init servers......");
		
		//用户服务
		Container.registerServer("userserver", new UserServer());
		Container.registerServerPath(""+Protocol.REQUEST_LOGIN_SERVER,"userserver/userLogin");
		Container.registerServerPath(""+Protocol.REQUEST_UPDATE_INFO,"userserver/requestUpdateInfo");
		Container.registerServerPath(""+Protocol.REQUST_GET_ACTIVITY_LIST,"userserver/requestActivities");
		Container.registerServerPath(""+Protocol.REQUST_PLAYER_MSG,"userserver/requestInfo");
		Container.registerServerPath(""+Protocol.REQUST_FRIEND_MSG,"userserver/requestOtherInfo");
		Container.registerServerPath(""+Protocol.REQUEST_PERSONAL_PROP_LIST,"userserver/requestPersonalItems");
		Container.registerServerPath(""+Protocol.REQUST_BROADCAST,"userserver/requestBroadcast");
		Container.registerServerPath(""+Protocol.REQUEST_PLAYER_HART,"userserver/requestHart");
		Container.registerServerPath(""+Protocol.REQUEST_CUSTOMER,"userserver/requestCustomer");
		Container.registerServerPath(""+Protocol.REQUEST_NEW_END,"userserver/requestNewPlayerEnd");
		
		
		
		//游戏服务
		Container.registerServer("gameServer", new GameServer());
		/*********************************** 初级场 *************************************************/
		Container.registerServerPath(""+Protocol.REQUEST_INTO_PLAYGROUND,"gameServer/enterArea");
		Container.registerServerPath(""+Protocol.REQUEST_INTO_GAME,"gameServer/enterRoom");
		Container.registerServerPath(""+Protocol.REQUEST_READY_GAME_STATE,"gameServer/requestReady");
		Container.registerServerPath(""+Protocol.REQUEST_BEGIN_GAME,"gameServer/requestStartGame");
		Container.registerServerPath(""+Protocol.REQUEST_FIGHT_CART,"gameServer/requestFightGamerList");
		Container.registerServerPath(""+Protocol.REQUST_LOOK_POKER,"gameServer/lookCard");
		Container.registerServerPath(""+Protocol.REQUST_FOLLOW_POKER,"gameServer/followCard");
		Container.registerServerPath(""+Protocol.REQUST_FILLING_POKER,"gameServer/addGolds");
		Container.registerServerPath(""+Protocol.REQUST_COMPARE_POKER,"gameServer/fightCard");
		Container.registerServerPath(""+Protocol.REQUEST_COMFIRM_FIRECARD,"gameServer/requestComfirmFireCart");
		Container.registerServerPath(""+Protocol.REQUST_GIVE_UP_POKER,"gameServer/givenUpCard");
		Container.registerServerPath(""+Protocol.REQUST_EXCHANGE_POKER,"gameServer/exchaneCard");
		Container.registerServerPath(""+Protocol.REQUST_DOUBLE_POKER,"gameServer/doubleGolds");
		Container.registerServerPath(""+Protocol.REQUST_BAN_POKER,"gameServer/forbidenCardFight");
		Container.registerServerPath(""+Protocol.REQUST_CHAT,"gameServer/chatWithEveryone");
		Container.registerServerPath(""+Protocol.REQUST_GET_GOLDS,"gameServer/recevieAwordInGame");
		Container.registerServerPath(""+Protocol.REQUST_CHANGE_TABLE,"gameServer/changeGameTable");
		Container.registerServerPath(""+Protocol.REQUST_LEAVE_ROOM,"gameServer/exitRoom");
		Container.registerServerPath(""+Protocol.REQUST_LEAVE_GAME,"gameServer/exitGame");
		Container.registerServerPath(""+Protocol.REQUEST_START_MATCH,"gameServer/requestMatchStart");
		Container.registerServerPath(""+Protocol.REQUEST_ENTERAREA,"gameServer/checkEnterArea");
		//道具使用
        Container.registerServer("playerPropsService", new PlayerPropsService());
        Container.registerServerPath(""+Protocol.REQUEST_PLAYER_PROP_INFO, "playerPropsService/getPlayerPropInfo");
        Container.registerServerPath(""+Protocol.REQUEST_PLAYER_PROP_USE, "playerPropsService/getPlayerPropUse");
        
		/*********************************** 中级场 *************************************************/
		
		Container.registerServerPath(""+Protocol.REQUEST_MIDDLE_INTO_GAME,"gameServer/enterRoom");
		Container.registerServerPath(""+Protocol.REQUEST_MIDDLE_READY_GAME_STATE,"gameServer/requestReady");
		Container.registerServerPath(""+Protocol.REQUEST_MIDDLE_BEGIN_GAME,"gameServer/requestStartGame");
		Container.registerServerPath(""+Protocol.REQUEST_MIDDLE_LOOK_POKER,"gameServer/lookCard");
		Container.registerServerPath(""+Protocol.REQUEST_MIDDLE_FOLLOW_POKER,"gameServer/followCard");
		Container.registerServerPath(""+Protocol.REQUEST_MIDDLE_FILLING_POKER,"gameServer/addGolds");
		Container.registerServerPath(""+Protocol.REQUEST_MIDDLE_COMPARE_POKER,"gameServer/fightCard");
		Container.registerServerPath(""+Protocol.REQUEST_MIDDLE_GIVE_UP_POKER,"gameServer/givenUpCard");
		Container.registerServerPath(""+Protocol.REQUEST_MIDDLE_EXCHANGE_POKER,"gameServer/exchaneCard");
		Container.registerServerPath(""+Protocol.REQUEST_MIDDLE_DOUBLE_POKER,"gameServer/doubleGolds");
		Container.registerServerPath(""+Protocol.REQUEST_MIDDLE_BAN_POKER,"gameServer/forbidenCardFight");
		Container.registerServerPath(""+Protocol.REQUEST_MIDDLE_PLAYER_PROP_INFO, "playerPropsService/getPlayerPropInfo");
		Container.registerServerPath(""+Protocol.REQUEST_MIDDLE_PLAYER_PROP_USE, "playerPropsService/getPlayerPropUse");
		Container.registerServerPath(""+Protocol.REQUEST_MIDDLE_GET_GOLDS,"gameServer/recevieAwordInGame");
		Container.registerServerPath(""+Protocol.REQUEST_MIDDLE_LEAVE_ROOM,"gameServer/exitRoom");
		
		
		
		Container.registerServerPath(""+Protocol.REQUEST_MIDDLE_CHAT,"gameServer/chatWithEveryone");
		Container.registerServerPath(""+Protocol.REQUEST_MIDDLE_INTO_PLAYGROUND,"gameServer/enterArea");
		Container.registerServerPath(""+Protocol.REQUEST_MIDDLE_ONLINE_PRIZE, "onlineRewardService/requestReward");
		Container.registerServerPath(""+Protocol.REQUEST_MIDDLE_CHANGE_TABLE,"gameServer/changeGameTable");
		Container.registerServerPath(""+Protocol.REQUEST_MIDDLE_LEAVE_GAME,"gameServer/exitGame");
		Container.registerServerPath(""+Protocol.REQUEST_MIDDLE_FIGHT_CART,"gameServer/requestFightGamerList");
		Container.registerServerPath(""+Protocol.REQUEST_MIDDLE_COMFIRM_FIRECARD,"gameServer/requestComfirmFireCart");
		Container.registerServerPath(""+Protocol.REQUEST_MIDDLE_ENTERAREA,"gameServer/checkEnterArea");
		Container.registerServerPath(""+Protocol.REQUEST_MIDDLE_GET_FRIEND_LIST,"friendserver/getFriends");
		Container.registerServerPath(""+Protocol.REQUEST_MIDDLE_REMOVE_FINEND,"friendserver/removeFriend");
		Container.registerServerPath(""+Protocol.REQUEST_MIDDLE_GIFT,"friendserver/requestSendGift");
		Container.registerServerPath(""+Protocol.REQUEST_MIDDLE_ADD_FROENDS,"friendserver/addFriend");
		Container.registerServerPath(""+Protocol.REQUEST_MIDDLE_FRIEND_MSG,"userserver/requestOtherInfo");
		Container.registerServerPath(""+Protocol.REQUEST_MIDDLE_SEND_GOLD,"friendserver/requestSendGold");
		Container.registerServerPath(""+Protocol.REQUEST_MIDDLE_FOLLWO_FRIEND,"friendserver/followFriend");
		
		
		/*********************************** 高级场 *************************************************/

		Container.registerServerPath(""+Protocol.REQUEST_HIGHT_INTO_GAME,"gameServer/enterRoom");
		Container.registerServerPath(""+Protocol.REQUEST_HIGHT_READY_GAME_STATE,"gameServer/requestReady");
		Container.registerServerPath(""+Protocol.REQUEST_HIGHT_BEGIN_GAME,"gameServer/requestStartGame");
		Container.registerServerPath(""+Protocol.REQUEST_HIGHT_LOOK_POKER,"gameServer/lookCard");
		Container.registerServerPath(""+Protocol.REQUEST_HIGHT_FOLLOW_POKER,"gameServer/followCard");
		Container.registerServerPath(""+Protocol.REQUEST_HIGHT_FILLING_POKER,"gameServer/addGolds");
		Container.registerServerPath(""+Protocol.REQUEST_HIGHT_COMPARE_POKER,"gameServer/fightCard");
		Container.registerServerPath(""+Protocol.REQUEST_HIGHT_GIVE_UP_POKER,"gameServer/givenUpCard");
		Container.registerServerPath(""+Protocol.REQUEST_HIGHT_EXCHANGE_POKER,"gameServer/exchaneCard");
		Container.registerServerPath(""+Protocol.REQUEST_HIGHT_DOUBLE_POKER,"gameServer/doubleGolds");
		Container.registerServerPath(""+Protocol.REQUEST_HIGHT_BAN_POKER,"gameServer/forbidenCardFight");
		Container.registerServerPath(""+Protocol.REQUEST_HIGHT_PLAYER_PROP_INFO, "playerPropsService/getPlayerPropInfo");
		Container.registerServerPath(""+Protocol.REQUEST_HIGHT_PLAYER_PROP_USE, "playerPropsService/getPlayerPropUse");
		Container.registerServerPath(""+Protocol.REQUEST_HIGHT_GET_GOLDS,"gameServer/recevieAwordInGame");
		Container.registerServerPath(""+Protocol.REQUEST_HIGHT_LEAVE_ROOM,"gameServer/exitRoom");
		
		Container.registerServerPath(""+Protocol.REQUEST_HIGHT_CHAT,"gameServer/chatWithEveryone");
		Container.registerServerPath(""+Protocol.REQUEST_HIGHT_INTO_PLAYGROUND,"gameServer/enterArea");
		Container.registerServerPath(""+Protocol.REQUEST_HIGHT_ONLINE_PRIZE, "onlineRewardService/requestReward");
		Container.registerServerPath(""+Protocol.REQUEST_HIGHT_CHANGE_TABLE,"gameServer/changeGameTable");
		Container.registerServerPath(""+Protocol.REQUEST_HIGHT_LEAVE_GAME,"gameServer/exitGame");
		Container.registerServerPath(""+Protocol.REQUEST_HIGHT_FIGHT_CART,"gameServer/requestFightGamerList");
		Container.registerServerPath(""+Protocol.REQUEST_HIGHT_COMFIRM_FIRECARD,"gameServer/requestComfirmFireCart");
		Container.registerServerPath(""+Protocol.REQUEST_HIGHT_ENTERAREA,"gameServer/checkEnterArea");
		Container.registerServerPath(""+Protocol.REQUEST_HIGHT_GET_FRIEND_LIST,"friendserver/getFriends");
		Container.registerServerPath(""+Protocol.REQUEST_HIGHT_REMOVE_FINEND,"friendserver/removeFriend");
		Container.registerServerPath(""+Protocol.REQUEST_HIGHT_GIFT,"friendserver/requestSendGift");
		Container.registerServerPath(""+Protocol.REQUEST_HIGHT_ADD_FROENDS,"friendserver/addFriend");
		Container.registerServerPath(""+Protocol.REQUEST_HIGHT_FRIEND_MSG,"userserver/requestOtherInfo");
		Container.registerServerPath(""+Protocol.REQUEST_HIGHT_SEND_GOLD,"friendserver/requestSendGold");
		Container.registerServerPath(""+Protocol.REQUEST_HIGHT_FOLLWO_FRIEND,"friendserver/followFriend");
		
		
		/*********************************** 比赛级场 *************************************************/

		Container.registerServerPath(""+Protocol.REQUEST_MATCH_INTO_GAME,"gameServer/enterRoom");
		Container.registerServerPath(""+Protocol.REQUEST_MATCH_READY_GAME_STATE,"gameServer/requestReady");
		Container.registerServerPath(""+Protocol.REQUEST_MATCH_BEGIN_GAME,"gameServer/requestStartGame");
		Container.registerServerPath(""+Protocol.REQUEST_MATCH_LOOK_POKER,"gameServer/lookCard");
		Container.registerServerPath(""+Protocol.REQUEST_MATCH_FOLLOW_POKER,"gameServer/followCard");
		Container.registerServerPath(""+Protocol.REQUEST_MATCH_FILLING_POKER,"gameServer/addGolds");
		Container.registerServerPath(""+Protocol.REQUEST_MATCH_COMPARE_POKER,"gameServer/fightCard");
		Container.registerServerPath(""+Protocol.REQUEST_MATCH_GIVE_UP_POKER,"gameServer/givenUpCard");
		Container.registerServerPath(""+Protocol.REQUEST_MATCH_EXCHANGE_POKER,"gameServer/exchaneCard");
		Container.registerServerPath(""+Protocol.REQUEST_MATCH_DOUBLE_POKER,"gameServer/doubleGolds");
		Container.registerServerPath(""+Protocol.REQUEST_MATCH_BAN_POKER,"gameServer/forbidenCardFight");
		Container.registerServerPath(""+Protocol.REQUEST_MATCH_PLAYER_PROP_INFO, "playerPropsService/getPlayerPropInfo");
		Container.registerServerPath(""+Protocol.REQUEST_MATCH_PLAYER_PROP_USE, "playerPropsService/getPlayerPropUse");
		Container.registerServerPath(""+Protocol.REQUEST_MATCH_GET_GOLDS,"gameServer/recevieAwordInGame");
		Container.registerServerPath(""+Protocol.REQUEST_MATCH_LEAVE_ROOM,"gameServer/exitRoom");
		
		
		Container.registerServerPath(""+Protocol.REQUEST_MATCH_CHAT,"gameServer/chatWithEveryone");
		Container.registerServerPath(""+Protocol.REQUEST_MATCH_INTO_PLAYGROUND,"gameServer/enterArea");
		Container.registerServerPath(""+Protocol.REQUEST_MATCH_ONLINE_PRIZE, "onlineRewardService/requestReward");
		Container.registerServerPath(""+Protocol.REQUEST_MATCH_CHANGE_TABLE,"gameServer/changeGameTable");
		Container.registerServerPath(""+Protocol.REQUEST_MATCH_LEAVE_GAME,"gameServer/exitGame");
		Container.registerServerPath(""+Protocol.REQUEST_MATCH_FIGHT_CART,"gameServer/requestFightGamerList");
		Container.registerServerPath(""+Protocol.REQUEST_MATCH_COMFIRM_FIRECARD,"gameServer/requestComfirmFireCart");
		Container.registerServerPath(""+Protocol.REQUEST_MATCH_ENTERAREA,"gameServer/checkEnterArea");
		Container.registerServerPath(""+Protocol.REQUEST_MATCH_GET_FRIEND_LIST,"friendserver/getFriends");
		Container.registerServerPath(""+Protocol.REQUEST_MATCH_REMOVE_FINEND,"friendserver/removeFriend");
		Container.registerServerPath(""+Protocol.REQUEST_MATCH_GIFT,"friendserver/requestSendGift");
		Container.registerServerPath(""+Protocol.REQUEST_MATCH_ADD_FROENDS,"friendserver/addFriend");
		Container.registerServerPath(""+Protocol.REQUEST_MATCH_FRIEND_MSG,"userserver/requestOtherInfo");
		Container.registerServerPath(""+Protocol.REQUEST_MATCH_SEND_GOLD,"friendserver/requestSendGold");
		Container.registerServerPath(""+Protocol.REQUEST_MATCH_FOLLWO_FRIEND,"friendserver/followFriend");
		
		
		
		//任务服务
		Container.registerServer("taskServer", new TaskServer());
		Container.registerServerPath(""+Protocol.REQUEST_TASK_LIST,"taskServer/requestTasks");
		Container.registerServerPath(""+Protocol.REQUEST_GET_TASK_WARD,"taskServer/requestReceiveTaskAword");
		
		// 商场服务
		Container.registerServer("shopserver", new ShopServer());
		Container.registerServerPath(""+Protocol.REQUST_BUY_GEMSTONE,"shopserver/requestBuyGemstone");
		Container.registerServerPath(""+Protocol.REQUST_BUY_PROP,"shopserver/requestBuyProp");
		Container.registerServerPath(""+Protocol.REQUST_BUY_PROP_ENSURE,"shopserver/requestComfirmProp");
		Container.registerServerPath(""+Protocol.REQUEST_GOODS_LIST,"shopserver/requestExcchangeGoodsList");
		Container.registerServerPath(""+Protocol.REQUST_BUY_GEMSTONE_ENSURE,"shopserver/requestComfirmGemstone");
		Container.registerServerPath(""+Protocol.REQUEST_PROP_LIST,"shopserver/requestPropList");
		Container.registerServerPath(""+Protocol.REQUST_EXCHANGE_GOODS,"shopserver/requestExcchangeGoods");
		Container.registerServerPath(""+Protocol.REQUEST_SENCOND_CHANGNE,"shopserver/comfirmExcchangeGoods");
//		
		// 好友服务
		Container.registerServer("friendserver", new FriendServer());
		Container.registerServerPath(""+Protocol.REQUST_GET_FRIEND_LIST,"friendserver/getFriends");
		Container.registerServerPath(""+Protocol.REQUST_ADD_FROENDS,"friendserver/addFriend");
		Container.registerServerPath(""+Protocol.REQUEST_GIFT_LIST,"friendserver/requestGiftList");
		Container.registerServerPath(""+Protocol.REQUST_GIFT,"friendserver/requestSendGift");
		Container.registerServerPath(""+Protocol.REQUEST_REMOVE_FINEND,"friendserver/removeFriend");
		Container.registerServerPath(""+Protocol.REQUEST_SEND_GOLD,"friendserver/requestSendGold");
		Container.registerServerPath(""+Protocol.REQUEST_FOLLWO_FRIEND,"friendserver/followFriend");
		
		//系统服务
		Container.registerServer("systemserver", new SystemServer());
		Container.registerServerPath(""+Protocol.REQUEST_MESSAGE_LIST,"systemserver/requestMessageList");
		
		//奖励服务
		Container.registerServer("dailyawardserver", new DailyAwardServer());
        Container.registerServerPath(""+Protocol.REQUEST_DALIY_LIST,"dailyawardserver/responsePrizeList");
        Container.registerServerPath(""+Protocol.REQUEST_GIVE_AWARD,"dailyawardserver/receivePrize");
        
        //排行服务
        Container.registerServer("rankServer",new RankServer());
        Container.registerServerPath(""+Protocol.REQUST_GET_EANKING_LIST,"rankServer/requestRanking");
		
		//测试服务
		Container.registerServer("testserver", new TestServer());
        Container.registerServerPath(""+Protocol.TEST_DAILYPRIZE,"testserver/testDailyPrizeManager");
        
        //在线领取
        Container.registerServer("onlineRewardService", new OnlineRewardService());
        Container.registerServerPath(""+Protocol.REQUEST_ONLINE_PRIZE, "onlineRewardService/requestReward");
        
        
        
        //充值
        Container.registerServer("payService", new PayService());
        Container.registerServerPath(""+Protocol.REQUEST_PAY_INFO, "payService/getPayConfig");
//        Container.registerServerPath(""+Protocol.REQUEST_PAY_SUCC, "payService/pay");
        Container.registerServerPath(""+Protocol.REQUEST_ZFB, "payService/zfbPay");
        Container.registerServerPath(""+Protocol.REQUEST_BAIFUBAO, "payService/baiFuBaoPay");
        
        
      //充值
//        Container.registerServer("mmPayService", new MMPayService());
        Container.registerServerPath(""+Protocol.REQUEST_MOBILEMM, "payService/mmPay");
        
        Container.registerServer("adminServer", new AdminServer());
        Container.registerServerPath(""+Protocol.REQUEST_ONLENUM, "adminServer/getOnlineNum");
        Container.registerServerPath(""+Protocol.REQUEST_CLOSEROBOT, "adminServer/closeOpenRobot");
        Container.registerServerPath(""+Protocol.REQUEST_CLOSE_PRESSENT_GOLDS, "adminServer/closePressentGolds");
        Container.registerServerPath(""+Protocol.REQUEST_SENDSYSTEM_NOTICE, "adminServer/sendNotice");
        Container.registerServerPath(""+Protocol.REQUEST_PAY, "adminServer/adminPay");
        Container.registerServerPath(""+Protocol.REQUEST_SEAL, "adminServer/sealAccount");
        Container.registerServerPath(""+Protocol.REQUEST_ADD_GOLD_COPPER, "adminServer/requestAddPlayerRich");
        Container.registerServerPath(""+Protocol.REQUEST_QUERY_PAY_LIST, "adminServer/checkPayForPlayer");
        Container.registerServerPath(""+Protocol.REQUEST_QUERY_GOLD_COPPER, "adminServer/questPlayerRich");
        Container.registerServerPath(""+Protocol.REQUEST_QUERY_PROP_COUNT, "adminServer/queryPlayerProp");
        Container.registerServerPath(""+Protocol.REQUEST_CLEAR_PLAYER_INFO, "adminServer/clearPlayerInfo");
        Container.registerServerPath(""+Protocol.REQUEST_ADD_PROP, "adminServer/requestAddProp");
        Container.registerServerPath(""+Protocol.REQUEST_PAY_LSIT_BY_PLAYER, "adminServer/requestPlayerPayList");
        Container.registerServerPath(""+Protocol.REQUEST_PAY_ZFB_BY_PLAYER, "adminServer/questZFBRecords");
        Container.registerServerPath(""+Protocol.REQUEST_NOTICE_LIST, "adminServer/questNoticeList");
        Container.registerServerPath(""+Protocol.REQUEST_ADD_NEW_NOTICE, "adminServer/addNewNotice");
        Container.registerServerPath(""+Protocol.REQUEST_UPDATE_NEW_NOTICE, "adminServer/updateNewNotice");
        Container.registerServerPath(""+Protocol.REQUEST_DELETE_NEW_NOTICE, "adminServer/deleteNewNotice");
        Container.registerServerPath(""+Protocol.REQUEST_SEND_MESSAGE_LIST, "adminServer/questSendMsgList");
        Container.registerServerPath(""+Protocol.REQUEST_ADD_SEND_MESSAGE, "adminServer/questAddSendMsg");
        Container.registerServerPath(""+Protocol.REQUEST_UPDATE_SEND_MESSAGE, "adminServer/questUpdateSendMsg");
        Container.registerServerPath(""+Protocol.REQUEST_DELETE_SEND_MESSAGE, "adminServer/questDeleteSendMsg");
        Container.registerServerPath(""+Protocol.REQUEST_PLAYER_CONSUME_RECORD, "adminServer/questConsumeRecords");
        
        
        
        Container.registerServer("socket_proxy", new HartServer());
        Container.registerServerPath(""+Protocol.REQUEST_SOCKET_PROXY, "socket_proxy/requestHart");
        
        Container.registerServer("shareServer", new ShareServer());
        Container.registerServerPath(""+Protocol.REQUEST_SHARE_INDEX, "shareServer/shareIndex");
        Container.registerServerPath(""+Protocol.REQUEST_SHARE_GETREWARD, "shareServer/shareGetReward");
        Container.registerServerPath(""+Protocol.REQUEST_SHARE_REGISTER, "shareServer/shareGetShareInfo");
        
        //情人系统
        Container.registerServer("loverServer", new LoverServer());
		Container.registerServerPath(""+Protocol.REQUEST_LOVERMESSAGE, "loverServer/getLoverMessage");
		Container.registerServerPath(""+Protocol.REQUEST_SENDMESSAGE, "loverServer/sendLoveMessage");
		
		//帮助系统
		Container.registerServer("helpServer", new HelpServer());
		Container.registerServerPath(""+Protocol.REQUEST_HELPTYPE, "helpServer/getLoverMessage");
		
		
		Container.registerServer("newpay", new PayNewServer());
        Container.registerServerPath(""+Protocol.REQUEST_CREATE_PAY_ORDER, "newpay/createPayOrder");
        Container.registerServerPath(""+Protocol.REQUEST_PAY_SUCC, "newpay/pay");
        Container.registerServerPath(""+Protocol.REQUEST_WEIMIPAY, "newpay/payServer");
        Container.registerServerPath(""+Protocol.REQUEST_PAYINFO, "newpay/getPayInfo");
        
        
        Container.registerServer("wheel", new WheelFortuneServer());
        Container.registerServerPath(""+Protocol.REQUEST_WHEEL, "wheel/getAllInfo");
        Container.registerServerPath(""+Protocol.REQUEST_WHEEL_CHECKTODAY, "wheel/getCheckToday");
        Container.registerServerPath(""+Protocol.REQUEST_WHEEL_LOTTERY, "wheel/getLottery");
        Container.registerServerPath(""+Protocol.REQUEST_WHEEL_LEAVEMESSAGE, "wheel/leaveMessage");
        Container.registerServerPath(""+Protocol.REQUEST_WHEEL_LEAVEWHEEL, "wheel/leaveWheel");
        Container.registerServerPath(""+Protocol.REQUEST_WHEEL_SELFMESSAGE, "wheel/getSelfMessage");
        Container.registerServerPath(""+Protocol.REQUEST_WHEEL_ADMIN, "wheel/adminCollection");
        Container.registerServerPath(""+Protocol.REQUEST_WHEEL_PAYLIST, "newpay/getWheelList");
	}
	
	public void initTimerServer() {
		/**注册系统定时器**/
//		SimpleTaskManagerService service = new SimpleTaskManagerService(5);
//		service.scheduleAtFixedRate(TimerTssk.getInstance(), 300, 100,TimeUnit.SECONDS);
	}
}
