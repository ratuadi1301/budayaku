package com.ata.apps.budayaku.controller.user;

import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.ata.apps.budayaku.model.User;
import com.ata.apps.budayaku.service.UserService;

public class IndexController extends Controller {

	@Override
	protected Navigation run() throws Exception {
		System.out.println("admin panel");
		User user = (User) request.getAttribute("currentUser");
		if (user.getGroup().getId() == 1) {
			List<User> users = UserService.get().list();
			requestScope("users", users);
			return forward("index.jsp");
		} else {
			return redirect("/");
		}
	}
}
