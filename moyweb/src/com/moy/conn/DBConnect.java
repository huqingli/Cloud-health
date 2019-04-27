package com.moy.conn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnect {
	private static ThreadLocal<Connection> local =new ThreadLocal<Connection>();
	
	public static void closeConn(){
		   try{
				if(local.get()!=null){
					Connection conn = local.get();
					conn.close();
					local.set(null);
				}
		      }catch(Exception e){
		    	  System.err.println(e.getMessage());
		      }
			}

				

			public static void closeAll(ResultSet rs, PreparedStatement ps) {
				// TODO Auto-generated method stub
				try {
					if(rs!=null&&!rs.isClosed()){	rs.close();	}
					if(ps!=null&&!ps.isClosed()){ ps.close(); }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

}
