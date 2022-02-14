package boraproj.evaluation;

import java.util.ArrayList;
import java.util.List;

public class Evaluation_2 {
	
	private ArrayList<String> model_under_construction;
	
	private  ArrayList<String> suggestions;
	private  ArrayList<String> relevant_repo;
	private  ArrayList<String> TP;
	private  ArrayList<String> FP;
	private  ArrayList<String> TN;
	private  ArrayList<String> FN;

	
	
	public Evaluation_2 ( ArrayList<String> model_under_construction, ArrayList<String> relevant_repo , ArrayList<String> suggestion) {
		
		this.model_under_construction = model_under_construction;
		this.relevant_repo = relevant_repo;
		this.suggestions = suggestion;
		
	}
	
	
	public ArrayList<String> getModelUnderConstruction(){
		
		return this.model_under_construction;
	}
	
	public ArrayList<String> getSuggestions(){
		
		return this.suggestions;
	}
	
	public ArrayList<String> getModel_under_construction() {
		return model_under_construction;
	}


	public ArrayList<String> getRelevant_repo() {
		return relevant_repo;
	}
	
	
	public ArrayList<String> getTP() {

		ArrayList<String> common = new ArrayList<String>(this.getSuggestions());
		common.retainAll(model_under_construction);
		this.TP = common;
		return this.TP;
	}



	public ArrayList<String> getFP() {
		
		ArrayList<String> sug = new ArrayList<String>(this.getSuggestions()); 
		System.out.println("Suggestoins are: "+sug);
		ArrayList<String> tp = new ArrayList<String>(this.getTP());
		System.out.println("TP are: "+tp);
		sug.removeAll(tp);
		System.out.println("FP are: "+sug);
				
//		ArrayList<String> m_u_const = model_under_construction;
//		ArrayList<String> common = new ArrayList<String>();
//		common = this.getTP();
////		System.out.println("MUC: "+m_u_const+" common: "+common);
//		m_u_const.removeAll(common);
//		this.FP = m_u_const;
//		System.out.println("FP are: "+this.FP);
//		return this.FP;
		
		return sug;
	}


	public ArrayList<String> getTN() {
		
		ArrayList<String> m_u_const = new ArrayList<String>(this.getModelUnderConstruction());
		ArrayList<String> domain_relevants = new ArrayList<String>(this.getRelevant_repo());
		ArrayList<String> sug = new ArrayList<String>(this.getSuggestions());
		ArrayList<String> not_relevant = new ArrayList<String>();
		domain_relevants.removeAll(m_u_const);
		not_relevant = domain_relevants;
		not_relevant.removeAll(sug);
		
		return not_relevant;
	}



	public ArrayList<String> getFN() {
		
		ArrayList<String> m_u_const = new ArrayList<String>(this.getModelUnderConstruction());
		ArrayList<String> sug = new ArrayList<String>(this.getSuggestions());
		m_u_const.removeAll(sug);
//		System.out.println("FN are: "+m_u_const);
		return m_u_const;
		
//		ArrayList<String> m_u_const = new ArrayList<String>(this.getModelUnderConstruction());
//		ArrayList<String> domain_relevants = new ArrayList<String>(this.relevant_repo);
//		ArrayList<String> sug = new ArrayList<String>(this.suggestions);
//		ArrayList<String> are_in_repo = new ArrayList<String>();
//		domain_relevants.retainAll(m_u_const);
//		are_in_repo = domain_relevants;
//		ArrayList<String> false_negatives = new ArrayList<String>();
//		are_in_repo.removeAll(sug);
//		false_negatives = are_in_repo;
//
//		return false_negatives;
	}



		
	
	public double precision() {
		
		
		double tp = this.getTP().size();
		double fp = this.getFP().size();
		
		System.out.println("TP: "+tp+" FP:"+fp);
		return tp/(tp+fp);
	}
	
	
	public double recall() {
		
		double tp = this.getTP().size();
		double fn = this.getFN().size()-1;
		System.out.println("FN is:"+fn);
				
		return tp/(tp+fn);
	}
	
	public double fMeaure() {
		
		double numeruesi = 2*(this.precision()*this.recall());
		double emeruesi = this.precision() + this.recall();
		
		return numeruesi/emeruesi;
	}
	
	public double mmr() {
		
		return 1;
	}
	
	public double recallRepo() {
		
		double sug = this.getSuggestions().size();
		double repo_rel = this.getRelevant_repo().size();
		
		return sug/repo_rel;
	}
	
	public int reused() {
		
		return this.getTP().size();
	}
}
