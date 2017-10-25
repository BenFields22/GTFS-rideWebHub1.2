package com.load;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import com.load.FileMeta;



/**
 * Servlet implementation class LoadCSV
 */
public class LoadCSV extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static List<FileMeta> files = new LinkedList<FileMeta>();
	public static String realPath;
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
		System.out.println("In loadCSV");
		files.addAll(MultipartRequestHandler.uploadByApacheFileUpload(request));
		realPath = getServletContext().getRealPath("/");
		File file = new File(realPath+"testFolder");
		int index = files.size()-1;
		String filename = files.get(index).getFileName();
		String type = files.get(index).getFileType();
		
		UploadServlet.uploadedFiles.add(files.get(index).getFileName());
		System.out.println("File:"+files.get(index).getFileName());
		File file2 = new File(realPath+"testFolder//"+files.get(index).getFileName());
		file2.createNewFile();
		InputStream initialStream = files.get(index).getContent();
	    byte[] buffer = new byte[initialStream.available()];
	    initialStream.read(buffer);
	    initialStream.close();
	    File targetFile = file2;
	    OutputStream outStream = new FileOutputStream(targetFile);
	    outStream.write(buffer);
	    outStream.close();
	    response.setContentLength(0);
		
		
	}
}




