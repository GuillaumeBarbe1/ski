package net.ent.etrs.ski.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LazyDataUtil {
    
    public static String sortedByMapToStr(Map<String, SortMeta> sortBy){
        StringBuilder result = new StringBuilder();
        for(Map.Entry<String, SortMeta> e : sortBy.entrySet()){
            result.append(e.getValue().getField()).append(":").append(e.getValue().getOrder().equals(SortOrder.ASCENDING) ? "ASC" : "DESC").append(";");
        }
        return LazyDataUtil.removeLastSemicolon(result.toString());
    }
    
    public static String filterByMapToStr(Map<String, FilterMeta> filterBy){
        StringBuilder result = new StringBuilder();
        for(Map.Entry<String, FilterMeta> e : filterBy.entrySet()){
            result.append(e.getValue().getField()).append(":").append(e.getValue().getFilterValue()).append(";");
        }
        return LazyDataUtil.removeLastSemicolon(result.toString());
    }
    
    private static String removeLastSemicolon(String s) {
        if(s.length()>0){
            s = s.substring(0, s.length() -1);
        }
        return s;
    }

}
