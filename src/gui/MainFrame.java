package gui;

import pursuitDomain.PredatorIndividual;
import pursuitDomain.Problem;
import pursuitDomain.AdHocProblem;
import pursuitDomain.GeneticAProblem;
import pursuitDomain.TestCase;
import pursuitDomain.PursuitDomainExperimentsFactory;

import pursuitDomain.RandomProblem;
import experiments.Experiment;
import experiments.ExperimentEvent;
import ga.GAEvent;
import ga.GAListener;
import ga.GeneticAlgorithm;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.omg.CORBA.Environment;

public class MainFrame extends JFrame implements GAListener {

	private static final long serialVersionUID = 1L;

	private GeneticAlgorithm<PredatorIndividual, GeneticAProblem> ga;
	private PredatorIndividual bestInRun;
	private PursuitDomainExperimentsFactory experimentsFactory;
	private PanelTextArea problemPanel;
	PanelTextArea bestIndividualPanel;

	private PanelParameters panelParameters = new PanelParameters(this);
	private JButton buttonDataSet = new JButton("Data set");
	private JButton buttonRun = new JButton("Run");
	private JButton buttonStop = new JButton("Stop");
	private JButton buttonExperiments = new JButton("Experiments");
	private JButton buttonRunExperiments = new JButton("Run experiments");
	private JTextField textFieldExperimentsStatus = new JTextField("", 10);

	private XYSeries seriesBestIndividual;
	private XYSeries seriesAverage;
	private SwingWorker<Void, Void> worker;

	private Problem problem;
	private MainFrame instance;
	private PanelSimulation simulationPanel;
	private TestCase testCase = TestCase.getInstace();
	
	//Statistics Atributes
	private boolean buttonDataSetSelected=false;

	public MainFrame() {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	private void jbInit() throws Exception {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Pursuit Domain");

		// North Left Panel
		JPanel panelNorthLeft = new JPanel(new BorderLayout());
		panelNorthLeft.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(""),
				BorderFactory.createEmptyBorder(1, 1, 1, 1)));

		panelNorthLeft.add(panelParameters, java.awt.BorderLayout.WEST);
		JPanel panelButtons = new JPanel();
		panelButtons.add(buttonDataSet);
		buttonDataSet.addActionListener(new ButtonDataSet_actionAdapter(this));
		panelButtons.add(buttonRun);
		// porque esatira desligado? buttonRun.setEnabled(true);
		buttonRun.addActionListener(new ButtonRun_actionAdapter(this));
		panelButtons.add(buttonStop);
		buttonStop.setEnabled(false);
		buttonStop.addActionListener(new ButtonStop_actionAdapter(this));
		panelNorthLeft.add(panelButtons, java.awt.BorderLayout.SOUTH);

