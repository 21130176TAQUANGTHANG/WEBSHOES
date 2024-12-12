<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %><%--
  Created by IntelliJ IDEA.
  User: thang
  Date: 12/10/2024
  Time: 3:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            <a class="navbar-brand" href="index.jsp"><%= bundle.getString("home.title") %></a>
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="product"><%= bundle.getString("menu.home") %></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="listproduct.jsp"><%= bundle.getString("menu.productList") %></a>
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
                            <a class="dropdown-item" href="userProfile.jsp">
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
    <h2>Thông tin khách hàng</h2>
    <div class="card mb-3">
        <div class="card-body">
            <h5 class="card-title">Thông tin cá nhân</h5>
            <p><strong>Tên:</strong> ${user.username}</p>
            <p><strong>Email:</strong> ${user.email}</p>
            <p><strong>Điện thoại:</strong> ${user.phone}</p>
            <p><strong>Địa chỉ:</strong> ${user.address}</p>
        </div>
    </div>

    <!-- Nút chuyển đổi -->
    <div class="d-flex mb-3">
        <button class="btn btn-primary me-2" onclick="showPendingOrders()">Chờ xác nhận</button>
        <button class="btn btn-secondary" onclick="showConfirmedOrders()">Đã xác nhận</button>
    </div>

    <!-- Bảng đơn hàng chưa xác nhận -->
    <div id="pending-orders" style="display: block;">
        <h2>Đơn hàng chưa xác nhận</h2>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>OrderId</th>
                <th>Ngày đặt hàng</th>
                <th>Tổng tiền</th>
                <th>Ghi chú</th>
                <th>Trạng thái</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${empty pendingOrders}">
                <tr>
                    <td colspan="5" style="text-align: center;">Không có đơn hàng nào chưa xác nhận.</td>
                </tr>
            </c:if>
            <c:if test="${not empty pendingOrders}">
                <c:forEach var="o" items="${pendingOrders}">
                    <tr>
                        <td>${o.orderId}</td>
                        <td><fmt:formatDate value="${o.orderDate}" pattern="dd/MM/yyyy HH:mm" /></td>
                        <td><fmt:formatNumber value="${o.totalPrice}" type="currency" currencySymbol="₫" /></td>
                        <td>${o.notes}</td>
                        <td>${o.status}</td>
                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>
    </div>

    <!-- Bảng đơn hàng đã xác nhận -->
    <div id="confirmed-orders" style="display: none;">
        <h2>Đơn hàng đã xác nhận</h2>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>OrderId</th>
                <th>Ngày đặt hàng</th>
                <th>Tổng tiền</th>
                <th>Ghi chú</th>
                <th>Trạng thái</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${empty confirmedOrders}">
                <tr>
                    <td colspan="5" style="text-align: center;">Không có đơn hàng nào đã xác nhận.</td>
                </tr>
            </c:if>
            <c:if test="${not empty confirmedOrders}">
                <c:forEach var="o" items="${confirmedOrders}">
                    <tr>
                        <td>${o.orderId}</td>
                        <td><fmt:formatDate value="${o.orderDate}" pattern="dd/MM/yyyy HH:mm" /></td>
                        <td><fmt:formatNumber value="${o.totalPrice}" type="currency" currencySymbol="₫" /></td>
                        <td>${o.notes}</td>
                        <td>${o.status}</td>
                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>
    </div>
</div>
<script>
    function showPendingOrders() {
        document.getElementById("pending-orders").style.display = "block";
        document.getElementById("confirmed-orders").style.display = "none";
    }

    function showConfirmedOrders() {
        document.getElementById("pending-orders").style.display = "none";
        document.getElementById("confirmed-orders").style.display = "block";
    }
</script>


</body>
</html>
