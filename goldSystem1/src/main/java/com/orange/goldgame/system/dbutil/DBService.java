package com.orange.goldgame.system.dbutil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient;
import com.ibatis.sqlmap.engine.impl.SqlMapExecutorDelegate;
import com.ibatis.sqlmap.engine.mapping.result.ResultMap;
import com.ibatis.sqlmap.engine.mapping.result.ResultMapping;
import com.orange.goldgame.domain.BaseObject;
import com.orange.goldgame.system.dbutil.DBThread.OperaInfo;
import com.orange.goldgame.system.util.SqlMapConfig;
import com.orange.goldgame.util.PropertiesUtil;

public class DBService {
	private static final Logger log = LoggerFactory.getLogger(DBService.class);
	private static boolean init = false;
	private static Map<String, String> resultSet = new HashMap<String, String>();
	private static Map<String, String> tableName = new HashMap<String, String>();
	private static final String DBTABLE_CONFIG = "dbtable.properties";
	private static Map<String, ResultMapping[]> orz = new HashMap<String, ResultMapping[]>();
	private static DBThread operaThread = null;

	public static DBThread getOperaThread() {
		return operaThread;
	}

	/**
	 * 
	 */
	public static void doTerminate() {
		log.info("DBService doTerminate beg, sync2db...");
		long beg = System.currentTimeMillis();
		try {
			if (operaThread != null) {
				operaThread.doTerminate();
			}
			operaThread.join();
		} catch (Exception e) {
			log.error("exception:", e);
		}
		long use = System.currentTimeMillis() - beg;
		log.info("DBService doTerminate end, sync2db use time:" + use);
	}

	/**
	 * 
	 */
	public static void initialize() {
		if (!init) {
			init = true;
			Properties properties = PropertiesUtil.loadProperties(DBTABLE_CONFIG);
			for (Object clazz : properties.keySet()) {
				String resultSetName = properties.getProperty((String) clazz);
				if (resultSetName == null)
					continue;
				resultSet.put((String) clazz, resultSetName);
				int pos = resultSetName.indexOf('.', 0);
				tableName.put((String) clazz, resultSetName.substring(0, pos));
			}
			operaThread = new DBThread();
			operaThread.start();
		}
	}

	/**
	 * 合并执行，Cache
	 * 
	 * @param obj
	 */
	public static void commit(BaseObject obj) {
		DBThread.OperaInfo opera = getUpdateOperaInfo(obj);
		if (opera != null) {
			operaThread.push(opera);
			return;
		}
		log.error("commit::getUpdateOperaInfo null:" + obj.toString());
	}

	/**
	 * 异步删除
	 * 
	 * @param obj
	 */
	public static void commitDelete(BaseObject obj) {
		try {
			String clazz = obj.getClass().getName();
			String id = BeanUtils.getProperty(obj, "id");
			String item_id = null;
			String player_id = null;
			try {
				item_id = BeanUtils.getProperty(obj, "itemId");
			} catch (Exception e) {
			}
			try {
				player_id = BeanUtils.getProperty(obj, "playerId");
			} catch (Exception e) {
			}
			DBThread.OperaInfo opera = new DBThread.OperaInfo();
			opera.setEop(DBThread.OperaInfo.EOpera.DELETE);
			opera.setId(Integer.valueOf(id));
			opera.setTable(tableName.get(clazz));
			opera.setFields(null);
			opera.setWhere("");
			if (item_id != null) {
				opera.setItem_id(item_id);
			}
			if (player_id != null) {
				opera.setPlayer_id(player_id);
			}
			operaThread.push(opera);
		} catch (Exception e) {
			log.error("exception", e);
		}
	}

	/**
	 * 异步删除
	 * 
	 * @param obj
	 */
	public static void commitWhereDelete(BaseObject obj, List<Integer> fetchListId) {
		try {
			HashMap<String, String> fields = new HashMap<String, String>();
			DBService.getNotNullFields(obj, fields, null);
			String whr = "";
			Set<String> name = fields.keySet();
			int count = 0;
			for (String key : name) {
				whr += key + "='" + fields.get(key) + "'";
				if (++count < name.size())
					whr += " and ";
			}
			String clazz = obj.getClass().getName();
			DBThread.OperaInfo opera = new DBThread.OperaInfo();
			opera.setEop(DBThread.OperaInfo.EOpera.DELETE_WHERE);
			opera.setId(0);
			opera.setFetchListId(fetchListId);
			opera.setTable(tableName.get(clazz));
			opera.setFields(null);
			opera.setWhere(whr);
			operaThread.push(opera);
		} catch (Exception e) {
			log.error("exception", e);
		}
	}

