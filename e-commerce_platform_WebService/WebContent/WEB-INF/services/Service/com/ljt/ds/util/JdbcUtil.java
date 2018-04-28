package ljt.ds.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import ljt.ds.model.Model;
import ljt.ds.model.ModelList;

public interface JdbcUtil {

    public List<Map<String, Object>> setResultsetToMap(ResultSet rs)
            throws SQLException;

    public ModelList<Model<String, Object>> setResultsetToDataTable(
            ResultSet rs, int first, int max) throws SQLException;

    public ModelList<Model<String, Object>> setObjectArrayListToDataTable(
            List<Object[]> rs, Map<Integer, String> idxColNameMap);

    public Object convertNclobToObject(Object value) throws SQLException;

    public Object convertClobToObject(Object value) throws SQLException;
}
