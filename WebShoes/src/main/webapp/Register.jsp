<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Register</title>
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
    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/RegisterServlet?lang=vi">Tiếng Việt</a></li>
    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/RegisterServlet?lang=en">English</a></li>
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
        <a href="Login.jsp"><%= bundle.getString("menu.login") %></a>
      </c:if>
    </div>
    </div>
  </nav>
</header>
<div>
  <section class="vh-100" style="background-color: #eee;">
    <div class="container h-100">
      <div class="row d-flex justify-content-center align-items-center h-100">
        <div class="col-lg-12 col-xl-11">
          <div class="card text-black" style="border-radius: 25px;">
            <div class="card-body p-md-5">
              <div class="row justify-content-center">
                <div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">

                  <p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4"><%= bundle.getString("menu.signup") %></p>
                  <div>
                    <!-- Kiểm tra và hiển thị thông báo lỗi -->
                    <c:if test="${not empty errorMessage}">
                      <div class="alert alert-danger" role="alert">
                          ${errorMessage}
                      </div>
                    </c:if>

                  </div>
                  <form action="RegisterServlet" method="post" class="mx-1 mx-md-4">

                    <div class="d-flex flex-row align-items-center mb-4">
                      <i class="fas fa-user fa-lg me-3 fa-fw"></i>
                      <div data-mdb-input-init class="form-outline flex-fill mb-0">
                        <input type="text" id="form3Example1c" class="form-control" name="username"/>
                        <label class="form-label" for="form3Example1c"><%= bundle.getString("signup.name") %></label>
                      </div>
                    </div>

                    <div class="d-flex flex-row align-items-center mb-4">
                      <i class="fas fa-envelope fa-lg me-3 fa-fw"></i>
                      <div data-mdb-input-init class="form-outline flex-fill mb-0">
                        <input type="email" id="form3Example3c" class="form-control" name="email"/>
                        <label class="form-label" for="form3Example3c"><%= bundle.getString("signup.email")%></label>
                      </div>
                    </div>

                    <div class="d-flex flex-row align-items-center mb-4">
                      <i class="fas fa-lock fa-lg me-3 fa-fw"></i>
                      <div data-mdb-input-init class="form-outline flex-fill mb-0">
                        <input type="password" id="form3Example4c" class="form-control" name="password"/>
                        <label class="form-label" for="form3Example4c"><%= bundle.getString("sign.password")%></label>
                      </div>
                    </div>

                    <div class="d-flex flex-row align-items-center mb-4">
                      <i class="fas fa-key fa-lg me-3 fa-fw"></i>
                      <div data-mdb-input-init class="form-outline flex-fill mb-0">
                        <input type="password" id="form3Example4cd" class="form-control" name="repeatPassword"/>
                        <label class="form-label" for="form3Example4cd"><%= bundle.getString("sign.repeatpassword")%></label>
                      </div>
                    </div>

                    <div class="d-flex flex-row align-items-center mb-4">
                      <i class="fas fa-phone fa-lg me-3 fa-fw"></i>
                      <div data-mdb-input-init class="form-outline flex-fill mb-0">
                        <input type="password" id="form3Example5c" class="form-control" name="phone"/>
                        <label class="form-label" for="form3Example5c"><%= bundle.getString("sign.phone")%></label>
                      </div>
                    </div>

                    <div class="d-flex flex-row align-items-center mb-4">
                      <i class="fas fa-map-location-dot fa-lg me-3 fa-fw"></i>
                      <div data-mdb-input-init class="form-outline flex-fill mb-0">
                        <input type="password" id="form3Example5cd" class="form-control" name="address"/>
                        <label class="form-label" for="form3Example5cd"><%= bundle.getString("sign.address")%></label>
                      </div>
                    </div>

                    <div class="form-check d-flex justify-content-center mb-5">
                      <input class="form-check-input me-2" type="checkbox" value="" id="form2Example3c" />
                      <label class="form-check-label" for="form2Example3c">
                        <%= bundle.getString("sign.notice")%> <a href="#!"><%= bundle.getString("sign.rule")%></a>
                      </label>
                    </div>

                    <div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
                      <button  type="submit" class="btn btn-primary btn-lg"><%= bundle.getString("menu.signup") %></button>
                    </div>

                  </form>

                </div>
                <div class="col-md-10 col-lg-6 col-xl-7 d-flex align-items-center order-1 order-lg-2">

                  <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-registration/draw1.webp"
                       class="img-fluid" alt="Sample image">

                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</div>
</body>
<!-- MDB -->
<script
        type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.2/mdb.umd.min.js"
></script>
</html>
