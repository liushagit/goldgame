package com.orange.goldgame.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

import com.orange.goldgame.action.ShareRewardAction;
import com.orange.goldgame.domain.ActivityConfig;
import com.orange.goldgame.domain.AppConfig;
import com.orange.goldgame.domain.AppVersion;
import com.orange.goldgame.domain.AppellationConfig;
import com.orange.goldgame.domain.AreaConfig;
import com.orange.goldgame.domain.ArearobotPre;
import com.orange.goldgame.domain.ClientToolNotice;
import com.orange.goldgame.domain.GoodsConfig;
import com.orange.goldgame.domain.HelpQa;
import com.orange.goldgame.domain.HelpType;
import com.orange.goldgame.domain.NoticeConfig;
import com.orange.goldgame.domain.PackageProps;
import com.orange.goldgame.domain.PayChannelConfig;
import com.orange.goldgame.domain.PayInfo;
import com.orange.goldgame.domain.PropsConfig;
import com.orange.goldgame.domain.RobotPre;
import com.orange.goldgame.domain.ServerConfig;
import com.orange.goldgame.domain.ShareReward;
import com.orange.goldgame.domain.StakeConfig;
import com.orange.goldgame.domain.WheelReward;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.server.service.ActivityService;
import com.orange.goldgame.util.DateUtil;
import com.orange.goldgame.util.DirtyWordsUtil;


public class ResourceCache{
	private static final ResourceCache instance = new ResourceCache();

	public static final Random r = new Random();
	private ResourceCache(){}
	public static ResourceCache getInstance() {
		return instance;
	}
	
	//支付类型刷新时间
	private long lashRefleshTime = 0;
	//机器人发牌概率刷新
	private long robotpreLastRefleshTime = 0;
	//称谓刷新时间
	private long chengweiLastRefleshTime = 0;
	//活动刷新时间
	private long activityLastRefleshTime = 0;
	//客户发送信息刷新时间
	private long clientNoticeLastRefleshTime = 0;
	
	/**系统配置信息缓存*/
	private Map<String, AppConfig> appConfigs = new HashMap<String, AppConfig>();
	private Map<Integer, AppVersion> appVersions = new HashMap<Integer, AppVersion>();
	private Map<Integer, ServerConfig> serverConfigs = new HashMap<Integer, ServerConfig>();
	
	/**场次信息缓存*/
	private Map<Integer, AreaConfig> areaConfigs = new HashMap<Integer, AreaConfig>();
	private Map<Integer, StakeConfig> stakeConfigs = new HashMap<Integer, StakeConfig>();
	
	/**商城道具缓存*/
	private Map<Integer, PropsConfig> propsConfigs = new HashMap<Integer, PropsConfig>();
	private Map<Integer, List<PropsConfig>> propsConfigType = new HashMap<Integer, List<PropsConfig>>();
	private Map<Integer, PackageProps> packageProps = new HashMap<Integer, PackageProps>();
	
	/**比赛玩家总注*/
	private Map<Integer,Integer> matchStakeConfigs = new HashMap<Integer,Integer>();
	
	/**兑换实物缓存*/
	private Map<Integer, GoodsConfig> goodsConfigs = new HashMap<Integer, GoodsConfig>();
	
	/**活动缓存*/
	private Map<Integer,ActivityConfig> activityConfigs = new HashMap<Integer, ActivityConfig>();
	
	/**称谓缓存*/
	private List<AppellationConfig> appellationConfigs = new ArrayList<AppellationConfig>();
	
	private Map<Integer, NoticeConfig> noticeConfigs = new ConcurrentHashMap<Integer, NoticeConfig>();
	
//	private Map<Integer, PayInfo> payinfos = new HashMap<Integer, PayInfo>();
	//支付信息   话费or其他->类型->支付信息
	private Map<Integer, Map<Integer, Map<Integer, PayInfo>>> payinfos = new HashMap<Integer, Map<Integer,Map<Integer, PayInfo>>>();
	/**key是渠道ID，值是本身*/
	private Map<Integer,PayChannelConfig> payChannelConfig = new HashMap<Integer, PayChannelConfig>();

	private Map<Integer, ArearobotPre> areaRobotMap = new HashMap<Integer, ArearobotPre>();
	
	private Map<Integer, ShareReward> shareReMap = new HashMap<Integer, ShareReward>();
	
