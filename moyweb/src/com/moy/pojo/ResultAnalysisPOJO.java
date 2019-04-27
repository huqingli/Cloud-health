package com.moy.pojo;

public class ResultAnalysisPOJO {
	private int id;
	private String grade;
	private String resultcomment;
	private String heartcomment;
	private String foodcomment;
	private String heartage;
	private String food;
	private String sport;
	private String rate;
	
	private String sportcomment;
	
	public String getSportcomment(){
		return sportcomment;
	}
	public void setSportcomment(String sportcomment){
		this.sportcomment = sportcomment;
	}
	public String getFoodcomment(){
		return foodcomment;
	}
	public void setFoodcomment(String foodcomment){
		this.foodcomment = foodcomment;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGrade() {
		return grade;
	}
	public String getRate() {
		return rate;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getResultcomment() {
		return resultcomment;
	}
	public void setResultcomment(String resultcomment) {
		this.resultcomment = resultcomment;
	}
	public void setFood(String food){
		this.food = food;
	}
	public String getFood(){
		return food;
	}
	public void setSport(String sport){
		this.sport = sport;
	}
	public String getSport(){
		return sport;
	}
	
	/**
	 * 得出健康等级结果
	 * @param dValue
	 * @return
	 */
	public String getGradeOfHealth(String rate, String age){
		int dValue = analyzeRate(rate,age);
		int dValueAbs = Math.abs(dValue);
		
		if(dValue == 0) return "五星";
		if(dValueAbs < 10)return "四星";
			else if(dValueAbs < 15)return "三星";
				else if(dValueAbs < 20)return  "二星";
					else return "一星";
	}
	/**
	 * 得出心率情况
	 */
	public String getGradeOfHR(String rate, String age){
		int dValue = analyzeRate(rate,age);
		if(dValue > 15) return "高";
		else if(dValue < -15) return "低";
		else return "正常";
	}
	
	/**
	 * 得出心脏情况
	 */
	public String getGradeOfHeart(String rate, String age){
		int dValue = analyzeRate(rate,age);
		int dValueAbs = Math.abs(dValue);
		
		if(dValue == 0) return "好";
		else if(dValueAbs < 15)return "中";
				else return "差";
	}
	/**
	 * 分析心率，用于辅助分析健康等级
	 * @param rate
	 * @param age
	 * @return
	 */
	public static int analyzeRate(String Rate, String age){
		
		int ageInt = Integer.parseInt(age);
		int rate = Integer.parseInt(Rate);
		int dValue = rate;
		
		if(ageInt <=1 ){
			if(rate > 130) dValue -= 130;
			else if(rate < 110)dValue -= 110;
			else dValue = 0;
		}
		if(ageInt <=4 && ageInt >1){
			if(rate > 120) dValue -= 120;
			else if(rate < 100)dValue -= 100;
			else dValue = 0;
		}
		if(ageInt <=8 && ageInt >4){
			if(rate > 100) dValue -= 100;
			else if(rate < 80)dValue -= 80;
			else dValue = 0;
		}
		if(ageInt <=14 && ageInt >8){
			if(rate > 90) dValue -= 90;
			else if(rate < 70)dValue -= 70;
			else dValue = 0;
		}
		if(ageInt <=21 && ageInt >14){
			if(rate > 81) dValue -= 81;
			else if(rate < 76)dValue -= 76;
			else dValue = 0;
		}
		if(ageInt <=60 && ageInt >21){
			if(rate > 73) dValue -= 73;
			else if(rate < 70)dValue -= 70;
			else dValue = 0;
		}
		if(ageInt >=61){
			if(rate > 74) dValue -= 74;
			else if(rate < 67)dValue -= 67;
			else dValue = 0;
		}
		System.out.print("age:");
		System.out.println(ageInt);
		System.out.print("rate:");
		System.out.println(rate);
		System.out.print("dValue:");
		System.out.println(dValue);
		return dValue;
	}
	
}
