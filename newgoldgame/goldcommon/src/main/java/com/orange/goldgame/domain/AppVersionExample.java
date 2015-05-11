package com.orange.goldgame.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppVersionExample {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table app_version
	 * @ibatorgenerated  Mon Nov 04 17:01:22 CST 2013
	 */
	protected String orderByClause;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table app_version
	 * @ibatorgenerated  Mon Nov 04 17:01:22 CST 2013
	 */
	protected List oredCriteria;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table app_version
	 * @ibatorgenerated  Mon Nov 04 17:01:22 CST 2013
	 */
	public AppVersionExample() {
		oredCriteria = new ArrayList();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table app_version
	 * @ibatorgenerated  Mon Nov 04 17:01:22 CST 2013
	 */
	protected AppVersionExample(AppVersionExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table app_version
	 * @ibatorgenerated  Mon Nov 04 17:01:22 CST 2013
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table app_version
	 * @ibatorgenerated  Mon Nov 04 17:01:22 CST 2013
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table app_version
	 * @ibatorgenerated  Mon Nov 04 17:01:22 CST 2013
	 */
	public List getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table app_version
	 * @ibatorgenerated  Mon Nov 04 17:01:22 CST 2013
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table app_version
	 * @ibatorgenerated  Mon Nov 04 17:01:22 CST 2013
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table app_version
	 * @ibatorgenerated  Mon Nov 04 17:01:22 CST 2013
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table app_version
	 * @ibatorgenerated  Mon Nov 04 17:01:22 CST 2013
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table app_version
	 * @ibatorgenerated  Mon Nov 04 17:01:22 CST 2013
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

		public Criteria andAppIdIsNull() {
			addCriterion("app_id is null");
			return this;
		}

		public Criteria andAppIdIsNotNull() {
			addCriterion("app_id is not null");
			return this;
		}

		public Criteria andAppIdEqualTo(String value) {
			addCriterion("app_id =", value, "appId");
			return this;
		}

		public Criteria andAppIdNotEqualTo(String value) {
			addCriterion("app_id <>", value, "appId");
			return this;
		}

		public Criteria andAppIdGreaterThan(String value) {
			addCriterion("app_id >", value, "appId");
			return this;
		}

		public Criteria andAppIdGreaterThanOrEqualTo(String value) {
			addCriterion("app_id >=", value, "appId");
			return this;
		}

		public Criteria andAppIdLessThan(String value) {
			addCriterion("app_id <", value, "appId");
			return this;
		}

		public Criteria andAppIdLessThanOrEqualTo(String value) {
			addCriterion("app_id <=", value, "appId");
			return this;
		}

		public Criteria andAppIdLike(String value) {
			addCriterion("app_id like", value, "appId");
			return this;
		}

		public Criteria andAppIdNotLike(String value) {
			addCriterion("app_id not like", value, "appId");
			return this;
		}

		public Criteria andAppIdIn(List values) {
			addCriterion("app_id in", values, "appId");
			return this;
		}

		public Criteria andAppIdNotIn(List values) {
			addCriterion("app_id not in", values, "appId");
			return this;
		}

		public Criteria andAppIdBetween(String value1, String value2) {
			addCriterion("app_id between", value1, value2, "appId");
			return this;
		}

		public Criteria andAppIdNotBetween(String value1, String value2) {
			addCriterion("app_id not between", value1, value2, "appId");
			return this;
		}

		public Criteria andVersionIsNull() {
			addCriterion("version is null");
			return this;
		}

		public Criteria andVersionIsNotNull() {
			addCriterion("version is not null");
			return this;
		}

		public Criteria andVersionEqualTo(String value) {
			addCriterion("version =", value, "version");
			return this;
		}

		public Criteria andVersionNotEqualTo(String value) {
			addCriterion("version <>", value, "version");
			return this;
		}

		public Criteria andVersionGreaterThan(String value) {
			addCriterion("version >", value, "version");
			return this;
		}

		public Criteria andVersionGreaterThanOrEqualTo(String value) {
			addCriterion("version >=", value, "version");
			return this;
		}

		public Criteria andVersionLessThan(String value) {
			addCriterion("version <", value, "version");
			return this;
		}

		public Criteria andVersionLessThanOrEqualTo(String value) {
			addCriterion("version <=", value, "version");
			return this;
		}

		public Criteria andVersionLike(String value) {
			addCriterion("version like", value, "version");
			return this;
		}

		public Criteria andVersionNotLike(String value) {
			addCriterion("version not like", value, "version");
			return this;
		}

		public Criteria andVersionIn(List values) {
			addCriterion("version in", values, "version");
			return this;
		}

		public Criteria andVersionNotIn(List values) {
			addCriterion("version not in", values, "version");
			return this;
		}

		public Criteria andVersionBetween(String value1, String value2) {
			addCriterion("version between", value1, value2, "version");
			return this;
		}

		public Criteria andVersionNotBetween(String value1, String value2) {
			addCriterion("version not between", value1, value2, "version");
			return this;
		}

		public Criteria andUrlIsNull() {
			addCriterion("url is null");
			return this;
		}

		public Criteria andUrlIsNotNull() {
			addCriterion("url is not null");
			return this;
		}

		public Criteria andUrlEqualTo(String value) {
			addCriterion("url =", value, "url");
			return this;
		}

		public Criteria andUrlNotEqualTo(String value) {
			addCriterion("url <>", value, "url");
			return this;
		}

		public Criteria andUrlGreaterThan(String value) {
			addCriterion("url >", value, "url");
			return this;
		}

		public Criteria andUrlGreaterThanOrEqualTo(String value) {
			addCriterion("url >=", value, "url");
			return this;
		}

		public Criteria andUrlLessThan(String value) {
			addCriterion("url <", value, "url");
			return this;
		}

		public Criteria andUrlLessThanOrEqualTo(String value) {
			addCriterion("url <=", value, "url");
			return this;
		}

		public Criteria andUrlLike(String value) {
			addCriterion("url like", value, "url");
			return this;
		}

		public Criteria andUrlNotLike(String value) {
			addCriterion("url not like", value, "url");
			return this;
		}

		public Criteria andUrlIn(List values) {
			addCriterion("url in", values, "url");
			return this;
		}

		public Criteria andUrlNotIn(List values) {
			addCriterion("url not in", values, "url");
			return this;
		}

		public Criteria andUrlBetween(String value1, String value2) {
			addCriterion("url between", value1, value2, "url");
			return this;
		}

		public Criteria andUrlNotBetween(String value1, String value2) {
			addCriterion("url not between", value1, value2, "url");
			return this;
		}

		public Criteria andAppShortUrlIsNull() {
			addCriterion("app_short_url is null");
			return this;
		}

		public Criteria andAppShortUrlIsNotNull() {
			addCriterion("app_short_url is not null");
			return this;
		}

		public Criteria andAppShortUrlEqualTo(String value) {
			addCriterion("app_short_url =", value, "appShortUrl");
			return this;
		}

		public Criteria andAppShortUrlNotEqualTo(String value) {
			addCriterion("app_short_url <>", value, "appShortUrl");
			return this;
		}

		public Criteria andAppShortUrlGreaterThan(String value) {
			addCriterion("app_short_url >", value, "appShortUrl");
			return this;
		}

		public Criteria andAppShortUrlGreaterThanOrEqualTo(String value) {
			addCriterion("app_short_url >=", value, "appShortUrl");
			return this;
		}

		public Criteria andAppShortUrlLessThan(String value) {
			addCriterion("app_short_url <", value, "appShortUrl");
			return this;
		}

		public Criteria andAppShortUrlLessThanOrEqualTo(String value) {
			addCriterion("app_short_url <=", value, "appShortUrl");
			return this;
		}

		public Criteria andAppShortUrlLike(String value) {
			addCriterion("app_short_url like", value, "appShortUrl");
			return this;
		}

		public Criteria andAppShortUrlNotLike(String value) {
			addCriterion("app_short_url not like", value, "appShortUrl");
			return this;
		}

		public Criteria andAppShortUrlIn(List values) {
			addCriterion("app_short_url in", values, "appShortUrl");
			return this;
		}

		public Criteria andAppShortUrlNotIn(List values) {
			addCriterion("app_short_url not in", values, "appShortUrl");
			return this;
		}

		public Criteria andAppShortUrlBetween(String value1, String value2) {
			addCriterion("app_short_url between", value1, value2, "appShortUrl");
			return this;
		}

		public Criteria andAppShortUrlNotBetween(String value1, String value2) {
			addCriterion("app_short_url not between", value1, value2,
					"appShortUrl");
			return this;
		}

		public Criteria andAppNameIsNull() {
			addCriterion("app_name is null");
			return this;
		}

		public Criteria andAppNameIsNotNull() {
			addCriterion("app_name is not null");
			return this;
		}

		public Criteria andAppNameEqualTo(String value) {
			addCriterion("app_name =", value, "appName");
			return this;
		}

		public Criteria andAppNameNotEqualTo(String value) {
			addCriterion("app_name <>", value, "appName");
			return this;
		}

		public Criteria andAppNameGreaterThan(String value) {
			addCriterion("app_name >", value, "appName");
			return this;
		}

		public Criteria andAppNameGreaterThanOrEqualTo(String value) {
			addCriterion("app_name >=", value, "appName");
			return this;
		}

		public Criteria andAppNameLessThan(String value) {
			addCriterion("app_name <", value, "appName");
			return this;
		}

		public Criteria andAppNameLessThanOrEqualTo(String value) {
			addCriterion("app_name <=", value, "appName");
			return this;
		}

		public Criteria andAppNameLike(String value) {
			addCriterion("app_name like", value, "appName");
			return this;
		}

		public Criteria andAppNameNotLike(String value) {
			addCriterion("app_name not like", value, "appName");
			return this;
		}

		public Criteria andAppNameIn(List values) {
			addCriterion("app_name in", values, "appName");
			return this;
		}

		public Criteria andAppNameNotIn(List values) {
			addCriterion("app_name not in", values, "appName");
			return this;
		}

		public Criteria andAppNameBetween(String value1, String value2) {
			addCriterion("app_name between", value1, value2, "appName");
			return this;
		}

		public Criteria andAppNameNotBetween(String value1, String value2) {
			addCriterion("app_name not between", value1, value2, "appName");
			return this;
		}

		public Criteria andIconUrlIsNull() {
			addCriterion("icon_url is null");
			return this;
		}

		public Criteria andIconUrlIsNotNull() {
			addCriterion("icon_url is not null");
			return this;
		}

		public Criteria andIconUrlEqualTo(String value) {
			addCriterion("icon_url =", value, "iconUrl");
			return this;
		}

		public Criteria andIconUrlNotEqualTo(String value) {
			addCriterion("icon_url <>", value, "iconUrl");
			return this;
		}

		public Criteria andIconUrlGreaterThan(String value) {
			addCriterion("icon_url >", value, "iconUrl");
			return this;
		}

		public Criteria andIconUrlGreaterThanOrEqualTo(String value) {
			addCriterion("icon_url >=", value, "iconUrl");
			return this;
		}

		public Criteria andIconUrlLessThan(String value) {
			addCriterion("icon_url <", value, "iconUrl");
			return this;
		}

		public Criteria andIconUrlLessThanOrEqualTo(String value) {
			addCriterion("icon_url <=", value, "iconUrl");
			return this;
		}

		public Criteria andIconUrlLike(String value) {
			addCriterion("icon_url like", value, "iconUrl");
			return this;
		}

		public Criteria andIconUrlNotLike(String value) {
			addCriterion("icon_url not like", value, "iconUrl");
			return this;
		}

		public Criteria andIconUrlIn(List values) {
			addCriterion("icon_url in", values, "iconUrl");
			return this;
		}

		public Criteria andIconUrlNotIn(List values) {
			addCriterion("icon_url not in", values, "iconUrl");
			return this;
		}

		public Criteria andIconUrlBetween(String value1, String value2) {
			addCriterion("icon_url between", value1, value2, "iconUrl");
			return this;
		}

		public Criteria andIconUrlNotBetween(String value1, String value2) {
			addCriterion("icon_url not between", value1, value2, "iconUrl");
			return this;
		}
	}
}