/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 * 
 */
package asgn2Simulators;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * @author hogan
 *
 */
@SuppressWarnings("serial")
public class GUISimulator extends JFrame implements ActionListener, Runnable {
	
	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;
	
	private int logFontSize = 16;
	private int btnFontSize = 24;
	
	private JPanel pnlTitle;
	private JPanel pnlLogDisplay;
	private JPanel pnlConstraints;
	
	private JLabel lblTitle;
	private JLabel lblSeed;
	private JLabel lblFirstProb;
	
	private JTextField txtSeed;
	private JTextField txtFirstProb;
	
	private JButton btnRun;
	private JButton btnLoad;
	private JButton btnUnload;
	private JButton btnFind;
	private JButton btnSwitch;
	
	private JTextArea txtLogOutput;

	public GUISimulator(String title) throws HeadlessException {
		super(title); // This just sets the title of the JFrame
	}

	@Override
	public void run() {
		createGUI(); // Create GUI just adds the GUI components to the JFrame
	}

	public static void main(String[] args) {
		// Sets the default look and feel of the JFrame
		JFrame.setDefaultLookAndFeelDecorated(true);
		
		// Invokes the the JFrame and allows main() to finish
		SwingUtilities.invokeLater(new GUISimulator("Aircraft Simulator"));
	}
	
	private void createGUI() { 
		setSize(WIDTH, HEIGHT); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setLayout(new BorderLayout());

		// Create the component containers
		pnlTitle = createPanel(Color.LIGHT_GRAY);
		pnlLogDisplay = createPanel(Color.LIGHT_GRAY);
		pnlConstraints = createPanel(Color.LIGHT_GRAY);
		
		// Add the containers to the JFrame
		this.getContentPane().add(pnlTitle,BorderLayout.NORTH);
		this.getContentPane().add(pnlLogDisplay,BorderLayout.CENTER);
		this.getContentPane().add(pnlConstraints,BorderLayout.SOUTH);
		
		createTitlePanel(pnlTitle);
		
		// Add Constraints labels and text fields to the panel
		createConstraintsPanel(pnlConstraints);
		
		createLogPanel(pnlLogDisplay);
		
		
		
		this.repaint();
		this.setVisible(true);	
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//Get event source
		Object src=arg0.getSource();
		//Consider the alternatives - not all active at once.
		if(src==btnRun){
				JButton btn = ((JButton) src);
				txtLogOutput.setText(btn.getText().trim());
				//JOptionPane.showMessageDialog(this,"A Stupid Warning Message","Wiring Class: Warning",JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private JPanel createPanel(Color c) {
		
		//Create a JPanel object and store it in a local var
		JPanel panel = new JPanel();
		//set the background colour to that passed in c
		panel.setBackground(c);
		//Return the JPanel object	
		return panel;
	}
	
	private void createTitlePanel(JPanel panel){	
		GridBagLayout layout = new GridBagLayout();
		panel.setLayout(layout);
		
		this.lblTitle = createLabel("Aircraft Simulator", JLabel.CENTER);
		
		GridBagConstraints constraints = getDefaultConstraints();
		
		addComponentToPanel(panel, this.lblTitle, constraints, 0, 0, 1, 1);
	}
	
	private void createConstraintsPanel(JPanel panel) {
		// Add a grid layout to the panel
		GridBagLayout layout = new GridBagLayout();
		panel.setLayout(layout);
		
		//Create a constraint to be used as a default for all components
		GridBagConstraints constraints = getDefaultConstraints();
		
		this.btnRun = createButton("Run Simulation");
		addComponentToPanel(panel, this.btnRun,constraints,1,10,1,1); 
		
		// Repeat this for all other constraints
		// Seed input
		this.lblSeed = createLabel("Seed:", JLabel.RIGHT);
		this.txtSeed = createIntTextField(Constants.DEFAULT_SEED, 5);
		addComponentToPanel(panel, this.lblSeed,constraints,0,0,1,1); 
		addComponentToPanel(panel, this.txtSeed,constraints,1,0,1,1);
		
		// First class probability input
		this.lblFirstProb = createLabel("First Class Probability:", JLabel.RIGHT);
		this.txtFirstProb = createDoubleTextField(Constants.DEFAULT_FIRST_PROB, 5);
		addComponentToPanel(panel, this.lblFirstProb,constraints,0,1,1,1); 
		addComponentToPanel(panel, this.txtFirstProb,constraints,1,1,1,1);
	}
	
	private void createLogPanel(JPanel panel){	
		GridBagLayout layout = new GridBagLayout();
		panel.setLayout(layout);
		
		this.txtLogOutput = createTextArea();
		
		GridBagConstraints constraints = getDefaultConstraints();
		
		addComponentToPanel(panel, txtLogOutput, constraints, 0, 0, 1, 1);
	}
	
	/* Simple function to add a component to a panel */
	
	private void addComponentToPanel(JPanel jp,Component c, GridBagConstraints constraints,int x, int y, int w, int h) {
		constraints.gridx = x; 
		constraints.gridy = y; 
		constraints.gridwidth = w; 
		constraints.gridheight = h; 
		jp.add(c, constraints);
	}
	
	/* Simple function to get a default constraints object */
	
	private GridBagConstraints getDefaultConstraints() {		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.WEST;	
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.weightx = 100;
		constraints.weighty = 100;
		
		return constraints;
	}
	
	/* The following functions are used to create standard swing components */
	
	private JButton createButton(String str) {
		//Create a JButton object and store it in a local var
		JButton btn = new JButton();
		//Set the button text to that passed in str
		btn.setText(str);
		btn.setFont(new Font("Arial",Font.BOLD,btnFontSize));
		//Add the frame as an actionListener
		btn.addActionListener(this);
		//Return the JButton object
		return btn;
	}
	
	private JLabel createLabel(String labelText, int alignment) {
		//Create a JLabel object and store it in a local var
		JLabel lbl = new JLabel(labelText);
		// Set the alignment of the text
		lbl.setHorizontalAlignment(alignment);
		lbl.setFont(new Font("Arial",Font.BOLD,btnFontSize));
		//Return the JLabel object
		return lbl; 
	}
	
	private JFormattedTextField createDoubleTextField(Double value, int numColumns) {
		//Create a JTextField object and store it in a local var
		JFormattedTextField txt = new JFormattedTextField(DecimalFormat.getPercentInstance());
		// Add the value to the Formatted text field
		txt.setValue(value);
		txt.setFont(new Font("Arial",Font.BOLD,btnFontSize));
		//Add the frame as an actionListener
		txt.addActionListener(this);
		//Return the JTextField object
		return txt; 
	}
	
	private JFormattedTextField createIntTextField(int value, int numColumns) {
		//Create a JTextField object and store it in a local var
		JFormattedTextField txt = new JFormattedTextField(NumberFormat.getInstance());
		// Add the value to the Formatted text field
		txt.setValue(value);
		txt.setFont(new Font("Arial",Font.BOLD,btnFontSize));
		//Add the frame as an actionListener
		txt.addActionListener(this);
		//Return the JTextField object
		return txt; 
	}
	
	private JTextArea createTextArea() {		
		JTextArea textArea = new JTextArea(); 
		textArea.setEditable(false); 
		textArea.setLineWrap(true);
		textArea.setFont(new Font("Arial",Font.BOLD,logFontSize));
		textArea.setBorder(BorderFactory.createEtchedBorder());
		
		return textArea;
	}
	
	

	

}
