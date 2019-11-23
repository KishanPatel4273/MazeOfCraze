package Map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import Render.*;
import Display.*;
import Entity.*;
import Input.Controller;
import Questions.QuestionHandler;

public class Map {
	
	private ArrayList<Entity> entities;
	private ArrayList<Chest> chests;
	private ArrayList<Door> doors;
	
	private QuestionHandler questionHandler;
	private LoadTextures textures;
	private int width, height;
	protected int scale, mapWidth, mapHeight;
	private Player player;
	private Controller controller;
	private MiniMap miniMap;
	private int difficulty;
	private Exit exit;
	
	/**
	 * 
	 * @param mapPath map file Path
	 * @param texturePaths texture file path
	 * @param textFilePath level text file path
	 * @param scale 
	 */
 	public Map(String mapPath, String texturePaths, String textFilePath, Player player, int width, int height, int scale) {
		init();
		this.width = width;
		this.height = height;
		this.scale = scale;
		this.player = player;
		entities = new ArrayList<Entity>();
		chests = new ArrayList<Chest>();
		doors =  new ArrayList<Door>();
		
		textures = new LoadTextures(new LoadFile(texturePaths).getFileText());
		readFile(new LoadFile(textFilePath).getFileText());
		generateMap(new Texture(mapPath));

		Entity.initEntity(entities);//add all the entities to main list
		Chest.initChest(chests);
		Door.initDoor(doors);
 
		controller = new Controller();
	}
		
	public Map(String mapPath,LoadTextures textures, String textFilePath, int width, int height, int scale, MapHandler mapHandler) {
		init();
		this.width = width;
		this.height = height;
		this.scale = scale;
		this.textures = textures;
		entities = new ArrayList<Entity>();
		chests = new ArrayList<Chest>();
		doors =  new ArrayList<Door>();

		readFile(new LoadFile(textFilePath).getFileText());
		generateMap(new Texture(mapPath));

		Entity.initEntity(entities);//add all the entities to main list
		Chest.initChest(chests);
		Door.initDoor(doors);

		player = new Player(2 * scale/2+1,  2 * scale/2+1, 1, 2, scale/2);
		controller = new Controller();
		miniMap = new MiniMap(mapWidth / scale, mapHeight /  scale, 4);
		
		questionHandler = new QuestionHandler();
	}
	
	private void init() {
		Projectile.projectiles.clear();
		Chest.chests.clear();
		Door.doors.clear();
	}
	
	//reads text file to load objects quickly
	public void readFile(ArrayList<String> fileText) {
		String keyC = "Chest:";
		String keyD = "Door:";
		String keyE = "Exit:";
		String divider = " ";
		for(int i = 0; i < fileText.size(); i++) {
			String currentPath = fileText.get(i);
			if(currentPath.indexOf(keyC) > -1) {
				currentPath = currentPath.substring(currentPath.indexOf(keyC) + keyC.length());
				int x = Integer.valueOf(currentPath.substring(0, currentPath.indexOf(divider)));
				currentPath = currentPath.substring(currentPath.indexOf(divider) + 1);
				int y = Integer.valueOf(currentPath.substring(0, currentPath.indexOf(divider)));
				currentPath = currentPath.substring(currentPath.indexOf(divider) + 1);
				int width = Integer.valueOf(currentPath.substring(0, currentPath.indexOf(divider)));
				currentPath = currentPath.substring(currentPath.indexOf(divider) + 1);
				int height = Integer.valueOf(currentPath.substring(0));
				chests.add(new Chest(x * scale + (scale - (int) (width * (scale/500f)))/2, y * scale + (scale - (int) (height * (scale/500f)))/2, (int) (width * (scale/500f)), (int) (height * (scale/500f)), textures.getTexture("closechest"), scale / 3));
				entities.add(chests.get(chests.size() - 1));
			}
			if(currentPath.indexOf(keyD) > -1) {
				currentPath = currentPath.substring(currentPath.indexOf(keyD) + keyD.length());
				int x = Integer.valueOf(currentPath.substring(0, currentPath.indexOf(divider)));
				currentPath = currentPath.substring(currentPath.indexOf(divider) + 1);
				int y = Integer.valueOf(currentPath.substring(0, currentPath.indexOf(divider)));
				currentPath = currentPath.substring(currentPath.indexOf(divider) + 1);
				int width = Integer.valueOf(currentPath.substring(0, currentPath.indexOf(divider)));
				currentPath = currentPath.substring(currentPath.indexOf(divider) + 1);
				int height = Integer.valueOf(currentPath.substring(0));
				doors.add(new Door(x * scale, y * scale, (int) (width * (scale/500f)), (int) (height * (scale/500f)), 3425687, scale / 4));
				entities.add(doors.get(doors.size() - 1));
			}
			if(currentPath.indexOf(keyE) > -1) {
				currentPath = currentPath.substring(currentPath.indexOf(keyE) + keyE.length());
				int x = Integer.valueOf(currentPath.substring(0, currentPath.indexOf(divider)));
				currentPath = currentPath.substring(currentPath.indexOf(divider) + 1);
				int y = Integer.valueOf(currentPath.substring(0, currentPath.indexOf(divider)));
				currentPath = currentPath.substring(currentPath.indexOf(divider) + 1);
				int width = Integer.valueOf(currentPath.substring(0, currentPath.indexOf(divider)));
				currentPath = currentPath.substring(currentPath.indexOf(divider) + 1);
				int height = Integer.valueOf(currentPath.substring(0));
				exit = new Exit(x * scale + (scale - (int) (width * (scale/500f)))/2, y * scale + (scale - (int) (height * (scale/500f)))/2, (int) (width * (scale/500f)), (int) (height * (scale/500f)), scale / 4);
				entities.add(exit);
			}
		}
	}
	
