package com.ata.apps.budayaku.controller;

import javax.servlet.http.Cookie;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.ata.apps.budayaku.server.LoginRequiredFilter;

public class DoLogoutController extends Controller {

    @Override
    public Navigation run() throws Exception {
        if (isGet()) {
            Cookie userIdCookie = null, sessionCookie = null;
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if (cookie.getName().equals(LoginRequiredFilter.APP_USER_ID)) {
                        userIdCookie = cookie;
                    } else if (cookie.getName().equals(LoginRequiredFilter.APP_SESSION)) {
                        sessionCookie = cookie;
                    }
                }
            }

            if (userIdCookie != null) {
                try {
//                    Key key = KeyFactory.stringToKey(userIdCookie.getValue());
//                    User user = UserService.get().getByKey(key);
//                    if (user != null) {
//                        user.setSession(null);
//                        UserService.get().store(user);
//                    }

                    userIdCookie.setValue("");
                    response.addCookie(userIdCookie);
                } catch (IllegalArgumentException e) {
                    servletContext.log("Invalid user ID sent", e);
                }
            }

            if (sessionCookie != null) {
                sessionCookie.setValue("");
                response.addCookie(sessionCookie);
            }

            return redirect("login");
        }
        return null;
    }


}
