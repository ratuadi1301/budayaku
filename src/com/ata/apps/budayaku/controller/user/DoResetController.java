package com.ata.apps.budayaku.controller.user;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.ata.apps.budayaku.model.User;
import com.ata.apps.budayaku.service.UserService;
import com.ata.apps.budayaku.util.BCrypt;

public class DoResetController extends Controller {

	@Override
	protected Navigation run() throws Exception {
		System.out.println("Do Reset Password");
		User currentUser = (User) request.getAttribute("currentUser");
		if (currentUser.getGroup().getId() == 1) {
			Long userId = Long.valueOf(request.getParameter("idUser"));
			User user = UserService.get().getById(userId);
			String password = request.getParameter("newPassword");
			String pwdDigest = BCrypt.hashpw(password, BCrypt.gensalt());
			user.setPwdDigest(pwdDigest);
			UserService.get().store(user);
			return redirect("detail?userId=" + userId);
		} else {
			return redirect("/");
		}
	}
}
