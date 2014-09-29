package komunikator.utils;

public enum ColumnType {
	NULL("null"),
	BLOB("blob"),
	INTEGER("integer"), 
	REAL("real"), 
	TEXT("text");
	
	private final String val;
	
	ColumnType(String val){
		this.val = val;
	}
	
	public String toString(){
		return val;
	}

}
