package com.orange.goldgame.server.manager;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.juice.orange.game.container.GameRoom;
import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.action.AppConfigAction;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.server.RoomTalkService;
import com.orange.goldgame.server.domain.GameTable;
import com.orange.goldgame.server.domain.GameTableSeat;
import com.orange.goldgame.server.domain.Gamer;
import com.orange.goldgame.server.emun.GamerState;
import com.orange.goldgame.server.engine.BaseEngine;

public class RobotTalkManager {
	
	private static Logger log=LoggerFactory.getLogger(RobotTalkManager.class);
	private static RobotTalkManager robotTalk=new RobotTalkManager();
	private static Timer timer=new Timer();
	private GregorianCalendar calendar = new GregorianCalendar();
	private AppConfigAction configAction=BaseEngine.getInstace().getAppConfigActionImpl();
	private Random random=new Random();
	public static RobotTalkManager getInstance(){
		return robotTalk;
	}
	
	public void start(){
		timer.schedule(new TimerTask() {
			public void run() {
				try{
					robotStartTalk();
				}catch (Exception e) {
                    log.error("机器人发表语音有问题.....", e);
                }
			}
		}, calendar.getTime(),10000);
	}

	protected void robotStartTalk() {
		String voiceCount=configAction.findAppConfigByKey(Constants.TALK_VOICE).getAppValue();
		int count=Integer.parseInt(voiceCount);
		for(GameRoom room:GameRoomManager.getInstance().getGameRoomSet().values()){
			GameTable table=(GameTable) room.getObject(Constants.ROOM_TO_TABLE_KEY);
			if(table!=null){
				if(random.nextInt(10)>=5){
					List<Gamer> roGamer=getTableRobot(table);
					if(roGamer!=null&&roGamer.size()>0){
						Gamer gamer=roGamer.get(random.nextInt(roGamer.size()));
						if(gamer!=null){
							int num=random.nextInt(count);
							if(num==7) break;
							String msg=""+num;
							RoomTalkService.sendTalkMessage(room, gamer.getPlayerId(), Constants.COMMON_TYPE_ZERO, msg);
							break;							
						}
					}					
				}
			}
		}
	}

	private List<Gamer> getTableRobot(GameTable table) {
		List<Gamer> gamers=new ArrayList<Gamer>();
		for(GameTableSeat seat:table.getSeats()){
			if(seat.getGamer()!=null&&seat.getGamer().isRobot()&&
					seat.getGamer().getState()==GamerState.GAME_PLAYING){
				gamers.add(seat.getGamer());
			}
		}
		return gamers;
	}

}
