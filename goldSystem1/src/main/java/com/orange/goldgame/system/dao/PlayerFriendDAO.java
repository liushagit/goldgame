package com.orange.goldgame.system.dao;

import java.sql.SQLException;
import java.util.List;

import com.orange.goldgame.domain.PlayerFriend;
import com.orange.goldgame.domain.PlayerFriendExample;

public interface PlayerFriendDAO {

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_friend
     * @ibatorgenerated  Wed Jul 17 17:16:01 CST 2013
     */
    int countByExample(PlayerFriendExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_friend
     * @ibatorgenerated  Wed Jul 17 17:16:01 CST 2013
     */
    int deleteByExample(PlayerFriendExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_friend
     * @ibatorgenerated  Wed Jul 17 17:16:01 CST 2013
     */
    int deleteByPrimaryKey(Integer id) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_friend
     * @ibatorgenerated  Wed Jul 17 17:16:01 CST 2013
     */
    Integer insert(PlayerFriend record) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_friend
     * @ibatorgenerated  Wed Jul 17 17:16:01 CST 2013
     */
    Integer insertSelective(PlayerFriend record) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_friend
     * @ibatorgenerated  Wed Jul 17 17:16:01 CST 2013
     */
    List selectByExample(PlayerFriendExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_friend
     * @ibatorgenerated  Wed Jul 17 17:16:01 CST 2013
     */
    PlayerFriend selectByPrimaryKey(Integer id) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_friend
     * @ibatorgenerated  Wed Jul 17 17:16:01 CST 2013
     */
    int updateByExampleSelective(PlayerFriend record,
            PlayerFriendExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_friend
     * @ibatorgenerated  Wed Jul 17 17:16:01 CST 2013
     */
    int updateByExample(PlayerFriend record, PlayerFriendExample example)
            throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_friend
     * @ibatorgenerated  Wed Jul 17 17:16:01 CST 2013
     */
    int updateByPrimaryKeySelective(PlayerFriend record) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_friend
     * @ibatorgenerated  Wed Jul 17 17:16:01 CST 2013
     */
    int updateByPrimaryKey(PlayerFriend record) throws SQLException;
}