package com.orange.goldgame.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.juice.orange.game.database.ConnectionResource;
import com.juice.orange.game.database.IJuiceDBHandler;
import com.orange.goldgame.domain.CsvUtil;
import com.orange.goldgame.domain.ExchangeRecord;
import com.orange.goldgame.domain.Message;

public class MessageDAO extends ConnectionResource{
	
	private CsvUtil csvSystemMessageList;
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	public MessageDAO(){
//		String userDir = System.getProperty("user.dir");
//		try {
//			csvSystemMessageList=new CsvUtil(userDir + File.separator + "config"+File.separator+"messageList.csv"); 
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	 private static IJuiceDBHandler<Message> FRIENDS_MESSAGE_HAMDLER = new IJuiceDBHandler<Message>() {
	        @Override
	        public Message handler(ResultSet rs) throws SQLException {
	        	Message c = new Message();
	        	c.setMessage(rs.getString("message"));
	        	c.setIsCheck(rs.getByte("isCheck"));
	        	c.setSendTime(df.format(rs.getDate("time")));
	            return c;
	        }
	 };
	 
	 private static IJuiceDBHandler<ExchangeRecord> EXCHANGE_MESSAGE_HAMDLER = new IJuiceDBHandler<ExchangeRecord>() {
		 @Override
		 public ExchangeRecord handler(ResultSet rs) throws SQLException {
			 ExchangeRecord c = new ExchangeRecord();
			 /*c.setGoodsId(rs.getInt("goodsId"));
			 c.setExchaneTime(df.format(rs.getDate("exchangeTime")));
			 c.setIsExchane(rs.getInt("isExchange"));*/
			 return c;
		 }
	 };
	 
	 /** 查询玩家为查看的消息 */
	 public List<Message> getAllMessage(int playerId) {
		 String sql = "select message,isCheck,time" +" from MESSAGE where playerId=? AND isCheck=?";
		 return this.queryForList(sql, FRIENDS_MESSAGE_HAMDLER, playerId,(byte)0);
	 }
	 
	 /** 查询玩家兑换商品实物的消息 */
	 public List<ExchangeRecord> getExchangeMessage(int playerId) {
		 String sql = "select goodsId,exchangeTime,isExchange" +" from EXCHANGE_RECORD where playerId=?";
		 return this.queryForList(sql, EXCHANGE_MESSAGE_HAMDLER, playerId);
	 }
	
	/**获取系统信息*/
	public List<String> getSystemMessage() {
		List<String> msgList = new ArrayList<String>();
		for(int i=0;i<csvSystemMessageList.getRowNum();i++){
			for(int j=0;j<csvSystemMessageList.getColNum();j++){
				msgList.add(csvSystemMessageList.getString(i, j));
			}
		}
		return msgList;
	}
	
	/**获取好友消息*/
	public List<Message> getFriendsMessage(int playerId) {
		return getAllMessage(playerId);
	}
	/**获取兑换实物信息*/
	public List<ExchangeRecord> getGoodsExchangeMessage(int playerId) {
		return getExchangeMessage(playerId);
	}
	
	
}
