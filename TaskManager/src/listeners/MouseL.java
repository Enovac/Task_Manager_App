package listeners;

import engine.GamePanel;
import engine.TaskLauncher;
import screens.*;

import java.awt.*;
import java.awt.event.*;
public class MouseL implements MouseListener{
	
	
	
//=====================Constructor======================	
//=====================Methods==========================	
	public void mousePressed(MouseEvent e) {
		int x=e.getX(); int y=e.getY();GamePanel gp=TaskLauncher.gp;
		if(TaskLauncher.gp.getCurrentScreen()==gp.HOME_SCREEN)
			homeScreen(x,y,gp);
//		else if(TaskLauncher.gp.getCurrentScreen()==gp.FOLDER_SCREEN)
//			FolderScreen(x,y);
//		else if(TaskLauncher.gp.getCurrentScreen()==gp.SHOP_SCREENP)
//			shopScreen(x, y);
//		else
//			paintScreen(x,y);
	}
//=====================HomeScreen==========================	
	public void homeScreen(int x,int y,GamePanel gp) {
		//Incase in !Getting input
		if(x>80) {
			if(x>=HomeScreen.trolleyX&&x<=HomeScreen.trolleyX+HomeScreen.trolleyWidth
			&&y>=HomeScreen.trolleyY&&y<=HomeScreen.trolleyY+HomeScreen.trolleyHeight)
				;
			else if(x>=HomeScreen.calX&&x<=+HomeScreen.calX+HomeScreen.calWidth
			&&y>=HomeScreen.calY&&y<=HomeScreen.calY+HomeScreen.calHeight)
				;
			else if(x>=HomeScreen.paintX&&x<=+HomeScreen.paintX+HomeScreen.paintWidth
					&&y>=HomeScreen.paintY&&y<=HomeScreen.paintY+HomeScreen.paintHeight)
				;	
			gp.setSelectedFolder(-1);
		}
		else {
			if(y/HomeScreen.FOLDER_GAP<=gp.getNumOfFolders())
				gp.setSelectedFolder(y/HomeScreen.FOLDER_GAP);
			else
				gp.setSelectedFolder(-1);

		}
		
	}

	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
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

}
