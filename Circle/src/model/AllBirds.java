package model;

import java.util.ArrayList;

import model.Bird;
import theGame.Game;


public class AllBirds {

	/*Constantes */
	private ArrayList<Bird> Oiseaux = new ArrayList();
	private Game game;

	/**
	 * Constructeur
	 * @param g
	 */
	public AllBirds(Game g) {
		this.game = g;
		int r = (int) (1 + (Math.random() * (6 - 1)))	;
		for(int i=0; i<r ;i++) {
			Bird bird = new Bird(true, game);
			Oiseaux.add(bird);			
		}
		
		System.out.println("TAILLE LISTE OISEAUX " +Oiseaux.size());
	}

	
	/**
	 * Renvoi si la liste d'oiseaux est vide
	 * @return
	 */
	public boolean isEmpty() {
		return Oiseaux.isEmpty();
	}

	/**
	 * Ajoute un oiseau à la liste des oiseaux present
	 */
	public void addBird() {
		Bird bird = new Bird(false, game);
		Oiseaux.add(bird); 
		(new Thread(bird)).start();
		
		System.out.println("AJOUT D'UN NOUVEL OISEAU");
		System.out.println(Oiseaux.size());

	}
	
	/**
	 * Renvoie la liste des oiseaux
	 * @return
	 */
	public ArrayList<Bird> getList() {
		return this.Oiseaux;
	}

	/**
	 * retire un oiseau de la liste
	 * @param bird
	 */
	public void remove(Bird bird) {
		this.Oiseaux.remove(bird);
	}

	
}
