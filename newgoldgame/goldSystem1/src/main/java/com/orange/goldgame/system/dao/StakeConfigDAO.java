package com.orange.goldgame.system.dao;

import java.sql.SQLException;
import java.util.List;

import com.orange.goldgame.domain.StakeConfig;
import com.orange.goldgame.domain.StakeConfigExample;

public interface StakeConfigDAO {

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table stake_config
	 * @ibatorgenerated  Thu Jul 11 15:56:09 CST 2013
	 */
	int countByExample(StakeConfigExample example) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table stake_config
	 * @ibatorgenerated  Thu Jul 11 15:56:09 CST 2013
	 */
	int deleteByExample(StakeConfigExample example) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table stake_config
	 * @ibatorgenerated  Thu Jul 11 15:56:09 CST 2013
	 */
	int deleteByPrimaryKey(Integer id) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table stake_config
	 * @ibatorgenerated  Thu Jul 11 15:56:09 CST 2013
	 */
	Integer insert(StakeConfig record) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table stake_config
	 * @ibatorgenerated  Thu Jul 11 15:56:09 CST 2013
	 */
	Integer insertSelective(StakeConfig record) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table stake_config
	 * @ibatorgenerated  Thu Jul 11 15:56:09 CST 2013
	 */
	List selectByExample(StakeConfigExample example) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table stake_config
	 * @ibatorgenerated  Thu Jul 11 15:56:09 CST 2013
	 */
	StakeConfig selectByPrimaryKey(Integer id) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table stake_config
	 * @ibatorgenerated  Thu Jul 11 15:56:09 CST 2013
	 */
	int updateByExampleSelective(StakeConfig record, StakeConfigExample example)
			throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table stake_config
	 * @ibatorgenerated  Thu Jul 11 15:56:09 CST 2013
	 */
	int updateByExample(StakeConfig record, StakeConfigExample example)
			throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table stake_config
	 * @ibatorgenerated  Thu Jul 11 15:56:09 CST 2013
	 */
	int updateByPrimaryKeySelective(StakeConfig record) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table stake_config
	 * @ibatorgenerated  Thu Jul 11 15:56:09 CST 2013
	 */
	int updateByPrimaryKey(StakeConfig record) throws SQLException;
}