package com.orange.goldgame.domain;

public class NoticeMessage extends BaseObject{
    
    private static final long serialVersionUID = 1L;
    //妮名
    private String nickName;
    //消息体
    private String message;
    
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
