package com.orange.goldgame.server.pay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.cache.action.PropCacheAction;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.core.ErrorCode;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.AppConfig;
import com.orange.goldgame.domain.PayInfo;
import com.orange.goldgame.domain.PayOrder;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerPay;
import com.orange.goldgame.domain.PlayerProps;
import com.orange.goldgame.domain.PropsConfig;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.server.manager.SessionManger;
import com.orange.goldgame.server.service.GoldService;
import com.orange.goldgame.server.service.PlayerService;
import com.orange.goldgame.server.wheel.WheelFortuneService;
import com.orange.goldgame.util.EDUtil;
import com.orange.goldgame.util.MD5;
import com.orange.goldgame.util.OrangeMD5Util;
import com.orange.goldgame.vo.InPayListInfoVo;
import com.orange.goldgame.vo.InPayOrderVo;
import com.orange.goldgame.vo.InPlayerPayVo;
import com.orange.goldgame.vo.InWheelListVo;

public class PayNewService {

	private static final int undone = 0;
	private static final int done = 1;

	private static final int sequencePlayerId = 1;
	private static final int sequencePayId = 2;
	private static final int sequenceOrderId = 3;
	private static final int sequencePayType = 4;
	private static final int sequencePayTime = 5;
	
	private static List<Integer> sequences = new ArrayList<Integer>();
	static{
		sequences.add(sequencePlayerId);
		sequences.add(sequencePayId);
		sequences.add(sequenceOrderId);
		sequences.add(sequencePayType);
		sequences.add(sequencePayTime);
	}
	
	public static ErrorCode create(Player player, InPayOrderVo vo) {

		ErrorCode res = checkNewOrder(vo);
		
		if (res.getStatus() != ErrorCode.SUCC) {
			return res;
		}
		PayInfo pi = PayInfoService.getPayInfo(vo.getbPayId());
		res = checkWheel(player, pi);
		if (res.getStatus() != ErrorCode.SUCC) {
			return res;
		}
		if (pi == null) {
			return new ErrorCode(-1, "订单错误，请联系客服！");
		}
		PayOrder order = createPayOrder(player, pi.getId());
		if (order == null) {
			return new ErrorCode(-1, "生成订单失败");
		}
		return new ErrorCode(ErrorCode.SUCC, order);
	}

	private static ErrorCode checkNewOrder(InPayOrderVo vo) {
		StringBuffer sb = new StringBuffer();
		sb.append(vo.getaPlayerID());
		sb.append(vo.getbPayId());
		sb.append(vo.getcPayName());
		sb.append(vo.getdTime());
		sb.append(vo.getePayType());
		sb.append(vo.getfPayPrice());

		String newSign = MD5.encode(sb.toString());

		if (!newSign.equals(vo.getgSign())) {
			return new ErrorCode(-1, "订单错误");
		}
		return new ErrorCode(ErrorCode.SUCC);
	}
	private static final int MAX_NUM = 999;
	private static ErrorCode checkWheel(Player player,PayInfo pi) {
		if(pi.getPayProType().intValue() == WheelFortuneService.TICKETID){
			PlayerProps pp = PropCacheAction.getplayerPropByPropid(player, WheelFortuneService.TICKETID);
			if(pp != null){
				PropsConfig pc = ResourceCache.getInstance().getPropsConfigs().get(pp.getPropsConfigId());
				
				if(pp != null && pp.getNumber() + pi.getGold() >= MAX_NUM) return new ErrorCode(-2,"您的" + pc.getName()+"已超过" + MAX_NUM + "不能继续购买！");
			}
		}
		return new ErrorCode(ErrorCode.SUCC);
	}
	/**
	 * 生成本次玩家充值时需要的加密顺序
	 * @param player
	 * @return
	 */
	public static String getMd5KeySequence(Player player,String orderId){
//		Collections.shuffle(sequences);
		Collections.sort(sequences);
		StringBuffer sb = new StringBuffer();
		for (int i : sequences) {
			sb.append(i).append("_");
		}
		sb.delete(sb.length() -1, sb.length());
		player.getSequence().put(orderId, sb.toString());
		//TODO 对sb加密
		return EDUtil.encrypt(sb.toString());
	}
	

