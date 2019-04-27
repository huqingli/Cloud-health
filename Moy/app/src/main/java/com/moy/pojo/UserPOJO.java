package com.moy.pojo;

public class UserPOJO {
	
	private int userId;
	private String userName;
	private String password;
	private String sex;
	private String age;
	private String height;
	private String weight;
	private String phoneNum;
	private String smoking;
	private String drinking;
	private String gaoya;
	private String diya;

	public String getGaoya() {
		return gaoya;
	}

	public void setGaoya(String gaoya) {
		this.gaoya = gaoya;
	}

	public String getDiya() {
		return diya;
	}

	public void setDiya(String diya) {
		this.diya = diya;
	}

	public String getSmoking() {
		return smoking;
	}
	public void setSmoking(String smoking) {
		this.smoking = smoking;
	}
	public String getDrinking() {
		return drinking;
	}
	public void setDrinking(String drinking) {
		this.drinking = drinking;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		if(userName==null)
			return "";
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
		if (sex==null)
			return "";
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAge() {
		if (age==null)
			return "";
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getHeight() {
		if(height==null)
			return "";
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWeight() {
		if (weight==null)
			return "";
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	

}
