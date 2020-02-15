package model;

import theGame.Game;

public class BezierThread extends Thread{

	private Game game;
	
	public BezierThread(Game g) {
		this.game = g;
	}
	
	
	@Override
	public void run() {
		while(game.isRunning()) {
//			System.err.println(game.getRunningStatus());


			while(!game.getPauseStatus()) {
			
				game.UpdateCourse();
//				game.CheckCollision();
				game.repaintTrue();
			
			try { Thread.sleep(15); }
		    catch (Exception e) { e.printStackTrace(); }			
			}
		}
		System.err.println("GameEnded");
	} 
	
}
