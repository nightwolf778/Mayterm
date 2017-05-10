package Test2;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Hitbox {
//	private float xPos;
//	private float yPos;
//	private float length;
//	private float height;
	private Rectangle box;
	
	public Hitbox(float xPos, float yPos, float length, float height){
//		this.xPos = xPos;
//		this.yPos = yPos;
//		this.length = length;
//		this.height = height;
		this.box = new Rectangle(xPos, yPos, length, height);
	}
	

	public boolean intersectsTop(Shape s){
		if (box.getMinY() >= s.getMaxY()){
			return true;
		}
		else
			return false;
	}
	
	public boolean intersectsRight(Shape s){
		if (box.getMaxX() <= s.getMinX()){
			return true;
		}
		else
			return false;
	}
	
	public boolean intersectsBottom(Shape s){
		if (box.getMaxY()<=s.getMinY()){
			return true;
		}
		else
			return false;
	}
	public boolean intersectsLeft(Shape s){
		if (box.getMinX() >= s.getMaxX())
			return true;
		else
			return false;
	}
}
