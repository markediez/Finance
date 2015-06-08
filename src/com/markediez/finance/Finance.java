package com.markediez.finance;
import javax.swing.JFrame;

import com.markediez.finance.windows.MainWindow;
public class Finance {
	public static void main(String[] args) {
		MainWindow mainWindow = new MainWindow("Finance");
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
