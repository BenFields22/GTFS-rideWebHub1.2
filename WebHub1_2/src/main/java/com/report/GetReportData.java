package com.report;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetReportData
 */
public class GetReportData extends HttpServlet {
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
		
		String aggreg = request.getParameter("aggreg");
		String agency = request.getParameter("agcy");
		String startDate = request.getParameter("StartDate");
		String endDate = request.getParameter("EndDate");
		String startTime = request.getParameter("StartTime");
		String endTime = request.getParameter("EndTime");
		
		System.out.println("aggregation: "+aggreg);
		System.out.println("agency: "+agency);
		
		if(aggreg.equals("system"))
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
					String query = "select agency,sum(boardings::integer) from board_alight where service_arrival_time::time <= time '"+endTime+"' and service_arrival_time::time >= time '"+startTime+"' and service_date::date >= '"+startDate+"' and service_date::date <= date '"+endDate+"' and boardings != '0' and boardings != 'NULL' and agency = '"+agency+"' group by agency";
					String query2 = "select agency,sum(alightings::integer) from board_alight where service_arrival_time::time <= time '"+endTime+"' and service_arrival_time::time >= time '"+startTime+"' and service_date::date >= '"+startDate+"' and service_date::date <= date '"+endDate+"' and alightings != '0' and alightings != 'NULL' and agency = '"+agency+"' group by agency";
					String query3 = "select count(*) from board_alight where service_arrival_time::time <= time '"+endTime+"' and service_arrival_time::time >= time '"+startTime+"' and service_date::date >= '"+startDate+"' and service_date::date <= date '"+endDate+"' and agency = '"+agency+"'";

					//String query = "SELECT trip_id, sum(boardings::integer), sum(alightings::integer) FROM board_alight WHERE boardings ~ E'^\\d+$' and alightings ~ E'^\\d+$' group by trip_id;";
					PreparedStatement stmt = con.prepareStatement(query);
					ResultSet Rs = stmt.executeQuery();
					PreparedStatement stmt2 = con.prepareStatement(query2);
					ResultSet Rs2 = stmt2.executeQuery();
					PreparedStatement stmt3 = con.prepareStatement(query3);
					ResultSet Rs3 = stmt3.executeQuery();
					
					
					System.out.println(query);
					int count = 0;
					Integer sum = 0;
					Integer alightings = 0;
					
					while (Rs.next()) {
				    	sum = Rs.getInt(2);
					    count++;
					    
					}
					while (Rs2.next()) {
						alightings = Rs2.getInt(2);
					    count++;
					}
					Integer records = 0;
					while (Rs3.next()) {
						//agency = Rs2.getString(1);
						records = Rs3.getInt(1);
					}
					
					System.out.println("sum is " +sum);
					String selector = "<tr><td align = 'center'>"+sum.toString()+ " </td><td align = 'center'> "+alightings.toString()+ " </td><td align = 'center'> "+records.toString()+"</td>";
					selector+="</tr>";
					System.out.println(selector);
					response.setContentType("test/plain");
					response.setCharacterEncoding("UTF-8");
					//response.getWriter().write("testing from servlet");
					response.getWriter().write(selector);
							
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	if(aggreg.equals("route"))
	{
		String selector = "<tr><td align = 'center'>NULL</td><td align = 'center'>NULL</td><td align = 'center'>NULL</td><td align = 'center'>NULL</td></tr>";
		response.setContentType("test/plain");
		response.setCharacterEncoding("UTF-8");
		//response.getWriter().write("testing from servlet");
		response.getWriter().write(selector);
	}
	
	if(aggreg.equals("trip"))
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
				String query = "select trip_id,sum(boardings::integer), sum(alightings::integer),count(*) from board_alight where service_arrival_time::time <= time '"+endTime+"' and service_arrival_time::time >= time '"+startTime+"' and service_date::date >= '"+startDate+"' and service_date::date <= date '"+endDate+"' and boardings != 'NULL' and alightings != 'NULL' and agency = 'RVTD' group by trip_id order by trip_id";

				//String query = "SELECT trip_id, sum(boardings::integer), sum(alightings::integer) FROM board_alight WHERE boardings ~ E'^\\d+$' and alightings ~ E'^\\d+$' group by trip_id;";
				PreparedStatement stmt = con.prepareStatement(query);
				ResultSet Rs = stmt.executeQuery();
				
				
				
				System.out.println(query);
				int count = 0;
				
				String content = "";
				int track = -1;
				int countV = 0;
				while (Rs.next()) {
					
					String id = Rs.getString(1);
					Integer boardings = Rs.getInt(2);
					Integer alightings = Rs.getInt(3);
					Integer records = Rs.getInt(4);
					//content = content + "[\""+id+"\",\""+boardings.toString()+"\",\""+alightings.toString()+"\",\""+records.toString()+"\"]";
					content = content +"<tr><td align = 'center'>" +id+"</td><td align = 'center'>"+boardings.toString()+"</td><td align = 'center'>"+alightings.toString()+"</td><td align = 'center'>"+records.toString()+"</td></tr>";
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
	
	if(aggreg.equals("stop"))
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
				String query = "select stop_id,sum(boardings::integer), sum(alightings::integer),count(*) from board_alight where service_arrival_time::time <= time '"+endTime+"' and service_arrival_time::time >= time '"+startTime+"' and service_date::date >= '"+startDate+"' and service_date::date <= date '"+endDate+"' and boardings != 'NULL' and alightings != 'NULL' and agency = 'RVTD' group by stop_id order by stop_id";

				//String query = "SELECT trip_id, sum(boardings::integer), sum(alightings::integer) FROM board_alight WHERE boardings ~ E'^\\d+$' and alightings ~ E'^\\d+$' group by trip_id;";
				PreparedStatement stmt = con.prepareStatement(query);
				ResultSet Rs = stmt.executeQuery();
				
				
				
				System.out.println(query);
				int count = 0;
				
				String content = "";
				int track = -1;
				int countV = 0;
				while (Rs.next()) {
					String id = Rs.getString(1);
					Integer boardings = Rs.getInt(2);
					Integer alightings = Rs.getInt(3);
					Integer records = Rs.getInt(4);
					//content = content + "[\""+id+"\",\""+boardings.toString()+"\",\""+alightings.toString()+"\",\""+records.toString()+"\"]";
					content = content + "<tr><td align = 'center'>"+id+"</td><td align = 'center'>"+boardings.toString()+"</td><td align = 'center'>"+alightings.toString()+"</td><td align = 'center'>"+records.toString() +"</td></tr>";
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
