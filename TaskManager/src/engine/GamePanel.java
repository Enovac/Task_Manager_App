package engine;
import ui.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import listeners.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
public class GamePanel extends JPanel implements Runnable,Serializable,WindowListener{
	//System
	private int screenWidth=900;
	private int screenHeight=700;
	transient private KeyL key;
	transient private MouseL mouse;
	private HomeScreen home;
	transient private Thread gameThread;
	private final int FPS=30 ;
	private TaskScreen taskScreen;
	private ShopScreen shop;
	private PaintScreen paintScreen;
	private CalScreen calScreen;
	public Cal cal;
	private String desktop=System.getProperty("user.home") + File.separator +"Desktop";
	private String savePath=desktop+File.separator+"CalData"+File.separator+"Save.txt";
	//Folders
	private ArrayList<TaskFolder> folderList;
	private int lastFolderY1=5;
	private int lastFolderY2=65;
	private final int MAX_FOLDER_Y=665;
	//Screens 
	private final int HOME_SCREEN=1;
	private final int FOLDER_SCREEN=2;
	private final int SHOP_SCREEN=3;
	private final int PAINT_SCREEN=4;
	private final int CAL_SCREEN=5;
	private int currentScreen=HOME_SCREEN;
	//input
	private boolean getInput;
	//shop and paint icons
	transient public BufferedImage[]characters;
	transient public BufferedImage[]wallpaper;
	private boolean charact;
	private int zeh2t;
	//debug
	boolean debug;
//======================Constructor==================	
	GamePanel(){
		setPreferredSize(new Dimension(screenWidth,screenHeight));
		setDoubleBuffered(true);
		setFocusable(true);
		setBackground(Color.white);
		cal=new Cal();
		shop=new ShopScreen(this);
		paintScreen=new PaintScreen(this);
		key=new KeyL(this);
		mouse=new MouseL(this);
		home=new HomeScreen(this);
		taskScreen=new TaskScreen(this);
		calScreen=new CalScreen(this);
		this.addMouseListener(mouse);
		this.addKeyListener(key);
		folderList=new ArrayList<TaskFolder>();
		loadImages();
		gameThread=new Thread(this);
		gameThread.start();
	}

//======================G&S========================== 
	public int getCalScreen() {
		return CAL_SCREEN;
	}
	public void setCalGap(int x,int y) {
		taskScreen.setCalGap(x, y);
	}
	public void toggleConfirm() {
		shop.toggleConfirm();
	}
	public boolean getConfirm() {
		return shop.getConfirm();
	}
	public boolean accChar(int x) {
		return paintScreen.accChar(x);
	}
	public boolean accWall(int x) {
		return paintScreen.accWall(x);
	}
	public void setSelectedChar(int x) {
		paintScreen.setSelectedChar(x);
		home.setSelectedChar(x);
	}
	public void setSelectedWall(int x) {
		paintScreen.setSelectedWall(x);
		home.setSelectedWall(x);
	}
	public boolean isBoughtCharacter(int x) {
		return shop.isBoughtCharacter(x);
	}
	public boolean isBoughtWall(int x) {
		return shop.isBoughtWall(x);
	}
	public void deleteTask() {
		taskScreen.deleteTask();
	}
	public void increaseMoney() {
		shop.increaseMoney();
	}
	public BufferedImage getCharacterImage(int x) {
		return characters[x];
	}
	public BufferedImage getWallPaperImage(int x) {
		return wallpaper[x];
	}
	public void setSelectedTask(int x) {
		taskScreen.setSelectedTask(x);
	}
	public int getSelectedTask() {
		return taskScreen.getSelectedTask();
	}
	public int getTaskX() {
		return taskScreen.getTaskX();
	}
	public int getTaskY() {
		return taskScreen.getTaskY();
	}
	public int getTaskNum() {
		return taskScreen.getTaskNum();
	}
	public int getCurrentScreen() {
		return currentScreen;
	}
	public int getHomeScreen() {
		return HOME_SCREEN;
	}
	public int getShopScreen() {
		return SHOP_SCREEN;
	}
	public int getPaintScreen() {
		return PAINT_SCREEN;
	}
	public int getFolderScreen() {
		return FOLDER_SCREEN;
	}
	public int getScreenWidth() {
		return screenWidth;
	}
	public int getScreenHeight() {
		return screenHeight;
	}
	public TaskFolder getFolder(int index) {
		return folderList.get(index);
	}
	public int getFolderNum() {
		return folderList.size();
	}
	public int getLastFolderY1() {
		return lastFolderY1;
	}
	public int getLastFolderY2() {
		return lastFolderY2;
	}
	public int getMaxFolderY() {
		return MAX_FOLDER_Y;
	}
	public boolean getInput() {
		return getInput;
	}
	public int getMoney(){
		return shop.getMoney();
	}
	public int getSelectedInput() {
		return taskScreen.getSelectedInput();
	}
	public void setSelectedInput(int x) {
		taskScreen.setSelectedInput(x);
	}
	public int getHoursInput() {
		return taskScreen.getHoursInput();
	}
	public int getNameInput() {
		return taskScreen.getNameInput();
	}
	public int getMinutesInput() {
		return taskScreen.getMinutesInput();
	}
	public int getDeadLineInput() {
		return taskScreen.getDeadLineInput();
	}
	public String getName() {
		return	key.getName();
	}
	public String getHours() {
		return	key.getHours();
	}
	public String getMinutes() {
		return	key.getMinutes();
	}
	public String getDeadLine() {
		return	key.getDeadline();
	}
	public int getFolderIconY() {
		return home.getFOLDER_ICON_Y();
	}
	public int getFolderIconX() {
		return home.getFOLDER_ICON_X();
	}
	public void setFolderIcon(int x) {
		home.setFolderIcon(x);
	}
	public void setCurrentFolder(int x) {
		home.setCurrentFolder(x);
	}
	public int getCurrentFolder() {
		return home.getCurrentFolder();
	}
	public void setOpenedFolder(int x) {
		taskScreen.setOpenedFolder(folderList.get(x));
	}
	public int getMaxTask() {
		return taskScreen.getMaxTask();
	}
	public void toggleIsDone(int x) {
		taskScreen.toggleIsDone(x);
	}
//======================Methods======================
	public void heart(BufferedImage f) {
		shop.heart=f;
	}
	public void waitToConfirm(boolean type,int index) {
		charact=type;
		zeh2t=index;
		toggleConfirm();
	}
	public void confirmed() {
		if(charact)
			buyCharacter(zeh2t);
		else
			buyWallpaper(zeh2t);
		toggleConfirm();
				
	}
	public boolean checkC(int x) {
		return shop.getMoney()>=shop.getCharacterPrice(x);
	}
	public boolean checkW(int x) {
		return	shop.getMoney()>=shop.getwallPrice(x);
	}
	public void buyCharacter(int  x) {
		shop.removeCharacter(x);
		shop.removeCharMoney(x);
		paintScreen.addCharacter(x);
		
	}
	public void buyWallpaper(int x) {

		shop.removeWall(x);
		shop.removeWallMoney(x);
		paintScreen.addWallpaper(x);
		
	}
	public void createFolder() {
		if(lastFolderY1!=MAX_FOLDER_Y) {
		toggleGetInput();
		}
	}
	public void folderCreated(String name) {
		folderList.add(new TaskFolder(name,home.getFolderIcon()));
		lastFolderY1+=60;
		lastFolderY2+=60;
	}
	public void toggleGetInput() {
		getInput=getInput?false:true;
	}
	public void deleteFolder() {
		folderList.remove(getCurrentFolder());
		setCurrentFolder(-1);
		lastFolderY1-=60;
		lastFolderY2-=60;
	}
	
