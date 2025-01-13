<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'vi_VN'}" />
<fmt:setBundle basename="messages" />
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
    <button class="btn btn-light dropdown-toggle">
        <fmt:message key="home.title" />
    </button>
    <ul class="dropdown-menu">
        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/productlist?lang=vi">Tiếng Việt</a></li>
        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/productlist?lang=en">English</a></li>
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


            <c:if test="${sessionScope.user != null}">
                <div class="dropdown me-5">
                    <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
                       data-mdb-toggle="dropdown" aria-expanded="false">
                        <%= bundle.getString("home.hello") %>, ${sessionScope.user.username}
                    </a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userDropdown">
                        <!-- Hiển thị thông tin cá nhân -->
                        <li>
                            <a class="dropdown-item" href="userProfileServlet">
                                <i class="fas fa-user-circle me-2"></i><%= bundle.getString("menu.information") %>
                            </a>
                        </li>
                        <!-- Nút đăng xuất -->
                        <li>
                            <c:if test="${sessionScope.user.role == 1}">
                                <a href="admin.jsp" class="dropdown-item">
                                    <i class="fas fa-user-circle me-2"></i><%= bundle.getString("admin.home") %>
                                </a>
                            </c:if>
                        </li>
                        <li>
                            <a href="CustomerOrderServlet" class="dropdown-item">
                                <i class="fas fa-user-circle me-2"></i><%= bundle.getString("menu.order") %>
                            </a>
                        </li>

                        <li>
                            <a class="dropdown-item text-danger" href="LogoutServlet">
                                <i class="fas fa-sign-out-alt me-2"></i><%= bundle.getString("menu.logout") %>
                            </a>
                        </li>
                    </ul>
                </div>
            </c:if>

            <c:if test="${sessionScope.facebookUser !=null}">
                <h3>${sessionScope.facebookUser.name}</h3>
                <a href="LogoutServlet" class="btn btn-danger"><%= bundle.getString("menu.logout") %></a>
            </c:if>

            <c:if test="${sessionScope.googleUser !=null}">
                <h3>${sessionScope.googleUser.name}</h3>
                <a href="LogoutServlet" class="btn btn-danger"><%= bundle.getString("menu.logout") %></a>
            </c:if>


            <c:if test="${sessionScope.user ==null}">
                <a href="LoginServlet"><%= bundle.getString("menu.login") %></a>
            </c:if>
        </div>
        </div>
    </nav>
</header>

<div class="container">
    <h2>Quên mật khẩu</h2>
    <h1>Quên mật khẩu</h1>
    <form action="ForgotPasswordServlet" method="post">
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>
        <button type="submit">Tiếp tục</button>
    </form>
    <p style="color: red;"><%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %></p>
</div>
</body>
</html>
