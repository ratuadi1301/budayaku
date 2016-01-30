package com.ata.apps.budayaku.controller.user;

import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.ata.apps.budayaku.model.Group;
import com.ata.apps.budayaku.model.User;
import com.ata.apps.budayaku.service.GroupService;
import com.ata.apps.budayaku.service.UserService;

public class DetailController extends Controller {

	@Override
	protected Navigation run() throws Exception {
		System.out.println("admin panel");
		User currentUser = (User) request.getAttribute("currentUser");
		if (currentUser.getGroup().getId() == 1) {
			Long userId = Long.valueOf(request.getParameter("userId"));
			User user = UserService.get().getById(userId);
			List<Group> groups = GroupService.get().list();
			requestScope("user", user);
			requestScope("groups", groups);
			return forward("detail.jsp");
		} else {
			return redirect("/");
		}
	}
}
