package com.orange.goldgame.system.dao;

import java.sql.SQLException;
import java.util.List;

import com.orange.goldgame.domain.AppellationConfig;
import com.orange.goldgame.domain.AppellationConfigExample;

public interface AppellationConfigDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table appellation_config
     *
     * @ibatorgenerated Mon Aug 26 10:43:53 CST 2013
     */
    int countByExample(AppellationConfigExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table appellation_config
     *
     * @ibatorgenerated Mon Aug 26 10:43:53 CST 2013
     */
    int deleteByExample(AppellationConfigExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table appellation_config
     *
     * @ibatorgenerated Mon Aug 26 10:43:53 CST 2013
     */
    int deleteByPrimaryKey(Integer id) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table appellation_config
     *
     * @ibatorgenerated Mon Aug 26 10:43:53 CST 2013
     */
    Integer insert(AppellationConfig record) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table appellation_config
     *
     * @ibatorgenerated Mon Aug 26 10:43:53 CST 2013
     */
    Integer insertSelective(AppellationConfig record) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table appellation_config
     *
     * @ibatorgenerated Mon Aug 26 10:43:53 CST 2013
     */
    List selectByExample(AppellationConfigExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table appellation_config
     *
     * @ibatorgenerated Mon Aug 26 10:43:53 CST 2013
     */
    AppellationConfig selectByPrimaryKey(Integer id) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table appellation_config
     *
     * @ibatorgenerated Mon Aug 26 10:43:53 CST 2013
     */
    int updateByExampleSelective(AppellationConfig record, AppellationConfigExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table appellation_config
     *
     * @ibatorgenerated Mon Aug 26 10:43:53 CST 2013
     */
    int updateByExample(AppellationConfig record, AppellationConfigExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table appellation_config
     *
     * @ibatorgenerated Mon Aug 26 10:43:53 CST 2013
     */
    int updateByPrimaryKeySelective(AppellationConfig record) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table appellation_config
     *
     * @ibatorgenerated Mon Aug 26 10:43:53 CST 2013
     */
    int updateByPrimaryKey(AppellationConfig record) throws SQLException;
}