	private Map<Integer, Map<Integer, HelpQa>> helpQaMap= new HashMap<Integer, Map<Integer,HelpQa>>();
	private Map<Integer, HelpType> helpTypeMap = new HashMap<Integer, HelpType>();
	private Map<Integer, ClientToolNotice> clientNoticeMap = new ConcurrentHashMap<Integer, ClientToolNotice>();
	private LinkedBlockingDeque<ClientToolNotice> runClientNotices = new LinkedBlockingDeque<ClientToolNotice>();
	

	private Map<Integer, WheelReward> wheelReward = new HashMap<Integer, WheelReward>();
	/** 机器人获取牌型概率 ***/
	private Map<Integer, Map<String, Integer>> robotPres = new HashMap<Integer, Map<String,Integer>>();
	public void initResource(){
	    lashRefleshTime = System.currentTimeMillis();
	    
		initAppConfigs();
		initAppVersions();
		initServerConfigs();
		initAreaConfig();
		initStakeConfig();
		initPropsConfig();
		initPackageProps();
		initGoodsConfig();
		initNoticeConfigs();
		initRobotPre();
		initPayInfo();
		initMatchStake();
		initAppellationConfigs();
		initPayChannelConfig();
		initActivityConfigs();
		initareaRobotMap();
		initShareReward();
		initDirtyWords();
		initHelp();
		initWheelReward();
		initClientNotices();
	}
	
	private void reInit(){
		robotpreLastRefleshTime = System.currentTimeMillis();
		initRobotPre();
		initareaRobotMap();
		initRobotPre();
		initareaRobotMap();
		initAppConfigs();
		initAppVersions();
		initHelp();
		initWheelReward();
		initPayInfo();
	}
    public Map<Integer, Map<Integer, Map<Integer, PayInfo>>> getPayinfos() {
    	if (System.currentTimeMillis() - robotpreLastRefleshTime > 60 * 1000) {
			reInit();
		}
		return payinfos;
	}
    
    public PayInfo getPayinfoById(int id) {
    	for (Map<Integer, Map<Integer, PayInfo>> in:payinfos.values()) {
			for (Map<Integer, PayInfo> pis : in.values()) {
				for (PayInfo pi : pis.values()) {
					if (id == pi.getId().intValue()) {
						return pi;
					}
				}
			}
		}
		return null;
	}
    
    public Map<Integer, ShareReward> getShareReMap() {
    	if (System.currentTimeMillis() - robotpreLastRefleshTime > 60 * 1000) {
			reInit();
		}
		return shareReMap;
	}
    
    private void initClientNotices(){
    	List<ClientToolNotice> list = BaseEngine.getInstace().getClientNoticeAction().getClientNotices();
		for(ClientToolNotice notice : list){
			clientNoticeMap.put(notice.getId(), notice);
			addNoDoneClientNotice(notice);
		}
    }
    
    /**把未发送的客服消息加入队列**/
    private void addNoDoneClientNotice(ClientToolNotice notice) {
    	if(runClientNotices.contains(notice)) return;
		if(notice.getStatus() == Constants.COMMON_TYPE_ZERO){
			int ccount = notice.getSendAccount();
			if(ccount <= 0) return;
			long startTime = notice.getStartTime().getTime();
	    	long endTime = notice.getEndTime().getTime();
	    	String[] ss = notice.getSendSpace().split("\\|");
	    	if(ss.length <= 0){
	    		
	    		StringBuffer sb = new StringBuffer();
	    		long space = endTime - startTime;
	    		long space_time = space/ccount;
	    		sb.append("0").append("|");
	    		for(int index=1;index<=ccount;index++){
	    			sb.append(startTime + space_time*index).append("#");
	    		}
	    		notice.setSendSpace(sb.toString());
	    		BaseEngine.getInstace().getClientNoticeAction().updateClientNotice(notice);
	    		
	    	}
	    	clientNoticeMap.put(notice.getId(), notice);
			runClientNotices.add(notice);
		}
	}
    
    
	private void initShareReward(){
    	shareReMap.clear();
    	ShareRewardAction action = BaseEngine.getInstace().getShareRewardAction();
    	List<ShareReward> list = action.quaryAll();
    	for (ShareReward sr : list) {
    		shareReMap.put(sr.getId(), sr);
		}
    }
    
