package accessory;

import java.awt.Point;
import java.util.List;

import strategy.Newbie;
import strategy.Strategy;
import system.Direction;
import system.Game;

public class Unit extends Accessory {
	private int durability = 15;
	protected Direction direction;
	protected Point destination;
	private List<Point> road;
	private int speed = 2;
	private Strategy strategy;
	
	public Unit (Point p) {
		super(p);
		direction = Direction.UP;
		strategy = new Newbie();
	}
	
	public void setStrategy(Strategy strat) {
		strategy = strat;
	}
	
	@Override
	protected int[][] getXy(int delayX, int delayY) {
		int [][] xy = scrawl();
		int save, size = xy[0].length;
		
		for(int i = 0; i < size; i++) {
			save = xy[0][i];
			xy[0][i]=direction.getValX(save,xy[1][i])+delayX;
			xy[1][i]=direction.getValY(save,xy[1][i])+delayY;
		}
		
		return xy;
	}
	
	@Override
	protected int[][] scrawl() {
		return strategy.scrawl();
	}
	public boolean nextStep() {
		return strategy.nextStep(current, destination);
	}
	public boolean needToTarget() {
		return strategy.needToTarget();
	}
	public void lockOnto(Unit target) {
		strategy.lockOnto(target);
		if(target!=null)
			direction = direction.aim(current, target.current);
	}
	public void stealing(GoldMine m, Point p) {
		strategy.stealing(m, p);
	}
	public int givingGold() {
		return strategy.givingGold();
	}
	
	public void setRoad(List<Point> l) {
		road = l;
	}

	public void preparation (Point departure, Point arrival) {
		if(destination.equals(departure)) {
			road.add(new Point(arrival));
		}
	}
	public void teleportation(Point arrival) {
		if(road.contains(arrival)&&!destination.equals(arrival)) {
			current.setLocation(arrival);
		}
	}
	public void slowerSpeed() {
		speed = 1;
	}
	public void averageSpeed() {
		speed = 2;
	}
	
	public Point getDestination() {
		return new Point(destination.x/Game.SQUARE, destination.y/Game.SQUARE);
	}
	public void setDestination(Point p) {
		if(strategy.needToSetDestination()) {
			destination = p;
			strategy.updatePosition(new Point(p));
		}
	}
	public Point getCurrent() {
		Point p = new Point(current);
		if(p.x%Game.SQUARE!=0||p.y%Game.SQUARE!=0)
			direction.correction(p);
		return p;
	}
	
	public void attacked() {
		durability--;
	}
	public boolean isDead() {
		return durability<=0;
	}
	
	public int durability() {
		return durability;
	}
	public boolean emptyRoad() {
		return road==null||road.isEmpty();
	}
	
	// MOUVEMENT
	public Point checkTheMove() {
		if(current.equals(road.get(0)))
			return new Point(road.get(0));
		return null;
	}
	public boolean needToMove() {
		if(road.size()==1) {
			road.remove(0);
			return false;
		}
		return strategy.needToMove();
	}
	public Point getCheckPoint() {
		Point previous = road.remove(0);
		Point next = new Point(road.get(0));
		if(previous.x!=next.x)
			direction = previous.x > next.x ? Direction.LEFT : Direction.RIGHT;
		else if(previous.y!=next.y)
			direction = previous.y > next.y ? Direction.UP : Direction.DOWN;
		return next;
	}
	public boolean move () {
		if(emptyRoad()) return false;
		Point p = road.get(0);
		if(current.x!=p.x) current.x += current.x > p.x ? -speed : speed;
		else if(current.y!=p.y) current.y += current.y > p.y ? -speed : speed;
		return true;
	}
}
