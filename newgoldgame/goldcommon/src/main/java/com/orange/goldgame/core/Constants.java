package com.orange.goldgame.core;

public class Constants {

	//通用类型
	/**宝石消耗|**/
	public static final byte COMMON_TYPE_ZERO=0;
	/**道具消耗|**/
	public static final byte COMMON_TYPE_ONE=1;
	/**兑换券消耗|**/
	public static final byte COMMON_TYPE_TWO=2;
	/****/
	/**系统默认语音**/
    public static final String TALK_VOICE="talk_voice";
    	
	
	/************************** Memcached key begin ***************************/
	
	public static final String PLAYER_STATUS_KEY = "player_status_key_";
	public static final String PLAYER_STATUS_OFFLINE = "0";
	public static final String PLAYER_STATUS_ONLINE = "1";
	public static final String PLAYER_STATUS_TABLE = "2";
	public static final String PLAYER_STATUS_MATCH = "3";
	public static final String PLAYER_FOR_COPPER="player_for_copper";
	public static final String PLAYER_FOR_GOLD="player_for_gold";
	
	
	/************************** Memcached key end ***************************/
	
	public static final int ONE_MINUTE = 1 * 1000;
	public static final String ROOM_TO_TABLE_KEY = "table";
	public static final String PLAYER_KEY = "player";
	/**玩家第一次注册登录系统给玩家赠送的注册奖励*/
	public static final String LOGIN_AWARD = "firstLoginAword";
	public static final String DUES_RATE = "dues_rate";
	public static final String LOW_DUES_RATE = "low_dues_rate";
	public static final String MIDDLE_DUES_RATE = "middle_dues_rate";
	public static final String HIGH_DUES_RATE = "high_dues_rate";
	public static final String READY_NUMBER = "ready_number";
	public static final String READY_TIMEOUT = "ready_timeout";
	public static final String RACE_SIGNUP_NUMBER = "race_signup_number";
	public static final String PLAY_CARD_TIMEOUT = "play_card_timeout";
	public static final String COMPARED_CARD_ROUND_NUMBER = "compared_card_round_number";
	public static final String ROUND_NUMBER = "round_number";
	public static final String DAILY_EXCHANGE = "daily_exchange";
	public static final String EXCHANGLE_LIMIT = "EXCHANGLE_LIMIT";
	public static final String FIRST_LOGIN = "first_login";
	/**系统每日赠送配置*/
	public static final String DATE_SEND = "date_send";
	/**每日赠送奖励类型*/
	public static final String DAILY_GIVE_TYPE = "gold";
	/**玩家能够送礼最低要求的游戏币的数量*/
	public static final String SEND_GIFT_NUMBER = "send_gift_number";
	/**好友类型*/
	public static final int FRIEND_TYPE = 1;
	/**每日领取*/
	public static final String DAILY_PRIZE = "daily_prize";
	
	/**每日在线奖励金钱*/
	public static final String DAILY_ONLINE_PRIZE = "send_online_money";
	
	/**每日在线奖励时间间隔*/
	public static final String DAILY_ONLINE_TIME = "send_online_time";
	
	/**每日在线奖励类型*/
	public static final String DAILY_ONLINE_TYPE = "online_gold";
	
	/**奖励规则*/
	public static final String REWARD_RULE = "reward_rule";
	
	/**  机器人胜利概率 ***/
	public static final String ROBOT_WIN_PRE = "robot_win_pre";
	/**  机器人胜 ***/
	public static final int ROBOT_WIN = 1;
	/**  机器人败 ***/
	public static final int ROBOT_FAL = -1;
	
	/**发公告需要的金币*/
	public static final String NOTICE_PAY = "notice_pay";
	/**公告长度限制*/
	public static final String NOTICE_LENGTH = "notice_length";
	/**系统公告刷新间隔*/
	public static final String NOTICE_SYSTEM_REFLESH = "notice_system_reflesh";
	/**礼物类型*/
	public static final String GIFT_TYPE = "gift_type";
	/**好友个数上限*/
	public static final String FRIEND_NUMBER="friend_number";
	/** 比赛结束所需要的局数*/
	public static final String OVER_MATCH_AMOUNT = "match_amount";
	/**比赛玩家总注*/
    public static final String MATCH_STAKE = "match_stake";
    /** 客服信息 */
    public static final String CUSTOMER_INFO = "customer_info";
    
    /** 进入游戏桌位置*/
    public static final int ENTER_SEAT = 1;
    /** 进入游戏者旁观*/
    public static final int ENTER_WATCH = 2;
    /*** 从旁观进入游戏*/
    public static final int ENTER_WATCHTOSEAT = 3;
    
    /**
     * 封号
     */
    public static final int PLAYER_STATUS_SEAL = 1;
    /**
     * 
     * 禁言->发表系统公告
     */
    public static final int PLAYER_STATUS_BAN_SYSTEM = 2;
    /**禁言->发表房间内快捷喊话**/
    public static final int PLAYER_STATUS_BAN_SPEED = 3;
    /**禁言->发表房间内自定义喊话**/
    public static final int PLAYER_STATUS_BAN_CUSTOM = 4;
    
    /*******start重置选项列表*********/
    public static final int CLEAR_COPPER=1;
    public static final int CLEAR_GOLD=2;
    
    /*******end重置选项列表*********/
	
