package pillsgame;

import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GameButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	GameButton(GameButton b) {
		super(b.getText());
		this.setContentAreaFilled(b.isContentAreaFilled());
		this.setBorderPainted(b.isBorderPainted());
		this.setFocusPainted(b.isFocusPainted());
		this.setIcon(b.getIcon());
		this.setPreferredSize(b.getPreferredSize());
	}
	GameButton(String filePath, Dimension preferredSize) {
		super("");
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		try {
			this.setIcon(new ImageIcon((Image)ImageIO.read(getClass().getResource(filePath))));
		} catch (IOException e) {e.printStackTrace();}
		this.setPreferredSize(preferredSize);
	}
	GameButton(String filePath, String filePathPressed, Dimension preferredSize) {
		super("");
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		try {
			this.setIcon(new ImageIcon((Image)ImageIO.read(getClass().getResource(filePath))));
			this.setPressedIcon(new ImageIcon((Image)ImageIO.read(getClass().getResource(filePathPressed))));
		} catch (IOException e) {e.printStackTrace();}
		this.setPreferredSize(preferredSize);
	}
	GameButton(Image img, Dimension preferredSize) {
		super("");
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setIcon(new ImageIcon(img));
		this.setPreferredSize(preferredSize);
	}
	GameButton(Pill pill) {
		super("");
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setIcon(new ImageIcon(pill.getImage()));
		this.setPreferredSize(new Dimension(116, 116));
	}
}
