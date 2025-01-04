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
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid #ddd; padding: 8px; }
        th { background-color: #f2f2f2; }
        h1 { text-align: center; }
        .search-bar { margin: 20px auto; width: 50%; display: flex; }
        .search-bar input { flex: 1; margin-right: 10px; }
    </style>
</head>
<body>
<header>
    <nav class="navbar navbar-expand-lg navbar-light bg-body-tertiary">
        <div class="container-fluid">
            <a class="navbar-brand" href="index.jsp">Trang chủ</a>
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item"><a class="nav-link active" href="product">Danh sách sản phẩm</a></li>
                    <li class="nav-item"><a class="nav-link" href="viewCart.jsp">Giỏ hàng</a></li>
                </ul>
            </div>
        </div>
    </nav>
</header>

<h1>Danh sách sản phẩm</h1>

<!-- Danh sách sản phẩm -->
<div class="container">
    <div class="row">
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-warning">${errorMessage}</div>
        </c:if>

        <c:forEach var="product" items="${listproducts}">
            <div class="col-lg-3 col-md-4 col-sm-6 mb-4">
                <div class="card">
                    <img src="${pageContext.request.contextPath}/image/${product.productImage}" class="card-img-top" alt="${product.productName}" style="max-height: 200px; object-fit: cover;">
                    <div class="card-body">
                        <h5 class="card-title"><c:out value="${product.productName}" /></h5>
                        <p>Giá: <c:out value="${currencySymbol}" /><fmt:formatNumber value="${product.productPrice * exchangeRate}" maxFractionDigits="0" /></p>
                        <a href="productDetail?productId=${product.productId}" class="btn btn-info">Xem chi tiết</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<!-- Thanh điều hướng dưới dòng "Danh sách sản phẩm" -->
<div class="container mt-4">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item"><a class="nav-link active" href="#">New & Featured</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Men</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Women</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Kids</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Sale</a></li>
            </ul>
            <form class="d-flex">
                <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                <button class="btn btn-outline-secondary" type="submit"><i class="fas fa-search"></i></button>
            </form>
            <div class="d-flex align-items-center ms-3">
                <a href="#" class="me-3"><i class="far fa-heart"></i></a>
                <a href="#"><i class="fas fa-shopping-bag"></i></a>
            </div>
        </div>
    </nav>
</div>
</body>
</html>
