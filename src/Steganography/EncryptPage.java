package Steganography;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class EncryptPage extends JFrame {
	
	private BufferedImage originalImage = null;
	private BufferedImage encryptedImage = null;
	private JPanel contentPane;
	private JLabel lblOriginalImage;
	private JLabel lblModdedImage;
	private JTextArea textAreaMessage;

	/**
	 * Create the frame.
	 */
	public EncryptPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panelOriginalImage = new JPanel();
		panelOriginalImage.setBounds(15, 16, 340, 220);
		panelOriginalImage.setBorder(new TitledBorder(null, "Original Image", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panelModdedImage = new JPanel();
		panelModdedImage.setBounds(379, 16, 340, 220);
		panelModdedImage.setBorder(new TitledBorder(null, "Modded Image", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JButton btnOpen = new JButton("OPEN");
		btnOpen.setBounds(15, 380, 100, 30);
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOpenActionPerformed(e);
			}
		});
		
		JButton btnEncrypt = new JButton("ENCRYPT");
		btnEncrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnEncryptActionPerformed(e);
			}
		});
		btnEncrypt.setBounds(125, 380, 100, 30);
		
		JButton btnSave = new JButton("SAVE");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSaveActionPerformed(e);
			}
		});
		btnSave.setBounds(15, 421, 100, 30);
		
		JButton btnReset = new JButton("RESET");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnResetActionPerformed(e);
			}
		});
		btnReset.setBounds(125, 421, 100, 30);
		
		panelModdedImage.setLayout(null);
		lblModdedImage = new JLabel("");
		lblModdedImage.setBounds(10, 23, 320, 186);
		panelModdedImage.add(lblModdedImage);
		panelOriginalImage.setLayout(null);
		
		lblOriginalImage = new JLabel("");
		lblOriginalImage.setBounds(10, 21, 320, 188);
		panelOriginalImage.add(lblOriginalImage);
		contentPane.setLayout(null);
		contentPane.add(panelOriginalImage);
		contentPane.add(panelModdedImage);
		contentPane.add(btnOpen);
		contentPane.add(btnEncrypt);
		contentPane.add(btnSave);
		contentPane.add(btnReset);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Message", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(15, 247, 704, 100);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 21, 684, 68);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(scrollPane);
		
		textAreaMessage = new JTextArea();
		textAreaMessage.setLineWrap(true);
		scrollPane.setViewportView(textAreaMessage);
		
		JButton btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnBackActionPerformed(e);
			}
		});
		btnBack.setBounds(619, 421, 100, 30);
		contentPane.add(btnBack);
	}

	private void btnOpenActionPerformed(ActionEvent e){
		File file = openFileDialog();
		
		if(file == null) {
			return;
		}
		
		try {
			originalImage = ImageIO.read(file);
			lblOriginalImage.setIcon(new ImageIcon(originalImage));
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
	
	private void btnSaveActionPerformed(ActionEvent e) {
		if(encryptedImage == null) {
			JOptionPane.showMessageDialog(this, "No Encrypted Image", "No encrypted image to save.", JOptionPane.ERROR_MESSAGE);      
		}else {
			File file = saveFileDialog();
			if(file==null) {
				return;
			}
			String name = file.getName();
			String ext = name.substring(name.lastIndexOf(".")+1).toLowerCase();
			if(!ext.contains(".")) {
				ext = "png";
				file = new File(file.getAbsolutePath()+".png");
			}
			try {
				ImageIO.write(encryptedImage, ext, file);
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}
		
	}
	
	private File saveFileDialog() {
		File selectedFile = null;
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg", "png", "bmp"));
		fc.setAcceptAllFileFilterUsed(false);
		int result = fc.showSaveDialog(this);
		
        if (result == JFileChooser.APPROVE_OPTION) {
        	selectedFile = fc.getSelectedFile();
        }
        
        return selectedFile;
		
	}
	
	private void btnEncryptActionPerformed(ActionEvent e){
		if(textAreaMessage.equals("") || originalImage == null) {
			JOptionPane.showMessageDialog(this,  "Encryption Failed", "Message or image not found.", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		String message = textAreaMessage.getText();
		encryptedImage = originalImage.getSubimage(0, 0, originalImage.getWidth(), originalImage.getHeight());
		encrypt(encryptedImage, message);
		lblModdedImage.setIcon(new ImageIcon(encryptedImage));
		this.validate();	
	}
	
	private void encrypt(BufferedImage image, String message) {
		int messageLength = message.length();
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		
		if(messageLength * 8 +32 > imageWidth*imageHeight) {
			JOptionPane.showMessageDialog(this, "Image too small", "Message to large to encrypt in this image.", JOptionPane.ERROR_MESSAGE);
			return;
		}else {
			encryptSize(image, messageLength,0,0);
		    byte b[] = message.getBytes();
		    for(int i=0; i<b.length; i++)
		    embedByte(image, b[i], i*8+32, 0);
		}
	}
	
	private void encryptSize(BufferedImage img, int n, int start, int storageBit) {
	    int maxWidth = img.getWidth();
	    int maxHeight = img.getHeight(); 
	    int count=0;
	    
	    for(int i=0; i<maxWidth && count<32; i++) {
	    	for(int j=0; j<maxHeight && count<32; j++) {
	    		int rgb = img.getRGB(i, j);
	    		int bit = getBitValue(n, count);
	    		rgb = setBitValue(rgb, storageBit, bit);
	    		img.setRGB(i, j, rgb);
	    		count++;
	        }
	    }
	}
	
	private int getBitValue(int n, int location) {
		int bit = (int)(n& Math.round(Math.pow(2, location)));
	    return bit==0?0:1;
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
	 
	 private void embedByte(BufferedImage img, byte b, int start, int storageBit) {
		 int maxWidth = img.getWidth();
		 int maxHeight = img.getHeight(); 
		 int startX = start/maxHeight;
		 int startY = start - startX*maxHeight;
		 int count=0;
		 for(int i=startX; i<maxWidth && count<8; i++) {
			 for(int j=startY; j<maxHeight && count<8; j++) {
				 int rgb = img.getRGB(i, j);
				 int bit = getBitValue(b, count);
				 rgb = setBitValue(rgb, storageBit, bit);
				 img.setRGB(i, j, rgb);
				 count++;
		 	}
		 }
	 }
	 
	 private void btnResetActionPerformed(ActionEvent e){
		 textAreaMessage.setText("");
		 originalImage = null;
		 encryptedImage = null;
		 lblOriginalImage.setIcon(null);
		 lblModdedImage.setIcon(null);
	}
	 
	 private void btnBackActionPerformed(ActionEvent e) {
		 MainPage mp = new MainPage();
		 mp.setVisible(true);
		 mp.setLocationRelativeTo(null);
		 dispose();
	 }
	 
	 
}
