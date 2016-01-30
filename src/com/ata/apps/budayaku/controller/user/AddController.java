package com.ata.apps.budayaku.controller.user;

import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.ata.apps.budayaku.model.Group;
import com.ata.apps.budayaku.model.User;
import com.ata.apps.budayaku.service.GroupService;

public class AddController extends Controller {

	@Override
	protected Navigation run() throws Exception {
		System.out.println("admin panel");
		User currentUser = (User) request.getAttribute("currentUser");
		if (currentUser.getGroup().getId() == 1) {
			List<Group> groups = GroupService.get().list();
			requestScope("groups", groups);
			System.out.println("add user");
			return forward("add.jsp");
		} else {
			return redirect("/");
		}
	}
}
