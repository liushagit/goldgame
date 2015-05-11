package com.orange.goldgame.server.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.action.AppConfigAction;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.AppConfig;
import com.orange.goldgame.domain.NoticeConfig;
import com.orange.goldgame.domain.NoticeMessage;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.exception.GoldException;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.server.service.GoldService;


/**
 * 公告管理器
 * @author yesheng
 *
 */
public class NoticeManager {
    
    private static final NoticeManager instance = new NoticeManager();
    
    /**
     * 公告存放列表
     */
    private List<NoticeMessage> noticeList = new ArrayList<NoticeMessage>();

    private Player systemPlayer = null;
    /**
     * 最新添加时间
     */
    private Long lastModifyTime = new Date().getTime();
    
    private static Timer timer = new Timer();  
    
    private AppConfigAction appConfigAction = BaseEngine.getInstace().getAppConfigActionImpl();
    private List<NoticeConfig> systemNoticeList = new ArrayList<NoticeConfig>();
    /**
     * 当前系统公告坐标
     */
    private int currentSystemNotice = 0;
    
    /**
     * 取实例的唯一方式
     * @return
     */
    public static NoticeManager getInstance(){
        return instance;
    }
    
    private Map<Integer, Integer> noitceLimitMap = new HashMap<Integer, Integer>();
    
    public Map<Integer, Integer> getNoitceLimitMap() {
		return noitceLimitMap;
	}

	private NoticeManager(){
        for(NoticeConfig notice : ResourceCache.getInstance().getNoticeConfigs().values()){
            systemNoticeList.add(notice);
        }
        systemPlayer = new Player();
        systemPlayer.setCopper(0);
        systemPlayer.setId(-1);
        systemPlayer.setNickname("系统公告");
        
        noitceLimitMap.put(1, 60000);
        noitceLimitMap.put(2, 250000);
        noitceLimitMap.put(3, 500000);
    }
    
    /**
     * 开始任务
     * 最后操作时间相隔15秒，就添加系统公告
     */
    public void startTask(){
        String refleshTimeStr = appConfigAction.
                findAppConfigByKey(Constants.NOTICE_SYSTEM_REFLESH).getAppValue();
        final int refleshTime = Integer.parseInt(refleshTimeStr);
        GregorianCalendar calendar = new GregorianCalendar();
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    
                    if(System.currentTimeMillis() - lastModifyTime > refleshTime * 1000){
//                        addSystemNotice();
                    }
                    
                } catch (Exception e) {
                }
            }
        }, calendar.getTime(), 100);
    }
    
    /**
     * 加入系统公告
     */
    public void addSystemNotice(){
        
//        if(currentSystemNotice >= ResourceCache.getInstance().getNoticeConfigs().size()){
//            currentSystemNotice = 0;
//        }
//        NoticeConfig config = systemNoticeList.get(currentSystemNotice);
//        //当前公告要继续加
//        currentSystemNotice++;
//        
//        NoticeMessage message = new NoticeMessage();
//        message.setMessage(config.getDestance());
//        message.setNickName(config.getName());
//        
//        //加入公告栈
//        pushMessage(systemPlayer,message);
        
        
    }
    
    public void sendPublicMessage(Player player,String msg){
    	if (player == null) {
			player = systemPlayer;
		}
    	NoticeMessage message = new NoticeMessage();
    	if (msg.length() > Constants.MAX_MSG_LENGTH) {
			msg = msg.substring(0, Constants.MAX_MSG_LENGTH);
		}
        message.setMessage(msg);
        message.setNickName(player.getNickname());
        pushMessage(player, message);
    }
    
    /**
     * 加入公告栈
     */
    private void pushMessage(Player player,NoticeMessage message){
        //记录操作时间
        lastModifyTime = System.currentTimeMillis();
        if(noticeList.size() > 100){
            noticeList.remove(0);
        }
        noticeList.add(message);
        
        broadcastAllPlayer(player,message);
    }
    
    /**
     * 将信息广播给所有玩家
     */
    private void broadcastAllPlayer(Player player ,NoticeMessage message){
        OutputMessage om = new OutputMessage();
        om.putInt(player.getId());
        om.putString(message.getNickName());
        om.putString(message.getMessage());
        om.putInt(GoldService.getLeftCopper(player));
        
        Set<Integer> keySet = SessionManger.getInstance().getGameSessions().keySet();
        for(int key : keySet){
        	if(key==player.getId()){
        		SessionManger.getInstance().getSession(key).sendMessage(Protocol.RESPONSE_SYSTEM_MESSAGE, om);
        	}
        }
    }
    
    /**
     * 获取公告
     * @return
     */
    public NoticeMessage getNotice(){
        return noticeList.get(noticeList.size()-1);
    }
    
    /**
     * 加入用户公告
     */
    public void sendPlayerMessage(Player player ,NoticeMessage message) throws GoldException{
        //检查message，长度不能大于20
        AppConfig messageConfig = appConfigAction.findAppConfigByKey(Constants.NOTICE_LENGTH);
        int messageLength = Integer.parseInt(messageConfig.getAppValue());
        String str ="";
        if(message.getMessage().length()>messageLength){
            str = message.getMessage().substring(0, messageLength);
            message.setMessage(str);
        }
        
        //扣金币
        AppConfig config = appConfigAction.findAppConfigByKey(Constants.NOTICE_PAY);
        int needGold = Integer.parseInt(config.getAppValue());
        if(player.getCopper() < needGold){
            throw new GoldException("金币不足！");
        }
        GoldService.consumeCopperAndUpdateGamer(player, needGold);
        pushMessage(player,message);
    }
    
    /**
     * 获得最新的公告，数量10条
     * @return
     */
    public List<NoticeMessage> getLatestNotices(){
        return noticeList.subList(noticeList.size()-1-10, noticeList.size()-1);
    }
    
    /**
     * 返回发送公告所需要的金币
     * @return
     */
    public int getNoticeNeedGolds(){
        AppConfig config = appConfigAction.findAppConfigByKey(Constants.NOTICE_PAY);
        int needGold = Integer.parseInt(config.getAppValue());
        return needGold;
    }
}
