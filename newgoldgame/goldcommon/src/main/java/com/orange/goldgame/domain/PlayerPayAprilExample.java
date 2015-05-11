package com.orange.goldgame.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerPayAprilExample {
    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database table player_pay_april
     *
     * @abatorgenerated Wed Mar 26 14:13:55 CST 2014
     */
    protected String orderByClause;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database table player_pay_april
     *
     * @abatorgenerated Wed Mar 26 14:13:55 CST 2014
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_pay_april
     *
     * @abatorgenerated Wed Mar 26 14:13:55 CST 2014
     */
    public PlayerPayAprilExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_pay_april
     *
     * @abatorgenerated Wed Mar 26 14:13:55 CST 2014
     */
    protected PlayerPayAprilExample(PlayerPayAprilExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_pay_april
     *
     * @abatorgenerated Wed Mar 26 14:13:55 CST 2014
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_pay_april
     *
     * @abatorgenerated Wed Mar 26 14:13:55 CST 2014
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_pay_april
     *
     * @abatorgenerated Wed Mar 26 14:13:55 CST 2014
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_pay_april
     *
     * @abatorgenerated Wed Mar 26 14:13:55 CST 2014
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_pay_april
     *
     * @abatorgenerated Wed Mar 26 14:13:55 CST 2014
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_pay_april
     *
     * @abatorgenerated Wed Mar 26 14:13:55 CST 2014
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_pay_april
     *
     * @abatorgenerated Wed Mar 26 14:13:55 CST 2014
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This class was generated by Abator for iBATIS.
     * This class corresponds to the database table player_pay_april
     *
     * @abatorgenerated Wed Mar 26 14:13:55 CST 2014
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

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("condition", condition);
            map.put("value", value);
            criteriaWithSingleValue.add(map);
        }

        protected void addCriterion(String condition, List<? extends Object> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("condition", condition);
            map.put("values", values);
            criteriaWithListValue.add(map);
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
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

        public Criteria andPlayerIdIn(List<Integer> values) {
            addCriterion("player_id in", values, "playerId");
            return this;
        }

        public Criteria andPlayerIdNotIn(List<Integer> values) {
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

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return this;
        }

        public Criteria andAllGoldsIsNull() {
            addCriterion("all_golds is null");
            return this;
        }

        public Criteria andAllGoldsIsNotNull() {
            addCriterion("all_golds is not null");
            return this;
        }

        public Criteria andAllGoldsEqualTo(Integer value) {
            addCriterion("all_golds =", value, "allGolds");
            return this;
        }

        public Criteria andAllGoldsNotEqualTo(Integer value) {
            addCriterion("all_golds <>", value, "allGolds");
            return this;
        }

        public Criteria andAllGoldsGreaterThan(Integer value) {
            addCriterion("all_golds >", value, "allGolds");
            return this;
        }

        public Criteria andAllGoldsGreaterThanOrEqualTo(Integer value) {
            addCriterion("all_golds >=", value, "allGolds");
            return this;
        }

        public Criteria andAllGoldsLessThan(Integer value) {
            addCriterion("all_golds <", value, "allGolds");
            return this;
        }

        public Criteria andAllGoldsLessThanOrEqualTo(Integer value) {
            addCriterion("all_golds <=", value, "allGolds");
            return this;
        }

        public Criteria andAllGoldsIn(List<Integer> values) {
            addCriterion("all_golds in", values, "allGolds");
            return this;
        }

        public Criteria andAllGoldsNotIn(List<Integer> values) {
            addCriterion("all_golds not in", values, "allGolds");
            return this;
        }

        public Criteria andAllGoldsBetween(Integer value1, Integer value2) {
            addCriterion("all_golds between", value1, value2, "allGolds");
            return this;
        }

        public Criteria andAllGoldsNotBetween(Integer value1, Integer value2) {
            addCriterion("all_golds not between", value1, value2, "allGolds");
            return this;
        }

        public Criteria andRewardsNumIsNull() {
            addCriterion("rewards_num is null");
            return this;
        }

        public Criteria andRewardsNumIsNotNull() {
            addCriterion("rewards_num is not null");
            return this;
        }

        public Criteria andRewardsNumEqualTo(Integer value) {
            addCriterion("rewards_num =", value, "rewardsNum");
            return this;
        }

        public Criteria andRewardsNumNotEqualTo(Integer value) {
            addCriterion("rewards_num <>", value, "rewardsNum");
            return this;
        }

        public Criteria andRewardsNumGreaterThan(Integer value) {
            addCriterion("rewards_num >", value, "rewardsNum");
            return this;
        }

        public Criteria andRewardsNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("rewards_num >=", value, "rewardsNum");
            return this;
        }

        public Criteria andRewardsNumLessThan(Integer value) {
            addCriterion("rewards_num <", value, "rewardsNum");
            return this;
        }

        public Criteria andRewardsNumLessThanOrEqualTo(Integer value) {
            addCriterion("rewards_num <=", value, "rewardsNum");
            return this;
        }

        public Criteria andRewardsNumIn(List<Integer> values) {
            addCriterion("rewards_num in", values, "rewardsNum");
            return this;
        }

        public Criteria andRewardsNumNotIn(List<Integer> values) {
            addCriterion("rewards_num not in", values, "rewardsNum");
            return this;
        }

        public Criteria andRewardsNumBetween(Integer value1, Integer value2) {
            addCriterion("rewards_num between", value1, value2, "rewardsNum");
            return this;
        }

        public Criteria andRewardsNumNotBetween(Integer value1, Integer value2) {
            addCriterion("rewards_num not between", value1, value2, "rewardsNum");
            return this;
        }
    }
}