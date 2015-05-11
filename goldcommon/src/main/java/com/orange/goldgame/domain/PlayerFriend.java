package com.orange.goldgame.domain;

import java.util.Date;

public class PlayerFriend extends BaseObject {

    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database column player_friend.id
     * @ibatorgenerated  Wed Jul 17 17:16:01 CST 2013
     */
    private Integer id;
    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database column player_friend.player_id
     * @ibatorgenerated  Wed Jul 17 17:16:01 CST 2013
     */
    private Integer playerId;
    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database column player_friend.friend_id
     * @ibatorgenerated  Wed Jul 17 17:16:01 CST 2013
     */
    private Integer friendId;
    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database column player_friend.friend_name
     * @ibatorgenerated  Wed Jul 17 17:16:01 CST 2013
     */
    private String friendName;
    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database column player_friend.friend_type
     * @ibatorgenerated  Wed Jul 17 17:16:01 CST 2013
     */
    private Integer friendType;
    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database column player_friend.add_time
     * @ibatorgenerated  Wed Jul 17 17:16:01 CST 2013
     */
    private Date addTime;
    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database column player_friend.sex
     * @ibatorgenerated  Wed Jul 17 17:16:01 CST 2013
     */
    private Boolean sex;

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the database column player_friend.id
     * @return  the value of player_friend.id
     * @ibatorgenerated  Wed Jul 17 17:16:01 CST 2013
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database column player_friend.id
     * @param id  the value for player_friend.id
     * @ibatorgenerated  Wed Jul 17 17:16:01 CST 2013
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the database column player_friend.player_id
     * @return  the value of player_friend.player_id
     * @ibatorgenerated  Wed Jul 17 17:16:01 CST 2013
     */
    public Integer getPlayerId() {
        return playerId;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database column player_friend.player_id
     * @param playerId  the value for player_friend.player_id
     * @ibatorgenerated  Wed Jul 17 17:16:01 CST 2013
     */
    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the database column player_friend.friend_id
     * @return  the value of player_friend.friend_id
     * @ibatorgenerated  Wed Jul 17 17:16:01 CST 2013
     */
    public Integer getFriendId() {
        return friendId;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database column player_friend.friend_id
     * @param friendId  the value for player_friend.friend_id
     * @ibatorgenerated  Wed Jul 17 17:16:01 CST 2013
     */
    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the database column player_friend.friend_name
     * @return  the value of player_friend.friend_name
     * @ibatorgenerated  Wed Jul 17 17:16:01 CST 2013
     */
    public String getFriendName() {
        return friendName;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database column player_friend.friend_name
     * @param friendName  the value for player_friend.friend_name
     * @ibatorgenerated  Wed Jul 17 17:16:01 CST 2013
     */
    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the database column player_friend.friend_type
     * @return  the value of player_friend.friend_type
     * @ibatorgenerated  Wed Jul 17 17:16:01 CST 2013
     */
    public Integer getFriendType() {
        return friendType;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database column player_friend.friend_type
     * @param friendType  the value for player_friend.friend_type
     * @ibatorgenerated  Wed Jul 17 17:16:01 CST 2013
     */
    public void setFriendType(Integer friendType) {
        this.friendType = friendType;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the database column player_friend.add_time
     * @return  the value of player_friend.add_time
     * @ibatorgenerated  Wed Jul 17 17:16:01 CST 2013
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database column player_friend.add_time
     * @param addTime  the value for player_friend.add_time
     * @ibatorgenerated  Wed Jul 17 17:16:01 CST 2013
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the database column player_friend.sex
     * @return  the value of player_friend.sex
     * @ibatorgenerated  Wed Jul 17 17:16:01 CST 2013
     */
    public Boolean getSex() {
        return sex;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database column player_friend.sex
     * @param sex  the value for player_friend.sex
     * @ibatorgenerated  Wed Jul 17 17:16:01 CST 2013
     */
    public void setSex(Boolean sex) {
        this.sex = sex;
    }
}