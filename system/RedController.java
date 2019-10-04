package system;

import java.awt.Point;

import accessory.Matter;
import accessory.Territory;
import accessory.Unit;
import strategy.Harvester;

public class RedController extends Controller {

	public RedController(Matter[][] s, Playground ground) {
		super(s, ground);
		installingTerritory(1, 4);
		Point p = territory.get(1);
		((Territory) table[p.x][p.y]).setController(this);
	}
	
	@Override
	protected void selectingUnit(int i, int j) {
		if(table[i][j].hasSomeoneRed()) {
			pending = table[i][j].getRedUnit();
			System.out.println("selectionné");
		}
	}

	@Override
	public Point setView() {
		return new Point(0,0);
	}

	@Override
	public void choosingPosition(Point p) {
		pending.setDestination(p);
		ground.redInMotion(pending);
	}

	@Override
	protected void createdUnit(Point p) {
		table[p.x][p.y].letSomeoneRedIn(new Unit(Game.realPixel(p.x,p.y)));
	}
	@Override
	public void promotedHarvester() {
		pending.setStrategy(new Harvester(Game.realPixel(4, 4)));
	}
	
	@Override
	public int getGold() {
		return goldInRedPocket;
	}
	@Override
	public void addGold(int amount) {
		goldInRedPocket+=amount;
		if(ground.gameOver())
			gameOver();
	}
	@Override
	public void removeGold(int amount) {
		goldInRedPocket-=amount;
	}
}
