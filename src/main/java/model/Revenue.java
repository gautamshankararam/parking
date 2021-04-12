package model;

public class Revenue {
	private int id;
	private String slot;
	private int amount;
	private int vehicles;
	private int duration;
	private int rent;
	public Revenue(int id, String slot, int amount,int vehicles,int duration) {
		super();
		this.id = id;
		this.slot = slot;
		this.amount = amount;
		this.vehicles= vehicles;
		this.duration = duration;
	}
	
	public Revenue(int id, String slot, int amount, int vehicles, int duration, int rent) {
		super();
		this.id = id;
		this.slot = slot;
		this.amount = amount;
		this.vehicles = vehicles;
		this.duration = duration;
		this.rent = rent;
	}

	public int getVehicles() {
		return vehicles;
	}
	public void setVehicles(int vehicles) {
		this.vehicles = vehicles;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSlot() {
		return slot;
	}
	public void setSlot(String slot) {
		this.slot = slot;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getRent() {
		return rent;
	}
	public void setRent(int rent) {
		this.rent = rent;
	}
	
	

	
}
