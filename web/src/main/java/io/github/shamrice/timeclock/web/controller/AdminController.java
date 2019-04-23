package io.github.shamrice.timeclock.web.controller;

import io.github.shamrice.timeclock.core.request.CreateUserRequest;
import io.github.shamrice.timeclock.core.service.AuthenticationService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class AdminController {

    private static Logger logger = Logger.getLogger(AdminController.class);

    @GetMapping("/admin")
    public String getAdmin(@CookieValue(value = "authCookie", required = false) String authCookie)  {

        logger.info("Getting create user page... checking for cookies.");

        if (authCookie == null || authCookie.isEmpty()) {
            logger.info("No cookies found for request. Returning.");
            return "greeting";
        } else {
            logger.info("Cookie value=" + authCookie);

            AuthenticationService authenticationService = new AuthenticationService();
            if (authenticationService.isSessionValid(authCookie)) {
                logger.info("Found auth cookie. Showing admin page.");
                return "admin/admin";
            }
        }

        return "greeting";
    }

    @PostMapping("/admin")
    public String postAAdmin(
            @RequestParam Map<String, String> body,
            Model model
    ) {

        if (body != null && body.get("username") != null && body.get("name") != null) {

            CreateUserRequest createUserRequest = new CreateUserRequest(
                    body.get("username"),
                    body.get("password"),
                    body.get("name"),
                    Boolean.valueOf(body.get("isEnabled"))
            );

            AuthenticationService authenticationService = new AuthenticationService();
            authenticationService.createUser(createUserRequest);

            model.addAttribute("isCreated", "User Created!");
        } else {
            model.addAttribute("isCreated", "User creation failed.");
        }

        return "admin/createuser";
    }


}
