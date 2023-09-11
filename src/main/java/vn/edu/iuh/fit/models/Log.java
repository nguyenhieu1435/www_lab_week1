package vn.edu.iuh.fit.models;

import jakarta.persistence.*;

import java.time.LocalDate;

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
    private LocalDate loginTime;
    @Column(name = "logout_time", columnDefinition = "DATETIME DEFAULT current_timestamp()", nullable = false)
    private LocalDate logoutTime;
    @Column(columnDefinition = "VARCHAR(250) default ''", nullable = false)
    private String notes;

    public Log() {
    }

    public Log(String accountId, String notes) {
        this.accountId = accountId;
        this.notes = notes;
    }

    private long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public LocalDate getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDate loginTime) {
        this.loginTime = loginTime;
    }

    public LocalDate getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(LocalDate logoutTime) {
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
