<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<HTML>
<HEAD>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<link href="${pageContext.request.contextPath}/css/Style1.css" rel="stylesheet" type="text/css" />
	<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>




<script type="text/javascript">
	function showDetail(oid) {
		$.getJSON("${pageContext.request.contextPath}/adminOrderServlet_showDetail", {"oid" : oid}, function(data) {
			var str = "<table class='table table-striped'  cellspacing='0' align='center'>";
			str += "<tr>";
			str += "<th>图片</th>";
			str += "<th>商名</th>";
			str += "<th>价格</th>";
			str += "<th>数量</th>";
			str += "<th>小计</th>";

			str += "</tr>"
			$(data).each(function(index, element1) {
				$(element1.orderItems).each(function(i,element){
					
				
				str += "<tr>";
				str += "<td><img with='40' height='45' src='" + element.product.pimage + "'/></td>";
				str += "<td>" + element.product.pname + "</td>";
				str += "<td>" + element.product.shop_price + "</td>";
				str += "<td>" + element.count + "</td>";
				str += "<td>" + element.subtotal + "</td>";
				str += "</tr>";
				});
				str+="<td colspan='5'  align='right'>总计："+element1.total+"</td>"
			});
			$("#insert").empty();
			$("#insert").append(str);
			$("#myModal").modal('show');
		});

	}
	function createXmlHttp() {

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
					<td class="ta_01" align="center" bgColor="#afd1f3"><strong>订单列表</strong>
					</TD>
				</tr>

				<tr>
					<td class="ta_01" align="center" bgColor="#f5fafe">
						<table cellspacing="0" cellpadding="1" rules="all"
							bordercolor="gray" border="1" id="DataGrid1"
							style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
							<tr
								style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">

								<td align="center" width="10%">序号</td>
								<td align="center" width="10%">订单编号</td>
								<td align="center" width="10%">订单金额</td>
								<td align="center" width="10%">收货人</td>
								<td align="center" width="10%">订单状态</td>
								<td align="center" width="50%">订单详情</td>
							</tr>
							<c:forEach var="o" items="${pageBean.data }" varStatus="status">
								<tr onmouseover="this.style.backgroundColor = 'white'"
									onmouseout="this.style.backgroundColor = '#F5FAFE';">
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="18%">${status.count }</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="17%">${o.oid }</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="17%">${o.total }</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="17%">${o.name}</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="17%"><c:choose>
											<c:when test="${o.state==0 }">
												<a
													href="${pageContext.request.contextPath }/adminOrderServlet_updateState?oid=${o.oid }&state=${o.state+1 }">未付款</a>
											</c:when>
											<c:when test="${o.state==1 }">
												<a
													href="${pageContext.request.contextPath }/adminOrderServlet_updateState?oid=${o.oid }&state=${o.state+1 }">已付款，待发货</a>
											</c:when>
											<c:when test="${o.state==2 }">
												<a
													href="${pageContext.request.contextPath }/adminOrderServlet_updateState?oid=${o.oid }&state=${o.state+1 }">已发货，待收获</a>
											</c:when>
											<c:when test="${o.state==3 }">已完成</c:when>
										</c:choose></td>
									<td align="center" style="HEIGHT: 22px"><input
										type="button" value="订单详情" onclick="showDetail('${o.oid }')" />

										<div id="div2"></div></td>

								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>

			</TBODY>

		</table>
		<div style="width: 380px; margin: 0 auto; margin-top: 50px;">
			<ul class="pagination" style="text-align: center; margin-top: 10px;">

				<c:if test="${pageBean.curPage==1}">
					<li class="disabled"><a aria-label="Previous"><span
							aria-hidden="true">&laquo;</span></a></li>
				</c:if>
				<c:if test="${pageBean.curPage!=1 }">
					<li><a
						href="${pageContext.request.contextPath }/adminOrderServlet_findOrderByPage?state=${param.state }&curPage=${pageBean.curPage-1 }"
						aria-label="Previous"><span aria-hidden="false">&laquo;</span></a></li>
				</c:if>


				<c:forEach begin="1" end="${pageBean.totalPage }" var="i">
					<c:if test="${pageBean.curPage==i }">
						<li class="active"><a
							href="${pageContext.request.contextPath }/adminOrderServlet_findOrderByPage?state=${param.state }&curPage=${i}">${i}</a></li>
					</c:if>
					<c:if test="${pageBean.curPage!=i}">
						<li><a
							href="${pageContext.request.contextPath }/adminOrderServlet_findOrderByPage?state=${param.state }&curPage=${i}">${i}</a></li>
					</c:if>
				</c:forEach>
				<!-- 下一页 -->
				<c:if test="${pageBean.curPage==pageBean.totalPage}">
					<li class="disabled"><a aria-label="Next"> <span
							aria-hidden="true">&raquo;</span>
					</a></li>
				</c:if>
				<!-- 
					<span aria-hidden="false">&laquo;</span></a></li>
					<li class="disabled"><a aria-label="Previous"><span
							aria-hidden="true">&laquo;</span></a></li>
					 -->
				<c:if test="${pageBean.curPage!=pageBean.totalPage }">
					<li><a
						href="${pageContext.request.contextPath }/adminOrderServlet_findOrderByPage?state=${param.state }&curPage=${pageBean.curPage+1 }"
						aria-label="Next"> <span aria-hidden="false">&raquo;</span>
					</a></li>
				</c:if>

			</ul>
		</div>


	</form>
	<div class="modal fade" tabindex="-1" role="dialog" id="myModal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">订单详情</h4>
				</div>
				<div class="modal-body" id="insert">
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</body>
</HTML>

