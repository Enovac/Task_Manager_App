package listeners;
import java.awt.event.*;
import java.awt.*;
import engine.*;
import ui.Cal;
public class MouseL implements MouseListener{
	private GamePanel gp;
	public MouseL(GamePanel gp) {
		this.gp=gp;
	}
	@Override
	public void mousePressed(MouseEvent e) {
		int x=e.getX(); int y=e.getY();
		if(gp.getCurrentScreen()==gp.getHomeScreen())
			HomeScreen(x,y);
		else if(gp.getCurrentScreen()==gp.getFolderScreen())
			FolderScreen(x,y);
		else if(gp.getCurrentScreen()==gp.getShopScreen())
			shopScreen(x, y);
		else
			paintScreen(x,y);
	}	
//====================Shop&Paint====================
	public void shopScreen(int x,int y) {
		if(!gp.getConfirm()) {
		if(y>=175&&y<=275) {
			int index=select(x,1,gp.characters.length);
			if(index!=99)
				if(gp.checkC(index))
				gp.waitToConfirm(true, index);
		}
		else if(y>=400&&y<=500) {
			int index=select(x,2,gp.wallpaper.length);
			if(index!=99)
				if(gp.checkW(index))
				gp.waitToConfirm(false, index);
		}
		}
		else {
			if(y>=gp.getScreenHeight()/2-80&&y<=gp.getScreenHeight()/2-45)
				if(x>=gp.getScreenWidth()-260&&x<=gp.getScreenWidth()-200) {
					gp.confirmed();
				}
				else if(x>=gp.getScreenWidth()-140&&x<=gp.getScreenWidth()-80)
					gp.toggleConfirm();
		}
	}
	public void paintScreen(int x,int y) {
		if(y>=175&&y<=275) {
			int index=select(x,3,gp.characters.length);
			if(index!=99)
				gp.setSelectedChar(index);
		}
		else if(y>=400&&y<=500) {
			int index=select(x,4,gp.wallpaper.length);
			if(index!=99)
				gp.setSelectedWall(index);
		}
		else if(y>=34&&y<=54) 
			if(x>=250&&x<=270)
				gp.setSelectedChar(-1);
			else if(x>=gp.getScreenWidth()/2+240&&x<=gp.getScreenWidth()/2+260)
				gp.setSelectedWall(-1);
	}
	public int select(int x,int num,int length) {
		int w=-80;
		for(int i=0;i<length;i++) 
			if(checkAvailable(i,num)) {
				w+=135;
				if(x>=w&&x<=w+100)
					return i;
			}
		return 99;
	}
	public boolean checkAvailable(int i ,int num) {
		switch(num) {
		case 1:return !gp.isBoughtCharacter(i);
		case 2:return !gp.isBoughtWall(i);
		case 3:return gp.accChar(i);
		default:return gp.accWall(i);
		}
	}
//====================FolderScreen===================	
	public void FolderScreen(int x,int y){
		if(!gp.getInput()) {
		if(x>=gp.getTaskX())
			if(x<=gp.getTaskX()+20&&y>=gp.getTaskY()+55*gp.getTaskNum()&&y<=(gp.getTaskY()+55*gp.getTaskNum())+20)
				gp.createTask();
			else{
				boolean found=false;
				for(int i=0;i<gp.getTaskNum();i++) 
					if(y>=gp.getTaskY()+i*55&&y<=gp.getTaskY()+i*55+20) {
						if(x<=gp.getTaskX()+20)
							gp.toggleIsDone(i);
						else {
							gp.setSelectedTask(i);
							found=true;
						}
						break;
					}
				if(!found)gp.setSelectedTask(-1);
			}}
		else
			gettingInputF(x,y);
	}
	public void gettingInputF(int x,int y) {
		int w=gp.getScreenWidth();int h=gp.getScreenHeight()/2-250;
		if(y>=h+125&&y<=h+155) {
			if(x>=w+80-420&&x<=w+110-420) 
				gp.cal.changeMonth(-1);
			else if(x>=w+290-420&&x<=w+320-420)
				gp.cal.changeMonth(1);
			
		}
		else if(y>=h+50&&y<=h+90) {
		if(x>=w-331&&x<w-240) {
			gp.setSelectedInput(gp.getHoursInput());}
		else if(x>=w-154&&x<=w-20)
			gp.setSelectedInput(gp.getMinutesInput());
		}
		else if(y>=322&&x>=478)
			gp.cal.setSelected(x,y);
		else if(x<w-420)
			gp.setSelectedInput(gp.getNameInput());
		
		
	}
//====================HomeScreen===================	
	public void HomeScreen(int x,int y) {
		if(!gp.getInput()) {
			if(x<=80&&x>=0&&y<=gp.getLastFolderY2()&&y>=gp.getLastFolderY1())
				gp.createFolder();	
			
			else if(x<=80) {
				boolean found=false;
				for(int i=0;i<gp.getFolderNum()&&!found;i++) {
					if(y>=5+60*i&&y<=55+60*i) {
						if(i==gp.getCurrentFolder()) {
							gp.setOpenedFolder(i);
							gp.switchScreen(gp.getFolderScreen());
							break;
						}
						else{	
						gp.setCurrentFolder(i);
						found=true;}
					}
				}
				if(!found)gp.setCurrentFolder(-1);///////////////error hereee
			}
			else { 
				gp.setCurrentFolder(-1);
				if(y>=5&&y<=44&&x>=gp.getScreenWidth()-55&&x<=gp.getScreenWidth()-5) 
					gp.switchScreen(gp.getShopScreen());
				else if(y>=0&&y<=50&&x>=gp.getScreenWidth()-125&&x<=gp.getScreenWidth()-70)
					gp.switchScreen(gp.getPaintScreen());
				else if(y>=6&&y<=44&&x>=720&&x<=760)
					gp.switchScreen(gp.getCalScreen());
			}
			}
			else
				gettingInput(x,y);
	}
	public void gettingInput(int x,int y) {
		if(y>=gp.getFolderIconY()&&y<=gp.getFolderIconY()+50) {
			int val=gp.getFolderIconX();
			if(x>=val&&x<=val+80) 
				gp.setFolderIcon(0);
			else if(x>=val+120*1&&x<=(val+120*1)+80) {
				gp.setFolderIcon(1);
			}
			else if(x>=val+120*2&&x<=(val+120*2)+80) {
				gp.setFolderIcon(2);
			}	
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}
