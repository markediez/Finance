package com.markediez.finance.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import com.markediez.finance.Expense;
import com.markediez.finance.SQLAccess;

public class ReviewPanel {
	JScrollPane panelDay, panelWeek, panelMonth, panelYear;
	JPanel panel;
	JTabbedPane tabbedPane;
	ArrayList<Expense> day, week, month, year;
	public  ReviewPanel() {
		
	}
	
	public JPanel getPanel() {
		panel = new JPanel();
		tabbedPane = new JTabbedPane();
		tabbedPane.setPreferredSize(new Dimension(500,400));

		panelDay = expenseToJPanel(SQLAccess.getReport(SQLAccess.reportType.DAY));
		panelWeek = expenseToJPanel(SQLAccess.getReport(SQLAccess.reportType.WEEK));
		panelMonth = expenseToJPanel(SQLAccess.getReport(SQLAccess.reportType.MONTH));
		panelYear = expenseToJPanel(SQLAccess.getReport(SQLAccess.reportType.YEAR));

		Dimension size = panelDay.getPreferredSize();
		size.width = 500;
		size.height = 400;
		panelDay.setPreferredSize(size);
		
		tabbedPane.addTab("TODAY", panelDay);
		tabbedPane.addTab("THIS WEEK", panelWeek);
		tabbedPane.addTab("THIS MONTH", panelMonth);
		tabbedPane.addTab("THIS YEAR", panelYear);
		
		tabbedPane.setSize(new Dimension(450,450));
		
		panel.add(tabbedPane, BorderLayout.CENTER);
		SQLAccess.close();
		return panel;

	}
	
	public void updatePanel() {
		panelDay = expenseToJPanel(SQLAccess.getReport(SQLAccess.reportType.DAY));
		panelWeek = expenseToJPanel(SQLAccess.getReport(SQLAccess.reportType.WEEK));
		panelMonth = expenseToJPanel(SQLAccess.getReport(SQLAccess.reportType.MONTH));
		panelYear = expenseToJPanel(SQLAccess.getReport(SQLAccess.reportType.YEAR));
		
		tabbedPane.removeAll();
		
		tabbedPane.addTab("TODAY", panelDay);
		tabbedPane.addTab("THIS WEEK", panelWeek);
		tabbedPane.addTab("THIS MONTH", panelMonth);
		tabbedPane.addTab("THIS YEAR", panelYear);
		
		
	}
	
	private JScrollPane expenseToJPanel(ArrayList<Expense> arrayExpense) {
		GridLayout expenseFormat = new GridLayout(arrayExpense.size() + 1, 1);
		expenseFormat.setHgap(10);
		expenseFormat.setVgap(10);
		JPanel panel = new JPanel(expenseFormat);
		for (int index = 0; index < arrayExpense.size(); index++) {
			JPanel newExpensePanel = new JPanel(new GridLayout(5,2));

			newExpensePanel = arrayExpense.get(index).toJPanel();
			if (index % 2 == 0) {
				newExpensePanel.setBackground(new Color(238,233,233));
			} else {
				newExpensePanel.setBackground(new Color(240,248,255));
			}

			panel.add(newExpensePanel);
		}
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(panel);
		scrollPane.setSize(new Dimension(500,500));

		return scrollPane;
	}
}
