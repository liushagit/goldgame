package com.orange.goldgame.domain;

public class Gift extends BaseObject{

    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database column gift.id
     * @ibatorgenerated  Wed Jul 24 16:28:11 CST 2013
     */
    private Integer id;
    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database column gift.player_id
     * @ibatorgenerated  Wed Jul 24 16:28:11 CST 2013
     */
    private Integer playerId;
    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database column gift.amont
     * @ibatorgenerated  Wed Jul 24 16:28:11 CST 2013
     */
    private Integer amont;
    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database column gift.prop_id
     * @ibatorgenerated  Wed Jul 24 16:28:11 CST 2013
     */
    private Integer propId;

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the database column gift.id
     * @return  the value of gift.id
     * @ibatorgenerated  Wed Jul 24 16:28:11 CST 2013
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database column gift.id
     * @param id  the value for gift.id
     * @ibatorgenerated  Wed Jul 24 16:28:11 CST 2013
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the database column gift.player_id
     * @return  the value of gift.player_id
     * @ibatorgenerated  Wed Jul 24 16:28:11 CST 2013
     */
    public Integer getPlayerId() {
        return playerId;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database column gift.player_id
     * @param playerId  the value for gift.player_id
     * @ibatorgenerated  Wed Jul 24 16:28:11 CST 2013
     */
    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the database column gift.amont
     * @return  the value of gift.amont
     * @ibatorgenerated  Wed Jul 24 16:28:11 CST 2013
     */
    public Integer getAmont() {
        return amont;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database column gift.amont
     * @param amont  the value for gift.amont
     * @ibatorgenerated  Wed Jul 24 16:28:11 CST 2013
     */
    public void setAmont(Integer amont) {
        this.amont = amont;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the database column gift.prop_id
     * @return  the value of gift.prop_id
     * @ibatorgenerated  Wed Jul 24 16:28:11 CST 2013
     */
    public Integer getPropId() {
        return propId;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database column gift.prop_id
     * @param propId  the value for gift.prop_id
     * @ibatorgenerated  Wed Jul 24 16:28:11 CST 2013
     */
    public void setPropId(Integer propId) {
        this.propId = propId;
    }
    
}