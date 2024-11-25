<%@ page import="Cart.Cart" %>
<%@page import="Cart.CartProduct" %>
<%@ page import="java.util.Map" %>
<%@ page import="Product.Product" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%--
  Created by IntelliJ IDEA.
  User: thang
  Date: 10/13/2024
  Time: 1:44 PM
  To change this template use File | Settings | File Templates.
--%>
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

                <c:if test="${sessionScope.user != null}">
                    <h3>Xin chào, ${sessionScope.user.username}</h3>
                    <a href="LogoutServlet" class="btn btn-danger">Logout</a>

                    <!-- Kiểm tra nếu role = 1 (admin) thì hiển thị nút truy cập admin.jsp -->
                    <c:if test="${sessionScope.user.role == 1}">
                        <a href="admin.jsp" class="btn btn-primary">Go to Admin Page</a>
                    </c:if>
                </c:if>


                <c:if test="${sessionScope.facebookUser !=null}">
                    <h3>${sessionScope.facebookUser.name}</h3>
                    <a href="LogoutServlet" class="btn btn-danger">Logout</a>
                </c:if>

                <c:if test="${sessionScope.googleUser !=null}">
                    <h3>${sessionScope.googleUser.name}</h3>
                    <a href="LogoutServlet" class="btn btn-danger">Logout</a>
                </c:if>

                <c:if test="${sessionScope.user ==null}">
                    <a href="Login.jsp">Dang nhap</a>
                </c:if>
            </div>
        </div>
    </nav>
</header>

<div class="container mt-5">
    <h2 class="text-center">Giỏ hàng của bạn</h2>

    <%
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) cart = new Cart();
        Map<Integer, CartProduct> cartItems = cart.getData();

        if (cartItems.isEmpty()) {
    %>
    <div class="alert alert-warning" role="alert">Giỏ hàng của hiện bạn trống. Vui lòng thêm sản phẩm vào giỏ hàng</div>
    <%
    } else {
        int totalPriceForAllProducts = 0;
    %>
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
        <%
            NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));

            for (Map.Entry<Integer, CartProduct> entry : cartItems.entrySet()) {
                CartProduct cartProduct = entry.getValue();
                Product product = cartProduct.getProduct();
                totalPriceForAllProducts += cartProduct.getSubtotal();
        %>
        <tr>
            <form action="RemoveCart" method="post">
                <td><img src="<%= product.getProductImage() %>" alt="<%= product.getProductName() %>" class="img-fluid" style="max-height: 100px;"></td>
                <td><%= product.getProductName() %></td>
                <td><%= cartProduct.getSize() %></td>
                <td><%= numberFormat.format(product.getProductPrice()) %></td>
                <td>
                    <input type="number" name="quantity" value="<%= cartProduct.getQuantity() %>" min="1" class="form-control" style="width: 80px;"
                           onchange="updateCart(<%= product.getProductId() %>, this.value)">
                </td>
                <td><%= numberFormat.format(cartProduct.getSubtotal()) %> đ</td>

                <td>
                    <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                    <button class="remove" type="submit" style="border: none; background-color: #FAFAFA;"><i class="bi bi-x-octagon text-danger fs-4"></i></button>
                </td>

            </form>
        </tr>
        <% } %>
        </tbody>
    </table>

    <div class="total">
        <p class="font-weight-bold">Tổng giá cho tất cả sản phẩm:
            <span id="totalPrice"><%= numberFormat.format(totalPriceForAllProducts) %></span> đ
        </p>
    </div>

    <%
        }
    %>

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
