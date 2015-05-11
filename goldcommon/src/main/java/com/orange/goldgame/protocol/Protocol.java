package com.orange.goldgame.protocol;

public class Protocol {
	/** 包头*/
	public static final short HEAD = 1000;
	/** 包尾*/
	public static final short END = 2000;
	/** ip*/
	public static final String server_ip = "192.168.1.34";
	/** 端口号*/
	public static final int server_port = 5000;
	
	/****************************************客户端 协议begin************************/
	/**************************** 中级场  begin ************************************/
	public static final short REQUEST_MIDDLE_INTO_GAME = 2007;
	public static final short REQUEST_MIDDLE_READY_GAME_STATE = 2008;
	public static final short REQUEST_MIDDLE_BEGIN_GAME = 2083;
	public static final short REQUEST_MIDDLE_LOOK_POKER = 2009;
	public static final short REQUEST_MIDDLE_FOLLOW_POKER = 2010;
	public static final short REQUEST_MIDDLE_FILLING_POKER = 2011;
	public static final short REQUEST_MIDDLE_COMPARE_POKER = 2012;
	public static final short REQUEST_MIDDLE_GIVE_UP_POKER = 2013;
	public static final short REQUEST_MIDDLE_EXCHANGE_POKER = 2014;
	public static final short REQUEST_MIDDLE_DOUBLE_POKER = 2015;
	public static final short REQUEST_MIDDLE_BAN_POKER = 2016;
	public static final short REQUEST_MIDDLE_PLAYER_PROP_INFO = 2101;
	public static final short REQUEST_MIDDLE_PLAYER_PROP_USE = 2102;
	public static final short REQUEST_MIDDLE_LEAVE_ROOM = 2029;
	public static final short REQUEST_MIDDLE_GET_GOLDS = 2029;
	
	public static final short REQUEST_MIDDLE_CHAT = 2021;
	public static final short REQUEST_MIDDLE_INTO_PLAYGROUND = 2003;
	public static final short REQUEST_MIDDLE_ONLINE_PRIZE = 2090;
	public static final short REQUEST_MIDDLE_CHANGE_TABLE = 2028;
	public static final short REQUEST_MIDDLE_LEAVE_GAME = 2030;
	public static final short REQUEST_MIDDLE_FIGHT_CART = 2050;
	public static final short REQUEST_MIDDLE_COMFIRM_FIRECARD = 2034;
	public static final short REQUEST_MIDDLE_ENTERAREA = 2125;
	public static final short REQUEST_MIDDLE_GET_FRIEND_LIST = 2020;
	public static final short REQUEST_MIDDLE_REMOVE_FINEND = 2063;
	public static final short REQUEST_MIDDLE_GIFT = 2023;
	public static final short REQUEST_MIDDLE_ADD_FROENDS = 2024;
	public static final short REQUEST_MIDDLE_FRIEND_MSG = 2031;
	public static final short REQUEST_MIDDLE_SEND_GOLD = 2119;
	public static final short REQUEST_MIDDLE_FOLLWO_FRIEND = 2120;
	
	
	/**************************** 中级场    end  ************************************/
	
	/**************************** 高级场  begin ************************************/
	
	public static final short REQUEST_HIGHT_INTO_GAME = 3007;
	public static final short REQUEST_HIGHT_READY_GAME_STATE = 3008;
	public static final short REQUEST_HIGHT_BEGIN_GAME = 3083;
	public static final short REQUEST_HIGHT_LOOK_POKER = 3009;
	public static final short REQUEST_HIGHT_FOLLOW_POKER = 3010;
	public static final short REQUEST_HIGHT_FILLING_POKER = 3011;
	public static final short REQUEST_HIGHT_COMPARE_POKER = 3012;
	public static final short REQUEST_HIGHT_GIVE_UP_POKER = 3013;
	public static final short REQUEST_HIGHT_EXCHANGE_POKER = 3014;
	public static final short REQUEST_HIGHT_DOUBLE_POKER = 3015;
	public static final short REQUEST_HIGHT_BAN_POKER = 3016;
	public static final short REQUEST_HIGHT_PLAYER_PROP_INFO = 3101;
	public static final short REQUEST_HIGHT_PLAYER_PROP_USE = 3102;
	public static final short REQUEST_HIGHT_LEAVE_ROOM = 3029;
	public static final short REQUEST_HIGHT_GET_GOLDS = 3029;
	
