package com.ata.apps.budayaku.controller.user;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.ata.apps.budayaku.model.User;
import com.ata.apps.budayaku.service.UserService;

public class ResetController extends Controller {

	@Override
	protected Navigation run() throws Exception {
		System.out.println("admin panel");
		User currentUser = (User) request.getAttribute("currentUser");
		if (currentUser.getGroup().getId() == 1) {
			Long userId = Long.valueOf(request.getParameter("id"));
			User user = UserService.get().getById(userId);
			requestScope("user", user);
			return forward("resetPassword.jsp");
		} else {
			return redirect("/");
		}
	}
}
