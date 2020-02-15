package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import control.KeyBoardControl;
import model.CircleState;
import theGame.Game;

public class Affichage extends JPanel{

	/* Constantes */
	private Game game;
	private Timer timer;
	public BufferedImage image ;

	private viewOiseau VO;
	
	public boolean repaint = false;

	/**
	 * Constructeur  
	 */
	public Affichage(Game g) {
		this.game = g;
		loadImage();
		timer = createTimer ();
	    timer.start ();
	    VO = new viewOiseau();
	}
	
	/**
	 * Cree un timer pour mettre a jour l'affichage
	 * @return
	 */
	private Timer createTimer ()
	  {
	    // Création d'une instance de listener 
	    // associée au timer
	    ActionListener action = new ActionListener ()
	      {
	        // Méthode appelée à chaque tic du timer
	        public void actionPerformed (ActionEvent event)
	        {
	          // Repaint si il faut
	          if (game.getRepaint()) {            
	            repaint();
	          	repaint= false;
	          }
	        }
	      };
	      
	    // Création d'un timer qui génère un tic
	    // chaque 1 millième de seconde
	    return new Timer (1, action);
	  }  
	 
	
	/**
	 * Charge l'image du cercle
	 */
	private void loadImage() {
		try {	
			image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("Circle.png"));
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}
	 
	/**
	 * Dessine le cercle dans le panel
	 * @param g
	 */
	 private void draw_Circle(Graphics g) {
		
		int x = game.getCircleState().getXPos();
		int y = game.getCircleState().getYPos();
		
//		g.drawOval(x, y, 100, 200);
		
		g.drawImage(image, x, y, null);
		
		//Points de collision
//		g.setColor(Color.red);
//		g.drawRect(x+58, y+19, 1, 1);
//		g.drawRect(x+58, y+152, 1, 1);
//		g.setColor(Color.black);

	 }
	
	/**
	 * Dessine le parcours dans le panel
	 * @param g
	 */
	 private void drawCourse(Graphics g) {
		ArrayList<Point> p = game.getPointList();
		int length = p.size();
		
		for(int i=0; i<length-1 ; i++) {
			g.drawLine(p.get(i).x, p.get(i).y, p.get(i+1).x, p.get(i+1).y);		
		}
				
		//Point de collision
//		g.drawOval(game.collision.x, game.collision.y-5, 1, 10);

	 }
	
	/**
	 * Dessine le score dans le Panel
	 * @param g
	 */
	 private void drawScore(Graphics g) {
		g.drawString(Integer.toString(game.getScore()),10, 20);
		if(game.getLost())
			g.drawString("Votre score est de : " + Integer.toString(game.getScore()),650, 600);
	 }
	
	/**
	 * Dessine les oiseaux dans le panel
	 * @param g
	 */
	 private void drawBirds(Graphics g) {		
		VO.draw(g, game.getBirdsList());
	 }
	
	/**
	 * Dessine les courbes de Bezier dans le panel
	 * @param g
	 */
	 private void paint_courbe(Graphics g) {
		Graphics2D g1 = (Graphics2D) g;
		
		ArrayList<QuadCurve2D> Courbe = game.getBezier();
		for(QuadCurve2D p : Courbe) {
			g1.draw(p);
		}
	}
	
	/**
	 * affiche les zones de collision de Bezier
	 * @param g
	 */
	 public void test(Graphics g) {
	 	g.setColor(Color.green);

		ArrayList<QuadCurve2D> list = game.getBezier();
		CircleState Circle = game.getCircleState();
		
		int TopYCircle = Circle.getYPos() + 19;
		int BotYCircle = Circle.getYPos() + 152;

		if(list.get(0).getX2() < Circle.getXPos() + 58) {
			g.drawRect((int) list.get(1).getX2(), (int) list.get(1).getY2()-40, 1, 80);

			if(list.get(1).contains(Circle.getXPos() + 58, TopYCircle) || list.get(1).contains(Circle.getXPos() + 58, BotYCircle) ) {
				
				g.setColor(Color.red);

				g.drawRect(Circle.getXPos() + 58, TopYCircle, 10, 10);
				g.drawRect(Circle.getXPos() + 58, BotYCircle, 10, 10);

				
			}
			g.setColor(Color.red);

			g.drawRect((int) list.get(1).getCtrlX(), (int) list.get(1).getCtrlY(), 10, 10);

			g.setColor(Color.blue);

			Rectangle i = list.get(1).getBounds();
			g.drawRect(i.x, i.y, i.width, i.height);
		}else {
			
			g.setColor(Color.red);

			g.drawRect((int) list.get(1).getCtrlX(), (int) list.get(1).getCtrlY()-4, 1, 8);
			
			g.setColor(Color.blue);

			Rectangle i = list.get(1).getBounds();
			g.drawRect(i.x, i.y, i.width, i.height);
			g.setColor(Color.pink);

			g.drawRect((int) list.get(0).getX2(), (int) list.get(0).getY2()-40, 1, 80);
			if(list.get(1).contains(Circle.getXPos() + 58, TopYCircle) || list.get(1).contains(Circle.getXPos() + 58, BotYCircle) ) {
				
				g.setColor(Color.red);
				g.drawRect(Circle.getXPos() + 58, TopYCircle, 10, 10);

				g.drawRect(Circle.getXPos() + 58, BotYCircle, 10, 10);

				System.err.println("COLLISION\nPERDU");
			}
		}
		g.setColor(Color.black);

	}
	
//	@Override
	public void paint(Graphics g) {
		super.paint(g);
//		g.clearRect(0, 0, 1500, 800);


		drawBirds(g);

		draw_Circle(g);
		
		if(game.getMode() == 1 ) {
			drawCourse(g);
		}else {
			paint_courbe(g);
//			test(g);
		}
		drawScore(g);
		
		if(!game.isRunning() && !game.getLost())
			g.drawString("Appuyez sur la touche \"s\" pour d�buter la partie ",600, 600);


	}
	

	
}