    private void initareaRobotMap(){
    	List<ArearobotPre> list = BaseEngine.getInstace().getAreaRobotPreAction().getAll();
    	areaRobotMap.clear();
    	for (ArearobotPre arp : list) {
    		areaRobotMap.put(arp.getAreaId(), arp);
		}
    }
	public Map<Integer, Map<String, Integer>> getRobotPres() {
		if (System.currentTimeMillis() - robotpreLastRefleshTime > 60 * 1000) {
			reInit();
		}
		return robotPres;
	}
	public Map<Integer, ArearobotPre> getAreaRobotMap() {
		if (System.currentTimeMillis() - robotpreLastRefleshTime > 60 * 1000) {
			reInit();
		}
		return areaRobotMap;
	}
	
	 public Map<Integer, Map<Integer, HelpQa>> getHelpQaMap() {
		 if (System.currentTimeMillis() - robotpreLastRefleshTime > 60 * 1000) {
				reInit();
			}
			return helpQaMap;
	}
	 public Map<Integer, HelpType> getHelpTypeMap() {
		 if (System.currentTimeMillis() - robotpreLastRefleshTime > 60 * 1000) {
			 reInit();
		 }
		 return helpTypeMap;
	 }
	public Map<String, AppConfig> getAppConfigs() {
		if (System.currentTimeMillis() - robotpreLastRefleshTime > 60 * 1000) {
			reInit();
		}
		return appConfigs;
	}
	public Map<Integer, AppVersion> getAppVersions() {
		return appVersions;
	}
	public Map<Integer, ServerConfig> getServerConfigs() {
		return serverConfigs;
	}
	public Map<Integer, AreaConfig> getAreaConfigs() {
		
		return areaConfigs;
	}
	public Map<Integer, StakeConfig> getStakeConfigs() {
		return stakeConfigs;
	}
	public Map<Integer, PropsConfig> getPropsConfigs() {
		return propsConfigs;
	}
	public Map<Integer, PackageProps> getPackageProps() {
		return packageProps;
	}
	public Map<Integer, GoodsConfig> getGoodsConfigs() {
		return goodsConfigs;
	}
	public Map<Integer, NoticeConfig> getNoticeConfigs(){
		initNoticeConfigs();
	    return noticeConfigs;
	}
	public Map<Integer, List<PropsConfig>> getPropsConfigType() {
		return propsConfigType;
	}
	
	private void initMatchStake(){
	    AppConfig config = BaseEngine.getInstace().getAppConfigActionImpl().findAppConfigByKey(Constants.MATCH_STAKE);
	    String valueStr = config.getAppValue();
	    String[] val = valueStr.split("\\|");
	    for(String stateStr : val){
	        String ste[] = stateStr.split("_");
	        int key = Integer.parseInt(ste[0]);
	        int value = Integer.parseInt(ste[1]);
	        matchStakeConfigs.put(key, value);
	    }
	}
	

    private void initPayChannelConfig() {
        payChannelConfig.clear();
        List<PayChannelConfig> configs = BaseEngine.getInstace().getPayTypeConfigAction().getAll();
        if(configs == null){
            return;
        }
        for(PayChannelConfig config : configs){
            payChannelConfig.put(config.getChannelId(), config);
        }
    }
    
    private void initActivityConfigs(){
        activityConfigs.clear();
        List<ActivityConfig> list = BaseEngine.getInstace()
                .getActivityActionIpml().getAll();
        if(list == null) return;
        for(ActivityConfig config : list){
            activityConfigs.put(config.getId(), config);
        }
    }
	
	private void initAppellationConfigs() {
	    appellationConfigs.clear();
        List<AppellationConfig> configList = BaseEngine.getInstace().getAppellationAction().getAllAppellation();
        for(AppellationConfig config : configList){
            appellationConfigs.add(config);
        }
        Collections.sort(appellationConfigs, new Comparator<AppellationConfig>(){
            @Override
            public int compare(AppellationConfig o1, AppellationConfig o2) {
                return o2.getAmount()-o1.getAmount();
            }
        });
    }
	
