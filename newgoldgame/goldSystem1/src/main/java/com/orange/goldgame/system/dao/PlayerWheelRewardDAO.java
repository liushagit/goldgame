package com.orange.goldgame.system.dao;

import java.sql.SQLException;
import java.util.List;

import com.orange.goldgame.domain.PlayerWheelReward;
import com.orange.goldgame.domain.PlayerWheelRewardExample;

public interface PlayerWheelRewardDAO {
    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_wheel_reward
     *
     * @abatorgenerated Mon Mar 24 15:26:27 CST 2014
     */
    Integer insert(PlayerWheelReward record) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_wheel_reward
     *
     * @abatorgenerated Mon Mar 24 15:26:27 CST 2014
     */
    int updateByPrimaryKey(PlayerWheelReward record) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_wheel_reward
     *
     * @abatorgenerated Mon Mar 24 15:26:27 CST 2014
     */
    int updateByPrimaryKeySelective(PlayerWheelReward record) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_wheel_reward
     *
     * @abatorgenerated Mon Mar 24 15:26:27 CST 2014
     */
    List<PlayerWheelReward> selectByExample(PlayerWheelRewardExample example) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_wheel_reward
     *
     * @abatorgenerated Mon Mar 24 15:26:27 CST 2014
     */
    PlayerWheelReward selectByPrimaryKey(Integer id) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_wheel_reward
     *
     * @abatorgenerated Mon Mar 24 15:26:27 CST 2014
     */
    int deleteByExample(PlayerWheelRewardExample example) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_wheel_reward
     *
     * @abatorgenerated Mon Mar 24 15:26:27 CST 2014
     */
    int deleteByPrimaryKey(Integer id) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_wheel_reward
     *
     * @abatorgenerated Mon Mar 24 15:26:27 CST 2014
     */
    int countByExample(PlayerWheelRewardExample example) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_wheel_reward
     *
     * @abatorgenerated Mon Mar 24 15:26:27 CST 2014
     */
    int updateByExampleSelective(PlayerWheelReward record, PlayerWheelRewardExample example) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_wheel_reward
     *
     * @abatorgenerated Mon Mar 24 15:26:27 CST 2014
     */
    int updateByExample(PlayerWheelReward record, PlayerWheelRewardExample example) throws SQLException;
}