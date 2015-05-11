/**
 * 
 */
package com.orange.goldgame.test;

import java.util.HashMap;
import java.util.Map;

import com.orange.game.china.mobile.market.utils.HttpUtil;

/**
 * @author guojiang
 *
 */
public class Test {

	public static void main(String[] args) {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<WimiPay>"
				+ "<mchNo>7DCB7B21C259D3C2BFCD4646848C144E</mchNo>"
				+ "<phone>13380395329</phone>"
				+ "<fee>1</fee>"
				+ "<!-- 自定义内容 -->"
				+ "<extData>1</extData> <!--自定义参数值（10个长度）-->"
				+ "<!-- mobileType：1 电信 2 联通-->"
				+ "<mobileType>1</mobileType>"
				+ "</WimiPay>";
//		String xml = "<?xmlversion=\"";
		Map<String, String> parm = new HashMap<String, String>();
		parm.put("xml", xml);
//			String result = HttpReqSender.sendPostReq("http://localhost:9999/wimipay", parm);
//			String reString = HttpUtil.post("http://192.168.1.252:9999/wimipay", xml);
			String reString = HttpUtil.post("http://localhost:9999/wimipay", xml);
			System.out.println(reString);
		
	}
}
