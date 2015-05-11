package com.orange.goldgame.server.service;

import java.util.List;

import com.juice.orange.game.handler.SocketRequest;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.util.InputMessage;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.domain.HelpQa;
import com.orange.goldgame.protocol.Protocol;

public class HelpServer {
	
	public void requestHelpInfo(SocketRequest request,SocketResponse response){
		InputMessage im=request.getInputMessage();
		int userId=im.getInt();
		byte helpId=im.getByte();
		List<HelpQa> list=HelpService.getAllHelpInfo(helpId);
		short size=(short)list.size();
		System.out.println("用户ID:"+userId+"      帮助类型ID:"+helpId+"      帮助SIZE:"+size);
		OutputMessage om=new OutputMessage();
		om.putByte(helpId);
		om.putShort(size);
		if(size>0){
			for(HelpQa qa:list){
				om.putString(qa.getQuestion());
				om.putString(qa.getAnswer());
			}
		}
		response.sendMessage(Protocol.RESPONSE_HELP_lIST, om);
	}

}
