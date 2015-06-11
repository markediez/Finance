package com.markediez.finance.panels;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import com.markediez.finance.Expense;
import com.markediez.finance.SQLAccess;

import java.awt.GridLayout;
import java.awt.Color;

public class ExpensePanel extends JPanel   {
	private static final long serialVersionUID = 1L;
	private JLabel labelTitle, labelAmount, labelType, labelDescription;
	private JComboBox<String> comboBoxPaymentType;
	private JTextField textFieldTitle, textFieldAmount;
	private JTextArea textAreaDescription;
	private boolean isAdded;
	public static Border outerBorder, innerBorder;
	
	public ExpensePanel() {
		this.isAdded = false;
	}
	
	public void showDialog() throws Exception {
		int decision = JOptionPane.showConfirmDialog(null, getPanel(), "Add Expense", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		
		if (decision == JOptionPane.OK_OPTION) {
			Expense newExpense = new Expense(textFieldTitle.getText(), textAreaDescription.getText(), Float.parseFloat(textFieldAmount.getText()), comboBoxPaymentType.getSelectedItem().toString());
			JOptionPane.showMessageDialog(null, "Expense Added!\n\n" + newExpense.toString());
			
			isAdded = true;
			SQLAccess.add(newExpense);
		}
	}
	
	public boolean isAdded() { return this.isAdded; }
	
	private JPanel getPanel() {
		// Borders
		outerBorder = BorderFactory.createLineBorder(Color.BLACK);
		innerBorder = BorderFactory.createEmptyBorder(10,10,10,10);
		
		// Label
		labelTitle = new JLabel("Title:");
		labelAmount = new JLabel("Amount:");
		labelType = new JLabel("Payment Type:");
		labelDescription = new JLabel("Description");

		// Combo Box
		comboBoxPaymentType = new JComboBox<String>(Expense.paymentList);
		
		// Text Field
		textFieldTitle = new JTextField(20);
		textFieldAmount = new JTextField(20);
		
		textFieldTitle.setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));
		textFieldAmount.setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));
		
		// Text Area
		textAreaDescription = new JTextArea(5,20);
		textAreaDescription.setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));
		
		// Panels
		GridLayout gl_textField = new GridLayout(3,2);
		gl_textField.setVgap(10);
		JPanel panelExpenseTextField = new JPanel(gl_textField);
		panelExpenseTextField.add(labelType);
		panelExpenseTextField.add(comboBoxPaymentType);
		panelExpenseTextField.add(labelTitle);
		panelExpenseTextField.add(textFieldTitle);
		panelExpenseTextField.add(labelAmount);
		panelExpenseTextField.add(textFieldAmount);
		
		JPanel panelExpenseTextArea = new JPanel(new GridLayout(1,2));
		panelExpenseTextArea.add(labelDescription);
		panelExpenseTextArea.add(textAreaDescription);
		
		
		GridLayout gl_form = new GridLayout(2,1);
		gl_form.setVgap(10);
		JPanel panelExpense = new JPanel(gl_form);
		panelExpense.add(panelExpenseTextField);
		panelExpense.add(panelExpenseTextArea);
		
		return panelExpense;
	}
}
