package net.ent.etrs.ski.view.forfait;

import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.Forfait;
import net.ent.etrs.ski.model.entities.Piste;
import net.ent.etrs.ski.model.facades.FacadeForfait;
import net.ent.etrs.ski.model.facades.FacadePiste;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

public class LazyDataModelForfait extends LazyDataModel<Forfait> {

    @Inject
    private FacadeForfait facade;

    @Override
    public List<Forfait> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
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
