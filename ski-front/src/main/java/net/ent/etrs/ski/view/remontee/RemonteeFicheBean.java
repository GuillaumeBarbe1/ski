package net.ent.etrs.ski.view.remontee;

import lombok.Getter;
import lombok.Setter;
import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.Piste;
import net.ent.etrs.ski.model.entities.Remontee;
import net.ent.etrs.ski.model.entities.Station;
import net.ent.etrs.ski.model.entities.references.Etat;
import net.ent.etrs.ski.model.facades.FacadePiste;
import net.ent.etrs.ski.model.facades.FacadeRemontee;
import net.ent.etrs.ski.model.facades.FacadeStation;
import net.ent.etrs.ski.utils.JsfUtils;
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
public class RemonteeFicheBean implements Serializable {


    @Inject
    @Getter
    private Remontee remontee;

    @Inject
    private FacadeRemontee facadeRemontee;

    @PostConstruct
    void init() {
        try {
            if (FacesContext.getCurrentInstance().getExternalContext().getFlash().get(RemonteeListeBean.FLASH) != null) {
                Long id = (Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get(RemonteeListeBean.FLASH);
                this.remontee = this.facadeRemontee.find(id).get();
                FacesContext.getCurrentInstance().getExternalContext().getFlash().put(RemonteeListeBean.FLASH, id);
            }
        }catch (BusinessException e) {
            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR,"Erreur modification metier");
        }

    }



    public void valider()  {

        try {
            this.facadeRemontee.save(this.remontee);
            JsfUtils.sendMessage(null, FacesMessage.SEVERITY_INFO, "La remontée a bien été enregistré !");
        } catch (Exception | BusinessException e) {
            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR,"Erreur sauvegarde metier");
        }

        this.remontee = CDI.current().select(Remontee.class).get();
    }


    public List<Etat> getEtats()
    {
        return Arrays.asList(Etat.values());
    }

}
