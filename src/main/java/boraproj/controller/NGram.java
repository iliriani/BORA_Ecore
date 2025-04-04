package boraproj.controller;
import java.util.*;
import java.util.stream.Collectors;
import java.lang.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.jena.sparql.function.library.leviathan.sec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import boraproj.services.AddWeights;
import boraproj.services.RankResults;
import boraproj.services.RepoConnect;

@RestController
public class NGram {
	
	@Autowired
	RepoConnect stored;
//	String directory;
	@Autowired
	RankResults rank;
	ArrayList<String> retried_ngrams;
	ArrayList<String> model_ngram;
	AddWeights weights;

	public NGram() {
		this.stored = new RepoConnect();
		this.rank = new RankResults();
		this.weights = new AddWeights();
		
	}
	
	public NGram(String directory) {
		this.stored = new RepoConnect(directory);
		this.rank = new RankResults(directory);
		this.weights = new AddWeights(directory);
//		this.directory = directory;
	}

	public void compare(String entity) {

		model_ngram = new ArrayList<String>();
		model_ngram.add(entity);
		model_ngram.add("Notebook");
		model_ngram.add("Teacher");

		retried_ngrams = stored.getRelatedClasses(entity);

//		retried_ngrams.removeAll(model_ngram);

		ArrayList<String> recommendations = new ArrayList<String>();
		System.out.println("You might have to add to your model: ");
		for (String item : retried_ngrams) {
			if (!model_ngram.contains(item)) {
				recommendations.add(item);

			}
		}

		for (String i : recommendations) {
			System.out.println(i);
		}

	}
	
	
	
	public ArrayList<String> getDomainClasses(String domain) {

		ArrayList<String> domain_classes = new ArrayList<String>();
		domain_classes = stored.getRelatedDomainClasses(domain);
//		if (domain_classes.size() != 0) {
//			System.out.println("All classes for "+domain+" are " + retried_ngrams.toString());
//			
//		}else {System.out.println("Nothing is found in the repository!");}

		return domain_classes;
	}

	@RequestMapping("/hello")
	public String sayHello() {
		return "Selam!";
	}
	
	
	
	@RequestMapping(method=RequestMethod.POST, value="onegram")
	public ArrayList<String> oneGram(@RequestParam String entity) {

		retried_ngrams = stored.getRelatedClasses(entity);
		retried_ngrams.remove(entity);
		  if (retried_ngrams.size() != 0) {
		  System.out.println("1-gram recommendations: : " + retried_ngrams.toString());
		  
		  } else { System.out.println("Nothing is found in the repository!"); }
		  
		  
//		  Rank the results based on the term frequency
		  HashMap rec = rank.rankNgramResults(entity, retried_ngrams);
		  ArrayList<String> recommendations = (ArrayList<String>) rec.keySet().stream().collect(Collectors.toList());
		  
//		  Collections.sort(retried_ngrams);
		  System.out.println("Ranked rec: "+recommendations);
		return recommendations;
	}


