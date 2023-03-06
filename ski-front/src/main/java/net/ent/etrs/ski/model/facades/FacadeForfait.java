package net.ent.etrs.ski.model.facades;

import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.Forfait;
import net.ent.etrs.ski.model.facades.dtos.ForfaitDto;
import net.ent.etrs.ski.model.facades.dtos.converters.ForfaitDtoConverter;
import net.ent.etrs.ski.utils.LazyDataModelUtil;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FacadeForfait extends AbstractFacade {
    //extends AbstractFacade
    private static final String URL_FORFAIT = BACK_URL + "forfaits/";

    public Optional<Forfait> find(Long id) throws BusinessException {
        try {
            ForfaitDto forfaitDto = this.client
                    .target(URL_FORFAIT)
                    .path(String.valueOf(id))
                    .request(MediaType.APPLICATION_JSON)
                    .get(ForfaitDto.class);
            return Optional.of(ForfaitDtoConverter.toEntity(forfaitDto));
        } catch (NotFoundException e) {
            throw new BusinessException("Aucune forfait pour cet identifiant");
        } catch (ServerErrorException e) {
            throw new BusinessException("Erreur lors du chargement de la forfait");
        }
    }

    public Iterable<Forfait> findAll() throws BusinessException {
        List<ForfaitDto> forfaitDtoList = this.client
                .target(URL_FORFAIT)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<ForfaitDto>>() {});
        return ForfaitDtoConverter.toEntityList(forfaitDtoList);
    }

    public Optional<Forfait> save(Forfait forfait) throws BusinessException {
        ForfaitDto dto = ForfaitDtoConverter.toDto(forfait);
        Response resp = this.client
                .target(URL_FORFAIT)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(dto));

        if (resp.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            throw new BusinessException("Erreur lors de la création de la forfait");
        }
        return Optional.of(ForfaitDtoConverter.toEntity(resp.readEntity(ForfaitDto.class)));
    }

    public Optional<Forfait> update(Forfait forfait) throws BusinessException {
        ForfaitDto dto = ForfaitDtoConverter.toDto(forfait);
        Response resp = this.client
                .target(URL_FORFAIT)
                .path(String.valueOf(forfait.getId()))
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(dto));

        if (resp.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            throw new BusinessException("Erreur lors de la modification de la forfait");
        }

        if (resp.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            throw new BusinessException("Aucune piste pour cet identifiant");
        }
        return Optional.of(ForfaitDtoConverter.toEntity(resp.readEntity(ForfaitDto.class)));
    }

    public void delete(Long id) throws BusinessException {
        Response resp = this.client.target(URL_FORFAIT)
                .path(String.valueOf(id))
                .request(MediaType.APPLICATION_JSON)
                .delete();

        if (resp.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            throw new BusinessException("Erreur lors de la suppression de la forfait");
        }

        if (resp.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            throw new BusinessException("Aucune forfait pour cet identifiant");
        }
    }


    public long count() throws BusinessException {
        return 0;
    }

    public void deleteAll(List<Forfait> forfaits) throws BusinessException {
    }

    public List<Forfait> findAllDispo() throws BusinessException {
        return null;
    }

    public List<Forfait> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) throws BusinessException {
        String sorted = LazyDataModelUtil.sortedMapToStr(sortBy);
        String filter = LazyDataModelUtil.filterMapToStr(filterBy);
        List<ForfaitDto> forfaitDtoList = this.client
                .target(URL_FORFAIT)
                .queryParam("first", first)
                .queryParam("pageSize", pageSize)
                .queryParam("sortedBy", sorted)
                .queryParam("filterBy", filter)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<ForfaitDto>>() {});
        return ForfaitDtoConverter.toEntityList(forfaitDtoList);
    }

    public int count(Map<String, FilterMeta> filterBy) {
        String filter = LazyDataModelUtil.filterMapToStr(filterBy);
        return this.client
                .target(URL_FORFAIT)
                .path("count")
                .queryParam("filterBy", filter)
                .request(MediaType.APPLICATION_JSON)
                .get(Integer.class);
    }


}
