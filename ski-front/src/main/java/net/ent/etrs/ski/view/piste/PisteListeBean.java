package net.ent.etrs.ski.view.piste;

import lombok.Getter;
import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.Piste;
import net.ent.etrs.ski.model.entities.references.Etat;
import net.ent.etrs.ski.model.entities.references.Niveau;
import net.ent.etrs.ski.model.facades.FacadePiste;
import net.ent.etrs.ski.utils.JsfUtils;
import org.apache.commons.collections4.IterableUtils;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Named
@ViewScoped
public class PisteListeBean implements Serializable {

    public final static String FLASH = "pistes";
    
    @Inject
    private FacadePiste facadePiste;
    
    @Getter
    private List<Piste> pisteList;
    
    @PostConstruct
    public void init() {
        try {
            this.pisteList = IterableUtils.toList(this.facadePiste.findAll());
        } catch (BusinessException e)
        {
            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR, "Erreur chargement metier");
        }
    }

    public void supprimer(Long id) throws Exception {
        try {
            this.facadePiste.delete(id);
        } catch (BusinessException e) {
            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR,"Erreur suppression metier");
        }
        JsfUtils.sendMessage(null, FacesMessage.SEVERITY_INFO, "La piste a bien été supprimée !");
        this.init();
    }

    public void modifier(Long id) throws Exception {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put(this.FLASH, id);
    }
    
    public List<Niveau> getNiveaux(){
        return new ArrayList<>(Arrays.asList(Niveau.values()));
    }

    public List<Etat> getEtats(){
        return new ArrayList<>(Arrays.asList(Etat.values()));
    }
}
