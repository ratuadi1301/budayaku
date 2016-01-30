package com.ata.apps.budayaku.controller;

import java.util.UUID;

import javax.servlet.http.Cookie;

import org.hibernate.criterion.Restrictions;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.ata.apps.budayaku.model.User;
import com.ata.apps.budayaku.server.LoginRequiredFilter;
import com.ata.apps.budayaku.service.UserService;
import com.ata.apps.budayaku.util.BCrypt;

public class DoLoginController extends Controller {
	public static final int COOKIE_MAX_AGE = 30 * 24 * 60 * 60;
//	public static String HOTEL_USER_ID = "hotelUserId";
//	public static String HOTEL_SESSION = "hotelSession";

	@Override
	public Navigation run() throws Exception {
		if (isPost()) {
			try {
				String username = asString("username");
				String password = asString("password");
				User user = UserService.get().getByCriterions(
						Restrictions.eq("username", username));
				if (user == null) {
					throw new IllegalArgumentException(
							"Invalid username or password");
				}
				return doLogin(user, password);

			} catch (IllegalArgumentException e) {
				requestScope("error", e.getMessage());
				e.printStackTrace();
				StringBuilder sb = new StringBuilder();
				sb.append("Tidak dapat melanjutkan proses, error:").append(
						e.getMessage());
				return forward("/error.jsp");
			}
		}
		return null;
	}

	private Navigation doLogin(User user, String password) {
		boolean valid = BCrypt.checkpw(password, user.getPwdDigest());
		if (!valid) {
			String message = "Invalid username or password";
			throw new IllegalArgumentException(message);
		}

		UUID uuid = UUID.randomUUID();
		String userSession = uuid.toString();
		user.setUserSession(userSession);
		if (UserService.get().store(user) == null) {
			throw new RuntimeException("Failed to update user data");
		}

		String userId = String.valueOf(user.getId());
		Cookie userIdCookie = new Cookie(LoginRequiredFilter.APP_USER_ID, userId);
		userIdCookie.setMaxAge(COOKIE_MAX_AGE);
		response.addCookie(userIdCookie);

		Cookie sessionCookie = new Cookie(LoginRequiredFilter.APP_SESSION, userSession);
		sessionCookie.setMaxAge(COOKIE_MAX_AGE);
		response.addCookie(sessionCookie);
		
		if (user.getGroup().getId() == 1) {
			requestScope("currentUser", user);
			return redirect("/sessionToken");
		} else {
			requestScope("currentUser", user);
			return redirect(basePath);
		}
	}

}
