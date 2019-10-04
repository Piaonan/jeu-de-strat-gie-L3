package accessory;

import java.awt.Color;
import java.awt.Point;

import system.Game;

public class Tree extends Matter {
	
	public Tree(Point p) {
		super(p);
	}
	@Override
	protected int[][] scrawl() {
		int space = Game.SQUARE;
		int [][] xy = new int [2][5];
		xy[0] = new int [5];
		xy[1] = new int [5];
		
		xy[0][0] = space/2;       xy[1][0] = 1;
		xy[0][1] = 1;             xy[1][1] = 3*space/4;
		xy[0][2] = 3*space/4;     xy[1][2] = space;
		xy[0][3] = space/4;       xy[1][3] = space;
		xy[0][4] = space;         xy[1][4] = 3*space/4;		
		return xy;
	}

	@Override
	public Color getColor() {
		return new Color(105,139,34);
	}
}
