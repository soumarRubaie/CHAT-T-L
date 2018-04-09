package Client;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JList;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.BoxLayout;
import javax.swing.JSplitPane;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.List;
import java.awt.TextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SallePage {
	static String label[] = { "Zero", "One", "Two", "Three", "Four", "Five", "Six",
		      "Seven", "Eight", "Nine", "Ten", "Eleven" };
	
	
	private static JTextField textField;
	
	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Salle discussion");
		frame.setSize(902, 739);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.WEST);
			
		JScrollPane scrollPane = new JScrollPane();
		JList list = new JList(label);
		scrollPane.setViewportView(list);
		JButton btnConsulterProfil = new JButton("Ajouter utilisateur dans la salle");
		btnConsulterProfil.setForeground(Color.RED);
		btnConsulterProfil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnConsulterProfil.setBounds(36, 224, 169, 25);
		
		JLabel lblListeDesUtilisateurs = new JLabel("Liste des utilisateurs : ");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(56)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(32)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblListeDesUtilisateurs)
								.addComponent(btnConsulterProfil))))
					.addContainerGap(47, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(23)
					.addComponent(lblListeDesUtilisateurs)
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnConsulterProfil)
					.addContainerGap(270, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.NORTH);
		
		JLabel lblSalle = new JLabel("Salle1 ");
		panel_1.add(lblSalle);
		
		
		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, BorderLayout.CENTER);
		
		JLabel lblMessage = new JLabel("\u00C9crire Message:");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JButton btnEnvoyer = new JButton("Envoyer");
		btnEnvoyer.setForeground(new Color(255, 0, 255));
		
		JList list_1 = new JList();
		
		JTextPane txtpnSoumarAcrit = new JTextPane();
		txtpnSoumarAcrit.setEditable(false);
		txtpnSoumarAcrit.setText("Soumar a \u00E9crit \u00E0 7:28 le 2018-04-04 : Bonjour;Aziz a \u00E9crit \u00E0 7:30 le 2018-04-04 : Bonjour Soumar;");
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblMessage)
					.addGap(27)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(btnEnvoyer)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, 347, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtpnSoumarAcrit, GroupLayout.PREFERRED_SIZE, 444, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
							.addComponent(list_1, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(119)
							.addComponent(list_1, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(22)
							.addComponent(txtpnSoumarAcrit, GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)))
					.addGap(18)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMessage))
					.addGap(18)
					.addComponent(btnEnvoyer)
					.addGap(7))
		);
		panel_2.setLayout(gl_panel_2);
		
		
		
		frame.setVisible(true);
		
	}
}
