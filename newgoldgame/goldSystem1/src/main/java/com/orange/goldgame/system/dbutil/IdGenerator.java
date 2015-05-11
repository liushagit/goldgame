package com.orange.goldgame.system.dbutil;

import com.orange.goldgame.domain.BaseObject;


public class IdGenerator extends BaseObject {

	private static final long serialVersionUID = 1797062508875871760L;


	private String tableName;

	private Integer seqno;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Integer getSeqno() {
		return seqno;
	}

	public void setSeqno(Integer seqno) {
		this.seqno = seqno;
	}
	
}