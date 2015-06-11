package com.markediez.finance.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.BevelBorder;

import com.markediez.finance.Expense;
import com.markediez.finance.SQLAccess;
import com.markediez.finance.panels.ExpensePanel;

public class MainWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	final String sDay = "TODAY";
	final String sWeek = "THIS WEEK";
	final String sMonth = "THIS MONTH";
	final String sYear = "THIS YEAR";
	JLabel year, month, week, day, report;
	JPanel reportPanel, buttonPanel, westPanel;
	JButton update, add;
	SQLAccess access;


	public MainWindow(String title) {
		super(title);
		this.setSize(1000,500);
		this.init();
		this.setLocationRelativeTo(null);				// Opens program to center screen
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == update) {
			repaint();
			revalidate();
		} else {
			try {
				new ExpensePanel().showDialog();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
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

	private void init()  {

		this.add(getReportPanel(), BorderLayout.WEST);
		this.add(getReviewPanel(), BorderLayout.CENTER);

		SQLAccess.close();
		this.setResizable(false);
	}

	private JPanel getReportPanel() {
		access = new SQLAccess();

		// Labels
		year = new JLabel("Amount spent this YEAR:");
		month = new JLabel("Amount spent this MONTH:");
		week = new JLabel("Amount spent this WEEK:");
		day = new JLabel("Amount spent TODAY:");

		year.setHorizontalAlignment(JLabel.RIGHT);
		month.setHorizontalAlignment(JLabel.RIGHT);
		week.setHorizontalAlignment(JLabel.RIGHT);
		day.setHorizontalAlignment(JLabel.RIGHT);

		// Buttons
		update = new JButton("Reload");
		add = new JButton("Add");
		update.addActionListener(this);
		add.addActionListener(this);

		// Panels
		GridLayout reportForm = new GridLayout(4,2);
		reportForm.setHgap(10);
		reportForm.setVgap(5);

		reportPanel = new JPanel(reportForm);
		reportPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED),BorderFactory.createEmptyBorder(5,5,5,5)),"Report"));
		reportPanel.add(year);
		reportPanel.add(new JLabel(access.getTotalExpense(SQLAccess.reportType.YEAR)));
		reportPanel.add(month);
		reportPanel.add(new JLabel(access.getTotalExpense(SQLAccess.reportType.MONTH)));
		reportPanel.add(week);
		reportPanel.add(new JLabel(access.getTotalExpense(SQLAccess.reportType.WEEK)));
		reportPanel.add(day);
		reportPanel.add(new JLabel(access.getTotalExpense(SQLAccess.reportType.DAY)));

		buttonPanel = new JPanel();
		buttonPanel.add(update);
		buttonPanel.add(add);

		// Organize and add
		GridBagLayout westLayout = new GridBagLayout();
		westPanel = new JPanel(westLayout);
		westPanel.add(reportPanel);
		westPanel.setBorder(BorderFactory.createEmptyBorder(125, 0, 0, 0));

		JPanel form = new JPanel(new GridLayout(2,1));
		form.setPreferredSize(new Dimension(400, getPreferredSize().height));
		form.add(westPanel);
		form.add(buttonPanel);

		return form;
	}

	private JPanel getReviewPanel() {
		JPanel panel = new JPanel();
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setPreferredSize(new Dimension(500,400));

		JScrollPane panelDay = expenseToJPanel(access.getReport(SQLAccess.reportType.DAY));
		Dimension size = panelDay.getPreferredSize();
		size.width = 500;
		size.height = 400;
		panelDay.setPreferredSize(size);
		JScrollPane panelWeek = expenseToJPanel(access.getReport(SQLAccess.reportType.WEEK));
		JScrollPane panelMonth = expenseToJPanel(access.getReport(SQLAccess.reportType.MONTH));
		JScrollPane panelYear = expenseToJPanel(access.getReport(SQLAccess.reportType.YEAR));

		tabbedPane.addTab(sDay, panelDay);
		tabbedPane.addTab(sWeek, panelWeek);
		tabbedPane.addTab(sMonth, panelMonth);
		tabbedPane.addTab(sYear, panelYear);

		tabbedPane.setSize(new Dimension(450,450));

		panel.add(tabbedPane, BorderLayout.CENTER);
		return panel;
	}
}
