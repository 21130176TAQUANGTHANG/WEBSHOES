<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>verifySignature.jsp</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href=admin.jsp>Admin</a>
    </div>
    <div class="container">
        <a class="navbar-brand" href="ShowLogServlet">Nhật ký hoạt động</a>
    </div>
</nav>

<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2>Danh sách Đơn hàng</h2>
    </div>

    <!-- Table -->
    <table class="table table-striped table-hover text-center align-middle">
        <thead class="table-dark">
        <tr>
            <th>OrderId</th>
            <th>UserId</th>
            <th>TotalPrice</th>
            <th>OrderDate</th>
            <th>Status</th>
            <th>Confirm</th>
            <th>Huy</th>
            <th>Verify Signature</th>
            <th>print invoice</th>
        </tr>
        </thead>
        <tbody>
        <!-- Nếu không có đơn hàng -->
        <c:if test="${empty orderList_verify}">
            <tr>
                <td colspan="7" class="text-center text-danger">Không có đơn hàng nào.</td>
            </tr>
        </c:if>

        <!-- Lặp danh sách đơn hàng -->
        <c:if test="${not empty orderList_verify}">
            <c:forEach var="o" items="${orderList_verify}">
                <tr>
                    <td>${o.orderId}</td>
                    <td>${o.userId}</td>
                    <td>${o.totalPrice}</td>
                    <td>${o.orderDate}</td>
                    <td>
                        <span class="badge ${o.status eq 'Chờ xác nhận' ? 'bg-warning' : 'bg-success'}">
                                ${o.status}
                        </span>
                    </td>
                    <td>
                        <c:if test="${o.status eq 'Chờ xác nhận'}">
                            <form method="post" action="ConfirmOrderServlet">
                                <input type="hidden" name="orderId" value="${o.orderId}"/>
                                <button type="submit" class="btn btn-success btn-sm">Xác nhận</button>
                            </form>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${o.status eq 'Chờ xác nhận'}">
                            <form action="CancelOrderServlet" method="post">
                                <input type="hidden" name="orderId" value="${o.orderId}" />
                                <button type="submit" class="btn btn-danger btn-sm">Hủy Đơn Hàng</button>
                            </form>
                        </c:if>
                    </td>
                    <td>
                        <form action="VerifySignatureServlet" method="post">
                            <input type="hidden" name="orderId" value="${o.orderId}">
                            <button type="submit" class="btn btn-warning btn-sm">Kiểm tra chữ ký</button>
                        </form>
                    </td>
                    <td>
                        <form action="EditOrderServlet" method="get">
                            <a href="EditOrderServlet?orderId=${o.orderId}" class="btn btn-info">thông tin đơn hàng</a>
                        </form>
                    </td>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>

    <!-- Pagination -->
    <nav aria-label="Page navigation">
        <ul class="pagination justify-content-center">
            <c:forEach var="i" begin="1" end="${totalPages}">
                <li class="page-item ${i == currentPage ? 'active' : ''}">
                    <a class="page-link" href="admin_order?page=${i}">${i}</a>
                </li>
            </c:forEach>
        </ul>
    </nav>
    <c:if test="${not empty message}">
        <div class="alert alert-warning">
            ⚠ ${message}
        </div>
    </c:if>

    <c:if test="${not empty verifyResult}">
        <div class="mt-4">
            <h4>Kết quả kiểm tra chữ ký</h4>
            <c:choose>
                <c:when test="${verifyResult.valid}">
                    <div class="alert alert-success">
                        ✔ Chữ ký hợp lệ.
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-danger">
                        ✘ Chữ ký không hợp lệ hoặc đã bị giả mạo.
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </c:if>

</div>

<!-- Bootstrap JS Bundle (with Popper) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
