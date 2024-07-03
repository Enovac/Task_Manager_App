package ui;
import java.awt.*;
import engine.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.*;

import javax.imageio.ImageIO;
public class PaintScreen implements Serializable{
	public int [] wallpapers;
	public int [] characters;
	private  final Font defFont=new Font("Arial",Font.BOLD,30);
	private  final Font dFont=new Font("Arial",Font.BOLD,25);
	public int selectedWall=-1;
	public int selectedChar=-1;
	GamePanel gp;
	public PaintScreen(GamePanel gp) {
		this.gp=gp;
		wallpapers= new int[6];
		characters=new int[4];
	}
	public void addCharacter(int x) {
		characters[x]=1;
	}
	public void addWallpaper(int x) {
		wallpapers[x]=1;
	}
	public void setSelectedChar(int x) {
		selectedChar=x;
	}
	public void setSelectedWall(int x) {
		selectedWall=x;
	}
	public boolean accChar(int x) {
		return characters[x]==1?true:false;
	}
	public boolean accWall(int x) {
		return wallpapers[x]==1?true:false;
	}
	public void paintScreen(Graphics2D g2d) {
		g2d.setFont(dFont);
		g2d.setStroke(new BasicStroke(3));
		g2d.drawString("Remove Character",10,50);
		g2d.drawRect(250,34, 20, 20);
		if(selectedChar==-1)
		g2d.fillRect(254,38, 12, 12);
		g2d.drawString("Remove Wallpaper",gp.getScreenWidth()/2,50);
		g2d.drawRect(gp.getScreenWidth()/2+240,34, 20, 20);
		if(selectedWall==-1)
		g2d.fillRect(gp.getScreenWidth()/2+244,38, 12, 12);
		g2d.setFont(defFont);
		g2d.drawString("Select Character:",10,145);//Y>175 Y<275
		int x=-80;
		for(int i=0;i<characters.length;i++) 
			if(characters[i]==1) {
				if(i==0)
					g2d.drawImage(gp.getCharacterImage(i),x+=135,190,null);
				else
					g2d.drawImage(gp.getCharacterImage(i),x+=135,175,null);
				if(i==selectedChar) {
					if(i==0)
					g2d.drawRect(x-10, 165,110,120);
					else 
						g2d.drawRect(x-10, 165,120,120);
}
			}
		g2d.drawString("Select Wallpaper:",10,360);
		 x=-80;
		for(int i=0;i<wallpapers.length;i++) 
			if(wallpapers[i]==1) { 
				g2d.drawImage(gp.getWallPaperImage(i),x+=135,400,null);
				if(i==selectedWall)
					g2d.drawRect(x-10, 390,120,120);
			}
	}
	
}
