package net.ent.etrs.ski.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LazyDataModelUtil {
    
    public static String sortedMapToStr(Map<String, SortMeta> sortBy) {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, SortMeta> e : sortBy.entrySet()) {
            sb.append(e.getValue().getField()).append(":").append(e.getValue().getOrder().equals(SortOrder.ASCENDING) ? "ASC" : "DESC").append(";");
        }
        return LazyDataModelUtil.removeLastSemicolon(sb.toString());
    }
    
    public static String filterMapToStr(Map<String, FilterMeta> filterBy) {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, FilterMeta> e : filterBy.entrySet()) {
            sb.append(e.getValue().getField()).append(":").append(e.getValue().getFilterValue()).append(";");
        }
        return LazyDataModelUtil.removeLastSemicolon(sb.toString());
    }
    
    private static String removeLastSemicolon(String s) {
        if (s.length()>0) {
            s = s.substring(0, s.length()-1);
        }
        return s;
    }
    
}
