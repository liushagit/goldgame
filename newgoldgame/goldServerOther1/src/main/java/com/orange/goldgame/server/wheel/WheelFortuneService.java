package com.orange.goldgame.server.wheel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.juice.orange.game.cached.MemcachedResource;
import com.juice.orange.game.container.GameRoom;
import com.juice.orange.game.container.GameRoomImpl;
import com.juice.orange.game.container.GameSession;
import com.juice.orange.game.log.LoggerFactory;
import com.juice.orange.game.log.LoggerName;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.cache.action.PropCacheAction;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.core.ErrorCode;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.AppConfig;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerProps;
import com.orange.goldgame.domain.PlayerWheelRecoder;
import com.orange.goldgame.domain.PlayerWheelReward;
import com.orange.goldgame.domain.PlayerWheelTimes;
import com.orange.goldgame.domain.PropsConfig;
import com.orange.goldgame.domain.RGB;
import com.orange.goldgame.domain.WheelReward;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.server.manager.NoticeManager;
import com.orange.goldgame.server.manager.RobotManager;
import com.orange.goldgame.server.manager.SessionManger;
import com.orange.goldgame.server.service.BaseServer;
import com.orange.goldgame.server.service.GoldService;
import com.orange.goldgame.server.service.PlayerService;
import com.orange.goldgame.util.DateUtil;
import com.orange.goldgame.util.RandomUtil;
import com.orange.goldgame.util.URLUtil;
import com.orange.goldgame.vo.WheelMessage;

public class WheelFortuneService {

	public static final int TICKETID = 15;//抽奖卷id
	private static final int TICKETAMOUNT = 1;//每次抽奖需要消耗的抽奖卷
	public static final int ONE_PROP = 500;//
	public static final int ONE_YUAN_YIDONG = 307;//1分钱id
	public static final int ONE_YUAN_DIANXIN = 401;//1元钱id
	public static final int ONE_YUAN_LIANTONG = 501;//1元钱id
	public static final int LOTTERY_PLAYER= 1;//获取抽奖结果时玩家的概率
	public static final int LOTTERY_ROBOT= 2;//获取抽奖结果时机器人的概率
	public static final int RECODER_WAIT = 1;//获得实物奖励时没处理状态
	public static final int RECODER_SUCC = 0;//获得实物奖励时处理状态
	
	public static final int self_r = 254;
	public static final int self_g = 218;
	public static final int self_b = 61;
	
	public static final int other_r = 25;
	public static final int other_g = 25;
	public static final int other_b = 25;
	
	public static GameRoom WHEELROOM = new GameRoomImpl("-10086");
	public static LinkedBlockingQueue<WheelMessage> allMsg = new LinkedBlockingQueue<WheelMessage>();
	public static int MAX_MSG_SIZE = 10;
	public static List<Integer> addMsg = new ArrayList<Integer>();
	private static Logger logger = LoggerFactory.getLogger(LoggerName.GOLD);
	
	static{
		addMsg.add(2);
		addMsg.add(3);
	}
	
	/************** 初始化界面 *********************/
	/**
	 * 获取转盘信息
	 * @return
	 */
	public static ErrorCode getWheelFortune(){
		ErrorCode result = new ErrorCode(ErrorCode.SUCC);
		Map<Integer, WheelReward> wheelReward = ResourceCache.getInstance().getWheelReward();
		List<WheelReward> list = new ArrayList<WheelReward>();
		list.addAll(wheelReward.values());
		Collections.sort(list, new Comparator<WheelReward>() {

			@Override
			public int compare(WheelReward o1, WheelReward o2) {
				return o1.getId() - o2.getId();
			}

		});
		result.setObject(list);
		return result;
	}
	/**
	 * 获取玩家兑奖卷数量
	 * @param player
	 * @return
	 */
	public static int getTicket(Player player){
		PlayerProps pp = PropCacheAction.getplayerPropByPropid(player, TICKETID);
		return pp == null ? 0 : pp.getNumber();
	}
	
