package io.github.shamrice.timeclock.core.service;

import io.github.shamrice.timeclock.core.manager.DataManager;
import io.github.shamrice.timeclock.core.model.models.SessionModel;
import io.github.shamrice.timeclock.core.model.models.UserModel;
import io.github.shamrice.timeclock.core.request.CreateUserRequest;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class AuthenticationService {

    private static Logger logger = Logger.getLogger(AuthenticationService.class);

    private DataManager<UserModel> userModelDataManager;
    private DataManager<SessionModel> sessionModelDataManager;

    public AuthenticationService() {
        this.userModelDataManager = new DataManager<>();
        this.sessionModelDataManager = new DataManager<>();
    }

    public boolean createUser(CreateUserRequest createUserRequest) {

        //todo: validate this further

        if (createUserRequest != null) {

            logger.info("Creating new user : " + createUserRequest.getUsername());

            try {
                SecureRandom random = new SecureRandom();
                byte[] salt = new byte[16];
                random.nextBytes(salt);
                //String saltString = new String(salt);
                String saltString = Hex.encodeHexString(salt);

                String passwordHash = getPasswordHash(createUserRequest.getPassword(), saltString);

                UserModel newUser = new UserModel();
                newUser.setUsername(createUserRequest.getUsername());
                newUser.setPasswordHash(passwordHash);
                newUser.setSalt(saltString);
                newUser.setEnabled(createUserRequest.isEnabled());
                newUser.setName(createUserRequest.getName());
                newUser.setCreateDt(createUserRequest.getCreateDt());

                logger.info("Creating new user :: " + newUser.toString());
                userModelDataManager.create(newUser);

                return true;

            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
                return false;
            }

        } else {
            logger.error("Attempted to call to create user with null CreateUserRequest.");
        }

        return false;
    }

    public boolean authenticate(String username, String password) {

        try {

            UserModel user = userModelDataManager.getByColumn(UserModel.class, "username", username);

            if (user == null) {
                logger.info("User not found: " + username);
                return false;
            }

            String attemptedPasswordHash = getPasswordHash(password, user.getSalt());

            logger.debug("Username: " + user.getUsername() + " : password: " + password + " : db salt: "
                    + user.getSalt() + " : db password_hash: " + user.getPasswordHash()
                    + " : attempted password_hash: " + attemptedPasswordHash);

            if (attemptedPasswordHash.equals(user.getPasswordHash())) {
                logger.info("Successfully authenticated user: " + username);
                return true;
            } else {
                logger.info("Authentication failed for user: " + username + " : invalid password.");
                return false;
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return false;
        }

    }

    public String createSession(String username) {
        if (username != null) {
            UserModel user = userModelDataManager.getByColumn(UserModel.class, "username", username);

            if (user != null) {


                UUID newSessionKey = UUID.randomUUID();

                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                cal.add(Calendar.HOUR_OF_DAY, 3);

                SessionModel sessionModel = new SessionModel();
                sessionModel.setUserId(user.getUserId());
                sessionModel.setCreateDt(new Date());
                sessionModel.setSessionKey(newSessionKey.toString());
                sessionModel.setExpireDt(cal.getTime());

                sessionModelDataManager.create(sessionModel);
                logger.info("Created session for user " + username + " session: " + sessionModel.toString());

                return newSessionKey.toString();

            } else {
                logger.info("Unable to find user : " + username + " to create session for.");
            }
        }

        return null;
    }

    public boolean isSessionValid(String sessionKey) {

        logger.info("Checking if session is valid for session key: " + sessionKey);

        try {
            SessionModel sessionModel = sessionModelDataManager.getByColumn(
                    SessionModel.class, "sessionKey", sessionKey);

            if (sessionModel != null) {
                Date currentDate = new Date();
                if (sessionModel.getExpireDt() != null && sessionModel.getExpireDt().after(currentDate)) {
                    logger.info("Session: " + sessionModel.toString() + " :: is Valid.");
                    return true;
                }
                logger.info("Session: " + sessionModel.toString() + " :: is not valid.");
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

        logger.info("Session: session is either invalid or does not exist for session key: " + sessionKey);
        return false;
    }

    private String getPasswordHash(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 128);

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();

        return Hex.encodeHexString(hash);
    }
}
