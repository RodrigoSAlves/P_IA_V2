package gui;

import ga.geneticOperators.MutationMUTATION_NAME;
import ga.geneticOperators.Recombination;
import ga.geneticOperators.RecombinationOneCut;
import ga.geneticOperators.RecombinationTwoCuts;
import ga.geneticOperators.RecombinationUniform;
import ga.selectionMethods.SelectionMethod;
import ga.selectionMethods.Tournament;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import pursuitDomain.PredatorIndividual;
import pursuitDomain.TestCase;
import pursuitDomain.GeneticAProblem;

public class PanelParameters extends PanelAtributesValue {

    public static final int TEXT_FIELD_LENGHT = 7;
    public static final String SEED = "1";
    public static final String POPULATION_SIZE = "100";
    public static final String GENERATIONS = "100";
    public static final String TOURNAMENT_SIZE = "2";
    public static final String PROB_RECOMBINATION = "0.7";
    public static final String PROB_MUTATION = "0.1";
    public static final String NUMBER_RUNS = "1";
    JTextField jTextFieldSeed = new JTextField(SEED, TEXT_FIELD_LENGHT);
    JTextField jTextFieldN = new JTextField(POPULATION_SIZE, TEXT_FIELD_LENGHT);
    JTextField jTextFieldGenerations = new JTextField(GENERATIONS, TEXT_FIELD_LENGHT);
    String[] selectionMethods = {"Tournament"};
    String[] selectionControler = {"No Selection", "Random", "AdHoc", "Homegen", "Heterogen"};
    JComboBox jComboBoxSelectionMethods = new JComboBox(selectionMethods);
    JComboBox jComboBoxSelectionControllers = new JComboBox(selectionControler);
    JTextField jTextFieldTournamentSize = new JTextField(TOURNAMENT_SIZE, TEXT_FIELD_LENGHT);
    String[] recombinationMethods = {"One cut", "Two cuts", "Uniform"};
    JComboBox jComboBoxRecombinationMethods = new JComboBox(recombinationMethods);
    JTextField jTextFieldProbRecombination = new JTextField(PROB_RECOMBINATION, TEXT_FIELD_LENGHT);
    JTextField jTextFieldProbMutation = new JTextField(PROB_MUTATION, TEXT_FIELD_LENGHT);
    JTextField jTextFieldNumberRuns = new JTextField(NUMBER_RUNS, TEXT_FIELD_LENGHT);
    private TestCase testCase = TestCase.getInstace();

    private MainFrame mainFrame;

    
    //Labels
	JLabel lblSeed = new JLabel("Seed: ");
	JLabel lblPopSize = new JLabel("Population size: ");
	JLabel lblNumberGens = new JLabel("# of generations: ");
	JLabel lblSelectMethod = new JLabel("Selection method: ");
	JLabel lblTournmentSize = new JLabel("Tournament size: ");
	JLabel lblRecombMethod = new JLabel("Recombination method: ");
	JLabel lblRecombProb = new JLabel("Recombination prob.: ");
	JLabel lblMutationProb = new JLabel("Mutation prob.: ");
	JLabel lblTestControl = new JLabel("Test Controler: ");
	JLabel lblRuns = new JLabel ("# of runs: ");


   

    //MORE PARAMETERS?
    
    public PanelParameters(MainFrame mainframe) {
    	this.mainFrame = mainframe;
        title = "Genetic algorithm parameters";


        labels.add(lblTestControl);
        valueComponents.add(jComboBoxSelectionControllers);
        jComboBoxSelectionControllers.addActionListener(new JComboxSelectionControllers_ActionAdapter(this));

        //MORE PARAMETERS?
        
        configure();
    }
    

    public void actionPerformedSelectionMethods(ActionEvent e) {
        jTextFieldTournamentSize.setEnabled(jComboBoxSelectionMethods.getSelectedIndex() == 0);
    }

