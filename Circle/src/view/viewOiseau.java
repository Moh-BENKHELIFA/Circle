package view;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import model.Bird;
import model.AllBirds;

public class viewOiseau {
	
	/**Constantes*/
//	private ArrayList<Bird> Oiseaux = new ArrayList();
	private BufferedImage[] sprites = new BufferedImage[9];

	/**Constructeur*/
	public viewOiseau() {
		loadSprites();
		loadBirds();
	}
	
	/**
	 * Charge aleatoirement 1 a 6 oiseau 
	 */
	private void loadBirds() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Dessine l'ensemble des oiseaux présent dans la liste
	 * @param g
	 */
	public void draw(Graphics g, ArrayList<Bird> birds) {
		
		if(birds != null /*!birds.isEmpty()*/) {
			for(Bird bird : birds){
				if(bird.getInScreen())
					g.drawImage(sprites[bird.img],bird.getPosition(), bird.getHauteur(), bird.getDimensionX(),bird.getDimensionY(),null);	
				else {
					bird.stop();
					birds.remove(bird);
					System.err.println("REMOVED");
				}
			}
		}
	}
	
	
	/**
	 * Charge l'ensemble des sprites de l'oiseau dans un tableau
	 */
	public void loadSprites() {
		for(int i=1; i<9; i++) {
			try {
				sprites[i] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("Bird/animate-bird"+i+".png"));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();				
			}
		}
	}
	
}
