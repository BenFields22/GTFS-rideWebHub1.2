package com.load;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.load.FileMeta;

/**
 * Servlet implementation class editFile
 */
@MultipartConfig
public class GetFile extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static List<FileMeta> files = new LinkedList<FileMeta>();


	/***************************************************
	 * URL: /GetFile
	 * doPost(): upload the files and other parameters
	 ****************************************************/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	/***************************************************
	 * URL: /GetFile?f=value
	 * doGet(): get file of index "f" from List<FileMeta> as an attachment
	 ****************************************************/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. Get f from URL upload?f="?"
		System.out.println("Hit servlet");
				 String value2 = request.getParameter("mname");
				//File file = new File(FileUploadServlet.realPath+"testFolder"+getFile.getFileName());
				 String content = new Scanner(new File(UploadServlet.realPath+"testFolder/"+value2)).useDelimiter("\\Z").next();
				 //System.out.println(content);
				 response.setHeader("Cache-control", "no-cache, no-store");
				 response.setHeader("Pragma", "no-cache");
				 response.setHeader("Expires", "-1");
				 response.setCharacterEncoding("UTF-8");
				 String htmlcontent = "<textarea id='areaText'>" + content +"</textarea>";
				 response.getWriter().write(htmlcontent);			 	
					 	
	}

}
