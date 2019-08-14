package pillsgame;


import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class GameWindow {
	String border=""; //DESIGN SWITCHER - NOT IMPLEMENTED YET
	Timer timer;
	int time=480;
	Timer customerGenerator;
	Boolean nextCustomer=true;
	
	//Starting and Finishing Screens
	JFrame frame;
	GamePanel glass;
	GameButton startButton;
	
	GamePanel glassFinish;
	GameButton newGameButton;
	JTextField nameField;
	JButton confirmNameButton;
	GamePanel highScoreTable;
	JLabel highScoreTableLabel;
	
	//Menu bar
	JMenuBar menubar;
	JMenu pauseMenu;
	JMenuItem newGame;
	JMenuItem highScores;
	JMenuItem quit;
	
	//Left panel
	GamePanel leftPanel;
	BlankPanel recipeHolder;
	GamePanel recipePanel;
	GamePanel recipeStampedPanel;
	BlankPanel recipeFoilPanel;
	BlankPanel refuseServiceButtonPanel;
	GameButton refuseServiceButton;
	
	JLabel doctorName;
	JLabel patientName;
	JLabel signature;
	JLabel pillListLabel;

	//Middle panel
	GamePanel midPanel;
	//Right panel
	JPanel rightPanel; //+?
	JLabel clock;
	JLabel totalLabel;
	JLabel total;
	JLabel thisTransaction;
	
	//Bottom panel
	JPanel botPanel;
	GamePanel botLeftPanel;
	GamePanel botRightPanel;
	GamePanel paperBag;
	JLayeredPane bagInside;
	//JPanel bagInside;
	Integer lastPillIdx;
	Boolean[] bagPositions={true, true, true, true, true, true, true, true, true};
	GameButton sellButton;
	JLabel focusMsg;
	
	//LISTENERS
	HoverListener pbl;
	GameButtonActionListener al;
	
	//DATA
	ArrayList<Pill> pills=new ArrayList<Pill>();
	ArrayList<GameButton> pillButtons=new ArrayList<GameButton>();
	CashRegister till=new CashRegister(0, 0);
	PillBag bag=new PillBag();
	Customer customer;
	HighScoreTable highScoreData;
	int lowestHighScore=0;
	
	public GameWindow() {
		readHighScoreData();
		if(!highScoreData.highScores.isEmpty()) {
			lowestHighScore=highScoreData.highScores.get(highScoreData.highScores.size()-1).getScore();
		}
		//if(!highScoreData.highScores.contains(new HighScoreEntry("Xx0ldLady42xX", 999999))){
		//	highScoreData.add("Xx0ldLady42xX", 999999);
		//}
		
		//WINDOW
		frame=new JFrame("PharMania");
		//frame.setPreferredSize(new Dimension(1200, 933));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(new GridBagLayout());
		
		glass=new GamePanel("/images/glasspanel.png", new Dimension(1200, 933));
		glass.setLayout(new BorderLayout());
		startButton=new GameButton("/images/startbutton.png", "/images/startbuttonpressed.png", new Dimension(118, 118));
		glass.add(startButton, BorderLayout.CENTER);
		
		frame.setGlassPane(glass);
		
		glass.setVisible(true);
		GlassInputConsumer gic=new GlassInputConsumer();
		glass.addMouseListener(gic);
		
		glassFinish=new GamePanel("/images/glasspanelfinish.png", new Dimension(1200, 933));
		glassFinish.addMouseListener(gic);
		glassFinish.setVisible(false);
		//glassFinish.setLayout(new GridBagLayout());
		glassFinish.setLayout(null);
		
		newGameButton=new GameButton("/images/newgamebutton.png", "/images/newgamebuttonpressed.png", new Dimension(118, 118));
		newGameButton.setBounds(860, 680, 118, 118);
		
		nameField=new JTextField(10);
		nameField.setFont(new Font("SansSerif", Font.PLAIN, 35));
		nameField.setVisible(false);
		nameField.setBounds(40, 780, 300, 38);
		
		confirmNameButton=new JButton("Confirm");
		confirmNameButton.setFont(new Font("Monospaced", Font.BOLD, 35));
		confirmNameButton.setVisible(false);
		confirmNameButton.setBounds(380, 780, 230, 38);
		
		highScoreTable=new GamePanel("/images/highscores.png", new Dimension(570, 580));
		highScoreTable.setBounds(40, 33+40, 570, 580);
		highScoreTable.setLayout(null);
		highScoreTableLabel=new JLabel(highScoreData.toHtml());
		highScoreTableLabel.setFont(new Font("Monospaced", Font.BOLD, 30));
		highScoreTableLabel.setVerticalAlignment(SwingConstants.TOP);
		highScoreTableLabel.setBounds(30, 100, 540, 420); //-50 height
		//highScoreTable.add(highScoreTableLabel, BorderLayout.CENTER);
		
/*		GridBagConstraints gfcon=new GridBagConstraints();
		gfcon.weightx=0.5; gfcon.weighty=0.5;
		gfcon.gridx=0; gfcon.gridy=0; gfcon.gridwidth=2; gfcon.gridheight=4;
		glassFinish.add(highScoreTable, gfcon);
		gfcon.gridy=4; gfcon.gridwidth=1; gfcon.gridheight=1;
		glassFinish.add(nameField, gfcon);
		gfcon.gridx=1;
		glassFinish.add(confirmNameButton, gfcon);
		gfcon.gridx=2; gfcon.gridy=0; gfcon.gridwidth=2; gfcon.gridheight=4;
		//glassFinish.add(new BlankPanel(new Dimension(600, 720)));
		gfcon.gridx=2; gfcon.gridy=4; gfcon.gridwidth=2; gfcon.gridheight=1;
		glassFinish.add(newGameButton, gfcon);
*/		
		glassFinish.add(highScoreTable);
		highScoreTable.add(highScoreTableLabel);
		glassFinish.add(nameField);
		glassFinish.add(confirmNameButton);
		glassFinish.add(newGameButton);
		
		//MENUBAR
		menubar=new JMenuBar();
		menubar.setLayout(new BorderLayout());
		frame.setJMenuBar(menubar);
		
		pauseMenu=new JMenu("+");
		newGame=new JMenuItem("New Game");
		highScores=new JMenuItem("High Scores");
		quit=new JMenuItem("Quit");
		pauseMenu.add(newGame);
		//pauseMenu.add(highScores);					//High Scores removed from menu
		pauseMenu.addSeparator();
		pauseMenu.add(quit);
		menubar.add(pauseMenu, BorderLayout.WEST);
		Font monofontbold20=new Font("SansSerif", Font.PLAIN, 20);
		pauseMenu.setFont(monofontbold20.deriveFont(Font.BOLD));
		newGame.setFont(monofontbold20);
		highScores.setFont(monofontbold20);
		quit.setFont(monofontbold20);

		
		
		////FÕ PANELEK FELÉPÍTÉSE
		leftPanel=new GamePanel("/images/leftpanel"+border+".png", new Dimension(334, 450));  ////Trying design switcher
		leftPanel.setLayout(new BorderLayout());
		recipeHolder=new BlankPanel(new Dimension(296, 416));
		recipeHolder.setLayout(new OverlayLayout(recipeHolder));
		recipeHolder.getLayout().preferredLayoutSize(recipeHolder);
		leftPanel.add(new BlankPanel(new Dimension(334, 19)), BorderLayout.NORTH);
		leftPanel.add(new BlankPanel(new Dimension(19, 416)), BorderLayout.EAST);
		leftPanel.add(recipeHolder, BorderLayout.CENTER);
		leftPanel.add(new BlankPanel(new Dimension(19, 416)), BorderLayout.WEST);
		leftPanel.add(new BlankPanel(new Dimension(334, 15)), BorderLayout.SOUTH);
		
		refuseServiceButtonPanel=new BlankPanel(new Dimension(296, 416));
		refuseServiceButtonPanel.setLayout(null);
		recipeFoilPanel=new BlankPanel(new Dimension(296, 416));
		recipePanel=new GamePanel("/images/recipe.png", new Dimension(296, 416));
		recipeStampedPanel=new GamePanel("/images/recipestamped.png", new Dimension(296, 416));
		recipeHolder.add(refuseServiceButtonPanel);
		recipeHolder.add("recipeFoil", recipeFoilPanel);
		recipeHolder.add("recipe", recipePanel);
		recipeHolder.add("recipeStamped", recipeStampedPanel);
		recipePanel.setVisible(false);
		recipeStampedPanel.setVisible(false);
		
		doctorName=new JLabel("Dr. B. Knowles Alot");
		doctorName.setFont(new Font("Monospaced", Font.PLAIN, 20));
		//doctorName.setBounds(35, 37, 281, 23);
		doctorName.setBounds(20, 37, 281, 23);
		patientName=new JLabel("Sick Alot");
		patientName.setFont(new Font("Monospaced", Font.PLAIN, 20));
		//patientName.setBounds(35, 100, 281, 23);
		patientName.setBounds(20, 100, 281, 23);
		signature=new JLabel("Papi");
		signature.setFont(new Font("Serif", Font.ITALIC, 20));
		signature.setForeground(Color.BLUE);
		signature.setBounds(30, 339, 286, 26);
		recipeFoilPanel.setLayout(null);
		recipeFoilPanel.add(doctorName);
		recipeFoilPanel.add(patientName);
		recipeFoilPanel.add(signature); // IDEA: elcsúszkáló aláírás és pecsét
		doctorName.setVisible(false);
		patientName.setVisible(false);
		signature.setVisible(false);
		pillListLabel=new JLabel("<html><body>CRANIAZE 1x<br>BIOASE 1x<br>NOCIGEN 1x<br>MYCOURIN 1x<br>HYROFORM 1x<br>BIOGEN 1x</body></html>");
		pillListLabel.setFont(new Font("Monospaced", Font.PLAIN, 17));
		pillListLabel.setBounds(46, 195, 264, 140);
		recipeFoilPanel.add(pillListLabel);
		pillListLabel.setVisible(false);
		
		refuseServiceButton=new GameButton("/images/refusebutton.png", "/images/refusebuttonpressed.png", new Dimension(117, 117));
		refuseServiceButton.setBounds(91, 150, 117, 117);
		refuseServiceButtonPanel.add(refuseServiceButton);
		refuseServiceButton.setVisible(false);
		
		
		//Middle panel
		midPanel=new GamePanel("/images/midpanel.png", new Dimension(513, 450));
		//Right panel
		rightPanel=new GamePanel("/images/rightpanel.png", new Dimension(353, 450));
		rightPanel.setLayout(null);
		clock=new JLabel("08:00");
		clock.setFont(new Font("Monospaced", Font.BOLD, 20));
		clock.setForeground(Color.GREEN);
		clock.setBounds(32, 252, 60, 23);
		clock.setBackground(Color.RED);
		clock.setVisible(true);
		rightPanel.add(clock);
//		totalLabel=new JLabel("Total:");
//		totalLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
//		totalLabel.setForeground(Color.GREEN);
//		totalLabel.setBounds(96, 252, 80, 23);
//		rightPanel.add(totalLabel);
		total=new JLabel("0 Ft", SwingConstants.RIGHT);
		total.setFont(new Font("Monospaced", Font.BOLD, 20));
		total.setForeground(Color.GREEN);
		total.setBounds(185, 252, 132, 23);
		rightPanel.add(total);
		thisTransaction=new JLabel("0 Ft", SwingConstants.RIGHT);
		thisTransaction.setFont(new Font("Monospaced", Font.BOLD, 40));
		thisTransaction.setForeground(Color.GREEN);
		thisTransaction.setBounds(32, 280, 285, 33);
		rightPanel.add(thisTransaction);
		//Bottom panel
		botPanel=new JPanel();
		botPanel.setLayout(new GridBagLayout());
		botLeftPanel=new GamePanel("/images/botleftpanel.png", new Dimension(640, 450));
		botRightPanel=new GamePanel("/images/botrightpanel.png", new Dimension(560, 450));
		botRightPanel.setLayout(new BorderLayout());
		paperBag=new GamePanel("/images/paperbag.png", new Dimension(560, 240));
		
		focusMsg=new JLabel("<html><body>Hover over a pill to see its name here!<br><center>Click on it to add it to the bag!</center></body></html>", SwingConstants.CENTER);
		focusMsg.setFont(new Font("Monospaced", Font.BOLD, 20));
		focusMsg.setPreferredSize(new Dimension(560, 60));
		sellButton=new GameButton("/images/sellbutton.png", "/images/sellbuttonpressed.png", new Dimension(356, 125));
		sellButton.setDisabledIcon(sellButton.getIcon());
		
		botRightPanel.add(focusMsg, BorderLayout.NORTH);
		botRightPanel.add(paperBag, BorderLayout.CENTER);
		paperBag.setLayout(null);
		bagInside=new JLayeredPane();
		//		bagInside=new JPanel();
//		bagInside.setPreferredSize(new Dimension(431, 116));
//		bagInside.setLayout(new OverlayLayout(bagInside));
		//bagInside=new JPanel();
		//bagInside.setLayout(new FlowLayout(FlowLayout.RIGHT, -68, 0));
		//bagInside.add(new BlankPanel(new Dimension(-68, 116)));
		
		bagInside.setBounds(62, 62, 431, 116);
		bagInside.setOpaque(false);
		//bagInside.setBackground(new Color(123, 121, 100, 5));
		paperBag.add(bagInside);

		BlankPanel botRightBotPanel=new BlankPanel(new Dimension(560, 150));
		botRightPanel.add(botRightBotPanel, BorderLayout.SOUTH);
		botRightBotPanel.setLayout(new BoxLayout(botRightBotPanel, BoxLayout.PAGE_AXIS));
		BlankPanel sellButtonHolder=new BlankPanel(new Dimension(560, 125));
		botRightBotPanel.add(sellButtonHolder);
		sellButtonHolder.add(sellButton);
		botRightBotPanel.add(new BlankPanel(new Dimension(560, 35)));
		
		
		////botpanel majd Frame felépítése a fõ panelekbõl
		GridBagConstraints constraint=new GridBagConstraints();
		constraint.fill=GridBagConstraints.BOTH;
		constraint.weightx=0.5;
		constraint.weighty=0.5;
		
		constraint.gridx=0;
		constraint.gridy=0;
		botPanel.add(botLeftPanel, constraint);
		constraint.gridx=1;
		botPanel.add(botRightPanel, constraint);		
		
		constraint.gridx=0;
		constraint.gridy=0;
		constraint.gridwidth=334;
		constraint.gridheight=1;
		frame.add(leftPanel, constraint);
		constraint.gridx=334;
		constraint.gridy=0;
		constraint.gridwidth=513;
		constraint.gridheight=1;
		frame.add(midPanel, constraint);
		constraint.gridx=334+513;
		constraint.gridy=0;
		constraint.gridwidth=353;
		constraint.gridheight=1;
		frame.add(rightPanel, constraint);
		constraint.gridx=0;
		constraint.gridy=1;
		constraint.gridwidth=1200;
		constraint.gridheight=1;
		frame.add(botPanel, constraint);
		
		//SIZES -pontosítás
		//leftPanel.setPreferredSize(new Dimension(334, 450));
		//midPanel.setPreferredSize(new Dimension(513, 450));
		//rightPanel.setPreferredSize(new Dimension(343, 450));
		botPanel.setPreferredSize(new Dimension(1200, 450));
		
		//COLORS
		//leftPanel.setBackground(new Color(254, 190, 178));
		//midPanel.setBackground(new Color(241, 246, 245));
		//rightPanel.setBackground(new Color(200, 223, 218));
		
		///////////KÍSÉRLETEZÉS///////////////////////////////
		//midPanel.add(new GameButton("/images/pill4faded.png", new Dimension(116, 116)));
		//midPanel.add(new GameButton("/images/pills/4.png", new Dimension(116,116)));
		
		////Gyógyszerszekrény felépítése
		PillGenerator pg=new PillGenerator();
		pills=pg.generate(6);
		for(int i=0; i<6; i++) {
			pillButtons.add(new GameButton(pills.get(i)));
		}
		botLeftPanel.setLayout(new GridBagLayout());
		GridBagConstraints c2=new GridBagConstraints();
		c2.fill=GridBagConstraints.BOTH;
		c2.weightx=0.5;
		c2.weighty=0.5;
		
		////Blank row
		c2.gridx=0;
		c2.gridy=0;
		c2.gridwidth=640;
		c2.gridheight=29;
		botLeftPanel.add(new BlankPanel(new Dimension(640, 29)), c2);
		////First row
		//1. space
		c2.gridx=0;
		c2.gridy=29;
		c2.gridwidth=58;
		c2.gridheight=116;
		botLeftPanel.add(new BlankPanel(new Dimension(58, 116)), c2);
		//1. pill
		c2.gridx=58;
		c2.gridy=29;
		c2.gridwidth=116;
		c2.gridheight=116;
		botLeftPanel.add(pillButtons.get(0), c2);
		//2. space
		c2.gridx=58+116;
		c2.gridy=29;
		c2.gridwidth=88;
		c2.gridheight=116;
		botLeftPanel.add(new BlankPanel(new Dimension(88, 116)), c2);
		//2. pill
		c2.gridx=58+116+88;
		c2.gridy=29;
		c2.gridwidth=116;
		c2.gridheight=116;
		botLeftPanel.add(pillButtons.get(1), c2);
		//3. space
		c2.gridx=58+116+88+116;
		c2.gridy=29;
		c2.gridwidth=88;
		c2.gridheight=116;
		botLeftPanel.add(new BlankPanel(new Dimension(88, 116)), c2);
		//3. pill
		c2.gridx=58+116+88+116+88;
		c2.gridy=29;
		c2.gridwidth=116;
		c2.gridheight=116;
		botLeftPanel.add(pillButtons.get(2), c2);
		//4. space
		c2.gridx=58+116+88+116+88+116;
		c2.gridy=29;
		c2.gridwidth=58;
		c2.gridheight=116;
		botLeftPanel.add(new BlankPanel(new Dimension(58, 116)), c2);
		////Blank row
		c2.gridx=0;
		c2.gridy=29+116;
		c2.gridwidth=640;
		c2.gridheight=87;
		botLeftPanel.add(new BlankPanel(new Dimension(640, 87)), c2);
		////Second row
		//1. space
		c2.gridx=0;
		c2.gridy=29+116+87;
		c2.gridwidth=58;
		c2.gridheight=116;
		botLeftPanel.add(new BlankPanel(new Dimension(58, 116)), c2);
		//1. pill
		c2.gridx=58;
		c2.gridy=29+116+87;
		c2.gridwidth=116;
		c2.gridheight=116;
		botLeftPanel.add(pillButtons.get(3), c2);
		//2. space
		c2.gridx=58+116;
		c2.gridy=29+116+87;
		c2.gridwidth=88;
		c2.gridheight=116;
		botLeftPanel.add(new BlankPanel(new Dimension(88, 116)), c2);
		//2. pill
		c2.gridx=58+116+88;
		c2.gridy=29+116+87;
		c2.gridwidth=116;
		c2.gridheight=116;
		botLeftPanel.add(pillButtons.get(4), c2);
		//3. space
		c2.gridx=58+116+88+116;
		c2.gridy=29+116+87;
		c2.gridwidth=88;
		c2.gridheight=116;
		botLeftPanel.add(new BlankPanel(new Dimension(88, 116)), c2);
		//3. pill
		c2.gridx=58+116+88+116+88;
		c2.gridy=29+116+87;
		c2.gridwidth=116;
		c2.gridheight=116;
		botLeftPanel.add(pillButtons.get(5), c2);
		//4. space
		c2.gridx=58+116+88+116+88+116;
		c2.gridy=29+116+87;
		c2.gridwidth=58;
		c2.gridheight=116;
		botLeftPanel.add(new BlankPanel(new Dimension(58, 116)), c2);
		////Blank row
		c2.gridx=0;
		c2.gridy=29+116+87+116;
		c2.gridwidth=640;
		c2.gridheight=102;
		botLeftPanel.add(new BlankPanel(new Dimension(640, 102)), c2);
		
		////Adding LISTENERS
		pbl=new HoverListener();
		al=new GameButtonActionListener();
		for(int i=0; i<6; i++) {
			pillButtons.get(i).addMouseListener(pbl);
			pillButtons.get(i).setActionCommand(Integer.toString(i));
			pillButtons.get(i).addActionListener(al);
		}
		startButton.setActionCommand("start");
		startButton.addActionListener(al);
		sellButton.setActionCommand("sell");
		sellButton.addActionListener(al);
		refuseServiceButton.setActionCommand("refuse");
		refuseServiceButton.addActionListener(al);
		recipePanel.addMouseListener(pbl);
		recipeStampedPanel.addMouseListener(pbl);
		leftPanel.addMouseListener(pbl);
		botLeftPanel.addMouseListener(pbl);
		timer=new Timer(250, al); //2 perc játékidõ 
		timer.setActionCommand("tick");
		customerGenerator=new Timer(750, al);
		customerGenerator.setActionCommand("newcustomer");
		
		newGame.addActionListener(al);
		newGame.setActionCommand("newgame");
		highScores.addActionListener(al);
		highScores.setActionCommand("highscores");
		quit.addActionListener(al);
		quit.setActionCommand("quit");
		
		newGameButton.addActionListener(al);
		newGameButton.setActionCommand("newgame");
		confirmNameButton.addActionListener(al);
		confirmNameButton.setActionCommand("confirmname");
		
		
		
		
				
		////////
		//PACK
		//frame.validate();
		showGameElements(false);
		frame.pack();
		frame.setVisible(true);
	}
	// /CONSTRUCTOR
    //////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void setVisible(boolean b) {
		frame.setVisible(b);
	}
	public void startGame() {
		showGameElements(true);
		timer.start();
		customerGenerator.start();
	}
	public void finishGame() {
		customerGenerator.stop();
		recipeHolder.setVisible(false);
		pauseMenu.setEnabled(false);
		sellButton.setVisible(false);
		frame.setGlassPane(glassFinish);
		glassFinish.setVisible(true);
		total.setForeground(Color.GREEN);
		total.setFont(total.getFont().deriveFont(Font.BOLD));
		if(highScoreData.highScores.size()<10 || till.getTotal()>lowestHighScore) {
			nameField.setVisible(true);
			confirmNameButton.setVisible(true);
		}		
	}
	public void scoreScreen(String name) {
		nameField.setVisible(false);
		confirmNameButton.setVisible(false);
		highScoreData.add(name, till.getTotal());
		highScoreTableLabel.setText(highScoreData.toHtml());
		//frame.repaint();
		//highScoreTable.setVisible(true);
		writeHighScoreData();
	}
	public void showGameElements(Boolean b) {
		for(int i=0; i<6; i++) {
			pillButtons.get(i).setVisible(b);
		}
		sellButton.setVisible(b);
		paperBag.setVisible(b);
		total.setVisible(b);
		thisTransaction.setVisible(b);
		clock.setVisible(b);
		focusMsg.setVisible(b);
		pauseMenu.setEnabled(b);
	}
	public void timerTick() {
		if(time>=960) {
			timer.stop();
		 } else {
			time++;
			String hours=(time/60<10)?"0"+Integer.toString(time/60):Integer.toString(time/60);
			String minutes=(time%60<10)?"0"+Integer.toString(time%60):Integer.toString(time%60);
			clock.setText(hours+":"+minutes);
		 }
	}
	public void addPill(int idx) {
		if(bag.size!=9) {
			Pill p=pills.get(idx);			
			till.putPill(p, bag);
			GameButton b=new GameButton(p);
			b.setActionCommand(Integer.toString(idx));
			b.addActionListener(al);
			int firstFreePosition;
			for(firstFreePosition=0; firstFreePosition<=bag.size && bagPositions[firstFreePosition]!=true; firstFreePosition++) {
			}
			bagPositions[firstFreePosition]=false;
			b.setBounds(5+(firstFreePosition)*38, 0, 116, 116);
			bagInside.add(b, JLayeredPane.DEFAULT_LAYER, -firstFreePosition);
			bagInside.moveToFront(b);
			//bagInside.add(p.getImage(), new Dimension(116, 116));
			frame.revalidate();
			frame.repaint();
			thisTransaction.setText(till.thisTransactionString()+" Ft");
			System.out.println("Heard ya! Added stuff to bag! Bag size="+bag.size);
		} else {
			focusMsg.setFont(new Font("Monospaced", Font.BOLD, 30));
			focusMsg.setText("Bag is full!");
		}
	}
	public void removePill(int idx) {
		till.removePill(pills.get(idx), bag);
		thisTransaction.setText(till.thisTransactionString()+" Ft");
		System.out.println("Removin!!!");
	}
	public void generateCustomer() {
		customer=new Customer(pills);
		doctorName.setText(new String("Dr. "+NameGenerator.generate()));
		patientName.setText(customer.getReceiptName());
		signature.setText(customer.getReceiptSignature());
		pillListLabel.setText(customer.getReceiptPillListHtml());
		showRecipe(true);
		if(!paperBag.isVisible()) paperBag.setVisible(true);
	}
	public void showRecipe(Boolean b) {
		doctorName.setVisible(b);
		patientName.setVisible(b);
		signature.setVisible(b);
		pillListLabel.setVisible(b);
		recipeHolder.setVisible(b);
		if(b) {
			Boolean stamped=customer.isReceiptStamped();
			recipeStampedPanel.setVisible(stamped);
			recipePanel.setVisible(!stamped);
			//System.out.println(customer.isReceiptStamped());
		}
	}
	public void nextCustomer() {
		sellButton.setEnabled(false);	
		showRecipe(false);
		nextCustomer=true;
	}
	public void serveCustomer() {
		Component[] p=bagInside.getComponents();
		for(int i=0; i<p.length; i++) {
			bagInside.remove(p[i]);
		}
		for(int i=0; i<bagPositions.length; i++) {
			bagPositions[i]=true;
		}
		frame.revalidate();
		frame.repaint();
		int x=till.pay(customer, bag);
		switch(x) {
			case -1:
				total.setForeground(Color.RED);
//				String s; //X vagy ! a képernyõ baloldalán
				break;
			case 0:
				total.setForeground(Color.YELLOW);
				break;
			case 1:
				total.setForeground(Color.GREEN);
		}
		/*if(x==-1) {
			total.setForeground(Color.RED);
			String s; //X vagy ! a képernyõ baloldalán
		} else {
			
			total.setForeground(Color.GREEN);
		}*/
		total.setText(till.totalString()+" Ft");
		thisTransaction.setText("0 Ft");
		nextCustomer();
	}
	@SuppressWarnings("unchecked")
	public void readHighScoreData() {
		highScoreData=new HighScoreTable();
		File f=new File("highscores.dat");
		if(f.exists()) {
			if(f.length()!=0) {
				try {
					ObjectInputStream ois;
					ois = new ObjectInputStream(new FileInputStream("highscores.dat"));
					highScoreData.highScores=(List<HighScoreEntry>) ois.readObject();				
					ois.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} else {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void writeHighScoreData() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("highscores.dat"));
			oos.writeObject(highScoreData.highScores);
			oos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	//////////////////
	// LISTENERS
	class GameButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd=e.getActionCommand();
			if(cmd.length()<=2) {
				int idx=Integer.parseInt(e.getActionCommand());
				if(idx>=0 && idx<6) {
					if(((JComponent)e.getSource()).getParent().equals(botLeftPanel)) {
						addPill(idx);
					} else {
						GameButton b=(GameButton)e.getSource();
						bagPositions[(b.getBounds().x-5)/38]=true;
						bagInside.remove((JComponent)e.getSource());
						frame.revalidate();
						frame.repaint();
						removePill(idx);
					}
				}	
			} else {
				switch(cmd) {
				case "start":
					glass.setVisible(false);
					startGame();
					break;
					
				case "tick":
					timerTick();
					if(!timer.isRunning()) finishGame();
					break;
				case "newcustomer":
					if(nextCustomer) {
						generateCustomer();
						sellButton.setEnabled(true);
						nextCustomer=false;
					}
					break;
				case "refuse":
					nextCustomer();
					break;
				case "sell":
					serveCustomer();
					break;
				case "newgame":
					new GameWindow();
					frame.dispose();
					break;
				case "highscores":
					break;
				case "quit":
					frame.dispose();
					break;
				case "confirmname":
					scoreScreen(nameField.getText().length()==0?"Anon":nameField.getText());
					break;
				}
			}
		}
	}
	class HoverListener extends MouseInputAdapter {
		//int experienceTimer=3;
		@Override
		public void mouseEntered(MouseEvent evt) {
			if(botLeftPanel.equals(evt.getComponent().getParent())) {
				focusMsg.setFont(new Font("Monospaced", Font.BOLD, 30));
				Pill p=pills.get(pillButtons.indexOf(evt.getComponent()));
				focusMsg.setText("+"+p.getName()+"    "+p.getPrice()+" Ft");
			}
			if(botLeftPanel.equals(evt.getComponent())) {
				focusMsg.setFont(new Font("Monospaced", Font.BOLD, 20));
				//focusMsg.setText("<html><body>Hover over a pill to see its name here!<br><center>Click on it to add it to the bag!</center></body></html>");
				refuseServiceButton.setVisible(false);
			}
			if(recipeHolder.equals(evt.getComponent().getParent())) {
				refuseServiceButton.setVisible(true);
				//if(experienceTimer>=0) {
					focusMsg.setFont(new Font("Monospaced", Font.BOLD, 20));
					focusMsg.setText("<html><body><center>Click the X to refuse service to<br>customers with no stamp or wrong signature!</center></body></html>");
				//	experienceTimer--;
				//}
			}
			if(leftPanel.equals(evt.getComponent())) {
				refuseServiceButton.setVisible(false);
				focusMsg.setText("");
			}
		}
		@Override
		public void mouseExited(MouseEvent evt) {
			if(botLeftPanel.equals(evt.getComponent().getParent())) {
				//focusMsg.setText("Hover over a pill to see it's name here!");
				//if(experienceTimer>=0) {
					focusMsg.setText("");
				//}
			}
		}
	}
	class GlassInputConsumer implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			e.consume();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			e.consume();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			e.consume();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			e.consume();
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			e.consume();
		}
	}
	
}
