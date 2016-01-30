package com.ata.apps.budayaku.controller;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class LoginController extends Controller {

    @Override
    public Navigation run() throws Exception {
    	System.out.println("login controller");
        return forward("login.jsp");
    }


}
