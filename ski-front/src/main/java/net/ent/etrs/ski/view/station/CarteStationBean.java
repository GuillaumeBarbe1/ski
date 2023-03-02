package net.ent.etrs.ski.view.station;

import lombok.Getter;
import lombok.Setter;
import net.ent.etrs.ski.utils.JsfUtils;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.*;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class CarteStationBean implements Serializable {

    @Getter
    private MapModel model;

    @Getter
    @Setter
    private Marker marker;
    @Getter
    private String centerGeoMap = "41.850033, -87.6500523";
    @Getter
    private String centerRevGeoMap = "41.850033, -87.6500523";

    @PostConstruct
    public void init() {
        model = new DefaultMapModel();
        chargementMarkersStations();
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
    }

    private void chargementMarkersStations() {

        Marker pMap = new Marker( new LatLng((41.850033),-87.6500523),"Chamonix");
        model.addOverlay(pMap);
    }
}
