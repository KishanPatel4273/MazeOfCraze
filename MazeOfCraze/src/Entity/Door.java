package Entity;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import Display.ButtonHandler;
import Display.Display;
import Questions.Question;
import Questions.QuestionHandler;
import Render.Texture;

public class Door extends Entity{

	public static ArrayList<Door> doors = new ArrayList<Door>();
	private int radius;
	private boolean open, questionAnswered;
	private Rectangle closed;
	private boolean indexFix = true;
	private static String directory = System.getProperty("user.dir") + "/Absegami HS_5897_PatelPatel_ComputerGameandSimulationProgramming";

	public static Texture vDC = new Texture(directory + "/res/VertDoorClosed.png");
	public static Texture hDC = new Texture(directory + "/res/HorDoorClosed.png");

	public Door(int x, int y, int width, int height, int intRGB, int radius) {
		super(x,y,width, height, intRGB, true);
		this.radius = radius;
		open = false;
		setTag("Door");
		closed = getBounds();
		questionAnswered = false;
		if(width < height) {
			setTexture(vDC);
		} else { 
			setTexture(hDC);
		} 
	}
	
	public Door(int x, int y, int width, int height, int radius) {
		super(x,y,width, height, true);
		this.radius = radius;
		open = false;
		setTag("Door");
		closed = getBounds();
		questionAnswered = false;
		if(width < height) {
			setTexture(vDC);
		} else { 
			setTexture(hDC);
		}
	}
	
	public static void initDoor(ArrayList<Door> d) {
		doors = d;
	}
	
	//ask player question
	public void tick(Player player, ButtonHandler buttonHandler, QuestionHandler questionHandler, int difficulty) {
		if(!questionAnswered) {
			if(Display.input.key[KeyEvent.VK_E] && !open && detect(player)) {
				questionHandler.askQuestion(buttonHandler, difficulty);
				open = true;
				System.out.println(12345);
			}
			//player answers it right
			if(open && questionHandler.getAnswerPressed().equals(questionHandler.getCurrentQuestion().getCorrectAnswerLetter())) {
				buttonHandler.turnOffQuestion();
				questionHandler.setAnswerPressed("-1");	
				if(indexFix) {
					questionHandler.remove();
					indexFix = false;
				}
				open();
				questionAnswered = true;
				//Display.score += difficulty;
			} else if(open && !questionHandler.getAnswerPressed().equals(questionHandler.getCurrentQuestion().getCorrectAnswerLetter()) && !questionHandler.getAnswerPressed().equals("-1")) {
				//player answer it wrong
				Display.score -= difficulty;
				if(Display.score < 0) {
					Display.score = 0;
				}
				close();
				reset();
				buttonHandler.turnOffQuestion();
				questionHandler.setAnswerPressed("-1");
				player.respawn();
			}
		}
	}
	
	//sees if player is nears door
	public boolean detect(Player player) {
		return getAOE().intersects(player.getBounds()) && !open;
	}
	
	public Rectangle getAOE() {
		return new Rectangle(x - radius, y - radius, 2 * radius + width, 2 * radius + height);
	}
	
	//resets door
	public void reset() {
		questionAnswered = false;
		open = false;
		close();
	}
	
	//opens door
	public void open() {
		open = true;
		if(getWidth() > getHeight()) {
			setX(getX() +  getWidth() * 3 / 4);
			setWidth(getWidth() / 4);
		} else {
			setY(getY() + getHeight() * 3 / 4);
			setHeight(getHeight() / 4);
		}
	}
	
	//closes doors
	public void close() {
		open = false;
		setX(closed.x);
		setY(closed.y);
		setWidth(closed.width);
		setHeight(closed.height);
	}
	
	public boolean isOpen() {
		return open;
	}
	
	
}