package com.orange.goldgame.server.pay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.juice.orange.game.container.GameSession;
import com.orange.goldgame.core.ErrorCode;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.AppConfig;
import com.orange.goldgame.domain.PayInfo;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerActivityCpa;
import com.orange.goldgame.domain.PlayerPay;
import com.orange.goldgame.domain.PlayerPayApril;
import com.orange.goldgame.domain.PropsConfig;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.server.manager.PlayerInfoCenterManager;
import com.orange.goldgame.server.manager.SessionManger;
import com.orange.goldgame.server.service.GoldService;
import com.orange.goldgame.server.wheel.WheelFortuneService;
import com.orange.goldgame.util.DateUtil;
import com.orange.goldgame.vo.CPAActivity;

public class PayActivityService {

//	public static final String FIRSTPAYBEGINTIME = "20140401";
	private static final String OFF50_BEGIN = "20140404";
	private static final String OFF50_END = "20140413";
	private static final int OFF50_HOUR[] = {20,21};
	
	private static final String APRIL_BEGIN = "20140301";
	private static final String APRIL_END = "20140413";
	
	private static final int APRIL_MONEY = 20;
	/**
	 * 首次充值奖励
	 * @param player
	 */
	public static ErrorCode checkFirstPay(Player player,PayInfo payInfo){
		List<PlayerPay> first = BaseEngine.getInstace().getPlayerPayAction().queryAllPayForPlayer(player.getId());
		List<PlayerPay> list = new ArrayList<PlayerPay>();
		if (first != null && first.size() > 0) {
			for (PlayerPay li : first) {
				if (li.getGold() > 1) {
					list.add(li);
				}
			}
			
		}
		if (list != null && list.size() > 1) {
			return new ErrorCode(-1,"已经充值过，不赠送首充礼包！");
		}
		GoldService.addGold(player, payInfo.getMoney());
		String msg = "恭喜您成功获得“爱的第一次”";
		int begin = msg.length();
		msg = msg + payInfo.getMoney() + "宝石活动奖励！";
		String end = payInfo.getMoney() + "";
		
		PlayerInfoCenterManager.getInstance().addPlayerInfoCenter(player.getId(), true, 11, "", msg, 0xfff4dd1a, begin, begin + end.length());
		GameSession session = SessionManger.getInstance().getSession(player.getId());
		if (session != null) {
			PlayerInfoCenterManager.getInstance().noticePlayerInfoCenter(session);
		}
		return new ErrorCode(ErrorCode.SUCC,msg);
	}
	
	public static boolean isOff50(int packageId){
		if( !DateUtil.isInDateRange(OFF50_BEGIN, OFF50_END) ){
			return false;
		}
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		if (Arrays.binarySearch(OFF50_HOUR, hour) < 0) {
			return false;
		}
		AppConfig ac = ResourceCache.getInstance().getAppConfigs().get("propoff");
		String value = ac.getAppValue();
		String values[] = value.split("_");
		List<Integer> con = new ArrayList<Integer>();
		for (int i = 0; i < values.length; i++) {
			con.add(Integer.parseInt(values[i]));
		}
		Collections.sort(con);
		return con.contains(packageId);
	}
	
	public static ErrorCode off50(Player player,int packageId,int propMoney){
		boolean isIn = DateUtil.isInDateRange(OFF50_BEGIN, OFF50_END);
		if (!isIn) {
			return new ErrorCode(-1, "不在活动范围内！");
		}
		if (isOff50(packageId)) {
			return new ErrorCode(ErrorCode.SUCC,(propMoney / 2) + "");
		}else {
			return new ErrorCode(ErrorCode.SUCC,propMoney + "");
		}
//		}
	}
	
