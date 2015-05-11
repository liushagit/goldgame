package com.orange.goldgame.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.AppConfig;
import com.orange.goldgame.domain.AppVersion;
import com.orange.goldgame.domain.GoodsConfig;
import com.orange.goldgame.domain.PackageProps;
import com.orange.goldgame.domain.PropsConfig;
import com.orange.goldgame.domain.ServerConfig;
import com.orange.goldgame.domain.StakeConfig;

/**
 * @author wuruihuang 2013.7.9
 * 遍历gameServer缓存的类
 */
public class CacheActionImpl implements CacheAction {
	
	@Override
	public AppConfig getAppConfigByKey(String key) {
		return ResourceCache.getInstance().getAppConfigs().get(key);
	}

	@Override
	public List<Integer> getStakesByAreaId(int areaId) {
		List<Integer> stakeValues = new ArrayList<Integer>();
		Map<Integer, StakeConfig> stakeConfigMap = ResourceCache.getInstance().getStakeConfigs();
		for(Map.Entry<Integer, StakeConfig> enty: stakeConfigMap.entrySet()){
			StakeConfig  stakeConfig = enty.getValue();
			if(areaId==stakeConfig.getAreaId()){
				stakeValues.add(stakeConfig.getStakeNumber());
			}
		}
		return stakeValues;
	}

	@Override
	public PropsConfig getPropsConfigById(int propsConfigId) {
		return ResourceCache.getInstance().getPropsConfigs().get(propsConfigId);
	}
	
	@Override
	public PackageProps getPackagePropsById(int packagePropsId) {
		return ResourceCache.getInstance().getPackageProps().get(packagePropsId);
	}

	@Override
	public GoodsConfig getGoodsConfigById(int goodsConfigId) {
		return ResourceCache.getInstance().getGoodsConfigs().get(goodsConfigId);
	}

	@Override
	public ServerConfig getServerConfigById(int serverConfigId) {
		return null;
	}

	@Override
	public AppVersion getAppVersion(String appId, String versionId) {
		AppVersion appInfo = null;
		Map<Integer, AppVersion> appVersionMap = ResourceCache.getInstance().getAppVersions();
		for(Map.Entry<Integer, AppVersion> enty: appVersionMap.entrySet()){
			AppVersion  appVersion = enty.getValue();
			if(appVersion.getAppId().equals(appId)&&appVersion.getVersion().equals(versionId)){
				appInfo = appVersion;
			}
		}
		return appInfo;
	}

	@Override
	public List<GoodsConfig> getAllGoodsConfig() {
		Map<Integer, GoodsConfig> goodsConfigMap = ResourceCache.getInstance().getGoodsConfigs();
		List<GoodsConfig> goodsConfigList = new ArrayList<GoodsConfig>();
		for(Map.Entry<Integer, GoodsConfig> gc :goodsConfigMap.entrySet()){
			goodsConfigList.add(gc.getValue());
		}
		return goodsConfigList;
	}

	@Override
	public List<PropsConfig> getAllPropsConfig(int key) {
		Map<Integer, PropsConfig> propsConfigMap = ResourceCache.getInstance().getPropsConfigs();
		List<PropsConfig> propsConfigList = new ArrayList<PropsConfig>();
		for(Map.Entry<Integer, PropsConfig> pc :propsConfigMap.entrySet()){
			PropsConfig pcg = pc.getValue();
			if(pcg.getPropsType()==key){
				propsConfigList.add(pcg);
			}
		}
		return propsConfigList;
	}

}
