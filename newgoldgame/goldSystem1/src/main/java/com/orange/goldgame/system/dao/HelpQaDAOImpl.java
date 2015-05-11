package com.orange.goldgame.system.dao;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orange.goldgame.domain.HelpQa;
import com.orange.goldgame.domain.HelpQaExample;

public class HelpQaDAOImpl implements HelpQaDAO {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table help_qa
	 * @ibatorgenerated  Wed Jul 24 10:22:52 CST 2013
	 */
	private SqlMapClient sqlMapClient;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table help_qa
	 * @ibatorgenerated  Wed Jul 24 10:22:52 CST 2013
	 */
	public HelpQaDAOImpl(SqlMapClient sqlMapClient) {
		super();
		this.sqlMapClient = sqlMapClient;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table help_qa
	 * @ibatorgenerated  Wed Jul 24 10:22:52 CST 2013
	 */
	public int countByExample(HelpQaExample example) throws SQLException {
		Integer count = (Integer) sqlMapClient.queryForObject(
				"help_qa.ibatorgenerated_countByExample", example);
		return count.intValue();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table help_qa
	 * @ibatorgenerated  Wed Jul 24 10:22:52 CST 2013
	 */
	public int deleteByExample(HelpQaExample example) throws SQLException {
		int rows = sqlMapClient.delete(
				"help_qa.ibatorgenerated_deleteByExample", example);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table help_qa
	 * @ibatorgenerated  Wed Jul 24 10:22:52 CST 2013
	 */
	public int deleteByPrimaryKey(Integer id) throws SQLException {
		HelpQa key = new HelpQa();
		key.setId(id);
		int rows = sqlMapClient.delete(
				"help_qa.ibatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table help_qa
	 * @ibatorgenerated  Wed Jul 24 10:22:52 CST 2013
	 */
	public Integer insert(HelpQa record) throws SQLException {
		Object newKey = sqlMapClient.insert("help_qa.ibatorgenerated_insert",
				record);
		return (Integer) newKey;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table help_qa
	 * @ibatorgenerated  Wed Jul 24 10:22:52 CST 2013
	 */
	public Integer insertSelective(HelpQa record) throws SQLException {
		Object newKey = sqlMapClient.insert(
				"help_qa.ibatorgenerated_insertSelective", record);
		return (Integer) newKey;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table help_qa
	 * @ibatorgenerated  Wed Jul 24 10:22:52 CST 2013
	 */
	public List selectByExample(HelpQaExample example) throws SQLException {
		List list = sqlMapClient.queryForList(
				"help_qa.ibatorgenerated_selectByExample", example);
		return list;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table help_qa
	 * @ibatorgenerated  Wed Jul 24 10:22:52 CST 2013
	 */
	public HelpQa selectByPrimaryKey(Integer id) throws SQLException {
		HelpQa key = new HelpQa();
		key.setId(id);
		HelpQa record = (HelpQa) sqlMapClient.queryForObject(
				"help_qa.ibatorgenerated_selectByPrimaryKey", key);
		return record;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table help_qa
	 * @ibatorgenerated  Wed Jul 24 10:22:52 CST 2013
	 */
	public int updateByExampleSelective(HelpQa record, HelpQaExample example)
			throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = sqlMapClient.update(
				"help_qa.ibatorgenerated_updateByExampleSelective", parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table help_qa
	 * @ibatorgenerated  Wed Jul 24 10:22:52 CST 2013
	 */
	public int updateByExample(HelpQa record, HelpQaExample example)
			throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = sqlMapClient.update(
				"help_qa.ibatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table help_qa
	 * @ibatorgenerated  Wed Jul 24 10:22:52 CST 2013
	 */
	public int updateByPrimaryKeySelective(HelpQa record) throws SQLException {
		int rows = sqlMapClient.update(
				"help_qa.ibatorgenerated_updateByPrimaryKeySelective", record);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table help_qa
	 * @ibatorgenerated  Wed Jul 24 10:22:52 CST 2013
	 */
	public int updateByPrimaryKey(HelpQa record) throws SQLException {
		int rows = sqlMapClient.update(
				"help_qa.ibatorgenerated_updateByPrimaryKey", record);
		return rows;
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table help_qa
	 * @ibatorgenerated  Wed Jul 24 10:22:52 CST 2013
	 */
	private static class UpdateByExampleParms extends HelpQaExample {
		private Object record;

		public UpdateByExampleParms(Object record, HelpQaExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}