package com.orange.goldgame.server.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.juice.orange.game.cached.MemcachedResource;
import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.core.ErrorCode;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.AppConfig;
import com.orange.goldgame.domain.AppellationConfig;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.StakeConfig;
import com.orange.goldgame.exception.GoldException;
import com.orange.goldgame.server.domain.GameTable;
import com.orange.goldgame.server.domain.Gamer;
import com.orange.goldgame.server.domain.HandCards;
import com.orange.goldgame.util.RandomUtil;

/**
 * 机器人管理类
 * @author guojiang
 *
 */
public class RobotManager {
    
    private static final RobotManager instance = new RobotManager();
    private boolean isUseRobot = true;
//    private boolean isUseRobot = false;
    public static RobotManager getInstance() {
        return instance;
    }
    private final int robotNum = 100;
    private AtomicInteger robotId = new AtomicInteger(1);
    private final LinkedBlockingQueue<Gamer> robotList = new LinkedBlockingQueue<Gamer>();
//  private Map<Integer, Player> robotPlayers = new ConcurrentHashMap<Integer, Player>();
    private Random rd = new Random();
    private List<String> nameList = new ArrayList<String>();
    private List<String> manNameList = new ArrayList<String>();
    private List<String> cacheNameList = new ArrayList<String>();
    private List<String> cacheManNameList = new ArrayList<String>();
    private int count = 1;
    private Logger log = LoggerFactory.getLogger(RobotManager.class);
    /**
     * 初始化机器人
     */
    private RobotManager(){
        
        String cacheStr = ResourceCache.getInstance().getAppConfigs().get("robot_nickname").getAppValue();
        String names[] = cacheStr.split("###");
        for(String name : names){
            nameList.add(name);
        }
        
        String manStr = ResourceCache.getInstance().getAppConfigs().get("man_nickname").getAppValue();
        String manName[] = manStr.split("###");
        for(String name : manName){
            manNameList.add(name);
        }
        
        initCacheName();
        initManName();
        for(int i = 0 ; i < robotNum ; i++){
            addOneGamer();
        }
    }
    
    private synchronized void initCacheName(){
        cacheNameList.clear();
        for(String name : nameList){
            if(!name.equals("")){
                cacheNameList.add(name);
            }
        }
        Collections.shuffle(cacheNameList);
    }
    
    private synchronized void initManName(){
        cacheManNameList.clear();
        for(String name : manNameList){
            if(!name.equals("")){
                cacheManNameList.add(name);
            }
        }
        Collections.shuffle(cacheManNameList);
    }
    
    /**
     * 获取一个机器人
     * @return
     */
    public Gamer getOneGamer() {
        Gamer gamer = null;
        do{
            gamer  = robotList.poll();
            if (gamer == null) {
                addOneGamer();
                gamer  = robotList.poll();
            }
            if(System.currentTimeMillis()-gamer.getLastAddGame()>60*1000){
                break;
            }
        } while(true);
        
        return gamer;
    }
    /**
     * 增加一个机器人
     */
    private void addOneGamer(){
        Gamer gamer = new Gamer(-1);
        gamer.setPlayerId(-robotId.getAndIncrement());
        gamer.reset();
        
        gamer.setRobot(true);
        resetPlayer(gamer);
        gamer.setRobot(true);
        robotList.add(gamer);
    }
    
    private int geneteSex(){
        log.debug("rd:"+rd);
        int rdInt = rd.nextInt(1000);
        if(rdInt >= 700){
            return 0;
        }
        return 1;
    }
    
    private synchronized String getHeadImage(int sex){
        int rdnum = rd.nextInt(100);
        String picUrl = "";
        if(rdnum > 30){
            if(sex ==1){
                picUrl = "girl/"+"girl_"+(count%401)+".png";
                count++;
            }
            else{
                picUrl = "boy/"+"boy_"+(count%110)+".png";
                count++;
            }
            String url = ResourceCache.getInstance().getAppConfigs().
                    get(Constants.HEAD_SERVER_URL).getAppValue();
            String str = url+picUrl;
            return str;
        }
        else{
            int rdd = rd.nextInt(4);
            if(sex ==1){
                return (rdd +4)+"";
            }else{
                return rdd+"";
            }
        }
    }
    
    public synchronized String geneteNickName(int sex){
        if(sex == 1){
            if(cacheNameList.size()<=1){
                initCacheName();
            }
            return cacheNameList.remove(0);
        }else{
            if(cacheManNameList.size()<=1){
                initManName();
            }
            return cacheManNameList.remove(0);
        }
    }
    
    
    /**
     * 归还一个机器人
     * @param player
     */
    public void backOnePlayer(Gamer gamer){
        if (!gamer.isRobot()) {
            return;
        }
        resetPlayer(gamer);
        gamer.setLastAddGame(System.currentTimeMillis());
        robotList.add(gamer);
    }
    
    /**
     * 重置机器人
     * @param player
     */
    private void resetPlayer(Gamer gamer){
        log.debug("重置机器人");
        gamer.reset();
        getRobotPlayer(gamer.getPlayerId());
        
    }
    