	public static final short REQUEST_HIGHT_CHAT = 3021;
	public static final short REQUEST_HIGHT_INTO_PLAYGROUND = 3003;
	public static final short REQUEST_HIGHT_ONLINE_PRIZE = 3090;
	public static final short REQUEST_HIGHT_CHANGE_TABLE = 3028;
	public static final short REQUEST_HIGHT_LEAVE_GAME = 3030;
	public static final short REQUEST_HIGHT_FIGHT_CART = 3050;
	public static final short REQUEST_HIGHT_COMFIRM_FIRECARD = 3034;
	public static final short REQUEST_HIGHT_ENTERAREA = 3125;
	public static final short REQUEST_HIGHT_GET_FRIEND_LIST = 3020;
	public static final short REQUEST_HIGHT_REMOVE_FINEND = 3063;
	public static final short REQUEST_HIGHT_GIFT = 3023;
	public static final short REQUEST_HIGHT_ADD_FROENDS = 3024;
	public static final short REQUEST_HIGHT_FRIEND_MSG = 3031;
	public static final short REQUEST_HIGHT_SEND_GOLD = 3119;
	public static final short REQUEST_HIGHT_FOLLWO_FRIEND = 3120;
	/**************************** 高级场    end  ************************************/
	
	
	/**************************** 比赛级场  begin ************************************/
	
	public static final short REQUEST_MATCH_INTO_GAME = 4007;
	public static final short REQUEST_MATCH_READY_GAME_STATE = 4008;
	public static final short REQUEST_MATCH_BEGIN_GAME = 4083;
	public static final short REQUEST_MATCH_LOOK_POKER = 4009;
	public static final short REQUEST_MATCH_FOLLOW_POKER = 4010;
	public static final short REQUEST_MATCH_FILLING_POKER = 4011;
	public static final short REQUEST_MATCH_COMPARE_POKER = 4012;
	public static final short REQUEST_MATCH_GIVE_UP_POKER = 4013;
	public static final short REQUEST_MATCH_EXCHANGE_POKER = 4014;
	public static final short REQUEST_MATCH_DOUBLE_POKER = 4015;
	public static final short REQUEST_MATCH_BAN_POKER = 4016;
	public static final short REQUEST_MATCH_PLAYER_PROP_INFO = 4101;
	public static final short REQUEST_MATCH_PLAYER_PROP_USE = 4102;
	public static final short REQUEST_MATCH_LEAVE_ROOM = 4029;
	public static final short REQUEST_MATCH_GET_GOLDS = 4029;
	
	
	public static final short REQUEST_MATCH_CHAT = 4021;
	public static final short REQUEST_MATCH_INTO_PLAYGROUND = 4003;
	public static final short REQUEST_MATCH_ONLINE_PRIZE = 4090;
	public static final short REQUEST_MATCH_CHANGE_TABLE = 4028;
	public static final short REQUEST_MATCH_LEAVE_GAME = 4030;
	public static final short REQUEST_MATCH_FIGHT_CART = 4050;
	public static final short REQUEST_MATCH_COMFIRM_FIRECARD = 4034;
	public static final short REQUEST_MATCH_ENTERAREA = 4125;
	public static final short REQUEST_MATCH_GET_FRIEND_LIST = 4020;
	public static final short REQUEST_MATCH_REMOVE_FINEND = 4063;
	public static final short REQUEST_MATCH_GIFT = 4023;
	public static final short REQUEST_MATCH_ADD_FROENDS = 4024;
	public static final short REQUEST_MATCH_FRIEND_MSG = 4031;
	public static final short REQUEST_MATCH_SEND_GOLD = 4119;
	public static final short REQUEST_MATCH_FOLLWO_FRIEND = 4120;
	
