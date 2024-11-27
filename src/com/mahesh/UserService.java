package com.mahesh;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
	public  boolean signUp(String username, String password, String email) {
		String query = "insert into users(username, password, email) values(?, ?, ?)";
		try(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pst = conn.prepareStatement(query)) {
			
			pst.setString(1, username);
			pst.setString(2,password);
			pst.setString(3, email);
			
			int rowsInserted = pst.executeUpdate();
			return rowsInserted > 0;
			
		} catch(SQLException e) {
			System.out.println("Sign-Up Error" + e.getMessage());
		}
		return false;
	}
	public boolean signIn(String username, String password) {
		String userQuery ="select password,login_attempts, account_locked from users where username=? ";
		String updatedAttemptsQuery = "update users set login_attempts = ? ,account_locked = ? where username = ?";
		
		int maxAttempts = 3;
		
		try(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement checkPst = conn.prepareStatement(userQuery);
				PreparedStatement updatePst = conn.prepareStatement(updatedAttemptsQuery)){
			
			//check if username exits in database
			checkPst.setString(1,username );
			ResultSet rs = checkPst.executeQuery();
			if(rs.next()){
				String storedPassword = rs.getString("password");
				int loginAttempts = rs.getInt("login_attempts");
				boolean isLocked = rs.getBoolean("accounrt_locked");
				
				if(isLocked) {
					System.out.println("Account is Locked Due to too many failed login Attempts." );
					return false;
				}
				if(storedPassword.equals(password)) {
					updatePst.setInt(1,0);
					updatePst.setBoolean(2,false);
					updatePst.setString(3, username);
					updatePst.executeUpdate();
					
					return true;
				} else {
					loginAttempts++;
					boolean lockAccount = loginAttempts >= maxAttempts;
					
					updatePst.setInt(1, loginAttempts);
					updatePst.setBoolean(2, lockAccount);
					updatePst.setString(3, username);
					updatePst.executeUpdate();
					
					if(lockAccount) {
						System.out.println("Account is Locked Due to too many failed login Attempts." );
						
					}else {
						System.out.println("Incorrect Password ." + (maxAttempts-loginAttempts) + "Attempts left.");
						
					}
					
					return false;
										
				}
			}else {
				System.out.println("Username not found");
			}
		}catch(SQLException e) {
			System.out.println("sign-in Error" + e.getMessage());
		}		
		
		return false;
	}
}
