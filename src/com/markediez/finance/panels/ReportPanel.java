package com.markediez.finance.panels;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import com.markediez.finance.SQLAccess;

import java.awt.*;

public class ReportPanel {
	private JLabel day,week,month,year;
	private GridLayout layout;
	
	public ReportPanel() {
		this.layout = new GridLayout(4,2);
		this.year = new JLabel(SQLAccess.getTotalExpense(SQLAccess.reportType.YEAR));
		this.month = new JLabel(SQLAccess.getTotalExpense(SQLAccess.reportType.MONTH));
		this.week = new JLabel(SQLAccess.getTotalExpense(SQLAccess.reportType.WEEK));
		this.day = new JLabel(SQLAccess.getTotalExpense(SQLAccess.reportType.DAY));
	}
	
	public JPanel getPanel() {
		layout.setHgap(10);
		JPanel reportPanel = new JPanel(layout);
		
		JLabel lDay = new JLabel("Spent TODAY:");
		JLabel lWeek = new JLabel("Spent this WEEK:");
		JLabel lMonth = new JLabel("Spent this MONTH:");
		JLabel lYear = new JLabel("Spent this YEAR:");
		
		lDay.setHorizontalAlignment(JLabel.RIGHT);
		lWeek.setHorizontalAlignment(JLabel.RIGHT);
		lMonth.setHorizontalAlignment(JLabel.RIGHT);
		lYear.setHorizontalAlignment(JLabel.RIGHT);
		
		reportPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED),BorderFactory.createEmptyBorder(5,5,5,5)),"Report"));
		reportPanel.add(lDay);
		reportPanel.add(day);
		reportPanel.add(lWeek);
		reportPanel.add(week);
		reportPanel.add(lMonth);
		reportPanel.add(month);
		reportPanel.add(lYear);
		reportPanel.add(year);
		
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(125, 0, 0, 0));
		panel.add(reportPanel);
		
		return panel;
	}
	
	public void updatePanel() {
		day.setText(SQLAccess.getTotalExpense(SQLAccess.reportType.DAY));
		week.setText(SQLAccess.getTotalExpense(SQLAccess.reportType.WEEK));
		month.setText(SQLAccess.getTotalExpense(SQLAccess.reportType.MONTH));
		year.setText(SQLAccess.getTotalExpense(SQLAccess.reportType.YEAR));
	}
}
