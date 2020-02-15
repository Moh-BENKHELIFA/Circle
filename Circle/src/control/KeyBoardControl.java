package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.CircleState;
import theGame.Game;

public class KeyBoardControl implements KeyListener{
				
	private Game game;
	
	/**
	 * Constructeur de la classe Control 
	 * Permet de gerer les interactions de l'utilisateur
	 * 
	 * @param g	  
	 */
	public KeyBoardControl(Game g) {
		this.game = g;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(game.isRunning()) {										//Quand le jeu est en pleine partie
			if(e.getKeyCode() == 32) {								//La touche espace du clavier
				if(!game.getPauseStatus()) {						//Si le jeu n'est pas en pause
					game.CircleJump();								//Effectue l'action de saut
				}
					
			}	
			
			if(e.getKeyCode() == 80) {								//La touche p du clavier
				game.Pause();										//Met le jeu en pause
				if(game.getPauseStatus())
					System.out.println("PAUSE");
				else
					System.out.println("PLAY");			
			}
		}
		else {														//Quand la partie n'a pas encore commence 
		
			if(e.getKeyCode() == 83) {								//La touche s du clavier
				game.Start();										//Debute la partie
					System.out.println("PLAY");			
			}
		}
		
		if(e.getKeyCode() == 78) {									//La touche n du clavier
			game.NewGame();								
			game.repaintTrue();
		}	
	}

	@Override
	public void keyReleased(KeyEvent e) {}
	
	@Override
	public void keyTyped(KeyEvent e) {}
}
