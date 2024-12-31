<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %><%--
  Created by IntelliJ IDEA.
  User: thang
  Date: 12/5/2024
  Time: 10:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Title</title>
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
        margin-top: 0; /* Tùy chỉnh để menu không bị lệch */
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
<div class="container mt-5">
    <h2 class="text-center mb-4">Thông tin cá nhân</h2>
    <div class="card">
        <div class="card-header bg-primary text-white text-center">
            <h5>Thông tin tài khoản</h5>
        </div>
        <div class="card-body">
            <div class="row">
                <!-- Cột bên trái: Bảng thông tin -->
                <div class="col-md-6">
                    <table class="table table-borderless">
                        <tbody>
                        <!-- Hiển thị thông tin từ session -->
                        <tr>
                            <th>ID:</th>
                            <td>${sessionScope.user.id}</td>
                        </tr>
                        <tr>
                            <th>Họ tên:</th>
                            <td>${sessionScope.user.username}</td>
                        </tr>
                        <tr>
                            <th>Email:</th>
                            <td>${sessionScope.user.email}</td>
                        </tr>
                        <tr>
                            <th>Số điện thoại:</th>
                            <td>${sessionScope.user.phone}</td>
                        </tr>
                        <tr>
                            <th>Địa chỉ:</th>
                            <td>${sessionScope.user.address}</td>
                        </tr>
                        <tr>
                            <th>Vai trò:</th>
                            <td>
                                <c:choose>
                                    <c:when test="${sessionScope.user.role == 1}">Quản trị viên</c:when>
                                    <c:otherwise>Người dùng</c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <!-- Cột bên phải: Nội dung bổ sung -->
                <div class="col-md-6">
                    <h4>Bảo mật </h4>
<%--                    <a href="download">Click here to download the JAR file</a>--%>

                    <c:choose>
                        <c:when test="${userHasKey}">

                            <p class="text-success">Bạn đã có key. Nếu muốn thay thế, hãy tải lên key mới.</p>
                        </c:when>
                        <c:otherwise>
                            <a href="KeyGenerationServlet"><i class="fas fa-key"></i> Tạo key</a><br>
                        </c:otherwise>
                    </c:choose>
                    <h5 style="font-size: 14px;">Nếu bạn bị lộ private key hãy nhấn vào nút dưới đây:</h5>
                    <form action="ReportKeyServlet" method="POST">
                        <button type="submit" class="btn btn-danger">Report</button>
                    </form>

                    <!-- Hiển thị thông báo nếu có -->
                    <c:if test="${not empty deletionTime}">
                        <div class="alert alert-warning mt-3">
                            Khóa của bạn sẽ bị xóa vào lúc <strong>${deletionTime}</strong>.
                        </div>
                    </c:if>

                    <!-- Hiển thị thông báo key đã xóa thành công -->
                    <c:if test="${not empty successMessage}">
                        <div class="alert alert-success mt-3">
                                ${successMessage}
                        </div>
                    </c:if>                </div>
            </div>
        </div>

        <div class="card-footer text-center">
            <a href="editUserProfile.jsp" class="btn btn-warning">Chỉnh sửa thông tin</a>
            <a href="LogoutServlet" class="btn btn-danger">Đăng xuất</a>
        </div>
    </div>
</div>
</body>
</html>
