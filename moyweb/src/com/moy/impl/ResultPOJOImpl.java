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

public class ResultPOJOImpl implements ResultDAO{
	
	 Connection conn = null;
	public ResultPOJOImpl(Connection conn){
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

		
		// 对表操作进行更新
	public static boolean addresult(String heartrate, String heartage,
			String relaxationlevel, String respirationrate, String fmheartrate,
			String date, String rrinterval, int userid) {
		// TODO Auto-generated method stub
		boolean b = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = -1;
		Connection conn = GetConnection.getConnection();
		String sql = "update result set heartrate = ?, heartage = ?, relaxationlevel = ?," +
				"respirationrate = ?,fmheartrate = ?,date = ?,rrinterval = ?" +
				"where username = ?";
		try{
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			ps.setObject(1, heartrate);
			ps.setObject(2, heartage);
			ps.setObject(3, relaxationlevel);
			ps.setObject(4, respirationrate);
			ps.setObject(5, fmheartrate);
			ps.setObject(6, date);
			ps.setObject(7, rrinterval);
			ps.setObject(8, userid);
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
	public static boolean addresult1(String highpressure,String lowpressure,int userid) {
		// TODO Auto-generated method stub
		boolean b = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = -1;
		Connection conn = GetConnection.getConnection();
		String sql = "update result set highpressure = ?, lowpressure = ?" +
				"where userid = ?";
		try{
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			ps.setObject(1, highpressure);
			ps.setObject(2, lowpressure);
			ps.setObject(3, userid);
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
	public static ResultAnalysisPOJO createwebOfFood(String userName, String rate, String age) {
		// TODO Auto-generated method stub
		
		int ageInt = Integer.parseInt(age); 
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultAnalysisPOJO resultanalysis=null;
		String grade=null;
		
		Connection conn = GetConnection.getConnection();
		String sql = "select * from resultAboutFood where ? between ageMin and ageMax ";
		try{
			resultanalysis = new ResultAnalysisPOJO();
			grade = resultanalysis.getGradeOfHR(rate, age);
			resultanalysis.setGrade(grade);
			ps = conn.prepareStatement(sql);
			ps.setObject(1, ageInt);
			rs = ps.executeQuery();
			while(rs.next()){
				resultanalysis.setId(rs.getInt("id"));
				if(grade.equals("正常")){
					resultanalysis.setFoodcomment(rs.getString("normalFoodComment"));
					continue;
				}
				if(grade.equals("高")){
					resultanalysis.setFoodcomment(rs.getString("highFoodComment"));
					continue;
				}
				if(grade.equals("低")){
					resultanalysis.setFoodcomment(rs.getString("lowFoodComment"));
					continue;
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnect.closeAll(rs, ps);
			DBConnect.closeConn();
		}
		return resultanalysis;
	}
	
	
	/**
	 * 访问运动相关的数据库的数据
	 * @param userName
	 * @param rate
	 * @param age
	 * @return
	 */
	public static ResultAnalysisPOJO createwebOfSport(String userName, String rate, String age) {
		// TODO Auto-generated method stub
		
		int ageInt = Integer.parseInt(age); 
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultAnalysisPOJO resultanalysis=null;
		String grade=null;
		
		Connection conn = GetConnection.getConnection();
		String sql = "select * from resultAboutSport where ? between ageMin and ageMax ";
		try{
			resultanalysis = new ResultAnalysisPOJO();
			grade = resultanalysis.getGradeOfHR(rate, age);
			resultanalysis.setGrade(grade);
			ps = conn.prepareStatement(sql);
			ps.setObject(1, ageInt);
			rs = ps.executeQuery();
			while(rs.next()){
				resultanalysis.setId(rs.getInt("id"));
				if(grade.equals("正常")){
					resultanalysis.setSportcomment(rs.getString("normalSportComment"));
					continue;
				}
				if(grade.equals("高")){
					resultanalysis.setSportcomment(rs.getString("highSportComment"));
					continue;
				}
				if(grade.equals("低")){
					resultanalysis.setSportcomment(rs.getString("lowSportComment"));
					continue;
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnect.closeAll(rs, ps);
			DBConnect.closeConn();
		}
		return resultanalysis;
	}
	
	/**
	 * 访问检测报告的数据库数据
	 * @param userName
	 * @param rate
	 * @param age
	 * @return
	 */
	public static ResultAnalysisPOJO createweb(String userName, String rate, String age) {
		// TODO Auto-generated method stub
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultAnalysisPOJO resultanalysis=null;
		String grade=null;
		Connection conn = GetConnection.getConnection();
		
		String sql = "select * from resultanalysis where grade=?";
		/* request.setAttribute("list", list);  
	       request.getRequestDispatcher("index.jsp").forward(request, response); */
		
		try{
			conn.setAutoCommit(false);
			resultanalysis = new ResultAnalysisPOJO();
			grade = resultanalysis.getGradeOfHealth(rate,age);
			ps = conn.prepareStatement(sql);
			ps.setObject(1, grade);
			rs = ps.executeQuery();
			while(rs.next()){
				resultanalysis.setGrade(rs.getString("grade"));
				System.out.println(rs.getString("grade"));
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
		
		
		UserPOJO user=new UserPOJO();
		user=UserPOJOImpl.checkUserName(userName);
		System.out.println("check user done");
		
		if(user!=null )
		{	 String heartage=null;
		     String heartcomment=null;
		     String userid = ResultPOJOImpl.getUserid(userName);
		     
			 heartage = ResultPOJOImpl.getheartage(userid);
			 System.out.println("heart" + heartage);
			 int ageInt = Integer.parseInt(age);
			 int heartageInt = Integer.parseInt(heartage);
			 heartageInt = (heartageInt + ageInt)/2;
			 heartage = heartageInt+"";
			 resultanalysis.setHeartage(heartage);
			 heartcomment=HeartAgeExplainPOJOImpl.getcomment(Integer.parseInt(user.getAge()),Integer.parseInt(heartage));
			 resultanalysis.setHeartcomment(heartcomment);
			 System.out.println("info done");
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

	public static String getheartage(String userid) {
		// TODO Auto-generated method stub
		
		String heartage = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = GetConnection.getConnection();
		String sql = "select * from result where userid = ? ";
		try{
			ps = conn.prepareStatement(sql);
			ps.setObject(1, userid);
			rs = ps.executeQuery();
			while(rs.next()){
				heartage=rs.getString("heartage");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnect.closeAll(rs, ps);
			DBConnect.closeConn();
		}
		return heartage;
		
	}
	public static String getUserid(String username) {
		// TODO Auto-generated method stub
		
		String userid = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = GetConnection.getConnection();
		String sql = "select * from user where username = ?";
		try{
			ps = conn.prepareStatement(sql);
			ps.setObject(1, username);
			rs = ps.executeQuery();
			while(rs.next()){
				userid =rs.getInt("userid")+"";
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnect.closeAll(rs, ps);
			DBConnect.closeConn();
		}
		return userid;
	}
	public static String getheartrate(String userid) {
		// TODO Auto-generated method stub
		
		String heartrate = "77777";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = GetConnection.getConnection();
		String sql = "select * from result where userid = ? ";
		try{
			ps = conn.prepareStatement(sql);
			ps.setObject(1, userid);
			rs = ps.executeQuery();
			while(rs.next()){
				System.out.println("asdasdasdasdasdasdasdasd");
				heartrate=rs.getString("heartrate");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnect.closeAll(rs, ps);
			DBConnect.closeConn();
		}
		return heartrate;
		
	}

	
}
