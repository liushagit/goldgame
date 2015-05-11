<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="com.orange.goldgame.bean.PlayerWheelRecoder"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.orange.goldgame.util.DateUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript" src="/My97DatePicker/WdatePicker.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>抽奖结果</title>
</head>
<body>
	<form name="form1" method="post" action="/p">
		<table width="1024" border="1" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#CCCCCC">
			<tr>
			<%
				long tomorrow = System.currentTimeMillis() + (24 * 60 * 60 * 1000);
			%>
				<td>开始时间： <input name="start" type="text" value="<%=DateUtil.getDateDesc() %>"
					size="10" onMouseDown="WdatePicker()"> 结束时间： <input
					name="end" type="text" value="<%=DateUtil.getDateDesc(new Date(tomorrow)) %>" size="10"
					onMouseDown="WdatePicker()"> 是否处理： <input name="status"
					type="text" value="1" size="2" onMouseDown="">（0代表已处理，1代表未处理，-1代表所有）
					<input name="type" type="hidden" value="quary"> <input
					name="id" type="hidden" value="0"> <input name="submit"
					type="submit">
				</td>
			</tr>
		</table>
	</form>
	<br />

	<form name="form2" method="post" action="/p">
		<table width="1024" border="1" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#CCCCCC">
			<tr>
				<th>id</th>
				<th>玩家id</th>
				<th>联系方式</th>
				<th>创建时间</th>
				<th>中奖结果</th>
				<th>处理时间</th>
				<th>处理结果</th>
				<th>处理</th>
			</tr>
			<%
				Object o = request.getAttribute("infos");

				if (o != null) {
					List<PlayerWheelRecoder> list = (List<PlayerWheelRecoder>) o;
					PlayerWheelRecoder pwr = null;
					for (int i = 0; i < list.size(); i++) {
						pwr = list.get(i);
			%>
			<tr>
				<td><%=pwr.getId()%></td>

				<td><%=pwr.getPlayerId()%></td>

				<td><%=pwr.getPlayerPhone()%></td>

				<td><%=DateUtil.getDetailDate(pwr.getCreateTime())%></td>

				<td><%=pwr.getRewardName()%></td>

				<td><%=DateUtil.getDetailDate(pwr.getUpdateTime())%></td>

				<td>
					<%
						if (pwr.getChangeStatus() == 1) {
									out.print("未处理");
								} else {
									out.print("已处理");
								}
					%>
				</td>
				<td>
					<%
						if (pwr.getChangeStatus() == 1) {
					%> 
					<input name="start" type="hidden" value="20140301" size="10"> 
					<input name="end" type="hidden" value="20140402" size="10"> 
					<input name="status" type="hidden" value="1" size="2" onMouseDown="">
					<input name="id" type="hidden" value="<%=pwr.getId()%>"> 
					<input name="type" type="hidden" value="update"> 
					<input type="submit" name="submit" value="处理"> 
					<%
 	} else {

 			}
 %>

				</td>
			</tr>
			<%
				}
				}
			%>

		</table>
	</form>
</body>
</html>