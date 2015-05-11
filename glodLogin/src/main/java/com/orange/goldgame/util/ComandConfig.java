package  com.orange.goldgame.util;


/**
 * 协议常量设置
 * @author zhonghuating
 *
 */
public class ComandConfig {
	public static int CAMERA_WIDTH = 800; //宽度
    public static int CAMERA_HEIGHT =480; //高度
	/*外网Ftp服务器配置*/
	public static final int FTP_PORT = 21;  //ftp端口号
	public static final String FTP_HOST = "42.121.110.234"; //ftp的IP
	public static final String FTP_USERNAME = "4528209zht"; //ftp帐号
	public static final String FTP_PASSWORD = "4528209zht"; //ftp密码
	public static final String FTP_FILE_PATH = "SuperStar";  //ftp文件夹名称
	
	public static final String HTTP_FILE_PATH = "http://42.121.110.236:8080/SuperStar";
	
//  public static String host = "192.168.2.200";//小办公室服務器
//	public static String host = "192.168.2.122";//大厅服务器
//	public static String host = "42.121.110.234";//外网服务器  

//	public static String host = "192.168.58.58"; //内网服务器
//	public static String host = "192.168.1.1"; //内网服务器
	public static String host = "192.168.1.104"; //内网服务器


//	public static String host = "ifree123.eicp.net";
	/**
	 * 端口号
	 * */
	public static int port = 11001;                                     
	/**
	 * 包头
	 * */
	public static final short msg_head = 1000;            
	/**
	 * 包尾
	 * */
	public static final short msg_end  = 2000;
	/**
	 * 玩家类型
	 * */
    public static final short role_type = 9000;             
	/**
	 * 登陆类型
	 * */
    public static final short login_type = 8000;          
	/**
	 * 心跳包
	 * */
    public static final short HEART_PACK = 100;            

    
    /**
     * 发送玩家信息成功
     */
    public static final short playerInfoSuc = 1005;
    /**
     * 发送玩家信息失败
     */
    public static final short playerInfoFail = 1006;
	/**
	 * 玩家分数
	 * */
    public static final short room_sorce = 2000;       
	/**
	 * 更新玩家分数
	 * */
    public static final short update_sorce = 12001;  
	/**
	 * 更新玩家状态
	 * */
    public static final short update_playerInfo = 12002;  
	/**
	 * 请求准备
	 * */
    public static final short requist_ready = 12003;      
	/**
	 * 房主向服务器请求开始
	 * */
    public static final short requist_startKTV = 2004;  
    
    //
	/**
	 * 玩家进入K歌大厅
	 * */
    public static final short ENTER_PK_HALL = 8001;   // 8001
    /**
	 * 玩家请求创建房间
	 * */
    public static final short CREATE_ROOM = 8002;
	/**
	 * 玩家进入房间
	 * */
    public static final short ENTER_ROOM= 8003;   // 2007    
    
    
    /**获取社交好友*/
    public static final short REQUEST_SOCIAL_FRIENDS = 3001;
    
    /**获取社交好友*/
    public static final short REQUEST_NEWS = 3002;
	/**
	 * 玩家进入房间成功
	 * */
    public static final short intoRoomSuc = 8004;   // 2008  
	/**
	 * 操作失败
	 * */
    public static final short action_fail = 8005;	// 2009 
	/**
	 * 玩家离开房间
	 * */
    public static final short player_leftRoom = 8006;     // 3008        
    /**
	 * 玩家离开大厅
	 * */
    public static final short player_leftPK = 8009;  
    //
    /**
   	 * 玩家开始游戏
   	 * */
    public static final short player_startGame = 8101;   // 3007 
    /**
	 * 玩家离开游戏
	 * */
    public static final short player_leftGame = 8102;		// 3009
    //
    
	/**
	 * 世界地图
	 * */
    public static final short word = 3500;         
	/**
	 * 设置
	 * */
    public static final short setting = 4500;     
	
    
    /*******************修改后台重新定义的协议号 **********************************/
	/**
	 * 判断注册记录有没有这个手机的imei
	 * */
    public static final short IS_REGISTER  = 1001;  
    /***
     * 提交个人信息并登陆
     */
    public static final short SEND_ROLE_INF = 1002;
    /**
	 * 老用户直接登陆
	 * */
    public static final short LOGIN = 1003;  
    
    /**修改个人信息*/
    public static final short MODIFY_PLAYER_INF = 1004;  
    
    /**获取商店道具*/
    public static final short GET_FASHION_ITEMS = 2001;
    
    /**获取仓库的商品信息*/
    public static final short GET_MY_FASHION_ITEMS = 2003;
    
