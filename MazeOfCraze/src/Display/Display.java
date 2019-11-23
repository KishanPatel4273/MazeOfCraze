package Display;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

import Input.InputHandler;
import Map.MapHandler;
import Render.Texture;

public class Display extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;

	public static int score = 0;
	public static int scale = 300;
	public static final int WIDTH = 9 * 150;
	public static final int HEIGHT = 6 * 150;
	public static final String TITLE = "Maze of Craze";

	private Thread thread;
	private boolean running = false;
	private boolean gameDisplaying;
	public static InputHandler input;
	private MapHandler mapHandler;
	private ButtonHandler buttonHandler;
	private String directory = System.getProperty("user.dir")  + "/Absegami HS_5897_PatelPatel_ComputerGameandSimulationProgramming";
	private int fps;
	
	public Display() {
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		
		buttonHandler = new ButtonHandler(this);
		mapHandler = new MapHandler(buttonHandler);
		input = new InputHandler();
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
	}

	private void start() {
		if (running)
			return;
		running = true;
		gameDisplaying = false;
		thread = new Thread(this);
		thread.start();
	}

	private void stop() {
		if (!running)
			return;
		running = false;
		System.exit(0);
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	//game loop
	public void run() {
		int frames = 0;
		double unprocessedSeconds = 0;
		long previousTime = System.nanoTime();
		double secondsPerTick = 1 / 60.0;
		int tickCount = 0;
		boolean ticked = false;

		while (running) {
			long currentTime = System.nanoTime();
			long passedTime = currentTime - previousTime;
			previousTime = currentTime;
			unprocessedSeconds += passedTime / 1000000000.0;
			requestFocus();
			
			while (unprocessedSeconds > secondsPerTick) {
				tick();
				unprocessedSeconds -= secondsPerTick;
				ticked = true;
				tickCount++;
				if (tickCount % 60 == 0) {
					//System.out.println(frames + "fps");
					fps = frames;
					previousTime += 1000;
					frames = 0;
				}

			}
			if (ticked) {
				render();
				frames++;
			}
			render();
			frames++;
		}
	}
	
	
	private void tick() {
		buttonTick();
		if(gameDisplaying) {			
			mapHandler.tick(buttonHandler);
		}
		if(input.key[KeyEvent.VK_ESCAPE]) {
			stop();
		}
	}
	
	//checks for button updates to screen
	public void buttonTick() {
		buttonHandler.tick(input.key);
		if(buttonHandler.buttonList[2].getButtonState()){//Exit is clicked
			stop();
		}
		gameDisplaying = !buttonHandler.getPauseGame();//see if you should render game
		for(int i = 0; i < buttonHandler.getLevelState().length; i++) {
			if(buttonHandler.getLevelState()[i]) {
				mapHandler.setCurrentMap(i);
				gameDisplaying = true;
				buttonHandler.resetLevelStates();
			}
		}
	}
	Texture backgroundMM = new Texture(directory + "/res/BackgroundMainMenu.png");
	Texture backgroundMS = new Texture(directory + "/res/PixelatedLevelSelectBackground.jpg");
	Texture CreditAndEnd = new Texture(directory + "/res/Credits.png");
	Texture losingScreen = new Texture(directory + "/res/losingScreen.png");
	Texture HTP = new Texture(directory + "/res/HowToPlay.png");

	
	int timeC = 1;
	boolean undoC = false;
	//renders if needs
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		if(buttonHandler.areVisable("Start screen") || buttonHandler.areVisable("Map selector")) {
			g.clearRect(0, 0, WIDTH * 2, HEIGHT * 2);
		}
		
		if(buttonHandler.areVisable("Start Screen")) {
			g.clearRect(0, 0, WIDTH * 2, HEIGHT * 2);
			g.drawImage(backgroundMM.getTexture(), 0, 0, WIDTH, HEIGHT, null);

		}
		
		if(buttonHandler.areVisable("Map selector")) {
			g.clearRect(0, 0, WIDTH * 2, HEIGHT * 2);
			g.drawImage(backgroundMS.getTexture(), 0, 0, WIDTH, HEIGHT, null);
		}
		
		if(gameDisplaying) {			
			mapHandler.render(g);
			g.setFont(new Font("Arial", 0, 25));
			g.setColor(Color.CYAN);
			g.drawString(fps +" FPS", 10 + + mapHandler.getCurrentMap().getOrigin().x, 50 + mapHandler.getCurrentMap().getOrigin().y);
			g.drawString("Score:" + score, 10 + + mapHandler.getCurrentMap().getOrigin().x, 25 + mapHandler.getCurrentMap().getOrigin().y);
		
		}
		
		if(buttonHandler.getControlScreenState()) {
			g.clearRect(0, 0, WIDTH * 2, HEIGHT * 2);
			g.drawImage(buttonHandler.getControlScreen().getTexture(), 0, 0, WIDTH, HEIGHT, null);
		}
		
		if(buttonHandler.getHTPState()) {
			g.clearRect(0, 0, WIDTH * 2, HEIGHT * 2);
			g.drawImage(HTP.getTexture(), 0, 0, WIDTH, HEIGHT, null);
		}	

		if((Map.MapHandler.lose || Map.MapHandler.end) && timeC % 2000 != 0) {//credits
			timeC++;
			buttonHandler.disableAll();
			score = 0;
			if(Map.MapHandler.end) {
				g.drawImage(CreditAndEnd.getTexture(), 0, 0, WIDTH, HEIGHT, null);
			} 
			if(Map.MapHandler.lose) {
				g.drawImage(losingScreen.getTexture(), 0, 0, WIDTH, HEIGHT, null);
			}
			if(timeC % 1999 == 1) {
				timeC = 0;
				undoC = true;
			}
		} 
		
		if(undoC) {
			buttonHandler.enable("Start Screen");
			undoC = false;
			mapHandler.lose = false;
			mapHandler.end = false;
		}
		
		g.dispose();
		bs.show();
	}
	public static void main(String[] args) {
		Display game = new Display();
		JFrame frame = new JFrame(TITLE);
		for(int i = 0; i < game.buttonHandler.buttonList.length; i++){
			frame.add(game.buttonHandler.buttonList[i].getButton());
		}
		frame.add(game.buttonHandler.getQuestionPromt());
		frame.add(game.buttonHandler.getDialogue());
		frame.add(game);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		game.start();
		
		//Audio
		Mixer mixer;
		Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();
		mixer = AudioSystem.getMixer(mixInfos[0]);
		DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
		Clip clip = null;
		try { clip = (Clip)mixer.getLine(dataInfo); }
		catch(LineUnavailableException lue) {lue.printStackTrace(); }
		String directory = System.getProperty("user.dir") + "/Absegami HS_5897_PatelPatel_ComputerGameandSimulationProgramming";
		try {
			URL soundURL = new URL("file:///" + directory + "/res/Pim Poy.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
			clip.open(audioStream);
		}
		catch(LineUnavailableException lue) {lue.printStackTrace();}
		catch(UnsupportedAudioFileException uafe) {uafe.printStackTrace(); }
		catch(IOException ioe) {ioe.printStackTrace();}
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		
		try {Thread.sleep(clip.getMicrosecondLength()/1000);}
		catch(InterruptedException ie) {ie.printStackTrace();}
	
		
	}
}