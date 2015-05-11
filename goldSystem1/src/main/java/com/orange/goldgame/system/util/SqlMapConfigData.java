package com.orange.goldgame.system.util;

import java.io.Reader;

import org.apache.log4j.Logger;


import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.juice.orange.game.log.LoggerFactory;

public class SqlMapConfigData {
	private static final Logger log = LoggerFactory.getLogger(SqlMapConfigData.class);
	private static SqlMapClient sqlMap;

	public static SqlMapClient getSqlMapInstance() {
		return sqlMap;
	}
	public static void init() {
		try {
			String resource = "com/orange/goldgame/system/maps/SqlMapConfig_data.xml";
			Reader reader = Resources.getResourceAsReader(resource);
			sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
		} catch (Exception e) {
			log.error("exception", e);
			throw new RuntimeException("Error initializing: " + e);
		}
	}
}
