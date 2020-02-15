package model;

import java.util.Random;

import theGame.Game;
import view.viewOiseau;

public class AddBirdThread extends Thread {

	/**Constantes */
	private Game game;
	
	/**Constructeur 
	 * @param e
	 * @param vO 
	 * */
	public AddBirdThread(Game g) {
		
		this.game = g;
		
	}
	
	
	public void run() {
	
		while(game.isRunning()) {
	
			if(!game.getPauseStatus()) {
			
				game.addBird();
				game.repaintTrue();
			
			try { Thread.sleep(10000); }
		    catch (Exception e) { e.printStackTrace(); }
			
			}
	
			try { Thread.sleep(1); }
		    catch (Exception e) { e.printStackTrace(); }
		}			
	}

}
