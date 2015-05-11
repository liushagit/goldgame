package com.orange.goldgame.domain;

public class PlayerProps extends BaseObject{

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column player_props.id
	 * @ibatorgenerated  Mon Jul 15 16:36:18 CST 2013
	 */
	private Integer id;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column player_props.player_id
	 * @ibatorgenerated  Mon Jul 15 16:36:18 CST 2013
	 */
	private Integer playerId;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column player_props.props_config_id
	 * @ibatorgenerated  Mon Jul 15 16:36:18 CST 2013
	 */
	private Integer propsConfigId;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column player_props.number
	 * @ibatorgenerated  Mon Jul 15 16:36:18 CST 2013
	 */
	private Integer number;

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column player_props.id
	 * @return  the value of player_props.id
	 * @ibatorgenerated  Mon Jul 15 16:36:18 CST 2013
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column player_props.id
	 * @param id  the value for player_props.id
	 * @ibatorgenerated  Mon Jul 15 16:36:18 CST 2013
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column player_props.player_id
	 * @return  the value of player_props.player_id
	 * @ibatorgenerated  Mon Jul 15 16:36:18 CST 2013
	 */
	public Integer getPlayerId() {
		return playerId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column player_props.player_id
	 * @param playerId  the value for player_props.player_id
	 * @ibatorgenerated  Mon Jul 15 16:36:18 CST 2013
	 */
	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column player_props.props_config_id
	 * @return  the value of player_props.props_config_id
	 * @ibatorgenerated  Mon Jul 15 16:36:18 CST 2013
	 */
	public Integer getPropsConfigId() {
		return propsConfigId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column player_props.props_config_id
	 * @param propsConfigId  the value for player_props.props_config_id
	 * @ibatorgenerated  Mon Jul 15 16:36:18 CST 2013
	 */
	public void setPropsConfigId(Integer propsConfigId) {
		this.propsConfigId = propsConfigId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column player_props.number
	 * @return  the value of player_props.number
	 * @ibatorgenerated  Mon Jul 15 16:36:18 CST 2013
	 */
	public Integer getNumber() {
		return number;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column player_props.number
	 * @param number  the value for player_props.number
	 * @ibatorgenerated  Mon Jul 15 16:36:18 CST 2013
	 */
	public void setNumber(Integer number) {
		this.number = number;
	}
}