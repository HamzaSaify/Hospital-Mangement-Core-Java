package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
	private Connection connection;
	private Scanner scanner;
	public Patient(Connection connection,Scanner scanner) {
		this.connection=connection;
		this.scanner=scanner;
	}
	public void addPatient() {
		System.out.println("enter patient name : ");
		String name=scanner.next();
		System.out.println("enter patient age : ");
		int age=scanner.nextInt();
		System.out.println("ente patient gender : ");
		String gender=scanner.next();
		
		try {
			String query="INSERT INTO Patients(name, age, gender) Values(?, ?, ?)";
			PreparedStatement preparedStatement=connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, age);
			preparedStatement.setString(3, gender);
		int affectedrows=preparedStatement.executeUpdate();
		
		if(affectedrows>0) {
			System.out.println("patient data added succesfullly");
		}else {
			System.out.println("not added data");
		}
		
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void viewPatients() {
		try {
			String query="select * from patients";
			PreparedStatement preparedStatement=connection.prepareStatement(query); 
			ResultSet resultSet=preparedStatement.executeQuery();
			System.out.println("Patients");
			//System.out.println("+------------+--------------+------------+-----+");
			System.out.println("|patient id  | name         | age        |gender");  
			//System.out.println("+------------+--------------+------------+-----+");		
		
			while(resultSet.next()) {
				int id=resultSet.getInt("id");
				String name=resultSet.getString("name");
				int age=resultSet.getInt("age");
				String gender=resultSet.getString("gender");
				System.out.printf("|%-12s|%-14%|%-12s|%-5s|"+id,name,age,gender);
				//System.out.println("+------------+--------------+------------+-----+");
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean getPatientbyId(int id){
		String query="select * from patients WHERE id = ?";
		try{
			PreparedStatement preparedStatement=connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
