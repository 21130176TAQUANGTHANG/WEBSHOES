<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Danh sách sản phẩm</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet" />
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.2/mdb.min.css" rel="stylesheet" />
    <style>
        .navbar-nav li {
            margin-right: 15px;
        }
        .product-card img {
            height: 200px;
            object-fit: cover;
        }
        .product-container {
            margin-top: 30px;
        }
        .product-card {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>

<!-- Header -->
<header>
    <!-- Thanh điều hướng chính -->
    <nav class="navbar navbar-expand-lg navbar-light bg-body-tertiary">
        <div class="container-fluid">
            <a class="navbar-brand" href="index.jsp">Trang chủ</a>
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item"><a class="nav-link active" href="listproduct.jsp">Danh sách sản phẩm</a></li>
                    <li class="nav-item"><a class="nav-link active" href="listproduct">Danh sách sản phẩm</a></li>
                    <li class="nav-item"><a class="nav-link" href="viewCart.jsp">Giỏ hàng</a></li>
                </ul>
            </div>
        </div>
    </nav>
</header>

<!-- Thanh điều hướng phụ -->
<div class="container mt-3">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item"><a class="nav-link active" href="#">New & Featured</a></li>
                <li class="nav-item"><a class="nav-link" href="filterProduct?logo=0">Nike</a></li>
                <li class="nav-item"><a class="nav-link" href="filterProduct?logo=2">Adidas</a></li>
                <li class="nav-item"><a class="nav-link" href="filterProduct?logo=3">Others</a></li>

            </ul>
            <form action="search" method="get">
                <input type="text" name="name" placeholder="Search by product name" />
                <button type="submit">Search</button>
            </form>
        </div>
    </nav>
</div>

<!-- Danh sách sản phẩm -->
<div class="container product-container">
    <h2 class="text-center mb-4">Danh sách sản phẩm</h2>

    <!-- Error message display -->
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-warning text-center">${errorMessage}</div>
    </c:if>

    <!-- Product list -->
    <div class="row">
        <c:forEach var="product" items="${listproducts}">
            <div class="col-lg-3 col-md-4 col-sm-6 mb-4">
                <div class="card product-card">
                    <img src="${pageContext.request.contextPath}/image/${product.productImage}" class="card-img-top" alt="${product.productName}">
                    <div class="card-body">
                        <h5 class="card-title"><c:out value="${product.productName}" /></h5>
                        <p>Giá: <c:out value="${currencySymbol}" /><fmt:formatNumber value="${product.productPrice * exchangeRate}" maxFractionDigits="0" /></p>
                        <p>Số lượng: <c:out value="${product.productQuantity}" /></p>
                        <a href="productDetail?productId=${product.productId}" class="btn btn-info">Xem chi tiết</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<!-- Footer -->
<footer class="bg-light text-center text-lg-start mt-4">
    <div class="text-center p-3">
        © 2024 Danh sách sản phẩm. All Rights Reserved.
    </div>
</footer>


<script src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.2/mdb.min.js"></script>
</body>
</html>
