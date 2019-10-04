package system;

import java.awt.Point;

import accessory.GoldMine;
import accessory.Matter;
import accessory.Mud;
import accessory.Teleport;
import accessory.Tree;

public class Factory {
	public static void createTree(Matter[][] table) {
		Point p = randomPoint(table);
		table[p.x][p.y] = new Tree(Game.realPixel(p.x,p.y));
	}
	public static void createMud(Matter[][] table) {
		Point p = randomPoint(table);
		table[p.x][p.y] = new Mud(Game.realPixel(p.x,p.y));
	}
	public static void createTeleporter(Matter[][] table, Point p, Point q) {
		Point departure = Game.realPixel(p.x,p.y);
		Point arrival = Game.realPixel(q.x,q.y);
		table[p.x][p.y] = new Teleport(departure, arrival);
		table[q.x][q.y] = new Teleport(arrival, departure);
	}
	public static void createTeleporter(Matter[][] table) {
		Point p = randomPoint(table);
		Point q = randomPoint(table);
		Point departure = Game.realPixel(p.x,p.y);
		Point arrival = Game.realPixel(q.x,q.y);
		table[p.x][p.y] = new Teleport(departure, arrival);
		table[q.x][q.y] = new Teleport(arrival, departure);
	}
	public static Point createGoldMine(Matter[][] table) {
		Point p = randomPoint(table);
		table[p.x][p.y] = new GoldMine(random(7,13)*10,Game.realPixel(p.x, p.y));
		return p;
	}
	
	
	private static int random(int min, int max) {
		return min + (int)(Math.random() * ((max - min) + 1));
	}
	private static Point randomPoint(Matter[][] table) {
		Point p = new Point();
		do {
			p.x = (int)(Math.random() * Game.SIZETABLE);
			p.y = (int)(Math.random() * Game.SIZETABLE);
		} while(table[p.x][p.y].isOccupied());
		return p;
	}
}
