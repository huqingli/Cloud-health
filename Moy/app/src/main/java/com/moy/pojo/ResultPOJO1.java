package com.moy.pojo;
/**
 *
 *@Author Zlq
 *@Date 2018/12/21 19:45
 *@Description: 继承ResultPOJP类在原来的基础上增加了血压的高低
 *
 * */
public class ResultPOJO1 extends ResultPOJO{
    private String lowPressure;
    private String highPressure;
    public String getLowPressure(){
        return lowPressure;
    }
    public void setLowPressure(String lowPressure1){
        this.lowPressure = lowPressure1;
    }
    public String getHighPressure(){
        return highPressure;
    }
    public void setHighPressure(String highPressure1){
        this.highPressure = highPressure1;
    }
}
