package boraproj.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.andrewoma.dexx.collection.HashSet;

import RefClasses.NLModeler;

@Service
public class BotService {
	private static boolean model_requested_having_class = false;
	private static boolean class_request_having_attri = false;
	
	@Autowired
	RepoConnect stored;
	
	@Autowired
	NLPService service;
	
//	@Autowired
	NLModeler nlmodeler;
	
	public BotService() {
		stored = new RepoConnect();
		service = new NLPService();
//		nlmodeler = new NLModeler();
	}
	
	public BotService(String path) {
		stored = new RepoConnect(path);
		service = new NLPService();
		nlmodeler = new NLModeler();
	}
	
	
	public List<NLModeler> getMatchingModels(String text) {
		
//		Initially lemmatize the user search string and then tokenize it
		ArrayList<String> userLemmas = service.getLemmas(text);
		System.out.println("User lemmas are: "+userLemmas);
		
			//Check if user request a model that has specific classes
			ArrayList<String> model_request_tokents_having_class = new ArrayList<>();
			model_request_tokents_having_class.add("have");model_request_tokents_having_class.add("contain");model_request_tokents_having_class.add("constitute");model_request_tokents_having_class.add("build");
			model_request_tokents_having_class.add("include");model_request_tokents_having_class.add("consist");model_request_tokents_having_class.add("comprise");
			for(int i=0; i<model_request_tokents_having_class.size();i++) {
				if(userLemmas.contains(model_request_tokents_having_class.get(i))) {
					System.out.println("Po hin ne contains mode haver..."+ model_request_tokents_having_class.get(i));
					model_requested_having_class = true;
					break;}
				}
			
			//Check if user request a class with specific attributes without mentionng attributes
			for(int i=0; i<model_request_tokents_having_class.size();i++) {
				if( userLemmas.contains("class") && userLemmas.contains(model_request_tokents_having_class.get(i))) {
					System.out.println("Po hin ne CLASSS contains mode haver..."+ model_request_tokents_having_class.get(i));
					class_request_having_attri = true;
					break;}
				}
			
			
		String lematized = "";
		for(String lemma: userLemmas) {lematized = lematized + " " + lemma;}
//		Tokenize now the search string
		ArrayList<String> userTerms = service.getRelevantTokens(lematized);
	
		
		System.out.println("All terms and tokens: "+userTerms);
		
		ArrayList<String> recommendedModels = new ArrayList<String>();
//		List<NLModeler> nlModelers = new ArrayList<NLModeler>();
		
		userTerms = capitalizeAll(userTerms);
		userTerms = removeNoisedTerms(userTerms);
		
		for(String term: userTerms) {

			ArrayList<String> modelNames = stored.getModelName(term);
			for(String model:modelNames) {
				if (model.equals("")) {model = "X";}
				recommendedModels.add(model);
//				System.out.println(model+" - "+term);
				
			}
		}
		
		LinkedList<String> ranked = new LinkedList<String>();
		ranked = this.getMostFrequented(recommendedModels);
		
		List<NLModeler> nlModelers = new ArrayList<NLModeler>(getRelevantData(userTerms));
		System.out.println(ranked);
		
		return nlModelers;
	}
	
	
//	Get the model which has been appeared most
	public LinkedList getMostFrequented(ArrayList<String> list) {

		 LinkedList<String> ranked = new LinkedList<String>();
		 
	        Map<String, Integer> frequencyMap = new HashMap<String, Integer>();
	        for (String s: list)
	        {
	            Integer count = frequencyMap.get(s);
	            if (count == null) {
	                count = 0;
	            }
	 
	            frequencyMap.put(s, count + 1);
	        }
	        
	        Map<String, Integer> temp = this.sortByValue(frequencyMap);
	        
	        for (Map.Entry<String, Integer> entry: temp.entrySet()) {
	        	ranked.add(entry.getKey());
//	            System.out.println(entry.getKey() + ": " + entry.getValue());
	        }
	        
	        return ranked;
	}
	
	
    // function to sort hashmap by values
    public Map<String, Integer> sortByValue(Map<String, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list =
               new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());
 
        // Sort the list
        Collections.sort(list, Collections.reverseOrder(new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        }));
        
         
        // put data from sorted list to hashmap
        Map<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;

        
    }
	
	
    private ArrayList<String> capitalizeAll(ArrayList<String> terms){
    	ArrayList<String> cap = new ArrayList<>();
    	for(String t: terms) {
    		String c = capitalize(t);
    		cap.add(c);
    	}
    	return cap;
    }
	
	private String capitalize(final String line) {
		   return Character.toUpperCase(line.charAt(0)) + line.substring(1);
		}
	
	
