<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>文件上传</title>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/statics/css/style.css" />
<script type="text/javascript" 	src="${pageContext.request.contextPath}/statics/js/js.js" ></script>
</head>

<body>

	<h1 id="title">上传图片</h1>

	<form action="${pageContext.request.contextPath}/upload.html"
		method="post" enctype="multipart/form-data">
		<p>
			照片描述:<input type="text" name="desc">
		</p>
		<p>
			选择照片:<input type="file" name="myfile">
		</p>
		<p>
			<input type="submit" value="提交照片">
	</form>

</body>
</html>
