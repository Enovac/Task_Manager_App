package ui;
import java.awt.*;
import java.util.*;
import java.io.Serializable;

import engine.*;
public class TaskScreen implements Serializable{
	private GamePanel gp;
	private TaskFolder currentF;
	private final Font titleFont=new Font("Arial",Font.BOLD,30);
	private final Font tFont=new Font("Arial",Font.BOLD,20);
	private  final Font defFont=new Font("Arial",Font.BOLD,20);
	private final int MAX_TASK_NUM=11;
	private final int TASK_Y=100;
	private final int TASK_X=10;
	private int selectedTask=-1;
	private int selectedInput;
	private final int selectedName=0;
	private final int selectedHours=1;
	private final int selectedMin=2;
	private final int selectedDead=3;
	private int count;
	private int selectedGapX=-1;
	private int selectedGapY=-1;
//===================Constructor=====================	
	public TaskScreen(GamePanel gp) {
		this.gp=gp;
	}
//======================G&S==========================
	
	public void setCalGap(int x,int y) {
	selectedGapX=x+1;
	selectedGapY=y+1;
	}
	public void setOpenedFolder(TaskFolder x) {
		currentF=x;
	}
	public TaskFolder getOpenedFolder() {
		return currentF;
	}
	public int getTaskX() {
		return TASK_X;
	}
	public int getTaskY() {
		return TASK_Y;
	}
	public int getTaskNum() {
		return currentF.getTaskNum();
	}
	public int getMaxTask() {
		return MAX_TASK_NUM;
	}
	public void toggleIsDone(int x) {
		if(!currentF.getTask(x).isDoneFinal())
			gp.increaseMoney();
			currentF.toggleIsDone(x);
	}
	public void setSelectedTask(int x) {
		selectedTask=x;
	}
	public int getSelectedTask() {
		return selectedTask;
	}
	public int getSelectedInput() {
		return selectedInput;
	}
	public void setSelectedInput(int x) {
		selectedInput=x;
	}
	public int getHoursInput() {
		return selectedHours;
	}
	public int getNameInput() {
		return selectedName;
	}
	public int getMinutesInput() {
		return selectedMin;
	}
	public int getDeadLineInput() {
		return selectedDead;
	}
	public void setTotalTime() {
		currentF.setTotalTime();
	}

//======================Methods======================
	public void addTask(Task task) {
		currentF.addTask(task);
	}
	public void deleteTask() {
		currentF.deleteTask(selectedTask);
	}
//======================Paint========================
	public void paintFolderScreen(Graphics2D g2d) {
		g2d.setFont(titleFont);
		g2d.setColor(Color.black);
		FontMetrics fm=g2d.getFontMetrics();
		int stringWidth=fm.stringWidth(currentF.getName());
		g2d.drawString(currentF.getName().isEmpty()?"N/A":currentF.getName(),((gp.getScreenWidth()-stringWidth)/2)-30,30);
		g2d.setFont(tFont);
		g2d.drawString(("Time to complete: "+currentF.getTotalHours()+"h "+currentF.getTotalMins()+"m"),TASK_X,TASK_Y-25);
		paintTasks(g2d);
	}
	public void paintTasks(Graphics2D g2d) {
		g2d.setStroke(new BasicStroke(3));
		if(gp.getInput())
			paintInput(g2d);
			
		for(int i=0;i<currentF.getTaskNum();i++) {
			int d=currentF.getDay(i);
			g2d.drawRect(TASK_X, TASK_Y+i*55,20,20);
			String taskName=currentF.getTaskName(i);
			if(i==selectedTask)
				g2d.drawString(taskName+"  <<  "+(currentF.getHours(i)==0?"":" "+currentF.getHours(i)+"h")
				+(currentF.getMinutes(i)==0?"":" "+currentF.getMinutes(i)+"m")+
				(d==-1?"":
				" Deadline: "+currentF.getDay(i)+"/"+currentF.getMonth(i)+"/"
						+currentF.getYear(i)), 35, TASK_Y+i*55+18);
			else
				g2d.drawString(taskName+"  "+(currentF.getHours(i)==0?"":" "+currentF.getHours(i)+"h")
				+(currentF.getMinutes(i)==0?"":" "+currentF.getMinutes(i)+"m")+(d==-1?"":" Deadline: "+currentF.getDay(i)+"/"+currentF.getMonth(i)+"/"
						+currentF.getYear(i)), 35, TASK_Y+i*55+18);
			if(currentF.isDone(i)) {
				FontMetrics fm=g2d.getFontMetrics();
				int width=fm.stringWidth(taskName);
				g2d.drawLine(35,TASK_Y+i*55+12,width+35,TASK_Y+i*55+12);
				g2d.drawLine(TASK_X+5,TASK_Y+i*55+5,TASK_X+10, TASK_Y+i*55+16);
				g2d.drawLine(TASK_X+10,TASK_Y+i*55+16,TASK_X+27, TASK_Y+i*55-14);}
		}
		if(currentF.getTaskNum()<MAX_TASK_NUM&&!gp.getInput()) {
			g2d.drawRect(TASK_X, TASK_Y+55*(currentF.getTaskNum()), 20,20);
			g2d.drawLine(TASK_X+4, TASK_Y+55*(currentF.getTaskNum())+10, TASK_X+16, TASK_Y+55*(currentF.getTaskNum())+10);
			g2d.drawLine(TASK_X+10, TASK_Y+55*(currentF.getTaskNum())+4, TASK_X+10, TASK_Y+55*(currentF.getTaskNum())+16);}	
	}
	public void paintInput(Graphics2D g2d) {
		count++;
		int x=gp.getScreenWidth()-420;int y=gp.getScreenHeight()/2-250;
		int inputW=400;int inputH=53+57*(gp.cal.getMaxRow()+3);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f));
		g2d.setColor(Color.white);
		g2d.fillRoundRect(x,y,inputW,inputH,25,25);
		g2d.setColor(Color.BLACK);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
		g2d.setStroke(new BasicStroke(3));
		g2d.drawRoundRect(x,y,inputW,inputH,25,25);
		g2d.setFont(defFont);
		g2d.drawRect(TASK_X, TASK_Y+55*(currentF.getTaskNum()), 20,20);
		FontMetrics fm=g2d.getFontMetrics();
		int nameWidth=fm.stringWidth(gp.getName());
		if(selectedInput!=selectedName||count<=35)
			g2d.drawString("  <<", 35+nameWidth, TASK_Y+55*(currentF.getTaskNum())+18);
		g2d.drawString(gp.getName(), 35, TASK_Y+55*(currentF.getTaskNum())+18);
		g2d.drawString("Enter time needed to complete task :",x+20,y+40);
		g2d.drawString("Hours: "+gp.getHours(),x+20,y+70);
		if(selectedInput!=selectedHours||count<=35)
		g2d.drawString("<<",x+140,y+70);
		g2d.drawString("Minutes: "+gp.getMinutes(),x+180,y+70);
		if(selectedInput!=selectedMin||count<=35)
		g2d.drawString("<<",x+320,y+70);
		g2d.drawString("Enter deadLine of task: ",x+20,y+110);
		int gap=0;
		for(int i=0;i<=gp.cal.getMaxRow();i++) {
			g2d.drawLine(x, y+(gap+=57)+110, x+inputW, y+gap+110);
			if(i!=6)
			g2d.drawLine(x+gap, y+170, x+gap, y+inputH);
		}
		if(selectedGapX!=-1) {
		g2d.setColor(Color.white);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.8f));
		g2d.fillRect((selectedGapX-1)*gp.cal.getCalGap()+x,(selectedGapY+1)*gp.cal.getCalGap()+y+110,gp.cal.getCalGap(),gp.cal.getCalGap());
		}
		g2d.setColor(Color.black);
		gp.cal.drawCal(g2d, x, y);
		if(count>60)
			count=0;
	}
}
