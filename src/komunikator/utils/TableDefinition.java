package komunikator.utils;

import java.util.ArrayList;
import java.util.List;

import komunikator.profile.ProfileDAO;

public class TableDefinition {
	private List<ColumnDefinition> columnDefinitions;
	private String tableName;

	public static final String idColumnName = "_id";

	public TableDefinition(String name) {
		tableName = name;
		columnDefinitions = new ArrayList<ColumnDefinition>();
		addColumn(idColumnName, ColumnType.INTEGER,
				ColumnConstraint.PRIMARY_KEY, ColumnConstraint.AUTO_INCREMENT);
	}

	public void addColumn(String name, ColumnType columnType,
			ColumnConstraint... constraints) {
		columnDefinitions.add(new ColumnDefinition(name, columnType,
				constraints));
	}

	public void addColumn(ColumnDefinition definition) {
		columnDefinitions.add(definition);
	}

	public ColumnDefinition getColumn(String name) {
		int index = getIndex(name);
		if (index == -1)
			return null;
		return columnDefinitions.get(index);
	}

	public int getIndex(String name) {
		for (int i = 0; i < columnDefinitions.size(); i++)
			if (columnDefinitions.get(i).getName().equals(name))
				return i;
		return -1;
	}

	public String getTableName() {
		return tableName;
	}

	public String getCreateQuery() {
		StringBuilder sb = new StringBuilder("create table " + tableName + " (");
		for (int i = 0; i < columnDefinitions.size() - 1; i++)
			sb.append(columnDefinitions.get(i).toString() + ",");
		sb.append(columnDefinitions.get(columnDefinitions.size() - 1)
				.toString());
		sb.append(");");
		return sb.toString();
	}

	public String getDropQuery() {
		return "drop table if exists " + tableName + ";";
	}

}
