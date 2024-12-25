<%@ page import="java.util.List" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%--<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'vi_VN'}" />--%>
<fmt:setBundle basename="messages" />

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
<style>
    .dropdown:hover .dropdown-menu {
        display: block;
        margin-top: 0;
    }

</style>
<body>
<%
    Locale locale = (Locale) session.getAttribute("locale");
    if (locale == null) {
        locale = new Locale("vi", "VN"); // Ngôn ngữ mặc định là Tiếng Việt
    }
    ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
%>

<div class="dropdown me-3 bg-secondary">
    <button
            class="btn btn-light dropdown-toggle"
            type="button"
            id="languageDropdown"
            data-mdb-toggle="dropdown"
            aria-expanded="false"
    >Chọn ngôn ngữ
        <%= locale.getDisplayLanguage(locale) %>
        <c:out value="${bundle.getString('home.title')}" /> <!-- Hiển thị ngôn ngữ hiện tại -->
    </button>
    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="languageDropdown">
        <li><a class="dropdown-item" href="changeLanguage?lang=vi">Tiếng Việt</a></li>
        <li><a class="dropdown-item" href="changeLanguage?lang=en">English</a></li>
    </ul>
</div>


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
            <a class="navbar-brand" href="product"><%= bundle.getString("home.title") %></a>
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="product"><%= bundle.getString("menu.home") %></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="listproduct"><%= bundle.getString("menu.productList") %></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="viewCart.jsp"><%= bundle.getString("menu.cart") %></a>
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
                    <div class="dropdown me-5">
                        <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
                           data-mdb-toggle="dropdown" aria-expanded="false">
                            Xin chào, ${sessionScope.user.username}
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userDropdown">
                            <!-- Hiển thị thông tin cá nhân -->
                            <li>
                                <a class="dropdown-item" href="userProfileServlet">
                                    <i class="fas fa-user-circle me-2"></i>Thông tin cá nhân
                                </a>
                            </li>
                            <!-- Nút đăng xuất -->
                            <li>
                                <c:if test="${sessionScope.user.role == 1}">
                                    <a href="admin.jsp" class="dropdown-item">
                                        <i class="fas fa-user-circle me-2"></i>Go to Admin Page
                                    </a>
                                </c:if>
                            </li>
                            <li>
                                <a href="CustomerOrderServlet" class="dropdown-item">
                                    <i class="fas fa-user-circle me-2"></i>Đơn hàng
                                </a>
                            </li>
                            <li>
                                <a href="CustomerHistoryOrder.jsp" class="dropdown-item">
                                    <i class="fas fa-user-circle me-2"></i>Lịch sử đặt hàng
                                </a>
                            </li>

                            <li>
                                <a class="dropdown-item text-danger" href="LogoutServlet">
                                    <i class="fas fa-sign-out-alt me-2"></i>Đăng xuất
                                </a>
                            </li>
                        </ul>
                    </div>
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
<%--index.jsp--%>
<div class="container">
    <!-- Products -->
    <section>
        <div class="text-center">
            <div class="row">
                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-warning">${errorMessage}</div>
                </c:if>

                <c:forEach var="product" items="${products}">
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


</body>
<!-- MDB -->
<script
        type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.2/mdb.umd.min.js"
></script>
</html>