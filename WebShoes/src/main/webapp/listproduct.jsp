<%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 11/12/2024
  Time: 6:22 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Danh sách sản phẩm</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
<h1>Danh sách sản phẩm</h1>

<%
    // Tạo danh sách sản phẩm giả lập
    class Product {
        String name;
        double price;

        Product(String name, double price) {
            this.name = name;
            this.price = price;
        }
    }

    List<Product> products = new ArrayList<>();
    products.add(new Product("Sản phẩm 1", 100.0));
    products.add(new Product("Sản phẩm 2", 150.0));
    products.add(new Product("Sản phẩm 3", 200.0));
%>

<table>
    <tr>
        <th>Tên sản phẩm</th>
        <th>Giá</th>
    </tr>
    <%
        // Hiển thị danh sách sản phẩm
        for (Product product : products) {
    %>
    <tr>
        <td><%= product.name %></td>
        <td><%= product.price %></td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
