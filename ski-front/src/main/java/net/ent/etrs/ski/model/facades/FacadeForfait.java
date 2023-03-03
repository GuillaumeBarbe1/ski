package net.ent.etrs.ski.model.facades;

import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.Forfait;
import net.ent.etrs.ski.model.facades.dtos.ForfaitDto;
import net.ent.etrs.ski.model.facades.dtos.PisteDto;
import net.ent.etrs.ski.model.facades.dtos.converters.ForfaitDtoConverter;
import net.ent.etrs.ski.model.facades.dtos.converters.PisteDtoConverter;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class FacadeForfait extends AbstractFacade {
    
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
            throw new BusinessException("Aucun forfait pour cet identifiant");
        } catch (ServerErrorException e) {
            throw new BusinessException("Erreur lors du chargement du forfait");
        }
    }
    
    public Iterable<Forfait> findAll() throws BusinessException {
        List<ForfaitDto> forfaitDtoList = this.client
                .target(URL_FORFAIT)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<ForfaitDto>>(){});
        return ForfaitDtoConverter.toEntityList(forfaitDtoList);
    }
    
    public Optional<Forfait> save(Forfait forfait) throws BusinessException {
        ForfaitDto dto = ForfaitDto.builder().nom("ABC").prixJournalier("123.32").build();
        Response resp = this.client
                .target(URL_FORFAIT)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(dto));
        
        if (resp.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            throw new BusinessException("Erreur lors de la création du forfait");
        }
        return Optional.of(ForfaitDtoConverter.toEntity(resp.readEntity(ForfaitDto.class)));
    }
    
    public Optional<Forfait> update(Forfait forfait) throws BusinessException {
        ForfaitDto dto = ForfaitDto.builder().id(52L).nom("ABC").prixJournalier("369.65").build();
        Response resp = this.client
                .target(URL_FORFAIT)
                .path(String.valueOf(dto.getId()))
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(dto));
        
        if (resp.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            throw new BusinessException("Erreur lors de la création du forfait");
        }
    
        if (resp.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            throw new BusinessException("Aucun forfait pour cet identifiant");
        }
        return Optional.of(ForfaitDtoConverter.toEntity(resp.readEntity(ForfaitDto.class)));
    }
    
    public void delete(Long id) throws BusinessException {
        Response resp = this.client.target(URL_FORFAIT)
                .path(String.valueOf(id))
                .request(MediaType.APPLICATION_JSON)
                .delete();
        
        if (resp.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            throw new BusinessException("Erreur lors de la suppression du forfait");
        }
    
        if (resp.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            throw new BusinessException("Aucun forfait pour cet identifiant");
        }
    }
    
    public long count() throws BusinessException {
        return 0;
    }


}
