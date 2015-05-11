/**
 * CardGameServer
 * com.card.game.mt.server
 * GameServer.java
 */
package com.orange.goldgame.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;

import org.apache.log4j.Logger;

import com.juice.orange.game.container.GameRoom;
import com.juice.orange.game.container.GameSession;
import com.juice.orange.game.handler.SocketRequest;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.log.LoggerFactory;
import com.juice.orange.game.util.InputMessage;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.AreaConfig;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerTalk;
import com.orange.goldgame.exception.GoldException;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.server.RoomTalkService;
import com.orange.goldgame.server.domain.GameTable;
import com.orange.goldgame.server.domain.GameTableSeat;
import com.orange.goldgame.server.domain.Gamer;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.server.engine.ResponseEngine;
import com.orange.goldgame.server.exception.GoldenNOTEnoughException;
import com.orange.goldgame.server.manager.GameRoomManager;
import com.orange.goldgame.server.manager.GameTableManager;
import com.orange.goldgame.server.manager.GamerSet;
import com.orange.goldgame.server.manager.SessionManger;
import com.orange.goldgame.server.task.robot.RobotRequestPool;

/**
 * @author wuruihuang 2013.5.22 需要增加的功能 记录玩家比赛的次数 游戏术语：
 *         庄家：上一轮赢的玩家是下一轮的庄家，第一局或者赢家离开的情况随机选一个玩家为庄家。
 *         底分：游戏开始前每位玩家都要先投入游戏币。根据游戏房间的不同而数值不同。与底注说的是用一个意思。 底注：指游戏开始后每位玩家投入的初始注。
 *         锅底：每局游戏所有玩家下注之和。和奖池说的是同一个意思。 奖池：每局游戏所有玩家下注之和。
 * 
 *         单注封顶：每个玩家每次下注的上限。 暗注：不看牌的下注。 明注：看牌后的下注。明注后投入的游戏币单注的2倍。如果加的话是先加后翻倍。
 *         跟注：和上家加入同样的筹码。注：明注相当于暗注的2倍。
 *         加注：加入比上家上手单注更多的筹码。加注后不能超过单注封顶。锅底：每副牌玩家所有下注之和。
 *         投注：庄家的顺时针的下一家先开始下注，其他玩家依次顺时针操作
 *         。轮到玩家操作时，玩家根据条件和判断形势可以进行跟注、加注这两种投注方式之中的一种。
 *         手数封顶：每副牌每名玩家下注次数的上限（不包括底注），当达到手数封顶时，玩家将只可以与其他玩家比牌（比牌时仍要支付比牌费用）。
 * 
 *         看牌：查看自己三张牌的花色和点数，不能分别看一张或两张。 弃牌：指玩家自动弃权,本副牌认输且不收回本副牌筹码。玩家可以随时弃牌
 *         比牌：拿自己的牌和其他玩家的牌比大小，同时要支付当前单注两倍的比牌费用，加入锅底。 开牌：当最后游戏者只剩下 2
 *         个人时，则可以随时选择开牌； 或者有大于 2 人的用户，但是付出的游戏币已经达到封顶时，则由系统开牌。开牌时不用投注
 *         当某个玩家所剩的游戏币不够下次付出时，系统自动开牌。
 * 
 *         牌型：特殊（花色不同的235）、豹子（炸弹）、顺金（同花顺、色托）、金花（色皮）、顺子（拖拉机）、对子、单张（散牌）。豹子 > 顺金 >
 *         金花 > 顺子 > 对子 > 单张
 * 
 *         特殊奖励：获得特殊的牌型并成为赢家，可以得到相应的特殊奖励， 特殊奖励从所有当局失败玩家处平均扣除给胜利玩家。
 *         若某失败玩家不够付出特殊奖励，则付出其所有剩余的游戏币。 豹子：获得特殊奖励 = 底分之和 *10 顺金：获得特殊奖励 = 底分之和 *5
 * 
 */
public class GameServer extends BaseServer {

    private static final Logger logger = LoggerFactory
            .getLogger(GameServer.class);


    /**
     * 玩家请求进入游戏房间 创建玩家游戏角色
     */
    public void enterRoom(SocketRequest request, SocketResponse response)
            throws GoldException {

        // 接收玩家进入房间的请求
        InputMessage msg = request.getInputMessage();
        int playerId = msg.getInt();
        int areaId = msg.getByte();
        AreaConfig config = ResourceCache.getInstance().getAreaConfigs().get(areaId);
        Player player = PlayerService.getSimplePlayerByPlayerId(playerId);
        SessionManger.getInstance().putSession(playerId, request.getSession());
        if(player.getCopper()>config.getTopLimitGolds()){
            sendErrorMsg(request.getSession(),Constants.ENTER_ROOM_ERROR,"您的金币超出了该场次的上限，请选择其他场次！");
            return ;
        }
        enterRoomByPlayerIdAndAreaId(playerId, areaId);
    }
    
