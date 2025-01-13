<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Nhập mã OTP</h1>
<form action="VerifyOtpServlet" method="post">
  <label for="otp">Mã OTP:</label>
  <input type="text" id="otp" name="otp" required>
  <button type="submit">Xác nhận</button>
</form>
<p style="color: red;"><%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %></p>
</body>
</html>
