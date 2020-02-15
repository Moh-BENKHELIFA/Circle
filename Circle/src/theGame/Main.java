package theGame;

import control.*;
//import control.Control;
import model.*;
import view.*;

public class Main {

	public static void main(String[] args) {
		
		int x = 1500;
		int y = 800;
		
		
		Game game = new Game();
		Window window = new Window(x,y,game);
	}
}
