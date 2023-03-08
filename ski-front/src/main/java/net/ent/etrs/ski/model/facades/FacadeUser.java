package net.ent.etrs.ski.model.facades;

import net.ent.etrs.ski.model.facades.dtos.UserLoginDto;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

public class FacadeUser extends AbstractFacade {

    private static final String URL_USERS = BACK_URL + "users/";

    public String login(UserLoginDto userLoginDto) {
        return this.client
                .target(URL_USERS)
                .path("login")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(userLoginDto))
                .getHeaderString(HttpHeaders.AUTHORIZATION);
    }
}
