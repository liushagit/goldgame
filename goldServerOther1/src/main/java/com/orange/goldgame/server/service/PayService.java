package com.orange.goldgame.server.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.juice.orange.game.container.GameSession;
import com.juice.orange.game.exception.JuiceException;
import com.juice.orange.game.handler.SocketRequest;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.log.LoggerFactory;
import com.juice.orange.game.log.LoggerName;
import com.juice.orange.game.util.InputMessage;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.alipay.RSASignature;
import com.orange.goldgame.cache.action.PropCacheAction;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.core.ErrorCode;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.ActivityPayRecord;
import com.orange.goldgame.domain.PayChannelConfig;
import com.orange.goldgame.domain.PayInfo;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerPay;
import com.orange.goldgame.domain.PropsConfig;
import com.orange.goldgame.domain.SealInfo;
import com.orange.goldgame.domain.ZfbPayRecord;
import com.orange.goldgame.exception.GoldException;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.server.manager.PlayerInfoCenterManager;
import com.orange.goldgame.server.manager.SessionManger;
import com.orange.goldgame.server.pay.DianJinPayService;
import com.orange.goldgame.server.pay.MMPayService;
import com.orange.goldgame.server.pay.PayInfoService;
import com.orange.goldgame.util.DateUtil;
import com.orange.goldgame.util.KeysUtil;
import com.orange.goldgame.util.MD5;
import com.orange.goldgame.util.XmlUtil;
import com.orange.goldgame.vo.ActivityConfigVo;
import com.orange.goldgame.vo.Alipay;

/**
 * 支付
 * @author guojiang
 *
 */
public class PayService {

	private final int alipayType = 101;//支付宝
	private final int caifutong = 401;//支付宝
	private final int dianJin = 601;//点金
	private final String wait_pay = "WAIT_BUYER_PAY";
	private final String finish_pay = "TRADE_FINISHED";
    private Logger log = LoggerFactory.getLogger(PayService.class);
    private Logger log_gold = LoggerFactory.getLogger(LoggerName.GOLD);
    
