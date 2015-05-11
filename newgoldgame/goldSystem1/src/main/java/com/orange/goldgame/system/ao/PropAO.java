package com.orange.goldgame.system.ao;


public class PropAO extends BaseAO {
//
//	private CsvUtil csvVoucher;
//	private CsvUtil csvPropList;
//	public PropAO(){
//		Properties properties = new Properties();
//		String userDir = System.getProperty("user.dir");
//		try {
//			csvVoucher = new CsvUtil(userDir + File.separator + "config"+File.separator+"voucher.csv"); 
//			csvPropList=new CsvUtil(userDir + File.separator + "config"+File.separator+"propList.csv"); 
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	/** 玩家请求领取赠送金币 */
//	public int getGiveGolds(int playerId, int days) {
//		int golds = 0;
//		if (this.getPlayerAO().getPlayerMsg(playerId).get(0).getDays() != days) {//天数不同表示作弊
//			return -1;
//		} else if (this.getPlayerAO().getPlayerMsg(playerId).get(0)
//				.getIsGetAward() == 1) {//已领取过了，不能再领取
//			return -1;
//		} else {
//			switch (days) {
//			case 1:
//				golds = 500;
//				break;
//			case 2:
//				golds = 1000;
//				break;
//			case 3:
//				golds = 1500;
//				break;
//			case 4:
//				golds = 2000;
//				break;
//			case 5:
//				golds = 2500;
//				break;
//			default:
//				golds = 2500;
//				break;
//			}
//			//玩家领取金币，更新标志
//			getPlayerAO().getPlayerDAO().getGiveGolds(playerId);
//			// 日志记录
//			getLogAO().getLogDAO().addLog(playerId, "登陆后领取赠送金币", new Date());
//			return updateGolds(playerId, golds);
//			
//		}
//	}
//
//	/** 玩家充值，更新玩家所拥有的宝石 */
//	public int getGemstone(int playerId, int num) {
//		int exchangeVoucher=num/6;
//		// 日志记录
//		getLogAO().add(playerId, "玩家成功购买了"+num+"颗宝石", new Date());
//		int allGemstone=this.factory.getEstateDAO().getGemstone(playerId, num);
//		this.getExchangeVoucher(playerId, exchangeVoucher);
//		return allGemstone;
//	}
//
//	/** 玩家完成任务，点击领取金币时更新金币 */
//	public int getTaskAward(int playerId, int taskId) {
//		Task t=this.factory.getTaskDAO().getTask(taskId);
//		if(t.getIsGetAward()==1){//isGetAward为1表示已领取奖励，不可再领取
//			System.out.println("您已领取过奖励不再次领取。。。。");
//			return 0;
//		}else{
//			int golds=t.getAward();
//			this.factory.getTaskDAO().getTaskAward(taskId);
//			this.factory.getLogDAO().addLog(playerId, "完成任务后领取奖励", new Date());
//			return updateGolds(playerId, golds);
//		}
//	}
//	
//	/**游戏中获得奖励更新金币*/
//	public int getGameAward(int playerId, int golds){
//		//日志记录
//		this.factory.getLogDAO().addLog(playerId, "游戏中获得奖励更新金币", new Date());
//		return updateGolds(playerId, golds);
//	}
//	
//	/**返回玩家财富值（金币，宝石，兑换券，换牌卡，翻倍卡，金币卡）*/
//	public Estate getEstate(int playerId){
//		return this.factory.getEstateDAO().getAllEstate(playerId);
//	}
//	
//	/**玩家添加好友时赠送见面礼 更新金币*/
//	public int addFriends(int playerId1,int playerId2,byte giftType,int golds){
//		Estate e=this.getEstate(playerId1);
//		if(e.getGolds()>=golds){//金币数大于礼物价值，可以赠送，否则请购买金币
//			//更新金币
//			int all_golds=updateGolds(playerId1, -golds);
//			//更新礼物
//			getGiftAO().updateGift(playerId2, giftType);
//			//添加好友
//			getFriendsAO().addFriend(playerId1, playerId2);
//			//日志记录
//			getLogAO().getLogDAO().addLog(playerId1, "添加好友", new Date());
//			return all_golds;
//		}else{
//			System.out.println("您的金币不足以购买该商品，请及时充值");
//			return -1;
//		}
//		
//	}
//	
//	/**玩家赠送礼物给其他玩家 */
//	public int payGift(int playerId1,int playerId2,byte giftType,int golds){
//		Estate e=this.getEstate(playerId1);
//		if(e.getGolds()>=golds){//金币数大于礼物价值，可以赠送，否则请购买金币
//			//更新金币
//			int all_golds=updateGolds(playerId1, -golds);
//			//更新礼物
//			getGiftAO().updateGift(playerId2, giftType);
//			//日志记录
//			getLogAO().getLogDAO().addLog(playerId1, "赠送礼物", new Date());
//			return all_golds;
//		}else{
//			System.out.println("您的金币不足以购买该商品，请及时充值");
//			return -1;
//		}
//		
//	}
//	
//	/** 游戏结束时更新玩家金币数*/
//	public void gameOver(int playerId,int score){
//		int wins=0;
//		int loses=0;
//		if(score>0){//赢了
//			wins=1;
//			if(score>30000){//赢的总分大于30000，赠送一个兑换券
//				//赠送兑换券
//				this.getExchangeVoucher(playerId,1);
//				//更新玩家任务记录
//				this.factory.getTaskDAO().updateTask(playerId, wins, loses);
//				//更新玩家金币数
//				updateGolds(playerId, score);
//				//日志记录
//				getLogAO().add(playerId, "玩家赢得比赛并获得一张兑换券奖励", new Date());
//			}else{
//				//更新玩家任务记录
//				this.factory.getTaskDAO().updateTask(playerId, wins, loses);
//				//更新玩家金币数
//				updateGolds(playerId, score);
//				//日志记录
//				getLogAO().add(playerId, "玩家赢得比赛", new Date());
//			}
//		}else{//输了
//			loses=1;
//			//更新玩家任务记录
//			this.factory.getTaskDAO().updateTask(playerId, wins, loses);
//			//更新玩家金币数
//			updateGolds(playerId, score);
//		}
//	}
//	
//	/**搞活动时赠送各种礼品*/
//	
//	/**玩家购买道具时，更新玩家财富值 */
//	public void updateEstate(int playerId,int propId){
//		int gemstone=this.factory.getEstateDAO().needGemstones(propId);
//		int golds=0;
//		int exchangeVoucher=0;
//		int exchangeCard=0;
//		int fourTimesCard=0;
//		int eightTimesCard=0;
//		int banCard=0;
//		Estate e=this.getEstate(playerId);
//		//玩家购买道具所需的宝石数量若是大于自己所拥有的宝石数量则不能购买
//		if(e.getGemstone()<gemstone){
//			System.out.println("您的宝石不够，不能购买该道具，请及时充值");
//		}else{
//			
//			switch(propId){
//			case 1001:
//				golds=Integer.parseInt(csvPropList.getString(0,2))+
//				Integer.parseInt(csvPropList.getString(0,4));//购买10000金币
//				break;
//			case 1002:
//				golds=Integer.parseInt(csvPropList.getString(1,2))+
//				Integer.parseInt(csvPropList.getString(1,4));//购买20000金币
//				break;
//			case 1003:
//				golds=Integer.parseInt(csvPropList.getString(2,2))+
//				Integer.parseInt(csvPropList.getString(2,4));//购买60000金币
//				exchangeVoucher=1;//赠送1个兑换券
//				break;
//			case 1004:
//				golds=Integer.parseInt(csvPropList.getString(3,2))+
//				Integer.parseInt(csvPropList.getString(3,4));//购买300000金币
//				exchangeVoucher=5;//兑换5个兑换券
//				break;
//			case 1005:
//				golds=Integer.parseInt(csvPropList.getString(4,2))+
//				Integer.parseInt(csvPropList.getString(4,4));//购买1000000金币
//				exchangeVoucher=15;//赠送15个兑换券
//				break;
//			case 1006:
//				golds=Integer.parseInt(csvPropList.getString(5,4));//赠送3000金币
//				fourTimesCard=Integer.parseInt(csvPropList.getString(5,2));
//				break;
//			case 1007:
//				golds=Integer.parseInt(csvPropList.getString(6,4));//赠送3000金币
//				eightTimesCard=Integer.parseInt(csvPropList.getString(6,2));
//				break;
//			case 1008:
//				golds=Integer.parseInt(csvPropList.getString(7,4));//赠送3000金币
//				banCard=Integer.parseInt(csvPropList.getString(7,2));
//				break;
//			case 1009:
//				golds=Integer.parseInt(csvPropList.getString(8,4));//赠送3000金币
//				exchangeCard=Integer.parseInt(csvPropList.getString(8,2));
//				break;
//			}
//			this.factory.getEstateDAO().updateEstate(playerId,-gemstone, golds, exchangeVoucher, exchangeCard, fourTimesCard, eightTimesCard, banCard);
//			//记录玩家购买过的道具	
//		}
//	}
//
//	/** 玩家使用换牌卡*/
//	public int updateExchangeCard(int playerId){
//		//日志记录
//		
//		return this.factory.getEstateDAO().updateExchangeCard(playerId);
//	}
//	
//	/** 玩家使用4倍卡*/
//	public int updateFourTimesCard(int playerId) {
//		//日志记录
//		
//		return this.factory.getEstateDAO().updateFourTimesCard(playerId);
//	}
//	
//	/** 玩家使用8倍卡*/
//	public int updateEightTimesCard(int playerId){
//		//日志记录
//		
//		return this.factory.getEstateDAO().updateEightTimesCard(playerId);
//	}
//	
//	/** 玩家使用禁比卡 */
//	public int updateBanCard(int playerId){
//		//日志记录
//		
//		return this.factory.getEstateDAO().updateBanCard(playerId);
//	}
//	
//	/** 玩家获得兑换券*/
//	public int getExchangeVoucher(int playerId,int exchangeVoucher){
//		//日志记录
//		
//		return this.factory.getEstateDAO().getExchangeVoucher(playerId,exchangeVoucher);
//	}
//	
//	/** 玩家使用兑换券兑换实物*/
//	public int getGoods(int playerId,int goodsId){
//		Estate e=this.getEstate(playerId);
//		int exchangeVoucher=this.factory.getEstateDAO().needExchangeVoucher(goodsId);
//		if(e.getExchangeVoucher()<exchangeVoucher){
//			System.out.println("你的兑换券数量不够，不能兑换物品");
//			return -1;
//		}else{
//			int num=e.getExchangeVoucher()-exchangeVoucher;
//			this.factory.getEstateDAO().updateExchangeVoucher(playerId, num);
//			System.out.println("恭喜您，成功兑换实物。。。");
//			return num;
//		}
//		
//	}
//	
//	/** 更新玩家金币 */
//	public int updateGolds(int playerId, int golds){
//		int all_golds=this.factory.getEstateDAO().updateGolds(playerId, golds);
//		//更新玩家的等级称谓
//		this.factory.getPlayerDAO().updateAppellation(playerId, all_golds);
//		return all_golds;
//	}
//	
//	/** 返回财富榜*/
//	public List<WealthListModel> getWealthList(){
//		return this.factory.getWealthListDAO().getWealthList();
//	}
//	
//	/** 返回魅力榜*/
//	public List<CharmListModel> getCharmList(){
//		return this.factory.getCharmListDAO().getCharmList();
//	}
//	
//	/** 返回兑换榜*/
//	public List<ExchangeListModel> getExchangeList(){
//		return this.factory.getExchangeListDAO().getExchangeList();
//	}
//	
//	/** 返回比赛榜*/
//	public List<RaceListModel> getRaceList(){
//		return this.factory.getRaceListDAO().getRaceList();
//	}
//	
//	/** 返回道具列表*/
//	public List<Props> gePropList(){
//		return this.factory.getPropDAO().getPropList();
//	}
//	
//	/** 返回指定Id的道具*/
//	public Props geProp(int propId){
//		return this.factory.getPropDAO().gePropById(propId);
//	}
}
