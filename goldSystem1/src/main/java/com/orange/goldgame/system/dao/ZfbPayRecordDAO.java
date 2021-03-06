package com.orange.goldgame.system.dao;

import com.orange.goldgame.domain.ZfbPayRecord;
import com.orange.goldgame.domain.ZfbPayRecordExample;
import java.sql.SQLException;
import java.util.List;

public interface ZfbPayRecordDAO {

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table zfb_pay_record
	 * @ibatorgenerated  Wed Nov 20 10:11:05 CST 2013
	 */
	int countByExample(ZfbPayRecordExample example) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table zfb_pay_record
	 * @ibatorgenerated  Wed Nov 20 10:11:05 CST 2013
	 */
	int deleteByExample(ZfbPayRecordExample example) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table zfb_pay_record
	 * @ibatorgenerated  Wed Nov 20 10:11:05 CST 2013
	 */
	int deleteByPrimaryKey(Integer id) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table zfb_pay_record
	 * @ibatorgenerated  Wed Nov 20 10:11:05 CST 2013
	 */
	Integer insert(ZfbPayRecord record) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table zfb_pay_record
	 * @ibatorgenerated  Wed Nov 20 10:11:05 CST 2013
	 */
	Integer insertSelective(ZfbPayRecord record) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table zfb_pay_record
	 * @ibatorgenerated  Wed Nov 20 10:11:05 CST 2013
	 */
	List selectByExample(ZfbPayRecordExample example) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table zfb_pay_record
	 * @ibatorgenerated  Wed Nov 20 10:11:05 CST 2013
	 */
	ZfbPayRecord selectByPrimaryKey(Integer id) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table zfb_pay_record
	 * @ibatorgenerated  Wed Nov 20 10:11:05 CST 2013
	 */
	int updateByExampleSelective(ZfbPayRecord record,
			ZfbPayRecordExample example) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table zfb_pay_record
	 * @ibatorgenerated  Wed Nov 20 10:11:05 CST 2013
	 */
	int updateByExample(ZfbPayRecord record, ZfbPayRecordExample example)
			throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table zfb_pay_record
	 * @ibatorgenerated  Wed Nov 20 10:11:05 CST 2013
	 */
	int updateByPrimaryKeySelective(ZfbPayRecord record) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table zfb_pay_record
	 * @ibatorgenerated  Wed Nov 20 10:11:05 CST 2013
	 */
	int updateByPrimaryKey(ZfbPayRecord record) throws SQLException;
}