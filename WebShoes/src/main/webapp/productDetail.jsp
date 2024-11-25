<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
  <title>Chi tiết sản phẩm</title>
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
  <div class="row">
    <!-- Phần hình ảnh sản phẩm -->
    <div class="col-md-6">
      <img src="${product.productImage}" alt="${product.productName}" class="img-fluid">
    </div>

    <!-- Phần thông tin chi tiết sản phẩm -->
    <div class="col-md-6">
      <h4 class="mb-3">${product.productName}</h4>
      <p>Mã SP: <strong>${product.productId}</strong></p>
      <p class="text-danger h4 price"><strong>${product.productPrice} ₫</strong></p>


      <form action="AddCartServlet" method="get" onsubmit="return checkSizeSelection()">
        <!-- Thông tin sản phẩm -->
        <input type="hidden" name="id" value="${product.productId}">

        <!-- Chọn size giày -->
        <h5>CHỌN SIZE GIÀY</h5>
        <div class="btn-group mb-3" role="group" aria-label="Chọn size giày">
          <input type="radio" class="btn-check" name="size" id="size40" value="40" autocomplete="off">
          <label class="btn btn-outline-secondary" for="size40">40</label>

          <input type="radio" class="btn-check" name="size" id="size40.5" value="40.5" autocomplete="off">
          <label class="btn btn-outline-secondary" for="size40.5">40.5</label>

          <input type="radio" class="btn-check" name="size" id="size41" value="41" autocomplete="off">
          <label class="btn btn-outline-secondary" for="size41">41</label>

          <input type="radio" class="btn-check" name="size" id="size42" value="42" autocomplete="off">
          <label class="btn btn-outline-secondary" for="size42">42</label>

          <input type="radio" class="btn-check" name="size" id="size42.5" value="42.5" autocomplete="off">
          <label class="btn btn-outline-secondary" for="size42.5">42.5</label>

          <input type="radio" class="btn-check" name="size" id="size43" value="43" autocomplete="off">
          <label class="btn btn-outline-secondary" for="size43">43</label>

          <input type="radio" class="btn-check" name="size" id="size44" value="44" autocomplete="off">
          <label class="btn btn-outline-secondary" for="size44">44</label>
        </div>

        <!-- Chọn số lượng -->
        <div class="mb-3">
          <label for="quantity" class="form-label">Số lượng:</label>
          <div class="input-group">
            <button class="btn btn-outline-secondary" type="button" onclick="decreaseQuantity()">-</button>
            <input type="number" id="quantity" name="quantity" class="form-control text-center" value="1" min="1" max="${product.productQuantity}">
            <button class="btn btn-outline-secondary" type="button" onclick="increaseQuantity()">+</button>
          </div>
        </div>

        <!-- Nút thêm vào giỏ hàng -->
        <button type="submit" class="btn btn-warning btn-lg w-100 mb-3">Thêm vào giỏ</button>
        <button type="submit" class="btn btn-danger btn-lg w-100">Mua Ngay</button>

      </form>

      <!-- Liên hệ tư vấn -->
      <p class="mt-3">
        Hoặc đặt mua: <strong class="text-danger">01234567898</strong> (Tư vấn miễn phí)
      </p>
    </div>
  </div>
</div>
<script>
  function checkSizeSelection() {
    const sizeSelected = document.querySelector('input[name="size"]:checked');
    if (!sizeSelected) {
      alert("Vui lòng chọn size giày trước khi thêm vào giỏ hàng.");
      return false;
    }
    return true;
  }

  function decreaseQuantity() {
    const quantityInput = document.getElementById('quantity');
    if (quantityInput.value > 1) {
      quantityInput.value--;
    }
  }

  function increaseQuantity() {
    const quantityInput = document.getElementById('quantity');
    const maxQuantity = quantityInput.getAttribute('max');
    if (quantityInput.value < maxQuantity) {
      quantityInput.value++;
    }
  }

  // Hàm để định dạng giá tiền
  function formatPrice() {
    const priceElements = document.querySelectorAll('.price'); // Chọn tất cả phần tử có class "price"

    priceElements.forEach(element => {
      let price = element.innerText.replace('₫', '').trim(); // Lấy giá trị, loại bỏ "₫"
      let formattedPrice = parseInt(price).toLocaleString(); // Định dạng giá trị với dấu phẩy
      element.innerText = formattedPrice + '₫'; // Cập nhật lại giá trị với dấu phẩy và "₫"
    });
  }

  // Gọi hàm khi trang được tải
  window.onload = formatPrice;
</script>

</body>
</html>
