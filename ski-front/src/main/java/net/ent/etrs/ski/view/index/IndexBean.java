package net.ent.etrs.ski.view.index;

import lombok.Getter;
import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.Piste;
import net.ent.etrs.ski.model.entities.Station;
import net.ent.etrs.ski.model.facades.FacadeForfait;
import net.ent.etrs.ski.model.facades.FacadeStation;
import net.ent.etrs.ski.utils.JsfUtils;
import net.ent.etrs.ski.view.station.StationListeBean;
import org.apache.commons.collections4.IterableUtils;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class IndexBean implements Serializable
{

    @Getter
    private String debug = "DEBUG";
    
    @Inject
    private FacadeStation facadeStation;

    
    @Inject
    private FacadeForfait facadeForfait;
    
    @PostConstruct
    public void init() throws BusinessException {
        System.out.println("PASS");
    }

    public List<Station> getLstStations()
    {
        List<Station> retour = new ArrayList<>();
        try
        {
            retour=IterableUtils.toList(facadeStation.findAll());
        }
        catch (BusinessException e)
        {
            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR,"Erreur de chargement des stations");
        }
        return retour;
    }

    public void modifier(Station s)
    {
        JsfUtils.putInFlashScope(StationListeBean.FLASH,s.getId());
    }

}
