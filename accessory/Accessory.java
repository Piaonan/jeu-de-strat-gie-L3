package accessory;

import java.awt.Point;

public abstract class Accessory {
	protected Point current;
	
	public Accessory(Point p) {
		current = p;
	}
	
	
	protected abstract int [][] scrawl ();

	public int [][] getXy(Point p){
		if(current==null) return null;
		return getXy(current.x - p.x, current.y - p.y);
	}
	protected int[][] getXy(int delayX, int delayY) {
		int [][] xy = scrawl();
		if(xy==null) return null;
		int size = xy[0].length;
		
		for(int i = 0; i < size; i++) {
			xy[0][i]+=delayX;
			xy[1][i]+=delayY;
		}			
		
		return xy;
	}
}