		// North Right Panel - Chart creation
		seriesBestIndividual = new XYSeries("Best");
		seriesAverage = new XYSeries("Average");

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(seriesBestIndividual);
		dataset.addSeries(seriesAverage);
		JFreeChart chart = ChartFactory.createXYLineChart("Evolution", // Title
				"generation", // x-axis Label
				"fitness", // y-axis Label
				dataset, // Dataset
				PlotOrientation.VERTICAL, // Plot Orientation
				true, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
		);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(""),
				BorderFactory.createEmptyBorder(1, 1, 1, 1)));
		// default size
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));

		// North Panel
		JPanel northPanel = new JPanel(new BorderLayout());
		northPanel.add(panelNorthLeft, java.awt.BorderLayout.WEST);
		northPanel.add(chartPanel, java.awt.BorderLayout.CENTER);

		// Center panel
		problemPanel = new PanelTextArea("Problem data: ", 20, 40);
		bestIndividualPanel = new PanelTextArea("Best solution: ", 20, 40);
		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.add(problemPanel, java.awt.BorderLayout.WEST);
		centerPanel.add(bestIndividualPanel, java.awt.BorderLayout.CENTER);

		// South Panel
		JPanel southPanel = new JPanel();
		southPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(""),
				BorderFactory.createEmptyBorder(1, 1, 1, 1)));

		southPanel.add(buttonExperiments);
		buttonExperiments.addActionListener(new ButtonExperiments_actionAdapter(this));
		southPanel.add(buttonRunExperiments);
		buttonRunExperiments.setEnabled(false);
		buttonRunExperiments.addActionListener(new ButtonRunExperiments_actionAdapter(this));
		southPanel.add(new JLabel("Status: "));
		southPanel.add(textFieldExperimentsStatus);
		textFieldExperimentsStatus.setEditable(false);

		// Big left panel
		JPanel bigLeftPanel = new JPanel(new BorderLayout());
		bigLeftPanel.add(northPanel, java.awt.BorderLayout.NORTH);
		bigLeftPanel.add(centerPanel, java.awt.BorderLayout.CENTER);
		bigLeftPanel.add(southPanel, java.awt.BorderLayout.SOUTH);

		simulationPanel = new PanelSimulation(this);

		// Global structure
		JPanel globalPanel = new JPanel(new BorderLayout());
		globalPanel.add(bigLeftPanel, java.awt.BorderLayout.WEST);
		globalPanel.add(simulationPanel, java.awt.BorderLayout.EAST);
		this.getContentPane().add(globalPanel);

		pack();
	}
	// ----------------------------- Constructor End
	// ---------------------------------------- //

	public Problem getProblem() {
		return problem;
	}

	public PredatorIndividual getBestInRun() {
		return bestInRun;
	}

	// btnData Listenner
	public void buttonDataSet_actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser(new java.io.File("."));
		int returnVal = fc.showOpenDialog(this);

		try {
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File dataSet = fc.getSelectedFile();
				problem = GeneticAProblem.buildProblemFromFile(dataSet);
				problemPanel.textArea.setText(problem.toString());
				problemPanel.textArea.setCaretPosition(0);
				buttonRun.setEnabled(true);
				buttonDataSetSelected=true;
			}
		} catch (IOException e1) {
			e1.printStackTrace(System.err);
		} catch (java.util.NoSuchElementException e2) {
			JOptionPane.showMessageDialog(this, "File format not valid", "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}

	// btn Run listenner
	public void jButtonRun_actionPerformed(ActionEvent e) {
		try {
			/*if (problem == null || testCase.getCurrent() == 0) {
				JOptionPane.showMessageDialog(this, "You must first choose a problem", "Error!",
						JOptionPane.ERROR_MESSAGE);
				return;
			}*/
			
			bestIndividualPanel.textArea.setText("");
			seriesBestIndividual.clear();
			seriesAverage.clear();

			// Switch between modes of operation using the testCase Parameter
			
			switch (testCase.getCurrent()) {
			case (TestCase.RANDOM_CONTROLLER): {
				if (!buttonDataSetSelected) {
					String textRandom = buildStringAdHocRandom();
					problemPanel.textArea.setText(textRandom);
				}
				problem = new RandomProblem(
						Integer.parseInt(panelParameters.jTextFieldSeed.getText().toString().trim()),
						Integer.parseInt(panelParameters.jTextFieldNumberRuns.getText().toString().trim()));
				break;
			}
			case (TestCase.ADHOC_CONTROLLER):
			{
				if (!buttonDataSetSelected) {
					String textAdHoc = buildStringAdHocRandom();
					problemPanel.textArea.setText(textAdHoc);
				}
				problem = new AdHocProblem(Integer.parseInt(panelParameters.jTextFieldSeed.getText().toString().trim()),
						Integer.parseInt(panelParameters.jTextFieldNumberRuns.getText().toString().trim()));
				
			}
			}
		} catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(this, "Wrong parameters!", "Error!", JOptionPane.ERROR_MESSAGE);
		}
		
		worker = new SwingWorker<Void, Void>() {
			
			@Override
			public void done() {
				buttonStop.setEnabled(false);
			}
			
			@Override
			protected Void doInBackground() throws Exception {
				buttonStop.setEnabled(true);
				simulationPanel.buttonSimulate.setEnabled(false);
				problem.run();
				
				// Best Solution :
				// --> Distance from every predator to the prey
				// --> If win or not
				// --> Best run:
					// --> Soma distancias e num de itera��es
				
				String bestSolutionStr = buildStringBestSolution();
				bestIndividualPanel.textArea.setText(bestSolutionStr);
				simulationPanel.buttonSimulate.setEnabled(true);
				return null;
			}
		};
		
		worker.execute();

		/*
		 * Random random = new
		 * Random(Integer.parseInt(panelParameters.jTextFieldSeed.getText()));
		 * ga = new GeneticAlgorithm<>(
		 * Integer.parseInt(panelParameters.jTextFieldN.getText()),
		 * Integer.parseInt(panelParameters.jTextFieldGenerations.getText()),
		 * panelParameters.getSelectionMethod(),
		 * panelParameters.getRecombinationMethod(),
		 * panelParameters.getMutationMethod(), random);
		 * 
		 * System.out.println(ga);
		 * 
		 * ga.addGAListener(this);
		 * 
		 * manageButtons(false, false, true, false, false, false);
		 *
		 * worker = new SwingWorker<Void, Void>() {
		 * 
		 * @Override public Void doInBackground() { try {
		 * 
		 * // bestInRun = ga.run(problem);
		 * 
		 * } catch (Exception e) { e.printStackTrace(System.err); } return null;
		 * }
		 * 
		 * @Override public void done() { manageButtons(true, true, false, true,
		 * experimentsFactory != null, true); } };
		 * 
		 * worker.execute();
		 * 
		 * } catch (NumberFormatException e1) {
		 * JOptionPane.showMessageDialog(this, "Wrong parameters!", "Error!",
		 * JOptionPane.ERROR_MESSAGE); }
		 */
	}

	private String buildStringBestSolution() {
		StringBuilder sb = new StringBuilder();
		double totalDistance=0;
		double distance=0;
		
		sb.append("Resultado: ");
		sb.append(problem.getEnvironment().preyIsCaught()? "Win" : "Loss");
		sb.append("\n");
		for (int i = 0; i < problem.getEnvironment().getPredators().size(); i++){
			distance=problem.getEnvironment().computeDistanceBetweenCells(problem.getEnvironment().getPredators().get(i).getCell(), 
					problem.getEnvironment().getPrey().getCell());
			sb.append("Predator "+(i+1)+": "+distance+"\n");
			totalDistance+=distance;
		}
		
		sb.append("Best run: "+problem.getBestRun());
		
		return sb.toString();
	}

	@Override
	public void generationEnded(GAEvent e) {
		GeneticAlgorithm<PredatorIndividual, GeneticAProblem> source = e.getSource();
		bestIndividualPanel.textArea.setText(source.getBestInRun().toString());
		seriesBestIndividual.add(source.getGeneration(), source.getBestInRun().getFitness());
		seriesAverage.add(source.getGeneration(), source.getAverageFitness());
		if (worker.isCancelled()) {
			e.setStopped(true);
		}
	}
	

	private String buildStringAdHocRandom() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Test case: "+testCase.getCurrent()+"\n");
		sb.append("Seed: "+panelParameters.jTextFieldSeed.getText()+"\n");
		sb.append("# of runs: "+panelParameters.jTextFieldNumberRuns.getText());
		
		return sb.toString();
	}

	@Override
	public void runEnded(GAEvent e) {
	}

	public void jButtonStop_actionPerformed(ActionEvent e) {
		worker.cancel(true);
	}

	public void setbtnRunEnabled() {
		this.buttonRun.setEnabled(true);
	}

	public void buttonExperiments_actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser(new java.io.File("."));
		int returnVal = fc.showOpenDialog(this);

		try {
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				experimentsFactory = new PursuitDomainExperimentsFactory(fc.getSelectedFile());
				manageButtons(true, problem != null, false, true, true, false);
			}
		} catch (IOException e1) {
			e1.printStackTrace(System.err);
		} catch (java.util.NoSuchElementException e2) {
			JOptionPane.showMessageDialog(this, "File format not valid", "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void buttonRunExperiments_actionPerformed(ActionEvent e) {

		manageButtons(false, false, false, false, false, false);
		textFieldExperimentsStatus.setText("Running");

		worker = new SwingWorker<Void, Void>() {
			@Override
			public Void doInBackground() {
				try {
					while (experimentsFactory.hasMoreExperiments()) {
						try {

							Experiment experiment = experimentsFactory.nextExperiment();
							experiment.run();

						} catch (IOException e1) {
							e1.printStackTrace(System.err);
						}
					}
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
				return null;
			}

			@Override
			public void done() {
				manageButtons(true, problem != null, false, true, false, false);
				textFieldExperimentsStatus.setText("Finished");
			}
		};
		worker.execute();
	}

	@Override
	public void experimentEnded(ExperimentEvent e) {
	}

	private void manageButtons(boolean dataSet, boolean run, boolean stopRun, boolean experiments,
			boolean runExperiments, boolean runEnvironment) {

		buttonDataSet.setEnabled(dataSet);
		buttonRun.setEnabled(run);
		buttonStop.setEnabled(stopRun);
		buttonExperiments.setEnabled(experiments);
		buttonRunExperiments.setEnabled(runExperiments);
		simulationPanel.setJButtonSimulateEnabled(runEnvironment);
	}
}

class PanelTextArea extends JPanel {

	JTextArea textArea;

	public PanelTextArea(String title, int rows, int columns) {
		textArea = new JTextArea(rows, columns);
		setLayout(new BorderLayout());
		add(new JLabel(title), java.awt.BorderLayout.NORTH);
		JScrollPane scrollPane = new JScrollPane(textArea);
		textArea.setEditable(false);
		add(scrollPane);
	}
}

class ButtonDataSet_actionAdapter implements ActionListener {

	final private MainFrame adaptee;

	ButtonDataSet_actionAdapter(MainFrame adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.buttonDataSet_actionPerformed(e);
	}
}

class ButtonRun_actionAdapter implements ActionListener {

	final private MainFrame adaptee;

	ButtonRun_actionAdapter(MainFrame adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonRun_actionPerformed(e);
	}
}

class ButtonStop_actionAdapter implements ActionListener {

	final private MainFrame adaptee;

	ButtonStop_actionAdapter(MainFrame adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonStop_actionPerformed(e);
	}
}

class ButtonExperiments_actionAdapter implements ActionListener {

	final private MainFrame adaptee;

	ButtonExperiments_actionAdapter(MainFrame adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.buttonExperiments_actionPerformed(e);
	}
}

class ButtonRunExperiments_actionAdapter implements ActionListener {

	final private MainFrame adaptee;

	ButtonRunExperiments_actionAdapter(MainFrame adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.buttonRunExperiments_actionPerformed(e);
	}
}
