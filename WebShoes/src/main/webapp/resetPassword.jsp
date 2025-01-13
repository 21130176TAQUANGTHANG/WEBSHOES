<%--
  Created by IntelliJ IDEA.
  User: thang
  Date: 1/13/2025
  Time: 9:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Đặt lại mật khẩu</h1>
<form action="ResetPasswordServlet" method="post">
  <label for="password">Mật khẩu mới:</label>
  <input type="password" id="password" name="password" required>

  <label for="confirmPassword">Xác nhận mật khẩu:</label>
  <input type="password" id="confirmPassword" name="confirmPassword" required>

  <button type="submit">Đặt lại mật khẩu</button>
</form>
<p style="color: red;"><%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %></p>
</body>
</html>
