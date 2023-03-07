package net.ent.etrs.ski.view.remontee;

import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.Piste;
import net.ent.etrs.ski.model.entities.Remontee;
import net.ent.etrs.ski.model.facades.FacadePiste;
import net.ent.etrs.ski.model.facades.FacadeRemontee;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

public class LazyDataModelRemontee extends LazyDataModel<Remontee> {
    @Inject
    private FacadeRemontee facade;

    @Override
    public List<Remontee> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        try {
            return this.facade.load(first, pageSize, sortBy, filterBy);
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return this.facade.count(filterBy);
    }
}
