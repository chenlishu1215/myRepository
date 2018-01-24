<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<%
		response.sendRedirect(request.getContextPath()+"/product_indexProduct");
%>
<body>
	
	<a href="${pageContext.request.contextPath }/jsp/login.jsp">登陆</a>
	<a href="${pageContext.request.contextPath }/jsp/register.jsp">注册</a>
	<a href="${pageContext.request.contextPath }/product_indexProduct">首页</a>
</body>
</html>