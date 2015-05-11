package com.orange.goldgame.server.pay;

import java.util.List;

import org.apache.log4j.Logger;

import com.juice.orange.game.container.GameSession;
import com.juice.orange.game.exception.JuiceException;
import com.juice.orange.game.handler.SocketRequest;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.log.LoggerFactory;
import com.juice.orange.game.log.LoggerName;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.cache.action.PropCacheAction;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.core.ErrorCode;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.PayInfo;
import com.orange.goldgame.domain.PayOrder;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerProps;
import com.orange.goldgame.domain.PropsConfig;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.server.manager.SessionManger;
import com.orange.goldgame.server.service.BaseServer;
import com.orange.goldgame.server.service.GoldService;
import com.orange.goldgame.server.service.PlayerService;
import com.orange.goldgame.server.wheel.WheelFortuneService;
import com.orange.goldgame.util.SerializeUtil;
import com.orange.goldgame.vo.InPayListInfoVo;
import com.orange.goldgame.vo.InPayOrderVo;
import com.orange.goldgame.vo.InPlayerPayVo;
import com.orange.goldgame.vo.InWheelListVo;

public class PayNewServer {
	private Logger logger = LoggerFactory.getLogger(LoggerName.GOLD);
	
	public void createPayOrder(SocketRequest request, SocketResponse response)
			throws JuiceException {
		InPayOrderVo vo = new InPayOrderVo();
		try {
			SerializeUtil.setVo(request.getInputMessage(), vo);
		} catch (Exception e) {
		}
		Player player = PlayerService.getPlayer(vo.getaPlayerID(),
				request.getSession(),false);
		GameSession session = SessionManger.getInstance().getSession(player.getId());
		ErrorCode res = PayNewService.create(player, vo);
		if (res.getStatus() != ErrorCode.SUCC) {
			BaseServer.sendErrorMsg(session, (short) res.getStatus(),
					res.getMsg());
		} else {
			PayOrder order = (PayOrder) res.getObject();
			String sequence = PayNewService.getMd5KeySequence(player,order.getId()+"");
			OutputMessage om = new OutputMessage();
			om.putString(vo.getbPayId());
			om.putString(vo.getcPayName());
			om.putInt(vo.getePayType());
			om.putInt(vo.getfPayPrice());
			om.putString("" + order.getId());
			om.putString(sequence);
			om.putString(order.getId() + "_" + player.getId());
			if (session != null) {
				session.sendMessage(Protocol.RESPONSE_CREATE_PAY_ORDER, om);
			}
		}

	}