	public static ErrorCode chechApril(Player player,PayInfo payInfo){
		boolean isIn = DateUtil.isInDateRange(APRIL_BEGIN, APRIL_END);
		if (!isIn) {
			return new ErrorCode(-1, "不在活动范围内！");
		}
		
		PlayerPayApril april = player.getApril();
		if (april == null) {
			april = BaseEngine.getInstace().getPlayerPayAprilAction().quaryPlayerPayApril(player.getId());
		}
		if (april == null) {
			april = createApril(player,payInfo);
		}else {
			april.setAllGolds(april.getAllGolds() + payInfo.getMoney());
		}
		player.setApril(april);
		if (!DateUtil.isTimeDateRange(APRIL_BEGIN, APRIL_END, (int)(april.getCreateTime().getTime() / 1000))) {
			resetPlayerPayApril(april, payInfo);
		}
		int num = april.getAllGolds() / APRIL_MONEY;
		if (num > april.getRewardsNum()) {
			int amount = num - april.getRewardsNum();
			april.setRewardsNum(num);
			BaseEngine.getInstace().getPlayerPayAprilAction().updatePlayerPayApril(april);
			WheelFortuneService.addTicket(player, amount);
			PropsConfig pConfig = ResourceCache.getInstance().getPropsConfigs().get(WheelFortuneService.TICKETID);
			
			String msg = "恭喜您成功获得“欢天喜地迎四月，累计充值送大奖！”";
			int begin = msg.length();
			msg = msg + amount + "张" + pConfig.getName() + "！";
			String end = amount + "";
			
			PlayerInfoCenterManager.getInstance().addPlayerInfoCenter(player.getId(), true, 11, "", msg, 0xfff4dd1a, begin, begin + end.length());
			GameSession session = SessionManger.getInstance().getSession(player.getId());
			if (session != null) {
				PlayerInfoCenterManager.getInstance().noticePlayerInfoCenter(session);
			}
			return new ErrorCode(ErrorCode.SUCC,msg);
		}
		BaseEngine.getInstace().getPlayerPayAprilAction().updatePlayerPayApril(april);
		return new ErrorCode(-1);
	}
	
	private static PlayerPayApril createApril(Player player,PayInfo payInfo){
		PlayerPayApril april = new PlayerPayApril();
		april.setAllGolds(payInfo.getMoney());
		april.setCreateTime(new Date());
		april.setPlayerId(player.getId());
		april.setRewardsNum(0);
		int id = BaseEngine.getInstace().getPlayerPayAprilAction().insertPlayerPayApril(april);
		if (id < 0) {
			april = null;
		}else {
			april.setId(id);
		}
		return april;
	}
	
	private static void resetPlayerPayApril(PlayerPayApril april ,PayInfo payInfo){
		april.setAllGolds(payInfo.getMoney());
		april.setCreateTime(new Date());
		april.setRewardsNum(0);
		BaseEngine.getInstace().getPlayerPayAprilAction().updatePlayerPayApril(april);
	}
	
