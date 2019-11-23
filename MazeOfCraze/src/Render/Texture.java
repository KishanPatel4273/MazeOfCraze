package Render;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Texture {
	
	private BufferedImage image;
	private int id;
	private Color colorIndex;
	private String path;
	private boolean collidable;// for entity detection
	private static int idLoc;
	
	//load image file
	public Texture(String path) {
		this.path = path;
		id = idLoc++;
		try {
			if(ResourceLoader.load(path) == null) {
				image = ImageIO.read(new File(path));
			} else {
				image = ImageIO.read(ResourceLoader.load(path));
			}
			//System.out.println(path + " loaded");
		} catch (IOException e) {
			//System.out.println(path);
			e.printStackTrace();
		}
	}
	
	public Texture(String path, Color colorIndex) {
		this.path = path;
		id = idLoc++;
		this.colorIndex = colorIndex;
		try {
			if(ResourceLoader.load(path) == null) {
				image = ImageIO.read(ResourceLoader.load(path));
			} else {
				image = ImageIO.read(ResourceLoader.load(path));
			}
			//System.out.println(path + " loaded");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Texture(String path, boolean collidable) {
		this.path = path;
		id = idLoc++;
		this.collidable = collidable;
		try {
			if(ResourceLoader.load(path) == null) {
				image = ImageIO.read(new File(path));
			} else {
				image = ImageIO.read(ResourceLoader.load(path));
			}
			//System.out.println(path + " loaded");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Texture(String path,boolean collidable, Color colorIndex) {
		this.path = path;
		id = idLoc++;
		this.colorIndex = colorIndex;
		this.collidable = collidable;
		try {
			if(ResourceLoader.load(path) == null) {
				image = ImageIO.read(new File(path));
			} else {
				image = ImageIO.read(ResourceLoader.load(path));
			}
			//System.out.println(path + " loaded");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//return list of RGB ints in image
	public int[] getPixelValues() {
		int[] pixels = new int[image.getHeight() * image.getWidth()];
		for(int r = 0; r < image.getWidth(); r++){
			for(int c = 0;c < image.getHeight(); c++){
				pixels[c + r * image.getWidth()] = image.getRGB(c, r);
			}
		}
		return pixels;
	}
	
	//convet RGB int to color class
	public static Color getINRGBTORGB(int intRGB) {
		int r = (intRGB >> 16) &  0xFF;
		int g = (intRGB >> 8) & 0xFF;
		int b = (intRGB >> 0) & 0xFF;
		return new Color(r,g,b);
	}
	
	public BufferedImage getTexture() {
		return image;
	}
	
	public int getID() {
		return id;
	}
	
	public void setColorIndex(Color colorIndex) {
		this.colorIndex = colorIndex;
	}
	
	public Color getColorIndex() {
		return colorIndex;
	}
	
	public boolean isCollidable() {
		return collidable;
	}
	
	public String getPath() {
		return path;
	}
	
	//checks if Color c is the smame as the color tag
	public boolean equalsColorIdex(Color c) {
		if(colorIndex == null) {
			return false;
		} else {
			return colorIndex.getRed() == c.getRed() && colorIndex.getGreen() == c.getGreen() && colorIndex.getBlue() == c.getBlue();
		}
	}
}