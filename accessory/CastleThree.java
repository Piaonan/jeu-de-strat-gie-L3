package accessory;

import java.awt.Color;
import java.awt.Point;

import system.Game;

public class CastleThree extends Matter {
	private Color color;
	public CastleThree (Color c, Point p){
		super(p);
		color = c;
	}
	@Override
	public int[][] scrawl() {
		int space = Game.SQUARE;
		int [][] xy = new int [2][6];
		xy[0] = new int [6];
		xy[1] = new int [6];
		
		xy[0][0] = 1;             xy[1][0] = 0;
		xy[0][1] = space;         xy[1][1] = 0;
		xy[0][2] = space;         xy[1][2] = 3*space/4;
		xy[0][3] = 3*space/4;     xy[1][3] = 3*space/4;
		xy[0][4] = 3*space/4;     xy[1][4] = space;	
		xy[0][5] = 1;             xy[1][5] = space;	
		return xy;
	}

	@Override
	public Color getColor() {
		return color;
	}
}
