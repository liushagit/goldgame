/**
 * SuperStarServer
 * com.orange.superstar.server.service
 * ShopServer.java
 */
package com.orange.goldgame.server.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.juice.orange.game.exception.JuiceException;
import com.juice.orange.game.handler.SocketRequest;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.log.LoggerFactory;
import com.juice.orange.game.util.InputMessage;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.business.entity.ErrorCode;
import com.orange.goldgame.cache.action.PropCacheAction;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.GoodsConfig;
import com.orange.goldgame.domain.PackageProps;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerProps;
import com.orange.goldgame.domain.PropsConfig;
import com.orange.goldgame.domain.SynMoney;
import com.orange.goldgame.exception.GoldException;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.server.ShareGoldCache;
import com.orange.goldgame.server.activity.ConsumActivityService;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.server.engine.ResponseEngine;
import com.orange.goldgame.server.manager.PlayerInfoCenterManager;
import com.orange.goldgame.server.manager.ShopManager;
import com.orange.goldgame.server.manager.TaskManager;

/**
 * @author wuruihuang 2013.5.22
 */
public class ShopServer extends BaseServer {
	public static Logger logger = LoggerFactory.getLogger(ShopServer.class);
	
	/**
	 * 75 玩家请求道具列表 
	 */
	public void requestPropList(SocketRequest request, SocketResponse response) throws JuiceException {
		//接受请求道具列表 的请求
		InputMessage msg = request.getInputMessage();
		int playerId = msg.getInt();
		
		//处理请求：处理玩家请求道具列表 的请求
		List<PackageProps> props = new ArrayList<PackageProps>();
		props.addAll(ResourceCache.getInstance().getPackageProps().values());
		Player player = PlayerService.getPlayer(playerId, request.getSession());
		int gold = player.getGolds();
		//响应请求：服务器返回道具列表 
		ResponseEngine.propListResponse(response,props,gold);
	}
	
	
	/**
	 * 玩家请求购买道具协议 
	 */
	public void requestBuyProp(SocketRequest request, SocketResponse response){
		//接收玩家购买道具的请求
		InputMessage msg = request.getInputMessage();
		int playerId = msg.getInt();
		int packagePropsId = msg.getInt();
		Player player = PlayerService.getPlayer(playerId, request.getSession());
		
		try{
			PackageProps pp = ShopManager.getInstance().buyGoods(player, packagePropsId);
		    OutputMessage om = new OutputMessage();
		    SynMoney money=ShareGoldCache.getCacheGold(player.getId());
		    if(money!=null){
		    	om.putInt(money.getGold());
		    	om.putInt(money.getCopper());
		    }else{
		    	om.putInt(player.getGolds());
		    	om.putInt(GoldService.getLeftCopper(player));		    	
		    }
		    om.putString(pp.getPropsId()+"");
		    om.putInt(1);
		    om.putInt(pp.getPropsMoney());
		    request.getSession().sendMessage(Protocol.RESPONSE_PURCHASE_PROP_SUCCESS, om);
		    //驱动任务
		    TaskManager.getInstance().driveShopTaskTate(player, packagePropsId);
		    //添加信息
		    PropsConfig pConfig = ResourceCache.getInstance().getPropsConfigs().get(pp.getPropsId());
		    
		    ErrorCode res = ConsumActivityService.buyProActivity(player, pp);
		    String info = "";
		    if(res.getStatus() == ErrorCode.SUCC){
		    	BaseServer.sendErrorMsg(request.getSession(), (short)-1, "购买" + pConfig.getName() + "成功\n赠送" + res.getMsg());
		    	StringBuffer sb = new StringBuffer();
		    	sb.append("且获得").append(res.getMsg()).append("活动奖励！");
		    	info = "您成功购买了"+pConfig.getName()+"*"+pp.getPropsNumber() + sb.toString();
		    }else{
		    	info = "您成功购买了"+pConfig.getName()+"*"+pp.getPropsNumber()+"!";
		    }
		    PlayerInfoCenterManager.getInstance()
		    .addPlayerInfoCenter(player.getId(),true,Constants.BUY_GOODS,"",info);
            
		} catch(GoldException e){
		    sendErrorMsg(response, (short)1, e.getMessage());
		}
		
	}
	
	
	/**
	 * 77  玩家请求兑换实物列表 
	 */
	public void requestExcchangeGoodsList(SocketRequest request, SocketResponse response) throws JuiceException {
		//接受请求兑换实物列表 的请求
		InputMessage msg = request.getInputMessage();
		int playerId = msg.getInt();
		
		//处理请求：处理玩家请求兑换实物列表 的请求
		List<GoodsConfig> goodsList = BaseEngine.getInstace().getCacheAction().getAllGoodsConfig();
		Player player = PlayerService.getPlayer(playerId, request.getSession());
		PlayerProps pp = PropCacheAction.getplayerPropByPropid(player, PropCacheAction.exchange_prop);
		int exchangeNum = (pp == null ? 0 : pp.getNumber());
		//响应请求：服务器返回兑换实物列表 
		ResponseEngine.goodsListResponse(response,goodsList,exchangeNum);
	}
	
	/**
	 * 37 玩家请求兑换商品实物 
	 */
	public void requestExcchangeGoods(SocketRequest request, SocketResponse response) throws JuiceException {
		//接受请求兑换实物列表 的请求
		InputMessage msg = request.getInputMessage();
		int playerId = msg.getInt();
		int goodsId = msg.getInt();
		Player player = PlayerService.getPlayer(playerId, request.getSession());
		
		boolean flag = ShopManager.getInstance().isAbleChangeGoods(player, goodsId);
		if(!flag){
		    sendErrorMsg(request.getSession(), (short)1, "您的兑换券数量不足！");
		}else{
		    
		    OutputMessage om = new OutputMessage();
		    request.getSession().sendMessage(Protocol.RESPONSE_COMFIRM_CHANGE, om);
		}
	}
	
	/**
	 * 90玩家确认兑换商品实物
	 */
	public void comfirmExcchangeGoods(SocketRequest request, SocketResponse response) throws JuiceException {
		//接受请求兑换实物列表 的请求
		InputMessage msg = request.getInputMessage();
		int playerId = msg.getInt();
		int goodsId = msg.getInt();
		String cellphone = msg.getUTF();
		Player player = PlayerService.getPlayer(playerId, request.getSession());
		try{
		    int res = ShopManager.getInstance().changeGoods(player, goodsId, cellphone);
		    OutputMessage om = new OutputMessage();
		    PlayerProps pp = PropCacheAction.getplayerPropByPropid(player, PropCacheAction.exchange_prop);
		    int exchangeNum = (pp == null ? 0 : pp.getNumber());
		    om.putInt(exchangeNum);
		    request.getSession().sendMessage(Protocol.RESPONSE__EXCHANGE_GOODS_SUCCESS, om);
		    //添加信息
		    
		    GoodsConfig config = BaseEngine.getInstace().getGoodsActionIpml().getGoodsById(goodsId);
		    String msg2 = "您消耗了" + res + "兑换券，兑换了"+config.getGoodsName()+"，请及时联系客服！";
            PlayerInfoCenterManager.getInstance().addPlayerInfoCenter( player.getId(),true,Constants.CHANGLE,"",msg2);
            
		}catch(GoldException e){
		    sendErrorMsg(request.getSession(), (short)1, e.getMessage());
		}
		
		
	}
}
