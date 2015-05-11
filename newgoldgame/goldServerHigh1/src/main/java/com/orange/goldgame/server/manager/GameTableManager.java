package com.orange.goldgame.server.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;

import org.apache.log4j.Logger;

import com.juice.orange.game.cached.MemcachedResource;
import com.juice.orange.game.container.GameRoom;
import com.juice.orange.game.container.GameSession;
import com.juice.orange.game.log.LoggerFactory;
import com.juice.orange.game.log.LoggerName;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.cache.action.PropCacheAction;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.AreaConfig;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.StakeConfig;
import com.orange.goldgame.exception.GoldException;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.server.domain.Area;
import com.orange.goldgame.server.domain.GameTable;
import com.orange.goldgame.server.domain.GameTableSeat;
import com.orange.goldgame.server.domain.Gamer;
import com.orange.goldgame.server.domain.HandCards;
import com.orange.goldgame.server.domain.MatchTableSetManager;
import com.orange.goldgame.server.emun.GamerState;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.server.exception.GoldenNOTEnoughException;
import com.orange.goldgame.server.service.GameServer;
import com.orange.goldgame.server.service.GoldService;
import com.orange.goldgame.server.service.PlayerService;
import com.orange.goldgame.server.task.GameReadyThread;
import com.orange.goldgame.server.task.GameStakeThread;

/**
 * 用于管理所有跟桌子有关的逻辑
 * @author yesheng
 * 
 */
public class GameTableManager {
    public static final int maxTurn = 20;
    private static Random r = new Random();

    private static Logger log = LoggerFactory.getLogger(GameTableManager.class);
    private static final Logger identy_log = LoggerFactory.getLogger(LoggerName.IDENTIFY);
    private static final Logger gold_log = LoggerFactory.getLogger(LoggerName.GOLD);

    /**
     * 删除所有不在线的玩家
     * 
     * @throws GoldException
     */
    public static void removeUnOnlineGamer(GameTable table)
            throws GoldException, GoldenNOTEnoughException {
        Lock lock = table.getR();
        try {
            lock.lock();
            for (Gamer gamer : table.getReallyGamerList()) {
                if (gamer == null || gamer.isRobot())
                    return;
                GameSession session = SessionManger.getInstance().getSession(
                        gamer.getPlayerId());
                if (session == null) {
                    log.debug("正在清理不在线的玩家：" + gamer.getPlayerId());
                    boolean isCurr = table.getCurrentPlayerId() == gamer
                            .getPlayerId();
                    GameRoom room = GameRoomManager.getInstance()
                            .getRoomByTable(table);
                    removeGamer(table, gamer);
                    if (room == null)
                        return;
                    if (table.getGamingList().contains(gamer)) {
                        GameServer.sendGiveMessage(gamer, room);
                        if (isCurr) {
                            GameServer.responseNextGamer(room);
                        }
                    }
                    GameServer.broadcastExitRoom(room, gamer.getPlayerId());
                    log.debug("清理不在线的玩家：" + gamer.getPlayerId());
                }
            }
        } catch (Exception e) {
            if (e instanceof GoldenNOTEnoughException)
                throw (GoldenNOTEnoughException) e;
            if (e instanceof GoldException)
                throw (GoldException) e;
            log.error("lock", e);
        } finally {
            lock.unlock();
        }

    }

