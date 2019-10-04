package system;

import java.awt.Point;

public enum Direction {
	UP, DOWN, LEFT, RIGHT;
	
	private Direction() {
	}
	public int getValX(int a, int b) {
		if(this == UP)
			return a;
		if(this == DOWN) 
			return a;
		if(this == LEFT)
			return b;
		return Game.SQUARE-b+1;
	}
	
	public int getValY(int a, int b) {
		if(this == UP) 
			return b;
		if(this == DOWN) 
			return Game.SQUARE-b+1;
		if(this == LEFT) 
			return a;
		return a;
	}
	public void correction(Point p) {
		int a = Game.SQUARE;
		if(this == DOWN)
			p.y+=a;
		else if (this == RIGHT) 
			p.x+=a;
		p.x = p.x/a*a;
		p.y = p.y/a*a;
	}
	public Direction aim(Point a, Point b) {
		Direction d = this;
		int factor = Math.abs(a.x-b.x)-Math.abs(a.y-b.y);
		if(a.x!=b.x&&factor>0)
			d = a.x > b.x ? Direction.LEFT : Direction.RIGHT;
		else if(a.y!=b.y)
			d = a.y > b.y ? Direction.UP : Direction.DOWN;
		return d;
	}
}