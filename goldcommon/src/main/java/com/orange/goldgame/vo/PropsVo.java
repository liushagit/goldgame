package com.orange.goldgame.vo;

import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.domain.PackageProps;
import com.orange.goldgame.domain.PropsConfig;
import com.orange.goldgame.util.IOutputMeasageVo;

public class PropsVo implements IOutputMeasageVo{
	
	private PackageProps packageProps;
	private PropsConfig propsConfig;
	
	public PropsConfig getPropsConfig() {
		return propsConfig;
	}

	public void setPropsConfig(PropsConfig propsConfig) {
		this.propsConfig = propsConfig;
	}

	public PackageProps getPackageProps() {
		return packageProps;
	}

	public void setPackageProps(PackageProps packageProps) {
		this.packageProps = packageProps;
	}

	@Override
	public int getLength() {
		int length = 0;
		return length;
	}
	@Override
	public OutputMessage parseMessage() {
		OutputMessage msg = new OutputMessage();
		msg.putInt(propsConfig.getId());
		msg.putString(propsConfig.getName());
		msg.putInt(propsConfig.getPropsType());
		
		msg.putInt(packageProps.getPropsAward());
		msg.putInt(packageProps.getPropsMoney());
		msg.putInt(packageProps.getPropsNumber());
		return msg;
	}

}
