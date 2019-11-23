package Entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import Display.Display;
import Map.Map;
import Render.Texture;

public class Player extends Entity {

	private int walkingSpeed;
	private float runningMultiplyer;
	private int scale, a;
	private int health;
	private int xo, yo;
	public static int MAX_HEALTH = 100;
	//textures for different faces
	private static String directory = System.getProperty("user.dir") + "/Absegami HS_5897_PatelPatel_ComputerGameandSimulationProgramming";
	public static Texture down = new Texture(directory + "/res/Down.png");
	public static Texture left = new Texture(directory + "/res/Left.png");
	public static Texture right = new Texture(directory + "/res/Right.png");
	public static Texture up = new Texture(directory + "/res/Up.png");

	public Player(int x, int y, int width, int height, int scale) {
		super(x, y, width * (int) ((1/5f) * scale * 1.25), height * (int) ((1/5f) * scale * 1.25), true);
		this.scale = scale;
		this.xo = x;
		this.yo = y;
		init();
	}

	public Player(int x, int y, int width, int height, int scale, Texture texture) {
		super(x, y, width, height, texture, true);
		this.scale = scale;
		this.xo = x;
		this.yo = y;
		init();
	}

	public void init() {
		setTexture(up);
		setTag("Player");
		walkingSpeed = 5 * (int)((3 / 4f) * (double) (10f * scale) / 500f);
		runningMultiplyer = 1.5f;
		a = (int)(20f * scale / 500f);
		health = 50;
	}

	//was used to shoot projectiles not completed
	public void tick(Map map) {
		if(Display.input.mouseClicked){// && (Projectile.time + timeBeforeShooting > (System.currentTimeMillis() * 1000000000)) && Projectile.projectiles.size() < 23) {
			Display.input.mouseClicked = false;
			//Projectile.projectiles.add(new Projectile(getCenter().x, getCenter().y - height, width/4, width/4, map.getTextures().getTexture("projectile"), 1, 1));
		}
		
	}
		
	public int getWallingSpeed() {
		return walkingSpeed;
	}
	
	public int getRunningSpeed() {
		return (int) (walkingSpeed * runningMultiplyer);
	}

	public Rectangle getUpperBound() {
		return new Rectangle(x + a, y, width - 2 * a, a);
	}

	public Rectangle getLeftBound() {
		return new Rectangle(x, y + a, a, height - 2 * a);
	}

	public Rectangle getRightBound() {
		return new Rectangle(x + width - a, y + a, a, height - 2 * a);
	}

	public Rectangle getBottomBound() {
		return new Rectangle(x + a, y + height - a, width - 2 * a, a);
	}
	
	public void hit(int damage) {
		health-= damage;
	}
	
	public void addHealth(int health) {
		this.health += health;
		if(this.health > MAX_HEALTH) {
			this.health = MAX_HEALTH;
		}
	}
	
	public int getHealth() {
		return health;
	}

	public void respawn() {
		setX(xo);
		setY(yo);
	}
	
	//render health not implemented
	public void renderInfo(Point origin, Graphics g) {	
		g.setColor(Color.WHITE);
		g.fillRect(origin.x + 10 , origin.y + 10, MAX_HEALTH * scale / 200, MAX_HEALTH / 10);
		g.setColor(Color.RED);
		g.fillRect(origin.x + 10 , origin.y + 10, health * scale / 200, MAX_HEALTH / 10);
		g.setColor(Color.BLACK);
		for(int i = 0; i < 3; i++) {
			g.drawRect(origin.x + 10 - i , origin.y + 10 - i, (MAX_HEALTH * scale / 200)+ (2 * i), (MAX_HEALTH / 10) + (2 * i));
		}
		
	}
}