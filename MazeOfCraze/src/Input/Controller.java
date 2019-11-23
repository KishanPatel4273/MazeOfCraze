package Input;

import java.awt.event.KeyEvent;

import Entity.Entity;
import Entity.Player;
import Map.Map;

public class Controller {
	
	private int currentSpeed;
	public Player tick(boolean[] key, Player player, Map map) {
		
		//sets speed of player
		currentSpeed = player.getWallingSpeed();
		if(key[KeyEvent.VK_SHIFT]) {
			currentSpeed = player.getRunningSpeed();
		}
		
		//Collision detection
		for(Entity entity: Entity.entities) {			
			if(entity.isCollidable() && map.inFrame(entity)) {
				if(entity.getBounds().intersects(player.getUpperBound())) {//top
					key[KeyEvent.VK_W] = false;
					player.addY(currentSpeed);
				}  
				if(entity.getBounds().intersects(player.getBottomBound())) {//bottom
					key[KeyEvent.VK_S] = false;	
					player.addY(-currentSpeed);
				} 
				if(entity.getBounds().intersects(player.getRightBound())) {//right
					key[KeyEvent.VK_D] = false;
					player.addX(-currentSpeed);
				}  
				if(entity.getBounds().intersects(player.getLeftBound())) {//left
					key[KeyEvent.VK_A] = false;
					player.addX(currentSpeed * 2);
				}
			}
		}
	
		//updates exit object
		if(map.getExit() != null && key[KeyEvent.VK_E]) {
			map.getExit().tick(player);
		}
		
		//movement
		if(key[KeyEvent.VK_W]) {
			player.addY(-currentSpeed);
			player.setTexture(Player.up);
		}
		if(key[KeyEvent.VK_A]) {
			player.addX(-currentSpeed);
			player.setTexture(Player.left);
		}
		if(key[KeyEvent.VK_S]) {
			player.addY(currentSpeed);
			player.setTexture(Player.down);
		}
		if(key[KeyEvent.VK_D]) {
			player.addX(currentSpeed);
			player.setTexture(Player.right);
		}
		
		return player;
	}
}