	/**
	 * 获取大转盘奖励信息
	 * @return
	 */
	public static List<ErrorCode> getWheelMsg(Player player,boolean reInit){
		if (player == null) {
			if (reInit) {
				initAllMsg(1);
			}
			return null;
		}
		List<WheelMessage> playerWheelMessages = player.getPlayerWheelMessages();
		List<WheelMessage> self = new ArrayList<WheelMessage>();
		self.addAll(playerWheelMessages);
		if (allMsg.size() < MAX_MSG_SIZE) {
			initAllMsg(0);
		}
		if (ResourceCache.r.nextBoolean() && reInit) {
			initAllMsg(1);
		}
		self.addAll(allMsg);
		
		Collections.sort(self, new Comparator<WheelMessage>() {
			@Override
			public int compare(WheelMessage o1, WheelMessage o2) {
				return (int)(o2.getCreateTime().getTime() - o1.getCreateTime().getTime());
			}
		});
		List<ErrorCode> list = new ArrayList<ErrorCode>();
		ErrorCode code = null;
		RGB rgb = null;
		for (WheelMessage wm : self) {
			rgb = new RGB();
			if (wm.getPlayerId() == player.getId().intValue()) {
				rgb.setR(self_r);
				rgb.setG(self_g);
				rgb.setB(self_b);
			}else {
				rgb.setR(other_r);
				rgb.setG(other_g);
				rgb.setB(other_b);
			}
			code = new ErrorCode(ErrorCode.SUCC, wm.getMsg(),rgb);
			list.add(code);
		}
		if (list.size() > MAX_MSG_SIZE) {
			return list.subList(0, MAX_MSG_SIZE);
		}else {
			return list;
		}
	}
	
	private static void initAllMsg(int type){
		String name = "";
		WheelMessage wm = null;
		WheelReward wr = null;
		if (type == 0) {
			for (int i = 0; i < MAX_MSG_SIZE; i++) {
				name = RobotManager.getInstance().geneteNickName(ResourceCache.r.nextInt(2));
				wr = getOneReward(LOTTERY_ROBOT,null);
				wm = new WheelMessage();
				wm.setCreateTime(new Date());
				wm.setMsg("玩家" + name + "抽中" + wr.getName());
				wm.setPlayerId(-1);
				addOneMessage(wm);
			}
			
		}else {
			name = RobotManager.getInstance().geneteNickName(ResourceCache.r.nextInt(2));
			wr = getOneReward(LOTTERY_ROBOT,null);
			wm = new WheelMessage();
			wm.setCreateTime(new Date());
			wm.setMsg("玩家" + name + "抽中" + wr.getName());
			NoticeManager.getInstance().sendPublicMessage(null, "恭喜玩家" + name +"抽中" + wr.getName());
			addOneMessage(wm);
		}
	}
	
	/**********************************************/
	
	/**
	 * 检查玩家抽奖卷信息
	 * @param player
	 * @return
	 */
	public static boolean checkTicket(Player player){
		int amount = getTicket(player);
		return amount > TICKETAMOUNT;
	}
	
	/**
	 * 检查玩家今天是否是第一次抽奖
	 * @param player
	 * @return
	 */
	public static boolean checkToday(Player player){
		PlayerWheelReward pwr = player.getPlayerWheelReward();
		if (pwr == null) {
			return true;
		}
		return DateUtil.isSameDay(System.currentTimeMillis(), pwr.getRewardTime().getTime());
	}
	
	/**
	 * 抽奖
	 * @param player
	 * @return
	 */
	public static ErrorCode lottery(Player player,int lotteryType){
		PlayerWheelReward pwr = player.getPlayerWheelReward();
		if (pwr == null) {
			pwr = BaseEngine.getInstace().getPlayerWheelRewardAction().quaryPlayerWheelReward(player.getId());
		}
		if (pwr == null) {
			pwr = createPlayerWheelReward(player);
		}
		if (pwr == null) {
			return new ErrorCode(-1, "抽奖错误");
		}
		player.setPlayerWheelReward(pwr);
		ErrorCode res = null;
		switch (lotteryType) {
		case 1://抽奖卷
			res = lotteryByProp(player);
			break;
		case 2://1分钱
		case 3://1块钱
			res = lotteryByMoney(player, lotteryType);
			break;

		default:
			return new ErrorCode(-1, "抽奖错误");
		}
		if (res.getStatus() != ErrorCode.SUCC) {
			return res;
		}
		
		logger.info("wheellottery|" + player.getId() + "|" + lotteryType);
		pwr.setRewardTime(new Date());
		BaseEngine.getInstace().getPlayerWheelRewardAction().updatePlayerWheelReward(pwr);
		WheelReward wr = (WheelReward) res.getObject();
		PlayerWheelRecoder recoder = addReward(player, wr);
		if (recoder != null) {
			res.setMsg(recoder.getId()+"");
			player.getPlayerWheelRecoders().put(recoder.getId(), recoder);
		}else {
			res.setMsg("-1");
		}
		WheelMessage wm = null;
		if (addMsg.contains(wr.getRewardsType().intValue())) {
			wm = new WheelMessage();
			wm.setMsg("玩家" + player.getNickname() + "抽中" + wr.getName());
			wm.setPlayerId(player.getId());
			wm.setCreateTime(new Date());
			addOneMessage(wm);
			sendMessage(null,null);
		}
		wm = new WheelMessage();
		wm.setMsg("您抽中" + wr.getName());
		wm.setPlayerId(player.getId());
		wm.setCreateTime(new Date());
		player.getPlayerWheelMessages().add(wm);
		if (addMsg.contains(wr.getRewardsType().intValue()) || wr.getRewards() >= 10000) {
			NoticeManager.getInstance().sendPublicMessage(null, "恭喜玩家" + player.getNickname() +"抽中" + wr.getName());
		}
		return res;
	}
	
