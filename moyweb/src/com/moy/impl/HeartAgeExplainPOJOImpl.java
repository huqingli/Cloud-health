package com.moy.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.moy.conn.DBConnect;
import com.moy.conn.GetConnection;
import com.moy.dao.ResultDAO;
import com.moy.pojo.ResultAnalysisPOJO;
import com.moy.pojo.ResultPOJO;
import com.moy.pojo.UserPOJO;
import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

public class HeartAgeExplainPOJOImpl {
	
	 Connection conn = null;
	public HeartAgeExplainPOJOImpl(Connection conn){
		this.conn = conn;
	}
	
	public boolean doInsert(){
		return false;
	}
	
	
	
	public static void saverate(String userName, String rate){
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = GetConnection.getConnection();
		
		String sql = "insert into result(userName, heartrate) values(?,?)";
		try{
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			ps.setObject(1, userName);
			ps.setObject(2, rate);
			ps.executeUpdate();
			System.out.println(rate);
			conn.commit();
		}catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBConnect.closeAll(rs, ps);
			DBConnect.closeConn();
		}	
		
		
	}
	
	public static List<ResultPOJO> getdata(String userName){
		
		ResultPOJO result = null;
		List<ResultPOJO>list = new ArrayList<ResultPOJO>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = GetConnection.getConnection();
		System.out.println("22ww");
		
		String sql = "select * from result where userName= ?  order by userId desc limit 0,7";
		try{
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			ps.setObject(1, userName);
			rs = ps.executeQuery();
			while(rs.next()){
				result = new ResultPOJO();
				result.setHeartrate(rs.getString("heartrate"));
				result.setUserId(rs.getInt("userId"));
				result.setUserName(rs.getString("userName"));
				list.add(result);
				
			}
			System.out.println("1ww");
			conn.commit();
		}catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBConnect.closeAll(rs, ps);
			DBConnect.closeConn();
		}
		return list;
	}
    
		public boolean equalsUser(String userName, String password) {
		// TODO Auto-generated method stub
		return false;
	}

		
		// ¶Ô±í²Ù×÷½øÐÐ²åÈë
	public static boolean addresult(String heartrate, String heartage,
			String relaxationlevel, String respirationrate, String fmheartrate,
			String date, String rrinterval) {
		// TODO Auto-generated method stub
		boolean b = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = -1;
		Connection conn = GetConnection.getConnection();
		
		String sql = "insert into result(heartrate, heartage, relaxationlevel,respirationrate," +
				"fmheartrate,date,rrinterval)values(?,?,?,?,?,?,?)";
		try{
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			ps.setObject(1, heartrate);
			ps.setObject(2, heartage);
			ps.setObject(3, relaxationlevel);
			ps.setObject(4, respirationrate);
			ps.setObject(5, fmheartrate);
			ps.setObject(5, date);
			ps.setObject(5, rrinterval);
			result = ps.executeUpdate();
			b = result>0?true:false;
			conn.commit();
		}catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBConnect.closeAll(rs, ps);
			DBConnect.closeConn();
		}	
		return b;
	
	}

	public static ResultAnalysisPOJO createweb(String userName, String rate) {
		// TODO Auto-generated method stub
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultAnalysisPOJO resultanalysis=null;
		String grade=null;
		Connection conn = GetConnection.getConnection();
		if(rate==null||rate==""||rate=="0")
			grade="ÎåÐÇ";
		grade="5xing";
		String sql = "select * from resultanalysis where grade=?";
		/* request.setAttribute("list", list);  
	       request.getRequestDispatcher("index.jsp").forward(request, response); */
		
		try{
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			ps.setObject(1, grade);
			rs = ps.executeQuery();
			while(rs.next()){
				resultanalysis = new ResultAnalysisPOJO();
				resultanalysis.setId(rs.getInt("id"));
				resultanalysis.setGrade(rs.getString("grade"));
				resultanalysis.setResultcomment(rs.getString("resultcomment"));
				
			}
			conn.commit();
		}catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBConnect.closeAll(rs, ps);
			DBConnect.closeConn();
		}
		
		return resultanalysis;
		
       
        
        
        
        
        
        /*try{
			String sql = "select password from user where userName='"+userName+"'";

			stmt = this.conn.prepareStatement(sql);
			result = stmt.executeQuery();
			while (result.next()){
				if(password.equals(result.getString("password")))
					a = true;
				System.out.println("t");
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				System.out.print("11111");
				result.close();
				stmt.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		
	
		*/
		
      
		
	}

	public static String getcomment(int age,int heartage) {
		// TODO Auto-generated method stub
		String heartcomment = null;
		String comage=null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		/*
		if(age>heartage)
		{
			comage="ÄúµÄÐÄÔàÄê¼ÍÊÇ"+heartage+",±ÈÄúµÄÊµ¼ÊÄêÁäÐ¡"+(age-heartage)+"¡£";
		}
		else
		{
			comage="ÄúµÄÐÄÔàÄê¼ÍÊÇ"+heartage+",±ÈÄúµÄÊµ¼ÊÄêÁä´ó"+(heartage-age)+"¡£";
		}
		*/
		Connection conn = GetConnection.getConnection();
		String sql = "select * from heartageexplain where ? between heartagemin and heartagemax ";
		try{
			ps = conn.prepareStatement(sql);
			ps.setObject(1, heartage);
			rs = ps.executeQuery();
			while(rs.next()){
				heartcomment=rs.getString("heartcomment");
				//heartcomment=heartcomment+comage;			
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnect.closeAll(rs, ps);
			DBConnect.closeConn();
		}
		return heartcomment;
		
	}

	

	
}
