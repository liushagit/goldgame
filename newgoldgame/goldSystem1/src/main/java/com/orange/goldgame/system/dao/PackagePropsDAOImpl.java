package com.orange.goldgame.system.dao;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orange.goldgame.domain.PackageProps;
import com.orange.goldgame.domain.PackagePropsExample;

public class PackagePropsDAOImpl implements PackagePropsDAO {
    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
     */
    private SqlMapClient sqlMapClient;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
     */
    public PackagePropsDAOImpl(SqlMapClient sqlMapClient) {
        super();
        this.sqlMapClient = sqlMapClient;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
     */
    public Integer insert(PackageProps record) throws SQLException {
        Object newKey = sqlMapClient.insert("package_props.abatorgenerated_insert", record);
        return (Integer) newKey;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
     */
    public int updateByPrimaryKey(PackageProps record) throws SQLException {
        int rows = sqlMapClient.update("package_props.abatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
     */
    public int updateByPrimaryKeySelective(PackageProps record) throws SQLException {
        int rows = sqlMapClient.update("package_props.abatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
     */
    @SuppressWarnings("unchecked")
    public List<PackageProps> selectByExample(PackagePropsExample example) throws SQLException {
        List<PackageProps> list = (List<PackageProps>) sqlMapClient.queryForList("package_props.abatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
     */
    public PackageProps selectByPrimaryKey(Integer id) throws SQLException {
        PackageProps key = new PackageProps();
        key.setId(id);
        PackageProps record = (PackageProps) sqlMapClient.queryForObject("package_props.abatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
     */
    public int deleteByExample(PackagePropsExample example) throws SQLException {
        int rows = sqlMapClient.delete("package_props.abatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
     */
    public int deleteByPrimaryKey(Integer id) throws SQLException {
        PackageProps key = new PackageProps();
        key.setId(id);
        int rows = sqlMapClient.delete("package_props.abatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
     */
    public int countByExample(PackagePropsExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("package_props.abatorgenerated_countByExample", example);
        return count;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
     */
    public int updateByExampleSelective(PackageProps record, PackagePropsExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("package_props.abatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
     */
    public int updateByExample(PackageProps record, PackagePropsExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("package_props.abatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This class was generated by Abator for iBATIS.
     * This class corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
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