package net.ent.etrs.ski.model.facades.api;

import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.Remontee;
import net.ent.etrs.ski.model.facades.FacadeRemontee;
import net.ent.etrs.ski.model.facades.api.filters.annotations.JWTTokenNeeded;
import net.ent.etrs.ski.model.facades.api.filters.annotations.RoleAdmin;
import net.ent.etrs.ski.model.facades.api.filters.annotations.RoleUser;
import net.ent.etrs.ski.utils.Utils;
import org.apache.commons.collections4.IterableUtils;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@JWTTokenNeeded
@Path("/remontees")
public class FacadeRemonteeRest {

    @Inject
    private FacadeRemontee facadeRemontee;

    @GET
    @Path("/")
    @RoleUser
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(@QueryParam("first") @DefaultValue("0") Integer first,
                            @QueryParam("pageSize") @DefaultValue("10") Integer pageSize,
                            @QueryParam("sortedBy") @DefaultValue("nom:ASC") String sortedBy,
                            @QueryParam("filterBy") @DefaultValue("") String filterBy,
                            @QueryParam("piste") Long id) {
        try {

            Map<String, String> filter = Utils.strToMap(filterBy);
            Map<String, String> sorted = Utils.strToMap(sortedBy);

            List<Remontee> list;
            if (Objects.isNull(id)) {
                list = IterableUtils.toList(this.facadeRemontee.load(first, pageSize, sorted, filter));
            } else {
                list = IterableUtils.toList(this.facadeRemontee.findAllByPiste(id));
            }

            return Response.ok(list).build();
        } catch (BusinessException e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{id}")
    @RoleUser
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) {
        try {
            Optional<Remontee> optionalRemontee = this.facadeRemontee.find(id);
            if (optionalRemontee.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            return Response.ok(optionalRemontee.get()).build();
        } catch (BusinessException e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Path("/")
    @RoleAdmin
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Remontee remontee) {
        try {
            Optional<Remontee> optionalRemontee = this.facadeRemontee.save(remontee);
            if (optionalRemontee.isEmpty()) {
                return Response.serverError().build();
            }
            return Response.status(Response.Status.CREATED).entity(optionalRemontee.get()).build();
        } catch (BusinessException e) {
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/{id}")
    @RoleAdmin
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, Remontee remontee) {
        try {
            if (!this.facadeRemontee.exists(id) || !id.equals(remontee.getId())) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            Optional<Remontee> optionalRemontee = this.facadeRemontee.save(remontee);
            if (optionalRemontee.isEmpty()) {
                return Response.serverError().build();
            }
            return Response.status(Response.Status.CREATED).entity(optionalRemontee.get()).build();
        } catch (BusinessException e) {
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RoleAdmin
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
    @RoleUser
    @Produces(MediaType.APPLICATION_JSON)
    public Response count(
            @QueryParam("filterBy") @DefaultValue("") String filterBy) {
        try {
            Map<String, String> filter = Utils.strToMap(filterBy);
            int count = this.facadeRemontee.count(filter);
            return Response.ok(count).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

}
