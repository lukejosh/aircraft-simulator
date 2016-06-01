package asgn2Simulators;

import java.util.List;

import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import org.jfree.data.time.DynamicTimeSeriesCollection;

public class GUIController extends SwingWorker<String, GUIModel> {
	
	private Simulator sim;
	private JTextArea textArea;
	
	DynamicTimeSeriesCollection dataset;

	GUIController(JTextArea textArea, DynamicTimeSeriesCollection dataset, String[] args) {
		this.textArea = textArea;
		this.dataset = dataset;
		try {
			this.sim = createSimulatorUsingArgs(args);
		} catch (SimulationException e) {
			e.printStackTrace();
		}
    }

	@Override
	protected String doInBackground() throws Exception {
		GUIModel model = new GUIModel();
			
		try {				
			this.sim.createSchedule();
			
			//Main simulation loop 
			for (int time=0; time<=Constants.DURATION; time++) {
				model.clearValues();
				model.setTime(time);
				this.sim.resetStatus(time); 
				this.sim.rebookCancelledPassengers(time); 
				this.sim.generateAndHandleBookings(time);
				this.sim.processNewCancellations(time);
				if (time >= Constants.FIRST_FLIGHT) {
					this.sim.processUpgrades(time);
					this.sim.processQueue(time);
					this.sim.flyPassengers(time);
					this.sim.updateTotalCounts(time); 
					model.appendText(sim.getFlights(time).getStatus(time));
				} else {
					this.sim.processQueue(time);
				}
				
				//Log progress 
				model.appendText(sim.getStatus(time));
				model.appendText(sim.getSummary(time, (time >= Constants.FIRST_FLIGHT)));
				model.setNumPassengers(sim.getTotalFlown());
				
				publish(model);	
				
				Thread.sleep(250);
				setProgress((time) * 100 / Constants.DURATION);				
			}
			
			this.sim.finaliseQueuedAndCancelledPassengers(Constants.DURATION); 
			
			model.appendText(sim.getStatus(Constants.DURATION));
			model.appendText("End of simulation\n");
			model.appendText(sim.finalState());			
			model.appendText("-----\n");
			
		} catch (SimulationException e) {
			model.appendText("Simulation failure: " + e.toString() + "\n");
		} catch (Exception e) {
			model.appendText("Unexpected exception: " + e.toString() + "\n");
		}
		
		publish(model);
		return null;
	}
	
	@Override
	  protected void process(final List<GUIModel> chunks) {
	    // Updates the messages text area
	    for (final GUIModel model : chunks) {
	    	textArea.setText(textArea.getText() + model.getText());
	    	dataset.addValue(0, model.getTime(), model.getNumPassengers());
	    }
	    
	  }
	
	private static Simulator createSimulatorUsingArgs(String[] args) throws SimulationException {
		int seed = Integer.parseInt(args[0]);
		int maxQueueSize = Integer.parseInt(args[1]);
		double meanBookings = Double.parseDouble(args[2]);
		double sdBookings = Double.parseDouble(args[3]);
		double firstProb = Double.parseDouble(args[4]);
		double businessProb = Double.parseDouble(args[5]);
		double premiumProb = Double.parseDouble(args[6]);
		double economyProb = Double.parseDouble(args[7]);
		double cancelProb = Double.parseDouble(args[8]);
		
		return new Simulator(seed,maxQueueSize,meanBookings,sdBookings,firstProb,businessProb,
						  premiumProb,economyProb,cancelProb);	
	}

}
