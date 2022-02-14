package boraproj.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class RankResults {
	
	@Autowired
	AddWeights weights;
	
	
	public RankResults() {
		weights = new AddWeights();
	}
	
	public RankResults(String repository){
		
		weights = new AddWeights(repository);
	}
	
	
	
	public HashMap rankNgramResults(String cl, ArrayList<String> results) {
//		public HashMap rankNgramResults(String cl, ArrayList<String> results) {
			
			HashMap recommendations = new HashMap<String, Integer>();
			for(String rec: results) {
				int w = weights.getWeight(cl, rec);
				recommendations.put(rec, w);
			}
			
			recommendations = this.sortByValue(recommendations);
			
			return recommendations;

		}
		
	    // function to sort hashmap by values
	    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm)
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
	        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
	        for (Map.Entry<String, Integer> aa : list) {
	            temp.put(aa.getKey(), aa.getValue());
	        }
	        return temp;
	        
//	    	Map<String, Integer> sorted = hm
//	                .entrySet()
//	                .stream()
//	                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
//	                .collect(
//	                    toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
//	                        LinkedHashMap::new));
//	        return sorted;
	        
	    }

}
