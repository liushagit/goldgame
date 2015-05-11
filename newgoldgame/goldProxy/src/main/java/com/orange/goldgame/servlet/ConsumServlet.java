/**
 * 
 */
package com.orange.goldgame.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.orange.goldgame.core.model.Consum;
import com.orange.goldgame.dbutil.DBManager;
import com.orange.goldgame.util.DateUtil;
import com.orange.goldgame.util.FileUtil;
import com.orange.goldgame.util.SqlMapConfig;


/**
 * @author guojiang
 *
 */
public class ConsumServlet extends HttpServlet{
	 /**
     * Default constructor. 
     */
    public ConsumServlet() {
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
    public void init() throws ServletException {
    	log("============== init servlet =============");
    	InputStream in = ConsumServlet.class.getClassLoader().getResourceAsStream("db.properties");
		FileUtil.createNewFile(System.getProperty("user.dir") + File.separator + "db.properties", in);
    	SqlMapConfig.init();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String beginTime = request.getParameter("bt");
		String endTime = request.getParameter("et");
		List<Consum> list = DBManager.quaryConsum(beginTime, endTime);
		log("===============" + list.size()+"===============");
		Map<String, String> dateTime = new HashMap<String, String>();
		Map<String, String> itemName = new HashMap<String, String>();
		
		//返回给页面的数据
		Map<String, Map<String, Consum>> allConsu = new HashMap<String, Map<String,Consum>>();
		List<String> dateList = new ArrayList<String>();
		List<String> itemList = new ArrayList<String>();
		
		Map<String, Consum> map;
		for (Consum consum : list) {
			dateTime.put(DateUtil.getDateDesc(consum.getConsumTime()), DateUtil.getDateDesc(consum.getConsumTime()));
			itemName.put(consum.getName(), consum.getName());
			map = allConsu.get(DateUtil.getDateDesc(consum.getConsumTime()));
			if (map == null) {
				map = new HashMap<String, Consum>();
				allConsu.put(DateUtil.getDateDesc(consum.getConsumTime()), map);
			}
			map.put(consum.getName(), consum);
		}
		
		dateList.addAll(dateTime.keySet());
		itemList.addAll(itemName.keySet());
		
		Collections.sort(dateList);
		Collections.sort(itemList);
		
	}
	
}
