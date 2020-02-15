package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import theGame.Game;
import model.*;

public class MouseControl implements MouseListener {
	
	
	private Game game;
	
	/**
	 * Constructeur de la classe Control 
	 * Permet de gerer les interactions de l'utilisateur
	 * 
	 * @param a   Le panel ou il faudra rafraichir l'affichage
	 * @param e	  L'etat qui sera modifie
	 */
	public MouseControl(Game game) {
		this.game = game;
	}

	/**
	 * Modifie l'emplacement de l'ovale lors d'un clic
	 * 
	 * @param arg0 	MouseEvent
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getButton()==1) {								//Clic gauche de la souris
			if(!game.getPauseStatus()) {						//Si le jeu n'est pas en pause
				game.CircleJump();								//Effectue l'action de saut
			}
		}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

}