package com.orange.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.orange.core.proxy.ChatMessage;

/**
 * 聊天管理器
 * @author hqch
 *
 */
public class ChatManager {

	private static ChatManager instance = new ChatManager();
	
	/**当前拥有的系统信息*/
	private List<ChatMessage> sysMessageList;
	
	public static ChatManager getInstance() {
		return instance;
	}
	
	private ChatManager(){
		this.sysMessageList = Collections.synchronizedList(new ArrayList<ChatMessage>());
	}
	
	/**
	 * 刷新系统消息列表
	 * @param sysMsgs
	 */
	public void refreshMessage(String[] sysMsgs){
		this.sysMessageList.clear();
		
		for(String msg : sysMsgs){
			this.sysMessageList.add(new ChatMessage(msg));
		}
	}
	
	/**
	 * 随机系统消息
	 * @return
	 */
	public ChatMessage randomSystemMessage(){
		if(this.sysMessageList.size() == 0){
			return null;
		}
		int index = (int)(Math.random() * this.sysMessageList.size());
		return this.sysMessageList.get(index);
	}
}
