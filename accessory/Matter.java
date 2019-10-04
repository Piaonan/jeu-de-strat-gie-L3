package accessory;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

abstract public class Matter extends Accessory{
	private List<Unit> reds = new ArrayList<>();
	private List<Unit> purples = new ArrayList<>();
	
	public Matter(Point p) {
		super(p);
	}
	
	public abstract Color getColor();
	public boolean isOccupied() {
		return true;
	}
	public Point shortcut() {
		return null;
	}
	public boolean effect(Unit u) {
		u.setRoad(null);
		return false;
	}
	public void sideEffect(Unit u) {}
	public void stuckOrNot(List<Point> neighbors, int i, int j) {}
	
	public Unit [] getReds(){
		Unit [] t = new Unit[reds.size()];
		return reds.toArray(t);
	}
	public Unit [] getPurples(){
		Unit [] t = new Unit[purples.size()];
		return purples.toArray(t);
	}
	
	public boolean hasSomeoneRed() {
		return !reds.isEmpty();
	}
	public boolean hasSomeonePurple() {
		return !purples.isEmpty();
	}
	public void letSomeoneRedIn(Unit u) {
		reds.add(u);
	}
	public Unit getRedUnit() {
		return reds.get(0);
	}
	public void letSomeonePurpleIn(Unit u) {
		purples.add(u);
	}
	public Unit getPurpleUnit() {
		return purples.get(0);
	}
	public void changePlace(Unit u, Matter m) {
		if(reds.contains(u)) {
			reds.remove(u);
			if(m!=null) m.reds.add(u);
		}
		else if(purples.contains(u)) {
			purples.remove(u);
			if(m!=null) m.purples.add(u);
		}
		else 
			System.out.println("raté");
	}
	public Unit getTarget(final boolean side) {
		if(side&&!purples.isEmpty()) 
			return purples.get(0);
		else if(!side&&!reds.isEmpty()) 
			return reds.get(0);
		
		return null;
	}
}
