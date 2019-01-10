<%@ page pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8") ;
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
	String login_url = basePath + "loginPage.action" ; // 自定义登录路径
%>
<base href="<%=basePath%>" />
<h2>账户被强制下线，有可能存在安全隐患！</h2>
<img src="mvcimages/mldn.png" style="width:500px;">
<img src="mvcimages/jixianit.png" style="width:500px;">