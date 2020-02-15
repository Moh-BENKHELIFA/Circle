package theGame;

import control.*;
import model.*;
import view.*;

import java.awt.Point;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;


public class Game {
	
	public boolean running;													//Partie en cours ou non
	private boolean pause;													//Partie en pause ou non
	private boolean lost;													//Partie perdu ou non	
	private boolean repaint;												//Boolean qui indique si l'ont doit repaint

	private int mode;														//mode de jeu choisi
	
	private Window window;													//Fenetre d'affichage du jeu
	private Affichage display;												//Panel d'affichage du jeu
	
	public Point collision;													//Point de collision entre le cercle et le parcours

	
	/* Constantes sur l'etat du jeu */
	private CircleState Circle;
	private CircleMovementThread CircleMovementThread;
	
	private CourseThread CourseThread;
	private Course parcours;
	
	private BezierCourse Bezier;
	private BezierThread BezierThread;
	
	private AllBirds birds;
	private AddBirdThread ABT;
	
	private int SCORE = 0;
	
	/**
	 * Constructeur
	 */
	public Game() {
		
		this.running = false;
		this.lost = false;
		this.display = new Affichage(this);
		
	}
	
	
	/**
	 * Initie la partie
	 * Creation et ajout des differents controleurs
	 * Creation et ajout des differents model du jeu
	 */
	public void initGame() {
		
		/*Add Controlers*/
		KeyBoardControl ctrl = new KeyBoardControl(this);
		MouseControl ctrl2 = new MouseControl(this);
		window.addKeyListener(ctrl);
		window.addMouseListener(ctrl2);
		
		this.Circle = new CircleState();
		this.CircleMovementThread = new CircleMovementThread(this);

		/* Creation du parcours selon le mode choisis */
		if(this.mode == 1) {
			this.parcours = new Course(1500);
			this.CourseThread = new CourseThread(this); 
			this.CheckCollision();
		}else {
			this.Bezier = new BezierCourse(this);
			this.BezierThread = new BezierThread(this);
			this.CheckCollision();
		}
		
		/* Creation/Ajout des oiseaux */
		this.birds = new AllBirds(this);
		this.ABT = new AddBirdThread(this);
		
	}
	
	/**
	 * Debute la partie en demarrant les differents Thread
	 */
	public void Start() {
		
		this.running = true;
		this.CircleMovementThread.start();
		
		if(this.mode ==1) {
			this.CourseThread.start();
		}else {
			this.BezierThread.start();	
		}
		
		StartBirds();
		this.ABT.start();
	}

	/**
	 * Met en pause le jeu
	 */
	public void Pause() {
		
		if(this.pause) {
			this.pause = false;			
		}
		else {
			this.pause = true;
		}
	}
	
	/**
	 * Debute le thread de tout les oiseaux
	 */
	private void StartBirds() {
		for(Bird b : this.birds.getList()) {
			b.start();
		}
	}
	
	
	/**
	 * TODO DEBGUG
	 * Debute une nouvelle partie
	 */
	public void NewGame() {
		
		this.running = false;
		this.Circle = new CircleState();
		parcours = new Course(1500);
		CircleMovementThread = new CircleMovementThread(this);
		CourseThread = new CourseThread(this); 
		
		this.CheckCollision();
	}
	
	/**
	 * Arrete le jeu
	 */
	private void StopTheGame() {
		this.running = false;
		System.err.println("GAME STOPPED");
	}
	
	/**
	 * Met a jour l'etat du cercle
	 */
	public void UpdateCircleState() {
		this.Circle.movement();		
	}
	
	/**
	 * Met a jour l'etat du parcours
	 */
	public void UpdateCourse() {
		if(mode == 1) {
			this.parcours.UpdateCourse();
		}else {
			this.Bezier.UpdateCourse();
		}
		
		CheckCollision();
		
	}
	
	/**
	 * Calcul la posisition du point de collision entre le cercle et le parcours
	 */
	private void CalculateCollision() {

		Point RightPoint = parcours.getPointList().get(2);
		Point LeftPoint = parcours.getPointList().get(1);
		
		int XCircle = Circle.getXPos() + 58;
		
		if(XCircle == RightPoint.x) {
			collision = RightPoint;
		}
		else {
			int y = point2points(LeftPoint,RightPoint, XCircle);
			collision =  new Point(XCircle ,RightPoint.y-y);
		}
	}
	
	
	/**
	 * Renvoi le milieu d'un vecteur de deux points
	 * 
	 * @param a 		le point a gauche du cercle
	 * @param b			le point a droite du cercle
	 * @param XCircle 	la position x du cercle
	 */
	private int point2points(Point a, Point b, int XCircle) {
		
		Point vecteur = new Point(a.x - b.x, a.y-b.y);
		float posX = b.x - XCircle;

		float f = ((posX)/vecteur.x);
		
		float i = (float) vecteur.y * f;
		
		return (int) i;
	
	}
	
