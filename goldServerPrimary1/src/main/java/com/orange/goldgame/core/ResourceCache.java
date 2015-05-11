package com.orange.goldgame.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.orange.goldgame.domain.ActivityConfig;
import com.orange.goldgame.domain.AppConfig;
import com.orange.goldgame.domain.AppVersion;
import com.orange.goldgame.domain.AppellationConfig;
import com.orange.goldgame.domain.AreaConfig;
import com.orange.goldgame.domain.ArearobotPre;
import com.orange.goldgame.domain.GoodsConfig;
import com.orange.goldgame.domain.NoticeConfig;
import com.orange.goldgame.domain.PackageProps;
import com.orange.goldgame.domain.PayChannelConfig;
import com.orange.goldgame.domain.PayInfo;
import com.orange.goldgame.domain.PropsConfig;
import com.orange.goldgame.domain.RobotPre;
import com.orange.goldgame.domain.ServerConfig;
import com.orange.goldgame.domain.StakeConfig;
import com.orange.goldgame.domain.TaskConfig;
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
	
	/**系统配置信息缓存*/
	private Map<String, AppConfig> appConfigs = new HashMap<String, AppConfig>();
	private Map<Integer, AppVersion> appVersions = new HashMap<Integer, AppVersion>();
	private Map<Integer, ServerConfig> serverConfigs = new HashMap<Integer, ServerConfig>();
	private Map<String,List<TaskConfig>> taskConfigs=new HashMap<String,List<TaskConfig>>();
	private Map<Integer,TaskConfig> taskConfigMaps=new HashMap<Integer,TaskConfig>();
	
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
	
	private Map<Integer, NoticeConfig> noticeConfigs = new HashMap<Integer, NoticeConfig>();
	
//	private Map<Integer, PayInfo> payinfos = new HashMap<Integer, PayInfo>();
	//支付信息   话费or其他->类型->支付信息
	private Map<Integer, Map<Integer, Map<Integer, PayInfo>>> payinfos = new HashMap<Integer, Map<Integer,Map<Integer, PayInfo>>>();
	/**key是渠道ID，值是本身*/
	private Map<Integer,PayChannelConfig> payChannelConfig = new HashMap<Integer, PayChannelConfig>();

	private Map<Integer, ArearobotPre> areaRobotMap = new HashMap<Integer, ArearobotPre>();
	

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
		initDirtyWords();
		initTaskConfigs();
	}
	
    private void initTaskConfigs() {
    	List<TaskConfig> list=BaseEngine.getInstace().getTaskConfigActionImpl().getAll();
    	for(TaskConfig config:list){
    		String stype=config.getTaskType();
    		List<TaskConfig> configs=taskConfigs.get(stype);
    		if(configs==null){
    			configs=new ArrayList<TaskConfig>();
    		}
    		configs.add(config);
    		taskConfigs.put(stype, configs);
    		taskConfigMaps.put(config.getId(), config);
    	}
	}
	public Map<Integer, Map<Integer, Map<Integer, PayInfo>>> getPayinfos() {
		return payinfos;
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
			robotpreLastRefleshTime = System.currentTimeMillis();
			initRobotPre();
			initareaRobotMap();
		}
		return robotPres;
	}
	public Map<Integer, ArearobotPre> getAreaRobotMap() {
		if (System.currentTimeMillis() - robotpreLastRefleshTime > 60 * 1000) {
			robotpreLastRefleshTime = System.currentTimeMillis();
			initRobotPre();
			initareaRobotMap();
		}
		return areaRobotMap;
	}
	public Map<String, AppConfig> getAppConfigs() {
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
	    return noticeConfigs;
	}
	public Map<Integer, List<PropsConfig>> getPropsConfigType() {
		return propsConfigType;
	}	
	public Map<String,List<TaskConfig>> getTaskConfigs() {
		return taskConfigs;
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
		if (list != null) {
			for (AppConfig ac : list) {
				appConfigs.put(ac.getAppKey(), ac);
			}
		}
	}
	private void initAppVersions(){
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
				packageProps.put(pp.getId(),pp);
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
    
    public Map<Integer, TaskConfig> getTaskConfigMaps() {
		return taskConfigMaps;
	}
    
    public void initDirtyWords(){
    	AppConfig ac = appConfigs.get(DirtyWordsUtil.DIRTYWORLDS);
    	String dirtyWolds = "刷币|刷宝石|刷金币";
    	if (ac != null) {
    		dirtyWolds = ac.getAppValue();
		}
    	DirtyWordsUtil.initDirtyWords(dirtyWolds);
    }
}
