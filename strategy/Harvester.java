package strategy;

import java.awt.Point;

import accessory.GoldMine;
import system.Game;

public class Harvester implements Strategy {
	private Point goldMine;
	private Point goldReserve;
	private int gold;
	
	public Harvester (Point gR) {
		goldReserve = gR;
	}
	
	@Override
	public int[][] scrawl() {
		int space = Game.SQUARE;
		int [][] xy = new int [2][8];
		xy[0] = new int [8];
		xy[1] = new int [8];
		
		xy[0][0] = space/10;      xy[1][0] = 1;
		xy[0][1] = 2*space/5;     xy[1][1] = space/5;
		xy[0][2] = space/5;       xy[1][2] = 4*space/5;
		xy[0][3] = 4*space/5;     xy[1][3] = 4*space/5;
		xy[0][4] = 3*space/5;     xy[1][4] = space/5;	
		xy[0][5] = 9*space/10;    xy[1][5] = 1;	
		xy[0][6] = space;         xy[1][6] = space;	
		xy[0][7] = 0;             xy[1][7] = space;	
		return xy;
	}
	
	@Override
	public void stealing(GoldMine m, Point p) {
		gold = m.stolen();
		if(m.isEmpty())
			goldMine = null;
		else 
			goldMine = p;
	}
	@Override
	public int givingGold() {
		int amount = gold;
		gold = 0;
		return amount;
	}
	@Override
	public boolean nextStep(Point current, Point destination) {
		if(gold!=0) 
			destination.setLocation(goldReserve);
		else if(current.equals(goldReserve)&&goldMine!=null) 
			destination.setLocation(goldMine);
		else {
			goldMine = null;
			return false;
		}
		return true;
	}
}