	/**************************** 比赛级场    end  ************************************/
	/** 玩家请求登录*/
	public static final short REQUEST_LOGIN = 1001;
	/** 玩家请求登录游戏服务器*/
	public static final short REQUEST_LOGIN_SERVER = 2001;
	/** 玩家请求领取赠送金币*/
	public static final short REQUEST_GIVE_AWARD = 1002;
	/** 玩家请求进入游戏场*/
	public static final short REQUEST_INTO_PLAYGROUND = 1003;
	/** 玩家请求任务列表*/
	public static final short REQUEST_TASK_LIST = 1004;
	/** 玩家请求领取任务奖励*/
	public static final short REQUEST_GET_TASK_WARD = 1005;
	/** 玩家请求更新个人信息*/
	public static final short REQUEST_UPDATE_INFO = 1006;
	/** 玩家请求进入房间协议*/
	public static final short REQUEST_INTO_GAME = 1007;
	/** 玩家发送准备状态*/
	public static final short REQUEST_READY_GAME_STATE = 1008;
	/** 玩家发送看牌协议*/
	public static final short REQUST_LOOK_POKER = 1009;
	/** 玩家发送跟注协议*/
	public static final short REQUST_FOLLOW_POKER = 1010;
	/** 玩家发送加注协议*/
	public static final short REQUST_FILLING_POKER = 1011;
	/** 玩家发送比牌协议*/
	public static final short REQUST_COMPARE_POKER = 1012;
	/** 玩家发送放弃协议*/
	public static final short REQUST_GIVE_UP_POKER = 1013;
	/** 玩家发送换牌协议*/
	public static final short REQUST_EXCHANGE_POKER = 1014;
	/** 玩家发送翻倍协议*/
	public static final short REQUST_DOUBLE_POKER = 1015;
	/** 玩家发送禁比协议*/
	public static final short REQUST_BAN_POKER = 1016;
	/** 玩家发送购买道具协议*/
	public static final short REQUST_BUY_PROP = 1017;
	/** 玩家确认购买后请求更新财富值*/
	public static final short REQUST_BUY_PROP_ENSURE = 1018;
	
	/** 玩家发送购买宝石协议*/
	public static final short REQUST_BUY_GEMSTONE = 1037;
	/** 玩家确认购买后请求更新宝石数量*/
	public static final short REQUST_BUY_GEMSTONE_ENSURE = 1038;
	
	/** 玩家发送兑换实物协议*/
	public static final short REQUST_EXCHANGE_GOODS = 1019;
	/** 玩家发送请求好友列表协议*/
	public static final short REQUST_GET_FRIEND_LIST = 1020;
	/** 玩家发送聊天协议*/
	public static final short REQUST_CHAT = 1021;
	/** 玩家发送游戏中获得金币协议*/
	public static final short REQUST_GET_GOLDS = 1022;
	
	/** 玩家请求好友列表*/
//	public static final short REQUEST_FRIENDS = 1020;
	
	/**玩家删除好友*/
	public static final short REQUEST_REMOVE_FINEND = 1063;
	/** 玩家发送游戏中赠送礼物协议*/
	public static final short REQUST_GIFT = 1023;
	/** 玩家发送游戏中添加好友协议*/
	public static final short REQUST_ADD_FROENDS = 1024;
	/** 玩家发送喊话（广播）协议*/
	public static final short REQUST_BROADCAST = 1025;
	/** 玩家请求排行榜协议*/
	public static final short REQUST_GET_EANKING_LIST = 1026;
	/** 玩家请求活动列表协议*/
	public static final short REQUST_GET_ACTIVITY_LIST = 1027;
	/** 玩家请求换桌协议*/
	public static final short REQUST_CHANGE_TABLE = 1028;
	/** 玩家请求退出房间协议*/
	public static final short REQUST_LEAVE_ROOM = 1029;
	/** 玩家请求退出游戏协议*/
	public static final short REQUST_LEAVE_GAME = 1030;
	/** 玩家请求查看他人信息协议*/
	public static final short REQUST_FRIEND_MSG = 1031;
	/** 玩家注册协议*/
	public static final short REQUST_REGISTER = 1032;
	/** 玩家请求自己的个人信息协议*/
	public static final short REQUST_PLAYER_MSG = 1033;
	/** 玩家请求道具列表*/
	public static final short REQUEST_PROP_LIST=1043;
	/** 玩家请求兑换实物列表*/
	public static final short REQUEST_GOODS_LIST=1044;
	/** 玩家请求个人物品列表列表*/
	public static final short REQUEST_PERSONAL_PROP_LIST=1045;
	