	@RequestMapping(method=RequestMethod.POST, value="onegramdomainrelevant")
	public ArrayList<String> getOneGramDomainRelevant(@RequestParam String cl, @RequestParam String domain) {


		//Get all domain classes
		ArrayList<String> domain_classes = new ArrayList<String>();
		domain_classes = getDomainClasses(domain);


		if (domain_classes.size() != 0) {
			System.out.println("The domain is \"" + domain + "\"");

		} else {
			System.out.println("No domain is found!");
		}

		System.out.println("-----------------------------");

		ArrayList<String> n_gram_not_domain = oneGram(cl);
		
		// Remove not domain-specific classes
		ArrayList<String> recommend_domain_relevant = new ArrayList<String>();
		for (int i = 0; i < n_gram_not_domain.size(); i++) {
			if (domain_classes.contains(n_gram_not_domain.get(i))) {
				recommend_domain_relevant.add(n_gram_not_domain.get(i));
//				System.out.println("removed: "+n_gram_not_domain.get(i));
			}
		}
		
//		Collections.sort(recommend_domain_relevant);
		System.out.println("1-grams recommendations are: "+recommend_domain_relevant);

		/*
		 * for (String cl : recommend_domain_relevant) { System.out.println(cl); }
		 */
		
//		  Rank the results based on the term frequency
		  HashMap rec = rank.rankNgramResults(cl, recommend_domain_relevant);
		  ArrayList<String> recommendations = (ArrayList<String>) rec.keySet().stream().collect(Collectors.toList());
		  
		  System.out.println("Ranked rec: "+recommendations);
		  
		return recommendations;
	}
	
	
	@RequestMapping(method=RequestMethod.POST, value="twogramdomainrelevant")
	public ArrayList<String> getTwoGramDomainRelevant(@RequestParam String class1, @RequestParam String class2) {

		// Get domain for the given two classes
		retried_ngrams = stored.getDomain(class1, class2);

		System.out.println("Domain relevant recommendation for classes: " + class1 + " and " + class2);
		System.out.println("----------------------------");

		String domain = retried_ngrams.get(0);
		
		//Get all domain classes
		ArrayList<String> domain_classes = new ArrayList<String>();
		domain_classes = getDomainClasses(domain);


		if (retried_ngrams.size() != 0) {
			System.out.println("The domain is \"" + domain + "\"");

		} else {
			System.out.println("No domain is found!");
		}

		System.out.println("-----------------------------");

		ArrayList<String> n_gram_not_domain = twoGram(class1, class2);
//		System.out.println("Not relevant classes: "+n_gram_not_domain); System.out.println("------------------");
		
		// Remove not domain-specific classes
		ArrayList<String> recommend_domain_relevant = new ArrayList<String>();
		for (int i = 0; i < n_gram_not_domain.size(); i++) {
			if (domain_classes.contains(n_gram_not_domain.get(i))) {
				recommend_domain_relevant.add(n_gram_not_domain.get(i));
//				System.out.println("removed: "+n_gram_not_domain.get(i));
			}
		}
		
//		Collections.sort(recommend_domain_relevant);
		System.out.println("2-grams recommendations are: "+recommend_domain_relevant);

		/*
		 * for (String cl : recommend_domain_relevant) { System.out.println(cl); }
		 */
		
//		  Rank the results based on the term frequency
		  HashMap rec1 = rank.rankNgramResults(class1, recommend_domain_relevant);
		  HashMap rec2 = rank.rankNgramResults(class2, recommend_domain_relevant);
		  rec1.putAll(rec2);
		  
//		  rec2.forEach(
//				    (key, value) -> rec1.merge( key, value, (v1, v2) -> v1.equals(v2) ? v1 : v1 + "," + v2)
//				);
		  HashMap rec = rank.sortByValue(rec1); 
		  
		  ArrayList<String> recommendations = (ArrayList<String>) rec.keySet().stream().collect(Collectors.toList());
		  
		  System.out.println("Ranked results: "+recommendations);
		  
		return recommendations;
	}
	
	
	@RequestMapping(method=RequestMethod.POST, value="threegramdomainrelevant")
	public ArrayList<String> getThreeGramDomainRelevant(@RequestParam String class1, @RequestParam String class2, @RequestParam String class3) {
		
		//Getting the common comain from two 2-grams
		ArrayList<String> firstDomains = stored.getDomain(class1, class2);
		ArrayList<String> secondDomains = stored.getDomain(class2, class3);
		firstDomains.retainAll(secondDomains);
		String domain = firstDomains.get(0);
		
		//Get all domain relevant classes
		ArrayList<String> domain_classes = new ArrayList<String>();
		domain_classes = getDomainClasses(domain);


		if (domain_classes.size() != 0) {
			System.out.println("The domain is \"" + domain + "\"");

		} else {
			System.out.println("No domain is found!");
		}

		System.out.println("-----------------------------");
		
		ArrayList<String> n_gram_not_domain = threeGram(class1, class2, class3);
		
		// Remove not domain-specific classes
				ArrayList<String> recommend_domain_relevant = new ArrayList<String>();
				for (int i = 0; i < n_gram_not_domain.size(); i++) {
					if (domain_classes.contains(n_gram_not_domain.get(i))) {
						recommend_domain_relevant.add(n_gram_not_domain.get(i));
					}
				}
				
//				Collections.sort(recommend_domain_relevant);
				
				System.out.println("3-grams recommendations are: "+ recommend_domain_relevant.toString());

				/*
				 * for (String cl : recommend_domain_relevant) { System.out.println(cl); }
				 */
				
				  HashMap rec1 = rank.rankNgramResults(class1, recommend_domain_relevant);
				  HashMap rec2 = rank.rankNgramResults(class3, recommend_domain_relevant);
				  rec1.putAll(rec2);
				  HashMap rec = rank.sortByValue(rec1); 
				  
				  ArrayList<String> recommendations = (ArrayList<String>) rec.keySet().stream().collect(Collectors.toList());
				  
				  System.out.println("Ranked rec are: "+recommendations);
		
		
		return recommendations;
	}


