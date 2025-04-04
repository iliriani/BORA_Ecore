package boraproj.services;

public enum Type {
	
		PERSON("Person"),
		CITY("City"),
		STATE_OR_PROVINCE("State_or_province"),
		COUNTRY("Country"),
		EMAIL("Email"),
		TITLE("Title"),
		NAME("name");
		
	private String type;
	
	Type(String type){
		this.type = type;
	}
	
	public String getName() {
		return type;
	}
	
}
