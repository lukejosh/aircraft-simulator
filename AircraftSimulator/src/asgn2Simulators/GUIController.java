package asgn2Simulators;

import java.util.List;

import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.DynamicTimeSeriesCollection;
import org.jfree.data.xy.XYSeriesCollection;

public class GUIController extends SwingWorker<String, GUIModel> {
	
	private Simulator sim;
	private JTextArea textArea;
	
	XYSeriesCollection dataset1;
	XYSeriesCollection dataset2;
	DefaultCategoryDataset dataset3;

	GUIController(JTextArea textArea, XYSeriesCollection d1, XYSeriesCollection d2, DefaultCategoryDataset d3, String[] args) {
		this.textArea = textArea;
		this.dataset1 = d1;
		this.dataset2 = d2;
		this.dataset3 = d3;
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
			
			model.appendText("Start of Simulation\n");
			model.appendText(sim.toString() + "\n");
			model.appendText(sim.getFlights(Constants.FIRST_FLIGHT).initialState());
			
			
			//Main simulation loop 
			for (int time=0; time<=Constants.DURATION; time++) {
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
					//model.appendText(sim.getFlights(time).getStatus(time));
				} else {
					this.sim.processQueue(time);
				}
				
				//Log progress 
				//model.appendText(sim.getStatus(time));
				model.appendText(sim.getSummary(time, (time >= Constants.FIRST_FLIGHT)));
				
				model = addDataToModel(model, sim, time, (time >= Constants.FIRST_FLIGHT));
				
				publish(model);	
				
				Thread.sleep(250);
				setProgress((time) * 100 / Constants.DURATION);				
			}
			
			this.sim.finaliseQueuedAndCancelledPassengers(Constants.DURATION); 
			
			//model.appendText(sim.getStatus(Constants.DURATION));
			model.appendText("End of simulation\n");
			model.appendText(sim.finalState());			

			
		} catch (SimulationException e) {
			model.appendText("Simulation failure: " + e.toString() + "\n");
		} catch (Exception e) {
			model.appendText("Unexpected exception: " + e.toString() + "\n");
		}
		
		publish(model);
		return null;
	}
	
	private GUIModel addDataToModel(GUIModel model, Simulator simulator, int time, boolean isflying){
		
		try {
			if (isflying){
				model.setFirst(simulator.getFlightStatus(time).getNumFirst());		
				model.setBusiness(simulator.getFlightStatus(time).getNumBusiness());
				model.setPremium(simulator.getFlightStatus(time).getNumBusiness());
				model.setEconomy(simulator.getFlightStatus(time).getNumEconomy());
				model.setTotal(simulator.getFlightStatus(time).getTotal());
				model.setEmpty(simulator.getFlightStatus(time).getAvailable());
			}
			
			model.setQueued(simulator.numInQueue());
			model.setRefused(simulator.numRefused());
			
		} catch (SimulationException e) {
			e.printStackTrace();
		}
		
		return model;
	}
	
	@Override
	  protected void process(final List<GUIModel> chunks) {
	    // Updates the messages text area
	    for (final GUIModel model : chunks) {
	    	textArea.setText(model.getText());
	    	
	    	dataset1.getSeries(0).add(model.getTime(), model.getFirst());
	    	dataset1.getSeries(1).add(model.getTime(), model.getBusiness());
	    	dataset1.getSeries(2).add(model.getTime(), model.getPremium());
	    	dataset1.getSeries(3).add(model.getTime(), model.getEconomy());
	    	dataset1.getSeries(4).add(model.getTime(), model.getTotal());
	    	dataset1.getSeries(5).add(model.getTime(), model.getEmpty());
	    	
	    	dataset2.getSeries(0).add(model.getTime(), model.getQueued());
	    	dataset2.getSeries(1).add(model.getTime(), model.getRefused());
	    	
	    	dataset3.addValue(model.getCapacity(), "First", "Capacity");
	        dataset3.addValue(model.getTotalQueued(), "First", "Queued");
	        dataset3.addValue(model.getRefused(), "First", "Refused");
	       
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
