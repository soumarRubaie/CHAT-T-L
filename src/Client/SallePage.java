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
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.List;
import java.awt.TextField;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class SallePage extends JFrame{
	static Client client ;
	String userLabels[] ;
	int currenSalleId;
	String currentUsername;
	static JTextPane mainTextArea = new JTextPane();
	JPanel panel = new JPanel();
	static JList<String> userList = new JList<String>();
	static JTextField writeMessageField;


	// so that we can start with the login page, as with Home()
	public SallePage() {
		super("Salle discussion");
		this.setSize(902, 739);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().add(panel, BorderLayout.WEST);
		client = Client.getInstance();
		userLabels = client.getUserLabels();
		placeComponents(panel, this);
	
	}
	
	public SallePage(int salleIdAJoindre, String username) throws UnsupportedEncodingException {
		super("Salle discussion");
		this.setSize(902, 739);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().add(panel, BorderLayout.WEST);
		client = Client.getInstance();
		currentUsername = username;
		client.setCurrentUser(username);
		client.suscribeUserToSalle(salleIdAJoindre, client.getCurrentUser().getId());

		client.setCurrentSalle(salleIdAJoindre);
		currenSalleId = salleIdAJoindre;

		userLabels = client.getConnectedUserLabels();

		placeComponents(panel, this);
		initSalle(this, panel);
	}
	 
	
	private void initSalle(JFrame frame, JPanel panel) {
		/*Gets the messages stored if any and prints them on the main screen*/
		
		//Getting msg from Client (who got them from server)
		Salle s = client.getCurrentSalle();
		String toScreen = "";
		for (Message msg : s.getMessagesList()) {
			toScreen =  msg.getContenuMessage();
			writeToMainTextArea(Utils.lineReturn + toScreen);
		}
	}
	
	public static void updateSalle() {
		/*Appelé lorsque un client ajoute run new msg a la salle et/ou lors de regular updates*/
		Salle s = client.getCurrentSalle();
		String toScreen = "";
		//to reset
		resetToMainTextArea();
		for (Message msg : s.getMessagesList()) {
			toScreen =  msg.getContenuMessage();
			writeToMainTextArea(Utils.lineReturn + toScreen);
		}

		ArrayList<String> dum = new ArrayList<String>();
		for (User user : s.getSuscribersList()) {
			if (user !=null) {
				dum.add(s.getId() + "_" + user.getUsername());
			}
		}
		String userLabels[] = new String[dum.size()];
		dum.toArray(userLabels);
		
		userList.setListData(userLabels);
		userList.repaint();
	}
	public static void resetToMainTextArea() {
		mainTextArea.setText("");
	}
	
	
	
	public static void writeToMainTextArea(String toWrite) {
		StyledDocument doc = mainTextArea.getStyledDocument();

		//  Define a keyword attribute
		SimpleAttributeSet plainStyle = new SimpleAttributeSet();
		//StyleConstants.setForeground(keyWord, Color.RED);
		//StyleConstants.setBackground(keyWord, Color.YELLOW);
		//StyleConstants.setBold(keyWord, true);
		//  Add some text

	    try {
			doc.insertString(doc.getLength(), toWrite, plainStyle );
			
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SallePage frame = new SallePage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void placeComponents(JPanel panel, JFrame frame) {
		//############# Generic stuff

		Salle s = client.getCurrentSalle();

		ArrayList<String> dum = new ArrayList<String>();
		for (User user : s.getSuscribersList()) {
			if (user !=null) {
				dum.add(s.getId() + "_" + user.getUsername());
			}
		}
		String userLabels[] = new String[dum.size()];
		dum.toArray(userLabels);
		
		userList.setListData(userLabels);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(userList);
		// ######### Labels
		
		
		// ######## Text fields
		// ########### Buttons
		JButton btnBackToHome = new JButton("Retourner au lobby");
		btnBackToHome.setForeground(Color.RED);
		btnBackToHome.setBounds(36, 224, 169, 25);

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
								.addComponent(btnBackToHome))))
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
					.addComponent(btnBackToHome)
					.addContainerGap(270, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.NORTH);
		
		JLabel lblSalle = new JLabel(String.format("Salle %d : %s", currenSalleId, client.getCurrentSalle().getSalleNom()));
		panel_1.add(lblSalle);
		
		
		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, BorderLayout.CENTER);
		
		JLabel lblMessage = new JLabel("Ecrire Message:");

		writeMessageField = new JTextField();
		writeMessageField.setColumns(10);
		
		JButton btnEnvoyer = new JButton("Envoyer");
		btnEnvoyer.setForeground(new Color(255, 0, 255));
		
		JButton btnEffacerMessage = new JButton("Effacer un message");
		btnEnvoyer.setForeground(Color.RED);
		
		JList list_1 = new JList();
		
		mainTextArea.setEditable(false);
		//mainTextArea.setText("Zone de text - Échanges chat ci-dessous");
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblMessage)
					.addGap(27)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(btnEffacerMessage)
						.addComponent(btnEnvoyer)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(writeMessageField, GroupLayout.PREFERRED_SIZE, 347, GroupLayout.PREFERRED_SIZE)
								.addComponent(mainTextArea, GroupLayout.PREFERRED_SIZE, 444, GroupLayout.PREFERRED_SIZE))
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
							.addComponent(mainTextArea, GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)))
					.addGap(18)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(writeMessageField, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMessage))
					.addGap(18)
					.addComponent(btnEnvoyer)
					.addGap(18)
					.addComponent(btnEffacerMessage)
					.addGap(7))
		);
		panel_2.setLayout(gl_panel_2);
		
		
		// ###############Button listeners
		btnBackToHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
                try {
					client.unsuscribeUserToSalle(client.getSalleId(), client.getCurrentUser().getId());
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
               client.setNoSalle();
            	Home l = new Home();
				client.goToAnotherPage(l);
			}
		});
		
		btnEnvoyer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String toWrite = Utils.lineReturn + client.getSignatureForMessage()+": "+ writeMessageField.getText();
				writeToMainTextArea(toWrite);
				client.sendMsg(toWrite);

				try {
					client.updateClientLists();
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				updateSalle();
				writeMessageField.setText("");

			}
		});
		
		btnEffacerMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
            	EffacerMessagePage l = new EffacerMessagePage();
				client.goToAnotherPage(l);
			}
		});
		
		
		frame.setVisible(true);
	}
}
