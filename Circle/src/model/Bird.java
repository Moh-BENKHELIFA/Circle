package model;

import java.util.Random;

import theGame.Game;

public class Bird extends Thread{

	
	public int delai = 150;
	private static final int JUMP = 10;
	private int move = 10;
	private int dimensionX ;
	private int dimensionY ;
	private int hauteur;
	private int position;
	private int LWindow = 1500;
	private int HWindow = 800;
	private boolean inScreen;
	public int img;				//Correspond a etat dans le sujet // l'image de l'oiseau qui correspond
	
	private Game game;
	

	
	/**Constructeur*/
	public Bird(boolean init, Game g) {
		this.game = g;
		this.inScreen = true;
		this.img = 1;
		hauteur = initH();
		position = initP();
		setDimension();
		delai = initD();
		
		Random rd = new Random();
		if(init) {
			if(rd.nextBoolean()) {
				leftToRight();
			}else {
				rightToLeft();
			}
		}else {
			if(rd.nextBoolean()) {
				leftToRight();
				position = -500;

			}else {
				rightToLeft();
				position = 1600;

			}
			
		}
		
	}
	

	public void run() {

		while(game.isRunning() && inScreen) {
			if(!game.getPauseStatus()) {
				updateH();
				updateP();
				nextImg();
				checkinScreen();
				this.game.repaintTrue();
				try { Thread.sleep(this.delai); }
			    catch (Exception e) { e.printStackTrace(); }
				
			}

			try { Thread.sleep(1); }
		    catch (Exception e) { e.printStackTrace(); }
		}
	}
	
	/**
	 * Initie le mouvement de l'oiseau 
	 * Direction : gauche vers la droite
	 * (Oiseau rapide)
	 */
	private void leftToRight() {
		move = 10;
	}
	
	/**
	 * Initie le mouvement de l'oiseau 
	 * Direction : droite vers la fauche
	 * (Oiseau lent)
	 */
	private void rightToLeft() {
		move = -10;
	}
	
	/**
	 * Modifie l'etat de l'oiseau pour modifier son img à l'affichage
	 */
	public void nextImg() {
		if(img == 8) {
			img=1;
		}else {
			img++;
		}
	}

	
	/**
	 * Defini aleatoirement les dimensions de l'image
	 * Min : 25%
	 * Max : 100%
	 */
	private void setDimension() {
		int r = (int) (25 + (Math.random() * (100 - 25))); 		//genere aleatoirement un nombre entre 25 et 100
		this.dimensionX = 600*r/100;
		this.dimensionY = 469*r/100;
	}
	
	/**
	 * 
	 * @return dimension X de l'image
	 */
	public int getDimensionX() {
		return this.dimensionX;
	}
	
	/**
	 * 
	 * @return dimension Y de l'image
	 */
	public int getDimensionY() {
		return this.dimensionY;
	}
	
	/**
	 * Initilise aleatoirement le delai de mise a jour du thread de l'oiseau
	 * @return int
	 */
	private int initD() {
		this.move = (int) ((Math.random() * (30)));
		return (int) (80 + (Math.random() * (150 - 80)))	;
	}

	/**
	 * Initialise aleatoirement la position verticale de l'oiseau dans la fenêtre
	 * @return int
	 */
	private int initP() {
		return (int) (Math.random() * 1500);
	}


	/**
	 * Initialise aleatoirement la position horizontale de l'oiseau dans la fenêtre
	 * @return int
	 */
	private int initH() {
		return (int) (Math.random() * 200);
	}
	
	
	/**
	 * Verifie si l'oiseau est toujours dans l'écran
	 * Si non
	 * Modifie la valeur boolenne indiquant si il est affiché ou non
	 */
	private void checkinScreen() {
		if(this.hauteur > this.HWindow+500 || this.hauteur < -500 || this.position < -600 || this.position > this.LWindow+600 ) {
			this.inScreen = false;
		}
	}
	
	/**
	 * Stop le thread de l'oiseau
	 */
	public void stopThread() {
		this.inScreen = false;
	}
	
	/**
	 * Renvoi la valeur boolean de l'oiseau si il est present ou non dans la fenetre
	 * @return
	 */
	public boolean getInScreen() {
		return this.inScreen;
	}

	/**
	 * Met a jour la hauteur de l'oiseau 
	 */
	private void updateH() {
		Random rd = new Random();
		if(rd.nextBoolean()) {
			this.hauteur -= JUMP;
		}else {
			this.hauteur += JUMP;
		}
	}
	
	/**
	 * Renvoi la position y de l'oiseau
	 * @return
	 */
	public int getHauteur() {
		
		return this.hauteur;
	}

	/**
	 * Renvoi la position x de l'oiseau
	 * @return
	 */
	public int getPosition() {
		
		return this.position;
	}

	/**
	 * Met a jour la position de l'oiseau
	 * On defini aleatoirement si l'oiseau avance ou recule
	 */
	private void updateP() {
		Random rd = new Random();
		if(rd.nextBoolean()) {
			this.position += move;
		}else {
			this.position += move*(-0.2);
		}
//		checkinScreen();
	}
}
