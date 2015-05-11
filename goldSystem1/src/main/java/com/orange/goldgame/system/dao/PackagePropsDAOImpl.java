package com.orange.goldgame.system.dao;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orange.goldgame.domain.PackageProps;
import com.orange.goldgame.domain.PackagePropsExample;

public class PackagePropsDAOImpl implements PackagePropsDAO {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table package_props
	 * @ibatorgenerated  Thu Jul 11 15:53:37 CST 2013
	 */
	private SqlMapClient sqlMapClient;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table package_props
	 * @ibatorgenerated  Thu Jul 11 15:53:37 CST 2013
	 */
	public PackagePropsDAOImpl(SqlMapClient sqlMapClient) {
		super();
		this.sqlMapClient = sqlMapClient;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table package_props
	 * @ibatorgenerated  Thu Jul 11 15:53:37 CST 2013
	 */
	public int countByExample(PackagePropsExample example) throws SQLException {
		Integer count = (Integer) sqlMapClient.queryForObject(
				"package_props.ibatorgenerated_countByExample", example);
		return count.intValue();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table package_props
	 * @ibatorgenerated  Thu Jul 11 15:53:37 CST 2013
	 */
	public int deleteByExample(PackagePropsExample example) throws SQLException {
		int rows = sqlMapClient.delete(
				"package_props.ibatorgenerated_deleteByExample", example);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table package_props
	 * @ibatorgenerated  Thu Jul 11 15:53:37 CST 2013
	 */
	public int deleteByPrimaryKey(Integer id) throws SQLException {
		PackageProps key = new PackageProps();
		key.setId(id);
		int rows = sqlMapClient.delete(
				"package_props.ibatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table package_props
	 * @ibatorgenerated  Thu Jul 11 15:53:37 CST 2013
	 */
	public Integer insert(PackageProps record) throws SQLException {
		Object newKey = sqlMapClient.insert(
				"package_props.ibatorgenerated_insert", record);
		return (Integer) newKey;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table package_props
	 * @ibatorgenerated  Thu Jul 11 15:53:37 CST 2013
	 */
	public Integer insertSelective(PackageProps record) throws SQLException {
		Object newKey = sqlMapClient.insert(
				"package_props.ibatorgenerated_insertSelective", record);
		return (Integer) newKey;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table package_props
	 * @ibatorgenerated  Thu Jul 11 15:53:37 CST 2013
	 */
	public List selectByExample(PackagePropsExample example)
			throws SQLException {
		List list = sqlMapClient.queryForList(
				"package_props.ibatorgenerated_selectByExample", example);
		return list;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table package_props
	 * @ibatorgenerated  Thu Jul 11 15:53:37 CST 2013
	 */
	public PackageProps selectByPrimaryKey(Integer id) throws SQLException {
		PackageProps key = new PackageProps();
		key.setId(id);
		PackageProps record = (PackageProps) sqlMapClient.queryForObject(
				"package_props.ibatorgenerated_selectByPrimaryKey", key);
		return record;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table package_props
	 * @ibatorgenerated  Thu Jul 11 15:53:37 CST 2013
	 */
	public int updateByExampleSelective(PackageProps record,
			PackagePropsExample example) throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = sqlMapClient
				.update("package_props.ibatorgenerated_updateByExampleSelective",
						parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table package_props
	 * @ibatorgenerated  Thu Jul 11 15:53:37 CST 2013
	 */
	public int updateByExample(PackageProps record, PackagePropsExample example)
			throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = sqlMapClient.update(
				"package_props.ibatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table package_props
	 * @ibatorgenerated  Thu Jul 11 15:53:37 CST 2013
	 */
	public int updateByPrimaryKeySelective(PackageProps record)
			throws SQLException {
		int rows = sqlMapClient.update(
				"package_props.ibatorgenerated_updateByPrimaryKeySelective",
				record);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table package_props
	 * @ibatorgenerated  Thu Jul 11 15:53:37 CST 2013
	 */
	public int updateByPrimaryKey(PackageProps record) throws SQLException {
		int rows = sqlMapClient.update(
				"package_props.ibatorgenerated_updateByPrimaryKey", record);
		return rows;
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table package_props
	 * @ibatorgenerated  Thu Jul 11 15:53:37 CST 2013
	 */
	private static class UpdateByExampleParms extends PackagePropsExample {
		private Object record;

		public UpdateByExampleParms(Object record, PackagePropsExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}