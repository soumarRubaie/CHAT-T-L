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
		
		String label[] = { "Zero", "One", "Two", "Three" };
		
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
		
		JButton btnDconenxion = new JButton("Déconenxion");
		
		JButton btnAjouterUneSalle = new JButton("Ajouter une salle de discussion");
		btnAjouterUneSalle.setForeground(Color.RED);
		
		JLabel lblBonjour = new JLabel("Bonjour :)");
		lblBonjour.setForeground(new Color(220, 20, 60));
		lblBonjour.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		JButton btnModifierProfil = new JButton("Modifier mon profil");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
		    gl_contentPane.createParallelGroup(Alignment.TRAILING)
		        .addGroup(gl_contentPane.createSequentialGroup()
		            .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
		                .addGroup(gl_contentPane.createSequentialGroup()
		                    .addContainerGap()
		                    .addComponent(btnModifierProfil, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE))
		                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
		                    .addGroup(gl_contentPane.createSequentialGroup()
		                        .addContainerGap()
		                        .addComponent(btnDconenxion, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE))
		                    .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
		                        .addGroup(gl_contentPane.createSequentialGroup()
		                            .addGap(27)
		                            .addComponent(lblBonjour)
		                            .addPreferredGap(ComponentPlacement.RELATED, 99, Short.MAX_VALUE)
		                            .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
		                                .addComponent(lblListeDesUtilisateurs)
		                                .addComponent(list, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
		                                .addComponent(btnConsulterProfile, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
		                        .addGroup(gl_contentPane.createSequentialGroup()
		                            .addContainerGap(165, Short.MAX_VALUE)
		                            .addComponent(btnAjouterUneSalle)))))
		            .addGap(30)
		            .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
		                .addComponent(btnConsulterSalle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                .addComponent(list2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
		                .addComponent(lblListeDesSalles, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
		            .addGap(290))
		);
		gl_contentPane.setVerticalGroup(
		    gl_contentPane.createParallelGroup(Alignment.LEADING)
		        .addGroup(gl_contentPane.createSequentialGroup()
		            .addContainerGap()
		            .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
		                .addGroup(gl_contentPane.createSequentialGroup()
		                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
		                        .addComponent(lblListeDesSalles)
		                        .addComponent(lblListeDesUtilisateurs))
		                    .addPreferredGap(ComponentPlacement.UNRELATED)
		                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
		                        .addComponent(list2, GroupLayout.PREFERRED_SIZE, 254, GroupLayout.PREFERRED_SIZE)
		                        .addGroup(gl_contentPane.createSequentialGroup()
		                            .addComponent(list)
		                            .addGap(5)
		                            .addComponent(btnConsulterProfile)))
		                    .addPreferredGap(ComponentPlacement.RELATED)
		                    .addComponent(btnConsulterSalle)
		                    .addGap(19)
		                    .addComponent(btnModifierProfil)
		                    .addPreferredGap(ComponentPlacement.UNRELATED)
		                    .addComponent(btnAjouterUneSalle))
		                .addComponent(lblBonjour))
		            .addGap(18)
		            .addComponent(btnDconenxion)
		            .addContainerGap(220, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		
		// ############### Buttons (ACTION) listeners

		btnAjouterUneSalle.addActionListener(new ActionListener() {    
            @Override
            public void actionPerformed(ActionEvent e) {
                 AjouterSalle test = new AjouterSalle();
                 test.setVisible(true);
                 dispose();
            }
        });
		btnDconenxion.addActionListener(new ActionListener() {    
            @Override
            public void actionPerformed(ActionEvent e) {
            	LoginPage l = new LoginPage();
                 l.setVisible(true);
                 dispose();
            }
        });
		
		btnModifierProfil.addActionListener(new ActionListener() {    
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                ModifierUtilisateur l = new ModifierUtilisateur();
	                 l.setVisible(true);
	                 dispose();
	            }
	        });
	        
		
		
	}
}
