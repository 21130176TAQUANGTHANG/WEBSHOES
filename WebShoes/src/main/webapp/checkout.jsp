<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Cart.Cart" %>
<%@page import="Cart.CartProduct" %>
<%@ page import="java.util.Map" %>
<%@ page import="Product.Product" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="LoginUser.GoogleAccount" %>
<%@ page import="LoginUser.AccountFF" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Thông Tin Giao Hàng</title>
  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
  <!-- Font Awesome -->
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
  <!-- Google Fonts -->
  <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
  <!-- Custom CSS -->
  <style>
    body {
      font-family: 'Roboto', sans-serif;
      background-color: #f4f4f4;
    }
    .container {
      background-color: #fff;
      border-radius: 10px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
      padding: 20px;
      margin-top: 20px;
    }
    .cart-item {
      background-color: #f8f9fa;
      padding: 10px;
      border-radius: 5px;
      margin-bottom: 10px;
    }
    .total-price {
      background-color: #e9ecef;
      padding: 10px;
      border-radius: 5px;
      font-size: 18px;
      font-weight: bold;
    }
    .btn-primary {
      background-color: #007bff;
      border: none;
    }
    .btn-primary:hover {
      background-color: #0056b3;
    }
    .form-group label {
      font-weight: 500;
    }
    #payment-info {
      display: none;
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
                    <%
                        Cart cart = (Cart) session.getAttribute("cart");
                        if (cart == null)
                            cart = new Cart();

                    %>

                    <li class="nav-item">
                        <a class="nav-link" href="viewCart.jsp">gio hang <span><%= cart.getTotal()%> </span></i> </a>


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
                    String username = (String) session.getAttribute("username");
                    GoogleAccount googleUser = (GoogleAccount) session.getAttribute("googleUser"); // Thay đổi thành GoogleAccount
                    AccountFF facebookUser = (AccountFF) session.getAttribute("facebookUser"); // Lấy đối tượng AccountFF

                    if (username != null) {
                %>
                <span class="navbar-text me-3">Xin chào: <%=username%></span>
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
<div class="container mt-5">
  <h2 class="text-center mb-4 text-primary">Thông Tin Giao Hàng</h2>
  <form action="ProcessCheckout" method="post">
    <div class="row">
      <!-- Thông Tin Người Mua -->
      <div class="col-md-6">
        <div class="form-group mb-3">
          <label for="name">Họ và Tên</label>
          <input type="text" class="form-control" id="name" name="name" placeholder="Nhập họ và tên" required>
        </div>
        <div class="form-group mb-3">
          <label for="address">Địa Chỉ</label>
          <input type="text" class="form-control" id="address" name="address" placeholder="Nhập địa chỉ" required>
        </div>
        <div class="form-group mb-3">
          <label for="phone">Số Điện Thoại</label>
          <input type="tel" class="form-control" id="phone" name="phone" placeholder="Nhập số điện thoại" required>
        </div>
      </div>

      <!-- Giỏ Hàng -->
      <div class="col-md-6">
        <h4 class="mb-4 text-secondary">Giỏ Hàng</h4>
<%              NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
%>
        <!-- Hiển thị sản phẩm trong giỏ hàng -->
        <%
          Cart cartt = (Cart) session.getAttribute("cart");
          if (cartt != null && !cartt.getData().isEmpty()) {
            for (CartProduct cartProduct : cartt.getData().values()) {
              Product product = cartProduct.getProduct();
        %>
        <div class="cart-item d-flex justify-content-between align-items-center mb-3">
          <div class="cart-item-name"><%= product.getProductName() %></div>
          <div class="cart-item-price text-success"><%= formatter.format(product.getProductPrice()) %></div>
        </div>
        <div class="cart-item d-flex justify-content-between align-items-center mb-3">
          <div class="cart-item-name">Size Giày: <%= cartProduct.getSize() %></div>
          <div class="cart-item-quantity">Số lượng: <%= cartProduct.getQuantity() %></div>
        </div>
        <%
          }
        %>
        <!-- Hiển thị tổng tiền -->
        <div class="total-price d-flex justify-content-between align-items-center">
          <span><strong>Tổng Cộng</strong></span>
          <span class="text-danger"><strong><%=formatter.format(cartt.getTotalPrice()) %></strong></span>
        </div>
        <% } else { %>
        <div class="alert alert-warning" role="alert">Giỏ hàng của bạn trống.</div>
        <% } %>
      </div>
    </div>

    <!-- Nội Dung và Thanh Toán -->
    <div class="form-group mb-4">
      <label for="notes">Nội Dung</label>
      <textarea class="form-control" id="notes" name="notes" rows="3" placeholder="Ghi chú (nếu có)"></textarea>
    </div>


    <div class="container mt-5">
      <h2>Phương Thức Thanh Toán</h2>
      <div class="form-group mb-4">
        <label>Chọn phương thức thanh toán</label><br>
        <div class="form-check">
          <input class="form-check-input" type="radio" name="paymentMethod" id="paymentCOD" value="cod" onchange="togglePaymentMethod()" required>
          <label class="form-check-label" for="paymentCOD">
            Thanh toán khi nhận hàng (COD)
          </label>
        </div>
        <div class="form-check">
          <input class="form-check-input" type="radio" name="paymentMethod" id="paymentBank" value="bank" onchange="togglePaymentMethod()">
          <label class="form-check-label" for="paymentBank">
            Chuyển khoản ngân hàng
          </label>
        </div>
      </div>

      <!-- Thông Tin Chuyển Khoản -->
      <div id="payment-info" class="mb-4">
        <p>Vui lòng chuyển khoản tới số tài khoản: 123456789 - Ngân hàng ACB</p>
      </div>
    </div>



    <button type="submit" class="btn btn-primary btn-block">Thanh Toán</button>
  </form>
</div>

<!-- Bootstrap JS (Optional, if needed) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>

<script>
  function togglePaymentMethod() {
    const paymentMethod = document.querySelector('input[name="paymentMethod"]:checked').value;
    const paymentInfo = document.getElementById('payment-info');

    if (paymentMethod === 'bank') {
      paymentInfo.style.display = 'block'; // Hiện thông tin chuyển khoản
    } else {
      paymentInfo.style.display = 'none'; // Ẩn thông tin chuyển khoản
    }
  }
</script>

</body>
</html>