	private void initPayInfo(){
		payinfos.clear();
		List<PayInfo> list = BaseEngine.getInstace().getPayInfoAction().quaryAllPayInfos();
		for (PayInfo pi : list) {
//			payinfos.put(pi.getId(), pi);
			Map<Integer, Map<Integer, PayInfo>> map = payinfos.get(pi.getIsPhone());
			if (map == null) {
				map = new HashMap<Integer, Map<Integer, PayInfo>>();
				payinfos.put(pi.getIsPhone(), map);
			}
			Map<Integer, PayInfo> paymaps = map.get(pi.getPayType());
			if (paymaps == null) {
				paymaps = new HashMap<Integer, PayInfo>();
				map.put(pi.getPayType(), paymaps);
			}
			paymaps.put(pi.getId(), pi);
		}
	}
	private void initRobotPre(){
		List<RobotPre> list = BaseEngine.getInstace().getRobotPreActionImpl().quaryAllRobotPre();
		Map<String, Integer> map = null;
		robotPres.clear();
		for (RobotPre rp : list) {
			map  = robotPres.get(rp.getOperationType());
			if (map == null) {
				map = new HashMap<String, Integer>();
				robotPres.put(rp.getOperationType(), map);
			}
			map.put(rp.getOperation(), rp.getOperationValue());
		}
	}
	
	/**系统配置信息缓存*/
	private void initAppConfigs(){
		List<AppConfig> list = BaseEngine.getInstace().getAppConfigActionImpl().quaryAllAppConfigs();
		appConfigs.clear();
		if (list != null) {
			for (AppConfig ac : list) {
				appConfigs.put(ac.getAppKey(), ac);
			}
		}
	}
	
	private void initHelp(){
		helpQaMap.clear();
		helpTypeMap.clear();
		List<HelpQa> helpQas = BaseEngine.getInstace().getHelpAction().loadAllHelpInfo();
		List<HelpType> types = BaseEngine.getInstace().getHelpAction().loadHelpTypeInfo();
		Map<Integer, HelpQa> map = null;
		for (HelpQa hq : helpQas) {
			map = helpQaMap.get(hq.getHelpType());
			if (map == null) {
				map = new HashMap<Integer, HelpQa>();
				helpQaMap.put(hq.getHelpType(), map);
			}
			map.put(hq.getId(), hq);
		}
		for (HelpType helpType : types) {
			helpTypeMap.put(helpType.getId(), helpType);
		}
	}
	
	private void initAppVersions(){
		appVersions.clear();
		List<AppVersion> list = BaseEngine.getInstace().getApplicationAction().getAllAppVersions();
		if (list != null) {
			for (AppVersion av : list) {
				appVersions.put(av.getId(), av);
			}
		}
	}
	private void initNoticeConfigs(){
	    List<NoticeConfig> list = BaseEngine.getInstace().getNoticeConfigAction().getAllNoticeConfig();
	    if(list != null){
	        for(NoticeConfig nc : list){
	            noticeConfigs.put(nc.getId(), nc);
	        }
	    }
	}
	private void initServerConfigs(){
		List<ServerConfig> list = BaseEngine.getInstace().getServerConfigActionImpl().quaryAllServerConfigs();
		if (list != null) {
			for (ServerConfig sc : list) {
				serverConfigs.put(sc.getId(), sc);
			}
		}
	}
	
	/**场次信息缓存*/
	private void initAreaConfig(){
		List<AreaConfig> list = BaseEngine.getInstace().getAreaConfigAction().quaryAllAreaConfigs();
		if (list != null ) {
			for (AreaConfig ac : list) {
				areaConfigs.put(ac.getId(),ac);
			}
		}
	}
	private void initStakeConfig() {
		List<StakeConfig> list = BaseEngine.getInstace().getStakeConfigAction().getAllStakeConfig();
		if (list != null ) {
			for (StakeConfig sc : list) {
				stakeConfigs.put(sc.getId(),sc);
			}
		}		
	}
	
