package net.ent.etrs.ski.model.facades.api;

import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.Remontee;
import net.ent.etrs.ski.model.facades.FacadeRemontee;
import org.apache.commons.collections4.IterableUtils;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Path("/remontees")
public class FacadeRemonteeRest {
    
    @Inject
    private FacadeRemontee facadeRemontee;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(@QueryParam("piste") Long id) {
        try {
            List<Remontee> list;
            if(Objects.isNull(id)){
                list = IterableUtils.toList(this.facadeRemontee.findAll());
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

}
