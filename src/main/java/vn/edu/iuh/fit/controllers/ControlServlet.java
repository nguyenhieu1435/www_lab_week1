package vn.edu.iuh.fit.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.iuh.fit.models.*;
import vn.edu.iuh.fit.services.AccountService;
import vn.edu.iuh.fit.services.LogService;
import vn.edu.iuh.fit.services.RoleService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "ControlServlet", value = "/control-servlet")
public class ControlServlet extends HttpServlet {
    private AccountService accountService;
    private LogService logService;
    private RoleService roleService;

    public ControlServlet(){
        accountService = new AccountService();
        logService = new LogService();
        roleService = new RoleService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        System.out.println("Action " + action);
        switch (action){
            case "getLogList": {
                getLogList(req, resp);
                break;
            }
            case "getListAccByRole": {
                getListAccByRole(req, resp);
                break;
            }
            case "getRoleList":{
                getRoleList(req, resp);
                break;
            }
            case "getRoleOfAllAcc": {
                getRoleOfAllAcc(req, resp);
                break;
            }
            case "handleOpenDashboard": {
                handleOpenDashboard(req, resp);
                break;
            }
            case "getNewestDataAccount": {
                getNewestDataAccount(req, resp);
                break;
            }
            case "getNewestDataRole": {
                getNewestDataRole(req, resp);
                break;
            }
            case "getNewestLog": {
                getNewestLog(req, resp);
                break;
            }
            default:{
                resp.sendError(400, "Post Action is invalid");
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        System.out.println("Action " + action);
        switch (action){
            case "loginAction": {

                loginAction(req, resp);
                break;
            }
            case "logout":{
                logoutAction(req, resp);
                break;
            }
            case "addNewAccount": {
                addNewAccount(req, resp);
                break;
            }
            case "updateAccountFn":{
                updateAccountFn(req, resp);
                break;
            }
            case "deleteOneAccount": {
                deleteOneAccount(req, resp);
                break;
            }
            case "addNewRole": {
                addNewRole(req, resp);
                break;
            }
            case "updateRoleFn":{
                updateRoleFn(req, resp);
                break;
            }
            case "deleteOneRole": {
                deleteOneRole(req, resp);
                break;
            }
            case "addNewLog":{
                addNewLog(req, resp);
                break;
            }
            case "updateLogFN": {
                updateLogFN(req, resp);
                break;
            }
            case "deleteOneLog": {
                deleteOneLog(req, resp);
                break;
            }
            default:{
                resp.sendError(400, "Post Action is invalid");
                break;
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }


    protected void loginAction(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String accountId = req.getParameter("accountId");
        String password = req.getParameter("password");

        Optional<Account> optional;
        HttpSession httpSession;
        try {
            optional = accountService.login(accountId, password);

            boolean isLogged = logService.add(new Log(accountId, ""));

            Account account = optional.isPresent() ? optional.get() : null;

            boolean isAdmin = false;
            httpSession = req.getSession();

            httpSession.setAttribute("account", account);
            // remove errorMsg session

            httpSession.removeAttribute("loginError");

            for (GrantAccess grantAccess : account.getGrantAccesses()) {
                if (grantAccess.getRole().getRoleId().equalsIgnoreCase("admin")) {
                    isAdmin = true;
                    break;
                }
            }
            if (isAdmin && account.getStatus().getStatusNumber() == 1){
                handleOpenDashboard(req, resp);
            } else if (!isAdmin && account.getStatus().getStatusNumber() == 1) {
                resp.sendRedirect("userInfo.jsp");
            } else if (account.getStatus().getStatusNumber() < 1) {
                httpSession.removeAttribute("account");
                httpSession.setAttribute("loginError", "Tài khoản đã bị vô hiệu hóa");
                resp.sendRedirect("loginPage.jsp");
            }

        } catch (Exception e){
            resp.sendRedirect("loginPage.jsp");
            httpSession = req.getSession();
            httpSession.setAttribute("loginError", "Tài khoản hoặc mật khẩu không chính xác");
        }
    }

    public void logoutAction(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession httpSession = req.getSession();
        Account account = (Account) httpSession.getAttribute("account");

        if (account == null || account.getAccountId() == null){
            resp.sendRedirect("loginPage.jsp");
            return;
        }
        boolean isUpdateLogoutTime = logService.updateLogoutTime(account.getAccountId());

        if (isUpdateLogoutTime){
            httpSession.removeAttribute("account");
            httpSession.removeAttribute("logs");
            httpSession.removeAttribute("roles");
            httpSession.removeAttribute("accountsByRole");
            httpSession.removeAttribute("roleWasChosen");
            httpSession.removeAttribute("rolesBelongToAccount");
            resp.sendRedirect("loginPage.jsp");
        }
    }

    public void getLogList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Log> logs = logService.getAll();

        HttpSession httpSession = req.getSession();

        httpSession.setAttribute("logs", logs);

        resp.sendRedirect("logPage.jsp");

    }
    public void getListAccByRole(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();

        String roleId = req.getParameter("roleId");

        if (roleId != null){
            List<Account> accounts = accountService.getAccountsByRoleId(roleId);

            Role role = null;
            try {
                Optional<Role> op =  roleService.findOne(roleId);
                if (op.isPresent()){
                    role = op.get();
                }
            } catch (Exception e) {

            }

            httpSession.setAttribute("accountsByRole", accounts);
            httpSession.setAttribute("roleWasChosen", role != null ? role.getRoleName() : null);
            resp.sendRedirect("accountListByRole.jsp");
        } else {
            resp.sendRedirect("accountListByRole.jsp");
        }


    }
    public void getRoleList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Role> roles = roleService.getAll();
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("roles", roles);


        if (roles.size() > 0){
            List<Account> accounts = accountService.getAccountsByRoleId(roles.get(0).getRoleId());
            httpSession.setAttribute("roleWasChosen", roles.get(0).getRoleName());
            httpSession.setAttribute("accountsByRole", accounts);
        }
        resp.sendRedirect("accountListByRole.jsp");
    }
    public void getRoleOfAllAcc(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Account> accounts = accountService.getAll();

        for (Account account : accounts){
            account.getGrantAccesses();
        }

        HttpSession httpSession = req.getSession();

        httpSession.setAttribute("rolesBelongToAccount", accounts);

        resp.sendRedirect("viewRoleOfUser.jsp");

    }
    public void addNewAccount(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String accountId = req.getParameter("accountId");
        String fullName = req.getParameter("fullName");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");
        HttpSession httpSession = req.getSession();

        if (accountId == null || accountId.trim().isEmpty() || fullName == null || fullName.trim().isEmpty() || email == null || email.trim().isEmpty()
        || phone == null || phone.trim().isEmpty() || password == null || password.trim().isEmpty() ){

            httpSession.setAttribute("activeDashboardTab", "addAccount");
            httpSession.setAttribute("statusAddAccount", "Không được để trống field nào!");
            resp.sendRedirect("dashboard.jsp");
            return;
        }

        Account newAccount = new Account(accountId, fullName, password, email, phone, Status.ACTIVE);

        boolean isAdded = accountService.add(newAccount);

        if (isAdded){
            httpSession.setAttribute("statusAddAccount", "Thêm thành công!");
        } else{
            httpSession.setAttribute("statusAddAccount", "Thêm thất bại!");
        }
        httpSession.setAttribute("activeDashboardTab", "addAccount");
        resp.sendRedirect("dashboard.jsp");

    }
    public void handleOpenDashboard(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Account> accounts = accountService.getAll();
        List<Role> roles = roleService.getAll();
        List<Log> logs = logService.getAll();
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("accountRUD", accounts);
        httpSession.setAttribute("roleRUD", roles);
        httpSession.setAttribute("logRUD", logs);
        resp.sendRedirect("dashboard.jsp");
    }
    public void getNewestDataAccount(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Account> accounts = accountService.getAll();
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("accountRUD", accounts);
        httpSession.setAttribute("activeDashboardTab", "rudAccount");
        resp.sendRedirect("dashboard.jsp");
    }
    public void getNewestDataRole(HttpServletRequest req, HttpServletResponse resp) throws  IOException{
        List<Role> roles = roleService.getAll();
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("roleRUD", roles);
        httpSession.setAttribute("activeDashboardTab", "rudRole");
        resp.sendRedirect("dashboard.jsp");
    }
    public void updateAccountFn(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession httpSession = req.getSession();
        String accountId = req.getParameter("accountId");
        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");
        String status = req.getParameter("status");

        if (accountId == null || accountId.trim().isEmpty() || fullname == null || fullname.trim().isEmpty() || email == null || email.trim().isEmpty()
        || phone == null || phone.trim().isEmpty() || password == null || password.trim().isEmpty() || status == null || status.trim().isEmpty()){
            httpSession.setAttribute("statusRUDAccount", "Không được đế trống các field!");
            httpSession.setAttribute("activeDashboardTab", "rudAccount");
            resp.sendRedirect("dashboard.jsp");
            return;
        }

        Status statusEnum = Status.ACTIVE;
        if (status.equalsIgnoreCase(Status.DEACTIVE.name())){
            statusEnum = Status.DEACTIVE;
        } else if (status.equalsIgnoreCase(Status.DELETE.name())){
            statusEnum = Status.DELETE;
        }
        Account account  = new Account(accountId, fullname, password, email, phone, statusEnum );

        boolean isUpdated = accountService.update(account);

        if (isUpdated){
            httpSession.setAttribute("statusRUDAccount", "Cập nhật thành công");

        } else {
            httpSession.setAttribute("statusRUDAccount", "Cập nhật thất bại");
        }
        httpSession.setAttribute("activeDashboardTab", "rudAccount");
        resp.sendRedirect("dashboard.jsp");
    }
    public void deleteOneAccount(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession httpSession = req.getSession();

        String accountId = req.getParameter("accountIdForDelete");

        if (accountId.trim().isEmpty()){
            httpSession.setAttribute("statusRUDAccount", "Xóa thất bại");
            httpSession.setAttribute("activeDashboardTab", "rudAccount");
            resp.sendRedirect("dashboard.jsp");
            return;
        }

        boolean isDeleted = accountService.delete(accountId);

        if (isDeleted){
            httpSession.setAttribute("statusRUDAccount", "Xóa thành công");
            getNewestDataAccount(req, resp);
            return;
        } else {
            httpSession.setAttribute("statusRUDAccount", "Xóa thất bại");
        }
        httpSession.setAttribute("activeDashboardTab", "rudAccount");
        resp.sendRedirect("dashboard.jsp");
    }
    public void addNewRole(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String roleId = req.getParameter("roleId");
        String roleName = req.getParameter("roleName");
        String roleDescription = req.getParameter("description");
        Status status = Status.ACTIVE;
        HttpSession httpSession = req.getSession();

        if (roleId.trim().isEmpty() || roleName.trim().isEmpty() || roleDescription.trim().isEmpty()){
            httpSession.setAttribute("activeDashboardTab", "addRole");
            httpSession.setAttribute("statusAddRole", "Không được để trống field nào!");
            resp.sendRedirect("dashboard.jsp");
            return;
        }

        Role role = new Role(roleId, roleName, roleDescription, status);

        boolean isAdded = roleService.add(role);

        if (isAdded){
            httpSession.setAttribute("statusAddRole", "Thêm thành công!");
        } else{
            httpSession.setAttribute("statusAddRole", "Thêm thất bại!");
        }
        httpSession.setAttribute("activeDashboardTab", "addRole");
        resp.sendRedirect("dashboard.jsp");
    }
    public void updateRoleFn(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession httpSession = req.getSession();

        String roleId = req.getParameter("roleId");
        String roleName = req.getParameter("roleName");
        String roleDesc = req.getParameter("roleDesc");
        String status = req.getParameter("status");

        if (roleId.trim().isEmpty() || roleName.trim().isEmpty() || roleDesc.trim().isEmpty()){
            httpSession.setAttribute("statusRudRole", "Không được đế trống các field!");
            httpSession.setAttribute("activeDashboardTab", "rudRole");
            resp.sendRedirect("dashboard.jsp");
            return;
        }
        Status statusEnum = Status.ACTIVE;
        if (status.equalsIgnoreCase(Status.DEACTIVE.name())){
            statusEnum = Status.DEACTIVE;
        } else if (status.equalsIgnoreCase(Status.DELETE.name())){
            statusEnum = Status.DELETE;
        }
        Role role = new Role(roleId, roleName, roleDesc, statusEnum);
        boolean isUpdated = roleService.update(role);

        if (isUpdated){
            httpSession.setAttribute("statusRudRole", "Cập nhật thành công");

        } else {
            httpSession.setAttribute("statusRudRole", "Cập nhật thất bại");
        }
        httpSession.setAttribute("activeDashboardTab", "rudRole");
        resp.sendRedirect("dashboard.jsp");
    }
    public void deleteOneRole(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String roleId = req.getParameter("roleIdForDelete");
        HttpSession httpSession = req.getSession();
        if (roleId.trim().isEmpty()){
            httpSession.setAttribute("statusRudRole", "Xóa thất bại");
            httpSession.setAttribute("activeDashboardTab", "rudRole");
            resp.sendRedirect("dashboard.jsp");
            return;
        }

        boolean isDeleted = roleService.delete(roleId);

        if (isDeleted){
            httpSession.setAttribute("statusRudRole", "Xóa thành công");
            getNewestDataAccount(req, resp);
            return;
        } else {
            httpSession.setAttribute("statusRudRole", "Xóa thất bại");
        }
        httpSession.setAttribute("activeDashboardTab", "rudRole");
        resp.sendRedirect("dashboard.jsp");
    }
    public void addNewLog(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession httpSession = req.getSession();
        String accountId  = req.getParameter("accountId");
        String loginDateString  = req.getParameter("loginDate");
        String logoutDateString = req.getParameter("logoutDate");
        String note = req.getParameter("note");

        if (accountId.trim().isEmpty()){
            httpSession.setAttribute("statusAddLog", "Không được để trống account id");
            httpSession.setAttribute("activeDashboardTab", "addLog");
            resp.sendRedirect("dashboard.jsp");
            return;
        }

        try {
            Optional<Account> op = accountService.findOne(accountId);
            Account acc = op.isPresent() ? op.get() : null;

        } catch (NullPointerException e){
            httpSession.setAttribute("statusAddLog", "Tài khoản không tồn tại");
            httpSession.setAttribute("activeDashboardTab", "addLog");
            resp.sendRedirect("dashboard.jsp");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime loginTime = LocalDateTime.parse(loginDateString, formatter);
        LocalDateTime logoutTime = LocalDateTime.parse(logoutDateString, formatter);

        boolean isAdded = logService.add(new Log(accountId, loginTime, logoutTime, note));

        if (isAdded){
            httpSession.setAttribute("statusAddLog", "Thêm thành công");
        } else {
            httpSession.setAttribute("statusAddLog", "Thêm thất bại");
        }
        httpSession.setAttribute("activeDashboardTab", "addLog");
        resp.sendRedirect("dashboard.jsp");
    }
    public void getNewestLog(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Log> logs = logService.getAll();
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("logRUD", logs);
        httpSession.setAttribute("activeDashboardTab", "rudLog");
        resp.sendRedirect("dashboard.jsp");

    }
    public void updateLogFN(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        HttpSession httpSession = req.getSession();

        String logId = req.getParameter("logId");
        String accountId = req.getParameter("accountId");
        String note = req.getParameter("note");
        String loginDateString = req.getParameter("loginDate");
        String logoutDateString = req.getParameter("logoutDate");

        if (logId.trim().isEmpty() || accountId.trim().isEmpty()){
            httpSession.setAttribute("statusRUDLog", "account id không được để trống!");
            httpSession.setAttribute("activeDashboardTabc", "rudLog");
            resp.sendRedirect("dashboard.jsp");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime loginTime = LocalDateTime.parse(loginDateString, formatter);
        LocalDateTime logoutTime = LocalDateTime.parse(logoutDateString, formatter);

        Log log = new Log(Long.parseLong(logId), accountId, loginTime, logoutTime, note);

        boolean isUpdated = logService.update(log);
        System.out.println(isUpdated);
        if (isUpdated){
            httpSession.setAttribute("statusRUDLog", "Sửa thành công");
        } else {
            httpSession.setAttribute("statusRUDLog", "Sửa thất bại");
        }
        httpSession.setAttribute("activeDashboardTab", "rudLog");
        resp.sendRedirect("dashboard.jsp");
    }
    public void deleteOneLog(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String logId = req.getParameter("logIdForDelete");
        HttpSession httpSession = req.getSession();
        boolean isDeleted = logService.delete(Long.parseLong(logId));

        if (isDeleted){
            httpSession.setAttribute("statusRUDLog", "Xóa thành công");
        } else {
            httpSession.setAttribute("statusRUDLog", "Xóa thất bại");
        }
        List<Log> logs = logService.getAll();
        httpSession.setAttribute("logRUD", logs);
        httpSession.setAttribute("activeDashboardTab", "rudLog");
        resp.sendRedirect("dashboard.jsp");
    }
}
