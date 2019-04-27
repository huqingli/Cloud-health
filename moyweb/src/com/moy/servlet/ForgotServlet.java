package com.moy.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moy.impl.UserPOJOImpl;
import com.moy.pojo.UserPOJO;

import net.sf.json.JSONObject;

public class ForgotServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public ForgotServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String userName = request.getParameter("userName");
		String secpassword = request.getParameter("secpassword");
		String password = request.getParameter("password");
		String varificationcode = request.getParameter("verificationCode");
		String phoneNumber = request.getParameter("phoneNumber");
		System.out.println(phoneNumber);
		System.out.println(password);
		System.out.println(varificationcode);
		JSONObject json = new JSONObject();
		UserPOJO user = null;
		
		if(varificationcode == null){
			
		}else{
			try {
				user = UserPOJOImpl.resetPwdByPhone(phoneNumber, password, varificationcode);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (user==null) {
			System.out.println("user is null");
			json.put("message", "fail");

		} else {
			json.put("message", "success");
		}
		PrintWriter out = response.getWriter();
		out.print(json.toString());
		out.flush();
		out.close();
		/*
		UserPOJO user = UserPOJOImpl.checkUser(userName);
		if(user == null){
			json.put("message", "aaa");
			System.out.println("aaa");

		}else{
			boolean a = UserPOJOImpl.checkSec(userName, secpassword);
			if(a){
				boolean b = UserPOJOImpl.resetPwd(userName, password);
				json.put("message", "ccc");
				System.out.println("ccc");
			}else{
				json.put("message", "bbb");
				System.out.println("bbb");
			}
		}
		System.out.println(password);
		PrintWriter out = response.getWriter();
		
		
		out.print(json.toString());
		out.flush();
		out.close();
		*/
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
