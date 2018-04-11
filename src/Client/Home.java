package Client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Serveur.Requests;
import Structures.Salle;
import Structures.User;

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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class Home extends JFrame {
	Client client ;
	JList<String> userList = new JList<String>();
	JList<String> salleList = new JList<String>();
	JLabel lblBonjour = new JLabel();


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
		super();
		client = Client.getInstance();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 831, 705);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		Initialize();
	}
	
	private void Initialize() {
		
		//Retrouver les labels à partir des lists salles, usagers
		String salleLabels[] = client.getSalleLabels();
		String userLabels[] = client.getUserLabels();
		
		userList.setListData(userLabels);
		salleList.setListData(salleLabels);
		
		JButton btnConsulterProfile = new JButton("Consulter");

		
		JLabel lblListeDesUtilisateurs = new JLabel("Liste des utilisateurs");
		
		JLabel lblListeDesSalles = new JLabel("Liste des salles");
		
		JButton btnConsulterSalle = new JButton("Consulter");
		
		JButton btnDconenxion = new JButton("Déconnexion");
		
		JButton btnAjouterUneSalle = new JButton("Ajouter une salle de discussion");
		btnAjouterUneSalle.setForeground(Color.RED);
		
		lblBonjour.setText(String.format("Bonjour %s :)", client.getCurrentUser().getUsername()));
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
		                                .addComponent(userList, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
		                                .addComponent(btnConsulterProfile, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
		                        .addGroup(gl_contentPane.createSequentialGroup()
		                            .addContainerGap(165, Short.MAX_VALUE)
		                            .addComponent(btnAjouterUneSalle)))))
		            .addGap(30)
		            .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
		                .addComponent(btnConsulterSalle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                .addComponent(salleList, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
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
		                        .addComponent(salleList, GroupLayout.PREFERRED_SIZE, 254, GroupLayout.PREFERRED_SIZE)
		                        .addGroup(gl_contentPane.createSequentialGroup()
		                            .addComponent(userList)
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
		btnConsulterSalle.setEnabled(false);
		contentPane.setLayout(gl_contentPane);
		
		// ############### List (ACTION) listener
		salleList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
			    if (e.getValueIsAdjusting() == false) {

			        if (salleList.getSelectedIndex() == -1) {
			        //No selection, disable fire button.
			        	btnConsulterSalle.setEnabled(false);

			        } else {
			        //Selection, enable the fire button.
			        	btnConsulterSalle.setEnabled(true);
			        }
			    }
				
			}
		});
		
		// ############### Buttons (ACTION) listeners

		btnAjouterUneSalle.addActionListener(new ActionListener() {    
            @Override
            public void actionPerformed(ActionEvent e) {
                 AjouterSalle test = new AjouterSalle();
 				client.goToAnotherPage(test);
            }
        });
		btnDconenxion.addActionListener(new ActionListener() {    
            @Override
            public void actionPerformed(ActionEvent e) {
            	LoginPage l = new LoginPage();
				client.goToAnotherPage(l);
            }
        });
		
		btnModifierProfil.addActionListener(new ActionListener() {    
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                UserPage l = new UserPage();
	                client.goToAnotherPage(l);
	            }
	        }); 
		
		btnConsulterProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		btnConsulterSalle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int salleId = 0;
				String name = (String)salleList.getSelectedValue();
				ArrayList<Salle> salles = (ArrayList<Salle>) client.getSalles();
				
				for (Salle salle : salles) {
					String fullName = Integer.toString(salle.getId());
					fullName += "_";
					fullName += salle.getSalleNom();
					if (fullName.equals(name)) {
						salleId = salle.getId();
						break;
					}
				}
				
				SallePage sp;
				try {
					sp = new SallePage(salleId, client.getCurrentUser().getUsername());
					client.goToAnotherPage(sp);
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}


			}
		});
		
	}
	
	public void Update() {
		int currentIndex = salleList.getSelectedIndex();
		String salleLabels[] = client.getSalleLabels();
		String userLabels[] = client.getUserLabels();
		
		userList.setListData(userLabels);
		salleList.setListData(salleLabels);
		
		userList.repaint();
		salleList.repaint();
		salleList.setSelectedIndex(currentIndex);
		
		lblBonjour.setText(String.format("Bonjour %s :)", client.getCurrentUser().getUsername()));
	}
}