	public void getPayConfig(SocketRequest request, SocketResponse response) 
			throws JuiceException {
		//接收机器码和渠道号和版本号
		InputMessage msg = request.getInputMessage();
		int player_id  = msg.getInt();//player_id使用不到
		Player player = PlayerService.getPlayer(player_id, request.getSession());
		if(player == null){
		    return;
		}
		msg.getByte();
		int operator = msg.getByte();
		byte phoneType=0;
		try{
			phoneType=msg.getByte();   //新增	 是否MM		
		}catch(Exception e){
			log.debug("mm handle ing .");
		}
		byte MMtype=0;
		try{
			//新增 MM类型 0为普通MM 1为土豪 2为真人 3为欢乐 4为霸王	
			MMtype=msg.getByte();   	
		}catch(Exception e){
			log.debug("mm type handle ing .");
		}
		
		List<PayInfo> operatorList = new ArrayList<PayInfo>();
		List<PayInfo> paybayList = new ArrayList<PayInfo>();
		
		PayChannelConfig config = ResourceCache.getInstance()
                .getPayChannelConfig().get(operator);
		
		int showType = 0;
		boolean isShow=false;
		if(config != null){
		    showType = config.getType();
		    if(config.getIsShow()==Constants.COMMON_TYPE_ONE){
		    	isShow=true;
		    }
		}
		log.debug("operator:"+operator+",showType:"+showType);
		if(phoneType==Constants.COMMON_TYPE_ONE&&operator==2){
			operator=getMMPayType(MMtype);
		}
//		if(phoneType==Constants.COMMON_TYPE_ONE&&operator==127){
//			operator=2;
//		}
		//话费list

		//话费list
		operatorList = getPayInfoByOperator(operator);
		OutputMessage om = new OutputMessage();
		om.putShort(Short.parseShort(showType+""));
		om.putString(Constants.VoucherHelpInfo);
		
		short size = (short)operatorList.size();
		om.putShort(size);
//		om.putShort((short)0);
		for (PayInfo pi : operatorList) {
			om.putByte((byte)pi.getId().intValue());
			om.putString(pi.getPayId());
			om.putString(pi.getName());
			om.putInt(pi.getMoney());
			om.putInt(pi.getGold()-pi.getMoney());
			om.putString(pi.getPayId());
			om.putString(getInfoXml(pi, player));
			om.putString(NOTIFY_URL_MM);
		}
		
		//其他类型支付
		 Map<Integer, Map<Integer, Map<Integer, PayInfo>>> map = ResourceCache.getInstance().getPayinfos();
		 List<Integer> keySet = new ArrayList<Integer>();
		 for (int key : map.keySet()) {
			if (key != 1 && key != 2 && key != 3 && key != 127) {
				keySet.add(key);
			}
		}
		 Collections.sort(keySet);
		 for (int key : keySet) {
			 Map<Integer, Map<Integer, PayInfo>> phone =  map.get(key);
			 for (Map<Integer, PayInfo> map2 : phone.values()) {
				 paybayList = getPayInfoByPaybay(map2);
				 size = (short) paybayList.size();
//				 if (key == 4 || key == 0) {
//					 om.putShort((short)0);
//					 continue;
//				}
				 om.putShort(size);
				for (PayInfo pi : paybayList) {
					om.putByte((byte) pi.getId().intValue());
					om.putString(pi.getPayId());
					om.putString(pi.getName());
					om.putInt(pi.getMoney());
					om.putInt(pi.getGold() - pi.getMoney());
					if (pi.getPayType().intValue() == alipayType) {
						om.putString(getInfoFoSlipay(pi, player_id));
						om.putString(NOTIFY_URL);
					}else if (pi.getPayType().intValue() == dianJin) {
						om.putString(DianJinPayService.getJinDianInfo(pi, player_id));
						om.putString(NOTIFY_DIANJIN_URL);
					}else {
						om.putString(getInfoXml(pi, player));
						om.putString(NOTIFY_BAIFUBAO);
					}
				}
			 }
		}
		
		
		response.sendMessage(Protocol.RESPONSE_PAY_INFO, om);
	}
	private int getMMPayType(byte mMtype) {
		int MM_PAY_TYPE=127;
		switch (mMtype) {
		case Constants.MM_COMMON:	
			MM_PAY_TYPE=Constants.MM_COMMON_NUM;
			break;
		case Constants.MM_TUHAO:	
			MM_PAY_TYPE=Constants.MM_TUHAO_NUM;
			break;
		case Constants.MM_ZHENREN:		
			MM_PAY_TYPE=Constants.MM_ZHENREN_NUM;
			break;
		case Constants.MM_HUANLE:	
			MM_PAY_TYPE=Constants.MM_HUANLE_NUM;
			break;
		case Constants.MM_BAWANG:	
			MM_PAY_TYPE=Constants.MM_BAWANG_NUM;
			break;
		case Constants.MM_MOMO:	
			MM_PAY_TYPE=Constants.MM_MOMO_NUM;
			break;
		case Constants.MM_POKEWAGN:	
			MM_PAY_TYPE=Constants.MM_POKEWAGN_NUM;
			break;
		case Constants.MM_BAOZIWANG:	
			MM_PAY_TYPE=Constants.MM_BAOZIWANG_NUM;
			break;
		case Constants.MM_FENGKUANG_BAOZIWANG:	
			MM_PAY_TYPE=Constants.MM_FENGKUANG_BAOZIWANG_NUM;
			break;
		case Constants.MM_TUHAO_RUO:	
			MM_PAY_TYPE=Constants.MM_TUHAO_NUM_RUO;
			break;
		case Constants.MM_HUANLE_RUO:	
			MM_PAY_TYPE=Constants.MM_HUANLE_NUM_RUO;
			break;
		case Constants.MM_ZHENREN_RUO:	
			MM_PAY_TYPE=Constants.MM_ZHENREN_NUM_RUO;
			break;
		default:
//			break;
		}
		return MM_PAY_TYPE;
	}
	public void pay(SocketRequest request, SocketResponse response) 
			throws JuiceException {
		InputMessage msg = request.getInputMessage();
		ErrorCode res = checkPayInfo(msg);
		pay(res, request, response);
//		Player player = null;
//		if (res.getStatus() != ErrorCode.SUCC) {
//			BaseServer.sendErrorMsg(response, (short)1, res.getMsg());
//		}else {
//			PlayerPay pay = (PlayerPay)res.getObject();
//			player = PlayerService.getPlayer(pay.getPlayerId(), request.getSession());
//			PlayerPay pp = BaseEngine.getInstace().getPlayerPayAction().quaryPlayerPay(pay.getPlayerId(), pay.getOrderId());
//			if (pp != null) {
//				BaseServer.sendErrorMsg(response, (short)1, "该充值已经处理，不需要再次处理！");
//				return;
//			}else {
//				int result = 0;
//				int times = 0;
//				do {
//					result = BaseEngine.getInstace().getPlayerPayAction().insertPlayerPay(pay);
//					times ++;
//				} while (result < 0 && times < 3);
//			}
//			addGolds(player, pay);
//			
//			//添加信息
//			PlayerInfoCenterManager.getInstance().addPlayerInfoCenter(player.getId(), true,Constants.PAY,"","充值成功，您购买了"+pay.getGold()+"宝石！");
//	       
//			// 检查活动
//			checkActivity(player, pay);
//			
//			OutputMessage om = new OutputMessage();
//			om.putInt(player.getId());
//			om.putInt(player.getGolds());
//			om.putInt(GoldService.getLeftCopper(player));
//			om.putInt(player.getCopper());
//			response.sendMessage(Protocol.RESPONSE_PAY_SUCC, om);
//		}
		
	}
	
