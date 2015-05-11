package com.orange.goldgame.system.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.rowset.CachedRowSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.orange.goldgame.util.PropertiesUtil;
import com.sun.rowset.CachedRowSetImpl;

public class DBEngine {
	//	private static final String dbConfigFile = "dbconfig.xml";
	private static final Logger log = LoggerFactory.getLogger(DBEngine.class);
	private static Map<String, Properties> dbConfig = new HashMap<String, Properties>();
	//    private static Map<String, Properties> proxoolConfig = new HashMap<String, Properties>();
	private Connection connection = null;
	private boolean keepAlive = false;
	private String connName = null;
	public static final String SINGLE = "Single";
	public static final String PROXOOL = "Proxool";
	public static final String DB_PROPERTIES = "db.properties";
	public static final String DBNAME = "orangegame";

	//	private static String CONN_NAME = "conn_ppsea";

	//    static {     
	//    	initialize(); 
	//    }    
	/**
	 * 是否保持长连接
	 * 
	 * @param connName
	 * @param keepAlive
	 */
	public DBEngine(String connName, boolean keepAlive) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			log.error("DBEngine" , e1);
			return;
		}
		this.keepAlive = keepAlive;
		this.connName = connName;
		if (keepAlive) {
			try {
				this.connection = getConnection(connName);
			} catch (Exception e) {
				log.error("ERROR|getConn Exception:", e);
			}
		}
	}

	/**
	 * 执行查询
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public CachedRowSet executeQuery(String sql) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		if (this.keepAlive && !this.connection.isClosed())
			conn = this.connection;
		else
			conn = DBEngine.getConnection(this.connName);

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			CachedRowSetImpl crs = new CachedRowSetImpl();
			crs.populate(rs);
			return crs;
		} catch (SQLException e) {
			throw e;
		}
		//释放资源
		finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				throw e;
			} finally {
				if (!this.keepAlive && conn != null) {
					log.error("ERROR|Close Connection:" + sql);
					conn.close();
				}
			}
		}
	}

	/**
	 * 执行更新
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public int executeUpdate(String sql) throws Exception {
		PreparedStatement stmt = null;
		if (!keepAlive || connection.isClosed()) {
			connection = DBEngine.getConnection(this.connName);
		}
		try {
			log.info(sql);
			stmt = connection.prepareStatement(sql);
			return stmt.executeUpdate();
		}
		//解决mysql 8小时关闭连接问题
		catch (Exception e) {
			log.error(e.getMessage());
			connection.close();
			connection = DBEngine.getConnection(this.connName);
			try {
				stmt = connection.prepareStatement(sql);
				return stmt.executeUpdate();
			} catch (Exception e1) {
				throw e1;
			}
		}
		//释放资源
		finally {
			if (stmt != null) {
				stmt.close();
			}
			if (!keepAlive && connection != null) {
				log.error("ERROR|Close Connection:" + sql);
				connection.close();
			}
		}
	}

	/**
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public int insertFetchId(String sql) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		if (this.keepAlive && !this.connection.isClosed())
			conn = this.connection;
		else
			conn = DBEngine.getConnection(this.connName);

		try {
			stmt = conn.createStatement();
			int result = stmt.executeUpdate(sql);
			int id = -1;
			if (result > 0) {
				String sqlStr = "select last_insert_id() as id";
				rs = stmt.executeQuery(sqlStr);
				if (rs.next()) {
					id = rs.getInt("id");
				}
			}
			return id;
		} catch (SQLException e) {
			throw e;
		}
		//释放资源
		finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				throw e;
			} finally {
				if (!this.keepAlive && conn != null) {
					log.error("ERROR|Close Connection:" + sql);
					conn.close();
				}
			}
		}
	}

	/**
	 * 批量执行
	 */
	public int[] executeBatch(String[] sql) throws SQLException {
		if (sql == null)
			return null;

		int[] result = new int[sql.length];
		Connection conn = null;
		Statement stmt = null;

		if (this.keepAlive && !this.connection.isClosed())
			conn = this.connection;
		else
			conn = DBEngine.getConnection(this.connName);

		try {
			stmt = conn.createStatement();
			for (int i = 0; i < sql.length; ++i) {
				stmt.addBatch(sql[i]);
			}
			result = stmt.executeBatch();
		} catch (SQLException e) {
			log.error("ERROR|DBEngine.executeBatch error:", e);
			throw new SQLException("ERROR|DBEngine.executeBatch error:" + e.toString());
		}
		//释放资源
		finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				throw e;
			} finally {
				if (!this.keepAlive && conn != null) {
					log.error("ERROR|Close Connection:|", sql);
					conn.close();
				}
			}
		}
		return result;
	}

	/**
	 * 关闭当前连接
	 */
	public void close() {
		if (this.connection != null) {
			try {
				this.connection.close();
			} catch (SQLException e) {
				log.error("ERROR|DBEngine close:", e);
			}
		}
	}

	/**
	 * 根据名字获取一个数据库连接
	 */
	public static Connection getConnection(String name) throws SQLException {
		try {
			Connection conn = null;
			if (!dbConfig.containsKey(name)) {
				log.error("ERROR|No Such ConnName:" + name);
				throw new SQLException("ERROR|No Such ConnName:" + name);
			} else {
				Properties pro = dbConfig.get(name);
				String type = (String) pro.get("type");
				if (SINGLE.equalsIgnoreCase(type)) {
					conn = getSingleConnection(pro);
				} else if (PROXOOL.equalsIgnoreCase(type)) {
					//conn = getProxoolConnection(pro);
					conn = null;
					log.error("no support proxool connection!");
				}
			}
			return conn;
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			log.error("ERROR|getConnection error:", e);
		}
		return null;
	}

	/**
	 * 获取普通的单连接
	 */
	private static Connection getSingleConnection(Properties pro) throws SQLException {
		try {
			String dbURL = (String) pro.get("url");
			return DriverManager.getConnection(dbURL);
		} catch (SQLException e) {
			log.error("ERROR|getSingleConnection error: ", e);
			throw new SQLException("ERROR|getSingleConnection fail");
		} 
	}

	/**
	 * 初始化
	 */
	public static void initialize() {
		try {
			Properties properties = PropertiesUtil.loadProperties(DB_PROPERTIES);
			String userName = properties.getProperty("username");
			String password = properties.getProperty("password");
			String url = properties.getProperty("url");
			String dburl = url + "?user=" + userName + "&password=" + password /*+ "&useUnicode=true&characterEncoding=utf8&autoReconnect=true"*/.trim();
			Properties p = new Properties();
			p.put("name", DBNAME);
			p.put("type", SINGLE);
			p.put("url", dburl);
			dbConfig.put(DBNAME, p);
		} catch (Exception e) {
			log.error("ERROR|InitDBEngine error:", e);
		}
	}

	/**
	 * 获取属性节点
	 */
	private static String getAttr(Element node, String attrName) {
		NodeList paraList = node.getElementsByTagName(attrName);
		if (paraList.getLength() <= 0)
			return null;
		return ((Element) paraList.item(0)).getAttribute("value");
	}
	
	public static void main(String[] args) {
		initialize();
		DBEngine dbEngine = new DBEngine(DBNAME, true);
		
	}
}
