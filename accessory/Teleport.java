package accessory;

import java.awt.Color;
import java.awt.Point;
import java.util.List;

import system.Game;

public class Teleport extends Matter{
	private final Point arrival;
	public Teleport(Point t, Point p) {
		super(t);
		arrival = p;
	}
	
	@Override
	protected int[][] scrawl() {
		int space = Game.SQUARE;
		int [][] xy = new int [2][9];
		xy[0] = new int [9];
		xy[1] = new int [9];
		
		xy[0][0] = 1;             xy[1][0] = space/2;
		xy[0][1] = space;         xy[1][1] = space/2;
		xy[0][2] = 1;             xy[1][2] = space;
		xy[0][3] = space/2;       xy[1][3] = 1;
		xy[0][4] = space;         xy[1][4] = space;
		xy[0][5] = space;         xy[1][5] = 1;
		xy[0][6] = 1;             xy[1][6] = 1;
		xy[0][7] = 1;             xy[1][7] = space;
		xy[0][8] = space;         xy[1][8] = space;
		return xy;
	}

	@Override
	public Color getColor() {
		return new Color(186,85,211);
	}
	@Override
	public boolean effect(Unit u) {
		u.preparation(current, arrival);
		return true;
	}
	@Override
	public void sideEffect(Unit u) {
		u.teleportation(arrival);
	}
	@Override
	public Point shortcut() {
		return new Point(arrival.x/Game.SQUARE, arrival.y/Game.SQUARE);
	}
	@Override
	public void stuckOrNot(List <Point> neighbors, int i, int j) {
		neighbors.add(new Point(i, j));
	}
}