	private static PlayerWheelRecoder addReward(Player player,WheelReward wr){
		if (player == null || wr == null) {
			return null;
		}
		
		if (wr.getRewardsType() == 1) {//金币
			GoldService.addCopper(player, wr.getRewards());
			return null;
		}
		if (wr.getRewardsType() == 2) {//宝石
			GoldService.addGold(player, wr.getRewards());
			return null;
		}
		if (wr.getRewardsType() == 3) {//话费
			return createPlayerWheelRecoder(player, wr);
		}
		return null;
	}
	
	private static void addOneMessage(WheelMessage wm){
		allMsg.add(wm);
		if (allMsg.size() > MAX_MSG_SIZE) {
			allMsg.poll();
		}
	}
	
	private static PlayerWheelRecoder createPlayerWheelRecoder(Player player,WheelReward wr){
		PlayerWheelRecoder pwr = new PlayerWheelRecoder();
		pwr.setPlayerId(player.getId());
		pwr.setChangeStatus(RECODER_WAIT);
		pwr.setCreateTime(new Date());
		pwr.setPlayerPhone("");
		pwr.setWheelRewardId(wr.getId());
		pwr.setUpdateTime(DateUtil.getDate("20130331"));
		int id = BaseEngine.getInstace().getPlayerWheelRecoderAction().insertOneRecoder(pwr);
		if (id < 0) {
			return null;
		}
		pwr.setId(id);
		return pwr;
	}
	private static ErrorCode lotteryByProp(Player player){
		PropsConfig pc = ResourceCache.getInstance().getPropsConfigs().get(TICKETID);
		boolean res = PropCacheAction.releasePlayerProp(player, TICKETID);
		if (res) {
			WheelReward wr = getOneReward(LOTTERY_PLAYER,player);
			while (wr == null) {
				wr = getOneReward(LOTTERY_PLAYER,player);
			}
			return new ErrorCode(ErrorCode.SUCC,wr);
		}else {
			return new ErrorCode(-1, "没有" + pc.getName());
		}
	}
	private static ErrorCode lotteryByMoney(Player player,int type){
		WheelReward wr = getOneReward(LOTTERY_PLAYER,player);
		while (wr == null) {
			wr = getOneReward(LOTTERY_PLAYER,player);
		}
		return new ErrorCode(ErrorCode.SUCC,wr);
	}
	private static PlayerWheelReward createPlayerWheelReward(Player player) {
		PlayerWheelReward pwr = new PlayerWheelReward();
		pwr.setPlayerId(player.getId());
		pwr.setRewardTime(new Date());
		int id = BaseEngine.getInstace().getPlayerWheelRewardAction().insertPlayerWheelReward(pwr);
		pwr.setId(id);
		return id < 0 ? null : pwr;
	}
	
	/**
	 * 玩家增加抽奖卷
	 * @param player
	 * @param amount
	 */
	public static void addTicket(Player player , int amount){
		PropCacheAction.addProp(player, TICKETID, amount);
	}
	
	/**
	 * 检查玩家是不是第一次抽奖
	 * @param player
	 * @return
	 */
	private static boolean checkFirst(Player player){
		if (player == null) {
			return false;
		}
		PlayerWheelTimes pwt = player.getPlayerWheelTimes();
		if (pwt == null) {
			pwt = BaseEngine.getInstace().getPlayerWheelTimesAction().quaryPlayerWheelTimes(player.getId());
			if (pwt == null) {
				return true;
			}
			
			player.setPlayerWheelTimes(pwt);
		}
		return false;
	}
	
