package com.ata.apps.budayaku.server;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.ata.apps.budayaku.controller.DoLoginController;
import com.ata.apps.budayaku.model.User;
import com.ata.apps.budayaku.service.UserService;

public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = -3208409086358916855L;
	private ProgressListener progressListener;

	public void init(ServletConfig config) throws ServletException {
		super.init(config); // Create a progress listener
		progressListener = new ProgressListener() {
			private long megaBytes = -1;

			public void update(long pBytesRead, long pContentLength, int pItems) {
				long mBytes = pBytesRead / 1000000;
				if (megaBytes == mBytes) {
					return;
				}
				megaBytes = mBytes;
				System.out.println("We are currently reading item " + pItems);
				if (pContentLength == -1) {
					System.out.println("So far, " + pBytesRead + " bytes have been read.");
				} else {
					System.out.println("So far, " + pBytesRead + " of " + pContentLength + " bytes have been read.");
				}
			}
		};
	}

	@SuppressWarnings("rawtypes")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		// String userIdStr = request.getParameter("userId");
		// long userId = Long.parseLong(userIdStr);

		if (isMultipart) {
			String avatarUrl = "user.png";

			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setProgressListener(progressListener);

			try {
				System.out.println("fileuploads");
				List items = upload.parseRequest(request);
				Iterator iterator = items.iterator();
				while (iterator.hasNext()) {
					FileItem item = (FileItem) iterator.next();

					if (!item.isFormField()) {
						String fileName = item.getName();
						System.out.println("filename:" + fileName);
						System.out.println("getContentType:" + item.getContentType());
						String root = getServletContext().getRealPath("/");
						File path = new File(root + "/uploads");
						if (!path.exists() && !path.isDirectory()) {
							path.mkdir();
						}

						if (fileName.indexOf("/") > 0) {
							fileName = fileName.substring(fileName.indexOf("/"));
						}
						avatarUrl = "uploads/" + fileName;

						File uploadedFile = new File(path + "/" + fileName);
						item.write(uploadedFile);

						resize(path + "/" + fileName, path + "/" + fileName, 100, 0);
					} else {
						System.out.println("-fieldName:" + item.getFieldName());
						System.out.println("-getContentType:" + item.getContentType());
						System.out.println("-getName:" + item.getName());
						System.out.println("-getString:" + item.getString());
					}
				}

			} catch (FileUploadException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("---avatarUrl:" + avatarUrl);
			PrintWriter out = response.getWriter();
			out.println("{\"url\":\"" + avatarUrl + "\",\"status\":\"OK\",\"message\":\"msg\"}");

		}
	}

	private void resize(String inputImagePath, String outputImagePath, int scaledWidth, int scaledHeight)
			throws IOException {

		File inputFile = new File(inputImagePath);

		if (inputFile.length() > 100000) {
			BufferedImage inputImage = ImageIO.read(inputFile);

			int width = inputImage.getWidth();
			double koef = (double) width / (double) scaledWidth;

			BufferedImage outputImage = new BufferedImage(scaledWidth, (int) (inputImage.getHeight() / koef),
					inputImage.getType());

			Graphics2D g2d = outputImage.createGraphics();
			g2d.drawImage(inputImage, 0, 0, scaledWidth, (int) (inputImage.getHeight() / koef), null);
			g2d.dispose();

			String formatName = outputImagePath.substring(outputImagePath.lastIndexOf(".") + 1);

			ImageIO.write(outputImage, formatName, new File(outputImagePath));
		}

		File outputFile = new File(outputImagePath);
		if (outputFile.length() > 100000) {
			resize(inputImagePath, outputImagePath, 0.90);
		}

	}

	private void resize(String inputImagePath, String outputImagePath, double percent) throws IOException {
		File inputFile = new File(inputImagePath);
		BufferedImage inputImage = ImageIO.read(inputFile);
		int scaledWidth = (int) (inputImage.getWidth() * percent);
		int scaledHeight = (int) (inputImage.getHeight() * percent);
		resize(inputImagePath, outputImagePath, scaledWidth, scaledHeight);
	}
}