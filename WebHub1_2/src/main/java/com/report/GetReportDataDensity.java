package com.report;

import java.io.File;
import java.io.FileWriter;
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

import com.load.UploadServlet;

/**
 * Servlet implementation class GetReportDataDensity
 */

public class GetReportDataDensity extends HttpServlet {
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
		
		String path;
		path = getServletContext().getRealPath("/")+"CSVFolder//";
		File file2 = new File(path+"reportData.csv");
		file2.createNewFile();
		if(file2.exists())
		{
			System.out.println("CSV File Created");
		}
		else
		{
			System.out.println("Failed to create CSV File");
		}
		FileWriter fileWriter = null;
		fileWriter = new FileWriter(file2);
		//fileWriter.append("Initial,file,test,object,3");
		
		
		
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
				String query = "select boardings::integer,alightings::integer,lat,lon from board_alight,gtfs_stops where code = stop_id and service_arrival_time::time <= time '"+endTime+"' and service_arrival_time::time >= time '"+startTime+"' and service_date::date >= '"+startDate+"' and service_date::date <= date '"+endDate+"' and boardings != 'NULL' and alightings != 'NULL' and agency = '"+agency+"'";
				String query2 = "select max(lon),min(lon), max(lat),min(lat) from board_alight,gtfs_stops where code = stop_id and service_arrival_time::time <= time '"+endTime+"' and service_arrival_time::time >= time '"+startTime+"' and service_date::date >= '"+startDate+"' and service_date::date <= date '"+endDate+"' and boardings != 'NULL' and alightings != 'NULL' and agency = '"+agency+"'";
				String query3 = "select count(*) from board_alight,gtfs_stops where code = stop_id and service_arrival_time::time <= time '"+endTime+"' and service_arrival_time::time >= time '"+startTime+"' and service_date::date >= '"+startDate+"' and service_date::date <= date '"+endDate+"' and boardings != 'NULL' and alightings != 'NULL' and agency = '"+agency+"'";
				//String query = "SELECT trip_id, sum(boardings::integer), sum(alightings::integer) FROM board_alight WHERE boardings ~ E'^\\d+$' and alightings ~ E'^\\d+$' group by trip_id;";
				PreparedStatement stmt = con.prepareStatement(query);
				ResultSet Rs = stmt.executeQuery();
				
				PreparedStatement stmt3 = con.prepareStatement(query3);
				ResultSet Rs3 = stmt3.executeQuery();
				
				PreparedStatement stmt2 = con.prepareStatement(query2);
				ResultSet Rs2 = stmt2.executeQuery();
				double dx;
				double dy;
				
				int boardingsTable[] = new int[101];
				int alightingsTable[] = new int[101];
				for(int i = 0;i<100;i++)
				{
					boardingsTable[i] = 0;
					alightingsTable[i] = 0;
				}
				double maxlon = 0.0; 
				double minlon = 0.0; 
				double maxlat = 0.0; 
				double minlat = 0.0; 
				
				while (Rs2.next()) {
					
					maxlon = Rs2.getDouble(1);
					minlon = Rs2.getDouble(2);
					maxlat = Rs2.getDouble(3);
					minlat = Rs2.getDouble(4); 
				}
				dx = (maxlon - minlon)/10.0;
				dy = (maxlat - minlat)/10.0;
				
				System.out.println("Maxlon is :"+maxlon+" Minlon is :"+minlon+" Maxlat is: "+maxlat+" Minlat is: "+minlat);
				Integer records = 0;
				while (Rs3.next()) {
					
					records = Rs3.getInt(1);
					
				}
				
				System.out.println(query);
				int count = 0;
				
				
				int counter = 0;
				while (Rs.next()) {
					Integer boardings = Rs.getInt(1);
					Integer alightings = Rs.getInt(2);
					double lat = Rs.getDouble(3);
					double lon = Rs.getDouble(4);
					
					double latpos = ((lat - minlat)/(maxlat-minlat))*10.0;
					double lonpos = ((lon - minlon)/(maxlon-minlon))*10.0;
					
					//System.out.println("latPos is: "+latpos);
					//System.out.println("lonPos is: "+lonpos);
					
					int latIndex = (int)latpos;
					int lonIndex = (int)lonpos;
					latIndex = 10-latIndex;
					if(latIndex >= 10)
					{
						latIndex = 9;
					}
					//System.out.println("Lat is:"+lat+" Lon is: "+lon);
					//System.out.println("Lat index:"+latIndex+" LonIndex is: "+lonIndex);
					
					
					boardingsTable[latIndex*10+lonIndex] += boardings;
					alightingsTable[latIndex*10+lonIndex] += alightings;	
					counter++;
				}
				System.out.println("There were "+records+" records");
				for(int i = 0;i<10;i++)
				{
					System.out.println();
					for(int j = 0;j<10;j++)
					{
						System.out.print(" | "+ boardingsTable[i*10+j]);
					}
				}
				String content = "<div><strong>Format: </strong> (Boardings, Alightings) <br><strong>MaxLat:</strong> "+maxlat+" <br><strong>MinLat:</strong> "+minlat+" <br><strong>MaxLong:</strong> "+maxlon+" <br><strong>MinLong:</strong> "+minlon+"<br> <strong>Number of records:</strong> "+records+"</div>";
				content += "<br><strong>Click on cell to generate marker on Map</strong><br><table>";
				double maxLatTable = maxlat;
				double minLongTable = minlon;
				for(int i =0;i<10;i++)
				{
					minLongTable = minlon;
					content+="<tr>";
					double avgLat = (maxLatTable + maxLatTable - dy)/2.0;
					for(int j=0;j<10;j++)
					{
						fileWriter.append(boardingsTable[i*10+j]+"|"+alightingsTable[i*10+j]);
						double avgLong = (minLongTable + minLongTable + dx)/2.0;
						content += "<td onclick='AddIcon("+avgLat+","+avgLong+")'> ("+ boardingsTable[i*10+j]+", "+alightingsTable[i*10+j]+") </td>";
						minLongTable += dx;
						if(j<10)
						{
							fileWriter.append(",");
						}
					}
					
					fileWriter.append("\n");
					maxLatTable-= dy;
					content+="</tr>";
				}
				
				content += "</table>";
				
				System.out.println();
				System.out.println(content);
				response.setContentType("test/plain");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(content);
						
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		fileWriter.flush();
		fileWriter.close();
	}
	
	

}
