package com.markediez.finance;

import java.awt.Color;
import java.awt.GridLayout;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Expense {
	public static String[] paymentList = {"CASH", "CHECK", "DEBIT", "CREDIT"};
	private int id;
	private DecimalFormat df;
	private String title;
	private String description;
	private float amount;
	private String paymentType;
	private Date createdAt, modifiedAt;
	

	// Constructors
	public Expense () {
		df = new DecimalFormat();
		df.setMinimumFractionDigits(2);
		df.setMaximumFractionDigits(2);
		this.title ="No Title";
		this.description = "No Description";
		this.amount = 0.00f;
		this.paymentType = "CASH";
		this.createdAt = new Date(Calendar.getInstance().getTimeInMillis());
		this.modifiedAt = new Date(Calendar.getInstance().getTimeInMillis());
	}

	public Expense(String title, String description, float amount, String type) {
		this();
		this.title = title;
		this.description = description;
		this.amount = amount;
		this.paymentType = type;
	}
	
	public Expense(int id, String title, String description, float amount, String type, Date createdDate, Date modifiedDate) {
		this();
		this.title = title;
		this.description = description;
		this.amount = amount;
		this.paymentType = type;
		this.createdAt = createdDate;
		this.modifiedAt = modifiedDate;
	}

	// Getters and Setters
	public int getId() { return this.id; }
	public String getTitle() { return this.title; }
	public String getDescription() { return this.description; }
	public float getAmount() { return this.amount; }
	public String getAmountString(){ return "$" + df.format(getAmount());}
	public String getPaymentType() { return this.paymentType; }
	public Date getCreatedDate() { return this.createdAt; }
	public Date getModifiedDate() { return this.modifiedAt; }
	
	public void getId(int id) { this.id = id; }
	public void setTitle(String title) { this.title = title; }
	public void setDescription(String description) { this.description = description; }
	public void setAmount(float amount) { this.amount = amount; }
	public void setType (String paymentType) { this.paymentType = paymentType; }
	public void setCreateDate(Date createDate) { this.createdAt = createDate; }
	public void setModifyDate(Date modifyDate) { this.modifiedAt = modifyDate; }
	
	// Methods
	public float add(Expense otherExpense) { return this.amount + otherExpense.getAmount(); }
	
	public JPanel toJPanel() {
		GridLayout layout = new GridLayout(5,2);
		
		JLabel lpaymentType = new JLabel("Payment Type:");
		JLabel ltitle = new JLabel("Title:");
		JLabel lamount = new JLabel("Amount:");
		JLabel ldescription = new JLabel("Description:");
		JLabel ldate = new JLabel("Date:");
		
		JLabel paymentType = new JLabel(this.paymentType);
		JLabel title = new JLabel(this.title);
		JLabel amount = new JLabel(this.getAmountString());
		JLabel description = new JLabel(this.description);
		JLabel date = new JLabel(this.createdAt.toString());
		
		JPanel myPanel = new JPanel(layout);
		myPanel.add(ldate);
		myPanel.add(date);
		myPanel.add(lpaymentType);
		myPanel.add(paymentType);
		myPanel.add(ltitle);
		myPanel.add(title);
		myPanel.add(lamount);
		myPanel.add(amount);
		myPanel.add(ldescription);
		myPanel.add(description);
		
		myPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK),
																BorderFactory.createEmptyBorder(10,10,10,10)));
		
		return myPanel;
	}
	
	@Override
	public String toString() {
		return "Payment Type: " + getPaymentType()
				+ "\nTitle: " + getTitle()
				+ "\nAmount: $" + getAmountString()
				+ "\nDescription: " + getDescription()
				+ "\nDate Added: " + getCreatedDate()
				+ "\nDate Modified: " + getModifiedDate()
				+ "\n\n";
	}


}
