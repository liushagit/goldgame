package com.orange.goldgame.system.ao;

import java.util.List;

import com.orange.goldgame.system.Application;
import com.orange.goldgame.system.DAOFactory;
import com.orange.goldgame.vo.GoodsVo;

/**
 * @author wuruihuang 2013.6.21
 *
 */
public class GoodsAO{
	private DAOFactory factory;
	public GoodsAO() {
		this.factory = Application.getDAOFactory();
	}
	/** 获取商品兑换列表*/
	public List<GoodsVo> getGoodsList(){
		return factory.getGoodsDAO().getGoodsList();
	}
}
