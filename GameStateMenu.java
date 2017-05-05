//GameStateMenu.java
package TeleLob;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.*;

public class GameStateMenu extends BasicGameState{
	
	private SpriteSheet BobStagnant = null;
	private Animation BobAnimation1 = null;
	private SpriteSheet OpeningBackground = null;
	private Animation OpeningBackgroundAnimation = null;
	private Image OpeningBackgroundImage = null;
	private SpriteSheet BobLooking = null;
	private Animation BobAnimation2 = null;
	private int xBob = 295;
	private int yBob = 564;
	private int xpos;
    private int ypos;
    private double angle;
    private int BobFrame;
	
	
	public String mouse;
	@Override
	public void init(GameContainer container, StateBasedGame arg1) throws SlickException {
		BobStagnant = new SpriteSheet("data/TeleLob-Blink-clone.png",32,32);
		BobAnimation1 = new Animation(BobStagnant,100);
		OpeningBackground = new SpriteSheet("data/TeleLob-Menu.png",1200,750);
		OpeningBackgroundAnimation = new Animation(OpeningBackground,200);
		OpeningBackgroundImage = OpeningBackgroundAnimation.getImage(0);
		BobLooking = new SpriteSheet("data/TeleLob-Eye-Roll.png",32,32);
		BobAnimation2 = new Animation(BobLooking,100);
	}

	@Override
	public void render(GameContainer container, StateBasedGame arg1, Graphics arg2) throws SlickException {
		if(angle == 210.0){
			BobFrame = 1;
		}
		OpeningBackgroundImage.draw();
		if(OpeningBackgroundAnimation.getFrame() == 7){
			OpeningBackgroundAnimation.setCurrentFrame(0);
		}
		OpeningBackgroundAnimation.draw();
		arg2.setColor(Color.white);
	    Input input = container.getInput();
	    xpos = input.getMouseX();
	    ypos = input.getMouseY();
	    
	    mouse = "x position equals " + xpos + " and y pos equals " + ypos + " angle equals " + angle;
	    arg2.drawString(mouse, 50, 50);
	    arg2.setColor(Color.cyan);
	    arg2.drawLine(xpos, ypos, xBob, yBob);
	    if(angle < 0 || angle > 180){
		    BobAnimation1.draw(xBob-50, yBob-50, 100, 100); 
		    if(BobAnimation1.getFrame() == 10){
		    	BobAnimation1.addFrame(BobStagnant, 100);
		    }
		}else{
			BobAnimation2.draw(xBob-50, yBob-50, 100, 100);
			BobAnimation2.setCurrentFrame(BobFrame);
		}
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame arg1, int delta) throws SlickException {
		if(angle == 210.0){
			BobFrame = 1;
		}
		this.angle = getAngle((double)xpos,(double)(ypos));
		final double n = 16.153846154;
		BobAnimation1.update(delta/10);
		if(angle >= 0 || angle < 210){
			for(int i = 13; i > 0; i--){
				if(angle - n*i >= 0 && angle - n*i <= n){

					BobFrame = 12-i;
				}
			}
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public double getAngle(double x, double y){
		return Math.toDegrees((Math.atan2((yBob-y+100),(xBob-x))));
	}

}
