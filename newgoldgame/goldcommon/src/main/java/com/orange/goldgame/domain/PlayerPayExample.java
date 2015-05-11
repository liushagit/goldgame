package com.orange.goldgame.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerPayExample {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table player_pay
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	protected String orderByClause;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table player_pay
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	protected List oredCriteria;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_pay
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	public PlayerPayExample() {
		oredCriteria = new ArrayList();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_pay
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	protected PlayerPayExample(PlayerPayExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_pay
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_pay
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_pay
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	public List getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_pay
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_pay
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_pay
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_pay
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table player_pay
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
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

		public Criteria andPayTimeIsNull() {
			addCriterion("pay_time is null");
			return this;
		}

		public Criteria andPayTimeIsNotNull() {
			addCriterion("pay_time is not null");
			return this;
		}

		public Criteria andPayTimeEqualTo(Date value) {
			addCriterion("pay_time =", value, "payTime");
			return this;
		}

		public Criteria andPayTimeNotEqualTo(Date value) {
			addCriterion("pay_time <>", value, "payTime");
			return this;
		}

		public Criteria andPayTimeGreaterThan(Date value) {
			addCriterion("pay_time >", value, "payTime");
			return this;
		}

		public Criteria andPayTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("pay_time >=", value, "payTime");
			return this;
		}

		public Criteria andPayTimeLessThan(Date value) {
			addCriterion("pay_time <", value, "payTime");
			return this;
		}

		public Criteria andPayTimeLessThanOrEqualTo(Date value) {
			addCriterion("pay_time <=", value, "payTime");
			return this;
		}

		public Criteria andPayTimeIn(List values) {
			addCriterion("pay_time in", values, "payTime");
			return this;
		}

		public Criteria andPayTimeNotIn(List values) {
			addCriterion("pay_time not in", values, "payTime");
			return this;
		}

		public Criteria andPayTimeBetween(Date value1, Date value2) {
			addCriterion("pay_time between", value1, value2, "payTime");
			return this;
		}

		public Criteria andPayTimeNotBetween(Date value1, Date value2) {
			addCriterion("pay_time not between", value1, value2, "payTime");
			return this;
		}

		public Criteria andGoldIsNull() {
			addCriterion("gold is null");
			return this;
		}

		public Criteria andGoldIsNotNull() {
			addCriterion("gold is not null");
			return this;
		}

		public Criteria andGoldEqualTo(Integer value) {
			addCriterion("gold =", value, "gold");
			return this;
		}

		public Criteria andGoldNotEqualTo(Integer value) {
			addCriterion("gold <>", value, "gold");
			return this;
		}

		public Criteria andGoldGreaterThan(Integer value) {
			addCriterion("gold >", value, "gold");
			return this;
		}

		public Criteria andGoldGreaterThanOrEqualTo(Integer value) {
			addCriterion("gold >=", value, "gold");
			return this;
		}

		public Criteria andGoldLessThan(Integer value) {
			addCriterion("gold <", value, "gold");
			return this;
		}

		public Criteria andGoldLessThanOrEqualTo(Integer value) {
			addCriterion("gold <=", value, "gold");
			return this;
		}

		public Criteria andGoldIn(List values) {
			addCriterion("gold in", values, "gold");
			return this;
		}

		public Criteria andGoldNotIn(List values) {
			addCriterion("gold not in", values, "gold");
			return this;
		}

		public Criteria andGoldBetween(Integer value1, Integer value2) {
			addCriterion("gold between", value1, value2, "gold");
			return this;
		}

		public Criteria andGoldNotBetween(Integer value1, Integer value2) {
			addCriterion("gold not between", value1, value2, "gold");
			return this;
		}

		public Criteria andOrderIdIsNull() {
			addCriterion("order_id is null");
			return this;
		}

		public Criteria andOrderIdIsNotNull() {
			addCriterion("order_id is not null");
			return this;
		}

		public Criteria andOrderIdEqualTo(String value) {
			addCriterion("order_id =", value, "orderId");
			return this;
		}

		public Criteria andOrderIdNotEqualTo(String value) {
			addCriterion("order_id <>", value, "orderId");
			return this;
		}

		public Criteria andOrderIdGreaterThan(String value) {
			addCriterion("order_id >", value, "orderId");
			return this;
		}

		public Criteria andOrderIdGreaterThanOrEqualTo(String value) {
			addCriterion("order_id >=", value, "orderId");
			return this;
		}

		public Criteria andOrderIdLessThan(String value) {
			addCriterion("order_id <", value, "orderId");
			return this;
		}

		public Criteria andOrderIdLessThanOrEqualTo(String value) {
			addCriterion("order_id <=", value, "orderId");
			return this;
		}

		public Criteria andOrderIdLike(String value) {
			addCriterion("order_id like", value, "orderId");
			return this;
		}

		public Criteria andOrderIdNotLike(String value) {
			addCriterion("order_id not like", value, "orderId");
			return this;
		}

		public Criteria andOrderIdIn(List values) {
			addCriterion("order_id in", values, "orderId");
			return this;
		}

		public Criteria andOrderIdNotIn(List values) {
			addCriterion("order_id not in", values, "orderId");
			return this;
		}

		public Criteria andOrderIdBetween(String value1, String value2) {
			addCriterion("order_id between", value1, value2, "orderId");
			return this;
		}

		public Criteria andOrderIdNotBetween(String value1, String value2) {
			addCriterion("order_id not between", value1, value2, "orderId");
			return this;
		}

		public Criteria andPayTypeIsNull() {
			addCriterion("pay_type is null");
			return this;
		}

		public Criteria andPayTypeIsNotNull() {
			addCriterion("pay_type is not null");
			return this;
		}

		public Criteria andPayTypeEqualTo(String value) {
			addCriterion("pay_type =", value, "payType");
			return this;
		}

		public Criteria andPayTypeNotEqualTo(String value) {
			addCriterion("pay_type <>", value, "payType");
			return this;
		}

		public Criteria andPayTypeGreaterThan(String value) {
			addCriterion("pay_type >", value, "payType");
			return this;
		}

		public Criteria andPayTypeGreaterThanOrEqualTo(String value) {
			addCriterion("pay_type >=", value, "payType");
			return this;
		}

		public Criteria andPayTypeLessThan(String value) {
			addCriterion("pay_type <", value, "payType");
			return this;
		}

		public Criteria andPayTypeLessThanOrEqualTo(String value) {
			addCriterion("pay_type <=", value, "payType");
			return this;
		}

		public Criteria andPayTypeLike(String value) {
			addCriterion("pay_type like", value, "payType");
			return this;
		}

		public Criteria andPayTypeNotLike(String value) {
			addCriterion("pay_type not like", value, "payType");
			return this;
		}

		public Criteria andPayTypeIn(List values) {
			addCriterion("pay_type in", values, "payType");
			return this;
		}

		public Criteria andPayTypeNotIn(List values) {
			addCriterion("pay_type not in", values, "payType");
			return this;
		}

		public Criteria andPayTypeBetween(String value1, String value2) {
			addCriterion("pay_type between", value1, value2, "payType");
			return this;
		}

		public Criteria andPayTypeNotBetween(String value1, String value2) {
			addCriterion("pay_type not between", value1, value2, "payType");
			return this;
		}

		public Criteria andPayInfoIdIsNull() {
			addCriterion("pay_info_id is null");
			return this;
		}

		public Criteria andPayInfoIdIsNotNull() {
			addCriterion("pay_info_id is not null");
			return this;
		}

		public Criteria andPayInfoIdEqualTo(String value) {
			addCriterion("pay_info_id =", value, "payInfoId");
			return this;
		}

		public Criteria andPayInfoIdNotEqualTo(String value) {
			addCriterion("pay_info_id <>", value, "payInfoId");
			return this;
		}

		public Criteria andPayInfoIdGreaterThan(String value) {
			addCriterion("pay_info_id >", value, "payInfoId");
			return this;
		}

		public Criteria andPayInfoIdGreaterThanOrEqualTo(String value) {
			addCriterion("pay_info_id >=", value, "payInfoId");
			return this;
		}

		public Criteria andPayInfoIdLessThan(String value) {
			addCriterion("pay_info_id <", value, "payInfoId");
			return this;
		}

		public Criteria andPayInfoIdLessThanOrEqualTo(String value) {
			addCriterion("pay_info_id <=", value, "payInfoId");
			return this;
		}

		public Criteria andPayInfoIdLike(String value) {
			addCriterion("pay_info_id like", value, "payInfoId");
			return this;
		}

		public Criteria andPayInfoIdNotLike(String value) {
			addCriterion("pay_info_id not like", value, "payInfoId");
			return this;
		}

		public Criteria andPayInfoIdIn(List values) {
			addCriterion("pay_info_id in", values, "payInfoId");
			return this;
		}

		public Criteria andPayInfoIdNotIn(List values) {
			addCriterion("pay_info_id not in", values, "payInfoId");
			return this;
		}

		public Criteria andPayInfoIdBetween(String value1, String value2) {
			addCriterion("pay_info_id between", value1, value2, "payInfoId");
			return this;
		}

		public Criteria andPayInfoIdNotBetween(String value1, String value2) {
			addCriterion("pay_info_id not between", value1, value2, "payInfoId");
			return this;
		}

		public Criteria andTradeIdIsNull() {
			addCriterion("trade_id is null");
			return this;
		}

		public Criteria andTradeIdIsNotNull() {
			addCriterion("trade_id is not null");
			return this;
		}

		public Criteria andTradeIdEqualTo(String value) {
			addCriterion("trade_id =", value, "tradeId");
			return this;
		}

		public Criteria andTradeIdNotEqualTo(String value) {
			addCriterion("trade_id <>", value, "tradeId");
			return this;
		}

		public Criteria andTradeIdGreaterThan(String value) {
			addCriterion("trade_id >", value, "tradeId");
			return this;
		}

		public Criteria andTradeIdGreaterThanOrEqualTo(String value) {
			addCriterion("trade_id >=", value, "tradeId");
			return this;
		}

		public Criteria andTradeIdLessThan(String value) {
			addCriterion("trade_id <", value, "tradeId");
			return this;
		}

		public Criteria andTradeIdLessThanOrEqualTo(String value) {
			addCriterion("trade_id <=", value, "tradeId");
			return this;
		}

		public Criteria andTradeIdLike(String value) {
			addCriterion("trade_id like", value, "tradeId");
			return this;
		}

		public Criteria andTradeIdNotLike(String value) {
			addCriterion("trade_id not like", value, "tradeId");
			return this;
		}

		public Criteria andTradeIdIn(List values) {
			addCriterion("trade_id in", values, "tradeId");
			return this;
		}

		public Criteria andTradeIdNotIn(List values) {
			addCriterion("trade_id not in", values, "tradeId");
			return this;
		}

		public Criteria andTradeIdBetween(String value1, String value2) {
			addCriterion("trade_id between", value1, value2, "tradeId");
			return this;
		}

		public Criteria andTradeIdNotBetween(String value1, String value2) {
			addCriterion("trade_id not between", value1, value2, "tradeId");
			return this;
		}

		public Criteria andBuyIdIsNull() {
			addCriterion("buy_id is null");
			return this;
		}

		public Criteria andBuyIdIsNotNull() {
			addCriterion("buy_id is not null");
			return this;
		}

		public Criteria andBuyIdEqualTo(String value) {
			addCriterion("buy_id =", value, "buyId");
			return this;
		}

		public Criteria andBuyIdNotEqualTo(String value) {
			addCriterion("buy_id <>", value, "buyId");
			return this;
		}

		public Criteria andBuyIdGreaterThan(String value) {
			addCriterion("buy_id >", value, "buyId");
			return this;
		}

		public Criteria andBuyIdGreaterThanOrEqualTo(String value) {
			addCriterion("buy_id >=", value, "buyId");
			return this;
		}

		public Criteria andBuyIdLessThan(String value) {
			addCriterion("buy_id <", value, "buyId");
			return this;
		}

		public Criteria andBuyIdLessThanOrEqualTo(String value) {
			addCriterion("buy_id <=", value, "buyId");
			return this;
		}

		public Criteria andBuyIdLike(String value) {
			addCriterion("buy_id like", value, "buyId");
			return this;
		}

		public Criteria andBuyIdNotLike(String value) {
			addCriterion("buy_id not like", value, "buyId");
			return this;
		}

		public Criteria andBuyIdIn(List values) {
			addCriterion("buy_id in", values, "buyId");
			return this;
		}

		public Criteria andBuyIdNotIn(List values) {
			addCriterion("buy_id not in", values, "buyId");
			return this;
		}

		public Criteria andBuyIdBetween(String value1, String value2) {
			addCriterion("buy_id between", value1, value2, "buyId");
			return this;
		}

		public Criteria andBuyIdNotBetween(String value1, String value2) {
			addCriterion("buy_id not between", value1, value2, "buyId");
			return this;
		}
	}
}