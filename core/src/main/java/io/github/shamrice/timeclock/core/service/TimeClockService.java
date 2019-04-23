package io.github.shamrice.timeclock.core.service;

import io.github.shamrice.timeclock.core.manager.DataManager;
import io.github.shamrice.timeclock.core.model.models.UserModel;
import io.github.shamrice.timeclock.core.model.models.UserTimeModel;
import org.apache.log4j.Logger;

import java.util.Date;

public class TimeClockService {

    private static Logger logger = Logger.getLogger(TimeClockService.class);

    private DataManager<UserModel> userModelDataManager;
    private DataManager<UserTimeModel> userTimeModelDataManager;

    public TimeClockService() {
        this.userModelDataManager = new DataManager<>();
        this.userTimeModelDataManager = new DataManager<>();
    }

    public void updateUser(UserModel userModel) {
        if (userModel != null) {
            logger.info("Updating existing user: " + userModel.toString());
            userModelDataManager.update(userModel);
        }
    }

    public UserModel getUser(int userId) {
        return userModelDataManager.get(UserModel.class, userId);
    }

    public void clockIn(int userId) {

        logger.debug("Clocking in user_id: " + userId);

        UserModel userModel = userModelDataManager.get(UserModel.class, userId);

        if (userModel != null && userModel.isEnabled()) {

            UserTimeModel userTimeModel = userTimeModelDataManager.getByColumn(
                    UserTimeModel.class,
                    "userId",
                    userModel.getUserId());

            if (userTimeModel == null) {
                logger.info("Unable to find existing user time record for user_id: " + userId + " username: " + userModel.getUsername());

                UserTimeModel newUserTimeModel = new UserTimeModel();
                newUserTimeModel.setUserId(userModel.getUserId());
                newUserTimeModel.setUserDate(new Date());
                newUserTimeModel.setTimeIn(new Date());
                userTimeModelDataManager.create(newUserTimeModel);

                logger.info("Created new user_time record for user_id: " + userId + " clock in time: " + newUserTimeModel.getTimeIn());
            } else {
                userTimeModel.setTimeIn(new Date());
                userTimeModelDataManager.update(userTimeModel);
                logger.info("Updated user_time record for user_id: " + userId + " clock in time: " + userTimeModel.getTimeIn());
            }
        } else {
            logger.error("Unable to clock in user_id: " + userId + " : User does not exist or is not enabled.");
        }
    }

    public void clockOut(int userId) {
        logger.debug("Clocking out user_id: " + userId);

        UserModel userModel = userModelDataManager.get(UserModel.class, userId);

        if (userModel != null && userModel.isEnabled()) {

            UserTimeModel userTimeModel = userTimeModelDataManager.getByColumn(
                    UserTimeModel.class,
                    "userId",
                    userModel.getUserId());

            if (userTimeModel == null) {
                logger.info("Unable to find existing user time record for user_id: " + userId + " username: " + userModel.getUsername());

                UserTimeModel newUserTimeModel = new UserTimeModel();
                newUserTimeModel.setUserId(userModel.getUserId());
                newUserTimeModel.setUserDate(new Date());
                newUserTimeModel.setTimeIn(new Date());
                newUserTimeModel.setTimeOut(new Date());
                userTimeModelDataManager.create(newUserTimeModel);

                logger.info("Created new user_time record for user_id: " + userId + " clock in time: "
                        + newUserTimeModel.getTimeIn() + " : clock out time: " + newUserTimeModel.getTimeOut());
            } else {
                userTimeModel.setTimeOut(new Date());
                userTimeModelDataManager.update(userTimeModel);
                logger.info("Updated user_time record for user_id: " + userId + " clock in time: "
                        + userTimeModel.getTimeIn() + " : clock out time: " + userTimeModel.getTimeOut());
            }
        } else {
            logger.error("Unable to clock out user_id: " + userId + " : User does not exist or is not enabled.");
        }
    }
}
