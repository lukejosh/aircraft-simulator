package asgn2Simulators;

public class GUIModel {
	
	private int time;
	private String text = "";
	private int numPassengers = 0;
	
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void appendText(String text) {
		this.text = this.text.concat(text);
	}	
	public int getNumPassengers() {
		return numPassengers;
	}
	public void setNumPassengers(int numPassengers) {
		this.numPassengers = numPassengers;
	}
	public void clearValues(){
		this.text = "";
		this.numPassengers = 0;
	}
	
}
