<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
	<head></head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>会员登录</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>

<style>
  body{
   margin-top:20px;
   margin:0 auto;
 }
 .carousel-inner .item img{
	 width:100%;
	 height:300px;
 }
 .container .row div{ 
	 /* position:relative;
	 float:left; */
 }
 
font {
    color: #3164af;
    font-size: 18px;
    font-weight: normal;
    padding: 0 10px;
}
 </style>

</head>
 
 <script>
 	function changeCode(obj){
 		$(obj).attr("src","${pageContext.request.contextPath}/validate_getValidate?time="+new Date().getTime());
 	}
 	
 	function blur1(obj){
 		if($(obj).val()!=null&&$(obj).val().trim()!=""){
	 		$.post("${pageContext.request.contextPath}/user_unameAjax",{"username":obj.value},function(result){
	 			var flag=("true"==result);
	 			var msg="该用户名可用";
	 			var color="green";
	 			$("#sub").removeProp("disabled")
	 			if(flag){
	 				msg="该用户名不可用"
	 				color="red";
	 				$("#sub").prop("disabled","true")
	 				
	 			}
	 				
	 				$("#sp1").text(msg);
	 				$("#sp1").css("color",color);
	 		});
 		}else{
 			$("#sub").prop("disabled","true")
 			$("#sp1").text("用户名不能为空！")
 			$("#sp1").css("color","red")
 		}
 		
 	}
 </script>

<body>




			<!--
            	时间：2015-12-30
            	描述：菜单栏
            -->
			
			<!--
            	时间：2015-12-30
            	描述：导航条
            -->
			<!-- 引入外部jsp页面 -->
			<jsp:include page="comms/head.jsp"/>




<div class="container" style="width:100%;background:url('${pageContext.request.contextPath}/image/regist_bg.jpg');">
<div class="row"> 

	<div class="col-md-2"></div>
	
	


	<div class="col-md-8" style="background:#fff;padding:40px 80px;margin:30px;border:7px solid #ccc;">
		<font>会员注册</font>USER REGISTER<font style="color:red;">${msg }</font>
		<form class="form-horizontal" style="margin-top:5px;" method="post" action="${pageContext.request.contextPath }/user_register">
			<input type="hidden" name="method" value="regist">
			 <div class="form-group">
			    <label for="username" class="col-sm-2 control-label">用户名</label>
			    <div class="col-sm-6">
			      <input type="text"  value="${user.username}" class="form-control" id="username" placeholder="请输入用户名" name="username" onblur="blur1(this)"><span id="sp1"></span>
			    </div>
			  </div>
			   <div class="form-group">
			    <label for="inputPassword3" class="col-sm-2 control-label">密码</label>
			    <div class="col-sm-6">
			      <input type="password" value="${user.password }" class="form-control" id="inputPassword3" placeholder="请输入密码" name="password">
			    </div>
			  </div>
			   <div class="form-group">
			    <label for="confirmpwd" class="col-sm-2 control-label">确认密码</label>
			    <div class="col-sm-6">
			      <input type="password" value="${user.password }" class="form-control" id="confirmpwd" placeholder="请输入确认密码">
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="inputEmail3" class="col-sm-2 control-label">Email</label>
			    <div class="col-sm-6">
			      <input type="email"  value="${user.email }" class="form-control" id="inputEmail3" placeholder="Email" name="email" onblur="blur2(this)"/><span id="sp2"></span>
			    </div>
			  </div>
			  
			  <div class="form-group">
			    <label for="inputEmail3" class="col-sm-2 control-label">手机号码</label>
			    <div class="col-sm-6">
			      <input type="tel"  value="${user.telephone }" class="form-control" id="inputEmail3"  placeholder="手机号码" name="telephone">
			    </div>
			  </div>
			 <div class="form-group">
			    <label for="usercaption" class="col-sm-2 control-label">姓名</label>
			    <div class="col-sm-6">
			      <input type="text" value="${user.name }" class="form-control" id="usercaption" placeholder="请输入姓名" name="name">
			    </div>
			  </div>
			  <div class="form-group opt">  
			  <label for="inlineRadio1" class="col-sm-2 control-label">性别</label>  
			  <div class="col-sm-6">
			 <label class="radio-inline">
			  	<input type="radio" name="sex" id="inlineRadio1" value="1"> 男
			</label>
			<label class="radio-inline">
			  <input type="radio" name="sex" id="inlineRadio2" value="0"> 女
			</label>
			</div>
			  </div>		
			  <div class="form-group">
			    <label for="date" class="col-sm-2 control-label">出生日期</label>
			    <div class="col-sm-6">
			      <input type="date" value="${user.birthday }" class="form-control"  name="birthday">		      
			    </div>
			  </div>
			  
			  <div class="form-group">
			    <label for="date" class="col-sm-2 control-label">验证码</label>
			    <div class="col-sm-3">
			      <input type="text" class="form-control" name="vcode" >
			      
			    </div>
			    <div class="col-sm-2">
			    <img src="${pageContext.request.contextPath}/validate_getValidate" onclick="changeCode(this)"/>
			    </div>
			    
			  </div>
			 
			  <div class="form-group">
			    <div class="col-sm-offset-2 col-sm-10">
			      <input type="submit" id ="sub" width="100" value="注册" name="submit" border="0"
				    style="background: url('${pageContext.request.contextPath}/images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
				    height:35px;width:100px;color:white;">
			    </div>
			  </div>
			</form>
	</div>
	
	<div class="col-md-2"></div>
  
</div>
</div>

	  
	
	<div style="margin-top:50px;">
			<img src="${pageContext.request.contextPath}/image/footer.jpg" width="100%" height="78" alt="我们的优势" title="我们的优势" />
		</div>

		<div style="text-align: center;margin-top: 5px;">
			<ul class="list-inline">
				<li><a>关于我们</a></li>
				<li><a>联系我们</a></li>
				<li><a>招贤纳士</a></li>
				<li><a>法律声明</a></li>
				<li><a>友情链接</a></li>
				<li><a target="_blank">支付方式</a></li>
				<li><a target="_blank">配送方式</a></li>
				<li><a>服务声明</a></li>
				<li><a>广告声明</a></li>
			</ul>
		</div>
		<div style="text-align: center;margin-top: 5px;margin-bottom:20px;">
			Copyright &copy; 2005-2016 传智商城 版权所有
		</div>

</body></html>




