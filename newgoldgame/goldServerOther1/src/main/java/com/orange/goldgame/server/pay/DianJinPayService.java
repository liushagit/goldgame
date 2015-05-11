package com.orange.goldgame.server.pay;

import org.apache.log4j.Logger;

import com.juice.orange.game.exception.JuiceException;
import com.juice.orange.game.handler.SocketRequest;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.log.LoggerFactory;
import com.juice.orange.game.util.InputMessage;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.domain.PayInfo;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.util.MD5;

public class DianJinPayService {
	
	private static final String DIANJIN_APP_ID = "";
	private static final String APP_KEY = "";
	private Logger log = LoggerFactory.getLogger(DianJinPayService.class);
	public void zfbPay(SocketRequest request, SocketResponse response) 
			throws JuiceException {
		InputMessage msg = request.getInputMessage();
		String App_Id = msg.getUTF();//应用id
		String User_Id = msg.getUTF();//玩家点金id
		String Urecharge_Id = msg.getUTF();//开发者发起购买请求时传过来的开发者自身订单ID
		String Extra = msg.getUTF();//开发者发起购买请求时传过来的开发者自定义字段
		String Recharge_Money = msg.getUTF();//充值金额-人民币， 单位元，精确到分
		String Recharge_Gold_Count = msg.getUTF();//充值虚拟货币个数
		String Pay_Status = msg.getUTF();//支付状态:0未处理，1成功，2失败
		String Create_Time = msg.getUTF();//创建时间
		String Sign = msg.getUTF();//签名示例：
//		md5(App_Id=9&Create_Time=1351675829&Extra=a123456&Pay_Status=1
//		&Recharge_Gold_Count=1&Recharge_Money=100&Uin=10671
//		&Urecharge_Id=1234567a123456789b123456789c123456789d1) 
//		其中红色部分为app_key， 请不要修改蓝色字体的顺序。
		if (!DIANJIN_APP_ID.equals(App_Id)) {
			OutputMessage om = new OutputMessage();
			om.putString("appIdError");
			response.sendMessage(Protocol.RESPONSE_PAY_SUCC, om);
			return;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("App_Id=").append(App_Id).append("&");
		sb.append("Create_Time=").append(Create_Time).append("&");
		sb.append("Extra=").append(Extra).append("&");
		sb.append("Pay_Status=").append(Pay_Status).append("&");
		sb.append("Recharge_Gold_Count=").append(Recharge_Gold_Count).append("&");
		sb.append("Recharge_Money=").append(Recharge_Money).append("&");
		sb.append("Urecharge_Id=").append(Urecharge_Id).append("&");
		
		String new_sign = MD5.encode(sb.toString(),APP_KEY);
		if (!new_sign.equals(Sign)) {
			OutputMessage om = new OutputMessage();
			om.putString("MD5FAIL");
			response.sendMessage(Protocol.RESPONSE_PAY_SUCC, om);
			return;
		}
		
		
	}
	
	public static String getJinDianInfo(PayInfo pi,int player_id){
		return PayInfoService.getInfoForPay(pi, player_id);
	}
}
