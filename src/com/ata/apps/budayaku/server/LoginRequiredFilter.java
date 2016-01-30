package com.ata.apps.budayaku.server;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ata.apps.budayaku.model.User;
import com.ata.apps.budayaku.service.UserService;

public class LoginRequiredFilter implements Filter {

	private static Logger log = LoggerFactory
			.getLogger(LoginRequiredFilter.class);

	public static final int COOKIE_MAX_AGE = 30 * 24 * 60 * 60; // 30 days
	public static final String APP_USER_ID = "app_user_id";
	public static final String APP_SESSION = "app_session";

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		doFilter((HttpServletRequest) request, (HttpServletResponse) response,
				chain);
	}

	protected void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		boolean valid = isValidUser(request);
		if (valid) {
			chain.doFilter(request, response);
		} else {
			redirectTofrontPage(request, response);
		}
	}

	public static boolean isValidUser(HttpServletRequest request) {
		String userId = null, sessionId = null;
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				log.trace("Cookie name : " + cookie.getName());
				if (cookie.getName().equals(APP_USER_ID)) {
					userId = cookie.getValue();
					log.trace("\tuserId: " + userId);
				} else if (cookie.getName().equals(APP_SESSION)) {
					sessionId = cookie.getValue();
					log.trace("\tsessionId: " + sessionId);
				}
			}
		}

		System.out.println("userId:" + userId);
		
		boolean verified = false;

		if ((userId != null) && !userId.isEmpty() && (sessionId != null)
				&& !sessionId.isEmpty()) {
			try {
				verified = isValidUserId(Long.valueOf(userId), sessionId);
				
				if(verified){
					User user = UserService.get().getByCriterions(
							Restrictions.eq(User.ID, Long.valueOf(userId)));
					request.setAttribute("currentUser", user);
					
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("is user verified:" + verified);

		return verified;
	}

	private void redirectTofrontPage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/login");
	}

	public static boolean isValidUserId(Long Id, String sessionId) {
		User user = UserService.get().getByCriterions(
				Restrictions.eq(User.ID, Id));
		
		System.out.println("user:" + user.getUsername());
		
//		if(user != null && sessionId.equals(user.getUserSession())){
//			Set<Menu> menus = user.getGroup().getMenu();
//			List<Long> mId = new ArrayList<Long>();
//			for (Menu menu : menus) {
//				mId.add(menu.getId());
//			}
//			List<Menu> menuList = MenuService.get().listByCriterions(0, 0,
//					Order.asc(Menu.ID), Restrictions.in(Menu.ID, mId));
//
//			request.setAttribute("menuList", menuList);
//			
//		}
//
		return (user != null && sessionId.equals(user.getUserSession()));
	}

}
