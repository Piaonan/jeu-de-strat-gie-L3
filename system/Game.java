package system;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;

import accessory.CastleFour;
import accessory.CastleOne;
import accessory.CastleThree;
import accessory.CastleTwo;
import accessory.Empty;
import accessory.Matter;
import look.Look;

public class Game {
	
	private JFrame frameOne = new JFrame("Peuple rouge");
	private JFrame frameTwo = new JFrame("Peuple violet");
	private Playground ground;
	public final static int WIDTH = 10;
	public final static int HEIGHT = 9;
	public final static int SQUARE = 500/WIDTH;
	public final static int SIZETABLE = 50;
	public final static int LIMITX = SIZETABLE - WIDTH;
	public final static int LIMITY = SIZETABLE - HEIGHT;
	
	private static Matter [][] table = new Matter[SIZETABLE][SIZETABLE];
	
	public Game() {
		initialize();
		
		ground = new Playground(table);
		
		Look one = new Look(table, new RedController(table, ground));
		Look two = new Look(table, new PurpleController(table, ground));
		
		ground.addObserver(one);
		ground.addObserver(two);
		ground.setElements();
		framing(frameTwo, two, false);
		framing(frameOne, one, true);
	}
	
	private void framing(JFrame frame, Look game, boolean b) {
		Dimension screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int h = (int)(screen.getHeight()-game.getSize().height)/2;
		int l1 = (int)(screen.getWidth()/2-game.getSize().width)/2;
		int l2 = l1+(int)screen.getWidth()/2;
		
		frame.setContentPane(game);
		frame.setSize(game.getSize());
		if(b) frame.setLocation(l1,h);
		else frame.setLocation(l2,h);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	private void initialize() {
		for(int i = 0; i < SIZETABLE; i++) {
			table[i] = new Matter[SIZETABLE];
			for(int j = 0; j < SIZETABLE; j++) {
				table[i][j] = new Empty();
			}
		}
		
		letCastleIn(2,new Color(240,11,48));
		letCastleIn(SIZETABLE-4,new Color(90,36,143));
	}
	
	private void letCastleIn(int x,Color c) {
		table[x][x] = new CastleOne(c,realPixel(x, x));
		table[x+1][x] = new CastleTwo(c,realPixel(x+1, x));
		table[x][x+1] = new CastleThree(c,realPixel(x, x+1));
		table[x+1][x+1] = new CastleFour(c,realPixel(x+1, x+1));
	}
	
	public static Point realPixel(int i, int j) {
		return new Point( SQUARE * i, SQUARE * j);
	}	
}
