package Display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import Map.MapHandler;
import Render.Texture;

public class ButtonHandler implements ActionListener{
	
	private int scaleB = 1, width = Display.WIDTH, height = Display.HEIGHT;
	private int buttonW = 150 * scaleB, buttonH = buttonW / 4;
	protected Button[] buttonList;
	private boolean[] levelState;
	private boolean pauseGame, pausePlayer, controlScreenState, HTPState; 
	private JLabel queationPromt, dialogue;
	private Texture controlScreen;
	private String directory = System.getProperty("user.dir") + "/Absegami HS_5897_PatelPatel_ComputerGameandSimulationProgramming";

	
	public ButtonHandler(Display Display) {
		controlScreen = new Texture(directory + "/res/Controls.png");
		
		buttonList = new Button[24];
		buttonList[0] = new Button(new JButton(), width / 2 - buttonW / 2, (height - (int)(buttonH *3 - buttonH/2)) / 2 - buttonH / 2 + 200, buttonW, buttonH, "Start", true, "Start Screen");
		buttonList[1] = new Button(new JButton(), width / 2 - buttonW / 2, (height - (int)(buttonH * 0 - buttonH/2)) / 2 - buttonH / 2 + 200, buttonW, buttonH, "Controls", true, "Start Screen");
		buttonList[2] = new Button(new JButton(), width / 2 - buttonW / 2, (height + (int)(buttonH * 7 - buttonH/2)) / 2 - buttonH /2 + 200, buttonW, buttonH, "Quit", true, "Start Screen");
		
		buttonList[3] = new Button(new JButton(), width - buttonW, 0, buttonW, buttonH, "Menu", false, "Menu");
		buttonList[4] = new Button(new JButton(), width - buttonW, 0, buttonW, buttonH, "Back", false, "Back");
		buttonList[5] = new Button(new JButton(), width / 2 - buttonW / 2, (height - (int)(buttonH *10 - buttonH/4)) / 2 - buttonH / 2, buttonW, buttonH, "Main Menu", false, "Main Menu");
		buttonList[6] = new Button(new JButton(), width / 2 - buttonW / 2, (height - (int)(buttonH *0 - buttonH/4)) / 2 - buttonH / 2 + 40, buttonW, buttonH, "Continue", false, "Main Menu");
		buttonList[16] = new Button(new JButton(), width - buttonW, 0, buttonW, buttonH, "Back", false, "Start Controls");
		buttonList[17] = new Button(new JButton(), width / 2 - buttonW / 2, (height - (int)(buttonH *6 - buttonH/4)) / 2 - buttonH / 2, buttonW, buttonH, "Controls", false, "Main Menu");
		buttonList[18] = new Button(new JButton(), width - buttonW, 0, buttonW, buttonH, "Back", false, "Pause Controls");

		buttonList[20] = new Button(new JButton(), width - buttonW, 0, buttonW, buttonH, "Back", false, "Start HTP");
		buttonList[21] = new Button(new JButton(), width / 2 - buttonW / 2, (height - (int)(buttonH *2 - buttonH/4)) / 2 - buttonH / 2, buttonW, buttonH, "How to Play", false, "Main Menu");
		buttonList[22] = new Button(new JButton(), width - buttonW, 0, buttonW, buttonH, "Back", false, "Pause HTP");
		buttonList[23] = new Button(new JButton(), width / 2 - buttonW / 2, (height + (int)(buttonH * 4 - buttonH/2)) / 2 - buttonH / 2 + 200, buttonW, buttonH, "How to Play", true, "Start Screen");


		
		//level picker screen
		buttonList[7] = new Button(new JButton(), width / 2 - buttonW / 2, ((int)(buttonH * 6 - buttonH)) / 2 - buttonH /2, buttonW, buttonH, "Level 1", false, "Map selector");
		buttonList[8] = new Button(new JButton(), width / 2 - buttonW / 2, ((int)(buttonH * 12 - buttonH)) / 2 - buttonH /2, buttonW, buttonH, "Level 2", false, "Map selector");
		buttonList[9] = new Button(new JButton(), width / 2 - buttonW / 2, ((int)(buttonH * 18 - buttonH)) / 2 - buttonH /2, buttonW, buttonH, "Level 3", false, "Map selector");
		buttonList[10] = new Button(new JButton(), width / 2 - buttonW / 2, ((int)(buttonH * 24 - buttonH)) / 2 - buttonH /2, buttonW, buttonH, "Level 4", false, "Map selector");
		buttonList[11] = new Button(new JButton(), width / 2 - buttonW / 2, ((int)(buttonH * 30 - buttonH)) / 2 - buttonH /2, buttonW, buttonH, "Level 5", false, "Map selector");
		buttonList[19] = new Button(new JButton(), width / 2 - buttonW / 2, ((int)(buttonH * 45 - buttonH)) / 2 - buttonH /2, buttonW, buttonH, "Reset", false, "Map selector");

		//Question screen
		int a = 78, b = 20;
		buttonList[12] = new Button(new JButton(), width/2 - a - buttonW/2, height/2 - b - buttonH/2, buttonW, buttonH, "A", false, "Answer Choice");			
		buttonList[13] = new Button(new JButton(), width/2 + a - buttonW/2, height/2 - b - buttonH/2, buttonW, buttonH, "B", false, "Answer Choice");
		buttonList[14] = new Button(new JButton(), width/2 - a - buttonW/2, height/2 + b - buttonH/2, buttonW, buttonH, "C", false, "Answer Choice");
		buttonList[15] = new Button(new JButton(), width/2 + a - buttonW/2, height/2 + b - buttonH/2, buttonW, buttonH, "D", false, "Answer Choice");
		int qpw = 500, qph = 200;
		queationPromt = new JLabel();
		queationPromt.setBounds(width/2 - qpw/2,  height/2 - 10 - qph - b - buttonH/2, qpw, qph);
		queationPromt.setVisible(false);
		
		qpw = 100; qph = 20;
		dialogue = new JLabel();
		dialogue.setBounds(width/2 - qpw/2,  height/2 - 10 - qph - b - buttonH/2, qpw, qph);
		dialogue.setVisible(false);
		
		for(int i = 0; i < buttonList.length; i++){
			buttonList[i].button.addActionListener(this);
		}
		
		levelState = new boolean[] {false, false, false, false, false};
		pauseGame = true;
		pausePlayer = false;
	}

	
	//if button is clicked it will run the if statement
	public void tick(boolean[] key) {
		if(buttonList[0].getButtonState()) {//Start is clicked
			disableAll();
			enable("Map selector");	
			enable("Back");
		}
		if(buttonList[1].getButtonState()){//controls is clicked(start screen)
			disableAll();
			enable("Start Controls");
			controlScreenState = true;
		}
		if(buttonList[16].getButtonState()){//back in control screen is clicked
			disableAll();
			enable("Start Screen");
			controlScreenState = false;
		}
		if(buttonList[17].getButtonState()){//controls is clicked(pause menu)
			disableAll();
			enable("Pause Controls");
			controlScreenState = true;
		}
		if(buttonList[18].getButtonState()){//back in control screen is clicked
			disableAll();
			enable("Menu");
			controlScreenState = false;
			pauseGame = false;
		}
		
		if(buttonList[3].getButtonState()) {//Menu is clicked(Game screen) //key[KeyEvent.VK_ESCAPE] || 
			disableAll();
			enable("Main Menu");
			pauseGame = true;
		}
		if(buttonList[4].getButtonState()) {//Back is clicked(game select screen)
			disableAll();
			enable("Start screen");
			pauseGame = true;
		}
		if(buttonList[5].getButtonState()) {//Main Menu is clicked(game pause menu screen)
			disableAll();
			enable("Start screen");
			pauseGame = true;
		}
		if(buttonList[6].getButtonState()) {//Back (pause screen)is clicked
			disableAll();
			enable("Menu");
			pauseGame = false;
		}
		if(MapHandler.closeGame1) {
			MapHandler.closeGame1 = false;
			disableAll();
			enable("Map selector");	
			enable("Back");
			pauseGame = true;
		}
		if(buttonList[19].getButtonState()) {
			MapHandler.locked = new boolean[]{true, false, false, false, false};
			Display.score = 0;
			MapHandler.end = false;
		}
		if(buttonList[23].getButtonState()) {//HTP is clicked in start screen
			disableAll();
			enable("Start HTP");
			HTPState = true;
		}
		if(buttonList[20].getButtonState()) {//back is clicked in HTP screen
			disableAll();
			enable("Start Screen");
			HTPState = false;
		}
		if(buttonList[21].getButtonState()) {//HTP is clicked in main menu screen
			disableAll();
			enable("Pause HTP");
			HTPState = true;
			pauseGame = true;
		}
		if(buttonList[22].getButtonState()) {//back is clicked in HTP screen
			disableAll();
			enable("Menu");
			HTPState = false;
			pauseGame = false;
		}
		
		if(getLevelIndex() > -1) {//Level1 is clicked
			levelState[getLevelIndex()] = true;
			pauseGame = false;
			disableAll();
 			enable("Menu");
		}
	}
	
