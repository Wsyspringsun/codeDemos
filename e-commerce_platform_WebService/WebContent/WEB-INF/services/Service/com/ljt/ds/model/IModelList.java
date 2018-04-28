package ljt.ds.model;

import java.io.Serializable;
import java.util.List;

public interface IModelList<E extends Model<String, Object>> extends List<E>,
        Serializable {

    public enum DataSortRule {

        ASC, DESC
    }

    public enum DataSortType {

        SortAsString, SortAsInteger, SortAsDate
    }

    public enum DataValuationType {

        Equal, NotEqual, GreaterThan, GreaterThanOrEqual, LessThan, LessThanOrEqual
    }

    public int getColCount();

    public int getRowCount();

    public String getColName(int idx);

    public boolean hasRows();

    public void sortBy(String colName, DataSortType sortType,
            DataSortRule sortRule);

    public IModelList<E> select(String colName, DataValuationType valuation,
            Object comparedValue);

    public StringBuilder getDataAsSimpleXML();

    public void writeSimpleXML(String path);

    public E Row(int idx);
}
