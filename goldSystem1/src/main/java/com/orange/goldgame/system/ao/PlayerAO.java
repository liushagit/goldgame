package com.orange.goldgame.system.ao;


public class PlayerAO extends BaseAO {
//
//	/** 玩家登陆 */
//	public Player login(String account, String nickname, String password) {
//		if (getPlayerMsg(account, password).size() <= 0) {// 玩家第一次登陆给玩家注册信息
//			this.factory.getPlayerDAO().firstLogin(account, nickname, password);
//			// 第一次登陆，赠送金币40000
//			this.factory.getEstateDAO().insertEstate(
//					getPlayerMsg(account, password).get(0).getPlayerId(),
//					40000);
//			//第一次登陆，初始化任务列表
//			this.factory.getTaskDAO().insertTask(getPlayerMsg(account, password).get(0).getPlayerId());
//			//第一次登陆初始化礼品列表
//			this.factory.getGiftDAO().insertGift(getPlayerMsg(account, password).get(0).getPlayerId());
//			return getPlayerMsg(account,password).get(0);
//		} else {// 非第一次登陆，只做更新
//			 return this.factory.getPlayerDAO().login(account, nickname, password);
//		}
//		
//	}
//
//	/** 玩家请求更新个人信息 */
//	public void update(int playerId, String sex, int age, String nickname,
//			String tag, String connect) {
//		this.factory.getPlayerDAO().update(playerId, sex, age, nickname, tag,
//				connect);
//	}
//
//	/** 查询玩家信息 */
//	public List<Player> getPlayerMsg(int playerId){
//		return this.factory.getPlayerDAO().getPlayerMsg(playerId);
//	}
//	
//	/** 查询玩家信息 */
//	public List<Player> getPlayerMsg(String account,String password){
//		return this.factory.getPlayerDAO().getPlayerMsg(account, password);
//	}
}
