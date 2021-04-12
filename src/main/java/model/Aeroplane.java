package model;

public class Aeroplane {
	private int id;
	private String name;
	private int apronId;
	private String apronName;
	private int parkingDuration;
	private int parkingCost;
	
	
	
	public Aeroplane(int id, String name, int apronId, String apronName, int parkingDuration, int parkingCost) {
		this.id = id;
		this.name = name;
		this.apronId = apronId;
		this.apronName = apronName;
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
	public int getApronId() {
		return apronId;
	}
	public void setApronId(int apronId) {
		this.apronId = apronId;
	}
	public String getApronName() {
		return apronName;
	}
	public void setApronName(String apronName) {
		this.apronName = apronName;
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
