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
<<<<<<< HEAD
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;
=======
import java.text.DecimalFormat;
import java.text.NumberFormat;
>>>>>>> 78f1f25325cf95d119fc550755d1d754d95d1707

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
<<<<<<< HEAD
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker.StateValue;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.DynamicTimeSeriesCollection;
import org.jfree.data.time.Second;
import org.jfree.data.xy.XYDataset;

import asgn2Aircraft.AircraftException;
import asgn2Passengers.PassengerException;
=======
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
>>>>>>> 78f1f25325cf95d119fc550755d1d754d95d1707

/**
 * @author hogan
 *
 */
@SuppressWarnings("serial")
public class GUISimulator extends JFrame implements ActionListener, Runnable {
	
<<<<<<< HEAD
	private static final Random random = new Random();
	
	public static final int WIDTH =  900;
	public static final int HEIGHT = 900;
	
	private JPanel mainPanel;
	private JPanel titlePanel;
	private JPanel tabbedPanel;
	private JPanel parametersPanel;
	
	// Display for error messages
	private JTextArea displayText;           
	private JScrollPane displayScrollPane;
	
	// Entry fields for simulation parameters
	private JTextField seedText;       
	private JTextField maxQueueSizeText;
	private JTextField meanBookingsText;
	private JTextField sdBookingsText;       
	private JTextField firstProbText;
	private JTextField businessProbText;       
	private JTextField premiumProbText;       
	private JTextField economyProbText;
	private JTextField cancelProbText;
	
	// Buttons
	private JPanel buttonsPanel;
	private JButton startButton;
	private JButton stopButton;

	// Progress bar for simulation progress
	private JPanel progressPanel;
	JProgressBar progressBar = new JProgressBar();
	
	DynamicTimeSeriesCollection dataset;

	// Places where we'll add components to the frame
	private enum Position {TOP, MIDDLE, BOTTOM};

	// Simulation state
	private Simulator sim;

	public GUISimulator(String title) throws HeadlessException {
		super(title); // This just sets the title of the JFrame
	}

	public static void main(String[] args) {
		GUISimulator gui = new GUISimulator("Aircraft Simulator");
		SwingUtilities.invokeLater(gui);
=======
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
>>>>>>> 78f1f25325cf95d119fc550755d1d754d95d1707
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
	
	

	

	@Override
	public void run() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		initialiseComponents();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource(); 
		if (source == startButton) startSimulation();		 
	}
	
	private void initialiseComponents(){
		
		this.setSize(WIDTH, HEIGHT); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		this.setLayout(new BorderLayout());
		
		mainPanel = new JPanel();
		titlePanel = new JPanel();
		tabbedPanel = new JPanel();
		parametersPanel = new JPanel();
		
		// Choose a flexible grid layout for the main frame
		GridBagLayout layout = new GridBagLayout();
		mainPanel.setLayout(layout);
		
		// JFreeChart
		dataset = new DynamicTimeSeriesCollection(1, Constants.DURATION, new Second());
        dataset.setTimeBase(new Second(0, 0, 0, 1, 1, 2011));
        dataset.addSeries(gaussianData(), 0, "Human");
        JFreeChart chart = createChart(dataset);
        mainPanel.add(new ChartPanel(chart), makeConstraints(Position.TOP));
		
		// Text area for displaying instructions and error messages
		displayText = new JTextArea(15, 40); // lines by columns
		displayText.setEditable(false);
		displayText.setLineWrap(true);
		displayScrollPane = new JScrollPane(displayText);
		mainPanel.add(displayScrollPane, makeConstraints(Position.TOP));
		resetDisplay("Set the initial simulation parameters and press 'Start'\n\n");
		
		// Progress bar for displaying current stock level (initially indeterminate)
		progressPanel = new JPanel();
		JLabel progressLabel = new JLabel("Simulation Progress");
		progressPanel.add(progressLabel);
		progressBar.setIndeterminate(true);
		progressBar.setStringPainted(true);
		progressPanel.add(progressBar);
		mainPanel.add(progressPanel, makeConstraints(Position.MIDDLE));
		
		// Add editable panels for simulation parameters
		seedText = addParameterPanel("Random number seed:", Constants.DEFAULT_SEED);       
		maxQueueSizeText = addParameterPanel("Maximum queue size:", Constants.DEFAULT_MAX_QUEUE_SIZE);
		meanBookingsText = addParameterPanel("Mean number of daily bookings:", Constants.DEFAULT_DAILY_BOOKING_MEAN);
		sdBookingsText = addParameterPanel("SD daily bookings:", Constants.DEFAULT_DAILY_BOOKING_SD);       
		firstProbText = addParameterPanel("First class probability:", Constants.DEFAULT_FIRST_PROB);
		businessProbText = addParameterPanel("Business class probability:", Constants.DEFAULT_BUSINESS_PROB);     
		premiumProbText = addParameterPanel("Premium class probability:", Constants.DEFAULT_PREMIUM_PROB);       
		economyProbText = addParameterPanel("Economy class probability:", Constants.DEFAULT_ECONOMY_PROB);
		cancelProbText = addParameterPanel("Cancellation probability:", Constants.DEFAULT_CANCELLATION_PROB);
		
		// Panel for buttons
		buttonsPanel = new JPanel();
		mainPanel.add(buttonsPanel, makeConstraints(Position.BOTTOM));
		buttonsPanel.setVisible(true);
		
		// Button for starting the simulation
		startButton = new JButton("Start");
		startButton.addActionListener(this);
		mainPanel.add(startButton, makeConstraints(Position.BOTTOM));
		buttonsPanel.add(startButton);
		
		this.getContentPane().add(mainPanel,BorderLayout.CENTER);
		this.pack();
		this.repaint();
		this.setVisible(true);
	}
	
