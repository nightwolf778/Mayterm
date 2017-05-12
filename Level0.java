package TeleLob;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Test2.Ball;

public class Level0 extends BasicGameState{
	private ArrayList<Rectangle> walls;
	private float xBob;
	private float yBob;
	private Ball bobBall;
	private Vector2f velocity;
	private float time;
	private float acceleration;
	private float vyInit;
	private int collisions;
	private int lastCol;
	private int radius;
	private SpriteSheet bobFlight = null;
	private SpriteSheet bobGround = null;
	private Animation bobGroundAni;
	private boolean flying;
	private float maxV;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		walls = new ArrayList<Rectangle>();
		
		
		Rectangle wall1 = new Rectangle(0, 0, 1200, 50);
		Rectangle wall2 = new Rectangle(0, 0, 50, 750);
		Rectangle wall3 = new Rectangle(0, 700, 1200, 50);
		Rectangle wall4 = new Rectangle(1150, 0, 50, 750);
		Rectangle wall5 = new Rectangle(300, 0, 50, 400);
		Rectangle wall6 = new Rectangle(600, 300, 50, 300);
		
		walls.add(wall1);
		walls.add(wall2);
		walls.add(wall3);
		walls.add(wall4);
		walls.add(wall5);
		walls.add(wall6);
		
	
		bobFlight = new SpriteSheet("data/TeleLob-flight.png",32,32);
		bobGround = new SpriteSheet("data/TeleLob-Blink-clone.png",32,32);
		bobGroundAni = new Animation(bobGround, 100);
		xBob = 700;
		yBob = 400;
		radius = 30;
		time = 0;
		velocity = new Vector2f(0, 0);
		acceleration = 10;
		vyInit = 0;
		flying = false;
		bobBall = new Ball(radius, velocity, acceleration, xBob, yBob, vyInit);
		maxV = 150;
		
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		if (!flying){
			bobGroundAni.draw(xBob - radius, yBob-radius, radius*2, radius*2);
		}
		else if(flying){
			bobFlight.draw(xBob - radius, yBob-radius, radius*2, radius*2);
		}
		for (Rectangle r: walls){
			g.setColor(Color.green);
			g.fill(r);
		}
		g.drawString("xBob: "+xBob, 100, 300);
		g.drawString("xBobBall: "+bobBall.getX(), 100, 325);
		g.drawString("yBob: "+yBob, 100, 350);
		g.drawString("yBobBall: "+bobBall.getY(), 100, 375);
		g.drawString("Velocity Mag: "+velocity.length(), 100, 400);
		g.drawString("hitbox Y: "+bobBall.getBox().getMinY(), 100, 425);
		g.drawString("hitbox X: "+bobBall.getBox().getMaxX(), 100, 450);
		g.draw(bobBall.getBox());
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		
		this.bobBall= new Ball(radius, velocity, acceleration, xBob, yBob, vyInit);
		
		if (velocity.length() != 0){
			this.bobBall = new Ball(radius, velocity, acceleration, xBob, yBob, vyInit);
			xBob = bobBall.getX(time);
			
			if(flying){
				bobBall.updateVy(time);
//				System.out.println("Got here -1");
			
				this.velocity.set(velocity.getX(), bobBall.getVelocityY());
				yBob = bobBall.getY(time);
	//			bob.setLocation(bobBall.getX(time)-10, bobBall.getY(time)-10);
			
				time += delta/12000f;
		
			}
		}
			
		
		if (container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){

			collisions = 0;
			flying = true;
			velocity.set(new Vector2f((float)(container.getInput().getMouseX()-xBob),(float)(-container.getInput().getMouseY()+yBob)));

			bobBall.setVelocity(velocity.getX(), velocity.getY());
			if (velocity.length() > maxV){
				float ratio = maxV/velocity.length();
				velocity.scale(ratio);
			}
			
			this.vyInit = velocity.getY();
		} 
		
