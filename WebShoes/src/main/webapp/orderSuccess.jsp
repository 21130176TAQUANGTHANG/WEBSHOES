<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
  <title>Đặt Hàng Thành Công</title>
  <!-- Font Awesome -->
  <link
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
          rel="stylesheet"
  />
  <!-- Google Fonts -->
  <link
          href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
          rel="stylesheet"
  />
  <!-- MDB -->
  <link
          href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.2/mdb.min.css"
          rel="stylesheet"
  />
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css">
</head>
<body>
<header>
  <nav class="navbar navbar-expand-lg navbar-light bg-body-tertiary">
    <div class="container-fluid">
      <a class="navbar-brand" href="index.jsp">Trang chủ</a>
      <button class="navbar-toggler" type="button" data-mdb-toggle="collapse" data-mdb-target="#navbarTogglerDemo03">
        <i class="fas fa-bars"></i>
      </button>
      <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item"><a class="nav-link" href="listproduct.jsp">Danh sách sản phẩm</a></li>
          <li class="nav-item"><a class="nav-link" href="viewCart.jsp">Giỏ hàng</a></li>
        </ul>
        <c:choose>
          <c:when test="${not empty sessionScope.user}">
            <span class="navbar-text me-2">Xin chào, ${sessionScope.user.username}</span>
            <a href="LogoutServlet" class="btn btn-danger btn-sm">Logout</a>
          </c:when>
          <c:otherwise>
            <a href="Login.jsp" class="btn btn-primary btn-sm">Đăng nhập</a>
          </c:otherwise>
        </c:choose>
      </div>
    </div>
  </nav>
</header>
<div class="container mt-5">
    <h2 class="text-center mb-4">Thông Tin Đơn Hàng</h2>
    <c:forEach var="order" items="${orders}">
        <div class="order-details">
            <p><strong>Mã đơn hàng:</strong> ${order.orderId}</p>
            <p><strong>Ngày đặt:</strong> ${order.orderDate}</p>
            <p><strong>Trạng thái:</strong> ${order.status}</p>
            <p><strong>Tổng tiền:</strong> <fmt:formatNumber value="${order.totalPrice}" type="currency" currencySymbol="₫" /></p>
            <c:forEach var="orderItem" items="${order.orderItems}">
                <div>
                    <p><strong>Sản phẩm:</strong> ${orderItem.productName}</p>
                    <p><strong>Số lượng:</strong> ${orderItem.quantity}</p>
                    <p><strong>Giá:</strong> <fmt:formatNumber value="${orderItem.price}" type="currency" currencySymbol="₫" /></p>
                </div>
            </c:forEach>
        </div>
    </c:forEach>
</div>

</body>
</html>
