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
				System.out.print("gender->" + result.getString("gender") + " : ");
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
			display();
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
			display();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// to retrieve all employees who have joined within particular date range
	public static void displayRecordsWithinGivenDateRange() {
		try {
			Statement statement = connection.createStatement();
			String query = "select * from employee_payroll where StartDate between '2022-07-01' and DATE(now())";
			ResultSet result = statement.executeQuery(query);
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

	// to find sum, max, min, avg, count of male and female employees
	public static void performAggregateFunctions() {
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement
					.executeQuery("SELECT SUM(Salary), gender FROM employee_payroll GROUP BY gender;");
			System.out.println("\n" + result + " records affected.");
			System.out.println("\n:: Sum ::");
			while (result.next()) {
				System.out.print("SUM(Salary)->" + result.getString("SUM(Salary)") + " : ");
				System.out.print("Gender->" + result.getString("Gender") + "\n");
			}

			result = statement.executeQuery("SELECT MIN(Salary), gender FROM employee_payroll GROUP BY gender;");
			System.out.println("\n" + result + " records affected.");
			System.out.println("\n:: Minimum ::");
			while (result.next()) {
				System.out.print("MIN(Salary)->" + result.getString("MIN(Salary)") + " : ");
				System.out.print("Gender->" + result.getString("Gender") + "\n");
			}

			result = statement.executeQuery("SELECT MAX(Salary), gender FROM employee_payroll GROUP BY gender;");
			System.out.println("\n" + result + " records affected.");
			System.out.println("\n:: Maximum ::");
			while (result.next()) {
				System.out.print("MAX(Salary)->" + result.getString("MAX(Salary)") + " : ");
				System.out.print("Gender->" + result.getString("Gender") + "\n");
			}

			result = statement.executeQuery("SELECT AVG(Salary), gender FROM employee_payroll GROUP BY gender;");
			System.out.println("\n" + result + " records affected.");
			System.out.println("\n:: Average ::");
			while (result.next()) {
				System.out.print("AVG(Salary)->" + result.getString("AVG(Salary)") + " : ");
				System.out.print("Gender->" + result.getString("Gender") + "\n");
			}

			result = statement.executeQuery("SELECT COUNT(gender), gender FROM employee_payroll GROUP BY gender;");
			System.out.println("\n" + result + " records affected.");
			System.out.println("\n:: Employee Count ::");
			while (result.next()) {
				System.out.print("COUNT(gender)->" + result.getString("COUNT(gender)") + " : ");
				System.out.print("Gender->" + result.getString("Gender") + "\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// to retrieve payroll data by employee name
	public static void retrievePayrollDataByName() {
		try {
			String query = "Select * from Employee_payroll where Name=?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, "Sunil");
			ResultSet result = preparedStatement.executeQuery();
			System.out.println("\n" + result + " records retrieved.");
			while (result.next()) {
				System.out.print("ID->" + result.getInt("ID") + " : ");
				System.out.print("Name->" + result.getString("Name") + " : ");
				System.out.print("Salary->" + result.getString("Salary") + " : ");
				System.out.print("StartDate->" + result.getString("StartDate") + " : ");
				System.out.print("Gender->" + result.getString("Gender") + "\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// to add a new employee to the table
	public static void insertEmployeeIntoTable() {
		try {
			String query = "insert into employee_payroll values(?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, 8);
			preparedStatement.setString(2, "Saanvi");
			preparedStatement.setString(3, "F");
			preparedStatement.setFloat(4, (float) 45000.00);
			preparedStatement.setDate(5, java.sql.Date.valueOf("2021-02-09"));
			Integer result = preparedStatement.executeUpdate();
			System.out.println(result + " records affected");
			display();
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
		updateSalaryWithPreparedStatement();
		displayRecordsWithinGivenDateRange();
		performAggregateFunctions();
		retrievePayrollDataByName();
		insertEmployeeIntoTable();
		closeConnection();
	}
}
