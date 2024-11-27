package com.mahesh;

public class Bus {
	private int busId;
	private String busName;
	private String source;
	private String destination;
	private int seats;
	private double fare;
	
	
	public int getBusId() {
		return busId;
	}


	public String getBusName() {
		return busName;
	}


	public String getSource() {
		return source;
	}


	public String getDestination() {
		return destination;
	}


	public int getSeats() {
		return seats;
	}


	public double getFare() {
		return fare;
	}


	public Bus(int busId, String busName, String source, String destination, int seats, double fare) {
		super();
		this.busId = busId;
		this.busName = busName;
		this.source = source;
		this.destination = destination;
		this.seats = seats;
		this.fare = fare;
	}


	@Override
	public String toString() {
		return "Bus [busId=" + busId + ", busName=" + busName + ", source=" + source + ", destination=" + destination
				+ ", seats=" + seats + ", fare=" + fare + "]";
	}
	
	
}
