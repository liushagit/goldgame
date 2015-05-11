package com.orange.goldgame.server.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.juice.orange.game.exception.JuiceException;
import com.juice.orange.game.handler.SocketRequest;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.log.LoggerFactory;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.core.ErrorCode;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.ActivityConfig;
import com.orange.goldgame.domain.AppConfig;
import com.orange.goldgame.domain.AppVersion;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.util.SerializeUtil;
import com.orange.goldgame.vo.InShareGetRewardVo;
import com.orange.goldgame.vo.InShareIndexVo;
import com.orange.goldgame.vo.InShareRegisterVo;
import com.orange.goldgame.vo.OutShareGetReward;
import com.orange.goldgame.vo.OutShareIndexPlayerListVo;
import com.orange.goldgame.vo.OutShareIndexRewardListVo;
import com.orange.goldgame.vo.OutShareIndexVo;
import com.orange.goldgame.vo.OutShareRegisterVo;

public class ShareServer {

	private final Logger log = LoggerFactory.getLogger(ShareServer.class);
	/**
	 * 玩家请求分享主界面
	 * @param request
	 * @param response
	 * @throws JuiceException
	 */
	public void shareIndex(SocketRequest request, SocketResponse response) throws JuiceException {
		InShareIndexVo vo = new InShareIndexVo();
		try {
			SerializeUtil.setVo(request.getInputMessage(), vo);
		
			Player player = PlayerService.getPlayer(vo.getaPlayerId(), request.getSession());
			OutShareIndexVo outVo = new OutShareIndexVo();
			outVo.setaInviteCode(player.getId() + "");
			outVo.setbFriendNum(ShareService.getSelfShare(player));
			outVo.setcIsInvited((byte)(ShareService.isShare(player)?1:0));
			AppConfig ac = ResourceCache.getInstance().getAppConfigs().get(Constants.SHARE_CONTENT_KEY);
			String content = "";
			if (ac != null) {
				content= ac.getAppValue().replace("XXX", player.getId()+"");
			}else {
				content = Constants.SHARE_CONTENT + player.getId() + Constants.SHARE_URL;
			}
			Map<Integer, AppVersion> appVersions = ResourceCache.getInstance().getAppVersions();
			AppVersion av = null;
			for (AppVersion avs : appVersions.values()) {
				if (avs.getAppId().equals(vo.getbAppId())) {
					av = avs;
				}
			}
			if (av == null) {
				av = appVersions.get(1);
			}
			outVo.setdShareContent(av.getAppName() + content);
			outVo.seteAppUrl(av.getAppShortUrl());
			List<OutShareIndexRewardListVo> rewardListVo = ShareService.getIndexRewardListVos(player);
			List<OutShareIndexPlayerListVo> fSharePlayerList = ShareService.getSharePlayerList(player);
			outVo.setfShareRewardList(rewardListVo);
			outVo.setgSharePlayerList(fSharePlayerList);
			outVo.sethIconUrl(av.getIconUrl());
			OutputMessage outMsg = SerializeUtil.getOutputMessage(outVo);
			response.sendMessage(Protocol.RESPONSE_SHARE_INDEX, outMsg);
		} catch (Exception e) {
			log.error("shareIndex",e);
		}
		
	}
	
	
	public void shareGetReward(SocketRequest request, SocketResponse response) throws JuiceException {
		InShareGetRewardVo inVo = new InShareGetRewardVo();
		try {
			SerializeUtil.setVo(request.getInputMessage(), inVo);
			Player player = PlayerService.getPlayer(inVo.getaPlayerId(), request.getSession());
			ErrorCode res = ShareService.getShareReward(player, inVo.getbGoldId());
			if (res.getStatus() != ErrorCode.SUCC) {
				BaseServer.sendErrorMsg(response, (short)1, res.getMsg());
				return;
			}
			OutShareGetReward out = new OutShareGetReward();
			out.setaCopper(player.getCopper());
			OutputMessage outMsg = SerializeUtil.getOutputMessage(out);
			response.sendMessage(Protocol.RESPONSE_SHARE_GETREWARD, outMsg);
		} catch (Exception e) {
			log.error("shareGetReward",e);
		}
	}
	
	public void shareGetShareInfo(SocketRequest request, SocketResponse response) throws JuiceException {
		InShareRegisterVo vo = new InShareRegisterVo();
		try {
			SerializeUtil.setVo(request.getInputMessage(), vo);
			Player player = PlayerService.getPlayer(vo.getaPlayerId(), request.getSession());
			
			ErrorCode res = ShareService.registerShare(player, Integer.parseInt(vo.getbSharePlayerId()));
			if (res.getStatus() != ErrorCode.SUCC) {
				BaseServer.sendErrorMsg(response, (short)1, res.getMsg());
				return;
			}
			OutShareRegisterVo out = new OutShareRegisterVo();
			out.setaShareMsg("填写成功获得" + ShareService.SHARE_COPPER + "金币");
			out.setbResult((byte)1);
			out.setcCopper(player.getCopper());
			OutputMessage outMsg = SerializeUtil.getOutputMessage(out);
			response.sendMessage(Protocol.RESPONSE_SHARE_REGISTER, outMsg);
			
		} catch (Exception e) {
			BaseServer.sendErrorMsg(request.getSession(), (short)1, "很抱歉，您输入的邀请码错误，请重新输入！");
			log.error("shareGetShareInfo",e);
		}
	}
	
}
