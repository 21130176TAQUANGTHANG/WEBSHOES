<%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 13/01/2025
  Time: 8:29 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Nhập mã OTP</title>
</head>
<body>
<h2>Nhập mã OTP</h2>
<form action="VerifyOtpServlet" method="post">
    <input type="hidden" name="email" value="${email}">
    <label for="otp">Mã OTP:</label>
    <input type="text" name="otp" id="otp" maxlength="4" required>
    <button type="submit">Xác nhận</button>
</form>
<p style="color:red">${error}</p>
</body>
</html>
