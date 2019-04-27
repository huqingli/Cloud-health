package com.moy.proxy;

import java.sql.Connection;

import com.moy.conn.GetConnection;
import com.moy.dao.UserDAO;
import com.moy.impl.UserPOJOImpl;

public class UserDAOproxy implements UserDAO {
	
	Connection conn = null;
	UserPOJOImpl impl = null;
	public UserDAOproxy(){
		this.conn = GetConnection.getConnection();
		this.impl = new UserPOJOImpl(this.conn);
	}
	public void close(){
		try{
			this.conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean equalsUser(String userName, String password) {
		boolean a=this.impl.equalsUser(userName,password);
		this.close();
		return a;
	}
	public boolean doInsert() {
		// TODO Auto-generated method stub
		return false;
	}

}
