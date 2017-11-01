package com.report;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class GenerateReport
 */
public class GenerateReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://ridedb.cr8hn6m3gchm.us-west-2.rds.amazonaws.com/ridedb","rideadmindb","***");
			if	(con.isValid(0))
			{
				System.out.println("Connection made");
			}
			else
			{
				System.out.println("Connection failed");
			}
				Class.forName("org.postgresql.Driver");
				String query = "select \"name\" from \"gtfs_agencies\"";
				PreparedStatement stmt = con.prepareStatement(query);
				ResultSet Rs = stmt.executeQuery();
				String selector = "<table id=\"cat\"><tr>"+
						"<th>Number</th>"+
						"<th>Agency</th>"+
						"</tr>";
				int count = 0;
				while(Rs.next())
				{
					count++;
					selector=selector+"<tr><td>"+ count+ "</td>"+ "<td>"+Rs.getString(1)+"</td></tr>";
					System.out.println(Rs.getString(1));
				}
				selector=selector+"</table>";
				response.setContentType("test/plain");
				response.setCharacterEncoding("UTF-8");
				//response.getWriter().write("testing from servlet");
				response.getWriter().write(selector);
						
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}


