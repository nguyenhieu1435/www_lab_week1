<%@ page import="java.util.List" %>
<%@ page import="vn.edu.iuh.fit.models.Log" %>
<%@ page import="java.time.format.DateTimeFormatter" %><%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 14/09/2023
  Time: 12:06 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/parts/header.jsp" %>
<%@ include file="/parts/navbar.jsp"%>

<%
    List<Log> logs = (List<Log>) request.getSession().getAttribute("logs");
    if (request.getSession().getAttribute("account") == null){
        response.sendRedirect("loginPage.jsp");
    }
%>

<div>
    <h1>Log Table</h1>
    <% if (logs != null && logs.size() > 0) {%>
    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Account Id</th>
            <th scope="col">Login time</th>
            <th scope="col">Logout time</th>
            <th scope="col">Notes</th>
        </tr>
        </thead>
        <tbody>
            <% for(Log log : logs) { %>
                <tr>
                    <th scope="row"><%= log.getId() %></th>
                    <td><%= log.getAccountId() %></td>
                    <td><%= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(log.getLoginTime()) %></td>
                    <td><%= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(log.getLogoutTime()) %></td>
                    <td><%= log.getNotes() %></td>
                </tr>
            <% } %>
        </tbody>
    </table>
    <% } else { %>
        <h1>Không có dữ liệu nào</h1>
    <% } %>

</div>
<%@ include file="/parts/footer.jsp" %>