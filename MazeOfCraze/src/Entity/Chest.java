package Entity;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Display.ButtonHandler;
import Display.Display;
import Render.Texture;
public class Chest extends Entity{

	private static String directory = System.getProperty("user.dir") + "/Absegami HS_5897_PatelPatel_ComputerGameandSimulationProgramming";
	public static Texture openChest = new Texture(directory + "/res/openchest.png");
	public static ArrayList<Chest> chests = new ArrayList<Chest>();
	private int radius;
	private boolean open;
	
	public Chest(int x, int y, int width, int height, int radius) {
		super(x, y, width, height, true);
		this.radius = radius;
	}
	
	public Chest(int x, int y, int width, int height, int intRGB, int radius) {
		super(x,y,width, height, intRGB, true);
		this.radius = radius;
		setTag("Chest");
	}
	
	public Chest(int x, int y, int width, int height, Texture texture, int radius) {
		super(x,y,width, height, texture, true);
		this.radius = radius;
		setTag("Chest");
	}
	
	public static void initChest(ArrayList<Chest> c) {
		chests = c;
	}
	int time;
	//sees if player is near the chest and has clicked "e"
	public void tick(Player player, ButtonHandler buttonHandler, int difficulty) {
		time++;
		if(detect(player) && Display.input.key[KeyEvent.VK_E]) {
			open = true;
			super.setTexture(openChest);
			//player.addHealth(25);
			Display.score += difficulty;
			buttonHandler.turnONDialogue("You got " + difficulty + " points!");
			time = 1;
		}
		if(time % 200 == 0) {
			buttonHandler.turnOffDialogue();
		}
		
	}
	
	//checks if player is near
	public boolean detect(Player player) {
		return getAOE().intersects(player.getBounds()) && !open;
	}
	
	public Rectangle getAOE() {
		return new Rectangle(x - radius, y - radius, 2 * radius + width, 2 * radius + height);
	}
	
	public Point getCenter() {
		return new Point(x + width / 2, y + height / 2);
	}
}