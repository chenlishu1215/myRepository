<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	
	
	
		<div class="container-fluid">
				<div class="col-md-4">
					<img src="${pageContext.request.contextPath}/img/logo2.png" />
				</div>
				<div class="col-md-5">
					<img src="${pageContext.request.contextPath}/img/header.png" />
				</div>
				<div class="col-md-3" style="padding-top:20px">
					<ol class="list-inline">
						<c:if test="${empty user }">
							<li><a href="${pageContext.request.contextPath }/jsp/login.jsp">登录</a></li>
							<li><a href="${pageContext.request.contextPath }/jsp/register.jsp">注册</a></li>
							
						</c:if>
						<c:if test="${not empty user }">
							<li><a>${user.username }</a></li>
							<li><a href="${pageContext.request.contextPath }/user_exit">注销</a></li>
							<li><a href="${pageContext.request.contextPath }/order_findByPage?curPage=1">查看订单</a></li>
						</c:if>
						
							<li><a href="${pageContext.request.contextPath }/jsp/cart.jsp">购物车</a></li>
						
					</ol>
				</div>
		</div>

		<div class="container-fluid">
				<nav class="navbar navbar-inverse">
					<div class="container-fluid">
						<!-- Brand and toggle get grouped for better mobile display -->
						<div class="navbar-header">
							<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
								<span class="sr-only">Toggle navigation</span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
							</button>
							<a class="navbar-brand" href="${pageContext.request.contextPath }/product_indexProduct">首页</a>
		</div>

<script>
//页面加载完成后 执行 ajax动态查询到所有的商品分类的信息
	$(function(){
		
		
		var url="${pageContext.request.contextPath}/category_findAll";
		var json={"method":"findAll"};
		
		$.getJSON(url,json,function(json){
			//遍历json集合
			$(json).each(function(index,category){
				//通过添加子节点 展示所有的分类信息
				$("#menu_ul").append("<li><a href='${pageContext.request.contextPath}/product_findByPage?cid="+category.cid+"&curPage=1&pageSize=12'>"+category.cname+"</a></li>");
			});
		});
	});
</script>
						<!-- Collect the nav links, forms, and other content for toggling -->
						<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
							<ul class="nav navbar-nav" id="menu_ul">
												
							</ul>
							
							<form class="navbar-form navbar-right" role="search">
								<div class="form-group">
									<input type="text" class="form-control" placeholder="Search">
								</div>
								<button type="submit" class="btn btn-default">Submit</button>
							</form>

						</div>
						<!-- /.navbar-collapse -->
					</div>
					<!-- /.container-fluid -->
				</nav>
			</div>
	
