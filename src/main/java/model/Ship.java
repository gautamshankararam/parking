package model;

public class Ship {
	private int id;
	private String name;
	private int dockId;
	private String dockName;
	private int parkingDuration;
	private int parkingCost;
	
	
	
	public Ship(int id, String name, int dockId, String dockName, int parkingDuration, int parkingCost) {
		this.id = id;
		this.name = name;
		this.dockId = dockId;
		this.dockName = dockName;
		this.parkingDuration = parkingDuration;
		this.parkingCost = parkingCost;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDockId() {
		return dockId;
	}
	public void setDockId(int dockId) {
		this.dockId = dockId;
	}
	public String getDockName() {
		return dockName;
	}
	public void setDockName(String dockName) {
		this.dockName = dockName;
	}
	public int getParkingDuration() {
		return parkingDuration;
	}
	public void setParkingDuration(int parkingDuration) {
		this.parkingDuration = parkingDuration;
	}
	public int getParkingCost() {
		return parkingCost;
	}
	public void setParkingCost(int parkingCost) {
		this.parkingCost = parkingCost;
	}
	
	
	
	
	

}
