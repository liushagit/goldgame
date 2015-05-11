package com.orange.goldgame.server.task;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.log4j.Logger;

import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.ClientToolNotice;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerPay;
import com.orange.goldgame.server.domain.SendMessage;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.util.DateUtil;

public class ClientNoticeTask {
	
	private static Logger logger = LoggerFactory.getLogger(ClientNoticeTask.class);
//	private Set<Integer> sendPlayers = new HashSet<Integer>();
	private static ClientNoticeTask noticeTask = new ClientNoticeTask();
	private LinkedBlockingDeque<SendMessage> messages = new LinkedBlockingDeque<SendMessage>();
	private Map<Integer, Integer> sendAccount = new HashMap<Integer, Integer>();
	private Map<Integer,Set<Integer>> sendPlayerSet = new HashMap<Integer,Set<Integer>>();
	private Timer timer = new Timer();
	private Calendar calendar = new GregorianCalendar();
	private ClientNoticeTask(){}
	
	public static ClientNoticeTask getInstance(){
		return noticeTask;
	}	
	
	public void start(){
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					addRunClientNotice();
				} catch (Exception e) {
					logger.error("client notice error --->"+e.getMessage());
				}
			}
		}, calendar.getTime(), 1000);
	}
	
	private void addRunClientNotice(){
		LinkedBlockingDeque<ClientToolNotice> clientNoticeQueue = ResourceCache.getInstance().getRunClientNotices();
		Iterator<ClientToolNotice> notices = clientNoticeQueue.iterator();
		while(notices.hasNext()){
			ClientToolNotice notice = notices.next();
			sendAccount.put(notice.getId(), 0);
			sendPlayerSet.put(notice.getId(),new HashSet<Integer>());
			doRunClientNotice(notice);
			clientNoticeQueue.remove(notice);
		}
	}
	
	private final int CHECK_RANGE = 100;
	
    private final short isPay = 1;         //	1.是否有充值过 conditin|range_range
    private final short copperRange = 2;   //	2.指定金币范围
    private final short goldRange = 3;     //	3.指定宝石范围
    private final short isOnline = 4;      //	4.用户在线or离线
    private final short sexRange = 5;      //	5.性别:男or女
    
    /**发送信息逻辑**/
	private void doRunClientNotice(ClientToolNotice notice) {
		if(!isSendClientNotice(notice)){
			return;
		}
		String runCon = notice.getNoticeCondition();
		String[] conditions = runCon.split("\\|");
		short conditionId = Short.parseShort(conditions[0]);
		String request = conditions[1];
		int ccount = 0;
		List<PlayerPay> payList = null;
		List<Player> playerList = null;
		int min = 0;
		int max = 0;
		switch (conditionId) {
		case isPay:
			do{
				payList = BaseEngine.getInstace().getPlayerPayAction()
						.queryPayByRange(ccount * CHECK_RANGE +1,(ccount + 1) * CHECK_RANGE);
				ccount++ ;
				getPayPlayers(payList, notice);
			}while(payList != null);
			break;
			
		case copperRange:
			String[] cRange = request.split("_");
			min = Integer.parseInt(cRange[0]);
			max = Integer.parseInt(cRange[1]);
			do{
				playerList = BaseEngine.getInstace().getPlayerActionIpml().
						queryPlayersByCopperRange(min, max,ccount * CHECK_RANGE +1,(ccount + 1) * CHECK_RANGE);
				ccount++;		
				getConditionPlayers(playerList, notice);
			}while(playerList != null);
			break;
			
		case goldRange:
			String[] gRange = request.split("_");
			min = Integer.parseInt(gRange[0]);
			max = Integer.parseInt(gRange[1]);
			do{
				playerList = BaseEngine.getInstace().getPlayerActionIpml().
						queryPlayersByGoldRange(min, max,ccount * CHECK_RANGE +1,(ccount + 1) * CHECK_RANGE);
				ccount++;		
				getConditionPlayers(playerList, notice);
			}while(playerList != null);
			break;
			
		case isOnline:
			int isOn = Integer.parseInt(request);
			do{
				playerList = BaseEngine.getInstace().getPlayerActionIpml().
						queryPlayersByOnline(isOn,ccount * CHECK_RANGE +1,(ccount + 1) * CHECK_RANGE);
				ccount++;		
				getConditionPlayers(playerList, notice);
			}while(playerList != null);
			break;
		case sexRange:
			int sex = Integer.parseInt(request);
			do{
				playerList = BaseEngine.getInstace().getPlayerActionIpml().
						queryPlayersBySex(sex,ccount * CHECK_RANGE +1,(ccount + 1) * CHECK_RANGE);
				ccount++;		
				getConditionPlayers(playerList, notice);
			}while(playerList != null);
			break;
		}
	}
	
	/**是否满足发送的条件**/
	private boolean isSendClientNotice(ClientToolNotice notice) {
		if(!DateUtil.isInTimeRange(notice.getStartTime(), notice.getEndTime())){
			return false;
		}
		String space = notice.getSendSpace();
		String[] sendSpace = space.split("\\|");
		if(sendSpace.length <= 0){
			return false;
		} 
		int sendNum = Integer.parseInt(sendSpace[0]);
		String sendTime = sendSpace[1];
		String[] sendTimes = sendTime.split("#");
		if(sendNum >= sendTimes.length){
			return false;
		}
		long runTime = Long.parseLong(sendTimes[sendNum]);
		long now = System.currentTimeMillis();
		if(now < runTime){
			return false;
		}
		return true;
	}

	/**获取指定玩家的数据**/
	private void getConditionPlayers(List<Player> playerList,ClientToolNotice notice){
		Set<Integer> sets = sendPlayerSet.get(notice.getId());
		if(playerList == null){
			sets.clear();
			sendPlayerSet.put(notice.getId(), sets);
			isRunClientNotice(notice);
			return;
		}
		int count = sendAccount.get(notice.getId());
		for(Player player : playerList){
			int playerId = player.getId();
			if(!sets.contains(playerId)){
				count++;
				sets.add(playerId);
				SendMessage msg = new SendMessage(playerId, notice.getNoticeMessage());
				messages.add(msg);
			}
		}
		sendAccount.put(notice.getId(), count);
	}
	
	
	/**获取充值玩家的数据**/
	private void getPayPlayers(List<PlayerPay> payList,ClientToolNotice notice){
		Set<Integer> sets = sendPlayerSet.get(notice.getId());
		if(payList == null){
			sets.clear();
			sendPlayerSet.put(notice.getId(), sets);
			isRunClientNotice(notice);
			return;
		}
		int count = sendAccount.get(notice.getId());
		for(PlayerPay pay : payList){
			int playerId = pay.getPlayerId();
			if(!sets.contains(playerId)){
				count++;
				sets.add(playerId);
				SendMessage msg = new SendMessage(playerId, notice.getNoticeMessage());
				messages.add(msg);
			}
		}
		sendAccount.put(notice.getId(), count);
	}


	/**判断是否玩家客户指定的发送次数**/
	private void isRunClientNotice(ClientToolNotice notice) {
		String space = notice.getSendSpace();
		String[] sendSpace = space.split("\\|");
		if(sendSpace.length <= 0){
			return;
		} 
		StringBuffer sb = new StringBuffer();
		int sendNum = Integer.parseInt(sendSpace[0]);
		sendNum++;
		String sendTime = sendSpace[1];
		sb.append(sendNum).append("|").append(sendTime);
		String[] sendTimes = sendTime.split("#");
		if(sendNum >= sendTimes.length){
			notice.setStatus(Constants.COMMON_TYPE_ONE);
		}
		int sendCount = sendAccount.get(notice.getId());
		notice.setSuccessSend(sendCount);
		BaseEngine.getInstace().getClientNoticeAction().updateClientNotice(notice);
		ResourceCache.getInstance().getClientNotices().put(notice.getId(), notice);
	}
	
	public LinkedBlockingDeque<SendMessage> getMessages() {
		return messages;
	}

}
