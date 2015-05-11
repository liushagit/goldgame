/**
 * Juice
 * com.juice.orange.game.bootstrap
 * ServerBootstrap.java
 */
package com.orange.goldgame.server;

import java.io.File;
import java.io.InputStream;

import org.apache.log4j.Logger;

import bsh.Interpreter;

import com.juice.orange.game.bootstrap.BootstrapProperties;
import com.juice.orange.game.bootstrap.CachedProperties;
import com.juice.orange.game.bootstrap.DataBaseProperties;
import com.juice.orange.game.cached.MemcachedResource;
import com.juice.orange.game.container.Container;
import com.juice.orange.game.log.LoggerFactory;
import com.juice.orange.game.rmi.JuiceRemoteManager;
import com.juice.orange.game.rmi.RemoteConfig;
import com.juice.orange.game.server.IJuiceServer;
import com.juice.orange.game.server.JuiceServers;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.server.manager.NoticeManager;
import com.orange.goldgame.server.task.CleanTask;
import com.orange.goldgame.server.task.GameReadyThread;
import com.orange.goldgame.server.task.GameStakeThread;
import com.orange.goldgame.server.task.TimeOutTaskManager;
import com.orange.goldgame.server.task.robot.RobotCheckTimeOut;
import com.orange.goldgame.server.task.robot.RobotTask;
import com.orange.goldgame.util.FileUtil;

/**
 * @author shaojieque
 * 2013-4-18
 */
public class ServerGameBootstrap {
	public static Logger logger = LoggerFactory.getLogger(ServerGameBootstrap.class);
	/** 获取根目录*/
	public static String ROOT_DIR = System.getProperty("user.dir");
	
	/**
	 * 主函数
	 */
	public static void main(String[] args) throws Exception{
		// 读取服务器配置
		Interpreter interpreter = new Interpreter();
		InputStream in = ServerGameBootstrap.class.getClassLoader().getResourceAsStream("juice.con");
		FileUtil.createNewFile(ROOT_DIR + File.separator + "juice.bsh", in);
		interpreter.source(ROOT_DIR + File.separator + "juice.bsh");
		Object _database = interpreter.get("database");
		if (_database != null && _database instanceof DataBaseProperties) {
			// DataBaseProperties database = (DataBaseProperties) _database;
			// setupDataBase(database);
		}
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
		
		Object _cached = interpreter.get("cached");
		if (_cached!=null && _cached instanceof CachedProperties) {
			CachedProperties cached =  (CachedProperties) _cached;
			MemcachedResource.buildMemcached(cached.getMemcachedUrl());
		}

		
		initResource();
		
		new CleanTask().start();
		TimeOutTaskManager.getInstance().runManager();
		startGameTableReady();
		startNoticeManager();
		startRobotTask();
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
	
	private static void initResource(){
		ResourceCache.getInstance().initResource();
	}
	
	private static void startGameTableReady(){
		GameReadyThread.getInstance().start();
		GameStakeThread.getInstance().start();
	}
	
	private static void startNoticeManager(){
	    NoticeManager.getInstance().startTask();
	}
	
	
	private static void startRobotTask(){
		new RobotTask().start();
		new RobotCheckTimeOut().start();
	}
}