	public void switchScreen(int x) {
		currentScreen=x;
		if(currentScreen==FOLDER_SCREEN) {
			switch(taskScreen.getOpenedFolder().getID()) {
			case 1:setBackground(new Color(112,173,173));break;
			case 2:setBackground(new Color(248, 200, 220));break;
			case 3:setBackground(new Color(255,241,146));break;
			}
			cal.resetCal();
		}
		else if(currentScreen==HOME_SCREEN||currentScreen==CAL_SCREEN)
			setBackground(Color.white);
		else
			setBackground(new Color(245,245,220));
		
			
	}
	
	public void createTask() {
		if(getTaskNum()<=getMaxTask()) {
			toggleGetInput();
			}
		
	}
	public void addTask(Task task) {
		taskScreen.addTask(task);
		taskScreen.setTotalTime();
	}

//======================Paint=========================
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d=(Graphics2D)g;
		if(currentScreen==HOME_SCREEN)
			home.paintHome(g2d);
		else if(currentScreen==FOLDER_SCREEN)
			taskScreen.paintFolderScreen(g2d);
		else if(currentScreen==SHOP_SCREEN)
			shop.paintShop(g2d);
		else if (currentScreen==CAL_SCREEN)
			calScreen.paintCalScreen(g2d);
		else
			paintScreen.paintScreen(g2d);
//else
		g2d.dispose();
	}
