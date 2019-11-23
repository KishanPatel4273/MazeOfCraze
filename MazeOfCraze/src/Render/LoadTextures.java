package Render;

import java.awt.Color;
import java.util.ArrayList;

public class LoadTextures {
	
	private ArrayList<Texture> texture;
	private ArrayList<String> path;
	
	public LoadTextures(ArrayList<String> path) {
		this.path = path;
		texture = new ArrayList<Texture>();
		load();
	}
	
	public LoadTextures(ArrayList<Texture> t, int n) {
		path = new ArrayList<String>();
		texture = new ArrayList<Texture>();

		texture = t;
		
		for(int i = 0; i < texture.size(); i++) {
			path.add(texture.get(i).getPath());
		}
	}
	
	//load text file
	private void load() {
		String keyC = "collidable:";
		String keyI = "index:";
		String divider = " ";
		for(int i = 0; i < path.size(); i++) {
			String currentPath = path.get(i);
			if (currentPath.indexOf(keyC) > -1 && currentPath.indexOf(keyI) > -1) {
				String p = currentPath.substring(0, currentPath.indexOf(keyC) - 1); 
				currentPath = currentPath.substring(currentPath.indexOf(keyC) + keyC.length());
				String c = currentPath.substring(0, currentPath.indexOf(keyI) - 1);
				currentPath = currentPath.substring(currentPath.indexOf(keyI) + keyI.length());
				String r = currentPath.substring(0, currentPath.indexOf(divider));
				currentPath = currentPath.substring(currentPath.indexOf(r) + r.length() + 1);
				String g = currentPath.substring(0 , currentPath.indexOf(divider));
				currentPath = currentPath.substring(currentPath.indexOf(g) + g.length() + 1);
				String b = currentPath;
				if(c.indexOf("true") > -1) {
					texture.add(new Texture(p, true, new Color(Integer.valueOf(r), Integer.valueOf(g), Integer.valueOf(b))));
				} else {
					texture.add(new Texture(p, false, new Color(Integer.valueOf(r), Integer.valueOf(g), Integer.valueOf(b))));
				}
			} else if (currentPath.indexOf(keyI) > -1) {
				String p = currentPath.substring(0, currentPath.indexOf(keyI) - 1); 
				currentPath = currentPath.substring(currentPath.indexOf(keyI) + keyI.length());
				String r = currentPath.substring(0, currentPath.indexOf(divider));
				currentPath = currentPath.substring(currentPath.indexOf(r) + r.length() + 1);
				String g = currentPath.substring(0 , currentPath.indexOf(divider));
				currentPath = currentPath.substring(currentPath.indexOf(g) + g.length() + 1);
				String b = currentPath;
				texture.add(new Texture(p, new Color(Integer.valueOf(r), Integer.valueOf(g), Integer.valueOf(b))));
			} else if(currentPath.indexOf(keyC) > -1) {
				String p = currentPath.substring(0, currentPath.indexOf(keyC) - 1); 
				if(currentPath.indexOf("true") > -1) {
					texture.add(new Texture(p, true));
				} else {
					texture.add(new Texture(p, false));
				}
			} else {
				texture.add(new Texture(currentPath));
			} 	
		}
	}
	
	public ArrayList<Texture> getTextures(){
		return texture;
	}
	
	//return texture with containing path
	public Texture getTexture(String Path) {
		for(int i = 0; i <  path.size(); i++) {
			if(path.get(i).indexOf(Path) > -1) {
				return texture.get(i);
			}
		}
		return null;
	}
}
