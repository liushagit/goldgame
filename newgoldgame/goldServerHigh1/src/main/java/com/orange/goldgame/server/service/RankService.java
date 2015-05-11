package com.orange.goldgame.server.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import com.orange.goldgame.core.Constants;
import com.orange.goldgame.domain.Gift;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerProps;
import com.orange.goldgame.domain.Ranking;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.server.manager.PlayerInfoCenterManager;
import com.orange.goldgame.server.manager.RobotManager;

public class RankService {
	private static Map<Integer, List<Ranking>> rankMaps = new ConcurrentHashMap<Integer, List<Ranking>>();
	public static volatile long last_lodad_time  = 0;
	private static final int load_time =30 * 60 * 1000;  //每半个小时更新一次
	private static final int lowest_gold=1500000;  //财富榜的最低要求
	private static final int range_gold=50000;  //财富榜的最低要求
	private static final int lowest_exchange=5000;  //进兑换帮得最低要求
	private static final int range_exchange=300;  //进兑换帮得最低要求
	private static final int base_exchange=150;  //进兑换帮得最低要求
	private static final int range_charm=100;  //进兑换帮得最低要求
	private static final int lOWEST_RATE=30;  //最低胜利
	private static final int ranking_count=6;  //排行的最大数目
	private static final int EXCHANGE_ID=14;   //玩家兑换券所对应的ID
	private static final byte WEALTH_RANK=0;   //财富排行榜
	private static final byte EXCHANGE_RANK=1; //兑换排行榜
	private static final byte CHARM_RANK=2;   //魅力排行榜
	private static final byte MATCH_RANK=3;   //比赛排行榜
	private static final int GIFT_BASE=1000;  //礼品的基数
	private static final int FLOWER=1000/GIFT_BASE;   //鲜花
	private static final int EGG=1000/GIFT_BASE;  //鸡蛋
	private static final int CAR=10000/GIFT_BASE;  //小车
	private static final int HOUR=50000/GIFT_BASE; //洋房
	private static final int SHIP=100000/GIFT_BASE; //游轮
	private static final int FLOWER_TYPE=9;  
	private static final int EGG_YPE=10;
	private static final int CAR_TYPE=11;
	private static final int HOUR_TYPE=12;
	private static final int SHIP_TYPE=13;
	private static Random random=new Random();
	private static final int WIN_RATE=30;
	private static final int BAN_STATUS=1;
	
	public static List<Ranking> getRankByTypeId(int rankTypeId){
		//1 判断时间
		//2 从缓存或db加载
		List<Ranking> ranks = rankMaps.get(rankTypeId);
		if(ranks == null){
			addWealthRankRecords();
			addExchangeRankRecords();
			addGlamourRankRecords();
			addMatchRankRecords();
			ranks = BaseEngine.getInstace().getRankingAction().loadAllRankingByRankId(rankTypeId);
			rankMaps.put(rankTypeId, ranks);
		}else{
			if(System.currentTimeMillis() - last_lodad_time >= load_time){
				addWealthRankRecords();
				addExchangeRankRecords();
				addGlamourRankRecords();
				addMatchRankRecords();
				last_lodad_time = System.currentTimeMillis();
				ranks=BaseEngine.getInstace().getRankingAction().loadAllRankingByRankId(rankTypeId);
				rankMaps.put(rankTypeId, ranks);
			}		
		}
		ranks = rankMaps.get(rankTypeId);
		Collections.sort(ranks, new Comparator<Ranking>() {
			public int compare(Ranking ran1, Ranking ran2) {					
				return ran1.getRankValue()<=ran2.getRankValue()?1:-1;
			}
		});
		return ranks;
	}

	//财富榜
	public static void addWealthRankRecords() {
		List<Ranking> wealthRanks=new ArrayList<Ranking>();
		List<Player> players=BaseEngine.getInstace().getPlayerActionIpml().getAllPlayers();
		List<Player> beyond50W=new ArrayList<Player>();
		if(players==null||players.size()<=0){
			return;
		}
		for(Player p:players){
			if(p!=null&&p.getStatus()!=BAN_STATUS&&p.getCopper()>=lowest_gold&&!beyond50W.contains(p)){
				beyond50W.add(p);
			}
		}
				
		if(beyond50W!=null&&beyond50W.size()>0){
			wealthRanks=addPlayerIntoRank(beyond50W);
		}
				
		if(wealthRanks!=null&&wealthRanks.size()>0){
			wealthRanks=addOtherPlayerIntoRank(wealthRanks);			
		}else{		
			wealthRanks=addRobotIntoRank();	
		}
		
		updateRankRecords(wealthRanks);				
	}

