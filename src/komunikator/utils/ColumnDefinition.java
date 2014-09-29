package komunikator.utils;

public class ColumnDefinition {
	private String name;
	private ColumnType columnType;
	private ColumnConstraint[] constraints;

	public ColumnDefinition(String name, ColumnType columnType,
			ColumnConstraint... constraints) {
		this.name = name;
		this.columnType = columnType;
		this.constraints = constraints;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ColumnType getColumnType() {
		return columnType;
	}

	public void setColumnType(ColumnType columnType) {
		this.columnType = columnType;
	}

	public ColumnConstraint[] getConstraints() {
		return constraints;
	}

	public void setConstraints(ColumnConstraint... constraints) {
		this.constraints = constraints;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(name + " " + columnType + " ");
		for(ColumnConstraint c: constraints)
			sb.append(c+" ");
		return sb.toString();
	}
}
