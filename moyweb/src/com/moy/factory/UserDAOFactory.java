package com.moy.factory;

import com.moy.dao.UserDAO;
import com.moy.proxy.UserDAOproxy;



public class UserDAOFactory {
	public static UserDAO getDAOInstance(){
		return new UserDAOproxy();
	}

}
