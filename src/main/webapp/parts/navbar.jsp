<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 13/09/2023
  Time: 8:40 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="vn.edu.iuh.fit.models.Account" %>
<%@ page import="vn.edu.iuh.fit.models.GrantAccess" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    Account accountNav = (Account)request.getSession().getAttribute("account");
    boolean isAdmin = false;
    if (accountNav != null && accountNav.getGrantAccesses() != null){
        for (GrantAccess grantAccess : accountNav.getGrantAccesses()){
            if (grantAccess.getRole().getRoleId().equalsIgnoreCase("admin")){
                isAdmin = true;
                break;
            }
        }
    }

%>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link font-weight-bold" href="index.jsp">Trang chủ</a>
                </li>
                <% if (isAdmin) { %>
                    <form action="control-servlet" method="get">
                        <input type="hidden" name="action" value="handleOpenDashboard"/>
                        <input type="submit" class="nav-link font-weight-bold btn-in-navlink" value="Dashboard"/>
                    </form>
                <% } %>
                <li class="nav-item">
                    <form action="control-servlet" method="get">
                        <input type="hidden" name="action" value="getRoleOfAllAcc"/>
                        <input type="submit" class="nav-link font-weight-bold btn-in-navlink" value="Quyền của các account"/>
                    </form>
                </li>
                <li class="nav-item">
                    <form action="control-servlet" method="get">
                        <input type="hidden" name="action" value="getRoleList"/>
                        <input type="submit" class="nav-link font-weight-bold btn-in-navlink" value="Hiển thị các account theo role"/>
                    </form>
                </li>
                <li class="nav-item">
                    <%if (accountNav != null){%>
                        <form action="control-servlet" method="get">
                            <input type="hidden" name="action" value="getLogList"/>
                            <input type="submit" class="nav-link font-weight-bold btn-in-navlink" value="Log đăng nhập"/>
                        </form>
                    <%} else {%>
                        <a class="nav-link font-weight-bold" href="loginPage.jsp">Log đăng nhập</a>
                    <%}%>
                </li>
            </ul>
            <div class="d-flex">
                <% if (accountNav != null) { %>
                <a href="userInfo.jsp" class="nav-link">Thông tin người dùng</a>
                <% } %>

                <% if (accountNav != null) { %>
                <form action="control-servlet" method="POST">
                    <input name="action" value="logout" class="btn btn-danger" type="hidden">
                    <button type="submit" class="btn btn-danger">Đăng xuất</button>
                </form>

                <% } %>

                <% if (accountNav == null) { %>
                <a href="loginPage.jsp" class="btn btn-success" type="submit">Đăng nhập</a>
                <% } %>
            </div>
        </div>
    </div>
</nav>