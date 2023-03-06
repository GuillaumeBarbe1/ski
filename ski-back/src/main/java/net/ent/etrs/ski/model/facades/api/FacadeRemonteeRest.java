package net.ent.etrs.ski.model.facades.api;

import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.Remontee;
import net.ent.etrs.ski.model.facades.FacadeRemontee;
import net.ent.etrs.ski.model.facades.api.dtos.RemonteeDto;
import net.ent.etrs.ski.model.facades.api.dtos.converters.RemonteeDtoConverter;
import net.ent.etrs.ski.utils.Utils;
import org.apache.commons.collections4.IterableUtils;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Path("/remontees")
public class FacadeRemonteeRest {

    @Inject
    private FacadeRemontee facadeRemontee;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(@QueryParam("first") @DefaultValue("1") Integer first,
                            @QueryParam("pageSize") @DefaultValue("10") Integer pageSize,
                            @QueryParam("sortedBy") @DefaultValue("nom:ASC") String sortedBy,
                            @QueryParam("filterBy") @DefaultValue("") String filterBy) {
        try {
            Map<String, String> filter = Utils.strToMap(filterBy);
            Map<String, String> sorted = Utils.strToMap(sortedBy);

            List<Remontee> list = IterableUtils.toList(this.facadeRemontee.load(first, pageSize, sorted, filter));

            return Response.ok(RemonteeDtoConverter.toDtoList(list)).build();
        } catch (BusinessException e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) {
        try {
            Optional<Remontee> optionalRemontee = this.facadeRemontee.find(id);
            if (optionalRemontee.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(RemonteeDtoConverter.toDto(optionalRemontee.get())).build();
        } catch (BusinessException e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(RemonteeDto remonteeDto) {
        try {
            Remontee remontee = RemonteeDtoConverter.toEntity(remonteeDto);
            Optional<Remontee> optionalRemontee = this.facadeRemontee.save(remontee);
            if (optionalRemontee.isEmpty()) {
                return Response.serverError().build();
            }
            return Response.status(Response.Status.CREATED).entity(RemonteeDtoConverter.toDto(optionalRemontee.get())).build();
        } catch (BusinessException e) {
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, RemonteeDto remonteeDto) {
        try {
            if (!this.facadeRemontee.exists(id) || !id.equals(remonteeDto.getId())) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            Remontee remontee = RemonteeDtoConverter.toEntity(remonteeDto);
            Optional<Remontee> optionalRemontee = this.facadeRemontee.save(remontee);
            if (optionalRemontee.isEmpty()) {
                return Response.serverError().build();
            }
            return Response.status(Response.Status.CREATED).entity(RemonteeDtoConverter.toDto(optionalRemontee.get())).build();
        } catch (BusinessException e) {
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            if (!this.facadeRemontee.exists(id)) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            this.facadeRemontee.delete(id);
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
            int count = this.facadeRemontee.count(filter);
            return Response.ok(count).build();
        } catch (BusinessException e) {
            return Response.serverError().build();
        }
    }

}
