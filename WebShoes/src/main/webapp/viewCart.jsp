<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>viewCart.jsp</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
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
                <form class="d-flex input-group w-auto me-3">
                    <input
                            type="search"
                            class="form-control"
                            placeholder="Tìm kiếm"
                            aria-label="Search"
                    />
                    <button
                            data-mdb-ripple-init
                            class="btn btn-outline-primary"
                            type="button"
                            data-mdb-ripple-color="dark"
                    >
                        Tìm kiếm
                    </button>
                </form>

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
    <h2 class="text-center">Giỏ hàng của bạn</h2>

    <c:if test="${empty sessionScope.cart || sessionScope.cart.data.isEmpty()}">
        <div class="alert alert-warning" role="alert">Giỏ hàng của bạn hiện trống. Vui lòng thêm sản phẩm vào giỏ hàng</div>
    </c:if>

    <c:if test="${!empty sessionScope.cart && !sessionScope.cart.data.isEmpty()}">
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Hình ảnh</th>
                <th>Sản phẩm</th>
                <th>Size</th>
                <th>Giá</th>
                <th>Số lượng</th>
                <th>Tổng</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:set var="totalPriceForAllProducts" value="0" />
            <c:forEach var="entry" items="${sessionScope.cart.data}">
                <c:set var="cartProduct" value="${entry.value}" />
                <c:set var="product" value="${cartProduct.product}" />
                <c:set var="totalPriceForAllProducts" value="${totalPriceForAllProducts + cartProduct.subtotal}" />
                <tr>
                    <form action="RemoveCart" method="post">
                        <td><img src="${product.productImage}" alt="${product.productName}" class="img-fluid" style="max-height: 100px;"></td>
                        <td>${product.productName}</td>
                        <td>${cartProduct.size}</td>
                        <td><fmt:formatNumber value="${product.productPrice}" type="currency" currencySymbol="₫"/></td>
                        <td>
                            <input type="number" name="quantity" value="${cartProduct.quantity}" min="1" class="form-control" style="width: 80px;"
                                   onchange="updateCart(${product.productId}, this.value)">
                        </td>
                        <td><fmt:formatNumber value="${cartProduct.subtotal}" type="currency" currencySymbol="₫"/> đ</td>
                        <td>
                            <input type="hidden" name="productId" value="${product.productId}">
                            <button class="remove" type="submit" style="border: none; background-color: #FAFAFA;"><i class="bi bi-x-octagon text-danger fs-4"></i></button>
                        </td>
                    </form>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <div class="total">
            <p class="font-weight-bold">Tổng giá cho tất cả sản phẩm:
                <span id="totalPrice"><fmt:formatNumber value="${totalPriceForAllProducts}" type="currency" currencySymbol="₫"/></span> đ
            </p>
        </div>
    </c:if>

    <div class="checkout">
        <a href="checkout.jsp" class="btn btn-success">Thanh Toán</a>
    </div>

    <a href="product" class="btn btn-secondary mt-3">Tiếp tục mua sắm</a>
</div>
<script>
    function updateCart(productId, quantity) {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "UpdateCartServlet", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        // Xử lý khi nhận phản hồi từ server
        xhr.onreadystatechange = function() {
            if (xhr.readyState == 4 && xhr.status == 200) {
                // Cập nhật lại tổng giá và các thông tin liên quan
                document.getElementById("totalPrice").innerHTML = xhr.responseText;
            }
        };

        // Gửi yêu cầu với id sản phẩm và số lượng mới
        xhr.send("productId=" + productId + "&quantity=" + quantity);
    }
</script>
</body>
</html>
