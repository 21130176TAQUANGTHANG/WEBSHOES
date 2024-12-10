<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SB Admin 2 - Dashboard</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"></script>

    <!-- Custom fonts for this template-->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="css/sb-admin-2.min.css" rel="stylesheet">
    <!-- Custom fonts for this template-->
    <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="css/sb-admin-2.min.css" rel="stylesheet">

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

<body id="page-top">

<!-- Page Wrapper -->
<div id="wrapper">

    <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

        <!-- Sidebar - Brand -->
        <a class="sidebar-brand d-flex align-items-center justify-content-center" href="admin.jsp">
            <div class="sidebar-brand-icon rotate-n-15">
                <i class="fas fa-laugh-wink"></i>
            </div>
            <div class="sidebar-brand-text mx-3">SB Admin <sup>2</sup></div>
        </a>

        <!-- Divider -->
        <hr class="sidebar-divider my-0">

        <!-- Nav Item - Dashboard -->
        <li class="nav-item active">
            <a class="nav-link" href="product">
                <i class="fas fa-fw fa-tachometer-alt"></i>
                <span>Web store</span></a>
        </li>

        <!-- Divider -->
        <hr class="sidebar-divider">

        <!-- Heading -->
        <div class="sidebar-heading">
            Addons
        </div>

        <!-- Nav Item - Charts -->
        <li class="nav-item">
            <a class="nav-link" href="AdminProduct">
                <i class="fas fa-fw fa-chart-area"></i>
                <span>AdminProduct</span></a>
        </li>

        <!-- Nav Item - Tables -->
        <li class="nav-item">
            <a class="nav-link" href="AccountADServlet">
                <i class="fas fa-fw fa-table"></i>
                <span>Adminaccount</span></a>
        </li>

        <li class="nav-item">
            <a class="nav-link" href="feedback.jsp">
                <i class="fas fa-fw fa-table"></i>
                <span>feedback</span></a>
        </li>

        <li class="nav-item">
            <a class="nav-link" href="admin_order">
                <i class="fas fa-fw fa-table"></i>
                <span>Quản lý đơn hàng</span></a>
        </li>

        <li class="nav-item">
            <a class="nav-link" href="editStore.html">
                <i class="fas fa-fw fa-table"></i>
                <span>Giao diện của hàng</span></a>
        </li>


        <!-- Divider -->
        <hr class="sidebar-divider d-none d-md-block">

        <!-- Sidebar Toggler (Sidebar) -->
        <div class="text-center d-none d-md-inline">
            <button class="rounded-circle border-0" id="sidebarToggle"></button>
        </div>

    </ul>
    <!-- End of Sidebar -->

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

        <!-- Main Content -->
        <div id="content">

            <!-- Topbar -->
            <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

                <!-- Sidebar Toggle (Topbar) -->
                <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                    <i class="fa fa-bars"></i>
                </button>
                <form
                        class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 navbar-search">
                    <div class="input-group">
                        <input type="text" class="form-control bg-light border-0 small" placeholder="Search for..."
                               aria-label="Search" aria-describedby="basic-addon2">
                        <div class="input-group-append">
                            <button class="btn btn-primary" type="button">
                                <i class="fas fa-search fa-sm"></i>
                            </button>
                        </div>
                    </div>
                </form>
                <!-- Topbar Navbar -->
                <ul class="navbar-nav ml-auto">

                    <!-- Nav Item - Search Dropdown (Visible Only XS) -->
                    <li class="nav-item dropdown no-arrow d-sm-none">
                        <a class="nav-link dropdown-toggle" href="#" id="searchDropdown" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <i class="fas fa-search fa-fw"></i>
                        </a>
                        <!-- Dropdown - Messages -->
                        <div class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in"
                             aria-labelledby="searchDropdown">
                            <form class="form-inline mr-auto w-100 navbar-search">
                                <div class="input-group">
                                    <input type="text" class="form-control bg-light border-0 small"
                                           placeholder="Search for..." aria-label="Search"
                                           aria-describedby="basic-addon2">
                                    <div class="input-group-append">
                                        <button class="btn btn-primary" type="button">
                                            <i class="fas fa-search fa-sm"></i>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </li>

                    <!-- Nav Item - Alerts -->
                    <div class="topbar-divider d-none d-sm-block"></div>

                    <!-- Nav Item - User Information -->
                    <li class="nav-item dropdown no-arrow">
                        <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <span class="mr-2 d-none d-lg-inline text-gray-600 small">Douglas McGee</span>
                            <img class="img-profile rounded-circle"
                                 src="img/undraw_profile.svg">
                        </a>
                        <!-- Dropdown - User Information -->
                        <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
                             aria-labelledby="userDropdown">
                            <a class="dropdown-item" href="#">
                                <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                                Profile
                            </a>
                            <a class="dropdown-item" href="#">
                                <i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>
                                Settings
                            </a>
                            <a class="dropdown-item" href="#">
                                <i class="fas fa-list fa-sm fa-fw mr-2 text-gray-400"></i>
                                Activity Log
                            </a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">
                                <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                                Logout
                            </a>
                        </div>
                    </li>

                </ul>

            </nav>
            <!-- End of Topbar -->

            <div class="container mt-5">
                <div class="d-flex justify-content-between p-2 ">
                    <h2>Product List</h2>
                    <div>

                        <button type="button" class="btn btn-success pt-2 pb-2" data-bs-toggle="modal" data-bs-target="#addProductModal">Add</button>
                    </div>
                </div>
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>Product ID</th>
                        <th>Product Name</th>
                        <th>Product Image</th>
                        <th>Price</th>
                        <th>Description</th>
                        <th>Quantity</th>
                        <th>Size</th>
                        <th>Color</th>
                        <th>Brand</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="o" items="${adminproducts}">
                        <tr>
                            <td>${o.productId}</td>
                            <td>${o.productName}</td>
                            <td>
                                <img src="${pageContext.request.contextPath}/image/${o.productImage}" alt="${o.productName}" style="max-width: 100px; max-height: 100px;">
                            </td>
                            <td>${o.formatPrice}</td>
                            <td>${o.productDescription}</td>
                            <td>${o.productQuantity}</td>
                            <td>${o.productSize}</td>
                            <td>${o.productColor}</td>
                            <td>${o.productLogo}</td>
                            <td>
                                <button type="button" class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#editProductModal"
                                        data-id="${o.productId}" data-name="${o.productName}" data-image="${o.productImage}"
                                        data-price="${o.productPrice}" data-description="${o.productDescription}"
                                        data-quantity="${o.productQuantity}" data-size="${o.productSize}"
                                        data-color="${o.productColor}" data-logo="${o.productLogo}">
                                    Edit
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

            </div>

        </div>

        <!-- Footer -->
        <footer class="sticky-footer bg-white">
            <div class="container my-auto">
                <div class="copyright text-center my-auto">
                    <span>Copyright &copy; Your Website 2021</span>
                </div>
            </div>
        </footer>
        <!-- End of Footer -->

    </div>
    <!-- End of Content Wrapper -->

