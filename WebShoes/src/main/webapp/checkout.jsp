<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thông Tin Giao Hàng (checkout.jsp)</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
</head>
<body>

<header>
    <nav class="navbar navbar-expand-lg navbar-light bg-body-tertiary">
        <div class="container-fluid">
            <button
                    data-mdb-collapse-init
                    class="navbar-toggler"
                    type="button"
                    data-mdb-target="#navbarTogglerDemo03"
                    aria-controls="navbarTogglerDemo03"
                    aria-expanded="false"
                    aria-label="Toggle navigation"
            >
                <i class="fas fa-bars"></i>
            </button>
            <a class="navbar-brand" href="index.jsp">Trang chủ</a>
            <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="product">Trang chủ</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="listproduct.jsp">Danh sách sản phẩm</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="viewCart.jsp">Giỏ hàng</a>
                    </li>
                </ul>
                <c:choose>
                    <c:when test="${sessionScope.user != null}">
                        <h3>Xin chào, ${sessionScope.user.username}</h3>
                        <a href="LogoutServlet" class="btn btn-danger">Logout</a>
                        <c:if test="${sessionScope.user.role == 1}">
                            <a href="admin.jsp" class="btn btn-primary">Go to Admin Page</a>
                        </c:if>
                    </c:when>
                    <c:when test="${sessionScope.facebookUser != null}">
                        <h3>${sessionScope.facebookUser.name}</h3>
                        <a href="LogoutServlet" class="btn btn-danger">Logout</a>
                    </c:when>
                    <c:when test="${sessionScope.googleUser != null}">
                        <h3>${sessionScope.googleUser.name}</h3>
                        <a href="LogoutServlet" class="btn btn-danger">Logout</a>
                    </c:when>
                    <c:otherwise>
                        <a href="Login.jsp">Đăng nhập</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </nav>
</header>

<div class="container mt-5">
    <h2 class="text-center mb-4 text-primary">Thông Tin Giao Hàng</h2>
    <form action="ProcessCheckout" method="post">
        <div class="row">
            <!-- Thông Tin Người Mua -->
            <div class="col-md-6">
                <div class="form-group mb-3">
                    <label for="name">Họ và Tên</label>
                    <input type="text" class="form-control" id="name" name="name" placeholder="Nhập họ và tên" required>
                </div>
                <div class="form-group mb-3">
                    <label for="address">Địa Chỉ</label>
                    <input type="text" class="form-control" id="address" name="address" placeholder="Nhập địa chỉ" required>
                </div>
                <div class="form-group mb-3">
                    <label for="phone">Số Điện Thoại</label>
                    <input type="tel" class="form-control" id="phone" name="phone" placeholder="Nhập số điện thoại" required>
                </div>
            </div>

            <!-- Giỏ Hàng -->
            <div class="col-md-6">
                <h4 class="mb-4 text-secondary">Giỏ Hàng</h4>
                <c:if test="${not empty sessionScope.cart.data}">
                    <c:forEach var="cartProduct" items="${sessionScope.cart.data}">
                        <div class="cart-item d-flex justify-content-between align-items-center mb-3">
                            <div class="cart-item-name">${cartProduct.value.product.productName}</div>
                            <div class="cart-item-price text-success">
                                <fmt:formatNumber value="${cartProduct.value.product.productPrice}" type="currency" currencySymbol="₫"/>
                            </div>
                        </div>
                        <div class="cart-item d-flex justify-content-between align-items-center mb-3">
                            <div class="cart-item-name">Size Giày: ${cartProduct.value.size}</div>
                            <div class="cart-item-quantity">Số lượng: ${cartProduct.value.quantity}</div>
                        </div>
                    </c:forEach>
                    <div class="total-price d-flex justify-content-between align-items-center">
                        <span><strong>Tổng Cộng</strong></span>
                        <span class="text-danger"><strong>
              <fmt:formatNumber value="${sessionScope.cart.totalPrice}" type="currency" currencySymbol="₫"/>
            </strong></span>
                    </div>
                </c:if>
                <c:if test="${empty sessionScope.cart.data}">
                    <div class="alert alert-warning" role="alert">Giỏ hàng của bạn trống.</div>
                </c:if>
            </div>
        </div>

        <!-- Nội Dung và Thanh Toán -->
        <div class="form-group mb-4">
            <label for="notes">Nội Dung</label>
            <textarea class="form-control" id="notes" name="notes" rows="3" placeholder="Ghi chú (nếu có)"></textarea>
        </div>


        <button type="submit" class="btn btn-primary btn-block">Thanh Toán</button>
    </form>
</div>

</body>
</html>
