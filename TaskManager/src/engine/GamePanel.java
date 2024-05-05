package engine;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import listeners.*;
import screens.*;
public class GamePanel extends JPanel implements Runnable,Serializable,WindowListener{
//System
	public static final int SCREEN_WIDTH=900;
	public static final int SCREEN_HEIGHT=700;
	private static final int FPS=30;
	private static transient Thread gameThread;
	public static boolean debug;
	private static boolean gettingInput;
//Screens 
	public static  final int HOME_SCREEN=1;
	public static final int FOLDER_SCREEN=2;
	public static final int SHOP_SCREEN=3;
	public static final int PAINT_SCREEN=4;
	public static final int CAL_SCREEN=5;
	private static int currentScreen=HOME_SCREEN;
	private HomeScreen homeScreen;
	private ShopScreen shopScreen;
	private PaintScreen paintScreen;

//Listeners
	private static MouseL mouse;
	private static KeyL key;
//Images
	transient public static BufferedImage[]characters;
	transient public static BufferedImage[]wallpapers;
	transient public static BufferedImage heart;
//Bought?
	private int money=0;
	private boolean[] boughtWallpapers;
	private boolean[] boughtCharacters;
	
//=====================Constructor======================		
	GamePanel(){
		setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		setDoubleBuffered(true);
		setFocusable(true);
		setBackground(Color.white);
		//Listeners
		addMouseListener(mouse=new MouseL());
		addKeyListener(key=new KeyL());
		//Screens
		homeScreen=new HomeScreen(this);
		shopScreen=new ShopScreen(this);
		paintScreen=new PaintScreen();
		//Thread
		loadImages();
		gameThread=new Thread(this);
		gameThread.start();
	}
//=====================Methods==========================
	public static void switchScreen(int x) {
		currentScreen=x;
	}
	public void deductMoney(int x) {
		money-=x;
	}
	public void increaseMoney() {
		money++;
	}
//=====================Paint============================
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d=(Graphics2D)g;
		if(currentScreen==HOME_SCREEN)
			homeScreen.paintHomeScreen(g2d);
		else if(currentScreen==FOLDER_SCREEN)
			;
		else if(currentScreen==SHOP_SCREEN)
			shopScreen.paintShop(g2d);
		else if (currentScreen==CAL_SCREEN)
			;
		else
			paintScreen.paintPaintScreen(g2d);
		g2d.dispose();
	}
//=====================G&S==============================
	public boolean isGettingInput() {
		return gettingInput;
	}
	public int getMoney() {
		return money;
	}
	public boolean isCharacterBought(int i) {
		return boughtCharacters[i];
	}
	public boolean isWallpaperBought(int i) {
		return boughtWallpapers[i];
	}
	public void setBoughtWallpaper(int i) {
		boughtWallpapers[i]=true;
	}
	public void setBoughtCharacter(int i) {
		boughtCharacters[i]=true;
	}
	//get name???
	public int getCurrentScreen() {
		return currentScreen;
	}
	public int getNumOfFolders() {
		return homeScreen.getNumOfFolders();
	}
	public void setSelectedFolder(int i){
	homeScreen.setSelectedFolder(i);
	}
//=====================Load=============================
	public void loadImages() {
		try {
			heart=ImageIO.read(getClass().getResourceAsStream("/res/util/heart.png"));
			characters=new BufferedImage[4];
			wallpapers=new BufferedImage[6];
			characters[0]=(ImageIO.read(getClass().getResourceAsStream("/res/character/Egg.png")));
			characters[1]=(ImageIO.read(getClass().getResourceAsStream("/res/character/FTimmy.png")));
			characters[2]=(ImageIO.read(getClass().getResourceAsStream("/res/character/Timmy.png")));
			characters[3]=(ImageIO.read(getClass().getResourceAsStream("/res/character/Nerd.png")));
			wallpapers[0]=(ImageIO.read(getClass().getResourceAsStream("/res/wallpaper/bricks wallpaper.png")));
			wallpapers[1]=(ImageIO.read(getClass().getResourceAsStream("/res/wallpaper/chrismas.png")));
			wallpapers[2]=(ImageIO.read(getClass().getResourceAsStream("/res/wallpaper/dotted wallpaper.png")));
			wallpapers[3]=(ImageIO.read(getClass().getResourceAsStream("/res/wallpaper/flower wallpaper.png")));
			wallpapers[4]=(ImageIO.read(getClass().getResourceAsStream("/res/wallpaper/gradient wallpaper.png")));
			wallpapers[5]=(ImageIO.read(getClass().getResourceAsStream("/res/wallpaper/mushroom wallpaper.png")));

		}catch(Exception ex) {ex.printStackTrace();}
		
	}
//=====================Save============================= 
//=====================GameClock========================	
	public void run() {
		double delta=0;
		double drawInterval=1000000000/FPS;
		long lastTime=System.nanoTime();
		long currentTime;
//		long timer=0; (Enable for FPS CHECK)
		int drawCount=0;
		while(gameThread!=null) {
			currentTime=System.nanoTime();
			delta+=(currentTime-lastTime)/drawInterval;
//			timer+=currentTime-lastTime; (Enable for FPS CHECK)
			lastTime=currentTime;
			if(delta>=1) {
				long before=System.nanoTime();
				repaint();
				delta--;
				drawCount++;
				long after=System.nanoTime();
				if(debug) 
					System.out.println(after-before);
 			}
//			if(timer>=1000000000) {   (Enable for FPS CHECK)
// 				System.out.println("FPS : "+drawCount);
//				timer=0;drawCount=0; }	
		}
	}
	
//=====================WindowControl====================
	@Override
	public void windowOpened(WindowEvent e) {
//		loadFolders();
	}
	@Override
	public void windowClosing(WindowEvent e) {
//		saveFolders();
		System.exit(0);
	}
	@Override
	public void windowClosed(WindowEvent e) {
	}
	@Override
	public void windowIconified(WindowEvent e) {
	}
	@Override
	public void windowDeiconified(WindowEvent e) {	
	}
	@Override
	public void windowActivated(WindowEvent e) {	
	}
	@Override
	public void windowDeactivated(WindowEvent e) {
	}
}
