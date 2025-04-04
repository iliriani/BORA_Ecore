package boraproj.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import boraproj.services.AttributeForRecommendations;
import boraproj.services.AttributeService;
import boraproj.services.RepoConnect;

@RestController
public class AttributeGet {

	@Autowired
	RepoConnect stored;
	@Autowired
	AttributeService attr_service;
	@Autowired
	AttributeForRecommendations attr_class_recommendation;
	
	ArrayList<String> retried_ngrams;
	ArrayList<String> model_ngram;
	
	
	public AttributeGet(){
		stored = new RepoConnect();
		attr_service = new AttributeService();
	}
	
	public AttributeGet(String repository) {
		stored = new RepoConnect(repository);
		attr_service = new AttributeService(repository);
		attr_class_recommendation = new AttributeForRecommendations();
	}
	
	@RequestMapping(method=RequestMethod.POST, value="attributes")
	public ArrayList<String> getAttribute(@RequestParam String cl) {
		
//		System.out.println("The attributes for class Teacher are: ");
		return attr_service.getAttributes(cl);
	}
	
	
	public ArrayList<String> getAlsoNotDomainAttributes(String cl, ArrayList<String> cur_attr){
			
			return attr_service.getAlsoNotDomainAttributes(cl, cur_attr);
	}
	
	
	@RequestMapping(method=RequestMethod.POST, value="domainAttributes")
	public ArrayList<String> getDomainRelevantAttributes(@RequestParam String cl,@RequestParam String domain){
		
		return attr_service.getDomainRelevantAttributes(cl, domain);
		
	}
	
//	public ArrayList<String> getDataType( String cl) {
//		
//		return stored.getDataTypes(cl);
//	}
//	
	
	@RequestMapping(method=RequestMethod.POST, value="attributesWithDatatypes")
	public LinkedHashMap getAttributesWithDataTypes(@RequestParam String cl) {
		
		return attr_service.getAttributesWithDataTypes(cl);
	}
	
	
	@RequestMapping(method=RequestMethod.POST, value="attributesWithDatatypesDomainRelevant")
	public LinkedHashMap getDomainAttributesWithDataTypes(@RequestParam String cl, @RequestParam String domain) {
		
		return attr_service.getAttributesWithDataTypesDomainRelevant(cl, domain);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="classRecommendationsFromAttributes")
	public ArrayList getClassRecommendationsFromAttrSimilarities(@RequestParam ArrayList<String> class_attrs) {
		
		return attr_class_recommendation.getClassesWithSameAttributes(class_attrs);
	}
	
}
