package com.moy.dao;

public interface UserDAO {
	public boolean equalsUser(String userName, String password);
	
	public boolean doInsert();
}
