package com.orange.goldgame.vo;

import java.util.Map;


public class ActivityConfigVo {

	private int golds;
	private Map<Integer, Integer> props;
	public int getGolds() {
		return golds;
	}
	public void setGolds(int golds) {
		this.golds = golds;
	}
	public Map<Integer, Integer> getProps() {
		return props;
	}
	public void setProps(Map<Integer, Integer> props) {
		this.props = props;
	}
}
