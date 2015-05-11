package com.orange.goldgame.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.juice.orange.game.cached.MemcachedResource;
import com.juice.orange.game.exception.JuiceException;
import com.juice.orange.game.handler.SocketRequest;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.log.LoggerFactory;
import com.juice.orange.game.util.InputMessage;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.core.ErrorCode;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerPay;
import com.orange.goldgame.domain.PlayerProps;
import com.orange.goldgame.domain.Ranking;
import com.orange.goldgame.domain.SealInfo;
import com.orange.goldgame.domain.SynMoney;
import com.orange.goldgame.exception.SuperstarException;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.server.ShareGoldCache;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.server.manager.AppellationManager;
import com.orange.goldgame.server.manager.NoticeManager;
import com.orange.goldgame.server.manager.RobotManager;
import com.orange.goldgame.server.manager.RunTimePropertyManager;
import com.orange.goldgame.server.manager.SessionManger;
import com.orange.goldgame.util.DateUtil;

public class AdminServer {

	private final Logger log = LoggerFactory.getLogger(AdminServer.class);
	/**
	 * 获取在线人数
	 * @param request
	 * @param response
	 * @throws JuiceException
	 */
	public void getOnlineNum(SocketRequest request, SocketResponse response) throws JuiceException {
		int onle_num = SessionManger.getInstance().getGameSessions().size();
		OutputMessage om = new OutputMessage();
		om.putInt(onle_num);
		response.sendMessage(Protocol.RESPONSE_ONLENUM, om);
	}
	
	/**
	 * 关闭或开启机器人
	 * @param request
	 * @param response
	 * @throws JuiceException
	 */
	public void closeOpenRobot(SocketRequest request, SocketResponse response) throws JuiceException {
		InputMessage im = request.getInputMessage();
		int close = im.getInt();
		if (close == 1) {
			RobotManager.getInstance().setUseRobot(true);
		}else {
			RobotManager.getInstance().setUseRobot(false);
		}
		OutputMessage om = new OutputMessage();
		om.putString(RobotManager.getInstance().isUseRobot()?"used":"closed");
		response.sendMessage(Protocol.RESPONSE_CLOSEROBOT, om);
	}
	
	public void sendNotice(SocketRequest request, SocketResponse response) throws JuiceException {
	    InputMessage im = request.getInputMessage();
	    String content = im.getUTF();
	    NoticeManager.getInstance().sendPublicMessage(null, content);
	    OutputMessage om = new OutputMessage();
        om.putString("发送成功！");
        response.sendMessage(Protocol.RESPONSE_SENDSYSTEM_NOTICE, om);
	}
	
	/**
	 * 关闭或开启好友赠送
	 * @param request
	 * @param response
	 * @throws JuiceException
	 */
	public void closePressentGolds(SocketRequest request, SocketResponse response) throws JuiceException {
		InputMessage im = request.getInputMessage();
		int close = im.getInt();
		RunTimePropertyManager.getInstance().setAbleGiveGold(close == 1?true:false);
		OutputMessage om = new OutputMessage();
		om.putString(RunTimePropertyManager.getInstance().isAbleGiveGold()?"present":"closed");
		response.sendMessage(Protocol.RESPONSE_CLOSEROBOT, om);
	}
	
