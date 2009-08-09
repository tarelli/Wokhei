package com.brainz.wokhei.servlet;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.brainz.wokhei.server.FileLoader;

public class ServeSiteMapServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7644836428024901522L;

	private static final Logger log = Logger.getLogger(ServeSiteMapServlet.class.getName());

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
		String fileType = req.getParameter("fileType");

		byte[] bytes = null;
		File file = null;

		if(fileType.equals("XML"))
		{
			file = new File("sitemaps/sitemap.xml");
			@SuppressWarnings("unused")
			String fullPath = file.getCanonicalPath();
			bytes = FileLoader.getBytesFromFile(file);
		}
		else if(fileType.equals("TXT"))
		{
			file = new File("sitemaps/sitemap.txt");
			bytes = FileLoader.getBytesFromFile(file);
		}
		else
		{
			log.log(Level.WARNING,"Sitemap filetype not supported");
		}

		if(bytes!= null)
		{
			res.setContentType("text/plain");
			res.getOutputStream().write(bytes);
		}
		else
		{
			log.log(Level.WARNING,"Sitemap byte array empty");
		}
	}
}
