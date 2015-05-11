/**
 * SuperStarLogin
 * com.orange.superstar.login.server
 * UserServer.java
 */
package com.orange.goldgame.login.server;

import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.juice.orange.game.exception.JuiceException;
import com.juice.orange.game.handler.SocketRequest;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.log.LoggerFactory;
import com.juice.orange.game.util.InputMessage;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.admin.command.CommandExec;
import com.orange.goldgame.domain.ServerConfig;
import com.orange.goldgame.protocol.Protocol;

/**
 * 
 * @author wuruihuang 2013.7.4
 */
public class UserServer extends BaseServer{
	Logger logger =  LoggerFactory.getLogger(UserServer.class);
	
	/***
	 * 返回逻辑服务器地址
	 */
	public void isExisted(SocketRequest request, SocketResponse response) throws JuiceException {
		InputMessage msg = request.getInputMessage();
		String app_id = "";
		int version = 1;
		String channel_id = "";
		try {
			app_id = msg.getUTF();
			version = msg.getInt();
			channel_id = msg.getUTF();
		} catch (Exception e) {
		}
		if (app_id != null && !app_id.equals("")) {
			
		}
		//
		List<ServerConfig> list  = serverInfoImpl.quaryAllServerConfigs();
		ServerConfig config = null;
		// 根据玩家多少进入相应服务器
		if (list.size() > 0) {
			int index = 0;
			if (list.size() > 1) {
				index = new Random().nextInt(list.size());
			}
			
//		    try {
		    	config = list.get(index);
//                for(ServerConfig serConfig : list){
//                    JSONObject jsonObject = CommandExec.exec("3101|",serConfig);
//                    int num = (Integer)jsonObject.get("online");
//                    if(serConfig.getPlayerNum()>=num){
//                        config = serConfig;
//                        break;
//                    }
//                }
//            } catch (JSONException e) {
//                logger.error("数据解析错误", e);
//                config = list.get(0);
//            }
			//
			OutputMessage om =  new OutputMessage();
			om.putString(config.getServerIp());
			om.putInt(config.getServerPort());
			logger.info(config.getServerIp() + ":" + config.getServerPort());
			response.sendMessage(Protocol.RESPONSE_LOGINABLE_SERVER_INFO,om);
		}else {
			OutputMessage om =  new OutputMessage();
			om.putString("127.0.0.1");
			om.putInt(5000);
			logger.info("127.0.0.1:5000");
			response.sendMessage(Protocol.RESPONSE_LOGINABLE_SERVER_INFO,om);
		}
	}
	
}
