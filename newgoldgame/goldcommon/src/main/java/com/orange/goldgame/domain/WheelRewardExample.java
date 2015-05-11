package com.orange.goldgame.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WheelRewardExample {

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table wheel_reward
	 * @abatorgenerated  Tue Mar 25 16:00:31 CST 2014
	 */
	protected String orderByClause;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table wheel_reward
	 * @abatorgenerated  Tue Mar 25 16:00:31 CST 2014
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table wheel_reward
	 * @abatorgenerated  Tue Mar 25 16:00:31 CST 2014
	 */
	public WheelRewardExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table wheel_reward
	 * @abatorgenerated  Tue Mar 25 16:00:31 CST 2014
	 */
	protected WheelRewardExample(WheelRewardExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table wheel_reward
	 * @abatorgenerated  Tue Mar 25 16:00:31 CST 2014
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table wheel_reward
	 * @abatorgenerated  Tue Mar 25 16:00:31 CST 2014
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table wheel_reward
	 * @abatorgenerated  Tue Mar 25 16:00:31 CST 2014
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table wheel_reward
	 * @abatorgenerated  Tue Mar 25 16:00:31 CST 2014
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table wheel_reward
	 * @abatorgenerated  Tue Mar 25 16:00:31 CST 2014
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table wheel_reward
	 * @abatorgenerated  Tue Mar 25 16:00:31 CST 2014
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table wheel_reward
	 * @abatorgenerated  Tue Mar 25 16:00:31 CST 2014
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to the database table wheel_reward
	 * @abatorgenerated  Tue Mar 25 16:00:31 CST 2014
	 */
	public static class Criteria {
		protected List<String> criteriaWithoutValue;
		protected List<Map<String, Object>> criteriaWithSingleValue;
		protected List<Map<String, Object>> criteriaWithListValue;
		protected List<Map<String, Object>> criteriaWithBetweenValue;

		protected Criteria() {
			super();
			criteriaWithoutValue = new ArrayList<String>();
			criteriaWithSingleValue = new ArrayList<Map<String, Object>>();
			criteriaWithListValue = new ArrayList<Map<String, Object>>();
			criteriaWithBetweenValue = new ArrayList<Map<String, Object>>();
		}

		public boolean isValid() {
			return criteriaWithoutValue.size() > 0
					|| criteriaWithSingleValue.size() > 0
					|| criteriaWithListValue.size() > 0
					|| criteriaWithBetweenValue.size() > 0;
		}

		public List<String> getCriteriaWithoutValue() {
			return criteriaWithoutValue;
		}

		public List<Map<String, Object>> getCriteriaWithSingleValue() {
			return criteriaWithSingleValue;
		}

		public List<Map<String, Object>> getCriteriaWithListValue() {
			return criteriaWithListValue;
		}

		public List<Map<String, Object>> getCriteriaWithBetweenValue() {
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
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("condition", condition);
			map.put("value", value);
			criteriaWithSingleValue.add(map);
		}

		protected void addCriterion(String condition,
				List<? extends Object> values, String property) {
			if (values == null || values.size() == 0) {
				throw new RuntimeException("Value list for " + property
						+ " cannot be null or empty");
			}
			Map<String, Object> map = new HashMap<String, Object>();
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
			List<Object> list = new ArrayList<Object>();
			list.add(value1);
			list.add(value2);
			Map<String, Object> map = new HashMap<String, Object>();
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

		public Criteria andIdIn(List<Integer> values) {
			addCriterion("id in", values, "id");
			return this;
		}

		public Criteria andIdNotIn(List<Integer> values) {
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

		public Criteria andRewardsIsNull() {
			addCriterion("rewards is null");
			return this;
		}

		public Criteria andRewardsIsNotNull() {
			addCriterion("rewards is not null");
			return this;
		}

		public Criteria andRewardsEqualTo(Integer value) {
			addCriterion("rewards =", value, "rewards");
			return this;
		}

		public Criteria andRewardsNotEqualTo(Integer value) {
			addCriterion("rewards <>", value, "rewards");
			return this;
		}

		public Criteria andRewardsGreaterThan(Integer value) {
			addCriterion("rewards >", value, "rewards");
			return this;
		}

		public Criteria andRewardsGreaterThanOrEqualTo(Integer value) {
			addCriterion("rewards >=", value, "rewards");
			return this;
		}

		public Criteria andRewardsLessThan(Integer value) {
			addCriterion("rewards <", value, "rewards");
			return this;
		}

		public Criteria andRewardsLessThanOrEqualTo(Integer value) {
			addCriterion("rewards <=", value, "rewards");
			return this;
		}

		public Criteria andRewardsIn(List<Integer> values) {
			addCriterion("rewards in", values, "rewards");
			return this;
		}

		public Criteria andRewardsNotIn(List<Integer> values) {
			addCriterion("rewards not in", values, "rewards");
			return this;
		}

		public Criteria andRewardsBetween(Integer value1, Integer value2) {
			addCriterion("rewards between", value1, value2, "rewards");
			return this;
		}

		public Criteria andRewardsNotBetween(Integer value1, Integer value2) {
			addCriterion("rewards not between", value1, value2, "rewards");
			return this;
		}

		public Criteria andRewardsTypeIsNull() {
			addCriterion("rewards_type is null");
			return this;
		}

		public Criteria andRewardsTypeIsNotNull() {
			addCriterion("rewards_type is not null");
			return this;
		}

		public Criteria andRewardsTypeEqualTo(Integer value) {
			addCriterion("rewards_type =", value, "rewardsType");
			return this;
		}

		public Criteria andRewardsTypeNotEqualTo(Integer value) {
			addCriterion("rewards_type <>", value, "rewardsType");
			return this;
		}

		public Criteria andRewardsTypeGreaterThan(Integer value) {
			addCriterion("rewards_type >", value, "rewardsType");
			return this;
		}

		public Criteria andRewardsTypeGreaterThanOrEqualTo(Integer value) {
			addCriterion("rewards_type >=", value, "rewardsType");
			return this;
		}

		public Criteria andRewardsTypeLessThan(Integer value) {
			addCriterion("rewards_type <", value, "rewardsType");
			return this;
		}

		public Criteria andRewardsTypeLessThanOrEqualTo(Integer value) {
			addCriterion("rewards_type <=", value, "rewardsType");
			return this;
		}

		public Criteria andRewardsTypeIn(List<Integer> values) {
			addCriterion("rewards_type in", values, "rewardsType");
			return this;
		}

		public Criteria andRewardsTypeNotIn(List<Integer> values) {
			addCriterion("rewards_type not in", values, "rewardsType");
			return this;
		}

		public Criteria andRewardsTypeBetween(Integer value1, Integer value2) {
			addCriterion("rewards_type between", value1, value2, "rewardsType");
			return this;
		}

		public Criteria andRewardsTypeNotBetween(Integer value1, Integer value2) {
			addCriterion("rewards_type not between", value1, value2,
					"rewardsType");
			return this;
		}

		public Criteria andNameIsNull() {
			addCriterion("name is null");
			return this;
		}

		public Criteria andNameIsNotNull() {
			addCriterion("name is not null");
			return this;
		}

		public Criteria andNameEqualTo(String value) {
			addCriterion("name =", value, "name");
			return this;
		}

		public Criteria andNameNotEqualTo(String value) {
			addCriterion("name <>", value, "name");
			return this;
		}

		public Criteria andNameGreaterThan(String value) {
			addCriterion("name >", value, "name");
			return this;
		}

		public Criteria andNameGreaterThanOrEqualTo(String value) {
			addCriterion("name >=", value, "name");
			return this;
		}

		public Criteria andNameLessThan(String value) {
			addCriterion("name <", value, "name");
			return this;
		}

		public Criteria andNameLessThanOrEqualTo(String value) {
			addCriterion("name <=", value, "name");
			return this;
		}

		public Criteria andNameLike(String value) {
			addCriterion("name like", value, "name");
			return this;
		}

		public Criteria andNameNotLike(String value) {
			addCriterion("name not like", value, "name");
			return this;
		}

		public Criteria andNameIn(List<String> values) {
			addCriterion("name in", values, "name");
			return this;
		}

		public Criteria andNameNotIn(List<String> values) {
			addCriterion("name not in", values, "name");
			return this;
		}

		public Criteria andNameBetween(String value1, String value2) {
			addCriterion("name between", value1, value2, "name");
			return this;
		}

		public Criteria andNameNotBetween(String value1, String value2) {
			addCriterion("name not between", value1, value2, "name");
			return this;
		}

		public Criteria andPlayerPreIsNull() {
			addCriterion("player_pre is null");
			return this;
		}

		public Criteria andPlayerPreIsNotNull() {
			addCriterion("player_pre is not null");
			return this;
		}

		public Criteria andPlayerPreEqualTo(Integer value) {
			addCriterion("player_pre =", value, "playerPre");
			return this;
		}

		public Criteria andPlayerPreNotEqualTo(Integer value) {
			addCriterion("player_pre <>", value, "playerPre");
			return this;
		}

		public Criteria andPlayerPreGreaterThan(Integer value) {
			addCriterion("player_pre >", value, "playerPre");
			return this;
		}

		public Criteria andPlayerPreGreaterThanOrEqualTo(Integer value) {
			addCriterion("player_pre >=", value, "playerPre");
			return this;
		}

		public Criteria andPlayerPreLessThan(Integer value) {
			addCriterion("player_pre <", value, "playerPre");
			return this;
		}

		public Criteria andPlayerPreLessThanOrEqualTo(Integer value) {
			addCriterion("player_pre <=", value, "playerPre");
			return this;
		}

		public Criteria andPlayerPreIn(List<Integer> values) {
			addCriterion("player_pre in", values, "playerPre");
			return this;
		}

		public Criteria andPlayerPreNotIn(List<Integer> values) {
			addCriterion("player_pre not in", values, "playerPre");
			return this;
		}

		public Criteria andPlayerPreBetween(Integer value1, Integer value2) {
			addCriterion("player_pre between", value1, value2, "playerPre");
			return this;
		}

		public Criteria andPlayerPreNotBetween(Integer value1, Integer value2) {
			addCriterion("player_pre not between", value1, value2, "playerPre");
			return this;
		}

		public Criteria andRobotPreIsNull() {
			addCriterion("robot_pre is null");
			return this;
		}

		public Criteria andRobotPreIsNotNull() {
			addCriterion("robot_pre is not null");
			return this;
		}

		public Criteria andRobotPreEqualTo(Integer value) {
			addCriterion("robot_pre =", value, "robotPre");
			return this;
		}

		public Criteria andRobotPreNotEqualTo(Integer value) {
			addCriterion("robot_pre <>", value, "robotPre");
			return this;
		}

		public Criteria andRobotPreGreaterThan(Integer value) {
			addCriterion("robot_pre >", value, "robotPre");
			return this;
		}

		public Criteria andRobotPreGreaterThanOrEqualTo(Integer value) {
			addCriterion("robot_pre >=", value, "robotPre");
			return this;
		}

		public Criteria andRobotPreLessThan(Integer value) {
			addCriterion("robot_pre <", value, "robotPre");
			return this;
		}

		public Criteria andRobotPreLessThanOrEqualTo(Integer value) {
			addCriterion("robot_pre <=", value, "robotPre");
			return this;
		}

		public Criteria andRobotPreIn(List<Integer> values) {
			addCriterion("robot_pre in", values, "robotPre");
			return this;
		}

		public Criteria andRobotPreNotIn(List<Integer> values) {
			addCriterion("robot_pre not in", values, "robotPre");
			return this;
		}

		public Criteria andRobotPreBetween(Integer value1, Integer value2) {
			addCriterion("robot_pre between", value1, value2, "robotPre");
			return this;
		}

		public Criteria andRobotPreNotBetween(Integer value1, Integer value2) {
			addCriterion("robot_pre not between", value1, value2, "robotPre");
			return this;
		}
	}
}