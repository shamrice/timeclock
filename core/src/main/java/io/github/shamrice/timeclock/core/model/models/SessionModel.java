package io.github.shamrice.timeclock.core.model.models;

import io.github.shamrice.timeclock.core.model.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "user_session")
public class SessionModel extends Model {

    @Id
    @Column(name = "user_session_id")
    private int userSessionId;

    @Column(name = "user_id")
    private int userId;

    @Column (name = "session_key")
    private String sessionKey;

    @Column (name = "expire_dt")
    private Date expireDt;

    @Column (name = "create_dt")
    private Date createDt;

    @Column (name = "mod_dt")
    private Date modDt;

    @Override
    public String toString() {
        return "user_session_id: " + userSessionId + " : user_id:" + userId + " : session_key: " + sessionKey
                + " : expire_dt: " + expireDt + " : create_dt: " + createDt + " : mod_dt: " + modDt;
    }

    public void setUserSessionId(int userSessionId) {
        this.userSessionId = userSessionId;
    }

    public int getUserSessionId() {
        return userSessionId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setExpireDt(Date expireDt) {
        this.expireDt = expireDt;
    }

    public Date getExpireDt() {
        return expireDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setModDt(Date modDt) {
        this.modDt = modDt;
    }

    public Date getModDt() {
        return modDt;
    }
}
