package boraproj.services;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddWeights {
	
	@Autowired
	RepoConnect repoCon;
	
	
	public AddWeights() {
		repoCon = new RepoConnect();
	}
	
	public AddWeights(String path) {
		repoCon = new RepoConnect(path);
	}
	
	
	//Get all classes from the repository
	public ArrayList getAllClasses() {
		
		ArrayList<String> classes =  new ArrayList<String>();
		classes = repoCon.getAllClasses();
//		System.out.println(classes.size());
		return classes;
		
	}
	
	
//	Getting all connected classes to Class cl
	public ArrayList getClassConnections(String cl) {
		
		ArrayList<String> classes =  new ArrayList<String>();
		classes = repoCon.getRelatedClasses(cl);
//		System.out.println(classes.size());
		return classes;
		
	}
	
	
//	Getting the number of how much class2 appears after class1
	public int getConnectionFrekuency(String class1, String class2) {
		
		ArrayList<String> number =  new ArrayList<String>();
		number = repoCon.getConnectionFrekueny(class1,class2);
//		System.out.println(number);
		int nr = Integer.parseInt(number.get(0));  
		return nr;
		
	}
	
//	Add respective weights for cl1 and cl2
	public void addWeights(String cl1, String cl2, int w) {
		
		repoCon.insertWeight(cl1, cl2, w);
	}
	
//	Get the weights for between Class cl1 and cl2
	public int getWeight(String cl1, String cl2) {
		
		ArrayList<String> w = repoCon.getWeights(cl1, cl2);
		int nr;
		if(w.isEmpty()) {nr = 0;}
		else { nr = Integer.parseInt(w.get(0)); }
		return nr;
	}
	
	
//	Updates dhe weight of the given class connection
	public void updateWeight(String main_class, ArrayList<String> selected_classes) {
		

		for(String cl:selected_classes) {
			int w = this.getWeight(main_class, cl);
			int updated = w+1;
			System.out.println(main_class+"  "+cl+"  :"+w);
			repoCon.updateWeight(main_class, cl, w,updated);
			
		}
		
	}
	
	
//	Updates dhe weight of the given class connection
	public void undoUpdateWeight(String main_class, ArrayList<String> selected_classes) {
		

		for(String cl:selected_classes) {
			int w = this.getWeight(main_class, cl);
			int updated = w-1;
			System.out.println(main_class+"  "+cl+"  :"+w);
			repoCon.updateWeight(main_class, cl, w,updated);
			
		}
		
	}
	
	
//	Fill the repository with weights based on their appearance frekuency
	public void insertAllWeight() {
		
		ArrayList<String> classes = this.getAllClasses();
		
		for (String cl: classes) {
			ArrayList<String> conn = this.getClassConnections(cl);
			for(String con: conn) {
				if(this.getWeight(cl, con) == 0) {
					int f = this.getConnectionFrekuency(cl, con);
					this.addWeights(cl, con, f);
				}
				
//				else {continue; }
			}
			
			
		}
	}

}
