<%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 11/12/2024
  Time: 9:55 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
  <title>Thông báo báo mất khóa</title>
  <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.2/mdb.min.css" rel="stylesheet" />
</head>
<body>
<div class="container mt-5">
  <h2 class="text-center mb-4">Kết quả báo mất khóa</h2>

  <c:if test="${not empty message}">
    <div class="alert alert-success" role="alert">
      <strong>${message}</strong>
    </div>
  </c:if>

  <div class="card">
    <div class="card-body">
      <h5>Public Key mới:</h5>
      <textarea rows="5" class="form-control mb-3" readonly>${publicKey}</textarea>
      <input type="hidden" name="publicKey" value="${publicKey}" />
    </div>
    <div class="card-footer">
      <!-- Nút để quay lại trang chính -->
      <a href="product" class="btn btn-primary">Quay lại trang chủ</a>
      <!-- Nút để tạo khóa mới -->
      <a href="KeyGenerationServlet" class="btn btn-primary">Tạo khóa mới</a>
    </div>
  </div>
</div>
</body>
</html>
