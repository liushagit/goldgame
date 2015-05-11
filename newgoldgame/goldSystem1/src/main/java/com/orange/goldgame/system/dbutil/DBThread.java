/**
 * 
 */
package com.orange.goldgame.system.dbutil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.sql.rowset.CachedRowSet;

import org.apache.log4j.Logger;

import com.juice.orange.game.log.LoggerFactory;
import com.juice.orange.game.log.LoggerName;


public class DBThread extends Thread {
	private static final Logger log = LoggerFactory.getLogger(LoggerName.SQL);
	private static final Logger logExec = LoggerFactory.getLogger(LoggerName.SQLEXEC);
	private int sequenceNo = 0;
	private static DBEngine dbEngine = null;
	private boolean terminated = false;
//	private long delayTime = 5 * 60 * 1000;
	private long delayTime = 100;
	private LinkedBlockingQueue<OperaInfo> operaQueue = new LinkedBlockingQueue<OperaInfo>();
	private Map<String, OperaInfo> cachedData = new LinkedHashMap<String, OperaInfo>();

	/**
	 * 
	 * @return
	 */
	public long getDelayTime() {
		return delayTime;
	}

	/**
	 * 
	 * @param delayTime
	 */
	public void setDelayTime(long delayTime) {
		this.delayTime = delayTime;
	}

	/**
	 * 
	 */
	public void doTerminate() {
		terminated = true;
	}

	/**
	 * 
	 */
	public DBThread() {
//		delayTime = Integer.valueOf(GlobalConfig.zone.element("db").attributeValue("delaytime"));
		dbEngine = new DBEngine(DBEngine.DBNAME, true);
	}

	/**
	 * 
	 * @param op
	 */
	public void logsql(OperaInfo oper) {
		try {
			if (oper.isIs_sql()) {
				log.debug("|" + oper.getSequenceNo() + "|Q:" + operaQueue.size() + "|M:" + cachedData.size() + "|" + oper.typeToString() + "|" + oper.getPlayer_id() + "|" + oper.getTable() + "|" + oper.getId() + "|" + toSqlForLog(oper, false) + ";");
			}
		} catch (Exception e) {
			logExec.error("logsql exception:", e);
		}
	}

	/**
	 * 
	 * @param o
	 */
	public void push(OperaInfo oper) {
		oper.setSequenceNo(++this.sequenceNo);
		oper.setCommitTime(new java.util.Date());
		logsql(oper);
		operaQueue.add(oper);
	}

	/**
	 * 
	 */
	public void terminate() {
		terminated = true;
	}

	/**
	 * 
	 */
	public boolean flush(String key) {
		synchronized (cachedData) {
			OperaInfo op = cachedData.get(key);
			if (op != null) {
				exec(op, true);
				logExec.error("flush to db true:" + key);
				return true;
			}
		}
		logExec.error("flush to db false:" + key);
		return false;
	}

	/**
	 * 生成SQL语句
	 * 
	 * @param op
	 * @return
	 */
	public String toSql(OperaInfo op, boolean insert) {
		StringBuffer sql = new StringBuffer();
		if (op.getEop() == OperaInfo.EOpera.UPDATE || op.getEop() == OperaInfo.EOpera.NO_CACHE_UPDATE) {
			String opera = "update ";
			if (insert)
				opera = "insert into ";
			sql.append(opera + op.getTable() + " set ");
			Map<String, String> fields = op.getFields();
			Set<String> keys = fields.keySet();
			int i = 0;
			for (String s : keys) {
				if (i != 0)
					sql.append(",");
				Set<String> bf = op.getBlobFields();
				//blob字段，不能带单引号
				if (bf != null && bf.contains(s)) {
					sql.append(s + "=" + fields.get(s));
				} else {
					sql.append(s + "='" + fields.get(s) + "'");
				}
				++i;
			}
			if (!insert)
				sql.append(" where " + op.getWhere());
		} else if (op.getEop() == OperaInfo.EOpera.NO_CACHE_UPDATE_WHERE) {
			String opera = "update ";
			sql.append(opera + op.getTable() + " set ");
			Map<String, String> fields = op.getFields();
			Set<String> keys = fields.keySet();
			int i = 0;
			for (String s : keys) {
				if (i != 0)
					sql.append(",");
				sql.append(s + "='" + fields.get(s) + "'");
				++i;
			}
			sql.append(" where " + op.getWhere());
		} else if (op.getEop() == OperaInfo.EOpera.DELETE || op.getEop() == OperaInfo.EOpera.NO_CACHE_DELETE) {
			sql.append("delete from " + op.getTable() + " where id=" + op.getId());
		} else if (op.getEop() == OperaInfo.EOpera.DELETE_WHERE || op.getEop() == OperaInfo.EOpera.NO_CACHE_DELETE_WHERE) {
			sql.append("delete from " + op.getTable() + " where " + op.getWhere());
		}
		return sql.toString();

	}

