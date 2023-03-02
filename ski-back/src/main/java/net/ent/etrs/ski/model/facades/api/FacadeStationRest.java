package net.ent.etrs.ski.model.facades.api;

import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.Station;
import net.ent.etrs.ski.model.facades.FacadeStation;
import net.ent.etrs.ski.model.facades.api.dtos.StationDto;
import net.ent.etrs.ski.model.facades.api.dtos.converters.StationDtoConverter;
import org.apache.commons.collections4.IterableUtils;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/stations")
public class FacadeStationRest {
    
    @Inject
    private FacadeStation facadeStation;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        try {
            List<Station> list = IterableUtils.toList(this.facadeStation.findAll());
            
            return Response.ok(StationDtoConverter.toDtoList(list)).build();
        } catch (BusinessException e) {
            return Response.serverError().build();
        }
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) {
        try {
            Optional<Station> optionalStation = this.facadeStation.find(id);
            if (optionalStation.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            
            return Response.ok(StationDtoConverter.toDto(optionalStation.get())).build();
        } catch (BusinessException e) {
            return Response.serverError().build();
        }
    }
    
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(StationDto stationDto) {
        try {
            Station station = StationDtoConverter.toEntity(stationDto);
            Optional<Station> optionalStation = this.facadeStation.save(station);
            if (optionalStation.isEmpty()) {
                return Response.serverError().build();
            }
            return Response.status(Response.Status.CREATED).entity(StationDtoConverter.toDto(optionalStation.get())).build();
        } catch (BusinessException e) {
            return Response.serverError().build();
        }
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, StationDto stationDto) {
        try {
            if (!this.facadeStation.exists(id) || !id.equals(stationDto.getId())) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            Station station = StationDtoConverter.toEntity(stationDto);
            Optional<Station> optionalStation = this.facadeStation.save(station);
            if (optionalStation.isEmpty()) {
                return Response.serverError().build();
            }
            return Response.status(Response.Status.CREATED).entity(StationDtoConverter.toDto(optionalStation.get())).build();
        } catch (BusinessException e) {
            return Response.serverError().build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            if (!this.facadeStation.exists(id)) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            this.facadeStation.delete(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (BusinessException e) {
            return Response.serverError().build();
        }
    }

}