	/**
	 * 封号处理
	 * @param request
	 * @param response
	 * @throws JuiceException
	 */
	public void sealAccount(SocketRequest request, SocketResponse response) throws JuiceException {
	    InputMessage im = request.getInputMessage();
	    int playerId = im.getInt();
	    int type = im.getInt();
	    String reason = im.getUTF();
	    Player player = null;
	    //封号
	    if(type==Constants.PLAYER_STATUS_SEAL){
	    	player = PlayerService.getSimplePlayerByPlayerId(playerId);
	        player.setStatus(Constants.PLAYER_STATUS_SEAL);
	        BaseEngine.getInstace().getPlayerActionIpml().modifyPlayer(player);
	        
	        SealInfo info = new SealInfo();
	        info.setPlayerId(playerId);
	        info.setSealReason(reason);
	        info.setSealTime(new Date());
	        BaseEngine.getInstace().getSealInfoAction().insert(info);
	    }
	    //解封
	    else if(type==0){
	        player = PlayerService.getSimplePlayerByPlayerId(playerId);
            player.setStatus(0);
            BaseEngine.getInstace().getPlayerActionIpml().modifyPlayer(player);
	    }
	    //禁言
	    else if(type==Constants.PLAYER_STATUS_BAN){
	        player = PlayerService.getSimplePlayerByPlayerId(playerId);
            player.setStatus(Constants.PLAYER_STATUS_BAN);
            BaseEngine.getInstace().getPlayerActionIpml().modifyPlayer(player);
            
            SealInfo info = new SealInfo();
            info.setPlayerId(playerId);
            info.setSealReason(reason);
            info.setSealTime(new Date());
            BaseEngine.getInstace().getSealInfoAction().insert(info);
	    }
	    MemcachedResource.save(Constants.PLAYER_STATUS +player.getId(), type, 24 * 60 * 60);
	    OutputMessage om = new OutputMessage();
	    om.putString("success");
	    response.sendMessage(Protocol.RESPONSE_SEAL, om);
	}
	
	public void adminPay(SocketRequest request, SocketResponse response)throws JuiceException{
		InputMessage im = request.getInputMessage();
	    String imei = im.getUTF();
	    String pay_id = im.getUTF();
	    String info = im.getUTF();
	    String orderid = im.getUTF();
	    
	    ErrorCode res = SDKPayService.pay(imei, pay_id, info, orderid);
	    OutputMessage om = new OutputMessage();
	    om.putString(res.getStatus()+"");
	    response.sendMessage(Protocol.RESPONSE_PAY, om);
	}
	
	/**
	 * 查询玩家的金币数与宝石数目
	 * 
	 */
	public void queryPlayRich(SocketRequest request,SocketResponse response){
		InputMessage im=request.getInputMessage();
		int playerId=im.getInt();
		Player player=PlayerService.getPlayer(playerId, request.getSession());
		OutputMessage om=new OutputMessage();
		om.putInt(player.getCopper());
		om.putInt(player.getGolds());
		response.sendMessage(Protocol.RESPONSE_QUERY_GOLD_COPPER, om);
	}
	
	/**
	 * 添加金币或者宝石
	 * @param request
	 * @param response
	 */
	public void addGoldOrStone(SocketRequest request,SocketResponse response){
		InputMessage im=request.getInputMessage();
		int playerId=im.getInt();
		int copper=im.getInt();
		int gold=im.getInt();
		Player player=PlayerService.getPlayer(playerId, request.getSession());
		boolean isSuccess=false;
		OutputMessage om=new OutputMessage();
		isSuccess=GoldService.addCopper(player, copper);
		om.putString(isSuccess?"success":"fail");
		isSuccess=GoldService.addGold(player, gold);
		om.putString(isSuccess?"success":"fail");
		request.getSession().sendMessage(Protocol.RESPONSE_ADD_GOLD_COPPER, om);		
	}
	
