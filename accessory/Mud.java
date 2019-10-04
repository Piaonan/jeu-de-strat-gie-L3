package accessory;

import java.awt.Color;
import java.awt.Point;
import java.util.List;

import system.Game;

public class Mud extends Matter {
	public Mud(Point p) {
		super(p);
	}
	@Override
	protected int[][] scrawl() {
		int space = Game.SQUARE;
		int [][] xy = new int [2][12];
		xy[0] = new int [12];
		xy[1] = new int [12];
			
		xy[0][0] = 1;           xy[1][0] = space;
		xy[0][1] = space;       xy[1][1] = space;
		xy[0][2] = space;       xy[1][2] = 1;
		xy[0][3] = space/5;     xy[1][3] = 1;
		xy[0][4] = space/5;     xy[1][4] = 3*space/5;
		xy[0][5] = 3*space/5;   xy[1][5] = 3*space/5;
		xy[0][6] = 3*space/5;   xy[1][6] = 2*space/5;
		xy[0][7] = 2*space/5;   xy[1][7] = 2*space/5;
		xy[0][8] = 2*space/5;   xy[1][8] = space/5;
		xy[0][9] = 4*space/5;   xy[1][9] = space/5;
		xy[0][10] = 4*space/5;  xy[1][10] = 4*space/5;
		xy[0][11] = 1;	        xy[1][11] = 4*space/5;
		return xy;
	}

	@Override
	public Color getColor() {
		return new Color(139,69,19);
	}
	@Override
	public boolean effect(Unit u) {
		u.slowerSpeed();
		return true;
	}
	@Override
	public void sideEffect(Unit u) {
		u.averageSpeed();
	}
	@Override
	public void stuckOrNot(List <Point> neighbors, int i, int j) {
		neighbors.add(new Point(i, j));
	}
}
