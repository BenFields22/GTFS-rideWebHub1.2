package com.report;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetReportSelections
 */
public class GetReportSelections extends HttpServlet {
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
		String agency = request.getParameter("agcy");
		
		System.out.println("agency is: "+agency);
		try {
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://ridedb.cr8hn6m3gchm.us-west-2.rds.amazonaws.com/testDB","rideadmindb","alltheridestuff");
			if	(con.isValid(0))
			{
				System.out.println("Connection made successfully");
			}
			else
			{
				System.out.println("Connection failed");
			}
				Class.forName("org.postgresql.Driver");
				
				String query = "select count(trip_id) from board_alight where trip_id is not null and agency = '"+agency+"'";
				PreparedStatement stmt = con.prepareStatement(query);
				ResultSet Rs = stmt.executeQuery();
				System.out.println(query);
				
				
				String content = "<option value=\"1\">System</option>";
				
				while (Rs.next()) {
					Integer num = Rs.getInt(1);
					if(num >0)
					{
						content += "<option value=\"4\">Trip Level</option>";
					}
				    
				}
				query = "select count(stop_id) from board_alight where stop_id is not null and agency = '"+agency+"'";
				stmt = con.prepareStatement(query);
				Rs = stmt.executeQuery();
				System.out.println(query);

				while (Rs.next()) {
					Integer num = Rs.getInt(1);
					if(num >0)
					{
						content += "<option value=\"3\">Stop Level</option>";
					}
				    
				}
				query = "select count(route_id) from ridership where route_id is not null and agency_id = '"+agency+"'";
				stmt = con.prepareStatement(query);
				Rs = stmt.executeQuery();
				System.out.println(query);

				while (Rs.next()) {
					Integer num = Rs.getInt(1);
					if(num >0)
					{
						//content += "<option value=\"2\">Route Level</option>";
					}
				    
				}
				
				
				System.out.println(content);
				response.setContentType("test/plain");
				response.setCharacterEncoding("UTF-8");
				//response.getWriter().write("testing from servlet");
				response.getWriter().write(content);
						
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