////	Creates the NLModeler class and returns them
//	public List<NLModeler> getRelevantData(LinkedList<String> domains, ArrayList<String> terms) {
//		
//		List<NLModeler> nlmodelers = new ArrayList<NLModeler>();
//		ArrayList<String> tokens = new ArrayList<String>();
//		
//		for(String term: terms) {tokens.add(this.capitalize(term));}
//		
////		tokens.forEach(token -> {this.capitalize(token);});
//		
////		System.out.println("From method tokens: "+tokens);
//		
//		for(String domain: domains) {
//			
//			nlmodeler = new NLModeler();
//			
//			nlmodeler.setDomain(domain);
//			ArrayList<String> all_domain_classes = new ArrayList<String>(stored.getRelatedDomainClasses(domain));
//			ArrayList<String> all_tokens = new ArrayList<String>(stored.getRelatedDomainClasses(domain));
//			
//			ArrayList<String> island_classes = new ArrayList<String>(stored.getIslandClasses(domain));
//			all_domain_classes.addAll(island_classes);
//			all_tokens.addAll(island_classes);
////			System.out.println("domain: "+domain+" - "+all_domain_classes.size());
//			all_tokens.retainAll(tokens);
//			
//			nlmodeler.setTokens(all_tokens);
//			nlmodeler.setClasses(all_domain_classes);
//			nlmodelers.add(nlmodeler);
//			
//		}
//		
//		return nlmodelers;
//	}
	
	public ArrayList<String> removeNoisedTerms(ArrayList<String> tokens) {
		ArrayList<String> noise = new ArrayList<>();
		noise.add("Want");noise.add("Give");noise.add("Like");
		noise.add("Show");noise.add("Find");noise.add("Create");
		tokens.removeAll(noise);
		return tokens;
	}
	
