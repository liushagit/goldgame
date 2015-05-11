package com.orange.goldgame.server.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.juice.orange.game.exception.JuiceException;
import com.juice.orange.game.handler.SocketRequest;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.log.LoggerFactory;
import com.juice.orange.game.log.LoggerName;
import com.juice.orange.game.util.InputMessage;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.alipay.RSASignature;
import com.orange.goldgame.domain.PayInfo;
import com.orange.goldgame.domain.PayOrder;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.ZfbPayRecord;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.server.pay.PayNewService;
import com.orange.goldgame.util.KeysUtil;
import com.orange.goldgame.util.XmlUtil;
import com.orange.goldgame.vo.Alipay;

/**
 * 支付
 * @author guojiang
 *
 */
public class PayService {

    private Logger log = LoggerFactory.getLogger(PayService.class);
    private Logger log_gold = LoggerFactory.getLogger(LoggerName.GOLD);
    
	public void getPayConfig(SocketRequest request, SocketResponse response) 
			throws JuiceException {
		// 接收机器码和渠道号和版本号
		InputMessage msg = request.getInputMessage();
		int player_id = msg.getInt();// player_id使用不到
		Player player = PlayerService
				.getPlayer(player_id, request.getSession());
		if (player == null) {
			return;
		}
		msg.getByte();
		int operator = msg.getByte();
		
		List<PayInfo> listAll = PayNewService.getPayInfos(operator);
		OutputMessage om = PayNewService.getPayListMessage(listAll);
		response.sendMessage(Protocol.RESPONSE_PAY_INFO, om);

	}
	
	public static final String APP_ID = "fkzjh"; 
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
		ZfbPayRecord zfb = saveZFBPayRecrod(alipay);
		
			if (zfb != null) {
				PayNewService.payServer(Integer.parseInt(zfb.getPayId()));
			}
			OutputMessage out = new OutputMessage();
			out.putString("success");
			response.sendMessage(Protocol.RESPONSE_ZFB, out);
			return;
//		}
	}

	private ZfbPayRecord saveZFBPayRecrod(Alipay alipay) {
		if(alipay.getTradeStatus().trim().equals("WAIT_BUYER_PAY")){
			return null;
		}
		ZfbPayRecord record=new ZfbPayRecord();
		String payId_playerId[] = alipay.getOutTradeNo().split("_");
		String pay_id="111111";
		int player_id=-1;
		try{
			pay_id = payId_playerId[0];
			PayOrder order = BaseEngine.getInstace().getPayOrderAction().loadPayOrderById(Integer.parseInt(pay_id));
			if (order != null) {
				player_id = order.getPlayerId();
			}
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
		return record;
	}
	
	
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
