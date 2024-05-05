package screens;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.*;
import engine.*;
import java.awt.*;
import tasks.*;
public class HomeScreen implements Serializable{
//Miscellaneous
	GamePanel gp;
//Fonts
	private  final Font MAIN_FONT=new Font("Arial",Font.BOLD,20);
	private  final Font FOLDER_FONT=new Font("Arial",Font.PLAIN,15);
//Folder
	private ArrayList<TaskFolder> folderList;
//Images
	transient private BufferedImage[]folderPic;
	transient private BufferedImage trolley;
	transient private BufferedImage paint;
	transient private BufferedImage cal;
//Objects X,Y
	private final int INPUT_FOLDER_ICON_Y;
	private final int INPUT_FOLDER_ICON_X;
	public final int INPUT_FOLDER_GAP=120;
	public static final int FOLDER_GAP=60;//Note folder height 50,so Gap 10
	private static final int MAX_FOLDER_Y=665;
	private int lastFolderY1=5;//lastCreatedFolderY
	public static final int trolleyX=GamePanel.SCREEN_WIDTH-55;
	public static final int trolleyY=5;
	public static final int trolleyWidth=50;
	public static final int trolleyHeight=39;
	public static final int calX=GamePanel.SCREEN_WIDTH-180;
	public static final int calY=5;
	public static final int calWidth=40;
	public static final int calHeight=40;
	public static final int paintX=GamePanel.SCREEN_WIDTH-125;
	public static final int paintY=-5;
	public static final int paintWidth=55;
	public static final int paintHeight=55;
	public static final int heartX=GamePanel.SCREEN_WIDTH/2-45;
	public static final int heartY=0;
	public static final int heartWidth=50;
	public static final int heartHeight=50;
//UI
	private int selectedCharacter=-1;
	private int selectedWallpaper=-1;
	private int selectedInputFolderIcon=1;
	private int selectedFolder=-1;

//=====================Constructor======================	
	public HomeScreen(GamePanel gp) {
		this.gp=gp;
		INPUT_FOLDER_ICON_Y=GamePanel.SCREEN_HEIGHT/2-40;
		INPUT_FOLDER_ICON_X=GamePanel.SCREEN_WIDTH/2-160;
		loadImages();
		folderList=new ArrayList<TaskFolder>();
	}
//=====================Methods==========================
	public void setSelectedFolder(int i){
		if(i==selectedFolder)
			;//Open Fold.er
		else
			selectedFolder=i;
	}
//=====================Paint============================
	public void paintHomeScreen(Graphics2D g2d) {
			if(selectedWallpaper!=-1)
				g2d.drawImage(GamePanel.wallpapers[selectedWallpaper],0,0,null);//50 39
			
			if(selectedCharacter!=-1) {
				switch(selectedCharacter) {
				case 0:g2d.drawImage(GamePanel.characters[selectedCharacter],GamePanel.SCREEN_WIDTH/2-130,GamePanel.SCREEN_HEIGHT/2+90,null);break;
				case 1:g2d.drawImage(GamePanel.characters[selectedCharacter],GamePanel.SCREEN_WIDTH/2-150,GamePanel.SCREEN_HEIGHT/2+50,null);break;
				case 2:g2d.drawImage(GamePanel.characters[selectedCharacter],GamePanel.SCREEN_WIDTH/2-150,GamePanel.SCREEN_HEIGHT/2+40,null);break;
				case 3:g2d.drawImage(GamePanel.characters[selectedCharacter],GamePanel.SCREEN_WIDTH/2-150,GamePanel.SCREEN_HEIGHT/2+60,null);
				}
			}
			g2d.drawImage(trolley,trolleyX,trolleyY,null);
			g2d.drawImage(paint,paintX,paintY,null);
			g2d.drawImage(cal,calX,calY,null);
			g2d.drawImage(GamePanel.heart,heartX,heartY,null);
			g2d.setFont(MAIN_FONT);
			g2d.drawString("x"+gp.getMoney(),GamePanel.SCREEN_WIDTH/2,27);
			paintFolders(g2d);
			if(gp.isGettingInput())
				paintInput(g2d);
//			g2d.setColor(Color.gray);////////////////////delete<<<
		}
	private void paintFolders(Graphics2D g2d) {
		int folderY=5;//first 5>55  second 65>>115  gap is 10  y(x)=-55+60*(x+1)
		int numOfFolders=folderList.size();
		g2d.setColor(Color.black);
		FontMetrics fm=g2d.getFontMetrics();
		g2d.setFont(FOLDER_FONT);
		for(int i=0;i<numOfFolders;i++) {
			int id=getFolderID(i);
			String name=getFolderName(i);
			g2d.drawImage(folderPic[id],0,folderY+FOLDER_GAP*i,null);
			int stringWidth=(80-fm.stringWidth(name)-4)/2;
			g2d.drawString(name,stringWidth,folderY+FOLDER_GAP*i+35);//35 is text padding
		}
		if(selectedFolder!=-1) {
			g2d.setFont(MAIN_FONT);
			g2d.drawString("<<",85, folderY+FOLDER_GAP*selectedFolder+35);//85 is << padding,35//
		}
		if(lastFolderY1!=MAX_FOLDER_Y) {
			g2d.drawImage(folderPic[0],0,folderY+FOLDER_GAP*numOfFolders,null);
			}
	}
	private void paintInput(Graphics2D g2d) {
		int x=GamePanel.SCREEN_WIDTH/2-200;int y=GamePanel.SCREEN_HEIGHT/2-200;
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.9f));
		g2d.setColor(new Color(245,245,220));
		g2d.fillRoundRect(x,y,400,250,25,25);
		g2d.setColor(Color.BLACK);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
		g2d.setStroke(new BasicStroke(3));
		g2d.drawRoundRect(x,y,400,250,25,25);
		g2d.setFont(MAIN_FONT);
		g2d.drawString("Enter Folder Name Max 5 Characters :",x+20,y+40);