    public static void sendClearInfo(int playerId){
        GameSession session = SessionManger.getInstance().getSession(playerId);
        if(session != null){
            OutputMessage om = new OutputMessage();
            session.sendMessage(Protocol.RESPONSE_CLEAR_INFO, om);
        }
    }
    
    /**
     * 单纯地进入房间，不给房间加入session，并广播
     * @param playerId
     * @param roomId
     */
    public static void enterRoonByPlayerIdAndRoomId(Gamer gamer){
        Player player = PlayerService.getSimplePlayerByPlayerId(gamer.getPlayerId());
        GameSession session = SessionManger.getInstance()
                .getSession(gamer.getPlayerId());
        if(session != null){
            GameRoom room = null;
            room = GameRoomManager.getInstance().enterRoom(gamer, gamer.getRoomId(),Constants.ENTER_WATCHTOSEAT);
            if(room != null){
                broadcastRoomInfo(room,session, player, Constants.ENTER_WATCHTOSEAT,0);
            }
            
        }
    }
    
    public static void enterRoomByPlayerIdAndRoomId(int playerId,String roomId,int type,int friendId){
        Player player = PlayerService.getSimplePlayerByPlayerId(playerId);
        Gamer gamer = GamerSet.getInstance().getGamerByPlayerId(playerId);
        GameRoom room = null;
        if (gamer != null) {
            exitRoomByPlayerId(playerId);
        }
        if(gamer == null || gamer.getRoomId() == null || room == null ){
            gamer = new Gamer(player.getId());
            gamer.init();
            gamer.setFriendId(friendId);
            room = GameRoomManager.getInstance().enterRoom(gamer, roomId,type);
        }
        broadcastRoomInfo(room, SessionManger.getInstance()
                .getSession(playerId),player, type,friendId);
        if(type == Constants.ENTER_WATCH){
            GameServer.broadcastWatch(room, playerId);
        }
    }

    private void enterRoomByPlayerIdAndAreaId(int playerId, int areaId) {
        Player player = PlayerService.getSimplePlayerByPlayerId(playerId);
        Gamer gamer = GamerSet.getInstance().getGamerByPlayerId(playerId);
        GameRoom room = null;
        if (gamer != null) {
            exitRoomByPlayerId(playerId);
        }
        if(gamer == null || gamer.getRoomId() == null || room == null ){
            gamer = new Gamer(player.getId());
            gamer.init();
            room = GameRoomManager.getInstance().enterRoom(gamer, areaId);
        }
        broadcastRoomInfo(room, SessionManger.getInstance()
                .getSession(playerId), player);
    }
    
    private void enterRoomByPlayerIdAndAreaId(int playerId, int areaId,String roomId) {
        Player player = PlayerService.getSimplePlayerByPlayerId(playerId);
        Gamer gamer = GamerSet.getInstance().getGamerByPlayerId(playerId);
        if (gamer != null && gamer.getRoomId() != null) {
            GamerSet.getInstance().removeGamerByGamer(gamer);
            gamer = null;
        }
        if(gamer == null || gamer.getRoomId() == null){
            gamer = new Gamer(player.getId());
            gamer.init();
            GameRoom rRoom = null;
            rRoom = GameRoomManager.getInstance().enterRoom(gamer, areaId,roomId);
            broadcastRoomInfo(rRoom, SessionManger.getInstance()
                    .getSession(playerId), player);
        }
    }
    
    
    
    private static void broadcastRoomInfo(GameRoom room, GameSession session,
            Player player ) {
        broadcastRoomInfo(room,session,player,Constants.ENTER_SEAT,0);
    }
    
