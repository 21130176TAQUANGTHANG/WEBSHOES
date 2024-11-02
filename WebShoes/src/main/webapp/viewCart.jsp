<%--
  Created by IntelliJ IDEA.
  User: thang
  Date: 10/13/2024
  Time: 1:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>cart</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
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

<div class="container mt-5">
    <h2 class="text-center">Giỏ hàng của bạn</h2>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Hình ảnh</th>
            <th>Sản phẩm</th>
            <th>Size</th>
            <th>Giá</th>
            <th>Số lượng</th>
            <th>Tổng</th>
            <th></th>
        </tr>
        </thead>
        <tbody>

            <form action="RemoveCart" method="post">
                <td><img src="" alt="" class="img-fluid" style="max-height: 100px;"></td>
                <td></td>
                <td></td>
                <td></td>
                <td>
                    <input type="number" name="quantity" value="" min="1" class="form-control" style="width: 80px;">
                </td>
                <td>đ</td>

                <td>
                    <input type="hidden" name="productId" value="">
                    <button class="remove" type="submit" style="border: none; background-color: #FAFAFA;"><i class="bi bi-x-octagon text-danger fs-4"></i></button>
                </td>

            </form>
        </tr>
        </tbody>
    </table>

    <div class="total">
        <p class="font-weight-bold">Tổng giá cho tất cả sản phẩm:
            <span id="totalPrice"></span> đ
        </p>
    </div>

    <div class="checkout">
        <a href="checkout.jsp" class="btn btn-success">Thanh Toán</a>
    </div>

    <a href="product" class="btn btn-secondary mt-3">Tiếp tục mua sắm</a>
</div>
</body>
</html>