	private void pay(ErrorCode res,SocketRequest request,SocketResponse response){
		Player player = null;
		if (res.getStatus() != ErrorCode.SUCC) {
			BaseServer.sendErrorMsg(response, (short)1, res.getMsg());
		}else {
			PlayerPay pay = (PlayerPay)res.getObject();
			player = PlayerService.getPlayer(pay.getPlayerId(), request.getSession());
			PlayerPay pp = BaseEngine.getInstace().getPlayerPayAction().quaryPlayerPay(pay.getPlayerId(), pay.getOrderId());
			if (pp != null) {
				BaseServer.sendErrorMsg(response, (short)1, "该充值已经处理，不需要再次处理！");
				return;
			}else {
				int result = 0;
				int times = 0;
				do {
					result = BaseEngine.getInstace().getPlayerPayAction().insertPlayerPay(pay);
					times ++;
				} while (result < 0 && times < 3);
			}
			addGolds(player, pay);
			
			//添加信息
			PlayerInfoCenterManager.getInstance().addPlayerInfoCenter(player.getId(), true,Constants.PAY,"","充值成功，您购买了"+pay.getGold()+"宝石！");
	       
			// 检查活动
			checkActivity(player, pay);
			
			OutputMessage om = new OutputMessage();
			om.putInt(player.getId());
			om.putInt(player.getGolds());
			om.putInt(GoldService.getLeftCopper(player));
			om.putInt(player.getCopper());
			response.sendMessage(Protocol.RESPONSE_PAY_SUCC, om);
		}
	}
	
	private void addGolds(Player player , PlayerPay pay){
	    if(isExistPayInfo(pay)!=null){
	        pay.setGold(isExistPayInfo(pay).getGold());
	    }
	    PayInfo pi = PayInfoService.getPayInfo(pay.getPayInfoId());
		GoldService.addGold(player, pi.getGold());
		//关键字|玩家ID|玩家付费金钱|获得的宝石数
        log_gold.info("pay_add_gold|" + player.getId()+"|"+pi.getMoney()+"|"+pay.getGold());
	}
	
