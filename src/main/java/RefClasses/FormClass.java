package RefClasses;

import java.util.ArrayList;
import java.util.List;

public class FormClass {

	private List<String> baseClass;
	private List<Controls> controls;
	private List<String> MambaFunctions;
	private List<Datasets> dataSets;

	public FormClass() {

		controls = new ArrayList<Controls>();
		MambaFunctions = new ArrayList<>();
		baseClass = new ArrayList<>();
		dataSets = new ArrayList<>();

	}

	public List<Datasets> getDataSets() {
		return dataSets;
	}

	public void setDataSets(ArrayList<Datasets> arrayList) {
		this.dataSets = arrayList;
	}

	public List<Controls> getControls() {
		return controls;
	}

	public void setControls(List<Controls> controls) {
		this.controls = controls;
	}

	public List<String> getMambaFunctions() {
		return MambaFunctions;
	}

	public void setMambaFunctions(List<String> mambaFunctions) {
		MambaFunctions = mambaFunctions;
	}

	public List<String> getBaseClass() {
		return baseClass;
	}

	public void setBaseClass(List<String> baseClass) {
		this.baseClass = baseClass;
	}

}