	/**商城道具缓存*/
	private void initPropsConfig() {
		List<PropsConfig> list = BaseEngine.getInstace().getPropActionIpml().getAllPropsConfig();
		if (list != null ) {
			List<PropsConfig> pct = null;
			for (PropsConfig pc : list) {
				propsConfigs.put(pc.getId(),pc);
				pct = propsConfigType.get(pc.getPropsType());
				if (pct == null) {
					pct = new ArrayList<PropsConfig>();
					propsConfigType.put(pc.getPropsType(), pct);
				}
				pct.add(pc);
			}
		}		
	}
	private void initPackageProps() {
		List<PackageProps> list = BaseEngine.getInstace().getPropActionIpml().queryAllPackageProp();
		if (list != null ) {
			for (PackageProps pp : list) {
				if(pp.getActivityReward() != null && pp.getActivityReward().length() > 0){
					String tmp[] = pp.getActivityReward().split("\\|");
					String ids[];
					for(int i = 0 ; i < tmp.length ; i ++){
						ids = tmp[i].split("_");
						Map<Integer, Integer> activityRewards = pp.getActivityRewards();
						if(activityRewards == null){
							activityRewards = new HashMap<Integer, Integer>();
							pp.setActivityRewards(activityRewards);
						}
						
						pp.getActivityRewards().put(Integer.parseInt(ids[0]), Integer.parseInt(ids[1]));
					}
				}
				packageProps.put(pp.getPropsId(),pp);
			}
		}		
	}
	
	/**兑换实物缓存*/
	private void initGoodsConfig() {
		List<GoodsConfig> list = BaseEngine.getInstace().getGoodsActionIpml().findAllGoodsConfig();
		if (list != null ) {
			for (GoodsConfig gc : list) {
				goodsConfigs.put(gc.getId(),gc);
			}
		}		
	}
    public Map<Integer, Integer> getMatchStakeConfigs() {
        return matchStakeConfigs;
    }
    public List<AppellationConfig> getAppellationConfigs() {
        if(System.currentTimeMillis() - chengweiLastRefleshTime > Constants.CACHE_FRESH_TIME*1000*60){
            chengweiLastRefleshTime = System.currentTimeMillis();
            initAppellationConfigs();
        }
        return appellationConfigs;
    }
    public Map<Integer, PayChannelConfig> getPayChannelConfig() {
        if(System.currentTimeMillis() - lashRefleshTime > Constants.CACHE_FRESH_TIME * Constants.ONE_MINUTE){
            lashRefleshTime = System.currentTimeMillis();
            initPayChannelConfig();
        }
        return payChannelConfig;
    }
    
   
    public Map<Integer, ActivityConfig> getActivityConfigs() {
    	if (!DateUtil.isInDateRange(ActivityService.beginTime, ActivityService.endTime)) {
    		activityConfigs.clear();
    		return activityConfigs;
		}
    	if (System.currentTimeMillis() - activityLastRefleshTime > Constants.ACTIVITY_FRESH_TIME * Constants.ONE_MINUTE ) {
			initActivityConfigs();
		}
        return activityConfigs;
    }
    
    public void initDirtyWords(){
    	AppConfig ac = appConfigs.get(DirtyWordsUtil.DIRTYWORLDS);
    	String dirtyWolds = "刷币|刷宝石|刷金币";
    	if (ac != null) {
    		dirtyWolds = ac.getAppValue();
		}
    	DirtyWordsUtil.initDirtyWords(dirtyWolds);
    }
    private void initWheelReward() {
    	wheelReward.clear();
    	List<WheelReward> list = BaseEngine.getInstace().getWheelFortuneAction().quaryAll();
    	for (WheelReward wr : list) {
    		wheelReward.put(wr.getId(), wr);
		}
		
	}
	public Map<Integer, WheelReward> getWheelReward() {
		if (System.currentTimeMillis() - activityLastRefleshTime > Constants.ACTIVITY_FRESH_TIME * Constants.ONE_MINUTE ) {
			reInit();
		}
		return wheelReward;
	}
    
    public Map<Integer, ClientToolNotice> getClientNotices(){
    	refreshClientNoticeTime();
    	return clientNoticeMap;
    }
    
    private void refreshClientNoticeTime(){
    	if(System.currentTimeMillis() - clientNoticeLastRefleshTime > 45 * 60 * 1000){
    		clientNoticeLastRefleshTime = System.currentTimeMillis();
    		runClientNotices.clear();
    		initClientNotices();
    	}
    }
    
    public LinkedBlockingDeque<ClientToolNotice> getRunClientNotices(){
    	refreshClientNoticeTime();
    	return runClientNotices;
    }
    
    public void addRunClientNotices(ClientToolNotice newNotice){
    	clientNoticeMap.put(newNotice.getId(), newNotice);
		addNoDoneClientNotice(newNotice);
    }
    
    public void removeRunClientNotice(ClientToolNotice newNotice){
    	if(runClientNotices.contains(newNotice)){
    		runClientNotices.remove(newNotice);
    	}
    }
}
