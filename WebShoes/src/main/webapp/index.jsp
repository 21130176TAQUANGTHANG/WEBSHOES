<%@ page import="java.util.List" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="LoginUser.User" %>
<%@ page import="LoginUser.GoogleAccount" %>
<%@ page import="LoginUser.AccountFF" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
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
                        <a class="nav-link" href="viewCart.jsp">gio hang</i> </a>


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

                <%
                    User username = (User) session.getAttribute("user");
                    GoogleAccount googleUser = (GoogleAccount) session.getAttribute("googleUser"); // Thay đổi thành GoogleAccount
                    AccountFF facebookUser = (AccountFF) session.getAttribute("facebookUser"); // Lấy đối tượng AccountFF

                    if (username != null) {
                %>
                <span class="navbar-text me-3">Xin chào: <%=username.getUsername()%></span>
                <a href="LogoutServlet" class="btn btn-danger">Logout</a>
                <a href="OrderHistory">Lịch sử đơn hàng</a>
                <%
                } else if (googleUser != null) {
                %>
                <span class="navbar-text me-3">Xin chào: <%=googleUser.getName()%></span>
                <a href="LogoutServlet" class="btn btn-danger">Logout</a>
                <a href="OrderHistory">Lịch sử đơn hàng</a>
                <%
                } else if (facebookUser != null) { // Kiểm tra đối tượng facebookUser
                %>
                <span class="navbar-text me-3">Xin chào: <%=facebookUser.getName()%></span> <!-- Lấy tên từ đối tượng -->
                <a href="LogoutServlet" class="btn btn-danger">Logout</a>
                <a href="OrderHistory">Lịch sử đơn hàng</a>
                <%
                } else {
                %>
                <a href="Login.jsp" class="btn btn-danger">Đăng nhập</a>
                <%
                    }
                %>
            </div>
        </div>
    </nav>
</header>

<div class="container">
    <!-- Products -->
    <section>
        <div class="text-center">
            <div class="row">

                <div class="col-lg-3 col-md-4 col-sm-6 mb-4">
                    <div class="card">
                        <div class="bg-image hover-zoom ripple ripple-surface ripple-surface-light" data-mdb-ripple-color="light">
                            <img src="" class="w-100" alt="" />
                            <a href="productDetail?productId=">
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
                            <a href="" class="text-reset">
                                <h5 class="card-title mb-2"></h5>
                            </a>
                            <a href="" class="text-reset">
                                <p></p>
                            </a>
                            <h6 class="mb-3 price"></h6>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </section>


    <!-- Pagination -->
    <nav aria-label="Page navigation example" class="d-flex justify-content-center mt-3">
        <ul class="pagination">
            <li class="page-item disabled">
                <a class="page-link" href="#" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
            <li class="page-item active"><a class="page-link" href="#">1</a></li>
            <li class="page-item"><a class="page-link" href="#">2</a></li>
            <li class="page-item"><a class="page-link" href="#">3</a></li>
            <li class="page-item"><a class="page-link" href="#">4</a></li>
            <li class="page-item"><a class="page-link" href="#">5</a></li>
            <li class="page-item">
                <a class="page-link" href="#" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
    </nav>
    <!-- Pagination -->
</div>


</body>
<!-- MDB -->
<script
        type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.2/mdb.umd.min.js"
></script>
</html>