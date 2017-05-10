package Test2;

import org.newdawn.slick.geom.Vector2f;

public class Ball extends Hitbox{
	
	private int radius;
	private Vector2f velocity;
	private float xPos;
	private float yPos;
	private float acceleration;
	private float vyInit;
	
	public Ball(int radius, Vector2f velocity, float acceleration, float xPos, float yPos, float vyInit){
		super(xPos-(float)radius, yPos-(float)radius, 2.0f*radius, 2.0f*radius);
		this.radius = radius;
		this.velocity = velocity;
		this.xPos = xPos;
		this.yPos = yPos;
		this.acceleration = acceleration;
		this.vyInit = vyInit;
	}
	
	public float getX(float time){
		if (velocity.length() != 0){
			return xPos + velocity.getX()*time;
		}
		else
			return xPos;
	}
	public float getX(){
		return xPos;
	}
	public float getY(){
		return yPos;
	}
	
	public float getY(float time){
		if (velocity.length() != 0){
			return yPos - velocity.getY()*time;
		}
		else
			return yPos;
	}
	
	public void updateVy(float time){
		this.velocity.set(velocity.getX(), (velocity.getY() - (this.acceleration*time)));
	}
	
	public float getVelocityX(){
		return velocity.getX();
	}
	
	public float getVelocityY(){
		return velocity.getY();
	}
	
	public int getRadius(){
		return radius;
	}
	
	public void setX(float x){
		this.xPos = x; 
	}
	
	public void setY(float y){
		this.yPos = y;
	}
	
	public Vector2f getVelocity(){
		return this.velocity;
	}
	public void setVelocity(float x, float y){
		this.velocity.set(x, y);
	}

}
