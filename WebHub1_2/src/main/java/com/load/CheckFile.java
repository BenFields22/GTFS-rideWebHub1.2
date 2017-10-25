package com.load;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tools.ant.util.StringUtils;

/**
 * Servlet implementation class CheckFile
 */

public class CheckFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public static int ordinalIndexOf(String str, String substr, int n) {
	    int pos = str.indexOf(substr);
	    while (--n > 0 && pos != -1)
	        pos = str.indexOf(substr, pos + 1);
	    return pos;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("In CheckFile get");
		response.setContentLength(0);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String value2 = request.getParameter("info");
		String name = request.getParameter("name");
		System.out.println(name + "|" +value2);
		System.out.println("In CheckFile post");
		String cvsSplitBy = ",";
		String[] headers = value2.split(cvsSplitBy);
		
		if(UploadServlet.uploadedFiles.contains(name))
		{
			UploadServlet.realPath = getServletContext().getRealPath("/");
			String csvFile = UploadServlet.realPath+"/testfolder/"+name;
	        BufferedReader br = null;
	        String line = "";
	        int correct = 1;
	        

	        try {

	            br = new BufferedReader(new FileReader(csvFile));
	            
	            
	            int lineN = 1;
	            while ((line = br.readLine()) != null) {
	            	System.out.println("beginning of new line");
	                // use comma as separator
	                String[] country = line.split(cvsSplitBy);
	                int size = country.length;
	                if(size != headers.length){
	                	response.setContentType("test/plain");
	        			response.setCharacterEncoding("UTF-8");
	        			response.getWriter().write("The file does not have the proper number of columns on line "+lineN+"\n");
	        			correct = 0;
	                }
	                for	(int i = 0; i <size;i++){
	                	System.out.println(country[i]+ ",");
	                	if(lineN == 1){
	                		System.out.println(country[i] +"|"+headers[i]);
	                		if(!country[i].equals(headers[i])){
	                			response.setContentType("test/plain");
	    	        			response.setCharacterEncoding("UTF-8");
	    	        			response.getWriter().write("The file does not have the correct header name in column "+(i+1)+": "+country[i]+" should be "+headers[i]+"\n");
	    	        			correct = 0;
	                		}
	                	}
	                }

	                lineN++;

	            }

	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (br != null) {
	                try {
	                    br.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }

	    if(correct==1)
	    {
	    	
	        
	        //use file.renameTo() to rename the file
	    	File file = new File(UploadServlet.realPath+"/testFolder/"+name);
	    	String filePath = UploadServlet.realPath+"/testFolder/"+name;
	    	System.out.println(UploadServlet.realPath+"/testFolder/"+name);
	    	int pos = ordinalIndexOf(filePath, ".", 2);
	    	String main = (UploadServlet.realPath+"/testFolder/"+name).substring(0, pos);
	    	System.out.println(main);
	    	System.out.println(main+".txt");
	    	// File (or directory) with new name
	    	File file2 = new File(main+".txt");
	    	// Rename file (or directory)
	    	boolean success = file.renameTo(file2);
	    	if (!success)
	    	{
	    	// File was not successfully renamed
	    		System.out.println("Successfully changed name");
	    	}
	    	String newName = file2.getName();
	    	UploadServlet.uploadedFiles.remove(name);
	    	UploadServlet.uploadedFiles.add(newName);
	    	response.setContentType("test/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("File Loaded");
	    }
			
		}

		else{
			response.setContentType("test/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("There is no file loaded named "+name+" please ensure the file has been selected and named correctly");
		}
		
	}

}