	private static PayOrder createPayOrder(Player player, int payInfoId) {
		PayOrder order = new PayOrder();
		order.setPayInfoId(payInfoId);
		order.setPayStatus(undone);
		order.setPlayerId(player.getId());
		int id = BaseEngine.getInstace().getPayOrderAction()
				.insertPayOrder(order);
		if (id > 0) {
			order.setId(id);
		} else {
			order = null;
		}
		return order;
	}
	public static ErrorCode pay(Player player, int orderId, InPlayerPayVo vo) {
		//支付宝，话费充值，不走客户端
		return pay(player, orderId, vo, true);
	}
	
	/**
	 * 话费
	 * 服务端通知充值
	 * @param orderId
	 * @return
	 */
	public static ErrorCode payServer(int orderId){
		PayOrder order = BaseEngine.getInstace().getPayOrderAction()
				.loadPayOrderById(orderId);
		if (order == null) {
			return new ErrorCode(-1, "订单不存在！");
		}
		Player player = PlayerService.getSimplePlayerByPlayerId(order.getPlayerId());
		if (player == null) {
			return new ErrorCode(-1, "玩家不存在！");
		}
		AppConfig ac = ResourceCache.getInstance().getAppConfigs().get("self");
		String self[];
		int selfId[] = null;
		if (ac != null) {
			self = ac.getAppValue().split("_");
			selfId = new int[self.length];
			for (int i = 0; i < self.length; i++) {
				selfId[i] = Integer.parseInt(self[i]);
			}
		}
		if (selfId != null && selfId.length > 0) {
			if (Arrays.binarySearch(selfId, player.getId()) >= 0) {
				return pay(player, order, null, false,true);
			}else {
				return new ErrorCode(-1,"充值错误");
			}
		}else {
			return new ErrorCode(-1,"充值错误");
		}
	}
	
	/**
	 * 话费，支付宝不客户端
	 * @param player
	 * @param orderId
	 * @param vo
	 * @param check
	 * @return
	 */
	private static ErrorCode pay(Player player, int orderId, InPlayerPayVo vo,boolean check) {
		PayOrder order = BaseEngine.getInstace().getPayOrderAction()
				.loadPayOrderById(orderId);
		if (order == null) {
			return new ErrorCode(-1, "订单不存在！");
		}
		PayInfo info = ResourceCache.getInstance().getPayinfoById(
				order.getPayInfoId());
		
		// TODO 支付宝，话费充值，不走本方法
		return pay(player, order, vo, check,false);
	}
	public static ErrorCode pay(Player player, PayOrder order, InPlayerPayVo vo,boolean check,boolean isServer) {
		if (check) {
			ErrorCode res = checkSign(vo,player);
			if (res.getStatus() != ErrorCode.SUCC) {
				return res;
			}
		}

		// 1、更新支付订单状态
		if (order == null) {
			return new ErrorCode(-1, "请确认充值订单是否正确");
		}
		if (order.getPayStatus().intValue() == done) {
			return new ErrorCode(-1, "该订单已处理！");
		}
		order.setPayStatus(done);
		BaseEngine.getInstace().getPayOrderAction().updatePayOrder(order);
		PayInfo info = ResourceCache.getInstance().getPayinfoById(
				order.getPayInfoId());
		// 2、给玩家加钱
		PlayerPay pay = createPay(player, info, order.getId());
		//大转盘
		ErrorCode resFirst = null;
		ErrorCode resApril = null;
		ErrorCode cpaActivity = PayActivityService.checkCPA(player, info);
		if(info.getPayProType().intValue() == WheelFortuneService.TICKETID){//买抽奖卷
			PropCacheAction.addProp(player, WheelFortuneService.TICKETID, info.getGold());
			PropsConfig pc = ResourceCache.getInstance().getPropsConfigs().get(WheelFortuneService.TICKETID);
			return new ErrorCode(Constants.FULL_WHEEL,"成功购买" + pc.getName() + "*" + info.getGold());
		}else if(info.getId().intValue() == WheelFortuneService.ONE_YUAN_YIDONG 
				|| info.getId().intValue() == WheelFortuneService.ONE_YUAN_DIANXIN
				|| info.getId().intValue() == WheelFortuneService.ONE_YUAN_LIANTONG
				){//抽奖
			if (!isServer) {
				ErrorCode res_lottery = WheelFortuneService.lottery(player, 2);
				WheelFortuneService.sendResult(player, res_lottery, SessionManger.getInstance().getSession(player.getId()));
			}
		}else {
			
			if (cpaActivity.getStatus() != ErrorCode.SUCC) {
				GoldService.addGold(player, info.getGold());
			}
//			GoldService.addGold(player, info.getGold());
			resFirst = PayActivityService.checkFirstPay(player,info);
			resApril = PayActivityService.chechApril(player, info);
		}

		StringBuffer sb = new StringBuffer();
		if (resFirst != null && resFirst.getStatus() == ErrorCode.SUCC) {
			sb.append(resFirst.getMsg()).append("\n");
		}
		if (resApril != null && resApril.getStatus() == ErrorCode.SUCC) {
			sb.append(resApril.getMsg()).append("\n");
		}
		if (cpaActivity != null && cpaActivity.getStatus() == ErrorCode.SUCC) {
			sb.append(cpaActivity.getMsg());
		}
		return new ErrorCode(ErrorCode.SUCC, sb.toString() , info);
	}

