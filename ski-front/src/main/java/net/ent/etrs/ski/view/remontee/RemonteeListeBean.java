package net.ent.etrs.ski.view.remontee;


import lombok.Getter;
import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.references.Etat;
import net.ent.etrs.ski.model.facades.FacadeRemontee;
import net.ent.etrs.ski.utils.JsfUtils;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Named
@ViewScoped
public class RemonteeListeBean implements Serializable {

    public final static String FLASH = "remontees";

    @Inject
    private FacadeRemontee facadeRemontee;

    @Getter
    @Inject
    private LazyDataModelRemontee remonteeList;

    @PostConstruct
    public void init() {
    
    }

    public void supprimer(Long id) throws Exception {
        try {
            this.facadeRemontee.delete(id);
        } catch (BusinessException e) {
            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR, "Erreur suppression metier");
        }
        JsfUtils.sendMessage(null, FacesMessage.SEVERITY_INFO, "La remontée a bien été supprimé !");
        this.init();
    }

    public void modifier(Long id) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put(this.FLASH, id);
        } catch (Exception e) {
            JsfUtils.sendMessage(e);
        }

    }

    public List<Etat> getEtats() {
        return Arrays.asList(Etat.values());
    }
}
