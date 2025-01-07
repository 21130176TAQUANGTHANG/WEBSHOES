<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'vi_VN'}" />
<fmt:setBundle basename="messages" />

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>index.jsp</title>
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
<%--    <link href="css/index.css" rel="stylesheet"/>--%>
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
        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/product?lang=vi">Tiếng Việt</a></li>
        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/product?lang=en">English</a></li>
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
                                <a class="dropdown-item" href="userProfileServlet">
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
                                <a href="CustomerOrderServlet" class="dropdown-item">
                                    <i class="fas fa-user-circle me-2"></i>Đơn hàng
                                </a>
                            </li>
                            <li>
                                <a href="CustomerHistoryOrder.jsp" class="dropdown-item">
                                    <i class="fas fa-user-circle me-2"></i>Lịch sử đặt hàng
                                </a>
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
<%--Carousel--%>
<div id="carouselExampleCaptions" class="carousel slide" style="height: 500px;">
    <div class="carousel-indicators">
        <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
        <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="1" aria-label="Slide 2"></button>
        <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="2" aria-label="Slide 3"></button>
    </div>
    <div class="carousel-inner" style="height: 100%;">
        <div class="carousel-item active">
            <img class="d-block w-100 h-100" style="object-fit: cover; object-position: center;" src="data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxMSEhUSEhIVFRUXGBUXFxUVFxUVFRYVFRgWFxUYFhcYHSggGBolHRUVITEhJSkrLi4uFx8zODMtNygtLisBCgoKDg0OFxAQFS0dHR0tLSstLS0tKy0tLS0tLS0tLSstKy0tKy0tLS0rLS0rNy0tLS0tKysrKystKy0tKzcuLf/AABEIASwAqAMBIgACEQEDEQH/xAAcAAACAgMBAQAAAAAAAAAAAAAEBQMGAAECBwj/xAA/EAABAwIDBQUGBAQFBQEAAAABAAIRAyEEBTESQVFhcQaBkaHwEyIyscHRB0JS4SNicvEUM4KSwiRDotLyc//EABkBAAMBAQEAAAAAAAAAAAAAAAABAgMEBf/EACQRAQEBAQADAAICAQUAAAAAAAABAhEDITESQQRRIhMUMkJx/9oADAMBAAIRAxEAPwDgUQtikF2FsL2raXGgwcF0GhYtqOmxbWLEraGQshbWFZ0OYWIbF45lMXP1VfxnaY6M2R/M6/ksdWT6FnIXJKpDs6qTLqsngJA7gFDie1xiACdNeW4kLP8AOGvjhKjc1UHAdsajXe/7zby0ADwKuuX5lTrt26bpG/cQeBCJuUdSlq5cFKVG9FoCVggazUwqBB1wpoAOYCb+uSHysAYkEaAO+SkrPiUG9+wQ4azPQcEX4rv6W3sZU/6sf/oPNpCxB9jK4OJB/npHxJCxZbnxFh/C2AtBdL2+BiwLFtZ6yGLa0sSlnyhtLc2zEU2m/wBFNmOMFNhcTFlQsbjHVXFx+EaBc/n1MfA3mWYucSfLlz+yRV8QeK3i8RMoO5suDWrb7N26sURhq204AgfdTUsrIbtOG6Y8UPQpkOsNFPRyosTTgorKMzqYd+0w8i06EcwucZVbOiFZc2T6T07Jc7ZiBb3XgSWH6HeEycF5nhqzqLmVW6gyOfLovR8JiRUY17dHAH9lpnXQjqBB1kwrNQFYKjLMTSQNRnFNaoQVVqDG9lHRWHVh8HLFF2edFXu+RC2izpLfC2FgW16OdcJkLFixbX+4GLRW0t7QY/2FB9TfEN/qNgsd8k6FU7U5n7Wp7NmjTeN7v2SXH1NluyO/mVrBCxeb6k9T9yoHjaN/XFeX5NflenI4wmBdUIDRJKuGXdkzTaHOaSeMadyc/h/kUtFVwF9F6HTwIjRc+tOjGJ+3mDcskbLpHJKs0ymJAEWsQvYMTlLXC4SavlAMtcJ4Hl91H5LuJXhmIwpBuuaFG/1Xo3aDsxIJAvfvhVEYUN1txWuddc+8caNMFhB19XCc9isdG1Qcf5mf8h9e8pLXfOmoQlDFlj21GmHNM/seSuXiHp1QWQNYInD4gVKbXt0cAfHcoaq1lACqEFWCYVggayA5yT/OHR30WLWVN/ig7rjxBWKoa7BdLAtrrtJyVpbK0VWd8DFRPxCx81KdAH4Rtu/qdZvlJ71epXj2cYw1cRUqcXmOg91vkAsv5G/8f/SF4U+5HE/LTzXTKEvDeJA81xgnaDgm+UUdqq2f1N8jJXBa0xPb2Ls5hAyixsaNCf02IDL2w1vQJk1c1dTRpoTFUN+8I4LiqRvSBLisCDuXnXbHIfZy9osvVwAdEoz/AC8VaTm8R4FOXhanY8AxIIKELryn2aYIsc5rhBBi6SVKXBbSuWxcOxWO2mOpE3b7zf6Xa+B+aeVVRuytfYxLeDgWnvuPMBXmots30kHVCArpjVS7FKg7wLb0z/MfG4lYtZYwuAP6TP8A5CfmtrSfBxcgtrQWLopNFaKxaKkwOdV/Z0Kr+DHEdYsvH2r0/tvX2cK8fqgd0heYLn819wGWAfclWTJsufVgtMETEcfQVTwf1V3yLOBhxT2qZIc4AGWgRIBJJIiJ3rm018f16dkFd4YG1DJgXT/btKrtDE06lMVqTpEgHlPEJ+we4CsLHR0ur4qsTDA0DibrhuXVn3fWPSBHkFvH4ttJpe9wa0bzYeKAw3a/Ckke2Aix2g5oBvYkiAbG3JOQWwzoZc9hltSeR0Rr2mLqHB5kyoJa5p6EFF1NFNDy/wDEXAta5tTQO9130Xm1elDiN4Xpf4sv/hNbv2p9eK88cJYx5+IWPQWHrktc30w39LQ4tcHDUEHvGi9Ho1NpjXcQCqFWp371eME2KTBwaB4Ba4rKuaqXYrRMayXYpawk+XiKQMb/AJOBWLnLXbTNgC+15WWK4fVzWltaK6CaWisWiikqP4jVopU28XGe4Lz4K7/iO7/JHHbMf7f3VSwdCZK5PLf8gLy2hMHu8V6l2a7NsLG+1pteLWcvPsDR/i0qY4ie9e45dTAYANwC5t11eHPYgqYVjG7LGNZtbIIbYQDOnefFWFosBySYCazRwune9Z2tOFWb5Q2q5rzJ2bgbRAB4wLE9Unf2Mw9R5qOYdskkySQZkm0wdSrc4rqmESlYSZV2aoUDtMYAeKb1rBEPCX5hWhpSpvLu3wNaqB+VmveqNixsP9mdCF69VywOY6q8ayZOgGk+HzXjmPxYq4hz2/DMN/pBt91ePbPfqJqGHLngdPt9VcQIEJLkNCTtd/2+/cnr1vienPr6HqhLsUEwqFA4rRaEzC4gU6RI+J038liV1nHZ2ViXsPSVyVtcuK7A5KwFaKjxFXZa53ATHE7gpoUbtvVD68aimyP9bjPyhJcE5G59UtJMuc4k84HysUqwj1x7vdA4wFbZrscdBszyEgH5le5YKoNkdF4UyjN+II+a9W7GZgK+Gbf3mjZd1Flh5I6fDvno4weKDcR7+/ToE8q4ymXWMKu4bEUzULHkBw3EgeCcUn02/maAeYUNeWijyXVKoh35hSAvUb4qKhimvuyY4wQD0nVSfKZuekHajHCjQqVXaNaSnG0vMvxczTaa3CsOpD6h4NHwjvN+5GZ2o1eTrz7F9q8ZVpuovruNNxPuCAIJnZmJLd0ShsJhjPkOZ0+q3l+DBO0TAVmynAX2yI/SPqV0yOW2/aZZVhdhgClqohoQ9YrRISqUFXRzwhKwT4CzEC6xbxSxHA9DXDisLlwSuj8w2Shcyd7hHoRvRBKX5zWDaTyeBUXdoeeZ5X2qhH6QfGL/AFCAoFcYioXOLjqSSe/Vd0NVzULJlrpZHX5LfZ3P3YLEbUk0nECoOH8wHJB5XUgd/r5ITMKUEqb7VLx6xneB9vs1me9bVusagghC0MA+w2n+coX8LM5DqZwzz71P4Z3sOnhp4L0ylRCyt49TwfyrnPOdV/J8hE7RB6uuf2Vm2AAALALtsAIGviC47LO87go1pn5fLfJfbjHYyLN1+XMryLtFQdVxVQO0BGu87IPhee9esVqMDnxVI7RYXZrbUfEB5WP0T8N7pzeX/iRUcMBAAgBH0wuGNUzQuxzJgbIasVOhqxQQZ5QtYol5QtUoMvxWqxZiliYXzaWpULHruVYbJVU7a5kGs9kLl2o4Dj3wnWbZiyi3aceg3k8AN686zXGms8uNtYHD7nmo1QBYJKMpMW8FRn165otwDRbfbwWRxzgXa+tP/pT4obbA7eNfqhcMQLbvobHyRmGpkyPU7/kg+DuyeHPtA5ph02I1C9YwOZVgILQ7np4rzXJabqTmvI92RJG7mfXyXqNDQLm8lvXTj4Ja+o/4jA4D7o+jTAEBBYYyUa6qBvWNq0FcJRj8rbiHBpMEAmRu3X9bkZj8cGgmVJl2GdAb+Z8OeeA3D1zWnizfy6jd9KvS7LVnbWxskCbm0xwSiowtJaRBBIPUWK9TxNQMHs2jQXSDF5JSqOkthx12TE9d06rsmnLxSwhK5VwrdlDqx/c4fUJFmeQV6dyzaHFvveI1VdhcInlC1SiH2Q1QpgDiCtrfsS92y25KxP8ALM+05m35FrZXA/MPEIXN83bSbYguOgQdauGiSVVMxxjqjjCrd4SHHYx1R204yT5chwCGYySuvYlT0WQsKBNIQPL7lQ1DtG2mg6Lp793oBbYybJLjqhTn1uTvLcEXEncJ+SzKcv8AaHlvVuwOWflA1v8A3Ua1xrjKbI8B7hDhu8QQPrITnBMLRsbhYdNw5qenhoaOMevqiadCLrC+2rgOPRR1ieKlrOhB1asAlHB1zhML7SreSG+8e7QK3YCmGtLzqfQSXJMPFE1J+Mz3aAfXvT7G+7TA9cvmts55GG72l2y57oG83Pzui24VrRr37v30RmFoBjb6+Z4+ZURftHWypDmlS9a+uq7bS7/V1MNI9R9l0eEW6eoCAR5v2Zo4kEOZsu3ObZwPXevKe0uRVcI/ZfdpnZeNCPoeS92AS/P8lp4ui6k/fdrhYtcNCFWdJseB4GsGvvMG0hYpszy9+Hqvo1BDmGOR3gjkQVievHNXq8+W5nC/Na023cBvP2SYnh39VntXbyT1WmidFV11m6HFdNK1sorAYCpVdssaSfIDiVNVJ0O1klP8gyB9dwtDd7t3dxKsWSdjWNh1Q7brQPyjnzV1y/AhggCFlrf9Nc4LspyNlNoACd0sKNwhE0qCKFNZVoXVGKOo+FJjqwB10Setito2RA7r1lBUBLSpqOHlF/4W0IBvkzP+lpcBE+N/qmeNZtFg5z4An6IDInThY3t2h/tMj5hMa7r0zuk+bSt/0wv1JmL4b1QVP3oDe87uvXTw8I8zrFz2sB1R9JjabPpqZPPvCZOqdKNT36dfX910O7pqd8SVB7baM6bvVrqUP057792lzuSCUO3RJ6WC6DVpnrcPWik8/kgPP/xVyPbpDEsa3aZZ+4lhsOsH5rFecfhW1ab6btHNLT3iJWK5r0nj5QcLzoOCnY2AuabJN1duxHY84pwrVgfYg2boahHD+Xmgi7sx2YfiSHvEU53/AJh9ld8nyf2Je0wHbTrgRLZOxHdGiurcrayIDWNAAgC87gPX1UOIwkm4IcN8bu/UJWdjq8XLOfsDh8PFoTCjTUbPd1HeLj7qanXadCsLLPq7OJQ2FBjcYKbSSsxGMa0Ezoqziq5rOn8o0+6XCQ1KzqjpKKoUVNQw0bkU2iik5omESXqPYAWsKzbpTMuJdAGgAJA2vCdd4siS0C+zOJHtatKeDgP6rH5BOHGGCdWmD3WnwIKS5VgfZva4XcTD3f1C3QTCbZg+BO4iD13fVa85Gflz+NBZafaVnO/SAB1cf280VmGKu4cLd6F7PvH8R0X2o8AI7kHVrjbPC/fc+acZmTKsgevW/RGU3+W6b/Yb79Enwr5EdfqI5dUyw9QncY6BrR9TqnYY+kTyHeSfPqiAhaDjGpP+kAfff5KZhnj4pFUkclpbCxNL5v7C9n/8XXh3+WwBz43yfdb3we4L3nK8E2m0NaAAAAANwC89/CDDBmDNWJNSqfBpbTA8Q4969LbILQOcnoLeZHgrAkBDYmhJBILr2FoFo4aa+KlfVggAST4AbyT9N/iV2aokCbndvjj05oIuqYeJ2hFpGzra5tPqwusqZUTcbLvLzRmIwwebnha+68iD6gLujRDZI39B8gENc+bU/av4vImus5ju4lD0shaLA1LbrfZW3aW2lLk/pf8Ar/3lU6mRk/8AerN6ez+rF1Rygj89V/8AVs/8WhW7aSXGZw5x2MONo6GobsH9P6jz06pzHfkH+4k/6haWTn9He4/dH0csj4nW4N+6GZmlanatT2h+tmve37eCY4bFsqN2mOkacweBG4o1m5K/ydX1PSSoxoYQ0ARfwug8wpbdNzeIPjqEU8KGt8KzZd79Vvs3iv4L5HvNqOafAEfVBMqXG9EUoZUrN0kh/iIPmPNLaVT3n8nfP+6cM5w1QanrA0tcTyv/AHTDCVIudd20eE6D/SPBJMO7fzBg2Ajf5f2Rwx4a6drnvk6bu48dU6Z5Sgax3ujgBry2UYw9Pn5+CT4fEueZAcO6/W/d4I9lV0TDj4D5c1IHyOS0oWPJ3HvC0ml5t+F1YMy5j97XVREwJNUkTw+IX5q0Nx1Qgv2iWyI2dkcBYEaTOp0Xnn4WZl/Cr4abz7VotJaQGvidLtZfdtSrk1wDSCLxILQddYtda5s/asfj+znDY+o1x2zLYFrEgbzIAmN4TQGBLACXXkmAecwZVWMPeGwdmYvtCZuYnjEcoJ4KzUHjZbs2bAgcBFktc76LfO+hDSYvrvjRblRbS2CpQlC7alWDx9R1V1N1MgASHcZJ2Sb2sCmYKDssRZi1zqT2s+ItcB1IVao42kxgaQQ4AjZ0MmBN9Dz3SVaSUm7RZa7ENAa8CJsfhdPGOEeZVZ3ycGZ3U98Lm41ktOxUJEOb70k7QIBDQdIBtG5F5JTf7V79gsYWxBsS6QQQOQ2vFIcxyfEmDsMOy0NHs9kGBNzpJunfZyhWpMcyqZEgtMzrMjjFhbmi69caa8cmZfzlv9HhehcdWhq1Uqpdmdf3VkiF2OeNoHiCPr9ElomHv/0ozGVrch9EDg27dQj1xTioLplzrMExF93r91YsFgWNLXOuYg75PIetUHRgGWj3dmSemv18CmdI7LQ65HfpEm8axPeAnQZ03wdLDVE0XCN3I/JB4d9weN9LTv13EIijv5aX4XBUlYMbosXDTYFaQXHy1lmY1MLXbVbZ7DcHQg/E08iF7Lkme0cZTD6bgIjapnVp3gwdNeSof4kdmth4xFAbTHQ17G3LXbiAPynTlZU7B1KtB7Xt26ZmxgtniL69FpZ1n8fQuHwrZvBAnSxMn80RYC0XTSm4QAPALx7Ke2ZpiHNlupjdPL7K2ZZ2yoVIioATuNkuLXnaXTSleFx7XCQQeinfjmN+J7WzpJAnpKAYgrraQ1KsCAQQQbgi4IXZelQkc9Qvco31VA+skExeoqtWEPWxIA1E8zHmqpnWePLXU9nYJBG0CHWNrXGtxoiQ5OrM7Eg3BkG4PJLsZXkH1uVey7HOeS3bLWs/INnQzHvXt0U+IxnxX3fdFO55WYipIKhys3J7u/0EtGOvBU2V4j+IW8bjqEQ1rwdQzboeBBvPz80zoPgFoOn6rxqfnP8AtVfw5MGNfiEneDfyMd6bYR8u2jYxpzdEfLz5qqZqyrYHTcep0nw4/NGNq36317+PqyV0H+5IgRBPltDpv8EVt/CZ5W5R47u8KeA0pumQNRx0nisQ9F0Ea3jnfhKxJLyvNMwNNwFN/tONhA5SNUtxWZ1agghscIB+atmHyhrB7jRw2j7zjyClw+SgmzWg8gJW3UKvllOuw7UO2SPhiQfKyJdjS25YwgatLRfiOK9AZllOlTL3vHuglx3ADglbOzxrtFZ4jaBLWARDT8Jcd5i/ekCjG5dsD2uEqbBgH2Z/y3b7fpPklFPOXYsbJpVBUbILg0lo4gmw81baeVQIh5AEAESAB3Liv7oiISFjWS06zKbWuqQBMAAG265CMNJ5/wC9Unq3/wBVFSxNoRuFiNom+4I4ZfWo1Ro+etvMIcmtMEHqCCnlKltAu8F22kAlYCVuHJ/ITzcJ+alfl1M/GxpPNot0TptQDcuazNoaoNXquTUtzAOlkDWySlrsnh8Tvunr5CjeJT4FTrZJSFwD/ud90vdhdioHBxEblacZRhV/Gi6XFQ9w5jvBHy9dyPw13NMkER0JP0mErwg2mt5wPJNsOIawmzhI+k+aZi8K4gOgdeBi1xwj5IzDvMCN3iZBiB5odgAOmvDx389lSU3wdifePyJ13DW/kppDWVzAI3CDxm1/uViHpWdBMjjqIvceUBYjgVp3aynNqJjqBZOMHnNF1M1AYIsR+aTo0DfO7ivOwU37LT/iWQJ+nNbWRl1anYCpVh1Yw2QRRGltNs/mPknlHMi2zgpdgb1HUw6z6p3UzJrtAVwxgdcgKH2UblsvgICDEYOmTZoUL8r4EhHUeJXT6yACp4WoLe0t0UrgQL3RIchMVXAGqA7ZcLiowjTTgoKNeUUx8oAOoAUK+yPxFEaoCqg4HriQq7mVO6sTilGZtsUKjrLHDYby+6aU32g7haZ7uunkkmTu91wO46cj/ZNQPy8bienruQYw1iQ0HXxm2hUxxJkcSBHHjrx0KA9pLQDYg2OotvPripH1gYtOsTYyZt3xfoUAzbWIdc2uZPI7uURCxLzXvyPHrJM6AXA6rEuEo7BeIvw3r0XshkPsm+0ePfd5Dgp8n7M0aJ2oL3/qfcz0T8K9aZSIzSXLkQtFihQUhQPYjXtUTmpgI4qNtMm6KfTUVQFAQVLb0A4bRRrmErqlhgEGipYWUZRoQF2xq6cYSAHEWS+qjcYUG4JwAaoul2PEhMMUUsxVRCguUGHOHED15pif06wPpyS3LbVDzBTN9PfvgeHRBo2V5BEG3nv+q6DzHvXk6jvUb6ojW5voI3D10UNWvADTcHX7fNMD31PdAts8DHd5fJYlZ2jZsndGvesSHx6qWLQCkC05DNyCtrlbCQbXLgu1w5AQELh7VLVKiJQHJYFzC2SsCDacYURkqUrVIXQYarR3lBYgQm2KKV10ApxKS4kp1jEkr6pnEeDtUaeabF0WmD8wk4MGUzruvHMfNI6HqMLyI1mOn7EIvB5J7Qw9xFxOg38egRWHotABAuZlNsOwS3nr5p9LrnB5W1jvdnhO/vPRYmeIbDGkEjX7LSkn/9k=" alt="...">
            <div class="carousel-caption d-none d-md-block">
                <h5>First slide label</h5>
                <p>Some representative placeholder content for the first slide.</p>
            </div>
        </div>
        <div class="carousel-item">
            <img class="d-block w-100 h-100" style="object-fit: cover; object-position: center;"
                 src="https://www.shutterstock.com/shutterstock/videos/25551566/thumb/1.jpg?ip=x480" alt="...">
            <div class="carousel-caption d-none d-md-block">
                <h5>Second slide label</h5>
                <p>Some representative placeholder content for the second slide.</p>
            </div>
        </div>
        <div class="carousel-item">
            <img class="d-block w-100 h-100" style="object-fit: cover; object-position: center;"
                 src="C:\Users\thang\OneDrive\Desktop\\trnahalinh2.png" alt="...">
            <div class="carousel-caption d-none d-md-block">
                <h5>Third slide label</h5>
                <p>Some representative placeholder content for the third slide.</p>
            </div>
        </div>
    </div>
    <button class="carousel-control-prev text-secondary" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
    </button>
    <button class="carousel-control-next text-secondary" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
    </button>
