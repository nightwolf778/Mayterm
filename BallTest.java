package Test2;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class BallTest extends StateBasedGame{

	public BallTest(String title) {
		super(title);
	}

	public static void main(String[] args) throws SlickException{
		AppGameContainer app = new AppGameContainer(new BallTest("BallTest"));
		app.setDisplayMode(800, 600, false);
		app.setAlwaysRender(true);
		app.start();
		 
	}


	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.addState(new MainState());
	}
}