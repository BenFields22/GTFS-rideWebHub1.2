package com.load;
import com.load.GtfsDatabaseLoaderMain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.postgresql.copy.CopyManager;
import org.postgresql.jdbc.PgConnection;



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
		args[1] = "--url=\"jdbc:postgresql://ridedb.cr8hn6m3gchm.us-west-2.rds.amazonaws.com/testDB\"";
		args[2] = "--username=\"rideadmindb\"";
		args[3] = "--password=\"alltheridestuff\"";
		args[4] = realPath+"testFolder/";
		String fileLocation = realPath+"testFolder/";
		
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
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://ridedb.cr8hn6m3gchm.us-west-2.rds.amazonaws.com/testDB","rideadmindb","alltheridestuff");
			if	(con.isValid(0))
			{
				System.out.println("Connection for uploading ride files made successfully");
			}
			else
			{
				System.out.println("Connection for uploading ride files failed");
			}
			
				Class.forName("org.postgresql.Driver");
				String query = "ALTER USER rideadmindb WITH SUPERUSER;";
				
				PreparedStatement stmt = con.prepareStatement(query);
				
				//boolean Rs1 = stmt.execute();
				
				File tmpDir = new File(fileLocation+"board_alight.txt");
				boolean exists = tmpDir.exists();
				String text = null;
				if(exists){
					
					BufferedReader brTest = new BufferedReader(new FileReader(fileLocation+"board_alight.txt"));
					text = brTest.readLine();
					// Stop. text is the first line.
					System.out.println(text);
					

				}
				
				query = "CREATE TABLE IF NOT EXISTS board_alight("+
						"trip_id text,"+
						"stop_id text,"+
						"stop_sequence text,"+
						"record_use text,"+
						"schedule_relationship text,"+
						"boardings text,"+
						"alightings text,"+
						"current_load text,"+
						"load_type text,"+
						"rack_down text,"+
						"bike_boardings text,"+
						"bike_alightings text,"+
						"ramp_used text,"+
						"ramp_boardings text,"+
						"ramp_alightings text,"+
						"service_date text,"+
						"service_arrival_time text,"+
						"service_departure_time text,"+
						"source text"+
						");";
				 
				stmt = con.prepareStatement(query);
				tmpDir = new File(fileLocation+"board_alight.txt");
				exists = tmpDir.exists();
				if(exists){
					boolean Rs = stmt.execute();
					PgConnection copyOperationConnection = (PgConnection) con;
					CopyManager copyManager = new CopyManager(copyOperationConnection);
					copyManager.copyIn("COPY board_alight("+text+") FROM STDIN WITH DELIMITER ',' csv header", new FileReader(fileLocation+"board_alight.txt"));
				}
				
				
				tmpDir = new File(fileLocation+"trip_capacity.txt");
				exists = tmpDir.exists();
				if(exists){
					
					BufferedReader brTest = new BufferedReader(new FileReader(fileLocation+"trip_capacity.txt"));
					text = brTest.readLine();
					// Stop. text is the first line.
					System.out.println(text);
					

				}
				
				query = "CREATE TABLE IF NOT EXISTS trip_capacity("+
						"agency_id text,"+
						"trip_id text,"+
						"service_date text,"+
						"vehicle_description text,"+
						"seated_capacity text,"+
						"standing_capacity text,"+
						"wheelchair_capacity text,"+
						"bike_capacity text"+
						");";
				
				stmt = con.prepareStatement(query);
				tmpDir = new File(fileLocation+"trip_capacity.txt");
				exists = tmpDir.exists();
				if(exists){
					boolean Rs = stmt.execute();
					PgConnection copyOperationConnection = (PgConnection) con;
					CopyManager copyManager = new CopyManager(copyOperationConnection);
					copyManager.copyIn("COPY trip_capacity("+text+") FROM STDIN WITH DELIMITER ',' csv header", new FileReader(fileLocation+"trip_capacity.txt"));
				}
				
				tmpDir = new File(fileLocation+"rider_trip.txt");
				exists = tmpDir.exists();
				if(exists){
					
					BufferedReader brTest = new BufferedReader(new FileReader(fileLocation+"rider_trip.txt"));
					text = brTest.readLine();
					// Stop. text is the first line.
					System.out.println(text);
					

				}
				
				query = "CREATE TABLE IF NOT EXISTS rider_trip("+
						"rider_id text,"+
						"agency_id text,"+
						"trip_id text,"+
						"boarding_stop_id text,"+
						"boarding_stop_sequence text,"+
						"alighting_stop_id text,"+
						"alighting_stop_sequence text,"+
						"service_date text,"+
						"boarding_time text,"+
						"alighting_time text,"+
						"rider_type text,"+
						"rider_type_description text,"+
						"fare_paid text,"+
						"transaction_type text,"+
						"fare_media text,"+
						"accompanying_device text,"+
						"transfer_status text,"+
						"PRIMARY KEY(rider_id)"+
						");";
				stmt = con.prepareStatement(query);
				tmpDir = new File(fileLocation+"rider_trip.txt");
				exists = tmpDir.exists();
				if(exists){
					boolean Rs = stmt.execute();
					PgConnection copyOperationConnection = (PgConnection) con;
					CopyManager copyManager = new CopyManager(copyOperationConnection);
					copyManager.copyIn("COPY rider_trip("+text+") FROM STDIN WITH DELIMITER ',' csv header", new FileReader(fileLocation+"rider_trip.txt"));
				}
				
				tmpDir = new File(fileLocation+"ridership.txt");
				exists = tmpDir.exists();
				if(exists){
					
					BufferedReader brTest = new BufferedReader(new FileReader(fileLocation+"ridership.txt"));
					text = brTest.readLine();
					// Stop. text is the first line.
					System.out.println(text);
				}
				
				query = "CREATE TABLE IF NOT EXISTS ridership("+
						"total_boardings text,"+
						"total_alightings text,"+
						"ridership_start_date text,"+
						"ridership_end_date text,"+
						"ridership_start_time text,"+
						"ridership_end_time text,"+
						"service_id text,"+
						"monday text,"+
						"tuesday text,"+
						"wednesday text,"+
						"thursday text,"+
						"friday text,"+
						"saturday text,"+
						"sunday text,"+
						"agency_id text,"+
						"route_id text,"+
						"direction_id text,"+
						"trip_id text,"+
						"stop_id text,"+
						"PRIMARY KEY(ridership_start_date,ridership_end_date)"+
						");";
				
				stmt = con.prepareStatement(query);
				tmpDir = new File(fileLocation+"ridership.txt");
				exists = tmpDir.exists();
				if(exists){
					boolean Rs = stmt.execute();
					PgConnection copyOperationConnection = (PgConnection) con;
					CopyManager copyManager = new CopyManager(copyOperationConnection);
					copyManager.copyIn("COPY ridership("+text+") FROM STDIN WITH DELIMITER ',' csv header", new FileReader(fileLocation+"ridership.txt"));
				}
				
				tmpDir = new File(fileLocation+"ride_feed_info.txt");
				exists = tmpDir.exists();
				if(exists){
					
					BufferedReader brTest = new BufferedReader(new FileReader(fileLocation+"ride_feed_info.txt"));
					text = brTest.readLine();
					// Stop. text is the first line.
					System.out.println(text);

				}
				
				query = "CREATE TABLE IF NOT EXISTS ride_feed_info("+
						"ride_files text,"+
						"ride_start_date text,"+
						"ride_end_date text,"+
						"gtfs_feed_date text,"+
						"default_currency_type text,"+
						"ride_feed_version text"+
						");";
				stmt = con.prepareStatement(query);
				tmpDir = new File(fileLocation+"ride_feed_info.txt");
				exists = tmpDir.exists();
				if(exists){
					boolean Rs = stmt.execute();
					PgConnection copyOperationConnection = (PgConnection) con;
					CopyManager copyManager = new CopyManager(copyOperationConnection);
					copyManager.copyIn("COPY ride_feed_info("+text+") FROM STDIN WITH DELIMITER ',' csv header", new FileReader(fileLocation+"ride_feed_info.txt"));
				}
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		
		response.setContentType("test/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("Feed Loaded Into Database");
		
	}

}
