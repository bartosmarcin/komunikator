package komunikator.utils;

public enum ColumnConstraint {
	PRIMARY_KEY("primary key"),
	AUTO_INCREMENT("autoincrement");
	
	private final String val;
	
	private ColumnConstraint(String val){
		this.val = val;		
	}
	public String toString(){
		return val;
	}
}
