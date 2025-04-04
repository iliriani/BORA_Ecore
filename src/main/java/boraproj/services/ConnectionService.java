package boraproj.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConnectionService {
	@Autowired
	RepoConnect stored;

	public ConnectionService() {
		stored = new RepoConnect();
	}
	
	public ConnectionService(String repository) {
		stored = new RepoConnect(repository);
	}
	
	public String[] getConnectionMultiplicity(String cl1, String cl2) {
		return stored.getConnectionMultiplicity(cl1, cl2);
	}
	
	
	public ArrayList<String> getRoleNames(String cl){
		return stored.getRoleNames(cl);
	}
	
	
	public String[] getConnectionRoleNames(String cl1, String cl2) {
		return stored.getConnectionRoleNames(cl1, cl2);
	}
	
	
	
//	Get all connection details
	public String[][] getConnectionDetails(String cl1, String cl2){
		
		ArrayList<String> cl1_roles = this.getRoleNames(cl1);
		ArrayList<String> cl2_roles = this.getRoleNames(cl2);
		
		String[] multiplicity = this.getConnectionMultiplicity(cl1, cl2);
		
		String[] connection_names = this.getConnectionRoleNames(cl1, cl2);
		
		String cl1_rol = "";
		String cl2_rol = "";
		
//		Assign the right role for the connection
		for(String role:cl1_roles) {
			if(role.equals(connection_names[0])) {
				cl1_rol = connection_names[0];
			} 			
		}
		
		if(cl1_rol.equals("")) {
			cl1_rol = connection_names[1]; 
			cl2_rol = connection_names[0]; }
		else {cl2_rol = connection_names[1];}
		
//		for(String role:cl2_roles) {
//			if(role.equals(connection_names[0])) {
//				cl2_rol = connection_names[0];
//			}else {cl2_rol = connection_names[1];}
//		}
		
		String[][] detail = new String[2][2];
		
//		Add the multiplicity data
		detail[0][0] = multiplicity[0];
		detail[1][0] = multiplicity[1];
		
		detail[0][1] = cl1_rol;
		detail[1][1] = cl2_rol;
		
				
		return detail;
	}
	
//	public String getCommonRole(ArrayList<String> roles, String[] connRoles) {
//		
//		for(String role:cl1_roles) {
//			if(role.equals(connection_names[0])) {
//				cl1_rol = connection_names[0];
//			}else {cl1_rol = connection_names[1];}
//		}
//		
//	}
	
}
