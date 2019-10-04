package strategy;

import java.awt.Point;

import accessory.GoldMine;
import accessory.Unit;

public interface Strategy {
	
	public int [][] scrawl ();
	default void updatePosition(Point p) {}
	public boolean nextStep(Point current, Point destination);
	default public void stealing(GoldMine m, Point p) {}
	default public int givingGold() {
		return 0;
	}
	default public void lockOnto(Unit target) {}
	default public boolean needToTarget(){
		return false;
	}
	default public boolean needToMove() {
		return true;
	}
	default public boolean needToSetDestination() {return true;}
	default boolean timeToKill(long cooldown) {
		return System.nanoTime()-cooldown>999999999;
	}
}
