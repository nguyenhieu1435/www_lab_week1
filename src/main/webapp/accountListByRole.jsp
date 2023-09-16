<%@ page import="java.util.List" %>
<%@ page import="org.checkerframework.checker.units.qual.A" %>
<%@ page import="vn.edu.iuh.fit.models.Role" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/parts/header.jsp" %>
<%@ include file="/parts/navbar.jsp"%>

<%
    if (accountNav == null){
        response.sendRedirect("loginPage.jsp");
        return;
    }
    List<Role> roles = (List<Role>)request.getSession().getAttribute("roles");
    List<Account> accounts = (List<Account>) request.getSession().getAttribute("accountsByRole");
    String roleWasSelected = (String)request.getSession().getAttribute("roleWasChosen");


%>

<div class="container">
    <form method="GET" action="control-servlet" class="mt-5 mb-5 text-center">
        <div class="d-flex align-items-center justify-content-center">
             <label class="font-weight-bold h3">Select role</label>
            <%if (roles != null && roles.size() > 0) {%>
                <%for (int i = 0; i < roles.size(); i++){%>
                    <div class="d-flex mx-3 justify-content-center" style="cursor: pointer">
                        <input type="radio" name="roleId" id="<%=roles.get(i).getRoleId()%>" style="cursor: pointer" value="<%=roles.get(i).getRoleId()%>"
                            <%= (i == 0) ? "checked" : ""%>
                        />

                        <label class="ml-2 h4 d-inline-block mx-2" style="cursor: pointer" for="<%=roles.get(i).getRoleId()%>"><%=roles.get(i).getRoleName()%></label>
                    </div>
                <%}%>
            <%} else {%>
                <form action="control-servlet" method="GET">
                    <input type="hidden" name="action" value="getRoleList"/>
                    <button type="submit" class="mx-3 btn btn-primary">Lấy danh sách Role</button>
                </form>
            <%}%>


        </div>

        <% if (roles == null) { %>
            <div class=""></div>
        <% } else {%>
            <div class="">
                <input type="submit" class="btn btn-success" value="Lấy danh sách"/>
            </div>
        <%}%>
        <input type="hidden" name="action" value="getListAccByRole" />
    </form>
    <%if (accounts == null || accounts.size() == 0) {%>
        <h1>Không có dữ liệu</h1>
    <%} else {%>
        <h3>Danh sách tài khoản thuộc role <%=roleWasSelected != null ? roleWasSelected : ""%></h3>
        <div>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">Account ID</th>
                    <th scope="col">Fullname</th>
                    <th scope="col">Email</th>
                    <th scope="col">Phone</th>
                    <th scope="col">Status</th>
                </tr>
                </thead>
                <tbody>
                    <%for (Account account : accounts) {%>
                        <tr>
                            <th scope="row"><%=account.getAccountId()%></th>
                            <td><%=account.getFullName()%></td>
                            <td><%=account.getEmail()%></td>
                            <td><%=account.getPhone()%></td>
                            <td><%=account.getStatus()%></td>
                        </tr>
                    <%}%>

                </tbody>
            </table>
        </div>
    <%}%>

</div>


<%@ include file="/parts/footer.jsp" %>