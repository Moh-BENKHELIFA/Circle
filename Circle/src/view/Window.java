package view;

import java.awt.Component;
import javax.swing.*;

import control.KeyBoardControl;
import theGame.Game;

public class Window extends JFrame {
	
	
	
	private Game game;

	/**
	 *Constructeur de l'objet Window qui permet de creer une fenetre
	 *de dimension quelconque.
	 *On interdit la redimension de la fenetre
	 *On centre la fenetre sur l'ecran
	 * 
	 * @param width 	longeur de la fenetre
	 * @param height 	hauteur de la fenetre
	 * @param g			Jeu a afficher
	 */
	public Window(int width, int height, Game g) {
		
			this.game = g;
			g.setWindow(this);

			setTitle("Circle");
			setSize(width, height);
			setResizable(false);

		    setLocationRelativeTo(null); //Centrer la fenetre
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			setContentPane(new GameModeSelect(g, this));
			setVisible(true);

		}
	
	
	public void changeContentPanel (JPanel Panel) {
		
		this.setContentPane(Panel);	
		this.setVisible(true);
	
		game.initGame();
	}


}
