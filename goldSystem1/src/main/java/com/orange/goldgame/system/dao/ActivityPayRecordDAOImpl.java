package com.orange.goldgame.system.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orange.goldgame.domain.ActivityPayRecord;
import com.orange.goldgame.domain.ActivityPayRecordExample;
import java.sql.SQLException;
import java.util.List;

public class ActivityPayRecordDAOImpl implements ActivityPayRecordDAO {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table activity_pay_record
	 * @ibatorgenerated  Fri Nov 22 09:22:25 CST 2013
	 */
	private SqlMapClient sqlMapClient;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table activity_pay_record
	 * @ibatorgenerated  Fri Nov 22 09:22:25 CST 2013
	 */
	public ActivityPayRecordDAOImpl(SqlMapClient sqlMapClient) {
		super();
		this.sqlMapClient = sqlMapClient;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table activity_pay_record
	 * @ibatorgenerated  Fri Nov 22 09:22:25 CST 2013
	 */
	public int countByExample(ActivityPayRecordExample example)
			throws SQLException {
		Integer count = (Integer) sqlMapClient.queryForObject(
				"activity_pay_record.ibatorgenerated_countByExample", example);
		return count.intValue();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table activity_pay_record
	 * @ibatorgenerated  Fri Nov 22 09:22:25 CST 2013
	 */
	public int deleteByExample(ActivityPayRecordExample example)
			throws SQLException {
		int rows = sqlMapClient.delete(
				"activity_pay_record.ibatorgenerated_deleteByExample", example);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table activity_pay_record
	 * @ibatorgenerated  Fri Nov 22 09:22:25 CST 2013
	 */
	public int deleteByPrimaryKey(Integer id) throws SQLException {
		ActivityPayRecord key = new ActivityPayRecord();
		key.setId(id);
		int rows = sqlMapClient.delete(
				"activity_pay_record.ibatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table activity_pay_record
	 * @ibatorgenerated  Fri Nov 22 09:22:25 CST 2013
	 */
	public Integer insert(ActivityPayRecord record) throws SQLException {
		Object newKey = sqlMapClient.insert(
				"activity_pay_record.ibatorgenerated_insert", record);
		return (Integer) newKey;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table activity_pay_record
	 * @ibatorgenerated  Fri Nov 22 09:22:25 CST 2013
	 */
	public Integer insertSelective(ActivityPayRecord record)
			throws SQLException {
		Object newKey = sqlMapClient.insert(
				"activity_pay_record.ibatorgenerated_insertSelective", record);
		return (Integer) newKey;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table activity_pay_record
	 * @ibatorgenerated  Fri Nov 22 09:22:25 CST 2013
	 */
	public List selectByExample(ActivityPayRecordExample example)
			throws SQLException {
		List list = sqlMapClient.queryForList(
				"activity_pay_record.ibatorgenerated_selectByExample", example);
		return list;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table activity_pay_record
	 * @ibatorgenerated  Fri Nov 22 09:22:25 CST 2013
	 */
	public ActivityPayRecord selectByPrimaryKey(Integer id) throws SQLException {
		ActivityPayRecord key = new ActivityPayRecord();
		key.setId(id);
		ActivityPayRecord record = (ActivityPayRecord) sqlMapClient
				.queryForObject(
						"activity_pay_record.ibatorgenerated_selectByPrimaryKey",
						key);
		return record;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table activity_pay_record
	 * @ibatorgenerated  Fri Nov 22 09:22:25 CST 2013
	 */
	public int updateByExampleSelective(ActivityPayRecord record,
			ActivityPayRecordExample example) throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = sqlMapClient.update(
				"activity_pay_record.ibatorgenerated_updateByExampleSelective",
				parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table activity_pay_record
	 * @ibatorgenerated  Fri Nov 22 09:22:25 CST 2013
	 */
	public int updateByExample(ActivityPayRecord record,
			ActivityPayRecordExample example) throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = sqlMapClient.update(
				"activity_pay_record.ibatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table activity_pay_record
	 * @ibatorgenerated  Fri Nov 22 09:22:25 CST 2013
	 */
	public int updateByPrimaryKeySelective(ActivityPayRecord record)
			throws SQLException {
		int rows = sqlMapClient
				.update("activity_pay_record.ibatorgenerated_updateByPrimaryKeySelective",
						record);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table activity_pay_record
	 * @ibatorgenerated  Fri Nov 22 09:22:25 CST 2013
	 */
	public int updateByPrimaryKey(ActivityPayRecord record) throws SQLException {
		int rows = sqlMapClient.update(
				"activity_pay_record.ibatorgenerated_updateByPrimaryKey",
				record);
		return rows;
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table activity_pay_record
	 * @ibatorgenerated  Fri Nov 22 09:22:25 CST 2013
	 */
	private static class UpdateByExampleParms extends ActivityPayRecordExample {
		private Object record;

		public UpdateByExampleParms(Object record,
				ActivityPayRecordExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}