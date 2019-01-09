<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
	request.setCharacterEncoding("UTF-8") ;
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
	String logout_url = basePath + "mldn-logout" ; // 自定义注销路径
	String info_url = basePath + "pages/info/url.action" ; // 自定义注销路径
	String echo_url = basePath + "pages/message/input.action" ; // 自定义注销路径
%>
<base href="<%=basePath%>" />
<security:authorize access="isAuthenticated()">
	<h3>用户已经成功登录了！</h3>
</security:authorize>
<security:authorize access="hasRole('USER')">
	<h3>用户拥有USER角色！</h3>
</security:authorize>
<security:authorize access="hasRole('ADMIN')">
	<h3>用户拥有ADMIN角色！</h3>
</security:authorize>
<h2>登录成功，欢迎“<security:authentication property="principal.username"/>”回来，也可以选择<a href="<%=logout_url%>">注销</a>！</h2>
<h2>更多内容请访问：<a href="http://www.mldn.cn">MLDN-魔乐科技</a>。</h2>
<h2>查看信息：<a href="<%=info_url%>">查看网站信息</a>。</h2>
<h2>ECHO操作：<a href="<%=echo_url%>">查看网站信息</a>。</h2>
<img src="mvcimages/mldn.png" style="width:500px;">
<img src="mvcimages/jixianit.png" style="width:500px;">