	/** 玩家请求查看系统信息或者是个人信息（即好友消息）或者是兑换信息*/
	public static final short REQUEST_MESSAGE_LIST=1046;
	
	/** 玩家请求游戏开始 */
	public static final short REQUEST_BEGIN_GAME = 1083;
	
	/** 玩家请求可比牌列表*/
	public static final short REQUEST_FIGHT_CART = 1050;
	/**1060 请求礼物列表*/
	public static final short REQUEST_GIFT_LIST = 1060;
	
	/** 1047玩家确认兑换商品实物*/
	public static final short REQUEST_COMFIRM_EXCHANGE_GOODS=1047;
	
	/** 1034玩家确认已比牌*/
	public static final short REQUEST_COMFIRM_FIRECARD = 1034;
	
	/** 1052玩家请求每日奖励列表*/
	public static final short REQUEST_DALIY_LIST = 1052;
	
	/**1070 玩家兑换实物第二条*/
	public static final short REQUEST_SENCOND_CHANGNE = 1070;
	
	/** 3001玩家启动游戏*/
	public static final short REQUEST_EDITION_UPDATE = 3001;
	
	
	/** 1077请求帮助列表*/
	public static final short REQUEST_HELP_lIST = 1077;
	
	/** 1090请求游戏在线领取*/
	public static final short REQUEST_ONLINE_PRIZE = 1090;
	
	/** 1101 请求道具数量*/
	public static final short REQUEST_PLAYER_PROP_INFO = 1101;
	
	/** 1102 请求使用道具*/
	public static final short REQUEST_PLAYER_PROP_USE = 1102;
	
	/** 1104 请求充值列表*/
	public static final short REQUEST_PAY_INFO = 1104;
	
	/** 1105 请求充值*/
	public static final short REQUEST_PAY_SUCC = 1105;
	/** 1103 请求比赛开始*/
	public static final short REQUEST_START_MATCH = 1103;
	/** 1106 心跳包*/
	public static final short REQUEST_PLAYER_HART = 1106;
	/** 1107 请求客服信息 */
	public static final short REQUEST_CUSTOMER = 1107;
	/** 1119 玩家请求赠送金币*/
	public static final short REQUEST_SEND_GOLD = 1119;
	/** 1120 玩家请求跟踪好友*/
	public static final short REQUEST_FOLLWO_FRIEND = 1120;
	/*** 1124新手引导完成 ****/
	public static final short REQUEST_NEW_END = 1124;
	/*** 1125玩家请求进入场地 ****/
	public static final short REQUEST_ENTERAREA = 1125;
	
	
	/*** 1126玩家请求分享 ****/
	public static final short REQUEST_SHARE_INDEX = 1126;
	/*** 1127玩家请求填写推荐玩家信息 ****/
	public static final short REQUEST_SHARE_REGISTER = 1127;
	/*** 1128玩家请求领取奖励 ****/
	public static final short REQUEST_SHARE_GETREWARD = 1128;
	
	/*** 1131玩家断线重连 ****/
	public static final short REQUEST_RETURN_GAME = 1131;
	
	
	/****************************************客户端 协议end************************/

	
	/****************************************服务端 协议begin************************/
	
