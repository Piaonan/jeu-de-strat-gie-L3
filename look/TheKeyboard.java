package look;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import system.Controller;
import system.Game;

public class TheKeyboard extends KeyAdapter {
	
	private Look look;
	private Controller controller;
	
	public TheKeyboard(Look l, Controller c) {
		super();
		look = l;
		controller = c;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			look.setView(0,-1);
			break;
		case KeyEvent.VK_DOWN:
			look.setView(0,1);
			break;
		case KeyEvent.VK_LEFT:
			look.setView(-1,0);
			break;
		case KeyEvent.VK_RIGHT:
			look.setView(1,0);
			break;
		case KeyEvent.VK_F1:
			controller.promotingHarvester();
			break;
		case KeyEvent.VK_F2:
			controller.promotingSentinel();
			break;
		case KeyEvent.VK_F3:
			controller.promotingFighter();
			break;
		case KeyEvent.VK_F4:
			controller.creatingUnit();
			break;
		case KeyEvent.VK_ESCAPE:
			controller.switching();
			break;
		case KeyEvent.VK_NUMPAD7:
			look.superView(0,0);
			break;
		case KeyEvent.VK_NUMPAD3:
			look.superView(Game.LIMITX, Game.LIMITY);
			break;
		case KeyEvent.VK_ENTER:
			controller.specialMode();
			break;
		default:
			System.out.println("commande inexistante");
			break;
		}
	}	
}
