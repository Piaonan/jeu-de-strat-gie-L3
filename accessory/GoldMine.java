package accessory;

import java.awt.Color;
import java.awt.Point;

import system.Game;
import system.Playground;

public class GoldMine extends Matter{
	private int remainingGold;
	
	public GoldMine(int amount, Point p) {
		super(p);
		remainingGold = amount;
	}
	
	public boolean isEmpty() {
		return remainingGold<=0;		
	}
	@Override
	protected int[][] scrawl() {
		int space = Game.SQUARE;
		int [][] xy = new int [2][12];
		xy[0] = new int [12];
		xy[1] = new int [12];
			
		xy[0][0] = 3*space/10+1;  xy[1][0] = 1;
		xy[0][1] = 1;             xy[1][1] = 1;
		xy[0][2] = 1;             xy[1][2] = 3*space/10+1;
		xy[0][3] = space;         xy[1][3] = 3*space/10+1;
		xy[0][4] = space;         xy[1][4] = 1;
		xy[0][5] = 7*space/10;    xy[1][5] = 1;
		xy[0][6] = 7*space/10;    xy[1][6] = space;
		xy[0][7] = space;         xy[1][7] = space;
		xy[0][8] = space;         xy[1][8] = 7*space/10;
		xy[0][9] = 1;             xy[1][9] = 7*space/10;
		xy[0][10] = 1;            xy[1][10] = space;
		xy[0][11] = 3*space/10+1; xy[1][11] = space;
		return xy;
	}

	@Override
	public Color getColor() {
		return new Color(238,201,0);
	}
	@Override
	public boolean effect(Unit u) {
		u.setRoad(null);
		u.stealing(this,current);
		Playground.checkGoldMines();
		return false;
	}
	public int stolen() {
		remainingGold-=10;
		return 10;
	}
}
