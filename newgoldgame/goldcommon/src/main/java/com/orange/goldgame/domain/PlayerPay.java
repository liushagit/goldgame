package com.orange.goldgame.domain;

import java.util.Date;

public class PlayerPay extends BaseObject {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column player_pay.id
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	private Integer id;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column player_pay.player_id
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	private Integer playerId;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column player_pay.pay_time
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	private Date payTime;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column player_pay.gold
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	private Integer gold;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column player_pay.order_id
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	private String orderId;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column player_pay.pay_type
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	private String payType;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column player_pay.pay_info_id
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	private String payInfoId;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column player_pay.trade_id
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	private String tradeId;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column player_pay.buy_id
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	private String buyId;

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column player_pay.id
	 * @return  the value of player_pay.id
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column player_pay.id
	 * @param id  the value for player_pay.id
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column player_pay.player_id
	 * @return  the value of player_pay.player_id
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	public Integer getPlayerId() {
		return playerId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column player_pay.player_id
	 * @param playerId  the value for player_pay.player_id
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column player_pay.pay_time
	 * @return  the value of player_pay.pay_time
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	public Date getPayTime() {
		return payTime;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column player_pay.pay_time
	 * @param payTime  the value for player_pay.pay_time
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column player_pay.gold
	 * @return  the value of player_pay.gold
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	public Integer getGold() {
		return gold;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column player_pay.gold
	 * @param gold  the value for player_pay.gold
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	public void setGold(Integer gold) {
		this.gold = gold;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column player_pay.order_id
	 * @return  the value of player_pay.order_id
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column player_pay.order_id
	 * @param orderId  the value for player_pay.order_id
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column player_pay.pay_type
	 * @return  the value of player_pay.pay_type
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	public String getPayType() {
		return payType;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column player_pay.pay_type
	 * @param payType  the value for player_pay.pay_type
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	public void setPayType(String payType) {
		this.payType = payType;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column player_pay.pay_info_id
	 * @return  the value of player_pay.pay_info_id
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	public String getPayInfoId() {
		return payInfoId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column player_pay.pay_info_id
	 * @param payInfoId  the value for player_pay.pay_info_id
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	public void setPayInfoId(String payInfoId) {
		this.payInfoId = payInfoId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column player_pay.trade_id
	 * @return  the value of player_pay.trade_id
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	public String getTradeId() {
		return tradeId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column player_pay.trade_id
	 * @param tradeId  the value for player_pay.trade_id
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column player_pay.buy_id
	 * @return  the value of player_pay.buy_id
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	public String getBuyId() {
		return buyId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column player_pay.buy_id
	 * @param buyId  the value for player_pay.buy_id
	 * @ibatorgenerated  Thu Oct 31 17:18:43 CST 2013
	 */
	public void setBuyId(String buyId) {
		this.buyId = buyId;
	}
}