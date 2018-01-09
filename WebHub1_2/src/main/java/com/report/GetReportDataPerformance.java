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
 * Servlet implementation class GetReportDataPerformance
 */

public class GetReportDataPerformance extends HttpServlet {
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
		String select = request.getParameter("select");
		String aggreg = request.getParameter("aggreg");
		String agency = request.getParameter("agcy");
		String startDate = request.getParameter("StartDate");
		String endDate = request.getParameter("EndDate");
		String startTime = request.getParameter("StartTime");
		String endTime = request.getParameter("EndTime");
		
		System.out.println("aggregation: "+aggreg);
		System.out.println("agency: "+agency);
		System.out.println("report selection: "+select);
		
		if(aggreg.equals("System Level"))
		{
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
					String query = "select max(boardings::integer),min(boardings::integer),max(alightings::integer),min(alightings::integer),count(*) from board_alight where service_arrival_time::time <= time '"+endTime+"' and service_arrival_time::time >= time '"+startTime+"' and service_date::date >= '"+startDate+"' and service_date::date <= date '"+endDate+"' and boardings != 'NULL' and alightings != 'NULL' and agency = '"+agency+"' group by agency";

					//String query = "SELECT trip_id, sum(boardings::integer), sum(alightings::integer) FROM board_alight WHERE boardings ~ E'^\\d+$' and alightings ~ E'^\\d+$' group by trip_id;";
					PreparedStatement stmt = con.prepareStatement(query);
					ResultSet Rs = stmt.executeQuery();
					
					
					
					System.out.println(query);
					
					int count = 0;
					
					String content = "";
					int track = -1;
					int countV = 0;
					while (Rs.next()) {
						
						
						Integer MaxBoardings = Rs.getInt(1);
						Integer MinBoardings = Rs.getInt(2);
						Integer MaxAlightings = Rs.getInt(3);
						Integer MinAlightings = Rs.getInt(4);
						Integer records = Rs.getInt(5);
						//content = content + "[\""+id+"\",\""+boardings.toString()+"\",\""+alightings.toString()+"\",\""+records.toString()+"\"]";
						content = content +"<tr><td align = 'center'>" +MaxBoardings.toString()+"</td><td align = 'center'>"+MinBoardings.toString()+"</td><td align = 'center'>"+MaxAlightings.toString()+"</td><td align = 'center'>"+MinAlightings.toString()+"</td><td align = 'center'>"+records.toString()+"</td></tr>";
						track++;
						countV++;
					    
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
		
	if(aggreg.equals("Route Level"))
	{
		String selector = "<tr><td align = 'center'>NULL</td><td align = 'center'>NULL</td><td align = 'center'>NULL</td><td align = 'center'>NULL</td><td align = 'center'>NULL</td><td align = 'center'>NULL</td></tr>";
		response.setContentType("test/plain");
		response.setCharacterEncoding("UTF-8");
		//response.getWriter().write("testing from servlet");
		response.getWriter().write(selector);
	}
	
	if(aggreg.equals("Trip Level"))
	{
		
		
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
				String query = "select trip_id,max(boardings::integer),min(boardings::integer),max(alightings::integer),min(alightings::integer),count(*) from board_alight where service_arrival_time::time <= time '"+endTime+"' and service_arrival_time::time >= time '"+startTime+"' and service_date::date >= '"+startDate+"' and service_date::date <= date '"+endDate+"' and boardings != 'NULL' and alightings != 'NULL' and agency = 'RVTD' group by trip_id order by trip_id";

				PreparedStatement stmt = con.prepareStatement(query);
				ResultSet Rs = stmt.executeQuery();
				
				
				
				System.out.println(query);
				int count = 0;
				
				String content = "";
				int track = -1;
				int countV = 0;
				while (Rs.next()) {
					
					String id = Rs.getString(1);
					Integer MaxBoardings = Rs.getInt(2);
					Integer MinBoardings = Rs.getInt(3);
					Integer MaxAlightings = Rs.getInt(4);
					Integer MinAlightings = Rs.getInt(5);
					Integer records = Rs.getInt(6);
					//content = content + "[\""+id+"\",\""+boardings.toString()+"\",\""+alightings.toString()+"\",\""+records.toString()+"\"]";
					content = content +"<tr><td align = 'center'>" +id+"</td><td align = 'center'>" +MaxBoardings.toString()+"</td><td align = 'center'>"+MinBoardings.toString()+"</td><td align = 'center'>"+MaxAlightings.toString()+"</td><td align = 'center'>"+MinAlightings.toString()+"</td><td align = 'center'>"+records.toString()+"</td></tr>";
					track++;
					countV++;
				    
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
	
	if(aggreg.equals("Stop Level"))
	{
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
				String query = "select stop_id,max(boardings::integer),min(boardings::integer),max(alightings::integer),min(alightings::integer),count(*) from board_alight where service_arrival_time::time <= time '"+endTime+"' and service_arrival_time::time >= time '"+startTime+"' and service_date::date >= '"+startDate+"' and service_date::date <= date '"+endDate+"' and boardings != 'NULL' and alightings != 'NULL' and agency = 'RVTD' group by stop_id order by stop_id";

				PreparedStatement stmt = con.prepareStatement(query);
				ResultSet Rs = stmt.executeQuery();
				
				
				
				System.out.println(query);
				int count = 0;
				
				String content = "";
				int track = -1;
				int countV = 0;
				while (Rs.next()) {
					
					String id = Rs.getString(1);
					Integer MaxBoardings = Rs.getInt(2);
					Integer MinBoardings = Rs.getInt(3);
					Integer MaxAlightings = Rs.getInt(4);
					Integer MinAlightings = Rs.getInt(5);
					Integer records = Rs.getInt(6);
					//content = content + "[\""+id+"\",\""+boardings.toString()+"\",\""+alightings.toString()+"\",\""+records.toString()+"\"]";
					content = content +"<tr><td align = 'center'>" +id+"</td><td align = 'center'>" +MaxBoardings.toString()+"</td><td align = 'center'>"+MinBoardings.toString()+"</td><td align = 'center'>"+MaxAlightings.toString()+"</td><td align = 'center'>"+MinAlightings.toString()+"</td><td align = 'center'>"+records.toString()+"</td></tr>";
					track++;
					countV++;
				    
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

}
