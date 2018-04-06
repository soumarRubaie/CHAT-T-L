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
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

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
		
		JList list = new JList(label);
		
		JList list2 = new JList(label);
		
		JButton btnConsulterProfile = new JButton("Consulter");
		btnConsulterProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JLabel lblListeDesUtilisateurs = new JLabel("Liste des utilisateurs");
		
		JLabel lblListeDesSalles = new JLabel("Liste des salles");
		
		JButton btnConsulterSalle = new JButton("Consulter ");
		
		JButton btnModifierMonProfil = new JButton("Modifier mon profil");
		
		JButton btnDconenxion = new JButton("Déconenxion");
		
		JButton btnAjouterUneSalle = new JButton("Ajouter une salle de discussion");
		btnAjouterUneSalle.setForeground(Color.RED);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(111, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnAjouterUneSalle)
						.addComponent(btnModifierMonProfil)
						.addComponent(btnDconenxion, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addComponent(lblListeDesUtilisateurs)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnConsulterProfile)
								.addComponent(list, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE))))
					.addGap(30)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnConsulterSalle, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
						.addComponent(list2, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblListeDesSalles, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
					.addGap(290))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblListeDesSalles)
						.addComponent(lblListeDesUtilisateurs))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(list2, GroupLayout.PREFERRED_SIZE, 254, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(list)
							.addGap(18)
							.addComponent(btnConsulterProfile)))
					.addGap(18)
					.addComponent(btnConsulterSalle)
					.addGap(41)
					.addComponent(btnAjouterUneSalle)
					.addGap(18)
					.addComponent(btnModifierMonProfil)
					.addGap(18)
					.addComponent(btnDconenxion)
					.addGap(82))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
