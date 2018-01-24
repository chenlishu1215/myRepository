<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<HTML>
<HEAD>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" 	type="text/css" />

	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
	<link href="${pageContext.request.contextPath}/css/Style1.css" rel="stylesheet" type="text/css" />
	<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
	<script language="javascript" src="${pageContext.request.contextPath}/js/public.js"></script>
	<script type="text/javascript">
				function addProduct(){
					window.location.href = "${pageContext.request.contextPath}/adminProductServlet_addPage.action";
				}
	</script>
	<script>
			function del(pid){
				var flag=confirm("确定删除这个商品吗？");
				if(flag){
					location.href="${pageContext.request.contextPath}/adminProductServlet_deleteProduct?pid="+pid;
				}
			}
			onload=function(){
				if("${state}"=="1"){
					alert("商品删除成功！");
				}else if("${state}"=="0"){
					alert("商品删除失败！");
				}
			}
	</script>
</HEAD>
<body>
	<br>
	<form id="Form1" name="Form1"
		action="${pageContext.request.contextPath}/user/list.jsp"
		method="post">
		<table cellSpacing="1" cellPadding="0" width="100%" align="center"
			bgColor="#f5fafe" border="0">
			<TBODY>
				<tr>
					<td class="ta_01" align="center" bgColor="#afd1f3"><strong>商品列表</strong>
					</TD>
				</tr>
				<tr>
					<td class="ta_01" align="right">
						<button type="button" id="add" name="add" value="添加"
							class="button_add" onclick="addProduct()">
							&#28155;&#21152;</button>

					</td>
				</tr>
				<tr>
					<td class="ta_01" align="center" bgColor="#f5fafe">
						<table cellspacing="0" cellpadding="1" rules="all"
							bordercolor="gray" border="1" id="DataGrid1"
							style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
							<tr
								style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">

								<td align="center" width="18%">序号</td>
								<td align="center" width="17%">商品图片</td>
								<td align="center" width="17%">商品名称</td>
								<td align="center" width="17%">商品价格</td>
								<td align="center" width="17%">是否热门</td>
								<td width="7%" align="center">编辑</td>
								<td width="7%" align="center">删除</td>
							</tr>
							<c:forEach items="${pageBean.data }" var="p" varStatus="vs">
								<tr onmouseover="this.style.backgroundColor = 'white'"
									onmouseout="this.style.backgroundColor = '#F5FAFE';">
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="18%">${vs.count }</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="17%"><img width="40" height="45"
										src="${ pageContext.request.contextPath }/${p.pimage}">
									</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="17%">${p.pname }</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="17%">${p.shop_price }</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="17%"><c:if test="${p.is_hot==1 }">是</c:if> <c:if
											test="${p.is_hot!=1 }">否</c:if></td>
									<td align="center" style="HEIGHT: 22px"><a
										href="${ pageContext.request.contextPath }/adminProductServlet_productBackShow?pid=${p.pid}">
											<img
											src="${pageContext.request.contextPath}/images/i_edit.gif"
											border="0" style="CURSOR: hand">
									</a></td>

									<td align="center" style="HEIGHT: 22px"><a
										href="javascript:" onclick="del('${p.pid}')"> <img
											src="${pageContext.request.contextPath}/images/i_del.gif"
											width="16" height="16" border="0" style="CURSOR: hand">
									</a></td>
								</tr>
							</c:forEach>
						</table>
						<div style="width: 380px; margin: 0 auto; margin-top: 50px;">
							<ul class="pagination"
								style="text-align: center; margin-top: 10px;">
								<c:if test="${pageBean.curPage==1}">
									<li class="disabled"><a aria-label="Previous"><span
											aria-hidden="true">&laquo;</span></a></li>
								</c:if>
								<c:if test="${pageBean.curPage!=1 }">
									<li><a
										href="${pageContext.request.contextPath }/adminProductServlet_findAllProduct?curPage=${pageBean.curPage-1 }"
										aria-label="Previous"><span aria-hidden="false">&laquo;</span></a></li>
								</c:if>


								<c:forEach begin="1" end="${pageBean.totalPage }" var="i">
									<c:if test="${pageBean.curPage==i }">
										<li class="active"><a
											href="${pageContext.request.contextPath }/adminProductServlet_findAllProduct?curPage=${i}">${i}</a></li>
									</c:if>
									<c:if test="${pageBean.curPage!=i}">
										<li><a
											href="${pageContext.request.contextPath }/adminProductServlet_findAllProduct?curPage=${i}">${i}</a></li>
									</c:if>
								</c:forEach>
								<!-- 下一页 -->
								<c:if test="${pageBean.curPage==pageBean.totalPage}">
									<li class="disabled"><a aria-label="Next"> <span
											aria-hidden="true">&raquo;</span>
									</a></li>
								</c:if>
								<c:if test="${pageBean.curPage!=pageBean.totalPage }">
									<li><a
										href="${pageContext.request.contextPath }/adminProductServlet_findAllProduct?curPage=${pageBean.curPage+1 }"
										aria-label="Next"> <span aria-hidden="false">&raquo;</span>
									</a></li>
								</c:if>

							</ul>
						</div>

					</td>
				</tr>
			</TBODY>
		</table>
	</form>
</body>
</HTML>

