package boraproj.evaluation;
import java.util.ArrayList;

import boraproj.controller.NGram;

public class Evaluation {

	private String model;
	private NGram n_gram;
	private ArrayList<String> all_classes;
	private ArrayList<String> predicted;
	
	public Evaluation(String model) {
		this.model = model;
		n_gram = new NGram("../BORA/src/main/resources/KG");
	}
	
	public double precision(String cl1, String cl2) {
		all_classes = n_gram.getAllClasses(model); 
		all_classes.remove(cl1);
		all_classes.remove(cl2);
//		all_classes.remove(cl3);
		System.out.println("all classes: " + all_classes);

		double all = all_classes.size();
		predicted = n_gram.twoGram(cl1, cl2);
		System.out.println("Predicted classes are: "+ predicted);
		 // Get the right predicted classes
        ArrayList<String> common_classes = new ArrayList<String>();

        for (String temp : predicted) {

            if (all_classes.contains(temp)) {

                common_classes.add(temp);
            }
        }
		
		
		double predicted_right = common_classes.size();
		double calc = predicted_right/(double)predicted.size(); 
		System.out.println("Precision is: "+ calc);
		return calc;
	}
	
	public double recall(String cl1, String cl2) {
		all_classes = n_gram.getAllClasses(model); 
		all_classes.remove(cl1);
		all_classes.remove(cl2);
//		all_classes.remove(cl3);
//		System.out.println("all classes: " + all_classes);

		double all = all_classes.size();
		predicted = n_gram.twoGram(cl1, cl2);
		 // Get the right predicted classes
        ArrayList<String> common_classes = new ArrayList<String>();

        for (String temp : predicted) {

            if (all_classes.contains(temp)) {

                common_classes.add(temp);
            }
        }
		
		
		double predicted_right = common_classes.size();
		double calc = predicted_right/all; 
		System.out.println("Recall is: "+ calc);
		return calc;
	}
	
	//Calculate the F-beta score for b = o.5
	public double fscore(String cl1, String cl2) {
		double precision = this.precision(cl1, cl2);
		double recall = this.recall(cl1, cl2);
		System.out.println(precision+"  "+recall);
		double emr = 0.5*(1.0/precision) + 0.5*(1.0/recall);
		double calc = 1.0/emr;
		System.out.println("Fcore is: " +calc);
		return calc;
	}
}
