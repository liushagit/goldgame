/**
 * SuperStarCommon
 * com.orange.superstar.domain
 * Prop.java
 */
package com.orange.goldgame.vo;

import java.io.Serializable;

/**
 * @author shaojieque 
 * 2013-5-6
 */
public class Prop implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//
	public static final int PROP_TYPE_HEAD = 1;
	public static final int PROP_TYPE_CLOTHES = 2;
	public static final int PROP_TYPE_TROUSERS = 3;
	public static final int PROP_TYPE_MUISC = 4;
	//
	private int id;
	private String name;
	private String memo;
	private String logo;
	private int type;
	private int price;

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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Prop [id=" + id + ", name=" + name + ", memo=" + memo
				+ ", logo=" + logo + ", type=" + type + ", price=" + price
				+ "]";
	}
}
