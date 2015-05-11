package com.orange.goldgame.action;

import java.util.List;

import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerProps;

public interface PlayerAction {
	/** 玩家登陆 */
	public Player login(String account, String nickname, String password);
	
	/** 玩家请求更新个人信息 */
	public void update(int playerId, String sex, int age, String nickname,
			String tag, String connect);
	
	/** 查询玩家信息 */
	public List<Player> getPlayerMsg(int playerId);
	
	/** 查询玩家信息 */
	public List<Player> getPlayerMsg(String account,String password);
	
	/**查询所有玩家信息**/
	public List<Player> getAllPlayers();
	
	
	
	
	/** 增加玩家信息 
	 * @param machineCode2 */
	public Player createPlayer(String machineCode, String machineCode2,String app_channel);
	
	/** 通过id查询单个玩家信息 */
	public Player loadPlayerById(int playerId);
	
	/** 通过机器码code查询单个玩家信息 */
	public Player loadPlayerByCode(String machineCode);
	
	/** 修改玩家信息 */
	public void modifyPlayer(Player p);

	/** 查询玩家个人物品信息 */
	public List<PlayerProps> loadAllPersonalItemById(int playerId);
	
	
	/**通过指定玩家的金币范围查询玩家数据**/
	public List<Player> queryPlayersByCopperRange(int min,int max,int start,int end);
	
	/**通过指定玩家的宝石范围查询玩家数据**/
	public List<Player> queryPlayersByGoldRange(int min,int max,int start,int end);
	
	/**通过指定玩家的性别查询玩家数据**/
	public List<Player> queryPlayersBySex(int sex,int start,int end);
	
	/**通过判断玩家是否在线查询玩家数据**/
	public List<Player> queryPlayersByOnline(int isOnline,int start,int end);
}