	//更新排行榜记录
	private static void updateRankRecords(List<Ranking> rankRecords) {
		Collections.sort(rankRecords, new Comparator<Ranking>() {
			public int compare(Ranking r1, Ranking r2) {			
				return r1.getRankValue()<=r2.getRankValue()?1:-1;
			}
		});
		
		for(int i=1;i<=ranking_count;i++){
			Ranking rs=rankRecords.get(i-1);
			rs.setId(i);	
			BaseEngine.getInstace().getRankingAction().update(rs);
	    }
	}

	//添加机器人的数据入排行榜
	private static List<Ranking> addRobotIntoRank() {
		List<Ranking> wealthRanks=new ArrayList<Ranking>();
		for(int i=1;i<=ranking_count;i++){
			Ranking r=new Ranking();	
			int rankId=-i;
			int AIGold=random.nextInt(range_gold)+lowest_gold;
			r.setPlayerId(rankId);
			r.setPlayerName(RobotManager.getInstance().geneteNickName(random.nextInt(2)));
			r.setRankValue(AIGold);
			r.setRankType((int)WEALTH_RANK);
			wealthRanks.add(r);
		}
		return wealthRanks;
	}

	//添加机器人进入财富榜
	private static List<Ranking> addOtherPlayerIntoRank(List<Ranking> wealthRanks) {
		int newsize;
		newsize=wealthRanks.size();
		 Ranking te=wealthRanks.get(random.nextInt(newsize));
		 if(ranking_count-newsize>0&&ranking_count-newsize<ranking_count){
			 for(int i=1;i<=ranking_count-newsize;i++){
					Ranking r=new Ranking();	
					int AIGold=random.nextInt(range_gold)+te.getRankValue();  //以玩家目前的最高分为基准
				    int rankId=-i;
					r.setPlayerId(rankId);
					r.setPlayerName(RobotManager.getInstance().geneteNickName(random.nextInt(2)));
					r.setRankValue(AIGold);
					r.setRankType((int)WEALTH_RANK);
					wealthRanks.add(r);
			 }
		 }
		 return wealthRanks;
	}

	//把持有金币超过50W的玩家加入排行榜中
	private static List<Ranking> addPlayerIntoRank(List<Player> beyond50W) {
		List<Ranking> wealthRanks=new ArrayList<Ranking>();
		Collections.sort(beyond50W,new Comparator<Player>() {
			public int compare(Player p1, Player p2) {					
				return p1.getCopper()<=p2.getCopper()?1:-1;
			}
		});
		for(int i=0;i<beyond50W.size();i++){
			Player te=beyond50W.get(i);
			Ranking r=new Ranking();
			r.setPlayerId(te.getId());
			r.setPlayerName(te.getNickname());
			r.setRankValue(te.getCopper());
			r.setRankType((int)WEALTH_RANK);
			wealthRanks.add(r);
			PlayerInfoCenterManager.getInstance().addPlayerInfoCenter(te.getId(),true,Constants.RANK,"","恭喜您进入了财富排行榜！您目前的排行是"+(i+1)+"名！");
		}
		return wealthRanks;
	}
	
