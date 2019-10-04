package system;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.Timer;

import accessory.Empty;
import accessory.GoldMine;
import accessory.Matter;
import accessory.Unit;

public class Playground extends Observable {

	private final static int SIZE = 5;
	private static Matter [][] table;
	private List<Unit> redsInMotion = new ArrayList<>();
	private List<Unit> purplesInMotion = new ArrayList<>();
	private static List<Point> mines = new ArrayList<>();
	
	public Playground(Matter [][] m) {
		super();
		table = m;
	}
	
	public void setElements() {
		setGoldMines();
		Factory.createTeleporter(table, new Point(0,6), new Point(6,6));
		for(int i = 0; i < SIZE*4; i++) {
			for(int j = 0; j < SIZE; j++) {
				Factory.createTree(table);
				Factory.createMud(table);
			}
			Factory.createTeleporter(table);
		}
	}
	private void setGoldMines() {
		for(int i = 0; i < SIZE; i++)
			mines.add(Factory.createGoldMine(table));
	}
	
	private Timer motion = new Timer(15, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(redsInMotion.isEmpty()&&purplesInMotion.isEmpty())
				motion.stop();
			Unit u;
			for(int i = 0; i < redsInMotion.size(); i++) 
				if(!timeToMove((u=redsInMotion.get(i))))
					if(!loop(u))
						redsInMotion.remove(i);
			
			for(int i = 0; i < purplesInMotion.size(); i++) 
				if(!timeToMove((u=purplesInMotion.get(i))))
					if(!loop(u))
						purplesInMotion.remove(i);
			
			
			alertAll(null);
		}
	});
	
	
	/* 
	 * La méthode vérifie d'abord s'il y a une case à parcourir,
	 * si l'unité a fini de parcourir une cause, on libère la case du point,
	 * puis on récupère les coordonnées de la prochaine case à parcourir,
	 * on déplace ensuite l'unité si la case le permet.
	 * @param u La méthode prend l'unité à traiter
	 * @return true si l'unité doit s'arrêter, sinon false
	 */
	private boolean timeToMove(Unit u) {
		if(u.emptyRoad()) return false;
		Point previous, next;
		
		if((previous=u.checkTheMove())!=null){
			fighting(previous,u);
			if(u.needToMove()) {
				next = realIndex(u.getCheckPoint());
				table[previous.x][previous.y].sideEffect(u);
				if(table[next.x][next.y].effect(u))
					table[previous.x][previous.y].changePlace(u,table[next.x][next.y]);
			}
		}
		return u.move();
	}
	private void fighting(Point p, Unit u) {
		realIndex(p);
		if(!u.needToTarget()) return;
		Unit target = null;
		List <Point> l = new ArrayList<>();
		l.add(p);
		
		target = searchTarget(l,new ArrayList<>(),0,redsInMotion.contains(u));
		
		u.lockOnto(target);
		if(target!=null&&target.isDead()) {
			p = realIndex(target.getCurrent());
			table[p.x][p.y].changePlace(target, null);
			removeSomeoneInMotion(target);
		}
	}
	
	private Unit searchTarget(List <Point> perimeter, List <Point> visited, int rayon, final boolean side) {
		List <Point> newPerimeter = new ArrayList<>();
		Unit target = null;
		
		for(Point p : perimeter) {
			visited.add(p);
			if((target=table[p.x][p.y].getTarget(side))!=null) {
				//System.out.println("ennemi à x="+p.x+",y="+p.y);
				return target;
			}
			for(Point q : getNeighbors(p, null))
				if(!visited.contains(q)&&table[q.x][q.y].shortcut()==null)
					newPerimeter.add(q);
		}
		
		if(rayon == 3) {
			//System.out.println("pas d'ennemi trouvé");
			return null;
		}
		return searchTarget(newPerimeter,visited,rayon+1,side);
	}
	
	private boolean loop(Unit u) {
		if(u.nextStep()) {
			road(u);
			return true;
		}
		return false;
	}
	
	
	//Recherche d'un chemin
	void redInMotion(Unit pending) {
		if(redsInMotion.isEmpty()) 
			motion.start();
		road(pending);
		if(!redsInMotion.contains(pending))
			redsInMotion.add(pending);
	}
	void purpleInMotion(Unit pending) {
		if(purplesInMotion.isEmpty()) 
			motion.start();
		road(pending);
		if(!purplesInMotion.contains(pending))
			purplesInMotion.add(pending);
	}
	
	private void road(Unit u){
		Point [][] root = new Point[Game.SIZETABLE][Game.SIZETABLE];
		for (int i = 0; i < Game.SIZETABLE; i++) 
			root[i] = new Point[Game.SIZETABLE];
		List <Point> way = new ArrayList<>();
		
		Point in = realIndex(u.getCurrent());
		way.add(in);
		Point p = u.getDestination();
		if(searchRoad(p,way,root)) {
			way.clear();
			while(!p.equals(in)) {
				way.add(0,Game.realPixel(p.x, p.y));
				p = root[p.x][p.y];
			}
			way.add(0,Game.realPixel(in.x,in.y));
			u.setRoad(way);
			table[in.x][in.y].effect(u);
		}
		else u.setRoad(null);
	}
	private boolean searchRoad(Point stop, List<Point> way, Point[][] root){
		if(way.size()==0) return false;
		List<Point> newWay = new ArrayList<>();
		for(Point p : way) {
			for(Point q : getNeighbors(p, stop)) {
				if(root[q.x][q.y]==null) {
					root[q.x][q.y] = p;
					if(q.equals(stop)) return true;
					shortcut(stop, newWay, root, q);
				}
			}
		}
		return searchRoad(stop, newWay, root);
	}
	private List<Point> getNeighbors(Point p, Point stop){ 
		List<Point> neighbors = new ArrayList<>();
		
		if(p.x > 0)
			test(neighbors, stop, p.x-1,p.y);
		
		if(p.x < Game.SIZETABLE-1)
			test(neighbors, stop, p.x+1,p.y);
		
		if(p.y > 0)
			test(neighbors, stop, p.x,p.y-1);
		
		if(p.y < Game.SIZETABLE-1)
			test(neighbors, stop, p.x,p.y+1);
		
		return neighbors;
	}
	private void test(List<Point> neighbors, Point stop, int i, int j) {
		if(stop != null && stop.x == i && stop.y == j)
			neighbors.add(new Point(i,j));
		else 
			table[i][j].stuckOrNot(neighbors, i, j);
	}
	private void shortcut(Point stop, List<Point> newWay, Point[][] root, Point q) {
		Point p = null;
		if((p=table[q.x][q.y].shortcut())!=null&&!stop.equals(p)) {
			newWay.add(p);
			root[p.x][p.y] = q;
		} else
			newWay.add(q);
	}
	
	
	
	//Autre
	void alertAll(String s) {
		setChanged();
	    notifyObservers(s);
	}
	
	private static Point realIndex(Point p){
		p.x/=Game.SQUARE;
		p.y/=Game.SQUARE;
		return p;
	}
	
	private void removeSomeoneInMotion(Unit u) {
		if(redsInMotion.contains(u))
			redsInMotion.remove(u);
		else if(purplesInMotion.contains(u))
			purplesInMotion.remove(u);
	}
	
	public static void checkGoldMines() {
		Point p; GoldMine m;
		for(int i = 0; i < mines.size(); i++) {
			p = mines.get(i);
			m = (GoldMine) table[p.x][p.y];
			if(m.isEmpty()) {
				table[p.x][p.y] = new Empty();
				mines.remove(p);
				i--;
			}
		}
	}
	boolean gameOver() {
		if(mines.isEmpty()) {
			motion.stop();
			return true;
		}
		return false;
	}
	
	// Mode pour les tests de fin
	void specialMode() {
		Point p;
		for(int i = 0; i < mines.size(); i++) {
			p = mines.get(i);
			table[p.x][p.y] = new Empty();
			mines.remove(p);
			i--;
		}
		table[7][7] = new GoldMine(30,Game.realPixel(7, 7));
		mines.add(new Point(7,7));
		alertAll(null);
	}
}
