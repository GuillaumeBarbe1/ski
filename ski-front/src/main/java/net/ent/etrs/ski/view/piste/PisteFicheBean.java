package net.ent.etrs.ski.view.piste;

import lombok.Getter;
import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.Remontee;
import net.ent.etrs.ski.model.entities.references.Etat;
import net.ent.etrs.ski.model.facades.FacadePiste;
import net.ent.etrs.ski.model.entities.Piste;
import net.ent.etrs.ski.model.entities.references.Niveau;
import net.ent.etrs.ski.model.facades.FacadeRemontee;
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
public class PisteFicheBean implements Serializable {

    @Inject
    @Getter
    private Piste piste;
    
    @Inject
    private FacadePiste facadePiste;

    @Inject
    private FacadeRemontee facadeRemontee;

    @PostConstruct
    void init() {
        if (FacesContext.getCurrentInstance().getExternalContext().getFlash().get(PisteListeBean.FLASH) != null) {
            Long id = (Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get(PisteListeBean.FLASH);
            try {
                this.piste = this.facadePiste.find(id).get();
            } catch (Exception | BusinessException e) {
                JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR,"Erreur chargement metier");
            }
        }
    }

    public void valider()  {
        try {
            this.facadePiste.save(this.piste);
        } catch (BusinessException e) {
            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR,"Erreur sauvegarde metier");
        }
        JsfUtils.sendMessage(null, FacesMessage.SEVERITY_INFO, "La piste a bien été enregistré !");
    }

    public  List<Remontee> getRemonteesDispo()
    {
        List<Remontee> retour = new ArrayList<>();
        try
        {
            retour = IterableUtils.toList(facadeRemontee.findAll());
        }
        catch (BusinessException e)
        {
            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR,"Erreur chargement remontées");
        }
        return retour;
    }

    public List<Etat> getEtats(){
        return new ArrayList<>(Arrays.asList(Etat.values()));
    }

    public List<Niveau> getNiveaux(){
        return new ArrayList<>(Arrays.asList(Niveau.values()));
    }
}
