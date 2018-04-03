package Client;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Font;

public class LoginPage {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Login IFT585  projet chat");
		frame.setSize(426, 386);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		placeComponents(panel);
		
		JLabel lblVousNavezPas = new JLabel("Vous n'avez pas de compte?");
		lblVousNavezPas.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblVousNavezPas.setBounds(105, 239, 222, 25);
		panel.add(lblVousNavezPas);

		frame.setVisible(true);
	}

	private static void placeComponents(JPanel panel) {

		panel.setLayout(null);

		JLabel userLabel = new JLabel("Utilisateur");
		userLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		userLabel.setBounds(23, 43, 80, 25);
		panel.add(userLabel);

		JTextField userText = new JTextField(20);
		userText.setBounds(128, 45, 160, 25);
		panel.add(userText);

		JLabel passwordLabel = new JLabel("Mot de passe");
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		passwordLabel.setBounds(23, 96, 104, 25);
		panel.add(passwordLabel);

		JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(128, 98, 160, 25);
		panel.add(passwordText);

		JButton loginButton = new JButton("Connexion");
		loginButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		loginButton.setBounds(142, 149, 134, 46);
		panel.add(loginButton);
		
		JButton registerButton = new JButton("Inscription");
		registerButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		registerButton.setBounds(142, 277, 134, 51);
		panel.add(registerButton);
	}
}