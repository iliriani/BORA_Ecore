package boraproj;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import boraproj.controller.AttributeGet;
import boraproj.controller.ConnectionController;
import boraproj.controller.NGram;
import boraproj.controller.RLController;
import boraproj.evaluation.Evaluation_2;
import boraproj.evaluation.PrintEvaluationResults;
import boraproj.services.AttributeService;
import boraproj.services.BotService;
import boraproj.services.ConnectionService;
import boraproj.services.NLPService;
import boraproj.services.RankResults;
import boraproj.services.RepoConnect;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class BoraMain {
	
	public static void main(String[] args) {
		
		SpringApplication.run(BoraMain.class, args);
		
		
		System.out.println("BORA is running successfully!");

		
		BotService botService = new BotService(); botService.getMatchingModels("I want to order a Trip with a photograph and leg");
		
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

}