	//1跟牌，2叫牌，3比拍，4看牌，5弃牌，-1错误信息
	public static final int FOLLOW_GOLD = 1;
	public static final int ADD_GOLD = 2;
	public static final int COMP_CARD = 3;
	public static final int LOOK_CARD = 4;
	public static final int GIVEUP_CARD = 5;
	public static final int READY = 6;
	public static final int EXITROOM = 7;
	
	public static final short NO_GOLD = 1000;
	public static final short ENTER_ROOM_ERROR = 100;
	
	public static final String HEAD_SERVER_URL = "HEAD_SERVER_URL";
	public static final String MD5_URL = "ImageMD5Controller?img=";
	
	//信息类型
	public static final int SEND_GIFT = 1;
	public static final int ADD_FRIEND = 2;
	public static final int DELETE_FRIEND = 3;
	public static final int PAY = 4;
	public static final int BUY_GOODS = 5;
	public static final int WIN_INFO = 6;
	public static final int FAIL_INFO = 7;
	public static final int CHANGLE = 8;
	public static final int RANK = 9;
	public static final int SPECIAL_INFO = 10;
	public static final int LOVER = 10;
	public static final int DEFALUT_MESSAGE = -1;
	
	
	//缓存刷新时间
	public static final int CACHE_FRESH_TIME = 2;
	//消息刷新时间
	public static final int ACTIVITY_FRESH_TIME = 2;
	//不准备的局数上限
	public static final int NO_READY_LIMIT = 5;
	
	/**运营商类型*/
	//移动
	public static final int Mobile = 1;
	//联通
	public static final int Telecom = 2;
	//电信
	public static final int Netcom = 3;
	
	/**运营商类型*/
	//联通
	public static final int Unicom = 1;
	//移动
	public static final int Move = 2;
	//电信
	public static final int China_Telecom = 3;
	
	/**计费类型*/
	//支付宝
	public static final int PayBay = 101;
	//手机充值 移动 
	public static final int PHONE = 200;
	//充值界面帮助
	public static final String VoucherHelpInfo = "1.1元=1宝石=10000金币。 2.10元以上赠送20%宝石(仅支付宝、财付通参与)。";
	//消息最大长度
	public static final int MAX_MSG_LENGTH = 70;
	//最小赢取数量发公告
	public static final int MIN_WIN_SORCE = 1000;
	
	public static final String SHARE_CONTENT_KEY = "share_content";
	public static final String SHARE_CONTENT_URL = "share_content_url";
	public static final String SHARE_CONTENT = "欢迎使用";
	public static final String SHARE_URL = "http://m.wiorange.com/pi?id=1";
	
	
	public static final String PLAYER_LAST_TALBE = "player_last_talbe_";
	public static final String ORANGE_IMEI_KEY = "orange_imei_key_";
	
	public static final String MESSAGE_INOF = "message_inof_";
	
	public static final String PLAYER_STATUS = "player_status_";
	
	public static final String PLAYER_PROPS_KEY = "player_props_key_";
	
	public static final String ADD_LOVER = "add_lover";
	
	/**炸金花活动->首次充值**/
	public static final String ACTIVITY_FIRST_PAY="ACTIVITY_FIRST_PAY";
	/**炸金花活动->累计充值**/
	public static final String ACTIVITY_SUM_PAY="ACTIVITY_SUM_PAY";
	/**炸金花活动->累计充值金额**/
	public static final int ACTIVITY_PAY_AWARD_GOLD=500;
	/**炸金花活动->首次充值并消费奖励**/
	public static final int ACTIVITY_PAY_AWARD_FOR_FIRST=-1;
	/**炸金花活动->充值累计奖励**/
	public static final int ACTIVITY_PAY_AWARD_FOR_SUM=-500;
	
	/****************移动MM计费开始**************************/
	/**普通MM计费0->127**/
	public static final byte MM_COMMON=0;
	public static final int MM_COMMON_NUM=127;
	/**土豪MM计费1->128**/
	public static final byte MM_TUHAO=1;
	public static final int MM_TUHAO_NUM=128;
	/**真人MM计费2->129**/
	public static final byte MM_ZHENREN=2;
	public static final int MM_ZHENREN_NUM=129;
	/**欢乐MM计费3->130**/
	public static final byte MM_HUANLE=3;
	public static final int MM_HUANLE_NUM=130;
	/**霸王MM计费4->131**/
	public static final byte MM_BAWANG=4;
	public static final int MM_BAWANG_NUM=131;
	/**陌陌赢三张MM计费5->132**/
	public static final byte MM_MOMO=5;
	public static final int MM_MOMO_NUM=132;
	/**陌陌赢三张MM计费6->133**/
	public static final byte MM_POKEWAGN=6;
	public static final int MM_POKEWAGN_NUM=133;
	/**豹子王MM计费7->133**/
	public static final byte MM_BAOZIWANG=7;
	public static final int MM_BAOZIWANG_NUM=134;
	/****************移动MM计费结束**************************/
	
	/**************情人消息开始***************/
	public static final String PLAYER_ALL_MESSAGE = "player_all_message_";
	public static final String PLAYER_LOVER_MESSAGE = "player_lover_message_";
	/**************情人消息结束***************/
	
	
	public static final short FULL_WHEEL = -2;
	public static final short SUCC_WHEEL = -3;
	
}