	//兑换榜
	public static void addExchangeRankRecords() {
		List<Ranking> exchangeRanks=new ArrayList<Ranking>();
		List<PlayerProps> playerProps=BaseEngine.getInstace().getPlayerPropsActionImpl().getAllPlayerExhangeCount(EXCHANGE_ID);
		if(playerProps!=null&&playerProps.size()>0){
			exchangeRanks=getPlayerPropRecords(playerProps);
		}
		
		if(exchangeRanks!=null&&exchangeRanks.size()>0){
			exchangeRanks=addPlayerExchangeRecords(exchangeRanks);			
		}else{
			exchangeRanks=addOtherExchangeRecords();	
		}
		
		Collections.sort(exchangeRanks, new Comparator<Ranking>() {
			public int compare(Ranking r1, Ranking r2) {			
				return r1.getRankValue()<=r2.getRankValue()?1:-1;
			}
		});		
		for(int i=1;i<=ranking_count;i++){
			Ranking rs=exchangeRanks.get(i-1);
			rs.setId(i+6);	
			BaseEngine.getInstace().getRankingAction().update(rs);	
	    }		
	}

	//模拟机器人兑换券的数据入数据库
	private static List<Ranking> addOtherExchangeRecords() {
		List<Ranking> exchangeRanks=new ArrayList<Ranking>();
		for(int i=1;i<=ranking_count;i++){
			Ranking r=new Ranking();	
			int baseExchange=base_exchange;   //默认的最低奖券数
			int AIGold=random.nextInt(range_exchange)+baseExchange;
		    int rankId=-i-6;
			r.setPlayerId(rankId);
			r.setPlayerName(RobotManager.getInstance().geneteNickName(random.nextInt(2)));
			r.setRankValue(AIGold);
			r.setRankType((int)EXCHANGE_RANK);
			exchangeRanks.add(r);
		}
		return exchangeRanks;
	}

	//添加新的奖券记录入数据库
	private static List<Ranking> addPlayerExchangeRecords(List<Ranking> exchangeRanks) {
		int newsize;
		newsize=exchangeRanks.size();	
		 Ranking te=exchangeRanks.get(random.nextInt(newsize));
		 if(ranking_count-newsize>0&&ranking_count-newsize<ranking_count){
			 for(int i=1;i<=ranking_count-newsize;i++){
					Ranking r=new Ranking();	
					int baseExchange=base_exchange;   //默认的最低奖券数
					if(te!=null){
						baseExchange=te.getRankValue();
					}
					int AIGold=random.nextInt(range_exchange)+baseExchange;
				    int rankId=-i-6;
					r.setPlayerId(rankId);
					r.setPlayerName(RobotManager.getInstance().geneteNickName(random.nextInt(2)));
					r.setRankValue(AIGold);
					r.setRankType((int)EXCHANGE_RANK);
					exchangeRanks.add(r);
			 }
		 }
		 return exchangeRanks;
	}

	private static List<Ranking> getPlayerPropRecords(List<PlayerProps> playerProps) {
		List<Ranking> exchangeRanks=new ArrayList<Ranking>();
		Collections.sort(playerProps, new Comparator<PlayerProps>() {
			public int compare(PlayerProps p1, PlayerProps p2) {			
				return p1.getNumber()<=p2.getNumber()?1:-1;
			}
		});
		for(int i=0;i<playerProps.size();i++){
			PlayerProps pp=playerProps.get(i);
			if(pp!=null){
				if(pp.getNumber()>lowest_exchange){
					Ranking r=new Ranking();
					Player p=PlayerService.getSimplePlayerByPlayerId(pp.getPlayerId());
					if(p!=null&&p.getStatus()!=BAN_STATUS){
						r.setPlayerId(pp.getPlayerId());
						r.setPlayerName(p.getNickname());
						r.setRankValue(pp.getNumber());
						r.setRankType((int)EXCHANGE_RANK);
						exchangeRanks.add(r);		
						PlayerInfoCenterManager.getInstance().addPlayerInfoCenter(p.getId(),true,Constants.RANK,"","恭喜您进入了兑换排行榜！您目前的排行是"+(i+1)+"名！");
					}
				}
			}
		}
		return exchangeRanks;
	}
	
	
	//魅力榜
	public static void addGlamourRankRecords() {
		Map<Integer, List<Gift>> giftMap=new HashMap<Integer, List<Gift>>();
		List<Player> players=BaseEngine.getInstace().getPlayerActionIpml().getAllPlayers();
		List<Ranking> charmRanks=new ArrayList<Ranking>();
		if(players!=null&&players.size()>0){
			for(Player p:players){
				List<Gift> gifts=BaseEngine.getInstace().getGiftActionIpml(p.getId()).findGiftByPlayerId(p.getId());
				if(gifts!=null&&gifts.size()>0){
					giftMap.put(p.getId(), gifts);
				}
			}			
		}
		if(giftMap!=null&&giftMap.size()>0){
			charmRanks=getPlayerCharmRecords(giftMap);			
		}
		
		//备注
		if(charmRanks!=null&&charmRanks.size()>0){
			charmRanks=addCharmRecords(charmRanks);			
		}else{
			charmRanks=addOtherCharmRecords();	
		}		
		Collections.sort(charmRanks, new Comparator<Ranking>() {
			public int compare(Ranking r1, Ranking r2) {	
				return r1.getRankValue()<=r2.getRankValue()?1:-1;
			}
		});		
		for(int i=1;i<=ranking_count;i++){
			Ranking rank=charmRanks.get(i-1);
			rank.setId(i+12);
			BaseEngine.getInstace().getRankingAction().update(rank);				
		}		
	}

