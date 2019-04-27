package com.moy.pojo;

public class ResultAnalysisPOJO {
	

	private int id;
	private String grade;
	private String resultcomment;
	private String heartcomment;
	private String heartage;
	private String sport;
	private String food;
	private String sportcomment;
	private String foodcomment;

	public String getFoodcomment(){return foodcomment;}
	public void setFoodcomment(String foodcomment){this.foodcomment = foodcomment;}

	public String getSportcomment(){return sportcomment;}
	public void setSportcomment(String sportcomment){this.sportcomment = sportcomment;}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getResultcomment() {
		return resultcomment;
	}
	public String getHeartcomment() {
		return heartcomment;
	}
	public void setHeartcomment(String heartcomment) {
		this.heartcomment = heartcomment;
	}
	public String getHeartage() {
		return heartage;
	}
	public void setHeartage(String heartage) {
		this.heartage = heartage;
	}
	public void setResultcomment(String resultcomment) {
		this.resultcomment = resultcomment;
	}

	public void setFood(String food){
		this.food = food;
	}
	public String getFood()
	{
		return food;
	}

	public void setSport(String sport){
		this.sport=sport;
	}
	public String getSport(){
		return sport;
	}
	

}
