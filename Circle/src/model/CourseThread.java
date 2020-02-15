package model;

import theGame.Game;

public class CourseThread extends Thread{

	private Game game;
	
	 /**
     * Constructeur
     * @param game
     */
	public CourseThread(Game g) {
		this.game = g;
	}
	
	
	@Override
	public void run() {
		while(game.isRunning()) {

			if(!game.getPauseStatus()) {
			
				game.UpdateCourse();
//				game.CheckCollision();
				game.repaintTrue();
			
			try { Thread.sleep(15); }
		    catch (Exception e) { e.printStackTrace(); }
			
			}
			
			/* Pour faire foncitonner l'etat de PAUSE */
			try { Thread.sleep(1); }
		    catch (Exception e) { e.printStackTrace(); }
		}
		System.err.println("GameEnded(Course)");
	} 	
}
