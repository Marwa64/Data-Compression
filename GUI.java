import java.util.*; 
import java.awt.EventQueue;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GUI {
	
	private String filePath = "", dirPath = "";
	
	private boolean compress_file_chosen = false, decompress_file_chosen = false, compress_dir_chosen = false, decompress_dir_chosen = false;
	
	private JFrame frame;
	private JTabbedPane tabbedPane;
	private JPanel landingPanel, compressPanel, decompressPanel;
	private JButton btnBack_Compress, btnBack_Decompress;
	private JLabel lblCompressionTech;
	private JLabel lblInputFile_1;
	private JButton btnBack_Compress_1;
	private JLabel lblDecompressionTechnique;
	private JLabel lblInputFile;
	private JButton btnDecompress_1;
	private JButton btnFile_Decompress;
	private JComboBox comboBox_compress, comboBox_decompress;
	private JButton btnFile_Compress;
	private JLabel lblInputFile_2;
	private JButton btnFile_Compress_1;
	private JLabel lblInputFile_3;
	private JButton btnFile_Compress_2;
	private JLabel lblSelectedFile_1;
	private JLabel lblSelectedDir_1;
	private JLabel lblSelectedFile_2;
	private JLabel lblSelectedDir_2;
	private JLabel lblsuccess_compress;
	private JLabel lblsuccess_decompress;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Data Compression");
		frame.setResizable(false);
		frame.setBounds(100, 100, 770, 458);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, -26, 770, 706);
		frame.getContentPane().add(tabbedPane);
		
		landingPanel= new JPanel();
		landingPanel.setBackground(new Color(230, 230, 220));
		landingPanel.setLayout(null);
		tabbedPane.addTab("Landing Page", null, landingPanel, null);
		
		decompressPanel= new JPanel();
		decompressPanel.setBackground(new Color(230, 230, 220));
		decompressPanel.setLayout(null);
		
		btnBack_Decompress = new JButton("Back");
		btnBack_Decompress.setForeground(Color.WHITE);
		btnBack_Decompress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblsuccess_decompress.setText("");
				decompress_file_chosen = false;
				decompress_dir_chosen = false;
				lblSelectedFile_2.setText("no file selected");
				lblSelectedDir_2.setText("no directory selected");
				tabbedPane.setSelectedComponent(landingPanel); // Go to landing tab
			}
		});
		btnBack_Decompress.setBackground(Color.DARK_GRAY);
		btnBack_Decompress.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 18));
		btnBack_Decompress.setBounds(10, 10, 92, 34);
		decompressPanel.add(btnBack_Decompress);

		tabbedPane.addTab("Decompression Page", null, decompressPanel, null);
		
		lblDecompressionTechnique = new JLabel("Decompression Technique:");
		lblDecompressionTechnique.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 21));
		lblDecompressionTechnique.setBounds(68, 70, 349, 41);
		decompressPanel.add(lblDecompressionTechnique);
		
		lblInputFile = new JLabel("Input File:");
		lblInputFile.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 21));
		lblInputFile.setBounds(68, 133, 164, 41);
		decompressPanel.add(lblInputFile);
		
		btnDecompress_1 = new JButton("Decompress");
		btnDecompress_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String technique = (String)comboBox_decompress.getSelectedItem();
				if (decompress_file_chosen && decompress_dir_chosen) {
					switch (technique) {
						case "LZ77": {
							try {
								if (LZ77.decompress(filePath, dirPath)) {
									lblsuccess_decompress.setText("The file has been successfully decompressed!");
								} else {
									lblsuccess_decompress.setText("");
								}
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							break;
						}
						case "Huffman": {
							System.out.println("Hello!");
							break;
						}
					}
				}
			}
		});
		btnDecompress_1.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 20));
		btnDecompress_1.setBackground(Color.WHITE);
		btnDecompress_1.setBounds(268, 308, 177, 53);
		decompressPanel.add(btnDecompress_1);
		
		btnFile_Decompress = new JButton("Select");
		btnFile_Decompress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Using this process to invoke the constructor,
				// JFileChooser points to user's default directory
				JFileChooser j = new JFileChooser();
				 
	            // only allow files of .txt extension
	            FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt files", "txt");
	            j.addChoosableFileFilter(restrict);
	            
				// Open the save dialog
				int r = j.showSaveDialog(null);
				
	            // if the user selects a file
	            if (r == JFileChooser.APPROVE_OPTION) {
	            	decompress_file_chosen = true;
	                // set the label to the path of the selected file
	            	filePath = j.getSelectedFile().getAbsolutePath();
	                lblSelectedFile_2.setText("<html>" + filePath + "</html>");
	            } else {
	            	lblSelectedFile_2.setText("no file selected");
	            	decompress_file_chosen = false;
	            }
			}
		});
		btnFile_Decompress.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 20));
		btnFile_Decompress.setBackground(SystemColor.control);
		btnFile_Decompress.setBounds(200, 140, 113, 34);
		decompressPanel.add(btnFile_Decompress);
		
		comboBox_decompress = new JComboBox();
		comboBox_decompress.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 18));
		comboBox_decompress.setModel(new DefaultComboBoxModel(new String[] {"LZ77", "Huffman"}));
		comboBox_decompress.setSelectedIndex(1);
		comboBox_decompress.setBounds(397, 77, 177, 34);
		decompressPanel.add(comboBox_decompress);
		
		lblInputFile_3 = new JLabel("Output Directory:");
		lblInputFile_3.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 21));
		lblInputFile_3.setBounds(68, 205, 226, 41);
		decompressPanel.add(lblInputFile_3);
		
		btnFile_Compress_2 = new JButton("Select");
		btnFile_Compress_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Using this process to invoke the constructor,
				// JFileChooser points to user's default directory
				JFileChooser j = new JFileChooser();
				 
				// set the selection mode to directories only
				j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	            
				// Open the save dialog
				int r = j.showSaveDialog(null);
				
	            // if the user selects a file
	            if (r == JFileChooser.APPROVE_OPTION) {
	            	decompress_dir_chosen = true;
	                // set the label to the path of the selected file
	            	dirPath = j.getSelectedFile().getAbsolutePath();
	                lblSelectedDir_2.setText("<html>" + dirPath + "</html>");
	            } else {
	            	lblSelectedDir_2.setText("no directory selected");
	            	decompress_dir_chosen = false;
	            }
			}
		});
		btnFile_Compress_2.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 20));
		btnFile_Compress_2.setBackground(SystemColor.menu);
		btnFile_Compress_2.setBounds(288, 212, 113, 34);
		decompressPanel.add(btnFile_Compress_2);
		
		lblSelectedFile_2 = new JLabel("no file selected");
		lblSelectedFile_2.setVerticalAlignment(SwingConstants.TOP);
		lblSelectedFile_2.setForeground(new Color(0, 51, 153));
		lblSelectedFile_2.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 14));
		lblSelectedFile_2.setBounds(323, 146, 419, 53);
		decompressPanel.add(lblSelectedFile_2);
		
		lblSelectedDir_2 = new JLabel("no directory selected");
		lblSelectedDir_2.setVerticalAlignment(SwingConstants.TOP);
		lblSelectedDir_2.setForeground(new Color(0, 51, 153));
		lblSelectedDir_2.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 14));
		lblSelectedDir_2.setBounds(411, 218, 331, 80);
		decompressPanel.add(lblSelectedDir_2);
		
		lblsuccess_decompress = new JLabel("");
		lblsuccess_decompress.setForeground(new Color(51, 204, 51));
		lblsuccess_decompress.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 15));
		lblsuccess_decompress.setBounds(186, 370, 388, 41);
		decompressPanel.add(lblsuccess_decompress);
		
		JButton btnCompress = new JButton("Compress");
		btnCompress.setBackground(Color.WHITE);
		btnCompress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.setSelectedComponent(compressPanel); // Go to compress tab
			}
		});
		btnCompress.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 24));
		btnCompress.setBounds(70, 208, 215, 78);
		landingPanel.add(btnCompress);
		
		JLabel lblNewLabel = new JLabel("Data Compression");
		lblNewLabel.setFont(new Font("Lucida Handwriting", Font.PLAIN, 36));
		lblNewLabel.setBounds(166, 49, 405, 109);
		landingPanel.add(lblNewLabel);
		
		JButton btnDecompress = new JButton("Decompress");
		btnDecompress.setBackground(Color.WHITE);
		btnDecompress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.setSelectedComponent(decompressPanel); // Go to decompress tab
			}
		});
		btnDecompress.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 24));
		btnDecompress.setBounds(448, 208, 209, 78);
		landingPanel.add(btnDecompress);
		
		compressPanel= new JPanel();
		compressPanel.setBackground(new Color(230, 230, 220));
		compressPanel.setLayout(null);
		tabbedPane.addTab("Compression Page", null, compressPanel, null);
		
		btnBack_Compress = new JButton("Back");
		btnBack_Compress.setForeground(Color.WHITE);
		btnBack_Compress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblsuccess_compress.setText("");
				compress_file_chosen = false;
				compress_dir_chosen = false;
				lblSelectedFile_1.setText("no file selected");
				lblSelectedDir_1.setText("no directory selected");
				tabbedPane.setSelectedComponent(landingPanel); // Go to landing tab
			}
		});
		btnBack_Compress.setBackground(Color.DARK_GRAY);
		btnBack_Compress.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 18));
		btnBack_Compress.setBounds(10, 10, 92, 34);
		compressPanel.add(btnBack_Compress);
		
		lblCompressionTech = new JLabel("Compression Technique:");
		lblCompressionTech.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 21));
		lblCompressionTech.setBounds(66, 74, 349, 41);
		compressPanel.add(lblCompressionTech);
		
		lblInputFile_1 = new JLabel("Input File:");
		lblInputFile_1.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 21));
		lblInputFile_1.setBounds(66, 137, 164, 41);
		compressPanel.add(lblInputFile_1);
		
		btnBack_Compress_1 = new JButton("Compress");
		btnBack_Compress_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String technique = (String)comboBox_compress.getSelectedItem();
				if (compress_file_chosen && compress_dir_chosen) {
					switch (technique) {
						case "LZ77": {
							try {
								if (LZ77.compress(filePath, dirPath)) {
									lblsuccess_compress.setText("The file has been successfully compressed!");
								} else {
									lblsuccess_compress.setText("");
								}
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							break;
						}
						case "Huffman": {
							System.out.println("Hello!");
							break;
						}
					}
				}
			}
		});
		btnBack_Compress_1.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 20));
		btnBack_Compress_1.setBackground(Color.WHITE);
		btnBack_Compress_1.setBounds(269, 300, 177, 53);
		compressPanel.add(btnBack_Compress_1);
		
		comboBox_compress = new JComboBox();
		comboBox_compress.setModel(new DefaultComboBoxModel(new String[] {"LZ77", "Huffman"}));
		comboBox_compress.setSelectedIndex(1);
		comboBox_compress.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 18));
		comboBox_compress.setBounds(362, 81, 177, 34);
		compressPanel.add(comboBox_compress);
		
		btnFile_Compress = new JButton("Select");
		btnFile_Compress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Using this process to invoke the constructor,
				// JFileChooser points to user's default directory
				JFileChooser j = new JFileChooser();
				 
	            // only allow files of .txt extension
	            FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt files", "txt");
	            j.addChoosableFileFilter(restrict);
	            
				// Open the save dialog
				int r = j.showSaveDialog(null);
				
	            // if the user selects a file
	            if (r == JFileChooser.APPROVE_OPTION) {
	            	compress_file_chosen = true;
	                // set the label to the path of the selected file
	            	filePath = j.getSelectedFile().getAbsolutePath();	
	                //File file = new File(path);
	                lblSelectedFile_1.setText("<html>" + filePath + "</html>");
	            } else {
	            	lblSelectedFile_1.setText("no file selected");
	            	compress_file_chosen = false;
	            }
			}
		});
		btnFile_Compress.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 20));
		btnFile_Compress.setBackground(SystemColor.control);
		btnFile_Compress.setBounds(199, 144, 113, 34);
		compressPanel.add(btnFile_Compress);
		
		lblInputFile_2 = new JLabel("Output Directory:");
		lblInputFile_2.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 21));
		lblInputFile_2.setBounds(66, 208, 226, 41);
		compressPanel.add(lblInputFile_2);
		
		btnFile_Compress_1 = new JButton("Select");
		btnFile_Compress_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Using this process to invoke the constructor,
				// JFileChooser points to user's default directory
				JFileChooser j = new JFileChooser();
				 
				// set the selection mode to directories only
				j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	            
				// Open the save dialog
				int r = j.showSaveDialog(null);
				
	            // if the user selects a file
	            if (r == JFileChooser.APPROVE_OPTION) {
	            	compress_dir_chosen = true;
	                // set the label to the path of the selected file
	            	dirPath = j.getSelectedFile().getAbsolutePath();
	                lblSelectedDir_1.setText("<html>" + dirPath + "</html>");
	            } else {
	            	lblSelectedDir_1.setText("no directory selected");
	            	compress_dir_chosen = false;
	            }
			}
		});
		btnFile_Compress_1.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 20));
		btnFile_Compress_1.setBackground(SystemColor.control);
		btnFile_Compress_1.setBounds(292, 215, 113, 34);
		compressPanel.add(btnFile_Compress_1);
		
		lblSelectedFile_1 = new JLabel("no file selected");
		lblSelectedFile_1.setVerticalAlignment(SwingConstants.TOP);
		lblSelectedFile_1.setForeground(new Color(0, 51, 153));
		lblSelectedFile_1.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 14));
		lblSelectedFile_1.setBounds(322, 150, 420, 53);
		compressPanel.add(lblSelectedFile_1);
		
		lblSelectedDir_1 = new JLabel("no directory selected");
		lblSelectedDir_1.setVerticalAlignment(SwingConstants.TOP);
		lblSelectedDir_1.setForeground(new Color(0, 51, 153));
		lblSelectedDir_1.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 14));
		lblSelectedDir_1.setBounds(415, 221, 327, 76);
		compressPanel.add(lblSelectedDir_1);
		
		lblsuccess_compress = new JLabel("");
		lblsuccess_compress.setForeground(new Color(51, 204, 51));
		lblsuccess_compress.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 15));
		lblsuccess_compress.setBounds(207, 363, 349, 41);
		compressPanel.add(lblsuccess_compress);
		
	}
}
