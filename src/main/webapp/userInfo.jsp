<%@ page import="vn.edu.iuh.fit.models.Account" %><%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 12/09/2023
  Time: 8:44 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%
  Account account = null;
  if (request.getSession().getAttribute("account") == null){
    response.sendRedirect("loginPage.jsp");
  } else {
    account = (Account)request.getSession().getAttribute("account");
  }
%>
<%@ include file="/parts/header.jsp" %>
<%@ include file="/parts/navbar.jsp"%>
  <div class="w-100 h-100 mx-auto d-flex justify-content-center align-items-center">
      <div class="cardInfo">
          <div class="cardAvt">
            <img src="https://i1.sndcdn.com/artworks-000189080723-ez2uad-t500x500.jpg"/>
          </div>
          <div class="mt-4 px-5">
            <p class="font-weight-bold">Fullname: <span><%= account != null ?  account.getFullName() : null %></span></p>
            <p class="font-weight-bold">Account: <span><%=  account != null ?  account.getAccountId() : null %></span></p>
            <p class="font-weight-bold">Email: <span><%=  account != null ?  account.getEmail() : null %></span></p>
            <p class="font-weight-bold">Phone: <span><%=  account != null ?  account.getPhone() : null %></span></p>
            <p class="font-weight-bold">Account status: <span><%=  account != null ?  account.getStatus() : null %></span></p>
          </div>
      </div>
  </div>

<%@ include file="/parts/footer.jsp" %>