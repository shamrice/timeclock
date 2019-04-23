package io.github.shamrice.timeclock.core.request;

import java.util.Date;

public class CreateUserRequest {

    private String username;
    private String password;
    private String name;
    private boolean isEnabled;
    private Date createDt;

    public CreateUserRequest(String username, String password, String name, boolean isEnabled) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.isEnabled = isEnabled;
        this.createDt = new Date();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public Date getCreateDt() {
        return createDt;
    }
}