	//---------------------------------------------------------------------
	/**
	 * 同步删除
	 * 
	 * @param obj
	 */
	public static int commitNoCacheDelete(BaseObject obj) {
		try {
			DBThread.OperaInfo opera = getUpdateOperaInfo(obj);
			opera.setEop(DBThread.OperaInfo.EOpera.NO_CACHE_DELETE);
			if (opera != null) {
				operaThread.logsql(opera);
				return operaThread.exec(opera, true);
			}
			log.error("commitNoCache::getUpdateOperaInfo null:" + obj.toString());
		} catch (Exception e) {
			log.error("exception", e);
		}
		return -1;
	}

	/**
	 * 同步更新
	 * 
	 * @param obj
	 */
	public static int commitNoCacheUpdate(BaseObject obj) {
		try {
			DBThread.OperaInfo opera = getUpdateOperaInfo(obj);
			opera.setEop(DBThread.OperaInfo.EOpera.NO_CACHE_UPDATE);
			if (opera != null) {
				operaThread.logsql(opera);
				return operaThread.exec(opera, true);
			}
			log.error("commitNoCache::getUpdateOperaInfo null:" + obj.toString());
		} catch (Exception e) {
			log.error("exception", e);
		}
		return -1;
	}

	/**
	 * 同步批量更新
	 * 
	 * @param obj
	 * @param criteria
	 */
	public static int commitNoCacheWhereUpdate(BaseObject obj, BaseObject whereObj) {
		try {
			HashMap<String, String> fields = new HashMap<String, String>();
			DBService.getNotNullFields(whereObj, fields, null);
			String whr = "";
			Set<String> name = fields.keySet();
			int count = 0;
			for (String key : name) {
				whr += key + "='" + fields.get(key) + "'";
				if (++count < name.size())
					whr += " and ";
			}
			fields.clear();
			Set<String> blobFields = new HashSet<String>();
			DBService.getNotNullFields(obj, fields, blobFields);
			String clazz = obj.getClass().getName();
			DBThread.OperaInfo opera = new DBThread.OperaInfo();
			opera.setEop(DBThread.OperaInfo.EOpera.NO_CACHE_UPDATE_WHERE);
			opera.setId(0);
			opera.setTable(tableName.get(clazz));
			opera.setFields(fields);
			opera.setBlobFields(blobFields);
			opera.setWhere(whr);
			operaThread.logsql(opera);
			return operaThread.exec(opera, true);
		} catch (Exception e) {
			log.error("exception", e);
		}
		return -1;
	}

	/**
	 * 同步批量删除
	 * 
	 * @param obj
	 * @param criteria
	 */
	public static int commitNoCacheWhereDelete(BaseObject whereObj) {
		try {
			HashMap<String, String> fields = new HashMap<String, String>();
			DBService.getNotNullFields(whereObj, fields, null);
			String whr = "";
			Set<String> name = fields.keySet();
			int count = 0;
			for (String key : name) {
				whr += key + "='" + fields.get(key) + "'";
				if (++count < name.size())
					whr += " and ";
			}
			String clazz = whereObj.getClass().getName();
			DBThread.OperaInfo opera = new DBThread.OperaInfo();
			opera.setEop(DBThread.OperaInfo.EOpera.NO_CACHE_DELETE_WHERE);
			opera.setId(0);
			opera.setTable(tableName.get(clazz));
			opera.setFields(null);
			opera.setWhere(whr);
			operaThread.logsql(opera);
			return operaThread.exec(opera, true);
		} catch (Exception e) {
			log.error("exception", e);
		}
		return -1;
	}

