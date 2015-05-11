package com.orange.goldgame.server.engine;

import com.juice.orange.game.container.Container;
import com.orange.goldgame.action.ActivityConfigAction;
import com.orange.goldgame.action.ActivityPayAction;
import com.orange.goldgame.action.AppConfigAction;
import com.orange.goldgame.action.AppellationAction;
import com.orange.goldgame.action.ApplicationAction;
import com.orange.goldgame.action.AreaConfigAction;
import com.orange.goldgame.action.AreaRobotPreAction;
import com.orange.goldgame.action.CacheAction;
import com.orange.goldgame.action.CacheActionImpl;
import com.orange.goldgame.action.ClientToolNoticeAction;
import com.orange.goldgame.action.ExchangeRecordAction;
import com.orange.goldgame.action.ExpectConsumeLogAction;
import com.orange.goldgame.action.FriendAction;
import com.orange.goldgame.action.GetOnceAction;
import com.orange.goldgame.action.GetPrizeAction;
import com.orange.goldgame.action.GiftAction;
import com.orange.goldgame.action.GiftActionRobotImpl;
import com.orange.goldgame.action.GoodsAction;
import com.orange.goldgame.action.HelpAction;
import com.orange.goldgame.action.LogAction;
import com.orange.goldgame.action.MessageAction;
import com.orange.goldgame.action.NoticeConfigAction;
import com.orange.goldgame.action.PayInfoAction;
import com.orange.goldgame.action.PayOrderAction;
import com.orange.goldgame.action.PayTypeConfigAction;
import com.orange.goldgame.action.PlayerAction;
import com.orange.goldgame.action.PlayerActivityCpaAction;
import com.orange.goldgame.action.PlayerConsumeAction;
import com.orange.goldgame.action.PlayerInfoCenterAction;
//import com.orange.goldgame.action.PlayerLoverAction;
import com.orange.goldgame.action.PlayerMessageAction;
import com.orange.goldgame.action.PlayerPayAction;
import com.orange.goldgame.action.PlayerPayAprilAction;
import com.orange.goldgame.action.PlayerPropsAction;
import com.orange.goldgame.action.PlayerShareAction;
import com.orange.goldgame.action.PlayerTalkAction;
import com.orange.goldgame.action.PlayerWheelRecoderAction;
import com.orange.goldgame.action.PlayerWheelRewardAction;
import com.orange.goldgame.action.PlayerWheelTimesAction;
import com.orange.goldgame.action.PropsAction;
import com.orange.goldgame.action.RankingAction;
import com.orange.goldgame.action.RobotPreAction;
import com.orange.goldgame.action.SealInfoAction;
import com.orange.goldgame.action.ServerConfigAction;
import com.orange.goldgame.action.ShareRewardAction;
import com.orange.goldgame.action.StakeConfigAction;
import com.orange.goldgame.action.TaskAction;
import com.orange.goldgame.action.TaskConfigAction;
import com.orange.goldgame.action.WheelRewardAction;
import com.orange.goldgame.action.ZFBPayRecordAction;
/**
 * @author wuruihuang 2013.6.17
 * 此类的作用是获取操作数据库的类
 */
public class BaseEngine {
	private static final BaseEngine instace = new BaseEngine();
	public static BaseEngine getInstace() {
		return instace;
	}
	private BaseEngine(){}
	
	private  final String REMOTE_PREFIX_SYSTEM = "GoldenFlowerSystemServer";

	private  PlayerAction playerActionIpml = (PlayerAction)Container.
			createRemoteService(PlayerAction.class, REMOTE_PREFIX_SYSTEM);
	
	private  GiftAction giftActionIpml = (GiftAction)Container.
			createRemoteService(GiftAction.class, REMOTE_PREFIX_SYSTEM);
	
	private  FriendAction friendActionIpml = (FriendAction)Container.
			createRemoteService(FriendAction.class, REMOTE_PREFIX_SYSTEM);
	
	private  TaskAction taskActionIpml = (TaskAction)Container.
			createRemoteService(TaskAction.class, REMOTE_PREFIX_SYSTEM);
	
	private  PropsAction propActionIpml = (PropsAction)Container.
			createRemoteService(PropsAction.class, REMOTE_PREFIX_SYSTEM);
	
