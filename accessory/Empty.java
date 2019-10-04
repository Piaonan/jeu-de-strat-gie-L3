package accessory;

import java.awt.Color;
import java.awt.Point;
import java.util.List;

public class Empty extends Matter{

	public Empty() {
		super(null);
	}
	
	@Override
	public Color getColor() {
		return Color.white;
	}

	@Override
	public boolean effect(Unit u) {
		return true;
	}

	@Override
	protected int[][] scrawl() {
		return null;
	}
	
	@Override
	public boolean isOccupied() {
		return false;
	}
	
	@Override
	public void stuckOrNot(List<Point> neighbors, int i, int j) {
		neighbors.add(new Point(i,j));
	}
	
}
