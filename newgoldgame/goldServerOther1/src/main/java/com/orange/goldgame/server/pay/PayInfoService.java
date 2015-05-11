package com.orange.goldgame.server.pay;

import java.util.Map;

import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.PayInfo;
import com.orange.goldgame.domain.Player;

public class PayInfoService {

	public static String getInfoForPay(PayInfo pi,int player_id){
		StringBuffer sb = new StringBuffer();
		sb.append(pi.getPayId()).append("_");
		sb.append(player_id).append("_");
		sb.append(System.currentTimeMillis());
		return sb.toString();
	}
	
	public static PayInfo getPayInfo(String payId){
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
	
	public static String getInfoXml(PayInfo pi, Player player) {
		StringBuffer sb = new StringBuffer();
		sb.append("f");
		return sb.toString();
	}
}
