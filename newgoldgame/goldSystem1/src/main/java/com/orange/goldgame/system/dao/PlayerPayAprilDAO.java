package com.orange.goldgame.system.dao;

import java.sql.SQLException;
import java.util.List;

import com.orange.goldgame.domain.PlayerPayApril;
import com.orange.goldgame.domain.PlayerPayAprilExample;

public interface PlayerPayAprilDAO {
    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_pay_april
     *
     * @abatorgenerated Wed Mar 26 14:13:55 CST 2014
     */
    Integer insert(PlayerPayApril record) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_pay_april
     *
     * @abatorgenerated Wed Mar 26 14:13:55 CST 2014
     */
    int updateByPrimaryKey(PlayerPayApril record) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_pay_april
     *
     * @abatorgenerated Wed Mar 26 14:13:55 CST 2014
     */
    int updateByPrimaryKeySelective(PlayerPayApril record) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_pay_april
     *
     * @abatorgenerated Wed Mar 26 14:13:55 CST 2014
     */
    List<PlayerPayApril> selectByExample(PlayerPayAprilExample example) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_pay_april
     *
     * @abatorgenerated Wed Mar 26 14:13:55 CST 2014
     */
    PlayerPayApril selectByPrimaryKey(Integer id) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_pay_april
     *
     * @abatorgenerated Wed Mar 26 14:13:55 CST 2014
     */
    int deleteByExample(PlayerPayAprilExample example) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_pay_april
     *
     * @abatorgenerated Wed Mar 26 14:13:55 CST 2014
     */
    int deleteByPrimaryKey(Integer id) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_pay_april
     *
     * @abatorgenerated Wed Mar 26 14:13:55 CST 2014
     */
    int countByExample(PlayerPayAprilExample example) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_pay_april
     *
     * @abatorgenerated Wed Mar 26 14:13:55 CST 2014
     */
    int updateByExampleSelective(PlayerPayApril record, PlayerPayAprilExample example) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_pay_april
     *
     * @abatorgenerated Wed Mar 26 14:13:55 CST 2014
     */
    int updateByExample(PlayerPayApril record, PlayerPayAprilExample example) throws SQLException;
}