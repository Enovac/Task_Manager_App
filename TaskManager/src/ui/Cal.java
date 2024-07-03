package ui;
import java.util.*;

import javax.imageio.ImageIO;
import java.io.*;
import java.awt.*;
import java.awt.image.BufferedImage;
public class Cal {
	private  Calendar cal;//JAN IS 0
	private  final String[] MONTHS={"January","February","March","April","May","June","July","August","September","October",
			"November","December"};
	private  final String[] DAYS_OF_WEEK={"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
	private  final int CAL_GAP=57;
	public static  int currentYear;
	public static  int currentMonth;
	public static  int currentDay;
	public static int actualYear;
	public static int actualMonth;
	public static  int maxDays;	
	public static  int shift;
	public static  int maxRow;
	public static  int lastMax;
	private  int selectedR=-1;
	private  int selectedC=-1;
	private  int selectedDay=-1;
	private  BufferedImage leftArrow;
	private  BufferedImage rightArrow;
	public Cal() {
		try {
			leftArrow=(ImageIO.read(getClass().getResourceAsStream("/res/util/arrowLeft.png")));
			rightArrow=(ImageIO.read(getClass().getResourceAsStream("/res/util/arrowRight.png")));
			}catch(Exception ex) {ex.printStackTrace();}
		resetCal();
	}
	public  int getCYear() {
		return currentYear;
	}
	public  int getCMonth() {
		return currentMonth;
	}
	public  int getCDay() {
		return selectedDay;
	}
	public  void resetCal() {
		selectedR=-1;
		selectedC=-1;
		selectedDay=-1;
		cal=Calendar.getInstance();
		currentYear=cal.get(cal.YEAR);
		actualYear=currentYear;
		currentMonth=cal.get(cal.MONTH);
		actualMonth=currentMonth;
		currentDay=cal.get(cal.DAY_OF_MONTH);

		setMaxDays(currentMonth,currentYear,0);

		Calendar temp=Calendar.getInstance();
		temp.set(temp.DAY_OF_MONTH,1);
		shift=temp.get(temp.DAY_OF_WEEK)-temp.SUNDAY;
		 maxRow=5;
		if(maxDays+shift>7*5)
			maxRow=6;
		cal.set(cal.MONTH, cal.get(cal.MONTH)-1);
	
		setMaxDays(cal.get(cal.MONTH),cal.get(cal.YEAR),1);
		cal.set(cal.MONTH, cal.get(cal.MONTH)+1);

	}
	public void changeMonth(int x) {
		if(x!=-1||currentYear>actualYear||currentMonth>actualMonth) {
		cal.add(cal.MONTH,x);
		currentYear=cal.get(cal.YEAR);
		currentMonth=cal.get(cal.MONTH);
		currentDay=cal.get(cal.DAY_OF_MONTH);
		setMaxDays(currentMonth,currentYear,0);
		Calendar temp=Calendar.getInstance();
		temp.setTime(cal.getTime());
		temp.set(temp.DAY_OF_MONTH,1);
		shift=temp.get(temp.DAY_OF_WEEK)-temp.SUNDAY;
		 maxRow=5;
		if(maxDays+shift>7*5)
			maxRow=6;
		cal.set(cal.MONTH, cal.get(cal.MONTH)-1);
		setMaxDays(cal.get(cal.MONTH),cal.get(cal.YEAR),1);
		cal.set(cal.MONTH, cal.get(cal.MONTH)+1);}
	}
	public  void setMaxDays(int cm,int cy,int type) {
		int store;
		switch(cm) {
		case 0:case 2:case 4:case 6:case 7:case 9:case 11:
			store=31;break;
		case 1:
			if(cy%4==0&&(cy%100!=0||cy%400==0))
				store=29;
			else store=28;
			break;
		default:store=30;
		}
		if(type==0)
			maxDays=store;
		else
			lastMax=store;
	}
	public  int getCalGap() {
		return CAL_GAP;
	}
	public  int getMaxRow() {
		return maxRow;
	}
	public  void setSelected(int x,int y) {
		selectedC=((x-478)/CAL_GAP)+1;
		selectedR=((y-322)/CAL_GAP)+1;
		int tempDay=(7*(selectedR-1)+selectedC)-shift<=maxDays?(7*(selectedR-1)+selectedC)-shift:((7*(selectedR-1)+selectedC)%maxDays-shift);
		if(currentYear>actualYear||currentMonth>actualMonth)
			selectedDay=tempDay;
		else if(tempDay<currentDay&&(7*(selectedR-1)+selectedC)-shift<=maxDays||selectedR==1&&selectedC<=shift) 
			selectedR=-1;
		else if(selectedR>maxRow)
			selectedR=-1;
		else if(currentYear<actualYear||currentMonth<actualMonth&&currentYear==actualYear)
			selectedR=-1;
		else
			selectedDay=tempDay;
	}
	public  void drawCal(Graphics2D g2d,int x,int y) {
		FontMetrics fm=g2d.getFontMetrics();
		String month=MONTHS[currentMonth]+" "+currentYear;
		int wm=fm.stringWidth(month);
		g2d.drawString(month, x+(400-wm)/2, y+150);
		g2d.drawImage(leftArrow,x+80, y+125, null);
		g2d.drawImage(rightArrow,x+290, y+125, null);
		int notCount=1;
		for(String day:DAYS_OF_WEEK) {
			int w=fm.stringWidth(day);
			g2d.drawString(day,x+(notCount++*CAL_GAP)-((CAL_GAP+w)/2), y-(CAL_GAP/2)+120+CAL_GAP*2);
		}
		if(selectedR!=-1) {
			g2d.setColor(Color.white);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.6f));
			g2d.fillRect((480+(selectedC-1)*CAL_GAP),(324+(selectedR-1)*CAL_GAP), CAL_GAP, CAL_GAP);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));

		}
		for(int row=1;row<=maxRow;row++) {
			for(int col=1;col<=7;col++) {
				int day=lastMax-(shift-row*col);
				if(row*col>shift||row>1)
				 day=(7*(row-1)+col)-shift<=maxDays?(7*(row-1)+col)-shift:((7*(row-1)+col)%maxDays-shift);
				if(day<currentDay&&(7*(row-1)+col)-shift<=maxDays||row==1&&col<=shift) {
					if(currentYear==actualYear&&currentMonth==actualMonth) {
					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.6f));
					g2d.setColor(Color.red);
					g2d.fillRect((col-1)*CAL_GAP+x,(row+1)*CAL_GAP+y+110, CAL_GAP, CAL_GAP);
					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
					}
				}
				g2d.setColor(Color.black);
				int w=fm.stringWidth(""+day);
				g2d.drawString(""+day,x+(col*CAL_GAP)-((CAL_GAP+w)/2), y-(CAL_GAP/2)+120+((row+2)*CAL_GAP));
			}
		}
		
	}
	
}
