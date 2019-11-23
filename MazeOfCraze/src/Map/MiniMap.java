package Map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import Entity.Entity;
import Render.Texture;

public class MiniMap {

	//class is disable to increase difficulty
	private int pixels[];
	private int width, height;
	private int px, py;
	private int scale;
	
	public MiniMap(int width, int height, int scale) {
		this.width = width;
		this.height = height;
		this.scale = scale;
		pixels = new int[width * height];
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = 16777215;
		}
	}
	
	//plots new points to fill out mini-map
	public void update(Map map) {
		for(Entity e: Entity.entities) {
			int r = e.getY() / map.scale;//y
			int c = e.getX() / map.scale;//x
			if(map.inFrame(e)) {
				if(e.isCollidable() && e.getTag().equals("Entity")) {
					pixels[c + r * width] = 0;
				}
			}
		}
		px = map.getPlayer().getX() / map.scale;
		py = map.getPlayer().getY() / map.scale;
	}
	
	//render minimap to screen
	public void render(Point origin, Graphics g) {
		g.drawRect(origin.x - 1, origin.y - 1, width * scale + 2, height * scale + 2);
		for(int x = 0; x < width;x++) {
			for(int y = 0; y < height; y++) {
				g.setColor(Texture.getINRGBTORGB(pixels[x + y * width]));
				g.drawRect(origin.x + x + ((scale - 1) * x), origin.y + y + ((scale - 1) * y), scale, scale);
			}
		}
		g.setColor(Color.RED);
		g.fillRect(origin.x + px + ((scale - 1) * px), origin.y + py + ((scale - 1) * py), scale, scale);
	}	
}
