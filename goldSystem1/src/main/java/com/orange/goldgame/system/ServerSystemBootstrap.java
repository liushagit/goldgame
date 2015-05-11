/**
 * Juice
 * com.juice.orange.game.bootstrap
 * ServerBootstrap.java
 */
package com.orange.goldgame.system;

import java.io.File;
import java.io.InputStream;

import org.apache.log4j.Logger;

import bsh.Interpreter;

import com.juice.orange.game.bootstrap.BootstrapProperties;
import com.juice.orange.game.bootstrap.DataBaseProperties;
import com.juice.orange.game.container.Container;
import com.juice.orange.game.log.LoggerFactory;
import com.juice.orange.game.rmi.JuiceRemoteManager;
import com.juice.orange.game.rmi.RemoteConfig;
import com.juice.orange.game.server.IJuiceServer;
import com.juice.orange.game.server.JuiceServers;
import com.orange.goldgame.system.dbutil.DBEngine;
import com.orange.goldgame.system.dbutil.DBService;
import com.orange.goldgame.system.dbutil.DBThread;
import com.orange.goldgame.system.util.SqlMapConfig;
import com.orange.goldgame.system.util.SqlMapConfigData;
import com.orange.goldgame.util.FileUtil;

/**
 * @author shaojieque
 * 2013-4-18
 */
public class ServerSystemBootstrap {
	public static Logger logger = LoggerFactory.getLogger(ServerSystemBootstrap.class);
	/** 获取根目录*/
	public static String ROOT_DIR = System.getProperty("user.dir");
	
	/**
	 * 主函数
	 */
	public static void main(String[] args) throws Exception{
		// 读取服务器配置
		Interpreter interpreter = new Interpreter();
		InputStream in = ServerSystemBootstrap.class.getClassLoader().getResourceAsStream("juice.con");
		FileUtil.createNewFile(ROOT_DIR + File.separator + "juice.bsh", in);
		in = ServerSystemBootstrap.class.getClassLoader().getResourceAsStream("db.properties");
		FileUtil.createNewFile(ROOT_DIR + File.separator + "db.properties", in);
		interpreter.source(ROOT_DIR + File.separator + "juice.bsh");
//		Object _database = interpreter.get("database");
//		if (_database != null && _database instanceof DataBaseProperties) {
//			DataBaseProperties database = (DataBaseProperties) _database;
//			setupDataBase(database);
//		}
		//
		Object _bootstrap = interpreter.get("bootstrap");
		if (_bootstrap != null && _bootstrap instanceof BootstrapProperties) {
			BootstrapProperties bootstrap = (BootstrapProperties) _bootstrap;
			try {
				bootstrap.setPort(Integer.parseInt(args[0]));
			} catch (Exception e) {
			}
			setupBootstrap(bootstrap);
		}
		//
		Object _remotes = interpreter.get("remotes");
		if (_remotes != null) {
			RemoteConfig[] remotes = (RemoteConfig[]) _remotes;
			setupRemoteServer(remotes);
		}
		//
		Object _app = interpreter.get("app");
		if (_app!=null && _app instanceof Application) {
			Application app =  (Application) _app;
			app.init();
		}
		
		initDB();
	}
	
	
	// 初始化数据库配置
	private static void setupDataBase(DataBaseProperties dbp) throws Exception {
//		logger.info("connect to database:" + dbp.getUrl());
//		// create a new configuration object
//		Class.forName("com.mysql.jdbc.Driver");
//		BoneCPConfig config = new BoneCPConfig();
//		// set the JDBC url
//		config.setJdbcUrl(dbp.getUrl());
//		config.setUsername(dbp.getUserName()); // set the username
//		config.setPassword(dbp.getPassword()); // set the password
//		// 一些参数设置
//		config.setPartitionCount(3);
//		config.setMaxConnectionsPerPartition(20);
//		config.setMinConnectionsPerPartition(10);
//		config.setAcquireIncrement(3);
//		config.setPoolAvailabilityThreshold(20);
//		config.setReleaseHelperThreads(2);
//		config.setIdleMaxAge(240, TimeUnit.MINUTES);
//		config.setIdleConnectionTestPeriod(10, TimeUnit.MINUTES);
//		config.setStatementsCacheSize(20);
//		config.setStatementReleaseHelperThreads(3);
//		// setup the connection
//		BoneCP connectionPool = new BoneCP(config); 
//		ConnectionDataBaseHelper.setBoneCP(connectionPool);
	}
	
	// 初始化服务器启动配置
	private static void setupBootstrap(BootstrapProperties bp) throws Exception {
		logger.info("Configuration Server bootstrap params.......");
		logger.info("Server protocol:\t" + bp.getProtocol());
		logger.info("Server port:\t" + bp.getPort());
		IJuiceServer server = JuiceServers.createWebServer(bp.getPort());
		server.setTransport(bp.getProtocol());
		server.start().get();
	}
	
	// 初始化配置远程服务器配置
	private static void setupRemoteServer(RemoteConfig[] configs) throws Exception {
		logger.info("Configuration Remote Server.......");
		for (RemoteConfig config : configs) {
			logger.info("Add Remote Server - Name:" + config.getName() + ";Address:" + config.getAddress() +
					";Port:" + config.getPort());
			Container.addConfig(config);
		}
		JuiceRemoteManager.setupRemoteServer();
	}
	
	private static void initDB(){
		logger.info("init db --- " );
		SqlMapConfig.init();
		SqlMapConfigData.init();
		
		DBEngine.initialize();
		DBThread thread = new DBThread();
		thread.start();
		DBService.initialize();
	}
}
