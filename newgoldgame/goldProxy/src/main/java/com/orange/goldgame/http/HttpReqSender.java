package com.orange.goldgame.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * http请求发送类
 * 
 * @author guojiang
 * 
 */
public class HttpReqSender {

	private static final Log logger = LogFactory.getLog(HttpReqSender.class);
	private static final int TIMEOUT = 3000;

	private HttpReqSender() {
	}

	private static HttpClient getClient(){
		HttpClient client = new HttpClient();
		client.getHttpConnectionManager().getParams().setConnectionTimeout(TIMEOUT);
	  client.getHttpConnectionManager().getParams().setSoTimeout(TIMEOUT);
		return client;
	}
	/**
	 * 发送HTTP Post请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String sendPostReq(String url, Map<String, String> params)
			throws HttpException {
		HttpClient httpClient = getClient();
		
		PostMethod postMethod = new PostMethod(url);
		NameValuePair[] pairs = null;
		try {
			pairs = builderParameters(params);
		} catch (UnsupportedEncodingException e1) {
			logger.error("Send HttpReq Error:", e1);
			throw new HttpException("Error encoding parameters.", e1);
		}
		postMethod.setRequestBody(pairs);

		String response = null;
		try {
			httpClient.executeMethod(postMethod);
			logger.debug("-- http response status:"
					+ postMethod.getStatusLine());

			InputStream in = postMethod.getResponseBodyAsStream();
			response = getResponse(in);
			logger.info("-- http response:" + response);
			in.close();
		} catch (Exception e) {
			logger.error("Caught an exception while sending http request.", e);
			throw new HttpException("Error Sending HttpReq.", e);
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
		}

		return response;
	}
	
	/**
	 * 发送HTTP Post请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String sendPostReq(String url,String params)
			throws HttpException {
		HttpClient httpClient = getClient();
		
		PostMethod postMethod = new PostMethod(url);
		NameValuePair[] pairs = null;
		try {
			pairs = builderParameters(params);
		} catch (UnsupportedEncodingException e1) {
			logger.error("Send HttpReq Error:", e1);
			throw new HttpException("Error encoding parameters.", e1);
		}
		postMethod.setRequestBody(pairs);
		String response = null;
		try {
			httpClient.executeMethod(postMethod);
			logger.debug("-- http response status:"
					+ postMethod.getStatusLine());

			InputStream in = postMethod.getResponseBodyAsStream();
			response = getResponse(in);
			logger.info("-- http response:" + response);
			in.close();
		} catch (Exception e) {
			logger.error("Caught an exception while sending http request.", e);
			throw new HttpException("Error Sending HttpReq.", e);
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
		}

		return response;
	}
	
	private static String getResponse(InputStream in) throws HttpException{
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new InputStreamReader(in));
			
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			throw new HttpException(e.getMessage());
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch(IOException e) {
				logger.error("Can not close stream.");
			}
				
		}
		
		return sb.toString();
	}

	public static String sendPostReq(String url) throws HttpException {
		HttpClient httpClient = getClient();

		logger.debug("url:" + url);
		PostMethod postMethod = new PostMethod(url);

		String response = null;
		try {
			httpClient.executeMethod(postMethod);
			logger.debug("-- http response status:"
					+ postMethod.getStatusLine());

			InputStream in = postMethod.getResponseBodyAsStream();
			response = getResponse(in);
			logger.info("-- http response:" + response);
			in.close();
		} catch (Exception e) {
			logger.error("Caught an exception while sending http request.", e);
			throw new HttpException("Error Sending HttpReq.", e);
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
		}

		return response;
	}

	/**
	 * 发送HTTP Post请求，不接收响应
	 * 
	 * @param url
	 * @param params
	 */
	public static void sendPostReqWithoutResp(String url,
			Map<String, String> params) throws HttpException {
		HttpClient httpClient = getClient();

		PostMethod postMethod = new PostMethod(url);
		NameValuePair[] pairs = null;
		try {
			pairs = builderParameters(params);
		} catch (UnsupportedEncodingException e1) {
			logger.error("Send HttpReq Error:", e1);
			throw new HttpException("Error encoding parameters.", e1);
		}
		postMethod.setRequestBody(pairs);

		try {
			httpClient.executeMethod(postMethod);

		} catch (Exception e) {
			logger.error("Caught an exception while sending http request.", e);
			throw new HttpException("Error Sending HttpReq.", e);
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
		}

	}

	private static NameValuePair[] builderParameters(Map<String, String> params)
			throws UnsupportedEncodingException {
		NameValuePair[] pairs = new NameValuePair[params.size()];

		int i = 0;
		for (Map.Entry<String, String> entry : params.entrySet()) {
//			pairs[i] = new NameValuePair(entry.getKey(), URLEncoder.encode(
//					entry.getValue(), "utf-8"));
			pairs[i] = new NameValuePair(entry.getKey(),
					entry.getValue());
			i++;
		}
		return pairs;
	}
	
	private static NameValuePair[] builderParameters(String params)
			throws UnsupportedEncodingException {
		NameValuePair[] pairs = new NameValuePair[1];
		
			pairs[0] = new NameValuePair("", URLEncoder.encode(
					params, "utf-8"));
		return pairs;
	}

	public static String sendGetReq(String url) {
		HttpClient httpClient = getClient();
		logger.info("--- To send http request. url:" + url);
		GetMethod getMethod = new GetMethod(url);

		String response = null;
		try {
			httpClient.executeMethod(getMethod);
			logger
					.debug("-- http response status:"
							+ getMethod.getStatusLine());

			InputStream in = getMethod.getResponseBodyAsStream();
			response = getResponse(in);
			logger.info("-- http response:" + response);
			in.close();
		} catch (Exception e) {
			logger.error("Caught an exception while sending http request.", e);
		} finally {
			if (getMethod != null) {
				getMethod.releaseConnection();
			}
		}

		return response;
	}

	public static void sendGetReqWithoutResp(String url) {
		HttpClient httpClient = getClient();

		GetMethod getMethod = new GetMethod(url);
		logger.info("--- To send http request. url:" + url);

		try {
			httpClient.executeMethod(getMethod);
			logger
					.debug("-- http response status:"
							+ getMethod.getStatusLine());

		} catch (Exception e) {
			logger.error("Caught an exception while sending http request.", e);
		} finally {
			if (getMethod != null) {
				getMethod.releaseConnection();
			}
		}
	}
	
	public static void main(String[] args) throws Exception{
		System.out.println(sendPostReq("http://portal.ucweb.com/dev3/zhangyy/upsw/trade/recharge/index.htm?card_pwd=1103&prod_nbr=1801&notify_url=http%3A%2F%2F221.181.72.49%3A8089%2Frc&card_no=zhouqian&order_id=9fae98618c534be68b44b655b0bc6564&sign=30b8f2fff914c511ff61c34f3bb0e55&pay_amt=100&wp_id=&rc_type=1&op_type=2&store_nbr=5088&user_id=1001052&card_amt=20&prod_name="));
		
		System.out.println(sendPostReq("http://portal.ucweb.com/dev3/zhangyy/upsw/trade/recharge/index.htm?card_pwd=1103&prod_nbr=1801&notify_url=http%3A%2F%2F221.181.72.49%3A8089%2Frc&card_no=zhouqian&order_id=9fae98618c534be68b44b655b0bc6564&sign=30b8f2fff914c511ff61c34f3bb0e55&pay_amt=100&rc_type=1&op_type=2&store_nbr=5088&user_id=1001052&card_amt=20"));
	}
}
