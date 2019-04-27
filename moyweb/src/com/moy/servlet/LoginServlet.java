package com.moy.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.moy.factory.UserDAOFactory;
import com.moy.impl.UserPOJOImpl;
import com.moy.pojo.UserPOJO;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");

		UserPOJO user = null;
		Boolean flag = false;
		String phonenum = request.getParameter("phoneNum");
		String password = request.getParameter("password");
		String countrycode = request.getParameter("countryCode");
		String varificationcode = request.getParameter("verificationCode");


		System.out.println(phonenum);
		System.out.println(password);
		System.out.println(countrycode);
		System.out.println("varificationcode:"+varificationcode);
		
		JSONObject json = new JSONObject();
		if (countrycode == null) {
			user = UserPOJOImpl.loginUser(phonenum, password);
		} else {
			try {
				user = UserPOJOImpl.loginPhone(countrycode, phonenum, varificationcode);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (user == null) {
			json.put("message", "fail");

		} else {
			json.put("message", "success");
			System.out.println(user.getUserId());
			json.put("userId", user.getUserId());
			System.out.println(user.getUserName());
			if(user.getUserName()!=null)
				json.put("userName", user.getUserName());
			else
				json.put("userName", "");
			System.out.println(user.getPassword());
			json.put("password", user.getPassword());
			System.out.println(user.getPhonenum());
			json.put("phonenum", user.getPhonenum());
			if(user.getSex()!=null)
				json.put("sex", user.getSex());
			else
				json.put("sex","");
			System.out.println(user.getSex());
			if(user.getAge()!=null)
				json.put("age", user.getAge());
			else
				json.put("age", "");
			System.out.println(user.getAge());
			if( user.getHeight()!=null)
				json.put("height", user.getHeight());
			else
				json.put("height", "");
			System.out.println(user.getHeight());
			if(user.getWeight()!=null)
				json.put("weight", user.getWeight());
			else
				json.put("weight","");
			System.out.println(user.getWeight());
		}

		PrintWriter out = response.getWriter();
		out.print(json.toString());
		out.flush();
		out.close();

	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}