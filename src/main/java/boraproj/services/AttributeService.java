package boraproj.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class AttributeService {
	
	@Autowired
	RepoConnect stored;
	
	public AttributeService() {
		stored = new RepoConnect();
	}
	
	public AttributeService(String path) {
		stored = new RepoConnect(path);
	}
	
	public ArrayList<String> getAttributes(String cl) {
		
//		System.out.println("The attributes for class Teacher are: ");
		return stored.getAttributes(cl);
	}
	
	public String getDataType( String attr) {
		
		return stored.getDataType(attr);
	}
	
	
	public ArrayList<String> getDataTypes( String cl) {
		
		int nr = this.getAttributes(cl).size();
		return stored.getDataTypes(cl,nr);
	}
	

	public ArrayList<String> getAlsoNotDomainAttributes(String cl, ArrayList<String> cur_attr){
		
		ArrayList<String> domain_attributes = new ArrayList<String>();
		domain_attributes = this.getAttributes(cl);
		domain_attributes.removeAll(cur_attr);
		return domain_attributes;
		
	}
	
	public ArrayList<String> getDomainRelevantAttributes(String cl, String domain){
		
		ArrayList<String> all_domain_attributes = new ArrayList<String>();
		ArrayList<String> class_attributes = new ArrayList<String>();
		all_domain_attributes = stored.getAllDomainAttributes(domain);
		class_attributes = this.getAttributes(cl);
		ArrayList<String> domain_attributes = new ArrayList<String>();
		
		for(String atr: class_attributes) {
			if(all_domain_attributes.contains(atr)) {
				domain_attributes.add(atr);
			}
		}

		return domain_attributes;
		
	}
	
//	public LinkedHashMap getAttributesWithDataTypes(String cl) {
//		
//		ArrayList<String> attributes = this.getAttributes(cl);
//		ArrayList<String> dataTypes = this.getDataTypes(cl);
//		LinkedHashMap<String,String> maped = new LinkedHashMap<String,String>();
//		
//		int i=0;
//		for(String a: attributes) {
//			maped.put(a, dataTypes.get(i));
//			i++;
//		}
//		
//		return maped;
//	}

	
	public LinkedHashMap getAttributesWithDataTypes(String cl) {
		
		ArrayList<String> attributes = this.getAttributes(cl);
		ArrayList<String> dataType;
		LinkedHashMap<String,String> maped = new LinkedHashMap<String,String>();
		
		int i=0;
		for(String a: attributes) {
			String attr = this.getDataType(a);
			maped.put(a, attr);
			i++;
		}
		
		return maped;
	}
	
	
	public LinkedHashMap getAttributesWithDataTypesDomainRelevant(String cl, String domain) {
		
		ArrayList<String> attributes = this.getDomainRelevantAttributes(cl, domain);
		ArrayList<String> dataType;
		LinkedHashMap<String,String> maped = new LinkedHashMap<String,String>();
		
		int i=0;
		for(String a: attributes) {
			String attr = this.getDataType(a);
			maped.put(a, attr);
			i++;
		}
		
		return maped;
	}
	
}
