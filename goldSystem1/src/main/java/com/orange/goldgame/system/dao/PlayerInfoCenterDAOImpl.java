package com.orange.goldgame.system.dao;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orange.goldgame.domain.PlayerInfoCenter;
import com.orange.goldgame.domain.PlayerInfoCenterExample;

public class PlayerInfoCenterDAOImpl implements PlayerInfoCenterDAO {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table player_info_center
	 * @ibatorgenerated  Fri Sep 13 20:23:44 CST 2013
	 */
	private SqlMapClient sqlMapClient;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_info_center
	 * @ibatorgenerated  Fri Sep 13 20:23:44 CST 2013
	 */
	public PlayerInfoCenterDAOImpl(SqlMapClient sqlMapClient) {
		super();
		this.sqlMapClient = sqlMapClient;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_info_center
	 * @ibatorgenerated  Fri Sep 13 20:23:44 CST 2013
	 */
	public int countByExample(PlayerInfoCenterExample example)
			throws SQLException {
		Integer count = (Integer) sqlMapClient.queryForObject(
				"player_info_center.ibatorgenerated_countByExample", example);
		return count.intValue();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_info_center
	 * @ibatorgenerated  Fri Sep 13 20:23:44 CST 2013
	 */
	public int deleteByExample(PlayerInfoCenterExample example)
			throws SQLException {
		int rows = sqlMapClient.delete(
				"player_info_center.ibatorgenerated_deleteByExample", example);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_info_center
	 * @ibatorgenerated  Fri Sep 13 20:23:44 CST 2013
	 */
	public int deleteByPrimaryKey(Integer id) throws SQLException {
		PlayerInfoCenter key = new PlayerInfoCenter();
		key.setId(id);
		int rows = sqlMapClient.delete(
				"player_info_center.ibatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_info_center
	 * @ibatorgenerated  Fri Sep 13 20:23:44 CST 2013
	 */
	public Integer insert(PlayerInfoCenter record) throws SQLException {
		Object newKey = sqlMapClient.insert(
				"player_info_center.ibatorgenerated_insert", record);
		return (Integer) newKey;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_info_center
	 * @ibatorgenerated  Fri Sep 13 20:23:44 CST 2013
	 */
	public Integer insertSelective(PlayerInfoCenter record) throws SQLException {
		Object newKey = sqlMapClient.insert(
				"player_info_center.ibatorgenerated_insertSelective", record);
		return (Integer) newKey;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_info_center
	 * @ibatorgenerated  Fri Sep 13 20:23:44 CST 2013
	 */
	public List selectByExample(PlayerInfoCenterExample example)
			throws SQLException {
		List list = sqlMapClient.queryForList(
				"player_info_center.ibatorgenerated_selectByExample", example);
		return list;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_info_center
	 * @ibatorgenerated  Fri Sep 13 20:23:44 CST 2013
	 */
	public PlayerInfoCenter selectByPrimaryKey(Integer id) throws SQLException {
		PlayerInfoCenter key = new PlayerInfoCenter();
		key.setId(id);
		PlayerInfoCenter record = (PlayerInfoCenter) sqlMapClient
				.queryForObject(
						"player_info_center.ibatorgenerated_selectByPrimaryKey",
						key);
		return record;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_info_center
	 * @ibatorgenerated  Fri Sep 13 20:23:44 CST 2013
	 */
	public int updateByExampleSelective(PlayerInfoCenter record,
			PlayerInfoCenterExample example) throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = sqlMapClient.update(
				"player_info_center.ibatorgenerated_updateByExampleSelective",
				parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_info_center
	 * @ibatorgenerated  Fri Sep 13 20:23:44 CST 2013
	 */
	public int updateByExample(PlayerInfoCenter record,
			PlayerInfoCenterExample example) throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = sqlMapClient.update(
				"player_info_center.ibatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_info_center
	 * @ibatorgenerated  Fri Sep 13 20:23:44 CST 2013
	 */
	public int updateByPrimaryKeySelective(PlayerInfoCenter record)
			throws SQLException {
		int rows = sqlMapClient
				.update("player_info_center.ibatorgenerated_updateByPrimaryKeySelective",
						record);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_info_center
	 * @ibatorgenerated  Fri Sep 13 20:23:44 CST 2013
	 */
	public int updateByPrimaryKey(PlayerInfoCenter record) throws SQLException {
		int rows = sqlMapClient
				.update("player_info_center.ibatorgenerated_updateByPrimaryKey",
						record);
		return rows;
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table player_info_center
	 * @ibatorgenerated  Fri Sep 13 20:23:44 CST 2013
	 */
	private static class UpdateByExampleParms extends PlayerInfoCenterExample {
		private Object record;

		public UpdateByExampleParms(Object record,
				PlayerInfoCenterExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}