<%--
  Created by IntelliJ IDEA.
  User: thang
  Date: 12/6/2024
  Time: 1:18 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>KeyUpload.jsp</title>
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
      <a class="navbar-brand" href="product">Trang chủ</a>
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
  <h4>Bảo mật</h4>
  <c:if test="${userHasKey}">
    <h5>Đã có Key, vui lòng tải lên để xác nhận</h5>
    <form action="uploadKey" method="post" enctype="multipart/form-data">
      <div class="form-group">
        <label for="publicKeyFile">Chọn Public Key:</label>
        <input type="file" class="form-control" name="publicKeyFile" id="publicKeyFile" accept=".txt" required />
      </div>
      <button type="submit" class="btn btn-success">Tải lên Key</button>
    </form>
  </c:if>

  <!-- Thông báo -->
  <c:if test="${not empty successMessage}">
    <div class="alert alert-success mt-3">${successMessage}</div>
  </c:if>
  <c:if test="${not empty errorMessage}">
    <div class="alert alert-danger mt-3">${errorMessage}</div>
  </c:if>
</div>


</body>
</html>
