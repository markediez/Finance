package com.markediez.finance.windows;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame implements ActionListener {
	JLabel year, month, week, day;
	JButton review, add;
	
	public MainWindow(String title) {
		super(title);
		this.init();
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == review) {
			JOptionPane.showMessageDialog(this, "Set Up Review Reports!", "Review", JOptionPane.WARNING_MESSAGE );
		} else {
			JOptionPane.showMessageDialog(this, "Set Up Add Reports!", "Review", JOptionPane.WARNING_MESSAGE );
		}
	}
	
	private void init() {
		JPanel reportPanel, buttonPanel;
		// Labels
		year = new JLabel("Amount spent this YEAR:");
		month = new JLabel("Amount spent this MONTH:");
		week = new JLabel("Amount spent this WEEK:");
		day = new JLabel("Amount spent TODAY:");
		
		// Buttons
		review = new JButton("Review");
		add = new JButton("Add");
		review.addActionListener(this);
		add.addActionListener(this);
		
		// Panels
		reportPanel = new JPanel(new GridLayout(4,1));
		reportPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED),"Report"));
		reportPanel.add(year);
		reportPanel.add(month);
		reportPanel.add(week);
		reportPanel.add(day);
		
		buttonPanel = new JPanel();
		buttonPanel.add(review);
		buttonPanel.add(add);
		
		this.add(reportPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.pack();
		this.setResizable(false);
	}
}
