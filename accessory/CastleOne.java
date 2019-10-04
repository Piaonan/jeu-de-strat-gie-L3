package accessory;

import java.awt.Color;
import java.awt.Point;

import system.Game;

public class CastleOne extends Matter {
	private Color color;
	public CastleOne (Color c, Point p){
		super(p);
		color = c;
	}
	@Override
	public int[][] scrawl() {
		int space = Game.SQUARE;
		int [][] xy = new int [2][8];
		xy[0] = new int [8];
		xy[1] = new int [8];
		
		xy[0][0] = 1;             xy[1][0] = 1;
		xy[0][1] = space/2;       xy[1][1] = 1;
		xy[0][2] = space/2;       xy[1][2] = space/3;
		xy[0][3] = 3*space/4;     xy[1][3] = space/3;
		xy[0][4] = 3*space/4;     xy[1][4] = 1;	
		xy[0][5] = space;         xy[1][5] = 1;
		xy[0][6] = space;         xy[1][6] = space;
		xy[0][7] = 1;             xy[1][7] = space;
		return xy;
	}

	@Override
	public Color getColor() {
		return color;
	}
}
