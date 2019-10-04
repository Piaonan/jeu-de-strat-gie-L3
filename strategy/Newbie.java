package strategy;

import java.awt.Point;

import system.Game;

public class Newbie implements Strategy{

	public Newbie() {
	}
	
	
	@Override
	public int[][] scrawl() {
		int space = Game.SQUARE;
		int [][] xy = new int [2][3];
		xy[0] = new int [3];
		xy[1] = new int [3];
		
		xy[0][0] = 2*space/5;    xy[1][0] = space/5;
		xy[0][1] = 3*space/5;    xy[1][1] = space/5;
		xy[0][2] = space/2;      xy[1][2] = space;
		return xy;
	}

	@Override
	public boolean nextStep(Point current, Point destination) {
		return false;
	}
}
