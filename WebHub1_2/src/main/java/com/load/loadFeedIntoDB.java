package com.load;
import com.load.GtfsDatabaseLoaderMain;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;



/**
 * Servlet implementation class loadFeedIntoDB
 */
public class loadFeedIntoDB extends HttpServlet {
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
		String [] args = new String[5];
		String realPath = getServletContext().getRealPath("/");
		args[0] = "--driverClass=\"org.postgresql.Driver\"";
		args[1] = "--url=\"jdbc:postgresql://localhost:5432/testDB\"";
		args[2] = "--username=\"postgres\"";
		args[3] = "--password=\"ben\"";
		args[4] = realPath+"testFiles/good_feed/";
		
		boolean b = true;
		try{
			GtfsDatabaseLoaderMain.main(args);
			b = false;
		}catch(HibernateException exception){
		     System.out.println("Problem creating session factory");
		     exception.printStackTrace();
		}
		if(b){
			System.out.println("Upload unsuccessful");
		}
		
		response.setContentType("test/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("Feed Loaded Into Database");
		
	}

}