	//reads png file to make the map, each pixel color value goes with to object
	public void generateMap(Texture map) {
		int[] pixels = map.getPixelValues();
		mapWidth = map.getTexture().getWidth() * scale;
		mapHeight = map.getTexture().getHeight() * scale;
		for(int x = 0; x < map.getTexture().getWidth(); x++) {
			for(int y = 0; y < map.getTexture().getHeight(); y++) {
				for(int i = 0; i < textures.getTextures().size(); i++) {
					if(textures.getTextures().get(i).equalsColorIdex(Texture.getINRGBTORGB(pixels[x + y * map.getTexture().getWidth()]))) {
						entities.add(new Entity(x * scale, y * scale, scale, scale, textures.getTextures().get(i), textures.getTextures().get(i).isCollidable()));
						break;
					}
				}
			}
		}
	}
	
	//renders all the objects
	public void render(Graphics g) {	
		g.translate(width/2 - player.getX(), height/2 - player.getY());//keeps player in the center
		g.clearRect(player.getX() - width/2, player.getY() - height/2, (int) (width * 1.5f), (int) (height * 1.5f));
		
		g.setColor(Color.BLACK);
		g.fillRect(getFrame().x, getFrame().y, getFrame().width, getFrame().height);
		
		for(Entity entity: Entity.entities) {//entities	
			if(inFrame(entity) && entity.getTag().equals("Entity")) {
				entity.render(g);
			}
		}
	
		exit.render(g);
		
		for(Chest chests: Chest.chests) {//chest
			chests.render(g);
		}
	
		for(Door doors: Door.doors) {//door
			doors.render(g);
		}
		
		for(Projectile p: Projectile.projectiles) {//projectile
			p.render(g);
		}
		
		//miniMap
		//miniMap.render(new Point(getOrigin().x + (width - mapWidth/scale) - 100, getOrigin().y), g);
		//player
		player.render(g);
		//player.renderInfo(getOrigin(), g);
	}
	
	//updates all objects
	public void tick(ButtonHandler buttonHandler) {
		if(!buttonHandler.getPausePlayer()) {
			player = controller.tick(Display.input.key, player, this);
		}
		player.tick(this);
		questionHandler.tick(buttonHandler);
		for(Door door: Door.doors){
			door.tick(player, buttonHandler, questionHandler, difficulty);
		}
		
		for(int i = 0; i < Projectile.projectiles.size(); i++) {
			Projectile.projectiles.get(i).tick(this);
			if(!inFrame(Projectile.projectiles.get(i))) {
				Projectile.projectiles.get(i).deactivate();
			}
			if(!Projectile.projectiles.get(i).isActive()) {
				Projectile.projectiles.remove(i);
				i--;
			}
		}
		
		for(int i = 0; i < Chest.chests.size(); i++) {
			Chest.chests.get(i).tick(player, buttonHandler, MapHandler.currentLevel+1);
		}	

		miniMap.update(this);
	}

	//see if it should render entity
	public boolean inFrame(Entity e) {
		return getFrame().intersects(e.getBounds());
	}
	
	public Rectangle getFrame() { 
		return new Rectangle((int) (player.getCenter().getX() - width / 2 - scale), (int) (player.getCenter().getY() - height / 2 - scale), width + 2 * scale, height + 2 * scale); 
	}
	
	public Point getOrigin() {
		return new Point(player.getX() - width / 2, player.getY() - height / 2);
	}
	
	public Player getPlayer() {
		return player;
	}

	public LoadTextures getTextures() {
		return textures;
	}

	public QuestionHandler getQuestionHandler() {
		return questionHandler;
	}	
	
	public void setDifficulty(int d) {
		difficulty = d;
	}
	
	public Exit getExit() {
		return exit;
	}
}