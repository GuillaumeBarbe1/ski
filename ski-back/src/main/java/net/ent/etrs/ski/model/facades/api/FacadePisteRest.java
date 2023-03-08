package net.ent.etrs.ski.model.facades.api;

import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.Piste;
import net.ent.etrs.ski.model.facades.FacadePiste;
import net.ent.etrs.ski.model.facades.api.dtos.PisteDto;
import net.ent.etrs.ski.model.facades.api.dtos.converters.PisteDtoConverter;
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
@Path("/pistes")
public class FacadePisteRest {

    @Inject
    private FacadePiste facadePiste;

    @GET
    @Path("/")
    @RoleUser
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(@QueryParam("first") @DefaultValue("0") Integer first,
                            @QueryParam("pageSize") @DefaultValue("10") Integer pageSize,
                            @QueryParam("sortedBy") @DefaultValue("nom:ASC") String sortedBy,
                            @QueryParam("filterBy") @DefaultValue("") String filterBy,
                            @QueryParam("forfait") Long idForfait,
                            @QueryParam("station") Long idStation) {
        try {
            Map<String, String> filter = Utils.strToMap(filterBy);
            Map<String, String> sorted = Utils.strToMap(sortedBy);

            List<Piste> list;
            if (Objects.isNull(idForfait) && Objects.isNull(idStation)) {
                list = IterableUtils.toList(this.facadePiste.load(first, pageSize, sorted, filter));
            } else if (!Objects.isNull(idForfait)) {
                list = IterableUtils.toList(this.facadePiste.findAllByForfait(idForfait));
            } else {
                list = IterableUtils.toList(this.facadePiste.findAllByStation(idStation));
            }


            return Response.ok(PisteDtoConverter.toDtoList(list)).build();
        } catch (Exception | BusinessException e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{id}")
    @RoleUser
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
    @RoleAdmin
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
    @RoleAdmin
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
    @RoleAdmin
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
    @RoleUser
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
