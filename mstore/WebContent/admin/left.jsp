<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>菜单</title>
<link href="${pageContext.request.contextPath}/css/left.css" rel="stylesheet" type="text/css"/>
<link rel="StyleSheet" href="${pageContext.request.contextPath}/css/dtree.css" type="text/css" />
</head>
<body>
<table width="100" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="12"></td>
  </tr>
</table>
<table width="100%" border="0">
  <tr>
    <td>
<div class="dtree">

	<a href="javascript: d.openAll();">展开所有</a> | <a href="javascript: d.closeAll();">关闭所有</a>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/dtree.js"></script>
	<script type="text/javascript">
	
		d = new dTree('d');
		d.add('01',-1,'系统菜单树');
		d.add('0101','01','用户管理','','','mainFrame');
		d.add('010101','0101','用户管理','${pageContext.request.contextPath}/userAdmin_findAll.action?page=1','','mainFrame');
		
		d.add('0102','01','分类管理','','','mainFrame');
		d.add('010201','0102','所有分类','${pageContext.request.contextPath}/adminCategoryServlet_findAll','','mainFrame');
		d.add('010202','0102','添加分类','${pageContext.request.contextPath}/adminCategoryServlet_adminUI','','mainFrame');
		
		d.add('0104','01','商品管理');
		d.add('010401','0104','所有商品','${pageContext.request.contextPath}/adminProductServlet_findAllProduct?curPage=1','','mainFrame');
		d.add('010402','0104','添加商品','${pageContext.request.contextPath}/adminProductServlet_adminUI','','mainFrame');
		
		d.add('0105','01','订单管理');
		d.add('010501','0105','所有订单','${pageContext.request.contextPath}/adminOrderServlet_findOrderByPage?curPage=1','','mainFrame');
		d.add('010502','0105','未付款','${pageContext.request.contextPath}/adminOrderServlet_findOrderByPage?curPage=1&state=0','','mainFrame');
		d.add('010503','0105','已付款，待发货','${pageContext.request.contextPath}/adminOrderServlet_findOrderByPage?curPage=1&state=1','','mainFrame');
		d.add('010504','0105','已发货，待收获','${pageContext.request.contextPath}/adminOrderServlet_findOrderByPage?curPage=1&state=2','','mainFrame');
		d.add('010505','0105','已完成','${pageContext.request.contextPath}/adminOrderServlet_findOrderByPage?curPage=1&state=3','','mainFrame');
		document.write(d);
		
	</script>
</div>	</td>
  </tr>
</table>
</body>
</html>
