package com.ata.apps.budayaku.controller.user;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.ata.apps.budayaku.model.Group;
import com.ata.apps.budayaku.model.User;
import com.ata.apps.budayaku.service.GroupService;
import com.ata.apps.budayaku.service.UserService;

public class DoEditController extends Controller {

	@Override
	protected Navigation run() throws Exception {
		System.out.println("admin panel");
		User currentUser = (User) request.getAttribute("currentUser");
		if (currentUser.getGroup().getId() == 1) {
			Long userId = Long.valueOf(request.getParameter("userId"));
			Long groupId = Long.valueOf(request.getParameter("groupId"));
			String fullName = asString("fullName");
			String username = asString("username");

			User user = UserService.get().getById(userId);
			Group group = GroupService.get().getById(groupId);
			user.setFullName(fullName);
			user.setUsername(username);
			user.setGroup(group);
			UserService.get().store(user);
			System.out.println("update user");
			return redirect("/user/");
		} else {
			return redirect("/");
		}
	}
}
