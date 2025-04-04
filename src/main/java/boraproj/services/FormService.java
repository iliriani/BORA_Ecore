package boraproj.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import RefClasses.Controls;
import RefClasses.Datasets;
import RefClasses.FormClass;

@Service
public class FormService {
	
	private FormConnection conn;
	private FormClass formClass;
	private RepoConnect repoConnect;
	
	public FormService() {
		conn = new FormConnection();
		formClass = new FormClass();
		repoConnect = new RepoConnect();
	}
	
	public List<String> getDSLFunctions(String cl) {
		return conn.getAllDSLFunctions(cl);
	}
	
	public ArrayList<Controls> getControlInfo(String cl){
		return conn.getControlsInfo(cl);
	}
	
	
	public List<String> getBaseClass(String cl) {
		return repoConnect.getBaseClasses(cl);

	}
	
	public ArrayList<Datasets> getDataSets(String cl) {
		return conn.getDataSetsInfo(cl);
	}
	
	public FormClass initialiForm(String cl) {
		formClass.setBaseClass(getBaseClass(cl));
		formClass.setControls(getControlInfo(cl));
		formClass.setDataSets(getDataSets(cl));
		formClass.setMambaFunctions(getDSLFunctions(cl));

		
		return formClass;

	}
}
