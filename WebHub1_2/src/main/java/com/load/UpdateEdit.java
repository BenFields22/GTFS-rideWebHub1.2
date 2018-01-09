package com.load;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpdateEdit
 */
public class UpdateEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String html = "";
		Set myset = UploadServlet.uploadedFiles;
		Iterator<String> itr = myset.iterator();
		html += "<table id='editOptions' class ='table'><tr><th>FileName</th><th>Edit Button</th></tr>";
		while(itr.hasNext()){
				String curr = itr.next();
			  html = html+ "<tr><td>"+curr+"     </td><td> "+"<button id=\"editButton\" type=\"button\" onclick=\"editDocument('"+curr+"')\">Edit</button></td></tr>";
			}
		html += "</table>";
		//<style>#editOptions{color:black;}</style>
		response.setContentType("test/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(html);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