	/**
	 * 查询排行榜中玩家的数据
	 * @param request
	 * @param response
	 */
	public void checkPayForPlayer(SocketRequest request,SocketResponse response){
		InputMessage im=request.getInputMessage();
		byte checkType=im.getByte();  //查询类型 0为排行榜查询  1为玩家查询
		int playerId=im.getInt();
		byte rankType=im.getByte();   //排行榜类型
		List<PlayerPay> payInfos=new ArrayList<PlayerPay>();
		switch (checkType) {
		case 0:
			List<Ranking> ranks=BaseEngine.getInstace().getRankingAction().loadAllRankingByRankId(rankType);
			boolean isExit=false;
			if(ranks!=null&&ranks.size()>0){
				for(Ranking rank:ranks){
					if(rank.getPlayerId()==playerId){
						payInfos=BaseEngine.getInstace().getPlayerPayAction().queryAllPayForPlayer(playerId);
						isExit=true;
						break;
					}
				}
				if(!isExit){
					BaseServer.sendErrorMsg(request.getSession(), (short)1, "玩家不在排行榜之内...");
					return;
				}
			}
			break;
		case 1:
			payInfos=BaseEngine.getInstace().getPlayerPayAction().queryAllPayForPlayer(playerId);
			break;
		default:
			BaseServer.sendErrorMsg(request.getSession(), (short)1, "输入的检查参数类型有误...");
			return;
		}
		responsePlayerPayInfo(payInfos,response);
	}

	private void responsePlayerPayInfo(List<PlayerPay> payInfos,SocketResponse response) {
		OutputMessage om=new OutputMessage();
		int size=payInfos.size();
		om.putInt(size);
		for(PlayerPay pInfo:payInfos){
			om.putInt(pInfo.getPlayerId());
			om.putInt(pInfo.getGold());
			om.putString(DateUtil.getDetailDate(pInfo.getPayTime()));
			om.putString(pInfo.getPayType());			
		}
		response.sendMessage(Protocol.RESPONSE_QUERY_PAY_LIST, om);
	}
	
	/**
	 * 请求查询玩家的道具数目
	 * @param request
	 * @param response
	 */
	public void queryPlayerProp(SocketRequest request,SocketResponse response){
		InputMessage im=request.getInputMessage();
		int playerId=im.getInt();
		int propTypp=im.getInt();
		PlayerProps prop=BaseEngine.getInstace().getPlayerPropsActionImpl().getPlayerProp(playerId, propTypp);
		OutputMessage om=new OutputMessage();
		om.putString(prop!=null?""+prop.getNumber():"该玩家没有拥有此道具..");
		response.sendMessage(Protocol.RESPONSE_QUERY_PROP_COUNT, om);
	}
	
	/**
	 * 请求清除玩家数据(目前只提供清楚玩家金币或宝石数)
	 * @param request
	 * @param response
	 */
	public void clearPlayerInfo(SocketRequest request,SocketResponse response){
		InputMessage im=request.getInputMessage();
		int playerId=im.getInt();
		int clearType=im.getInt(); //希望清空的类型  目前1为金币 2为宝石数
 		int setNum=im.getInt();    //希望设置成的数
 		Player player=PlayerService.getPlayer(playerId, request.getSession());
 		ErrorCode error=processPlayerInfo(player,clearType,setNum);
 		OutputMessage om=new OutputMessage();
 		om.putString(error.getMsg());
 		response.sendMessage(Protocol.RESPONSE_CLEAR_PLAYER_INFO, om);
	}

	private ErrorCode processPlayerInfo(Player player, int clearType, int setNum) {
		boolean isSuccess=true;
		switch (clearType) {
		case Constants.CLEAR_COPPER:	
			player.setCopper(setNum);
			break;
		case Constants.CLEAR_GOLD:
			player.setGolds(setNum);
			break;
		default:
			isSuccess=false;
			break;
		}
		if(!isSuccess){
			return new ErrorCode(-1,"你请求修改的选项不对,请重新选择选项...");
		}
		SynMoney money=ShareGoldCache.getCacheGold(player.getId());
		if(money!=null){
			money.setCopper(player.getCopper());
			money.setGold(player.getGolds());
			ShareGoldCache.setCacheGold(player.getId(), money);
		}
		AppellationManager.getInstance().changeAppellation(player, player.getCopper());
		BaseEngine.getInstace().getPlayerActionIpml().modifyPlayer(player);
		return new ErrorCode(0,"设置玩家信息成功..");
	}
}