	@RequestMapping(method=RequestMethod.POST, value="twogram")
//	public ArrayList<String> twoGram(ArrayList<String> entity) {
	public ArrayList<String> twoGram(@RequestParam String class1, @RequestParam String class2) {
		// Split the 2-gram elemets
		model_ngram = new ArrayList<String>();
//		String firstEntity = entity.get(0);
//		String secondEntity = entity.get(1);

//		System.out.println(firstEntity+" "+secondEntity);

		ArrayList<String> items = stored.query_twograms(class1, class2);
		items.remove(class1);
		items.remove(class2);

		/*
		 * StoretoTDB store1 = new StoretoTDB(directory,firstEntity); Thread t1 = new
		 * Thread(store1); t1.start(); ArrayList<String> retrievedFirst =
		 * store1.retrievedItems;
		 * 
		 * StoretoTDB store2 = new StoretoTDB(directory,secondEntity); Thread t2 = new
		 * Thread(store2); t2.start(); ArrayList<String> retrievedSecond =
		 * store2.retrievedItems;
		 * 
		 */

		/*
		 * //Retrieve all releted classes with the respective 2-gram elements
		 * ArrayList<String> retrievedFirst = stored.query(firstEntity);
		 * retrievedFirst.add(firstEntity); ArrayList<String> retrievedSecond =
		 * stored.query(secondEntity); retrievedSecond.add(secondEntity);
		 * 
		 * 
		 * 
		 * //Remove the same classes retrieved from each 2-gram element
		 * ArrayList<String> recommendations = new ArrayList<String>();
		 * System.out.println("You might have to add to your model: "); for(String item
		 * : retrievedFirst) { if(!retrievedSecond.contains(item)) {
		 * recommendations.add(item);
		 * 
		 * } }
		 */

//		  retrievedFirst.addAll(retrievedSecond);
//		System.out.println("You might want to add also: ");
//		for (String i : items) {
//			System.out.println(i);
//		}
		
//		Collections.sort(items);
		
		  HashMap rec1 = rank.rankNgramResults(class1, items);
		  HashMap rec2 = rank.rankNgramResults(class2, items);
		  rec1.putAll(rec2);
		  HashMap rec = rank.sortByValue(rec1); 
		  
		  ArrayList<String> recommendations = (ArrayList<String>) rec.keySet().stream().collect(Collectors.toList());
		  
		  System.out.println("Ranked rec are: "+recommendations);
		
		
		return recommendations;

	}

	@RequestMapping(method=RequestMethod.POST, value="threeGram")
	public ArrayList<String> threeGram(@RequestParam String class1, @RequestParam String class2, @RequestParam String class3) {
		// Split the 2-gram elemets
		model_ngram = new ArrayList<String>();
		String firstEntity = class1;
		String thirdEntity = class3;
//		System.out.println(firstEntity+" "+secondEntity);

		ArrayList<String> items = stored.query_twograms(firstEntity, thirdEntity);
		items.remove(firstEntity);
		items.remove(thirdEntity);
		items.remove(class2);

//		  retrievedFirst.addAll(retrievedSecond);
//		for (String i : items) {
//			System.out.println(i);
//		}
		
//		Collections.sort(items);
		
		  HashMap rec1 = rank.rankNgramResults(class1, items);
		  HashMap rec2 = rank.rankNgramResults(class3, items);
		  rec1.putAll(rec2);
		  HashMap rec = rank.sortByValue(rec1); 
		  
		  ArrayList<String> recommendations = (ArrayList<String>) rec.keySet().stream().collect(Collectors.toList());
		  
		  System.out.println("Ranked rec are: "+recommendations);
		
		return recommendations;

	}
	
	
	@RequestMapping(method=RequestMethod.POST, value="islands")
	public ArrayList<String> getIslandClasses(@RequestParam String domain) {

		ArrayList<String> items = stored.getIslandClasses(domain);
		return items;

	}
	
	@RequestMapping(method=RequestMethod.POST, value="all_domain_classes")
	public ArrayList<String> getAllDomainClasses(String domain) {

		ArrayList<String> items = stored.getAllDomainClasses(domain);
		return items;

	}
	
	@RequestMapping(method=RequestMethod.POST, value="all_classes")
	public ArrayList<String> getAllClasses() {

		ArrayList<String> items = stored.getAllClassesFromKG();
		return items;

	}
	
	/*
	 * // Get the frequency weight of each recommendations and recommend the highest
	 * weight first
	 * 
	 * @RequestMapping(method=RequestMethod.POST, value="rankedResults") public
	 * HashMap rankNgramResults(@RequestParam String cl,@RequestParam
	 * ArrayList<String> results) { // public HashMap rankNgramResults(String cl,
	 * ArrayList<String> results) {
	 * 
	 * HashMap recommendations = new HashMap<String, Integer>(); for(String rec:
	 * results) { int w = weights.getWeight(cl, rec); recommendations.put(rec, w); }
	 * 
	 * recommendations = this.sortByValue(recommendations);
	 * 
	 * return recommendations;
	 * 
	 * }
	 * 
	 * // function to sort hashmap by values public static HashMap<String, Integer>
	 * sortByValue(HashMap<String, Integer> hm) { // Create a list from elements of
	 * HashMap List<Map.Entry<String, Integer> > list = new
	 * LinkedList<Map.Entry<String, Integer> >(hm.entrySet());
	 * 
	 * // Sort the list Collections.sort(list, Collections.reverseOrder(new
	 * Comparator<Map.Entry<String, Integer> >() { public int
	 * compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
	 * return (o1.getValue()).compareTo(o2.getValue()); } }));
	 * 
	 * 
	 * // put data from sorted list to hashmap HashMap<String, Integer> temp = new
	 * LinkedHashMap<String, Integer>(); for (Map.Entry<String, Integer> aa : list)
	 * { temp.put(aa.getKey(), aa.getValue()); } return temp;
	 * 
	 * // Map<String, Integer> sorted = hm // .entrySet() // .stream() //
	 * .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())) // .collect(
	 * // toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, //
	 * LinkedHashMap::new)); // return sorted;
	 * 
	 * }
	 * 
	 */
    	 
    
}
