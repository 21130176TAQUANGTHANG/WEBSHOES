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
    <nav class="navbar navbar-expand-lg navbar-light bg-body-tertiary">
        <div class="container-fluid">
            <a class="navbar-brand" href="index.jsp">Trang chủ</a>
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
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
                <li class="nav-item"><a class="nav-link" href="#">Nike</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Adidas</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Others</a></li>
            </ul>
            <form class="d-flex">
                <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                <button class="btn btn-outline-secondary" type="submit"><i class="fas fa-search"></i></button>
            </form>
        </div>
    </nav>
</div>

<!-- Danh sách sản phẩm -->
<div class="container product-container">
    <h2 class="text-center mb-4">Danh sách sản phẩm</h2>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-warning text-center">
                ${errorMessage}
        </div>
    </c:if>

    <div class="row">
        <c:forEach var="product" items="${listproducts}">
            <div class="col-md-4">
                <div class="card product-card">
                    <img src="${pageContext.request.contextPath}/images/${product.productImage}" class="card-img-top" alt="${product.productName}">
                    <div class="card-body">
                        <h5 class="card-title">${product.productName}</h5>
                        <p class="card-text">${product.productDescription}</p>
                        <p class="card-text">
                            Giá:
                            <strong>
                                <c:out value="${currencySymbol}" />
                                <fmt:formatNumber value="${product.productPrice * exchangeRate}" type="number" maxFractionDigits="0" />
                            </strong>
                        </p>
                        <p class="card-text">Số lượng: ${product.productQuantity}</p>
                        <a href="productDetail.jsp?id=${product.productId}" class="btn btn-primary">Chi tiết</a>
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
