package pillsgame;

import java.awt.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image backgroundImage;
	public GamePanel(String filePath, Dimension preferredSize) {
		if("".equals(filePath)) {
			backgroundImage=null; 
		} else {
			try {
				backgroundImage=ImageIO.read(getClass().getResource(filePath));
			} catch (IOException e) {e.printStackTrace();}
		}
		this.setPreferredSize(preferredSize);
		this.setOpaque(false);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, null);
	}
}
