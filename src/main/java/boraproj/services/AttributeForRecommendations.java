package boraproj.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import boraproj.controller.NGram;

@Service
public class AttributeForRecommendations {
	
	@Autowired
	RepoConnect stored;
	NGram ngrams;
	RankResults rank;
	
	public AttributeForRecommendations() {
		stored = new RepoConnect();
		ngrams = new NGram();
		rank = new RankResults();
	}
	

	
	public ArrayList<String> getClassesWithSameAttributes(ArrayList<String> attr) {
		
		ArrayList<String> classes = new ArrayList<>(stored.getClassFromAttributes(attr));
		
		ArrayList<String> related_classes = new ArrayList<>();
		
		for(String c: classes) {
//			ArrayList<String> related_classes = new ArrayList<>();
			related_classes.addAll(ngrams.oneGram(c));
		}
		
//		  Rank the results based on the term frequency
//		  HashMap rec = rank.rankNgramResults(entity, retried_ngrams);
//		  ArrayList<String> recommendations = (ArrayList<String>) rec.keySet().stream().collect(Collectors.toList());
		ArrayList<String> related_classes_non_duplicates = new ArrayList<>();
		
		for(String c: related_classes) {
			if(related_classes_non_duplicates.contains(c)) {continue;}
			else {related_classes_non_duplicates.add(c);}
		}
		
		System.out.println("recommendations are: "+related_classes_non_duplicates);
		System.out.println("Total: "+related_classes_non_duplicates.size());
		
		return related_classes_non_duplicates;
		
	}
}
