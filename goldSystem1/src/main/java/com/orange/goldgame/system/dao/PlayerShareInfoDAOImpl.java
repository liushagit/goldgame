package com.orange.goldgame.system.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orange.goldgame.domain.PlayerShareInfo;
import com.orange.goldgame.domain.PlayerShareInfoExample;
import java.sql.SQLException;
import java.util.List;

public class PlayerShareInfoDAOImpl implements PlayerShareInfoDAO {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table player_share_info
     *
     * @ibatorgenerated Fri Oct 25 09:38:45 CST 2013
     */
    private SqlMapClient sqlMapClient;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_share_info
     *
     * @ibatorgenerated Fri Oct 25 09:38:45 CST 2013
     */
    public PlayerShareInfoDAOImpl(SqlMapClient sqlMapClient) {
        super();
        this.sqlMapClient = sqlMapClient;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_share_info
     *
     * @ibatorgenerated Fri Oct 25 09:38:45 CST 2013
     */
    public int countByExample(PlayerShareInfoExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("player_share_info.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_share_info
     *
     * @ibatorgenerated Fri Oct 25 09:38:45 CST 2013
     */
    public int deleteByExample(PlayerShareInfoExample example) throws SQLException {
        int rows = sqlMapClient.delete("player_share_info.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_share_info
     *
     * @ibatorgenerated Fri Oct 25 09:38:45 CST 2013
     */
    public int deleteByPrimaryKey(Integer id) throws SQLException {
        PlayerShareInfo key = new PlayerShareInfo();
        key.setId(id);
        int rows = sqlMapClient.delete("player_share_info.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_share_info
     *
     * @ibatorgenerated Fri Oct 25 09:38:45 CST 2013
     */
    public Integer insert(PlayerShareInfo record) throws SQLException {
        Object newKey = sqlMapClient.insert("player_share_info.ibatorgenerated_insert", record);
        return (Integer) newKey;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_share_info
     *
     * @ibatorgenerated Fri Oct 25 09:38:45 CST 2013
     */
    public Integer insertSelective(PlayerShareInfo record) throws SQLException {
        Object newKey = sqlMapClient.insert("player_share_info.ibatorgenerated_insertSelective", record);
        return (Integer) newKey;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_share_info
     *
     * @ibatorgenerated Fri Oct 25 09:38:45 CST 2013
     */
    public List selectByExample(PlayerShareInfoExample example) throws SQLException {
        List list = sqlMapClient.queryForList("player_share_info.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_share_info
     *
     * @ibatorgenerated Fri Oct 25 09:38:45 CST 2013
     */
    public PlayerShareInfo selectByPrimaryKey(Integer id) throws SQLException {
        PlayerShareInfo key = new PlayerShareInfo();
        key.setId(id);
        PlayerShareInfo record = (PlayerShareInfo) sqlMapClient.queryForObject("player_share_info.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_share_info
     *
     * @ibatorgenerated Fri Oct 25 09:38:45 CST 2013
     */
    public int updateByExampleSelective(PlayerShareInfo record, PlayerShareInfoExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("player_share_info.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_share_info
     *
     * @ibatorgenerated Fri Oct 25 09:38:45 CST 2013
     */
    public int updateByExample(PlayerShareInfo record, PlayerShareInfoExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("player_share_info.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_share_info
     *
     * @ibatorgenerated Fri Oct 25 09:38:45 CST 2013
     */
    public int updateByPrimaryKeySelective(PlayerShareInfo record) throws SQLException {
        int rows = sqlMapClient.update("player_share_info.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_share_info
     *
     * @ibatorgenerated Fri Oct 25 09:38:45 CST 2013
     */
    public int updateByPrimaryKey(PlayerShareInfo record) throws SQLException {
        int rows = sqlMapClient.update("player_share_info.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table player_share_info
     *
     * @ibatorgenerated Fri Oct 25 09:38:45 CST 2013
     */
    private static class UpdateByExampleParms extends PlayerShareInfoExample {
        private Object record;

        public UpdateByExampleParms(Object record, PlayerShareInfoExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}