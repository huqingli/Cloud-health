package com.moy.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

public class ChartView extends View{
	

	  public int XPoint = 100; // 原点的X坐标
	  public int YPoint = 1000; // 原点的Y坐标
	  public int XScale = 150; // X的刻度长度
	  public int YScale = 150; // Y的刻度长度
	  public int XLength = 950; // X轴的长度
	   public int YLength = 800; // Y轴的长度 

	 public String[] XLabel; // X的刻度
	  public String[] YLabel; // Y的刻度
	  public int[] Data; // 数据
	  public String Title; // 显示的标题
	  private float screenW, screenH;
	  private float lastX;
	  private float lastY;
	  private Scroller scroller;
	  private float total_Width = 0;
	  
	  public ChartView(Context context, AttributeSet attr) {
		   super(context, attr);
		    scroller=new Scroller(context);
		   screenW = this.getWidth();
		   screenH = this.getHeight();
		   }
	  
	  public void SetInfo(String[] XLabels, String[] YLabels, int[] AllData,
			     String strTitle) {
			   XLabel = XLabels;
			   YLabel = YLabels;
			   Data = AllData;
			   Title = strTitle;
			  }
	  
	  

	  @Override
	   protected void onDraw(Canvas canvas) {
	   super.onDraw(canvas);// 重写onDraw方法
	   
	    System.out.println("3");
	    // canvas.drawColor(Color.WHITE);//设置背景颜色
	   Paint paint = new Paint();
	    paint.setStyle(Paint.Style.STROKE);
	     paint.setAntiAlias(true);// 去锯齿 
	  paint.setColor(Color.BLUE);// 颜色
	   Paint paint1 = new Paint();
	    paint1.setStyle(Paint.Style.STROKE);
	   paint1.setAntiAlias(true);// 去锯齿
	   paint1.setColor(Color.DKGRAY);
	   paint.setTextSize(36); // 设置轴文字大小
	    // 设置Y轴(对于系统来讲屏幕的原点在左上角）
	    canvas.drawLine(XPoint, YPoint - YLength, XPoint, YPoint, paint); // 轴线
	   for (int i = 0; i * YScale < YLength; i++) {
	    canvas.drawLine(XPoint, YPoint - i * YScale, XPoint + 5, YPoint - i
	       * YScale, paint); // 刻度 XPoint+5画出了一条短的小横线
	     try {
	      canvas.drawText(YLabel[i], XPoint - 22,
	         YPoint - i * YScale + 5, paint); // 文字
	     } catch (Exception e) {
	     }
	    }
	    canvas.drawLine(XPoint, YPoint - YLength, XPoint - 3, YPoint - YLength
	     + 6, paint); // 箭头
	   canvas.drawLine(XPoint, YPoint - YLength, XPoint + 3, YPoint - YLength
	       + 6, paint);
	    // 设置X轴
	    canvas.drawLine(XPoint, YPoint, XPoint + XLength, YPoint, paint); // 轴线
	   for (int i = 0; i * XScale < XLength; i++) {
	    canvas.drawLine(XPoint + i * XScale, YPoint, XPoint + i * XScale,
	       YPoint - 5, paint); // 刻度
	     try {
	      canvas.drawText(XLabel[i], XPoint + i * XScale - 10,
	         YPoint + 20, paint); // 文字
	      // 数据值
	      if (i > 0 && YCoord(Data[i - 1]) != -999
	        && YCoord(Data[i]) != -999) // 保证有效数据
	           canvas.drawLine(XPoint + (i - 1) * XScale, 
	       YCoord(Data[i - 1]), XPoint + i * XScale,
	         YCoord(Data[i]), paint);
	      canvas.drawCircle(XPoint + i * XScale, YCoord(Data[i]), 2,
	          paint);
	     } catch (Exception e) {
	     }
	    }
	    canvas.drawLine(XPoint + XLength, YPoint, XPoint + XLength - 6,
	     YPoint - 3, paint); // 箭头
	   canvas.drawLine(XPoint + XLength, YPoint, XPoint + XLength - 6,
	        YPoint + 3, paint);
	       paint.setTextSize(16);
	    canvas.drawText(Title, 150, 50, paint);
	   }
	  

	  private int YCoord(int y0) // 计算绘制时的Y坐标，无数据时返回-999
	  {
	   int y;
	   try {
	    y = y0;
	    } catch (Exception e) {
	     return -999; // 出错则返回-999
	   }
	   try {
	    return YPoint - y * YScale / Integer.parseInt(YLabel[1]);
	   } catch (Exception e) {
	    }
	   return y;
	   } 
	  @Override
	   public boolean onTouchEvent(MotionEvent event) {
	     switch (event.getAction()) { 
	  case MotionEvent.ACTION_DOWN:
	    lastX=event.getX();
	    lastY=event.getY();
	    
	     return true;
	    case MotionEvent.ACTION_MOVE:
	     float newX=event.getX();
	    float newY=event.getY();
	    
	     scrollBy((int) ((lastX-newX)*0.5), (int) ((lastY-newY)*0.5));
	    lastX=newX;
	    lastY=newY;
	    break;
	    case MotionEvent.ACTION_UP:
	     int scrollX=getScrollX();
	    int scrollY=getScrollY();
	     if((scrollX<0)&&(scrollX<-10||scrollY>10)){
	     //XY方向超出左边位置
	      scroller.startScroll(scrollX,scrollY, -scrollX, -scrollY);
	     invalidate();
	     }
	     else if((scrollX>total_Width-screenW)&&(scrollY<-10||scrollY>10)){
	     //XY方向超出右边位置
	      scroller.startScroll(scrollX,scrollY, (int)
	 (total_Width-screenW-scrollX), -scrollY);
	      invalidate();
	     }else if(scrollX<0){
	      //X方向超出左边的位置
	      scroller.startScroll(scrollX,scrollY, -scrollX, 0);
	     invalidate();
	     
	     }else if(scrollX>total_Width-screenW){
	         //X方向超出右边边的位置 
	    scroller.startScroll(scrollX,scrollY, (int)
	 (total_Width-screenW-scrollX), 0);
	      invalidate();
	    }else if(scrollY<-10||scrollY>10){
	      //Y方向超出了位置
	      scroller.startScroll(scrollX,scrollY, 0, -scrollY);
	     invalidate();
	     }
	     break;
	   default:
	     break;
	    }
	    return super.onTouchEvent(event);
	   } 
	  

	  @Override
	   public void computeScroll() {
	   if (scroller.computeScrollOffset()) {
	    // 调用这个下面的条件是由于scroller调用了滑动从而使它激发
	    scrollTo(scroller.getCurrX(), scroller.getCurrY());
	    invalidate();
	    return;
	    }
	    super.computeScroll();
	  }



}
