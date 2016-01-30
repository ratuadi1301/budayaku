package com.ata.apps.budayaku.controller;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class RestController extends Controller {

	private Logger log = Logger.getLogger(RestController.class.getSimpleName());

	// 200
	public static final int SC_OK = HttpServletResponse.SC_OK;
	public static final int SC_CREATED = HttpServletResponse.SC_CREATED;
	public static final int SC_NO_CONTENT = HttpServletResponse.SC_NO_CONTENT;
	// 400
	public static final int SC_BAD_REQUEST = HttpServletResponse.SC_BAD_REQUEST;
	public static final int SC_NOT_FOUND = HttpServletResponse.SC_NOT_FOUND;
	public static final int SC_CONFLICT = HttpServletResponse.SC_CONFLICT;
	// 500
	public static final int SC_INTERNAL_SERVER_ERROR = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
	public static final int SC_NOT_IMPLEMENTED = HttpServletResponse.SC_NOT_IMPLEMENTED;

	protected static final String MIMETYPE_JSON = "application/json";
	protected static final String MIMETYPE_KML = "application/vnd.google-earth.kml+xml";
	protected static final String MIMETYPE_KMZ = "application/vnd.google-earth.kmz";
	protected static final String MIMETYPE_TEXT = "text/plain";

	protected boolean hasResponse = false;


	@Override
	protected Navigation run() throws Exception {

		if (validateSession()) {

		} else {
			return null;
		}

		try {
			if (isGet()) {
				doGet();
			} else if (isPost()) {
				doPost();
			} else if (isPut()) {
				doPut();
			} else if (isDelete()) {
				doDelete();
			} else {
				response.sendError(SC_NOT_IMPLEMENTED);
			}
		} catch (IOException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			responseWriter(SC_INTERNAL_SERVER_ERROR);
		}
		return null;
	}

	public void doGet() throws IOException {
		response.sendError(SC_NOT_IMPLEMENTED);
	}

	public void doPost() throws IOException {
		response.sendError(SC_NOT_IMPLEMENTED);
	}

	public void doPut() throws IOException {
		response.sendError(SC_NOT_IMPLEMENTED);
	}

	public void doDelete() throws IOException {
		response.sendError(SC_NOT_IMPLEMENTED);
	}

	public void responseWriter(String text) throws IOException {
		responseWriter(text, null, null);
	}

	public void responseWriter(String text, String contentType) throws IOException {
		responseWriter(text, contentType, null);
	}

	public void responseWriter(String text, String contentType, String encoding) throws IOException {
		responseWriter(SC_OK, text, contentType, encoding);
	}

	public void responseWriter(int status) throws IOException {
		responseWriter(status, null, null, null);
	}

	public void responseWriter(int status, String text) throws IOException {
		responseWriter(status, text, null, null);
	}

	public void responseWriter(int status, String text, String contentType) throws IOException {
		responseWriter(status, text, contentType, null);
	}

	public void responseWriter(int status, String text, String contentType, String encoding) throws IOException {
		if (status == 0) {
			status = SC_OK;
		}
		if (status != SC_OK && status != SC_CREATED && status != SC_NO_CONTENT) {
			response.sendError(status);
			return;
		}

		if (contentType == null) {
			contentType = "text/plain";
		}
		if (encoding == null) {
			encoding = request.getCharacterEncoding();
			if (encoding == null) {
				encoding = "UTF-8";
			}
		}

		response.setStatus(status);
		response.setContentType(contentType + "; charset=" + encoding);

		PrintWriter out = null;
		try {
			out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), encoding));
			out.print(text);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	protected boolean validateSession() throws Exception {
		boolean aReturn = true;
		String sessionId = request.getHeader("hotelmateSession");
		if (sessionId == null) {
			aReturn = false;
			responseWriter(SC_BAD_REQUEST);

		} else {
		}

		System.out.println("aReturn:" + aReturn);
		return aReturn;
	}

}
