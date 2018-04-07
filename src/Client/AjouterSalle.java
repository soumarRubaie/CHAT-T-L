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

public class AjouterSalle extends JFrame {

    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;
    
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
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 745, 483);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        
        JButton btnConsulterProfile = new JButton("Ajouter");
        btnConsulterProfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        
        JLabel lblListeDesSalles = new JLabel("Ajouter une salle");
        lblListeDesSalles.setForeground(Color.RED);
        lblListeDesSalles.setFont(new Font("Thorndale AMT", Font.PLAIN, 16));
        
        textField = new JTextField();
        textField.setColumns(10);
        
        textField_1 = new JTextField();
        textField_1.setColumns(10);
        
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
                        .addComponent(btnConsulterProfile)
                        .addComponent(textField_1, GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                        .addComponent(textField))
                    .addContainerGap(78, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addGap(55)
                    .addComponent(lblListeDesSalles, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                    .addGap(18)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNewLabel))
                    .addGap(30)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNewLabel_1))
                    .addGap(30)
                    .addComponent(btnConsulterProfile)
                    .addGap(210))
        );
        contentPane.setLayout(gl_contentPane);
    }
}
