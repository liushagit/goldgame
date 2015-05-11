package com.orange.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.orange.glodconfig.util.Util;
import com.orange.glodconfig.util.XMLUtil;
import com.orange.log.LoggerFactory;


/**
 * 解析配置文件，获得目标服务器
 * @author hqch
 *
 */
public class DomainManager {

	private Logger logger = LoggerFactory.getLogger(DomainManager.class);
	
	private static DomainManager instance = new DomainManager();
	
	//key server标识 value server
	private Map<String, List<Domain>> domainMap;
	
	//key 协议号 value server标识
	private Map<String, String> protocolMap; 
	
	private static final String propFile = "protocol.properties";
	
	private static final String xmlFile = "global_config.xml";
	
	public static final String FIRST_CONN_SERVER = "other1";
	
	public static DomainManager getInstance() {
		return instance;
	}
	
	private DomainManager(){
		this.domainMap = Collections.synchronizedMap(new HashMap<String, List<Domain>>());
		this.protocolMap = Collections.synchronizedMap(new HashMap<String, String>());
	}
	
	/**
	 * 获取配置的所有目标服务器
	 * @return
	 */
	public List<Domain> getAllDomain(){
		List<Domain> retList = new ArrayList<Domain>();
		for(Entry<String,List<Domain>> entry : domainMap.entrySet()){
			retList.addAll(entry.getValue());
		}
		return retList;
	}
	
	/**
	 * 根据协议号获取对应的所有目标服务器
	 * @param protocol
	 * @return
	 */
	public List<Domain> getDomainMap4Protocol(String protocol){
		String serverFlag = protocolMap.get(protocol);
		if(serverFlag == null){
			logger.error("this " + protocol + " not have server flag.");
			return null;
		}
		
		List<Domain> retList = getDomainMap4Server(serverFlag);
		if(retList.size() == 0){
			logger.error("this " + protocol + " not have any domains.");
		}
		
		return retList;
	}
	
	/**
	 * 根据协议号获取对应的默认目标服务器
	 * @param protocol
	 * @return
	 */
	public Domain getDefualtDomainMap4Protocol(String protocol){
		String serverFlag = protocolMap.get(protocol);
		
		if(serverFlag == null){
			logger.error("this " + protocol + " not have server flag.");
			return null;
		}
		
		return getDefualtDomain4Server(serverFlag);
	}
	
	/**
	 * 根据服务器标识获取所有目标服务器
	 * @param serverFlag
	 * @return
	 */
	public List<Domain> getDomainMap4Server(String serverFlag){
		return domainMap.get(serverFlag);
	}
	
	/**
	 * 根据服务器标识获取默认目标服务器
	 * @param serverFlag
	 * @return
	 */
	public Domain getDefualtDomain4Server(String serverFlag){
		List<Domain> list = domainMap.get(serverFlag);
		if(list == null || list.size() == 0){
			logger.error(serverFlag + " not have any domains.");
		}
		for(Domain domain : list){
			if(domain.isDefault()){
				return domain;
			}
		}
		return list.get(0);
	}
	
	@SuppressWarnings("all")
	public void init(boolean reload){
		Map<String, List<Domain>> tmpDomainMap = new HashMap<String, List<Domain>>();
		Map<String, String> tmpProtocolMap = new HashMap<String, String>();
		
		Element element = null;
		try {
			element = XMLUtil.getRoot(xmlFile,reload);
		} catch (Exception e) {
			logger.error("DomainUtil init error :" , e);
			return;
		}
		
		Properties properties = Util.loadProperties(propFile);
		String str = null;
		String[] protocols = null;
		String serverFlag = null;
		for (Object obj : properties.keySet()) {
			str = String.valueOf(obj);
			serverFlag = properties.getProperty(str);
			protocols = str.split(",");
			for(String protocol : protocols){
				tmpProtocolMap.put(protocol, serverFlag);
			}
			
		}
		
		boolean isDefault = false;
		boolean isTrace = false;
		int maxOnline = 0;
		String value = null;
		List<Domain> list = null;
		List<Element> nodes = element.elements();
		String elementName = null;
		for (Element e : nodes) {
			Domain domain = new Domain();
			domain.setDomainID(e.attributeValue("id"));
			domain.setServerIP(e.attributeValue("host"));
			domain.setServerPort(Integer.parseInt(e.attributeValue("port")));

			value = e.attributeValue("name");
			if(value == null){
				domain.setDomainName(domain.getDomainID());
			} else {
				domain.setDomainName(value);
			}
			
			value = e.attributeValue("defualt");
			if(value == null){
				isDefault = false;
			} else {
				isDefault = Boolean.valueOf(value);
			}
			domain.setDefault(isDefault);
			
			value = e.attributeValue("maxOnline");
			if(value != null){
				maxOnline = Integer.valueOf(e.attributeValue("maxOnline"));
			} else {
				maxOnline = 0;
			}
			domain.setMaxOnline(maxOnline);
			
			value = e.attributeValue("isTrace");
			if(value == null){
				isTrace = false;
			} else {
				isTrace = Boolean.valueOf(value);
			}
			domain.setTrace(isTrace);
			
			elementName = e.getName();
			domain.setServerType(elementName);
			
			list = tmpDomainMap.get(elementName);
			if(list == null){
				list = new ArrayList<Domain>();
			}
			list.add(domain);
			tmpDomainMap.put(elementName, list);
		}
		
		this.domainMap = tmpDomainMap;
		this.protocolMap = tmpProtocolMap;
	}

	/**
	 * 根据目标服务器标识获取配置信息
	 * @param domainID
	 * @return
	 */
	public Domain getDomainByID(String domainID) {
		List<Domain> domainList = null;
		for(Entry<String,List<Domain>> entry : domainMap.entrySet()){
			domainList = entry.getValue();
			for(Domain domain : domainList){
				if(domain.getDomainID().equals(domainID)){
					return domain;
				}
			}
			
		}
		
		return null;
	}
}
