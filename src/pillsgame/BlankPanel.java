package pillsgame;

import java.awt.Dimension;

import javax.swing.JPanel;

public class BlankPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	BlankPanel(Dimension d) {
		this.setOpaque(false);
		this.setPreferredSize(d);
	}
}