</div>

<a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
</a>

<!-- Logout Modal-->
<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
            <div class="modal-footer">
                <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                <a class="btn btn-primary" href="login.html">Logout</a>
            </div>
        </div>
    </div>
</div>

<%--pop up add product--%>
<div class="modal fade" id="addProductModal" tabindex="-1" aria-labelledby="addProductModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addProductModalLabel">Add New Product</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form action="AddProductServlet" method="post" enctype="multipart/form-data">
                    <div class="mb-3">
                        <label for="productID" class="form-label">Product Id</label>
                        <input type="number" class="form-control" id="productID" name="productID" required>
                    </div>

                    <div class="mb-3">
                        <label for="productName" class="form-label">Product Name</label>
                        <input type="text" class="form-control" id="productName" name="productName" required>
                    </div>
                    <div class="mb-3">
                        <label for="productPrice" class="form-label">Price</label>
                        <input type="number" class="form-control" id="productPrice" name="productPrice" required>
                    </div>
                    <div class="mb-3">
                        <label for="productImage" class="form-label">Image</label>
                        <input type="file" class="form-control" id="productImage" name="productImage" required>
                    </div>
                    <div class="mb-3">
                        <label for="productDescription" class="form-label">Description</label>
                        <textarea class="form-control" id="productDescription" name="productDescription" rows="3" required></textarea>
                    </div>
                    <div class="mb-3">
                        <label for="productQuantity" class="form-label">Quantity</label>
                        <input type="number" class="form-control" id="productQuantity" name="productQuantity" required>
                    </div>
                    <div class="mb-3">
                        <label for="productSize" class="form-label">Size</label>
                        <input type="text" class="form-control" id="productSize" name="productSize" required>
                    </div>
                    <div class="mb-3">
                        <label for="productColor" class="form-label">Color</label>
                        <input type="text" class="form-control" id="productColor" name="productColor" required>
                    </div>
                    <div class="mb-3">
                        <label for="productLogo" class="form-label">Logo</label>
                        <input type="text" class="form-control" id="productLogo" name="productLogo" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Add Product</button>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- Toast thông báo -->
