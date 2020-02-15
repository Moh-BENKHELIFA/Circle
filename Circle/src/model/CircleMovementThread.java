package model;

import theGame.Game;
import view.Affichage;

public class CircleMovementThread extends Thread{

	private Game game;
	
    /**
     * Constructeur de la classe CircleMovementThread 
     * @param game
     */
	public CircleMovementThread(Game game) {
		this.game = game;
	}
	
	
	@Override
	public void run() {
		while(game.isRunning()) {
			
			if(!game.getPauseStatus()) {
				
				game.UpdateCircleState();			
				game.repaintTrue();

			try { Thread.sleep(game.getCircleDelai()); }
		    catch (Exception e) { e.printStackTrace(); }
			}
			
			/* Pour faire fonctionner l'etat de PAUSE */
			try { Thread.sleep(0); }
		    catch (Exception e) { e.printStackTrace(); }
		}
		System.err.println("GameEnded(Circle)");
	} 
}