	//模拟魅力榜记录入数据库
	private static List<Ranking> addOtherCharmRecords() {
		List<Ranking> charmRanks=new ArrayList<Ranking>();
		for(int i=1;i<=ranking_count;i++){
			Ranking r=new Ranking();					
			int AIGold=random.nextInt(range_charm)+range_charm;
		    int rankId=-i-12;
			r.setPlayerId(rankId);
			r.setPlayerName(RobotManager.getInstance().geneteNickName(random.nextInt(2)));
			r.setRankValue(AIGold);
			r.setRankType((int)CHARM_RANK);
			charmRanks.add(r);;
		}
		return charmRanks;
	}

	//添加魅力榜中的记录
	private static List<Ranking> addCharmRecords(List<Ranking> charmRanks) {
		int newsize;
		newsize=charmRanks.size();		 
		 if(ranking_count-newsize>0&&ranking_count-newsize<=ranking_count){
			 for(int i=1;i<=ranking_count-newsize;i++){
					Ranking r=new Ranking();					
					int AIGold=random.nextInt(range_charm)+range_charm;
				    int rankId=-i-12;
					r.setPlayerId(rankId);
					r.setPlayerName(RobotManager.getInstance().geneteNickName(random.nextInt(2)));
					r.setRankValue(AIGold);
					r.setRankType((int)CHARM_RANK);
					charmRanks.add(r);
			 }
		 }
		 return charmRanks;
	}

	//获取玩家魅力值记录
	private static List<Ranking> getPlayerCharmRecords(Map<Integer, List<Gift>> giftMap) {
		List<Ranking> charmRanks=new ArrayList<Ranking>();
		int i = 0;
		for(Map.Entry<Integer,List<Gift>> list:giftMap.entrySet()){
		    i++;
			Ranking r=new Ranking();
			int playerID=list.getKey();
			Player player=PlayerService.quaryPlayerByid(playerID);
			if(player!=null&&player.getStatus()!=BAN_STATUS){
				List<Gift> gs=list.getValue();
				r.setPlayerId(playerID);
				r.setPlayerName(player.getNickname());
				r.setRankValue(charmInfo(playerID,gs));
				r.setRankType((int)CHARM_RANK);
				charmRanks.add(r);
				PlayerInfoCenterManager.getInstance().addPlayerInfoCenter(player.getId(),true,Constants.RANK,"","恭喜您进入了魅力排行榜！您目前的排行是"+(i+1)+"名！");
			}
		}
		return charmRanks;
	}
	
	//比赛榜
	public static void addMatchRankRecords() {
		List<Ranking> matchRanks=new ArrayList<Ranking>();
		List<Player> players=BaseEngine.getInstace().getPlayerActionIpml().getAllPlayers();
		if(players!=null&&players.size()>0){
			List<Player> filterPlayers=filterPlayers(players);
			matchRanks=getPlayerMatchRecords(filterPlayers);			
		}
		if(matchRanks!=null&&matchRanks.size()>0){
			matchRanks=addMatchRecords(matchRanks);			
		}else{
			matchRanks=addOtherMatchRecords();
		}
		
		Collections.sort(matchRanks, new Comparator<Ranking>() {
			public int compare(Ranking r1, Ranking r2) {	
				return r1.getRankValue()<=r2.getRankValue()?1:-1;
			}
		});		
		for(int i=1;i<=ranking_count;i++){
			Ranking rank=matchRanks.get(i-1);
			rank.setId(i+18);
			BaseEngine.getInstace().getRankingAction().update(rank);				
		}
		
	}

