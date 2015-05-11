package com.orange.goldgame.system.dao;

import java.sql.SQLException;
import java.util.List;

import com.orange.goldgame.domain.SealInfo;
import com.orange.goldgame.domain.SealInfoExample;

public interface SealInfoDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table seal_info
     *
     * @ibatorgenerated Wed Oct 09 09:55:32 CST 2013
     */
    int countByExample(SealInfoExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table seal_info
     *
     * @ibatorgenerated Wed Oct 09 09:55:32 CST 2013
     */
    int deleteByExample(SealInfoExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table seal_info
     *
     * @ibatorgenerated Wed Oct 09 09:55:32 CST 2013
     */
    void insert(SealInfo record) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table seal_info
     *
     * @ibatorgenerated Wed Oct 09 09:55:32 CST 2013
     */
    void insertSelective(SealInfo record) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table seal_info
     *
     * @ibatorgenerated Wed Oct 09 09:55:32 CST 2013
     */
    List selectByExample(SealInfoExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table seal_info
     *
     * @ibatorgenerated Wed Oct 09 09:55:32 CST 2013
     */
    int updateByExampleSelective(SealInfo record, SealInfoExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table seal_info
     *
     * @ibatorgenerated Wed Oct 09 09:55:32 CST 2013
     */
    int updateByExample(SealInfo record, SealInfoExample example) throws SQLException;
}