////	Creates the NLModeler class and returns them
	public List<NLModeler> getRelevantData(ArrayList<String> tokens) {
		
		
		List<NLModeler> nlmodelers = new ArrayList<NLModeler>();
//		ArrayList<String> tokens = new ArrayList<String>();
//		
//		for(String term: terms) {tokens.add(this.capitalize(term));}
	
		if(tokens.contains("Model")) {
			tokens.remove("Model");
			//Query for models that have specific classes
			if(model_requested_having_class) {
				tokens.remove("Contain");tokens.remove("Constitute");tokens.remove("Build");tokens.remove("Have");tokens.remove("Class");
				nlmodelers = getTopicMatchedModels(tokens);
			}
			//Query for model names
			else {
//				System.err.println("Tokens inside method are: "+tokens);
				nlmodelers = getTopicMatchedModelNames(tokens);
				}
		}
//		Query for class having attributes (without mentioning attribute)
		else if(class_request_having_attri) {
			tokens.remove("Class");
			nlmodelers = getTopicMatchedClassesFromAttributes(tokens);
		}
		else if(tokens.contains("Attribute")) {
			tokens.remove("Attribute"); tokens.remove("Class");
			nlmodelers = getTopicMatchedClassesFromAttributes(tokens);
		}
		else if(tokens.contains("Connect")) {
			tokens.remove("Connect");tokens.remove("Class"); tokens.remove("Be");
			nlmodelers = getConnectedClasses(tokens);
		}
		
		else if(tokens.contains("Relate")) {
			tokens.remove("Relate");tokens.remove("Class");tokens.remove("Be");
			nlmodelers = getConnectedClasses(tokens);
		}
		
		else {
			tokens.remove("Class");tokens.remove("Give");
//			nlmodelers = getTopicMatchedClasses(tokens);
			nlmodelers = getMatchesClasses(tokens);
			}
		
		return nlmodelers;
	}
	
	
	
	
	private List<NLModeler> getConnectedClasses(ArrayList<String> tokens) {
		
		List<NLModeler> nlmodelers = new ArrayList<NLModeler>();

		for(String t: tokens) {
//			ArrayList<String> temp = stored.getRelatedClasses(t);
			ArrayList<String> temp = stored.getRelatedClassesForNLP(t);
			ArrayList<String> tok = new ArrayList<>(); 
			tok.add(t);
			nlmodeler = new NLModeler();
			nlmodeler.setClasses(temp);
			nlmodeler.setTokens(tok);
			
			nlmodelers.add(nlmodeler);
		}
		

		
	return nlmodelers;
}

	private List<NLModeler> getTopicMatchedClassesFromAttributes(ArrayList<String> tokens) {
		
		List<NLModeler> nlmodelers = new ArrayList<NLModeler>();

		ArrayList<String> classes = stored.getClassFromAttributes(tokens);
		
		nlmodeler = new NLModeler();
		
		nlmodeler.setClasses(classes);
		
		nlmodelers.add(nlmodeler);
		
	return nlmodelers;
}

	public List<NLModeler> getTopicMatchedModels(ArrayList<String> tokens){
		
		ArrayList<String> recommendedModels = new ArrayList<>();
		for(String term: tokens) {

			ArrayList<String> modelNames = stored.getModelNameForGivenClass(term);
			for(String model:modelNames) {
				if (model.equals("")) {model = "X";}
				recommendedModels.add(model);
				
			}
		}
		
		LinkedList<String> ranked = new LinkedList<String>();
		ranked = this.getMostFrequented(recommendedModels);

		
		List<NLModeler> nlmodelers = new ArrayList<NLModeler>();
		
		for(String domain: ranked) {
			
			nlmodeler = new NLModeler();
			
			nlmodeler.setDomain(domain);
//			ArrayList<String> all_domain_classes = new ArrayList<String>(stored.getRelatedDomainClasses(domain));
			ArrayList<String> all_domain_classes = new ArrayList<String>(stored.getRelatedDomainClassesForNLP(domain));
//			ArrayList<String> all_tokens = new ArrayList<String>(stored.getRelatedDomainClasses(domain));
			ArrayList<String> all_tokens = new ArrayList<String>(stored.getRelatedClassesForNLP(domain));
			ArrayList<String> island_classes = new ArrayList<String>(stored.getIslandClasses(domain));
			all_domain_classes.addAll(island_classes);
			all_tokens.addAll(island_classes);
//			System.out.println("domain: "+domain+" - "+all_domain_classes.size());
			all_tokens.retainAll(tokens);
			
			nlmodeler.setTokens(all_tokens);
			nlmodeler.setClasses(all_domain_classes);
			nlmodelers.add(nlmodeler);
			
		}
		
		return nlmodelers;
	}
	
	
	public List<NLModeler> getTopicMatchedModelNames(ArrayList<String> tokens){
		
		List<NLModeler> nlmodelers = new ArrayList<NLModeler>();
		ArrayList<String> all_domain_classes = new ArrayList<String>();
		ArrayList<String> all_tokens = new ArrayList<String>();
		ArrayList<String> island_classes = new ArrayList<String>();
		
		for(String tok:tokens) {
			
			nlmodeler = new NLModeler();
			
			
			ArrayList<String> all_matched_models = new ArrayList<String>(stored.getModelName(tok));
//			for (int i=0; i<all_matched_models.size();i++) {
				
				if (all_matched_models.size() > 0) {
					nlmodeler.setDomain(all_matched_models.get(0));
					all_domain_classes = stored.getRelatedDomainClassesForNLP(all_matched_models.get(0));
					all_tokens = stored.getRelatedClassesForNLP(all_matched_models.get(0));
					island_classes = stored.getIslandClasses(all_matched_models.get(0));
				}
//			}


			all_domain_classes.addAll(island_classes);
			all_tokens.addAll(island_classes);

			all_tokens.retainAll(tokens);
			
			nlmodeler.setTokens(all_tokens);
			nlmodeler.setClasses(all_domain_classes);
			nlmodelers.add(nlmodeler);
			
		}
		
		return nlmodelers;
	}
	

	public List<NLModeler> getMatchesClasses(ArrayList<String> tokens){
		List<NLModeler> nlmodelers = new ArrayList<NLModeler>();

		Set<String> classes = new LinkedHashSet<>();
		for(int i=0; i<tokens.size();i++) {
			
			ArrayList<String> temp = new ArrayList<>(stored.getTokenMatchedClasses(tokens.get(i)));
			classes.addAll(temp);
		}

		ArrayList<String> cl = new ArrayList<>(classes);
		nlmodeler = new NLModeler();
		
		nlmodeler.setClasses(cl);
		
		nlmodelers.add(nlmodeler);
		
	return nlmodelers;
	}
	
	
	
	public List<NLModeler> getTopicMatchedClasses(ArrayList<String> terms) {
		
		HashMap<String,LinkedHashMap<String, String>> cl_attributes = new HashMap<String,LinkedHashMap<String, String>>();
		List<NLModeler> nlmodelers = new ArrayList<NLModeler>();
		ArrayList<String> tokens = new ArrayList<String>();
		LinkedHashMap<String,String> attributes_with_datatypes = new LinkedHashMap<String,String>();
		
		for(String term: terms) {tokens.add(this.capitalize(term));}
		
		for(String token : tokens) {
			nlmodeler = new NLModeler();
			
			ArrayList<String> attributes = new ArrayList<String>(stored.getAttributes(token));
			if(attributes.size() != 0) {
				attributes_with_datatypes = getAttributesWithDataTypes(token);
				cl_attributes.put(token, attributes_with_datatypes);
				nlmodeler.setClass_attributes(cl_attributes);
			}
			
			nlmodelers.add(nlmodeler);
		}
		
		return nlmodelers;
	}
	
	
	public LinkedHashMap getAttributesWithDataTypes(String cl) {
		
		ArrayList<String> attributes = stored.getAttributes(cl);
		ArrayList<String> dataType;
		LinkedHashMap<String,String> maped = new LinkedHashMap<String,String>();
		
		int i=0;
		for(String a: attributes) {
			String attr = stored.getDataType(a);
			maped.put(a, attr);
			i++;
		}
		
		return maped;
	}
	
}
