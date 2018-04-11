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

public class UserPage extends JFrame {

    private JPanel contentPane;
    private JTextField textField_UserName;
    private JTextField textField_Password;
    
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UserPage frame = new UserPage();
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
    public UserPage() {
    	Client client = Client.getInstance();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 745, 483);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        
        
		// ############### Buttons (ACTION) listeners

        JButton btnUpdateProfile = new JButton("Mettre à jour");
        btnUpdateProfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
					String param_username = textField_UserName.getText();
					String param_password = textField_Password.getText();
					System.out.println("UPDATEUSER: username:" + param_username + " pass:" + param_password);

					System.out.println("UPDATEUSER: Appel à Requests.java pour utiliser le endpoint ");
					
					// Needed only when testing this page alone
					String userId = (Client.getInstance() == null) ? "0" : Integer.toString(Client.getInstance().currentUser.getId()); 
					if ( Requests.updateUser(userId, param_username, param_password)) {
						System.out.println("UPDATEUSER: Réponse du serveur: ");

						// Needed only when testing this page alone
						client.currentUser.setUsername(param_username);
						client.currentUser.setPassword(param_password);
						client.updateClientLists();

						// display Home
						Home home = new Home();
						client.goToAnotherPage(home);
					} else {
						System.out.println("CREATESALL: Échec de créeation salle - réessayer");
					}

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
        
        JButton btnAnnuler = new JButton("Annuler");
        btnAnnuler.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				// display Home
				Home home = new Home();
				client.goToAnotherPage(home);
            }
        });
		// ###############FIN Buttons (ACTION) listeners

        String userName = client.currentUser.getUsername();
        String password = client.currentUser.getPassword();
        
        JLabel lblNom = new JLabel("Modifier profil utilisateur");
        lblNom.setForeground(Color.RED);
        lblNom.setFont(new Font("Thorndale AMT", Font.PLAIN, 16));
        
        textField_UserName = new JTextField();
        textField_UserName.setColumns(10);
        textField_UserName.setText(userName);
        
        textField_Password = new JTextField();
        textField_Password.setColumns(10);
        textField_Password.setText(password);
        
        JLabel lblNewLabel = new JLabel("Nom d'utilisateur");
        
        JLabel lblNewLabel_1 = new JLabel("Mot de passe");
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addGap(287)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                        .addComponent(lblNewLabel)
                        .addComponent(lblNewLabel_1))
                    .addGap(18)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(lblNom, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnUpdateProfile)
                        .addComponent(btnAnnuler)
                        .addComponent(textField_Password)
                        .addComponent(textField_UserName))
                    .addContainerGap(78, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addGap(55)
                    .addComponent(lblNom, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                    .addGap(18)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(textField_UserName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNewLabel))
                    .addGap(30)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(textField_Password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNewLabel_1))
                    .addGap(30)
                    .addComponent(btnUpdateProfile)
                    .addGap(15)
                    .addComponent(btnAnnuler)
                    .addGap(210))
        );
        contentPane.setLayout(gl_contentPane);
    }
}