//===================Load===============================
	public void loadImages() {
		try {
			characters=new BufferedImage[4];
			wallpaper=new BufferedImage[6];
			characters[0]=(ImageIO.read(getClass().getResourceAsStream("/res/shop/egg.png")));
			characters[1]=(ImageIO.read(getClass().getResourceAsStream("/res/shop/FTimmy.png")));
			characters[2]=(ImageIO.read(getClass().getResourceAsStream("/res/shop/Timmy.png")));
			characters[3]=(ImageIO.read(getClass().getResourceAsStream("/res/shop/Nerd.png")));
			wallpaper[0]=(ImageIO.read(getClass().getResourceAsStream("/res/shop/bricks wallpaper.png")));
			wallpaper[1]=(ImageIO.read(getClass().getResourceAsStream("/res/shop/chrismas.png")));
			wallpaper[2]=(ImageIO.read(getClass().getResourceAsStream("/res/shop/dotted wallpaper.png")));
			wallpaper[3]=(ImageIO.read(getClass().getResourceAsStream("/res/shop/flower wallpaper.png")));
			wallpaper[4]=(ImageIO.read(getClass().getResourceAsStream("/res/shop/gradient wallpaper.png")));
			wallpaper[5]=(ImageIO.read(getClass().getResourceAsStream("/res/shop/mushroom wallpaper.png")));
		}catch(Exception ex) {ex.printStackTrace();}		
	}	
//=====================GameClock========================
	public void run() {
		double delta=0;
		double drawInterval=1000000000/FPS;
		long lastTime=System.nanoTime();
		long currentTime;
		
		long timer=0;
		int drawCount=0;
		while(gameThread!=null) {
			currentTime=System.nanoTime();
			
			delta+=(currentTime-lastTime)/drawInterval;
			timer+=currentTime-lastTime;
			lastTime=currentTime;
			if(delta>=1) {
				long before=System.nanoTime();
				repaint();
				delta--;
				drawCount++;
				long after=System.nanoTime();
//				if(false)
//					System.out.println(after-before);
 			}
			if(timer>=1000000000) {
//				System.out.println("FPS : "+drawCount);
				timer=0;drawCount=0;
			}
			
			
		}
	}
	
	
	
	
	
	public void saveFolders() {
		try {
			FileOutputStream fileStream = new FileOutputStream(new File(savePath));
			ObjectOutputStream os = new ObjectOutputStream(fileStream);
			os.writeObject(folderList);
			os.writeObject(shop.characters);
			os.writeObject(shop.wallpapers);
			os.writeObject(shop.money);
			os.writeObject(home.selectedChar);
			os.writeObject(home.selectedWall);
			os.writeObject(paintScreen.characters);
			os.writeObject(paintScreen.wallpapers);
			os.writeObject(paintScreen.selectedChar);
			os.writeObject(paintScreen.selectedWall);
			os.writeObject(lastFolderY1);
			os.writeObject(lastFolderY2);
			os.close();
		}catch(Exception ex) {ex.printStackTrace();}
	}
	
	public void loadFolders() {
		try {
			  File obj=new File(desktop+File.separator+"CalData");
		      obj.mkdir(); 
		      File myObj = new File(savePath);
		      
		      if (myObj.createNewFile()) {
		        System.out.println("File created: " + myObj.getName());
		      } else {
		        System.out.println("File already exists.");
		        try {
					FileInputStream fileStream = new FileInputStream(new File(savePath));
					ObjectInputStream os = new ObjectInputStream(fileStream);
					folderList=(ArrayList<TaskFolder>)os.readObject();
					shop.characters=(int[])os.readObject();
					shop.wallpapers=(int[])os.readObject();	
					shop.money=(int)os.readObject();
					home.selectedChar=(int)os.readObject();
					home.selectedWall=(int)os.readObject();
					paintScreen.characters=(int[])os.readObject();
					paintScreen.wallpapers=(int[])os.readObject();
					paintScreen.selectedChar=(int)os.readObject();
					paintScreen.selectedWall=(int)os.readObject();
					lastFolderY1=(int)os.readObject();
					lastFolderY2=(int)os.readObject();
					os.close();
				}catch(Exception ex) {}
		        
		        
		        
		      }
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
		
		
	}	
	
@Override
public void windowOpened(WindowEvent e) {
	loadFolders();
}
@Override
public void windowClosing(WindowEvent e) {
	saveFolders();
	System.exit(0);
}
@Override
public void windowClosed(WindowEvent e) {
}
@Override
public void windowIconified(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowDeiconified(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowActivated(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowDeactivated(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
	}
	