	private static final String cpa_begin = "20140430";
	private static final String cpa_end = "20140514";
	private static String cpaActivity = "50_35_3_16|100_70_5_16|200_140_10_16|500_350_25_16";
	public static Map<Integer, CPAActivity> cpaActivityMap = new HashMap<Integer, CPAActivity>();
	public static final int ACTUVUTYCPA = 1;
	static{
		String cpas[] = cpaActivity.split("\\|");
		String cpaInfo[];
		for (String s : cpas) {
			cpaInfo = s.split("_");
			CPAActivity cpa = new CPAActivity();
			cpa.setPayGolds(Integer.parseInt(cpaInfo[0]));
			cpa.setTodayMoney(Integer.parseInt(cpaInfo[1]));
			cpa.setOtherMoney(Integer.parseInt(cpaInfo[2]));
			cpa.setAllTimes(Integer.parseInt(cpaInfo[3]));
			cpaActivityMap.put(cpa.getPayGolds(), cpa);
		}
	}
	/**
	 * 检查CPA爱的基金活动
	 * @param player
	 * @param payInfo
	 * @return
	 */
	public static ErrorCode checkCPA(Player player,PayInfo payInfo){
		if(checkOver()){
			return new ErrorCode(-1,"活动已结束");
		}
		if (payInfo.getActivityType() <= 0) {
			return new ErrorCode(-1,"没有活动");
		}
		if (payInfo.getActivityType().intValue() == 1) {
			CPAActivity cpa = cpaActivityMap.get(payInfo.getMoney());
			if (cpa == null) {
				return new ErrorCode(-1,"没有活动");
			}
			//1、记录cpa活动记录
			PlayerActivityCpa pac = createPlayerActivityCpa(player,ACTUVUTYCPA,cpa);
			if (pac == null) {
				return new ErrorCode(-1,"生成活动记录错误，请联系管理员");
			}
			//2、给玩家加钱
			GoldService.addGold(player, cpa.getTodayMoney() + (payInfo.getGold() - payInfo.getMoney()));
			return new ErrorCode(ErrorCode.SUCC, "谢谢参加”爱的基金,基情来袭”活动,本次获得" + cpa.getTodayMoney() + "宝石，余下" + (cpa.getOtherMoney() * (cpa.getAllTimes() - 1)) + "钻石将于" + (cpa.getAllTimes() - 1) + "天返还完毕。");
		}
		return new ErrorCode(-1,"没有活动");
	}

	private static PlayerActivityCpa createPlayerActivityCpa(Player player,int activityType,CPAActivity activity){
		int id = -1;
		Date date = new Date();
		PlayerActivityCpa cpa = new PlayerActivityCpa();
		cpa.setCreateTime(date);
		cpa.setPlayerId(player.getId());
		cpa.setReceives(1);
		cpa.setReceiveTime(date);
		cpa.setActivityType(activityType);
		cpa.setPayMoney(activity.getPayGolds());
		id = BaseEngine.getInstace().getPlayerActivityCpaAction().insert(cpa);
		if (id < 0) {
			return null;
		}
		cpa.setId(id);
		return cpa;
		
	}
	
	/**
	 * 检查CPA活动
	 * @param player
	 * @return
	 */
	public static ErrorCode checkPlayerActivityCpas(Player player){
		List<PlayerActivityCpa> list = BaseEngine.getInstace().getPlayerActivityCpaAction().quaryByPlayerId(player.getId());
		if (list == null || list.size() <= 0) {
			return new ErrorCode(-1);
		}
		int money = 0;
		List<PlayerActivityCpa> real = new ArrayList<PlayerActivityCpa>();
		CPAActivity activity;
		for (PlayerActivityCpa pac : list) {
			if (isOverCpa(pac)) {
				continue;
			}
			real.add(pac);
			activity = cpaActivityMap.get(pac.getPayMoney());
			if (DateUtil.isSameDay(pac.getCreateTime().getTime(), System.currentTimeMillis())) {
				money += activity.getTodayMoney();
			}else {
				money += activity.getOtherMoney();
			}
			
			if (!DateUtil.isSameDay(pac.getReceiveTime().getTime(), System.currentTimeMillis())) {
				pac.setReceives(pac.getReceives() + 1);
				pac.setReceiveTime(new Date());
				BaseEngine.getInstace().getPlayerActivityCpaAction().update(pac);
				GoldService.addGold(player, activity.getOtherMoney());
			}
		}
		return new ErrorCode(ErrorCode.SUCC, "您今天共获得" + money + "宝石", real);
	}
	
	public static boolean checkOver(){
		return !DateUtil.isInDateRange(cpa_begin, cpa_end);
	}
	
	private static boolean isOverCpa(PlayerActivityCpa pac){
		CPAActivity cpa = cpaActivityMap.get(pac.getPayMoney());
		if (cpa == null) {
			return true;
		}
		long times = System.currentTimeMillis() - pac.getCreateTime().getTime();
		int day = (int)(times / (24 * 60 * 60 * 1000));
		if (day < cpa.getAllTimes()) {
			return false;
		}
		return true;
	}
}