	public String toSqlForLog(OperaInfo op, boolean insert) {
		StringBuffer sql = new StringBuffer();
		if (op.getEop() == OperaInfo.EOpera.UPDATE || op.getEop() == OperaInfo.EOpera.NO_CACHE_UPDATE) {
			String opera = "update ";
			if (insert)
				opera = "insert into ";
			sql.append(opera + op.getTable() + " set ");
			Map<String, String> fields = op.getFields();
			Set<String> keys = fields.keySet();
			List<String> keyP = toList(keys);
			int i = 0;
			for (String s : keyP) {
				if (i != 0)
					sql.append("|");
				Set<String> bf = op.getBlobFields();
				//blob字段，不能带单引号
				if (bf != null && bf.contains(s)) {
					sql.append(fields.get(s));
				} else {
					sql.append(fields.get(s));
				}
				++i;
			}
			if (!insert)
				sql.append(" where " + op.getWhere());
		} else if (op.getEop() == OperaInfo.EOpera.NO_CACHE_UPDATE_WHERE) {
			String opera = "update ";
			sql.append(opera + op.getTable() + " set ");
			Map<String, String> fields = op.getFields();
			Set<String> keys = fields.keySet();
			List<String> keyP = toList(keys);
			int i = 0;
			for (String s : keyP) {
				if (i != 0)
					sql.append("|");
				sql.append(fields.get(s));
				++i;
			}
			sql.append(" where " + op.getWhere());
		} else if (op.getEop() == OperaInfo.EOpera.DELETE || op.getEop() == OperaInfo.EOpera.NO_CACHE_DELETE) {
			sql.append("delete from " + op.getTable() + " where id=" + op.getId());
		} else if (op.getEop() == OperaInfo.EOpera.DELETE_WHERE || op.getEop() == OperaInfo.EOpera.NO_CACHE_DELETE_WHERE) {
			sql.append("delete from " + op.getTable() + " where " + op.getWhere());
		}
		return sql.toString();

	}
	
	public static List<String> toList(Set<String> old_key){
		List<String> new_key = new ArrayList<String>();
		new_key.addAll(old_key);
		Collections.sort(new_key);
		return new_key;
	}
	/**
	 * 
	 * @return
	 */
	public DBEngine getDBEngine() {
		return dbEngine;
	}

	/**
	 * 
	 * @param table
	 * @return
	 */
	public int getMaxId(String table) {
		String sql = "select max(id) from " + table;
		try {
			CachedRowSet rowset = dbEngine.executeQuery(sql);
			if (rowset != null && rowset.next()) {
				return rowset.getInt(1);
			}
		} catch (Exception e) {
			log.error("exception:", e);
		}
		return -1;
	}
	
	public boolean isExistPlayer(int playerID) {
		String sql = "select * from player where id=" + playerID;
		try {
			CachedRowSet rowset = dbEngine.executeQuery(sql);
			if (rowset != null && rowset.next()) {
				return true;
			}
		} catch (Exception e) {
			log.error("exception:", e);
		}
		return false;
	}