    /**
     * 给桌子加入玩家
     * 
     * @param table
     * @param gamer
     */
    public static void addGamer(GameTable table, Gamer gamer)
            throws GoldException, GoldenNOTEnoughException {
        Lock lock = table.getR();
        try {
            lock.lock();
            if (table.isFull()) {
                log.debug("游戏桌人数已满，禁止进入……");
                throw new GoldException("游戏桌已满！");
            }
            if (table.getInning() != 0 && isMatch(table)) {
                throw new GoldException("比赛场已开始，不允许进入！");
            }
            gamer.setFriendId(0);
            if (table.getGamerSeat(gamer) == null) {
                log.debug("玩家加入游戏桌……:" + gamer.getPlayerId());
                table.addGamer(gamer);
                table.increaseCurrentNum();
            }
            gamer.setLeftGolds(PlayerService.getCachePlayerByGamer(gamer)
                    .getCopper());
            GamerSet.getInstance().putGamer(gamer);

            // 如果是比赛场
            if (isMatch(table)) {
                table.addJoinedGamer(gamer);
                gamer.setLeftGolds(table.getGamerGolds());
                // TODO
                if (table.getJoinedGamerSize() >= GameTable.DEFAULT_SEAT_SIZE) {
                	//将自己的session加入到room
                	GameRoom room = GameRoomManager.getInstance().getRoomByTable(table);
                	room.addSession(SessionManger.getInstance().getSession(gamer.getPlayerId()));
                    startMatch(table);
                }
            }else {
            	if (!gamer.isRobot()) {
            		MemcachedResource.save(Constants.PLAYER_STATUS_KEY + gamer.getPlayerId(), Constants.PLAYER_STATUS_MATCH+"_" + 2 +"_" + table.getAreaId(), 24 * 60 * 60);
            	}
			}
        } catch (Exception e) {
            if (e instanceof GoldenNOTEnoughException)
                throw (GoldenNOTEnoughException) e;
            if (e instanceof GoldException)
                throw (GoldException) e;
            log.error("lock", e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 添加观看玩家
     * 
     * @param table
     * @param gamer
     */
    public static void addWatchGamer(GameTable table, Gamer gamer) {
        Lock lock = table.getR();
        try {
            lock.lock();
            log.debug("玩家进入观看……:" + gamer.getPlayerId());
            table.getWatchList().add(gamer);
            gamer.reset();
            GamerSet.getInstance().putGamer(gamer);
        } catch (Exception e) {
            if (e instanceof GoldenNOTEnoughException)
                throw (GoldenNOTEnoughException) e;
            if (e instanceof GoldException)
                throw (GoldException) e;
            log.error("lock", e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 给桌子移除玩家
     * 
     * @param table
     * @param gamer
     */
    public static void removeGamer(GameTable table, Gamer gamer)
            throws GoldException, GoldenNOTEnoughException {

        if (isMatch(table)) {
            table.removeJoinedGamer(gamer);
        }

        if (!isMatch(table)) {
            if (gamer.getState() == GamerState.GAME_READY) {
                table.setLastOperationTime(System.currentTimeMillis());
                gamer.unReady();
            }
        }
        
        // 如果正在游戏中，先放弃
        if (table.getGamingList().contains(gamer)) {
            giveUp(table, gamer);
        }

        log.debug("玩家退出游戏桌……" + gamer.getPlayerId());
        if(table.getGamerSeat(gamer)!=null){
            table.removeGamer(gamer);
            for(Gamer watchGamer : table.getWatchList()){
                if(gamer.getPlayerId() == watchGamer.getFriendId()){
                    table.getWatchList().remove(watchGamer);
                }
            }
        }
        
        if(table.getWatchList().contains(gamer)){
            table.getWatchList().remove(gamer);
        }

        gamer.setFriendId(0);
        table.decreaseCunrrentNum();

        GamerSet.getInstance().removeGamerByGamer(gamer);
        
        if (!gamer.isRobot()) {
			MemcachedResource.save(Constants.PLAYER_STATUS_KEY + gamer.getPlayerId(), Constants.PLAYER_STATUS_ONLINE, 24 * 60 * 60);
		}
    }

    /**
     * 开始比赛
     * 
     * @param table
     */
    public static void startMatch(GameTable table)
            throws GoldException {
        if (table.getJoinedGamerSize() <= 0) {
            throw new GoldException("比赛已关闭");
        }

        if (table.getInning() == 0) {
            table.setInning(1);
        }

        if (table.getInning() == 1) {
            // 如果进入比赛场，则扣除报名费
        	List<Gamer> remove = new ArrayList<Gamer>();
            for (Gamer gamer : table.getJoinedGameList()) {
                if (GameTableManager.isMatch(table) && !gamer.isRobot()) {
                    Player player = PlayerService.getCachePlayerByGamer(gamer);
                    try {
                    	GoldService.consumeInCopper(player, table.getLimitGolds());
					} catch (Exception e) {
						remove.add(gamer);
						
					}
                }
            }
            
            for (Gamer g : remove) {
            	GameServer.exitRoomByPlayerId(g.getPlayerId());
			}
        }

        table.resetDealCardNum();

        for (Gamer joingamer : table.getJoinedGameList()) {
            if (joingamer.getLeftGolds() >= table.getInitStake()) {
                gamerReady(table, joingamer);
                MemcachedResource.save(Constants.PLAYER_LAST_TALBE + joingamer.getPlayerId(), table.getAreaId(), 10 * 60);
            }
        }
        Collections.sort(table.getJoinedGameList(), new Comparator<Gamer>() {
            @Override
            public int compare(Gamer o1, Gamer o2) {
                return o2.getLeftGolds() - o1.getLeftGolds();
            }
        });

        if (!MatchTableSetManager.getInstance().isExist(table)) {
            MatchTableSetManager.getInstance().addMatchTable(table);
        }

        OutputMessage om = new OutputMessage();
        om.putByte((byte) table.getInning());
        om.putInt(table.getJoinedGameList().get(0).getPlayerId());
        GameRoom room = GameRoomManager.getInstance().getRoomByTable(table);
        room.sendMessage(Protocol.RESPONSE_JUSHU_RESULT, om);
    }

    /**
     * 玩家准备
     * @param table
     * @param gamer
     */
    public static void gamerReady(GameTable table, Gamer gamer)
            throws GoldException, GoldenNOTEnoughException {
        boolean isDeal = false;
        Lock lock = table.getR();
        try {
            lock.lock();
            isDeal = table.isDealCard();
            if (isDeal) {
            	return;
//                throw new GoldException("游戏已开始，不能准备哦！");
            }

            if (table.getInitStake() > gamer.getLeftGolds()) {
                throw new GoldenNOTEnoughException("您的金币不足，不能准备！");
            }

            if (gamer.getState() != GamerState.GAME_NOREADY) {
                throw new GoldException("您还不能准备哦！:" + gamer.getPlayerId());
            }
            log.debug("玩家准备……playerId:" + gamer.getPlayerId() + ",tableId:"
                    + table.getTableId() + ",准备前准备玩家个数：" + table.getGamingNum());
            gamer.ready();
            log.debug("玩家准备……playerId:" + gamer.getPlayerId() + ",tableId:"
                    + table.getTableId() + ",准备后准备玩家个数：" + table.getGamingNum());
            table.setLastOperationTime(System.currentTimeMillis());
            if (table.getGamingNum() == 2) {
                GameReadyThread.getInstance().addTable(table);
            }
            // 清理未准备的局数
            gamer.setNoplayAmount(0);
        } catch (Exception e) {
            if (e instanceof GoldException)
                throw (GoldException) e;
            if (e instanceof GoldenNOTEnoughException)
                throw (GoldenNOTEnoughException) e;
            log.error("gamerReady error!", e);
        } finally {
            lock.unlock();
        }

    }

    /**
     * 玩家取消准备
     */
    public static void gamerUnReady(GameTable table, Gamer gamer)
            throws GoldException, GoldenNOTEnoughException {
        if (isMatch(table)) {
            throw new GoldException("");
        }
        if (table.getGamingList().size() > 0) {
            throw new GoldException("游戏已开始，不能取消准备！");
        }
        if (gamer.getState() != GamerState.GAME_READY) {
            throw new GoldException("玩家未准备！");
        }
        log.debug("玩家取消……");
        gamer.unReady();
        table.setLastOperationTime(new Date().getTime());
    }

    /**
     * 玩家看牌
     */
    public static void lookCard(GameTable table, Gamer gamer)
            throws GoldException, GoldenNOTEnoughException {
        if (!table.getGamingList().contains(gamer)) {
            throw new GoldException("本局游戏已结束！");
        }

        if (gamer.isLookCard()) {
//            throw new GoldException("您已看牌！");
        	return;
        }

        log.debug("玩家看牌……");
        gamer.setLookCard(true);
        gamer.setState(GamerState.GAME_LOOK);
    }

    /**
     * 玩家放弃
     * 
     * @param table
     * @param gamer
     */
    public static void gamerGiveUp(GameTable table, Gamer gamer)
            throws GoldException, GoldenNOTEnoughException {
        log.debug("放弃检查1");
        if (table.getGamingList().contains(gamer)) {
            log.debug("游戏中还有:" + table.getGamingList().size());
            giveUp(table, gamer);
        }

    }

    private static void giveUp(GameTable table, Gamer gamer)
            throws GoldException, GoldenNOTEnoughException {
        log.debug("玩家放弃中……");
        gamer.setState(GamerState.GAME_GIVEUP);
        table.removeGamingGamer(gamer);
        // 记录最新时间
        table.setLastOperationTime(System.currentTimeMillis());

        // 判断是否游戏结束
        if (table.getGamingList().size() == 1 && table.isOverGamer()) {
            overGame(table);
        }
    }

    /**
     * 跟注
     * 
     * @param table
     * @param gamer
     */
    public static void followStake(GameTable table, Gamer gamer)
            throws GoldException, GoldenNOTEnoughException {
        if (table == null) {
            log.error("===table is null");
            return;
        }
        Lock lock = table.getR();
        try {
            lock.lock();
            log.debug(gamer.getPlayerId() + "跟注");
            int golds = 0;
            // 看牌了
            if (gamer.isLookCard()) {
                golds = table.getSingleStake() * 2;
            }
            // 没看牌
            else {
                golds = table.getSingleStake();
            }
            increaseStake(table, gamer, golds);
        } catch (Exception e) {
            if (e instanceof GoldenNOTEnoughException)
                throw (GoldenNOTEnoughException) e;
            if (e instanceof GoldException)
                throw (GoldException) e;
            log.error("follow stake", e);
        } finally {
            lock.unlock();
        }

    }

    /**
     * 加注
     * @param table
     * @param gamer
     * @param stakeTypeId
     */
    public static void addStake(GameTable table, Gamer gamer, int stakeTypeId)
            throws GoldException, GoldenNOTEnoughException {
        log.debug(gamer.getPlayerId() + "加注");
        Lock lock = table.getR();
        try {
            lock.lock();
            // 如果注码类型比当前注小，不执行
            if (stakeTypeId <= 0)
                throw new GoldException("注码类型错误！");
            StakeConfig stakeType = table.getStakeTypeMap().get(stakeTypeId);
            if (stakeType.getStakeNumber() < table.getSingleStake())
                throw new GoldException("玩家下注错误！");

            table.setSingleStake(stakeType.getStakeNumber());

            int golds = 0;
            // 看牌
            if (gamer.isLookCard()) {
                golds = table.getSingleStake() * 2;
            }
            // 没看牌
            else {
                golds = table.getSingleStake();
            }
            increaseStake(table, gamer, golds);
        } catch (Exception e) {
            if (e instanceof GoldenNOTEnoughException)
                throw (GoldenNOTEnoughException) e;
            if (e instanceof GoldException)
                throw (GoldException) e;
            log.error("add stake error", e);
        } finally {
            lock.unlock();
        }

    }

    /**
     * 叫注--金钱
     */
    private static void increaseStake(GameTable table, Gamer gamer, int gold)
            throws GoldException, GoldenNOTEnoughException {

        if (!table.getGamingList().contains(gamer)) {
            throw new GoldException("该玩家未进行游戏！");
        }

        log.debug("当前玩家：" + table.getCurrentGamer().getPlayerId());
        if (table.getCurrentGamer().getPlayerId() != gamer.getPlayerId()) {
            log.debug("还没有轮到该玩家，不允许叫注……");
            throw new GoldException("还没有轮到该玩家，不允许叫注！");
        }

        if (table.getTurns() >= maxTurn) {
            // 如果轮数大于等于20，不允许叫注
            log.debug("轮数大于等于" + maxTurn + "，不允许叫注……");
            throw new GoldException("轮数大于等于" + maxTurn + "，不允许叫注！");
        }
        log.debug("玩家叫注……");      

        Player player = null;
        if (gamer.isRobot()) {
            player = RobotManager.getInstance().getRobotPlayer(
                    gamer.getPlayerId());
        } else {
            GameSession session = SessionManger.getInstance().getSession(
                    gamer.getPlayerId());
            player = PlayerService.getPlayer(gamer.getPlayerId(), session);
        }
        
        // 如果不在比赛场
        if (!isMatch(table)) {
            // 如果金币不足，系统自动赠送
            if (gamer.getLeftGolds() <= gold * 2 && !gamer.isRobot()) {
                GetOnceManager.getPlayerGetOnce(player);
            }
        }
        if (gamer.getLeftGolds() < gold) {
            log.debug("玩家:" + player.getId() + "金币不足!");
            throw new GoldenNOTEnoughException("您的金币不足了！！");
        }
        // 如果是不是比赛场，扣真实金币
        if (!isMatch(table)) {
            GoldService.consumeInCopper(player, gold);
        }
        // 修改玩家参数
        gamer.consumeGolds(gold);
        if (gamer.isRobot()) {
			RobotManager.savePlayer(player, RobotManager.getInstance().getInfoKey(gamer.getPlayerId()));
		}
        // 增加台面上的总筹码
        table.addStake(gold);
        afterOperate(table);
    }

    public static boolean isMatch(GameTable table) {
        if (table.getAreaId() == Area.nomalId
                || table.getAreaId() == Area.richId
                || table.getAreaId() == Area.goldId) {
            return true;
        }
        return false;
    }

    /**
     * 比牌
     */
    public static boolean compareHandCard(GameTable table, Gamer gamer,
            Gamer gamer2) throws GoldException, GoldenNOTEnoughException {

        Lock lock = table.getR();
        try {
            lock.lock();
            if (gamer2 == null) {//比牌时对方跑掉
				gamer2 = new Gamer(-1024000);
			}
            if (gamer.getPlayerId() == gamer2.getPlayerId()) {
                throw new GoldException("不能跟自己比牌！！");
            }
            // 检查条件：跟注或加注3个回合
            if (!table.getGamingList().contains(gamer)) {
                throw new GoldException("该玩家未进行游戏！");
            }
            if (table.getCurrentGamer().getPlayerId() != gamer.getPlayerId()) {
                log.debug("还没有轮到该玩家，不允许操作……");
                throw new GoldException("还没轮到您操作！");
            }
            if (table.getTurns() < 3) {
                log.debug("不允许比牌……");
                throw new GoldException("未满3回合，不允许比牌！");
            }

            log.debug("扣金币,加总注……");

            int needGolds = 0;
            // 有没有看牌
            if (gamer.isLookCard()) {
                needGolds = table.getSingleStake() * 4 * gamer2.getUseNcard();
            } else {
                needGolds = table.getSingleStake() * 2 * gamer2.getUseNcard();
            }

            Player player = null;
            if (gamer.isRobot()) {
                player = RobotManager.getInstance().getRobotPlayer(
                        gamer.getPlayerId());
            } else {
                GameSession session = SessionManger.getInstance().getSession(
                        gamer.getPlayerId());
                player = PlayerService.getPlayer(gamer.getPlayerId(), session);
            }

            // 如果不在比赛场
            if (!isMatch(table) && !gamer.isRobot()) {
                // 如果金币不足，系统自动赠送
                if (gamer.getLeftGolds() <= needGolds * 2) {
                    GetOnceManager.getPlayerGetOnce(player);
                }
            }

            if (gamer.getLeftGolds() < needGolds && isMatch(table)) {
                needGolds = gamer.getLeftGolds();
            }

            if (!isMatch(table)) {
                GoldService.consumeInCopperNegative(player, needGolds);
                if (gamer.isRobot()) {
                	RobotManager.savePlayer(player, RobotManager.getInstance().getInfoKey(gamer.getPlayerId()));
				}
            }

            // 加入桌子总注
            table.addStake(needGolds);
            gamer.consumeGoldsNegative(needGolds);

            log.debug("比牌……");
            boolean result;
            if (gamer2.getPlayerId() == -1024000) {
				result = true;
				// 改变当前需要操作游戏者
				table.nextOperator();
			}else {
            // 两个手牌比较
	            if (gamer.getHandCards().compareTo(gamer2.getHandCards()) == 1) {
	                gamer2.setState(GamerState.GAME_LOSE);
	                table.removeGamingGamer(gamer2);
	                identy_log.info("compareHandCard|" + gamer.getPlayerId() + "|" + Arrays.toString(gamer.getHandCards().getCards())+"|"+ gamer2.getPlayerId() + "|" +Arrays.toString(gamer2.getHandCards().getCards())+"|"+gamer2.getPlayerId());
	                result = true;
	                // 改变当前需要操作游戏者
	                table.nextOperator();
	            } else {
	                gamer.setState(GamerState.GAME_LOSE);
	                table.removeGamingGamer(gamer);
	                identy_log.info("compareHandCard|" + gamer.getPlayerId() + "|" + Arrays.toString(gamer.getHandCards().getCards())+"|"+ gamer2.getPlayerId() + "|" +Arrays.toString(gamer2.getHandCards().getCards())+"|"+gamer.getPlayerId());
	                result = false;
	            }
			}
            // 设置已比牌
            table.setCompareCard(true);

            // 记录比过牌的人
            if (gamer2.getPlayerId() != -1024000) {
            	gamer.getFireGamer().add(gamer2);
            	gamer2.getFireGamer().add(gamer);
			}

            // 记录最新时间
            table.setLastOperationTime(System.currentTimeMillis());

            return result;
        } catch (Exception e) {
            if (e instanceof GoldenNOTEnoughException)
                throw (GoldenNOTEnoughException) e;
            if (e instanceof GoldException)
                throw (GoldException) e;
            log.error("lock", e);
        } finally {
            lock.unlock();
        }
        return false;

    }

    /**
     * 步骤： 记录操作时间 改变当前操作人 判断游戏是否结束
     */
    private static void afterOperate(GameTable table) throws GoldException,
            GoldenNOTEnoughException {
        // 判断是否游戏结束
        if (table.getGamingList().size() <= 1 && table.isOverGamer() == true) {
            overGame(table);
        }
        // 记录最新时间
        table.setLastOperationTime(new Date().getTime());
        // 改变当前需要操作游戏者
        log.debug("GameTableManager-afterOperate,切换前玩家："
                + table.getCurrentPlayerId());
        table.nextOperator();
        log.debug("GameTableManager-afterOperate,切换后玩家："
                + table.getCurrentPlayerId());
    }

    /**
     * 继续游戏
     * 
     * @param table
     */
    public static void continueGame(GameTable table) throws GoldException,
            GoldenNOTEnoughException {
        // 记录最新时间
        Lock lock = table.getR();
        try {
            lock.lock();
            table.setLastOperationTime(new Date().getTime());
            table.setCompareCard(false);
            // 判断是否游戏结束
            if (table.getGamingList().size() <= 1
                    && table.isOverGamer() == true) {
                overGame(table);
            } else {
                log.debug("GameTableManager-continueGame:table.getGamingList().size()="
                        + table.getGamingList().size()
                        + ",table.isOverGamer()=" + table.isOverGamer());
            }
        } catch (Exception e) {
            if (e instanceof GoldenNOTEnoughException)
                throw (GoldenNOTEnoughException) e;
            if (e instanceof GoldException)
                throw (GoldException) e;
            log.error("lock", e);
        } finally {
            lock.unlock();
        }

    }

    /**
     * 决定出牌顺序
     * 
     * @param table
     */
    private static void decideOrder(GameTable table) {
        // 检测已准备的玩家，按出牌顺序加入到GamingList
        LinkedBlockingQueue<Gamer> gamerList = table.getGamingList();
        for (GameTableSeat seat : table.getSeats()) {
            Gamer gamer = seat.getGamer();
            if (gamer == null) {
                continue;
            }
            if (gamer != null && gamer.getState() == GamerState.GAME_READY
                    && gamer.getLeftGolds() >= table.getInitStake()) {
                gamerList.add(gamer);
            }
            // 如果没有准备，并且不是比赛场，则记录没准备的局数
            if (gamer.getState() != GamerState.GAME_READY && !isMatch(table)) {
                gamer.setNoplayAmount(gamer.getNoplayAmount() + 1);
            }
            // 如果超过5局不退出，则退出该玩家
            if (gamer.getNoplayAmount() >= Constants.NO_READY_LIMIT
                    || gamer.getLeftGolds() <= 0) {
                GameServer.exitRoomByPlayerId(gamer.getPlayerId());
            }
        }
        // 决定庄家
        int index = 0;
        log.debug("决定庄家，已准备的玩家个数：" + table.getGamingNum());
        index = r.nextInt(table.getGamingNum());
        table.orderGamingList(index);
        table.setBanker(table.getCurrentGamer());

        for (Gamer gamer : table.getGamingList()) {
            gamer.setState(GamerState.GAME_PLAYING);
        }
    }

    /**
     * 发牌前，要先下注
     * 
     * @throws GoldException
     */
    private static void beforeDealCard(GameTable table) throws GoldException,
            GoldenNOTEnoughException {
        // 修改玩家参数
        Player player = null;
        for (Gamer gamer : table.getGamingList()) {
            int gold = table.getInitStake();
            if (gamer.isRobot()) {
                player = RobotManager.getInstance().getRobotPlayer(
                        gamer.getPlayerId());
            } else {
                player = PlayerService.getCachePlayerByGamer(gamer);
            }
            if (!isMatch(table)) {
                GoldService.consumeInCopper(player, gold);
            }
            // 修改玩家状态
            gamer.consumeGolds(gold);
            //
            table.addStake(gold);
        }

        // 广播通知
        GameRoom gameRoom = GameRoomManager.getInstance().getRoomByTable(table);

        OutputMessage om = new OutputMessage();
        om.putInt(table.getInitStake());
        om.putInt(table.getTotalStake());
        om.putShort((short) table.getGamingList().size());
        for (Gamer gamer : table.getGamingList()) {
            om.putInt(gamer.getPlayerId());
            om.putInt(gamer.getLeftGolds());
        }

        gameRoom.sendMessage(Protocol.RESPONSE_BEFOREPBLIC, om);
    }

    /**
     * 发牌
     */
    public static void dealCard(GameTable table) throws GoldException,
            GoldenNOTEnoughException {
        // 如果已经发牌，就直接返回
        Lock lock = table.getW();
        try {
            lock.lock();
            if (table.isDealCard()) {
                return;
            }

            table.setDealCard(true);
            // 决定出牌顺序
            decideOrder(table);

            beforeDealCard(table);

            log.debug("正在发牌……" + table.getGamingList().size());

            
           fapai(table);
           
            for (Gamer gamer : table.getGamingList()) {
                if (gamer.isRobot()) {
                    RobotManager.getInstance().checkPoke(gamer, table);
                }
            }
        } catch (Exception e) {
            log.error("dealCard", e);
        } finally {
            lock.unlock();
        }

    }
    
    private static int chackCard(HandCards handCards){
    	if (handCards == null) {
    		return 1;
		}
    	if (handCards.isDouble(handCards.getCards())) {
			return handCards.duizi_card;
		}
    	if (handCards.isFrequence(handCards.getCards())) {
			return handCards.shunzi_card;
		}
    	if (handCards.isSameColor(handCards.getCards())) {
    		return handCards.jinhua_card;
		}
    	if (handCards.isSequence(handCards.getCards())) {
    		return handCards.tonghuashun_card;
		}
    	if (handCards.isThreeble(handCards.getCards())) {
    		return handCards.santiao_card;
		}
    	return 1;
    }
    
    private static void fapai(GameTable table){
    	int max_player = 1;
    	int cards_tmp = 1;
    	 for (Gamer gamer : table.getGamingList()) {//给玩家发牌
         	if (gamer.isRobot()) {
					continue;
				}
             HandCards handCards = null;
             int type = RobotManager.getInstance().getPokeType(gamer);
             handCards = table.getPoker().getHandCards(type);
             cards_tmp = chackCard(handCards);
             if (cards_tmp > max_player) {
				max_player = cards_tmp;
			}
             log.debug("gamer:" + gamer.getPlayerId() + ",牌"
                     + handCards.getCard1() + "," + handCards.getCard2() + ","
                     + handCards.getCard3());
             gamer.setHandCards(handCards);
//             if (!isMatch(table)) {
//                 table.addJoinedGamer(gamer);
//             }
//             if (!gamer.isRobot()) {
//            	 MemcachedResource.save(Constants.PLAYER_LAST_TALBE + gamer.getPlayerId(), table.getAreaId(), 10 * 60);
//			}
         }
//    	 ArearobotPre arp = ResourceCache.getInstance().getAreaRobotMap().get(table.getAreaId());
//    	 boolean is_win = false;//是否胜利
//    	 boolean one = false;//是否已经有机器人胜利
//    	 if (arp != null) {
//    		 is_win = ResourceCache.r.nextInt(100) <= arp.getRobotPre();
//		}
//         for (Gamer gamer : table.getGamingList()) {//给机器人发牌
//         	if (!gamer.isRobot()) {
//					continue;
//				}
//         	 int type = 1;
//         	if (is_win && max_player < 6 && !one) {
//         		one = true;
//         		type = max_player +1;
//			}else {
//				type = RobotManager.getInstance().getPokeType(gamer);
//			}
//             HandCards handCards = null;
//             handCards = table.getPoker().getHandCards(type);
//             log.debug("gamer:" + gamer.getPlayerId() + ",牌"
//                     + handCards.getCard1() + "," + handCards.getCard2() + ","
//                     + handCards.getCard3());
//             gamer.setHandCards(handCards);
//             if (!isMatch(table)) {
//                 table.addJoinedGamer(gamer);
//             }
//         }
    }

    /**
     * 开始游戏
     */
    public static void startGame(GameTable table) throws GoldException,
            GoldenNOTEnoughException {
        log.debug("游戏开始");
        Lock lock = table.getR();
        try {
            lock.lock();
            table.setStartGame(false);
            // 改变游戏轮数
            table.setTurns(0);
            log.debug("已重置桌子,joingame-size:" + table.getGamingList().size());

            // 记录最新时间
            table.setLastOperationTime(new Date().getTime());

            // 设置单注
            table.setSingleStake(table.getInitStake());

            // 叫注超时管理
            GameStakeThread.getInstance().addTable(table);
        } catch (Exception e) {
            log.error("start gamer error", e);
        } finally {
            lock.unlock();
        }

    }

    /**
     * 结束游戏
     * 
     * @param table
     */
    public static void overGame(GameTable table)
            throws GoldException, GoldenNOTEnoughException {
        log.debug("结束逻辑开始……");
        if (!table.isOverGamer()) {
            return;
        }
        log.debug("结束逻辑-isOver，完成");

//        // 退出托管
//        GameStakeThread.getInstance().removeTable(table);
//        GameStakeThread.getInstance().addTable(table);

        table.setOverGamer(false);
        long now = System.currentTimeMillis();
        // 给参与过游戏，并且还在房间的玩家里面发送结果
        int isSuss = 0;
        boolean isshow = true;
        Gamer winer = table.getGamingList().peek();

        if (winer != null) {

            table.setWinner(winer);

            log.debug("游戏结束……");
            log.debug("赢家为：" + winer.getPlayerId());

            winer.addLeftGolds(table.getTotalStake());
            Player winPlayer = null;

            if (!isMatch(table)) {
                if (winer.isRobot()) {
                    winPlayer = RobotManager.getInstance().getRobotPlayer(
                            winer.getPlayerId());
                } else {
                    // 给赢家加金币,修改DB
                    winPlayer = PlayerService.getCachePlayerByGamer(winer);
                }
                // 税率
                int dues = Integer
                        .parseInt(ResourceCache.getInstance().getAppConfigs()
                                .get(Constants.DUES_RATE).getAppValue());
                int winSorce = table.getTotalStake() * (1 - dues / 100);
                GoldService.addCopper(winPlayer, winSorce);
                gold_log.info(winPlayer.getId()+"|"+winPlayer.getCopper()+" |addCopper:|"+winSorce);
                if (winer.isRobot()) {
					RobotManager.savePlayer(winPlayer, RobotManager.getInstance().getInfoKey(winer.getPlayerId()));
				}
//                if (NoticeManager.getInstance().getNoitceLimitMap().containsKey(table.getAreaId())) {
//                	if (winSorce >= NoticeManager.getInstance().getNoitceLimitMap().get(table.getAreaId())) {
//                		AreaConfig ac = ResourceCache.getInstance().getAreaConfigs().get(table.getAreaId());
//                		String msg = "恭喜" + winPlayer.getNickname() +"玩家在"+ac.getIntrodution()+"赢得了"+winSorce+"金币";
//                		NoticeManager.getInstance().sendPublicMessage(null, msg);
//                	}
//				}
                // 奖励管理
                isSuss = RewardManager.getInstance()
                        .gainRewardByAreaIdAndGolds(winer, table.getAreaId(),
                                table.getTotalStake(), winPlayer);
            }
            log.debug("已修改玩家金币，并奖励！");

            // 是否显示每局比赛结果
            if (isMatch(table)) {
                int limitAmount = Integer.parseInt(ResourceCache.getInstance()
                        .getAppConfigs().get(Constants.OVER_MATCH_AMOUNT)
                        .getAppValue());
                if (table.getInning() + 1 > limitAmount
                        || table.getCurrentNum() <= 1
                        || table.getJoinedGamerSize() <= 1) {
                    isshow = false;
                }
            }

            if (isshow) {
                log.debug("广播结果……");
                List<Gamer> gamerList = new ArrayList<Gamer>();
                for (GameTableSeat gamerSeat : table.getSeats()) {
                    Gamer gamer = gamerSeat.getGamer();
                    if (gamer != null) {
                        gamerList.add(gamer);
                    }
                }
                for (Gamer gamer : table.getWatchList()) {
                    if (gamer != null) {
                        gamerList.add(gamer);
                    }
                }
                for (Gamer gamer : gamerList) {
                    if (gamer != null) {
                        if (gamer.isRobot()) {
                            continue;
                        }

                        if (!isMatch(table)) {// 任务管理:驱动任务进度
                            Player player = PlayerService
                                    .getSimplePlayerByPlayerId(gamer
                                            .getPlayerId());
                            TaskManager.getInstance().driveTaskRate(player,
                                    gamer == winer ? 1 : 0);
                        }

                        // 记录信息
                        if (gamer.getPlayerId() > 0
                                && gamer.getPlayerId() == winer.getPlayerId()) {
//                            AreaConfig config = ResourceCache.getInstance().getAreaConfigs().get(table.getAreaId());
//                            String msg = "您在"+config.getIntrodution()+"获得胜利，赢得"+table.getTotalStake()+"金币和"+isSuss+"奖券！";
//                            PlayerInfoCenterManager.getInstance()
//                                .addPlayerInfoCenter(winer.getPlayerId(), false, Constants.WIN_INFO, "",msg );
//                            AreaConfig ac = ResourceCache.getInstance().getAreaConfigs().get(table.getAreaId());
//                    		msg = "恭喜" + winPlayer.getNickname() +"玩家在"+ac.getIntrodution()+"赢得了"+table.getTotalStake()+"金币";
//                    		NoticeManager.getInstance().sendPublicMessage(null, msg);
                        }

                        GameSession session = SessionManger.getInstance()
                                .getSession(gamer.getPlayerId());
                        if (session == null) {
                            continue;
                        }
                        OutputMessage om = new OutputMessage();
                        // 奖励兑换卷
                        if (isSuss != 0) {
                            om.putInt(winer.getPlayerId());
                            om.putInt(isSuss);
                        } else {
                            om.putInt(-1);
                            om.putInt(0);
                        }
                        // 赢家总共金币
                        om.putInt(winer.getLeftGolds());
                        // 结果数量
                        om.putShort((short) table.getJoinedGamerSize());
                        // 发送玩过牌的玩家信息
                        List<Gamer> list = table.getJoinedGameList();
                        for (Gamer joinGamer : list) {

                            Player joinplayer = null;
                            if (joinGamer.isRobot()) {
                                joinplayer = RobotManager
                                        .getInstance()
                                        .getRobotPlayer(joinGamer.getPlayerId());
                            } else {
                                joinplayer = PlayerService
                                        .getCachePlayerByGamer(joinGamer);
                            }
                            if (joinplayer == null) {
                                continue;
                            }
                            // 玩家id
                            om.putInt(joinGamer.getPlayerId());
                            // 玩家昵名
                            om.putString(joinplayer.getNickname());
                            // 输赢，和金币
                            if (joinGamer.getPlayerId() == winer.getPlayerId()) {
                                om.putByte((byte) 1);
                                om.putInt(table.getTotalStake()
                                        - joinGamer.getGolds());
                            } else {
                                om.putByte((byte) 0);
                                om.putInt(joinGamer.getGolds());
                            }
                            // 是否看牌
                            // 如果循环到自己本身
                            if (joinGamer.getPlayerId() == gamer.getPlayerId()) {
                                // 如果自己看牌了，就设为可见，否则不可见
                                if (joinGamer.isLookCard()) {
                                    om.putByte((byte) 1);
                                } else {
                                    om.putByte((byte) 0);
                                }
                            }
                            // 如果不是本身
                            else {
                                // 如果是比过牌的，就可以见,否则不可见
                                if (!gamer.getFireGamer().contains(joinGamer)
                                        || joinGamer.getHandCards() == null) {
                                    om.putByte((byte) 0);
                                } else {
                                    om.putByte((byte) 1);
                                }
                            }
                            log.debug("比赛结束的牌：joingamer:"
                                    + joinGamer.getPlayerId() + ",handcards:"
                                    + joinGamer.getHandCards());
                            om.putByte(joinGamer.getHandCards() == null ? 0
                                    : joinGamer.getHandCards().getCard1()
                                            .getCardId());
                            om.putByte(joinGamer.getHandCards() == null ? 0
                                    : joinGamer.getHandCards().getCard2()
                                            .getCardId());
                            om.putByte(joinGamer.getHandCards() == null ? 0
                                    : joinGamer.getHandCards().getCard3()
                                            .getCardId());
                        }
                        Object o = session.getObject(Constants.PLAYER_KEY);
                        if (o != null) {
                            Player pp = (Player) o;
                            log.debug("send result player:" + pp.getId() + "_"
                                    + (System.currentTimeMillis() - now));
                        } else {
                            log.debug("send result player:"
                                    + (System.currentTimeMillis() - now));
                        }

                        om.putString("1");
                        log.debug("发送前:" + (System.currentTimeMillis() - now));
                        session.sendMessage(Protocol.RESPONSE_FIGHT_RESULT, om);
                        log.debug("发送后:" + (System.currentTimeMillis() - now));
                    }
                }
            }

            log.debug("广播结束！");

            // 修改玩家输赢信息
            List<Gamer> list = table.getJoinedGameList();
            for (Gamer joinGamer : list) {
                Player joinPlayer = PlayerService
                        .getCachePlayerByGamer(joinGamer);
                if (joinGamer == winer) {
                    joinPlayer.setWins(joinPlayer.getWins() + 1);
                } else {
                    joinPlayer.setLoses(joinPlayer.getLoses() + 1);
                }
                BaseEngine.getInstace().getPlayerActionIpml()
                        .modifyPlayer(joinPlayer);
            }
            log.debug("已修改输赢信息！");

        } else {
            if (isshow) {
                log.debug("广播结果……");
                for (GameTableSeat gamerSeat : table.getSeats()) {
                    Gamer gamer = gamerSeat.getGamer();
                    if (gamer != null) {
                        if (gamer.isRobot()) {
                            continue;
                        }
                        GameSession session = SessionManger.getInstance()
                                .getSession(gamer.getPlayerId());
                        if (session == null) {
                            continue;
                        }
                        OutputMessage om = new OutputMessage();
                        om.putInt(-1);
                        om.putInt(0);
                        om.putInt(0);
                        om.putShort((short) 0);
//                        session.sendMessage(Protocol.RESPONSE_FIGHT_RESULT, om);
//                        log.info("fight null ==========");
                    }
                }
            }

            log.debug("广播结束！");
        }

        log.debug("已重置桌子,joingame-size:" + table.getJoinedGamerSize());
        // 重置桌子状态
        table.reset();
        if (!isMatch(table)) {
            table.clearJoinedGamerList();
        }
        log.debug("已重置桌子,joingame-size:" + table.getJoinedGamerSize());
        // 重置玩家状态
        boolean isrobot = false;
        for (GameTableSeat seat : table.getSeats()) {
            Gamer gamer = seat.getGamer();
            if (gamer == null)
                continue;
            isrobot = false;
            if (gamer.isRobot()) {
                isrobot = true;
            }
            gamer.reset();
            //
            if (isrobot) {
                gamer.setRobot(isrobot);
            }
        }
        log.debug("已重置玩家和机器人");

        log.debug("send result time:" + (System.currentTimeMillis() - now));

        // 比赛场逻辑
        if (isMatch(table)) {

            int innig = table.getInning();
            innig++;
            table.setInning(innig);
            int limitAmount = Integer.parseInt(ResourceCache.getInstance()
                    .getAppConfigs().get(Constants.OVER_MATCH_AMOUNT)
                    .getAppValue());

            List<Gamer> ids = new ArrayList<Gamer>();
            for (Gamer gamer : table.getJoinedGameList()) {
                if (gamer.getLeftGolds() < table.getInitStake()) {
                    ids.add(gamer);
                }
            }
            for(Gamer id : ids){
                table.removeJoinedGamer(id);
            }

            if (innig > limitAmount || table.getCurrentNum() <= 1
                    || table.getJoinedGamerSize() <= 1) {
                isshow = false;
                log.debug("比赛结束");
                MatchTableSetManager.getInstance().removeMatchTable(table);
                Collections.sort(table.getJoinedGameList(),
                    new Comparator<Gamer>() {
                        @Override
                        public int compare(Gamer o1, Gamer o2) {
                            return o2.getLeftGolds() - o1.getLeftGolds();
                        }
                    }
                );

                Player matchWin = PlayerService.getCachePlayerByGamer(table
                        .getJoinedGameList().get(0));
                GameRoom room = GameRoomManager.getInstance()
                        .getRoomByPlayerId(table.getJoinedGameList().get(0).getPlayerId());
                if(matchWin != null){
                    // 奖励
                    int areaId = table.getAreaId();
                    String award = ResourceCache.getInstance().getAreaConfigs()
                            .get(areaId).getAword();
                    String[] awards = award.split("\\|");
                    int awa_moneny = Integer.parseInt(awards[0].split("_")[1]);
                    int awa_exchange = Integer.parseInt(awards[1].split("_")[1]);
                    GoldService.addCopper(matchWin, awa_moneny);
                    PropCacheAction.addProp(matchWin,
                            PropCacheAction.exchange_prop, awa_exchange);

                    gold_log.info(matchWin.getId()+"|"+matchWin.getCopper()+"m_addCopper:|"+awa_moneny+"|"+awa_exchange);
                    //广播结果，发送消息
					AreaConfig config = ResourceCache.getInstance()
							.getAreaConfigs().get(table.getAreaId());
					String msg = "您在" + config.getIntrodution() + "获得胜利，赢得"
							+ awa_moneny + "金币和" + awa_exchange + "奖券！";
					PlayerInfoCenterManager.getInstance().addPlayerInfoCenter(
							matchWin.getId(), false, Constants.WIN_INFO, "",
							msg);
//					AreaConfig ac = ResourceCache.getInstance()
//							.getAreaConfigs().get(table.getAreaId());
//					msg = "恭喜" + matchWin.getNickname() + "玩家在"
//							+ ac.getIntrodution() + "赢得了"
//							+ table.getTotalStake() + "金币";
//					NoticeManager.getInstance().sendPublicMessage(null, msg);
                    
                    // 广播信息
                    OutputMessage winom = new OutputMessage();
                    winom.putString(matchWin.getNickname());
                    winom.putString(matchWin.getHeadImage());
                    winom.putInt(awa_moneny);
                    winom.putInt(awa_exchange);
                    winom.putByte(Byte.parseByte(matchWin.getSex() + ""));
                    
                    room.sendMessage(Protocol.RESPONSE_MATCH_RESULT, winom);
                    log.info("over room ==========");
                    
                    List<Gamer> joines = new ArrayList<Gamer>();
                    for (Gamer gamer : table.getJoinedGameList()) {
                        joines.add(gamer);
                    }
                    for(Gamer join : joines){
                        removeGamer(table, join);
                    }
                    
                }
                // 清除数据
                innig = 0;
                table.setInning(innig);
                table.clearJoinedGamerList();
                // 销毁桌子
                GameRoomManager.getInstance().destoryRoom(room);
                 // 退出托管
                GameStakeThread.getInstance().removeTable(table);
                return;
            }

        }

        log.debug("结束游戏逻辑完成");
    }

    // 是否在比赛场
    public static boolean isInMatch(Gamer gamer) {
        GameRoom room = GameRoomManager.getInstance().getRoomByPlayerId(
                gamer.getPlayerId());
        if (room == null)
            return false;
        GameTable table = (GameTable) room
                .getObject(Constants.ROOM_TO_TABLE_KEY);
        return isMatch(table);
    }

    // 销毁桌子
    public static void destroyTable(GameTable table) {
        Lock lock = table.getR();
        try {
            lock.lock();
            if (table.getGamingList().size() >= 0) {
                for (Gamer gamer : table.getGamingList()) {
                    GameTableManager.gamerGiveUp(table, gamer);
                }
            }
            for (GameTableSeat seat : table.getSeats()) {
                Gamer gamer = seat.getGamer();
                if (gamer != null) {
                    GameTableManager.removeGamer(table, gamer);
                    if (gamer.isRobot()) {
                        RobotManager.getInstance().backOnePlayer(gamer);
                    } else {
                        GameServer.responseExitRoomInfo(gamer.getPlayerId());
                    }
                }
            }
            table.reset();
            table = null;
        } catch (Exception e) {
            log.error("lock", e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 清理机器人，清理离线玩家
     * 
     * @param table
     */
    public static void cleanGamer(GameTable table) {
        if (table != null) {
            long curr = System.currentTimeMillis();
            GameSession session = null;
            if (curr - table.getLastOperationTime() > 3 * 60 * 1000) {
                Lock lock = table.getW();
                try {
                    lock.lock();
                    for (GameTableSeat seat : table.getSeats()) {
                        if (seat.getGamer() != null
                                && seat.getGamer().isRobot()) {
                            // 清理机器人
                            // giveUp(table, seat.getGamer());
                            removeGamer(table, seat.getGamer());
                        }
                        if (seat.getGamer() != null
                                && !seat.getGamer().isRobot()) {
                            session = SessionManger.getInstance().getSession(
                                    seat.getGamer().getPlayerId());
                            if (session == null) {
                                removeGamer(table, seat.getGamer());
                            }
                        }
                    }
                    int less = table.getCurrentNum();
                    if (less <= 0) {
                        table.reset();
                    }
                } catch (Exception e) {
                    log.error("清理玩家失败", e);
                } finally {
                    lock.unlock();
                }

            }
        }
    }
}
