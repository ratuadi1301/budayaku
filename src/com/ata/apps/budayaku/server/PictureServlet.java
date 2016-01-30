package com.ata.apps.budayaku.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

public class PictureServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private String workFolder;

	@Override
	public void init(ServletConfig config) throws ServletException {
		workFolder = config.getServletContext().getInitParameter(
				"imagesjourney.location");
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String requestUrl = request.getRequestURL().toString();
		String pic = "/picture/";
		String filename = requestUrl.substring(requestUrl.indexOf(pic)
				+ pic.length());
		
		String root = request.getRealPath("/");
		System.out.println(root);
		File path = new File(root + workFolder);
		if (!path.exists() && !path.isDirectory()) {
			path.mkdir();
		}

		File file = new File(path, filename);
		if (!file.exists()) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		String contentType = file.toURL().openConnection().getContentType();
		response.setContentType(contentType);

		FileInputStream fis = new FileInputStream(file);
		OutputStream out = response.getOutputStream();
		IOUtils.copy(fis, out);
		out.flush();
		fis.close();
	}

}