	/** 6001服务器返回可登陆的服务器信息 S->C */
	public static final short RESPONSE_LOGINABLE_SERVER_INFO = 6001;
	
	/** 5001服务器返回玩家信息 S->C */
	public static final short RESPONSE_PLAYER_LOGIN_INFO = 5001;
	
	/** 5002服务端返回玩家总金币数S->C */
	public static final short RESPONSE_TOTAL_GOLDS = 5002;
	
/** 作废5003服务器返回房间ID(roomId) S->C */
	public static final short RESPONSE_INTOABLE_ROOM = 5003;
	
	/** 5006服务器返回一个更新个人信息结果 S->C */
	public static final short RESPONSE_UPDATE_INFO_RESUILT = 5006;
	
	/** 5007服务端通知玩家进入房间 S->C */
	public static final short RESPONSE_NOTICE_INTO_ROOM = 5007;
	
	/** 5008服务器转发准备通知 S->C */
	public static final short RESPONSE_PLAYER_READY = 5008;
	
	/** 5080服务器广播开始发牌 S->C */
	public static final short RESPONSE_BEGIN_DISPACHER_CARD = 5080;
	
	/** 5114服务器通知游戏开始 S->C */
	public static final short RESPONSE_GAME_START = 5114;
	
	/** 5081服务器发送玩家手牌 S->C */
	public static final short RESPONSE_HANDCARD_INFO = 5081;
	
	/** 5082服务器通知有玩家进入房间 S->C */
	public static final short RESPONSE_OTHER_ENTER_ROOM = 5082;
	
	/** 5009服务器发送有玩家看牌 S->C */
	public static final short RESPONSE_NONEED_GOLDEN = 5009;
	
	/** 5013服务器发送有玩家放弃 S->C */
	public static final short RESPONSE_ABANDON = 5013;
	
	/** 5010服务器发送有玩家跟注协议 S->C */
	public static final short RESPONSE_FOLLOW_GOLDEN = 5010;
	
	/** 5011服务器发送有玩家加注 S->C */
	public static final short RESPONSE_ADD_GOLDEN = 5011;
	
	/** 5012服务器发送玩家比牌结果 S->C */
	public static final short RESPONSE_PLAYER_FIGHT_RESULT = 5012;
	
	/** 5034服务器发送本局结果 S->C */
	public static final short RESPONSE_FIGHT_RESULT = 5034;
	
	/** 5015服务器发送玩家翻倍 S->C */
	public static final short RESPONSE_FIGHT_DOUBLE_GOLDEN = 5015;
	
	/** 5014服务器返回玩家换牌协议 S->C */
	public static final short RESPONSE_EXCHANGE_CARD = 5014;
	
	/**5062服务器返回玩家剩余金币*/
	public static final short RESPONSE_LEFTGOLD = 5062;
	
	/** 5016服务器通知其他玩家有玩家使用禁比卡 S->C */
	public static final short RESPONSE_FOBIDDEN = 5016;
	
	/** 5021服务器转发玩家聊天内容 S->C */
	public static final short RESPONSE_PLAYER_CHATMESSAGE = 5021;
	
	/** 5030服务器通知有玩家离开房间S->C */
	public static final short RESPONSE_HAS_PLAYER_LEAVE_ROOM = 5030;
	
	/** 5028服务器返回换桌信息 S->C */
	public static final short RESPONSE_EXCHANGE_TABLE = 5028;
	
	/** 5025服务器返回玩家发送喊话内容（系统广播也包含在内：playerId为7000，nickname为系统消息） S->C */
	public static final short RESPONSE_SYSTEM_MESSAGE = 5025;
	
	/** 5039服务端通知有玩家进入房间 S->C */
	public static final short RESPONSE_HAS_PLAYER_INTO_ROOM = 5039;
	
	/** 5041服务器通知有玩家申请加为好友协议 S->C */
	public static final short RESPONSE_WAS_ADD_FRIEND = 5041;
	
	/** 5027服务器返回活动列表 S->C */
	public static final short RESPONSE_ACTIVITY_LIST = 5027;
	
