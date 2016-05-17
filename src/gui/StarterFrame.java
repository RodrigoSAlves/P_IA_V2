package gui;

import javax.swing.*;
import java.awt.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import javafx.event.Event;
import javafx.scene.control.ComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Created by ASUS-PC on 11/05/2016.
 */
public class StarterFrame extends JFrame {
	
	String[] selectionControler = {"No Selection", "Random", "AdHoc", "Homegen", "Heterogen"};
	private JComboBox  useCaseCB;
	private JButton btnTest;
    public StarterFrame (){
        try {
            startFrame();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private void startFrame() throws Exception{
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Pursuit Domain");
        
        this.setSize(500, 250);
        this.setResizable(true);
        JPanel headerPanel = new JPanel();
        
        JPanel rightPanel = new JPanel();
        
        JPanel leftPanel = new JPanel();
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
        	groupLayout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(headerPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addGroup(groupLayout.createSequentialGroup()
        					.addComponent(leftPanel, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
        					.addComponent(rightPanel, GroupLayout.PREFERRED_SIZE, 181, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap())
        );
        groupLayout.setVerticalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(headerPanel, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(leftPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(rightPanel, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
        			.addContainerGap())
        );
        
        useCaseCB = new JComboBox();
        updateComboBoxUseCases();
        
        JLabel lblTestCase = new JLabel("Test Case:");
        
        btnTest = new JButton("Test");
        btnTest.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		btnTeste_onClick(arg0);
        	}
        });
        
        GroupLayout gl_leftPanel = new GroupLayout(leftPanel);
        gl_leftPanel.setHorizontalGroup(
        	gl_leftPanel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_leftPanel.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(lblTestCase, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(useCaseCB, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
        			.addGap(18))
        		.addGroup(gl_leftPanel.createSequentialGroup()
        			.addGap(89)
        			.addComponent(btnTest)
        			.addContainerGap(116, Short.MAX_VALUE))
        );
        gl_leftPanel.setVerticalGroup(
        	gl_leftPanel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_leftPanel.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_leftPanel.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblTestCase)
        				.addComponent(useCaseCB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addComponent(btnTest)
        			.addContainerGap(58, Short.MAX_VALUE))
        );
        leftPanel.setLayout(gl_leftPanel);
        
        JLabel lblNewLabel = new JLabel("Inteligencia Artificial - 2016");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel lblPName = new JLabel("PROJETO \u2013 OS PREDADORES E A PRESA ");
        lblPName.setHorizontalAlignment(SwingConstants.CENTER);
        GroupLayout gl_headerPanel = new GroupLayout(headerPanel);
        gl_headerPanel.setHorizontalGroup(
        	gl_headerPanel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_headerPanel.createSequentialGroup()
        			.addGroup(gl_headerPanel.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_headerPanel.createSequentialGroup()
        					.addGap(124)
        					.addComponent(lblPName))
        				.addGroup(gl_headerPanel.createSequentialGroup()
        					.addGap(153)
        					.addComponent(lblNewLabel)))
        			.addContainerGap(141, Short.MAX_VALUE))
        );
        gl_headerPanel.setVerticalGroup(
        	gl_headerPanel.createParallelGroup(Alignment.TRAILING)
        		.addGroup(gl_headerPanel.createSequentialGroup()
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        			.addComponent(lblPName)
        			.addGap(3)
        			.addComponent(lblNewLabel)
        			.addContainerGap())
        );
        headerPanel.setLayout(gl_headerPanel);
        getContentPane().setLayout(groupLayout);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(""),
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));

    }
    
    private void updateComboBoxUseCases(){
    	
    	useCaseCB.addItem("1 - Random Controler");
    	
    }
    
    private void btnTeste_onClick(java.awt.event.ActionEvent evt)
    {
    	MainFrame frame = new MainFrame();
    	frame.setVisible(true);
    }
}
