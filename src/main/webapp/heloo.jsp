
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<% String s = (String) request.getAttribute("message");%>
<h1 style="color: crimson"><%=s%></h1>
</body>
</html>
