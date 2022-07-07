package Steganography;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainPage extends JFrame {

  private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public MainPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("STEGANOGRAPHY PROG");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 21));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(10, 11, 514, 40);
		contentPane.add(lblTitle);
		
		JButton btnEncrypt = new JButton("ENCRYPT");
		btnEncrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnEncryptActionPerformed(e);
			}
		});
		btnEncrypt.setBounds(215, 62, 110, 50);
		contentPane.add(btnEncrypt);
		
		JButton btnDecrypt = new JButton("DECRYPT");
		btnDecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnDecryptActionPerformed(e);
			}
		});
		btnDecrypt.setBounds(215, 123, 110, 50);
		contentPane.add(btnDecrypt);
		
		JButton btnExit = new JButton("EXIT");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnExit.setBounds(215, 184, 110, 50);
		contentPane.add(btnExit);
	}
	//Go to encrypt page.
	private void btnEncryptActionPerformed(ActionEvent e) {
		EncryptPage ep = new EncryptPage();
		ep.setVisible(true);
		ep.setLocationRelativeTo(null);
		dispose();
	}
	//Go to decrypt page.
	private void btnDecryptActionPerformed(ActionEvent e) {
		DecryptPage dp = new DecryptPage();
		dp.setVisible(true);
		dp.setLocationRelativeTo(null);
		dispose();
	}
	
	
}
