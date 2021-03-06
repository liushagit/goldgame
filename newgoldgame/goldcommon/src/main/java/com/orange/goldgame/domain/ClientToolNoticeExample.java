package com.orange.goldgame.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientToolNoticeExample {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table client_tool_notice
	 * @ibatorgenerated  Tue Jan 14 17:49:56 CST 2014
	 */
	protected String orderByClause;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table client_tool_notice
	 * @ibatorgenerated  Tue Jan 14 17:49:56 CST 2014
	 */
	protected List oredCriteria;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table client_tool_notice
	 * @ibatorgenerated  Tue Jan 14 17:49:56 CST 2014
	 */
	public ClientToolNoticeExample() {
		oredCriteria = new ArrayList();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table client_tool_notice
	 * @ibatorgenerated  Tue Jan 14 17:49:56 CST 2014
	 */
	protected ClientToolNoticeExample(ClientToolNoticeExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table client_tool_notice
	 * @ibatorgenerated  Tue Jan 14 17:49:56 CST 2014
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table client_tool_notice
	 * @ibatorgenerated  Tue Jan 14 17:49:56 CST 2014
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table client_tool_notice
	 * @ibatorgenerated  Tue Jan 14 17:49:56 CST 2014
	 */
	public List getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table client_tool_notice
	 * @ibatorgenerated  Tue Jan 14 17:49:56 CST 2014
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table client_tool_notice
	 * @ibatorgenerated  Tue Jan 14 17:49:56 CST 2014
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table client_tool_notice
	 * @ibatorgenerated  Tue Jan 14 17:49:56 CST 2014
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table client_tool_notice
	 * @ibatorgenerated  Tue Jan 14 17:49:56 CST 2014
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table client_tool_notice
	 * @ibatorgenerated  Tue Jan 14 17:49:56 CST 2014
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

		public Criteria andNoticeMessageIsNull() {
			addCriterion("notice_message is null");
			return this;
		}

		public Criteria andNoticeMessageIsNotNull() {
			addCriterion("notice_message is not null");
			return this;
		}

		public Criteria andNoticeMessageEqualTo(String value) {
			addCriterion("notice_message =", value, "noticeMessage");
			return this;
		}

		public Criteria andNoticeMessageNotEqualTo(String value) {
			addCriterion("notice_message <>", value, "noticeMessage");
			return this;
		}

		public Criteria andNoticeMessageGreaterThan(String value) {
			addCriterion("notice_message >", value, "noticeMessage");
			return this;
		}

		public Criteria andNoticeMessageGreaterThanOrEqualTo(String value) {
			addCriterion("notice_message >=", value, "noticeMessage");
			return this;
		}

		public Criteria andNoticeMessageLessThan(String value) {
			addCriterion("notice_message <", value, "noticeMessage");
			return this;
		}

		public Criteria andNoticeMessageLessThanOrEqualTo(String value) {
			addCriterion("notice_message <=", value, "noticeMessage");
			return this;
		}

		public Criteria andNoticeMessageLike(String value) {
			addCriterion("notice_message like", value, "noticeMessage");
			return this;
		}

		public Criteria andNoticeMessageNotLike(String value) {
			addCriterion("notice_message not like", value, "noticeMessage");
			return this;
		}

		public Criteria andNoticeMessageIn(List values) {
			addCriterion("notice_message in", values, "noticeMessage");
			return this;
		}

		public Criteria andNoticeMessageNotIn(List values) {
			addCriterion("notice_message not in", values, "noticeMessage");
			return this;
		}

		public Criteria andNoticeMessageBetween(String value1, String value2) {
			addCriterion("notice_message between", value1, value2,
					"noticeMessage");
			return this;
		}

		public Criteria andNoticeMessageNotBetween(String value1, String value2) {
			addCriterion("notice_message not between", value1, value2,
					"noticeMessage");
			return this;
		}

		public Criteria andNoticeConditionIsNull() {
			addCriterion("notice_condition is null");
			return this;
		}

		public Criteria andNoticeConditionIsNotNull() {
			addCriterion("notice_condition is not null");
			return this;
		}

		public Criteria andNoticeConditionEqualTo(String value) {
			addCriterion("notice_condition =", value, "noticeCondition");
			return this;
		}

		public Criteria andNoticeConditionNotEqualTo(String value) {
			addCriterion("notice_condition <>", value, "noticeCondition");
			return this;
		}

		public Criteria andNoticeConditionGreaterThan(String value) {
			addCriterion("notice_condition >", value, "noticeCondition");
			return this;
		}

		public Criteria andNoticeConditionGreaterThanOrEqualTo(String value) {
			addCriterion("notice_condition >=", value, "noticeCondition");
			return this;
		}

		public Criteria andNoticeConditionLessThan(String value) {
			addCriterion("notice_condition <", value, "noticeCondition");
			return this;
		}

		public Criteria andNoticeConditionLessThanOrEqualTo(String value) {
			addCriterion("notice_condition <=", value, "noticeCondition");
			return this;
		}

		public Criteria andNoticeConditionLike(String value) {
			addCriterion("notice_condition like", value, "noticeCondition");
			return this;
		}

		public Criteria andNoticeConditionNotLike(String value) {
			addCriterion("notice_condition not like", value, "noticeCondition");
			return this;
		}

		public Criteria andNoticeConditionIn(List values) {
			addCriterion("notice_condition in", values, "noticeCondition");
			return this;
		}

		public Criteria andNoticeConditionNotIn(List values) {
			addCriterion("notice_condition not in", values, "noticeCondition");
			return this;
		}

		public Criteria andNoticeConditionBetween(String value1, String value2) {
			addCriterion("notice_condition between", value1, value2,
					"noticeCondition");
			return this;
		}

		public Criteria andNoticeConditionNotBetween(String value1,
				String value2) {
			addCriterion("notice_condition not between", value1, value2,
					"noticeCondition");
			return this;
		}

		public Criteria andStartTimeIsNull() {
			addCriterion("start_time is null");
			return this;
		}

		public Criteria andStartTimeIsNotNull() {
			addCriterion("start_time is not null");
			return this;
		}

		public Criteria andStartTimeEqualTo(Date value) {
			addCriterion("start_time =", value, "startTime");
			return this;
		}

		public Criteria andStartTimeNotEqualTo(Date value) {
			addCriterion("start_time <>", value, "startTime");
			return this;
		}

		public Criteria andStartTimeGreaterThan(Date value) {
			addCriterion("start_time >", value, "startTime");
			return this;
		}

		public Criteria andStartTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("start_time >=", value, "startTime");
			return this;
		}

		public Criteria andStartTimeLessThan(Date value) {
			addCriterion("start_time <", value, "startTime");
			return this;
		}

		public Criteria andStartTimeLessThanOrEqualTo(Date value) {
			addCriterion("start_time <=", value, "startTime");
			return this;
		}

		public Criteria andStartTimeIn(List values) {
			addCriterion("start_time in", values, "startTime");
			return this;
		}

		public Criteria andStartTimeNotIn(List values) {
			addCriterion("start_time not in", values, "startTime");
			return this;
		}

		public Criteria andStartTimeBetween(Date value1, Date value2) {
			addCriterion("start_time between", value1, value2, "startTime");
			return this;
		}

		public Criteria andStartTimeNotBetween(Date value1, Date value2) {
			addCriterion("start_time not between", value1, value2, "startTime");
			return this;
		}

		public Criteria andEndTimeIsNull() {
			addCriterion("end_time is null");
			return this;
		}

		public Criteria andEndTimeIsNotNull() {
			addCriterion("end_time is not null");
			return this;
		}

		public Criteria andEndTimeEqualTo(Date value) {
			addCriterion("end_time =", value, "endTime");
			return this;
		}

		public Criteria andEndTimeNotEqualTo(Date value) {
			addCriterion("end_time <>", value, "endTime");
			return this;
		}

		public Criteria andEndTimeGreaterThan(Date value) {
			addCriterion("end_time >", value, "endTime");
			return this;
		}

		public Criteria andEndTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("end_time >=", value, "endTime");
			return this;
		}

		public Criteria andEndTimeLessThan(Date value) {
			addCriterion("end_time <", value, "endTime");
			return this;
		}

		public Criteria andEndTimeLessThanOrEqualTo(Date value) {
			addCriterion("end_time <=", value, "endTime");
			return this;
		}

		public Criteria andEndTimeIn(List values) {
			addCriterion("end_time in", values, "endTime");
			return this;
		}

		public Criteria andEndTimeNotIn(List values) {
			addCriterion("end_time not in", values, "endTime");
			return this;
		}

		public Criteria andEndTimeBetween(Date value1, Date value2) {
			addCriterion("end_time between", value1, value2, "endTime");
			return this;
		}

		public Criteria andEndTimeNotBetween(Date value1, Date value2) {
			addCriterion("end_time not between", value1, value2, "endTime");
			return this;
		}

		public Criteria andSendAccountIsNull() {
			addCriterion("send_account is null");
			return this;
		}

		public Criteria andSendAccountIsNotNull() {
			addCriterion("send_account is not null");
			return this;
		}

		public Criteria andSendAccountEqualTo(Integer value) {
			addCriterion("send_account =", value, "sendAccount");
			return this;
		}

		public Criteria andSendAccountNotEqualTo(Integer value) {
			addCriterion("send_account <>", value, "sendAccount");
			return this;
		}

		public Criteria andSendAccountGreaterThan(Integer value) {
			addCriterion("send_account >", value, "sendAccount");
			return this;
		}

		public Criteria andSendAccountGreaterThanOrEqualTo(Integer value) {
			addCriterion("send_account >=", value, "sendAccount");
			return this;
		}

		public Criteria andSendAccountLessThan(Integer value) {
			addCriterion("send_account <", value, "sendAccount");
			return this;
		}

		public Criteria andSendAccountLessThanOrEqualTo(Integer value) {
			addCriterion("send_account <=", value, "sendAccount");
			return this;
		}

		public Criteria andSendAccountIn(List values) {
			addCriterion("send_account in", values, "sendAccount");
			return this;
		}

		public Criteria andSendAccountNotIn(List values) {
			addCriterion("send_account not in", values, "sendAccount");
			return this;
		}

		public Criteria andSendAccountBetween(Integer value1, Integer value2) {
			addCriterion("send_account between", value1, value2, "sendAccount");
			return this;
		}

		public Criteria andSendAccountNotBetween(Integer value1, Integer value2) {
			addCriterion("send_account not between", value1, value2,
					"sendAccount");
			return this;
		}

		public Criteria andSendSpaceIsNull() {
			addCriterion("send_space is null");
			return this;
		}

		public Criteria andSendSpaceIsNotNull() {
			addCriterion("send_space is not null");
			return this;
		}

		public Criteria andSendSpaceEqualTo(String value) {
			addCriterion("send_space =", value, "sendSpace");
			return this;
		}

		public Criteria andSendSpaceNotEqualTo(String value) {
			addCriterion("send_space <>", value, "sendSpace");
			return this;
		}

		public Criteria andSendSpaceGreaterThan(String value) {
			addCriterion("send_space >", value, "sendSpace");
			return this;
		}

		public Criteria andSendSpaceGreaterThanOrEqualTo(String value) {
			addCriterion("send_space >=", value, "sendSpace");
			return this;
		}

		public Criteria andSendSpaceLessThan(String value) {
			addCriterion("send_space <", value, "sendSpace");
			return this;
		}

		public Criteria andSendSpaceLessThanOrEqualTo(String value) {
			addCriterion("send_space <=", value, "sendSpace");
			return this;
		}

		public Criteria andSendSpaceLike(String value) {
			addCriterion("send_space like", value, "sendSpace");
			return this;
		}

		public Criteria andSendSpaceNotLike(String value) {
			addCriterion("send_space not like", value, "sendSpace");
			return this;
		}

		public Criteria andSendSpaceIn(List values) {
			addCriterion("send_space in", values, "sendSpace");
			return this;
		}

		public Criteria andSendSpaceNotIn(List values) {
			addCriterion("send_space not in", values, "sendSpace");
			return this;
		}

		public Criteria andSendSpaceBetween(String value1, String value2) {
			addCriterion("send_space between", value1, value2, "sendSpace");
			return this;
		}

		public Criteria andSendSpaceNotBetween(String value1, String value2) {
			addCriterion("send_space not between", value1, value2, "sendSpace");
			return this;
		}

		public Criteria andSuccessSendIsNull() {
			addCriterion("success_send is null");
			return this;
		}

		public Criteria andSuccessSendIsNotNull() {
			addCriterion("success_send is not null");
			return this;
		}

		public Criteria andSuccessSendEqualTo(Integer value) {
			addCriterion("success_send =", value, "successSend");
			return this;
		}

		public Criteria andSuccessSendNotEqualTo(Integer value) {
			addCriterion("success_send <>", value, "successSend");
			return this;
		}

		public Criteria andSuccessSendGreaterThan(Integer value) {
			addCriterion("success_send >", value, "successSend");
			return this;
		}

		public Criteria andSuccessSendGreaterThanOrEqualTo(Integer value) {
			addCriterion("success_send >=", value, "successSend");
			return this;
		}

		public Criteria andSuccessSendLessThan(Integer value) {
			addCriterion("success_send <", value, "successSend");
			return this;
		}

		public Criteria andSuccessSendLessThanOrEqualTo(Integer value) {
			addCriterion("success_send <=", value, "successSend");
			return this;
		}

		public Criteria andSuccessSendIn(List values) {
			addCriterion("success_send in", values, "successSend");
			return this;
		}

		public Criteria andSuccessSendNotIn(List values) {
			addCriterion("success_send not in", values, "successSend");
			return this;
		}

		public Criteria andSuccessSendBetween(Integer value1, Integer value2) {
			addCriterion("success_send between", value1, value2, "successSend");
			return this;
		}

		public Criteria andSuccessSendNotBetween(Integer value1, Integer value2) {
			addCriterion("success_send not between", value1, value2,
					"successSend");
			return this;
		}

		public Criteria andStatusIsNull() {
			addCriterion("status is null");
			return this;
		}

		public Criteria andStatusIsNotNull() {
			addCriterion("status is not null");
			return this;
		}

		public Criteria andStatusEqualTo(Byte value) {
			addCriterion("status =", value, "status");
			return this;
		}

		public Criteria andStatusNotEqualTo(Byte value) {
			addCriterion("status <>", value, "status");
			return this;
		}

		public Criteria andStatusGreaterThan(Byte value) {
			addCriterion("status >", value, "status");
			return this;
		}

		public Criteria andStatusGreaterThanOrEqualTo(Byte value) {
			addCriterion("status >=", value, "status");
			return this;
		}

		public Criteria andStatusLessThan(Byte value) {
			addCriterion("status <", value, "status");
			return this;
		}

		public Criteria andStatusLessThanOrEqualTo(Byte value) {
			addCriterion("status <=", value, "status");
			return this;
		}

		public Criteria andStatusIn(List values) {
			addCriterion("status in", values, "status");
			return this;
		}

		public Criteria andStatusNotIn(List values) {
			addCriterion("status not in", values, "status");
			return this;
		}

		public Criteria andStatusBetween(Byte value1, Byte value2) {
			addCriterion("status between", value1, value2, "status");
			return this;
		}

		public Criteria andStatusNotBetween(Byte value1, Byte value2) {
			addCriterion("status not between", value1, value2, "status");
			return this;
		}
	}
}