</div>



<%--index.jsp--%>
<div class="container mt-5">
    <div class="m-2 d-flex justify-content-center">
        <h2>Sản phẩm bán chạy</h2>
    </div>

    <div id="carouselExampleCaptionss" class="carousel slide">
        <div class="carousel-indicators">
            <button type="button" data-bs-target="#carouselExampleCaptionss" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
            <button type="button" data-bs-target="#carouselExampleCaptionss" data-bs-slide-to="1" aria-label="Slide 2"></button>
            <button type="button" data-bs-target="#carouselExampleCaptionss" data-bs-slide-to="2" aria-label="Slide 3"></button>
        </div>
        <!-- Slides -->
        <div class="carousel-inner">
            <c:forEach var="product" items="${BestSellingProducts}" varStatus="status">
                <c:if test="${status.index % 4 == 0}">
                    <div class="carousel-item ${status.index == 0 ? 'active' : ''}">
                    <div class="row justify-content-center">
                </c:if>

                <!-- Sản phẩm -->
                <div class="col-lg-3 col-md-4 col-sm-6 mb-4">
                    <div class="card">
                        <div class="bg-image hover-zoom ripple ripple-surface ripple-surface-light"
                             data-mdb-ripple-color="light">
                            <img src="${pageContext.request.contextPath}/image/${product.productImage}" class="card-img-top" alt="${product.productName}" style="max-height: 200px; object-fit: cover;">
                            <a href="productDetail?productId=${product.productId}">
                                <div class="mask">
                                    <div class="d-flex justify-content-start align-items-end h-100">
                                        <h5><span class="badge bg-dark ms-2">NEW</span></h5>
                                    </div>
                                </div>
                                <div class="hover-overlay">
                                    <div class="mask" style="background-color: rgba(251, 251, 251, 0.15);"></div>
                                </div>
                            </a>
                        </div>
                        <div class="card-body">
                            <a href="productDetail?productId=${product.productId}" class="text-reset">
                                <h5 class="card-title mb-2"><c:out value="${product.productName}"/></h5>
                            </a>
                            <h6 class="mb-3">
                                <c:out value="${currencySymbol}"/>
                                <fmt:formatNumber value="${product.productPrice * exchangeRate}" maxFractionDigits="0" />
                            </h6>
                        </div>
                    </div>
                </div>

                <c:if test="${(status.index + 1) % 4 == 0 || status.last}">
                    </div>
                    </div>
                </c:if>
            </c:forEach>
        </div>
        <button class="carousel-control-prev text-secondary" style="position: absolute; left: -100px" type="button" data-bs-target="#carouselExampleCaptionss" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next text-secondary" style="position: absolute; right: -100px;" type="button" data-bs-target="#carouselExampleCaptionss" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>

</div>



</body>
<!-- MDB -->
<script
        type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.2/mdb.umd.min.js"
></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</html>