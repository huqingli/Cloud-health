package com.moy.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.moy.conn.DBConnect;
import com.moy.conn.GetConnection;
import com.moy.dao.UserDAO;
import com.moy.pojo.ResultPOJO;
import com.moy.pojo.UserPOJO;


public class UserPOJOImpl implements UserDAO {

	Connection conn = null;

	public UserPOJOImpl(Connection conn) {
		this.conn = conn;
	}

	public boolean doInsert() {
		return false;
	}
	
	public static boolean resetUsername(String userName, String phoneNum) {
		boolean b = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = -1;
		Connection conn = GetConnection.getConnection();
		String sql = "update user set userName = ? where phoneNum like ?";
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			ps.setObject(1, userName);
			ps.setObject(2, phoneNum);
			result = ps.executeUpdate();
			b = result > 0 ? true : false;
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBConnect.closeAll(rs, ps);
			DBConnect.closeConn();
		}
		return b;
	}
	
	public static boolean resetSecpwd(String secpwd ,String phoneNum ) {
		boolean b = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = -1;
		Connection conn = GetConnection.getConnection();
		String sql = "update user set secpassword = ? where phoneNum like ?";
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			ps.setObject(1, secpwd);
			ps.setObject(2, phoneNum);
			result = ps.executeUpdate();
			b = result > 0 ? true : false;
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBConnect.closeAll(rs, ps);
			DBConnect.closeConn();
		}
		return b;
	}
	public static boolean resetPwd(String userName, String password) {
		boolean b = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = -1;
		Connection conn = GetConnection.getConnection();
		String sql = "update user set password = ? where userName like ?";
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			ps.setObject(1, password);
			ps.setObject(2, userName);
			result = ps.executeUpdate();
			b = result > 0 ? true : false;
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBConnect.closeAll(rs, ps);
			DBConnect.closeConn();
		}
		return b;
	}
	/*
	 * 2018/11/19
	 * 用于比较验证码并修改密码
	 */
	public static UserPOJO resetPwdByPhone(String phoneNumber, String password,String varificationcode)throws URISyntaxException {
		boolean b = false;
		UserPOJO user = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = -1;
		String RESULT = "";
		Connection conn = GetConnection.getConnection();
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet get = new HttpGet(
				"http://webapi.sms.mob.com/sms/verify?appkey=20f7e6408a56e"
						+ "&phone=" + phoneNumber + "&zone=" + "86"
						+ "&&code=" + varificationcode);
		
		String sql = "update user set password = ? where phoneNum like ?";
		try {
			HttpResponse response = httpClient.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				RESULT = EntityUtils.toString(entity, "utf-8");
				String regEx = "[^0-9]";
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(RESULT);
				String STATUS = m.replaceAll("").trim();
				System.out.println(STATUS);
				if (STATUS.equals("200")) {
					conn.setAutoCommit(false);
					ps = conn.prepareStatement(sql);
					ps.setObject(1, password);
					ps.setObject(2, phoneNumber);
					result = ps.executeUpdate();
					b = result > 0 ? true : false;
					if(b == true){
						user = new UserPOJO();
						user.setTOF(b);
					}
					conn.commit();
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnect.closeAll(rs, ps);
			DBConnect.closeConn();
		}
		return user;
	}

	public static boolean checkSec(String phoneNum, String secpassword) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean a = false;
		Connection conn = GetConnection.getConnection();
		try {
			String sql = "select secpassword from user where phonenum='"
					+ phoneNum + "'";

			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				if (secpassword.equals(rs.getString("secpassword")))
					a = true;
				System.out.println("t");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return a;
	}

	@SuppressWarnings("null")
	public static UserPOJO checkPhoneNum(String phoneNum) {

		UserPOJO user = new UserPOJO();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = GetConnection.getConnection();
		String sql = "select * from user where phoneNum = ?";

		try {
			ps = conn.prepareStatement(sql);
			ps.setObject(1, phoneNum);
			rs = ps.executeQuery();
			while (rs.next()) {
				if (phoneNum.equals(rs.getString("phoneNum"))) {
					System.out.print("找到了" + phoneNum);
					user.setPhonenum(rs.getString("phonenum"));
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnect.closeAll(rs, ps);
			DBConnect.closeConn();
		}
		return user;
	}

public static UserPOJO checkUserName(String userName) {

		
		UserPOJO user = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = GetConnection.getConnection();
		String sql = "select * from user where userName=?";
		try{
			ps = conn.prepareStatement(sql);
			ps.setObject(1, userName);
			rs = ps.executeQuery();
			while(rs.next()){
				user = new UserPOJO();
				user.setUserName(rs.getString("userName"));
				user.setPassword(rs.getString("password"));
				//user.setSecpassword(rs.getString("secpassword"));
				user.setAge(rs.getString("age"));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnect.closeAll(rs, ps);
			DBConnect.closeConn();
		}
		return user;
	}
	
	
	public static boolean checkUserNames(String userName,int userid) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean a = false;
		Connection conn = GetConnection.getConnection();
		String sql = "select * from user where userName=? and userid <> ?";
		try{
			ps = conn.prepareStatement(sql);
			ps.setObject(1, userName);
			ps.setObject(2, userid);
			rs = ps.executeQuery();
			while(rs.next()){
				if(rs.getString("userName") != null){
					a = true;
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnect.closeAll(rs, ps);
			DBConnect.closeConn();
		}
		return a;
	}
	public static boolean modifyUser(String userName, String sex, String age,
			String height, String weight, String phoneNum, String merPhone) {
		boolean b = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = -1;
		Connection conn = GetConnection.getConnection();

		String sql = "update user set sex = ?, age = ?, height = ?, weight = ?, userName = ?, phoneNumtelephoneerecommenterecommentuser = ? where phoneNum=?";
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			ps.setObject(1, sex);
			ps.setObject(2, age);
			ps.setObject(3, height);
			ps.setObject(4, weight);
			ps.setObject(5, userName);
			ps.setObject(6, merPhone);
			ps.setObject(7, phoneNum);
			result = ps.executeUpdate();
			b = result > 0 ? true : false;
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBConnect.closeAll(rs, ps);
			DBConnect.closeConn();
		}
		return b;
	}

	public static boolean addinfoUser(String userName, String sex, String age,
			String height, String weight) {
		boolean b = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = -1;
		Connection conn = GetConnection.getConnection();

		String sql = "update user set sex = ?, age = ?, height = ?, weight = ? where userName like ?";
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			ps.setObject(1, sex);
			ps.setObject(2, age);
			ps.setObject(3, height);
			ps.setObject(4, weight);
			ps.setObject(5, userName);
			result = ps.executeUpdate();
			b = result > 0 ? true : false;
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBConnect.closeAll(rs, ps);
			DBConnect.closeConn();
		}
		return b;
	}

	/*
	 * public static boolean registerUser(String userName, String password,
	 * String secpassword){ boolean b = false; PreparedStatement ps = null;
	 * ResultSet rs = null; int result = -1; Connection conn =
	 * GetConnection.getConnection();
	 * 
	 * String sql =
	 * "insert into user(userName, password, secpassword)values(?,?,?)"; try{
	 * conn.setAutoCommit(false); ps = conn.prepareStatement(sql);
	 * ps.setObject(1, userName); ps.setObject(2, password); ps.setObject(3,
	 * secpassword); result = ps.executeUpdate(); b = result>0?true:false;
	 * conn.commit(); }catch (SQLException e) { e.printStackTrace(); try {
	 * conn.rollback(); } catch (SQLException e1) { e1.printStackTrace(); }
	 * }finally{ DBConnect.closeAll(rs, ps); DBConnect.closeConn(); } return b;
	 * }
	 */

	public static boolean registerUser(String phoneNum, String password) {
		boolean b = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = -1;
		Connection conn = GetConnection.getConnection();
		String sql = "insert into user(phoneNum, password)values(?,?)";
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			ps.setObject(1, phoneNum);
			ps.setObject(2, password);
			result = ps.executeUpdate();
			b = result > 0 ? true : false;
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBConnect.closeAll(rs, ps);
			DBConnect.closeConn();
		}
		return b;
	}
	public static boolean insertR(String phoneNum) {
		boolean b = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = -1;
		Connection conn = GetConnection.getConnection();
		String sql = "INSERT INTO result(userid) SELECT userid FROM `user` WHERE phoneNum = ?";
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			ps.setObject(1, phoneNum);
			result = ps.executeUpdate();
			b = result > 0 ? true : false;
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBConnect.closeAll(rs, ps);
			DBConnect.closeConn();
		}
		return b;
	}
	public boolean equalsUser(String userName, String password) {
		PreparedStatement stmt = null;
		ResultSet result = null;
		boolean a = false;
		try {
			String sql = "select password from user where userName='"
					+ userName + "'";

			stmt = this.conn.prepareStatement(sql);
			result = stmt.executeQuery();
			while (result.next()) {
				if (password.equals(result.getString("password")))
					a = true;
				System.out.println("t");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				System.out.print("11111");
				result.close();
				stmt.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return a;
	}
	
	public boolean equalsUserName(String userName) {
		PreparedStatement stmt = null;
		ResultSet result = null;
		boolean a = false;
		try {
			String sql = "select * from user where userName='"
					+ userName + "'";

			stmt = this.conn.prepareStatement(sql);
			result = stmt.executeQuery();
			while (result.next()) {
				if (userName.equals(result.getString("username")))
					a = true;
				System.out.println("t");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				System.out.print("11111");
				result.close();
				stmt.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return a;
	}

	public static UserPOJO loginUser(String phonenum, String password) {
		UserPOJO user = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = GetConnection.getConnection();

		String sql = "select * from user where phoneNum = ? and password = ?";
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			ps.setString(1, phonenum);
			ps.setString(2, password);
			rs = ps.executeQuery();
			while (user == null && rs.next()) {
				
				System.out.println("******************************");
				user = new UserPOJO();
				user.setUserId(rs.getInt("userId"));
				user.setUserName(rs.getString("userName"));
				user.setPassword(rs.getString("password"));
				user.setPhonenum(rs.getString("phoneNum"));
				user.setSex(rs.getString("sex"));
				user.setAge(rs.getString("age"));
				user.setHeight(rs.getString("height"));
				user.setWeight(rs.getString("weight"));
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBConnect.closeAll(rs, ps);
			DBConnect.closeConn();
		}
		return user;
	}

	public static UserPOJO loginPhone(String countryCode, String phoneNum,
			String varificationCode) throws URISyntaxException {
		String result = "";
		UserPOJO user = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = GetConnection.getConnection();
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet get = new HttpGet(
				"http://webapi.sms.mob.com/sms/verify?appkey=20f7e6408a56e"
						+ "&phone=" + phoneNum + "&zone=" + countryCode
						+ "&&code=" + varificationCode);
		try {
			HttpResponse response = httpClient.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity, "utf-8");
				String regEx = "[^0-9]";
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(result);
				String STATUS = m.replaceAll("").trim();
				System.out.println(STATUS);
				if (STATUS.equals("200")) {
					String sql = "select * from user where phoneNum = ?";
					conn.setAutoCommit(false);
					ps = conn.prepareStatement(sql);
					ps.setString(1, phoneNum);
					rs = ps.executeQuery();
					while (user == null && rs.next()) {
						user = new UserPOJO();
						user.setUserId(rs.getInt("userId"));
						user.setUserName(rs.getString("userName"));
						user.setPassword(rs.getString("password"));
						user.setPhonenum(rs.getString("phoneNum"));
						user.setSex(rs.getString("sex"));
						user.setAge(rs.getString("age"));
						user.setHeight(rs.getString("height"));
						user.setWeight(rs.getString("weight"));
					}
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnect.closeAll(rs, ps);
			DBConnect.closeConn();
		}
		return user;
	}

	public static void saverate(String userName, String rate) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = GetConnection.getConnection();

		String sql = "insert into result(userName, rate) values(?,?)";
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			ps.setObject(1, userName);
			ps.setObject(2, rate);
			ps.executeUpdate();
			System.out.println(rate);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBConnect.closeAll(rs, ps);
			DBConnect.closeConn();
		}

	}

	public static List<ResultPOJO> getdata(String userName) {

		ResultPOJO result = null;
		List<ResultPOJO> list = new ArrayList<ResultPOJO>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = GetConnection.getConnection();
		System.out.println("22ww");

		String sql = "select * from result where userName= ?  order by id desc limit 0,7";
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			ps.setObject(1, userName);
			rs = ps.executeQuery();
			while (rs.next()) {
				result = new ResultPOJO();
				result.setRate(rs.getInt("rate"));
				result.setUserId(rs.getInt("id"));
				result.setUserName(rs.getString("userName"));
				list.add(result);

			}
			System.out.println("1ww");
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBConnect.closeAll(rs, ps);
			DBConnect.closeConn();
		}
		return list;
	}
	
	public static ResultPOJO loginResultData(int userid) {
		ResultPOJO results = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Connection conn = GetConnection.getConnection();
		
		String sql = "select * from result where userid = ? ";
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			while (results == null && rs.next()) {
				results = new ResultPOJO();
				results.sethighpressure(rs.getString("highpressure"));
				results.setlowpressure(rs.getString("lowpressure"));
				results.setHeartrate(rs.getString("heartrate"));
				results.setHeartage(rs.getString("heartage"));
				results.setRespirationrate(rs.getString("respirationrate"));
				results.setRelaxationlevel(rs.getString("relaxationlevel"));
				results.setFmheartrate(rs.getString("fmheartrate"));
				results.setRrinterval(rs.getString("rrinterval"));
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBConnect.closeAll(rs, ps);
			DBConnect.closeConn();
		}
		return results;
	}

}
