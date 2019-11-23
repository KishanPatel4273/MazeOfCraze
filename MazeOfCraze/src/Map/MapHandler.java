 package Map;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import Display.ButtonHandler;
import Display.Display;
import Render.LoadTextures;
import Render.Texture;

public class MapHandler {
	
	
	private String directory = System.getProperty("user.dir") + "\\Absegami HS_5897_PatelPatel_ComputerGameandSimulationProgramming";
	private String[] mapPaths, levelPaths;
	private Map currentMap;
	private LoadTextures textures;
	private String texturePaths = directory + "/res/TexturePaths.txt";
	private int width, height, scale, currentMapIndex, currentDifficulty;
	protected int[] difficulty;
	public static boolean[] locked = {true, false, false, false, false};
	public static boolean closeGame1 = false;
	public static int currentLevel = 0;
	public static boolean end = false;
	public static boolean lose = false;
	
	public MapHandler(ButtonHandler buttonHandler) {
		width = Display.WIDTH; height = Display.HEIGHT; scale = Display.scale;
		//textures = new LoadTextures(new LoadFile(texturePaths).getFileText());
		ArrayList<Texture> t = new ArrayList<Texture>();
		t.add(new Texture(directory + "/res/ground.png", false, new Color(255,255,255)));
		t.add(new Texture(directory + "/res/wall.png", true, new Color(0,0,0)));
		t.add(new Texture(directory + "/res/projectile.png", true));
		t.add(new Texture(directory + "/res/closechest.png"));
		textures = new LoadTextures(t,1);
		mapPaths = new String[]{directory + "/res/Map1.png", directory + "/res/Map2.png", directory + "/res/Map3.png", directory + "/res/Map4.png", directory + "/res/Map5.png"};
		levelPaths = new String[]{directory + "\\res\\Level1.txt", directory + "/res/Level2.txt", directory + "/res/Level3.txt", directory + "/res/Level4.txt", directory + "/res/Level5.txt"};
		currentMapIndex = 0;
		difficulty = new int[] {1, 2, 3, 4, 5};
		currentMap = new Map(mapPaths[currentMapIndex], textures, levelPaths[currentMapIndex],width, height, scale, this);
	}
	
	public void render(Graphics g) {
		currentMap.render(g);
	}
	
	public void tick(ButtonHandler buttonHandler) {
		currentMap.tick(buttonHandler);
	}
	
	//changes map thought changing index
	public void setCurrentMap(int index) { 
		if(locked[index]) {
			currentMap = new Map(mapPaths[index], textures, levelPaths[index], width, height, scale, this);
			currentDifficulty = difficulty[index];
			currentMap.setDifficulty(getDifficulty());
			currentLevel = index;
		} else {
			closeGame1 = true;
		}
		
	}
	
	public int getDifficulty() {
		return currentDifficulty;
	}
	
	public Map getCurrentMap() {
		return currentMap;
	}
}	
