/**
 * 
 */
package com.orange.goldgame.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.orange.goldgame.bean.PlayerWheelRecoder;
import com.orange.goldgame.http.HttpReqSender;
import com.orange.goldgame.service.BaseService;
import com.orange.goldgame.util.URLUtil;


/**
 * @author guojiang
 *
 */
public class ProxyServlet extends HttpServlet{
	 /**
     * Default constructor. 
     */
    public ProxyServlet() {
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
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
		Map<String, String[]> map = request.getParameterMap();
		StringBuilder sb = new StringBuilder();
		sb.append("http://mmzjh.wiorange.com:9999/wheel?");
		
		String list[] = null;
		for (String key : map.keySet()) {
			if (key.equals("submit")) {
				continue;
			}
			list = map.get(key);
			sb.append(key).append("=").append(list[0]).append("&");
		}
		log(sb.toString());
		String result = HttpReqSender.sendGetReq(sb.toString());
		if (result != null && result.length() > 0) {
			result = URLUtil.decode(result);
			log(result);
//			if ("quary".equals(map.get("type")[0])) {
				BaseService.list.clear();
				JSONObject json = JSONObject.fromObject(result);
				JSONArray arrays = json.getJSONArray("data");
				
				List list2 = JSONArray.toList(arrays, new PlayerWheelRecoder(), new JsonConfig());//参数1为要转换的JSONArray数据，参数2为要转换的目标数据，即List盛装的数据
				List<PlayerWheelRecoder> person = null;
				PlayerWheelRecoder da = null;
				for (int i = 0; i < list2.size(); i++) {
					person = (List<PlayerWheelRecoder>) list2.get(i);
					for (int j = 0; j < person.size(); j++) {
						da = person.get(j);
						BaseService.list.add(da);
					}
				}
//			}
		}
		request.setAttribute("infos", BaseService.list);
		
		request.getRequestDispatcher ("index.jsp").forward(request, response);
	}
	
}
