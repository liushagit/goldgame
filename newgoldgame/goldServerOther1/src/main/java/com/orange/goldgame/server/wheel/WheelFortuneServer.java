package com.orange.goldgame.server.wheel;

import java.util.List;

import com.juice.orange.game.exception.JuiceException;
import com.juice.orange.game.handler.SocketRequest;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.util.InputMessage;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.core.ErrorCode;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.PayInfo;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.RGB;
import com.orange.goldgame.domain.WheelReward;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.server.pay.PayInfoService;
import com.orange.goldgame.server.service.BaseServer;
import com.orange.goldgame.server.service.PlayerService;
import com.orange.goldgame.vo.WheelMessage;

public class WheelFortuneServer {

	/**
	 * 获取主界面信息
	 * @param request
	 * @param response
	 * @throws JuiceException
	 */
	public void getAllInfo(SocketRequest request, SocketResponse response)
			throws JuiceException {
		WheelFortuneService.addSession(request.getSession());
		
		InputMessage im = request.getInputMessage();
		int playerId = im.getInt();
		Player player = PlayerService.getPlayer(playerId, request.getSession());
		
		int amout = WheelFortuneService.getTicket(player);
		
		ErrorCode wheel = WheelFortuneService.getWheelFortune();
		List<WheelReward> list = (List<WheelReward>) wheel.getObject();
		
		List<ErrorCode> msgList = WheelFortuneService.getWheelMsg(player,true);
		
		OutputMessage out = new OutputMessage();
		out.putInt(amout);
		
		out.putShort((short)list.size());
		for (WheelReward wr : list) {
			out.putByte((byte)wr.getId().intValue());
			out.putString(wr.getName());
			out.putByte((byte)wr.getRewardsType().intValue());
		}
		
		out.putShort((short)msgList.size());
		RGB rgb = null;
		for (ErrorCode code : msgList) {
			out.putString(code.getMsg());
			rgb = (RGB) code.getObject();
			out.putInt(rgb.getR());
			out.putInt(rgb.getG());
			out.putInt(rgb.getB());
		}
		response.sendMessage(Protocol.RESPONSE_WHEEL, out);
		
	}
	