	/** 5027服务器返回大厅信息 */
    public static final short RESPONSE_DATING_INFO = 5029;
	
	/** 5036服务器返回单注更改信息S->C */
	public static final short RESPONSE_CHANGE_SINGLEGOLDS = 5036;

	/** 5040服务器发送有玩家比牌S->C */
	public static final short RESPONSE_PLAYER_BETED = 5040;
	
	/** 5038服务器返回下注轮数更改信息S->C */
	public static final short RESPONSE_CHANGE_BETTIME = 5038;
	
	/** 5501服务器返回财富榜列表S->C */
	public static final short RESPONSE_WEALTH_LIST = 5501;
	
	/** 5502服务器返回兑换榜列表S->C */
	public static final short RESPONSE_EXCHANGE_LIST = 5502;
	
	/** 5503服务器返回魅力榜列表S->C */
	public static final short RESPONSE_CHARM_LIST = 5503;
	
	/** 5504服务器返回比赛榜列表S->C */
	public static final short RESPONSE_RECE_LIST = 5504;
	
	/** 5004服务器返回任务列表S->C */
	public static final short RESPONSE_SYSTEM_TASK_LIST = 5004;
	
	/** 5005服务器返回任务奖励*/
	public static final short RESPONSE_TASK_AWARD = 5005;
	
	/** 5031服务器返回他人信息S->C */
	public static final short RESPONSE_OTHER_INFO = 5031;
	
	/** 5033服务器返回自己个人的信息S->C */
	public static final short RESPONSE_MYSELF_INFO = 5033;
	
	/** 5045服务器返回赠送礼物协议S->C */
	public static final short RESPONSE_PLAYER_SEND_GIFT = 5045;
	
	/** 5043服务器返回道具列表S->C */
	public static final short RESPONSE_PROP_LIST = 5043;
	
	/** 5017服务器根据道具生成购买单并返回S->C */
	public static final short RESPONSE_PROP_ORDER = 5017;
	
	/** 5044服务器返回兑换实物列表S->C */
	public static final short RESPONSE_EXCHANGE_GOODS_LIST = 5044;
	
	/** 5048服务器根据宝石生成购买单并返回S->C */
	public static final short RESPONSE_GEMSTONE_ORDER = 5048;
	
	/** 5018服务器返回玩家购买宝石成功S->C */
	public static final short RESPONSE_PURCHASE_GEMSTONE = 5018;
	
	/** 5020服务器返回好友列表S->C */
	public static final short RESPONSE_FRIEND_LIST = 5020;
	
	/** 5089服务器返回宝石不够的信息S->C */
	public static final short RESPONSE_GAMESTONE_NOT_ENOUGH = 5089;
	
	/** 5042服务器返回购买道具成功S->C */
	public static final short RESPONSE_PURCHASE_PROP_SUCCESS = 5042;
	
	/** 5050服务器返回个人物品列表S->C */
	public static final short RESPONSE_PLAYER_GOODS_LIST = 5050;
	
	/** 5051服务器返回系统信息或者是个人信息（即好友消息）或者是兑换信息S->C */
	public static final short RESPONSE_MESSAGEINTO_LIST = 5051;
	
	/** 5054服务器返回玩家是否可以兑换商品实物信息S->C */
	public static final short RESPONSE_IS_EXCHANGE_ABLE = 5054;
	
	/** 5019服务器返回兑换实物成功S->C */
	public static final short RESPONSE__EXCHANGE_GOODS_SUCCESS = 5019;
	
	/** 5060服务器返回预期支付准备结束信息 */
	public static final short RESPONSE_EXPECT_READY_PAY = 5060;
	
	/** 5070服务器反馈是否可以进行登录游戏 */
	public static final short RESPONSE_IS_LOGINABLE = 5070;
	
	/** 5088服务器反馈错误类型 */
	public static final short RESPONSE_ERROR_MESSAGE = 5088;
	
	/** 服务器反馈可比牌列表 */
	public static final short RESPONSE_FIGHTGAMERLIST = 5250;
	
