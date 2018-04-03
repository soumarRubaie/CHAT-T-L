package Client;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPasswordField;

public class Inscription extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inscription frame = new Inscription();
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
	public Inscription() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 551, 589);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JButton btnNewButton = new JButton("Inscription");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnNewButton, -88, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnNewButton, 202, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnNewButton, -37, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnNewButton, 316, SpringLayout.WEST, contentPane);
		contentPane.add(btnNewButton);
		
		textField = new JTextField();
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, textField_1, 34, SpringLayout.SOUTH, textField);
		sl_contentPane.putConstraint(SpringLayout.EAST, textField, 0, SpringLayout.EAST, textField_1);
		sl_contentPane.putConstraint(SpringLayout.EAST, textField_1, -56, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, textField, 0, SpringLayout.WEST, textField_1);
		textField_1.setColumns(10);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.SOUTH, textField_1, -23, SpringLayout.NORTH, textField_2);
		textField_2.setColumns(10);
		contentPane.add(textField_2);
		
		textField_3 = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.WEST, textField_2, 0, SpringLayout.WEST, textField_3);
		sl_contentPane.putConstraint(SpringLayout.EAST, textField_2, 0, SpringLayout.EAST, textField_3);
		textField_3.setColumns(10);
		contentPane.add(textField_3);
		
		JLabel lblNom = new JLabel("Nom");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblNom, 67, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, textField, 1, SpringLayout.NORTH, lblNom);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblNom, 28, SpringLayout.WEST, contentPane);
		lblNom.setFont(new Font("Tahoma", Font.PLAIN, 17));
		contentPane.add(lblNom);
		
		JLabel lblDescription = new JLabel("Description");
		sl_contentPane.putConstraint(SpringLayout.WEST, textField_1, 142, SpringLayout.EAST, lblDescription);
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblDescription, 41, SpringLayout.SOUTH, lblNom);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblDescription, 0, SpringLayout.WEST, lblNom);
		lblDescription.setFont(new Font("Tahoma", Font.PLAIN, 17));
		contentPane.add(lblDescription);
		
		JLabel lblPays = new JLabel("Pays");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblPays, 47, SpringLayout.SOUTH, lblDescription);
		sl_contentPane.putConstraint(SpringLayout.NORTH, textField_2, 1, SpringLayout.NORTH, lblPays);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblPays, 0, SpringLayout.WEST, lblNom);
		lblPays.setFont(new Font("Tahoma", Font.PLAIN, 17));
		contentPane.add(lblPays);
		
		JLabel lblUsername = new JLabel("Username");
		sl_contentPane.putConstraint(SpringLayout.NORTH, textField_3, 1, SpringLayout.NORTH, lblUsername);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblUsername, 0, SpringLayout.WEST, lblNom);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 17));
		contentPane.add(lblUsername);
		
		JLabel lblMotDePasse = new JLabel("Mot de passe");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblMotDePasse, 315, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblUsername, -38, SpringLayout.NORTH, lblMotDePasse);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblMotDePasse, 0, SpringLayout.WEST, lblNom);
		lblMotDePasse.setFont(new Font("Tahoma", Font.PLAIN, 17));
		contentPane.add(lblMotDePasse);
		
		JLabel lblConfirmationMotDe = new JLabel("Confirmation mot de passe");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblConfirmationMotDe, 32, SpringLayout.SOUTH, lblMotDePasse);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblConfirmationMotDe, 0, SpringLayout.WEST, lblNom);
		lblConfirmationMotDe.setFont(new Font("Tahoma", Font.PLAIN, 17));
		contentPane.add(lblConfirmationMotDe);
		
		passwordField = new JPasswordField();
		sl_contentPane.putConstraint(SpringLayout.WEST, textField_3, 0, SpringLayout.WEST, passwordField);
		sl_contentPane.putConstraint(SpringLayout.EAST, textField_3, 213, SpringLayout.WEST, passwordField);
		sl_contentPane.putConstraint(SpringLayout.NORTH, passwordField, 1, SpringLayout.NORTH, lblMotDePasse);
		contentPane.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, passwordField_1, 31, SpringLayout.SOUTH, passwordField);
		sl_contentPane.putConstraint(SpringLayout.EAST, passwordField, 0, SpringLayout.EAST, passwordField_1);
		sl_contentPane.putConstraint(SpringLayout.WEST, passwordField_1, 27, SpringLayout.EAST, lblConfirmationMotDe);
		sl_contentPane.putConstraint(SpringLayout.EAST, passwordField_1, -56, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, passwordField, 0, SpringLayout.WEST, passwordField_1);
		contentPane.add(passwordField_1);
	}

}
