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
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

public class Home {
	private static JPasswordField passwordField;
	private static JPanel panel_2;
	static String label[] = { "Zero", "One", "Two", "Three", "Four", "Five", "Six",
		      "Seven", "Eight", "Nine", "Ten", "Eleven" };
	static String labelSalleDiscussion[] = { "Salle Zero", "Salle One", "Salle Two", "Salle Three", "Salle Four", "Salle Five", "Salle Six",
		      "Salle Seven", "Salle Eight", "Salle Nine", "Salle Ten", "Salle Eleven" };
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

			JFrame frame = new JFrame("Home");
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
			
			JPanel panel_4 = new JPanel();
			panel_4.setBorder(new TitledBorder(null, "Liste d'utilisateurs:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_4.setBounds(12, 21, 249, 196);
			panel_1.add(panel_4);
			panel_4.setLayout(null);
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(12, 26, 225, 161);
			panel_4.add(scrollPane);
			
			JList list = new JList(label);
			scrollPane.setViewportView(list);

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
			
			JPanel panel_3 = new JPanel();
			panel_3.setBorder(new TitledBorder(null, "Salle de discussion:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_3.setBounds(12, 230, 249, 250);
			panel_1.add(panel_3);
			panel_3.setLayout(null);
			
			JScrollPane scrollPane_1 = new JScrollPane();
			scrollPane_1.setBounds(6, 34, 231, 170);
			panel_3.add(scrollPane_1);
			
			JList list_1 = new JList(labelSalleDiscussion);
			scrollPane_1.setViewportView(list_1);
			
			JButton btnNewButton = new JButton("Rejoindre discussion");
			btnNewButton.setBounds(27, 212, 169, 25);
			panel_3.add(btnNewButton);
			
			
			
		}
}