	private  ActivityConfigAction activityActionIpml = (ActivityConfigAction)Container.
            createRemoteService(ActivityConfigAction.class, REMOTE_PREFIX_SYSTEM);
	
	private  GoodsAction goodsActionIpml = (GoodsAction)Container.
			createRemoteService(GoodsAction.class, REMOTE_PREFIX_SYSTEM);
	
	private  MessageAction messageActionIpml = (MessageAction)Container.
			createRemoteService(MessageAction.class, REMOTE_PREFIX_SYSTEM);
	
	private  LogAction logActionIpml = (LogAction)Container.
			createRemoteService(LogAction.class, REMOTE_PREFIX_SYSTEM);
	
	private  ExpectConsumeLogAction expectConsumeLogAction = (ExpectConsumeLogAction)Container.
            createRemoteService(ExpectConsumeLogAction.class, REMOTE_PREFIX_SYSTEM);
	
	private  AreaConfigAction areaConfigAction = (AreaConfigAction)Container.
			createRemoteService(AreaConfigAction.class, REMOTE_PREFIX_SYSTEM);
	
	private  AppConfigAction appConfigActionImpl = (AppConfigAction)Container.
			createRemoteService(AppConfigAction.class, REMOTE_PREFIX_SYSTEM);
	
	private  ApplicationAction applicationAction = (ApplicationAction)Container.
			createRemoteService(ApplicationAction.class, REMOTE_PREFIX_SYSTEM);
	
	private  StakeConfigAction stakeConfigAction = (StakeConfigAction)Container.
	        createRemoteService(StakeConfigAction.class, REMOTE_PREFIX_SYSTEM);
	
	private  ServerConfigAction ServerConfigActionImpl = (ServerConfigAction)Container.
			createRemoteService(ServerConfigAction.class, REMOTE_PREFIX_SYSTEM);
	
	private  NoticeConfigAction noticeConfigAction = (NoticeConfigAction)Container.
	        createRemoteService(NoticeConfigAction.class, REMOTE_PREFIX_SYSTEM);
	
	
	private GetOnceAction getOnceActionImpl = (GetOnceAction)Container.
			createRemoteService(GetOnceAction.class, REMOTE_PREFIX_SYSTEM);
	
	private GetPrizeAction getPrizeActionImpl = (GetPrizeAction)Container.
	        createRemoteService(GetPrizeAction.class, REMOTE_PREFIX_SYSTEM);
	
	private PlayerPropsAction playerPropsActionImpl = (PlayerPropsAction)Container.
	        createRemoteService(PlayerPropsAction.class, REMOTE_PREFIX_SYSTEM);
	private RobotPreAction robotPreActionImpl = (RobotPreAction)Container.
			createRemoteService(RobotPreAction.class, REMOTE_PREFIX_SYSTEM);
	
	private TaskConfigAction taskConfigActionImpl = (TaskConfigAction)Container.
            createRemoteService(TaskConfigAction.class, REMOTE_PREFIX_SYSTEM);
	
	private ExchangeRecordAction exchangeRecordAction = (ExchangeRecordAction)Container.
            createRemoteService(ExchangeRecordAction.class, REMOTE_PREFIX_SYSTEM);
	
	private PayInfoAction payInfoAction = (PayInfoAction)Container.
			createRemoteService(PayInfoAction.class, REMOTE_PREFIX_SYSTEM);
	
	private PlayerPayAction playerPayAction = (PlayerPayAction)Container.
			createRemoteService(PlayerPayAction.class, REMOTE_PREFIX_SYSTEM);
	
	private AppellationAction appellationAction = (AppellationAction)Container.
	        createRemoteService(AppellationAction.class, REMOTE_PREFIX_SYSTEM);
	
	private PayTypeConfigAction payTypeConfigAction = (PayTypeConfigAction)Container.
            createRemoteService(PayTypeConfigAction.class, REMOTE_PREFIX_SYSTEM);
	
	private PlayerInfoCenterAction playerInfoCenterAction = (PlayerInfoCenterAction)Container.
            createRemoteService(PlayerInfoCenterAction.class, REMOTE_PREFIX_SYSTEM);
	
	private AreaRobotPreAction areaRobotPreAction = (AreaRobotPreAction)Container.
			createRemoteService(AreaRobotPreAction.class,REMOTE_PREFIX_SYSTEM);
	
