package Client;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JTable;

public class Home {
	private static JPasswordField passwordField;
	private static JPanel panel_2;
	private static JTable table;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/**
		 * Liste utilisateur connectés & non connectés 
		 * Liste des salles de discussion
		 * 	- rejoindre une salle
		 * 	- quitter une salle
		 * 	- supprimer une salle
		 * Salle de discussion
		 * 	- personne presente dans la salle
		 * 	- Champs écriture d'un message
		 * 	- supprimer message (boutton delete X)
		 * 	- liste des messages (archive)
		 * 	- Boutton envoyer
		 * 
		 * Deconnexion 
		 * 
		 */
		

		
//		private static final Action action = new SwingAction();

			JFrame frame = new JFrame("formulaire d'inscription");
			frame.setSize(1040, 761);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			panel_2 = new JPanel();
			frame.getContentPane().add(panel_2);
			placeComponents(panel_2);

			frame.setVisible(true);
			
			
		}

		private static void placeComponents(JPanel panel) {

			panel.setLayout(null);
			
			JPanel panel_1 = new JPanel();
			panel_1.setBounds(12, 13, 998, 690);
			panel_2.add(panel_1);
			panel_1.setLayout(null);

			JLabel userLabel = new JLabel("Liste d'utilisateurs:");
			userLabel.setBounds(12, 15, 151, 25);
			panel_1.add(userLabel);
			userLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));

			JTextField userText = new JTextField(20);
			userText.setBounds(826, 196, 160, 25);
			panel_1.add(userText);

			JLabel passwordLabel = new JLabel("Mot de passe");
			passwordLabel.setBounds(596, 258, 104, 25);
			panel_1.add(passwordLabel);
			passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));

			JPasswordField passwordText = new JPasswordField(20);
			passwordText.setBounds(826, 260, 160, 25);
			panel_1.add(passwordText);
			
			JButton registerButton = new JButton("Inscription");
//			registerButton.setAction(action);
			registerButton.setBounds(651, 399, 134, 51);
			panel_1.add(registerButton);
			registerButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
			
			passwordField = new JPasswordField(20);
			passwordField.setBounds(826, 337, 160, 25);
			panel_1.add(passwordField);
			System.out.printf("valeur du input", passwordField);
			
			JLabel lblConfirmerMotDe = new JLabel("Confirmer mot de passe");
			lblConfirmerMotDe.setBounds(596, 322, 204, 51);
			panel_1.add(lblConfirmerMotDe);
			lblConfirmerMotDe.setFont(new Font("Tahoma", Font.PLAIN, 17));
			
			JButton btnDeconnexion = new JButton("Deconnexion");
			btnDeconnexion.setForeground(Color.RED);
			btnDeconnexion.setFont(new Font("Tahoma", Font.PLAIN, 17));
			btnDeconnexion.setBounds(852, 2, 134, 51);
			panel_1.add(btnDeconnexion);
			
			table = new JTable();
			table.setBounds(22, 64, 142, 168);
			panel_1.add(table);
			
			
			
		}
}
