package com.orange.goldgame.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayOrderExample {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table pay_order
     *
     * @ibatorgenerated Wed Feb 26 15:34:31 CST 2014
     */
    protected String orderByClause;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table pay_order
     *
     * @ibatorgenerated Wed Feb 26 15:34:31 CST 2014
     */
    protected List oredCriteria;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table pay_order
     *
     * @ibatorgenerated Wed Feb 26 15:34:31 CST 2014
     */
    public PayOrderExample() {
        oredCriteria = new ArrayList();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table pay_order
     *
     * @ibatorgenerated Wed Feb 26 15:34:31 CST 2014
     */
    protected PayOrderExample(PayOrderExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table pay_order
     *
     * @ibatorgenerated Wed Feb 26 15:34:31 CST 2014
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table pay_order
     *
     * @ibatorgenerated Wed Feb 26 15:34:31 CST 2014
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table pay_order
     *
     * @ibatorgenerated Wed Feb 26 15:34:31 CST 2014
     */
    public List getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table pay_order
     *
     * @ibatorgenerated Wed Feb 26 15:34:31 CST 2014
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table pay_order
     *
     * @ibatorgenerated Wed Feb 26 15:34:31 CST 2014
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table pay_order
     *
     * @ibatorgenerated Wed Feb 26 15:34:31 CST 2014
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table pay_order
     *
     * @ibatorgenerated Wed Feb 26 15:34:31 CST 2014
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table pay_order
     *
     * @ibatorgenerated Wed Feb 26 15:34:31 CST 2014
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

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("value", value);
            criteriaWithSingleValue.add(map);
        }

        protected void addCriterion(String condition, List values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("values", values);
            criteriaWithListValue.add(map);
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
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

        public Criteria andPayInfoIdIsNull() {
            addCriterion("pay_info_id is null");
            return this;
        }

        public Criteria andPayInfoIdIsNotNull() {
            addCriterion("pay_info_id is not null");
            return this;
        }

        public Criteria andPayInfoIdEqualTo(Integer value) {
            addCriterion("pay_info_id =", value, "payInfoId");
            return this;
        }

        public Criteria andPayInfoIdNotEqualTo(Integer value) {
            addCriterion("pay_info_id <>", value, "payInfoId");
            return this;
        }

        public Criteria andPayInfoIdGreaterThan(Integer value) {
            addCriterion("pay_info_id >", value, "payInfoId");
            return this;
        }

        public Criteria andPayInfoIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("pay_info_id >=", value, "payInfoId");
            return this;
        }

        public Criteria andPayInfoIdLessThan(Integer value) {
            addCriterion("pay_info_id <", value, "payInfoId");
            return this;
        }

        public Criteria andPayInfoIdLessThanOrEqualTo(Integer value) {
            addCriterion("pay_info_id <=", value, "payInfoId");
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

        public Criteria andPayInfoIdBetween(Integer value1, Integer value2) {
            addCriterion("pay_info_id between", value1, value2, "payInfoId");
            return this;
        }

        public Criteria andPayInfoIdNotBetween(Integer value1, Integer value2) {
            addCriterion("pay_info_id not between", value1, value2, "payInfoId");
            return this;
        }

        public Criteria andPayStatusIsNull() {
            addCriterion("pay_status is null");
            return this;
        }

        public Criteria andPayStatusIsNotNull() {
            addCriterion("pay_status is not null");
            return this;
        }

        public Criteria andPayStatusEqualTo(Integer value) {
            addCriterion("pay_status =", value, "payStatus");
            return this;
        }

        public Criteria andPayStatusNotEqualTo(Integer value) {
            addCriterion("pay_status <>", value, "payStatus");
            return this;
        }

        public Criteria andPayStatusGreaterThan(Integer value) {
            addCriterion("pay_status >", value, "payStatus");
            return this;
        }

        public Criteria andPayStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("pay_status >=", value, "payStatus");
            return this;
        }

        public Criteria andPayStatusLessThan(Integer value) {
            addCriterion("pay_status <", value, "payStatus");
            return this;
        }

        public Criteria andPayStatusLessThanOrEqualTo(Integer value) {
            addCriterion("pay_status <=", value, "payStatus");
            return this;
        }

        public Criteria andPayStatusIn(List values) {
            addCriterion("pay_status in", values, "payStatus");
            return this;
        }

        public Criteria andPayStatusNotIn(List values) {
            addCriterion("pay_status not in", values, "payStatus");
            return this;
        }

        public Criteria andPayStatusBetween(Integer value1, Integer value2) {
            addCriterion("pay_status between", value1, value2, "payStatus");
            return this;
        }

        public Criteria andPayStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("pay_status not between", value1, value2, "payStatus");
            return this;
        }
    }
}