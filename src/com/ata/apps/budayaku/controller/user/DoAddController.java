package com.ata.apps.budayaku.controller.user;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.ata.apps.budayaku.dao.GroupDAO;
import com.ata.apps.budayaku.model.Group;
import com.ata.apps.budayaku.model.User;
import com.ata.apps.budayaku.service.UserService;
import com.ata.apps.budayaku.util.BCrypt;

public class DoAddController extends Controller {

	@Override
	protected Navigation run() throws Exception {
		System.out.println("admin panel do Add");
		User currentUser = (User) request.getAttribute("currentUser");
		if (currentUser.getGroup().getId() == 1) {
 			Long groupId = Long.valueOf(request.getParameter("groupId"));
 			String fullName = asString("fullName"); 
 			String username = asString("username");
			String password = asString("password");
			String pwdDigest = BCrypt.hashpw(password, BCrypt.gensalt());

			User user = new User();
			Group group = GroupDAO.get().getByIdNew(groupId);
			user.setFullName(fullName);
			user.setUsername(username);
			user.setPwdDigest(pwdDigest);
			user.setWrongPwdCount(1);
			user.setPasswordExpired(false);
			user.setLocked(false);
			user.setDisabled(false);
			user.setGroup(group);
			UserService.get().store(user);
			System.out.println("add user");
			return redirect("/user/");
		} else {
			return redirect("/");
		}
	}
}
