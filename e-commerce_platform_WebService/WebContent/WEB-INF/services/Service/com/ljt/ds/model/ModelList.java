package ljt.ds.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.Set;


public class ModelList<E extends Model<String, Object>> extends ArrayList<E> implements IModelList<E>, Serializable {

	private static final long serialVersionUID = 1L;
    private int mColCount = -1;
    private Set<String> mColSet = null;

	/**
     * 总件数
     */
    private long totalRow = 0;

    /**
     * 表示番号
     */
    private int displayPage = 1;

    /**
     * 表示件数
     */
    private int displayRow = 0;

    /**
     * 总件数
     *
     * @param totalRow 总件数
     */
    public void setTotalRow(long totalRow) {
        this.totalRow = totalRow;
    }

    /**
     * 总件数
     *
     * @return 总件数
     */
    public long getTotalRow() {
        return totalRow;
    }

    /**
     * 表示番号
     *
     * @param displayPage 表示番号
     */
    public void setDisplayPage(int displayPage) {
        this.displayPage = displayPage;
    }

    /**
     * 表示番号
     *
     * @return 表示番号
     */
    public int getDisplayPage() {
        return displayPage;
    }

    /**
     * 表示件数
     *
     * @param displayRow 表示件数
     */
    public void setDisplayRow(int displayRow) {
        this.displayRow = displayRow;
    }

    /**
     * 表示件数
     *
     * @return 表示件数
     */
    public int getDisplayRow() {
        return displayRow;
    }

    @Override
	public int getColCount() {
        if (this.mColCount == -1) {
            if (this.size() == 0) {
                this.mColCount = 0;
            } else {
                this.mColCount = (this.get(0)).size();
            }
        }
        return this.mColCount;
    }


    @Override
	public int getRowCount() {
        return this.size();
    }

    @Override
    public String getColName(int idx) {
        if (this.mColCount == -1) {
            this.getColCount();
        }
        if (this.mColCount == 0) {
            return null;
        }
        if (this.mColSet == null) {
            mColSet = (this.get(0)).keySet();
        }
        return mColSet.toArray()[idx].toString();
    }

    @Override
	public E Row(int idx) {
        return this.get(idx);
    }

    @Override
	public boolean hasRows() {
        if (this.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
	public StringBuilder getDataAsSimpleXML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"GB2312\" standalone=\"yes\" ?>\n");
        sb.append("<getData>\n");

        for (int i = 0; i < this.size(); i++) {
            E r = this.get(i);
            sb.append("<row>\n");
            for (Entry<String, Object> entry : r.entrySet()) {
                sb.append("\t<").append(entry.getKey()).append(">");
                if (null == entry.getValue()) {
                    sb.append("(null)");
                } else {
                    sb.append(entry.getValue().toString());
                }
                sb.append("</").append(entry.getKey()).append(">\n");
            }
            sb.append("</row>\n");
        }
        sb.append("</getData>\n");
        return sb;
    }

    @Override
	public void writeSimpleXML(String path) {
        PrintWriter pw = null;
        try {
            File file = new File(path);
            pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            try {
                pw.println(getDataAsSimpleXML().toString());
            } finally {
                pw.close();
            }
        } catch (IOException ex) {
            //
        } finally {
            pw.close();
        }
    }

    @Override
	@SuppressWarnings({"rawtypes", "unchecked"})
    public void sortBy(final String colName, final DataSortType sortType,
            final DataSortRule sortRule) {

        if (this.getColCount() == 0) {
            return;
        }
        Collections.sort(this, new Comparator<E>() {

            @Override
            public int compare(E o1, E o2) {
                Object oVal1 = o1.getValueByName(colName);
                Object oVal2 = o2.getValueByName(colName);

                if (sortType == DataSortType.SortAsInteger) {
                    return sortRule == DataSortRule.ASC ? (Integer) oVal1
                            - (Integer) oVal2 : (Integer) oVal2
                            - (Integer) oVal1;
                }
                if (sortType == DataSortType.SortAsString
                        || sortType == DataSortType.SortAsDate) {
                    if (oVal1 == null && oVal2 == null) {
                        return 0;
                    } else if (oVal1 == null) {
                        return sortRule == DataSortRule.ASC ? 1 : -1;
                    } else if (oVal2 == null) {
                        return sortRule == DataSortRule.ASC ? -1 : 1;
                    }
                    if (sortRule == DataSortRule.ASC) {
                        return ((Comparable) oVal1).compareTo(oVal2) * 1;
                    } else {
                        return ((Comparable) oVal1).compareTo(oVal2) * -1;
                    }
                }
                return 0;
            }
        });
    }

    @Override
	@SuppressWarnings({"rawtypes", "unchecked"})
    public IModelList<E> select(String colName, DataValuationType valuation,
            Object comparedValue) {

    	IModelList<E> selected = new ModelList<E>();
        int compareRtn = 0;
        boolean isHit = false;

        for (int i = 0; i <= this.getRowCount() - 1; i++) {
            Object val = this.Row(i).getValueByName(colName);
            if (val == null) {
                continue;
            }
            compareRtn = ((Comparable) val).compareTo(comparedValue);

            isHit = false;
            if (compareRtn == 0) {
                if (valuation == DataValuationType.Equal
                        || valuation == DataValuationType.GreaterThanOrEqual
                        || valuation == DataValuationType.LessThanOrEqual) {
                    isHit = true;
                }
            } else if (compareRtn < 0) {
                if (valuation == DataValuationType.NotEqual
                        || valuation == DataValuationType.LessThan
                        || valuation == DataValuationType.LessThanOrEqual) {
                    isHit = true;
                }
            } else if (compareRtn > 0) {
                if (valuation == DataValuationType.NotEqual
                        || valuation == DataValuationType.GreaterThan
                        || valuation == DataValuationType.GreaterThanOrEqual) {
                    isHit = true;
                }
            }
            if (isHit) {
                selected.add(this.Row(i));
            }

        }
        return selected;
    }

}
