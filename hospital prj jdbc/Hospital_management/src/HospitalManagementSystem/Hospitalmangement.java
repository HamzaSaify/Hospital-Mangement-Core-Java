package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Hospitalmangement {
private static final String url="jdbc:mysql://localhost:3306/hospital";
private static final String username="root";
private static final String password="root123";


public static void main(String[] args) {
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
	}
	catch(ClassNotFoundException e){
		e.printStackTrace();
	}
	Scanner scanner=new Scanner(System.in);
	try {
		Connection connection=DriverManager.getConnection(url,username,password);
	Patient patient=new Patient(connection,scanner);
	Doctor doctor=new Doctor(connection);
	while(true) {
		System.out.println("Hospital Mangement System");
		System.out.println("1.add patients");
		System.out.println("2.view patients");
		System.out.println("3.view doctors");
		System.out.println("4.book appointments");
		System.out.println("5.exit");
		System.out.println("enter ur choice");
		int choice=scanner.nextInt();
		
		switch(choice) {
		case 1:
			patient.addPatient();
		break;
		case 2:
			patient.viewPatients();
		break;
		case 3:
			doctor.viewDoctors();
		case 4:
			bookAppointment(patient, doctor, connection, scanner);
			break;
			
		case 5:
			return;
			default:System.out.println("enter valid option");

		}		
	}

	}
	catch(SQLException e) {
		e.printStackTrace();
	}



}

public static void bookAppointment(Patient patient,Doctor doctor,Connection connection,Scanner scanner) {
	
	System.out.println("enter patient id");
	int patientId=scanner.nextInt();
	System.out.println("enter doctor id: ");
	int doctorId=scanner.nextInt();
	System.out.println("enter appointment date yy--mm--dd");
	String appointmentdate=scanner.nextLine();
	if(patient.getPatientbyId(patientId)&& doctor.getDoctorbyId(doctorId)) {
		if(checkdoctoravailability(doctorId,appointmentdate, connection)) {
			String appointmentQuery= "INSERT INTO  appointments(patient_id,doctor_id, appointment_date) VALUES(?, ?, ?)";
			try {
				PreparedStatement preparedStatement=connection.prepareStatement(appointmentQuery);
				preparedStatement.setInt(1,patientId);
				preparedStatement.setInt(2,doctorId);
				preparedStatement.setString(3,appointmentdate);
			int rowsaffected=preparedStatement.executeUpdate();
			if(rowsaffected>0) {
				System.out.println("appointment booked");
			}
			else {
				System.out.println("appointment not booked");
			}
			
			
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
			
			
			
		}else {
			System.out.println("doctor doesnt avaialable at that date");
		}
	}else {
		System.out.println("either doctor or patient doesn't exist");
	}
	
	
	
}

private static boolean checkdoctoravailability(int doctorId, String appointmentdate,Connection connection) {
	String query= "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
	try {
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1,doctorId);
		preparedStatement.setString(2,appointmentdate);
		
		ResultSet resultSet=preparedStatement.executeQuery();
		if(resultSet.next()) {
			int count=resultSet.getInt(1);
			if(count==0) {
				return true;
			}else {
				return false;
			}
		}
		
	}catch(SQLException e) {
		e.printStackTrace();
	}
	return false;




}


}
