<%@ page import="java.util.List" %>
<%@ page import="java.util.stream.Collectors" %><%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 14/09/2023
  Time: 5:00 CH
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/parts/header.jsp" %>

<%@ include file="/parts/navbar.jsp"%>

<%
    if (accountNav == null){
        response.sendRedirect("loginPage.jsp");
        return;
    }
    List<Account> accountsWithRole = (List<Account>) request.getSession().getAttribute("rolesBelongToAccount");
    System.out.println(accountsWithRole.get(0).getGrantAccesses());
%>

<div class="container">


    <h3 class="text-center font-weight-bold my-5">Quyền của các account</h3>

    <%if (accountsWithRole == null){%>
    <form action="control-servlet" method="get" class="text-center">
        <input type="hidden" name="action" value="getRoleOfAllAcc"/>
        <input type="submit" class="btn btn-success" value="Lấy danh sách"/>
    </form>
    <%}%>

    <%if (accountsWithRole == null || accountsWithRole.size() == 0) {%>
        <h3></h3>
    <%} else {%>
        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col">Account Id</th>
                <th scope="col">Full name</th>
                <th scope="col">Phone</th>
                <th scope="col">Email</th>
                <th scope="col">Status</th>
                <th scope="col">Roles</th>
            </tr>
            </thead>
            <tbody>
                <%for (Account account : accountsWithRole) {%>
                <tr>
                    <th scope="row"><%=account.getAccountId()%></th>
                    <td><%=account.getFullName()%></td>
                    <td><%=account.getPhone()%></td>
                    <td><%=account.getEmail()%></td>
                    <td><%=account.getStatus()%></td>
                    <td><%=account.getGrantAccesses() == null ? "" : account.getGrantAccesses().stream().map(x -> x.getRole().getDescription()).collect(Collectors.joining(", "))%></td>
                </tr>
                <%}%>
            </tbody>
        </table>
    <%}%>

</div>
<%@ include file="/parts/footer.jsp" %>