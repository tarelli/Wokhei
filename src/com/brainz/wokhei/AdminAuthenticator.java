package com.brainz.wokhei;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.users.User;

public class AdminAuthenticator {
	
	@SuppressWarnings("unchecked")
	public static boolean isAdmin(User candidateAdmin)
	{
		boolean returnValue = false;
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
	    String select_query = "select from " + Admin.class.getName();
	    Query query = pm.newQuery(select_query); 
	    query.setFilter("administrator == paramUser"); 
	    query.declareParameters("java.lang.String paramUser"); 
	    List<Admin> admins = (List<Admin>) query.execute(candidateAdmin);
	    
	    if (!admins.isEmpty())
	    {	
	    	returnValue = true;
	    }
	    
		return returnValue;
	}

}
