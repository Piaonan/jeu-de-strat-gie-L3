package strategy;

import java.awt.Point;

import accessory.Unit;
import system.Game;

public class Sentinel implements Strategy {
	private Point a;
	private Point b;
	private Unit target;
	private long cooldown = System.nanoTime();
	
	public Sentinel() {
	}
	
	@Override
	public int[][] scrawl() {
		int space = Game.SQUARE;
		int [][] xy = new int [2][7];
		xy[0] = new int [7];
		xy[1] = new int [7];
		
		xy[0][0] = 3*space/8;     xy[1][0] = space;
		xy[0][1] = 5*space/8;     xy[1][1] = space;
		xy[0][2] = space/2;       xy[1][2] = space/4;
		xy[0][3] = space/4;       xy[1][3] = 2*space/5;
		xy[0][4] = space/2;       xy[1][4] = 1;	
		xy[0][5] = 3*space/4;     xy[1][5] = 2*space/5;	
		xy[0][6] = space/2;       xy[1][6] = space/4;		
		return xy;
	}
	
	@Override
	public boolean nextStep(Point current, Point destination) {
		if(a != null && b != null) {
			if(current.equals(a))
				destination.setLocation(b);
			else
				destination.setLocation(a);
			return true;
		}
		return false;
	}
	
	@Override
	public void updatePosition(Point p) {
		if(b != null) {a = p; b = null;}
		else if(a == null) a = p;
		else if(!a.equals(p)) b = p;
	}
	
	@Override
	public void lockOnto(Unit u) {
		target = u;
		if(target!=null&&timeToKill(cooldown)) {
			target.attacked();
			cooldown = System.nanoTime();
		}
	}
	@Override
	public boolean needToTarget() {
		return a!=null&&b!=null&&timeToKill(cooldown);
	}
	@Override
	public boolean needToMove() {
		return target==null;
	}
	@Override
	public boolean needToSetDestination() {
		return target==null;
	}
}
