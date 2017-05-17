package TeleLob;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Test2.Ball;
import Test2.Vector;
import org.newdawn.slick.geom.Vector2f;

public class LevelTest extends BasicGameState{
	private ArrayList<Circle> balls;
	private ArrayList<Rectangle> walls;
	private float xBob;
	private float yBob;
	private Vector2f mouse;
	private Vector2f velocity;
	private float time;
	private float acceleration;
	private float vyInit;
	private Ball bobBall;
	private Circle bob;
	private int collisions;
	private int lastCol;
	private boolean done;
	private SpriteSheet bobFlight;
	private SpriteSheet bobGround;
	private Animation bobGroundAni;
	private int radius;
	
	@Override
	public void init(GameContainer container, StateBasedGame arg1) throws SlickException {
		balls = new ArrayList<Circle>();
		walls = new ArrayList<Rectangle>();
		xBob = 700;
		yBob = 400;
		time = 0;
		radius = 30;
		mouse = new Vector2f(0,0);
		velocity = new Vector2f(0, 0);
		acceleration = 10;
		vyInit = 0;
		bobBall = new Ball(radius, velocity, acceleration, xBob, yBob, vyInit);
		bob = new Circle(bobBall.getX(0), bobBall.getY(0), bobBall.getRadius()/2f); 
		balls.add(bob);
		collisions = 0;
		lastCol = -1;
		done = false;
		bobFlight = new SpriteSheet("data/TeleLob-flight.png",32,32);
		bobGround = new SpriteSheet("data/TeleLob-Blink-clone.png",32,32);
		bobGroundAni = new Animation(bobGround, 100);
		
		
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
//		for (Circle c: balls){
//			g.setColor(Color.cyan);
//			g.fill(c);
//		}
		for (Rectangle r: walls){
			g.setColor(Color.magenta);
			g.fill(r);
		}
		g.setColor(Color.green);
//		g.drawLine(xBob, yBob, mouse.getX()+xBob, -mouse.getY()+yBob);
//		g.drawString("Angle: "+mouse.getTheta(), 100, 25);
//		g.drawString("Magnitude: "+mouse.length(), 100, 50);
		g.drawString("Velocity Angle: "+velocity.getTheta(), 100, 75);
		g.drawString("Velocity Magnitude: "+velocity.length(), 100, 100);
		g.drawString("Velocity X: "+velocity.getX(), 100, 125);
		g.drawString("Velocity Y: "+velocity.getY(), 100, 150);
		g.drawString("Velocity Yinit: "+vyInit, 100, 175);
		g.drawString("xBob: "+xBob, 100, 200);
		g.drawString("yBob: "+yBob, 100, 225);
//		g.drawString("Time: "+time, 100, 250);
//		if (walls.size() > 0){
//			g.drawString("Rect xMin: "+walls.get(0).getMinX(), 500, 25);
//			g.drawString("Floor yMin:"+walls.get(1).getMinY(), 500, 75);
//		}
		g.drawString("Collisions: "+collisions, 500, 50);
//		g.drawString("Velocity Mag: "+velocity.length(), 500, 100);
		
		if (done){
			bobGroundAni.draw(xBob - radius, yBob-radius, radius*2, radius*2);
		}
		else if(!done){
			bobFlight.draw(xBob - radius, yBob-radius, radius*2, radius*2);
		}
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException{
		
	
		this.bobBall = new Ball(10, velocity, acceleration, xBob, yBob, vyInit);
		xBob = bobBall.getX(time);
		
		if(!done){
			bobBall.updateVy(time);
			
		}
		this.velocity.set(bobBall.getVelocityX(), bobBall.getVelocityY());
		yBob = bobBall.getY(time);
		bob.setLocation(bobBall.getX(time)-10, bobBall.getY(time)-10);
		
		if (velocity.length() != 0){
			time += delta/12000f;
		}
		
		
//		int wallH = 200;
//		int wallW = 50;
//		
//		int floorH = 50;
//		int floorW = 800;
//		
//		Rectangle wall1 = new Rectangle(600, 250, wallW, wallH);
//		walls.add(wall1);
//		Rectangle floor = new Rectangle(0, 550, floorW, wallH);
//		walls.add(floor);
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
		
		float maxV = 150;
		
		mouse = new Vector2f(container.getInput().getMouseX()-xBob, -container.getInput().getMouseY()+yBob);
		
		if (container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){

			collisions = 0;
			done = false;
			velocity.set(new Vector2f((float)(container.getInput().getMouseX()-xBob),(float)(-container.getInput().getMouseY()+yBob)));

			bobBall.setVelocity(velocity.getX(), velocity.getY());
			if (velocity.length() > maxV){
				float ratio = maxV/velocity.length();
				velocity.scale(ratio);
			}
			
			this.vyInit = velocity.getY();
			done = false;

		} 
		
		
		
		for (int i = 0; i < walls.size()-1; i++){
			if(bob.intersects(walls.get(i))){
				
				boolean cond1 = bobBall.intersectsRight(walls.get(i));
				boolean cond2 = bobBall.intersectsLeft(walls.get(i));
				boolean cond3 = bobBall.intersectsBottom(walls.get(i));
				boolean cond4 = bobBall.intersectsTop(walls.get(i));
				
//				boolean cond5 = bobBall.getVelocityX() > 0;
//				boolean cond6 = bobBall.getVelocityX() < 0;
//				boolean cond7 = bobBall.getVelocityY() > 0;
//				boolean cond8 = bobBall.getVelocityY() < 0;
				
//				boolean cond1 = ((xBob+10.0f) - walls.get(i).getMinX() <= 0);
//				boolean cond2 = ((xBob-10.0f) - walls.get(i).getMaxX() <= 0);
//				boolean cond3 = ((yBob+10.0f) - walls.get(i).getMinY() <= 0);
//				boolean cond4 = ((yBob-10.0f) - walls.get(i).getMaxY() <= 0);
//				boolean cond5 = (yBob+10.0f <= walls.get(i).getMaxY() && yBob-10.0f >= walls.get(i).getMinY());
//				boolean cond6 = (xBob-10.0f <= walls.get(i).getMaxX() && xBob+10.0f >= walls.get(i).getMinX());
				
//				System.out.println("Got here 0");
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
		
		if(collisions >1) {
			done = true;
			velocity.sub(velocity);
			switch (lastCol){
				case (1): bobBall.setX(bobBall.getX()-1);
				case (2): bobBall.setX(bobBall.getX()+1);
				case (3): bobBall.setY(bobBall.getY()-1);
				case (4): bobBall.setY(bobBall.getY()+1);
			}
		}
		if (collisions > 100){
			switch (lastCol){
				case (1): bobBall.setX(bobBall.getX()-1);
				case (2): bobBall.setX(bobBall.getX()+1);
				case (3): bobBall.setY(bobBall.getY()-1);
				case (4): bobBall.setY(bobBall.getY()+1);
			}
		}
	}	
		

		
//		if (collisions > 3){
//			bobBall.setVelocity(0, 0);
//			bobBall.setX(xBob);
//			bobBall.setY(yBob);
//			container.pause();
//			if (container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){
//				
//				this.velocity = new Vector2f((float)(container.getInput().getMouseX()-xBob),(float)(-container.getInput().getMouseY()+yBob));
//				if (velocity.length() > 200){
//					float ratio = 200.0f/velocity.length();
//					velocity.scale(ratio);
//				}
//				this.vyInit = velocity.getY();
//				collisions = 0;
//				container.resume();
//				
//				
//			}
//		}
	
	


	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 1;
	}
	
//	public double getBobX(){
//		Vector2f velocity = this.velocity;
//		return xBob + velocity.getX()*time;
//	}
//	
//	public void updateVelocityY(double acceleration){
//		this.velocity.set(velocity.getX(), (float)acceleration*time);
//	}
//	
//	public double getBobY(double acceleration){
//		Vector2f velocity = this.velocity;
//		return yBob - vyInit*time+ 0.5*(acceleration)*(Math.pow(time, 2));
//	}
//	public Vector2f reflectX(Vector2f v){
//		float x = velocity.getX();
//		float y = velocity.getY();
//		v.set(-x*.75f, y);
//		return v;
//	}
//	public Vector2f reflectY(Vector2f v){
//		float x = velocity.getX();
//		float y = velocity.getY();
//		v.set(x, -y*.75f);
//		return v;
//	}
}

