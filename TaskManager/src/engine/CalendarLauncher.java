package engine;
import java.awt.Image;
import java.awt.event.*;
import javax.swing.*;
public class CalendarLauncher extends JFrame{
	public GamePanel gp;
	public static void main(String[] args) {
		new CalendarLauncher().launch();
	}
	void launch() {
		Image frameicon=new ImageIcon(getClass().getResource("/res/util/japan.png")).getImage();
		setIconImage(frameicon);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		gp=new GamePanel();
		add(gp);
		addWindowListener(gp);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
