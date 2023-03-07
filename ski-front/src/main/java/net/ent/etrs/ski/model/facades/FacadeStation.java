package net.ent.etrs.ski.model.facades;

import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.Piste;
import net.ent.etrs.ski.model.entities.Station;
import net.ent.etrs.ski.model.facades.dtos.PisteDto;
import net.ent.etrs.ski.model.facades.dtos.StationDto;
import net.ent.etrs.ski.model.facades.dtos.converters.PisteDtoConverter;
import net.ent.etrs.ski.model.facades.dtos.converters.StationDtoConverter;
import net.ent.etrs.ski.utils.LazyDataModelUtil;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FacadeStation extends AbstractFacade {

    private static final String URL_STATION = BACK_URL + "stations/";

    public Optional<Station> find(Long id) throws BusinessException {
        try {
            StationDto stationDto = this.client
                    .target(URL_STATION)
                    .path(String.valueOf(id))
                    .request(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken())
                    .get(StationDto.class);
            return Optional.of(StationDtoConverter.toEntity(stationDto));
        } catch (NotFoundException e) {
            throw new BusinessException("Aucune station pour cet identifiant");
        } catch (ServerErrorException e) {
            throw new BusinessException("Erreur lors du chargement de la station");
        }
    }


    public Iterable<Station> findAll() throws BusinessException {
        List<StationDto> stationDtoList = this.client
                .target(URL_STATION)
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken())
                .get(new GenericType<List<StationDto>>(){});
        return StationDtoConverter.toEntityList(stationDtoList);
    }


    public Optional<Station> save(Station station) throws BusinessException {
        StationDto dto = StationDtoConverter.toDto(station);
        Response resp = this.client
                .target(URL_STATION)
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken())
                .post(Entity.json(dto));

        if (resp.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            throw new BusinessException("Erreur lors de la création de la station");
        }
        return Optional.of(StationDtoConverter.toEntity(resp.readEntity(StationDto.class)));
    }

    public Optional<Station> update(Station station) throws BusinessException {
        StationDto dto = StationDtoConverter.toDto(station);
        Response resp = this.client
                .target(URL_STATION)
                .path(String.valueOf(station.getId()))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken())
                .put(Entity.json(dto));

        if (resp.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            throw new BusinessException("Erreur lors de la modification de la station");
        }

        if (resp.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            throw new BusinessException("Aucune station pour cet identifiant");
        }
        return Optional.of(StationDtoConverter.toEntity(resp.readEntity(StationDto.class)));
    }


    public void delete(Long id) throws BusinessException {
        Response resp = this.client.target(URL_STATION)
                .path(String.valueOf(id))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken())
                .delete();

        if (resp.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            throw new BusinessException("Erreur lors de la suppression de la station");
        }

        if (resp.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            throw new BusinessException("Aucune station pour cet identifiant");
        }
    }

    public List<Station> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) throws BusinessException {
        String sorted = LazyDataModelUtil.sortedMapToStr(sortBy);
        String filter = LazyDataModelUtil.filterMapToStr(filterBy);
        List<StationDto> stationDtoList = this.client
                .target(URL_STATION)
                .queryParam("first", first)
                .queryParam("pageSize", pageSize)
                .queryParam("sortedBy", sorted)
                .queryParam("filterBy", filter)
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken())
                .get(new GenericType<List<StationDto>>(){});
        return StationDtoConverter.toEntityList(stationDtoList);
    }

    public int count(Map<String, FilterMeta> filterBy) {
        String filter = LazyDataModelUtil.filterMapToStr(filterBy);
        return this.client
                .target(URL_STATION)
                .path("count")
                .queryParam("filterBy", filter)
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken())
                .get(Integer.class);
    }
    
    public Optional<Station> findByIdWithPistes(Long id) {
        return null;
    }

    public Long findByVille(String ville) throws BusinessException {
        return null;
    }
}
