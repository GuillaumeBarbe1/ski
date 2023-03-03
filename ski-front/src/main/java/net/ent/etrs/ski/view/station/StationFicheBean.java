package net.ent.etrs.ski.view.station;

import lombok.Getter;
import lombok.Setter;
import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.Piste;
import net.ent.etrs.ski.model.entities.Station;
import net.ent.etrs.ski.model.entities.references.Etat;
import net.ent.etrs.ski.model.facades.FacadePiste;
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
public class StationFicheBean implements Serializable {


    @Inject
    @Getter
    private Station station;

    @Inject
    private FacadeStation facadeStation;

    @Inject
    private FacadePiste facadePiste;
    
    @Getter @Setter
    private UploadedFile file;

    @PostConstruct
    void init()  {
        try {
            if (FacesContext.getCurrentInstance().getExternalContext().getFlash().get(StationListeBean.FLASH) != null) {
                Long id = (Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get(StationListeBean.FLASH);
                this.station = this.facadeStation.find(id).get();
            }
        } catch (BusinessException e) {
            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR,"Erreur modification metier");
        }

    }

    public void valider()  {
        if(this.file != null && this.file.getFileName() != null) {
            this.station.setPhoto(this.file.getContent());
        }
        try {
            this.facadeStation.save(this.station);
            JsfUtils.sendMessage(null, FacesMessage.SEVERITY_INFO, "La station a bien été enregistré !");
        } catch (Exception | BusinessException e) {
            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR,"Erreur sauvegarde metier");
        }

        this.station = CDI.current().select(Station.class).get();
    }


    public List<Piste> getPistesDispo(){
        List<Piste> list = new ArrayList<>(this.station.getPistes());
        try {
            list.addAll(IterableUtils.toList(this.facadePiste.findAll()));
        } catch (BusinessException e) {
            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR,"Erreur chargement metier");
        }
        return list;
    }

    public List<Etat> getEtats()
    {
        return Arrays.asList(Etat.values());
    }
}
