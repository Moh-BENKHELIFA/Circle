package model;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import java.util.Random;

import theGame.Game;

public class BezierCourse {

	/*Constantes */
	private Game game;
	private ArrayList<QuadCurve2D> Courbe;
	
	private int PointListSize = 1;
	private Point lastPoint;

	private final int STARTING_Y_POS = 350;
	
    public static final Random rand = new Random();

	
    private static final int X_MAX_DISTANCE = 450; 
    private static final int X_MIN_DISTANCE = 200; 

	private static final int Y_MAX_DISTANCE = 20;
	private static final int Y_MIN_DISTANCE = 10;


    private int moves = 5;


	/* Constructeurs */
	public BezierCourse(Game g) {
		this.game = g;
		Courbe = new ArrayList();

		initCourbe();

	}
	
	
	/**
	 *  Initie la courbe de Bezier 
	 */
	private void initCourbe () {
		
		int X = 0;
		for(int i=0; i<1; i++) {
			PointListSize++;
			
			QuadCurve2D courbe = new QuadCurve2D.Double();
			
			Point first = new Point(X,STARTING_Y_POS);
			Point mid = new Point(X+150,STARTING_Y_POS);
			Point last = new Point(X+X_MAX_DISTANCE +100,STARTING_Y_POS);
			courbe.setCurve(first, mid, last);
			
			Courbe.add(courbe);
			
			lastPoint = last;
			X += X_MAX_DISTANCE;
		}

		/* Creer suffisament de points pour couvrir l'ensemble de la fenetre*/
		int j = 1500/X_MAX_DISTANCE;
		for(int i=0; i<j+1; i++) {
			addPoint();
		}
	}
	
	/**
	 * Ajoute un Point
	 */
	private void addPoint() {
		
		QuadCurve2D p = new QuadCurve2D.Double();

		int randomX = rand.nextInt(X_MAX_DISTANCE) + X_MIN_DISTANCE ;
		int randomY = rand.nextInt(Y_MAX_DISTANCE) - Y_MIN_DISTANCE;
		
		Point first = new Point(lastPoint.x, lastPoint.y);
		Point last = new Point(lastPoint.x + randomX ,lastPoint.y + randomY);
		
		randomY = rand.nextInt(300) + 100;

		Point mid;
		if(rand.nextBoolean()) {
			 mid = new Point(first.x + randomX/2 ,first.y + randomY);


		}else {
			 mid = new Point(first.x + randomX/2 ,first.y - randomY);
		}		
		p.setCurve(first, mid, last);
		
		lastPoint = last;
		this.Courbe.add(p);
		
	}
	
	/**
	 * Met a jour la position des points
	 */
	public void UpdateCourse() {
		
		for(QuadCurve2D p : this.Courbe) {
			Point temp1 = new Point((int) p.getX1() - moves, (int) p.getP1().getY());
			Point temp2 = new Point((int) p.getCtrlX() - moves, (int) p.getCtrlPt().getY());
			Point temp3 = new Point((int) p.getX2() - moves, (int) p.getP2().getY());
			
			p.setCurve(temp1, temp2, temp3);
		}
		
		lastPoint.x -= moves;
		
		PointListUpdate();
	}
	
	
	/**
	 * Met a jour la liste des points
	 */
	private void PointListUpdate() {
		if(this.Courbe.get(0).getP2().getX() < 0) {
			
			QuadCurve2D p = this.Courbe.get(0);
			this.Courbe.remove(p);	
			 addPoint();

		}
	}


	/**
	 * Retourne la liste des Points 
	 * @return
	 */
	public ArrayList<QuadCurve2D> getBezier(){
		return this.Courbe;
	}
	
	
	//TODO
	//	COLLISION
	// HAUTEUR MAX DE LA COURBE DE BEZIER 

}
