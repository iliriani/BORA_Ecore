package RefClasses;

import java.util.ArrayList;
import java.util.List;

public class Controls {
	
	private String name;
	private String datasource;
	private String type;

	
	public Controls() {
//		type = new ArrayList<>();
//		datasource = new ArrayList<>();
//		name = new ArrayList<>();
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDatasource() {
		return datasource;
	}
	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