<div class="position-fixed bottom-0 end-0 p-3" style="z-index: 11">
    <div id="successToast" class="toast bg-success text-white" role="alert" aria-live="assertive" aria-atomic="true">
        <div class="toast-header">
            <strong class="me-auto">Notification</strong>
            <button type="button" class="btn-close btn-close-white" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
        <div class="toast-body">
            Product added successfully!
        </div>
    </div>
</div>

<!-- Modal Edit Product -->
<div class="modal fade" id="editProductModal" tabindex="-1" aria-labelledby="editProductModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editProductModalLabel">Edit Product</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form action="EditProductServlet" method="post" enctype="multipart/form-data">
                    <input type="hidden" id="editProductID" name="productID"> <!-- Để lưu trữ ID của sản phẩm cần chỉnh sửa -->

                    <div class="mb-3">
                        <label for="editProductName" class="form-label">Product Name</label>
                        <input type="text" class="form-control" id="editProductName" name="productName" required>
                    </div>

                    <div class="mb-3">
                        <label for="editProductImage" class="form-label">Product Image</label>
                        <input type="file" class="form-control" id="editProductImage" name="productImage">
                        <small class="form-text text-muted" id="currentFileName">
                            Current file: <span id="currentFileNameDisplay">No file selected</span>
                        </small>
                    </div>



                    <div class="mb-3">
                        <label for="editProductPrice" class="form-label">Price</label>
                        <input type="number" class="form-control" id="editProductPrice" name="productPrice" required>
                    </div>

                    <div class="mb-3">
                        <label for="editProductDescription" class="form-label">Description</label>
                        <textarea class="form-control" id="editProductDescription" name="productDescription" rows="3" required></textarea>
                    </div>

                    <div class="mb-3">
                        <label for="editProductQuantity" class="form-label">Quantity</label>
                        <input type="number" class="form-control" id="editProductQuantity" name="productQuantity" required>
                    </div>

                    <div class="mb-3">
                        <label for="editProductSize" class="form-label">Size</label>
                        <input type="text" class="form-control" id="editProductSize" name="productSize" required>
                    </div>

                    <div class="mb-3">
                        <label for="editProductColor" class="form-label">Color</label>
                        <input type="text" class="form-control" id="editProductColor" name="productColor" required>
                    </div>

                    <div class="mb-3">
                        <label for="editProductLogo" class="form-label">Logo</label>
                        <input type="text" class="form-control" id="editProductLogo" name="productLogo" required>
                    </div>

                    <button type="submit" class="btn btn-primary">Save Changes</button>
                </form>
            </div>
        </div>
    </div>
</div>



<script>
    // Hiển thị toast khi sản phẩm được thêm thành công
    document.addEventListener("DOMContentLoaded", () => {
        const urlParams = new URLSearchParams(window.location.search);
        if (urlParams.get('success') === 'true') {
            const successToast = new bootstrap.Toast(document.getElementById('successToast'));
            successToast.show();
        }
    });


</script>

<%--popup edit--%>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        var editProductModal = document.getElementById('editProductModal');

        editProductModal.addEventListener('show.bs.modal', function (event) {
            var button = event.relatedTarget; // Lấy button nhấn vào
            if (!button) return;

            // Lấy thông tin sản phẩm từ các thuộc tính data-
            var productId = button.getAttribute('data-id');
            var productName = button.getAttribute('data-name');
            var productImage = button.getAttribute('data-image');
            var productPrice = button.getAttribute('data-price');
            var productDescription = button.getAttribute('data-description');
            var productQuantity = button.getAttribute('data-quantity');
            var productSize = button.getAttribute('data-size');
            var productColor = button.getAttribute('data-color');
            var productLogo = button.getAttribute('data-logo');

            // Điền thông tin vào các ô input trong modal
            document.getElementById('editProductID').value = productId || '';
            document.getElementById('editProductName').value = productName || '';
            document.getElementById('editProductPrice').value = productPrice || '';
            document.getElementById('editProductDescription').value = productDescription || '';
            document.getElementById('editProductQuantity').value = productQuantity || '';
            document.getElementById('editProductSize').value = productSize || '';
            document.getElementById('editProductColor').value = productColor || '';
            document.getElementById('editProductLogo').value = productLogo || '';

            // Hiển thị tên file hình ảnh hiện tại (nếu có)
            var fileNameDisplay = document.getElementById('currentFileNameDisplay');
            fileNameDisplay.textContent = productImage ? productImage : 'No file selected';

            // Tooltip nếu file name quá dài
            if (productImage) {
                fileNameDisplay.setAttribute('title', productImage);
            }
        });
    });
</script>

</body>

</html>