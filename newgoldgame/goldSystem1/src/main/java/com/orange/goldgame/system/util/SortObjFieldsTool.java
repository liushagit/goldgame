package com.orange.goldgame.system.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient;
import com.ibatis.sqlmap.engine.impl.SqlMapExecutorDelegate;
import com.ibatis.sqlmap.engine.mapping.result.ResultMap;
import com.ibatis.sqlmap.engine.mapping.result.ResultMapping;
import com.orange.goldgame.util.PropertiesUtil;

/**
 * @author 鱼鹰
 * 2012-12-25 上午10:25:33
 */
public class SortObjFieldsTool {

	private static Map<String, String> resultSet = new HashMap<String, String>();
	private static SqlMapClient sqlMap;
	
	public static void main(String[] args) {
		try {
			init();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String className = readString("请输入查询的obj name:").toLowerCase();
		String fieldName = readString("请输入查询的字段名:").toLowerCase();
//		String printAll = readString("是否需要按序打印所有字段名");
		
		if (!resultSet.containsKey(className)) {
			System.err.println("no class config table:" + className);
			System.exit(0);
		}
		
		ExtendedSqlMapClient esmc = (ExtendedSqlMapClient) sqlMap;
		SqlMapExecutorDelegate delegate = esmc.getDelegate();
		ResultMap rm = delegate.getResultMap(resultSet.get(className));
		ResultMapping[] rmps = rm.getResultMappings();
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < rmps.length; ++i) {
			String col = rmps[i].getColumnName();
			list.add(col.toLowerCase());
		}
		Collections.sort(list);
//		if ( printAll.equals("true") ) {
			for (String name : list ) {
				String info = name;
				if ( name.equals(fieldName) ) {
					info += "	<----------";
				}
				System.out.println(info);
			}
//		}
		
		System.out.println("Result: " + (list.indexOf(fieldName) + 1));
		System.exit(0);
	}

	public static void init() throws IOException {
		Properties properties = PropertiesUtil.loadProperties("dbtable.properties");
		for (Object clazz : properties.keySet()) {
			String resultSetName = properties.getProperty((String) clazz);
			if (resultSetName == null)
				continue;
			String className = (String) clazz;
			String simplyName = className.substring(className.lastIndexOf(".")+1, className.length());
			resultSet.put(simplyName.toLowerCase(), resultSetName);
		}
		
		String resource = "com/orange/goldgame/system/maps/SqlMapConfig.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
	}
	
	public static String readString(String msg) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = "";
		try {
			System.out.println(msg);
			str = br.readLine();
		} catch (Exception e) {
			System.err.println(e);
		}
		return str;
	}
	
}
