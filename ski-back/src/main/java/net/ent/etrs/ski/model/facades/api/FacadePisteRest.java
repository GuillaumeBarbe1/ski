package net.ent.etrs.ski.model.facades.api;

import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.Piste;
import net.ent.etrs.ski.model.entities.Station;
import net.ent.etrs.ski.model.facades.FacadePiste;
import net.ent.etrs.ski.model.facades.FacadeStation;
import net.ent.etrs.ski.model.facades.api.dtos.PisteDto;
import net.ent.etrs.ski.model.facades.api.dtos.StationDto;
import net.ent.etrs.ski.model.facades.api.dtos.converters.PisteDtoConverter;
import net.ent.etrs.ski.model.facades.api.dtos.converters.StationDtoConverter;
import net.ent.etrs.ski.utils.Utils;
import org.apache.commons.collections4.IterableUtils;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Path("/pistes")
public class FacadePisteRest {

    @Inject
    private FacadePiste facadePiste;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(@QueryParam("first") @DefaultValue("1") Integer first,
                            @QueryParam("pageSize") @DefaultValue("10")  Integer pageSize,
                            @QueryParam("sortedBy") @DefaultValue("nom:ASC")  String sortedBy,
                            @QueryParam("filterBy") @DefaultValue("") String filterBy) {
        try {
            Map<String, String> filter = Utils.strToMap(filterBy);
            Map<String, String> sorted = Utils.strToMap(sortedBy);
            
            List<Piste> list = IterableUtils.toList(this.facadePiste.load(first, pageSize, sorted, filter));

            return Response.ok(PisteDtoConverter.toDtoList(list)).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) {
        try {
            Optional<Piste> optionalPiste = this.facadePiste.find(id);
            if (optionalPiste.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            return Response.ok(PisteDtoConverter.toDto(optionalPiste.get())).build();
        } catch (BusinessException e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(PisteDto pisteDto) {
        try {
            Piste piste = PisteDtoConverter.toEntity(pisteDto);
            Optional<Piste> optionalPiste = this.facadePiste.save(piste);
            if (optionalPiste.isEmpty()) {
                return Response.serverError().build();
            }
            return Response.status(Response.Status.CREATED).entity(PisteDtoConverter.toDto(optionalPiste.get())).build();
        } catch (BusinessException e) {
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, PisteDto pisteDto) {
        try {
            if (!this.facadePiste.exists(id) || !id.equals(pisteDto.getId())) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            Piste piste = PisteDtoConverter.toEntity(pisteDto);
            Optional<Piste> optionalPiste = this.facadePiste.save(piste);
            if (optionalPiste.isEmpty()) {
                return Response.serverError().build();
            }
            return Response.status(Response.Status.CREATED).entity(PisteDtoConverter.toDto(optionalPiste.get())).build();
        } catch (BusinessException e) {
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            if (!this.facadePiste.exists(id)) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            this.facadePiste.delete(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (BusinessException e) {
            return Response.serverError().build();
        }
    }
    
    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response count(
            @QueryParam("filterBy") @DefaultValue("") String filterBy) {
        try {
            Map<String, String> filter = Utils.strToMap(filterBy);
            int count = this.facadePiste.count(filter);
            return Response.ok(count).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

}
