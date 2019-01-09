<%@ page pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8") ;
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
	String login_url = basePath + "mldn-login" ;	// 定义一个自己的登录路径 
%>
<base href="<%=basePath%>" />
<form action="<%=login_url%>" method="post">
	用户名：<input type="text" name="mid" id="mid"><br>
	密码：<input type="password" name="pwd" id="pwd"><br>
	<input type="submit" value="登录">
	<input type="reset" value="重置"> 
</form> 
