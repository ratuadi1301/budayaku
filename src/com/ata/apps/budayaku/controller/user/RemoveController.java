package com.ata.apps.budayaku.controller.user;

import javax.servlet.http.HttpServletResponse;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.repackaged.org.json.JSONObject;

import com.ata.apps.budayaku.model.User;
import com.ata.apps.budayaku.service.UserService;

public class RemoveController extends Controller {

	@Override
	protected Navigation run() throws Exception {
		JSONObject jo = new JSONObject();
		String cmn = "result";
		String msgRst = "fail";

		if (request.getParameter("id") != null) {
			Long id = Long.valueOf(request.getParameter("id"));
			try {
				User u = UserService.get().getById(id);
				UserService.get().delete(u);
				msgRst = "success";
				jo.put(cmn, msgRst);
			} catch (Exception e) {
				msgRst = "fail : " + e.getMessage();
				jo.put(cmn, msgRst);
			}

		}

		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().print(jo);
		return null;
	}
}
