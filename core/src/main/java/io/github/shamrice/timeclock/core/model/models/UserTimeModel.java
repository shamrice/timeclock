package io.github.shamrice.timeclock.core.model.models;

import io.github.shamrice.timeclock.core.model.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_time")
public class UserTimeModel extends Model {

    @Id
    @Column(name = "user_time_id")
    private int userTimeId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "user_date")
    private Date userDate;

    @Column(name = "time_in")
    private Date timeIn;

    @Column(name = "time_out")
    private Date timeOut;

    public int getUserTimeId() {
        return userTimeId;
    }

    public void setUserTimeId(int userTimeId) {
        this.userTimeId = userTimeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getUserDate() {
        return userDate;
    }

    public void setUserDate(Date userDate) {
        this.userDate = userDate;
    }

    public Date getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(Date timeIn) {
        this.timeIn = timeIn;
    }

    public Date getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Date timeOut) {
        this.timeOut = timeOut;
    }

    //todo: finish this
    @Override
    public String toString() {
        return String.valueOf(userTimeId);
    }
}
