package model;

public class Car {
	private int id;
	private String name;
	private int areaId;
	private String areaName;
	private int parkingDuration;
	private int parkingCost;
	
	
	
	public Car(int id, String name, int areaId, String areaName, int parkingDuration, int parkingCost) {
		
		this.id = id;
		this.name = name;
		this.areaId = areaId;
		this.areaName = areaName;
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
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
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
