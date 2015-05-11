package com.orange.goldgame.action;

import java.util.List;

import com.orange.goldgame.domain.GoodsConfig;

public interface GoodsAction {
    GoodsConfig getGoodsById(int goodsId);

	List<GoodsConfig> findAllGoodsConfig();
}
