package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Course {

	/** Constantes */
	private ArrayList<Point> Courbe;
	private int PointListSize = 1;
	private Point lastPoint;
	
	private int LargeurPanel;
    public static final Random rand = new Random();
    private int moves = 5;
    private int Xposition = 0;
    private int STARTING_Y_POS = 350;

    private static final int X_DISTANCE = 300; 
    private static final int Y_MAX_DISTANCE = 100; 

    
    /**Constructeur*/
	public Course(int x) {
		this.LargeurPanel = x + X_DISTANCE;
		Courbe = new ArrayList<Point>();
		init_Courbe(this.LargeurPanel);
	}
	
	public Course() {
		this.LargeurPanel = 1500;
		Courbe = new ArrayList<Point>();
		init_Courbe(this.LargeurPanel);
	}
    
	/**
	 * Initie le parcours
	 * Ajout de quelques points au parcours pour couvrir l'ensemble de la fenetre
	 * @param LargeurPanel
	 */
	private void init_Courbe(int LargeurPanel) {
		
		/* Ajoute les premiers points pour former une ligne droite au depart */
		Courbe.add(new Point(this.Xposition,this.STARTING_Y_POS));
		
		for(int i=0; i<3; i++) {
			this.Xposition += X_DISTANCE;
			this.Courbe.add(new Point(this.Xposition,this.STARTING_Y_POS));
			this.PointListSize++;
		}
		
		 this.lastPoint = new Point(this.Xposition,this.STARTING_Y_POS);
		
		for(int i=0; i<7; i++) {
			
			int random=rand.nextInt(Y_MAX_DISTANCE) - 50;
			Xposition += X_DISTANCE;
			System.out.println(lastPoint.y );
			int y = lastPoint.y + random;
			if(y <200 || y >600) {
				y = lastPoint.y - random;
			}
			
			
			Point p = new Point(Xposition, y);
			Courbe.add(p);
			PointListSize++;
			
			lastPoint = p;
			
		}
		
		this.lastPoint = Courbe.get(PointListSize-1);

	}
	
	/**
	 * Modifie la position x des points du parcours
	 */
	public void UpdateCourse() {
		for(Point p : this.Courbe) {
			p.x -= this.moves;
		}
		PointListUpdate();
	}
	
	
	
    /**
     * Si le point le plus a gauche n'est plus sur l'ecran
     * On le retire de la liste
     * On modifie sa position x pour 
     * Lui donne une nouvelle position Y
     * et l'ajoute a la fin de la liste
     */
	private void PointListUpdate() {
		
		if(this.Courbe.get(0).x < -X_DISTANCE) {
			
			Point temp = this.Courbe.get(0);
			
			this.Courbe.remove(temp);
			temp.x = this.Courbe.get(this.Courbe.size()-1).x + X_DISTANCE;
			
			int random = rand.nextInt(Y_MAX_DISTANCE) - 100;
			temp.y = lastPoint.y + random;

			if(temp.y <200 || temp.y >700) {
				temp.y = lastPoint.y - random;
			}
			
			lastPoint = temp;
			
			this.Courbe.add(temp);
		}
	}
	 
	/**
	 * Retourne la liste de points
	 * @return
	 */
	public ArrayList<Point> getPointList(){
		return this.Courbe;
	}

}
