package com.orange.goldgame.system.dao;

import java.util.ArrayList;
import java.util.List;

import com.orange.goldgame.domain.CsvUtil;
import com.orange.goldgame.vo.GoodsVo;


public class GoodsDAO {
	private CsvUtil csvGoodsList;
	public GoodsDAO(){
//		String userDir = System.getProperty("user.dir");
//		try {
//			csvGoodsList = new CsvUtil(userDir + File.separator + "config"+File.separator+"goodsList.csv"); 
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	/**
	 * 获取商品兑换列表
	 * @return
	 */
	public List<GoodsVo> getGoodsList() {
		List<GoodsVo> goodsVoList = new ArrayList<GoodsVo>();
		for(int i=0;i<csvGoodsList.getRowNum();i++){
			GoodsVo goodsVo = new GoodsVo();
			for(int j=0;j<csvGoodsList.getColNum();j++){
				String str = csvGoodsList.getString(i, j);
				if(j==0){
					goodsVo.setGoodsId(Integer.parseInt(str));
				}else if(j==1){
					goodsVo.setGoodsName(str);
				}else if(j==2){
					goodsVo.setExchangeVoucher(Integer.parseInt(str));
				}else if(j==3){
//					goodsVo.setIntro(str);
				}
			}
			goodsVoList.add(goodsVo);
		}
		return goodsVoList;
	}
	
	/**
	 * 获取指定的商品实物
	 * @param goodsId
	 * @return
	 */
	public GoodsVo getGoodsById(int goodsId) {
		GoodsVo goodsVo = new GoodsVo();
		for(int i=0;i<csvGoodsList.getRowNum();i++){
			for(int j=0;j<csvGoodsList.getColNum();j++){
				String str = csvGoodsList.getString(i, j);
				if(j==0){
					if(Integer.parseInt(str)==goodsId){
						goodsVo.setGoodsId(Integer.parseInt(str));
						goodsVo.setGoodsName(csvGoodsList.getString(i, j+1));
						goodsVo.setExchangeVoucher(Integer.parseInt(csvGoodsList.getString(i, j+2)));
//						goodsVo.setIntro(csvGoodsList.getString(i, j+3));
					}
				}
			}
		}
		return goodsVo;
	}

}
