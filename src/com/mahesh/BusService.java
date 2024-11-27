package com.mahesh;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BusService {
	public List<String> getAvaialableBuses(){
		List<String> buses = new ArrayList<>();
		String query = "select bus_id, bus_name, seats_avaialable from buses";
		
		try(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pst = conn.prepareStatement(query);
				ResultSet rs = pst.executeQuery()){
				while(rs.next()) {
					int busId = rs.getInt("bus_id");
					String busName = rs.getString("bus_name");
					int seatsAvailable = rs.getInt("seats_avaialable");
					
					buses.add("Bus ID: " + busId + " , Name: " + busName + ", Seats Available: " + seatsAvailable);
				}
		}catch(SQLException e) {
			System.out.println("Error fetchinng buses" + e.getMessage());
		}
		return buses;
	}
	
	public  boolean addNewBus(String busName, int seatsAvailable) throws SQLException {
		String query = " insert into buses (bus_name, seats_avaialable) values(?,?)";
		
		try(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement Pst = conn.prepareStatement(query)){
			
			Pst.setString(1, busName);;
			Pst.setInt(2, seatsAvailable);
			
			int rowsInserted = Pst.executeUpdate();
			if(rowsInserted > 0) {
				System.out.println("Bus Added Successfully.");
				return true;
			}
		}catch(SQLException e) {
			System.out.println("Error adding bus" + e.getErrorCode());
		}
		return false;
	}
	
	
	public boolean bookBusTicket(int busId, int seats) {
		String checkSeatsQuery = "select seats_avaialable from buses where bus_id = ?";
		String updateSeatsQuery = " update buses set seats_avaialable = seats_avaialable - ? where bus_id = ?";
		
		try(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement checkPst = conn.prepareStatement(checkSeatsQuery);
				PreparedStatement updatePst = conn.prepareStatement(updateSeatsQuery)){
				
				checkPst.setInt(1, busId);
				ResultSet rs = checkPst.executeQuery();
				
				if(rs.next()) {
					int seatsAvaialable = rs.getInt("seats_avaialable");
					if(seatsAvaialable >= seats) {
						updatePst.setInt(1, seats);
						updatePst.setInt(2, busId);
						updatePst.executeUpdate();
						System.out.println("Ticketbooked successfully.");
						
						return true;
					}else {
						System.out.println("Not enough seats avaialable");
					} 
				}else {
					System.out.println("Bus not found");
				}

			}catch ( SQLException e) {
				System.out.println("Bookinf Error:" + e.getMessage());
			}
			return false;
	}
}
