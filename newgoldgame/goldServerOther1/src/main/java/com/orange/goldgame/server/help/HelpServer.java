package com.orange.goldgame.server.help;

import java.util.List;

import com.juice.orange.game.exception.JuiceException;
import com.juice.orange.game.handler.SocketRequest;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.util.InputMessage;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.domain.HelpQa;
import com.orange.goldgame.protocol.Protocol;

public class HelpServer {

	public void getLoverMessage(SocketRequest request, SocketResponse response)
			throws JuiceException {
		InputMessage msg = request.getInputMessage();
		int playerId = msg.getInt();
		int helpType = msg.getByte();
		List<HelpQa> helpQas = HelpService.getHelpQqByType(helpType);
		StringBuffer sb = new StringBuffer();
		HelpQa hq = null;
		for (int i = 0; i < helpQas.size(); i++) {
			hq = helpQas.get(i);
//			sb.append(i + 1).append("：");
			if (hq.getQuestion().length() > 0) {
				sb.append(hq.getQuestion()).append("\n");
			}
			sb.append(hq.getAnswer()).append("\n");
		}
		OutputMessage om = new OutputMessage();
		om.putString(sb.toString());
		om.putByte((byte)helpType);
		response.sendMessage(Protocol.RESPONSE_HELPTYPE, om);
	}
}
