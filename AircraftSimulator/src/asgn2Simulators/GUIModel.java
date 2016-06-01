package asgn2Simulators;

public class GUIModel {
	
	private int time = 0;
	private int first = 0;
	private int business = 0;
	private int premium = 0;
	private int economy = 0;
	private int total = 0;
	private int empty = 0;
	private int queued = 0;
	private int totalQueued = 0;
	private int refused = 0;
	private int capacity = 0;	
	private String text = "";
	
	public void clearValues(){
		this.text = "";
		this.first = 0;
		this.business = 0;
		this.premium = 0;
		this.economy = 0;
		this.total = 0;
		this.empty = 0;
	}
	
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

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public int getBusiness() {
		return business;
	}

	public void setBusiness(int business) {
		this.business = business;
	}

	public int getPremium() {
		return premium;
	}

	public void setPremium(int premium) {
		this.premium = premium;
	}

	public int getEconomy() {
		return economy;
	}

	public void setEconomy(int economy) {
		this.economy = economy;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getEmpty() {
		return empty;
	}

	public void setEmpty(int empty) {
		this.empty = empty;
	}

	public int getQueued() {
		return queued;
	}

	public void setQueued(int queued) {
		this.queued = queued;
		this.totalQueued += queued;
	}

	public int getRefused() {
		return refused;
	}

	public void setRefused(int refused) {
		this.refused = refused;
	}

	public int getCapacity() {
		return total+empty;
	}

	public int getTotalQueued() {
		return totalQueued;
	}	
	
	
}
