package ui;
import java.io.*;
import java.awt.*;
import engine.*;
public class CalScreen implements Serializable{
	private GamePanel gp;
	private final Font titleFont=new Font("Arial",Font.BOLD,30);
	private  final String[] DAYS_OF_WEEK={"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};

//======================Constructor==================	
	
	public CalScreen(GamePanel gp){
		this.gp=gp;
	}
//======================G&S========================== 
//======================Methods======================
	
//======================Paint=========================
	public void paintCalScreen(Graphics2D g2d) {
		FontMetrics fm=g2d.getFontMetrics();
		int gap=gp.getScreenWidth()/7; int pad=35;
		g2d.setFont(titleFont);
		int height=fm.getHeight();
		for(int i=0;i<DAYS_OF_WEEK.length;i++) 
			g2d.drawString(DAYS_OF_WEEK[i],pad+gap*i, height*2);
		int jump=gp.getScreenHeight()/4;
		for(int row=1;row<=4;row++) {
			for(int col=1;col<=7;col++) {
				int day=Cal.lastMax-(Cal.shift-row*col);
				if(row*col>Cal.shift||row>1)
				 day=(7*(row-1)+col)-Cal.shift<=Cal.maxDays?(7*(row-1)+col)-Cal.shift:((7*(row-1)+col)%Cal.maxDays-Cal.shift);
//				if(day<currentDay&&(7*(row-1)+col)-shift<=maxDays||row==1&&col<=shift) {
//					if(currentYear==actualYear&&currentMonth==actualMonth) {
//					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.6f));
//					g2d.setColor(Color.red);
//					g2d.fillRect((col-1)*CAL_GAP+x,(row+1)*CAL_GAP+y+110, CAL_GAP, CAL_GAP);
//					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
//					}
//				}
				int w=fm.stringWidth(""+day);
				g2d.drawString(""+day,pad+gap*(col-1)+fm.stringWidth(day<10?"3333333"+day:""+day)/4,height*4+jump*(row-1));
			}
			
			
			
			
				
			
			
			
			
				
		}
		
		
		
	}

}