	/**
	 * 
	 * @param op
	 */
	public int exec(OperaInfo op, boolean isNoCache) {
		int rows = 0;
		String sql = null;
		try {
			
			sql = toSql(op, false);
			long tb = System.nanoTime()/1000;
			rows = dbEngine.executeUpdate(sql);
			long te = System.nanoTime()/1000;
			//--------------------------------------------------
			logExec.debug("|" + "|" + op.getSequenceNo() + "|" + operaQueue.size() + "|" + cachedData.size() + "|" + op.typeToString() + "|" + op.getTable() + "|" + op.getId() + "|" + op.getItem_id() + "|" + op.getPlayer_id() + "|" + sql + "|" + rows + "|" + isNoCache + "|" + (te-tb) + "|");
			//--------------------------------------------------
			if (rows == 0 && (op.getEop() == OperaInfo.EOpera.UPDATE || op.getEop() == OperaInfo.EOpera.NO_CACHE_UPDATE)) {
				sql = toSql(op, true);
				tb = System.nanoTime()/1000;
				rows = dbEngine.executeUpdate(sql);
				te = System.nanoTime()/1000;
				logExec.debug("|" + "|" + op.getSequenceNo() + "|" + operaQueue.size() + "|" + cachedData.size() + "|" + op.typeToString() + "_INSERT" + "|" + op.getTable() + "|" + op.getId() + "|" + op.getItem_id() + "|" + op.getPlayer_id() + "|" + sql + "|" + rows + "|" + isNoCache + "|" + (te-tb) + "|");
			}
			if (isNoCache == true) {
				synchronized (cachedData) {
					logExec.info("remove err from catch:" + op.getKey() + "," + op.getTable());
					cachedData.remove(op.getKey());
				}
			}
			//--------------------------------------------------				
			return rows;
		} catch (Exception e) {
			logExec.error(sql, e);

		}
		if (isNoCache == true) {
			synchronized (cachedData) {
				logExec.error("remove err from catch:" + op.getKey() + "," + op.getTable());
				cachedData.remove(op.getKey());
			}
		}
		return -1;
	}

	/**
	 * 
	 */
	public void run() {
		long lastSyncDbTime = 0;

		while (!terminated) {
			try {
				OperaInfo op = operaQueue.poll(500, TimeUnit.MILLISECONDS);
				int count = 0;
				while (op != null) {
					String key = op.getKey();
					if (op.getEop() == OperaInfo.EOpera.DELETE_WHERE) {
						List<String> fkeys = op.getFetchKeys();
						for (String s : fkeys) {
							cachedData.remove(s);
						}
						if (fkeys.size() > 0) {
							logExec.debug(key + ":delete where remove from cache:" + fkeys.size());
						}
						exec(op, false);
					} else if (op.getEop() == OperaInfo.EOpera.DELETE) {
						OperaInfo delop = cachedData.remove(key);
						if (delop != null) {
							logExec.debug(key + ":delete remove from cache:" + delop.getKey());
						}
						exec(op, false);
					}
					//只有UPDATE(INSERT)能合并
					else if (op.getEop() == OperaInfo.EOpera.UPDATE) {
						OperaInfo opi = null;
						synchronized (cachedData) {
							opi = cachedData.get(key);
						}
						if (opi != null) {
							opi.setSequenceNo(op.getSequenceNo());
							opi.getFields().putAll(op.getFields());
						} else {
							opi = op;
						}
						synchronized (cachedData) {
							cachedData.put(key, opi);
						}
					} else {
						logExec.error("invalid oper:" + op.typeToString() + "|" + this.toSql(op, false));
					}
					if (++count > 20000)
						break;
					op = operaQueue.poll();
				}
				//---------------------------------------------
				List<String> allKeys = new LinkedList<String>();
				List<String> errKeys = new LinkedList<String>();
				java.util.Date now = new Date();
				synchronized (cachedData) {
					Collection<OperaInfo> ops = cachedData.values();
					for (OperaInfo info : ops) {
						if (info.getCommitTime().getTime() + delayTime <= now.getTime()) {
							int i = exec(info, false);
							if (i == -1) {
								errKeys.add(info.getKey());
							} else {
								allKeys.add(info.getKey());
							}
							continue;
						}
						break;
					}
					for (String s : errKeys) {
						OperaInfo opi = cachedData.get(s);
						opi.setCommitTime(new java.util.Date());
						cachedData.remove(s);
						cachedData.put(s, opi);
					}
					for (String s : allKeys) {
						cachedData.remove(s);
					}
				}
				if (lastSyncDbTime + 5000 < System.currentTimeMillis()) {
					lastSyncDbTime = System.currentTimeMillis();
					logExec.debug("curr qsize:" + operaQueue.size() + ",curr msize:" + cachedData.size() + ",proc succ:" + allKeys.size() + ",proc fail:" + errKeys.size() + ",lastSyncTime=(" + now.toString() + " - " + delayTime + "s)");
				}
			} catch (Exception e) {
				logExec.error("exception", e);
			}
		}
		//////////////////////////////////////////////////////////////
		synchronized (cachedData) {
			Collection<OperaInfo> ops = cachedData.values();
			logExec.info("DbThread, sync2db m size:" + cachedData.size());
			int count = 0;
			for (OperaInfo info : ops) {
				if ((count++) % 100 == 0) {
					logExec.info("DbThread, sync2db num :" + count);
				}
				exec(info, false);
			}
			cachedData.clear();
		}
		OperaInfo opi = null;
		int count = 0;
		logExec.info("DbThread, sync2db q size:" + operaQueue.size());
		while ((opi = operaQueue.poll()) != null) {
			if ((count++) % 100 == 0) {
				logExec.info("DbThread, sync2db num :" + count);
			}
			exec(opi, false);
		}
		logExec.info("DbThread sync2db done,m:" + cachedData.size() + ",q:" + operaQueue.size() + ",delay:" + delayTime);
		////////////////////////////////////////////////////////////
	}

