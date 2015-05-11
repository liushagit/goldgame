package com.orange.goldgame.system;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.domain.ActivityPayRecord;
import com.orange.goldgame.domain.ActivityPayRecordExample;
import com.orange.goldgame.domain.AppConfig;
import com.orange.goldgame.domain.AppConfigExample;
import com.orange.goldgame.domain.AppVersion;
import com.orange.goldgame.domain.AppVersionExample;
import com.orange.goldgame.domain.AreaConfig;
import com.orange.goldgame.domain.AreaConfigExample;
import com.orange.goldgame.domain.GetOnce;
import com.orange.goldgame.domain.GetOnceExample;
import com.orange.goldgame.domain.GoodsConfig;
import com.orange.goldgame.domain.HelpQa;
import com.orange.goldgame.domain.HelpQaExample;
import com.orange.goldgame.domain.HelpType;
import com.orange.goldgame.domain.HelpTypeExample;
import com.orange.goldgame.domain.NoticeConfig;
import com.orange.goldgame.domain.PackageProps;
import com.orange.goldgame.domain.PackagePropsExample;
import com.orange.goldgame.domain.PayInfo;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerExample;
import com.orange.goldgame.domain.PlayerFriend;
import com.orange.goldgame.domain.PlayerFriendExample;
//import com.orange.goldgame.domain.PlayerLover;
//import com.orange.goldgame.domain.PlayerLoverExample;
import com.orange.goldgame.domain.PlayerPay;
import com.orange.goldgame.domain.PlayerPayExample;
import com.orange.goldgame.domain.PlayerProps;
import com.orange.goldgame.domain.PlayerPropsExample;
import com.orange.goldgame.domain.PlayerShare;
import com.orange.goldgame.domain.PlayerShareExample;
import com.orange.goldgame.domain.PlayerShareInfo;
import com.orange.goldgame.domain.PlayerShareInfoExample;
import com.orange.goldgame.domain.PlayerTalk;
import com.orange.goldgame.domain.PlayerTalkExample;
import com.orange.goldgame.domain.PropsConfig;
import com.orange.goldgame.domain.PropsConfigExample;
import com.orange.goldgame.domain.Ranking;
import com.orange.goldgame.domain.RankingExample;
import com.orange.goldgame.domain.ServerConfig;
import com.orange.goldgame.domain.ServerConfigExample;
import com.orange.goldgame.domain.ShareReward;
import com.orange.goldgame.domain.StakeConfig;
import com.orange.goldgame.domain.StakeConfigExample;
import com.orange.goldgame.domain.SynMoney;
import com.orange.goldgame.domain.TaskConfigExample;
import com.orange.goldgame.domain.ZfbPayRecord;
import com.orange.goldgame.server.ShareGoldCache;
import com.orange.goldgame.system.dao.ActivityPayRecordDAO;
import com.orange.goldgame.system.dao.ActivityPayRecordDAOImpl;
import com.orange.goldgame.system.dao.AppConfigDAO;
import com.orange.goldgame.system.dao.AppConfigDAOImpl;
import com.orange.goldgame.system.dao.AppVersionDAO;
import com.orange.goldgame.system.dao.AppVersionDAOImpl;
import com.orange.goldgame.system.dao.AreaConfigDAO;
import com.orange.goldgame.system.dao.AreaConfigDAOImpl;
import com.orange.goldgame.system.dao.GetOnceDAO;
import com.orange.goldgame.system.dao.GetOnceDAOImpl;
import com.orange.goldgame.system.dao.GoodsConfigDAO;
import com.orange.goldgame.system.dao.GoodsConfigDAOImpl;
import com.orange.goldgame.system.dao.HelpQaDAO;
import com.orange.goldgame.system.dao.HelpQaDAOImpl;
import com.orange.goldgame.system.dao.HelpTypeDAO;
import com.orange.goldgame.system.dao.HelpTypeDAOImpl;
import com.orange.goldgame.system.dao.NoticeConfigDAO;
import com.orange.goldgame.system.dao.NoticeConfigDAOImpl;
import com.orange.goldgame.system.dao.PackagePropsDAO;
import com.orange.goldgame.system.dao.PackagePropsDAOImpl;
import com.orange.goldgame.system.dao.PayInfoDAO;
import com.orange.goldgame.system.dao.PayInfoDAOImpl;
import com.orange.goldgame.system.dao.PlayerDAO;
import com.orange.goldgame.system.dao.PlayerDAOImpl;
import com.orange.goldgame.system.dao.PlayerFriendDAO;
import com.orange.goldgame.system.dao.PlayerFriendDAOImpl;
//import com.orange.goldgame.system.dao.PlayerLoverDAO;
//import com.orange.goldgame.system.dao.PlayerLoverDAOImpl;
import com.orange.goldgame.system.dao.PlayerPayDAO;
import com.orange.goldgame.system.dao.PlayerPayDAOImpl;
import com.orange.goldgame.system.dao.PlayerPropsDAO;
import com.orange.goldgame.system.dao.PlayerPropsDAOImpl;
import com.orange.goldgame.system.dao.PlayerShareDAO;
import com.orange.goldgame.system.dao.PlayerShareDAOImpl;
import com.orange.goldgame.system.dao.PlayerShareInfoDAO;
import com.orange.goldgame.system.dao.PlayerShareInfoDAOImpl;
import com.orange.goldgame.system.dao.PlayerTalkDAO;
import com.orange.goldgame.system.dao.PlayerTalkDAOImpl;
import com.orange.goldgame.system.dao.PropsConfigDAO;
import com.orange.goldgame.system.dao.PropsConfigDAOImpl;
import com.orange.goldgame.system.dao.RankingDAO;
import com.orange.goldgame.system.dao.RankingDAOImpl;
import com.orange.goldgame.system.dao.ServerConfigDAO;
import com.orange.goldgame.system.dao.ServerConfigDAOImpl;
import com.orange.goldgame.system.dao.ShareRewardDAO;
import com.orange.goldgame.system.dao.ShareRewardDAOImpl;
import com.orange.goldgame.system.dao.StakeConfigDAO;
import com.orange.goldgame.system.dao.StakeConfigDAOImpl;
import com.orange.goldgame.system.dao.ZfbPayRecordDAO;
import com.orange.goldgame.system.dao.ZfbPayRecordDAOImpl;
import com.orange.goldgame.system.dbutil.DBService;
import com.orange.goldgame.system.dbutil.GlobalGenerator;
import com.orange.goldgame.system.util.SqlMapConfig;
import com.orange.goldgame.system.util.SqlMapConfigData;

