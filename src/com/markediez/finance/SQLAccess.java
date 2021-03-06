// http://www.vogella.com/tutorials/MySQLJava/article.html
// http://docs.oracle.com/javase/tutorial/displayCode.html?code=http://docs.oracle.com/javase/tutorial/uiswing/examples/layout/TabDemoProject/src/layout/TabDemo.java
package com.markediez.finance;

import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SQLAccess {
	public static enum reportType { DAY, WEEK, MONTH, YEAR };
	private static Connection connection = null;
	private static Statement statement = null;
	private static PreparedStatement preparedStatement = null;
	private static ResultSet resultSet = null;

	private static ArrayList<Expense> getExpense(ResultSet resultSet) {
		ArrayList<Expense> expenseArray = new ArrayList<Expense>();
		try {
			while(resultSet.next()) {
				Expense readExpense = new Expense(	resultSet.getInt("id"),
						resultSet.getString("title"),
						resultSet.getString("description"), 
						resultSet.getFloat("amount"), 
						resultSet.getString("paymentType"),
						resultSet.getString("createdAt"),
						resultSet.getString("modifiedAt"));

				expenseArray.add(readExpense);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			close();
		}
		return expenseArray;
	}
	
	public static String getTotalExpense(reportType type) {
		ArrayList<Expense> expenses = getReport(type);
		float total = 0;
		
		for (int index = 0; index < expenses.size(); index++) {
			total += expenses.get(index).getAmount();
		}
		
		Expense totalExpense = new Expense();
		totalExpense.setAmount(total);
		return totalExpense.getAmountString();
	}

	public static ArrayList<Expense> getReport(reportType type) {
		open();
		switch(type) {
		case DAY:
			resultSet = getDayReport();
			break;
		case WEEK:
			resultSet = getWeekReport();
			break;
		case MONTH:
			resultSet = getMonthReport();
			break;
		case YEAR:
			resultSet = getYearReport();
			break;
		default:
			System.out.println("default");
		}
		return getExpense(resultSet);
	}
	
	private static ResultSet getDayReport() {
		System.out.println("Getting Day Report");
		Calendar endDate = Calendar.getInstance();
		Calendar startDate = Calendar.getInstance();
		
		startDate.set(Calendar.HOUR_OF_DAY, 0);
		startDate.set(Calendar.MINUTE, 0);
		startDate.set(Calendar.SECOND, 0);
		startDate.set(Calendar.MILLISECOND, 0);
		
		endDate.set(Calendar.HOUR_OF_DAY, 23);
		endDate.set(Calendar.MINUTE, 59);
		endDate.set(Calendar.SECOND, 59);
		endDate.set(Calendar.MILLISECOND, 999);
		
		Date end = endDate.getTime();
		Date start = startDate.getTime();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return query("SELECT * from expense WHERE createdAt BETWEEN '"+ sdf.format(start) +"' AND '" + sdf.format(end) + "'");
	}
	
	private static ResultSet getWeekReport() {
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		
		startDate.set(Calendar.HOUR_OF_DAY, 0);
		startDate.set(Calendar.MINUTE, 0);
		startDate.set(Calendar.SECOND, 0);
		startDate.set(Calendar.MILLISECOND, 0);
		
		endDate.set(Calendar.HOUR_OF_DAY, 23);
		endDate.set(Calendar.MINUTE, 59);
		endDate.set(Calendar.SECOND, 59);
		endDate.set(Calendar.MILLISECOND, 59);
		
		startDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		Date beg = new Date(startDate.getTimeInMillis());
		Date end = new Date(endDate.getTimeInMillis());
	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return query("SELECT * from expense WHERE createdAt BETWEEN '"+ sdf.format(beg) +"' AND '"+ sdf.format(end) +"'");
	}
	
	private static ResultSet getMonthReport() {
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		
		startDate.set(Calendar.HOUR_OF_DAY, 0);
		startDate.set(Calendar.MINUTE, 0);
		startDate.set(Calendar.SECOND, 0);
		startDate.set(Calendar.MILLISECOND, 0);
		
		endDate.set(Calendar.HOUR_OF_DAY, 23);
		endDate.set(Calendar.MINUTE, 59);
		endDate.set(Calendar.SECOND, 59);
		endDate.set(Calendar.MILLISECOND, 59);
		
		startDate.set(Calendar.DAY_OF_MONTH, 1);
		Date beg = new Date(startDate.getTimeInMillis());
		Date end = new Date(endDate.getTimeInMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return query("SELECT * from expense WHERE createdAt BETWEEN '"+ sdf.format(beg) +"' AND '"+ sdf.format(end) +"'");
	}
	
	private static ResultSet getYearReport() {
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		
		startDate.set(Calendar.HOUR_OF_DAY, 0);
		startDate.set(Calendar.MINUTE, 0);
		startDate.set(Calendar.SECOND, 0);
		startDate.set(Calendar.MILLISECOND, 0);
		
		endDate.set(Calendar.HOUR_OF_DAY, 23);
		endDate.set(Calendar.MINUTE, 59);
		endDate.set(Calendar.SECOND, 59);
		endDate.set(Calendar.MILLISECOND, 59);
		
		startDate.set(Calendar.MONTH, Calendar.JANUARY);
		startDate.set(Calendar.DAY_OF_MONTH, 1);
		Date beg = new Date(startDate.getTimeInMillis());
		Date end = new Date(endDate.getTimeInMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return query("SELECT * from expense WHERE createdAt BETWEEN '"+ sdf.format(beg) +"' AND '"+ sdf.format(end) +"'");
	}

	private static ResultSet query(String sql) {
		open();
		try {
			System.out.println("Attempting to query " + sql);
			resultSet = statement.executeQuery(sql);
			System.out.println("Success");
		} catch (Exception e) {
			System.out.println(sql);
			System.out.println(e.getMessage());
		}

		return resultSet;
	}

	private static void open() {
		// Loads MySQL Driver for our DB
		try {
			Class.forName("org.sqlite.JDBC");
			// Connects to the DB
			connection = DriverManager.getConnection("jdbc:sqlite:finance.db");
			System.out.println("Opened database successfully");
			
			// Allow MySQL queries to the DB
			statement = connection.createStatement();
			System.out.println("Connected to create statements successfully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

	public static void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connection != null) {
				connection.close();
			}
			System.out.println("Closed connections successfully");
		} catch (Exception e) {
			System.out.println(e.getMessage() + " closing fail.");
		}
	}

	public static void add(Expense newExpense) throws Exception {
		connection = DriverManager.getConnection("jdbc:sqlite:finance.db");

		preparedStatement = connection.prepareStatement("insert into expense (paymentType, title, description, amount, createdAt, modifiedAt)"
				+ "VALUES (?,?,?,?,?,?)");
		preparedStatement.setString(1, newExpense.getPaymentType());
		preparedStatement.setString(2, newExpense.getTitle());
		preparedStatement.setString(3, newExpense.getDescription());
		preparedStatement.setFloat(4, newExpense.getAmount());
		preparedStatement.setString(5, newExpense.getCreatedDate());
		preparedStatement.setString(6, newExpense.getModifiedDate());
		preparedStatement.executeUpdate();
		System.out.println("Added an expense successfully");
	}
}