	/** 服务器反馈下一个操作者*/
	public static final short RESPONSE_NEXTGAMER = 5254;
	
	/** 服务器返回游戏轮数5255*/
	public static final short RESPONSE_TURNS = 5255;
	
	/**服务器返回发牌前信息5057*/
	public static final short RESPONSE_BEFOREPBLIC = 5057;
	
	/** 服务器返回添加好友是否成功5061*/
	public static final short RESPONSE_ADD_FRIEND_RESUILT = 5061;
	
	/** 服务端返回删除好友是否成功5067*/
	public static final short RESPONSE_REMOVE_FRIEND_IS_SUCCESS = 5067;
	
	/** 服务端返回是否可以送礼5068*/
	public static final short RESPONSE_ISSENDABLE = 5068;
	
	/** 服务器返回赠送金币列表5056**/
	public static final short RESPONSE_DAILYAWARTLIST = 5056;
	
	/**服务器返回礼物列表5058*/
	public static final short RESPONSE_GIFT_LIST = 5058;
	
	/**服务器返回领取每日赠送的结果5100*/
	public static final short RESPONSE_DAILY_GIVE = 5100;
	
	/**服务器返回奖励兑换卷*/
	public static final short RESPONSE_CHANGGE = 5047;
	
	/**服务器返回发公告所需要的金币**/
	public static final short RESPONSE_NOTICE_GOLES = 5049;
	
	/**5071 服务器返回兑换实物确认信息*/
	public static final short RESPONSE_COMFIRM_CHANGE = 5071;
	
	/** 5072 服务器返回帮助信息*/
	public static final short RESPONSE_HELP_lIST = 5072;
	
	/** 5090 服务器返回在线奖励信息*/
	public static final short RESPONSE_ONLINE_PRIZE = 5090;
	
	/** 5101 服务器返回道具数量*/
	public static final short RESPONSE_PLAYER_PROP_INFO = 5101;
	/** 5102 服务器返回使用道具*/
	public static final short RESPONSE_PLAYER_PROP_USE = 5102;
	/** 5073 服务器返回比赛结果信息*/
	public static final short RESPONSE_MATCH_RESULT = 5073;
	/** 5074 服务器返回比赛轮数开始前的信息*/
	public static final short RESPONSE_JUSHU_RESULT = 5074;
	/** 5075 服务器返回禁比卡时效*/
	public static final short RESPONSE_BAND_CARD = 5075;
	/** 5104 服务器返回充值列表*/
	public static final short RESPONSE_PAY_INFO = 5104;
	/** 5105 服务器返回充值成功*/
	public static final short RESPONSE_PAY_SUCC = 5105;
	/** 5115 服务器返回剩余金币 ,破产*/
	public static final short RESPONSE_LEFT_GOLD = 5115;
	/** 5116 服务器在游戏中返回赠送礼物信息*/
	public static final short RESPONSE_GIFT_GAMING = 5116;
	/** 5117服务器返回换桌清理成功*/
	public static final short RESPONSE_CLEAR_TABLE = 5117;
	/** 5118 服务器返回客服信息 */
	public static final short RESPONSE_CUSTOMER_INFO = 5118;
	/** 5120 服务器广播有人观看信息*/
	public static final short RESPONSE_WATCH = 5120;
	/** 5121 服务器返回有人观看信息*/
    public static final short RESPONSE_WATCH_INFO = 5121;
    /** 5122 服务器返回清理信息*/
    public static final short RESPONSE_CLEAR_INFO = 5122;
	/*** 服务端通知客户端有新消息***/
    public static final short RESPONSE_NOTICE_INFO = 5123;
    /*** 5124新手引导完成 ****/
	public static final short RESPONSE_NEW_END = 5124;
	/*** 5125返回是否能进入场地 ****/
	public static final short RESPONSE_ENTERAREA = 5125;
	
	
	/*** 5126玩家请求分享 ****/
	public static final short RESPONSE_SHARE_INDEX = 5126;
	/*** 5127玩家请求填写推荐玩家信息 ****/
	public static final short RESPONSE_SHARE_REGISTER = 5127;
	/*** 5128玩家请求领取奖励 ****/
	public static final short RESPONSE_SHARE_GETREWARD = 5128;
	
	
	/***-2000 支付宝服务端通知接口****/
	public static final short REQUEST_ZFB = -2000;
	/***-5000 支付宝返回****/
	public static final short RESPONSE_ZFB = -5000;
	
