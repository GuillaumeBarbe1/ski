package net.ent.etrs.ski.view.piste;

import net.ent.etrs.ski.model.entities.Piste;
import net.ent.etrs.ski.model.facades.FacadePiste;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

public class LazyDataModelPiste extends LazyDataModel<Piste> {
    
    @Inject
    private FacadePiste facade;
    
    @Override
    public List<Piste> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        return this.facade.load(first, pageSize, sortBy, filterBy);
    }
    
    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return this.facade.count(filterBy);
    }
}