	//gets the letter of button clicked, used for asking question
	public String getAnswerPressed() {
		for(Button button : buttonList) {
			if(button.getTag().equals("Answer Choice") && button.getButtonState()) {
				return button.getTitle();
			}
		}
		return "-1";
	}
	
	//hides the question gui
	public void turnOffQuestion() {
		disableAllButQuestion();
		queationPromt.setVisible(false);
		enable("Menu");
		pauseGame = false;
		pausePlayer = false;
	}
	
	//shows the question gui
	public void turnOnQuestion() {
		disableAll();
		enable("Answer Choice");
		enable("Menu");
		queationPromt.setVisible(true);
		pauseGame = false;
		pausePlayer = true;
	}
	
	//gets index of the next level
	public int getLevelIndex() {
		for(int i = 0; i < buttonList.length; i++){
			if(buttonList[i].getButtonState() && buttonList[i].getTitle().toLowerCase().indexOf("level") > -1) {
				for(int j = 0; j < levelState.length; j++) {
					if(buttonList[i].getTitle().toLowerCase().indexOf(j+1 + "") > -1) {
						return j;
					}
				}
			}
		}
		return -1;
	}
	
	//checks if a button is clicked
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		for(int i = 0; i < buttonList.length; i++){
			buttonList[i].update(src);
		}
	}
	
	//turns of all buttons with the same tag
	public void enable(String tag) {
		for(int i = 0; i < buttonList.length; i++){
			if(buttonList[i].getTag().toLowerCase().equals(tag.toLowerCase())) {
				buttonList[i].visablityState(true);
				buttonList[i].setButtonState(false);
			}
		}
	}
	
	//turns on the button with the correct index
	public void enable(int index) {
		buttonList[index].visablityState(true);
		buttonList[index].setButtonState(false);
	}
	
	//turns off all buttons
	public void disableAll(){
		for(int i = 0; i < buttonList.length; i++){
			buttonList[i].visablityState(false);
			buttonList[i].setButtonState(false);
		}
		queationPromt.setVisible(false);
		pausePlayer = false;
	}
	
	//turns every thing off, except qui components for asking question
	public void disableAllButQuestion(){
		for(int i = 0; i < buttonList.length; i++){
			buttonList[i].visablityState(false);
			buttonList[i].setButtonState(false);
		}
	}
	
	//diables button with inedx
	public void disable(int index) {
		buttonList[index].visablityState(false);
		buttonList[index].setButtonState(false);
	}
	
	//turns everything off excepts thing with the right tag
	public void disableAllBut(String tag) {
		for(int i = 0; i < buttonList.length; i++){
			if(!buttonList[i].getTag().toLowerCase().equals(tag.toLowerCase())) {
				buttonList[i].visablityState(false);
				buttonList[i].setButtonState(false);
			} else {
				buttonList[i].visablityState(true);
				buttonList[i].setButtonState(true);
			}
		}
	}
	
	//turns every thing off excepts thing with the tag
	public void disableAllBut(String[] tags) {
		for(int i = 0; i < buttonList.length; i++){
			int count = 0;
			for(int j = 0; j < tags.length; j++) {
				if(!buttonList[i].getTag().toLowerCase().equals(tags[j].toLowerCase())) {
					count++;
				} 
			}
			System.out.println(count + " " + buttonList[i].getTag());
			if(count < tags.length) {
				buttonList[i].visablityState(false);
				buttonList[i].setButtonState(false);
			} else {
				buttonList[i].visablityState(true);
				buttonList[i].setButtonState(true);
			}
		}
	}
	
	//sees if a button with tag is on
	public boolean areenable(String tag) {
		for(int i = 0; i < buttonList.length; i++){
			if(buttonList[i].getButtonState() && buttonList[i].getTag().toLowerCase().equals(tag.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean areVisable(String tag) {
		for(int i = 0; i < buttonList.length; i++){
			if(buttonList[i].getButton().isVisible() && buttonList[i].getTag().toLowerCase().equals(tag.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	//sees if button with title is on
	public boolean isenable(String title) {
		for(int i = 0; i < buttonList.length; i++){
			if(buttonList[i].getButtonState() && buttonList[i].getTitle().toLowerCase().equals(title.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean[] getLevelState() {
		return levelState;
	}
	
	public void resetLevelStates() {
		for(int i = 0; i < levelState.length; i++) {
			levelState[i] = false;
		}
	}
	
	public boolean getPauseGame() {
		return pauseGame;
	}
	
	public boolean getPausePlayer() {
		return pausePlayer;
	}
	
	public JLabel getQuestionPromt() {
		return queationPromt;
	}
	
	public void turnONDialogue(String text) {
		dialogue.setText(text);
		dialogue.setVisible(true);
	}
	
	public void turnOffDialogue() {
		dialogue.setText("");
		dialogue.setVisible(false);
	}
	
	public JLabel getDialogue() {
		return dialogue;
	}
	
	public void setQuestionPromt(JLabel queationPromt) {
		this.queationPromt = queationPromt;
	}
	
	public void setTextLabel(String text) {
		this.queationPromt.setText(text);
	}
	
	public boolean getControlScreenState() {
		return controlScreenState;
	}
	
	public Texture getControlScreen() {
		return controlScreen;
	}
	
	public boolean getHTPState() {
		return HTPState;
	}
}

