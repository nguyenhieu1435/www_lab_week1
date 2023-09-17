<%@ page import="java.util.List" %>
<%@ page import="vn.edu.iuh.fit.models.Role" %>
<%@ page import="vn.edu.iuh.fit.models.Log" %>
<%@ page import="java.time.format.DateTimeFormatter" %><%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 12/09/2023
  Time: 10:15 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/parts/header.jsp" %>
<%@ include file="/parts/navbar.jsp" %>

<%
    if (!isAdmin) {
        response.sendRedirect("index.jsp");
    }
    String statusAccount = (String) session.getAttribute("statusAddAccount");
    String activeDashboardTab = (String) session.getAttribute("activeDashboardTab");
    String statusRUDAccount = (String) session.getAttribute("statusRUDAccount");
    String statusAddRole = (String)session.getAttribute("statusAddRole");
    String statusAddLog = (String)session.getAttribute("statusAddLog");
    String statusRudRole = (String)session.getAttribute("statusRudRole");
    String statusRUDLog = (String) session.getAttribute("statusRUDLog");
    List<Account> accountRUD = (List<Account>) session.getAttribute("accountRUD");
    List<Role> roleRUD = (List<Role>) session.getAttribute("roleRUD");
    List<Log> logRUD = (List<Log>) session.getAttribute("logRUD");
%>


