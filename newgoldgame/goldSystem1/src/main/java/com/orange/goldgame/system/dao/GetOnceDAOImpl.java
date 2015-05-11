package com.orange.goldgame.system.dao;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orange.goldgame.domain.GetOnce;
import com.orange.goldgame.domain.GetOnceExample;

public class GetOnceDAOImpl implements GetOnceDAO {

    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database table get_once
     * @ibatorgenerated  Mon Jul 15 09:59:23 CST 2013
     */
    private SqlMapClient sqlMapClient;

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table get_once
     * @ibatorgenerated  Mon Jul 15 09:59:23 CST 2013
     */
    public GetOnceDAOImpl(SqlMapClient sqlMapClient) {
        super();
        this.sqlMapClient = sqlMapClient;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table get_once
     * @ibatorgenerated  Mon Jul 15 09:59:23 CST 2013
     */
    public int countByExample(GetOnceExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject(
                "get_once.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table get_once
     * @ibatorgenerated  Mon Jul 15 09:59:23 CST 2013
     */
    public int deleteByExample(GetOnceExample example) throws SQLException {
        int rows = sqlMapClient.delete(
                "get_once.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table get_once
     * @ibatorgenerated  Mon Jul 15 09:59:23 CST 2013
     */
    public int deleteByPrimaryKey(Integer id) throws SQLException {
        GetOnce key = new GetOnce();
        key.setId(id);
        int rows = sqlMapClient.delete(
                "get_once.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table get_once
     * @ibatorgenerated  Mon Jul 15 09:59:23 CST 2013
     */
    public Integer insert(GetOnce record) throws SQLException {
        Object newKey = sqlMapClient.insert("get_once.ibatorgenerated_insert",
                record);
        return (Integer) newKey;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table get_once
     * @ibatorgenerated  Mon Jul 15 09:59:23 CST 2013
     */
    public Integer insertSelective(GetOnce record) throws SQLException {
        Object newKey = sqlMapClient.insert(
                "get_once.ibatorgenerated_insertSelective", record);
        return (Integer) newKey;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table get_once
     * @ibatorgenerated  Mon Jul 15 09:59:23 CST 2013
     */
    public List selectByExample(GetOnceExample example) throws SQLException {
        List list = sqlMapClient.queryForList(
                "get_once.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table get_once
     * @ibatorgenerated  Mon Jul 15 09:59:23 CST 2013
     */
    public GetOnce selectByPrimaryKey(Integer id) throws SQLException {
        GetOnce key = new GetOnce();
        key.setId(id);
        GetOnce record = (GetOnce) sqlMapClient.queryForObject(
                "get_once.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table get_once
     * @ibatorgenerated  Mon Jul 15 09:59:23 CST 2013
     */
    public int updateByExampleSelective(GetOnce record, GetOnceExample example)
            throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update(
                "get_once.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table get_once
     * @ibatorgenerated  Mon Jul 15 09:59:23 CST 2013
     */
    public int updateByExample(GetOnce record, GetOnceExample example)
            throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update(
                "get_once.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table get_once
     * @ibatorgenerated  Mon Jul 15 09:59:23 CST 2013
     */
    public int updateByPrimaryKeySelective(GetOnce record) throws SQLException {
        int rows = sqlMapClient.update(
                "get_once.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table get_once
     * @ibatorgenerated  Mon Jul 15 09:59:23 CST 2013
     */
    public int updateByPrimaryKey(GetOnce record) throws SQLException {
        int rows = sqlMapClient.update(
                "get_once.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This class was generated by Apache iBATIS ibator. This class corresponds to the database table get_once
     * @ibatorgenerated  Mon Jul 15 09:59:23 CST 2013
     */
    private static class UpdateByExampleParms extends GetOnceExample {
        private Object record;

        public UpdateByExampleParms(Object record, GetOnceExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}