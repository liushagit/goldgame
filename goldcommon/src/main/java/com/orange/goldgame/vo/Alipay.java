package com.orange.goldgame.vo;

import java.util.Date;

public class Alipay {

	/**
	 * 交易状态
	 * TRADE_FINISHED：表示交易成功完成
	 * WAIT_BUYER_PAY：表示等待付款
	 */
	private String tradeStatus;
	
	/**
	 * 交易金额
	 */
	private float totalFree;
	/**
	 * 商品名称
	 */
	private String subject;
	/**
	 * 外部交易号（商户交易号）
	 */
	private String outTradeNo;
	
	/**
	 * 通知时间
	 */
	private Date notifyRegTime;
	
	/**
	 * 支付宝交易号
	 */
	private String tradeNo;

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public float getTotalFree() {
		return totalFree;
	}

	public void setTotalFree(float totalFree) {
		this.totalFree = totalFree;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public Date getNotifyRegTime() {
		return notifyRegTime;
	}

	public void setNotifyRegTime(Date notifyRegTime) {
		this.notifyRegTime = notifyRegTime;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
}
