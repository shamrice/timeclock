package io.github.shamrice.timeclock.core;

import io.github.shamrice.timeclock.core.model.models.UserModel;
import io.github.shamrice.timeclock.core.service.TimeClockService;

import java.util.Date;

public class Application {

    public static void main(String[] args) {
       //DataManager<UserModel> userManager = new DataManager<>();

        //UserModel user = userManager.get(1);

       // userManager.create(getTestUser());

        //DataManager<UserTimeModel> userTimeDataManager = new DataManager<>();
        /*
        UserTimeModel userTime = new UserTimeModel();
        userTime.setUserId(2);
        userTime.setUserDate(new Date());
        userTime.setTimeIn(new Date());

        userTimeDataManager.create(userTime);
        */
/*
        UserTimeModel userTime = userTimeDataManager.get(UserTimeModel.class, 2);
        userTime.setTimeOut(new Date());
        userTimeDataManager.update(userTime);
        */

        TimeClockService service = new TimeClockService();
        /*
        UserModel userModel = new UserModel();
        userModel.setUsername("UseName");
        userModel.setName("first name last name");
        userModel.setActive(true);
        service.createUser(userModel);

        service.clockIn(1);
        */

        service.clockOut(1);

    }

    private static UserModel getTestUser() {
        UserModel userModel = new UserModel();
        userModel.setUsername("Editor " + 500);
        userModel.setName("Name " + 500);
        userModel.setCreateDt(new Date());
        userModel.setModDt(new Date());

        return userModel;
    }
}
