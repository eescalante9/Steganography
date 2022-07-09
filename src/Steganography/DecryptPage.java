package Steganography;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class DecryptPage extends JFrame {

	private JPanel contentPane;
	
	BufferedImage Image = null;
	private JLabel lblModdedImage;
	private JTextArea textAreaMessage;


	/**
	 * Create the frame.
	 */
	public DecryptPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 630, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelModdedImage = new JPanel();
		panelModdedImage.setBorder(new TitledBorder(null, "Modded Image", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelModdedImage.setBounds(10, 11, 370, 325);
		contentPane.add(panelModdedImage);
		panelModdedImage.setLayout(null);
		
		lblModdedImage = new JLabel("");
		lblModdedImage.setBounds(10, 21, 350, 293);
		panelModdedImage.add(lblModdedImage);
		
		JPanel panelDecyptedMessage = new JPanel();
		panelDecyptedMessage.setBorder(new TitledBorder(null, "Decrypted Message", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelDecyptedMessage.setBounds(10, 347, 594, 103);
		contentPane.add(panelDecyptedMessage);
		panelDecyptedMessage.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 21, 574, 71);
		panelDecyptedMessage.add(scrollPane);
		
		textAreaMessage = new JTextArea();
		textAreaMessage.setLineWrap(true);
		scrollPane.setViewportView(textAreaMessage);
		
		JButton btnOpen = new JButton("OPEN");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOpenActionPerformed(e);
			}
		});
		btnOpen.setBounds(445, 51, 100, 30);
		contentPane.add(btnOpen);
		
		JButton btnDecrypt = new JButton("DECRYPT");
		btnDecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnDecryptActionPerformed(e);
			}
		});
		btnDecrypt.setBounds(445, 92, 100, 30);
		contentPane.add(btnDecrypt);
		
		JButton btnReset = new JButton("RESET");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnResetActionPerformed(e);
			}
		});
		btnReset.setBounds(445, 133, 100, 30);
		contentPane.add(btnReset);
		
		JButton btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnBackActionPerformed(e);
			}
		});
		btnBack.setBounds(445, 306, 100, 30);
		contentPane.add(btnBack);
	}
	
	private void btnOpenActionPerformed(ActionEvent e) {
		File file = openFileDialog();
		if(file== null) {
			return;
		}
		try {
			Image = ImageIO.read(file);
			lblModdedImage.setIcon(new ImageIcon(Image));
			this.validate();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	private File openFileDialog() {
		File selectedFile = null;
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg", "png", "bmp"));
		fc.setAcceptAllFileFilterUsed(false);
		int result = fc.showOpenDialog(this);
		
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fc.getSelectedFile();
        }
        
        return selectedFile;
	}
	
	
	private void btnDecryptActionPerformed(ActionEvent e) {
		if(Image == null){
			JOptionPane.showMessageDialog(this,  "You must first choose an image to decrypt.", "Choose Picture", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int messageLength = decryptSize(Image, 0, 0);
	    // System.out.println(messageLength);
	    byte b[] = new byte[messageLength];
	    for(int i=0; i<messageLength; i++) {
	    	b[i] = extractByte(Image, i*8+32, 0);
	    }
	    textAreaMessage.setText(new String(b));
	}	
	
	private int decryptSize(BufferedImage img, int start, int storageBit) {
		int maxWidth = img.getWidth();
		int maxHeight = img.getHeight(); 
	
		int count=0;
		int length = 0;
		for(int i=0; i<maxWidth && count<32; i++) {
			for(int j=0; j<maxHeight && count<32; j++) {
				int rgb = img.getRGB(i, j);
				int bit = getBitValue(rgb, storageBit);
				length = setBitValue(length, count, bit);
				count++;
			}
		}
		return length;
	}
	
	private int getBitValue(int n, int location) {
		int bitValue = (int)(n& Math.round(Math.pow(2, location)));
	    return bitValue==0?0:1;
	}
	 
	 private int setBitValue(int n, int location, int bit) {
	    int toggle = (int) Math.pow(2, location);
	    int bitValue = getBitValue(n, location);
	    if(bitValue == bit) {
	    	return n;
	    }
	    if(bitValue == 0 && bit == 1) {
	    	n |= toggle;
	    }else if(bitValue == 1 && bit == 0) {
	    	n ^= toggle;
	    }
	    return n;
	 }
	
	 private byte extractByte(BufferedImage img, int start, int storageBit) {
		 int maxWidth = img.getWidth();
		 int maxHeight = img.getHeight(); 
		 int startX = start/maxHeight;
		 int startY = start - startX*maxHeight;
		 int count=0;
		 byte b = 0;
		 for(int i=startX; i<maxWidth && count<8; i++) {
			 for(int j=startY; j<maxHeight && count<8; j++) {
				 int rgb = img.getRGB(i, j);
				 int bit = getBitValue(rgb, storageBit);
				 b = (byte)setBitValue(b, count, bit);
				 count++;
			 }
		 }
		 return b;
	 }
	 
	 private void btnResetActionPerformed(ActionEvent e) {
		 Image = null;
		 textAreaMessage.setText("");
		 lblModdedImage.setIcon(null);
	 }
	 
	 private void btnBackActionPerformed(ActionEvent e) {
		 MainPage mp = new MainPage();
		 mp.setVisible(true);
		 mp.setLocationRelativeTo(null);
		 dispose();
	 }
	
}
