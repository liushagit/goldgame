/**
 * 
 */
package com.orange.goldgame.server;

/**
 * @author guojiang
 *
 */
public class MemKeyServer {

	public static String getActivityMatchKey(int playerId){
		return "ac_ma_" + playerId;
	}
}