	/**
	 * 检查今天是否是第一次抽奖
	 * 返回使用道具：第一次：第N次（N大于1）
	 * @param request
	 * @param response
	 * @throws JuiceException
	 */
	public void getCheckToday(SocketRequest request, SocketResponse response)
			throws JuiceException {
		InputMessage im = request.getInputMessage();
		int playerId = im.getInt();
		int phoneType = 2;
		try {
			phoneType = im.getInt();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		Player player = PlayerService.getPlayer(playerId, request.getSession());
		
		int num = WheelFortuneService.getTicket(player);
		OutputMessage out = new OutputMessage();
		if (num > 0) {
			out.putByte((byte)1);//道具抽奖
			out.putString("一");
			out.putString("");
			out.putString("");
		}else {
			// TODO 暂时不加1分钱
//			boolean isSameDay = WheelFortuneService.checkToday(player);
			int id = WheelFortuneService.ONE_YUAN_YIDONG;
			switch (phoneType) {
			case 1:// 联通
				id = 501;
				break;
			case 2:// 移动
				id = 307;
				break;
			case 3:// 电信
				id = 401;
				break;
			case 127:// 移动mm
				id = 307;
				break;

			}
			PayInfo pi = null;
//			if (!isSameDay) {
//				out.putByte((byte)2);//1分钱抽奖
//				id = WheelFortuneService.ONE_FEN;
//				pi = ResourceCache.getInstance().getPayinfoById(id);
//				out.putString((pi.getMoney().floatValue() / 100f) + "");
//			}else {
				out.putByte((byte)3);//1元抽奖
				pi = ResourceCache.getInstance().getPayinfoById(id);
				out.putString(pi.getMoney()+ "");
//			}
			out.putString(pi.getPayId());
			out.putString(PayInfoService.getInfoXml(pi, player));
		}
		response.sendMessage(Protocol.RESPONSE_WHEEL_CHECKTODAY, out);
	}
	
	/**
	 * 抽奖
	 * @param request
	 * @param response
	 * @throws JuiceException
	 */
	public void getLottery(SocketRequest request, SocketResponse response)
			throws JuiceException {
		
		InputMessage im = request.getInputMessage();
		int playerId = im.getInt();
		int lotteryType = im.getByte();
		Player player = PlayerService.getPlayer(playerId, request.getSession());
		ErrorCode res = WheelFortuneService.lottery(player, lotteryType);
		WheelFortuneService.sendResult(player, res, request.getSession());
	}
	
	/**
	 * 玩家留电话
	 * @param request
	 * @param response
	 * @throws JuiceException
	 */
	public void leaveMessage(SocketRequest request, SocketResponse response)
			throws JuiceException {
		InputMessage im = request.getInputMessage();
		int playerId = im.getInt();
		int playerWheelRecorderId = im.getInt();
		String phone = im.getUTF();
		Player player = PlayerService.getPlayer(playerId, request.getSession());
		
		boolean res = WheelFortuneService.leaveMessage(player, phone, playerWheelRecorderId);;
		if (res) {
			BaseServer.sendErrorMsg(request.getSession(), (short)-1, "填写成功，请等待客服联系");
		}
		
	}
	/**
	 * 玩家离开大转盘界面
	 * @param request
	 * @param response
	 * @throws JuiceException
	 */
	public void leaveWheel(SocketRequest request, SocketResponse response)
			throws JuiceException {
		InputMessage im = request.getInputMessage();
		int playerId = im.getInt();
		Player player = PlayerService.getPlayer(playerId, request.getSession());
		
		WheelFortuneService.leaveWheel(player);
	}
	
	
	/**
	 * 1149
	 * 获取玩家当前抽奖记录
	 * @param request
	 * @param response
	 * @throws JuiceException
	 */
	public void getSelfMessage(SocketRequest request, SocketResponse response)
			throws JuiceException {
		InputMessage im = request.getInputMessage();
		int playerId = im.getInt();
		Player player = PlayerService.getPlayer(playerId, request.getSession());
		List<WheelMessage> list = player.getPlayerWheelMessages();
		if (list != null && list.size() > 0) {
			WheelMessage wm = list.get(list.size() -1);
			OutputMessage out = new OutputMessage();
			out.putShort((short)1);
			out.putString(wm.getMsg());
			out.putInt(WheelFortuneService.self_r);
			out.putInt(WheelFortuneService.self_g);
			out.putInt(WheelFortuneService.self_b);
			WheelFortuneService.sendMessage(request.getSession(),out);
		}
	}
	public void adminCollection(SocketRequest request, SocketResponse response)
			throws JuiceException {
		InputMessage im = request.getInputMessage();
		String start = im.getUTF();
		String end = im.getUTF();
		String id = im.getUTF();
		String type = im.getUTF();
		String status = "1";
		try {
			status = im.getUTF();
		} catch (Exception e) {
		}
		
		
		if ("quary".equals(type)) {
			String msg = WheelFortuneService.getWheelRecoder(start,end,Integer.parseInt(status));
			OutputMessage out = new OutputMessage();
			out.putString(msg);
			response.sendMessage(Protocol.RESPONSE_WHEEL_ADMIN, out);
		}
		if ("update".equals(type)) {
			WheelFortuneService.updateWheelRecoder(Integer.parseInt(id));
			
			
			String msg = WheelFortuneService.getWheelRecoder(start,end,Integer.parseInt(status));
			OutputMessage out = new OutputMessage();
			out.putString(msg);
			response.sendMessage(Protocol.RESPONSE_WHEEL_ADMIN, out);
		}
	}
}
