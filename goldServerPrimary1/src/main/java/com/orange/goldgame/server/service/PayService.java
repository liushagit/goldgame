package com.orange.goldgame.server.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.juice.orange.game.container.GameSession;
import com.juice.orange.game.exception.JuiceException;
import com.juice.orange.game.handler.SocketRequest;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.log.LoggerFactory;
import com.juice.orange.game.util.InputMessage;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.cache.action.PropCacheAction;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.core.ErrorCode;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.PayChannelConfig;
import com.orange.goldgame.domain.PayInfo;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerPay;
import com.orange.goldgame.domain.PropsConfig;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.server.manager.PlayerInfoCenterManager;
import com.orange.goldgame.server.manager.SessionManger;
import com.orange.goldgame.util.DateUtil;
import com.orange.goldgame.util.MD5;
import com.orange.goldgame.vo.ActivityConfigVo;

/**
 * 支付
 * @author guojiang
 *
 */
public class PayService {

    private Logger log = LoggerFactory.getLogger(PayService.class);
    
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
		
		List<PayInfo> operatorList = new ArrayList<PayInfo>();
		List<PayInfo> paybayList = new ArrayList<PayInfo>();
		
		PayChannelConfig config = ResourceCache.getInstance()
                .getPayChannelConfig().get(operator);
		
		int showType = 0;
		if(config != null){
		    showType = config.getType();
		}
		log.debug("operator:"+operator+",showType:"+showType);
		
		//话费list
		operatorList = getPayInfoByOperator(operator);
		OutputMessage om = new OutputMessage();
		om.putShort(Short.parseShort(showType+""));
		om.putString(Constants.VoucherHelpInfo);
		
		short size = (short)operatorList.size();
		om.putShort(size);
		for (PayInfo pi : operatorList) {
			om.putByte((byte)pi.getId().intValue());
			om.putString(pi.getPayId());
			om.putString(pi.getName());
			om.putInt(pi.getMoney());
			om.putInt(pi.getGold()-pi.getMoney());
		}
		
		//其他类型支付
		 Map<Integer, Map<Integer, Map<Integer, PayInfo>>> map = ResourceCache.getInstance().getPayinfos();
		 Map<Integer, Map<Integer, PayInfo>> phone =  map.get(0);
		for (Map<Integer, PayInfo> map2 : phone.values()) {
			paybayList = getPayInfoByPaybay(map2);
			size = (short) paybayList.size();
			om.putShort(size);
			for (PayInfo pi : paybayList) {
				om.putByte((byte) pi.getId().intValue());
				om.putString(pi.getPayId());
				om.putString(pi.getName());
				om.putInt(pi.getMoney());
				om.putInt(pi.getGold() - pi.getMoney());
			}
		}
		
		
		response.sendMessage(Protocol.RESPONSE_PAY_INFO, om);
		
	}
	public void pay(SocketRequest request, SocketResponse response) 
			throws JuiceException {
		InputMessage msg = request.getInputMessage();
		ErrorCode res = checkPayInfo(msg);
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
	    PayInfo pi = getPayInfo(pay.getPayInfoId());
		GoldService.addGold(player, pi.getGold());
		
	}
	
	private ErrorCode checkPayInfo(InputMessage msg){
		int playerId = msg.getInt();
		String payId = msg.getUTF();
		String timeStamp = msg.getUTF();
		String sign = msg.getUTF();
		Byte payType = msg.getByte();
		
		StringBuffer sb = new StringBuffer();
		sb.append(playerId).append(payId).append(timeStamp);
		String newSign = MD5.encode(sb.toString());
		
		if (newSign.equals(sign)) {
		    PlayerPay playerPay = new PlayerPay();
            playerPay.setOrderId(timeStamp);
            playerPay.setPayTime(new Date());
            playerPay.setPlayerId(playerId);
            PayInfo pi = getPayInfo(payId);
            playerPay.setPayInfoId(pi.getPayId());
            playerPay.setGold(pi.getMoney());
            playerPay.setPayType(payType+"");
            
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
		return getPayInfo(info.getPayInfoId());
	}
	
	private PayInfo getPayInfo(String payId){
		PayInfo payInfo = null;
		Map<Integer, Map<Integer, Map<Integer, PayInfo>>> payinfos = ResourceCache.getInstance().getPayinfos();
        for (Map<Integer, Map<Integer, PayInfo>> map : payinfos.values()) {
        	for (Map<Integer, PayInfo> map2 : map.values()) {
				for (PayInfo pi : map2.values()) {
					if (pi.getPayId().equals(payId)) {
						payInfo = pi;
						break;
					}
					
				}
			}
        }
        return payInfo;
	}
	
	private void checkActivity(Player player,PlayerPay playerPay){
		if (DateUtil.isInDateRange(ActivityService.beginTime, ActivityService.endTime)) {
			PayInfo pi = getPayInfo(playerPay.getPayInfoId());
			if (pi != null && ActivityService.getActivityVos().containsKey(pi.getMoney())) {
				ActivityConfigVo vo = ActivityService.getActivityVos().get(pi.getMoney());
				GoldService.addCopper(player, vo.getGolds());
				StringBuffer sb = new StringBuffer();
				sb.append("充值成功，赠送" + vo.getGolds() + "金币");
				if (vo.getProps() != null) {
					PropsConfig pc = null;
					for (Integer propid : vo.getProps().keySet()) {
						pc = ResourceCache.getInstance().getPropsConfigs().get(propid);
						if (pc != null) {
							PropCacheAction.addProp(player, pc.getId(), vo.getProps().get(propid));
							sb.append("，").append(pc.getName()).append("+").append(vo.getProps().get(propid));
						}
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
	}
}
