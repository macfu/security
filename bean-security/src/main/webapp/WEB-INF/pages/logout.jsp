<%@ page pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8") ;
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
	String login_url = basePath + "loginPage.action" ; // 自定义登录路径
%>
<base href="<%=basePath%>" />
<h2>注销成功，大爷欢迎下次再来玩，好好伺候着！！！</h2>
<h2>更多内容请访问：<a href="http://www.mldn.cn">MLDN-魔乐科技</a>。</h2>
<h2>重新登录：<a href="<%=login_url%>">登录</a>。</h2> 
<img src="mvcimages/mldn.png" style="width:500px;">
<img src="mvcimages/jixianit.png" style="width:500px;">