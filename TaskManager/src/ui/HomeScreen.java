package ui;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import engine.*;
public class HomeScreen implements Serializable{
//System
	private GamePanel gp;
	private  final Font defFont=new Font("Arial",Font.BOLD,20);
	private  final Font tFont=new Font("Arial",Font.PLAIN,15);
//Choosing Folder Icon	
	transient private BufferedImage[]folderPic;
	transient private BufferedImage trolley;
	transient private BufferedImage paint;
	transient private BufferedImage heart;
	transient private BufferedImage cal;
	private final int FOLDER_ICON_Y;
	private final int FOLDER_ICON_X;
	private int folderIcon=1;
//Rest	
	private int folderY;
	private int currentFolder=-1;
	//Images
	transient public BufferedImage[]characters;
	transient public BufferedImage[]wallpaper;
	public int selectedChar=-1;
	public int selectedWall=-1;
//======================Constructor====================
	public HomeScreen(GamePanel gp) {
		this.gp=gp;
		FOLDER_ICON_Y=gp.getScreenHeight()/2-40;
		FOLDER_ICON_X=gp.getScreenWidth()/2-160;
		loadImages();
	}
//======================G&S========================== 
	public void setSelectedChar(int x) {
		selectedChar=x;
	}
	public void setSelectedWall(int x) {
		selectedWall=x;
	}
	public int getFolderIcon() {
		return folderIcon;
	}
	public int getFOLDER_ICON_Y() {
		return FOLDER_ICON_Y;
	}
	public int getFOLDER_ICON_X() {
		return FOLDER_ICON_X;
	}
	public void setCurrentFolder(int x) {
		currentFolder=x;
	}
	public void setFolderIcon(int x) {
		folderIcon=x;
	}
	public int getCurrentFolder() {
		return currentFolder;
	}
//======================Methods======================
//======================Paint========================
	public void paintHome(Graphics2D g2d) {
		if(selectedWall!=-1)
		g2d.drawImage(wallpaper[selectedWall],0,0,null);//50 39
		g2d.drawImage(trolley,gp.getScreenWidth()-55,5,null);//50 39
		g2d.drawImage(paint,gp.getScreenWidth()-125,-5,null);//55 55
		g2d.drawImage(cal,gp.getScreenWidth()-180,5,null);//55 55
		g2d.drawImage(heart,gp.getScreenWidth()/2-45,0,null);//50 50
		g2d.setFont(defFont);
		g2d.drawString("x"+gp.getMoney(),gp.getScreenWidth()/2,27);
		paintFolder(g2d);
		if(gp.getInput())
			paintInput(g2d);
		g2d.setColor(Color.gray);
		if(selectedChar!=-1) {
			switch(selectedChar) {
			case 0:g2d.drawImage(characters[selectedChar],gp.getScreenWidth()/2-130,gp.getScreenHeight()/2+90,null);break;
			case 1:g2d.drawImage(characters[selectedChar],gp.getScreenWidth()/2-150,gp.getScreenHeight()/2+50,null);break;
			case 2:	g2d.drawImage(characters[selectedChar],gp.getScreenWidth()/2-150,gp.getScreenHeight()/2+40,null);break;
			case 3:g2d.drawImage(characters[selectedChar],gp.getScreenWidth()/2-150,gp.getScreenHeight()/2+60,null);//50 39
			}
		}	
	}
	public void paintFolder(Graphics2D g2d) {
		folderY=5;//first 5>55  second 65>>115  gap is 10  y(x)=-55+60*(x+1)
		for(int i=0;i<gp.getFolderNum();i++) {
			g2d.setFont(tFont);
			int id=gp.getFolder(i).getID();
			g2d.drawImage(folderPic[id],0,folderY+60*i,null);
			FontMetrics fm=g2d.getFontMetrics();
			String name=gp.getFolder(i).getName();
			int stringWidth=(80-fm.stringWidth(name)-4)/2;
			g2d.setColor(Color.black);
			g2d.drawString(name,stringWidth,folderY+60*i+35);
			if(i==currentFolder) {
				g2d.setFont(defFont);
				g2d.drawString("<<",85, folderY+60*i+35);}

		}
		if(gp.getLastFolderY1()!=gp.getMaxFolderY()) {
		g2d.drawImage(folderPic[0],0,folderY+60*gp.getFolderNum(),null);
		}
//		g2d.drawRect(90,10, 200, folderY+60*3);//90,10
//		g2d.drawRect(90, folderY+60*6, 200, 300);
	}
	public void paintInput(Graphics2D g2d) {
		int x=gp.getScreenWidth()/2-200;int y=gp.getScreenHeight()/2-200;
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.9f));
		g2d.setColor(new Color(245,245,220));
		g2d.fillRoundRect(x,y,400,250,25,25);
		g2d.setColor(Color.BLACK);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
		g2d.setStroke(new BasicStroke(3));
		g2d.drawRoundRect(x,y,400,250,25,25);
		g2d.setFont(defFont);
		g2d.drawString("Enter Folder Name Max 5 Characters :",x+20,y+40);
		g2d.drawString(">> "+gp.getName(),x+20,y+70);
		g2d.drawString("Pick Folder Icon : ",x+20,y+110);
		g2d.drawImage(folderPic[1],FOLDER_ICON_X+120*0,FOLDER_ICON_Y,null);
		g2d.drawImage(folderPic[2],FOLDER_ICON_X+120*1,FOLDER_ICON_Y,null);
		g2d.drawImage(folderPic[3],FOLDER_ICON_X+120*2,FOLDER_ICON_Y,null);
		g2d.setColor(Color.black);
		g2d.drawRoundRect(FOLDER_ICON_X+120*folderIcon-3,FOLDER_ICON_Y-3,85,55,10,10);
	}