    public SelectionMethod<PredatorIndividual, GeneticAProblem> getSelectionMethod() {
        switch (jComboBoxSelectionMethods.getSelectedIndex()) {
            case 0:
                return new Tournament<>(
                        Integer.parseInt(jTextFieldN.getText()),
                        Integer.parseInt(jTextFieldTournamentSize.getText()));
        }
        return null;
    }

    public Recombination<PredatorIndividual> getRecombinationMethod() {

        double recombinationProb = Double.parseDouble(jTextFieldProbRecombination.getText());

        switch (jComboBoxRecombinationMethods.getSelectedIndex()) {
            case 0:
                return new RecombinationOneCut<>(recombinationProb);
            case 1:
                return new RecombinationTwoCuts<>(recombinationProb);
            case 2:
                return new RecombinationUniform<>(recombinationProb);
        }
        return null;
    }

    public MutationMUTATION_NAME<PredatorIndividual> getMutationMethod() {
        double mutationProbability = Double.parseDouble(jTextFieldProbMutation.getText());
        //COMPLETE?
        return new MutationMUTATION_NAME<>(mutationProbability/*COMPLETE?*/);
    }
    
    public void updateComponentsBasedOnTestCase(){
    	int index = jComboBoxSelectionControllers.getSelectedIndex();
    	
    	switch(index){
    	case (0):{
    		setComponentsForPanelParameters(index);
    	}break;
    	case (1):{
    		setComponentsForPanelParameters(index);
    		testCase.setCurrent(index);
    	}break;
    	
    	case (2):{
    		setComponentsForPanelParameters(index);
    		testCase.setCurrent(index);
    	}break;
    	case (3):{
    		setComponentsForPanelParameters(index);
    		testCase.setCurrent(index);
    	}break;
    	case (4):{
    		setComponentsForPanelParameters(index);
    		testCase.setCurrent(index);
    	}break;
    	}
    }
    
