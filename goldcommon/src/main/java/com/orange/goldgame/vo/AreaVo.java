package com.orange.goldgame.vo;

import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.domain.AreaConfig;
import com.orange.goldgame.util.IOutputMeasageVo;


public class AreaVo implements IOutputMeasageVo{

	private AreaConfig area;
	private int awordGolds;
	private int awordVouche;
	
	public void setArea(AreaConfig area) {
		this.area = area;
	}

	@Override
	public int getLength() {
		int length = 0;
		length = 2*3+4*2+2+area.getIntrodution().getBytes().length;
		return length;
	}
	
	@Override
	public OutputMessage parseMessage() {
		OutputMessage message = new OutputMessage();
		message.putInt(area.getId());
		message.putShort(area.getAreaType());
		message.putShort(area.getAreaClass());
		message.putInt(area.getLimitGolds());
		message.putInt(area.getSingleGolds());
		//解析字符串
		String awordStr = area.getAword();
		parsingAwordString(awordStr);
		message.putInt(awordGolds);
		message.putInt(awordVouche);
		message.putString(area.getIntrodution());
		return message;
	}
	
	private void parsingAwordString(String awordStr){
		String[] awords = awordStr.split("|");
		for(String aw:awords){
			String[] aword = aw.split("_");
			String awordType = aword[0];
			if("1".equals(awordType)){
				awordGolds = Integer.valueOf(aword[1]);
			}else if("2".equals(awordType)){
				awordVouche = Integer.valueOf(aword[1]);
			}
			
		}
	}
}
