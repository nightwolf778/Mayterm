//TeleLob.java
package TeleLob;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class TeleLob extends StateBasedGame{

	
	public TeleLob(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new TeleLob("TeleLob"));
		app.setDisplayMode(1200, 750, false);
		app.setAlwaysRender(true);
		app.start();

	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.addState(new GameStateMenu());
		this.addState(new Level0());
		
	}

}
