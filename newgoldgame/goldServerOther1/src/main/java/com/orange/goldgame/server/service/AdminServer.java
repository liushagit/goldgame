package com.orange.goldgame.server.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.juice.orange.game.cached.MemcachedResource;
import com.juice.orange.game.exception.JuiceException;
import com.juice.orange.game.handler.SocketRequest;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.log.LoggerFactory;
import com.juice.orange.game.util.InputMessage;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.cache.action.PropCacheAction;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.core.ErrorCode;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.ClientToolNotice;
import com.orange.goldgame.domain.NoticeConfig;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerConsume;
import com.orange.goldgame.domain.PlayerPay;
import com.orange.goldgame.domain.PlayerProps;
import com.orange.goldgame.domain.PropsConfig;
import com.orange.goldgame.domain.Ranking;
import com.orange.goldgame.domain.SealInfo;
import com.orange.goldgame.domain.SynMoney;
import com.orange.goldgame.domain.ZfbPayRecord;
import com.orange.goldgame.exception.GoldException;
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
	
	/**客服playerId -> -211**/
	public static int tool_player_id = -211;
	/**
	 * 获取在线人数
	 * @param request
	 * @param response
	 * @throws JuiceException
	 */
	public void getOnlineNum(SocketRequest request, SocketResponse response) throws JuiceException {
		InputMessage im = request.getInputMessage();
		im.getInt();
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
		im.getInt();
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
	    im.getInt();
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
		im.getInt();
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
	    im.getInt();
	    int playerId = im.getInt();
	    int type = im.getInt();
	    String reason = im.getUTF();
//	    Player player = null;
	    Player player = PlayerService.getSimplePlayerByPlayerId(playerId);
	    String tip = "用户不存在,无法进行权限设置";
	    OutputMessage om = new OutputMessage();
	    SealInfo info = null;
	    if(player == null){
	    	om.putString(tip);
	    }else{
	    	info = BaseEngine.getInstace().getSealInfoAction().getSealInfo(playerId);
	    	//封号
	    	if(type==Constants.PLAYER_STATUS_SEAL){
	    		player.setStatus(Constants.PLAYER_STATUS_SEAL);
	    		BaseEngine.getInstace().getPlayerActionIpml().modifyPlayer(player);	
	    		tip = playerId + " 封号成功";
	    	}
	    	//解封
	    	else if(type==0){
	    		player = PlayerService.getSimplePlayerByPlayerId(playerId);
	    		player.setStatus(0);
	    		BaseEngine.getInstace().getPlayerActionIpml().modifyPlayer(player);
	    		tip = playerId + " 解封成功";
	    	}
	    	//禁言-系统公告
	    	else if(type==Constants.PLAYER_STATUS_BAN_SYSTEM){
	    		player = PlayerService.getSimplePlayerByPlayerId(playerId);
	    		player.setStatus(Constants.PLAYER_STATUS_BAN_SYSTEM);
	    		BaseEngine.getInstace().getPlayerActionIpml().modifyPlayer(player);
	    		tip = playerId + " 禁止发送公告成功";
	    	}
	    	//禁言->房间快捷喊话
	    	else if(type==Constants.PLAYER_STATUS_BAN_SPEED){
	    		player = PlayerService.getSimplePlayerByPlayerId(playerId);
	    		player.setStatus(Constants.PLAYER_STATUS_BAN_SPEED);
	    		BaseEngine.getInstace().getPlayerActionIpml().modifyPlayer(player);
	    		tip = playerId + "禁止房间快捷喊话成功";
	    	}
	    	//禁言->房间自定义有喊话
	    	else if(type==Constants.PLAYER_STATUS_BAN_CUSTOM){
	    		player = PlayerService.getSimplePlayerByPlayerId(playerId);
	    		player.setStatus(Constants.PLAYER_STATUS_BAN_CUSTOM);
	    		BaseEngine.getInstace().getPlayerActionIpml().modifyPlayer(player);
	    		tip = playerId + "禁止房间自定义喊话成功";
	    	}
	    	om.putString(tip);
	    	if(info == null){
    			info = new SealInfo();
    			info.setPlayerId(playerId);
    			info.setSealReason(reason);
    			info.setSealTime(new Date());
    			BaseEngine.getInstace().getSealInfoAction().insert(info);
    		}else{
    			info.setSealReason(reason);
    			info.setSealTime(new Date());
    			BaseEngine.getInstace().getSealInfoAction().updateSealInfo(info);
    		}
	    	MemcachedResource.save(Constants.PLAYER_STATUS +player.getId(), type, 24 * 60 * 60);
	    }
	    
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
	public void questPlayerRich(SocketRequest request,SocketResponse response){
		InputMessage im=request.getInputMessage();
		im.getInt();
		int playerId=im.getInt();
		int copper = -1;
		int golds = -1;
		int checkId = -1;
		try{
			Player player=PlayerService.getPlayer(playerId, request.getSession());
			checkId = player.getId();
			copper = player.getCopper();
			golds = player.getGolds();
		}catch(Exception e){
		}
		OutputMessage om=new OutputMessage();
		om.putInt(checkId);
		if(checkId > 0){
			om.putInt(copper);
			om.putInt(golds);
		}
		response.sendMessage(Protocol.RESPONSE_QUERY_GOLD_COPPER, om);
	}
	
	/**
	 * 添加金币或者宝石
	 * @param request
	 * @param response
	 */
	public void requestAddPlayerRich(SocketRequest request,SocketResponse response){
		InputMessage im=request.getInputMessage();
		im.getInt();
		int playerId=im.getInt();
		int copper=im.getInt();
		int gold=im.getInt();
		String tip = "该玩家不存在,无法查询";;
		OutputMessage om=new OutputMessage();
		try{
			Player player=PlayerService.getPlayer(playerId, request.getSession());
			if(player != null){
				if(copper > 0){
					GoldService.addCopper(player, copper);
				}else{
					GoldService.consumeInCopper(player, -copper);
				}
				if(gold > 0){
					GoldService.addGold(player, gold);
				}else{
					GoldService.consumeInGold(player, -gold);
				}	
				tip = "修改玩家金币或宝石成功";			
			}
		}catch(Exception e){
		}
		om.putString(tip);
		request.getSession().sendMessage(Protocol.RESPONSE_ADD_GOLD_COPPER, om);		
	}
	
	/**
	 * 查询排行榜中玩家的数据
	 * @param request
	 * @param response
	 */
	public void checkPayForPlayer(SocketRequest request,SocketResponse response){
		InputMessage im=request.getInputMessage();
		im.getInt();
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
		responsePlayerPayInfo(payInfos,response,Protocol.RESPONSE_QUERY_PAY_LIST);
	}

	private void responsePlayerPayInfo(List<PlayerPay> payInfos,SocketResponse response,short protocol) {
		OutputMessage om=new OutputMessage();
		int size=payInfos.size();
		om.putInt(size);
		for(PlayerPay pInfo:payInfos){
			om.putInt(pInfo.getPlayerId());
			om.putInt(pInfo.getGold());
			om.putString(DateUtil.getDetailDate(pInfo.getPayTime()));
			om.putString(pInfo.getPayInfoId());			
		}
		response.sendMessage(protocol, om);
	}
	
	/**
	 * 请求查询玩家的道具数目
	 * @param request
	 * @param response
	 */
	public void queryPlayerProp(SocketRequest request,SocketResponse response){
		InputMessage im=request.getInputMessage();
		im.getInt();
		int playerId=im.getInt();
		int propId=im.getInt();
		Player player = PlayerService.getPlayer(playerId, request.getSession());
		PlayerProps playerProp= PropCacheAction.getplayerPropByPropid(player, propId);
		PropsConfig prop = ResourceCache.getInstance().getPropsConfigs().get(propId);
		OutputMessage om=new OutputMessage();
		if(playerProp == null){
			om.putString(prop.getName()+":该玩家没有拥有此道具..");
			return;
		}
		om.putString(prop.getName()+"数量为:"+playerProp.getNumber());
		response.sendMessage(Protocol.RESPONSE_QUERY_PROP_COUNT, om);
	}
	
	/**
	 * 请求清除玩家数据(目前只提供清楚玩家金币或宝石数)
	 * @param request
	 * @param response
	 */
	public void clearPlayerInfo(SocketRequest request,SocketResponse response){
		InputMessage im=request.getInputMessage();
		im.getInt();
		int playerId=im.getInt();
		int clearType=im.getInt(); //希望清空的类型  目前1为金币 2为宝石数
 		int setNum=im.getInt();    //希望设置成的数
 		Player player=PlayerService.getPlayer(playerId, request.getSession());
 		ErrorCode error=processPlayerInfo(player,clearType,setNum);
 		OutputMessage om=new OutputMessage();
 		om.putString(error.getMsg());
 		response.sendMessage(Protocol.RESPONSE_CLEAR_PLAYER_INFO, om);
	}
	
	/*
	 * 查看玩家的道具列表
	 */	
	public void requestPlayerPayList(SocketRequest request,SocketResponse response){
		InputMessage im=request.getInputMessage();
		im.getInt();
		int playerId=im.getInt();
		List<PlayerPay> payInfos = new ArrayList<PlayerPay>();
		payInfos = BaseEngine.getInstace().getPlayerPayAction().queryAllPayForPlayer(playerId);
		responsePlayerPayInfo(payInfos,response,Protocol.RESPONSE_PAY_LSIT_BY_PLAYER);
		
	}
	
	
	/*
	 * 为玩家添加道具数
	 */	
	public void requestAddProp(SocketRequest request,SocketResponse response){
		InputMessage im=request.getInputMessage();
		im.getInt();
		int playerId=im.getInt();
		int propId=im.getInt();
		int num = im.getInt();
		String tip = "修改玩家道具失败";
		OutputMessage om=new OutputMessage();
		int ownProp = 0;
		int endProp2 = 0;
		try{
			Player player = PlayerService.getPlayer(playerId, request.getSession());
			if(player != null){
				PlayerProps startProp= PropCacheAction.getplayerPropByPropid(player, propId);
				if(startProp != null){
					ownProp = startProp.getNumber();
				}
				PropsConfig prop = ResourceCache.getInstance().getPropsConfigs().get(propId);
				PlayerProps endProp = PropCacheAction.addProp(player, propId, num);
				if(endProp != null){
					endProp2 = endProp.getNumber();
				}
				tip = prop.getName()+" 修改之前数量为:"+ownProp+" 修改之后数量为:"+endProp2;
			}
		}catch(Exception e){
		}
		om.putString(tip);
		response.sendMessage(Protocol.RESPONSE_ADD_PROP, om);
		
	}
	
	/**查看玩家支付宝充值记录**/
	public void questZFBRecords(SocketRequest request,SocketResponse response){
		InputMessage im = request.getInputMessage();
		im.getInt();
		int playerId = im.getInt();
		List<ZfbPayRecord> records = BaseEngine.getInstace().getZfbPayRecordAction().getZFBRecordsByPlayerId(playerId);
		OutputMessage om = new OutputMessage();
		int size = records.size();
		om.putInt(size);
		for(ZfbPayRecord record : records){
			om.putInt(record.getPlayerId());
			om.putString(record.getGoodsName());
			om.putString(record.getGoodsPrice()+"");
			om.putString(DateUtil.getDetailDate(record.getPayTime()));
		}
		response.sendMessage(Protocol.RESPONSE_PAY_ZFB_BY_PLAYER, om);
		
	}
	
	/**请求公告列表**/
	public void questNoticeList(SocketRequest request,SocketResponse response){
		InputMessage im = request.getInputMessage();
		im.getInt();
		List<NoticeConfig> notices = getNotictConfigs();
		OutputMessage om = new OutputMessage();
		int size = notices.size();
		om.putInt(size);
		for(NoticeConfig notice : notices){
			om.putInt(notice.getId());
			om.putString(notice.getName());
			om.putString(notice.getDestance());
			om.putString(DateUtil.getDetailDate(notice.getStartTime()));
			om.putString(DateUtil.getDetailDate(notice.getEndTime()));
		}
		response.sendMessage(Protocol.RESPONSE_NOTICE_LIST, om);
	}
	
	/**请求添加新公告**/
	public void addNewNotice(SocketRequest request,SocketResponse response){
		InputMessage im = request.getInputMessage();
		im.getInt();
		String name = im.getUTF();
		String desc = im.getUTF();
		String startTime = im.getUTF();
		String endTime = im.getUTF();
		
		NoticeConfig notice = addNewNotice(name,desc,startTime,endTime);
		String tip = "添加新公告失败";
		if(notice != null){
			ResourceCache.getInstance().getNoticeConfigs().put(notice.getId(), notice);
			tip = "添加新公告成功";
		}
		
		OutputMessage om = new OutputMessage();
		om.putString(tip);
		
		response.sendMessage(Protocol.RESPONSE_COMMON_TIP_MESSAGE, om);
		
	}
	
	/**请求修改新公告**/
	public void updateNewNotice(SocketRequest request,SocketResponse response){
		InputMessage im = request.getInputMessage();
		im.getInt();
		int id = im.getInt();
		String name = im.getUTF();
		String desc = im.getUTF();
		String startTime = im.getUTF();
		String endTime = im.getUTF();
		
		NoticeConfig notice = ResourceCache.getInstance().getNoticeConfigs().get(id);
		if(notice == null){
			notice = BaseEngine.getInstace().getNoticeConfigAction().getNoticeConfig(id);
			if(notice == null){
				throw new GoldException("没有此条公告存在,不允许修改");
			}
		}
		
		notice.setName(name);
		notice.setDestance(desc);
		notice.setStartTime(DateUtil.getDetailTime(startTime));
		notice.setStartTime(DateUtil.getDetailTime(endTime));
		BaseEngine.getInstace().getNoticeConfigAction().updateNoticeConfig(notice);
		ResourceCache.getInstance().getNoticeConfigs().put(id, notice);
		
		String tip = "修改公告成功";
		
		OutputMessage om = new OutputMessage();
		om.putString(tip);
		
		response.sendMessage(Protocol.RESPONSE_COMMON_TIP_MESSAGE, om);
		
	}
	
	
	/**请求删除新公告**/
	public void deleteNewNotice(SocketRequest request,SocketResponse response){
		InputMessage im = request.getInputMessage();
		im.getInt();
		int id = im.getInt();
		
		OutputMessage om = new OutputMessage();
		String tip = "删除新公告成功";
		NoticeConfig notice = ResourceCache.getInstance().getNoticeConfigs().get(id);
		if(notice == null){
			notice = BaseEngine.getInstace().getNoticeConfigAction().getNoticeConfig(id);
			if(notice == null){
				tip = "没有此公告,无法删除";
				om.putString(tip);
				response.sendMessage(Protocol.RESPONSE_COMMON_TIP_MESSAGE, om);
				return;
			}
			ResourceCache.getInstance().getNoticeConfigs().put(notice.getId(), notice);
		}
		
		BaseEngine.getInstace().getNoticeConfigAction().deleteNoticeConfig(id);
		ResourceCache.getInstance().getNoticeConfigs().remove(id);
		
		
		om.putString(tip);
		
		response.sendMessage(Protocol.RESPONSE_COMMON_TIP_MESSAGE, om);
		
	}
	
	
	/**请求定时发送消息列表**/
	public void questSendMsgList(SocketRequest request,SocketResponse response){
		InputMessage im = request.getInputMessage();
		im.getInt();
		List<ClientToolNotice> notices = getClientNotices();
		int size = notices.size();
		OutputMessage om = new OutputMessage();
		om.putInt(size);
		for(ClientToolNotice no : notices){
			om.putInt(no.getId());
			om.putString(no.getNoticeMessage());
			om.putString(no.getNoticeCondition());
			om.putString(DateUtil.getDetailDate(no.getStartTime()));
			om.putString(DateUtil.getDetailDate(no.getEndTime()));
			om.putInt(no.getSendAccount());
			om.putInt(no.getSuccessSend());
			om.putByte(no.getStatus());
		}
		
		response.sendMessage(Protocol.RESPONSE_SEND_MESSAGE_LIST, om);
		
	}
	
	
	/**请求添加新的定时发送消息**/
	public void questAddSendMsg(SocketRequest request,SocketResponse response){
		InputMessage im = request.getInputMessage();
		im.getInt();
		String condition = im.getUTF();
		String msg = im.getUTF();
		String start = im.getUTF();
		String end = im.getUTF();
		int ccount = im.getInt();
		
		
		ClientToolNotice notice = addNewClientNotice(condition,msg,start,end,ccount);
		String tip = "添加新的客服消息失败";
		if(notice != null){
			ResourceCache.getInstance().addRunClientNotices(notice);
			tip = "添加新的客服消息成功";
		}
		OutputMessage om = new OutputMessage();
		om.putString(tip);
		response.sendMessage(Protocol.RESPONSE_COMMON_TIP_MESSAGE, om);
		
	}
	
	
	/**请求修改定时发送信息的内容**/
	public void questUpdateSendMsg(SocketRequest request,SocketResponse response){
		InputMessage im = request.getInputMessage();
		im.getInt();
		int noticeId = im.getInt();
		String msg = im.getUTF();
		String condition = im.getUTF();
		String start = im.getUTF();
		String end = im.getUTF();
		int ccount = im.getInt();
		byte status = im.getByte();
		
		String tip = "请求修改发送信息失败";
		ClientToolNotice notice = ResourceCache.getInstance().getClientNotices().get(noticeId);
		if(notice != null){
			notice.setNoticeMessage(msg);
			notice.setNoticeMessage(condition);
			notice.setStartTime(DateUtil.getDetailTime(start));
			notice.setEndTime(DateUtil.getDetailTime(end));
			notice.setSendAccount(ccount);
			notice.setSendSpace("0");
			notice.setStatus(status);
			notice.setSuccessSend(0);
			BaseEngine.getInstace().getClientNoticeAction().updateClientNotice(notice);
			ResourceCache.getInstance().removeRunClientNotice(notice);
			ResourceCache.getInstance().addRunClientNotices(notice);
			tip = "请求修改发送信息成功";
		}
		OutputMessage om = new OutputMessage();
		om.putString(tip);
		response.sendMessage(Protocol.RESPONSE_COMMON_TIP_MESSAGE, om);
	}
	
	
	/**请求删除定时发送消息内容**/
	public void questDeleteSendMsg(SocketRequest request,SocketResponse response){
		InputMessage im = request.getInputMessage();
		im.getInt();
		int noticeId = im.getInt();
		
		String tip = "请求删除发送信息失败";
		ClientToolNotice notice = ResourceCache.getInstance().getClientNotices().get(noticeId);
		if(notice != null){
			BaseEngine.getInstace().getClientNoticeAction().deleteClientNotice(noticeId);
			ResourceCache.getInstance().getClientNotices().remove(noticeId);
			ResourceCache.getInstance().removeRunClientNotice(notice);
			tip = "请求删除发送信息成功";
		}
		
		OutputMessage om = new OutputMessage();
		om.putString(tip);
		response.sendMessage(Protocol.RESPONSE_COMMON_TIP_MESSAGE, om);
	}
	
	
	/**请求玩家宝石与道具的消耗记录**/
	public void questConsumeRecords(SocketRequest request,SocketResponse response){
		InputMessage im = request.getInputMessage();
		im.getInt();
		int playerId = im.getInt();
		byte type = im.getByte();
		String startTime = im.getUTF();
		String endTime = im.getUTF();
		OutputMessage om = new OutputMessage();
		int result = -1;
		Player player = PlayerService.getPlayer(playerId, request.getSession());
		if(player != null){
			result = player.getId();
		}
		om.putInt(result);
		if(result > 0){
			List<PlayerConsume> records = BaseEngine.getInstace().getPlayerConsumeAction().getPlayerConsumes(playerId, type,
					DateUtil.getDetailTime(startTime),DateUtil.getDetailTime(endTime));
			int size = records.size();
			om.putInt(size);
			for(PlayerConsume record : records){
				om.putInt(record.getPlayerId());
				om.putInt(record.getBeforeNum());
				om.putInt(record.getPropConfigId());
				om.putInt(record.getConsumeNum());
				om.putInt(record.getAfterNum());
				om.putString(DateUtil.getDetailDate(record.getComsumeTime()));
				om.putByte(record.getState());
			}
		}
		response.sendMessage(Protocol.RESPONSE_PLAYER_CONSUME_RECORD, om);
	}
	
	
	
	
	
	private ClientToolNotice addNewClientNotice(String condition, String msg,
			String start, String end, int ccount) {
		ClientToolNotice notice = new ClientToolNotice();
		notice.setNoticeCondition(condition);
		notice.setNoticeMessage(msg);
		notice.setStartTime(DateUtil.getDetailTime(start));
		notice.setEndTime(DateUtil.getDetailTime(end));
		notice.setSendAccount(ccount);
		notice.setSendSpace("0");
		notice.setSuccessSend(0);
		notice.setStatus(Constants.COMMON_TYPE_ZERO);
		notice =  BaseEngine.getInstace().getClientNoticeAction().addClientNotice(notice);
		ResourceCache.getInstance().addRunClientNotices(notice);
		return notice;
	}

	private NoticeConfig  addNewNotice(String name, String desc,
			String startTime, String endTime) {
		NoticeConfig notice = new NoticeConfig();
		notice.setName(name);
		notice.setDestance(desc);
		notice.setStartTime(DateUtil.getDetailTime(startTime));
		notice.setEndTime(DateUtil.getDetailTime(endTime));
		notice = BaseEngine.getInstace().getNoticeConfigAction().addNoticeConfig(notice);
		ResourceCache.getInstance().getNoticeConfigs().put(notice.getId(), notice);
		
		return notice;
	}

	private List<NoticeConfig> getNotictConfigs(){
		Map<Integer, NoticeConfig> configs = ResourceCache.getInstance().getNoticeConfigs();
		List<NoticeConfig> list = new ArrayList<NoticeConfig>();
		for(NoticeConfig notice : configs.values()){
			list.add(notice);
		}
		Collections.sort(list, new Comparator<NoticeConfig>() {
			@Override
			public int compare(NoticeConfig o1, NoticeConfig o2) {
				return o1.getId() - o2.getId();
			}
		});
		return list;
	}
	
	
	private List<ClientToolNotice> getClientNotices(){
		Map<Integer, ClientToolNotice> configs = ResourceCache.getInstance().getClientNotices();
		List<ClientToolNotice> list = new ArrayList<ClientToolNotice>();
		for(ClientToolNotice notice : configs.values()){
			list.add(notice);
		}
		Collections.sort(list, new Comparator<ClientToolNotice>() {
			@Override
			public int compare(ClientToolNotice o1, ClientToolNotice o2) {
				return o1.getId() - o2.getId();
			}
		});
		return list;
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
