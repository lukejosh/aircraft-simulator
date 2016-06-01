/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 * 
 */
package asgn2Simulators;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker.StateValue;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.DynamicTimeSeriesCollection;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


/**
 * @author hogan
 *
 */
@SuppressWarnings("serial")
public class GUISimulator extends JFrame implements ActionListener, Runnable {
	
	public static final int WIDTH =  900;
	public static final int HEIGHT = 900;
	
	public static final String FONT_STYLE =  "Arial";
	public static final int TITLE_FONT_SIZE =  22;
	public static final int TEXT_FONT_SIZE = 16;
	public static final int HEADING_FONT_SIZE = 20;
	public static final int INPUT_FONT_SIZE = 18;
	
	private String[] defaultValues;
	
	private JPanel tabbedPanel;
	private JPanel titlePanel;
	private JPanel inputPanel;
	
	// Display for error messages
	private JTextArea displayText;           
	
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
	private JButton startButton;
	private JButton stopButton;

	JProgressBar progressBar = new JProgressBar();
	
	XYSeriesCollection dataset1;
	XYSeriesCollection dataset2;
	DefaultCategoryDataset dataset3;
	
	public static void main(String[] args) {
		GUISimulator gui = new GUISimulator("Aircraft Simulator", args);
		SwingUtilities.invokeLater(gui);
	}

	public GUISimulator(String title, String[] args) throws HeadlessException {
		super(title);
		final int NUM_ARGS = 10;
		this.defaultValues = args;
		
		if (args.length == NUM_ARGS) {
					this.defaultValues = args;					
		} else {
			this.defaultValues = new String[NUM_ARGS];
			defaultValues[0] = String.valueOf(Constants.DEFAULT_SEED);
			defaultValues[1] = String.valueOf(Constants.DEFAULT_MAX_QUEUE_SIZE);
			defaultValues[2] = String.valueOf(Constants.DEFAULT_DAILY_BOOKING_MEAN);
			defaultValues[3] = String.valueOf(Constants.DEFAULT_DAILY_BOOKING_SD);
			defaultValues[4] = String.valueOf(Constants.DEFAULT_FIRST_PROB);
			defaultValues[5] = String.valueOf(Constants.DEFAULT_BUSINESS_PROB);
			defaultValues[6] = String.valueOf(Constants.DEFAULT_PREMIUM_PROB);
			defaultValues[7] = String.valueOf(Constants.DEFAULT_ECONOMY_PROB);
			defaultValues[8] = String.valueOf(Constants.DEFAULT_CANCELLATION_PROB);
		}
	}

