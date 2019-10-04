package system;

import java.awt.Point;

import accessory.Matter;
import accessory.Territory;
import accessory.Unit;
import strategy.Harvester;

public class PurpleController extends Controller {

	public PurpleController(Matter[][] m, Playground ground) {
		super(m, ground);
		installingTerritory(Game.SIZETABLE-5, Game.SIZETABLE-2);
		Point p = territory.get(0);
		Territory t = (Territory) table[p.x][p.y];
		t.setController(this);
	}
	
	@Override
	protected void selectingUnit(int i, int j) {
		if(table[i][j].hasSomeonePurple()) {
			pending = table[i][j].getPurpleUnit();
			System.out.println("selectionné");
		}
	}

	@Override
	public Point setView() {
		return new Point(Game.LIMITX, Game.LIMITY);
	}

	@Override
	public void choosingPosition(Point p) {
		pending.setDestination(p);
		ground.purpleInMotion(pending);
	}
	
	@Override
	protected void createdUnit(Point p) {
		table[p.x][p.y].letSomeonePurpleIn(new Unit(Game.realPixel(p.x,p.y)));
	}
	@Override
	public void promotedHarvester() {
		int size = Game.SIZETABLE;
		pending.setStrategy(new Harvester(Game.realPixel(size-5, size-5)));
		
	}

	@Override
	public int getGold() {
		return goldInPurplePocket;
	}
	@Override
	public void addGold(int amount) {
		goldInPurplePocket+=amount;
		if(ground.gameOver())
			gameOver();
	}
	@Override
	public void removeGold(int amount) {
		goldInPurplePocket-=amount;
	}
}
