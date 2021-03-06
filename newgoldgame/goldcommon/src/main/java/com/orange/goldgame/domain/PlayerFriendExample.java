package com.orange.goldgame.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerFriendExample {
    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database table player_friend
     *
     * @abatorgenerated Sat Mar 29 10:20:46 CST 2014
     */
    protected String orderByClause;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database table player_friend
     *
     * @abatorgenerated Sat Mar 29 10:20:46 CST 2014
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_friend
     *
     * @abatorgenerated Sat Mar 29 10:20:46 CST 2014
     */
    public PlayerFriendExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_friend
     *
     * @abatorgenerated Sat Mar 29 10:20:46 CST 2014
     */
    protected PlayerFriendExample(PlayerFriendExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_friend
     *
     * @abatorgenerated Sat Mar 29 10:20:46 CST 2014
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_friend
     *
     * @abatorgenerated Sat Mar 29 10:20:46 CST 2014
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_friend
     *
     * @abatorgenerated Sat Mar 29 10:20:46 CST 2014
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_friend
     *
     * @abatorgenerated Sat Mar 29 10:20:46 CST 2014
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_friend
     *
     * @abatorgenerated Sat Mar 29 10:20:46 CST 2014
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
     * This method corresponds to the database table player_friend
     *
     * @abatorgenerated Sat Mar 29 10:20:46 CST 2014
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table player_friend
     *
     * @abatorgenerated Sat Mar 29 10:20:46 CST 2014
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This class was generated by Abator for iBATIS.
     * This class corresponds to the database table player_friend
     *
     * @abatorgenerated Sat Mar 29 10:20:46 CST 2014
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

        public Criteria andFriendIdIsNull() {
            addCriterion("friend_id is null");
            return this;
        }

        public Criteria andFriendIdIsNotNull() {
            addCriterion("friend_id is not null");
            return this;
        }

        public Criteria andFriendIdEqualTo(Integer value) {
            addCriterion("friend_id =", value, "friendId");
            return this;
        }

        public Criteria andFriendIdNotEqualTo(Integer value) {
            addCriterion("friend_id <>", value, "friendId");
            return this;
        }

        public Criteria andFriendIdGreaterThan(Integer value) {
            addCriterion("friend_id >", value, "friendId");
            return this;
        }

        public Criteria andFriendIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("friend_id >=", value, "friendId");
            return this;
        }

        public Criteria andFriendIdLessThan(Integer value) {
            addCriterion("friend_id <", value, "friendId");
            return this;
        }

        public Criteria andFriendIdLessThanOrEqualTo(Integer value) {
            addCriterion("friend_id <=", value, "friendId");
            return this;
        }

        public Criteria andFriendIdIn(List<Integer> values) {
            addCriterion("friend_id in", values, "friendId");
            return this;
        }

        public Criteria andFriendIdNotIn(List<Integer> values) {
            addCriterion("friend_id not in", values, "friendId");
            return this;
        }

        public Criteria andFriendIdBetween(Integer value1, Integer value2) {
            addCriterion("friend_id between", value1, value2, "friendId");
            return this;
        }

        public Criteria andFriendIdNotBetween(Integer value1, Integer value2) {
            addCriterion("friend_id not between", value1, value2, "friendId");
            return this;
        }

        public Criteria andFriendNameIsNull() {
            addCriterion("friend_name is null");
            return this;
        }

        public Criteria andFriendNameIsNotNull() {
            addCriterion("friend_name is not null");
            return this;
        }

        public Criteria andFriendNameEqualTo(String value) {
            addCriterion("friend_name =", value, "friendName");
            return this;
        }

        public Criteria andFriendNameNotEqualTo(String value) {
            addCriterion("friend_name <>", value, "friendName");
            return this;
        }

        public Criteria andFriendNameGreaterThan(String value) {
            addCriterion("friend_name >", value, "friendName");
            return this;
        }

        public Criteria andFriendNameGreaterThanOrEqualTo(String value) {
            addCriterion("friend_name >=", value, "friendName");
            return this;
        }

        public Criteria andFriendNameLessThan(String value) {
            addCriterion("friend_name <", value, "friendName");
            return this;
        }

        public Criteria andFriendNameLessThanOrEqualTo(String value) {
            addCriterion("friend_name <=", value, "friendName");
            return this;
        }

        public Criteria andFriendNameLike(String value) {
            addCriterion("friend_name like", value, "friendName");
            return this;
        }

        public Criteria andFriendNameNotLike(String value) {
            addCriterion("friend_name not like", value, "friendName");
            return this;
        }

        public Criteria andFriendNameIn(List<String> values) {
            addCriterion("friend_name in", values, "friendName");
            return this;
        }

        public Criteria andFriendNameNotIn(List<String> values) {
            addCriterion("friend_name not in", values, "friendName");
            return this;
        }

        public Criteria andFriendNameBetween(String value1, String value2) {
            addCriterion("friend_name between", value1, value2, "friendName");
            return this;
        }

        public Criteria andFriendNameNotBetween(String value1, String value2) {
            addCriterion("friend_name not between", value1, value2, "friendName");
            return this;
        }

        public Criteria andFriendTypeIsNull() {
            addCriterion("friend_type is null");
            return this;
        }

        public Criteria andFriendTypeIsNotNull() {
            addCriterion("friend_type is not null");
            return this;
        }

        public Criteria andFriendTypeEqualTo(Integer value) {
            addCriterion("friend_type =", value, "friendType");
            return this;
        }

        public Criteria andFriendTypeNotEqualTo(Integer value) {
            addCriterion("friend_type <>", value, "friendType");
            return this;
        }

        public Criteria andFriendTypeGreaterThan(Integer value) {
            addCriterion("friend_type >", value, "friendType");
            return this;
        }

        public Criteria andFriendTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("friend_type >=", value, "friendType");
            return this;
        }

        public Criteria andFriendTypeLessThan(Integer value) {
            addCriterion("friend_type <", value, "friendType");
            return this;
        }

        public Criteria andFriendTypeLessThanOrEqualTo(Integer value) {
            addCriterion("friend_type <=", value, "friendType");
            return this;
        }

        public Criteria andFriendTypeIn(List<Integer> values) {
            addCriterion("friend_type in", values, "friendType");
            return this;
        }

        public Criteria andFriendTypeNotIn(List<Integer> values) {
            addCriterion("friend_type not in", values, "friendType");
            return this;
        }

        public Criteria andFriendTypeBetween(Integer value1, Integer value2) {
            addCriterion("friend_type between", value1, value2, "friendType");
            return this;
        }

        public Criteria andFriendTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("friend_type not between", value1, value2, "friendType");
            return this;
        }

        public Criteria andAddTimeIsNull() {
            addCriterion("add_time is null");
            return this;
        }

        public Criteria andAddTimeIsNotNull() {
            addCriterion("add_time is not null");
            return this;
        }

        public Criteria andAddTimeEqualTo(Date value) {
            addCriterion("add_time =", value, "addTime");
            return this;
        }

        public Criteria andAddTimeNotEqualTo(Date value) {
            addCriterion("add_time <>", value, "addTime");
            return this;
        }

        public Criteria andAddTimeGreaterThan(Date value) {
            addCriterion("add_time >", value, "addTime");
            return this;
        }

        public Criteria andAddTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("add_time >=", value, "addTime");
            return this;
        }

        public Criteria andAddTimeLessThan(Date value) {
            addCriterion("add_time <", value, "addTime");
            return this;
        }

        public Criteria andAddTimeLessThanOrEqualTo(Date value) {
            addCriterion("add_time <=", value, "addTime");
            return this;
        }

        public Criteria andAddTimeIn(List<Date> values) {
            addCriterion("add_time in", values, "addTime");
            return this;
        }

        public Criteria andAddTimeNotIn(List<Date> values) {
            addCriterion("add_time not in", values, "addTime");
            return this;
        }

        public Criteria andAddTimeBetween(Date value1, Date value2) {
            addCriterion("add_time between", value1, value2, "addTime");
            return this;
        }

        public Criteria andAddTimeNotBetween(Date value1, Date value2) {
            addCriterion("add_time not between", value1, value2, "addTime");
            return this;
        }

        public Criteria andSexIsNull() {
            addCriterion("sex is null");
            return this;
        }

        public Criteria andSexIsNotNull() {
            addCriterion("sex is not null");
            return this;
        }

        public Criteria andSexEqualTo(Boolean value) {
            addCriterion("sex =", value, "sex");
            return this;
        }

        public Criteria andSexNotEqualTo(Boolean value) {
            addCriterion("sex <>", value, "sex");
            return this;
        }

        public Criteria andSexGreaterThan(Boolean value) {
            addCriterion("sex >", value, "sex");
            return this;
        }

        public Criteria andSexGreaterThanOrEqualTo(Boolean value) {
            addCriterion("sex >=", value, "sex");
            return this;
        }

        public Criteria andSexLessThan(Boolean value) {
            addCriterion("sex <", value, "sex");
            return this;
        }

        public Criteria andSexLessThanOrEqualTo(Boolean value) {
            addCriterion("sex <=", value, "sex");
            return this;
        }

        public Criteria andSexIn(List<Boolean> values) {
            addCriterion("sex in", values, "sex");
            return this;
        }

        public Criteria andSexNotIn(List<Boolean> values) {
            addCriterion("sex not in", values, "sex");
            return this;
        }

        public Criteria andSexBetween(Boolean value1, Boolean value2) {
            addCriterion("sex between", value1, value2, "sex");
            return this;
        }

        public Criteria andSexNotBetween(Boolean value1, Boolean value2) {
            addCriterion("sex not between", value1, value2, "sex");
            return this;
        }

        public Criteria andLoverIntimacyIsNull() {
            addCriterion("lover_intimacy is null");
            return this;
        }

        public Criteria andLoverIntimacyIsNotNull() {
            addCriterion("lover_intimacy is not null");
            return this;
        }

        public Criteria andLoverIntimacyEqualTo(Integer value) {
            addCriterion("lover_intimacy =", value, "loverIntimacy");
            return this;
        }

        public Criteria andLoverIntimacyNotEqualTo(Integer value) {
            addCriterion("lover_intimacy <>", value, "loverIntimacy");
            return this;
        }

        public Criteria andLoverIntimacyGreaterThan(Integer value) {
            addCriterion("lover_intimacy >", value, "loverIntimacy");
            return this;
        }

        public Criteria andLoverIntimacyGreaterThanOrEqualTo(Integer value) {
            addCriterion("lover_intimacy >=", value, "loverIntimacy");
            return this;
        }

        public Criteria andLoverIntimacyLessThan(Integer value) {
            addCriterion("lover_intimacy <", value, "loverIntimacy");
            return this;
        }

        public Criteria andLoverIntimacyLessThanOrEqualTo(Integer value) {
            addCriterion("lover_intimacy <=", value, "loverIntimacy");
            return this;
        }

        public Criteria andLoverIntimacyIn(List<Integer> values) {
            addCriterion("lover_intimacy in", values, "loverIntimacy");
            return this;
        }

        public Criteria andLoverIntimacyNotIn(List<Integer> values) {
            addCriterion("lover_intimacy not in", values, "loverIntimacy");
            return this;
        }

        public Criteria andLoverIntimacyBetween(Integer value1, Integer value2) {
            addCriterion("lover_intimacy between", value1, value2, "loverIntimacy");
            return this;
        }

        public Criteria andLoverIntimacyNotBetween(Integer value1, Integer value2) {
            addCriterion("lover_intimacy not between", value1, value2, "loverIntimacy");
            return this;
        }

        public Criteria andFriendStatusIsNull() {
            addCriterion("friend_status is null");
            return this;
        }

        public Criteria andFriendStatusIsNotNull() {
            addCriterion("friend_status is not null");
            return this;
        }

        public Criteria andFriendStatusEqualTo(Integer value) {
            addCriterion("friend_status =", value, "friendStatus");
            return this;
        }

        public Criteria andFriendStatusNotEqualTo(Integer value) {
            addCriterion("friend_status <>", value, "friendStatus");
            return this;
        }

        public Criteria andFriendStatusGreaterThan(Integer value) {
            addCriterion("friend_status >", value, "friendStatus");
            return this;
        }

        public Criteria andFriendStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("friend_status >=", value, "friendStatus");
            return this;
        }

        public Criteria andFriendStatusLessThan(Integer value) {
            addCriterion("friend_status <", value, "friendStatus");
            return this;
        }

        public Criteria andFriendStatusLessThanOrEqualTo(Integer value) {
            addCriterion("friend_status <=", value, "friendStatus");
            return this;
        }

        public Criteria andFriendStatusIn(List<Integer> values) {
            addCriterion("friend_status in", values, "friendStatus");
            return this;
        }

        public Criteria andFriendStatusNotIn(List<Integer> values) {
            addCriterion("friend_status not in", values, "friendStatus");
            return this;
        }

        public Criteria andFriendStatusBetween(Integer value1, Integer value2) {
            addCriterion("friend_status between", value1, value2, "friendStatus");
            return this;
        }

        public Criteria andFriendStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("friend_status not between", value1, value2, "friendStatus");
            return this;
        }

        public Criteria andLoverGiftsIsNull() {
            addCriterion("lover_gifts is null");
            return this;
        }

        public Criteria andLoverGiftsIsNotNull() {
            addCriterion("lover_gifts is not null");
            return this;
        }

        public Criteria andLoverGiftsEqualTo(String value) {
            addCriterion("lover_gifts =", value, "loverGifts");
            return this;
        }

        public Criteria andLoverGiftsNotEqualTo(String value) {
            addCriterion("lover_gifts <>", value, "loverGifts");
            return this;
        }

        public Criteria andLoverGiftsGreaterThan(String value) {
            addCriterion("lover_gifts >", value, "loverGifts");
            return this;
        }

        public Criteria andLoverGiftsGreaterThanOrEqualTo(String value) {
            addCriterion("lover_gifts >=", value, "loverGifts");
            return this;
        }

        public Criteria andLoverGiftsLessThan(String value) {
            addCriterion("lover_gifts <", value, "loverGifts");
            return this;
        }

        public Criteria andLoverGiftsLessThanOrEqualTo(String value) {
            addCriterion("lover_gifts <=", value, "loverGifts");
            return this;
        }

        public Criteria andLoverGiftsLike(String value) {
            addCriterion("lover_gifts like", value, "loverGifts");
            return this;
        }

        public Criteria andLoverGiftsNotLike(String value) {
            addCriterion("lover_gifts not like", value, "loverGifts");
            return this;
        }

        public Criteria andLoverGiftsIn(List<String> values) {
            addCriterion("lover_gifts in", values, "loverGifts");
            return this;
        }

        public Criteria andLoverGiftsNotIn(List<String> values) {
            addCriterion("lover_gifts not in", values, "loverGifts");
            return this;
        }

        public Criteria andLoverGiftsBetween(String value1, String value2) {
            addCriterion("lover_gifts between", value1, value2, "loverGifts");
            return this;
        }

        public Criteria andLoverGiftsNotBetween(String value1, String value2) {
            addCriterion("lover_gifts not between", value1, value2, "loverGifts");
            return this;
        }
    }
}