    private SealInfoAction sealInfoAction = (SealInfoAction)Container.
            createRemoteService(SealInfoAction.class, REMOTE_PREFIX_SYSTEM);
    private PlayerShareAction playerShareAction = (PlayerShareAction)Container.
            createRemoteService(PlayerShareAction.class, REMOTE_PREFIX_SYSTEM);
    private ShareRewardAction shareRewardAction = (ShareRewardAction)Container.
    		createRemoteService(ShareRewardAction.class, REMOTE_PREFIX_SYSTEM);
    
    private PlayerTalkAction playerTalkAction=(PlayerTalkAction) Container.
    		createRemoteService(PlayerTalkAction.class, REMOTE_PREFIX_SYSTEM);
    
    private ZFBPayRecordAction zfbPayRecordAction=(ZFBPayRecordAction) Container.
    		createRemoteService(ZFBPayRecordAction.class, REMOTE_PREFIX_SYSTEM);
    
    private ActivityPayAction activityPayAction=(ActivityPayAction) Container.
    		createRemoteService(ActivityPayAction.class, REMOTE_PREFIX_SYSTEM);
    
//    private PlayerLoverAction playerLoverAction=(PlayerLoverAction) Container.
//    		createRemoteService(PlayerLoverAction.class, REMOTE_PREFIX_SYSTEM);
//    
    private PlayerMessageAction playerMessageAction=(PlayerMessageAction) Container.
    		createRemoteService(PlayerMessageAction.class, REMOTE_PREFIX_SYSTEM);
    
    private PayOrderAction payOrderAction=(PayOrderAction) Container.
    		createRemoteService(PayOrderAction.class, REMOTE_PREFIX_SYSTEM);
    
    private WheelRewardAction wheelFortuneAction=(WheelRewardAction) Container.
    		createRemoteService(WheelRewardAction.class, REMOTE_PREFIX_SYSTEM);
    
    private PlayerWheelRewardAction playerWheelRewardAction=(PlayerWheelRewardAction) Container.
    		createRemoteService(PlayerWheelRewardAction.class, REMOTE_PREFIX_SYSTEM);
    private PlayerWheelRecoderAction playerWheelRecoderAction=(PlayerWheelRecoderAction) Container.
    		createRemoteService(PlayerWheelRecoderAction.class, REMOTE_PREFIX_SYSTEM);
    private PlayerPayAprilAction playerPayAprilAction=(PlayerPayAprilAction) Container.
    		createRemoteService(PlayerPayAprilAction.class, REMOTE_PREFIX_SYSTEM);
    
    private PlayerConsumeAction playerConsumeAction = (PlayerConsumeAction) Container.
    		createRemoteService(PlayerConsumeAction.class, REMOTE_PREFIX_SYSTEM);
    
    private ClientToolNoticeAction clientNoticeAction = (ClientToolNoticeAction) Container.
    		createRemoteService(ClientToolNoticeAction.class, REMOTE_PREFIX_SYSTEM);
    
    private PlayerWheelTimesAction playerWheelTimesAction = (PlayerWheelTimesAction) Container.
    		createRemoteService(PlayerWheelTimesAction.class, REMOTE_PREFIX_SYSTEM);
	
    private PlayerActivityCpaAction playerActivityCpaAction = (PlayerActivityCpaAction) Container.
    		createRemoteService(PlayerActivityCpaAction.class, REMOTE_PREFIX_SYSTEM);
    
	public WheelRewardAction getWheelFortuneAction() {
		return wheelFortuneAction;
	}
	public PayOrderAction getPayOrderAction() {
		return payOrderAction;
	}
	public PlayerMessageAction getPlayerMessageAction() {
		return playerMessageAction;
	}
//	public PlayerLoverAction getPlayerLoverAction() {
//		return playerLoverAction;
//	}
	public PlayerConsumeAction getPlayerConsumeAction() {
		return playerConsumeAction;
	}
	public ShareRewardAction getShareRewardAction() {
		return shareRewardAction;
	}
	public PlayerShareAction getPlayerShareAction() {
		return playerShareAction;
	}
	public AreaRobotPreAction getAreaRobotPreAction() {
		return areaRobotPreAction;
	}
	public PayInfoAction getPayInfoAction() {
		return payInfoAction;
	}
	public PlayerPayAction getPlayerPayAction() {
		return playerPayAction;
	}
	
	public ClientToolNoticeAction getClientNoticeAction(){
		return clientNoticeAction;
	}

