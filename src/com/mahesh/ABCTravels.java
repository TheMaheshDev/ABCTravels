package com.mahesh;

import java.sql.SQLException;
import java.util.Scanner;

public class ABCTravels {
	private static UserService userService = new UserService();
	private static BusService busService = new BusService();
	private static boolean isLoggedIn = false;
	
	
	public static void main(String[] args) throws SQLException {
		Scanner scanner = new Scanner(System.in);
		int choice ;
		
		do {
			System.out.println("\n --- ABC TRAVELS APPLICATION---");
			System.out.println("1. Sign Up");
			System.out.println("2. Sign In");
			System.out.println("3. View Avaialable Buses");
			System.out.println("4. Book Bus Ticket");
			System.out.println("5. Add a New Bus"); 
			System.out.println("6. Exit");
			System.out.println("Enter Your Choice: ");
			choice = scanner.nextInt();
			scanner.nextLine();
			
			switch (choice){
				case 1 :
					signUp(scanner);
					System.out.println("sign-up successfully completed");
					break;
				case 2 :
					signIn(scanner);
					System.out.println("Sign-In successfull");
					break;
				case 3 :
					if(isLoggedIn) {
						System.out.println("im logged in");
						viewAvaialableBuses();
					}else {
						System.out.println("Please Sign-In First");
					}					
					break;
				case 4 :
					if(isLoggedIn) {
						bookBusTicket(scanner);
					}else {
						System.out.println("Please Sign-In First");
					}
					break;
				case 5 :
					addNewBus(scanner);
					
				case 6 :
					System.out.println(" Thank You for Using the ABC TRAVELS");
				default :
					System.out.println("Invalid choice. Pleasetry again.");
			}			
			
		}while(choice != 6);
		
		scanner.close();
	}
	
	
	private static void signUp(Scanner scanner) {
		System.out.println("Enter your UserName:");
		String username = scanner.nextLine();
		System.out.println("Enter your Password:");
		String password = scanner.nextLine();
		System.out.println("Enter your Email:");
		String email = scanner.nextLine();
		
		boolean success = userService.signUp(username,password,email);
		if(success) {
			System.out.println("Sign-Up Successfull");
		}else {
			System.out.println("Sign-Up failed.Try a different username");
		}
	}
	
	
	private static void signIn(Scanner scanner) {
		System.out.println("Enter username:");
		String username = scanner.nextLine();
		System.out.println("Enter password:");
		String password =  scanner.nextLine();
		
		isLoggedIn = userService.signIn(username, password);
		if(isLoggedIn) {
			System.out.println("Sign-In is Successfull.");
			
		}else {
			System.out.println("Sign-In failed.Please check youj credentials");
		}
	}
	
	
	private static void viewAvaialableBuses() {
		System.out.println("\nAvailable Buses:");
		busService.getAvaialableBuses().forEach(System.out::println);
	}
	
	
	private static void bookBusTicket(Scanner scanner) {
		System.out.println("Enter Bus Id to book:");
		int busId = scanner.nextInt();
		System.out.println("Enter Number of Seats to book");
		int seatsToBook = scanner.nextInt();
		
		boolean success = busService.bookBusTicket(busId, seatsToBook);
		if(success) {
			System.out.println("Booking Successfull.");
			
		}else {
			System.out.println("Booking failed.Neither Invalid Bus ID or insufficient seats.");
		}
	}
	
	private static void addNewBus(Scanner scanner) throws SQLException {
		System.out.println("Enter Bus Name:");
		String busName = scanner.nextLine();
		System.out.println("Enter Number of Avaialable seats:");
		int seatsAvaialable = scanner.nextInt();
		
		boolean success = busService.addNewBus(busName, seatsAvaialable);
		if(success) {
			System.out.println("Bus Added Successfully");
			
		}else {
			System.out.println("Failed to Add Bus. Please try again.");
		}
	}
	
}
