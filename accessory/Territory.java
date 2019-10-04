package accessory;

import java.awt.Color;
import java.awt.Point;
import java.util.List;

import system.Controller;

public class Territory extends Matter{
	private Controller controller;
	public Territory(Point p) {
		super(p);
	}
	public void setController(Controller c) {
		controller = c;
	}
	@Override
	protected int [][] scrawl (){
		return null;
	}
	
	@Override
	public boolean effect(Unit u) {
		if(controller!=null) 
			controller.addGold(u.givingGold());
		return true;
	}

	@Override
	public Color getColor() {
		return null;
	}
	@Override
	public void stuckOrNot(List <Point> neighbors, int i, int j) {
		neighbors.add(new Point(i, j));
	}
}