	private static PlayerWheelTimes createPlayerWheelTimes(Player player){
		PlayerWheelTimes pwt = new PlayerWheelTimes();
		pwt.setCreateTime(new Date());
		pwt.setPlayerId(player.getId());
		int id = BaseEngine.getInstace().getPlayerWheelTimesAction().insert(pwt);
		if (id <= 0) {
			return null;
		}
		pwt.setId(id);
		player.setPlayerWheelTimes(pwt);
		return pwt;
	}
	private static int first[] = {12,10};
	
	/**
	 * 获取奖励结果，1、真实玩家，2、其他类型
	 * @param type
	 * @return
	 */
	private static WheelReward getOneReward(int type,Player player){
		Map<Integer, WheelReward> wheelReward = ResourceCache.getInstance().getWheelReward();
		if (checkFirst(player)) {
			int id = first[ResourceCache.r.nextInt(first.length)];
			WheelReward wr = wheelReward.get(id);
			createPlayerWheelTimes(player);
			return wr;
		}
		
		Map<String, Integer> pre = new HashMap<String, Integer>();
		if (type == LOTTERY_PLAYER) {
			for (WheelReward wr : wheelReward.values()) {
				pre.put(wr.getId()+"", wr.getPlayerPre());
			}
		}else {
			for (WheelReward wr : wheelReward.values()) {
				pre.put(wr.getId()+"", wr.getRobotPre());
			}
		}
		
		String key = RandomUtil.getPreKey(pre);
		WheelReward wr = wheelReward.get(Integer.parseInt(key));
		if (LOTTERY_ROBOT == type) {
			return wr;
		}
		AppConfig ac = ResourceCache.getInstance().getAppConfigs().get("wheelreward");
		String ky[] = ac.getAppValue().split("\\|");
		String ids[];
		int times = -1;
		for (int i = 0; i < ky.length; i++) {
			ids = ky[i].split("_");
			if (wr.getId().intValue() == Integer.parseInt(ids[0])) {
				times = 0;
				String wheelKey = getKey(wr.getId());
				Object o = MemcachedResource.get(wheelKey);
				if (o != null) {
					times = Integer.parseInt(o.toString());
				}
				if (times >= Integer.parseInt(ids[1])) {
					return null;
				}
				times ++;
				MemcachedResource.save(wheelKey, times, 24 * 60 * 60);
				break;
			}
		}
		return wr;
	}
	
	private static String getKey(int id){
		return "wheel_id_" + DateUtil.getDateDesc() + "_" + id;
	}
	public static void addSession(GameSession session){
		WHEELROOM.addSession(session);
	}
	
	public static void removeSession(List<Integer> playerids){
		GameSession session = null;
		for (Integer id : playerids) {
			session = SessionManger.getInstance().getSession(id);
			if (session != null) {
				WHEELROOM.removeSession(session.getSessionId());
			}
		}
	}
//	public static long sendTime = 0;
	public static void sendMessage(GameSession selfSession , OutputMessage out){
		
		if (selfSession != null) {
			selfSession.sendMessage(Protocol.RESPONSE_WHEEL_MESSAGE, out);
		}else {
			if (ResourceCache.r.nextBoolean()) {
				return;
			}
			WheelFortuneService.getWheelMsg(null,true);
			Player player = null;
			
			List<GameSession> offline = new ArrayList<GameSession>();
			GameSession tmpSession = null;
			for (GameSession session : WHEELROOM.getSessionList()) {
				player = PlayerService.getOnLinePlayer(session);
				if (player == null) {
					offline.add(session);
				}else {
					tmpSession = SessionManger.getInstance().getSession(player.getId());
					if (tmpSession == null || !tmpSession.getSessionId().equals(session.getSessionId())) {
						offline.add(session);
					}
				}
			}
			
			for (GameSession session : offline) {
				WHEELROOM.removeSession(session.getSessionId());
			}
				out = new OutputMessage();
				out.putShort((short)1);
				WheelMessage wm = allMsg.peek();
				out.putString(wm.getMsg());
				out.putInt(other_r);
				out.putInt(other_g);
				out.putInt(other_b);
				WHEELROOM.sendMessage(Protocol.RESPONSE_WHEEL_MESSAGE, out);
		}
		
//		System.out.println("===========" + wm.getMsg() + "|"+  WHEELROOM.getSessionList().size() + "|" + (System.currentTimeMillis() - begin));
	}
	