public class DBManager {
	public static final Logger log = LoggerFactory.getLogger(DBManager.class);
	/******************************************************************************************
	 * DAO 缓存
	 * ****************************************************************************************/
	private static Map<String, Object> dataDaoMap = new HashMap<String, Object>();
	private static Map<String, Object> daoMap = new HashMap<String, Object>();

	public static Object getDataDao(Class clazz) {
		Object o = dataDaoMap.get(clazz.getName());
		if (o != null)
			return o;
		try {
			o = clazz.getConstructor(SqlMapClient.class).newInstance(SqlMapConfigData.getSqlMapInstance());
			dataDaoMap.put(clazz.getName(), o);
			return o;
		} catch (Exception e) {
			log.error("exception", e);
		}
		return null;
	}
	
	public static Object getDao(Class clazz) {
		Object o = daoMap.get(clazz.getName());
		if (o != null)
			return o;
		try {
			o = clazz.getConstructor(SqlMapClient.class).newInstance(SqlMapConfig.getSqlMapInstance());
			daoMap.put(clazz.getName(), o);
			return o;
		} catch (Exception e) {
			log.error("exception", e);
		}
		return null;
	}

	
	@SuppressWarnings("unchecked")
	public static List<ServerConfig> quaryAllServerConfigs(){
	    TaskConfigExample example = new TaskConfigExample();
		List<ServerConfig> serverConfigs = new ArrayList<ServerConfig>();
		ServerConfigDAO dao = (ServerConfigDAO)getDataDao(ServerConfigDAOImpl.class);
		try {
			serverConfigs = dao.selectByExample(null);
		} catch (Exception e) {
			log.error("获取serverConfig数据出错", e);
		}
		return serverConfigs;
	}
	
	@SuppressWarnings("unchecked")
	public static List<ServerConfig> quaryAllServerConfigsById(int id){
		ServerConfigDAO dao = (ServerConfigDAO)getDataDao(ServerConfigDAOImpl.class);
		try {
			ServerConfigExample example = new ServerConfigExample();
			example.createCriteria().andIdEqualTo(id);
			return dao.selectByExample(example);
		} catch (SQLException e) {
		}
		return new ArrayList<ServerConfig>();
	}


	@SuppressWarnings("unchecked")
	public static List<AreaConfig> quaryAllAreaConfigs() {
		List<AreaConfig> areas = null;
		AreaConfigDAO areaConfigdao = (AreaConfigDAO)getDataDao(AreaConfigDAOImpl.class);
		try {
//			AreaConfigExample areaExample = new AreaConfigExample();
			areas =  areaConfigdao.selectByExample(null);
		} catch (SQLException e) {
			log.debug("获取场次信息出错");
		}
		return areas;
	}


	public static AppVersion getApplicationById(String appId) {
		AppVersion appVersion = null;
		AppVersionDAO appDao = (AppVersionDAO)getDataDao(AppVersionDAOImpl.class);
		try {
			AppVersionExample appVersionExample = new AppVersionExample();
			appVersionExample.createCriteria().andAppIdEqualTo(appId);
			appVersion = (AppVersion)appDao.selectByExample(appVersionExample).get(0);
		} catch (Exception e) {
			log.error("ApplicationById error" , e);
		}
		return appVersion;
	}


	@SuppressWarnings("unchecked")
	public static Player getPlayerByCode(String machineCode) {
		Player player = null;
		PlayerDAO playerDao = (PlayerDAO) getDao(PlayerDAOImpl.class);
		try {
			PlayerExample playerExample = new PlayerExample();
			playerExample.createCriteria().andAccountEqualTo(machineCode);
			List<Player> players = playerDao.selectByExample(playerExample);
			if (players != null && players.size() > 0) {
				player = players.get(0);
			}
			loadPlayer(player);
		} catch (SQLException e) {
			log.error("loadplayer :" , e);
		}
		return player;
	}

	private static void loadPlayer(Player player){
		if (player != null) {
			quaryPlayerShare(player);
			
			queryPlayerActivityPay(player);
		}
	}

	public static Player getPlayerById(int playerId) {
		Player player = null;
		PlayerDAO playerDao = (PlayerDAO) getDao(PlayerDAOImpl.class);
		try {
			PlayerExample playerExample = new PlayerExample();
			playerExample.createCriteria().andIdEqualTo(playerId);
			List<Player> list = playerDao.selectByExample(playerExample);
			if (list != null && list.size() > 0) {
				player = list.get(0);
				
			}
			
			loadPlayer(player);
		} catch (SQLException e) {
			log.error("获取玩家信息数据出错",e);
		}
		
		return player;
	}
	
