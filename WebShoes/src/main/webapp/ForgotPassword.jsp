<%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 13/01/2025
  Time: 7:54 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Forgot Password</title>
</head>
<body>
<h2>Quên mật khẩu</h2>
<form action="ForgotPasswordServlet" method="post">
    <label for="email">Nhập email của bạn:</label>
    <input type="email" name="email" id="email" required>
    <button type="submit">Gửi</button>
</form>
<p style="color:red">${error}</p>
<p style="color:green">${message}</p>
</body>
</html>
