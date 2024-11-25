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
  <c:choose>
    <c:when test="${not empty sessionScope.cart && not empty sessionScope.cart.data}">
      <div class="alert alert-success text-center">
        <h4>Đặt Hàng Thành Công!</h4>
        <p>Cảm ơn bạn đã mua hàng. Đơn hàng của bạn sẽ được xử lý và giao hàng sớm nhất có thể.</p>
      </div>
      <table class="table table-bordered">
        <thead class="thead-dark">
        <tr>
          <th>#</th>
          <th>Tên Sản Phẩm</th>
          <th>Số Lượng</th>
          <th>Giá</th>
          <th>Thành Tiền</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="cartProductEntry" items="${sessionScope.cart.data}" varStatus="status">
          <c:set var="cartProduct" value="${cartProductEntry.value}" />
          <c:set var="product" value="${cartProduct.product}" />
          <tr>
            <td>${status.count}</td>
            <td>${product.productName}</td>
            <td>${cartProduct.quantity}</td>
            <td><fmt:formatNumber value="${product.price}" type="currency" currencySymbol="₫"/></td>
            <td><fmt:formatNumber value="${cartProduct.subtotal}" type="currency" currencySymbol="₫"/></td>
          </tr>
        </c:forEach>
        </tbody>
        <tfoot>
        <tr>
          <th colspan="4" class="text-right">Tổng Cộng:</th>
          <th><fmt:formatNumber value="${sessionScope.cart.totalPrice}" type="currency" currencySymbol="₫"/></th>
        </tr>
        </tfoot>
      </table>
      <div class="text-center mt-4">
        <a href="product" class="btn btn-primary">Tiếp Tục Mua Sắm</a>
      </div>
    </c:when>
    <c:otherwise>

      <div class="alert alert-warning text-center">
        <h4>Cảm ơn bạn đã mua hàng. Đơn hàng của bạn sẽ được xử lý và giao hàng sớm nhất có thể.!</h4>
        <a href="product" class="btn btn-primary">Tiếp Tục Mua Sắm</a>
      </div>

    </c:otherwise>
  </c:choose>
</div>
</body>
</html>
