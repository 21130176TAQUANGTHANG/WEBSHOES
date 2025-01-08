<%@ page import="LoginUser.User" %>
<%@ page import="LoginUser.GoogleAccount" %>
<%@ page import="LoginUser.AccountFF" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Trang đăng nhập</title>
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
    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/LoginServlet?lang=vi">Tiếng Việt</a></li>
    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/LoginServlet?lang=en">English</a></li>
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
  <section class="py-3 py-md-5 py-xl-8">
    <div class="container">
      <div class="row">
        <div class="col-12">
          <div class="mb-5">
            <h2 class="display-5 fw-bold text-center"><%= bundle.getString("menu.login") %></h2>
            <p class="text-center m-0"><%= bundle.getString("login.haveaccount") %> <a href="RegisterServlet"><%= bundle.getString("menu.signup") %></a></p>
          </div>
        </div>
      </div>
      <div class="row justify-content-center">
        <div class="col-12 col-lg-10 col-xl-8">
          <div class="row gy-5 justify-content-center">
            <div class="col-12 col-lg-5">
              <form action="LoginServlet" method="post">
                <p class="text-danger">${error}</p>
                <div class="row gy-3 overflow-hidden">
                  <div class="col-12">
                    <div class="form-floating mb-3">
                      <input type="email" class="form-control border-0 border-bottom rounded-0" name="email" id="email" placeholder="name@example.com" required>
                      <label for="email" class="form-label">Email</label>
                    </div>
                  </div>
                  <div class="col-12">
                    <div class="form-floating mb-3">
                      <input type="password" class="form-control border-0 border-bottom rounded-0" name="password" id="password" value="" placeholder="Password" required>
                      <label for="password" class="form-label"><%= bundle.getString("login.password") %></label>
                    </div>
                  </div>
                  <div class="col-12">
                    <div class="row justify-content-between">
                      <div class="col-6">
                        <div class="text-end">
                          <a href="#!" class="link-secondary text-decoration-none"><%= bundle.getString("login.forgotpassword")%></a>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-12">
                    <div class="d-grid">
                      <button class="btn btn-primary btn-lg" type="submit"><%= bundle.getString("menu.login") %></button>
                    </div>
                  </div>
                </div>
              </form>
            </div>
            <div class="col-12 col-lg-2 d-flex align-items-center justify-content-center gap-3 flex-lg-column">
              <div class="bg-dark h-100 d-none d-lg-block" style="width: 1px; --bs-bg-opacity: .1;"></div>
              <div class="bg-dark w-100 d-lg-none" style="height: 1px; --bs-bg-opacity: .1;"></div>
              <div><%= bundle.getString("login.or") %></div>
              <div class="bg-dark h-100 d-none d-lg-block" style="width: 1px; --bs-bg-opacity: .1;"></div>
              <div class="bg-dark w-100 d-lg-none" style="height: 1px; --bs-bg-opacity: .1;"></div>
            </div>
            <div class="col-12 col-lg-5 d-flex align-items-center">
              <div class="d-flex gap-3 flex-column w-100 ">
                <a href="https://accounts.google.com/o/oauth2/auth?scope=email profile openid

&redirect_uri=http://localhost:8080/WebShoes_war_exploded/LoginGGServlet

&response_type=code

&client_id=940824171014-ttmv3gbi69cdm7fsm6phtok3f2fi8iot.apps.googleusercontent.com

&approval_prompt=force" class="btn btn-lg btn-danger">
                  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-google" viewBox="0 0 16 16">
                    <path d="M15.545 6.558a9.42 9.42 0 0 1 .139 1.626c0 2.434-.87 4.492-2.384 5.885h.002C11.978 15.292 10.158 16 8 16A8 8 0 1 1 8 0a7.689 7.689 0 0 1 5.352 2.082l-2.284 2.284A4.347 4.347 0 0 0 8 3.166c-2.087 0-3.86 1.408-4.492 3.304a4.792 4.792 0 0 0 0 3.063h.003c.635 1.893 2.405 3.301 4.492 3.301 1.078 0 2.004-.276 2.722-.764h-.003a3.702 3.702 0 0 0 1.599-2.431H8v-3.08h7.545z" />
                  </svg>
                  <span class="ms-2 fs-6"><%= bundle.getString("login.withGoogle")%></span>
                </a>
                <a href="https://www.facebook.com/v19.0/dialog/oauth?client_id=1695171041245778
&redirect_uri=http://localhost:8080/WebShoes_war_exploded/LoginFFServlet" class="btn btn-lg btn-primary">
                  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-facebook" viewBox="0 0 16 16">
                    <path d="M16 8.049c0-4.446-3.582-8.05-8-8.05C3.58 0-.002 3.603-.002 8.05c0 4.017 2.926 7.347 6.75 7.951v-5.625h-2.03V8.05H6.75V6.275c0-2.017 1.195-3.131 3.022-3.131.876 0 1.791.157 1.791.157v1.98h-1.009c-.993 0-1.303.621-1.303 1.258v1.51h2.218l-.354 2.326H9.25V16c3.824-.604 6.75-3.934 6.75-7.951z" />
                  </svg>
                  <span class="ms-2 fs-6"><%= bundle.getString("login.withFacebook")%></span>
                </a>
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
