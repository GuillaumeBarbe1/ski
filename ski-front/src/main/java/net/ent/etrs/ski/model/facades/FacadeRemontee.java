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

public class FacadeRemontee extends AbstractFacade {

    private static final String URL_REMONTEE = BACK_URL + "remontees/";
    
    public Optional<Remontee> find(Long id) throws BusinessException {
        try {
            RemonteeDto remonteeDto = this.client
                    .target(URL_REMONTEE)
                    .path(String.valueOf(id))
                    .request(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken())
                    .get(RemonteeDto.class);
            return Optional.of(RemonteeDtoConverter.toEntity(remonteeDto));
        } catch (NotFoundException e) {
            throw new BusinessException("Aucune remontée pour cet identifiant");
        } catch (ServerErrorException e) {
            throw new BusinessException("Erreur lors du chargement de la remontée");
        }
    }
    
    public Iterable<Remontee> findAll() throws BusinessException {
        List<RemonteeDto> remonteeDtoList = this.client
                .target(URL_REMONTEE)
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken())
                .get(new GenericType<List<RemonteeDto>>(){});
        return RemonteeDtoConverter.toEntityList(remonteeDtoList);
    }

    public Iterable<Remontee> findAllByPiste(Long id) throws BusinessException {
        List<RemonteeDto> remonteeDtoList = this.client
                .target(URL_REMONTEE)
                .queryParam("piste",id)
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken())
                .get(new GenericType<List<RemonteeDto>>(){});
        return RemonteeDtoConverter.toEntityList(remonteeDtoList);
    }
    
    public Optional<Remontee> save(Remontee remontee) throws BusinessException {
        Response resp = this.client
                .target(URL_REMONTEE)
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken())
                .post(Entity.json(remontee));

        if (resp.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            throw new BusinessException("Erreur lors de la création de la remontée");
        }
        return Optional.of(RemonteeDtoConverter.toEntity(resp.readEntity(RemonteeDto.class)));
    }


    public Optional<Remontee> update(Remontee remontee) throws BusinessException {
        Response resp = this.client
                .target(URL_REMONTEE)
                .path(String.valueOf(remontee.getId()))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken())
                .put(Entity.json(remontee));

        if (resp.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            throw new BusinessException("Erreur lors de la modification de la remontée");
        }

        if (resp.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            throw new BusinessException("Aucune remontée pour cet identifiant");
        }
        return Optional.of(RemonteeDtoConverter.toEntity(resp.readEntity(RemonteeDto.class)));
    }
    
    public void delete(Long id) throws BusinessException {
        Response resp = this.client.target(URL_REMONTEE)
                .path(String.valueOf(id))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken())
                .delete();

        if (resp.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            throw new BusinessException("Erreur lors de la suppression de la remontée");
        }

        if (resp.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            throw new BusinessException("Aucune remontée pour cet identifiant");
        }
    }


    public List<Remontee> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) throws BusinessException {
        String sorted = LazyDataModelUtil.sortedMapToStr(sortBy);
        String filter = LazyDataModelUtil.filterMapToStr(filterBy);
        List<RemonteeDto> remonteeDtoList = this.client
                .target(URL_REMONTEE)
                .queryParam("first", first)
                .queryParam("pageSize", pageSize)
                .queryParam("sortedBy", sorted)
                .queryParam("filterBy", filter)
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken())
                .get(new GenericType<List<RemonteeDto>>(){});
        return RemonteeDtoConverter.toEntityList(remonteeDtoList);
    }

    public int count(Map<String, FilterMeta> filterBy) {
        String filter = LazyDataModelUtil.filterMapToStr(filterBy);
        return this.client
                .target(URL_REMONTEE)
                .path("count")
                .queryParam("filterBy", filter)
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken())
                .get(Integer.class);
    }

}
