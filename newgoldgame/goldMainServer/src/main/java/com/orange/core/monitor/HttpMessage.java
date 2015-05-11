package com.orange.core.monitor;

import java.util.HashMap;
import java.util.Map;

public class HttpMessage {

	private String url;
	
	private StringBuilder content;

	Map<String,Object> requestParams;
	
	public HttpMessage(String url) {
		this.url = url;
		this.content = new StringBuilder();
		this.requestParams = new HashMap<String, Object>();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public StringBuilder getContent() {
		return content;
	}

	public void setContent(StringBuilder content) {
		this.content = content;
	}

	public Map<String, Object> getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(Map<String, Object> requestParams) {
		this.requestParams = requestParams;
	}

	@Override
	public String toString() {
		return "HttpMessage [url=" + url + ", content=" + content + "]";
	}
}