    public Player getRobotPlayer(int player_id){
        Player player = new Player();
        player.setId(player_id);
        player.setAccount("robot");
        player.setAge(23);
        player.setDays(0);
        player.setIsGetAward(0);
        String key = getInfoKey(player_id);
        Object object = MemcachedResource.get(key);
        if (object != null) {
            log.debug(object.toString());
            String info[] = object.toString().split("#");
            player.setSex(Integer.parseInt(info[0]));
            player.setHeadImage(info[1]);
            player.setNickname(info[2]);
            player.setGolds(Integer.parseInt(info[3]));
            player.setCopper(Integer.parseInt(info[4]));
            player.setLoses(Integer.parseInt(info[5]));
            player.setWins(Integer.parseInt(info[6]));
        }else {
            player.setGolds(rd.nextInt(10)*20);
            player.setCopper(rd.nextInt(2000)*100);
            player.setSex(geneteSex());
            player.setLoses(rd.nextInt(300)+400);
            player.setWins(rd.nextInt(100)+100);
            player.setNickname(geneteNickName(player.getSex()));
            player.setHeadImage(getHeadImage(player.getSex()));
            savePlayer(player, key);
        }
        player.setAppellation(AppellationManager.getInstance().getAppellation(player.getCopper()));
        return player;
    }
    
    public static void savePlayer(Player player,String key){
        StringBuffer sb = new StringBuffer();
        sb.append(player.getSex()).append("#");
        sb.append(player.getHeadImage()).append("#");
        sb.append(player.getNickname()).append("#");
        sb.append(player.getGolds()).append("#");
        sb.append(player.getCopper()).append("#");
        sb.append(player.getLoses()).append("#");
        sb.append(player.getWins());
        MemcachedResource.save(key, sb.toString(), 15 * 24 * 60 * 60);
    }
    
    public String getInfoKey(int playerId){
        return "robot_info_" + playerId;
    }
    public String getPresentKey(int playerId){
        return "robot_present_" + playerId;
    }
    public int geneteCopper(){
        int result = 0;
        List<AppellationConfig> acl = ResourceCache.getInstance().getAppellationConfigs();
        int index = rd.nextInt(5);
        result = acl.get(acl.size()-index-1).getAmount()+100*rd.nextInt(500);
        return result;
    }

    /**
     * 检查本局是否胜利
     * @return
     */
    public boolean checkThisTurnsWin(){
        int win_pre = 70;
        AppConfig ac = ResourceCache.getInstance().getAppConfigs().get(Constants.ROBOT_WIN_PRE);
        if (ac != null) {
            win_pre = Integer.parseInt(ac.getAppValue());
        }
        int pre = ResourceCache.r.nextInt(100);
        if (pre > win_pre) {
            return false;
        }else {
            return true;
        }
    }
    
    
    /**
     * 检查自己本局是否会胜利
     * @param table
     * @return
     */
    public void checkPoke(Gamer gamer ,GameTable table){
        boolean win = false;
        LinkedBlockingQueue<Gamer> list = table.getGamingList();
        HandCards ownerCards = gamer.getHandCards();
        if (ownerCards == null) {
            return ;
        }
        HandCards hc = null;
        int res = 0;
        for (Gamer g : list) {
            if (g.getPlayerId() == gamer.getPlayerId()) {
                continue;
            }
            hc = g.getHandCards();
            res = ownerCards.compareTo(hc);
            if (res < 0) {
                win = false;
                break;
            }else {
                win = true;
            }
        }
        if (win) {
            win = checkThisTurnsWin();
        }
        if (win) {
            gamer.setWin(Constants.ROBOT_WIN);
        }else {
            gamer.setWin(Constants.ROBOT_FAL);
        }
    }
    
    /**
     * 获取机器人的下一个操作
     * @param gamer
     * @return 1跟牌，2叫牌，3比拍，4看牌，5弃牌，-1错误信息
     */
    private int getNextOperation(Gamer gamer){
        int res = 5;
        if (!gamer.isRobot()) {
            return -1;
        }
        if (gamer.getWin() == Constants.ROBOT_WIN) {
            String key = RandomUtil.getPreKey(ResourceCache.getInstance().getRobotPres().get(OPERATIONWIN));
            return Integer.parseInt(key);
        }else if (gamer.getWin() == Constants.ROBOT_FAL) {
            String key = RandomUtil.getPreKey(ResourceCache.getInstance().getRobotPres().get(OPERATIONFAIL));
            return Integer.parseInt(key);
        }
        return res;
    }
    
