package look;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;

import accessory.Matter;
import accessory.Unit;
import system.Controller;
import system.Game;

public class Look extends JComponent implements Observer {
	private boolean gameOver = false;
	private static final long serialVersionUID = 1L;
	
	
	public final static int WIDTH = Game.WIDTH;
	public final static int HEIGHT = Game.HEIGHT;
	public final static int SQUARE = Game.SQUARE;
	private Point view; //POSITION DE LA PARTIE VISIBLE
	private TheMouse click = new TheMouse(this);
	private TheKeyboard press;
	private final Matter[][] table;
	private final Controller controller;
	private BufferedImage picture;
	private StringBuilder message = new StringBuilder();
	
	private JLabel label = new JLabel();
	
	public Look(final Matter[][] t, final Controller c) {
		super();
		table = t;
		controller = c;
		
		setWindows();		
		setLabel();
	    
	    try {
			picture = ImageIO.read(new File("src/look/box.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	    for(int i = 0; i < 3; i++)
			controller.creatingUnit();	
	    message.append("Vous avez : "+controller.getGold()+" pièces d'or.");
	}
	private void setWindows() {
		setOpaque(true);
		setSize(507, 530);
		view = controller.setView();
    	press = new TheKeyboard(this, controller);
		addMouseListener(click);
		addKeyListener(press);
		setFocusable(true);
	}
	private void setLabel() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(10,10,15,10));
	    Font police = new Font("Serif", Font.PLAIN, 18);  
	    label.setFont(police);
	    label.setForeground(new Color(179, 179, 255));
	    label.setHorizontalAlignment(JLabel.CENTER); 
	    label.setVerticalAlignment(JLabel.CENTER);
	    add(label, BorderLayout.SOUTH);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.gray);
		g.fillRect(0, 0, SQUARE*WIDTH, SQUARE*HEIGHT);
		
		g.setColor(Color.WHITE);
		for(int i = 0; i <= WIDTH; i++) {
			g.drawLine(i*SQUARE, 0, i*SQUARE, HEIGHT*SQUARE);
			g.drawLine(0, i*SQUARE, WIDTH*SQUARE, i*SQUARE);
		}
		drawing(g);	
		if(picture!=null)
			g.drawImage(picture,0, HEIGHT*SQUARE, WIDTH*SQUARE+1, 50, null);
		label.setText(message.toString());
	}
	
	private void drawing(Graphics g) {
		int stopX = WIDTH+view.x+(view.x!=Game.LIMITX?1:0);
		int stopY = HEIGHT+view.y+(view.y!=Game.LIMITY?1:0);
		int startX = view.x+(view.x!=0?-1:0);
		int startY = view.y+(view.y!=0?-1:0);
		
		int delayX = SQUARE*view.x;
		int delayY = SQUARE*view.y;
		for(int i = startX; i < stopX; i++)
			for(int j = startY; j < stopY; j++)
				drawTheScrawl(g, table[i][j], delayX, delayY);
	}
	private void drawTheScrawl(Graphics g, Matter m, int delayX, int delayY) {
		int [][] Tabxy = m.getXy(new Point(delayX, delayY));
		if(Tabxy!=null) {
			g.setColor(m.getColor());
			g.fillPolygon(Tabxy[0], Tabxy[1], Tabxy[0].length);
		}
		drawUnits(g,delayX,delayY,m.getReds(),new Color(240,11,48));
		drawUnits(g,delayX,delayY,m.getPurples(),new Color(90,36,143));
	}
	private void drawUnits(Graphics g, int delayX, int delayY, Unit [] l, Color color) {
		g.setColor(color);
		for(Unit u : l) {
			int [][] Tabxy = u.getXy(new Point(delayX, delayY));
			if(Tabxy!=null)
				g.fillPolygon(Tabxy[0], Tabxy[1], Tabxy[0].length);
		}
	}
	
	
	
	// SOURIS
	public void clickOn(int i, int j) {
		if(j!=HEIGHT) 
			controller.mouseAlert(i+view.x,j+view.y);
	}
	
	// CLAVIER
	public void setView(int a, int b) {
		if((view.x==0&&a==-1)
		||(view.y==0&&b==-1)
		||(view.x==Game.LIMITX&&a==1)
		||(view.y==Game.LIMITY&&b==1)) return;
			view.x += a;
			view.y += b;
			repaint();
	}
	public void superView(int a, int b) {
		view.x = a;
		view.y = b;
		repaint();
	}
	
	@Override
	public void update(Observable obs, Object obj) {
		if(obj!=null) {
			message.setLength(0);
			message.append("Fin de la partie : "+obj);
			removeMouseListener(click);
			removeKeyListener(press);
			gameOver = true;
		}
		else if(!gameOver) {
			message.setLength(0);
			message.append("Vous avez : "+controller.getGold()+" pièces d'or.");
		}
		repaint();
	}
}
