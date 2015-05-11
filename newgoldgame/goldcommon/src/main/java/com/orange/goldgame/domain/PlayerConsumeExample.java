package com.orange.goldgame.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerConsumeExample {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table player_consume
	 * @ibatorgenerated  Sun Jan 19 17:27:05 CST 2014
	 */
	protected String orderByClause;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table player_consume
	 * @ibatorgenerated  Sun Jan 19 17:27:05 CST 2014
	 */
	protected List oredCriteria;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_consume
	 * @ibatorgenerated  Sun Jan 19 17:27:05 CST 2014
	 */
	public PlayerConsumeExample() {
		oredCriteria = new ArrayList();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_consume
	 * @ibatorgenerated  Sun Jan 19 17:27:05 CST 2014
	 */
	protected PlayerConsumeExample(PlayerConsumeExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_consume
	 * @ibatorgenerated  Sun Jan 19 17:27:05 CST 2014
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_consume
	 * @ibatorgenerated  Sun Jan 19 17:27:05 CST 2014
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_consume
	 * @ibatorgenerated  Sun Jan 19 17:27:05 CST 2014
	 */
	public List getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_consume
	 * @ibatorgenerated  Sun Jan 19 17:27:05 CST 2014
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_consume
	 * @ibatorgenerated  Sun Jan 19 17:27:05 CST 2014
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_consume
	 * @ibatorgenerated  Sun Jan 19 17:27:05 CST 2014
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_consume
	 * @ibatorgenerated  Sun Jan 19 17:27:05 CST 2014
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table player_consume
	 * @ibatorgenerated  Sun Jan 19 17:27:05 CST 2014
	 */
	public static class Criteria {
		protected List criteriaWithoutValue;
		protected List criteriaWithSingleValue;
		protected List criteriaWithListValue;
		protected List criteriaWithBetweenValue;

		protected Criteria() {
			super();
			criteriaWithoutValue = new ArrayList();
			criteriaWithSingleValue = new ArrayList();
			criteriaWithListValue = new ArrayList();
			criteriaWithBetweenValue = new ArrayList();
		}

		public boolean isValid() {
			return criteriaWithoutValue.size() > 0
					|| criteriaWithSingleValue.size() > 0
					|| criteriaWithListValue.size() > 0
					|| criteriaWithBetweenValue.size() > 0;
		}

		public List getCriteriaWithoutValue() {
			return criteriaWithoutValue;
		}

		public List getCriteriaWithSingleValue() {
			return criteriaWithSingleValue;
		}

		public List getCriteriaWithListValue() {
			return criteriaWithListValue;
		}

		public List getCriteriaWithBetweenValue() {
			return criteriaWithBetweenValue;
		}

		protected void addCriterion(String condition) {
			if (condition == null) {
				throw new RuntimeException("Value for condition cannot be null");
			}
			criteriaWithoutValue.add(condition);
		}

		protected void addCriterion(String condition, Object value,
				String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property
						+ " cannot be null");
			}
			Map map = new HashMap();
			map.put("condition", condition);
			map.put("value", value);
			criteriaWithSingleValue.add(map);
		}

		protected void addCriterion(String condition, List values,
				String property) {
			if (values == null || values.size() == 0) {
				throw new RuntimeException("Value list for " + property
						+ " cannot be null or empty");
			}
			Map map = new HashMap();
			map.put("condition", condition);
			map.put("values", values);
			criteriaWithListValue.add(map);
		}

		protected void addCriterion(String condition, Object value1,
				Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property
						+ " cannot be null");
			}
			List list = new ArrayList();
			list.add(value1);
			list.add(value2);
			Map map = new HashMap();
			map.put("condition", condition);
			map.put("values", list);
			criteriaWithBetweenValue.add(map);
		}

		public Criteria andIdIsNull() {
			addCriterion("id is null");
			return this;
		}

		public Criteria andIdIsNotNull() {
			addCriterion("id is not null");
			return this;
		}

		public Criteria andIdEqualTo(Integer value) {
			addCriterion("id =", value, "id");
			return this;
		}

		public Criteria andIdNotEqualTo(Integer value) {
			addCriterion("id <>", value, "id");
			return this;
		}

		public Criteria andIdGreaterThan(Integer value) {
			addCriterion("id >", value, "id");
			return this;
		}

		public Criteria andIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("id >=", value, "id");
			return this;
		}

		public Criteria andIdLessThan(Integer value) {
			addCriterion("id <", value, "id");
			return this;
		}

		public Criteria andIdLessThanOrEqualTo(Integer value) {
			addCriterion("id <=", value, "id");
			return this;
		}

		public Criteria andIdIn(List values) {
			addCriterion("id in", values, "id");
			return this;
		}

		public Criteria andIdNotIn(List values) {
			addCriterion("id not in", values, "id");
			return this;
		}

		public Criteria andIdBetween(Integer value1, Integer value2) {
			addCriterion("id between", value1, value2, "id");
			return this;
		}

		public Criteria andIdNotBetween(Integer value1, Integer value2) {
			addCriterion("id not between", value1, value2, "id");
			return this;
		}

		public Criteria andPlayerIdIsNull() {
			addCriterion("player_id is null");
			return this;
		}

		public Criteria andPlayerIdIsNotNull() {
			addCriterion("player_id is not null");
			return this;
		}

		public Criteria andPlayerIdEqualTo(Integer value) {
			addCriterion("player_id =", value, "playerId");
			return this;
		}

		public Criteria andPlayerIdNotEqualTo(Integer value) {
			addCriterion("player_id <>", value, "playerId");
			return this;
		}

		public Criteria andPlayerIdGreaterThan(Integer value) {
			addCriterion("player_id >", value, "playerId");
			return this;
		}

		public Criteria andPlayerIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("player_id >=", value, "playerId");
			return this;
		}

		public Criteria andPlayerIdLessThan(Integer value) {
			addCriterion("player_id <", value, "playerId");
			return this;
		}

		public Criteria andPlayerIdLessThanOrEqualTo(Integer value) {
			addCriterion("player_id <=", value, "playerId");
			return this;
		}

		public Criteria andPlayerIdIn(List values) {
			addCriterion("player_id in", values, "playerId");
			return this;
		}

		public Criteria andPlayerIdNotIn(List values) {
			addCriterion("player_id not in", values, "playerId");
			return this;
		}

		public Criteria andPlayerIdBetween(Integer value1, Integer value2) {
			addCriterion("player_id between", value1, value2, "playerId");
			return this;
		}

		public Criteria andPlayerIdNotBetween(Integer value1, Integer value2) {
			addCriterion("player_id not between", value1, value2, "playerId");
			return this;
		}

		public Criteria andBeforeNumIsNull() {
			addCriterion("before_num is null");
			return this;
		}

		public Criteria andBeforeNumIsNotNull() {
			addCriterion("before_num is not null");
			return this;
		}

		public Criteria andBeforeNumEqualTo(Integer value) {
			addCriterion("before_num =", value, "beforeNum");
			return this;
		}

		public Criteria andBeforeNumNotEqualTo(Integer value) {
			addCriterion("before_num <>", value, "beforeNum");
			return this;
		}

		public Criteria andBeforeNumGreaterThan(Integer value) {
			addCriterion("before_num >", value, "beforeNum");
			return this;
		}

		public Criteria andBeforeNumGreaterThanOrEqualTo(Integer value) {
			addCriterion("before_num >=", value, "beforeNum");
			return this;
		}

		public Criteria andBeforeNumLessThan(Integer value) {
			addCriterion("before_num <", value, "beforeNum");
			return this;
		}

		public Criteria andBeforeNumLessThanOrEqualTo(Integer value) {
			addCriterion("before_num <=", value, "beforeNum");
			return this;
		}

		public Criteria andBeforeNumIn(List values) {
			addCriterion("before_num in", values, "beforeNum");
			return this;
		}

		public Criteria andBeforeNumNotIn(List values) {
			addCriterion("before_num not in", values, "beforeNum");
			return this;
		}

		public Criteria andBeforeNumBetween(Integer value1, Integer value2) {
			addCriterion("before_num between", value1, value2, "beforeNum");
			return this;
		}

		public Criteria andBeforeNumNotBetween(Integer value1, Integer value2) {
			addCriterion("before_num not between", value1, value2, "beforeNum");
			return this;
		}

		public Criteria andPropConfigIdIsNull() {
			addCriterion("prop_config_id is null");
			return this;
		}

		public Criteria andPropConfigIdIsNotNull() {
			addCriterion("prop_config_id is not null");
			return this;
		}

		public Criteria andPropConfigIdEqualTo(Integer value) {
			addCriterion("prop_config_id =", value, "propConfigId");
			return this;
		}

		public Criteria andPropConfigIdNotEqualTo(Integer value) {
			addCriterion("prop_config_id <>", value, "propConfigId");
			return this;
		}

		public Criteria andPropConfigIdGreaterThan(Integer value) {
			addCriterion("prop_config_id >", value, "propConfigId");
			return this;
		}

		public Criteria andPropConfigIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("prop_config_id >=", value, "propConfigId");
			return this;
		}

		public Criteria andPropConfigIdLessThan(Integer value) {
			addCriterion("prop_config_id <", value, "propConfigId");
			return this;
		}

		public Criteria andPropConfigIdLessThanOrEqualTo(Integer value) {
			addCriterion("prop_config_id <=", value, "propConfigId");
			return this;
		}

		public Criteria andPropConfigIdIn(List values) {
			addCriterion("prop_config_id in", values, "propConfigId");
			return this;
		}

		public Criteria andPropConfigIdNotIn(List values) {
			addCriterion("prop_config_id not in", values, "propConfigId");
			return this;
		}

		public Criteria andPropConfigIdBetween(Integer value1, Integer value2) {
			addCriterion("prop_config_id between", value1, value2,
					"propConfigId");
			return this;
		}

		public Criteria andPropConfigIdNotBetween(Integer value1, Integer value2) {
			addCriterion("prop_config_id not between", value1, value2,
					"propConfigId");
			return this;
		}

		public Criteria andConsumeNumIsNull() {
			addCriterion("consume_num is null");
			return this;
		}

		public Criteria andConsumeNumIsNotNull() {
			addCriterion("consume_num is not null");
			return this;
		}

		public Criteria andConsumeNumEqualTo(Integer value) {
			addCriterion("consume_num =", value, "consumeNum");
			return this;
		}

		public Criteria andConsumeNumNotEqualTo(Integer value) {
			addCriterion("consume_num <>", value, "consumeNum");
			return this;
		}

		public Criteria andConsumeNumGreaterThan(Integer value) {
			addCriterion("consume_num >", value, "consumeNum");
			return this;
		}

		public Criteria andConsumeNumGreaterThanOrEqualTo(Integer value) {
			addCriterion("consume_num >=", value, "consumeNum");
			return this;
		}

		public Criteria andConsumeNumLessThan(Integer value) {
			addCriterion("consume_num <", value, "consumeNum");
			return this;
		}

		public Criteria andConsumeNumLessThanOrEqualTo(Integer value) {
			addCriterion("consume_num <=", value, "consumeNum");
			return this;
		}

		public Criteria andConsumeNumIn(List values) {
			addCriterion("consume_num in", values, "consumeNum");
			return this;
		}

		public Criteria andConsumeNumNotIn(List values) {
			addCriterion("consume_num not in", values, "consumeNum");
			return this;
		}

		public Criteria andConsumeNumBetween(Integer value1, Integer value2) {
			addCriterion("consume_num between", value1, value2, "consumeNum");
			return this;
		}

		public Criteria andConsumeNumNotBetween(Integer value1, Integer value2) {
			addCriterion("consume_num not between", value1, value2,
					"consumeNum");
			return this;
		}

		public Criteria andAfterNumIsNull() {
			addCriterion("after_num is null");
			return this;
		}

		public Criteria andAfterNumIsNotNull() {
			addCriterion("after_num is not null");
			return this;
		}

		public Criteria andAfterNumEqualTo(Integer value) {
			addCriterion("after_num =", value, "afterNum");
			return this;
		}

		public Criteria andAfterNumNotEqualTo(Integer value) {
			addCriterion("after_num <>", value, "afterNum");
			return this;
		}

		public Criteria andAfterNumGreaterThan(Integer value) {
			addCriterion("after_num >", value, "afterNum");
			return this;
		}

		public Criteria andAfterNumGreaterThanOrEqualTo(Integer value) {
			addCriterion("after_num >=", value, "afterNum");
			return this;
		}

		public Criteria andAfterNumLessThan(Integer value) {
			addCriterion("after_num <", value, "afterNum");
			return this;
		}

		public Criteria andAfterNumLessThanOrEqualTo(Integer value) {
			addCriterion("after_num <=", value, "afterNum");
			return this;
		}

		public Criteria andAfterNumIn(List values) {
			addCriterion("after_num in", values, "afterNum");
			return this;
		}

		public Criteria andAfterNumNotIn(List values) {
			addCriterion("after_num not in", values, "afterNum");
			return this;
		}

		public Criteria andAfterNumBetween(Integer value1, Integer value2) {
			addCriterion("after_num between", value1, value2, "afterNum");
			return this;
		}

		public Criteria andAfterNumNotBetween(Integer value1, Integer value2) {
			addCriterion("after_num not between", value1, value2, "afterNum");
			return this;
		}

		public Criteria andComsumeTimeIsNull() {
			addCriterion("comsume_time is null");
			return this;
		}

		public Criteria andComsumeTimeIsNotNull() {
			addCriterion("comsume_time is not null");
			return this;
		}

		public Criteria andComsumeTimeEqualTo(Date value) {
			addCriterion("comsume_time =", value, "comsumeTime");
			return this;
		}

		public Criteria andComsumeTimeNotEqualTo(Date value) {
			addCriterion("comsume_time <>", value, "comsumeTime");
			return this;
		}

		public Criteria andComsumeTimeGreaterThan(Date value) {
			addCriterion("comsume_time >", value, "comsumeTime");
			return this;
		}

		public Criteria andComsumeTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("comsume_time >=", value, "comsumeTime");
			return this;
		}

		public Criteria andComsumeTimeLessThan(Date value) {
			addCriterion("comsume_time <", value, "comsumeTime");
			return this;
		}

		public Criteria andComsumeTimeLessThanOrEqualTo(Date value) {
			addCriterion("comsume_time <=", value, "comsumeTime");
			return this;
		}

		public Criteria andComsumeTimeIn(List values) {
			addCriterion("comsume_time in", values, "comsumeTime");
			return this;
		}

		public Criteria andComsumeTimeNotIn(List values) {
			addCriterion("comsume_time not in", values, "comsumeTime");
			return this;
		}

		public Criteria andComsumeTimeBetween(Date value1, Date value2) {
			addCriterion("comsume_time between", value1, value2, "comsumeTime");
			return this;
		}

		public Criteria andComsumeTimeNotBetween(Date value1, Date value2) {
			addCriterion("comsume_time not between", value1, value2,
					"comsumeTime");
			return this;
		}

		public Criteria andStateIsNull() {
			addCriterion("state is null");
			return this;
		}

		public Criteria andStateIsNotNull() {
			addCriterion("state is not null");
			return this;
		}

		public Criteria andStateEqualTo(Byte value) {
			addCriterion("state =", value, "state");
			return this;
		}

		public Criteria andStateNotEqualTo(Byte value) {
			addCriterion("state <>", value, "state");
			return this;
		}

		public Criteria andStateGreaterThan(Byte value) {
			addCriterion("state >", value, "state");
			return this;
		}

		public Criteria andStateGreaterThanOrEqualTo(Byte value) {
			addCriterion("state >=", value, "state");
			return this;
		}

		public Criteria andStateLessThan(Byte value) {
			addCriterion("state <", value, "state");
			return this;
		}

		public Criteria andStateLessThanOrEqualTo(Byte value) {
			addCriterion("state <=", value, "state");
			return this;
		}

		public Criteria andStateIn(List values) {
			addCriterion("state in", values, "state");
			return this;
		}

		public Criteria andStateNotIn(List values) {
			addCriterion("state not in", values, "state");
			return this;
		}

		public Criteria andStateBetween(Byte value1, Byte value2) {
			addCriterion("state between", value1, value2, "state");
			return this;
		}

		public Criteria andStateNotBetween(Byte value1, Byte value2) {
			addCriterion("state not between", value1, value2, "state");
			return this;
		}
	}
}