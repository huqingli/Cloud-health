package com.moy.pojo;

public class UserPOJO {
	
	private String userName;
	private String password;
	private String secpassword;
	private String sex;
	private int userId;
	private String age;
	private String height;
	private String weight;
	private String phonenum = "";
	private boolean TOF = false; 
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getPhonenum() {
		return phonenum;
	}
	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}
	
	/*
	 * 2018/11/19 好像没什么用
	 */
	public void setTOF(boolean TOF){
		this.TOF = TOF;
	}
	public boolean getTOF(){
		return this.TOF;
	}
	
	
	public String getSecpassword() {
		return secpassword;
	}
	public void setSecpassword(String secpassword) {
		this.secpassword = secpassword;
	}
}
