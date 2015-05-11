package com.orange.goldgame.domain;

import java.io.Serializable;


/**
 * 活动实体
 * @author yesheng
 *
 */

public class Activity implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String introduction;
    private String dateLimit;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getIntroduction() {
        return introduction;
    }
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    public String getDateLimit() {
        return dateLimit;
    }
    public void setDateLimit(String dateLimit) {
        this.dateLimit = dateLimit;
    }
    
}
