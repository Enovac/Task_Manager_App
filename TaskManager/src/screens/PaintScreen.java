package screens;
import java.awt.*;
import java.io.*;
import engine.*;
public class PaintScreen implements Serializable {
	private GamePanel gp=TaskLauncher.gp;
	private int selectedWallpaper=-1;
	private int selectedCharacter=-1;
	private  final Font PRIMARY_FONT=new Font("Arial",Font.BOLD,30);
	private  final Font SECONDARY_FONT=new Font("Arial",Font.BOLD,25);//for boxes
	//Objects X,Y
	public final int UNSELECT_WALLPAPER_X=GamePanel.SCREEN_WIDTH/2+240;
	public final int UNSELECT_CHARACTER_X=250;
	public final int UNSELECT_Y=34;
	public final int UNSELECT_WIDTH=34;//for Both

	
//=====================Constructor======================	
//=====================Methods==========================
//=====================Paint============================
	public void paintPaintScreen(Graphics2D g2d) {
		g2d.setFont(SECONDARY_FONT);
		g2d.setStroke(new BasicStroke(3));
	//Character	
		g2d.drawString("Remove Character",10,50);
		g2d.drawRect(UNSELECT_CHARACTER_X,UNSELECT_Y, UNSELECT_WIDTH, UNSELECT_WIDTH);
		if(selectedCharacter==-1)
		g2d.fillRect(254,38, 12, 12);
	//Wallpaper
		g2d.drawString("Remove Wallpaper",GamePanel.SCREEN_WIDTH/2,50);
		g2d.drawRect(UNSELECT_WALLPAPER_X,UNSELECT_Y,UNSELECT_WIDTH,UNSELECT_WIDTH);
		if(selectedWallpaper==-1)
		g2d.fillRect(GamePanel.SCREEN_WIDTH/2+244,38, 12, 12);
		
		g2d.setFont(PRIMARY_FONT);
		g2d.drawString("Select Character:",10,145);//Y>175 Y<275
		int x=-80;
		for(int i=0;i<gp.characters.length;i++) 
			if(gp.isCharacterBought(i)) {
				if(i==0)
					g2d.drawImage(gp.characters[i],x+=135,190,null);
				else
					g2d.drawImage(gp.characters[i],x+=135,175,null);
				if(i==selectedCharacter) {
					if(i==0)
					g2d.drawRect(x-10, 165,110,120);
					else 
						g2d.drawRect(x-10, 165,120,120);}
			}
		g2d.drawString("Select Wallpaper:",10,360);
		 x=-80;
		for(int i=0;i<gp.wallpapers.length;i++) 
			if(gp.isWallpaperBought(i)) { 
				g2d.drawImage(gp.wallpapers[i],x+=135,400,null);
				if(i==selectedWallpaper)
					g2d.drawRect(x-10, 390,120,120);}
	}
//=====================G&S==============================	
	public void setSelectedCharacter(int x) {
		selectedCharacter=x;
	}
	public void setSelectedWallpaper(int x) {
		selectedWallpaper=x;
	}

}
