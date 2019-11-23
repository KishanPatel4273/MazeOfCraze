package Display;

import javax.swing.JButton;

public class Button {
	
	protected JButton button;
	private boolean buttonState;
	private String title, tag;
	
	public Button(JButton button, int x, int y, int width, int height, String title, boolean visablity, String tag){	
		this.button = button;
		this.title = title;
		this.tag = tag;
		button.setBounds(x, y, width, height);
		button.setText(title);
		button.setVisible(visablity);
		buttonState = false;
	}
	
	public Button(JButton button, int x, int y, int width, int height, String title, boolean visablity){	
		this.button = button;
		this.title = title;
		button.setBounds(x, y, width, height);
		button.setText(title);
		button.setVisible(visablity);
		buttonState = false;
	//
	}
	
	//state of button visibility is if it is being displayed
	public void visablityState(boolean b){
		button.setVisible(b);
	}
	
	//Tag is used to group the buttons
	public String getTag() {
		return tag;
	}
	
	public boolean getButtonState() {
		return buttonState;
	}
	
	//if button is clicked or not
	public void setButtonState(boolean buttonState) {
		this.buttonState = buttonState;
	}
	
	public JButton getButton() {
		return button;
	}
	
	public String getTitle() {
		return title;
	}
	
	//if button is clicked it tures its on
	public void update(Object src){
		if(src == button){
			buttonState = !buttonState;			
		}
	}
}
