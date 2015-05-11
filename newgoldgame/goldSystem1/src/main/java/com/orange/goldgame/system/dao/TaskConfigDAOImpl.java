package com.orange.goldgame.system.dao;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orange.goldgame.domain.TaskConfig;
import com.orange.goldgame.domain.TaskConfigExample;

public class TaskConfigDAOImpl implements TaskConfigDAO {

    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database table task_config
     * @ibatorgenerated  Mon Jul 22 14:30:59 CST 2013
     */
    private SqlMapClient sqlMapClient;

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table task_config
     * @ibatorgenerated  Mon Jul 22 14:30:59 CST 2013
     */
    public TaskConfigDAOImpl(SqlMapClient sqlMapClient) {
        super();
        this.sqlMapClient = sqlMapClient;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table task_config
     * @ibatorgenerated  Mon Jul 22 14:30:59 CST 2013
     */
    public int countByExample(TaskConfigExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject(
                "task_config.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table task_config
     * @ibatorgenerated  Mon Jul 22 14:30:59 CST 2013
     */
    public int deleteByExample(TaskConfigExample example) throws SQLException {
        int rows = sqlMapClient.delete(
                "task_config.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table task_config
     * @ibatorgenerated  Mon Jul 22 14:30:59 CST 2013
     */
    public int deleteByPrimaryKey(Integer id) throws SQLException {
        TaskConfig key = new TaskConfig();
        key.setId(id);
        int rows = sqlMapClient.delete(
                "task_config.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table task_config
     * @ibatorgenerated  Mon Jul 22 14:30:59 CST 2013
     */
    public Integer insert(TaskConfig record) throws SQLException {
        Object newKey = sqlMapClient.insert(
                "task_config.ibatorgenerated_insert", record);
        return (Integer) newKey;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table task_config
     * @ibatorgenerated  Mon Jul 22 14:30:59 CST 2013
     */
    public Integer insertSelective(TaskConfig record) throws SQLException {
        Object newKey = sqlMapClient.insert(
                "task_config.ibatorgenerated_insertSelective", record);
        return (Integer) newKey;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table task_config
     * @ibatorgenerated  Mon Jul 22 14:30:59 CST 2013
     */
    public List selectByExample(TaskConfigExample example) throws SQLException {
        List list = sqlMapClient.queryForList(
                "task_config.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table task_config
     * @ibatorgenerated  Mon Jul 22 14:30:59 CST 2013
     */
    public TaskConfig selectByPrimaryKey(Integer id) throws SQLException {
        TaskConfig key = new TaskConfig();
        key.setId(id);
        TaskConfig record = (TaskConfig) sqlMapClient.queryForObject(
                "task_config.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table task_config
     * @ibatorgenerated  Mon Jul 22 14:30:59 CST 2013
     */
    public int updateByExampleSelective(TaskConfig record,
            TaskConfigExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update(
                "task_config.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table task_config
     * @ibatorgenerated  Mon Jul 22 14:30:59 CST 2013
     */
    public int updateByExample(TaskConfig record, TaskConfigExample example)
            throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update(
                "task_config.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table task_config
     * @ibatorgenerated  Mon Jul 22 14:30:59 CST 2013
     */
    public int updateByPrimaryKeySelective(TaskConfig record)
            throws SQLException {
        int rows = sqlMapClient.update(
                "task_config.ibatorgenerated_updateByPrimaryKeySelective",
                record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table task_config
     * @ibatorgenerated  Mon Jul 22 14:30:59 CST 2013
     */
    public int updateByPrimaryKey(TaskConfig record) throws SQLException {
        int rows = sqlMapClient.update(
                "task_config.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This class was generated by Apache iBATIS ibator. This class corresponds to the database table task_config
     * @ibatorgenerated  Mon Jul 22 14:30:59 CST 2013
     */
    private static class UpdateByExampleParms extends TaskConfigExample {
        private Object record;

        public UpdateByExampleParms(Object record, TaskConfigExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}