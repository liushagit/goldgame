package com.orange.goldgame.system.dao;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orange.goldgame.domain.PlayerShare;
import com.orange.goldgame.domain.PlayerShareExample;

public class PlayerShareDAOImpl implements PlayerShareDAO {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table player_share
	 * @ibatorgenerated  Thu Oct 24 17:07:49 CST 2013
	 */
	private SqlMapClient sqlMapClient;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_share
	 * @ibatorgenerated  Thu Oct 24 17:07:49 CST 2013
	 */
	public PlayerShareDAOImpl(SqlMapClient sqlMapClient) {
		super();
		this.sqlMapClient = sqlMapClient;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_share
	 * @ibatorgenerated  Thu Oct 24 17:07:49 CST 2013
	 */
	public int countByExample(PlayerShareExample example) throws SQLException {
		Integer count = (Integer) sqlMapClient.queryForObject(
				"player_share.ibatorgenerated_countByExample", example);
		return count.intValue();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_share
	 * @ibatorgenerated  Thu Oct 24 17:07:49 CST 2013
	 */
	public int deleteByExample(PlayerShareExample example) throws SQLException {
		int rows = sqlMapClient.delete(
				"player_share.ibatorgenerated_deleteByExample", example);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_share
	 * @ibatorgenerated  Thu Oct 24 17:07:49 CST 2013
	 */
	public int deleteByPrimaryKey(Integer id) throws SQLException {
		PlayerShare key = new PlayerShare();
		key.setId(id);
		int rows = sqlMapClient.delete(
				"player_share.ibatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_share
	 * @ibatorgenerated  Thu Oct 24 17:07:49 CST 2013
	 */
	public Integer insert(PlayerShare record) throws SQLException {
		Object newKey = sqlMapClient.insert(
				"player_share.ibatorgenerated_insert", record);
		return (Integer) newKey;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_share
	 * @ibatorgenerated  Thu Oct 24 17:07:49 CST 2013
	 */
	public Integer insertSelective(PlayerShare record) throws SQLException {
		Object newKey = sqlMapClient.insert(
				"player_share.ibatorgenerated_insertSelective", record);
		return (Integer) newKey;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_share
	 * @ibatorgenerated  Thu Oct 24 17:07:49 CST 2013
	 */
	public List selectByExample(PlayerShareExample example) throws SQLException {
		List list = sqlMapClient.queryForList(
				"player_share.ibatorgenerated_selectByExample", example);
		return list;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_share
	 * @ibatorgenerated  Thu Oct 24 17:07:49 CST 2013
	 */
	public PlayerShare selectByPrimaryKey(Integer id) throws SQLException {
		PlayerShare key = new PlayerShare();
		key.setId(id);
		PlayerShare record = (PlayerShare) sqlMapClient.queryForObject(
				"player_share.ibatorgenerated_selectByPrimaryKey", key);
		return record;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_share
	 * @ibatorgenerated  Thu Oct 24 17:07:49 CST 2013
	 */
	public int updateByExampleSelective(PlayerShare record,
			PlayerShareExample example) throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = sqlMapClient.update(
				"player_share.ibatorgenerated_updateByExampleSelective", parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_share
	 * @ibatorgenerated  Thu Oct 24 17:07:49 CST 2013
	 */
	public int updateByExample(PlayerShare record, PlayerShareExample example)
			throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = sqlMapClient.update(
				"player_share.ibatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_share
	 * @ibatorgenerated  Thu Oct 24 17:07:49 CST 2013
	 */
	public int updateByPrimaryKeySelective(PlayerShare record)
			throws SQLException {
		int rows = sqlMapClient.update(
				"player_share.ibatorgenerated_updateByPrimaryKeySelective",
				record);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_share
	 * @ibatorgenerated  Thu Oct 24 17:07:49 CST 2013
	 */
	public int updateByPrimaryKey(PlayerShare record) throws SQLException {
		int rows = sqlMapClient.update(
				"player_share.ibatorgenerated_updateByPrimaryKey", record);
		return rows;
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table player_share
	 * @ibatorgenerated  Thu Oct 24 17:07:49 CST 2013
	 */
	private static class UpdateByExampleParms extends PlayerShareExample {
		private Object record;

		public UpdateByExampleParms(Object record, PlayerShareExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}