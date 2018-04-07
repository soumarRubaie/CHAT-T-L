package Client;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Serveur.AuthentificationUser;
import Serveur.Requests;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;

public class LoginPage extends JFrame {
	/**
	 * manque a retrieve les
	 * 
	 * @param username
	 * @param password
	 * @return
	 */

	// so that we can start with the login page, as with Home()
	public LoginPage() {
		// Define frame
		JFrame frame = new JFrame("Login IFT585  projet chat");
		frame.setSize(426, 386);

		// Setting what to do on close
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);

		// Details of layout performed there
		placeComponents(panel, frame);

	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage frame = new LoginPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static void placeComponents(JPanel panel, JFrame frame) {

		panel.setLayout(null);

		// ######### Labels
		JLabel userLabel = new JLabel("Utilisateur");
		userLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		userLabel.setBounds(23, 43, 80, 25);
		panel.add(userLabel);

		JLabel passwordLabel = new JLabel("Mot de passe");
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		passwordLabel.setBounds(23, 96, 104, 25);
		panel.add(passwordLabel);

		JLabel lblVousNavezPas = new JLabel("Vous n'avez pas de compte?");
		lblVousNavezPas.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblVousNavezPas.setBounds(105, 239, 222, 25);
		panel.add(lblVousNavezPas);

		// ######## Text fields
		JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(128, 98, 160, 25);
		panel.add(passwordText);

		JTextField userText = new JTextField(20);
		userText.setBounds(128, 45, 160, 25);
		panel.add(userText);

		// ########### Buttons
		JButton loginButton = new JButton("Connexion");
		loginButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		loginButton.setBounds(142, 149, 134, 46);
		panel.add(loginButton);

		JButton registerButton = new JButton("Inscription");
		registerButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		registerButton.setBounds(142, 277, 134, 51);
		panel.add(registerButton);

		// ###############Button listeners
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (Requests.athenticateUser(userText.getText(), passwordText.getText())) {
						System.out.println("AUTH: loggin success");
						// display Home
						Home home = new Home();
						home.setVisible(true);
						frame.dispose();
					} else {
						System.out.println("AUTH: login incorrect, essayer à nouveau");
						LoginPage lp = new LoginPage();
						lp.setVisible(true);
						frame.dispose();
						
					}

				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		registerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				// change view to inscription
				System.out.println("test redirect inscription");
				Inscription test = new Inscription();
				test.setVisible(true);

				// TODO: appel au socketTCP

			}

		});

		// This should always be last
		frame.setVisible(true);

	}

}
