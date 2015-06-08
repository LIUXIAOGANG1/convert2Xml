<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
    <title>Bootstrap In Practice - Landing Page Example</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<!-- Bootstrap -->
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>

<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css">
<!-- <link href='http://fonts.googleapis.com/css?family=Abel|Open+Sans:400,600' rel='stylesheet'> -->

    <style>
        body {
            padding-top: 20px;
            font-size: 16px;
            font-family: "Open Sans",serif;
        }
        h1 {
            font-family: "Abel", Arial, sans-serif;
            font-weight: 400;
            font-size: 40px;
        }
        .margin-base-vertical {
            margin: 40px 0;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <h1 class="margin-base-vertical">北京科技大学11级毕业设计</h1>
                <p>
                    本次毕业设计主要是针对从yaml工作流转换到xml文件。
                </p>
                <p>
                    转换过程主要有:1、.....
                    <br>&nbsp;&nbsp;&nbsp;&nbsp;2、。。。。。
                </p>
                
                <c:if test="${msg != null}">
					${msg}
				</c:if>
                
                <form class="margin-base-vertical" action="/convert2xml/login.html" method="post">
                    <p class="input-group text-center">
                        <span class="input-group-addon">用户名：</span>
                        <input id="name" name="name" type="text" class="form-control input-lg" placeholder="input your name" />
                    </p>

					<p class="input-group">
						<span class="input-group-addon">密&nbsp;&nbsp;码：</span>
						<input id="passWord" name="passWord" type="password" class="form-control input-lg" placeholder="input your password" />
					</p>

					<br><br>
                    <p class="text-center">
                        <button type="submit" class="btn btn-primary">登陆</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="button" onclick="reset()" class="btn btn-danger">重设</button>
                    </p>
                </form>
            </div>
        </div>
    </div>

<!-- 弹出框 -->
	<div class="modal fade" id="result" tabindex="-1" role="dialog"
		aria-labelledby="resultLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="destroyLabel">
						<b>提示！</b>
					</h4>
				</div>
				<div class="modal-body" id="resultBody"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
				</div>
			</div>
		</div>
	</div>
</body>



<script language="javascript">
	function reset() {
		$("#name").val("");
		$("#passWord").val("");
	}
</script>
</html>