	//机器人ACTION
	private GiftAction giftActionRobot = new GiftActionRobotImpl();
	
	//排行
	private RankingAction rankingAction=(RankingAction) Container.
			createRemoteService(RankingAction.class,REMOTE_PREFIX_SYSTEM);
	
	//帮助
	private HelpAction helpAction=(HelpAction) Container.
			createRemoteService(HelpAction.class, REMOTE_PREFIX_SYSTEM);
	
	public RobotPreAction getRobotPreActionImpl() {
		return robotPreActionImpl;
	}
	public PlayerPropsAction getPlayerPropsActionImpl() {
		return playerPropsActionImpl;
	}
	public GetOnceAction getGetOnceActionImpl() {
		return getOnceActionImpl;
	}

	public  NoticeConfigAction getNoticeConfigAction(){
	    return noticeConfigAction;
	}
	
	public  ServerConfigAction getServerConfigActionImpl() {
		return ServerConfigActionImpl;
	}

	private  CacheAction cacheAction = new CacheActionImpl();
	
	public  CacheAction getCacheAction() {
		return cacheAction;
	}

	public  ExpectConsumeLogAction getExpectConsumeLogAction() {
        return expectConsumeLogAction;
    }
	
	public  PlayerAction getPlayerActionIpml() {
		return playerActionIpml;
	}


	public  GiftAction getGiftActionIpml(int playerId) {
	    if(playerId>0)
	        return giftActionIpml;
	    else{
	        return giftActionRobot;
	    }
	}

	public  FriendAction getFriendActionIpml() {
		return friendActionIpml;
	}

	public  TaskAction getTaskActionIpml() {
		return taskActionIpml;
	}


	public  PropsAction getPropActionIpml() {
		return propActionIpml;
	}

    public  ActivityConfigAction getActivityActionIpml() {
        return activityActionIpml;
    }

    public  String getRemotePrefixSystem() {
        return REMOTE_PREFIX_SYSTEM;
    }

    public  GoodsAction getGoodsActionIpml() {
        return goodsActionIpml;
    }

    public  MessageAction getMessageActionIpml() {
        return messageActionIpml;
    }

    public  LogAction getLogActionIpml() {
        return logActionIpml;
    }

    public  AreaConfigAction getAreaConfigAction() {
        return areaConfigAction;
    }

    public  AppConfigAction getAppConfigActionImpl() {
        return appConfigActionImpl;
    }

    public  ApplicationAction getApplicationAction() {
        return applicationAction;
    }

    public  StakeConfigAction getStakeConfigAction() {
        return stakeConfigAction;
    }
    public GetPrizeAction getGetPrizeActionImpl() {
        return getPrizeActionImpl;
    }
    public TaskConfigAction getTaskConfigActionImpl() {
        return taskConfigActionImpl;
    }
    public ExchangeRecordAction getExchangeRecordAction() {
        return exchangeRecordAction;
    }
	public RankingAction getRankingAction() {
		return rankingAction;
	}
	public HelpAction getHelpAction() {
		return helpAction;
	}
    public AppellationAction getAppellationAction() {
        return appellationAction;
    }
    public PayTypeConfigAction getPayTypeConfigAction() {
        return payTypeConfigAction;
    }
    public PlayerInfoCenterAction getPlayerInfoCenterAction() {
        return playerInfoCenterAction;
    }
    public SealInfoAction getSealInfoAction() {
        return sealInfoAction;
    }
	public PlayerTalkAction getPlayerTalkAction() {
		return playerTalkAction;
	}
	public ZFBPayRecordAction getZfbPayRecordAction() {
		return zfbPayRecordAction;
	}
	public ActivityPayAction getActivityPayAction() {
		return activityPayAction;
	}
	public PlayerWheelRewardAction getPlayerWheelRewardAction() {
		return playerWheelRewardAction;
	}
	public PlayerWheelRecoderAction getPlayerWheelRecoderAction() {
		return playerWheelRecoderAction;
	}
	public PlayerPayAprilAction getPlayerPayAprilAction() {
		return playerPayAprilAction;
	}
	public PlayerWheelTimesAction getPlayerWheelTimesAction() {
		return playerWheelTimesAction;
	}
	public PlayerActivityCpaAction getPlayerActivityCpaAction() {
		return playerActivityCpaAction;
	}
	
    
}