	@Override
	public void run() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		initialiseComponents();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource(); 
		if (source == startButton && validateInputs()) startSimulation();		 
	}
	
	private void initialiseComponents(){	
		this.setSize(WIDTH, HEIGHT); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		this.setLayout(new BorderLayout());
		
		titlePanel = createTitlePanel();
		tabbedPanel = createTabbedPanel();
		inputPanel = createInputPanel();
		
		this.getContentPane().add(titlePanel,BorderLayout.NORTH);
		this.getContentPane().add(tabbedPanel,BorderLayout.CENTER);
		this.getContentPane().add(inputPanel,BorderLayout.SOUTH);
		
		setParametersFromArray(this.defaultValues);
		
		this.pack();
		this.repaint();
		this.setVisible(true);
	}
	
	private void startSimulation()
	{
		
		GUIController task = new GUIController(displayText, dataset1, dataset2, dataset3, getParametersAsArray());
		
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
					 			clearDatasets();
					 			progressBar.setIndeterminate(false);
					 			setUserControlsState(true);
					 			break;
					 		case DONE:
					 			progressBar.setIndeterminate(true);
					 			progressBar.setValue(0);
				 				setUserControlsState(false);
				 				break;
					 		case PENDING:
					 				
							default:
								break;
				          
				          }
						 break;
					}
				}
			});
		
		 task.execute();
	}
	
	private JPanel createTitlePanel(){
		JPanel titlePanel = new JPanel();
		
		JLabel titleLabel = new JLabel(this.getTitle());
		titleLabel.setFont(new Font(FONT_STYLE, Font.BOLD, TITLE_FONT_SIZE));	
		titlePanel.add(titleLabel);
		
		return titlePanel;
		
	}
	
	private JPanel createTabbedPanel(){
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setFont(new Font(FONT_STYLE, Font.PLAIN, HEADING_FONT_SIZE));
		
		// Create the panels for each tab
		JPanel logPanel = createLogPanel();				
		JPanel graph1Panel = createGraph1Panel();
		JPanel graph2Panel = createGraph2Panel();
		JPanel graph3Panel = createGraph3Panel();
        
		// Add the panels to the tabbed pane
        tabbedPane.add("Log", logPanel);
        tabbedPane.add("Graph 1", graph1Panel);
        tabbedPane.add("Graph 2", graph2Panel);
        tabbedPane.add("Graph 3", graph3Panel);
        
        // add the tabbed pane to the main panel
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
				
		return mainPanel;	
	}
	
	private JPanel createInputPanel() {
		JPanel inputPanel = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		inputPanel.setLayout(layout);
		
		JPanel progressPanel = createProgressPanel();
		JPanel parametersPanel = createParametersPanel();
		JPanel buttonsPanel = createButtonsPanel();
				
		GridBagConstraints parameterConstraints = makeConstraints(10);
		parameterConstraints.anchor = GridBagConstraints.WEST;
		parameterConstraints.fill = GridBagConstraints.BOTH;
		addToPanel(inputPanel, parametersPanel, parameterConstraints, 0, 0, 1, 1);
		
		GridBagConstraints progressConstraints = makeConstraints(10);
		progressConstraints.anchor = GridBagConstraints.WEST;
		addToPanel(inputPanel, progressPanel, progressConstraints, 0, 1, 1, 1);
		
		GridBagConstraints buttonsConstraints = makeConstraints(10);
		buttonsConstraints.anchor = GridBagConstraints.EAST;
		addToPanel(inputPanel, buttonsPanel, buttonsConstraints, 0, 1, 1, 1);
				
		return inputPanel;
	}
	
	private JPanel createLogPanel(){
		// Create and add the log info Panel to the parent panel
		JPanel logPanel = new JPanel();
		logPanel.setLayout(new BorderLayout());
		
		//displayText = new JTextArea(15, 40); // lines by columns
		displayText = new JTextArea();
		displayText.setEditable(false);
		displayText.setLineWrap(true);
		displayText.setFont(new Font(FONT_STYLE, Font.PLAIN, TEXT_FONT_SIZE));
		displayText.setText("Set the initial simulation parameters and press 'Start'\n\n");
	
		JScrollPane displayScrollPane = new JScrollPane(displayText);	
		logPanel.add(displayScrollPane, BorderLayout.CENTER);
		
		return logPanel;
	}
	
	private JPanel createGraph1Panel(){
		JPanel graphPanel = new JPanel();
		graphPanel.setLayout(new BorderLayout());
        
        dataset1 = new XYSeriesCollection();
        dataset1.addSeries(new XYSeries("First"));
        dataset1.addSeries(new XYSeries("Business"));
        dataset1.addSeries(new XYSeries("Premium"));
        dataset1.addSeries(new XYSeries("Economy"));
        dataset1.addSeries(new XYSeries("Total"));
        dataset1.addSeries(new XYSeries("Empty"));
        
        JFreeChart chart = createChart(dataset1, "Graph1", "Days", "Number of Passengers");
        graphPanel.add(new ChartPanel(chart), BorderLayout.CENTER);
        
        return graphPanel;
	}
	
	private JPanel createGraph2Panel(){
		JPanel graphPanel = new JPanel();
		graphPanel.setLayout(new BorderLayout());
        
        dataset2 = new XYSeriesCollection();
        dataset2.addSeries(new XYSeries("Queued"));
        dataset2.addSeries(new XYSeries("Refused"));
        
        JFreeChart chart = createChart(dataset2, "Graph2", "Days", "Number of Passengers");
        graphPanel.add(new ChartPanel(chart), BorderLayout.CENTER);
        
        return graphPanel;
	}
	
	private JPanel createGraph3Panel(){
		JPanel graphPanel = new JPanel();
		graphPanel.setLayout(new BorderLayout());
        
        dataset3 = new DefaultCategoryDataset();
        dataset3.addValue(0.0, "First", "Capacity");
        dataset3.addValue(0.0, "First", "Queued");
        dataset3.addValue(0.0, "First", "Refused");        
        JFreeChart chart = ChartFactory.createBarChart("Graph 3", "Type", "Nunber of passengers", dataset3);
        chart.removeLegend();
        graphPanel.add(new ChartPanel(chart), BorderLayout.CENTER);
        
        return graphPanel;
	}
	
	private JPanel createProgressPanel(){
		JPanel progressPanel = new JPanel();
		
		progressPanel.add(progressBar);
		progressBar.setIndeterminate(true);
		progressBar.setStringPainted(true);
		
		JLabel progressLabel = new JLabel("Simulation Progress");
		progressLabel.setFont(new Font(FONT_STYLE, Font.PLAIN, HEADING_FONT_SIZE));	
		progressPanel.add(progressLabel);
		
		return progressPanel;
	}
	
	private JPanel createParametersPanel(){
		JPanel parametersPanel = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		parametersPanel.setLayout(layout);
		
		seedText = addParameterToPanel(parametersPanel, "Random number seed:", 0, 0, 0);       
		maxQueueSizeText = addParameterToPanel(parametersPanel,"Maximum queue size:", Constants.DEFAULT_MAX_QUEUE_SIZE, 0, 1);
		meanBookingsText = addParameterToPanel(parametersPanel,"Mean number of daily bookings:", Constants.DEFAULT_DAILY_BOOKING_MEAN, 0, 2);
		sdBookingsText = addParameterToPanel(parametersPanel,"SD daily bookings:", Constants.DEFAULT_DAILY_BOOKING_SD, 0, 3);       
		firstProbText = addParameterToPanel(parametersPanel,"First class probability:", Constants.DEFAULT_FIRST_PROB, 3, 0);
		businessProbText = addParameterToPanel(parametersPanel,"Business class probability:", Constants.DEFAULT_BUSINESS_PROB, 3, 1);     
		premiumProbText = addParameterToPanel(parametersPanel,"Premium class probability:", Constants.DEFAULT_PREMIUM_PROB, 3, 2);       
		economyProbText = addParameterToPanel(parametersPanel,"Economy class probability:", Constants.DEFAULT_ECONOMY_PROB, 3, 3);
		cancelProbText = addParameterToPanel(parametersPanel,"Cancellation probability:", Constants.DEFAULT_CANCELLATION_PROB, 3, 4);
		
		return parametersPanel;
	}
	
	private JPanel createButtonsPanel(){		
		JPanel buttonsPanel = new JPanel();
		
		// Button for starting the simulation
		startButton = new JButton("Start");
		startButton.addActionListener(this);
		startButton.setFont(new Font(FONT_STYLE, Font.PLAIN, HEADING_FONT_SIZE));
		buttonsPanel.add(startButton, makeConstraints(2));
		
		return buttonsPanel;
	}
	
	private JFreeChart createChart(final XYDataset dataset, String title, String xLabel, String yLabel) {
        final JFreeChart result = ChartFactory.createXYLineChart(
        		title, xLabel, yLabel, dataset);
        final XYPlot plot = result.getXYPlot();
        ValueAxis domain = plot.getDomainAxis();
        domain.setAutoRange(true);
        ValueAxis range = plot.getRangeAxis();
        range.setAutoRange(true);
        return result;
    }
	
	private GridBagConstraints makeConstraints(int inset) {
		//final Integer inset = 20; // pixels from edge of main frame
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.weightx = 100;
		constraints.weighty = 100;
		constraints.insets = new Insets(inset, inset, inset, inset);
		return constraints;
	}
	
	private JTextField addParameterToPanel(JPanel panel, String label, Number defaultValue, int x, int y) {
		int w = 1, h = 1;
		
		JTextField parameterText = new JTextField("" + defaultValue, defaultValue.toString().length());		
		parameterText.setFont(new Font(FONT_STYLE, Font.PLAIN, INPUT_FONT_SIZE));
		parameterText.setEditable(true);
		parameterText.setHorizontalAlignment(JTextField.RIGHT); // flush left
		
		JLabel parameterLabel = new JLabel(label);
		parameterLabel.setFont(new Font(FONT_STYLE, Font.PLAIN, INPUT_FONT_SIZE));
		parameterLabel.setHorizontalAlignment(JTextField.LEFT); // flush right
		
		GridBagConstraints labelConstraints = makeConstraints(10);
		labelConstraints.fill = GridBagConstraints.BOTH;
		
		addToPanel(panel, parameterLabel, labelConstraints, x,y,w,h);
		addToPanel(panel, parameterText, labelConstraints, x+1,y,w,h);
		
		return parameterText;
	}
	
	private void addToPanel(JPanel jp,Component c, GridBagConstraints constraints,int x, int y, int w, int h) {
		constraints.gridx = x; 
		constraints.gridy = y; 
		constraints.gridwidth = w; 
		constraints.gridheight = h; 
		jp.add(c, constraints);
	}
	
	private void clearDatasets(){
		dataset1.getSeries(0).clear();
		dataset1.getSeries(1).clear();
		dataset1.getSeries(2).clear();
		dataset1.getSeries(3).clear();
		dataset1.getSeries(4).clear();
		dataset1.getSeries(5).clear();
		
		dataset2.getSeries(0).clear();
		dataset2.getSeries(1).clear();
		
		dataset3.clear();
	}
	
	private void setParametersFromArray(String[] args) {
		seedText.setText(args[0]);
		maxQueueSizeText.setText(args[1]);
		meanBookingsText.setText(args[2]);
		sdBookingsText.setText(args[3]);
		firstProbText.setText(args[4]);
		businessProbText.setText(args[5]);
		premiumProbText.setText(args[6]);
		economyProbText.setText(args[7]);
		cancelProbText.setText(args[8]);
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
	
	private boolean validateInputs(){
		boolean validated = true;
		String[] inputs = getParametersAsArray();
		
		int seed = Integer.parseInt(inputs[0]);
		if(seed < 0){
			validated = false;
			JOptionPane.showMessageDialog(this,"Seed must be greater than 0","Input Invalid",JOptionPane.WARNING_MESSAGE);	
		}
		
		int maxQueueSize = Integer.parseInt(inputs[1]);
		if(maxQueueSize < 0){
			validated = false;
			JOptionPane.showMessageDialog(this,"Max Queue Size must be greater than 0","Input Invalid",JOptionPane.WARNING_MESSAGE);	
		}
		
		double meanBookings = Double.parseDouble(inputs[2]);
		if(meanBookings < 0.0){
			validated = false;
			JOptionPane.showMessageDialog(this,"Mean Bookings must be greater than 0","Input Invalid",JOptionPane.WARNING_MESSAGE);	
		}
		
		double sdBookings = Double.parseDouble(inputs[3]);
		if(sdBookings < 0.0){
			validated = false;
			JOptionPane.showMessageDialog(this,"Standard Deviation Bookings must be greater than 0","Input Invalid",JOptionPane.WARNING_MESSAGE);	
		}
		
		double firstProb = Double.parseDouble(inputs[4]);
		double businessProb = Double.parseDouble(inputs[5]);
		double premiumProb = Double.parseDouble(inputs[6]);
		double economyProb = Double.parseDouble(inputs[7]);
		if((firstProb+businessProb+premiumProb+economyProb) != 1.0){
			validated = false;
			JOptionPane.showMessageDialog(this,"Passenger probabilities must add up to 1","Input Invalid",JOptionPane.WARNING_MESSAGE);	
		}
		
		double cancelProb = Double.parseDouble(inputs[8]);
		if(cancelProb > 1.0){
			validated = false;
			JOptionPane.showMessageDialog(this,"Cancel probability must not be greater than 1","Input Invalid",JOptionPane.WARNING_MESSAGE);	
		}
			
		return validated;
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
}