	/***-2001 财付通服务端通知接口****/
	public static final short REQUEST_BAIFUBAO = -2001;
	/***-5001 财付通服务端返回****/
	public static final short RESPONSE_BAIFUBAO = -5001;
	
	/***-2001 MM服务端通知接口****/
	public static final short REQUEST_MOBILEMM = -2002;
	/***-5001 MM服务端返回****/
	public static final short RESPONSE_MOBILEMM = -5002;
	
	
	/****************************************服务端 协议end************************/
	
	
	/*******************************管理员端口相关协议************************/
	
	/** 测试协议*/
	public static final short TEST_DAILYPRIZE = 7001;
	
	/*********************** admin port begin *************************/
	/** 6101 查看在线玩家数量*/
	public static final short REQUEST_ONLENUM = 6101;
	/*** 6102请求关闭或开放机器人***/
	public static final short REQUEST_CLOSEROBOT = 6102;
	/*** 6103请求关闭或开放赠送***/
	public static final short REQUEST_CLOSE_PRESSENT_GOLDS = 6103;
	/*** 6104请求发送系统公告 ***/
	public static final short REQUEST_SENDSYSTEM_NOTICE = 6104;
	/*** 6105请求发送充值请求***/
	public static final short REQUEST_PAY = 6105;
	/**6106 请求封号**/
	public static final short REQUEST_SEAL = 6106;
	/**6107 请求增加金币或宝石**/
	public static final short REQUEST_ADD_GOLD_COPPER = 6107;
	/**6108 查询玩家付费记录**/
	public static final short REQUEST_QUERY_PAY_LIST=6108;
	/**6109 请求查看玩家的金币与宝石**/
	public static final short REQUEST_QUERY_GOLD_COPPER=6109;
	/**6110 请求查看玩家相关道具数目**/
	public static final short REQUEST_QUERY_PROP_COUNT=6110;
	/**6111 请求清空玩家数据**/
	public static final short REQUEST_CLEAR_PLAYER_INFO=6111;
	
	/** 6901 服务端相应在线玩家数量*/
	public static final short RESPONSE_ONLENUM = 6901;
	/*** 6902相应关闭或开放机器人***/
	public static final short RESPONSE_CLOSEROBOT = 6902;
	/*** 6903请求关闭或开放赠送***/
	public static final short RESPONSE_CLOSE_PRESSENT_GOLDS = 6903;
	/*** 6904请求关闭或开放赠送***/
    public static final short RESPONSE_SENDSYSTEM_NOTICE = 6904;
    /*** 6905返回充值结果***/
	public static final short RESPONSE_PAY = 6905;
    /*** 6906返回封号结果*/
    public static final short RESPONSE_SEAL = 6906;
    /** 6907返回添加金币或宝石结果**/
    public static final short RESPONSE_ADD_GOLD_COPPER=6907;
    /**6908 返回玩家付费记录**/
    public static final short RESPONSE_QUERY_PAY_LIST=6908;
    /**6909 返回玩家的金币与宝石**/
    public static final short RESPONSE_QUERY_GOLD_COPPER=6909;
    /**6910 返回玩家道具数目**/
    public static final short RESPONSE_QUERY_PROP_COUNT=6910;
    /**6911 返回清除玩家数据结果**/
    public static final short RESPONSE_CLEAR_PLAYER_INFO=6911;
	/*********************** 管理员端口相关协议 *************************/
    
    
    /************* other **********/
    public static final short REQUEST_SOCKET_PROXY = -1000;
    /************* other **********/
}
