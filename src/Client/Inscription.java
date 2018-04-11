package Client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Serveur.Requests;

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
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Inscription extends JFrame {

    private JPanel contentPane;
    private JTextField textField_nom;
    private JTextField textField_description;
    private JTextField textField_pays;
    private JTextField textField_username;
    private JTextField textField_password;
    
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Inscription frame = new Inscription();
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
    public Inscription() {
		// ######### Labels
		// ######## Text fields
		// ########### Buttons
		// ############### Buttons (ACTION) listeners
    	Client client = Client.getInstance();
    	
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 745, 603);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        
        
		// ########### Buttons
        JButton btnTermine = new JButton("Terminer");
        btnTermine.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnTermine.setBackground(Color.GREEN);

        
		// ######### Labels
        JLabel lblListeDesSalles = new JLabel("Inscription");
        lblListeDesSalles.setForeground(Color.RED);
        lblListeDesSalles.setFont(new Font("Thorndale AMT", Font.PLAIN, 23));
        JLabel lblNewLabel = new JLabel("Nom ");
        JLabel lblNewLabel_1 = new JLabel("Description");

        JLabel lblNewLabel_2 = new JLabel("Pays");
        JLabel lblNewLabel_3 = new JLabel("Nom d'utilisateur");
        JLabel lblNewLabel_4 = new JLabel("Mot de passe");
        
		// ######## Text fields
        textField_nom = new JTextField();
        textField_nom.setColumns(10);

        
        textField_description = new JTextField();
        textField_description.setHorizontalAlignment(SwingConstants.LEFT);
        textField_description.setColumns(10);
        
        textField_pays = new JTextField();
        textField_pays.setColumns(10);
        
        textField_username = new JTextField();
        textField_username.setColumns(10);
        
        textField_password = new JTextField();
        textField_password.setColumns(10);
        

        
		// ######## Placing content

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addGap(336)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                        .addComponent(lblNewLabel)
                        .addComponent(lblNewLabel_1)
                        .addComponent(lblNewLabel_2)
                        .addComponent(lblNewLabel_3)
                        .addComponent(lblNewLabel_4))
                    .addGap(18)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addComponent(btnTermine)
                        .addComponent(textField_description, 116, 116, 116)
                        .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
                            .addComponent(lblListeDesSalles, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                            .addComponent(textField_nom)
                            .addComponent(textField_username)
                            .addComponent(textField_password)
                            .addComponent(textField_pays)))
                    .addContainerGap(170, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addGap(55)
                    .addComponent(lblListeDesSalles, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                    .addGap(18)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(textField_nom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNewLabel))
                    .addGap(30)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblNewLabel_1)
                        .addComponent(textField_description, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE))
                    .addGap(43)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(textField_pays, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNewLabel_2))
                    .addGap(29)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(textField_username, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNewLabel_3))
                    .addGap(34)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(textField_password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNewLabel_4))
                    .addGap(18)
                    .addComponent(btnTermine)
                    .addGap(137))
        );
        contentPane.setLayout(gl_contentPane);
        
		// ############### Buttons (ACTION) listeners
        btnTermine.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String param_username = textField_username.getText();
					String param_password = textField_password.getText();
					System.out.println("INSC: username:" + param_username + " pass:" + param_password);

					System.out.println("INSC: Appel à Requests.java pour utiliser le endpoint ");

					if (Requests.createUser(param_username, param_password)) {
						System.out.println("INSC: Réponse du serveur: ");

						client.updateClientLists();
						client.setCurrentUser(param_username);
						
						// display Home
						Home home = new Home();
						client.goToAnotherPage(home);

					} else {
						System.out.println("INSC: Échec de l'inscription - réessayer");
					}

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}});
        
        }


    }


