package view;

import theGame.Game;

public class DisplayThread extends Thread{
	
	private Game game;
	private Window window;
	private Affichage affi;
	private boolean repaint;
	
	public DisplayThread(Game game, Window window) {
		this.game = game;
		this.window = window;
		
	}

	public DisplayThread(Game game) {
		this.game = game;		
	}
	
	public Game getGame() {
		return this.game;
	}
	
	
	
	public void rePaint() {
		this.repaint = true;
	}
	
	
	public void run() {
		
//		while(game.getRunning()) {			
//
//			if(this.repaint) {
//				game.getDisplay().repaint();
////				System.out.println(this.game.getCircleState().getYPos());
//				this.repaint = false;
//			}
//
//				
//			try { Thread.sleep(42); }
//		    catch (Exception e) { e.printStackTrace(); }
//			
////		System.err.println("Oups");	
//			
//		}
		
		
	}
}
