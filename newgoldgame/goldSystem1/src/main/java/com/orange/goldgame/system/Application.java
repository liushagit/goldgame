/**
 * SuperStarLogin
 * com.orange.superstar.login
 * Application.java
 */
package com.orange.goldgame.system;


import org.apache.log4j.Logger;

import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.system.ao.BaseAO;
import com.orange.goldgame.system.ao.ExpectConsumeLogAO;
import com.orange.goldgame.system.ao.FriendsAO;
import com.orange.goldgame.system.ao.GiftAO;
import com.orange.goldgame.system.ao.GoodsAO;
import com.orange.goldgame.system.ao.LogAO;
import com.orange.goldgame.system.ao.MessageAO;
import com.orange.goldgame.system.ao.PlayerAO;
import com.orange.goldgame.system.ao.PropAO;
import com.orange.goldgame.system.ao.TaskAO;
import com.orange.goldgame.system.dao.CharmListDAO;
import com.orange.goldgame.system.dao.ExchangeListDAO;
import com.orange.goldgame.system.dao.ExpectConsumeLogDAO;
import com.orange.goldgame.system.dao.FriendsDAO;
import com.orange.goldgame.system.dao.GiftDAO;
import com.orange.goldgame.system.dao.GoodsDAO;
import com.orange.goldgame.system.dao.LogDAO;
import com.orange.goldgame.system.dao.MessageDAO;
import com.orange.goldgame.system.dao.PlayerDAO;
import com.orange.goldgame.system.dao.PropDAO;
import com.orange.goldgame.system.dao.RaceListDAO;
import com.orange.goldgame.system.dao.WealthListDAO;


/**
 * @author shaojieque
 * 2013-5-3
 */
public class Application {
	private static Logger logger = LoggerFactory.getLogger(Application.class);
	//
	private static DAOFactory factory;
	//静态AO
	private static BaseAO baseAO;
	private static PlayerAO playerAO;
	private static TaskAO taskAO;
	private static GiftAO giftAO;
	private static LogAO logAO;
	private static FriendsAO friendsAO;
	private static PropAO propAO;
	private static GoodsAO goodsAO;
	private static MessageAO messgaeAO;
	private static ExpectConsumeLogAO expectConsumeLogAO;
	public void init() {
		initServer();
		final LogDAO logDAO=new LogDAO();
		final FriendsDAO friendsDAO=new FriendsDAO();
		final WealthListDAO wealthListDAO=new WealthListDAO();
		final CharmListDAO charmListDAO=new CharmListDAO();
		final ExchangeListDAO exchangeListDAO=new ExchangeListDAO();
		final RaceListDAO raceListDAO=new RaceListDAO();
		final GoodsDAO goodsDAO = new GoodsDAO();
		final MessageDAO messageDAO = new MessageDAO();
		final ExpectConsumeLogDAO expectConsumeLogDAO = new ExpectConsumeLogDAO();
		factory = new DAOFactory() {



			@Override
			public GiftDAO getGiftDAO() {
				return null;
			}

			@Override
			public LogDAO getLogDAO() {
				return logDAO;
			}

			@Override
			public FriendsDAO getFriendsDAO() {
				return friendsDAO;
			}

			@Override
			public WealthListDAO getWealthListDAO() {
				return wealthListDAO;
			}

			@Override
			public CharmListDAO getCharmListDAO() {
				return charmListDAO;
			}

			@Override
			public ExchangeListDAO getExchangeListDAO() {
				return exchangeListDAO;
			}

			@Override
			public RaceListDAO getRaceListDAO() {
				return raceListDAO;
			}

			@Override
			public PropDAO getPropDAO() {
				return null;
			}

			@Override
			public GoodsDAO getGoodsDAO() {
				return goodsDAO;
			}

			@Override
			public MessageDAO getMessageDAO() {
				return messageDAO;
			}

            @Override
            public ExpectConsumeLogDAO getExpectConsumeLogDAO() {
                return expectConsumeLogDAO;
            }

			@Override
			public PlayerDAO getPlayerDAO() {
				// TODO Auto-generated method stub
				return null;
			}

		};
		
		baseAO=new BaseAO();
		
		playerAO = new PlayerAO();
		playerAO.setFactory(factory);
		
		
		giftAO = new GiftAO();
		giftAO.setFactory(factory);
		
		logAO = new LogAO();
		logAO.setFactory(factory);
		
		friendsAO=new FriendsAO();
		friendsAO.setFactory(factory);
		
		propAO = new PropAO();
		propAO.setFactory(factory);
		
		goodsAO = new GoodsAO();
		messgaeAO = new MessageAO();
		
		expectConsumeLogAO = new ExpectConsumeLogAO();
		expectConsumeLogAO.setFactory(factory);
	}
	
	public static ExpectConsumeLogAO getExpectConsumeLogAO(){
	    return expectConsumeLogAO;
	}
	
	public static MessageAO getMessgaeAO() {
		return messgaeAO;
	}
	
	public static GoodsAO getGoodsAO() {
		return goodsAO;
	}
	
	public static PropAO getPropAO() {
		return propAO;
	}
	
	public static TaskAO getTaskAO() {
		return taskAO;
	}

	public void initServer() {
		logger.info("Init servers......");
		//Container.registerServer("orange", new OrangeServer());
	}

	public static PlayerAO getPlayerAO() {
		return playerAO;
	}


	public static GiftAO getGiftAO() {
		return giftAO;
	}

	public static LogAO getLogAO() {
		return logAO;
	}
	
	public static DAOFactory getDAOFactory(){
		return factory;
	}

	public static BaseAO getBaseAO() {
		return baseAO;
	}

	public static FriendsAO getFriendsAO() {
		return friendsAO;
	}
	public static DAOFactory getFactory() {
		return factory;
	}
	
}