	private static List<Player> filterPlayers(List<Player> players) {
		List<Player> list=new ArrayList<Player>();
		for(Player p:players){
			if(p!=null&&p.getStatus()!=BAN_STATUS){
				list.add(p);
			}
		}
		return list;
	}

	//模拟比赛数据入排行榜
	private static List<Ranking> addOtherMatchRecords() {
		List<Ranking> matchRanks=new ArrayList<Ranking>();
		for(int i=1;i<=ranking_count;i++){
			Ranking r=new Ranking();					
			int AIGold=random.nextInt(20)+WIN_RATE;
		    int rankId=-i-18;
			r.setPlayerId(rankId);
			r.setPlayerName(RobotManager.getInstance().geneteNickName(random.nextInt(2)));
			r.setRankValue(AIGold);
			r.setRankType((int)MATCH_RANK);
			matchRanks.add(r);
		}
		return matchRanks;
	}

	//添加玩家的比赛记录
	private static List<Ranking> addMatchRecords(List<Ranking> matchRanks) {
		int newsize;
		newsize=matchRanks.size();	 
		 if(ranking_count-newsize>0&&ranking_count-newsize<=ranking_count){
			 for(int i=1;i<=ranking_count-newsize;i++){
					Ranking r=new Ranking();					
					int AIGold=random.nextInt(20)+WIN_RATE;
				    int rankId=-i-18;
					r.setPlayerId(rankId);
					r.setPlayerName(RobotManager.getInstance().geneteNickName(random.nextInt(2)));
					r.setRankValue(AIGold);
					r.setRankType((int)MATCH_RANK);
					matchRanks.add(r);
			 }
		 }
		 return matchRanks;
	}

	//获取玩家的比赛记录
	private static List<Ranking> getPlayerMatchRecords(List<Player> players) {
		List<Ranking> matchRanks=new ArrayList<Ranking>();
		int i = 0;
		for(Player p:players){
		    i++;
			int rate=setWinRate(p.getLoses(),p.getWins());
			if(rate>=lOWEST_RATE){
				Ranking r=new Ranking();
				r.setPlayerId(p.getId());
				r.setPlayerName(p.getNickname());
				r.setRankValue(rate);
				r.setRankType((int)MATCH_RANK);
				matchRanks.add(r);		
				PlayerInfoCenterManager.getInstance().addPlayerInfoCenter(p.getId(),true,Constants.RANK,"","恭喜您进入了比赛排行榜！您目前的排行是"+(i+1)+"名！");
			}
		}
		return matchRanks;
	}
	
	

	//计算玩家的胜率
	private static int setWinRate(int loses, int wins) {
		int SCALE=10;
		int rounds=loses+wins;
		if(rounds <= 50){
			return 0;
		}
		BigDecimal bWins=new BigDecimal(wins);
		BigDecimal bRounds=new BigDecimal(rounds);		
		double result=bWins.divide(bRounds, SCALE, BigDecimal.ROUND_HALF_UP).doubleValue();
		int rate=(int)(result*100);
		return 	rate;
	}

	//计算玩家魅力值
	private static int charmInfo(int playerId,List<Gift> giftList) {
		int result=0;
		if(giftList==null||giftList.size()<1){
			return result;
		}
		for(Gift g:giftList){
			int score=0;
			int type=g.getPropId();
			int num=g.getAmont();
			switch (type) {
			case FLOWER_TYPE:	
				score=num*FLOWER;
				break;
			case EGG_YPE:	
				score=num*EGG;
				break;
			case CAR_TYPE:		
				score=num*CAR;
				break;
			case HOUR_TYPE:		
				score=num*HOUR;
				break;
			case SHIP_TYPE:	
				score=num*SHIP;
				break;
			default:
				score=0;
				break;
			}
			result+=score;
		}						
       return result;
	}
	
	
			
	

}
