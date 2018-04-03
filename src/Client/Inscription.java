package Client;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;

public class Inscription {
	private static JPasswordField passwordField;
	private static JPanel panel_2;
//	private static final Action action = new SwingAction();

	public static void main(String[] args) {
		JFrame frame = new JFrame("formulaire d'inscription");
		frame.setSize(546, 449);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel_2 = new JPanel();
		frame.getContentPane().add(panel_2);
		placeComponents(panel_2);

		frame.setVisible(true);
		
		
	}

	private static void placeComponents(JPanel panel) {

		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(23, 43, 390, 287);
		panel_2.add(panel_1);
		panel_1.setLayout(null);

		JLabel userLabel = new JLabel("Utilisateur");
		userLabel.setBounds(0, 0, 80, 25);
		panel_1.add(userLabel);
		userLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));

		JTextField userText = new JTextField(20);
		userText.setBounds(230, 2, 160, 25);
		panel_1.add(userText);

		JLabel passwordLabel = new JLabel("Mot de passe");
		passwordLabel.setBounds(0, 64, 104, 25);
		panel_1.add(passwordLabel);
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));

		JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(230, 66, 160, 25);
		panel_1.add(passwordText);
		
		JButton registerButton = new JButton("Inscription");
//		registerButton.setAction(action);
		registerButton.setBounds(155, 236, 134, 51);
		panel_1.add(registerButton);
		registerButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		passwordField = new JPasswordField(20);
		passwordField.setBounds(230, 143, 160, 25);
		panel_1.add(passwordField);
		System.out.printf("valeur du input", passwordField);
		
		JLabel lblConfirmerMotDe = new JLabel("Confirmer mot de passe");
		lblConfirmerMotDe.setBounds(0, 128, 204, 51);
		panel_1.add(lblConfirmerMotDe);
		lblConfirmerMotDe.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		
		
	}
	
	/**
	 * verification des deux password fields
	 */
//	private class SwingAction extends AbstractAction {
//		public SwingAction() {
//			putValue(NAME, "SwingAction");
//			putValue(SHORT_DESCRIPTION, "Some short description");
//		}
//		public void actionPerformed(ActionEvent e) {
//		}
//	}
}