	private static ErrorCode checkSign(InPlayerPayVo vo,Player player) {

		String sequence = player.getSequence().get(vo.getcOrderId());
		if (sequence == null) {
//			return new ErrorCode(-1, "充值验证错误");
			sequence = "1_2_3_4_5";
		}
		StringBuffer sb = new StringBuffer();
		String s[] = sequence.split("_");
		for (int i = 0; i < s.length; i++) {
			if (sequencePlayerId == Integer.parseInt(s[i])) {
				sb.append(vo.getaPlayerId());
				continue;
			}
			if (sequencePayId == Integer.parseInt(s[i])) {
				sb.append(vo.getbPayId());
				continue;
			}
			if (sequenceOrderId == Integer.parseInt(s[i])) {
				sb.append(vo.getcOrderId());
				continue;
			}
			if (sequencePayType == Integer.parseInt(s[i])) {
				sb.append(vo.getePayType());
				continue;
			}
			if (sequencePayTime == Integer.parseInt(s[i])) {
				sb.append(vo.getfPayTime());
				continue;
			}
		}

		String newSign = MD5.encode(sb.toString());
		String userSign = OrangeMD5Util.getCheckInfo1(vo.getgUserName(), vo.gethUserPassword(), "", "", "");
		if (!newSign.equals(vo.getdSign())) {
			return new ErrorCode(-1, "充值验证错误");
		}
//		if (!userSign.equals(vo.getiUserSign())) {
//			return new ErrorCode(-1, "充值验证错误");
//		}
//		
		return new ErrorCode(ErrorCode.SUCC);
		
	}

	private static PlayerPay createPay(Player player, PayInfo info, int orderId) {
		PlayerPay playerPay = new PlayerPay();
		playerPay.setOrderId(orderId + "");
		playerPay.setPayTime(new Date());
		playerPay.setPlayerId(player.getId());
		playerPay.setPayInfoId(info.getPayId());
		playerPay.setGold(info.getMoney());
		playerPay.setPayType(info.getPayId());
		playerPay.setTradeId("");
		playerPay.setBuyId("");
		int id = BaseEngine.getInstace().getPlayerPayAction()
				.insertPlayerPay(playerPay);
		playerPay.setId(id);
		return playerPay;
	}
	
	public static final int PHONE_TYPE_DIANXIN = 3;
	public static final int PHONE_TYPE_LIANTONG = 1;
	public static final int PHONE_TYPE_YIDONG = 2;
	
