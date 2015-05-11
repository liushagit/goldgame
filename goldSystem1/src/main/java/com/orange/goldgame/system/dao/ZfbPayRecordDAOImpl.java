package com.orange.goldgame.system.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orange.goldgame.domain.ZfbPayRecord;
import com.orange.goldgame.domain.ZfbPayRecordExample;
import java.sql.SQLException;
import java.util.List;

public class ZfbPayRecordDAOImpl implements ZfbPayRecordDAO {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table zfb_pay_record
	 * @ibatorgenerated  Wed Nov 20 10:11:05 CST 2013
	 */
	private SqlMapClient sqlMapClient;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table zfb_pay_record
	 * @ibatorgenerated  Wed Nov 20 10:11:05 CST 2013
	 */
	public ZfbPayRecordDAOImpl(SqlMapClient sqlMapClient) {
		super();
		this.sqlMapClient = sqlMapClient;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table zfb_pay_record
	 * @ibatorgenerated  Wed Nov 20 10:11:05 CST 2013
	 */
	public int countByExample(ZfbPayRecordExample example) throws SQLException {
		Integer count = (Integer) sqlMapClient.queryForObject(
				"zfb_pay_record.ibatorgenerated_countByExample", example);
		return count.intValue();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table zfb_pay_record
	 * @ibatorgenerated  Wed Nov 20 10:11:05 CST 2013
	 */
	public int deleteByExample(ZfbPayRecordExample example) throws SQLException {
		int rows = sqlMapClient.delete(
				"zfb_pay_record.ibatorgenerated_deleteByExample", example);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table zfb_pay_record
	 * @ibatorgenerated  Wed Nov 20 10:11:05 CST 2013
	 */
	public int deleteByPrimaryKey(Integer id) throws SQLException {
		ZfbPayRecord key = new ZfbPayRecord();
		key.setId(id);
		int rows = sqlMapClient.delete(
				"zfb_pay_record.ibatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table zfb_pay_record
	 * @ibatorgenerated  Wed Nov 20 10:11:05 CST 2013
	 */
	public Integer insert(ZfbPayRecord record) throws SQLException {
		Object newKey = sqlMapClient.insert(
				"zfb_pay_record.ibatorgenerated_insert", record);
		return (Integer) newKey;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table zfb_pay_record
	 * @ibatorgenerated  Wed Nov 20 10:11:05 CST 2013
	 */
	public Integer insertSelective(ZfbPayRecord record) throws SQLException {
		Object newKey = sqlMapClient.insert(
				"zfb_pay_record.ibatorgenerated_insertSelective", record);
		return (Integer) newKey;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table zfb_pay_record
	 * @ibatorgenerated  Wed Nov 20 10:11:05 CST 2013
	 */
	public List selectByExample(ZfbPayRecordExample example)
			throws SQLException {
		List list = sqlMapClient.queryForList(
				"zfb_pay_record.ibatorgenerated_selectByExample", example);
		return list;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table zfb_pay_record
	 * @ibatorgenerated  Wed Nov 20 10:11:05 CST 2013
	 */
	public ZfbPayRecord selectByPrimaryKey(Integer id) throws SQLException {
		ZfbPayRecord key = new ZfbPayRecord();
		key.setId(id);
		ZfbPayRecord record = (ZfbPayRecord) sqlMapClient.queryForObject(
				"zfb_pay_record.ibatorgenerated_selectByPrimaryKey", key);
		return record;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table zfb_pay_record
	 * @ibatorgenerated  Wed Nov 20 10:11:05 CST 2013
	 */
	public int updateByExampleSelective(ZfbPayRecord record,
			ZfbPayRecordExample example) throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = sqlMapClient.update(
				"zfb_pay_record.ibatorgenerated_updateByExampleSelective",
				parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table zfb_pay_record
	 * @ibatorgenerated  Wed Nov 20 10:11:05 CST 2013
	 */
	public int updateByExample(ZfbPayRecord record, ZfbPayRecordExample example)
			throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = sqlMapClient.update(
				"zfb_pay_record.ibatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table zfb_pay_record
	 * @ibatorgenerated  Wed Nov 20 10:11:05 CST 2013
	 */
	public int updateByPrimaryKeySelective(ZfbPayRecord record)
			throws SQLException {
		int rows = sqlMapClient.update(
				"zfb_pay_record.ibatorgenerated_updateByPrimaryKeySelective",
				record);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table zfb_pay_record
	 * @ibatorgenerated  Wed Nov 20 10:11:05 CST 2013
	 */
	public int updateByPrimaryKey(ZfbPayRecord record) throws SQLException {
		int rows = sqlMapClient.update(
				"zfb_pay_record.ibatorgenerated_updateByPrimaryKey", record);
		return rows;
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table zfb_pay_record
	 * @ibatorgenerated  Wed Nov 20 10:11:05 CST 2013
	 */
	private static class UpdateByExampleParms extends ZfbPayRecordExample {
		private Object record;

		public UpdateByExampleParms(Object record, ZfbPayRecordExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}