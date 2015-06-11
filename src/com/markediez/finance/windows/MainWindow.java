package com.markediez.finance.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.markediez.finance.SQLAccess;
import com.markediez.finance.panels.ExpensePanel;
import com.markediez.finance.panels.ReportPanel;
import com.markediez.finance.panels.ReviewPanel;

public class MainWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	ReportPanel rPanel;
	ReviewPanel revPanel;
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
			rPanel.updatePanel();
			revPanel.updatePanel();

		} else {
			try {
				ExpensePanel expensePanel = new ExpensePanel();
				expensePanel.showDialog();
				if(expensePanel.isAdded()) {
					rPanel.updatePanel();
					revPanel.updatePanel();
				}
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
		
		revalidate();
		repaint();
		
	}


	private void init()  {
		rPanel = new ReportPanel();
		revPanel = new ReviewPanel();
		
		this.add(getLeftSide(), BorderLayout.WEST);
		this.add(revPanel.getPanel(), BorderLayout.CENTER);

		this.setResizable(false);
	}

	private JPanel getLeftSide() {
		// Buttons
		update = new JButton("Reload");
		add = new JButton("Add");
		update.addActionListener(this);
		add.addActionListener(this);

		buttonPanel = new JPanel();
		buttonPanel.add(update);
		buttonPanel.add(add);

		JPanel form = new JPanel(new GridLayout(2,1));
		form.setPreferredSize(new Dimension(400, getPreferredSize().height));
		form.add(rPanel.getPanel());
		form.add(buttonPanel);

		return form;
	}
}
