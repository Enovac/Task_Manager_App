package ui;
import java.awt.*;
import engine.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.*;

import javax.imageio.ImageIO;
public class ShopScreen implements Serializable{
	public int [] wallpapers;
	private int [] wallPrice;
	public int [] characters;
	private int [] characterPrice;
	private  final Font defFont=new Font("Arial",Font.BOLD,30);
	transient public  BufferedImage heart;
	transient public BufferedImage shopImage;
	public int money=65;
	GamePanel gp;
	private boolean confirm;
	public ShopScreen(GamePanel gp) {
		this.gp=gp;
		wallpapers= new int[6];
		characters=new int[4];
		characterPrice=new int[]{40,20,10,25};
		wallPrice=new int[] {5,40,10,20,50,30};
		try {
			shopImage=(ImageIO.read(getClass().getResourceAsStream("/res/shop/shop(1).png")));
		}catch(Exception ex) {}
	}
	public int getMoney() {
		return money;
	}
	public void removeCharMoney(int x) {
		money-=characterPrice[x];
	}
	public void removeWallMoney(int x) {
		money-=wallPrice[x];

	}
	public void removeCharacter(int x) {
		characters[x]=-1;
	}
	public void removeWall(int x) {
		wallpapers[x]=-1;
	}
	public boolean isBoughtCharacter(int x) {
		return characters[x]==-1?true:false;
	}
	public boolean isBoughtWall(int x) {
		return wallpapers[x]==-1?true:false;
	}
	public int getCharacterPrice(int x) {
		return characterPrice[x];
	}
	public int getwallPrice(int x) {
		return wallPrice[x];
	}
	public void increaseMoney() {
		money++;
	}
	public void toggleConfirm() {
		confirm=confirm?false:true;
	}
	public boolean getConfirm() {
		return confirm;
	}
	public void paintConfirm(Graphics2D g2d) {
		int x=gp.getScreenWidth()-300;int y=gp.getScreenHeight()/2-130;
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.6f));
		g2d.setColor(Color.red);
		g2d.fillRoundRect(x,y,250,130,25,25);
		g2d.setColor(Color.BLACK);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
		g2d.setStroke(new BasicStroke(3));
		g2d.drawRoundRect(x,y,250,130,25,25);
		g2d.setFont(defFont);
		g2d.drawString("Buy item?", x+55, y+30);
		g2d.drawString("Yes!", x+40, y+80);
		g2d.drawString("NO!", x+160, y+80);
		
	}
	public void paintShop(Graphics2D g2d) {
		if(confirm)
			paintConfirm(g2d);
		g2d.drawImage(shopImage,0,0,null);
		g2d.drawImage(heart,gp.getScreenWidth()-160,145,null);//50 50
		g2d.setFont(defFont);
		g2d.drawString("x"+gp.getMoney(),gp.getScreenWidth()-110,175);
		g2d.setFont(defFont);
		g2d.drawString("Buy Characters:",55,165);//Y>175 Y<275
		int x=-80;
		for(int i=0;i<characters.length;i++) 
			if(characters[i]!=-1) { 
				if(i==0)
					g2d.drawImage(gp.getCharacterImage(i),x+=135,190,null);
				else
				g2d.drawImage(gp.getCharacterImage(i),x+=135,175,null);
			g2d.drawImage(heart,x-5,300,null);
			g2d.drawString("x"+characterPrice[i],x+40,327);
			}
		
		g2d.drawString("Buy Wallpapers:",55,380);
		 x=-80;
		for(int i=0;i<wallpapers.length;i++) 
			if(wallpapers[i]!=-1) { 
				g2d.drawImage(gp.getWallPaperImage(i),x+=135,400,null);
				g2d.drawImage(heart,x-5,515,null);
				g2d.drawString("x"+wallPrice[i],x+40,537);
			}

	}
	
}
