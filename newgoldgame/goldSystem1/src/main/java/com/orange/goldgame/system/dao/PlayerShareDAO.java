package com.orange.goldgame.system.dao;

import java.sql.SQLException;
import java.util.List;

import com.orange.goldgame.domain.PlayerShare;
import com.orange.goldgame.domain.PlayerShareExample;

public interface PlayerShareDAO {

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_share
	 * @ibatorgenerated  Thu Oct 24 17:07:49 CST 2013
	 */
	int countByExample(PlayerShareExample example) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_share
	 * @ibatorgenerated  Thu Oct 24 17:07:49 CST 2013
	 */
	int deleteByExample(PlayerShareExample example) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_share
	 * @ibatorgenerated  Thu Oct 24 17:07:49 CST 2013
	 */
	int deleteByPrimaryKey(Integer id) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_share
	 * @ibatorgenerated  Thu Oct 24 17:07:49 CST 2013
	 */
	Integer insert(PlayerShare record) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_share
	 * @ibatorgenerated  Thu Oct 24 17:07:49 CST 2013
	 */
	Integer insertSelective(PlayerShare record) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_share
	 * @ibatorgenerated  Thu Oct 24 17:07:49 CST 2013
	 */
	List selectByExample(PlayerShareExample example) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_share
	 * @ibatorgenerated  Thu Oct 24 17:07:49 CST 2013
	 */
	PlayerShare selectByPrimaryKey(Integer id) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_share
	 * @ibatorgenerated  Thu Oct 24 17:07:49 CST 2013
	 */
	int updateByExampleSelective(PlayerShare record, PlayerShareExample example)
			throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_share
	 * @ibatorgenerated  Thu Oct 24 17:07:49 CST 2013
	 */
	int updateByExample(PlayerShare record, PlayerShareExample example)
			throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_share
	 * @ibatorgenerated  Thu Oct 24 17:07:49 CST 2013
	 */
	int updateByPrimaryKeySelective(PlayerShare record) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_share
	 * @ibatorgenerated  Thu Oct 24 17:07:49 CST 2013
	 */
	int updateByPrimaryKey(PlayerShare record) throws SQLException;
}