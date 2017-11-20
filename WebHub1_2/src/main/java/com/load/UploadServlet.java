package com.load;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTMLDocument.Iterator;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.load.FileMeta;
import com.load.Unzip;

//this to be used with Java Servlet 3.0 API
@MultipartConfig
public class UploadServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	public static Set<String> uploadedFiles = new HashSet<String>();
	public static String realPath;

	// this will store uploaded files
	static List<FileMeta> files = new LinkedList<FileMeta>();
	/***************************************************
	 * URL: /upload
	 * doPost(): upload the files and other parameters
	 ****************************************************/
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException{
	    
		// 1. Upload File Using Java Servlet API
		//files.addAll(MultipartRequestHandler.uploadByJavaServletAPI(request));			
			
		
		// 1. Upload File Using Apache FileUpload
		files.addAll(MultipartRequestHandler.uploadByApacheFileUpload(request));
		realPath = getServletContext().getRealPath("/");
		System.out.println("TestFolder is at"+ realPath+"testFolder/");
		File file = new File(realPath+"testFolder");
		//boolean success = file.mkdirs();
		
		/*if(success){
			System.out.println("Directory created successfully");
		}*/
		
		int index = files.size()-1;
		int count = 0;
		count++;
		String filename = files.get(index).getFileName();
		//uploadedFiles.add(files.get(index).getFileName());
		System.out.println("File:"+files.get(index).getFileName());
		File file2 = new File(realPath+"testFolder//"+files.get(index).getFileName());
		file2.getParentFile().mkdir();
		file2.createNewFile();
		InputStream initialStream = files.get(index).getContent();
	    byte[] buffer = new byte[initialStream.available()];
	    initialStream.read(buffer);
	    initialStream.close();
	    File targetFile = file2;
	    OutputStream outStream = new FileOutputStream(targetFile);
	    outStream.write(buffer);
	    outStream.close();
			
		System.out.println("The number of files handled is "+count );
		Unzip.unzip(realPath+"testFolder//"+files.get(index).getFileName(),realPath+"testFolder/");
		targetFile.delete();
		// 2. Set response type to json
		response.setHeader("Cache-control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "-1");
		response.setContentType("application/json");
		
		// 3. Convert List<FileMeta> into JSON format
    	ObjectMapper mapper = new ObjectMapper();
    	
    	// 4. Send result to client
    	mapper.writeValue(response.getOutputStream(), files);
	
	}
	/***************************************************
	 * URL: /upload?f=value
	 * doGet(): get file of index "f" from List<FileMeta> as an attachment
	 ****************************************************/
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException{
		
	}
}
