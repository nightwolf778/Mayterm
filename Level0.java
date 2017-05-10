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
		bobFlight = new SpriteSheet("data/TeleLob-flight.png",32,32);
		bobGround = new SpriteSheet("data/TeleLob-Blink-clone.png",32,32);
		bobGroundAni = new Animation(bobGround, 100);
		xBob = 700;
		yBob = 400;
		radius = 25;
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
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		this.bobBall = new Ball(10, velocity, acceleration, xBob, yBob, vyInit);
		xBob = bobBall.getX(time);
		
		if(flying){
			bobBall.updateVy(time);
			System.out.println("Got here 0");
		}
		this.velocity.set(bobBall.getVelocityX(), bobBall.getVelocityY());
		yBob = bobBall.getY(time);
//		bob.setLocation(bobBall.getX(time)-10, bobBall.getY(time)-10);
		
		if (velocity.length() != 0){
			time += delta/12000f;
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
		
		int width = 800;
		int height = 600;
		
		Rectangle wall1 = new Rectangle(0, 0, width, 50);
		Rectangle wall2 = new Rectangle(0, 0, 50, height);
		Rectangle wall3 = new Rectangle(0, height-50, width, 50);
		Rectangle wall4 = new Rectangle(width-50, 0, 50, height);
		Rectangle wall5 = new Rectangle(300, 0, 50, 400);
		Rectangle wall6 = new Rectangle(600, 300, 50, 300);
		
		walls.add(wall1);
		walls.add(wall2);
		walls.add(wall3);
		walls.add(wall4);
		walls.add(wall5);
		walls.add(wall6);
		
		for (int i = 0; i < walls.size()-1; i++){
			if(bobBall.intersects(walls.get(i))){
				
				boolean cond1 = bobBall.intersectsRight(walls.get(i));
				boolean cond2 = bobBall.intersectsLeft(walls.get(i));
				boolean cond3 = bobBall.intersectsBottom(walls.get(i));
				boolean cond4 = bobBall.intersectsTop(walls.get(i));
				float dampening = 0.45f;
				if (cond1){
					bobBall.setX(bobBall.getX()-1);
					xBob = bobBall.getX();
//					System.out.println("Got here 1");
					bobBall.setVelocity(-bobBall.getVelocityX()*dampening, bobBall.getVelocityY());
					lastCol = 1;
					break;
				}
				if (cond2){
					bobBall.setX(bobBall.getX()+1);
					xBob = bobBall.getX();
//					System.out.println("Got here 2");
					bobBall.setVelocity(-bobBall.getVelocityX()*dampening, bobBall.getVelocityY());
					lastCol = 2;
					break;
				}
				
				if(cond3){
					bobBall.setY(bobBall.getY()-1);
					yBob = bobBall.getY();
					System.out.println("Got here 3");
					bobBall.setVelocity(bobBall.getVelocityX(), -bobBall.getVelocityY()*dampening);
					lastCol = 3;
					collisions += 1;
					break;
				}
				if(cond4){
					bobBall.setY(bobBall.getY()+1);
					yBob = bobBall.getY();
					System.out.println("Got here 4");
					bobBall.setVelocity(bobBall.getVelocityX(), -bobBall.getVelocityY()*0.5f);
					lastCol = 4;
					break;
				}
			
			
			}
		}
	}

	@Override
	public int getID() {
		return 1;
	}

}