	private float[] gaussianData() {
        float[] a = new float[1];
        for (int i = 0; i < a.length; i++) {
            a[i] = (float) 0.0;
        }
        return a;
    }
	
	private JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart result = ChartFactory.createTimeSeriesChart(
            "Title of the chart", "hh:mm:ss", "milliVolts", dataset, true, true, false);
        final XYPlot plot = result.getXYPlot();
        ValueAxis domain = plot.getDomainAxis();
        domain.setAutoRange(true);
        ValueAxis range = plot.getRangeAxis();
        range.setAutoRange(true);
        return result;
    }
	
	private void setUserControlsState(Boolean isRunning){
		seedText.setEnabled(!isRunning);
		maxQueueSizeText.setEnabled(!isRunning);
		meanBookingsText.setEnabled(!isRunning);
		sdBookingsText.setEnabled(!isRunning);       
		firstProbText.setEnabled(!isRunning);
		businessProbText.setEnabled(!isRunning);     
		premiumProbText.setEnabled(!isRunning);       
		economyProbText.setEnabled(!isRunning);
		cancelProbText.setEnabled(!isRunning);    
		startButton.setEnabled(!isRunning);
	}
	
	private void startSimulation()
	{
		
		GUIController task = new GUIController(displayText, dataset, getParametersAsArray());
		
		task.addPropertyChangeListener(
			new PropertyChangeListener() {
				public  void propertyChange(PropertyChangeEvent event) {
					switch (event.getPropertyName()) {
					case "progress":
					    progressBar.setValue((Integer)event.getNewValue());
					    break;
					 case "state":
						 switch ((StateValue) event.getNewValue()) {
					 		case STARTED:
					 			resetDisplay("Simulation has started");
					 			progressBar.setIndeterminate(false);
					 			setUserControlsState(true);
					 			break;
					 		case DONE:
					 			appendDisplay("Simulation has ended");
					 			progressBar.setIndeterminate(false);
				 				setUserControlsState(false);
				 				break;
							default:
								break;
				          
				          }
						 break;
					}
				}
			});
		
		 task.execute();
	}
	
	private void resetDisplay(String initialText) {
		displayText.setText(initialText);
	}
	
	private void appendDisplay(String newText) {
		displayText.setText(displayText.getText() + newText);
	}
	
	private GridBagConstraints makeConstraints(Position location) {
		final Integer inset = 20; // pixels from edge of main frame
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST; // fix component on the left margin
		constraints.gridwidth = GridBagConstraints.REMAINDER; // component occupies whole row
		switch (location) {
		case TOP:
			constraints.weightx = 100; // give leftover horizontal space to this object
			constraints.weighty = 0; // no extra vertical space for this object
			constraints.insets = new Insets(inset, inset, 0, inset); // top, left, bottom, right
			break;
		case MIDDLE:
			constraints.weightx = 100; // give leftover horizontal space to this object
			constraints.weighty = 0; // no extra vertical space for this object
			constraints.insets = new Insets(0, inset, 0, inset); // top, left, bottom, right
			break;
		case BOTTOM:
			constraints.weightx = 100; // give leftover horizontal space to this object
			constraints.weighty = 0; // no extra vertical space for this object
			constraints.insets = new Insets(0, inset, inset, inset); // top, left, bottom, right
			break;
		}
		return constraints;
	}
	
	private JTextField addParameterPanel(String label, Number defaultValue) {
		// A parameter panel has two components, a label and a text field
		JPanel parameterPanel = new JPanel();
		JLabel parameterLabel = new JLabel(label);
		JTextField parameterText = new JTextField("" + defaultValue, defaultValue.toString().length());
		// Add the label to the parameter panel
		parameterLabel.setHorizontalAlignment(JTextField.RIGHT); // flush right
		parameterPanel.add(parameterLabel);
		// Add the text field
		parameterText.setEditable(true);
		parameterText.setHorizontalAlignment(JTextField.RIGHT); // flush right
		parameterPanel.add(parameterText);
		// Add the parameter panel to the main frame
		mainPanel.add(parameterPanel, makeConstraints(Position.MIDDLE));
		// Return the newly-created text field
		return parameterText;
	}
	
	private String[] getParametersAsArray(){
		String[] array = new String[9];
		
		array[0] = seedText.getText().trim();
		array[1] = maxQueueSizeText.getText().trim();
		array[2] = meanBookingsText.getText().trim();
		array[3] = sdBookingsText.getText().trim();
		array[4] = firstProbText.getText().trim();
		array[5] = businessProbText.getText().trim();
		array[6] = premiumProbText.getText().trim();
		array[7] = economyProbText.getText().trim();
		array[8] = cancelProbText.getText().trim();
		
		return array;
	}
	
}
