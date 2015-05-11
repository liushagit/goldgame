package com.orange.core;

/**
 * 目标服务器
 * @author hqch
 *
 */
public class Domain {

	
	/**目标服务器 ID*/
	private String domainID;
	
	/**目标服务器名称*/
	private String domainName;
	
	/**目标服务器 IP*/
	private String serverIP;
	
	/**目标服务器 端口号*/
	private int serverPort;
	
	/**是否是默认连接*/
	private boolean isDefault;
	
	/**最大在线人数*/
	private int maxOnline;
	
	/**可切换跟踪*/
	private boolean isTrace;
	
	/**服务器类型*/
	private String serverType;
	
	public String getDomainID() {
		return domainID;
	}

	public void setDomainID(String domainID) {
		this.domainID = domainID;
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public int getMaxOnline() {
		return maxOnline;
	}

	public void setMaxOnline(int maxOnline) {
		this.maxOnline = maxOnline;
	}

	public boolean isTrace() {
		return isTrace;
	}

	public void setTrace(boolean isTrace) {
		this.isTrace = isTrace;
	}

	public String getServerType() {
		return serverType;
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	@Override
	public String toString() {
		return "Domain [domainID=" + domainID + ", domainName=" + domainName
				+ ", serverIP=" + serverIP + ", serverPort=" + serverPort
				+ ", isDefault=" + isDefault + ", maxOnline=" + maxOnline
				+ ", isTrace=" + isTrace + ", serverType=" + serverType + "]";
	}
	
}
