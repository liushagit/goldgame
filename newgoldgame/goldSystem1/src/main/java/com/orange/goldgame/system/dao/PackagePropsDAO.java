package com.orange.goldgame.system.dao;

import java.sql.SQLException;
import java.util.List;

import com.orange.goldgame.domain.PackageProps;
import com.orange.goldgame.domain.PackagePropsExample;

public interface PackagePropsDAO {
    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
     */
    Integer insert(PackageProps record) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
     */
    int updateByPrimaryKey(PackageProps record) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
     */
    int updateByPrimaryKeySelective(PackageProps record) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
     */
    List<PackageProps> selectByExample(PackagePropsExample example) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
     */
    PackageProps selectByPrimaryKey(Integer id) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
     */
    int deleteByExample(PackagePropsExample example) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
     */
    int deleteByPrimaryKey(Integer id) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
     */
    int countByExample(PackagePropsExample example) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
     */
    int updateByExampleSelective(PackageProps record, PackagePropsExample example) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
     */
    int updateByExample(PackageProps record, PackagePropsExample example) throws SQLException;
}