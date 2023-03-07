package net.ent.etrs.ski.model.facades;

import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.Piste;
import net.ent.etrs.ski.model.entities.Remontee;
import net.ent.etrs.ski.model.facades.dtos.PisteDto;
import net.ent.etrs.ski.model.facades.dtos.RemonteeDto;
import net.ent.etrs.ski.model.facades.dtos.converters.PisteDtoConverter;
import net.ent.etrs.ski.model.facades.dtos.converters.RemonteeDtoConverter;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FacadePiste extends AbstractFacade {

    private static final String URL_PISTE = BACK_URL + "pistes/";
    
    public Optional<Piste> find(Long id) throws BusinessException {
        try {
            PisteDto pisteDto = this.client
                    .target(URL_PISTE)
                    .path(String.valueOf(id))
                    .request(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken())
                    .get(PisteDto.class);
            return Optional.of(PisteDtoConverter.toEntity(pisteDto));
        } catch (NotFoundException e) {
            throw new BusinessException("Aucune piste pour cet identifiant");
        } catch (ServerErrorException e) {
            throw new BusinessException("Erreur lors du chargement de la piste");
        }
    }
    
    public Iterable<Piste> findAll() throws BusinessException {
        List<PisteDto> pisteDtoList = this.client
                .target(URL_PISTE)
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken())
                .get(new GenericType<List<PisteDto>>(){});
        return PisteDtoConverter.toEntityList(pisteDtoList);
    }
    
    public Optional<Piste> save(Piste piste) throws BusinessException {
        PisteDto dto = PisteDtoConverter.toDto(piste);
        Response resp = this.client
                .target(URL_PISTE)
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken())
                .post(Entity.json(dto));

        if (resp.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            throw new BusinessException("Erreur lors de la création de la piste");
        }
        return Optional.of(PisteDtoConverter.toEntity(resp.readEntity(PisteDto.class)));
    }

    public Optional<Piste> update(Piste piste) throws BusinessException {
        PisteDto dto = PisteDtoConverter.toDto(piste);
        Response resp = this.client
                .target(URL_PISTE)
                .path(String.valueOf(piste.getId()))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken())
                .put(Entity.json(dto));

        if (resp.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            throw new BusinessException("Erreur lors de la modification de la piste");
        }

        if (resp.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            throw new BusinessException("Aucune piste pour cet identifiant");
        }
        return Optional.of(PisteDtoConverter.toEntity(resp.readEntity(PisteDto.class)));
    }

    public void delete(Long id) throws BusinessException {
        Response resp = this.client.target(URL_PISTE)
                .path(String.valueOf(id))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken())
                .delete();

        if (resp.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            throw new BusinessException("Erreur lors de la suppression de la piste");
        }

        if (resp.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            throw new BusinessException("Aucune piste pour cet identifiant");
        }
    }

    
    public long count() throws BusinessException {
        return 0;
    }

    public void deleteAll(List<Piste> pistes) throws BusinessException {
    }
    
    public List<Piste> findAllDispo() throws BusinessException {
        return null;
    }

    public Iterable<Piste> findAllByStation(Long id) throws BusinessException {
        List<PisteDto> pisteDtoList = this.client
                .target(URL_PISTE)
                .queryParam("station",id)
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken())
                .get(new GenericType<List<PisteDto>>(){});
        return PisteDtoConverter.toEntityList(pisteDtoList);
    }

    public Iterable<Piste> findAllByForfait(Long id) throws BusinessException {
        List<PisteDto> pisteDtoList = this.client
                .target(URL_PISTE)
                .queryParam("forfait",id)
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken())
                .get(new GenericType<List<PisteDto>>(){});
        return PisteDtoConverter.toEntityList(pisteDtoList);
    }
    
    public List<Piste> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) throws BusinessException {
        String sorted = LazyDataModelUtil.sortedMapToStr(sortBy);
        String filter = LazyDataModelUtil.filterMapToStr(filterBy);
        List<PisteDto> pisteDtoList = this.client
                .target(URL_PISTE)
                .queryParam("first", first)
                .queryParam("pageSize", pageSize)
                .queryParam("sortedBy", sorted)
                .queryParam("filterBy", filter)
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken())
                .get(new GenericType<List<PisteDto>>(){});
        return PisteDtoConverter.toEntityList(pisteDtoList);
    }
    
    public int count(Map<String, FilterMeta> filterBy) {
        String filter = LazyDataModelUtil.filterMapToStr(filterBy);
        return this.client
                .target(URL_PISTE)
                .path("count")
                .queryParam("filterBy", filter)
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken())
                .get(Integer.class);
    }
}
