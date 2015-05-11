package com.orange.goldgame.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropsConfigExample {

    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database table props_config
     * @ibatorgenerated  Wed Jul 31 11:43:32 CST 2013
     */
    protected String orderByClause;
    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database table props_config
     * @ibatorgenerated  Wed Jul 31 11:43:32 CST 2013
     */
    protected List oredCriteria;

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table props_config
     * @ibatorgenerated  Wed Jul 31 11:43:32 CST 2013
     */
    public PropsConfigExample() {
        oredCriteria = new ArrayList();
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table props_config
     * @ibatorgenerated  Wed Jul 31 11:43:32 CST 2013
     */
    protected PropsConfigExample(PropsConfigExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table props_config
     * @ibatorgenerated  Wed Jul 31 11:43:32 CST 2013
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table props_config
     * @ibatorgenerated  Wed Jul 31 11:43:32 CST 2013
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table props_config
     * @ibatorgenerated  Wed Jul 31 11:43:32 CST 2013
     */
    public List getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table props_config
     * @ibatorgenerated  Wed Jul 31 11:43:32 CST 2013
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table props_config
     * @ibatorgenerated  Wed Jul 31 11:43:32 CST 2013
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table props_config
     * @ibatorgenerated  Wed Jul 31 11:43:32 CST 2013
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table props_config
     * @ibatorgenerated  Wed Jul 31 11:43:32 CST 2013
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This class was generated by Apache iBATIS ibator. This class corresponds to the database table props_config
     * @ibatorgenerated  Wed Jul 31 11:43:32 CST 2013
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

        public Criteria andNameIn(List values) {
            addCriterion("name in", values, "name");
            return this;
        }

        public Criteria andNameNotIn(List values) {
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

        public Criteria andPropsTypeIsNull() {
            addCriterion("props_type is null");
            return this;
        }

        public Criteria andPropsTypeIsNotNull() {
            addCriterion("props_type is not null");
            return this;
        }

        public Criteria andPropsTypeEqualTo(Integer value) {
            addCriterion("props_type =", value, "propsType");
            return this;
        }

        public Criteria andPropsTypeNotEqualTo(Integer value) {
            addCriterion("props_type <>", value, "propsType");
            return this;
        }

        public Criteria andPropsTypeGreaterThan(Integer value) {
            addCriterion("props_type >", value, "propsType");
            return this;
        }

        public Criteria andPropsTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("props_type >=", value, "propsType");
            return this;
        }

        public Criteria andPropsTypeLessThan(Integer value) {
            addCriterion("props_type <", value, "propsType");
            return this;
        }

        public Criteria andPropsTypeLessThanOrEqualTo(Integer value) {
            addCriterion("props_type <=", value, "propsType");
            return this;
        }

        public Criteria andPropsTypeIn(List values) {
            addCriterion("props_type in", values, "propsType");
            return this;
        }

        public Criteria andPropsTypeNotIn(List values) {
            addCriterion("props_type not in", values, "propsType");
            return this;
        }

        public Criteria andPropsTypeBetween(Integer value1, Integer value2) {
            addCriterion("props_type between", value1, value2, "propsType");
            return this;
        }

        public Criteria andPropsTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("props_type not between", value1, value2, "propsType");
            return this;
        }

        public Criteria andDestanceIsNull() {
            addCriterion("destance is null");
            return this;
        }

        public Criteria andDestanceIsNotNull() {
            addCriterion("destance is not null");
            return this;
        }

        public Criteria andDestanceEqualTo(String value) {
            addCriterion("destance =", value, "destance");
            return this;
        }

        public Criteria andDestanceNotEqualTo(String value) {
            addCriterion("destance <>", value, "destance");
            return this;
        }

        public Criteria andDestanceGreaterThan(String value) {
            addCriterion("destance >", value, "destance");
            return this;
        }

        public Criteria andDestanceGreaterThanOrEqualTo(String value) {
            addCriterion("destance >=", value, "destance");
            return this;
        }

        public Criteria andDestanceLessThan(String value) {
            addCriterion("destance <", value, "destance");
            return this;
        }

        public Criteria andDestanceLessThanOrEqualTo(String value) {
            addCriterion("destance <=", value, "destance");
            return this;
        }

        public Criteria andDestanceLike(String value) {
            addCriterion("destance like", value, "destance");
            return this;
        }

        public Criteria andDestanceNotLike(String value) {
            addCriterion("destance not like", value, "destance");
            return this;
        }

        public Criteria andDestanceIn(List values) {
            addCriterion("destance in", values, "destance");
            return this;
        }

        public Criteria andDestanceNotIn(List values) {
            addCriterion("destance not in", values, "destance");
            return this;
        }

        public Criteria andDestanceBetween(String value1, String value2) {
            addCriterion("destance between", value1, value2, "destance");
            return this;
        }

        public Criteria andDestanceNotBetween(String value1, String value2) {
            addCriterion("destance not between", value1, value2, "destance");
            return this;
        }

        public Criteria andCopperIsNull() {
            addCriterion("copper is null");
            return this;
        }

        public Criteria andCopperIsNotNull() {
            addCriterion("copper is not null");
            return this;
        }

        public Criteria andCopperEqualTo(Integer value) {
            addCriterion("copper =", value, "copper");
            return this;
        }

        public Criteria andCopperNotEqualTo(Integer value) {
            addCriterion("copper <>", value, "copper");
            return this;
        }

        public Criteria andCopperGreaterThan(Integer value) {
            addCriterion("copper >", value, "copper");
            return this;
        }

        public Criteria andCopperGreaterThanOrEqualTo(Integer value) {
            addCriterion("copper >=", value, "copper");
            return this;
        }

        public Criteria andCopperLessThan(Integer value) {
            addCriterion("copper <", value, "copper");
            return this;
        }

        public Criteria andCopperLessThanOrEqualTo(Integer value) {
            addCriterion("copper <=", value, "copper");
            return this;
        }

        public Criteria andCopperIn(List values) {
            addCriterion("copper in", values, "copper");
            return this;
        }

        public Criteria andCopperNotIn(List values) {
            addCriterion("copper not in", values, "copper");
            return this;
        }

        public Criteria andCopperBetween(Integer value1, Integer value2) {
            addCriterion("copper between", value1, value2, "copper");
            return this;
        }

        public Criteria andCopperNotBetween(Integer value1, Integer value2) {
            addCriterion("copper not between", value1, value2, "copper");
            return this;
        }

        public Criteria andMultipleIsNull() {
            addCriterion("multiple is null");
            return this;
        }

        public Criteria andMultipleIsNotNull() {
            addCriterion("multiple is not null");
            return this;
        }

        public Criteria andMultipleEqualTo(Integer value) {
            addCriterion("multiple =", value, "multiple");
            return this;
        }

        public Criteria andMultipleNotEqualTo(Integer value) {
            addCriterion("multiple <>", value, "multiple");
            return this;
        }

        public Criteria andMultipleGreaterThan(Integer value) {
            addCriterion("multiple >", value, "multiple");
            return this;
        }

        public Criteria andMultipleGreaterThanOrEqualTo(Integer value) {
            addCriterion("multiple >=", value, "multiple");
            return this;
        }

        public Criteria andMultipleLessThan(Integer value) {
            addCriterion("multiple <", value, "multiple");
            return this;
        }

        public Criteria andMultipleLessThanOrEqualTo(Integer value) {
            addCriterion("multiple <=", value, "multiple");
            return this;
        }

        public Criteria andMultipleIn(List values) {
            addCriterion("multiple in", values, "multiple");
            return this;
        }

        public Criteria andMultipleNotIn(List values) {
            addCriterion("multiple not in", values, "multiple");
            return this;
        }

        public Criteria andMultipleBetween(Integer value1, Integer value2) {
            addCriterion("multiple between", value1, value2, "multiple");
            return this;
        }

        public Criteria andMultipleNotBetween(Integer value1, Integer value2) {
            addCriterion("multiple not between", value1, value2, "multiple");
            return this;
        }

        public Criteria andLeftTimeIsNull() {
            addCriterion("left_time is null");
            return this;
        }

        public Criteria andLeftTimeIsNotNull() {
            addCriterion("left_time is not null");
            return this;
        }

        public Criteria andLeftTimeEqualTo(Integer value) {
            addCriterion("left_time =", value, "leftTime");
            return this;
        }

        public Criteria andLeftTimeNotEqualTo(Integer value) {
            addCriterion("left_time <>", value, "leftTime");
            return this;
        }

        public Criteria andLeftTimeGreaterThan(Integer value) {
            addCriterion("left_time >", value, "leftTime");
            return this;
        }

        public Criteria andLeftTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("left_time >=", value, "leftTime");
            return this;
        }

        public Criteria andLeftTimeLessThan(Integer value) {
            addCriterion("left_time <", value, "leftTime");
            return this;
        }

        public Criteria andLeftTimeLessThanOrEqualTo(Integer value) {
            addCriterion("left_time <=", value, "leftTime");
            return this;
        }

        public Criteria andLeftTimeIn(List values) {
            addCriterion("left_time in", values, "leftTime");
            return this;
        }

        public Criteria andLeftTimeNotIn(List values) {
            addCriterion("left_time not in", values, "leftTime");
            return this;
        }

        public Criteria andLeftTimeBetween(Integer value1, Integer value2) {
            addCriterion("left_time between", value1, value2, "leftTime");
            return this;
        }

        public Criteria andLeftTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("left_time not between", value1, value2, "leftTime");
            return this;
        }
    }
}