package net.ent.etrs.ski.view.forfait;

import lombok.Getter;
import lombok.Setter;
import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.Forfait;
import net.ent.etrs.ski.model.entities.Piste;
import net.ent.etrs.ski.model.entities.Station;
import net.ent.etrs.ski.model.entities.references.Etat;
import net.ent.etrs.ski.model.facades.FacadeForfait;
import net.ent.etrs.ski.model.facades.FacadePiste;
import net.ent.etrs.ski.model.facades.FacadeStation;
import net.ent.etrs.ski.utils.JsfUtils;
import net.ent.etrs.ski.view.station.StationListeBean;
import org.apache.commons.collections4.IterableUtils;
import org.primefaces.model.file.UploadedFile;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.spi.CDI;
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
public class ForfaitFicheBean implements Serializable {


    @Inject
    @Getter
    private Forfait forfait;

    @Inject
    private FacadeForfait facadeForfait;

    @Inject
    private FacadePiste facadePiste;

    @PostConstruct
    void init()  {
        try {
            if (FacesContext.getCurrentInstance().getExternalContext().getFlash().get(ForfaitListeBean.FLASH) != null) {
                Long id = (Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get(ForfaitListeBean.FLASH);
                this.forfait = this.facadeForfait.find(id).get();
            }
        } catch (BusinessException e) {
            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR,"Erreur modification metier");
        }

    }


    public void valider()  {
        try{
            this.facadeForfait.save(this.forfait);
            JsfUtils.sendMessage(null, FacesMessage.SEVERITY_INFO, "Le forfait a bien été enregistré !");
        } catch (Exception | BusinessException e) {
            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR,"Erreur sauvegarde metier");
        }

        this.forfait = CDI.current().select(Forfait.class).get();
    }

    public List<Piste> getPistes()
    {
        List<Piste> retour = new ArrayList<>();
        try
        {
            retour= IterableUtils.toList(facadePiste.findAll());
        }
        catch (BusinessException e)
        {
            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR,"Erreur chargement forfait");
        }
        return retour;
    }
}
