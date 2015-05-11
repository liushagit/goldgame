/**
 * SuperStarCommon
 * com.orange.superstar.domain
 * UserProp.java
 */
package com.orange.goldgame.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author shaojieque 
 * 2013-5-6
 */
public class UserProp implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//
	private int id;
	private int userId;
	private int propId;
	private String propName;
	private String propLogo;
	private int propType;
	private Date deadline;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getPropId() {
		return propId;
	}

	public void setPropId(int propId) {
		this.propId = propId;
	}

	public String getPropName() {
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}

	public String getPropLogo() {
		return propLogo;
	}

	public void setPropLogo(String propLogo) {
		this.propLogo = propLogo;
	}

	public int getPropType() {
		return propType;
	}

	public void setPropType(int propType) {
		this.propType = propType;
	}
	
	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	@Override
	public String toString() {
		return "UserProp [id=" + id + ", userId=" + userId + ", propId="
				+ propId + ", propName=" + propName + ", propLogo=" + propLogo
				+ ", propType=" + propType + ", deadline=" + deadline + "]";
	}
}