	/**
	 * 玩家填写中奖信息
	 * @param player
	 * @param phone
	 * @param playerWheelRecorderId
	 */
	public static boolean leaveMessage(Player player,String phone,int playerWheelRecorderId){
		if (phone == null) {
			BaseServer.sendErrorMsg(SessionManger.getInstance().getSession(player.getId()), (short)-1, "填写信息不能为空");
			return false;
		}
		PlayerWheelRecoder recoder = player.getPlayerWheelRecoders().get(playerWheelRecorderId);
		if (recoder == null) {
			recoder = BaseEngine.getInstace().getPlayerWheelRecoderAction().quaryById(playerWheelRecorderId);
		}
		if (recoder == null) {
			return false;
		}
		if (recoder.getPlayerId().intValue() != player.getId().intValue()) {
			BaseServer.sendErrorMsg(SessionManger.getInstance().getSession(player.getId()), (short)-1, "不能修改其他玩家的信息");
			return false;
		}
		recoder.setPlayerPhone(phone);
		BaseEngine.getInstace().getPlayerWheelRecoderAction().updateRecoder(recoder);
		return true;
	}
	
	public static void sendResult(Player player,ErrorCode res,GameSession session){
		if (res.getStatus() != ErrorCode.SUCC) {
			BaseServer.sendErrorMsg(session, (short)-1, res.getMsg());
			return;
		}
		
		OutputMessage out = new OutputMessage();
		WheelReward reward = (WheelReward) res.getObject();
		out.putByte(reward.getId().byteValue());
		out.putString(reward.getName());
		out.putByte(reward.getRewardsType().byteValue());
		out.putInt(player.getCopper());
		out.putInt(player.getGolds());
		out.putInt(Integer.parseInt(res.getMsg()));
		PlayerProps pp = PropCacheAction.getplayerPropByPropid(player, WheelFortuneService.TICKETID);
		out.putInt(pp == null ? 0 : pp.getNumber());
		session.sendMessage(Protocol.RESPONSE_WHEEL_LOTTERY, out);
	}
	/**
	 * 玩家离开大转盘
	 * @param player
	 */
	public static void leaveWheel(Player player) {
		if (player == null) {
			return;
		}
		List<GameSession> sessions = WHEELROOM.getSessionList();
		List<GameSession> leave = new ArrayList<GameSession>();
		Player player2 = null;
		for (GameSession session : sessions) {
			player2 = (Player) session.getObject(Constants.PLAYER_KEY);
			if (player2.getId().intValue() == player.getId().intValue()) {
				leave.add(session);
			}
		}
		for (GameSession s : leave) {
			WHEELROOM.removeSession(s.getSessionId());
		}
	}
	
	public static final String BEGINTIME = "2014-03-10 00:00:00";
	
	public static String getWheelRecoder(String startTime,String endTime,int status){
		return getWheelRecoder(startTime, endTime, status, 0);
	}
	public static String getWheelRecoder(String startTime,String endTime,int status,int id){
		List<PlayerWheelRecoder> list = BaseEngine.getInstace().getPlayerWheelRecoderAction().quaryByTime(startTime, endTime,status);
		JSONObject json = new JSONObject();
		try {
			JSONArray jArray = new JSONArray();
			if (list != null) {
				
				for (PlayerWheelRecoder pwr : list) {
					if (pwr.getId().intValue() == id) {
						continue;
					}
					pwr.setRewardName(ResourceCache.getInstance().getWheelReward().get(pwr.getWheelRewardId()).getName());
					jArray.add(JSONArray.fromObject(pwr));
				}
			}
			json.put("data", jArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String string = json.toString();
		string = URLUtil.encode(string);
		System.out.println(string.length());
		return string;
	}
	
	public static boolean updateWheelRecoder(int id){
		PlayerWheelRecoder pwr = BaseEngine.getInstace().getPlayerWheelRecoderAction().quaryById(id);
		if (pwr != null) {
			pwr.setChangeStatus(RECODER_SUCC);
			pwr.setUpdateTime(new Date());
			BaseEngine.getInstace().getPlayerWheelRecoderAction().updateRecoder(pwr);
			return true;
		}
		return false;
	}
}
