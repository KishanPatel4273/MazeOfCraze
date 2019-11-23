package Entity;

import java.awt.Point;
import java.util.ArrayList;

import Display.Display;
import Input.InputHandler;
import Map.Map;
import Render.Texture;

public class Projectile extends Entity {
	
	//not implemented
	public static ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	public static long time;
	private boolean active;
	private float initialX, initialY, newX, newY, slope;
	private int speed, quadrant;
	private int[] slopeDirectionX = new int[] {1, -1, -1, 1};
	private int[] slopeDirectionY = new int[] {-1, -1, 1, 1};
	private int damage;
	
	public Projectile(int x, int y, int width, int height, int speed, int damage) {
		super(x, y, width, height, true);	
		active = true;
		initialX = x;
		initialY = y;
		this.slope = slope();
		this.speed = speed;
		this.damage = damage;
		time = (long) (System.nanoTime() / 1000000000.0);
	}
	
	public Projectile(int x, int y, int width, int height, int intRGB, int speed, int damage) {
		super(x, y, width, height, intRGB, true);
		active = true;
		initialX = x;
		initialY = y;
		this.slope = slope();
		this.speed = speed;
		this.damage = damage;
		time = (long) (System.nanoTime() / 1000000000.0);
	}
	
	public Projectile(int x, int y, int width, int height, Texture texture, int speed, int damage) {
		super(x, y, width, height, texture, true);
		active = true;
		initialX = x;
		initialY = y;
		this.slope = slope();
		this.speed = speed;
		this.damage = damage;
		time = (long) (System.nanoTime() / 1000000000.0);
	}

	public void tick(Map map) {
		for(int i = 0; i < speed; i++) {
			move();
			collision(map);
		}
	}
	
	//updates motion
	private void move() {
		if(slope <= 1) {//x
			for(int i = 0; i < 4; i++) {
				if(quadrant == (i+1)) {
					newX += slopeDirectionX[i];
					newY += slopeDirectionY[i] * slope;	
					break;
				}
			}
		} else if(slope > 1) {//y
			for(int i = 0; i < 4; i++) {
				if(quadrant == (i+1)) {
					newX += slopeDirectionX[i] * (float) (1f / slope);
					newY += slopeDirectionY[i];	
					break;
				}
			}
		} else if(quadrant == 0) {
			newX++;
		}
		setX((int) (initialX + newX));
		setY((int) (initialY + newY));
	}
	
	//sees if it collides with something
	public void collision(Map map) {
		for(Entity e: Entity.entities) {
			if(e.isCollidable() && e.getBounds().intersects(this.getBounds())) {
				if(e.getTag().indexOf("Entity") > -1) {
					deactivate();
				}
			}
		}
		if(map.getPlayer().getBounds().intersects(this.getBounds()) && isActive()) {
			map.getPlayer().hit(damage);
			deactivate();
		}
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void deactivate() {
		active = false;
	}
	
	//calculates info about the direction of projectile
	private float slope() { 
		Point mousePOS = new Point((getCenter().x - Display.WIDTH / 2) + InputHandler.mouseX , (getCenter().y - Display.HEIGHT / 2) + InputHandler.mouseY);
		if(x < mousePOS.x && y > mousePOS.y) {
			quadrant = 1;
		} else if(x > mousePOS.x && y > mousePOS.y) {
			quadrant = 2;
		} else if(x > mousePOS.x && y < mousePOS.y) {
			quadrant = 3;
		} else if(x < mousePOS.x && y < mousePOS.y) {
			quadrant = 4;
		}
		if((x - mousePOS.x) != 0) {
			return (float) Math.abs((float) (y - mousePOS.y) / (x - mousePOS.x));
		} else {
			return 100000;
		}
	}
}
