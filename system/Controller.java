package system;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import accessory.Matter;
import accessory.Territory;
import accessory.Unit;
import strategy.Berserker;
import strategy.Sentinel;

abstract public class Controller {
	protected Matter [][] table;
	protected Playground ground;
	protected Unit pending;
	protected List<Point> territory;
	protected static int goldInRedPocket = 200;
	protected static int goldInPurplePocket = 200;
	
	public Controller (Matter [][] m, Playground p) {
		table = m;
		ground = p;
	}
	
	protected void installingTerritory(int a, int b) {
		territory = new ArrayList<>();
		installedTerritory(territory,a,a);
		installedTerritory(territory,b,b);
		installedTerritory(territory,a,b);
		installedTerritory(territory,b,a);
		for(int i = a+1; i < b; i++) {
			installedTerritory(territory,a,i);
			installedTerritory(territory,i,a);
			
			installedTerritory(territory,b,i);
			installedTerritory(territory,i,b);
		}
	}
	private void installedTerritory(List<Point> territory, int i, int j) {
		table[i][j] = new Territory(Game.realPixel(i,j));
		territory.add(new Point(i,j));
	}
	
	abstract public Point setView();
	
	// SOURIS
	abstract protected void selectingUnit(int i, int j);
	abstract public void choosingPosition(Point p);
	public void mouseAlert(int i, int j) {
		if(pending==null||pending.isDead()) selectingUnit(i,j);
		else choosingPosition(Game.realPixel(i, j));
	}
	
	// CLAVIER (Affectation des rôles)
	
	abstract void promotedHarvester();
	public void promotingHarvester() {
		if(notEnough(0)) return;
		promotedHarvester();
		ground.alertAll(null);
	}
	public void promotingSentinel() {
		if(notEnough(20)) return;
		pending.setStrategy(new Sentinel());
		ground.alertAll(null);
	}
	public void promotingFighter() {
		if(notEnough(30)) return;
		pending.setStrategy(new Berserker());
		ground.alertAll(null);
	}
	abstract protected void createdUnit(Point p); 
	public void creatingUnit() {
		if(getGold()<50) return;
		removeGold(50);
		createdUnit(territory.get(random()));
		ground.alertAll(null);
	}
	public void switching() {
		pending = null;
	}
	
	// Autre
	
	private boolean notEnough (int cost) {
		if(pending==null||pending.isDead()||getGold()<cost) {
			if(pending!=null&&pending.isDead())
				pending = null;
			return true;
		}
		removeGold(cost);
		return false;
	}
	private static int random() {
		return (int)(Math.random() * 12);
	}
	abstract public int getGold();
	abstract public void addGold(int amount);
	abstract protected void removeGold(int amount);
	protected void gameOver() {
		if(goldInRedPocket>goldInPurplePocket)
			ground.alertAll("Victoire du peuple rouge.");
		else if(goldInRedPocket<goldInPurplePocket)
			ground.alertAll("Victoire du peuple violet.");
		else 
			ground.alertAll("Egalité.");
	}
	public void specialMode() {
		pending = null;
		ground.specialMode();
	}
}
