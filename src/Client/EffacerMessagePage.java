package Client;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;

import Serveur.Requests;
import Structures.Message;
import Structures.Salle;
import Structures.User;
import Structures.Utils;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.BoxLayout;
import javax.swing.JSplitPane;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import java.awt.List;
import java.awt.TextField;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;
import java.awt.event.ActionEvent;

public class EffacerMessagePage extends JFrame{

    private JPanel contentPane;
    
    ArrayList<JComponent> messages = new ArrayList<JComponent>();
    
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	EffacerMessagePage frame = new EffacerMessagePage();
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
    public EffacerMessagePage() {
    	Client client = Client.getInstance();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 745, 483);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        JScrollPane scroller = new JScrollPane(contentPane);
        
        setContentPane(scroller);
        
        
		// ############### Buttons (ACTION) listeners

        JButton btnUpdateMessages = new JButton("Mettre à jour");
        btnUpdateMessages.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
        	for (int i = 0; i < messages.size(); i++)
        		{

        			JComponent comp = messages.get(i);
        			if (comp instanceof JTextField)
        			{
        				JTextField textField = (JTextField)comp;
        				if (textField.getText().equals(""))
        				{
        					ArrayList<Message> liste = client.getCurrentSalle().getMessagesList();
        					try {
								if (Requests.deleteMessage(Integer.toString(liste.get(i).getIdMessage()), Integer.toString(client.currentSalle.getId()))) {
									System.out.println(String.format("DELETE: Réussite de l'effacement du message ID : %d", liste.get(i).getIdMessage()));
								} else {
									System.out.println(String.format("DELETE: Echec de l'effacement du message ID : %d", liste.get(i).getIdMessage()));
								}
								
							} catch (UnsupportedEncodingException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
        				}
        			}
        		}
				SallePage sp;
				try {
					sp = new SallePage(client.getCurrentSalle().getId(), client.getCurrentUser().getUsername());
					client.goToAnotherPage(sp);
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
		// ###############FIN Buttons (ACTION) listeners
        
        JLabel lblNom = new JLabel("Effacer des messages");
        lblNom.setForeground(Color.RED);
        lblNom.setFont(new Font("Thorndale AMT", Font.PLAIN, 16));
        lblNom.setToolTipText("Pour effacer un message, il suffit de supprimer le supprimer complètement et de laisser la ligne vide.");
        
		Salle s = client.getCurrentSalle();
		String toScreen = "";
		for (Message msg : s.getMessagesList()) {
			toScreen =  msg.getContenuMessage();
			
			if (msg.getIdUtilisateur() == client.currentUser.getId()) {
				JTextField field = new JTextField();
				field.setColumns(50);
				field.setText(toScreen);
				messages.add(field);
			} else {
				messages.add(new JLabel(toScreen));
			}
		}
        GroupLayout gl_contentPane = new GroupLayout(contentPane);

		ParallelGroup groupH =  gl_contentPane.createParallelGroup(Alignment.LEADING, false)
        .addComponent(lblNom, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
        .addComponent(btnUpdateMessages);
		
		for (JComponent component : messages) {
			groupH = groupH.addComponent(component);
		}
        
        gl_contentPane.setHorizontalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addGap(50)
                    .addGroup(groupH)
                    .addContainerGap(78, Short.MAX_VALUE))
        );

		SequentialGroup groupV = gl_contentPane.createSequentialGroup()
                .addGap(55)
                .addComponent(lblNom, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                .addGap(18);

		for (JComponent component : messages) {
			groupV = groupV.addComponent(component, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	                .addGap(30);
		}
		groupV = groupV.addComponent(btnUpdateMessages)
                .addGap(210);
		
        gl_contentPane.setVerticalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(groupV)
        );
        contentPane.setLayout(gl_contentPane);
    }
}
