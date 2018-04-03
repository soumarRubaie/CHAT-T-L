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
	private static JTextField textField;
	private static JTextField textField_1;
	private static JTextField textField_2;
//	private static final Action action = new SwingAction();

	public static void main(String[] args) {
		JFrame frame = new JFrame("formulaire d'inscription");
		frame.setSize(546, 559);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel_2 = new JPanel();
		frame.getContentPane().add(panel_2);
		placeComponents(panel_2);

		frame.setVisible(true);
		
		
	}

	private static void placeComponents(JPanel panel) {

		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(23, 13, 480, 488);
		panel_2.add(panel_1);
		panel_1.setLayout(null);

		JLabel userLabel = new JLabel("Nom d'utilisateur*");
		userLabel.setBounds(12, 178, 149, 25);
		panel_1.add(userLabel);
		userLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));

		JTextField userText = new JTextField(20);
		userText.setBounds(242, 180, 160, 25);
		panel_1.add(userText);

		JLabel passwordLabel = new JLabel("Mot de passe*");
		passwordLabel.setBounds(12, 206, 114, 25);
		panel_1.add(passwordLabel);
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));

		JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(242, 208, 160, 25);
		panel_1.add(passwordText);
		
		JButton registerButton = new JButton("Inscription");
//		registerButton.setAction(action);
		registerButton.setBounds(167, 378, 134, 51);
		panel_1.add(registerButton);
		registerButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		passwordField = new JPasswordField(20);
		passwordField.setBounds(242, 285, 160, 25);
		panel_1.add(passwordField);
		System.out.printf("valeur du input", passwordField);
		
		JLabel lblConfirmerMotDe = new JLabel("Confirmer mot de passe*");
		lblConfirmerMotDe.setBounds(12, 270, 204, 51);
		panel_1.add(lblConfirmerMotDe);
		lblConfirmerMotDe.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		JLabel lblNom = new JLabel("Nom");
		lblNom.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNom.setBounds(12, 13, 149, 25);
		panel_1.add(lblNom);
		
		textField = new JTextField(20);
		textField.setBounds(242, 15, 160, 25);
		panel_1.add(textField);
		
		JLabel lblPays = new JLabel("Pays");
		lblPays.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblPays.setBounds(12, 51, 149, 25);
		panel_1.add(lblPays);
		
		textField_1 = new JTextField(20);
		textField_1.setBounds(242, 53, 160, 25);
		panel_1.add(textField_1);
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblDescription.setBounds(12, 89, 149, 25);
		panel_1.add(lblDescription);
		
		textField_2 = new JTextField(20);
		textField_2.setBounds(242, 91, 160, 76);
		panel_1.add(textField_2);
		
		
		
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