	public static Player getOtherPlayerById(int playerId) {
		Player player = null;
		PlayerDAO playerDao = (PlayerDAO) getDao(PlayerDAOImpl.class);
		try {
			PlayerExample playerExample = new PlayerExample();
			playerExample.createCriteria().andIdEqualTo(playerId);
			List<Player> list = playerDao.selectByExample(playerExample);
			if (list != null && list.size() > 0) {
				player = list.get(0);
				
			}
		} catch (SQLException e) {
			log.error("获取玩家信息数据出错",e);
		}
		
		return player;
	}


	public static Player createPlayer(String nickname, String machineCode,String app_channel) {
		Player player = new Player();
		player.setAccount(machineCode);
		player.setAge(23);
		player.setAppellation("极品屌丝");
		player.setHeadImage("1");
		player.setDays(0);
		player.setIsGetAward(0);
		player.setLoses(0);
		player.setNickname(getNickName(nickname));
		player.setPassword(machineCode);
		player.setSex(0);
		player.setTag("这屌丝啥也不说");
		player.setWins(0);
		player.setConnect("");
		player.setDate(new Date());
		player.setCopper(10000);
		player.setGolds(0);
		player.setIsNewPlayer((byte)1);
		player.setStatus(0);
		player.setIslogin(0);
		player.setLastLoginTime(new Date());
		player.setShareTimes(0);
		player.setChannelId(app_channel);
		player.setOnlineTime(0);
		PlayerDAO playerDao = (PlayerDAO) getDao(PlayerDAOImpl.class);
		try {
		    player.setId(playerDao.insert(player));
		} catch (SQLException e) {
			log.error("获取玩家信息数据出错",e);
		}
		return player;
	}

	private static String getNickName(String nickName){
		String newNickName = nickName;
		PlayerDAO dao = (PlayerDAO)getDao(PlayerDAOImpl.class);
		PlayerExample example = new PlayerExample();
		example.createCriteria().andNicknameLike(nickName);
		List<Player> list = null;
		Map<String, String> nameMap = new HashMap<String, String>();
		try {
			list = dao.selectByExample(example);
			for (Player p : list) {
				nameMap.put(p.getNickname(), p.getNickname());
			}
		} catch (SQLException e) {
		}
		if (nameMap.size() > 0) {
			String name = nickName;
			int index = nameMap.size() +1;
			do {
				name = nickName;
				name = name  + index;
			} while (nameMap.containsKey(name));
			newNickName = name;
		}
		return newNickName;
	}
	
	/**
	 * 通过key来获取系统配置信息
	 * @param key
	 * @return
	 */
	public static AppConfig loadAppConfigByKey(String key) {
		
		AppConfig appConfig = null;
		AppConfigDAO appConfigDAO = (AppConfigDAO) getDataDao(AppConfigDAOImpl.class);
		try {
			AppConfigExample appConfigExample = new AppConfigExample();
			appConfigExample.createCriteria().andAppKeyEqualTo(key);
			appConfig = (AppConfig)appConfigDAO.selectByExample(appConfigExample).get(0);
		} catch (SQLException e) {
			log.debug("获取系统配置信息数据出错");
		}
		return appConfig;
	}





	public static void updatePlayer(Player p) {
		PlayerDAO playerDao = (PlayerDAO) getDao(PlayerDAOImpl.class);
		try {
//		    playerDao.updateByPrimaryKey(p);
			if (p.getId() > 0) {
				DBService.commit(p);
			}
		} catch (Exception e) {
			log.debug("修改玩家信息数据出错");
		}
	}


	public static PackageProps queryPackagePropsById(int propsConfigId) {
		List<PackageProps> packageProps = null;
		PackageProps packageProp = null;
		PackagePropsDAO packageProDao = (PackagePropsDAO)getDataDao(PackagePropsDAOImpl.class);
		try {
			PackagePropsExample packagePropExample = new PackagePropsExample();
			packagePropExample.createCriteria().andPropsIdEqualTo(propsConfigId);
			packageProps = packageProDao.selectByExample(packagePropExample);
			if(packageProps!=null&&packageProps.size()>0){
				packageProp = packageProps.get(0);
			}
		} catch (SQLException e) {
			log.error("获取PackageProps数据出错", e);
		}
		return packageProp;
	}
	
	@SuppressWarnings("unchecked")
	public static List<PackageProps> loadAllPackageProps() {
		List<PackageProps> props = null;
		PackagePropsDAO packageProDao = (PackagePropsDAO)getDataDao(PackagePropsDAOImpl.class);
		try {
			props = packageProDao.selectByExample(null);
		} catch (SQLException e) {
			log.error("获取prop数据出错", e);
		}
		return props;
	}

	public static List<PropsConfig> loadAllPropsConfig() {
		List<PropsConfig> propsConfigs = null;
		PropsConfigDAO propsConfigDao = (PropsConfigDAO)getDataDao(PropsConfigDAOImpl.class);
		try {
			propsConfigs = propsConfigDao.selectByExample(null);
		} catch (SQLException e) {
			log.error("获取prop数据出错", e);
		}
		return propsConfigs;
	}

	@SuppressWarnings("unchecked")
	public static PropsConfig loadPropsConfigById(int propsConfigId) {
		PropsConfig propsConfig = null;
		List<PropsConfig> propsConfigs = null;
		PropsConfigDAO propsConfigDao = (PropsConfigDAO)getDataDao(PropsConfigDAOImpl.class);
		try {
			PropsConfigExample propsConfigExample = new PropsConfigExample();
			propsConfigExample.createCriteria().andIdEqualTo(propsConfigId);
			propsConfigs = propsConfigDao.selectByExample(propsConfigExample);
			if(propsConfigs!=null&&propsConfigs.size()>0){
				propsConfig = propsConfigs.get(0);
			}
		} catch (SQLException e) {
			log.error("获取propsConfig数据出错", e);
		}
		return propsConfig;
	}

