package look;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import system.Game;


public class TheMouse extends MouseAdapter {
	private Look look;
	
	public TheMouse(Look l) {
		look = l;
	}

	@Override
	public void mouseClicked(MouseEvent e) {		
		int x = e.getX()/Game.SQUARE;
		int y = e.getY()/Game.SQUARE;
		look.clickOn(x, y);
	}
}