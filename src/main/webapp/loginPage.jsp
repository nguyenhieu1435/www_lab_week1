<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 12/09/2023
  Time: 7:45 SA
  To change this template use File | Settings | File Templates.
--%>


<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/parts/header.jsp" %>

<%@ include file="/parts/navbar.jsp"%>
<%

    String errorMsg = (String) request.getSession().getAttribute("loginError");

    if (request.getSession().getAttribute("account") != null){
        response.sendRedirect("userInfo.jsp");
    }
%>

<div class="w-50 mx-auto">
    <div class="mt-5">
        <h1 class="text-center">Đăng nhập</h1>
    </div>
    <form action="control-servlet" method="POST" class="mt-5">
        <div class="form-group mb-3">
            <label for="accountId">Account ID</label>
            <input type="text" class="form-control" id="accountId" name="accountId" placeholder="Please input account id"/>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control" id="password" name="password" placeholder="Please input password"/>
        </div>
        <div class="form-group">
            <input type="hidden" name="action" value="loginAction">
        </div>
        <div class="form-group text-center mt-3">
            <input type="submit" value="Submit" class="btn btn-primary">
        </div>

        <% if (errorMsg != null) {%>
            <div>
                    <span class="text-danger">
                        <%=errorMsg != null ? errorMsg : null%>
                    </span>
            </div>
        <% } %>


    </form>
</div>
<%@ include file="/parts/footer.jsp" %>