package com.orange.goldgame.server;

import org.apache.log4j.Logger;

import com.juice.orange.game.exception.JuiceException;
import com.juice.orange.game.handler.SocketRequest;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.log.LoggerFactory;

public class HartServer {
	private Logger log = LoggerFactory.getLogger(HartServer.class);
	public void requestHart(SocketRequest request, SocketResponse response) throws JuiceException {
		log.info("receiveHart");
	}
}