	/**
	 * Verifie si il y a collision selon le mode de jeu choisi
	 */
	public void CheckCollision() {
		if(this.mode ==1) {
			CheckCollisionClassic();
		}else {
//			CheckCollisionBezier();
		}
	}
	
	/**
	 * Verifie la collision dans le cas du parcours classique
	 */
	private void CheckCollisionClassic() {
		CalculateCollision();
		int TopYCircle = Circle.getYPos() + 19;
		int BotYCircle = Circle.getYPos() + 152;
		
		if(TopYCircle >= collision.y || BotYCircle <= collision.y) {
			this.lost = true;
			this.running = false;
			this.pause = true;
			
			StopTheGame();
			
			System.out.println("COLLISION\nPERDU");
		}
	}
	
	
	/**
	 * Verifie la collision dans le cas du parcours Bezier
	 */
	private void CheckCollisionBezier() {
		ArrayList<QuadCurve2D> list = this.Bezier.getBezier();
		
		int TopYCircle = Circle.getYPos() + 19;
		int BotYCircle = Circle.getYPos() + 152;

		if(list.get(0).getX2() < this.Circle.getXPos() + 58) {
			if(list.get(1).contains(this.Circle.getXPos() + 58, TopYCircle) || list.get(1).contains(this.Circle.getXPos() + 58, BotYCircle) ) {
				
				this.lost = true;
				this.running = false;
				this.pause = true;
				
				StopTheGame();
				
				System.out.println("COLLISION\nPERDU");
			}
		}else {
			if(list.get(0).contains(this.Circle.getXPos() + 58, TopYCircle) || list.get(0).contains(this.Circle.getXPos() + 58, BotYCircle) ) {
				this.lost = true;
				this.running = false;
				this.pause = true;
				
				StopTheGame();
				
				System.out.println("COLLISION\nPERDU");
			}
		}
	}
	
	
	/**
	 * Appel la methode de saut du cercle 
	 * Augmente le score de 1
	 */
	public void CircleJump() {
		this.Circle.Jumping();
		this.SCORE++;
	}
		
	/**
	 * Modifie la valeur de repaint a true
	 */
	public void repaintTrue() {
		this.repaint = true;
	}
	
	/**
	 * Modifie la valeur de repaint a faux
	 */
	public void repaintFalse() {
		this.repaint = false;
	}
	
	/**
	 * Appelle la methode addBird de l'objet birds
	 */
	public void addBird() {
		birds.addBird();
	}
	
	/**
	 * 
	 * @param win
	 */
	public void setWindow(Window win) {
		this.window = win;
	}
	
	/**
	 * Definie le mode de jeu
	 * @param mode
	 */
	public void setMode(int mode) {
		this.mode = mode;
	}
	
	/**
	 * Retourne le mode de jeu qui a ete choisi
	 * @return
	 */
	public int getMode() {
		return this.mode;
	}
	
	/**
	 * Retourne si la partie est en cours
	 * @return
	 */
	public boolean isRunning() {
		return this.running;
	}
	
	/**
	 * Retoure si la partie est en pause ou non
	 * @return
	 */
	public boolean getPauseStatus() {
		return this.pause;
	}
	
	/**
	 * Retourne si l'affichage doit etre actualise
	 * @return
	 */
	public boolean getRepaint() {
		return this.repaint;
	}
	
	/**
	 * Retourne si la partie est perdu
	 * @return
	 */
	public boolean getLost() {
		return this.lost;
	}
	
	
	/**
	 * Retourne le Score de la partie
	 * @return
	 */
	public int getScore() {
		return this.SCORE;
	}
	
	/**
	 * Retourne le Cercle du jeu
	 * @return
	 */
	public CircleState getCircleState() {
		return this.Circle;
	}
	
	/**
	 * Retourne le delai de raffraichissement du thread du cercle
	 * @return
	 */
	public int getCircleDelai() {
		return this.Circle.getDelai();
	}
	
	/**
	 * Retourne le parcours
	 * @return
	 */
	public Course getCourse() {
		return this.parcours;
	}
	
	/**
	 * Retourne la list des Points du parcours classique
	 * @return
	 */
	public ArrayList<Point> getPointList(){
		return this.parcours.getPointList();
	}
	
	/**
	 * Retourne le pannel d'affichage du jeu
	 * @return
	 */
	public Affichage getDisplay() {
		return this.display;
	}
	
	/**
	 * Retourne la liste des oiseaux
	 * @return
	 */
	public ArrayList<Bird> getBirdsList(){
		return birds.getList();
	}

	/**
	 * Retourne la list des Points du parcours Bezier
	 * @return
	 */
	public ArrayList<QuadCurve2D> getBezier(){
		return this.Bezier.getBezier();
	}


}
