package Client;


import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;

public class Home implements ActionListener  {
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

		public static  void placeComponents(JPanel panel) {

			panel.setLayout(null);
			
			JPanel panel_1 = new JPanel();
			panel_1.setBounds(12, -14, 998, 690);
			panel_2.add(panel_1);
			panel_1.setLayout(null);
			
			JPanel panel_4 = new JPanel();
			panel_4.setBorder(new TitledBorder(null, "Liste d'utilisateurs:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_4.setBounds(0, 2, 249, 260);
			panel_1.add(panel_4);
			panel_4.setLayout(null);
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(12, 26, 225, 185);
			panel_4.add(scrollPane);
			
			JList list = new JList(label);
			scrollPane.setViewportView(list);
			
			JButton btnConsulterProfil = new JButton("Consulter profil");
			btnConsulterProfil.setBounds(36, 224, 169, 25);
			panel_4.add(btnConsulterProfil);
//			btnConsulterProfil.addActionListener();

			
			
			JButton btnDeconnexion = new JButton("Deconnexion");
			btnDeconnexion.setForeground(Color.RED);
			btnDeconnexion.setFont(new Font("Tahoma", Font.PLAIN, 17));
			btnDeconnexion.setBounds(0, 639, 134, 51);
			panel_1.add(btnDeconnexion);

			
			JPanel panel_3 = new JPanel();
			panel_3.setBorder(new TitledBorder(null, "Salle de discussion:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_3.setBounds(0, 264, 249, 250);
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
			
			JButton btnModifierMonProfil = new JButton("Modifier mon profil");
			btnModifierMonProfil.setForeground(Color.RED);
			btnModifierMonProfil.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnModifierMonProfil.setBounds(0, 587, 134, 51);
			panel_1.add(btnModifierMonProfil);
			
			
			
		}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("TEST");
		
	}
}