    public void setComponentsForPanelParameters(int index){
    	
    	switch(index){
	    	case (0):{
	    		removeParameters();
	            this.configure();
	            this.revalidate();
	    	}break;
	    	case (1):{
	    		removeParameters();
	    		labels.add(lblSeed);
	            valueComponents.add(jTextFieldSeed);
	            jTextFieldSeed.addKeyListener(new IntegerTextField_KeyAdapter(null));
	            
	            labels.add(lblRuns);
	            valueComponents.add(jTextFieldNumberRuns);
	            jTextFieldNumberRuns.addKeyListener(new IntegerTextField_KeyAdapter(null));

	            this.configure();
	            this.revalidate();
	    	}break;
	    	
	    	case (2):{
	    		removeParameters();
	        	labels.add(lblSeed);
	            valueComponents.add(jTextFieldSeed);
	            jTextFieldSeed.addKeyListener(new IntegerTextField_KeyAdapter(null));
	            
	    		labels.add(lblRuns);
	            valueComponents.add(jTextFieldNumberRuns);
	            jTextFieldNumberRuns.addKeyListener(new IntegerTextField_KeyAdapter(null));
	            this.configure();
	            this.revalidate();
	    	}break;
	    	case (3):{
	    		removeParameters();
	        	labels.add(lblSeed);
	            valueComponents.add(jTextFieldSeed);
	            jTextFieldSeed.addKeyListener(new IntegerTextField_KeyAdapter(null));
	            
	    		labels.add(lblPopSize);
	            valueComponents.add(jTextFieldN);
	            jTextFieldN.addKeyListener(new IntegerTextField_KeyAdapter(null));
	
	            labels.add(lblNumberGens);
	            valueComponents.add(jTextFieldGenerations);
	            jTextFieldGenerations.addKeyListener(new IntegerTextField_KeyAdapter(null));
	
	            labels.add(lblSelectMethod);
	            valueComponents.add(jComboBoxSelectionMethods);
	            jComboBoxSelectionMethods.addActionListener(new JComboBoxSelectionMethods_ActionAdapter(this));
	
	            labels.add(lblTournmentSize);
	            valueComponents.add(jTextFieldTournamentSize);
	            jTextFieldTournamentSize.addKeyListener(new IntegerTextField_KeyAdapter(null));
	
	            labels.add(lblRecombMethod);
	            valueComponents.add(jComboBoxRecombinationMethods);
	
	            labels.add(lblRecombProb);
	            valueComponents.add(jTextFieldProbRecombination);
	
	            labels.add(lblMutationProb);
	            valueComponents.add(jTextFieldProbMutation);
	            this.configure();
	            this.revalidate();
	    		
	    	}break;
	    	case (4):{
	    		removeParameters();
	    		labels.add(lblSeed);
	            valueComponents.add(jTextFieldSeed);
	            jTextFieldSeed.addKeyListener(new IntegerTextField_KeyAdapter(null));
	            
	    		labels.add(lblPopSize);
	            valueComponents.add(jTextFieldN);
	            jTextFieldN.addKeyListener(new IntegerTextField_KeyAdapter(null));
	
	            labels.add(lblNumberGens);
	            valueComponents.add(jTextFieldGenerations);
	            jTextFieldGenerations.addKeyListener(new IntegerTextField_KeyAdapter(null));
	
	            labels.add(lblSelectMethod);
	            valueComponents.add(jComboBoxSelectionMethods);
	            jComboBoxSelectionMethods.addActionListener(new JComboBoxSelectionMethods_ActionAdapter(this));
	
	            labels.add(lblTournmentSize);
	            valueComponents.add(jTextFieldTournamentSize);
	            jTextFieldTournamentSize.addKeyListener(new IntegerTextField_KeyAdapter(null));
	
	            labels.add(lblRecombMethod);
	            valueComponents.add(jComboBoxRecombinationMethods);
	
	            labels.add(lblRecombProb);
	            valueComponents.add(jTextFieldProbRecombination);
	
	            labels.add(lblMutationProb);
	            valueComponents.add(jTextFieldProbMutation);
	            this.configure();
	            this.revalidate();
	    	}break;
    	}

    }
	private void removeParameters() {
		labels.removeAll(labels);
		valueComponents.removeAll(valueComponents);
		this.removeAll();
        this.configure();
        this.revalidate();
        labels.add(lblTestControl);
        valueComponents.add(jComboBoxSelectionControllers);

		labels.remove(lblSeed);
		valueComponents.remove(jTextFieldSeed);
		labels.remove(lblPopSize);
		valueComponents.remove(jTextFieldN);
		labels.remove(lblNumberGens);
		valueComponents.remove(jTextFieldGenerations);
		labels.remove(lblSelectMethod);
		valueComponents.remove(jComboBoxSelectionMethods);
		labels.remove(lblTournmentSize);
		valueComponents.remove(jTextFieldTournamentSize);
		labels.remove(lblRecombMethod);
		valueComponents.remove(jComboBoxRecombinationMethods);
		labels.remove(lblRecombProb);
		valueComponents.remove(jTextFieldProbRecombination);
		labels.remove(lblMutationProb);
		valueComponents.remove(jTextFieldProbMutation);

        this.configure();
        this.revalidate();
	}


	public void actionPerformedSelectionController(ActionEvent e) {
		updateComponentsBasedOnTestCase();
	}
}

class JComboBoxSelectionMethods_ActionAdapter implements ActionListener {

    final private PanelParameters adaptee;

    JComboBoxSelectionMethods_ActionAdapter(PanelParameters adaptee) {
        this.adaptee = adaptee;
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.actionPerformedSelectionMethods(e);
    }
}

class JComboxSelectionControllers_ActionAdapter implements ActionListener {
	final private PanelParameters adaptee;
	
	JComboxSelectionControllers_ActionAdapter(PanelParameters adaptee) {
		this.adaptee = adaptee;
		adaptee.updateComponentsBasedOnTestCase();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.actionPerformedSelectionController(e);		
	}
	
}

class IntegerTextField_KeyAdapter implements KeyListener {

    final private MainFrame adaptee;

    IntegerTextField_KeyAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) {
            e.consume();
        }
    }
   

}