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
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AjouterSalle extends JFrame {

    private JPanel contentPane;
    private JTextField textField_salleNom;
    private JTextField textField_salleDesc;
    
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AjouterSalle frame = new AjouterSalle();
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
    public AjouterSalle() {
    	Client client = Client.getInstance();

        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 745, 483);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        
        
		// ############### Buttons (ACTION) listeners

        JButton btnAjouter = new JButton("Ajouter");
        btnAjouter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
					String param_salleNom = textField_salleNom.getText();
					String param_salleDesc = textField_salleDesc.getText();
					System.out.println("CREATESALL: username:" + param_salleNom + " pass:" + param_salleDesc);

					System.out.println("CREATESALL: Appel à Requests.java pour utiliser le endpoint ");
					
					// Needed only when testing this page alone
					String userId = (Client.getInstance() == null) ? "0" : Integer.toString(Client.getInstance().getUserId()); 
					
					if (Requests.createSalle(param_salleNom, param_salleDesc, userId)) {
						System.out.println("CREATESALL: Réponse du serveur: ");

						// Needed only when testing this page alone
						if (client != null)
							client.updateClient();

						// display Home
						Home home = new Home();
						home.setVisible(true);
					
						dispose();	
					} else {
						System.out.println("CREATESALL: Échec de créeation salle - réessayer");
					}

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	
            }
        });
		// ###############FIN Buttons (ACTION) listeners

        
        
        JLabel lblListeDesSalles = new JLabel("Ajouter une salle");
        lblListeDesSalles.setForeground(Color.RED);
        lblListeDesSalles.setFont(new Font("Thorndale AMT", Font.PLAIN, 16));
        
        textField_salleNom = new JTextField();
        textField_salleNom.setColumns(10);
        
        textField_salleDesc = new JTextField();
        textField_salleDesc.setColumns(10);
        
        JLabel lblNewLabel = new JLabel("Nom de la salle");
        
        JLabel lblNewLabel_1 = new JLabel("Description");
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
                        .addComponent(lblListeDesSalles, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAjouter)
                        .addComponent(textField_salleDesc, GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                        .addComponent(textField_salleNom))
                    .addContainerGap(78, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addGap(55)
                    .addComponent(lblListeDesSalles, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                    .addGap(18)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(textField_salleNom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNewLabel))
                    .addGap(30)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(textField_salleDesc, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNewLabel_1))
                    .addGap(30)
                    .addComponent(btnAjouter)
                    .addGap(210))
        );
        contentPane.setLayout(gl_contentPane);
    }
}
