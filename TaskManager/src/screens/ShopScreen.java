package screens;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import javax.imageio.ImageIO;

import engine.GamePanel;
import engine.TaskLauncher;
public class ShopScreen implements Serializable{
	private  final Font MAIN_FONT=new Font("Arial",Font.BOLD,30);
	transient public BufferedImage shopImage;
	private GamePanel gp=TaskLauncher.gp;
	private int [] wallpaperPrice;
	private int [] characterPrice;
	private boolean confirm;//Means Attempted to buy and waiting confirmation

	
	
	
	
//=====================Constructor======================
	public ShopScreen(GamePanel gp) {
		characterPrice=new int[]{40,20,10,25};
		wallpaperPrice=new int[] {5,40,10,20,50,30};
		loadImage();
	}
//=====================Methods==========================
	public void buyCharacter(int x) {
		if(gp.getMoney()>=characterPrice[x]&&!gp.isCharacterBought(x)) {
		gp.deductMoney(characterPrice[x]);
		gp.setBoughtCharacter(x);
		}
	}
	public void removeWallMoney(int x) {
		if(gp.getMoney()>=wallpaperPrice[x]&&!gp.isWallpaperBought(x)) {
			gp.deductMoney(wallpaperPrice[x]);
			gp.setBoughtWallpaper(x);
			}
	}
	public void toggleConfirm() {
		confirm=confirm?false:true;
	}
//=====================Paint============================
	public void paintShop(Graphics2D g2d) {
		if(confirm)
			paintConfirm(g2d);
		g2d.drawImage(shopImage,0,0,null);
		g2d.drawImage(GamePanel.heart,GamePanel.SCREEN_WIDTH-160,145,null);//50 50
		g2d.setFont(MAIN_FONT);
		g2d.drawString("x"+gp.getMoney(),GamePanel.SCREEN_WIDTH-110,175);
		g2d.drawString("Buy Characters:",55,165);//Y>175 Y<275
		int x=-80;
		for(int i=0;i<gp.characters.length;i++) 
			if(!gp.isCharacterBought(i)) { 
				if(i==0)
					g2d.drawImage(gp.characters[i],x+=135,190,null);
				else
				g2d.drawImage(gp.characters[i],x+=135,175,null);
			g2d.drawImage(gp.heart,x-5,300,null);
			g2d.drawString("x"+characterPrice[i],x+40,327);
			}
		
		g2d.drawString("Buy Wallpapers:",55,380);
		 x=-80;
		for(int i=0;i<gp.wallpapers.length;i++) 
			if(!gp.isWallpaperBought(i)) { 
				g2d.drawImage(gp.wallpapers[i],x+=135,400,null);
				g2d.drawImage(gp.heart,x-5,515,null);
				g2d.drawString("x"+wallpaperPrice[i],x+40,537);
			}
	}
	private void paintConfirm(Graphics2D g2d) {
		int x=GamePanel.SCREEN_WIDTH-300;int y=GamePanel.HEIGHT/2-130;
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.6f));
		g2d.setColor(Color.red);
		g2d.fillRoundRect(x,y,250,130,25,25);
		g2d.setColor(Color.BLACK);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
		g2d.setStroke(new BasicStroke(3));
		g2d.drawRoundRect(x,y,250,130,25,25);
		g2d.drawString("Buy item?", x+55, y+30);
		g2d.drawString("Yes!", x+40, y+80);
		g2d.drawString("NO!", x+160, y+80);
	}
//=====================G&S==============================
	public boolean getConfirm() {
		return confirm;
	}
//=====================Load=============================
public void loadImage() {
	try {
		shopImage=(ImageIO.read(getClass().getResourceAsStream("/res/shop/shop(1).png")));
	}catch(Exception ex) {}
}
}
