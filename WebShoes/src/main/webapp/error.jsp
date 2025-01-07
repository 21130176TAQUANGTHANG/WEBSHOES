<%--
  Created by IntelliJ IDEA.
  User: thang
  Date: 12/23/2024
  Time: 11:21 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Error</title>

  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
<div class="container mt-5">
  <div class="alert alert-danger">
    <h4>Đã xảy ra lỗi:</h4>
    <p>${errorMessage}</p>
    <a href="admin.jsp" class="btn btn-primary">Quay lại trang chính</a>
  </div>
</div>
</body>
</html>
