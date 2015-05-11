package com.orange.goldgame.admin.command;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import com.orange.goldgame.admin.exception.GoldException;
import com.orange.goldgame.admin.socket.SocketSet;
import com.orange.goldgame.admin.util.MessagePacg;
import com.orange.goldgame.admin.util.Util;
import com.orange.goldgame.domain.ServerConfig;


public class OnleNumCmd extends AdminCmd {

	@Override
	public JSONObject process(ServerConfig serConfig) throws JSONException {
		JSONObject json = new JSONObject();
		Socket socket = SocketSet.getInstall().getSocket(serConfig.getServerIp(),serConfig.getServerPort());
		try {
			short prool = Short.parseShort(adminReq.cmd);
			if (socket != null && socket.isConnected()) {
				MessagePacg msg = new MessagePacg((short) 9000);
				msg.putMsgHead();
				msg.putAgreeID(prool);
				Util.sendEnd(msg, socket);
				InputStream in = socket.getInputStream();
				DataInputStream data = new DataInputStream(in);
				MessagePacg res = Util.getPacg(data);
				short protocolType = res.getAgreeID();
				int num = res.getInt();
				StringBuffer sb = new StringBuffer();
				sb.append(protocolType).append("|").append(num);
				json.put("online", num);
			}
		} catch (GoldException ge){
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}catch (Exception e) {
			json.put("result", "erorr");
		}
		return json;

	}

	@Override
	public boolean isResponsible(AdminRequest req) {
		if (req.cmd.startsWith("3101") ) {
			adminReq = req;
			return true;
		}
		return false;
	}

}
