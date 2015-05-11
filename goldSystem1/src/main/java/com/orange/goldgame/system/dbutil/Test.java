package com.orange.goldgame.system.dbutil;

import java.util.Date;

import com.orange.goldgame.domain.PlayerTalk;
import com.orange.goldgame.system.util.SqlMapConfig;

public class Test {

	public static void main(String[] args) {
		DBEngine.initialize();
		DBThread thread = new DBThread();
		thread.start();
		DBService.initialize();
		SqlMapConfig.init();
		int id = GlobalGenerator.getInstance().getReusedIdForNewObj("player_talk");
		System.out.println("=====" + id);
		PlayerTalk talk = new PlayerTalk();
		talk.setId(id);
		talk.setPlayerId(1);
		talk.setTalkMsg("testattt");
		talk.setTalkTime(new Date());
		talk.setTalkType((byte)0);
		DBService.commit(talk);
	}
}
