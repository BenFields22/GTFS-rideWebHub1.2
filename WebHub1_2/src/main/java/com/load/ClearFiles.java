package com.load;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ClearFiles
 */
public class ClearFiles extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String realPath = getServletContext().getRealPath("/");
		for (String s : UploadServlet.uploadedFiles) {
		    System.out.println("Deleting "+s);
		    File myFile = new File(realPath+"testFolder/"+s);
		    myFile.delete();
		}
		
		File myFileZ = new File(realPath+"testFolder/"+"ZippedFeed.zip");
		myFileZ.delete();
		UploadServlet.uploadedFiles.clear();
		response.setContentLength(0);
	}

}