	public static final int ZFB = 101;
	public static final int CFT = 401;
	public static final int ZFB_WHEEL = 504;
	public static final int CFT_WHEEL = 505;
	public static final int PHONE_WHEEL_YIDONG = 514;
	public static final int PHONE_WHEEL_LIANTONG = 515;
	public static final int PHONE_WHEEL_DIANXIN = 516;
	
	public static final int ZFB_CFT[] = {ZFB,CFT};
	public static final int ZFB_CFT_WHEEL[] = {ZFB_WHEEL,CFT_WHEEL,PHONE_WHEEL_YIDONG,PHONE_WHEEL_LIANTONG,PHONE_WHEEL_DIANXIN};
	public static final int PHONE_WHEEL[] = {PHONE_WHEEL_YIDONG,PHONE_WHEEL_LIANTONG,PHONE_WHEEL_DIANXIN};
	public static Map<Integer, Integer> PHONETYPE = new HashMap<Integer, Integer>();
	static{
		PHONETYPE.put(PHONE_TYPE_DIANXIN,203);
		PHONETYPE.put(PHONE_TYPE_LIANTONG,201);
		PHONETYPE.put(PHONE_TYPE_YIDONG,126);
	}
	
	public static Map<Integer, Integer> PHONETYPEWHEEL = new HashMap<Integer, Integer>();
	static{
		PHONETYPEWHEEL.put(PHONE_TYPE_DIANXIN,516);
		PHONETYPEWHEEL.put(PHONE_TYPE_LIANTONG,515);
		PHONETYPEWHEEL.put(PHONE_TYPE_YIDONG,514);
	}
	/**
	 * 根据手机类型获取充值列表
	 * @param phoneType
	 * @return
	 */
	public static List<PayInfo> getPayInfos(int phoneType){
		return getPayInfos(phoneType,0,false);
	}
	/**
	 * 根据手机类型获取充值列表
	 * @param phoneType
	 * @return
	 */
	public static List<PayInfo> getPayInfos(int phoneType,int propType,boolean isPhone){
		
		Map<Integer, Map<Integer, Map<Integer, PayInfo>>> payinfos = ResourceCache.getInstance().getPayinfos();
		List<PayInfo> list = new ArrayList<PayInfo>();
		
		for (Map<Integer, Map<Integer, PayInfo>> map : payinfos.values()) {
			for (Map<Integer, PayInfo> pis : map.values()) {
				for (PayInfo pi : pis.values()) {
					if(propType > 0){
						switch (propType) {
						case WheelFortuneService.TICKETID:
							if(isPhone){
								if (Arrays.binarySearch(PHONE_WHEEL, pi.getPayType().intValue()) >= 0) {
									list.add(pi);
								}
							}else{
								if (Arrays.binarySearch(ZFB_CFT_WHEEL, pi.getPayType().intValue()) >= 0) {
									list.add(pi);
								}
								
							}
							break;

						default:
							break;
						}
					}else{
						if (pi.getPayType().intValue() == PHONETYPE.get(phoneType)) {
							list.add(pi);
						}
						if (Arrays.binarySearch(ZFB_CFT, pi.getPayType().intValue()) >= 0) {
							list.add(pi);
						}
					}
				}
			}
		}
		
		return list;
	}
	
