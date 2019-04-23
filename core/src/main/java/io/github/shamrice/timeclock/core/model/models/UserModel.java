package io.github.shamrice.timeclock.core.model.models;

import io.github.shamrice.timeclock.core.model.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "user")
public class UserModel extends Model {

    @Id
    @Column (name = "user_id")
    private int userId;

    @Column (name = "username")
    private String username;

    @Column (name = "password_hash")
    private String passwordHash;

    @Column (name = "salt")
    private String salt;

    @Column (name = "name")
    private String name;

    @Column (name = "is_enabled")
    private boolean isEnabled;

    @Column (name = "create_dt")
    private Date createDt;

    @Column (name = "mod_dt")
    private Date modDt;

    public Date getModDt() {
        return modDt;
    }

    public void setModDt(Date modDt) {
        this.modDt = modDt;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    //TODO : finish this
    @Override
    public String toString() {
        return " user_id: " + userId + " : username: " + username +" : name: " + name + " : is_enabled: " + isEnabled
                + " : create_dt: " + createDt
                + " : mod_dt: " + modDt;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
