package model;

import theGame.Game;

public class CircleState {

	
	private Game game;
	
	/*The variables that manage the position*/
	private int x_pos = 220;
	private int Position = 200; 		
	private int FuturPosition;
	private int DecelerationPosition;
	
	/*The variables that manage the movement*/
	private final int JumpRange = 80;
	private int FallSpeed = 3;
	private int JumpSpeed = 3;
	private final int FallAcceleration = 1;
	private final int JumpDeceleration = 1;
	
	private int delai = 13;		//Update time of the Thread. Give the sensation of Speed
	
	private boolean jumping ;
	
	public CircleState() {
		
	}
	
	/**
	 * Determinate the futur position of the circle at the end of the jump
	 */
	private void NewFuturPosition() {
		
		this.FuturPosition = this.Position - this.JumpRange;
		
		if(this.FuturPosition <= 0){
			this.FuturPosition = 0;
		}
		
		DecelerationPosition = this.FuturPosition + (this.Position - this.FuturPosition)/2;

	}
	
	
	/**
	 * Change the position of the circle and his speed when he is jumping
	 */
	private void jump() {

		//Update position
		this.Position -= this.JumpSpeed; 
		
		//Update the speed
		if(this.Position <= this.DecelerationPosition) {
			this.delai += this.JumpDeceleration;
		}
		
		//Once the circle reaches its goal, he start to fall
		if(this.Position <= this.FuturPosition) {
			this.Position = this.FuturPosition;
			this.jumping = false;
		}
	}
	
	/**
	 * Update the state of the circle to "jump"
	 */
	public void Jumping() {
		NewFuturPosition();
		this.jumping = true;

		//Init the speed every time the player jump
		delai = 13;
		
		/* Prints the Circle Positions */
	/*
		System.out.println("Actual Position :       "+ this.Position);
		System.out.println("Futur Position :        " + this.FuturPosition);
		System.out.println("Deceleration Position : " + this.DecelerationPosition+"\n");
	 */
	}
	
	/**
	 * Manage the movement of the circle according to his state : "jumping" / "falling"
	 */
	public void movement() {
		if(jumping) {
			jump();
			
		}else if(!jumping){
			fall();
		}
	}

	
	/**
	 * Change the position of the circle and his speed when he is falling
	 */
	private void fall() {	
		
		this.Position += this.FallSpeed;
		
		if(this.Position>=600) {
			this.Position=600;
		}
		
		if(this.Position <= this.DecelerationPosition) {
			this.delai -= this.FallAcceleration;
		}
	}

	
	/**
	 * Return the X position
	 * @return
	 */
	public int getXPos() {
		return x_pos;
	}
	
	/**
	 * Return the Y position
	 * @return
	 */
	public int getYPos() {
		return Position;
	}
	
	/**
	 * Return the delai
	 * @return
	 */
	public int getDelai() {
		return this.delai;
	}
	
	
}
