package com.orange.goldgame.util;

import java.io.Reader;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class SqlMapConfig {
	private static SqlMapClient sqlMap;

	public static SqlMapClient getSqlMapInstance() {
		return sqlMap;
	}
	public static void init() {
		try {
			String resource = "com/orange/goldgame/core/map/SqlMapConfig.xml";
			Reader reader = Resources.getResourceAsReader(resource);
			sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
		} catch (Exception e) {
			throw new RuntimeException("Error initializing: " + e);
		}
	}
}
