package vn.edu.iuh.fit.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "log")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT(20)")
    private long id;
    @Column(name = "account_id", columnDefinition = "VARCHAR(50)", nullable = false)
    private String accountId;
    @Column(name = "login_time", columnDefinition = "DATETIME DEFAULT current_timestamp()", nullable = false)
    private LocalDateTime loginTime;
    @Column(name = "logout_time", columnDefinition = "DATETIME DEFAULT current_timestamp()", nullable = false)
    private LocalDateTime logoutTime;
    @Column(columnDefinition = "VARCHAR(250) default ''", nullable = false)
    private String notes;

    public Log() {
        this.loginTime = LocalDateTime.now();
        this.logoutTime = LocalDateTime.now();
    }

    public Log(String accountId, String notes) {
        this.accountId = accountId;
        this.notes = notes;
        this.loginTime = LocalDateTime.now();
        this.logoutTime = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    public LocalDateTime getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(LocalDateTime logoutTime) {
        this.logoutTime = logoutTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", accountId='" + accountId + '\'' +
                ", loginTime=" + loginTime +
                ", logoutTime=" + logoutTime +
                ", notes='" + notes + '\'' +
                '}';
    }
}
