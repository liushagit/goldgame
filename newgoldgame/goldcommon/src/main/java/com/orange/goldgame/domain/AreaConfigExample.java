package com.orange.goldgame.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AreaConfigExample {
    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database table area_config
     *
     * @abatorgenerated Wed Apr 16 10:41:12 CST 2014
     */
    protected String orderByClause;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database table area_config
     *
     * @abatorgenerated Wed Apr 16 10:41:12 CST 2014
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table area_config
     *
     * @abatorgenerated Wed Apr 16 10:41:12 CST 2014
     */
    public AreaConfigExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table area_config
     *
     * @abatorgenerated Wed Apr 16 10:41:12 CST 2014
     */
    protected AreaConfigExample(AreaConfigExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table area_config
     *
     * @abatorgenerated Wed Apr 16 10:41:12 CST 2014
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table area_config
     *
     * @abatorgenerated Wed Apr 16 10:41:12 CST 2014
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table area_config
     *
     * @abatorgenerated Wed Apr 16 10:41:12 CST 2014
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table area_config
     *
     * @abatorgenerated Wed Apr 16 10:41:12 CST 2014
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table area_config
     *
     * @abatorgenerated Wed Apr 16 10:41:12 CST 2014
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
     * This method corresponds to the database table area_config
     *
     * @abatorgenerated Wed Apr 16 10:41:12 CST 2014
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table area_config
     *
     * @abatorgenerated Wed Apr 16 10:41:12 CST 2014
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This class was generated by Abator for iBATIS.
     * This class corresponds to the database table area_config
     *
     * @abatorgenerated Wed Apr 16 10:41:12 CST 2014
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
            addCriterion("Id is null");
            return this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("Id is not null");
            return this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("Id =", value, "id");
            return this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("Id <>", value, "id");
            return this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("Id >", value, "id");
            return this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("Id >=", value, "id");
            return this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("Id <", value, "id");
            return this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("Id <=", value, "id");
            return this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("Id in", values, "id");
            return this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("Id not in", values, "id");
            return this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("Id between", value1, value2, "id");
            return this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("Id not between", value1, value2, "id");
            return this;
        }

        public Criteria andAreaTypeIsNull() {
            addCriterion("area_Type is null");
            return this;
        }

        public Criteria andAreaTypeIsNotNull() {
            addCriterion("area_Type is not null");
            return this;
        }

        public Criteria andAreaTypeEqualTo(Short value) {
            addCriterion("area_Type =", value, "areaType");
            return this;
        }

        public Criteria andAreaTypeNotEqualTo(Short value) {
            addCriterion("area_Type <>", value, "areaType");
            return this;
        }

        public Criteria andAreaTypeGreaterThan(Short value) {
            addCriterion("area_Type >", value, "areaType");
            return this;
        }

        public Criteria andAreaTypeGreaterThanOrEqualTo(Short value) {
            addCriterion("area_Type >=", value, "areaType");
            return this;
        }

        public Criteria andAreaTypeLessThan(Short value) {
            addCriterion("area_Type <", value, "areaType");
            return this;
        }

        public Criteria andAreaTypeLessThanOrEqualTo(Short value) {
            addCriterion("area_Type <=", value, "areaType");
            return this;
        }

        public Criteria andAreaTypeIn(List<Short> values) {
            addCriterion("area_Type in", values, "areaType");
            return this;
        }

        public Criteria andAreaTypeNotIn(List<Short> values) {
            addCriterion("area_Type not in", values, "areaType");
            return this;
        }

        public Criteria andAreaTypeBetween(Short value1, Short value2) {
            addCriterion("area_Type between", value1, value2, "areaType");
            return this;
        }

        public Criteria andAreaTypeNotBetween(Short value1, Short value2) {
            addCriterion("area_Type not between", value1, value2, "areaType");
            return this;
        }

        public Criteria andAreaClassIsNull() {
            addCriterion("area_Class is null");
            return this;
        }

        public Criteria andAreaClassIsNotNull() {
            addCriterion("area_Class is not null");
            return this;
        }

        public Criteria andAreaClassEqualTo(Short value) {
            addCriterion("area_Class =", value, "areaClass");
            return this;
        }

        public Criteria andAreaClassNotEqualTo(Short value) {
            addCriterion("area_Class <>", value, "areaClass");
            return this;
        }

        public Criteria andAreaClassGreaterThan(Short value) {
            addCriterion("area_Class >", value, "areaClass");
            return this;
        }

        public Criteria andAreaClassGreaterThanOrEqualTo(Short value) {
            addCriterion("area_Class >=", value, "areaClass");
            return this;
        }

        public Criteria andAreaClassLessThan(Short value) {
            addCriterion("area_Class <", value, "areaClass");
            return this;
        }

        public Criteria andAreaClassLessThanOrEqualTo(Short value) {
            addCriterion("area_Class <=", value, "areaClass");
            return this;
        }

        public Criteria andAreaClassIn(List<Short> values) {
            addCriterion("area_Class in", values, "areaClass");
            return this;
        }

        public Criteria andAreaClassNotIn(List<Short> values) {
            addCriterion("area_Class not in", values, "areaClass");
            return this;
        }

        public Criteria andAreaClassBetween(Short value1, Short value2) {
            addCriterion("area_Class between", value1, value2, "areaClass");
            return this;
        }

        public Criteria andAreaClassNotBetween(Short value1, Short value2) {
            addCriterion("area_Class not between", value1, value2, "areaClass");
            return this;
        }

        public Criteria andTopLimitGoldsIsNull() {
            addCriterion("top_Limit_Golds is null");
            return this;
        }

        public Criteria andTopLimitGoldsIsNotNull() {
            addCriterion("top_Limit_Golds is not null");
            return this;
        }

        public Criteria andTopLimitGoldsEqualTo(Integer value) {
            addCriterion("top_Limit_Golds =", value, "topLimitGolds");
            return this;
        }

        public Criteria andTopLimitGoldsNotEqualTo(Integer value) {
            addCriterion("top_Limit_Golds <>", value, "topLimitGolds");
            return this;
        }

        public Criteria andTopLimitGoldsGreaterThan(Integer value) {
            addCriterion("top_Limit_Golds >", value, "topLimitGolds");
            return this;
        }

        public Criteria andTopLimitGoldsGreaterThanOrEqualTo(Integer value) {
            addCriterion("top_Limit_Golds >=", value, "topLimitGolds");
            return this;
        }

        public Criteria andTopLimitGoldsLessThan(Integer value) {
            addCriterion("top_Limit_Golds <", value, "topLimitGolds");
            return this;
        }

        public Criteria andTopLimitGoldsLessThanOrEqualTo(Integer value) {
            addCriterion("top_Limit_Golds <=", value, "topLimitGolds");
            return this;
        }

        public Criteria andTopLimitGoldsIn(List<Integer> values) {
            addCriterion("top_Limit_Golds in", values, "topLimitGolds");
            return this;
        }

        public Criteria andTopLimitGoldsNotIn(List<Integer> values) {
            addCriterion("top_Limit_Golds not in", values, "topLimitGolds");
            return this;
        }

        public Criteria andTopLimitGoldsBetween(Integer value1, Integer value2) {
            addCriterion("top_Limit_Golds between", value1, value2, "topLimitGolds");
            return this;
        }

        public Criteria andTopLimitGoldsNotBetween(Integer value1, Integer value2) {
            addCriterion("top_Limit_Golds not between", value1, value2, "topLimitGolds");
            return this;
        }

        public Criteria andLimitGoldsIsNull() {
            addCriterion("limit_Golds is null");
            return this;
        }

        public Criteria andLimitGoldsIsNotNull() {
            addCriterion("limit_Golds is not null");
            return this;
        }

        public Criteria andLimitGoldsEqualTo(Integer value) {
            addCriterion("limit_Golds =", value, "limitGolds");
            return this;
        }

        public Criteria andLimitGoldsNotEqualTo(Integer value) {
            addCriterion("limit_Golds <>", value, "limitGolds");
            return this;
        }

        public Criteria andLimitGoldsGreaterThan(Integer value) {
            addCriterion("limit_Golds >", value, "limitGolds");
            return this;
        }

        public Criteria andLimitGoldsGreaterThanOrEqualTo(Integer value) {
            addCriterion("limit_Golds >=", value, "limitGolds");
            return this;
        }

        public Criteria andLimitGoldsLessThan(Integer value) {
            addCriterion("limit_Golds <", value, "limitGolds");
            return this;
        }

        public Criteria andLimitGoldsLessThanOrEqualTo(Integer value) {
            addCriterion("limit_Golds <=", value, "limitGolds");
            return this;
        }

        public Criteria andLimitGoldsIn(List<Integer> values) {
            addCriterion("limit_Golds in", values, "limitGolds");
            return this;
        }

        public Criteria andLimitGoldsNotIn(List<Integer> values) {
            addCriterion("limit_Golds not in", values, "limitGolds");
            return this;
        }

        public Criteria andLimitGoldsBetween(Integer value1, Integer value2) {
            addCriterion("limit_Golds between", value1, value2, "limitGolds");
            return this;
        }

        public Criteria andLimitGoldsNotBetween(Integer value1, Integer value2) {
            addCriterion("limit_Golds not between", value1, value2, "limitGolds");
            return this;
        }

        public Criteria andSingleGoldsIsNull() {
            addCriterion("single_Golds is null");
            return this;
        }

        public Criteria andSingleGoldsIsNotNull() {
            addCriterion("single_Golds is not null");
            return this;
        }

        public Criteria andSingleGoldsEqualTo(Integer value) {
            addCriterion("single_Golds =", value, "singleGolds");
            return this;
        }

        public Criteria andSingleGoldsNotEqualTo(Integer value) {
            addCriterion("single_Golds <>", value, "singleGolds");
            return this;
        }

        public Criteria andSingleGoldsGreaterThan(Integer value) {
            addCriterion("single_Golds >", value, "singleGolds");
            return this;
        }

        public Criteria andSingleGoldsGreaterThanOrEqualTo(Integer value) {
            addCriterion("single_Golds >=", value, "singleGolds");
            return this;
        }

        public Criteria andSingleGoldsLessThan(Integer value) {
            addCriterion("single_Golds <", value, "singleGolds");
            return this;
        }

        public Criteria andSingleGoldsLessThanOrEqualTo(Integer value) {
            addCriterion("single_Golds <=", value, "singleGolds");
            return this;
        }

        public Criteria andSingleGoldsIn(List<Integer> values) {
            addCriterion("single_Golds in", values, "singleGolds");
            return this;
        }

        public Criteria andSingleGoldsNotIn(List<Integer> values) {
            addCriterion("single_Golds not in", values, "singleGolds");
            return this;
        }

        public Criteria andSingleGoldsBetween(Integer value1, Integer value2) {
            addCriterion("single_Golds between", value1, value2, "singleGolds");
            return this;
        }

        public Criteria andSingleGoldsNotBetween(Integer value1, Integer value2) {
            addCriterion("single_Golds not between", value1, value2, "singleGolds");
            return this;
        }

        public Criteria andAwordIsNull() {
            addCriterion("aword is null");
            return this;
        }

        public Criteria andAwordIsNotNull() {
            addCriterion("aword is not null");
            return this;
        }

        public Criteria andAwordEqualTo(String value) {
            addCriterion("aword =", value, "aword");
            return this;
        }

        public Criteria andAwordNotEqualTo(String value) {
            addCriterion("aword <>", value, "aword");
            return this;
        }

        public Criteria andAwordGreaterThan(String value) {
            addCriterion("aword >", value, "aword");
            return this;
        }

        public Criteria andAwordGreaterThanOrEqualTo(String value) {
            addCriterion("aword >=", value, "aword");
            return this;
        }

        public Criteria andAwordLessThan(String value) {
            addCriterion("aword <", value, "aword");
            return this;
        }

        public Criteria andAwordLessThanOrEqualTo(String value) {
            addCriterion("aword <=", value, "aword");
            return this;
        }

        public Criteria andAwordLike(String value) {
            addCriterion("aword like", value, "aword");
            return this;
        }

        public Criteria andAwordNotLike(String value) {
            addCriterion("aword not like", value, "aword");
            return this;
        }

        public Criteria andAwordIn(List<String> values) {
            addCriterion("aword in", values, "aword");
            return this;
        }

        public Criteria andAwordNotIn(List<String> values) {
            addCriterion("aword not in", values, "aword");
            return this;
        }

        public Criteria andAwordBetween(String value1, String value2) {
            addCriterion("aword between", value1, value2, "aword");
            return this;
        }

        public Criteria andAwordNotBetween(String value1, String value2) {
            addCriterion("aword not between", value1, value2, "aword");
            return this;
        }

        public Criteria andIntrodutionIsNull() {
            addCriterion("introdution is null");
            return this;
        }

        public Criteria andIntrodutionIsNotNull() {
            addCriterion("introdution is not null");
            return this;
        }

        public Criteria andIntrodutionEqualTo(String value) {
            addCriterion("introdution =", value, "introdution");
            return this;
        }

        public Criteria andIntrodutionNotEqualTo(String value) {
            addCriterion("introdution <>", value, "introdution");
            return this;
        }

        public Criteria andIntrodutionGreaterThan(String value) {
            addCriterion("introdution >", value, "introdution");
            return this;
        }

        public Criteria andIntrodutionGreaterThanOrEqualTo(String value) {
            addCriterion("introdution >=", value, "introdution");
            return this;
        }

        public Criteria andIntrodutionLessThan(String value) {
            addCriterion("introdution <", value, "introdution");
            return this;
        }

        public Criteria andIntrodutionLessThanOrEqualTo(String value) {
            addCriterion("introdution <=", value, "introdution");
            return this;
        }

        public Criteria andIntrodutionLike(String value) {
            addCriterion("introdution like", value, "introdution");
            return this;
        }

        public Criteria andIntrodutionNotLike(String value) {
            addCriterion("introdution not like", value, "introdution");
            return this;
        }

        public Criteria andIntrodutionIn(List<String> values) {
            addCriterion("introdution in", values, "introdution");
            return this;
        }

        public Criteria andIntrodutionNotIn(List<String> values) {
            addCriterion("introdution not in", values, "introdution");
            return this;
        }

        public Criteria andIntrodutionBetween(String value1, String value2) {
            addCriterion("introdution between", value1, value2, "introdution");
            return this;
        }

        public Criteria andIntrodutionNotBetween(String value1, String value2) {
            addCriterion("introdution not between", value1, value2, "introdution");
            return this;
        }

        public Criteria andMaxNumIsNull() {
            addCriterion("max_num is null");
            return this;
        }

        public Criteria andMaxNumIsNotNull() {
            addCriterion("max_num is not null");
            return this;
        }

        public Criteria andMaxNumEqualTo(Integer value) {
            addCriterion("max_num =", value, "maxNum");
            return this;
        }

        public Criteria andMaxNumNotEqualTo(Integer value) {
            addCriterion("max_num <>", value, "maxNum");
            return this;
        }

        public Criteria andMaxNumGreaterThan(Integer value) {
            addCriterion("max_num >", value, "maxNum");
            return this;
        }

        public Criteria andMaxNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("max_num >=", value, "maxNum");
            return this;
        }

        public Criteria andMaxNumLessThan(Integer value) {
            addCriterion("max_num <", value, "maxNum");
            return this;
        }

        public Criteria andMaxNumLessThanOrEqualTo(Integer value) {
            addCriterion("max_num <=", value, "maxNum");
            return this;
        }

        public Criteria andMaxNumIn(List<Integer> values) {
            addCriterion("max_num in", values, "maxNum");
            return this;
        }

        public Criteria andMaxNumNotIn(List<Integer> values) {
            addCriterion("max_num not in", values, "maxNum");
            return this;
        }

        public Criteria andMaxNumBetween(Integer value1, Integer value2) {
            addCriterion("max_num between", value1, value2, "maxNum");
            return this;
        }

        public Criteria andMaxNumNotBetween(Integer value1, Integer value2) {
            addCriterion("max_num not between", value1, value2, "maxNum");
            return this;
        }
    }
}