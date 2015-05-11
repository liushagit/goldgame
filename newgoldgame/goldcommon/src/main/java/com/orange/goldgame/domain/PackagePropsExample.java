package com.orange.goldgame.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PackagePropsExample {
    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
     */
    protected String orderByClause;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
     */
    public PackagePropsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
     */
    protected PackagePropsExample(PackagePropsExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
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
     * This method corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This class was generated by Abator for iBATIS.
     * This class corresponds to the database table package_props
     *
     * @abatorgenerated Thu Apr 17 16:33:19 CST 2014
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

        public Criteria andPropsIdIsNull() {
            addCriterion("props_Id is null");
            return this;
        }

        public Criteria andPropsIdIsNotNull() {
            addCriterion("props_Id is not null");
            return this;
        }

        public Criteria andPropsIdEqualTo(Integer value) {
            addCriterion("props_Id =", value, "propsId");
            return this;
        }

        public Criteria andPropsIdNotEqualTo(Integer value) {
            addCriterion("props_Id <>", value, "propsId");
            return this;
        }

        public Criteria andPropsIdGreaterThan(Integer value) {
            addCriterion("props_Id >", value, "propsId");
            return this;
        }

        public Criteria andPropsIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("props_Id >=", value, "propsId");
            return this;
        }

        public Criteria andPropsIdLessThan(Integer value) {
            addCriterion("props_Id <", value, "propsId");
            return this;
        }

        public Criteria andPropsIdLessThanOrEqualTo(Integer value) {
            addCriterion("props_Id <=", value, "propsId");
            return this;
        }

        public Criteria andPropsIdIn(List<Integer> values) {
            addCriterion("props_Id in", values, "propsId");
            return this;
        }

        public Criteria andPropsIdNotIn(List<Integer> values) {
            addCriterion("props_Id not in", values, "propsId");
            return this;
        }

        public Criteria andPropsIdBetween(Integer value1, Integer value2) {
            addCriterion("props_Id between", value1, value2, "propsId");
            return this;
        }

        public Criteria andPropsIdNotBetween(Integer value1, Integer value2) {
            addCriterion("props_Id not between", value1, value2, "propsId");
            return this;
        }

        public Criteria andPropsNumberIsNull() {
            addCriterion("props_Number is null");
            return this;
        }

        public Criteria andPropsNumberIsNotNull() {
            addCriterion("props_Number is not null");
            return this;
        }

        public Criteria andPropsNumberEqualTo(Integer value) {
            addCriterion("props_Number =", value, "propsNumber");
            return this;
        }

        public Criteria andPropsNumberNotEqualTo(Integer value) {
            addCriterion("props_Number <>", value, "propsNumber");
            return this;
        }

        public Criteria andPropsNumberGreaterThan(Integer value) {
            addCriterion("props_Number >", value, "propsNumber");
            return this;
        }

        public Criteria andPropsNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("props_Number >=", value, "propsNumber");
            return this;
        }

        public Criteria andPropsNumberLessThan(Integer value) {
            addCriterion("props_Number <", value, "propsNumber");
            return this;
        }

        public Criteria andPropsNumberLessThanOrEqualTo(Integer value) {
            addCriterion("props_Number <=", value, "propsNumber");
            return this;
        }

        public Criteria andPropsNumberIn(List<Integer> values) {
            addCriterion("props_Number in", values, "propsNumber");
            return this;
        }

        public Criteria andPropsNumberNotIn(List<Integer> values) {
            addCriterion("props_Number not in", values, "propsNumber");
            return this;
        }

        public Criteria andPropsNumberBetween(Integer value1, Integer value2) {
            addCriterion("props_Number between", value1, value2, "propsNumber");
            return this;
        }

        public Criteria andPropsNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("props_Number not between", value1, value2, "propsNumber");
            return this;
        }

        public Criteria andPropsMoneyIsNull() {
            addCriterion("props_Money is null");
            return this;
        }

        public Criteria andPropsMoneyIsNotNull() {
            addCriterion("props_Money is not null");
            return this;
        }

        public Criteria andPropsMoneyEqualTo(Integer value) {
            addCriterion("props_Money =", value, "propsMoney");
            return this;
        }

        public Criteria andPropsMoneyNotEqualTo(Integer value) {
            addCriterion("props_Money <>", value, "propsMoney");
            return this;
        }

        public Criteria andPropsMoneyGreaterThan(Integer value) {
            addCriterion("props_Money >", value, "propsMoney");
            return this;
        }

        public Criteria andPropsMoneyGreaterThanOrEqualTo(Integer value) {
            addCriterion("props_Money >=", value, "propsMoney");
            return this;
        }

        public Criteria andPropsMoneyLessThan(Integer value) {
            addCriterion("props_Money <", value, "propsMoney");
            return this;
        }

        public Criteria andPropsMoneyLessThanOrEqualTo(Integer value) {
            addCriterion("props_Money <=", value, "propsMoney");
            return this;
        }

        public Criteria andPropsMoneyIn(List<Integer> values) {
            addCriterion("props_Money in", values, "propsMoney");
            return this;
        }

        public Criteria andPropsMoneyNotIn(List<Integer> values) {
            addCriterion("props_Money not in", values, "propsMoney");
            return this;
        }

        public Criteria andPropsMoneyBetween(Integer value1, Integer value2) {
            addCriterion("props_Money between", value1, value2, "propsMoney");
            return this;
        }

        public Criteria andPropsMoneyNotBetween(Integer value1, Integer value2) {
            addCriterion("props_Money not between", value1, value2, "propsMoney");
            return this;
        }

        public Criteria andPropsAwardIsNull() {
            addCriterion("props_Award is null");
            return this;
        }

        public Criteria andPropsAwardIsNotNull() {
            addCriterion("props_Award is not null");
            return this;
        }

        public Criteria andPropsAwardEqualTo(Integer value) {
            addCriterion("props_Award =", value, "propsAward");
            return this;
        }

        public Criteria andPropsAwardNotEqualTo(Integer value) {
            addCriterion("props_Award <>", value, "propsAward");
            return this;
        }

        public Criteria andPropsAwardGreaterThan(Integer value) {
            addCriterion("props_Award >", value, "propsAward");
            return this;
        }

        public Criteria andPropsAwardGreaterThanOrEqualTo(Integer value) {
            addCriterion("props_Award >=", value, "propsAward");
            return this;
        }

        public Criteria andPropsAwardLessThan(Integer value) {
            addCriterion("props_Award <", value, "propsAward");
            return this;
        }

        public Criteria andPropsAwardLessThanOrEqualTo(Integer value) {
            addCriterion("props_Award <=", value, "propsAward");
            return this;
        }

        public Criteria andPropsAwardIn(List<Integer> values) {
            addCriterion("props_Award in", values, "propsAward");
            return this;
        }

        public Criteria andPropsAwardNotIn(List<Integer> values) {
            addCriterion("props_Award not in", values, "propsAward");
            return this;
        }

        public Criteria andPropsAwardBetween(Integer value1, Integer value2) {
            addCriterion("props_Award between", value1, value2, "propsAward");
            return this;
        }

        public Criteria andPropsAwardNotBetween(Integer value1, Integer value2) {
            addCriterion("props_Award not between", value1, value2, "propsAward");
            return this;
        }

        public Criteria andActivityTypeIsNull() {
            addCriterion("activity_type is null");
            return this;
        }

        public Criteria andActivityTypeIsNotNull() {
            addCriterion("activity_type is not null");
            return this;
        }

        public Criteria andActivityTypeEqualTo(Integer value) {
            addCriterion("activity_type =", value, "activityType");
            return this;
        }

        public Criteria andActivityTypeNotEqualTo(Integer value) {
            addCriterion("activity_type <>", value, "activityType");
            return this;
        }

        public Criteria andActivityTypeGreaterThan(Integer value) {
            addCriterion("activity_type >", value, "activityType");
            return this;
        }

        public Criteria andActivityTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("activity_type >=", value, "activityType");
            return this;
        }

        public Criteria andActivityTypeLessThan(Integer value) {
            addCriterion("activity_type <", value, "activityType");
            return this;
        }

        public Criteria andActivityTypeLessThanOrEqualTo(Integer value) {
            addCriterion("activity_type <=", value, "activityType");
            return this;
        }

        public Criteria andActivityTypeIn(List<Integer> values) {
            addCriterion("activity_type in", values, "activityType");
            return this;
        }

        public Criteria andActivityTypeNotIn(List<Integer> values) {
            addCriterion("activity_type not in", values, "activityType");
            return this;
        }

        public Criteria andActivityTypeBetween(Integer value1, Integer value2) {
            addCriterion("activity_type between", value1, value2, "activityType");
            return this;
        }

        public Criteria andActivityTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("activity_type not between", value1, value2, "activityType");
            return this;
        }

        public Criteria andActivityRewardIsNull() {
            addCriterion("activity_reward is null");
            return this;
        }

        public Criteria andActivityRewardIsNotNull() {
            addCriterion("activity_reward is not null");
            return this;
        }

        public Criteria andActivityRewardEqualTo(String value) {
            addCriterion("activity_reward =", value, "activityReward");
            return this;
        }

        public Criteria andActivityRewardNotEqualTo(String value) {
            addCriterion("activity_reward <>", value, "activityReward");
            return this;
        }

        public Criteria andActivityRewardGreaterThan(String value) {
            addCriterion("activity_reward >", value, "activityReward");
            return this;
        }

        public Criteria andActivityRewardGreaterThanOrEqualTo(String value) {
            addCriterion("activity_reward >=", value, "activityReward");
            return this;
        }

        public Criteria andActivityRewardLessThan(String value) {
            addCriterion("activity_reward <", value, "activityReward");
            return this;
        }

        public Criteria andActivityRewardLessThanOrEqualTo(String value) {
            addCriterion("activity_reward <=", value, "activityReward");
            return this;
        }

        public Criteria andActivityRewardLike(String value) {
            addCriterion("activity_reward like", value, "activityReward");
            return this;
        }

        public Criteria andActivityRewardNotLike(String value) {
            addCriterion("activity_reward not like", value, "activityReward");
            return this;
        }

        public Criteria andActivityRewardIn(List<String> values) {
            addCriterion("activity_reward in", values, "activityReward");
            return this;
        }

        public Criteria andActivityRewardNotIn(List<String> values) {
            addCriterion("activity_reward not in", values, "activityReward");
            return this;
        }

        public Criteria andActivityRewardBetween(String value1, String value2) {
            addCriterion("activity_reward between", value1, value2, "activityReward");
            return this;
        }

        public Criteria andActivityRewardNotBetween(String value1, String value2) {
            addCriterion("activity_reward not between", value1, value2, "activityReward");
            return this;
        }
    }
}