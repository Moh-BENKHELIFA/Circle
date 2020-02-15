package view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import theGame.Game;


public class GameModeSelect extends JPanel{

	private ClassicGM Classic;
	private RoundGM Round;
	private Game game;
	private Window win;

	/**
	 * Construit le premier panel de choix de mode
	 * Cree et ajoute les boutons de choix de mode
	 * @param game
	 * @param w
	 */
	public GameModeSelect(Game game, Window w) {
		
		this.game = game;
		this.win = w;

		
		this.Classic = new ClassicGM(win, this);		
		this.Classic.setBounds(200, 200,493,382);
		this.Classic.setFocusable(false) ;


		this.Round = new RoundGM(win, this);
		this.Round.setBounds(800,200, 493,382);
		this.Round.setFocusable(false) ;


		this.add(this.Round);
		this.add(this.Classic);

		this.setLayout(null);				//We can place items everywhere on the panel

	}

/**
 * Bouton pour le mode de jeu 1
 * @author Mohamed
 */
class ClassicGM extends JButton{

	
	public ClassicGM(Window window, GameModeSelect Panel) {

		
		try {
		    Image img = ImageIO.read(getClass().getResource("/ModeSelect/Classique.png"));
		    setIcon(new ImageIcon(img));
		  } catch (Exception ex) {
		    System.err.println(ex);
		  }
		
		addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				

				Panel.game.setMode(1);
				window.changeContentPanel(game.getDisplay());
			}
		});		
	}
}


/**
 * Bouton pour le mode de jeu 2
 * @author Mohamed
 */
class RoundGM extends JButton{

	public RoundGM(Window window, GameModeSelect panel) {

		
		this.setText("Round");
		
		try {
		    Image img = ImageIO.read(getClass().getResource("/ModeSelect/Bezier.png"));
		    setIcon(new ImageIcon(img));
		  } catch (Exception ex) {
		    System.err.println(ex);
		  }
	
		
		addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				panel.removeAll();
				panel.repaint();
				panel.game.setMode(0);
				
				window.changeContentPanel(game.getDisplay());

			}
		});		
	}
}


}
