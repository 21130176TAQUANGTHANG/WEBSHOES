<%--
  Created by IntelliJ IDEA.
  User: thang
  Date: 9/8/2024
  Time: 1:50 PM
  To change this template use File | Settings | File Templates.
--%>
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