    /**获取声望在前10个的信息*/
    public static final short GET_TOP_TEN_ROLES = 3003;
    
    /**
	 * 返回玩家所有的音乐资源
	 * */
    public static final short GET_MUSIC_INF = 4007;  
    
    /**更新音乐最高分的信息*/
    public static final short UPDATE_MUSIC_INF = 4008;  
    
    /**获取音乐包信息*/
    public static final short GET_PK_MUSIC_INF = 4060;  
    
    /**请求获取pk大厅里面的在线玩家信息*/
    public static final short GET_HALL_PLAYER = 5017;      
    
    /**发现好友*/
    public static final short GET_FIND_FIND = 5018;    
    
    /**提交充值信息*/
    public static final short UPDATE_PAYMENT  = 6001;
    
    /*******************保留的以前的协议号 *********
	/**
	 * 登陆成功
	 * */
    public static final short loginSuc = 5001;              
	/**
	 * 登陆失败
	 * */
    public static final short loginFail = 1007;             
	/**
	 * 用户已在线
	 * */
    public static final short loginFail_Line = 1008;         
	/**
	 * 账号错误
	 * */
    public static final short loginFail_Admit = 5004;     
	/**
	 * 密码错误
	 * */
    public static final short loginFail_Pass = 1009;        
	/**
	 * 没有注册
	 * */
    public static final short loginFail_NoRegist = 1010;   
	/**
	 * 注册失败
	 * */
    public static final short registerFail = 11003;            
	/**
	 * 注册账号已占用
	 * */
    public static final short registFail_AlreadyExists= 1002;   
	/**
	 * 第一次登陆游戏
	 * */
    public static final short playerInfo_requist = 1501;          
	/**
	 * 个人信息返回
	 * */
    public static final short playerInfo_return = 1502;          
	/**
	 * 发送玩家角色信息
	 * */
    public static final short SUBMIT_ROLE_INF = 1002;             
	/**
	 * 返回玩家信息
	 * */
    public static final short returnPlayerInfo = 3004;        
	/**
	 * 玩家设置房间信息 
	 * */
    public static final short settingRoom = 3005;            
    public static final short settingRoomSuc = 2051;
	/**
	 * 玩家进入世界地图
	 * */
    public static final short intoWorld = 3006;           
    /**
     * 玩家房间聊天信息发送
     */
    public static final short player_roomChat = 3010;
    /**
     * 玩家房间聊天信息发送成功
     */
    public static final short player_roomChat_Suc = 3011;
    /**
     * 广播房聊信息
     */
    public static final short player_notify_chatContent = 3012; 
    /**
     * 领取赠送的礼物
     */
    public static final short player_achieve = 3014;
    /**
     * 系统赠送的礼物
     */
    public static final short player_gift = 3013;
    /**
     * 领取礼物成功
     */
    public static final short player_achieve_suc = 3015;
    /**
     *huoqu房间里面玩家信息
     */
    public static final short GET_PLAYER_INF = 3016;
    
    /**
     * 玩家请求发送音乐包数据
     */
    public static final short player_request_music = 3018;
    /**
     * 返回音乐包描述信息
     */
    public static final short player_return_music_config = 3019;
    /**
     * 返回音乐包每个模块的内容
     */
    public static final short player_return_music_iterator = 3020;
    /**
     * 玩家请求重新获取音乐包下载
     */
    public static final short player_download_music_restart = 3021;
    /**
     * 提示开始下载
     */
    public static final short player_download_music_start = 3022;
    /**
     * 玩家请求获取音乐包完毕
     */
    public static final short player_get_music_details_complete = 3023;
    public static final short player_request_start_download_music = 3028;
    public static final short player_request_download_music_data = 3029;
    public static final short player_request_download_music_complete = 3030;
    public static final short player_backto_room = 3031;
    public static final short player_update_playerstate = 3032;
    public static final short player_update_roomAdmin = 2050;
	/**
	 * 玩家请求开始计分
	 */
    public static final short player_setSorce = 4000;              
	/**
	 * 玩家开始游戏成功
	 * */
    public static final short player_startGame_suc =4001;    
	/**
	 * 玩家开始游戏失败
	 * */
    public static final short player_startGame_fail = 4002;   
	/**
	 * 玩家进入世界地图成功
	 * */
    public static final short player_intoworld_suc = 4003;     
	/**
	 * K歌比赛结束
	 * */
    public static final short player_game_end = 4005;        
	/**
	 * 获取房间资源
	 * */
    public static final short player_getRoomRes = 4006;       
	