	private ErrorCode checkPayInfo(InputMessage msg){
		int playerId = msg.getInt();
		String payId = msg.getUTF();
		String timeStamp = msg.getUTF();
		String sign = msg.getUTF();
		Byte payType = msg.getByte();
		String tradeId = "";
		String buyId = "";
		try {
			tradeId = msg.getUTF();
			buyId = msg.getUTF();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		StringBuffer sb = new StringBuffer();
		sb.append(playerId).append(payId).append(timeStamp).append(tradeId).append(buyId);
		String newSign = MD5.encode(sb.toString());
		if (newSign.equals(sign)) {
		    PlayerPay playerPay = new PlayerPay();
            playerPay.setOrderId(timeStamp);
            playerPay.setPayTime(new Date());
            playerPay.setPlayerId(playerId);
            PayInfo pi = PayInfoService.getPayInfo(payId);
            if (pi.getPayType().intValue() == alipayType) {
            	 return new ErrorCode(-2, "");
			}
//            if (pi.getPayType().intValue() == caifutong) {
//           	 return new ErrorCode(-2, "");
//			}
            int sum = BaseEngine.getInstace().getPlayerPayAction().getSumToday(playerId);
            if (pi.getPayType().intValue() == caifutong && sum >= 500 || pi.getGold() >= 500) {
            	Player player = PlayerService.getSimplePlayerByPlayerId(playerId);
            	player.setStatus(Constants.PLAYER_STATUS_SEAL);
     	        BaseEngine.getInstace().getPlayerActionIpml().modifyPlayer(player);
     	        
     	        SealInfo info = new SealInfo();
     	        info.setPlayerId(playerId);
     	        info.setSealReason("财付通刷宝石封号" + pi.getPayType());
     	        info.setSealTime(new Date());
     	        BaseEngine.getInstace().getSealInfoAction().insert(info);
     	       return new ErrorCode(-2, "");
			}
            
            if (pi.getPayType().intValue() != caifutong && pi.getPayType().intValue() != alipayType && sum > 600) {
            	Player player = PlayerService.getSimplePlayerByPlayerId(playerId);
            	player.setStatus(Constants.PLAYER_STATUS_SEAL);
     	        BaseEngine.getInstace().getPlayerActionIpml().modifyPlayer(player);
     	        
     	        SealInfo info = new SealInfo();
     	        info.setPlayerId(playerId);
     	        info.setSealReason("话费" + pi.getPayType());
     	        info.setSealTime(new Date());
     	        BaseEngine.getInstace().getSealInfoAction().insert(info);
     	       return new ErrorCode(-2, "");
			}
            
            playerPay.setPayInfoId(pi.getPayId());
            playerPay.setGold(pi.getMoney());
            playerPay.setPayType(payType+"");
            playerPay.setTradeId(tradeId);
            playerPay.setBuyId(buyId);
            return new ErrorCode(ErrorCode.SUCC, playerPay);
		}
		else {
            return new ErrorCode(-1, "充值验证错误");
        }
		
	}
	
	private List<PayInfo> getPayInfoByOperator(int operator){
	    List<PayInfo> list = new ArrayList<PayInfo>();
	    Map<Integer, Map<Integer, Map<Integer, PayInfo>>> map = ResourceCache.getInstance().getPayinfos();
	    Map<Integer, Map<Integer, PayInfo>> phone =  map.get(operator);
	    if(phone==null){
	    	return list;
	    }
	    for (Map<Integer, PayInfo> map1 :phone.values()) {
	    	list.addAll(map1.values());
		}
        Collections.sort(list, new Comparator<PayInfo>() {
            @Override
            public int compare(PayInfo o1, PayInfo o2) {
                return o1.getGold() - o2.getGold();
            }
        });
        return list;
	}
	
	private List<PayInfo> getPayInfoByPaybay(Map<Integer, PayInfo> map){
		List<PayInfo> list = new ArrayList<PayInfo>();
		list.addAll(map.values());
		Collections.sort(list, new Comparator<PayInfo>() {
            @Override
            public int compare(PayInfo o1, PayInfo o2) {
                return o1.getGold() - o2.getGold();
            }
        });
        return list;
	}
	
	private PayInfo isExistPayInfo(PlayerPay info){
		return PayInfoService.getPayInfo(info.getPayInfoId());
	}
	
//	private PayInfo getPayInfo(String payId){
//		PayInfo payInfo = null;
//		Map<Integer, Map<Integer, Map<Integer, PayInfo>>> payinfos = ResourceCache.getInstance().getPayinfos();
//        for (Map<Integer, Map<Integer, PayInfo>> map : payinfos.values()) {
//        	for (Map<Integer, PayInfo> map2 : map.values()) {
//				for (PayInfo pi : map2.values()) {
//					if (pi.getPayId().equals(payId)) {
//						payInfo = pi;
//						break;
//					}
//					
//				}
//			}
//        }
//        return payInfo;
//	}
	
	private void checkActivity(Player player,PlayerPay playerPay){
		if (DateUtil.isInDateRange(ActivityService.beginTime, ActivityService.endTime)) {
			
			PayInfo pi = PayInfoService.getPayInfo(playerPay.getPayInfoId());
			//是否首次充值
//			ActivityPayRecord payRecord=player.getFirstPayRecord();
			//累计充值
			ActivityPayRecord sumPayRecord = player.getSumPayRecord();
			if(sumPayRecord==null){
//				payRecord=saveActivityPayRecord(player,pi,Constants.ACTIVITY_FIRST_PAY);			
//				player.setFirstPayRecord(payRecord);
				sumPayRecord=saveActivityPayRecord2(player,pi,Constants.ACTIVITY_SUM_PAY);				
				player.setSumPayRecord(sumPayRecord);
			}
			if (pi != null && ActivityService.getActivityVos().containsKey(pi.getMoney())) {
				ActivityConfigVo vo = ActivityService.getActivityVos().get(pi.getMoney());
				//添加赠送宝石
				GoldService.addGold(player, vo.getGolds());
//				GoldService.addCopper(player, vo.getGolds());
				StringBuffer sb = new StringBuffer();
				sb.append("充值成功，赠送" + vo.getGolds() + "宝石");
//				if (vo.getProps() != null) {
//					PropsConfig pc = null;
//					for (Integer propid : vo.getProps().keySet()) {
//						pc = ResourceCache.getInstance().getPropsConfigs().get(propid);
//						if (pc != null) {
//							PropCacheAction.addProp(player, pc.getId(), vo.getProps().get(propid));
//							sb.append("，").append(pc.getName()).append("+").append(vo.getProps().get(propid));
//						}
//					}
//				}
				String msg = sb.toString();
				PlayerInfoCenterManager.getInstance().addPlayerInfoCenter(player.getId(), false, Constants.PAY, "",msg);
				GameSession session = SessionManger.getInstance().getSession(player.getId());
		        if (session != null) {
		        	PlayerInfoCenterManager.getInstance().noticePlayerInfoCenter(session);
				}
			}
			if(pi!=null){
				activityPayPrize(player,player.getSumPayRecord(),pi.getMoney(),Constants.ACTIVITY_SUM_PAY);				
			}
			
		}
	}
	
	/**
	 * 
	 * @param player  玩家
	 * @param payRecord 活动充值记录
	 * @param money  充值的金额
	 * @param pay_sign  活动内容(分首次充值并消费  与 累计消费)
	 */
	public static void activityPayPrize(Player player,ActivityPayRecord payRecord,int money,String pay_sign){
		if (payRecord != null) {
			ActivityConfigVo vo=null;
			if(pay_sign.equals(Constants.ACTIVITY_SUM_PAY)){
				payRecord.setPayMoneySum(payRecord.getPayMoneySum()+money);
				int prizeNum=payRecord.getPayMoneySum()/Constants.ACTIVITY_PAY_AWARD_GOLD;
				int runningNum=prizeNum-payRecord.getAwardNum();
				if(runningNum>0){
					for(int i=0;i<runningNum;i++){
						vo = ActivityService.getActivityVos().get(Constants.ACTIVITY_PAY_AWARD_FOR_SUM);
//						activityPayPrize(player, vo,"累计充值每满500元");
						int prizeGold = vo.getGolds();
						GoldService.addGold(player, prizeGold);
						payRecord.setAwardNum(payRecord.getAwardNum()+1);	
						PlayerInfoCenterManager.getInstance().addPlayerInfoCenter(player.getId(), false, Constants.PAY, "","累计充值每满500元,奖励"+prizeGold+"宝石");
					}
				}
				player.setSumPayRecord(payRecord);
			}
			//首次充值并消费
//			if(pay_sign.equals(Constants.ACTIVITY_FIRST_PAY)){
//				vo = ActivityService.getActivityVos().get(Constants.ACTIVITY_PAY_AWARD_FOR_FIRST);				
//				activityPayPrize(player, vo,"单次充值并消费奖励");
//				payRecord.setState(Constants.COMMON_TYPE_ONE);
//				player.setFirstPayRecord(payRecord);
//			}
			BaseEngine.getInstace().getActivityPayAction().updateActivityPayRecord(payRecord);
		}
	}
	private static void activityPayPrize(Player player, ActivityConfigVo vo,String message) {
			StringBuffer sb = new StringBuffer();
			sb.append(message);
			if (vo.getProps() != null) {
				PropsConfig pc = null;
				for (Integer propid : vo.getProps().keySet()) {
					pc = ResourceCache.getInstance().getPropsConfigs().get(propid);
					if (pc != null) {
						PropCacheAction.addProp(player, pc.getId(), vo.getProps().get(propid));
						sb.append(",").append(pc.getName()).append("+").append(vo.getProps().get(propid)+" ");
					}
				}
				String msg = sb.toString();
				PlayerInfoCenterManager.getInstance().addPlayerInfoCenter(player.getId(), false, Constants.PAY, "",msg);
				GameSession session = SessionManger.getInstance().getSession(player.getId());
				if (session != null) {
					PlayerInfoCenterManager.getInstance().noticePlayerInfoCenter(session);
				}					
			}
	}
	
	private ActivityPayRecord saveActivityPayRecord(Player player, PayInfo pi,String pay_sign) {
		ActivityPayRecord record=new ActivityPayRecord();
		record.setPlayerId(player.getId());
		record.setPayMoneySum(pi.getMoney());
		record.setPaySign(pay_sign);
		record.setState(Constants.COMMON_TYPE_ZERO);
		record.setAwardNum(0);
		int res = BaseEngine.getInstace().getActivityPayAction().addActivityPayRecord(record);
		if(res > 0){
			record.setId(res);
		}else{
			throw new GoldException("添加活动充值记录失败..");
		}
		return record;
	}
	
	private ActivityPayRecord saveActivityPayRecord2(Player player, PayInfo pi,String pay_sign) {
		ActivityPayRecord record=new ActivityPayRecord();
		record.setPlayerId(player.getId());
		record.setPayMoneySum(0);
		record.setPaySign(pay_sign);
		record.setState(Constants.COMMON_TYPE_ZERO);
		record.setAwardNum(0);
		int res = BaseEngine.getInstace().getActivityPayAction().addActivityPayRecord(record);
		if(res > 0){
			record.setId(res);
		}else{
			throw new GoldException("添加活动充值记录失败..");
		}
		return record;
	}
	
	
	public static final String APP_ID = "fkzjh"; 
	private static final String NOTIFY_URL = "http://114.215.175.174:9999/alipay";
	private static final String NOTIFY_DIANJIN_URL = "http://mmzjh.wiorange.com:9999/dianjin";
	private static final String NOTIFY_BAIFUBAO = "http://mmzjh.wiorange.com:9999/baifubao";
	private static final String NOTIFY_URL_MM = "http://notifurl_mm";
	
	private String getInfoXml(PayInfo pi, Player player) {
		StringBuffer sb = new StringBuffer();
//		sb.append(APP_ID).append("_");
//		sb.append(player.getId()).append("_");
//		sb.append(pi.getPayId()).append("_");
//		sb.append(System.currentTimeMillis()).append("_");
//		sb.append(player.getChannelId());
		sb.append("f");
		return sb.toString();
	}
//	private String getInfoXml(PayInfo pi,int player_id){
//		StringBuffer sb = new StringBuffer();
//		sb.append("<root>");
//		sb.append("<info app_id=\"").append(APP_ID).append("\" ");
//		sb.append("player_id=\"").append(player_id).append("\" ");
//		sb.append("pay_id=\"").append(pi.getPayId()).append("\" ");
//		sb.append("notif_url=\"").append(NOTIFY_URL).append("\"/");
//		sb.append("</root>");
//		return sb.toString();
//	}
	
	/**
	 * 支付宝支付附加值
	 * @param pi
	 * @param player_id
	 * @return
	 */
	private String getInfoFoSlipay(PayInfo pi,int player_id){
		return PayInfoService.getInfoForPay(pi, player_id);
	}
	
	/**
	 * 支付宝充值通知接口
	 * @param request
	 * @param response
	 * @throws JuiceException
	 */
	public void zfbPay(SocketRequest request, SocketResponse response) 
			throws JuiceException {
		InputMessage msg = request.getInputMessage();
		String notify_data = msg.getUTF();
		String sign = msg.getUTF();
		log.info("notify_data" + notify_data);
		log.info("sign" + sign);
		try {
			notify_data = URLDecoder.decode(notify_data, "UTF-8");
			sign = URLDecoder.decode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		log.info("notify_data===" + notify_data);
		log.info("sign===" + sign);
		
		Alipay alipay = pare(notify_data, sign);
		
		boolean isCheck = RSASignature.doCheck("notify_data=" + notify_data,
				sign, KeysUtil.alipay_public_key);
//		if (isCheck) {
			
			if (saveZFBPayRecrod(alipay)) {
				ErrorCode res = checkPayInfoAlipay(alipay);
				pay(res, request, response);
			}
			OutputMessage out = new OutputMessage();
			out.putString("success");
			response.sendMessage(Protocol.RESPONSE_ZFB, out);
			return;
//		}
//		if (!isCheck) {
//			OutputMessage out = new OutputMessage();
//			out.putString("sign");
//			response.sendMessage(Protocol.RESPONSE_ZFB, out);
//			return;
//		}
//		log.info("zfbPay|status" + alipay.getTradeStatus());
//		if (finish_pay.equals(alipay.getTradeStatus())) {
//			ErrorCode res = checkPayInfoAlipay(alipay);
//			if (res.getStatus() == ErrorCode.SUCC) {
//				PlayerPay pay = (PlayerPay)res.getObject();
//				Player player = PlayerService.getPlayer(pay.getPlayerId(), request.getSession());
//				PlayerPay pp = BaseEngine.getInstace().getPlayerPayAction().quaryPlayerPay(pay.getPlayerId(), pay.getOrderId());
//				if (pp != null) {
//					BaseServer.sendErrorMsg(response, (short)1, "该充值已经处理，不需要再次处理！");
//					return;
//				}else {
//					int result = 0;
//					int times = 0;
//					do {
//						result = BaseEngine.getInstace().getPlayerPayAction().insertPlayerPay(pay);
//						times ++;
//					} while (result < 0 && times < 3);
//				}
//				addGolds(player, pay);
//				
//				//添加信息
//				PlayerInfoCenterManager.getInstance().addPlayerInfoCenter(player.getId(), true,Constants.PAY,"","充值成功，您购买了"+pay.getGold()+"宝石！");
//		       
//				// 检查活动
//				checkActivity(player, pay);
//				
//				OutputMessage om = new OutputMessage();
//				om.putInt(player.getId());
//				om.putInt(player.getGolds());
//				om.putInt(GoldService.getLeftCopper(player));
//				om.putInt(player.getCopper());
//				GameSession session = SessionManger.getInstance().getSession(player.getId());
//				if (session != null) {
//					session.sendMessage(Protocol.RESPONSE_PAY_SUCC, om);
//				}
//			}
//		}
//		
//		OutputMessage out = new OutputMessage();
//		out.putString("success");
//		response.sendMessage(Protocol.RESPONSE_ZFB, out);
	}

	private boolean saveZFBPayRecrod(Alipay alipay) {
		if(alipay.getTradeStatus().trim().equals("WAIT_BUYER_PAY")){
			return false;
		}
		ZfbPayRecord record=new ZfbPayRecord();
		String payId_playerId[] = alipay.getOutTradeNo().split("_");
		String pay_id="111111";
		int player_id=-1;
		try{
			pay_id = payId_playerId[0];
			player_id = Integer.parseInt(payId_playerId[1]);
		}catch(Exception e){
			log.error("handle zfb record error.!");
		}
		record.setPayId(pay_id);
		record.setPlayerId(player_id);			
		record.setZfbUsername(alipay.getTradeNo());
		record.setGoodsName(alipay.getSubject());
		record.setGoodsPrice(alipay.getTotalFree());
		record.setTradeState(alipay.getTradeStatus());
		record.setPayTime(new Date());
		BaseEngine.getInstace().getZfbPayRecordAction().addZFBPayRecord(record);
		return true;
	}
	private ErrorCode checkPayInfoAlipay(Alipay alipay) {
		String payId_playerId[] = alipay.getOutTradeNo().split("_");
		String pay_id = payId_playerId[0];
		int player_id = Integer.parseInt(payId_playerId[1]);
		PlayerPay playerPay = BaseEngine.getInstace().getPlayerPayAction()
				.quaryPlayerPay(player_id, alipay.getOutTradeNo());
		if (playerPay != null) {
			return new ErrorCode(-1, "订单已经处理");
		}
		playerPay = new PlayerPay();
		playerPay.setOrderId(alipay.getOutTradeNo());
		playerPay.setPayTime(new Date());
		playerPay.setPlayerId(player_id);
		PayInfo pi = PayInfoService.getPayInfo(pay_id);
		playerPay.setPayInfoId(pi.getPayId());
		playerPay.setGold(pi.getMoney());
		playerPay.setPayType(pi.getPayType() + "");
		playerPay.setTradeId("");
		playerPay.setBuyId("");
		return new ErrorCode(ErrorCode.SUCC, playerPay);
	}
	
//	private ErrorCode checkPayInfoZfb(Alipay alipay){
//		int playerId = msg.getInt();
//		String payId = msg.getUTF();
//		String timeStamp = msg.getUTF();
//		String sign = msg.getUTF();
//		Byte payType = msg.getByte();
//		String tradeId = "";
//		String buyId = "";
//		try {
//			tradeId = msg.getUTF();
//			buyId = msg.getUTF();
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		
//		
//		
//		StringBuffer sb = new StringBuffer();
//		sb.append(playerId).append(payId).append(timeStamp).append(tradeId).append(buyId);
//		String newSign = MD5.encode(sb.toString());
//		
//		if (newSign.equals(sign)) {
//		    PlayerPay playerPay = new PlayerPay();
//            playerPay.setOrderId(timeStamp);
//            playerPay.setPayTime(new Date());
//            playerPay.setPlayerId(playerId);
//            PayInfo pi = getPayInfo(payId);
//            playerPay.setPayInfoId(pi.getPayId());
//            playerPay.setGold(pi.getMoney());
//            playerPay.setPayType(payType+"");
//            playerPay.setTradeId(tradeId);
//            playerPay.setBuyId(buyId);
//            return new ErrorCode(ErrorCode.SUCC, playerPay);
//		}
//		else {
//            return new ErrorCode(-1, "充值验证错误");
//        }
//		
//	}
	
	private Alipay pare(String notify_data,String sign){
		try {
			notify_data = URLDecoder.decode(notify_data, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String strXml = "<root>" + notify_data + "</root>";
		Document document = XmlUtil.strToXml(strXml);
		Element root = document.getRootElement();
		Element notify = root.element("notify");
		
		Alipay alipay = new Alipay();
		alipay.setTradeStatus(notify.elementText("trade_status"));
		alipay.setTotalFree(Float.parseFloat(notify.elementText("total_fee")));
		alipay.setSubject(notify.elementText("subject"));
		alipay.setOutTradeNo(notify.elementText("out_trade_no"));
//		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date begDate = null;;
//		try {
//			begDate = sf.parse(notify.elementText("notify_reg_time"));
//		} catch (ParseException e) {
//		}
//		alipay.setNotifyRegTime(begDate);
		alipay.setTradeNo(notify.elementText("trade_no"));
		return alipay;
	}
	
	public void baiFuBaoPay(SocketRequest request, SocketResponse response) 
			throws JuiceException {
		InputMessage in = request.getInputMessage();
		String transdata = in.getUTF();
		String sign = in.getUTF();
		log_gold.info("transdata:" + transdata);
		log_gold.info("sign:" + sign);
		OutputMessage out = new OutputMessage();
		out.putString("success");
		response.sendMessage(Protocol.RESPONSE_BAIFUBAO, out);
	}
	
	/**
	 * MM充值
	 * @param request
	 * @param response
	 * @throws JuiceException
	 */
	public void mmPay(SocketRequest request, SocketResponse response) 
			throws JuiceException {
		InputMessage msg = request.getInputMessage();
		String mminfo = msg.getUTF();
		log.info("MM info" + mminfo);
		
		String infos[] = mminfo.split("_");
		if (infos == null || infos.length <= 0) {
			OutputMessage out = new OutputMessage();
			out.putString("success");
			response.sendMessage(Protocol.RESPONSE_MOBILEMM, out);
			return;
		}
		
		ErrorCode res = MMPayService.mmPay(mminfo, response);
		if (res.getStatus() == ErrorCode.SUCC ) {
			pay(res, request, response);
		}
		
		
		OutputMessage out = new OutputMessage();
		out.putString("success");
		response.sendMessage(Protocol.RESPONSE_MOBILEMM, out);
	}
	
	public static void main(String[] args) {
		String notify_data = "<notify><partner>2088801160913236</partner><discount>0.00</discount><payment_type>1</payment_type><subject>购买10宝石</subject><trade_no>2013110744320045</trade_no><buyer_email>test@orangegame.cn</buyer_email><gmt_create>2013-11-07 15:01:31</gmt_create><quantity>1</quantity><out_trade_no>110715012214685</out_trade_no><seller_id>2088801160913236</seller_id><trade_status>TRADE_FINISHED</trade_status><is_total_fee_adjust>N</is_total_fee_adjust><total_fee>10.00</total_fee><gmt_payment>2013-11-07 15:01:31</gmt_payment><seller_email>pay@orangegame.cn</seller_email><gmt_close>2013-11-07 15:01:31</gmt_close><price>10.00</price><buyer_id>2088012744563457</buyer_id><use_coupon>N</use_coupon></notify>";
		String sign = "gpTyCl/TtrsdakSLahahTeZulzVxr2AKEWm987NGn6425BSvKEQByfjlnU4CXG9wAhzadK0WEC2LGYVPagsC2OhAkWO48bnu+0gS8F72t4pwnFy41GtF4Hsyo7PzwZuncy3jTE5GMZjEaVOHnKUoNSs01UPYEMTK2arTqUSdzb4=";
		boolean b = RSASignature.doCheck("notify_data=" + notify_data,sign, KeysUtil.alipay_public_key);
		System.out.println(b);

		String s = "gpTyCl/TtrsdakSLahahTeZulzVxr2AKEWm987NGn6425BSvKEQByfjlnU4CXG9wAhzadK0WEC2LGYVPagsC2OhAkWO48bnu 0gS8F72t4pwnFy41GtF4Hsyo7PzwZuncy3jTE5GMZjEaVOHnKUoNSs01UPYEMTK2arTqUSdzb4=";
		String a = "gpTyCl/TtrsdakSLahahTeZulzVxr2AKEWm987NGn6425BSvKEQByfjlnU4CXG9wAhzadK0WEC2LGYVPagsC2OhAkWO48bnu+0gS8F72t4pwnFy41GtF4Hsyo7PzwZuncy3jTE5GMZjEaVOHnKUoNSs01UPYEMTK2arTqUSdzb4=";
//		String b = "gpTyCl/TtrsdakSLahahTeZulzVxr2AKEWm987NGn6425BSvKEQByfjlnU4CXG9wAhzadK0WEC2LGYVPagsC2OhAkWO48bnu+0gS8F72t4pwnFy41GtF4Hsyo7PzwZuncy3jTE5GMZjEaVOHnKUoNSs01UPYEMTK2arTqUSdzb4=";
		
	}
	
}