	public static final int PHONELIST = 2;
	public static final int ZFBLIST = 0;
	public static final int CFTLIST = 1;
	/**
	 * 
	 * @param vo
	 * @param type 0：话费，1：支付宝，2：财付通
	 * @return
	 */
	public static List<PayInfo> getPayInfo(InPayListInfoVo vo,int type){
		PayInfo pi = PayInfoService.getPayInfo(vo.getbPayId());
		List<PayInfo> list = getPayInfos(vo.getcPhoneType(),pi.getPayProType(),vo.getdIsFull() > 0);
		List<PayInfo> realList = new ArrayList<PayInfo>();
		for (PayInfo pis :list) {
			if(pi.getPayProType().intValue() == WheelFortuneService.TICKETID){
				if(pis.getPayProType().intValue() != WheelFortuneService.TICKETID){
					continue;
				}
				if (pis.getMoney().intValue() == pi.getMoney().intValue()) {
					if (type == PHONELIST) {
						if (pis.getPayType().intValue() == PHONETYPEWHEEL.get(vo.getcPhoneType())) {
							realList.add(pis);
						}
					}
					if (type == ZFBLIST) {
						if (pis.getPayType().intValue() == ZFB_WHEEL) {
							realList.add(pis);
						}
					}
					if (type == CFTLIST) {
						if (pis.getPayType().intValue() == CFT_WHEEL) {
							realList.add(pis);
						}
					}
					
				}
			}else{
				if (pis.getMoney().intValue() == pi.getMoney().intValue()) {
					if (type == PHONELIST) {
						if (pis.getPayType().intValue() == PHONETYPE.get(vo.getcPhoneType())) {
							realList.add(pis);
						}
					}
					if (type == ZFBLIST) {
						if (pis.getPayType().intValue() == ZFB) {
							realList.add(pis);
						}
					}
					if (type == CFTLIST) {
						if (pis.getPayType().intValue() == CFT) {
							realList.add(pis);
						}
					}
					
				}
			}
		}
		return realList;
	}
	
	private static final int alipayType[] = {101,504};
	public static final String NOTIFY_URL_ALIPAY = "http://mmzjh.wiorange.com:9999/alipay";
	public static final String NOTIFY_URL_DEFULATE = "http://notifurl_mm";
	public static String getURL(PayInfo pi){
		if (Arrays.binarySearch(alipayType, pi.getPayType().intValue()) >= 0) {
			return NOTIFY_URL_ALIPAY;
		} else {
			return NOTIFY_URL_DEFULATE;
		}
	}

	
	
	/**
	 * 获取抽奖卷可购买列表
	 * @param phoneType
	 * @return
	 */
	public static List<PayInfo> getWheelList(InWheelListVo vo){
		
		Map<Integer, Map<Integer, Map<Integer, PayInfo>>> payinfos = ResourceCache.getInstance().getPayinfos();
		List<PayInfo> list = new ArrayList<PayInfo>();
		
		for (Map<Integer, Map<Integer, PayInfo>> map : payinfos.values()) {
			for (Map<Integer, PayInfo> pis : map.values()) {
				for (PayInfo pi : pis.values()) {
					if(vo.getbIsFull() > 0){
						if (pi.getPayProType().intValue() == WheelFortuneService.TICKETID 
								&& Arrays.binarySearch(PHONE_WHEEL, pi.getPayType()) < 0) {
							list.add(pi);
						}
						
					}else{
						if (pi.getPayProType().intValue() == WheelFortuneService.TICKETID
								&& Arrays.binarySearch(PHONE_WHEEL, pi.getPayType()) >= 0) {
							list.add(pi);
						}
					}
				}
			}
		}
		return list;
	}
	
	public static OutputMessage getPayListMessage(List<PayInfo> listAll){
		Map<Integer, PayInfo> infos = new HashMap<Integer, PayInfo>();
		for (PayInfo pi : listAll) {
			infos.put(pi.getMoney(), pi);
		}
		List<PayInfo> list = new ArrayList<PayInfo>();
		list.addAll(infos.values());
		Collections.sort(list, new Comparator<PayInfo>() {

			@Override
			public int compare(PayInfo o1, PayInfo o2) {
				return o1.getMoney() - o2.getMoney();
			}
		});
		OutputMessage om = new OutputMessage();
		if (list == null || list.size() <= 0) {
			om.putShort((short)0);
		}else {
			om.putShort((short)list.size());
			for (PayInfo pi : list) {
				om.putByte((byte)pi.getId().intValue());
				om.putString(pi.getPayId());
				om.putString(pi.getName());
				om.putInt(pi.getMoney());
				om.putInt(pi.getGold() - pi.getMoney());
				if(PayActivityService.checkOver()){
					om.putByte((byte)0);
				}else{
					om.putByte((byte)pi.getActivityType().intValue());
				}
			}
		}
		return om;
	}
}
