package com.orange.goldgame.domain;

public class StakeConfig extends BaseObject {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column stake_config.Id
	 * @ibatorgenerated  Thu Jul 11 15:56:09 CST 2013
	 */
	private Integer id;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column stake_config.area_Id
	 * @ibatorgenerated  Thu Jul 11 15:56:09 CST 2013
	 */
	private Integer areaId;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column stake_config.stake_Number
	 * @ibatorgenerated  Thu Jul 11 15:56:09 CST 2013
	 */
	private Integer stakeNumber;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column stake_config.intro
	 * @ibatorgenerated  Thu Jul 11 15:56:09 CST 2013
	 */
	private String intro;

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column stake_config.Id
	 * @return  the value of stake_config.Id
	 * @ibatorgenerated  Thu Jul 11 15:56:09 CST 2013
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column stake_config.Id
	 * @param id  the value for stake_config.Id
	 * @ibatorgenerated  Thu Jul 11 15:56:09 CST 2013
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column stake_config.area_Id
	 * @return  the value of stake_config.area_Id
	 * @ibatorgenerated  Thu Jul 11 15:56:09 CST 2013
	 */
	public Integer getAreaId() {
		return areaId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column stake_config.area_Id
	 * @param areaId  the value for stake_config.area_Id
	 * @ibatorgenerated  Thu Jul 11 15:56:09 CST 2013
	 */
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column stake_config.stake_Number
	 * @return  the value of stake_config.stake_Number
	 * @ibatorgenerated  Thu Jul 11 15:56:09 CST 2013
	 */
	public Integer getStakeNumber() {
		return stakeNumber;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column stake_config.stake_Number
	 * @param stakeNumber  the value for stake_config.stake_Number
	 * @ibatorgenerated  Thu Jul 11 15:56:09 CST 2013
	 */
	public void setStakeNumber(Integer stakeNumber) {
		this.stakeNumber = stakeNumber;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column stake_config.intro
	 * @return  the value of stake_config.intro
	 * @ibatorgenerated  Thu Jul 11 15:56:09 CST 2013
	 */
	public String getIntro() {
		return intro;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column stake_config.intro
	 * @param intro  the value for stake_config.intro
	 * @ibatorgenerated  Thu Jul 11 15:56:09 CST 2013
	 */
	public void setIntro(String intro) {
		this.intro = intro;
	}
}