<div class="row no-gutters dashboard-container">
    <div class="col-3 dashboard-control">

        <div class="dashboard-user">
            <div class="dashboard-user-img">
                <img src="https://i1.sndcdn.com/artworks-000189080723-ez2uad-t500x500.jpg"/>
            </div>
            <p><%=accountNav.getFullName()%>
            </p>
        </div>
        <ul class="nav nav-tabs" id="myTab" role="tablist" style="flex-direction: column">
            <li>
                Tài khoản
                <ul>
                    <li class="nav-item" role="presentation">
                        <button class="btn btn-primary mb-2 <%=activeDashboardTab == null ? "active" : ""%>"
                                id="add-account-tab" data-bs-toggle="tab" data-bs-target="#addAccount" type="button"
                                role="tab" aria-controls="addAccount" aria-selected="true">
                            Thêm tài khoản
                        </button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <form action="control-servlet" method="get">
                            <input type="hidden" name="action" value="getNewestDataAccount"/>
                            <button class="btn btn-primary mb-2 <%=(activeDashboardTab != null && activeDashboardTab.equalsIgnoreCase("rudAccount")) ? "active" : ""%>"
                                    id="rud-account-tab" data-bs-toggle="tab" data-bs-target="#rudAccount" type="submit"
                                    role="tab" aria-controls="rudAccount" aria-selected="false">
                                Sửa, Xóa, Xem Tài khoản
                            </button>
                        </form>
                    </li>
                </ul>
            </li>
            <li>
                Role
                <ul>
                    <li class="nav-item " role="presentation">
                        <button class="btn btn-primary mb-2 "
                                id="add-role" data-bs-toggle="tab" data-bs-target="#addRole" type="button" role="tab"
                                aria-controls="addRole" aria-selected="false">
                            Thêm role
                        </button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <form action="control-servlet" method="get">
                            <input type="hidden" name="action" value="getNewestDataRole"/>
                            <button class="btn btn-primary mb-2 <%=(activeDashboardTab != null && activeDashboardTab.equalsIgnoreCase("rudRole")) ? "active" : ""%>"
                                    id="rud-role" data-bs-toggle="tab" data-bs-target="#rudRole" type="submit" role="tab"
                                    aria-controls="rudRole" aria-selected="false">
                                    Sửa, Xóa, Xem role
                            </button>
                        </form>
                    </li>
                </ul>
            </li>
            <li>
                Log
                <ul>
                    <li class="nav-item " role="presentation">
                        <button class="btn btn-primary mb-2"
                                id="add-log" data-bs-toggle="tab" data-bs-target="#addLog" type="button" role="tab"
                                aria-controls="addLog" aria-selected="false">
                            Thêm Log
                        </button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <form action="control-servlet" method="get">
                            <input type="hidden" name="action" value="getNewestLog"/>
                            <button class="btn btn-primary mb-2 <%=(activeDashboardTab != null && activeDashboardTab.equalsIgnoreCase("rudLog")) ? "active" : ""%>"
                                    id="rud-log" data-bs-toggle="tab" data-bs-target="#rudLog" type="submit" role="tab"
                                    aria-controls="rudLog" aria-selected="false">
                                Sửa, Xóa, Xem Log
                            </button>
                        </form>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
    <div class="col-9 tab-content" id="myTabContent">
        <div class="tab-pane fade <%=activeDashboardTab == null ? "show active" : activeDashboardTab.equalsIgnoreCase("addAccount") ? "show active" : ""%>" id="addAccount" role="tabpanel" aria-labelledby="add-account-tab">
            <h3 class="text-primary text-center my-3">Thêm tài khoản</h3>
            <form style="width: 70%; margin: 0 auto" method="POST" action="control-servlet">
                <div class="mb-3">
                    <label for="inputAccountId" class="form-label">Account Id</label>
                    <input type="text" class="form-control" id="inputAccountId" name="accountId" required>
                </div>
                <div class="mb-3">
                    <label for="inputFullname" class="form-label">Fullname</label>
                    <input type="text" class="form-control" id="inputFullname" name="fullName" required>
                </div>
                <div class="mb-3">
                    <label for="inputEmail" class="form-label">Email</label>
                    <input type="email" class="form-control" id="inputEmail" name="email" required>
                </div>
                <div class="mb-3">
                    <label for="inputPhone" class="form-label">Phone</label>
                    <input type="text" class="form-control" id="inputPhone" name="phone" required>
                </div>
                <div class="mb-3">
                    <label for="inputPassword" class="form-label">Password</label>
                    <input type="text" class="form-control" id="inputPassword" name="password" required>
                </div>

                <input type="hidden" name="action" value="addNewAccount"/>

                <div class="text-center">
                    <button type="submit" class="btn btn-success">Tạo tài khoản</button>
                </div>

                <div class="text-center">
                    <h3 class="text-primary"><%=(statusAccount == null || statusAccount.isEmpty()) ? "" : statusAccount%>
                    </h3>
                </div>
            </form>

        </div>

        <div class="tab-pane fade <%=(activeDashboardTab != null && activeDashboardTab.equalsIgnoreCase("rudAccount")) ? "show active" : ""%>" id="rudAccount" role="tabpanel" aria-labelledby="rud-account-tab">
            <h3 class="text-primary text-center my-3">Sửa, Xóa, Xem tài khoản</h3>

            <h5 class="text-warning text-center my-3"><%=(statusRUDAccount == null || statusRUDAccount.isEmpty()) ? ""  : statusRUDAccount%></h5>

            <div style="margin: 0 auto; width: 98%; ">
                <div style="display: flex; align-items: center; width: 100%; justify-content: center; " >
                    <div style="display: flex; justify-content: center; align-items: center; border-bottom: 1px solid #ccc; padding-bottom: 2px; flex-grow: 1">
                        <div style="width: calc(100%/7)">
                            Account Id
                        </div>
                        <div style="width: calc(100%/7)">
                            Fullname
                        </div>
                        <div style="width: calc(100%/7)">
                            Email
                        </div>
                        <div style="width: calc(100%/7)">
                            Phone
                        </div>
                        <div style="width: calc(100%/7)">
                            Password
                        </div>
                        <div style="width: calc(100%/7)">
                            Status
                        </div>
                        <div style="width: calc(100%/7)">
                            Sửa
                        </div>
                    </div>
                    <div style="width: 10%">
                        Xóa
                    </div>
                </div>
                <%for (Account account : accountRUD) {%>
                    <div style="display: flex; align-items: center;">
                        <form action="control-servlet" method="POST"
                              style="display: flex; justify-content: center; align-items: center; border-bottom: 1px solid #ccc; padding-bottom: 2px">
                            <div style="width: calc(100%/7); word-wrap: break-word;">
                                <input style="outline: none; display: block; width: 100%;border: none" type="text" name="accountId"
                                       value="<%=account.getAccountId()%>" readonly required/>
                            </div>
                            <div style="width: calc(100%/7); word-wrap: break-word;">
                                <input style="outline: none; display: block; width: 100%" type="text" name="fullname"
                                       value="<%=account.getFullName()%>" required>
                            </div>
                            <div style="width: calc(100%/7);word-wrap: break-word;">
                                <input style="outline: none; display: block; width: 100%" type="email" name="email"
                                       value="<%=account.getEmail()%>" required>
                            </div>
                            <div style="width: calc(100%/7);word-wrap: break-word;">
                                <input style="outline: none; display: block; width: 100%" type="text" name="phone"
                                       value="<%=account.getPhone()%>" required>
                            </div>
                            <div style="width: calc(100%/7);word-wrap: break-word;">
                                <input style="outline: none; display: block; width: 100%" type="text" name="password"
                                       value="<%=account.getPassword()%>" required>
                            </div>
                            <div style="width: calc(100%/7);word-wrap: break-word;">
                                <select name="status">
                                    <option value="ACTIVE" <%=account.getStatus().toString().equalsIgnoreCase("ACTIVE") ? "selected" : ""%>>ACTIVE</option>
                                    <option value="DEACTIVE" <%=account.getStatus().toString().equalsIgnoreCase("DEACTIVE") ? "selected" : ""%>>DEACTIVE</option>
                                    <option value="DELETE" <%=account.getStatus().toString().equalsIgnoreCase("DELETE") ? "selected" : ""%>>DELETE</option>
                                </select>
                            </div>
                            <input type="hidden" name="action" value="updateAccountFn"/>
                            <div style="width: calc(100%/8);word-wrap: break-word;">
                                <button type="submit" class="btn-warning btn">Sửa</button>
                            </div>

                        </form>
                        <div style="width: 10%;word-wrap: break-word;">
                            <form action="control-servlet" method="POST">
                                <input type="hidden" name="action" value="deleteOneAccount"/>
                                <input type="hidden" name="accountIdForDelete" value="<%=account.getAccountId()%>">
                                <button type="submit" class="btn btn-danger">Xóa</button>
                            </form>
                        </div>
                    </div>
                <%}%>
            </div>
        </div>
        <div class="tab-pane fade  <%=(activeDashboardTab != null && activeDashboardTab.equalsIgnoreCase("addRole")) ? "show active" : ""%>"
             id="addRole" role="tabpanel" aria-labelledby="add-role">
            <h3 class="text-primary text-center my-3">Thêm Role</h3>

            <form style="width: 70%; margin: 0 auto" method="POST" action="control-servlet">
                <div class="mb-3">
                    <label for="roleId" class="form-label">Role Id</label>
                    <input type="text" class="form-control" id="roleId" name="roleId" required>
                </div>
                <div class="mb-3">
                    <label for="roleName" class="form-label">Role name</label>
                    <input type="text" class="form-control" id="roleName" name="roleName" required>
                </div>
                <div class="mb-3">
                    <label for="descriptionRole" class="form-label">Description</label>
                    <input type="text" class="form-control" id="descriptionRole" name="description" required>
                </div>

                <input type="hidden" name="action" value="addNewRole"/>

                <div class="text-center">
                    <button type="submit" class="btn btn-success">Tạo Role</button>
                </div>

                <div class="text-center">
                    <h3 class="text-primary">
                        <%=(statusAddRole == null || statusAddRole.isEmpty()) ? "" : statusAddRole%>
                    </h3>
                </div>
            </form>
        </div>
        <div class="tab-pane fade  <%=(activeDashboardTab != null && activeDashboardTab.equalsIgnoreCase("rudRole")) ? "show active" : ""%>"
             id="rudRole" role="tabpanel" aria-labelledby="rud-role">
            <h3 class="text-primary text-center my-3">Sửa, Xóa, Xem tài khoản</h3>
            <h5 class="text-warning text-center my-3"><%=(statusRudRole == null || statusRudRole.isEmpty()) ? ""  : statusRudRole%></h5>
            <div style="margin: 0 auto; width: 98%; ">
                <div style="display: flex; align-items: center; width: 100%; justify-content: center; " >
                    <div style="display: flex; justify-content: center; align-items: center; border-bottom: 1px solid #ccc; padding-bottom: 2px; flex-grow: 1">
                        <div style="width: calc(100%/5)">
                            Role Id
                        </div>
                        <div style="width: calc(100%/5)">
                            Role name
                        </div>
                        <div style="width: calc(100%/5)">
                            Role description
                        </div>
                        <div style="width: calc(100%/5)">
                            Status
                        </div>
                        <div style="width: calc(100%/5)">
                            Sửa
                        </div>
                    </div>
                    <div style="width: 10%">
                        Xóa
                    </div>
                </div>
                <%for (Role role : roleRUD) {%>
                <div style="display: flex; align-items: center;">
                    <form action="control-servlet" method="POST"
                          style="display: flex; justify-content: center; align-items: center; border-bottom: 1px solid #ccc; padding-bottom: 2px; flex-grow: 1">
                        <div style="width: calc(100%/5); word-wrap: break-word;">
                            <input style="outline: none; display: block; width: 100%;border: none" type="text" name="roleId"
                                   value="<%=role.getRoleId()%>" readonly required/>
                        </div>
                        <div style="width: calc(100%/5); word-wrap: break-word;">
                            <input style="outline: none; display: block; width: 100%" type="text" name="roleName"
                                   value="<%=role.getRoleName()%>" required>
                        </div>
                        <div style="width: calc(100%/5);word-wrap: break-word;">
                            <input style="outline: none; display: block; width: 100%" type="text" name="roleDesc"
                                   value="<%=role.getDescription()%>" required>
                        </div>
                        <div style="width: calc(100%/5);word-wrap: break-word;">
                            <select name="status">
                                <option value="ACTIVE" <%=role.getStatus().toString().equalsIgnoreCase("ACTIVE") ? "selected" : ""%>>ACTIVE</option>
                                <option value="DEACTIVE" <%=role.getStatus().toString().equalsIgnoreCase("DEACTIVE") ? "selected" : ""%>>DEACTIVE</option>
                                <option value="DELETE" <%=role.getStatus().toString().equalsIgnoreCase("DELETE") ? "selected" : ""%>>DELETE</option>
                            </select>
                        </div>
                        <input type="hidden" name="action" value="updateRoleFn"/>
                        <div style="width: calc(100%/5);word-wrap: break-word;">
                            <button type="submit" class="btn-warning btn">Sửa</button>
                        </div>

                    </form>
                    <div style="width: 10%;word-wrap: break-word;">
                        <form action="control-servlet" method="POST">
                            <input type="hidden" name="action" value="deleteOneRole"/>
                            <input type="hidden" name="roleIdForDelete" value="<%=role.getRoleId()%>">
                            <button type="submit" class="btn btn-danger">Xóa</button>
                        </form>
                    </div>
                </div>
                <%}%>
            </div>
        </div>
        <div class="tab-pane fade  <%=(activeDashboardTab != null && activeDashboardTab.equalsIgnoreCase("addLog")) ? "show active" : ""%>"
             id="addLog" role="tabpanel" aria-labelledby="add-log">
            <h3 class="text-primary text-center my-3">Thêm Log</h3>

            <form style="width: 70%; margin: 0 auto" method="POST" action="control-servlet">
                <div class="mb-3">
                    <label for="accountId" class="form-label">Account ID</label>
                    <input type="text" class="form-control" id="accountId" name="accountId" required>
                </div>
                <div class="mb-3">
                    <label for="noteId" class="form-label">Note</label>
                    <input type="text" class="form-control" id="noteId" name="note">
                </div>
                <div class="mb-3">
                    <label for="loginDate" class="form-label">Login Date</label>
                    <input type="datetime-local" class="form-control" id="loginDate" name="loginDate" required>
                </div>
                <div class="mb-3">
                    <label for="logoutDate" class="form-label">Logout Date</label>
                    <input type="datetime-local" class="form-control" id="logoutDate" name="logoutDate" required>
                </div>

                <input type="hidden" name="action" value="addNewLog"/>

                <div class="text-center">
                    <button type="submit" class="btn btn-success">Tạo log</button>
                </div>

                <div class="text-center">
                    <h3 class="text-primary">
                        <%=(statusAddLog == null || statusAddLog.isEmpty()) ? "" : statusAddLog%>
                    </h3>
                </div>
            </form>
        </div>
        <div class="tab-pane fade  <%=(activeDashboardTab != null && activeDashboardTab.equalsIgnoreCase("rudLog")) ? "show active" : ""%>"
             id="rudLog" role="tabpanel" aria-labelledby="rud-log">
            <h3 class="text-primary text-center my-3">Xóa, Sửa, Xem Log</h3>
            <h5 class="text-warning text-center my-3"><%=(statusRUDLog == null || statusRUDLog.isEmpty()) ? "" : statusRUDLog%></h5>
            <div style="margin: 0 auto; width: 98%; ">
                <div style="display: flex; align-items: center; width: 100%; justify-content: center; " >
                    <div style="display: flex; justify-content: center; align-items: center; border-bottom: 1px solid #ccc; padding-bottom: 2px; flex-grow: 1">
                        <div style="width: calc(100%/6)">
                            Log Id
                        </div>
                        <div style="width: calc(100%/6)">
                            Account Id
                        </div>
                        <div style="width: calc(100%/6)">
                            Note
                        </div>
                        <div style="width: calc(100%/6)">
                            Login Date
                        </div>
                        <div style="width: calc(100%/6)">
                            Logout Date
                        </div>
                        <div style="width: calc(100%/6)">
                            Sửa
                        </div>
                    </div>
                    <div style="width: 10%">
                        Xóa
                    </div>
                </div>
                <% for (Log log : logRUD) { %>
                <div style="display: flex; align-items: center;">
                    <form action="control-servlet" method="POST"
                          style="display: flex; justify-content: center; align-items: center; border-bottom: 1px solid #ccc; padding-bottom: 2px; flex-grow: 1">
                        <div style="width: calc(100%/6); word-wrap: break-word;">
                            <input style="outline: none; display: block; width: 100%;border: none" type="text" name="logId"
                                   value="<%=log.getId()%>" readonly required/>
                        </div>
                        <div style="width: calc(100%/6); word-wrap: break-word;">
                            <input style="outline: none; border: none; display: block; width: 100%" type="text" name="accountId"
                                   value="<%=log.getAccountId()%>" readonly required/>
                        </div>
                        <div style="width: calc(100%/6);word-wrap: break-word;">
                            <input style="outline: none; display: block; width: 100%" type="text" name="note"
                                   value="<%=log.getNotes()%>"/>
                        </div>
                        <div style="width: calc(100%/6);word-wrap: break-word;">
                            <input style="outline: none; display: block; width: 100%"  type="datetime-local" name="loginDate"
                                   value="<%=log.getLoginTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))%>"
                                required
                            />
                        </div>
                        <div style="width: calc(100%/6);word-wrap: break-word;">
                            <input style="outline: none; display: block; width: 100%"  type="datetime-local" name="logoutDate"
                                   value="<%=log.getLogoutTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))%>"
                                required
                            />
                        </div>
                        <input type="hidden" name="action" value="updateLogFN"/>
                        <div style="width: calc(100%/5);word-wrap: break-word;">
                            <button type="submit" class="btn-warning btn">Sửa</button>
                        </div>
                    </form>
                    <div style="width: 10%;word-wrap: break-word;">
                        <form action="control-servlet" method="POST">
                            <input type="hidden" name="action" value="deleteOneLog"/>
                            <input type="hidden" name="logIdForDelete" value="<%=log.getId()%>"/>
                            <button type="submit" class="btn btn-danger">Xóa</button>
                        </form>
                    </div>
                </div>
                <%}%>
            </div>
        </div>

    </div>
</div>

<%
    session.removeAttribute("activeDashboardTab");
    session.removeAttribute("statusAddAccount");
    session.removeAttribute("statusAddRole");
    session.removeAttribute("statusRUDAccount");
    session.removeAttribute("statusRudRole");
    session.removeAttribute("statusRUDLog");
    session.removeAttribute("statusAddLog");
%>

<%@ include file="/parts/footer.jsp" %>