	public static List<PropsConfig> queryPropsByType(int type) {
		List<PropsConfig> propsConfigs = null;
		PropsConfig propsConfig = null;
		PropsConfigDAO propsConfigDao = (PropsConfigDAO)getDataDao(PropsConfigDAOImpl.class);
		try {
			PropsConfigExample propsConfigExample = new PropsConfigExample();
			propsConfigExample.createCriteria().andPropsTypeEqualTo(type);
			propsConfigs = propsConfigDao.selectByExample(propsConfigExample);
		} catch (SQLException e) {
			log.error("获取propsConfig数据出错", e);
		}
		return propsConfigs;
	}
	
	@SuppressWarnings("unchecked")
	public static List<PlayerProps> loadAllPlayerProps(int playerId) {
		List<PlayerProps> playerProps = null;
		PlayerPropsDAO playerPropsDao = (PlayerPropsDAO)getDao(PlayerPropsDAOImpl.class);
		try {
			PlayerPropsExample playerPropsExample = new PlayerPropsExample();
			playerPropsExample.createCriteria().andPlayerIdEqualTo(playerId);
			playerProps = playerPropsDao.selectByExample(playerPropsExample);
		} catch (SQLException e) {
			log.error("获取PlayerProps数据出错", e);
		}
		return playerProps;
	}

