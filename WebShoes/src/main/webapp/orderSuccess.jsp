<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Cart.Cart" %>
<%@page import="Cart.CartProduct" %>
<%@ page import="Product.Product" %>
<%@ page import="java.util.Map" %>
<%@ page import="DBConnect.DBDAO" %>

<html>
<head>
    <title>Title</title>

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
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css">

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

        <c:if test="${sessionScope.user != null}">
          <h3>Xin chào, ${sessionScope.user.username}</h3>
          <a href="LogoutServlet" class="btn btn-danger">Logout</a>

          <!-- Kiểm tra nếu role = 1 (admin) thì hiển thị nút truy cập admin.jsp -->
          <c:if test="${sessionScope.user.role == 1}">
            <a href="admin.jsp" class="btn btn-primary">Go to Admin Page</a>
          </c:if>

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
  <%
    DBDAO dbdao = new DBDAO();
    Cart cart = (Cart) session.getAttribute("cart");
    if (cart != null && !cart.getData().isEmpty()) {
      for (Map.Entry<Integer, CartProduct> entry : cart.getData().entrySet()) {
        CartProduct cartProduct = entry.getValue();
        Product product = cartProduct.getProduct();
        int quality = product.getProductId();
        int quantityOrdered = cartProduct.getQuantity();

        // Cập nhật số lượng sản phẩm sau khi đặt hàng
        int remainingQuantity = dbdao.updateProductAfterOrder(quality, quantityOrdered);
  %>
  <div class="alert alert-success text-center">
    <h4>Đặt Hàng Thành Công!</h4>
    <p>Cảm ơn bạn đã mua hàng. Đơn hàng của bạn sẽ được xử lý và giao hàng sớm nhất có thể.</p>
    <h5>Sản phẩm: <%= product.getProductName() %></h5>
    <p>Số lượng đã đặt: <%= quantityOrdered %></p>
    <p>Còn lại trong kho: <strong><%= remainingQuantity %></strong></p>
    <a href="product" class="btn btn-primary">Tiếp Tục Mua Sắm</a>
  </div>
  <%
    }
  } else {
  %>
  <div class="alert alert-warning text-center">
    <h4>Không có sản phẩm nào trong giỏ hàng!</h4>
    <a href="product" class="btn btn-primary">Tiếp Tục Mua Sắm</a>
  </div>
  <%
    }
  %>
</div>
</body>
</html>
