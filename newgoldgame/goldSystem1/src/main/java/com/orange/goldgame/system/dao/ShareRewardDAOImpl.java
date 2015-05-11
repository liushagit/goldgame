package com.orange.goldgame.system.dao;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orange.goldgame.domain.ShareReward;
import com.orange.goldgame.domain.ShareRewardExample;

public class ShareRewardDAOImpl implements ShareRewardDAO {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table share_reward
     *
     * @ibatorgenerated Thu Oct 24 17:09:38 CST 2013
     */
    private SqlMapClient sqlMapClient;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table share_reward
     *
     * @ibatorgenerated Thu Oct 24 17:09:38 CST 2013
     */
    public ShareRewardDAOImpl(SqlMapClient sqlMapClient) {
        super();
        this.sqlMapClient = sqlMapClient;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table share_reward
     *
     * @ibatorgenerated Thu Oct 24 17:09:38 CST 2013
     */
    public int countByExample(ShareRewardExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("share_reward.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table share_reward
     *
     * @ibatorgenerated Thu Oct 24 17:09:38 CST 2013
     */
    public int deleteByExample(ShareRewardExample example) throws SQLException {
        int rows = sqlMapClient.delete("share_reward.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table share_reward
     *
     * @ibatorgenerated Thu Oct 24 17:09:38 CST 2013
     */
    public int deleteByPrimaryKey(Integer id) throws SQLException {
        ShareReward key = new ShareReward();
        key.setId(id);
        int rows = sqlMapClient.delete("share_reward.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table share_reward
     *
     * @ibatorgenerated Thu Oct 24 17:09:38 CST 2013
     */
    public Integer insert(ShareReward record) throws SQLException {
        Object newKey = sqlMapClient.insert("share_reward.ibatorgenerated_insert", record);
        return (Integer) newKey;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table share_reward
     *
     * @ibatorgenerated Thu Oct 24 17:09:38 CST 2013
     */
    public Integer insertSelective(ShareReward record) throws SQLException {
        Object newKey = sqlMapClient.insert("share_reward.ibatorgenerated_insertSelective", record);
        return (Integer) newKey;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table share_reward
     *
     * @ibatorgenerated Thu Oct 24 17:09:38 CST 2013
     */
    public List selectByExample(ShareRewardExample example) throws SQLException {
        List list = sqlMapClient.queryForList("share_reward.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table share_reward
     *
     * @ibatorgenerated Thu Oct 24 17:09:38 CST 2013
     */
    public ShareReward selectByPrimaryKey(Integer id) throws SQLException {
        ShareReward key = new ShareReward();
        key.setId(id);
        ShareReward record = (ShareReward) sqlMapClient.queryForObject("share_reward.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table share_reward
     *
     * @ibatorgenerated Thu Oct 24 17:09:38 CST 2013
     */
    public int updateByExampleSelective(ShareReward record, ShareRewardExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("share_reward.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table share_reward
     *
     * @ibatorgenerated Thu Oct 24 17:09:38 CST 2013
     */
    public int updateByExample(ShareReward record, ShareRewardExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("share_reward.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table share_reward
     *
     * @ibatorgenerated Thu Oct 24 17:09:38 CST 2013
     */
    public int updateByPrimaryKeySelective(ShareReward record) throws SQLException {
        int rows = sqlMapClient.update("share_reward.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table share_reward
     *
     * @ibatorgenerated Thu Oct 24 17:09:38 CST 2013
     */
    public int updateByPrimaryKey(ShareReward record) throws SQLException {
        int rows = sqlMapClient.update("share_reward.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table share_reward
     *
     * @ibatorgenerated Thu Oct 24 17:09:38 CST 2013
     */
    private static class UpdateByExampleParms extends ShareRewardExample {
        private Object record;

        public UpdateByExampleParms(Object record, ShareRewardExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}