	/**
	 * 浏览房间下一页
	 * */
    public static final short player_seeNext = 4009;            
	/**
	 * 返回下一页内容
	 * */
    public static final short player_returnNext = 4010;        
	/**
	 * 查询房间
	 * */
    public static final short player_searchRoom = 4011;        
	/**
	 * 进入商场
	 * */
    public static final short player_intoShop = 4012;           
	/**
	 * 购买商品
	 * */
    public static final short player_buyThing = 4013;          
	/**
	 * 购买成功
	 * */
    public static final short player_buySuc = 4014;             
	/**
	 * 添加好友
	 * */
    public static final short player_addFriend = 4015;         
	/**
	 * 删除好友
	 * */
    public static final short player_delFriend = 4016;          
	/**
	 * 添加成功
	 * */
    public static final short player_addFriendSuc = 4017;    
	/**
	 * 删除成功
	 * */
    public static final short player_delFriendSuc = 4018;     
	/**
	 * 通知玩家被添加为好友
	 * */
    public static final short player_notifyFriend = 4019;      
	/**
	 * 查询好友
	 * */
    public static final short player_searchFriend = 4020;     
	/**
	 * 打开好友列表
	 * */
    public static final short player_listFriend = 4021;          
	/**
	 * 打开好友列表成功
	 * */
    public static final short player_listFriendSuc = 4022;    
	/**
	 * 关闭好友列表
	 * */    
    public static final short player_closeFriend = 4023;       
	/**
	 * 通知被删除
	 * */
    public static final short player_noteDel = 4024;            
	/**
	 * 通知上线
	 * */
    public static final short player_noteUp = 4025;             
	/**
	 * 通知下线
	 * */
    public static final short player_noteDown = 4026;         
	/**
	 * 邀请好友
	 * */
    public static final short player_invitaFriend = 4032;      
	/**
	 * 返回邀请情况
	 * */
    public static final short player_returnInvite = 4030;      
	/**
	 * 广播创建房间
	 * */
    public static final short broadcastCreateRoom = 4027;  
	/**
	 * 广播房间更新
	 * */
    public static final short broadcastUpdateRoom = 4028; 
	/**
	 * 广播房间销毁
	 * */
    public static final short broadcastDestroyRoom = 4029;       
	/**
	 * 进入仓库
	 * */
    public static final short player_intoCK = 4030;              
	/**
	 * 使用物品
	 * */
    public static final short player_useThing = 4033;           
	/**
	 * 使用物品成功
	 * */
    public static final short player_useThingSuc = 4034;      
	/**
	 * 进入仓库成功
	 * */
    public static final short player_intoCKSuc = 4035;         
	/**
	 * 取消使用物品
	 * */
    public static final short player_resetUse = 4036;           
	/**
	 * 取消使用物品成功
	 * */
    public static final short player_resetUseSuc = 4037;     
	/**
	 * 删除物品
	 * */
    public static final short player_delThing = 4038;           
	/**
	 * 删除物品成功
	 * */
    public static final short player_delThingSuc = 4039;      
	/**
	 * 查看下一页
	 * */
    public static final short player_seeNextPage = 4040;     
	/**
	 * 离开仓库
	 * */
    public static final short player_leftCK = 4041;              
	/**
	 * 回复好友邀请
	 * */
    public static final short player_askInvita = 4042;         
	/**
	 * 物品掉落
	 * */
    public static final short player_leftThing = 4043;          

    public static final short BUY_FASHION = 2002;
    
    public static final short player_buy_thing_suc = 5004;
    
    public static final short player_left_fashion = 5005;
    public static final short player_request_friend_intoPk_size = 5006; //查找资源的总数
    public static final short player_get_friend_intoPk_size = 5007;

    public static final short player_return_CK_thing_info = 5011;
    public static final short OPERATE_FASHION = 5012;
    public static final short OPERATE_FASHION_SUCCUSS = 5013;
    public static final short player_buy_thing_fail = 5014;
    public static final short player_left_map = 5026;

    public static final short player_send_invited = 4025;		//发送邀请信息
    public static final short player_get_invited = 5019;		//返回邀请信息
    public static final short player_send_refuse_invited = 5020;	//发送拒绝
    public static final short player_get_refuse_info = 5021;		//返回拒绝信息
    public static final short player_return_friendList_intoPk = 5023;	//返回好友在PK大厅的信息
    public static final short player_return_friend_home_size = 5025;	//返回社交界面好友总数
    public static final short player_read_friendList = 5015;	//返回社交界面好友信息列表
    public static final short player_request_update_playerInfo = 5024;	//请求更新玩家信息
    public static final short player_return_update_playerInfo_suc = 5029;  //返回更新玩家信息成功
    public static final short player_return_musicList = 5030;
    public static final short player_return_data_update = 6000; //数据更新

}
