<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>editOrder.jsp</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="admin.jsp">Admin Dashboard</a>
    </div>
</nav>

<div class="container mt-4">
    <h2>Chỉnh sửa thông tin đơn hàng</h2>
    <c:if test="${empty order}">
        <div class="alert alert-danger">
            Không tìm thấy thông tin đơn hàng.
        </div>
    </c:if>
    <c:if test="${not empty order}">

        <form action="UpdateOrderServlet" method="post">
            <!-- Đặt ID của đơn hàng để gửi về servlet -->
            <input type="hidden" name="orderId" value="${order.orderId}">

            <!-- Thông tin người nhận -->
            <h3>Thông tin người nhận</h3>
            <div class="mb-3">
                <label for="name" class="form-label">Tên:</label>
                <input type="text" class="form-control" name="name" value="${order.name}" required>
            </div>
            <div class="mb-3">
                <label for="address" class="form-label">Địa chỉ:</label>
                <input type="text" class="form-control" name="address" value="${order.address}" required>
            </div>
            <div class="mb-3">
                <label for="phone" class="form-label">Số điện thoại:</label>
                <input type="text" class="form-control" name="phone" value="${order.phone}" required>
            </div>
            <div class="mb-3">
                <label for="notes" class="form-label">Ghi chú:</label>
                <textarea class="form-control" name="notes" rows="3">${order.notes}</textarea>
            </div>
            <div class="mb-3">
                <label for="status" class="form-label">Trạng thái:</label>
                <select class="form-select" name="status">
                    <option value="Chờ xác nhận" ${order.status == 'Chờ xác nhận' ? 'selected' : ''}>Chờ xác nhận</option>
                    <option value="Xác nhận thành công" ${order.status == 'Xác nhận thành công' ? 'selected' : ''}>Xác nhận thành công</option>
                </select>
            </div>

            <!-- Chi tiết đơn hàng -->
            <h4>Chi tiết đơn hàng</h4>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>Sản phẩm</th>
                    <th>Giá</th>
                    <th>Số lượng</th>
                    <th>Kích thước</th>
                    <th>Tổng tiền</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${order.orderItems}" varStatus="status">
                    <tr>
                        <!-- Lưu ID sản phẩm để gửi về -->
                        <input type="hidden" name="productIds" value="${item.productId}">
                        <td>${item.productName}</td>
                        <td class="item-price">${item.price}</td>
                        <td>
                            <input type="number" class="form-control item-quantity"
                                   name="quantities"
                                   value="${item.quantity}"
                                   min="1"
                                   required
                                   data-index="${status.index}"
                                   data-price="${item.price}">
                        </td>
                        <td><input type="text" class="form-control" name="sizes" value="${item.size}" required></td>
                        <td>
                            <input type="hidden" name="subtotals" class="item-subtotal" value="${item.subtotal}">
                            <span id="subtotal-${status.index}">${item.subtotal}</span>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>


            <!-- Nút cập nhật -->
            <button type="submit" class="btn btn-primary">Cập nhật đơn hàng</button>
        </form>
    </c:if>

</div>
<!-- Hiển thị nhật ký -->
<c:if test="${not empty logMessage}">
    <div class="alert alert-info">
        <strong>Log:</strong> ${logMessage}
    </div>
</c:if>
<!-- Bootstrap JS Bundle (with Popper) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Lắng nghe sự kiện thay đổi số lượng
    document.querySelectorAll('.item-quantity').forEach(item => {
        item.addEventListener('input', function() {
            let quantity = parseInt(this.value);
            let price = parseFloat(this.getAttribute('data-price'));
            let subtotal = quantity * price;
            let subtotalField = document.getElementById('subtotal-' + this.getAttribute('data-index'));
            let subtotalInput = this.closest('tr').querySelector('.item-subtotal');

            // Cập nhật subtotal trên giao diện
            subtotalField.innerText = subtotal.toFixed(2);
            subtotalInput.value = subtotal.toFixed(2);

            // Tính lại tổng tiền đơn hàng
            updateTotalPrice();
        });
    });

    // Tính lại tổng tiền
    function updateTotalPrice() {
        let totalPrice = 0;
        document.querySelectorAll('.item-subtotal').forEach(subtotal => {
            totalPrice += parseFloat(subtotal.value);
        });
        document.getElementById('total-price').innerText = totalPrice.toFixed(2);
    }
</script>


</body>
</html>