	public void pay(SocketRequest request, SocketResponse response)
			throws JuiceException {
		
		InPlayerPayVo vo = new InPlayerPayVo();
		try {
			SerializeUtil.setVo(request.getInputMessage(), vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Player player = PlayerService.getPlayer(vo.getaPlayerId(),
				request.getSession(),false);
		int old = player.getGolds();
		
		GameSession session = SessionManger.getInstance().getSession(player.getId());
			
			ErrorCode res = PayNewService.pay(player,
					Integer.parseInt(vo.getcOrderId()), vo);
			if(res.getStatus() == Constants.FULL_WHEEL){
				OutputMessage oo = new OutputMessage();
				PlayerProps pp = PropCacheAction.getplayerPropByPropid(player, WheelFortuneService.TICKETID);
				oo.putInt(pp.getNumber());
				session.sendMessage(Protocol.RESPONSE_WHEELNUM, oo);
				BaseServer.sendErrorMsg(session, (short)Constants.SUCC_WHEEL, res.getMsg());
				
				return;
			}
			if (res.getStatus() != ErrorCode.SUCC) {
				BaseServer.sendErrorMsg(session, (short) -1,
						res.getMsg());
				return;
			}
			
			
			PayInfo payInfo = (PayInfo) res.getObject();
			OutputMessage om = new OutputMessage();
			om.putInt(player.getId());
			om.putInt(player.getGolds());
			om.putInt(GoldService.getLeftCopper(player));
			
			om.putInt(payInfo.getMoney());
			om.putInt(payInfo.getGold());
			om.putInt(payInfo.getYoumeng());
			if (session != null) {
				session.sendMessage(Protocol.RESPONSE_PAY_SUCC, om);
				if (res.getMsg() != null && res.getMsg().length() > 0) {
					BaseServer.sendErrorMsg(session, (short)-1, res.getMsg());
				}
			}
			
		
		logger.info("paygold|" + player.getId() + "|" + old + "|" + player.getGolds() + "|" + payInfo.getGold());
		
		
	}

	/**
	 * 服务端通知
	 * @param request
	 * @param response
	 * @throws JuiceException
	 */
	public void payServer(SocketRequest request, SocketResponse response)
			throws JuiceException {
		String msg = request.getInputMessage().getUTF();
		logger.info("payServer|" + msg);
		ErrorCode res = PayNewService.payServer(Integer.parseInt(msg));
		OutputMessage out = new OutputMessage();
		out.putString("success");
		response.sendMessage(Protocol.RESPONS_WEIMIPAY, out);
			
		PayOrder order = BaseEngine.getInstace().getPayOrderAction()
				.loadPayOrderById(Integer.parseInt(msg));
		if (order == null) {
		}
		Player player = PlayerService.getSimplePlayerByPlayerId(order
				.getPlayerId());
		if (player == null) {
		}
		GameSession session = SessionManger.getInstance().getSession(player.getId());
		
		if (session != null) {
			BaseServer.sendErrorMsg(session, (short)-1, res.getMsg());
		}
	}
	
	/**
	 * @param request
	 * @param response
	 * @throws JuiceException
	 */
	public void getPayInfo(SocketRequest request, SocketResponse response)
			throws JuiceException {
		
		InPayListInfoVo vo = new InPayListInfoVo();
		try {
			SerializeUtil.setVo(request.getInputMessage(), vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		List<PayInfo> phoneList = PayNewService.getPayInfo(vo,PayNewService.PHONELIST);
		List<PayInfo> zfbList = PayNewService.getPayInfo(vo,PayNewService.ZFBLIST);
		List<PayInfo> cftList = PayNewService.getPayInfo(vo,PayNewService.CFTLIST);
		
		

		
		OutputMessage om = new OutputMessage();
		if (phoneList.size() > 0) {
			om.putByte((byte)PayNewService.PHONELIST);
		}else if (zfbList.size() > 0) {
			om.putByte((byte)PayNewService.ZFBLIST);
		}else if (cftList.size() > 0) {
			om.putByte((byte)PayNewService.CFTLIST);
		}else{
			om.putByte((byte)-1);
		}
		om.putShort((short)phoneList.size());
		if (phoneList.size() > 0) {
			PayInfo piInfo = phoneList.get(0);
			putMsg(piInfo, om,PayNewService.PHONELIST);
		}
		
		om.putShort((short)zfbList.size());
		if (zfbList.size() > 0) {
			PayInfo piInfo = zfbList.get(0);
			putMsg(piInfo, om,PayNewService.ZFBLIST);
		}
		
		om.putShort((short)cftList.size());
		if (cftList.size() > 0) {
			PayInfo piInfo = cftList.get(0);
			putMsg(piInfo, om,PayNewService.CFTLIST);
		}
		response.sendMessage(Protocol.RESPONSE_PAYINFO, om);
	}
	
	
	private void putMsg(PayInfo piInfo,OutputMessage om,int type){
		PropsConfig pc = ResourceCache.getInstance().getPropsConfigs().get(piInfo.getPayProType());
		
		om.putByte((byte)piInfo.getId().intValue());
		om.putString(piInfo.getPayId());
		om.putString(pc == null? piInfo.getName() : piInfo.getMoney() + pc.getName());
		om.putInt(piInfo.getMoney());
		StringBuffer sb = new StringBuffer();
		int res = piInfo.getGold() - piInfo.getMoney();
		if(res > 0){
			sb.append(res);
			sb.append(pc == null?"宝石":pc.getName());
		}
		om.putString(sb.toString());
		om.putString("短信支付只支持30元以下支付，每天可累计支付600元，超过范围将无法充值！");
		om.putInt(type);
		om.putString(PayNewService.getURL(piInfo));
		
	}
	
	/**
	 * 获取购买抽奖卷列表
	 * @param request
	 * @param response
	 * @throws JuiceException
	 */
	public void getWheelList(SocketRequest request, SocketResponse response)
			throws JuiceException {
		InWheelListVo vo = new InWheelListVo();
		try {
			SerializeUtil.setVo(request.getInputMessage(), vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<PayInfo> listAll = PayNewService.getWheelList(vo);
		OutputMessage om = PayNewService.getPayListMessage(listAll);
		response.sendMessage(Protocol.RESPONSE_WHEEL_PAYLIST, om);
		
	}
}
