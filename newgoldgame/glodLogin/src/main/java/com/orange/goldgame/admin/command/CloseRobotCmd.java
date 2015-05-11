package com.orange.goldgame.admin.command;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import com.orange.goldgame.admin.socket.SocketSet;
import com.orange.goldgame.admin.util.MessagePacg;
import com.orange.goldgame.admin.util.Util;
import com.orange.goldgame.domain.ServerConfig;


public class CloseRobotCmd extends AdminCmd{

	@Override
	public boolean isResponsible(AdminRequest req) {
		if (req.cmd.startsWith("3102") ) {
			adminReq = req;
			return true;
		}
		return false;
	}

	@Override
	public JSONObject process(ServerConfig serConfig) throws JSONException {
		JSONObject json = new JSONObject();
		try {
			short prool = Short.parseShort(adminReq.cmd);
			int closed = Integer.parseInt(adminReq.param.get(0));
			Socket socket = SocketSet.getInstall().getSocket(serConfig.getServerIp(),serConfig.getServerPort());
			if (socket != null && socket.isConnected()) {
				MessagePacg msg = new MessagePacg((short) 9000);
				msg.putMsgHead();
				msg.putAgreeID(prool);
				msg.putInt(closed);
				Util.sendEnd(msg, socket);
				InputStream in = socket.getInputStream();
				DataInputStream data = new DataInputStream(in);
				MessagePacg res = Util.getPacg(data);
				res.getAgreeID();
				String result = res.getUTF();
				json.put("result", "机器人状态：" + result);
			}
		} catch (Exception e) {
			json.put("result", "erorr");
		}
		return json;
	}

}
