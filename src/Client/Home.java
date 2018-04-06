package Client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SpringLayout;
import java.awt.List;
import java.awt.Button;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;

public class Home extends JFrame {

	private JPanel contentPane;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Home() {
		
		
		
		String label[] = { "Zero", "One", "Two", "Three", "Four", "Five", "Six",
			      "Seven", "Eight", "Nine", "Ten", "Eleven" };
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 831, 705);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{107, 0, 0};
		gbl_contentPane.rowHeights = new int[]{261, 46, 0, 0, 43, 40, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		JList list2 = new JList(label);
		scrollPane.setViewportView(list2);
		
		Button button_1 = new Button("Consulter profile");
		button_1.setFont(new Font("Dialog", Font.PLAIN, 16));
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.insets = new Insets(0, 0, 5, 5);
		gbc_button_1.gridx = 0;
		gbc_button_1.gridy = 1;
		contentPane.add(button_1, gbc_button_1);
		
		JList list = new JList(label);
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.anchor = GridBagConstraints.SOUTH;
		gbc_list.fill = GridBagConstraints.HORIZONTAL;
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.gridx = 0;
		gbc_list.gridy = 2;
		contentPane.add(list, gbc_list);
		
		Button button = new Button("Rejoindre salle");
		button.setFont(new Font("Dialog", Font.PLAIN, 16));
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.fill = GridBagConstraints.BOTH;
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 0;
		gbc_button.gridy = 3;
		contentPane.add(button, gbc_button);
		
		Button button_2 = new Button("Modifier mon profil");
		button_2.setForeground(Color.RED);
		button_2.setFont(new Font("Dialog", Font.PLAIN, 16));
		GridBagConstraints gbc_button_2 = new GridBagConstraints();
		gbc_button_2.fill = GridBagConstraints.BOTH;
		gbc_button_2.insets = new Insets(0, 0, 5, 5);
		gbc_button_2.gridx = 0;
		gbc_button_2.gridy = 4;
		contentPane.add(button_2, gbc_button_2);
		
		Button button_3 = new Button("Deconnexion");
		button_3.setForeground(Color.RED);
		button_3.setFont(new Font("Dialog", Font.PLAIN, 16));
		GridBagConstraints gbc_button_3 = new GridBagConstraints();
		gbc_button_3.fill = GridBagConstraints.BOTH;
		gbc_button_3.insets = new Insets(0, 0, 0, 5);
		gbc_button_3.gridx = 0;
		gbc_button_3.gridy = 5;
		contentPane.add(button_3, gbc_button_3);
	}
}
