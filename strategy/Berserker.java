package strategy;

import java.awt.Point;

import accessory.Unit;
import system.Game;

public class Berserker implements Strategy{
	private Unit target;
	private boolean bloodlust;
	private long cooldown = System.nanoTime();
	
	public Berserker () {
	}

	@Override
	public int[][] scrawl() {
		int space = Game.SQUARE;
		int [][] xy = new int [2][8];
		xy[0] = new int [8];
		xy[1] = new int [8];
		
		xy[0][0] = space/2;     xy[1][0] = 1;
		xy[0][1] = space/4;     xy[1][1] = 3*space/4;
		xy[0][2] = 3*space/4;   xy[1][2] = space;
		xy[0][3] = space/4;     xy[1][3] = space;
		xy[0][4] = 3*space/4;   xy[1][4] = 3*space/4;	
		xy[0][5] = 3*space/4;   xy[1][5] = 1;	
		xy[0][6] = 5*space/8;   xy[1][6] = space/8;	
		xy[0][7] = 5*space/8;   xy[1][7] = space/2;	
		return xy;
	}
	
	@Override
	public void updatePosition(Point p) {
		bloodlust = false;
	}
	
	@Override
	public boolean nextStep(Point current, Point destination) {
		bloodlust = true;
		if(target!=null)
			destination.setLocation(target.getCurrent());
		else 
			destination.setLocation(current);
		return true;
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
		return bloodlust;
	}
	@Override
	public boolean needToSetDestination() {
		return target==null;
	}
}