    /**
     * 获取机器人的下一个操作
     * @param gamer
     * @return 1跟牌，2叫牌，3比拍，4看牌，5弃牌，-1错误信息
     */
    public int nextOperation(Gamer gamer,GameTable table) throws GoldException{
        int operation = 0;
        if (gamer.isRobot()) {
            if (gamer != null && gamer.isRobot()) {
                operation = RobotManager.getInstance().getNextOperation(gamer);
                while (gamer.isLookCard() && operation == Constants.LOOK_CARD) {
                    operation = RobotManager.getInstance().getNextOperation(gamer);
                }
                
                switch (operation) {// 1跟牌，2叫牌，3比拍，4看牌，5弃牌，-1错误信息
                case Constants.FOLLOW_GOLD:
                    if(table.getTurns()>=GameTable.MAX_TURN){
                        operation = fightCart(table, gamer);
                    }else{
                        GameTableManager.followStake(table, gamer);
                    }
                    break;
                case Constants.ADD_GOLD:
                    if(table.getTurns()>=GameTable.MAX_TURN){
                        operation = fightCart(table, gamer);
                        break;
                    }
                    ErrorCode res =  getNextGold(table);
                    if(res.getStatus() == ErrorCode.SUCC){
                        GameTableManager.addStake(table, gamer, Integer.parseInt(res.getObject().toString()));
                        operation = Constants.ADD_GOLD;
                    }else {
                        GameTableManager.followStake(table, gamer);
                        operation = Constants.FOLLOW_GOLD;
                    }
                    break;
                case Constants.COMP_CARD:
                    operation = fightCart(table,gamer);
                    break;
                case Constants.LOOK_CARD:
                    GameTableManager.lookCard(table, gamer);
                    break;
                case Constants.GIVEUP_CARD:
                    if(table.getGamingList().size()>1){
                        GameTableManager.gamerGiveUp(table, gamer);
                    }
                    break;
                }
            }
        }
        return operation;
    }
    
    public int fightCart(GameTable table,Gamer gamer){
        int operation = Constants.COMP_CARD;
        Gamer com = null;
        List<Gamer> fightList = table.getFightCartList();
        int size = fightList.size();
        int res = 0;
        if (table.getTurns()<3 || size == 0) {
            GameTableManager.followStake(table, gamer);
            operation = Constants.FOLLOW_GOLD;
        }else {
            res = ResourceCache.r.nextInt(size);
            int times = 0;
            do {
                com = fightList.get(res);
                res = ResourceCache.r.nextInt(size);
                times ++;
            } while (com.getPlayerId() == gamer.getPlayerId() && times <= 10);
            
            if (table.getTurns() >= 20) {//直接比牌
                boolean win = GameTableManager.compareHandCard(table, gamer, com);
                table.setSrc(gamer);
                table.setDst(com);
                table.setWin(win);
            } else{
                if (com.getPlayerId() == gamer.getPlayerId() || (com.getUseBanCard() > 0 && table.getTurns() < 20)) {
                    GameTableManager.followStake(table, gamer);
                    operation = Constants.FOLLOW_GOLD;
                }else {
                    boolean win = GameTableManager.compareHandCard(table, gamer, com);
                    table.setSrc(gamer);
                    table.setDst(com);
                    table.setWin(win);
                }
            }
        }
        return operation;
    }
    
    private ErrorCode getNextGold(GameTable table){
        Map<Integer, StakeConfig> stakes = table.getStakeTypeMap();
        int single = table.getSingleStake();
        int id = -1;
        for (Integer key : stakes.keySet()) {
            if (stakes.get(key).getStakeNumber() > table.getSingleStake()) {
                if (single == table.getSingleStake()) {
                    single = stakes.get(key).getStakeNumber();
                    id = stakes.get(key).getId();
                }
                if (single > stakes.get(key).getStakeNumber()) {
                    single = stakes.get(key).getStakeNumber();
                    id = stakes.get(key).getId();
                }
                
            }
        }
        if (single != table.getSingleStake()) {
            return new ErrorCode(ErrorCode.SUCC, id);
        }
        return new ErrorCode(-1, -1);
    }
    /**
     * 获取扑克类型机器人的牌比较好
     * @param gamer
     * @return 1随机，2对子，3顺子，4同花，5同花顺，6三条
     */
    public int getPokeType(Gamer gamer){
        if (gamer.isRobot()) {
            String key = RandomUtil.getPreKey(ResourceCache.getInstance().getRobotPres().get(POKETYPE_ROBOT));
            return Integer.parseInt(key);
        }else {
            String key = RandomUtil.getPreKey(ResourceCache.getInstance().getRobotPres().get(POKETYPE_PLAYER));
            return Integer.parseInt(key);
        }
    }
    
//  public Player getRobotPlayer(int player_id){
//      return robotPlayers.get(player_id);
//  }
    
    /**
     * 玩家获取牌的概率 对子，顺子，同花等
     */
    private static final int POKETYPE_PLAYER = 0;
    /**
     * 机器人获取牌的概率 对子，顺子，同花等
     */
    private static final int POKETYPE_ROBOT = 1;
    /**
     * 机器人会赢时下次出牌概率
     */
    private static final int OPERATIONWIN = 2;
    /**
     * 机器人会输时下次出牌概率
     */
    private static final int OPERATIONFAIL = 3;

    public boolean isUseRobot() {
        return isUseRobot;
    }

    public void setUseRobot(boolean isUseRobot) {
        this.isUseRobot = isUseRobot;
    }
}
