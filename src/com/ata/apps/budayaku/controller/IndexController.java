package com.ata.apps.budayaku.controller;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.ata.apps.budayaku.dao.GroupDAO;
import com.ata.apps.budayaku.model.Group;
import com.ata.apps.budayaku.model.User;

public class IndexController extends Controller {

    @Override
    public Navigation run() throws Exception {
    	System.out.println("index...");
        return forward("index.jsp");
    }

}
