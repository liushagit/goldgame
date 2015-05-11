package com.orange.goldgame.system.dao;

import java.sql.SQLException;
import java.util.List;

import com.orange.goldgame.domain.ShareReward;
import com.orange.goldgame.domain.ShareRewardExample;

public interface ShareRewardDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table share_reward
     *
     * @ibatorgenerated Thu Oct 24 17:09:38 CST 2013
     */
    int countByExample(ShareRewardExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table share_reward
     *
     * @ibatorgenerated Thu Oct 24 17:09:38 CST 2013
     */
    int deleteByExample(ShareRewardExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table share_reward
     *
     * @ibatorgenerated Thu Oct 24 17:09:38 CST 2013
     */
    int deleteByPrimaryKey(Integer id) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table share_reward
     *
     * @ibatorgenerated Thu Oct 24 17:09:38 CST 2013
     */
    Integer insert(ShareReward record) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table share_reward
     *
     * @ibatorgenerated Thu Oct 24 17:09:38 CST 2013
     */
    Integer insertSelective(ShareReward record) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table share_reward
     *
     * @ibatorgenerated Thu Oct 24 17:09:38 CST 2013
     */
    List selectByExample(ShareRewardExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table share_reward
     *
     * @ibatorgenerated Thu Oct 24 17:09:38 CST 2013
     */
    ShareReward selectByPrimaryKey(Integer id) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table share_reward
     *
     * @ibatorgenerated Thu Oct 24 17:09:38 CST 2013
     */
    int updateByExampleSelective(ShareReward record, ShareRewardExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table share_reward
     *
     * @ibatorgenerated Thu Oct 24 17:09:38 CST 2013
     */
    int updateByExample(ShareReward record, ShareRewardExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table share_reward
     *
     * @ibatorgenerated Thu Oct 24 17:09:38 CST 2013
     */
    int updateByPrimaryKeySelective(ShareReward record) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table share_reward
     *
     * @ibatorgenerated Thu Oct 24 17:09:38 CST 2013
     */
    int updateByPrimaryKey(ShareReward record) throws SQLException;
}