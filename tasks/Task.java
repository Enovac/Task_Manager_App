package tasks;
import java.io.*;
public class Task implements Serializable,Comparable<Task>{
	 private String name;
	 private int hours;
	 private int minutes;
	 private int month;
	 private int day;
	 private int year;
	 private String dayName;
	 private boolean isDone;
	 private boolean isDoneFinal;
//=====================Constructor======================
	 public Task(String name,String hours,String minutes,int day,int month,int year) {
		 this.name=name;
		this.year=year;
		this.month=month+1;
		this.day=day;
		 try {
			 this.hours=Integer.parseInt(hours);
		 }catch(Exception ex) {this.hours=0;}
		 try {
			 this.minutes=Integer.parseInt(minutes);
		 }catch(Exception ex) {this.minutes=0;}
		 System.out.println(day+" "+month+" "+year);
	 }
//=====================Methods==========================
	 public void toggleIsDone() {
		 isDone=!isDone;
		 if(isDone)
			 isDoneFinal=true;
	 }
	 @Override
	 public int compareTo(Task x) {
		 if(day==-1)
			 return 1;
		 if(x.day==-1)
			 return -1;
		 if(year<x.year)
			 return -1;
		 if(year>x.year)
			 return 1;
		 if(month<x.month)
			 return -1;
		 if(month>x.month)
			 return 1;
		 if(day<x.day)
			 return -1;
		 if(day>x.day)
			 return 1;
		 return 0;
	 }
//=====================Paint============================
//=====================G&S==============================
	 public boolean isDoneFinal() {
		 return isDoneFinal;
	 }
	 public int getHours() {
		 return hours;
	 }
	 public int getMinutes() {
		 return minutes;
	 }
	 public boolean isDone() {
		 return isDone;
	 }
	 public String getName() {
		 return name;
	 }
	 public int getDay() {
		 return day;
	 }
	 public int getMonth() {
		 return month;
	 }
	 public int getYear() {
		 return year;
	 }
	 
	
	
	
	
}
