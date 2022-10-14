package employeePayrollJdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
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
		closeConnection();
	}
}