//======================Load==========================
	public void loadImages() {
		folderPic=new BufferedImage[4];
		try {
			folderPic[0]=(ImageIO.read(getClass().getResourceAsStream("/res/folder/ghostFolder.png")));
			folderPic[1]=(ImageIO.read(getClass().getResourceAsStream("/res/folder/blueFolder.png")));
			folderPic[2]=(ImageIO.read(getClass().getResourceAsStream("/res/folder/pinkFolder.png")));
			folderPic[3]=(ImageIO.read(getClass().getResourceAsStream("/res/folder/yellowFolder.png")));
			trolley=(ImageIO.read(getClass().getResourceAsStream("/res/util/trolley.png")));
			paint=(ImageIO.read(getClass().getResourceAsStream("/res/util/style.png")));
			heart=ImageIO.read(getClass().getResourceAsStream("/res/util/heart.png"));
			cal=ImageIO.read(getClass().getResourceAsStream("/res/util/calen.png"));
			gp.heart(heart);
			characters=new BufferedImage[4];
			wallpaper=new BufferedImage[6];
			characters[0]=(ImageIO.read(getClass().getResourceAsStream("/res/character/Egg.png")));
			characters[1]=(ImageIO.read(getClass().getResourceAsStream("/res/character/FTimmy.png")));
			characters[2]=(ImageIO.read(getClass().getResourceAsStream("/res/character/Timmy.png")));
			characters[3]=(ImageIO.read(getClass().getResourceAsStream("/res/character/Nerd.png")));
			wallpaper[0]=(ImageIO.read(getClass().getResourceAsStream("/res/wallpaper/bricks wallpaper.png")));
			wallpaper[1]=(ImageIO.read(getClass().getResourceAsStream("/res/wallpaper/chrismas.png")));
			wallpaper[2]=(ImageIO.read(getClass().getResourceAsStream("/res/wallpaper/dotted wallpaper.png")));
			wallpaper[3]=(ImageIO.read(getClass().getResourceAsStream("/res/wallpaper/flower wallpaper.png")));
			wallpaper[4]=(ImageIO.read(getClass().getResourceAsStream("/res/wallpaper/gradient wallpaper.png")));
			wallpaper[5]=(ImageIO.read(getClass().getResourceAsStream("/res/wallpaper/mushroom wallpaper.png")));

		}catch(Exception ex) {ex.printStackTrace();}
		
	}
}
