/**
 * 
 */
package com.brainz.wokhei.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.brainz.wokhei.File;
import com.brainz.wokhei.PMF;
import com.brainz.wokhei.shared.FileType;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;


/**
 * @author matteocantarelli
 *
 */
public class UploadFileServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6003934788688102743L;

	private static final Logger log = Logger.getLogger(UploadFileServlet.class.getName());

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
		try 
		{
			ServletFileUpload upload = new ServletFileUpload();
			res.setContentType("text/plain");

			Long orderId = Long.parseLong(req.getParameter("orderid"));
			FileType fileType = FileType.valueOf(req.getParameter("fileType"));

			FileItemIterator iterator = upload.getItemIterator(req);
			while (iterator.hasNext()) {
				FileItemStream item = iterator.next();
				InputStream stream = item.openStream();

				if (item.isFormField()) 
				{
					log.info("Got a form field: " + item.getFieldName());
				} 
				else 
				{
					log.info("Got an uploaded file: " + item.getFieldName() +	", name = " + item.getName());

					Blob uploadedFile=new Blob(IOUtils.toByteArray(stream));

					UserService userService = UserServiceFactory.getUserService();
					User user = userService.getCurrentUser();

					PersistenceManager pm = PMF.get().getPersistenceManager();
					String select_query = "select from " + File.class.getName();
					Query query = pm.newQuery(select_query);
					query.setFilter("orderid == paramId  &&  type == paramType");
					query.declareParameters("java.lang.Long paramId , com.brainz.wokhei.shared.FileType paramType");
					//execute
					List<File> files = (List<File>) query.execute(orderId,fileType);

					File newFile=null;
					if (!files.isEmpty()) {
						//should be only one - safety check here
						//a file for that order of that type already existed. Overwriting
						newFile = files.get(0);
						newFile.setFile(uploadedFile);
					}
					else
					{
						//no file existed. creating a new one.
						newFile=new File(uploadedFile,fileType,orderId);
					}
					try {

						pm.makePersistent(newFile);

						if (user != null) 
						{
							log.info("file [" + newFile.getId() + " type: "+ newFile.getFileType()+"] uploaded  from " + user.getNickname());
						} 
						else 
						{
							//should never happen!
							log.info("file [" + newFile.getId() + " type: "+ newFile.getFileType()+"] uploaded from NOBODY");
						}
					} 
					finally 
					{
						pm.close();
					}
				} 
			}
		} 
		catch (Exception ex) 
		{
			throw new ServletException(ex);
		}
	}
}
