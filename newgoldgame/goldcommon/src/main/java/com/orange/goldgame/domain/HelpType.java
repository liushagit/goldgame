package com.orange.goldgame.domain;

public class HelpType extends BaseObject {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column help_type.id
     *
     * @ibatorgenerated Wed Jul 24 10:23:54 CST 2013
     */
    private Integer id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column help_type.name
     *
     * @ibatorgenerated Wed Jul 24 10:23:54 CST 2013
     */
    private String name;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column help_type.id
     *
     * @return the value of help_type.id
     *
     * @ibatorgenerated Wed Jul 24 10:23:54 CST 2013
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column help_type.id
     *
     * @param id the value for help_type.id
     *
     * @ibatorgenerated Wed Jul 24 10:23:54 CST 2013
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column help_type.name
     *
     * @return the value of help_type.name
     *
     * @ibatorgenerated Wed Jul 24 10:23:54 CST 2013
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column help_type.name
     *
     * @param name the value for help_type.name
     *
     * @ibatorgenerated Wed Jul 24 10:23:54 CST 2013
     */
    public void setName(String name) {
        this.name = name;
    }
}