//		g2d.drawString(">> "+getzaname>>>>>,x+20,y+70);//get name is the name u type
		g2d.drawString("Pick Folder Icon : ",x+20,y+110);
		for(int i=0;i<3;i++)
		g2d.drawImage(folderPic[i+1],INPUT_FOLDER_ICON_X+INPUT_FOLDER_GAP*i,INPUT_FOLDER_ICON_Y,null);
		g2d.setColor(Color.black);
		g2d.drawRoundRect(INPUT_FOLDER_ICON_X+INPUT_FOLDER_GAP *selectedInputFolderIcon-3,INPUT_FOLDER_ICON_Y-3,85,55,10,10);
	}
//=====================G&S==============================	
	public int getFolderID(int i) {
		return 5;
	}
	public String getFolderName(int i) {
		return "";
	}
	public int getNumOfFolders() {
		return folderList.size();
	}
//=====================Load=============================
public void loadImages() {
	folderPic=new BufferedImage[4];
	try {
		folderPic[0]=(ImageIO.read(getClass().getResourceAsStream("/res/folder/ghostFolder.png")));
		folderPic[1]=(ImageIO.read(getClass().getResourceAsStream("/res/folder/blueFolder.png")));
		folderPic[2]=(ImageIO.read(getClass().getResourceAsStream("/res/folder/pinkFolder.png")));
		folderPic[3]=(ImageIO.read(getClass().getResourceAsStream("/res/folder/yellowFolder.png")));
		trolley=(ImageIO.read(getClass().getResourceAsStream("/res/util/trolley.png")));
		paint=(ImageIO.read(getClass().getResourceAsStream("/res/util/style.png")));
		cal=ImageIO.read(getClass().getResourceAsStream("/res/util/calen.png"));
		System.out.println("loaded");
	}catch(Exception ex) {ex.printStackTrace();}

}

}
