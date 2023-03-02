package net.ent.etrs.ski.model.facades.api;

import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.Forfait;
import net.ent.etrs.ski.model.entities.Piste;
import net.ent.etrs.ski.model.facades.FacadeForfait;
import net.ent.etrs.ski.model.facades.FacadePiste;
import net.ent.etrs.ski.model.facades.api.dtos.ForfaitDto;
import net.ent.etrs.ski.model.facades.api.dtos.PisteDto;
import net.ent.etrs.ski.model.facades.api.dtos.converters.ForfaitDtoConverter;
import net.ent.etrs.ski.model.facades.api.dtos.converters.PisteDtoConverter;
import org.apache.commons.collections4.IterableUtils;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/forfaits")
public class FacadeForfaitRest {


    @Inject
    private FacadeForfait facadeForfait;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        try {
            List<Forfait> list = IterableUtils.toList(this.facadeForfait.findAll());

            return Response.ok(ForfaitDtoConverter.toDtoList(list)).build();
        } catch (BusinessException e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) {
        try {
            Optional<Forfait> optionalForfait = this.facadeForfait.find(id);
            if (optionalForfait.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            return Response.ok(ForfaitDtoConverter.toDto(optionalForfait.get())).build();
        } catch (BusinessException e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(ForfaitDto forfaitDto) {
        try {
            Forfait forfait = ForfaitDtoConverter.toEntity(forfaitDto);
            Optional<Forfait> optionalForfait = this.facadeForfait.save(forfait);
            if (optionalForfait.isEmpty()) {
                return Response.serverError().build();
            }
            return Response.status(Response.Status.CREATED).entity(ForfaitDtoConverter.toDto(optionalForfait.get())).build();
        } catch (BusinessException e) {
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, ForfaitDto forfaitDto) {
        try {
            if (!this.facadeForfait.exists(id) || !id.equals(forfaitDto.getId())) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            Forfait forfait = ForfaitDtoConverter.toEntity(forfaitDto);
            Optional<Forfait> optionalForfait = this.facadeForfait.save(forfait);
            if (optionalForfait.isEmpty()) {
                return Response.serverError().build();
            }
            return Response.status(Response.Status.CREATED).entity(ForfaitDtoConverter.toDto(optionalForfait.get())).build();
        } catch (BusinessException e) {
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            if (!this.facadeForfait.exists(id)) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            this.facadeForfait.delete(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (BusinessException e) {
            return Response.serverError().build();
        }
    }

}
