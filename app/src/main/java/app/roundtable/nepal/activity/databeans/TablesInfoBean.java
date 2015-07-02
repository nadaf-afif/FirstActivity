package app.roundtable.nepal.activity.databeans;

/**
 * Created by afif on 30/6/15.
 */
public class TablesInfoBean {

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    private String tableName, tableId;
    private boolean isSelected;

}
