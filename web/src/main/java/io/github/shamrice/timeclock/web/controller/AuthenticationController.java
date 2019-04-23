package io.github.shamrice.timeclock.web.controller;

import io.github.shamrice.timeclock.core.service.AuthenticationService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class AuthenticationController {

    private static Logger logger = Logger.getLogger(AuthenticationController.class);

    @PostMapping("/auth/login")
    public ModelAndView postLogin(
            @RequestParam Map<String, String> body,
            HttpServletResponse response
    ) {

        boolean authResult = false;
        ModelAndView model = new ModelAndView();
        AuthenticationService authenticationService = new AuthenticationService();

        if (body != null && body.get("username") != null && body.get("password") != null) {
            authResult = authenticationService.authenticate(body.get("username"), body.get("password"));
        } else {
            logger.debug("Failed authentication validation for user authentication ");

        }

        model.addObject("result", authResult);

        if (authResult) {
            logger.debug("Successfully authenticated user : " + body.get("username"));

            model.setViewName("admin/admin");

            String sessionKey = authenticationService.createSession(body.get("username"));

            Cookie cookie = new Cookie("authCookie", sessionKey);
            cookie.setPath("/admin");
            cookie.setMaxAge(10000);
            response.addCookie(cookie);



        } else {
            logger.debug("failure, returning greetings view");
            model.setViewName("greeting");
        }

        return model;
    }


}
