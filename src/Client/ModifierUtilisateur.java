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
import javax.swing.JTextField;

public class ModifierUtilisateur extends JFrame {

    private JPanel contentPane;
    private JTextField txtSoumar;
    private JTextField textField_1;
    private JLabel lblNewLabel;
    private JLabel lblNewLabel_1;
    
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ModifierUtilisateur frame = new ModifierUtilisateur();
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
    public ModifierUtilisateur() {
        
        String label[] = { "Zero", "One", "Two", "Three" };
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 831, 705);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        
        JButton btnModifer = new JButton("Modifier");
        btnModifer.setForeground(Color.RED);
        
        JLabel lblBonjour = new JLabel("Modifier mon profil");
        lblBonjour.setForeground(new Color(220, 20, 60));
        lblBonjour.setFont(new Font("Tahoma", Font.PLAIN, 17));
        
        txtSoumar = new JTextField();
        txtSoumar.setText("soumar");
        txtSoumar.setColumns(10);
        
        textField_1 = new JTextField();
        textField_1.setColumns(10);
        
        lblNewLabel = new JLabel("Nom utilisateur");
        
        lblNewLabel_1 = new JLabel("Nouveau mot de passe");
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
            gl_contentPane.createParallelGroup(Alignment.TRAILING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addGap(221)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblNewLabel)
                        .addComponent(lblNewLabel_1))
                    .addGap(43)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addComponent(btnModifer, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
                        .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
                            .addComponent(lblBonjour, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textField_1)
                            .addComponent(txtSoumar)))
                    .addContainerGap(334, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addGap(49)
                    .addComponent(lblBonjour)
                    .addGap(51)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(txtSoumar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNewLabel))
                    .addGap(29)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNewLabel_1))
                    .addGap(34)
                    .addComponent(btnModifer)
                    .addContainerGap(409, Short.MAX_VALUE))
        );
        contentPane.setLayout(gl_contentPane);
        btnModifer.addActionListener(new ActionListener() {    
            @Override
            public void actionPerformed(ActionEvent e) {
                Home home = new Home();
                 home.setVisible(true);
                 dispose();

            }
        });
        
    }
}
