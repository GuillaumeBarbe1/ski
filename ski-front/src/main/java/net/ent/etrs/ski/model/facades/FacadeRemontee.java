package net.ent.etrs.ski.model.facades;

import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.Remontee;
import net.ent.etrs.ski.model.facades.dtos.RemonteeDto;
import net.ent.etrs.ski.model.facades.dtos.converters.RemonteeDtoConverter;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

public class FacadeRemontee extends AbstractFacade {

    private static final String URL_REMONTEE = BACK_URL + "remontees/";
    
    public Optional<Remontee> find(Long id) throws BusinessException {
        try {
            RemonteeDto remonteeDto = this.client
                    .target(URL_REMONTEE)
                    .path(String.valueOf(id))
                    .request(MediaType.APPLICATION_JSON)
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
                .get(new GenericType<List<RemonteeDto>>(){});

//        remonteeDtoList.forEach(System.out::println);
        return RemonteeDtoConverter.toEntityList(remonteeDtoList);
    }
    
    public Iterable<Remontee> findAllByPisteId(Long id) throws BusinessException {
        List<RemonteeDto> remonteeDtoList = this.client
                .target(URL_REMONTEE)
                .queryParam("piste",id)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<RemonteeDto>>(){});

//        remonteeDtoList.forEach(System.out::println);
        return RemonteeDtoConverter.toEntityList(remonteeDtoList);
    }
    
    public Optional<Remontee> save(Remontee remontee) throws BusinessException {
        Response resp = this.client
                .target(URL_REMONTEE)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(remontee));

        if (resp.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            throw new BusinessException("Erreur lors de la création de la remontée");
        }
//        System.out.println(resp.readEntity(RemonteeDto.class));
        return Optional.of(RemonteeDtoConverter.toEntity(resp.readEntity(RemonteeDto.class)));
    }


    public Optional<Remontee> update(Remontee remontee) throws BusinessException {
        Response resp = this.client
                .target(URL_REMONTEE)
                .path(String.valueOf(remontee.getId()))
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(remontee));

        if (resp.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            throw new BusinessException("Erreur lors de la modification de la remontée");
        }

        if (resp.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            throw new BusinessException("Aucune remontée pour cet identifiant");
        }
//        System.out.println(resp.readEntity(RemonteeDto.class));
        return Optional.of(RemonteeDtoConverter.toEntity(resp.readEntity(RemonteeDto.class)));
    }
    
    public void delete(Long id) throws BusinessException {
        Response resp = this.client.target(URL_REMONTEE)
                .path(String.valueOf(id))
                .request(MediaType.APPLICATION_JSON)
                .delete();

        if (resp.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            throw new BusinessException("Erreur lors de la suppression de la remontée");
        }

        if (resp.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            throw new BusinessException("Aucune remontée pour cet identifiant");
        }
    }

    
    public long count() throws BusinessException {
        return 0;
    }
}
