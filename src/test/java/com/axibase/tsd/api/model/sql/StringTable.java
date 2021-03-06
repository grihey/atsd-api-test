package com.axibase.tsd.api.model.sql;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.*;

/**
 * @author Igor Shmagrinskiy
 *         <p>
 *         Class for storing SQL result table in {@link String}
 *         objects. It is using custom deserializer
 *         </p>
 */
@JsonDeserialize(using = StringTableDeserializer.class)
public class StringTable {
    private Map<ColumnMetaData, List<String>> tableStructure;
    private int rowsCount = 0;


    public StringTable() {
        tableStructure = new TreeMap<>();
    }


    public void addColumnMetaData(ColumnMetaData columnMetaData) {
        if (tableStructure.containsKey(columnMetaData)) {
            throw new IllegalStateException("Table already contains this columnMetaData");
        }
        tableStructure.put(columnMetaData, new ArrayList<String>());
    }

    public void addRow(ArrayList<String> row) {
        int index = 0;
        for (String cell : row) {
            tableStructure
                    .get(getColumnMetaData(index))
                    .add(cell);
            index++;
        }
        rowsCount++;
    }

    public ColumnMetaData getColumnMetaData(int index) {
        for (ColumnMetaData columnMetaData : tableStructure.keySet()) {
            if (columnMetaData.getColumnIndex() == (index + 1)) {
                return columnMetaData;
            }
        }
        throw new IllegalStateException("Table doesn't contains column with index " + index);
    }

    public List<String> getRow(int index) {
        List<String> row = new ArrayList<>();
        for (ColumnMetaData columnMetaData : tableStructure.keySet()) {
            List<String> columnCells = tableStructure.get(columnMetaData);
            row.add(columnCells.get(index));
        }
        return row;

    }

    public String getValueAt(int i, int j) {
        return tableStructure.get(getColumnMetaData(i)).get(j);
    }

    public List<List<String>> getRows() {
        List<List<String>> rows = new ArrayList<>();
        for (int i = 0; i < rowsCount; i++) {
            rows.add(getRow(i));
        }
        return rows;
    }

    public List<List<String>> getColumns() {
        List<List<String>> columns = new ArrayList<>();
        for (ColumnMetaData columnMetaData : tableStructure.keySet()) {
            columns.add(tableStructure.get(columnMetaData));
        }
        return columns;
    }


    public Set<ColumnMetaData> getColumnsMetaData() {
        return tableStructure.keySet();
    }


    /**
     * Filter row values by column names. Leaves those values, that indexes corresponded
     * with columnNames contained in the set of requested column names
     *
     * @param requestedColumnNames - set of requested column names
     * @return filtered rows
     */
    public List<List<String>> filterRows(Set<String> requestedColumnNames) {
        Set<ColumnMetaData> columnsMetaData = tableStructure.keySet();
        List<Integer> indexesOfRequestedColumns = new ArrayList<>();
        List<List<String>> filteredRows = new ArrayList<>();
        int index = 0;
        for (ColumnMetaData columnMetaData : columnsMetaData) {
            if (requestedColumnNames.contains(columnMetaData.getName())) {
                indexesOfRequestedColumns.add(index);
            }
            index++;
        }
        List<List<String>> rows = getRows();
        for (List<String> row : rows) {
            List<String> filteredRow = new ArrayList<>();
            index = 0;
            for (String cell : row) {
                if (indexesOfRequestedColumns.contains(index)) {
                    filteredRow.add(cell);
                }
                index++;
            }
            filteredRows.add(filteredRow);
        }
        return filteredRows;
    }

    public List<String> columnValues(String requestedColumnName) {
        return filterRows(new HashSet<String>(Arrays.asList(requestedColumnName))).get(0);
    }


    /**
     * Filter row values by column names. Leaves those values, that indexes corresponded
     * with columnNames contained in the set of requested column names
     *
     * @param requestedColumnNames - set of requested column names represented as args
     * @return filtered rows
     */
    public List<List<String>> filterRows(String... requestedColumnNames) {
        Set<String> filter = new HashSet<>();
        for (String columnName : requestedColumnNames) {
            filter.add(columnName);
        }
        return filterRows(filter);
    }
}
