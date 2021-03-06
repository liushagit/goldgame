package com.orange.goldgame.domain;

import java.util.Date;

public class PlayerInfoCenter extends BaseObject {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column player_info_center.id
	 * @ibatorgenerated  Fri Sep 13 20:23:44 CST 2013
	 */
	private Integer id;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column player_info_center.player_id
	 * @ibatorgenerated  Fri Sep 13 20:23:44 CST 2013
	 */
	private Integer playerId;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column player_info_center.create_time
	 * @ibatorgenerated  Fri Sep 13 20:23:44 CST 2013
	 */
	private Date createTime;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column player_info_center.content
	 * @ibatorgenerated  Fri Sep 13 20:23:44 CST 2013
	 */
	private String content;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column player_info_center.message_type
	 * @ibatorgenerated  Fri Sep 13 20:23:44 CST 2013
	 */
	private Integer messageType;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column player_info_center.message_info
	 * @ibatorgenerated  Fri Sep 13 20:23:44 CST 2013
	 */
	private String messageInfo;

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column player_info_center.id
	 * @return  the value of player_info_center.id
	 * @ibatorgenerated  Fri Sep 13 20:23:44 CST 2013
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column player_info_center.id
	 * @param id  the value for player_info_center.id
	 * @ibatorgenerated  Fri Sep 13 20:23:44 CST 2013
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column player_info_center.player_id
	 * @return  the value of player_info_center.player_id
	 * @ibatorgenerated  Fri Sep 13 20:23:44 CST 2013
	 */
	public Integer getPlayerId() {
		return playerId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column player_info_center.player_id
	 * @param playerId  the value for player_info_center.player_id
	 * @ibatorgenerated  Fri Sep 13 20:23:44 CST 2013
	 */
	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column player_info_center.create_time
	 * @return  the value of player_info_center.create_time
	 * @ibatorgenerated  Fri Sep 13 20:23:44 CST 2013
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column player_info_center.create_time
	 * @param createTime  the value for player_info_center.create_time
	 * @ibatorgenerated  Fri Sep 13 20:23:44 CST 2013
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column player_info_center.content
	 * @return  the value of player_info_center.content
	 * @ibatorgenerated  Fri Sep 13 20:23:44 CST 2013
	 */
	public String getContent() {
		return content;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column player_info_center.content
	 * @param content  the value for player_info_center.content
	 * @ibatorgenerated  Fri Sep 13 20:23:44 CST 2013
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column player_info_center.message_type
	 * @return  the value of player_info_center.message_type
	 * @ibatorgenerated  Fri Sep 13 20:23:44 CST 2013
	 */
	public Integer getMessageType() {
		return messageType;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column player_info_center.message_type
	 * @param messageType  the value for player_info_center.message_type
	 * @ibatorgenerated  Fri Sep 13 20:23:44 CST 2013
	 */
	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column player_info_center.message_info
	 * @return  the value of player_info_center.message_info
	 * @ibatorgenerated  Fri Sep 13 20:23:44 CST 2013
	 */
	public String getMessageInfo() {
		return messageInfo;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column player_info_center.message_info
	 * @param messageInfo  the value for player_info_center.message_info
	 * @ibatorgenerated  Fri Sep 13 20:23:44 CST 2013
	 */
	public void setMessageInfo(String messageInfo) {
		this.messageInfo = messageInfo;
	}
	public static final String TABLENAME = "player_info_center";
}