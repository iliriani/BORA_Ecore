package boraproj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import boraproj.services.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import RefClasses.Controls;
import boraproj.controller.AttributeGet;
import boraproj.controller.ConnectionController;
import boraproj.controller.NGram;
import boraproj.controller.RLController;
import boraproj.evaluation.EvaluationForKfoldValidation;
import boraproj.evaluation.Evaluation_2;
import boraproj.evaluation.NLEvaluation;
import boraproj.evaluation.PrintEvaluationResults;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class BoraMain {

	public static void main(String[] args) {

		SpringApplication.run(BoraMain.class, args);

		System.out.println("BORA is running successfully!");
		

//		ArrayList<String> atr = new ArrayList<>();atr.add("Color");atr.add("Length");	
//		atr.add("Weight");
//		AttributeForRecommendations attr_rec = new AttributeForRecommendations(); attr_rec.getClassesWithSameAttributes(atr);
//		NLPService np = new NLPService();
//		np.getOpenIE("Give me all classes that have only two referenes and have attribute ID");
//		np.getCoReferences("Give me all classes that have only two referenes and have attribute ID.");
//		np.getCoReferences("Barack Obama was born in Hawaii.  He is the president. Obama was elected in 2008.");
		
//		BotService botService = new BotService(); botService.getMatchingModels("Can I have a RetailBarcodeMaskTable class?");
		NGram ngram = new NGram(); ngram.oneGram("Organization");
//		ArrayList<Double> mrrs = new ArrayList<>();
//		NLEvaluation ev = new NLEvaluation(); 
//		for (int i = 0; i < 10; i++) {
//			String rand_model = ev.getRandomModel();
//			String rand_cl1 = ev.getRandomClass(rand_model);
//			String rand_cl2 = ev.getRandomClass(rand_model);
//			String nl = ev.getUserNLSimulationForModelClasses(rand_cl1, rand_cl2);
//			double mrr = ev.getMRR_Models(nl, rand_model);
//			mrrs.add(mrr);
//			System.out.println( nl +" - "+mrr);
//		}
//		System.out.println("Average MRR is: "+calculateAverage(mrrs));
	  
		
		// Code for calcuating the mAP during 10-fold cross validation
		  
//		  String repoTest = "C:\\Users\\Ibrahimi\\Desktop\\10-fold evaluation ZappDev models\\2\\test\\repository";
//		  String repoTrain = "C:\\Users\\Ibrahimi\\Desktop\\10-fold evaluation ZappDev models\\2\\train\\repository";
////		  
//		  EvaluationForKfoldValidation comp = new EvaluationForKfoldValidation(repoTest);
//
//				RDFMerge m = new RDFMerge();
//		m.iterate();
//
//		  comp.createRepo("C:\\Users\\Ibrahimi\\Desktop\\10-fold evaluation ZappDev models\\2\\test\\repository\\merged.rdf",repoTest);
//
//		AddWeights w = new AddWeights(repoTest);
//		w.insertAllWeight();


//		comp.compare(repoTest, repoTrain);
//				  System.out.println("----------------");
//				  comp.compareAtttributeAndRelations(repoTest, repoTrain);



//		  ArrayList<String> all_test_classes = new ArrayList<String>(comp.getModelUnderConstruction(repoTest)); 
//		  
//		  ArrayList<Double> mAP_average= new ArrayList<Double>(); 
//		  for(String test_class: all_test_classes) {  
//			 ArrayList<String> train_suggestions = new ArrayList<String>(comp.getOnegram(test_class, repoTrain)); 
//		  ArrayList<String> test_suggestions = new ArrayList<String>(comp.getOnegram(test_class, repoTest)); 
//		  System.out.println("mAP is: "+comp.getmAP(test_suggestions, train_suggestions)); 
//		  mAP_average.add(comp.getmAP(test_suggestions,train_suggestions)); }
//		  
////		  This part is for attr-con recommendation map evaluation
//		  ArrayList<String> test_features = new ArrayList<String>();
//		  ArrayList<String> train_features = new ArrayList<String>();
//		  ArrayList<Double> mAP_average= new ArrayList<Double>(); 
//		  for(String test_class: all_test_classes) { 
//		  LinkedHashMap test_attributes = new LinkedHashMap<>(comp.getAttributesWithDataTypes(test_class,repoTest));
//		  LinkedHashMap train_attributes = new LinkedHashMap<>(comp.getAttributesWithDataTypes(test_class, repoTrain)); //
//		  ArrayList<String> test_cl_conn = new ArrayList<String>(comp.getConnectionMultiplicities(test_class, repoTest));
//		  ArrayList<String> train_cl_conn = new ArrayList<String>(comp.getConnectionMultiplicities(test_class, repoTrain));
//		   Set<String> test_attr_set = test_attributes.keySet(); 
//		  for(String test_atrr: test_attr_set) { 
//			  // System.out.p)rintln(test_atrr+": "+test_attributes.get(test_atrr)); 
//			  String add_test = test_atrr+"-"+test_attributes.get(test_atrr); 
//			  test_features.add(add_test);
//		  
//		  }
//		  
//		  Set<String> train_attr_set = train_attributes.keySet(); 
//		  for(String train_atrr: train_attr_set) { 
//		    String add_train = train_atrr+"-"+train_attributes.get(train_atrr);
//		    train_features.add(add_train);
//		  
//		  }
//		  
//		  test_features.addAll(test_cl_conn); train_features.addAll(train_cl_conn);
//		  
//		  System.out.println("mAP is: "+comp.getmAP(test_features, train_features));
//		  mAP_average.add(comp.getmAP(test_features, train_features)); }
//		 
//		  
//		  System.out.println("Average mAP: "+ calculateAverage(mAP_average));
		 

//		System.out.println("model under construction: "+comp.getModelUnderConstruction(repoTest));
//		comp.getOnegram("TimeSheetYear", repoTest);

//		ArrayList<String> test = new ArrayList<String>(); test.add("VoyageStowawayListPerson");test.add("DocumentReference");test.add("Stowaway");
//		ArrayList<String> sugg = new ArrayList<String>(); sugg.add("MRFMessage");sugg.add("VoyageArrivalNotice"); sugg.add("Voyage");sugg.add("DocumentReference");
//		sugg.add("VoyageDepartureNotice");sugg.add("VoyageStowawayListPerson");sugg.add("Stowaway");
//		
//		System.out.println(comp.getmAP(test, sugg));
//		comp.compare(repoTest, repoTrain);

//		String file = "C:\\Users\\Admin\\Desktop\\kFoldEvaluation\\EvaluationEcore\\10\\train\\trainKG10.rdf";
//		String repoPath = "C:\\Users\\Admin\\Desktop\\kFoldEvaluation\\EvaluationEcore\\10\\train\\repository";
////		
//		EvaluationForKfoldValidation comp = new EvaluationForKfoldValidation(repoPath);
//
//		AddWeights add_weights = new AddWeights(repoPath);
//		add_weights.insertAllWeight();
//		comp.getOnegram("Root", repoPath);

//		FormConnection formCon = new FormConnection();
//		ArrayList<Controls> all = formCon.getControlsInfo("Client");
//		for(Controls cont: all) {
//			System.out.println(cont.getName() + " "+ cont.getDatasource()+" "+cont.getType());
//		}
//		HashMap<String, String> map = formCon.getMetaClassesAndUIComponents("Client");
//		for (Entry<String, String> entry : map.entrySet()) {
//		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//		}

//		System.out.println("Connected meta-class is: -----------------");
//		System.out.println(formCon.getMetaClassesConnectedToForm());
//		NGram ngram = new NGram();
//		System.out.println(ngram.oneGram("Notebook"));
		System.out.println("Done!");

//		NLPService nlp_service = new NLPService();
//		System.out.println(nlp_service.getRelevantTokens("I want a model for an Vessel trip."));

		/*
		 * List<String> mod_u_cont = Arrays.asList(new String[]{"Vessel",
		 * "VesselCharacteristics","Trip","EmployeeList","Person","Note","Leg","Port",
		 * "PortStop","PortGate","Country"});
		 * 
		 * ArrayList<String> model_under_construction = new ArrayList<String>();
		 * model_under_construction.addAll(mod_u_cont);
		 * 
		 * List<String> rel_repo = Arrays.asList(new String[]{"PointOfInterest", "Note",
		 * "Person", "Leg", "Photograph", "Datasource", "UrbanLocationPair",
		 * "RailSegment", "FareType", "Charge", "PreferenceType", "Fare",
		 * "FlightSegment", "GeoCoordinates", "Preference", "ChargeType", "Currency",
		 * "MarineSegment", "UrbanSegment", "plan", "Solution", "Location"});
		 * ArrayList<String> relevant_repo = new ArrayList<String>();
		 * relevant_repo.addAll(rel_repo);
		 * 
		 * NGram ngram = new NGram(); ArrayList<String> sug =
		 * ngram.getOneGramDomainRelevant("Leg","Trip");
		 */

//		  Evaluation_2 evaluation = new Evaluation_2(model_under_construction,
//		  relevant_repo, sug);
//		  
//		  System.out.println(evaluation.precision()+" "+evaluation.recall()+"  "
//		  +evaluation.fMeaure());

//		PrintEvaluationResults evaluation = new PrintEvaluationResults(model_under_construction,
//				  relevant_repo, sug); evaluation.print();

//		ArrayList<String> selected = new ArrayList<String>();
//		selected.add("Student");selected.add("Absance");
//		
//		RLController rl = new RLController();
//		rl.undoUpdateWeights("Notebook", selected);

//		  String repoPath = "../BoraAI/src/main/resources/repository";

//		  AttributeGet conser = new AttributeGet();
//		  System.out.println(conser.getAttributesWithDataTypes("Order"));

//		ConnectionService con = new ConnectionService();
//		System.out.println(con.getConnectionDetails("Photograph","PointOfInterest"));

//		  RankResults rank = new RankResults(repoPath);
//		  InexactMatching inex = new InexactMatching();
//		  String[] words=new String[1];words[0]="Product";
//		  inex.synonimMatch(words);

//		  ArrayList results = ngram.oneGram("OrderStatus");
//		  ArrayList results1 = ngram.getThreeGramDomainRelevant("OrderProduct","Order","OrderStatus");
//		  System.out.println("-------------------------");
//		  System.out.println(rank.rankNgramResults("Photograph", results1)) ;
//		  System.out.println(rank.rankNgramResults("PointOfInterest", results1)) ;

//		String repoPath = "C:\\Users\\Admin\\eclipse-workspace\\BoraAI\\src\\main\\resources\\repository";

//		RepoConnect repoCon = new RepoConnect(repoPath);

//		AddWeights addWeights = new AddWeights(repoPath);
//		addWeights.addWeights("Notebook", "Absance", 4);

//		System.out.println(addWeights.getConnectionFrekuency("Notebook","Absance"));

//		if(addWeights.getWeight("Notebook", "Student") != 0) {
//			System.out.println(addWeights.getWeight("Notebook", "Student"));
//		}
//		
//		else {System.out.println("is null");}
//		System.out.println(addWeights.getWeight("Expense", "Currency"));

//		repoCon.getRelatedClasses("Notebook");

//		addWeights.insertAllWeight();

	}

	
	  // Method for calculating the average of an ArrayList, required for the mAP calculation
	  
	  public static double calculateAverage(ArrayList<Double> my_list) { 
		  return my_list.stream().mapToDouble(d -> d).average().orElse(0.0); }
	 


}
