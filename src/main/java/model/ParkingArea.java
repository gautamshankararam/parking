package model;

import java.util.ArrayList;


public class ParkingArea {
	
	private int id;
	private String name;
	private int capacity;
	private int basicCharge;
	private int basicDuration;
	private int extraCharge;
	private String type;
	private int rent;
	private ArrayList<Car> parkedCars;
	
	
	

	
	
	
	public ParkingArea(int id, String name, int capacity, int basicCharge, int basicDuration, int extraCharge,
			String type, int rent, ArrayList<Car> parkedCars) {
		super();
		this.id = id;
		this.name = name;
		this.capacity = capacity;
		this.basicCharge = basicCharge;
		this.basicDuration = basicDuration;
		this.extraCharge = extraCharge;
		this.type = type;
		this.rent = rent;
		this.parkedCars = parkedCars;
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
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getBasicCharge() {
		return basicCharge;
	}
	public void setBasicCharge(int basicCharge) {
		this.basicCharge = basicCharge;
	}
	public int getBasicDuration() {
		return basicDuration;
	}
	public void setBasicDuration(int basicDuration) {
		this.basicDuration = basicDuration;
	}
	public int getExtraCharge() {
		return extraCharge;
	}
	public void setExtraCharge(int extraCharge) {
		this.extraCharge = extraCharge;
	}
	public ArrayList<Car> getParkedCars() {
		return parkedCars;
	}
	public void setParkedCars(ArrayList<Car> parkedCars) {
		this.parkedCars = parkedCars;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getRent() {
		return rent;
	}

	public void setRent(int rent) {
		this.rent = rent;
	}

	
	
	
	
	
	

}
