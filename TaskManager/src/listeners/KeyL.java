package listeners;
import java.awt.event.*;
import engine.*;
import ui.Cal;
public class KeyL implements KeyListener{
	private StringBuilder name=new StringBuilder();
	private StringBuilder hours=new StringBuilder();
	private StringBuilder minutes=new StringBuilder();
	private StringBuilder deadLine=new StringBuilder();
	private GamePanel gp;
	public KeyL(GamePanel gp) {
		this.gp=gp;
	}
//G&S
	public String getName() {
		return name.toString();
	}
	public String getHours() {
		return hours.toString();
	}
	public String getMinutes() {
		return minutes.toString();
	}
	public String getDeadline() {
		return deadLine.toString();
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(gp.getCurrentScreen()==gp.getHomeScreen())
			HomeScreen(e);
		else if(gp.getCurrentScreen()==gp.getFolderScreen())
			FolderScreen(e);
		else if(e.getKeyCode()==KeyEvent.VK_ESCAPE&&!gp.getConfirm())
			gp.switchScreen(gp.getHomeScreen());
	}
//====================HomeScreen===================	
	public void HomeScreen(KeyEvent e) {
			if(gp.getInput()) 			
				switch(e.getKeyCode()) {
				case KeyEvent.VK_ENTER:gp.folderCreated(name.toString());
				case KeyEvent.VK_ESCAPE: name.delete(0, name.length()); gp.toggleGetInput();break;
				default:if(name.length()<5||e.getKeyCode()==KeyEvent.VK_BACK_SPACE)getInput(e,name);
				}
			else 
				if(gp.getCurrentFolder()!=-1&&e.getKeyCode()==KeyEvent.VK_X)
					gp.deleteFolder();
				else if(e.getKeyCode()==KeyEvent.VK_D);
//					gp.toggleDebug();		
	}
//====================FolderScreen===================	
	public void FolderScreen(KeyEvent e) {
		if(gp.getInput()) {/////////deelete priorirty and stuffx`
			switch(e.getKeyCode()) {
			case KeyEvent.VK_ENTER:gp.addTask(new Task(name.toString(),hours.toString(),minutes.toString(),gp.cal.getCDay(),gp.cal.getCMonth(),gp.cal.getCYear()));
			case KeyEvent.VK_ESCAPE:
			name.delete(0, name.length());
			hours.delete(0, hours.length());
			minutes.delete(0, minutes.length());
			deadLine.delete(0,deadLine.length());
			gp.cal.resetCal();
			gp.toggleGetInput();break;
			default:
				if(gp.getSelectedInput()==gp.getHoursInput()&&(hours.length()<2||e.getKeyCode()==KeyEvent.VK_BACK_SPACE))
					getInput(e,hours);
				else if(gp.getSelectedInput()==gp.getMinutesInput()&&(minutes.length()<2||e.getKeyCode()==KeyEvent.VK_BACK_SPACE))
					getInput(e,minutes);
				else if(gp.getSelectedInput()==gp.getNameInput())
					getInput(e,name);
				else if(gp.getSelectedInput()==gp.getDeadLineInput())
					getInput(e,deadLine);
			}
		}
		else if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
			gp.switchScreen(gp.getHomeScreen());
		else if(e.getKeyCode()==KeyEvent.VK_X&&gp.getSelectedTask()!=-1)
			gp.deleteTask();
		else if(e.getKeyCode()==KeyEvent.VK_D);
//			gp.toggleDebug();;
	}
//===================Getting Input===================
	public void getInput(KeyEvent e,StringBuilder test) {
		char c=(char)e.getKeyChar();
			
		if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE)
			try {
				test.deleteCharAt(test.length()-1);}
			catch(Exception ex) {}
		 else if(c>=32&&c<127) 
			 if(gp.getCurrentScreen()==gp.getHomeScreen()||(gp.getSelectedInput()!=gp.getHoursInput()&&gp.getSelectedInput()!=gp.getMinutesInput())
			    ||(c>='0'&&c<='9'))
			 test.append(c);	 
	}
	@Override
	public void keyReleased(KeyEvent e) {		
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}
}