		for (Shape w: walls){
			if(bobBall.getBox().intersects(w)){
				System.out.println("Got here 0");
				float maxX = bobBall.getBox().getMaxX();
				float minX = bobBall.getBox().getMinX();
				float maxY = bobBall.getBox().getMaxY();
				float minY = bobBall.getBox().getMinY();
				
				float shapeX = w.getMaxX();
				float shapex = w.getMinX();
				float shapeY = w.getMaxY();
				float shapey = w.getMinY();
				
				if(bobBall.getVelocityX() < 0){
					System.out.println("Got here -v X");
					if (bobBall.getVelocityY() > 0){
						System.out.println("Got here -v Y");
						if (minX <= shapeX && (maxY <= shapeY && minY >= shapey)){
							bobBall.setX(bobBall.getX()+1);
							xBob = bobBall.getX();
							System.out.println("Got here 2");
							bobBall.setVelocity(-bobBall.getVelocityX(), bobBall.getVelocityY());
							lastCol = 2;
						}
						if (maxY >= shapey && (minX >= shapex && maxX <= shapeX)){
							bobBall.setY(bobBall.getY()+1);
							yBob = bobBall.getY();
							System.out.println("Got here 4");
							bobBall.setVelocity(bobBall.getVelocityX(), -bobBall.getVelocityY()*0.5f);
							lastCol = 4;
						}
					}
					else{
						if (minX <= shapeX && (maxY <= shapeY && minY >= shapey)){
							bobBall.setX(bobBall.getX()+1);
							xBob = bobBall.getX();
							System.out.println("Got here 2");
							bobBall.setVelocity(-bobBall.getVelocityX(), bobBall.getVelocityY());
							lastCol = 2;
						}
					}
				}
				else{
					System.out.println("Got here +v X");
					if (bobBall.getVelocityY() < 0){
						System.out.println("Got here +v Y");
						if (maxX >= shapex && (maxY <= shapeY && minY >= shapey)){
							bobBall.setX(bobBall.getX()-10);
							xBob = bobBall.getX(time);
							System.out.println("Got here 1");
							bobBall.setVelocity(-bobBall.getVelocityX(), bobBall.getVelocityY());
							lastCol = 1;
						}
						if(minY <= shapeY && (minX >= shapex && maxX <= shapeX)){
							bobBall.setY(bobBall.getY()-1);
							yBob = bobBall.getY();
							System.out.println("Got here 3");
							bobBall.setVelocity(bobBall.getVelocityX(), -bobBall.getVelocityY());
							lastCol = 3;
							collisions += 1;
						}
					}
					else{
						if (maxX >= shapex && (maxY <= shapeY && minY >= shapey)){
							bobBall.setX(bobBall.getX()-10);
							xBob = bobBall.getX(time);
							System.out.println("Got here 1");
							bobBall.setVelocity(-bobBall.getVelocityX(), bobBall.getVelocityY());
							lastCol = 1;
						}
					}
				}
				
//				if(maxX >= shapex && (maxY <= shapeY && minY >= shapey)){
//					bobBall.setX(bobBall.getX()-10);
//					xBob = bobBall.getX(time);
//					System.out.println("Got here 1");
//					bobBall.setVelocity(-bobBall.getVelocityX(), bobBall.getVelocityY());
//					lastCol = 1;
//				}
//				if (minX <= shapeX && (maxY <= shapeY && minY >= shapey)){
//					bobBall.setX(bobBall.getX()+1);
//					xBob = bobBall.getX();
//					System.out.println("Got here 2");
//					bobBall.setVelocity(-bobBall.getVelocityX(), bobBall.getVelocityY());
//					lastCol = 2;
//				
//				}
//				
//				if(cond3){
//					bobBall.setY(bobBall.getY()-1);
//					yBob = bobBall.getY();
//					System.out.println("Got here 3");
//					bobBall.setVelocity(bobBall.getVelocityX(), -bobBall.getVelocityY()*dampening);
//					lastCol = 3;
//					collisions += 1;
//			
//				}
//				if(cond4){
//					bobBall.setY(bobBall.getY()+1);
//					yBob = bobBall.getY();
//					System.out.println("Got here 4");
//					bobBall.setVelocity(bobBall.getVelocityX(), -bobBall.getVelocityY()*0.5f);
//					lastCol = 4;
//				
//				}
//			
//			
			}
		}
	}

	@Override
	public int getID() {
		return 1;
	}

}