    private static void broadcastRoomInfo(GameRoom room, GameSession session,
            Player player ,int type,int friendId) {
        if (room == null) {
            sendErrorMsg(session, Constants.ENTER_ROOM_ERROR, "进入房间失败");
            return;
        } else {
            // 保护session
            List<GameSession> list = room.getSessionList();
            GameTable table = (GameTable) room
                    .getObject(Constants.ROOM_TO_TABLE_KEY);
            GameTableSeat[] seats = table.getSeats();
            Player p = null;
            boolean tmp = true;
            List<GameSession> removeSessions = new ArrayList<GameSession>();
            for (GameSession se : list) {
                tmp = true;
                try {
                    p = (Player) se.getObject(Constants.PLAYER_KEY);
                    if (p != null) {
                        for (GameTableSeat seat : seats) {
                            if (seat.getGamer() != null
                                    && seat.getGamer().getPlayerId() == p
                                            .getId()
                                    && p.getId().intValue() != player.getId()) {
                                tmp = false;
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                }
                if (tmp) {
                    removeSessions.add(se);
                }
            }
            for (GameSession gs : removeSessions) {
                room.removeSession(gs.getSessionId());
            }
            
            room.addSession(session);
            
            ResponseEngine.gamerEnterAreaResponse(session, room, player,type,friendId);
        }
    }

    /**
     * 玩家发送准备状态 找位置 改变玩家角色的状态
     */
    public void requestReady(SocketRequest request, SocketResponse response)
            throws GoldException {
        // 接收玩家游戏准备的请求
        InputMessage msg = request.getInputMessage();
        int playerId = msg.getInt();
        GameRoom room = GameRoomManager.getInstance().getRoomByPlayerId(
                playerId);
        // 请求处理：服务器处理玩家游戏准备的请求
        GameTable table = GameRoomManager.getInstance().getTableByPlayerId(
                playerId);
        Gamer gamer = GamerSet.getInstance().getGamerByPlayerId(playerId);

        if (room == null || table == null || gamer == null) {
            return;
        }

        try {

            if (GameTableManager.isMatch(table)) {
                sendErrorMsg(response, (short) 0, "比赛场不允许准备！");
                return;
            }
            GameTableManager.gamerReady(table, gamer);
            // 广播给房间每个玩家
            broadcastReadyInfo(room, playerId);
        } catch (GoldenNOTEnoughException e) {
            sendErrorMsg(response, (short) Constants.NO_GOLD, e.getMessage());
        } catch (GoldException e) {
            sendErrorMsg(response, (short) 0, e.getMessage());
        }

        

    }

    public static void broadcastReadyInfo(GameRoom room, int playerId) {
        OutputMessage om = new OutputMessage();
        om.putInt(playerId);
        room.sendMessage(Protocol.RESPONSE_PLAYER_READY, om);
    }

    /**
     * 玩家确认已比牌
     */
    public void requestComfirmFireCart(SocketRequest request,
            SocketResponse response) throws GoldException {
        InputMessage msg = request.getInputMessage();
        int playerId = msg.getInt();
        GameRoom room = GameRoomManager.getInstance().getRoomByPlayerId(
                playerId);
        GameTable table = (GameTable) room.getObject(Constants.ROOM_TO_TABLE_KEY);
        Lock lock = table.getR();
        int count = 0;
        try {
            lock.lock();
            for (Gamer gamer : table.getGamingList()) {
                if (gamer.getPlayerId() == playerId) {
                    table.increaseDealCardNum();
                    break;
                }
            }
            for (GameTableSeat seat : table.getSeats()) {
                if (seat.getGamer() != null && seat.getGamer().isRobot()) {
                    count++;
                }
            }
        } catch (Exception e) {
        } finally {
            lock.unlock();
        }

        // 如果确认个数等于玩牌的个数
        if (table.getGamingList().size() <= table.getDealCardCount() + count) {
            compareCardComfirm(room, table);
        }
    }

    public static void compareCardComfirm(GameRoom room,
            GameTable table) {
        if (!table.isCompareCard())
            return;
        responseNextGamer(room);
        table.resetDealCardNum();
        GameTableManager.continueGame(table);
        checkNextRobot(table, room);
    }

    /**
     * 请求开始游戏
     * 
     * @param request
     * @param response
     * @throws GoldException
     */
    public void requestStartGame(SocketRequest request, SocketResponse response)
            throws GoldException {
        InputMessage msg = request.getInputMessage();
        int playerId = msg.getInt();
        GameTable table = GameRoomManager.getInstance().getTableByPlayerId(
                playerId);
        Gamer gamer = GamerSet.getInstance().getGamerByPlayerId(playerId);
        GameRoom room = GameRoomManager.getInstance().getRoomByPlayerId(
                playerId);
        if (table == null)
            return;
        int robot_num = 0;
        for (Gamer robot : table.getGamingList()) {
            if (robot.getPlayerId() == playerId
                    && table.getGamingList().contains(gamer)) {
                table.increaseDealCardNum();
            }
            if (robot.isRobot()) {
                robot_num++;
            }
        }
//        if (table.getGamingList().size() <= (table.getDealCardCount() + robot_num)
//                && table.isStartGame()) {
        if (table.isStartGame()) {// 游戏开始逻辑
            try {
                startGameBussiness(table,room);
            } catch (GoldException e) {
                sendErrorMsg(request.getSession(), (short) 0, e.getMessage());
            } catch (GoldenNOTEnoughException e) {
                sendErrorMsg(request.getSession(), (short) Constants.NO_GOLD,
                        e.getMessage());
            }
        }
    }
    
    public static void startGameBussiness(GameTable table,GameRoom room){
        GameTableManager.startGame(table);
        table.resetDealCardNum();
        responseNextGamer(room);
        checkNextRobot(table, room);
    }

    private static final int delay_time = 5;

    public static void checkNextRobot(GameTable table,
            GameRoom room) {
        if (table.getGamingList().size() <= 1 && !GameTableManager.isMatch(table)) {
        	logger.debug("正在玩的数据错误");
            return;
        }

        Gamer g = GamerSet.getInstance().getGamerByPlayerId(
                table.getCurrentPlayerId());

        if (g != null && g.isRobot()) {
            // TODO 机器人加入线程监控
            int res = (ResourceCache.r.nextInt(delay_time) + 1) * 1000;
            if (g.getPlayerId() != table.getCurrentPlayerId()) {
            	logger.debug("需要加入的机器人不是本人");
                return;
            }
            RobotRequestPool.getInstance().addRequest(table, room, g, res);

        }
    }

    /**
     * 17 玩家发送看牌协议
     */
    public void lookCard(SocketRequest request, SocketResponse response)
            throws GoldException {
        // 接收玩家看牌的请求
        InputMessage msg = request.getInputMessage();
        int playerId = msg.getInt();

        GameRoom room = GameRoomManager.getInstance().getRoomByPlayerId(
                playerId);
        // 请求处理：服务器处理玩家游戏准备的请求
        GameTable table = GameRoomManager.getInstance().getTableByPlayerId(
                playerId);
        Gamer gamer = GamerSet.getInstance().getGamerByPlayerId(playerId);

        // 请求处理：服务器处理玩家看牌的请求
        try {
        	boolean isLook = gamer.isLookCard();
            GameTableManager.lookCard(table, gamer);
            // 响应请求：服务器反馈玩家看牌信息
            OutputMessage om1 = new OutputMessage();
            om1.putByte(gamer.getHandCards().getCard1().getCardId());
            om1.putByte(gamer.getHandCards().getCard2().getCardId());
            om1.putByte(gamer.getHandCards().getCard3().getCardId());

            request.getSession().sendMessage(Protocol.RESPONSE_HANDCARD_INFO,
                    om1);

            if (!isLook) {
            	sendLookCardMessage(gamer, room, request);
			}
        } catch (GoldException e) {
            sendErrorMsg(response, (short) 1, e.getMessage());
            return;
        } catch (GoldenNOTEnoughException e) {
            sendErrorMsg(response, (short) Constants.NO_GOLD, e.getMessage());
        }

    }

    public static void sendLookCardMessage(Gamer gamer, GameRoom room,
            SocketRequest request) {

        // 广播给其他玩家
        OutputMessage om2 = new OutputMessage();
        om2.putInt(gamer.getPlayerId());
        if (request != null) {
            room.sendMessage(Protocol.RESPONSE_NONEED_GOLDEN, om2,
                    request.getSession());
        } else {
            room.sendMessage(Protocol.RESPONSE_NONEED_GOLDEN, om2);
        }
    }

    /**
     * 19 玩家发送跟注协议 手数封顶：每副牌每名玩家下注次数的上限（不包括底注），
     * 当达到手数封顶时，玩家将只可以与其他玩家比牌（比牌时仍要支付比牌费用）。 明注：看牌后的下注。暗注：不看牌的下注。注：暗注相当于明注的2倍。
     * 跟注：和上家加入同样的筹码。注：暗注相当于明注的2倍。
     */
    public void followCard(SocketRequest request, SocketResponse response)
            throws GoldException {
        // 接收玩家跟注的请求
        InputMessage msg = request.getInputMessage();
        int playerId = msg.getInt();
        GameRoom room = GameRoomManager.getInstance().getRoomByPlayerId(
                playerId);
        if(room == null) {
            sendErrorMsg(response, (short) 1, "操作错误");
            return ;
        }
            
        GameTable table = (GameTable) room.getObject(Constants.ROOM_TO_TABLE_KEY);
        Gamer gamer = GamerSet.getInstance().getGamerByPlayerId(playerId);

        try {
            // 处理游戏逻辑
            GameTableManager.followStake(table, gamer);
            followCardSendMessage(gamer, table, room);
            checkNextRobot(table, room);
        } catch (GoldenNOTEnoughException e) {
            sendErrorMsg(response, (short) Constants.NO_GOLD, e.getMessage());
            return;
        } catch (GoldException e) {
            sendErrorMsg(response, (short) 1, e.getMessage());
            return;
        }

    }

    public static void followCardSendMessage(Gamer gamer, GameTable table,
            GameRoom room) {
        byte num = 1;
        if (gamer.isLookCard()) {
            num = 2;
        }
        // 广播信息
        OutputMessage om = new OutputMessage();
        om.putInt(gamer.getPlayerId());
        om.putByte(num);
        om.putInt(table.getSingleStake());
        om.putInt(gamer.getGolds());
        om.putInt(table.getTotalStake());
        om.putInt(gamer.getLeftGolds());
        room.sendMessage(Protocol.RESPONSE_FOLLOW_GOLDEN, om);
        // 广播下一个操作者是谁
        responseNextGamer(room);
    }

    /**
     * 21 玩家发送加注协议 单注封顶：每个玩家每次下注的上限。 加注：加注入比上家上手单更多的筹码。加注后不能超过单注封顶。
     * 明注：看牌后的下注。暗注：不看牌的下注。注：暗注相当于明注的2倍。 跟注：和上家加入同样的筹码。注：暗注相当于明注的2倍。
     */
    public void addGolds(SocketRequest request, SocketResponse response)
            throws GoldException {
        // 接收玩家加注的请求
        InputMessage msg = request.getInputMessage();
        int playerId = msg.getInt();
        byte goldType = msg.getByte();
        GameRoom room = GameRoomManager.getInstance().getRoomByPlayerId(
                playerId);
        GameTable table = GameRoomManager.getInstance().getTableByPlayerId(
                playerId);
        Gamer gamer = GamerSet.getInstance().getGamerByPlayerId(playerId);

        // 处理请求
        try {
            GameTableManager.addStake(table, gamer, (int) goldType);
            sendAddGoldMessage(gamer, table, room);
            checkNextRobot(table, room);
        } catch (GoldException e) {
            sendErrorMsg(response, (short) 1, e.getMessage());
            return;
        } catch (GoldenNOTEnoughException e) {
            sendErrorMsg(response, (short) Constants.NO_GOLD, e.getMessage());
            return;
        }

    }

    public static void sendAddGoldMessage(Gamer gamer, GameTable table,
            GameRoom room) {
        byte num = 1;
        if (gamer.isLookCard()) {
            num = 2;
        }

        OutputMessage om = new OutputMessage();
        om.putInt(gamer.getPlayerId());
        om.putByte(num);
        om.putInt(table.getSingleStake());
        om.putInt(gamer.getGolds());
        om.putInt(table.getTotalStake());
        om.putInt(gamer.getLeftGolds());

        room.sendMessage(Protocol.RESPONSE_ADD_GOLDEN, om);

        // 广播下一个操作者是谁
        responseNextGamer(room);
    }

    /**
     * 请求可比牌的列表
     */
    public void requestFightGamerList(SocketRequest request,
            SocketResponse response) throws GoldException {
        // 接收玩家id
        InputMessage msg = request.getInputMessage();
        int playerId = msg.getInt();

        GameTable table = GameRoomManager.getInstance().getTableByPlayerId(
                playerId);

        OutputMessage om = new OutputMessage();
        int size = table.getFightCartList().size();
        for (Gamer g : table.getFightCartList()) {
			if (g.getPlayerId() == playerId) {
				size --;
			}
		}
        om.putShort(size<0 ? 0 : (short)size);
        for (Gamer gam : table.getFightCartList()) {
            if(gam.getPlayerId()!=playerId){
                om.putInt(gam.getPlayerId());
            }
        }

        request.getSession().sendMessage(Protocol.RESPONSE_FIGHTGAMERLIST, om);
    }

    /**
     * 广播下一个操作者
     */
    public static void responseNextGamer(GameRoom room) throws GoldException {
        if (room == null)
            return;
        GameTable table = (GameTable) room
                .getObject(Constants.ROOM_TO_TABLE_KEY);
        if (table == null)
            return;
        Gamer gamer = table.getCurrentGamer();
        if (table.getGamingList().size() <= 1) {
            return;
        }
        if (gamer == null)
            return;
        int next = gamer.getPlayerId();
        OutputMessage om = new OutputMessage();
        om.putInt(next);
        room.sendMessage(Protocol.RESPONSE_NEXTGAMER, om);
    }

    /**
     * 23 玩家发送比牌协议 玩家可以在自己操作时与其他最多一位玩家比牌，比牌费用等于当前单注的两倍。当只剩两名玩家时，无论是第几轮都可以比牌。
     * 比牌时双方不能看到互相的牌，胜者继续游戏直至结束本局，负者损失本副牌的操作权，只在每局牌结束时可见。
     * 只剩两位玩家时，如果是比牌决定胜负的，则所有玩家（包括旁观者）都可以看见此二人的底牌。如果是一方放弃的情况，则仍不可见。
     * 每局结束时，所有玩家只能看见自己比过或跟自己比过的玩家的手牌。
     * 
     * 同种对子牌型，对子比对子的大小，其它牌型比最大的牌张。 同种散牌牌型，最大牌张相同则比第二大的牌张，以此类推。
     * 比牌时如出现双方牌型及大小相同的情况，主动比牌者为负者。
     * 
     * 根据比牌规则来判断胜负。显示所有没有放弃的玩家的牌型。如果可以投入游戏币的玩家只剩下一个，则判此玩家为胜利玩家。
     */
    public void fightCard(SocketRequest request, SocketResponse response)
            throws GoldException {
        // 接收玩家比牌的请求
        InputMessage msg = request.getInputMessage();
        int activeId = msg.getInt();// 主动比牌的玩家
        int passiveId = msg.getInt();// 被动比牌的玩家

        GameRoom room = GameRoomManager.getInstance().getRoomByPlayerId(
                activeId);
        // 请求处理：服务器处理玩家游戏准备的请求
        GameTable table = GameRoomManager.getInstance().getTableByPlayerId(
                activeId);
        Gamer gamer1 = GamerSet.getInstance().getGamerByPlayerId(activeId);
        Gamer gamer2 = GamerSet.getInstance().getGamerByPlayerId(passiveId);
        boolean result = false;
        try {
            result = GameTableManager.compareHandCard(table, gamer1, gamer2);
            sendFightMessage(gamer1, gamer2, result, table, room);
        } catch (GoldException e) {
            sendErrorMsg(response, (short) 1, e.getMessage());
            return;
        } catch (GoldenNOTEnoughException e) {
            sendErrorMsg(response, (short) Constants.NO_GOLD, e.getMessage());
            return;
        }
        
    }

    public static void sendFightMessage(Gamer src, Gamer dst, boolean win,
            GameTable table, GameRoom room) {
        OutputMessage om = new OutputMessage();
        om.putInt(src.getPlayerId());
        om.putInt(dst.getPlayerId());
        if (win) {
            om.putInt(src.getPlayerId());
        } else {
            om.putInt(dst.getPlayerId());
        }
        
        int res = src.isLookCard() ? 4 : 2;
        int n = dst.getUseNcard() > 0 ? dst.getUseNcard() : 1;
        res *= n;
        om.putByte((byte) res);
        om.putInt(table.getSingleStake());

        om.putInt(src.getGolds());
        om.putInt(table.getTotalStake());
        om.putInt(src.getLeftGolds());

        room.sendMessage(Protocol.RESPONSE_PLAYER_FIGHT_RESULT, om);
    }

    /**
     * 玩家发送放弃协议
     */
    public void givenUpCard(SocketRequest request, SocketResponse response)
            throws GoldException {
        // 接收玩家弃牌的请求
        InputMessage msg = request.getInputMessage();
        int gamerId = msg.getInt();

        GameRoom room = GameRoomManager.getInstance()
                .getRoomByPlayerId(gamerId);
        // 请求处理：服务器处理玩家游戏准备的请求
        GameTable table = GameRoomManager.getInstance().getTableByPlayerId(
                gamerId);
        Gamer gamer = GamerSet.getInstance().getGamerByPlayerId(gamerId);
        boolean isCurr = table.getCurrentPlayerId() == gamerId;
        try {
            GameTableManager.gamerGiveUp(table, gamer);

            sendGiveMessage(gamer, room);

            if(isCurr){
                responseNextGamer(room);
            }
            checkNextRobot(table, room);
            
        } catch (GoldException e) {
            sendErrorMsg(response, (short) 1, e.getMessage());
            return;
        } catch (GoldenNOTEnoughException e) {
            sendErrorMsg(response, (short) Constants.NO_GOLD, e.getMessage());
        }

    }

    public static void sendGiveMessage(Gamer gamer, GameRoom room) {
        OutputMessage om = new OutputMessage();
        om.putInt(gamer.getPlayerId());
        room.sendMessage(Protocol.RESPONSE_ABANDON, om);
    }

    /**
     * 玩家发送换桌协议
     */
    public void changeGameTable(SocketRequest request, SocketResponse response)
            throws GoldException {
        InputMessage msg = request.getInputMessage();
        int playerId = msg.getInt();
        int areaId = 0;
        GameTable table = null;
        String roomId = "";
        GameRoom room = GameRoomManager.getInstance().getRoomByPlayerId(
                playerId);
        if(room !=null){
            table = (GameTable)room.getObject(Constants.ROOM_TO_TABLE_KEY);
            areaId = table.getAreaId();
            roomId = room.getRoomId();
        }
        
        exitRoomByPlayerId(playerId,false);
        request.getSession().sendMessage(Protocol.RESPONSE_CLEAR_TABLE,
                new OutputMessage());
        
        
        if(areaId == 0 || roomId.equals("")){
            areaId = 1;
            enterRoomByPlayerIdAndAreaId(playerId, areaId);
        }else{
            enterRoomByPlayerIdAndAreaId(playerId, areaId,roomId);
        }
        
    }


    /**
     * 　　 玩家告知服务器离开房间 
     */
    public void exitRoom(SocketRequest request, SocketResponse response)
            throws GoldException {
        // 接收玩家离开房间的请求
        InputMessage msg = request.getInputMessage();
        int playerId = msg.getInt();
        exitRoomByPlayerId(playerId);
    }
    
    public static void responseExitRoomInfo(int playerId){
        Player player = PlayerService.getSimplePlayerByPlayerId(playerId);
        
        if (player != null) {
            // 返回给玩家
            OutputMessage om = new OutputMessage();
            om.putInt(playerId);
            om.putInt(player.getCopper());
            om.putInt(0);
            GameSession session = SessionManger.getInstance().getSession(
                    playerId);
            if (session != null) {
            	session.sendMessage(Protocol.RESPONSE_DATING_INFO, om);
			}
        }
    }
    public static GameRoom exitRoomByPlayerId(int playerId) {
    	return exitRoomByPlayerId(playerId,true);
    }
    
    
    public static GameRoom exitRoomByPlayerId(int playerId,boolean sendMessage) {

        responseExitRoomInfo(playerId);
        
        GameRoom room = GameRoomManager.getInstance().getRoomByPlayerId(
                playerId);
        if (room == null)
            return null;
        GameTable table = (GameTable) room
                .getObject(Constants.ROOM_TO_TABLE_KEY);
        Gamer gamer = GamerSet.getInstance().getGamerByPlayerId(playerId);
        
        boolean isCurr = table.getCurrentPlayerId() == playerId;
        
        boolean isExit = false;
        if (room != null) {
            isExit = GameRoomManager.getInstance().exitRoom(gamer,
                    room.getRoomId());
            GameSession session  = SessionManger.getInstance().getSession(playerId);
            if (session != null && sendMessage) {
            	 OutputMessage om = new OutputMessage();
                 om.putInt(playerId);
                 session.sendMessage(Protocol.RESPONSE_HAS_PLAYER_LEAVE_ROOM, om);
			}
        }
        if (isExit) {
            // TODO 广播退出房间
            broadcastExitRoom(room, playerId);
            
        } else {
            sendErrorMsg(SessionManger.getInstance().getSession(playerId),
                    (short) 1, "退出失败");
        }
        if (isCurr) {
            responseNextGamer(room);
        }
        return room;
    }

    public static void broadcastExitRoom(GameRoom room, int playerId) {
        OutputMessage om = new OutputMessage();
        om.putInt(playerId);
        room.sendMessage(Protocol.RESPONSE_HAS_PLAYER_LEAVE_ROOM, om);
        
        GameTable table = (GameTable)room.getObject(Constants.ROOM_TO_TABLE_KEY);
        if(!GameTableManager.isMatch(table) && table.getWatchList().size()>0 && !table.isFull()){
            Gamer watchGamer = null;
            GameSession session = null;
            do{
                watchGamer = table.getWatchList().poll();
                if(watchGamer == null){
                    break;
                }
                session = SessionManger.getInstance().getSession(watchGamer.getPlayerId());
            } while(session==null);
            if(watchGamer != null){
                GameServer.sendClearInfo(watchGamer.getPlayerId());
                GameServer.enterRoonByPlayerIdAndRoomId(watchGamer);
            }
        }
    }
    
    /**
     * 广播有玩家观看
     * @param room
     * @param playerId
     */
    public static void broadcastWatch(GameRoom room,int playerId){
        Player player = PlayerService.getSimplePlayerByPlayerId(playerId);
        if(player != null){
            OutputMessage om = new OutputMessage();
            om.putString(player.getNickname());
            room.sendMessage(Protocol.RESPONSE_WATCH, om);
        }
        
    }

    /**
     * 客户端请求比赛场开始
     * 
     * @param request
     * @param response
     * @throws GoldException
     */
    public void requestMatchStart(SocketRequest request, SocketResponse response) {
        InputMessage msg = request.getInputMessage();
        int player_id = msg.getInt();
        try {
            GameTable table = GameRoomManager.getInstance().getTableByPlayerId(
                    player_id);
            if(table == null){
                return ;
            }
            table.increaseDealCardNum();
//            if (table.getDealCardCount() >= table.getJoinedGamerSize()) {
            if (table.isStartGame()) {
                GameTableManager.startMatch(table);
                table.resetDealCardNum();
            }
        } catch (GoldException e) {
            sendErrorMsg(request.getSession(), (short) 1, e.getMessage());
        } catch (GoldenNOTEnoughException e) {
            sendErrorMsg(response, (short) Constants.NO_GOLD, e.getMessage());
        }

    }
    
    public void checkEnterArea(SocketRequest request, SocketResponse response) {
    	 InputMessage msg = request.getInputMessage();
         int player_id = msg.getInt();
         int area_id = msg.getByte();
         byte status = 0;
         String info = "succ";
         AreaConfig config = ResourceCache.getInstance().getAreaConfigs().get(area_id);
         if (config.getAreaType().intValue() == 1) {//普通场才需要确认
        	 Player player = PlayerService.getSimplePlayerByPlayerId(player_id);
             if(player.getCopper()>config.getTopLimitGolds()){
            	 status = 1;
            	 info = "您的金币超出了该场次的上限，请选择其他场次！";
             }
		}
         OutputMessage out = new OutputMessage();
         out.putByte(status);
         out.putByte((byte) config.getAreaType().intValue());
         out.putString(info);
         out.putByte((byte)area_id);
         response.sendMessage(Protocol.RESPONSE_ENTERAREA, out);
    }

    /**
     * 断线重连
     * @param request
     * @param response
     * @throws GoldException
     */
    public void returnGame(SocketRequest request, SocketResponse response)
            throws GoldException {
    	InputMessage in = request.getInputMessage();
    	int player_id = in.getInt();
    	logger.info("returnGame|" + player_id);
    	Player player = PlayerService.getPlayer(player_id, request.getSession());
    	if (player != null) {
    		GameRoom room  = GameRoomManager.getInstance().getRoomByPlayerId(player_id);
    		Player other = null;
			for (GameSession gs :room.getSessionList()) {
				try {
					other = (Player) gs.getObject(Constants.PLAYER_KEY);
					if (other.getId().intValue() == player.getId().intValue()) {
						room.removeSession(gs.getSessionId());
					}
				} catch (Exception e) {
				}
			}
    		room.addSession(request.getSession());
    		ResponseEngine.gamerEnterAreaResponse(request.getSession(),room,player,Constants.ENTER_SEAT,0);
		}
    }
    
    /**
    * 房间内的玩家发送聊天
    * @param request
    * @param response
    */
   public void chatWithEveryone(SocketRequest request,SocketResponse response){
		InputMessage im=request.getInputMessage();
		int playerId=im.getInt();
		byte type=im.getByte();
		String msg=im.getUTF();
		Player player = PlayerService.getPlayer(playerId, request.getSession());
		GameRoom room=GameRoomManager.getInstance().getRoomByPlayerId(playerId);
		if(room!=null){
			if((type == Constants.COMMON_TYPE_ZERO && player.getStatus() == Constants.PLAYER_STATUS_BAN_SPEED)
			|| (type == Constants.COMMON_TYPE_ONE && player.getStatus() == Constants.PLAYER_STATUS_BAN_CUSTOM)){
				sendErrorMsg(request.getSession(), (short)1, "您没有在房间喊话的权限");
				return;
			}
			RoomTalkService.sendTalkMessage(room, playerId,type,msg);
			savePlayerTalkRecord(playerId,type,msg);
		}else{
			sendErrorMsg(request.getSession(), (short)1, "不在房间内,无法发送聊天..");
		}
	}
   
   private void savePlayerTalkRecord(int playerId, byte type, String msg) {
   	if(type==Constants.COMMON_TYPE_ZERO) return;
		PlayerTalk talk=new PlayerTalk();
		talk.setPlayerId(playerId);
		talk.setTalkMsg(msg);
		talk.setTalkTime(new Date());
		talk.setTalkType(Constants.COMMON_TYPE_ONE);
		BaseEngine.getInstace().getPlayerTalkAction().insertPlayerTalk(talk);
	}
    
}
