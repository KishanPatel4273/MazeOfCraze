package Entity;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import Render.Texture;

public class Entity {
	
	public static ArrayList<Entity> entities;
	protected int x, y, width, height, intRGB;
	private Texture texture;
	private boolean collidable;
	private int order;
	protected String tag;
	//the class stores various information about objects in the game
	public Entity(int x, int y, int width, int height, boolean collidable) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.texture = null;
		this.collidable = collidable;
		this.order = 1;
		tag = "Entity";
	}
	
	public Entity(int x, int y, int width, int height, int intRGB, boolean collidable) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.intRGB = intRGB;
		this.texture = null;
		this.collidable = collidable;
		this.order = 1;
		tag = "Entity";
	}
	
	public Entity(int x, int y, int width, int height, Texture texture, boolean collidable) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.texture = texture;
		this.collidable = collidable;
		this.order = 1;
		tag = "Entity";
	}
	
	public static void initEntity(ArrayList<Entity> entities1) {
		entities = entities1;
	}
	
	public void render(Graphics g) {
		g.setColor(Texture.getINRGBTORGB(intRGB)); 
		g.fillRect(x, y, width, height);
		if(texture != null) {
			g.drawImage(texture.getTexture(), x, y, width, height, null);
		}
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public void addX(int a) {
		this.x += a;
	}
	
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void addY(int a) {
		this.y += a;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public int getIntRGB() {
		return intRGB;
	}

	public void setIntRGB(int intRGB) {
		this.intRGB = intRGB;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(getX(),getY(),width, height); 
	}

	public boolean isCollidable() {
		return collidable;
	}

	public void setCollidable(boolean collidable) {
		this.collidable = collidable;
	}
	
	public void setOrder(int order) {
		this.order = order;
	}
	
	public int getOrder() {
		return order;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public String getTag() {
		return tag;
	}
	
	public Point getCenter() {
		return new Point(x + width / 2, y + height / 2);
	}
	
	public String toString() {
		return "(" + x +  "," + y + ") " + isCollidable();
	}
}