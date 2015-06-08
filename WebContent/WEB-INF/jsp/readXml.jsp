<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>XML文档转换</title>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet"
	href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css">

<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<link rel="stylesheet"
	href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.bootcss.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<hr />
		<form:form action="/convert2xml/deal.html" method="post"
			commandName="readXml" role="form" cssClass="form-horizontal">
			<div class="form-group">
				<label for="readXml" class="col-sm-2 control-label">选择文件:</label>
				<div class="col-sm-10">
					<form:input path="xmlPath" type="file" class="form-control" />
				</div>
			</div>

			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="button" onclick="convertToXml()" class="btn btn-default">提交</button>
				</div>
			</div>
		</form:form>
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
	function convertToXml() {
		var _url = '/convert2xml/deal.html';
		var xmlPath = $.trim($("#xmlPath").val());

		$.ajax({
			type : "POST",
			url : _url,
			dataType : 'json',
			data : {
				"xmlPath" : xmlPath
			},
			complete : function() {
			},
			success : function(data) {
				if (data.returnCode == 0) {
					$('#resultBody').append("转换成功！");//写入返回回来内容
					$('#result').modal({
						show : true, //弹出框	
						backdrop : false, //此参数用于禁用点其他地方自动关闭
					});
				}
				if (data.returnCode == 1) {
					$('#resultBody').append("转换异常，请查看转换文档格式是否符合！");//写入返回回来内容
					$('#result').modal({
						show : true, //弹出框	
						backdrop : false, //此参数用于禁用点其他地方自动关闭
					});
				}
				if (data.returnCode == 2) {
					$('#resultBody').append("请选择转换文件！");//写入返回回来内容
					$('#result').modal({
						show : true, //弹出框	
						backdrop : false, //此参数用于禁用点其他地方自动关闭
					});
				}
			}
		});

		//关闭时，清除之前的数据。
		$('#result').on("hidden.bs.modal", function() {
			$('#resultBody').html('');
			//清除选中的路径
			$("#xmlPath").val("");
		});
	}
</script>
</html>