package com.orange.goldgame.action;

import java.util.List;

import com.orange.goldgame.domain.AppConfig;
import com.orange.goldgame.domain.AppVersion;
import com.orange.goldgame.domain.GoodsConfig;
import com.orange.goldgame.domain.PackageProps;
import com.orange.goldgame.domain.PropsConfig;
import com.orange.goldgame.domain.ServerConfig;

public interface CacheAction {
	
//	/**获取登录奖励参数*/
//	String getLoginAward();
//	/**获取税率*/
//	int getTaxRate();
//	/**获取每局游戏可以开始的准备人数*/
//	int getReadyNumber();
//	/**获取准备超时的限定时间*/
//	int getReadyTimeout();
//	/**获取比赛场中开赛时限定的报名人数*/
//	int getSignupNumber();
//	/**获取出牌的超时的限定时间*/
//	int getPlayCardTimeout();
//	/**获取可以比牌的回合数*/
//	int getComparedCardRoundNumber();
//	/**获取每局游戏可进行的回合数*/
//	int getRoundNumber();
//	
	
	/**获取指定系统配置信息*/
	AppConfig getAppConfigByKey(String key);
	ServerConfig getServerConfigById(int serverConfigId);
	AppVersion getAppVersion(String appId,String versionId);
	
	/**获取指定指定场次的下注筹码*/
	List<Integer> getStakesByAreaId(int areaId);
	
	/**获取所有的礼物道具*/
	List<PropsConfig> getAllPropsConfig(int key);

	/**获取指定的道具*/
	PropsConfig getPropsConfigById(int propsConfigId);
	
	/**获取指定指定出售的道具*/
	PackageProps getPackagePropsById(int packagePropsId);
	
	/**获取指定的兑换实物*/
	GoodsConfig getGoodsConfigById(int goodsConfigId);
	/**获取所有的兑换实物*/
	List<GoodsConfig> getAllGoodsConfig();
}