	public static List<AppConfig> quaryAllAppConfig(){
		AppConfigDAO dao = (AppConfigDAO)getDataDao(AppConfigDAOImpl.class);
		List<AppConfig> list = new ArrayList<AppConfig>();
		try {
			list = dao.selectByExample(null);
		} catch (SQLException e) {
			log.error("获取PlayerProps数据出错", e);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public static List<GoodsConfig> loadGoodsConfigs() {
		
		GoodsConfigDAO goodsConfigdao = (GoodsConfigDAO)getDataDao(GoodsConfigDAOImpl.class);
		List<GoodsConfig> goodsConfiglist = null;
		try {
			goodsConfiglist = goodsConfigdao.selectByExample(null);
		} catch (SQLException e) {
			log.error("获取GoodsConfig数据出错", e);
		}
		return goodsConfiglist;
	}

	public static AreaConfig findAreaConfigById(int areaConfigId) {
		
		AreaConfig areaConfig = null;
		AreaConfigDAO areaConfigDao = (AreaConfigDAO)getDataDao(AreaConfigDAOImpl.class);
		try {
			AreaConfigExample areaConfigExample = new AreaConfigExample();
			areaConfig = areaConfigDao.selectByPrimaryKey(areaConfigId);
		} catch (SQLException e) {
			log.error("获取AreaConfig数据出错", e);
		}
		return areaConfig;
	}

	public static StakeConfig loadStakeConfigByStakeId(int stakeId) {
		StakeConfig stakeConfig = null;
		StakeConfigDAO stakeConfigDao = (StakeConfigDAO)getDataDao(StakeConfigDAOImpl.class);
		try {
			StakeConfigExample stakeConfigExample = new StakeConfigExample();
			stakeConfigExample.createCriteria().andIdEqualTo(stakeId);
			stakeConfig = stakeConfigDao.selectByPrimaryKey(stakeId);
//			stakeConfig = (StakeConfig) stakeConfigDao.selectByExample(stakeConfigExample);
		} catch (SQLException e) {
			log.error("获取StakeConfig数据出错", e);
		}
		return stakeConfig;
	}

	@SuppressWarnings("unchecked")
	public static List<StakeConfig> loadStakeConfigByAreaId(int areaId) {
		List<StakeConfig> stakeConfigs = null;
		StakeConfigDAO stakeConfigDao = (StakeConfigDAO)getDataDao(StakeConfigDAOImpl.class);
		try {
			StakeConfigExample stakeConfigExample = new StakeConfigExample();
			stakeConfigExample.createCriteria().andAreaIdEqualTo(areaId);
			stakeConfigs = stakeConfigDao.selectByExample(stakeConfigExample);
		} catch (SQLException e) {
			log.error("获取List<StakeConfig>数据出错", e);
		}
		return stakeConfigs;
	}

	@SuppressWarnings("unchecked")
	public static List<StakeConfig> loadAllStakeConfig() {
		List<StakeConfig> stakeConfigs = null;
		StakeConfigDAO stakeConfigDao = (StakeConfigDAO)getDataDao(StakeConfigDAOImpl.class);
		try {
			stakeConfigs = stakeConfigDao.selectByExample(null);
		} catch (SQLException e) {
			log.error("获取List<StakeConfig>数据出错", e);
		}
		return stakeConfigs;
	}

	public static AreaConfig loadAreaConfigByTowId(int areaTypeId,
			int areaClassId) {
		AreaConfig areaConfig = null;
		List<AreaConfig> areaConfigs = null;
		AreaConfigDAO areaConfigDao = (AreaConfigDAO)getDao(AreaConfigDAOImpl.class);
		try {
			AreaConfigExample example = new AreaConfigExample();
			example.createCriteria().andAreaTypeEqualTo((short)areaTypeId).andAreaClassEqualTo((short)areaClassId);
			areaConfigs = areaConfigDao.selectByExample(example);
			if(areaConfigs!=null&&areaConfigs.size()>0){
				areaConfig = areaConfigs.get(0);
			}
		} catch (SQLException e) {
			log.error("获取AreaConfig数据出错", e);
		}
		return areaConfig;
	}

	public static void modifyPropsNumberByTowId(int playerId, int propsId, int propsNumber) {
		PlayerProps playerProps = null;
		PlayerPropsDAO playerPropsDao = (PlayerPropsDAO)getDao(PlayerPropsDAOImpl.class);
		try {
			PlayerPropsExample example = new PlayerPropsExample();
			example.createCriteria().andPlayerIdEqualTo(playerId).andPropsConfigIdEqualTo(propsId);
			playerProps = (PlayerProps) playerPropsDao.selectByExample(example).get(0);
			playerProps.setNumber(playerProps.getNumber()+propsNumber);
			playerPropsDao.updateByPrimaryKey(playerProps);
		} catch (SQLException e) {
			log.error("获取StakeConfig数据出错", e);
		}
	}

	public static List<AppVersion> loadAllAppVersiona() {
		List<AppVersion> appVersions = null;
		AppVersionDAO appVersionDao = (AppVersionDAO)getDataDao(AppVersionDAOImpl.class);
		try {
			appVersions = appVersionDao.selectByExample(null);
		} catch (SQLException e) {
			log.error("获取AppVersion数据出错", e);
		}
		return appVersions;
	}


	public static int insertPlayerProps(PlayerProps pp) {
		PlayerPropsDAO dao = (PlayerPropsDAO) getDao(PlayerPropsDAOImpl.class);
		try {
			return dao.insert(pp);
		} catch (SQLException e) {
			log.error("初始化玩家道具信息",e);
		}
		return -1;
	}
	
	public static List<PlayerProps> quaryAllPlayerProps(Player player){
		PlayerPropsDAO dao = (PlayerPropsDAO)getDao(PlayerPropsDAOImpl.class);
		PlayerPropsExample example = new PlayerPropsExample();
		example.createCriteria().andPlayerIdEqualTo(player.getId());
		List<PlayerProps> list = null;
		try {
			list = dao.selectByExample(example);
		} catch (SQLException e) {
			log.error("quary player props error:", e);
		}
		return list;
	}

	public static GetOnce findGetOnce(int playerId, String dateKey) {
		GetOnce getOnce = null;
		List<GetOnce> getOnces = null;
		GetOnceDAO getOnceDao = (GetOnceDAO)getDao(GetOnceDAOImpl.class);
		try {
			GetOnceExample example = new GetOnceExample();
			example.createCriteria().andPlayerIdEqualTo(playerId).andDayKeyEqualTo(dateKey);
			getOnces = getOnceDao.selectByExample(example);
			if(getOnces!=null&&getOnces.size()>0){
				getOnce = getOnces.get(0);
			}
		} catch (SQLException e) {
			log.error("获取GetOnce数据出错", e);
		}
		return getOnce;
	}

	public static void updateGetOnce(GetOnce getOnce) {
		GetOnceDAO dao = (GetOnceDAO) getDao(GetOnceDAOImpl.class);
		try {
			dao.updateByPrimaryKey(getOnce);
		} catch (SQLException e) {
			log.error("修数据出错",e);
		}
	}

	public static List<NoticeConfig> loadAllNoticeConfig() {
		List<NoticeConfig> noticeConfigs = null;
		NoticeConfigDAO dao = (NoticeConfigDAO)getDataDao(NoticeConfigDAOImpl.class);
		try {
			noticeConfigs = dao.selectByExample(null);
		} catch (SQLException e) {
			log.error("获取NoticeConfig数据出错", e);
		}
		return noticeConfigs;
	}

	public static void insert(GetOnce getOnce) {
		GetOnceDAO dao = (GetOnceDAO) getDao(GetOnceDAOImpl.class);
		try {
			dao.insert(getOnce);
		} catch (SQLException e) {
			log.error("初始化玩家每日领取信息",e);
		}
	}
	
	

	@SuppressWarnings("unchecked")
	public static List<PlayerFriend> loadFriendListById(int playerId) {
		List<PlayerFriend> playerFriends = null;
		PlayerFriendDAO dao = (PlayerFriendDAO)getDao(PlayerFriendDAOImpl.class);
		try {
			PlayerFriendExample example = new PlayerFriendExample();
			example.createCriteria().andPlayerIdEqualTo(playerId);
			playerFriends = dao.selectByExample(example);
		} catch (SQLException e) {
			log.error("获取PlayerFriend数据出错", e);
			playerFriends = new ArrayList<PlayerFriend>();
		}
		return playerFriends;
	}

	public static PlayerProps loadPlayerProps(int playerId, int propsConfigId) {
		PlayerProps playerProps = null;
		List<PlayerProps> playerPropsList = null;
		PlayerPropsDAO playerPropsDao = (PlayerPropsDAO)getDao(PlayerPropsDAOImpl.class);
		try {
			PlayerPropsExample example = new PlayerPropsExample();
			example.createCriteria().andPlayerIdEqualTo(playerId).andPropsConfigIdEqualTo(propsConfigId);
			playerPropsList = playerPropsDao.selectByExample(example);
			if(playerPropsDao!=null&&playerPropsList.size()>0){
				playerProps = playerPropsList.get(0);
			}
		} catch (SQLException e) {
			log.error("获取PlayerProps数据出错", e);
		}
		return playerProps;
	}

	public static void updatePlayerProps(PlayerProps pp) {
		PlayerPropsDAO playerPropsDao = (PlayerPropsDAO)getDao(PlayerPropsDAOImpl.class);
		try {
//			playerPropsDao.updateByPrimaryKey(pp);
			DBService.commit(pp);
		} catch (Exception e) {
			log.error("更新PlayerProps数据出错", e);
		}
	}

	@SuppressWarnings("unchecked")
	public static PlayerFriend selectPlayerFriend(int playerId, int friendId) {
		PlayerFriend playerFriend = null;
		List<PlayerFriend> playerFriends = null;
		PlayerFriendDAO dao = (PlayerFriendDAO)getDao(PlayerFriendDAOImpl.class);
		try {
			PlayerFriendExample example = new PlayerFriendExample();
			example.createCriteria().andPlayerIdEqualTo(playerId).andFriendIdEqualTo(friendId);
			playerFriends = dao.selectByExample(example);
			if(playerFriends!=null&&playerFriends.size()>0){
				playerFriend = playerFriends.get(0);
			}
		} catch (SQLException e) {
			log.error("获取PlayerFriend数据出错", e);
		}
		return playerFriend;
	}

	public static void removeFriend(PlayerFriend pf) {
		PlayerFriendDAO dao = (PlayerFriendDAO)getDao(PlayerFriendDAOImpl.class);
		try {
			dao.deleteByPrimaryKey(pf.getId());
		} catch (SQLException e) {
			log.error("获取PlayerFriend数据出错", e);
		}
	}

	@SuppressWarnings("unchecked")
	public static PlayerFriend inserFriend(PlayerFriend pf) {
		List<PlayerFriend> pfList = null;
		PlayerFriendDAO dao = (PlayerFriendDAO)getDao(PlayerFriendDAOImpl.class);
		try {
			PlayerFriendExample exmaple = new PlayerFriendExample();
			exmaple.createCriteria().andPlayerIdEqualTo(pf.getPlayerId()).andFriendIdEqualTo(pf.getFriendId());
			dao.insert(pf);
			pfList = dao.selectByExample(exmaple);
			if(pfList!=null&&pfList.size()>0){
				pf = pfList.get(0);
			}
		} catch (SQLException e) {
			log.error("获取PlayerFriend数据出错", e);
		}
		return pf;
	}
	
	public static void updateFriend(PlayerFriend pf){
		PlayerFriendDAO dao = (PlayerFriendDAO)getDao(PlayerFriendDAOImpl.class);
		try {
			dao.updateByPrimaryKey(pf);
		} catch (SQLException e) {
			log.error("update player friend error", e);
		}
	}
	
	
	//排名
	@SuppressWarnings("unchecked")
	public static List<Ranking> getUserRankByRankType(int rankTypeId){
		List<Ranking> rankings=null;
		RankingDAO dao=(RankingDAO) getDao(RankingDAOImpl.class);
		try {
			RankingExample example=new RankingExample();
		    example.createCriteria().andRankTypeEqualTo(rankTypeId);
		    rankings=dao.selectByExample(example);	
		    return rankings;
		} catch (SQLException e) {
			log.error("获取Ranking数据出错", e);
		}
		return rankings;
	}
	
	//帮助问题
	@SuppressWarnings("unchecked")
	public static List<HelpQa> getHelpInfos(int helpType){
		List<HelpQa> helps=null;
		HelpQaDAO dao=(HelpQaDAO) getDataDao(HelpQaDAOImpl.class);
		try {
			HelpQaExample example=new HelpQaExample();
			example.createCriteria().andHelpTypeEqualTo(helpType);
			helps=dao.selectByExample(example);
		} catch (SQLException e) {
			log.error("获取help信息数据出错", e);
		}
		return helps;	
	}
	
	//帮助类型
	@SuppressWarnings("unchecked")
	public static HelpType getHelpType(int helpTypeId){
		HelpType help=null;
		HelpTypeDAO dao=(HelpTypeDAO) getDataDao(HelpTypeDAOImpl.class);
		try {
			HelpTypeExample example=new HelpTypeExample();
			example.createCriteria().andIdEqualTo(helpTypeId);
			List<HelpType> helps=dao.selectByExample(example);
			if(helps!=null&&helps.size()>0){
				help=helps.get(0);
			}
		} catch (SQLException e) {
			log.error("获取help_type信息数据出错", e);
		}
		return help;
	}
	
	
	

	public static void deletePlayerProps(PlayerProps pp){
		PlayerPropsDAO dao = (PlayerPropsDAO)getDao(PlayerPropsDAOImpl.class);
		try {
			dao.deleteByPrimaryKey(pp.getId());
		} catch (SQLException e) {
			log.error("delete prop exception:" ,e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<PayInfo> quaryAllPayInfos(){
		PayInfoDAO dao = (PayInfoDAO)getDataDao(PayInfoDAOImpl.class);
		List<PayInfo> list = null;
		try {
			list = dao.selectByExample(null);
		} catch (SQLException e) {
			log.error("quaryAllPayInfos exception:", e);
			list = new ArrayList<PayInfo>();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public static PlayerPay quaryPlayerPay(int player_id, String order_id) {
		PlayerPayDAO dao  = (PlayerPayDAO)getDao(PlayerPayDAOImpl.class);
		PlayerPayExample example = new PlayerPayExample();
		example.createCriteria().andPlayerIdEqualTo(player_id).andOrderIdEqualTo(order_id);
		List<PlayerPay> list = null;
		try {
			list = dao.selectByExample(example);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		} catch (SQLException e) {
			log.error("quaryPlayerPay exception", e);
		}
		return null;
	}
	public static int insertPlayerPay(PlayerPay pay) {
		PlayerPayDAO dao  = (PlayerPayDAO)getDao(PlayerPayDAOImpl.class);
		int res = 0;
		try {
			res = dao.insert(pay);
		} catch (SQLException e) {
			log.error("insertPlayerPay exception", e);
		}
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public static List<PlayerPay> queryAllPayForPlayer(int playerId){
		List<PlayerPay> list=null;
		PlayerPayDAO dao  = (PlayerPayDAO)getDao(PlayerPayDAOImpl.class);
		PlayerPayExample example=new PlayerPayExample();
		example.createCriteria().andPlayerIdEqualTo(playerId);
		try {
			list=dao.selectByExample(example);
		} catch (SQLException e) {
			list=new ArrayList<PlayerPay>();
			log.error("query all pay info for player exception", e);
		}
		return list;
	}
	
	public static List<Player> loadAllPlayers(){
		List<Player> list=null;
		PlayerDAO playerDao = (PlayerDAO) getDao(PlayerDAOImpl.class);
		PlayerExample example = new PlayerExample();
		example.createCriteria().andStatusNotEqualTo(1);
		example.setOrderByClause("copper desc limit 10");
		try {
			list = playerDao.selectByExample(example);	
			
		} catch (SQLException e) {	
			list=new ArrayList<Player>();
			log.error("获取全部玩家信息数据出错",e);
		}	
		return list;
	}
	
	//插入排行榜信息
	public static void insert(Ranking rank) {
		RankingDAO dao=(RankingDAO) getDao(RankingDAOImpl.class);
		try {
//			dao.insertSelective(rank);
//			res=dao.insert(rank);
			dao.insert(rank);
		} catch (SQLException e) {
			log.error("添加排行榜数据出错",e);
		}	
	}

	//更新排行榜记录
	public static int update(Ranking rank) {
		int res=0;
		RankingDAO dao=(RankingDAO) getDao(RankingDAOImpl.class);
		try {
			res=dao.updateByPrimaryKey(rank);
		} catch (SQLException e) {
			log.error("更新排行榜数据出错",e);
		}
		return res;
	}
	
	//获取玩家道具记录
	@SuppressWarnings("unchecked")
	public static List<PlayerProps> getAllPlayerExhangeCount(int exchangeID) {	
		List<PlayerProps> list=new ArrayList<PlayerProps>();
		PlayerPropsDAO dao = (PlayerPropsDAO)getDao(PlayerPropsDAOImpl.class);
		PlayerPropsExample example=new PlayerPropsExample();
		example.createCriteria().andPropsConfigIdEqualTo(exchangeID);
		example.setOrderByClause("number desc limit 10");
		try {
			list=dao.selectByExample(example);
		} catch (SQLException e) {
			log.error("获取玩家道具记录",e);
		}
			return list;
	}
	
	public static PlayerProps getPlayerProp(int playerId,int propId){		
		PlayerProps playerPorp=null;
		PlayerPropsDAO dao = (PlayerPropsDAO)getDao(PlayerPropsDAOImpl.class);
		PlayerPropsExample example=new PlayerPropsExample();
		example.createCriteria().andPlayerIdEqualTo(playerId).andPropsConfigIdEqualTo(propId);
		try {
			List<PlayerProps> list=dao.selectByExample(example);
			if(list!=null&&list.size()>0){
				playerPorp=list.get(0);
			}
		} catch (SQLException e) {
			log.error("获取玩家指定道具失败...",e);
		}
		return playerPorp;
	}
	
	public static void deleteForRankType(int rankTypeID) {	
		RankingDAO dao=(RankingDAO) getDao(RankingDAOImpl.class);
		RankingExample example=new RankingExample();
		example.createCriteria().andRankTypeEqualTo(rankTypeID);
		try {
			dao.deleteByExample(example);
		} catch (SQLException e) {
			log.error("删除排行榜数据出错",e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static boolean queryRank(int playerID) {
		boolean isExit=false;
		List<Ranking> list=null;
		RankingDAO dao=(RankingDAO) getDao(RankingDAOImpl.class);
		RankingExample example=new RankingExample();
		example.createCriteria().andPlayerIdEqualTo(playerID);	
		try {
			list=dao.selectByExample(example);
			if(list!=null&&list.size()>0){
				isExit=true;
			}
		} catch (SQLException e) {
			list=new ArrayList<Ranking>();
			log.error("查询排行榜数据出错",e);
		}
			return isExit;
		}
	
	public static int insertPlayerShare(PlayerShare playerShare,PlayerShareInfo playerShareInfo){
		PlayerShareDAO dao = (PlayerShareDAO) getDao(PlayerShareDAOImpl.class);
		PlayerShareInfoDAO dao2 = (PlayerShareInfoDAO) getDao(PlayerShareInfoDAOImpl.class);
		int result = -1;
		try {
			result = dao.insert(playerShare);
//			dao2.insert(playerShareInfo);
		} catch (SQLException e) {
			log.error("insertPlayerShare error",e);
		}
		return result;
	}
	
	public static void updatePlayerShare(PlayerShare playerShare,PlayerShareInfo playerShareInfo){
		PlayerShareDAO dao = (PlayerShareDAO) getDao(PlayerShareDAOImpl.class);
		try {
			dao.updateByPrimaryKey(playerShare);
			if (playerShareInfo != null) {
				PlayerShareInfoDAO dao2 = (PlayerShareInfoDAO) getDao(PlayerShareInfoDAOImpl.class);
				dao2.insert(playerShareInfo);
			}
		} catch (SQLException e) {
			log.error("updatePlayerShare error",e);
		}
	}
	
	public static List<ShareReward> quaryAllShareRewards(){
		ShareRewardDAO dao = (ShareRewardDAO) getDataDao(ShareRewardDAOImpl.class);
		List<ShareReward> list = null;
		try {
			list = dao.selectByExample(null);
		} catch (SQLException e) {
			list = new ArrayList<ShareReward>();
			log.error("quaryAllShareRewards error",e);
		}
		return list;
	}
	
	public static void queryPlayerActivityPay(Player player){
		ActivityPayRecord record=null;
//		record=queryActivityPayRecords(player.getId(), Constants.ACTIVITY_FIRST_PAY);
//		if(record!=null){
//			player.setFirstPayRecord(record);
//		}
		
		record=queryActivityPayRecords(player.getId(), Constants.ACTIVITY_SUM_PAY);
		if(record!=null){
			player.setSumPayRecord(record);
		}
	}
	
	public static void quaryPlayerShare(Player player){
		PlayerShareDAO dao = (PlayerShareDAO) getDao(PlayerShareDAOImpl.class);
		PlayerShareExample example = new PlayerShareExample();
		example.createCriteria().andPlayerIdEqualTo(player.getId());
		try {
			List<PlayerShare> list = dao.selectByExample(example);
			if (list != null && list.size() > 0) {
				PlayerShare playerShare = list.get(0);
				player.setPlayerShare(playerShare);
				PlayerShareInfoDAO dao2 = (PlayerShareInfoDAO) getDao(PlayerShareInfoDAOImpl.class);
				PlayerShareInfoExample example2 = new PlayerShareInfoExample();
				example2.createCriteria().andPlayerIdEqualTo(player.getId());
				List<PlayerShareInfo> list2 = dao2.selectByExample(example2);
				if (list2 != null && list2.size() > 0) {
					Map<Integer,PlayerShareInfo> playerShareInfo = playerShare.getPlayerShareInfo();
					if (playerShareInfo == null) {
						playerShareInfo = new HashMap<Integer, PlayerShareInfo>();
						playerShare.setPlayerShareInfo(playerShareInfo);
					}
					Player otherPlayer=null;
					for (PlayerShareInfo psi : list2) {
						otherPlayer=getOtherPlayerById(psi.getOtherId());
						psi.setHeadImg(otherPlayer.getHeadImage());
						psi.setOtherName(otherPlayer.getNickname());
						playerShareInfo.put(psi.getOtherId(), psi);
					}
				}
			}
		} catch (SQLException e) {
			log.error("quaryPlayerShare error",e);
		}
		
	}
	
	
	public static void insertPlayerTalk(PlayerTalk talk){
		PlayerTalkDAO dao=(PlayerTalkDAO) getDao(PlayerTalkDAOImpl.class);
		try {
//			int id = GlobalGenerator.getInstance().getReusedIdForNewObj(PlayerTalk.TABLENAME);
//			talk.setId(id);
//			DBService.commit(talk);
			talk.setId(dao.insert(talk));
		} catch (Exception e) {
			log.error("insert player talk error",e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<PlayerTalk> queryPlayerTalkList(int player_id){
		List<PlayerTalk> list=null;
		PlayerTalkDAO dao=(PlayerTalkDAO) getDao(PlayerTalkDAOImpl.class);
		PlayerTalkExample example=new PlayerTalkExample();
		example.createCriteria().andPlayerIdEqualTo(player_id);
		try {
			list=dao.selectByExample(example);
		} catch (SQLException e) {
			list=new ArrayList<PlayerTalk>();
			log.error("query all player talk error",e);
		}
		return list;
	}
	
	public static void insertZFBRecord(ZfbPayRecord record){
		ZfbPayRecordDAO dao=(ZfbPayRecordDAO) getDao(ZfbPayRecordDAOImpl.class);
		try {
			dao.insert(record);
		} catch (SQLException e) {
			log.error("insert zfb pay record error!",e);
		}
	}
	
	public static int insertActivityPayRecord(ActivityPayRecord record){
		ActivityPayRecordDAO dao=(ActivityPayRecordDAO) getDao(ActivityPayRecordDAOImpl.class);
		try {
			return dao.insert(record);
		} catch (SQLException e) {
			log.error("insert activity pay record error!",e);
		}
		return -1;
	}
	
	public static void updateActivityPayRecord(ActivityPayRecord record){
		ActivityPayRecordDAO dao=(ActivityPayRecordDAO) getDao(ActivityPayRecordDAOImpl.class);
		try {
			dao.updateByPrimaryKey(record);
		} catch (SQLException e) {
			log.error("update activity pay record error!",e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static ActivityPayRecord queryActivityPayRecords(int playerId,String paySign){
		ActivityPayRecord payRecord=null;
		ActivityPayRecordDAO dao=(ActivityPayRecordDAO) getDao(ActivityPayRecordDAOImpl.class);
		ActivityPayRecordExample example=new ActivityPayRecordExample();
		example.createCriteria().andPlayerIdEqualTo(playerId).andPaySignEqualTo(paySign);
		try {
			List<ActivityPayRecord> list=dao.selectByExample(example);
			if(list!=null&&list.size()>0){
				payRecord=list.get(0);
			}
		} catch (SQLException e) {
			log.error("query activity pay record error!",e);
		}
		return payRecord;
	}
	
//	public static int insertPlayerLover(PlayerLover playerLover){
//		PlayerLoverDAO dao = (PlayerLoverDAO)getDao(PlayerLoverDAOImpl.class);
//		try {
//			return dao.insert(playerLover);
//		} catch (SQLException e) {
//			log.error("insertPlayerLover error",e);
//		}
//		return -1;
//	}
//	
//	public static void updatePlayerLover(PlayerLover playerLover){
//		DBService.commit(playerLover);
//	}
//	public static List<PlayerLover> quaryAllPlayerLover(Player player){
//		PlayerLoverDAO dao = (PlayerLoverDAO)getDao(PlayerLoverDAOImpl.class);
//		PlayerLoverExample example = new PlayerLoverExample();
//		example.createCriteria().andPlayerIdEqualTo(player.getId());
//		List<PlayerLover> list = null;
//		try {
//			list = dao.selectByExample(example);
//		} catch (SQLException e) {
//			list = new ArrayList<PlayerLover>();
//			log.error("quaryPlayerLover error",e);
//		}
//		return list;
//	}
}