	/**
	 * 转移mysql特殊字符
	 * 
	 * @param sql
	 * @return
	 */
	public static String encodeSQL(String sql) {
		if (sql == null) {
			return "";
		}
		// 涓嶇敤姝ｅ垯琛ㄨ揪寮忔浛鎹紝鐩存帴閫氳繃寰幆锛岃妭鐪乧pu鏃堕棿
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < sql.length(); ++i) {
			char c = sql.charAt(i);
			switch (c) {
			case '\\':
				sb.append("\\\\");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\'':
				sb.append("\'\'");
				break;
			case '\"':
				sb.append("\\\"");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * @param clazz
	 * @param fields
	 * @param keyId
	 */
	public static String getNotNullFields(BaseObject obj, HashMap<String, String> fields, Set<String> blobFields)

	{
		String clazz = obj.getClass().getName();
		String keyId = null;
		if (!resultSet.containsKey(clazz)) {
			log.error("no class config table:" + clazz);
			return keyId;
		}
		if (!orz.containsKey(clazz)) {
			ExtendedSqlMapClient esmc = (ExtendedSqlMapClient) SqlMapConfig.getSqlMapInstance();
			SqlMapExecutorDelegate delegate = esmc.getDelegate();
			ResultMap rm = delegate.getResultMap(resultSet.get(clazz));
			orz.put(clazz, rm.getResultMappings());
		}
		ResultMapping[] rmps = orz.get(clazz);
		try {
			for (int i = 0; i < rmps.length; ++i) {
				String col = rmps[i].getColumnName();
				String type = rmps[i].getJdbcTypeName();
				//BLOB
				if (type.equals("LONGVARBINARY")) {
					try {
						String[] vals = BeanUtils.getArrayProperty(obj, rmps[i].getPropertyName());
						StringBuffer sb = new StringBuffer();
						String hexStr = null;
						if (vals != null && vals.length > 0) {
							for (int j = 0; j < vals.length; ++j) {
								String xx = Integer.toHexString(Integer.valueOf(vals[j]).intValue() & 0xFF);
								if (xx.length() == 1)
									sb.append("0");
								sb.append(xx);
							}
							hexStr = sb.toString();
						}
						if (hexStr != null) {
							blobFields.add(col);
							fields.put(col, "0x" + hexStr);
						}
					} catch (Exception e) {
						log.error("exception:", e);
					}
					continue;
				}
				//OTHER
				String val = BeanUtils.getProperty(obj, rmps[i].getPropertyName());
				if (val == null) {
					continue;
				}
				if (col.equals("id"))
					keyId = val;
				if (type.equals("TIMESTAMP")) {
					val = strToLocalDate(val);
				} else if (type.equals("DATE")) {
					SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					val = tf.format(val);
				} else if (type.equals("BIT")) {
					if (val.equalsIgnoreCase("true"))
						val = "1";
					if (val.equalsIgnoreCase("false"))
						val = "0";
				}
				if (val != null) {
					val = DBService.encodeSQL(val);
					fields.put(col, val);
				}
			}
		} catch (Exception e) {
			log.error("exception", e);
		}
		return keyId;
	}

	/**
	 * 
	 * @param className
	 * @param id
	 * @return
	 */
	public static boolean flush(String className, int id) {
		try {
			String tab = tableName.get(className);
			if (tab == null || tab.equals("null")) {
				log.error("flush tab error, no class:" + className);
			}
			String key = OperaInfo.getKey(tab, id);
			return operaThread.flush(key);
		} catch (Exception e) {
			log.error("flush exception:" + e);
		}
		return false;
	}

	/*****************************************************************
	 * @param obj
	 * @return
	 */
	public static DBThread.OperaInfo getUpdateOperaInfo(BaseObject obj) {
		String clazz = obj.getClass().getName();
		String id = null;
		HashMap<String, String> fields = new HashMap<String, String>();
		try {
			Set<String> blobFields = new HashSet<String>();
			id = DBService.getNotNullFields(obj, fields, blobFields);
			String tab = tableName.get(clazz);
			if (tab == null || tab.equals("null")) {
				log.error("getUpdateOperaInfo tab error:" + clazz);
			}
			String whr = " id='" + id + "'";
			String item_id = null;
			String player_id = null;
			boolean is_sql = true;
			try {
				item_id = BeanUtils.getProperty(obj, "itemId");
			} catch (Exception e) {
			}
			try {
				player_id = BeanUtils.getProperty(obj, "playerId");
			} catch (Exception e) {
			}

			try {
				String printSql = BeanUtils.getProperty(obj, "printSql");
				if (!"true".equals(printSql)) {
					is_sql = false;
				}
			} catch (Exception e) {
			}
			DBThread.OperaInfo opera = new DBThread.OperaInfo();
			if (id != null)
				opera.setId(Integer.valueOf(id));
			opera.setEop(DBThread.OperaInfo.EOpera.UPDATE);
			opera.setTable(tab);
			opera.setFields(fields);
			opera.setBlobFields(blobFields);
			opera.setWhere(whr);
			opera.setIs_sql(is_sql);
			if (item_id != null) {
				opera.setItem_id(item_id);
			}
			if (player_id != null) {
				opera.setPlayer_id(player_id);
			}
			return opera;

		} catch (Exception e) {
			log.error("exception", e);
		}
		return null;
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String strToLocalDate(String str) {
		String myString = str;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
			java.util.Date d = (java.util.Date) sdf.parse(myString);
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String s = tf.format(c.getTime());
			return s;
		} catch (Exception e) {
			log.error("exception:" + str, e);
		}
		return "2008-01-01 08:00:00";
	}
	/**
	 * 
	 * @param argv
	 */
	/*
	 * public static void main(String[] argv) { SqlMapConfig.init();
	 * DBEngine.initialize(); DBService.initialize();
	 * 
	 * Test t = new Test(); byte[] bs = {14, 11, 12, 13, 127, -126};
	 * t.setId(100); t.setContent(bs); DBService.commitNoCacheUpdate(t); }
	 */
}