	public static class OperaInfo {
		enum EOpera {
			UPDATE, DELETE,
			//UPDATE_WHERE, 
			DELETE_WHERE, NO_CACHE_UPDATE, NO_CACHE_DELETE, NO_CACHE_UPDATE_WHERE, NO_CACHE_DELETE_WHERE,
		}

		///////////////////////////////////////////////////////////////////		
		private Integer id;
		private List<Integer> fetchListId = null;
		private EOpera eop;
		private String table;
		private Map<String, String> fields;
		private Set<String> blobFields = null;
		private String where;
		private java.util.Date commitTime;
		private int sequenceNo;
		private String item_id = "-";
		private String player_id = "-";
		private boolean is_sql = true;

		public boolean isIs_sql() {
			return is_sql;
		}

		public void setIs_sql(boolean is_sql) {
			this.is_sql = is_sql;
		}

		public String getPlayer_id() {
			return player_id;
		}

		public void setPlayer_id(String player_id) {
			this.player_id = player_id;
		}

		public String getItem_id() {
			return item_id;
		}

		public void setItem_id(String item_id) {
			this.item_id = item_id;
		}

		///////////////////////////////////////////////////////////////////
		public static String getKey(String tab, int iden) {
			return tab + "-" + iden;
		}

		public String getKey() {
			return table + "-" + id;
		}

		public List<String> getFetchKeys() {
			List<String> ls = new LinkedList<String>();
			if (fetchListId != null) {
				for (Integer id : this.fetchListId) {
					ls.add(table + "-" + id);
				}
			}
			return ls;
		}

		public String typeToString() {
			if (this.eop == EOpera.UPDATE)
				return "UPDATE";
			if (this.eop == EOpera.DELETE)
				return "DELETE";
			//if (this.eop == EOpera.UPDATE_WHERE) return "UPDATE_WHERE";
			if (this.eop == EOpera.DELETE_WHERE)
				return "DELETE_WHERE";
			if (this.eop == EOpera.NO_CACHE_UPDATE)
				return "NO_CACHE_UPDATE";
			if (this.eop == EOpera.NO_CACHE_DELETE)
				return "NO_CACHE_DELETE";
			if (this.eop == EOpera.NO_CACHE_UPDATE_WHERE)
				return "NO_CACHE_UPDATE_WHERE";
			if (this.eop == EOpera.NO_CACHE_DELETE_WHERE)
				return "NO_CACHE_DELETE_WHERE";
			return "";
		}

		///////////////////////////////////////////////////////////////////		
		public Integer getId() {
			return id;
		}

		public String getTable() {
			return table;
		}

		public String getWhere() {
			return where;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public EOpera getEop() {
			return eop;
		}

		public void setEop(EOpera eop) {
			this.eop = eop;
		}

		public void setTable(String table) {
			this.table = table;
		}

		public Map<String, String> getFields() {
			return fields;
		}

		public void setFields(Map<String, String> fields) {
			this.fields = fields;
		}

		public void setWhere(String where) {
			this.where = where;
		}

		///////////////////////////////////////////////////////////////////
		public void setSequenceNo(int sequenceNo) {
			this.sequenceNo = sequenceNo;
		}

		public int getSequenceNo() {
			return sequenceNo;
		}

		public void setCommitTime(java.util.Date commitTime) {
			this.commitTime = commitTime;
		}

		public java.util.Date getCommitTime() {
			return commitTime;
		}

		public void setFetchListId(List<Integer> fetchListId) {
			this.fetchListId = fetchListId;
		}

		public List<Integer> getFetchListId() {
			return fetchListId;
		}

		public void setBlobFields(Set<String> blobFields) {
			this.blobFields = blobFields;
		}

		public Set<String> getBlobFields() {
			return blobFields;
		}
	}
}
