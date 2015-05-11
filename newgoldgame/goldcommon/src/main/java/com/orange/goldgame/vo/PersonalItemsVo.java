package com.orange.goldgame.vo;

import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.domain.PropsConfig;
import com.orange.goldgame.util.IOutputMeasageVo;

/**
 * @author wuruihuang 2013.7.8
 * 玩家个人物品信息
 */
public class PersonalItemsVo implements IOutputMeasageVo{
	private PropsConfig propsConfig;
	private int number;
	
	public void setPropsConfig(PropsConfig propsConfig) {
		this.propsConfig = propsConfig;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	@Override
	public int getLength() {
		int length = 0;
		length = 4*2;
		return length;
	}
	@Override
	public OutputMessage parseMessage() {
		OutputMessage msg = new OutputMessage();
		msg.putInt(propsConfig.getId());
		msg.putInt(propsConfig.getPropsType());
		msg.putString(propsConfig.getName());
		msg.putInt(number);
		return msg;
	}

}
