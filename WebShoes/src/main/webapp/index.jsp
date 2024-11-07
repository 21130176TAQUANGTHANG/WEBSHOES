    <%@ page import="java.util.List" %>
    <%@ page import="java.text.NumberFormat" %>
    <%@ page import="java.util.Locale" %>
    <%@ page import="LoginUser.User" %>
    <%@ page import="LoginUser.GoogleAccount" %>
    <%@ page import="LoginUser.AccountFF" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>
    <head>
        <title>index.jsp</title>
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
                </div>
            </div>
        </nav>
    </header>

    <div class="container">
        <!-- Products -->
        <section>
            <div class="text-center">
                <div class="row">

                    <c:forEach var="product" items="${products}">
                        <div class="col-lg-3 col-md-4 col-sm-6 mb-4">
                            <div class="card">
                                <div class="bg-image hover-zoom ripple ripple-surface ripple-surface-light" data-mdb-ripple-color="light">
                                    <img src="${product.productImage}" class="w-100" alt="${product.productName}" />
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
                                        <h5 class="card-title mb-2"><c:out value="${product.productName}" /></h5>
                                    </a>
                                    <a href="productDetail?productId=${product.productId}" class="text-reset">
                                        <p><c:out value="${product.productName}" /></p>
                                    </a>
                                    <h6 class="mb-3 price">${product.productPrice}₫</h6>
                                </div>
                            </div>
                        </div>
                    </c:forEach>


                </div>
            </div>
        </section>
    </div>


    </body>
    <!-- MDB -->
    <script
            type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.2/mdb.umd.min.js"
    ></script>
    </html>