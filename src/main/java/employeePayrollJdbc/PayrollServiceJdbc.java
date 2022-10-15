package employeePayrollJdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

public class PayrollServiceJdbc {

	static Connection connection = null;
	static String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service";
	static String userName = "sunil";
	static String password = "root";

	// loading driver
	public static void loadingDriver() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("cannot find the driver in the classpath", e);
		}
	}

	// listing drivers
	private static void listDrivers() {
		Enumeration<Driver> driverList = DriverManager.getDrivers();
		while (driverList.hasMoreElements()) {
			Driver driverClass = (Driver) driverList.nextElement();
			System.out.println(" " + driverClass.getClass().getName());
		}
	}

	// connecting database
	public static void connectingDatabase() {
		try {
			System.out.println("connecting to database" + jdbcURL);
			connection = DriverManager.getConnection(jdbcURL, userName, password);
			System.out.println("connection is successfull" + connection);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// retrieving all the employee records from the table
	public static void display() {
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery("select * from employee_payroll");
			System.out.println(result + " records affected");
			while (result.next()) {
				System.out.print("ID->" + result.getInt("ID") + " : ");
				System.out.print("Name->" + result.getString("Name") + " : ");
				System.out.print("Salary->" + result.getFloat("Salary") + " : ");
				System.out.print("StartDate->" + result.getDate("StartDate"));
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// updating an employee salary using statement
	public static void updateSalaryWithStatement() {
		try {
			Statement statement = connection.createStatement();
			String query = "update employee_payroll set Salary=40000.00 where Name='Shyam'";
			Integer recordUpdated = statement.executeUpdate(query);
			System.out.println("records updated: " + recordUpdated);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// updating salary of an employee using prepared statement
	public static void updateSalaryWithPreparedStatement() {
		try {
			String whereCondition = "where name=?";
			String query = "UPDATE employee_payroll set Salary=?" + whereCondition;
			PreparedStatement preparedstatement = connection.prepareStatement(query);
			preparedstatement.setFloat(1, (float) 40000.00);
			preparedstatement.setString(2, "Sita");
			Integer recordUpdated = preparedstatement.executeUpdate();
			System.out.println("records updated: " + recordUpdated);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// closing the connection
	public static void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		loadingDriver();
		listDrivers();
		connectingDatabase();
		display();
		updateSalaryWithStatement();
		display();
		updateSalaryWithPreparedStatement();
		display();
		closeConnection();
	}
}
