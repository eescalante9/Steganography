package Steganography;

import java.awt.EventQueue;

public class Main {
	public static void main(String[] args) {
		//MainPage mp = new MainPage();
		//mp.setVisible(true);
		//mp.setLocationRelativeTo(null);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainPage frame = new MainPage();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

}
