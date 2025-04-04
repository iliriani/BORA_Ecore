package RefClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class NLModeler {

	private String domain;
	private ArrayList<String> tokens;
	private ArrayList<String> classes;
	private HashMap<String,LinkedHashMap<String, String>> class_attributes;
	

	public NLModeler() {}
	
	public NLModeler(String domain) {
		this.domain = domain;
	}


	public String getDomain() {
		return domain;
	}


	public void setDomain(String domain) {
		this.domain = domain;
	}


	public ArrayList<String> getTokens() {
		return tokens;
	}


	public void setTokens(ArrayList<String> tokens) {
		this.tokens = tokens;
	}


	public ArrayList<String> getClasses() {
		return classes;
	}


	public void setClasses(ArrayList<String> classes) {
		this.classes = classes;
	}
	
	public HashMap<String,LinkedHashMap<String, String>> getClass_attributes() {
		return class_attributes;
	}

	public void setClass_attributes(HashMap<String,LinkedHashMap<String, String>> class_attributes) {
		this.class_attributes = class_attributes;
	}
	
	
}
