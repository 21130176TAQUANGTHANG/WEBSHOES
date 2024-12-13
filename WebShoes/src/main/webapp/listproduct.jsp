<%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 11/12/2024
  Time: 6:22 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Danh sách sản phẩm</title>
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
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
        }
        th {
            background-color: #f2f2f2;
        }
        h1 {
            text-align: center;
        }
    </style>
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

                    <c:if test="${sessionScope.user !=null}">
                        <h3>${sessionScope.user.username}</h3>
                        <a href="LogoutServlet" class="btn btn-danger">Logout</a>
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

                </form>
            </div>
        </div>
    </nav>
</header>
<h1>Danh sách sản phẩm</h1>

<div style="text-align: right; margin: 10px 20px;">
    <button
            class="btn btn-primary"
            onclick="location.href='searchByCategory.jsp';"
    >
        Tìm sản phẩm theo
    </button>
<div class="container">
    <!-- Products -->
    <section>
        <div class="text-center">
            <div class="row">
                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-warning">${errorMessage}</div>
                </c:if>

                <c:forEach var="product" items="${listproducts}">
                    <div class="col-lg-3 col-md-4 col-sm-6 mb-4">
                        <div class="card">
                            <div class="bg-image hover-zoom ripple ripple-surface ripple-surface-light"
                                 data-mdb-ripple-color="light">
                                <img src="${pageContext.request.contextPath}/image/${product.productImage}" class="card-img-top" alt="${product.productName}" style="max-height: 200px; object-fit: cover;">
                                <a href="productDetail?productId=${product.productId}">
                                    <div class="mask">
                                        <div class="d-flex justify-content-start align-items-end h-100">
                                            <h5><span class="badge bg-dark ms-2">NEW</span></h5>
                                        </div>
                                    </div>
                                    <div class="hover-overlay">
                                        <div class="mask" style="background-color: rgba(251, 251, 251, 0.15);"></div>
                                    </div>
                                </a>
                            </div>
                            <div class="card-body">
                                <a href="productDetail?productId=${product.productId}" class="text-reset">
                                    <h5 class="card-title mb-2"><c:out value="${product.productName}"/></h5>
                                </a>
                                <a href="productDetail?productId=${product.productId}" class="text-reset">
                                    <p><c:out value="${product.productName}"/></p>
                                </a>
                                <h6 class="mb-3">
                                    <c:out value="${currencySymbol}"/>
                                    <fmt:formatNumber value="${product.productPrice * exchangeRate}" maxFractionDigits="0" />
                                </h6>
                            </div>
                        </div>
                    </div>
                </c:forEach>



            </div>
        </div>
    </section>
</div>
</div>
</body>
</html>
