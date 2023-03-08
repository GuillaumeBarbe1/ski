package net.ent.etrs.ski.model.facades.api;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.User;
import net.ent.etrs.ski.model.facades.FacadeUser;
import net.ent.etrs.ski.model.facades.api.dtos.UserLoginDto;
import net.ent.etrs.ski.utils.Hash;

import javax.crypto.SecretKey;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Path("/users")
public class FacadeUserRest {

    public static final String SECRET_KEY = "ghJGZrAzghMI2YU29fl8dT1G90on2qRQM0QvUPonH8BUhKG9UTD33aWoyj2ym28CGL7X/sJxgfkNRneeuqw8DA==";

    @Inject
    private FacadeUser facadeUser;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(UserLoginDto userLoginDto) {
        try {
            Optional<User> userOptional = this.facadeUser.findByLogin(userLoginDto.getLogin());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (Hash.checkPassword(userLoginDto.getPassword(), user.getPassword())) {
                    String token = this.issueToken(user);
                    return Response.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();
                } else {
                    return Response.status(Response.Status.UNAUTHORIZED).build();
                }
            } else {

                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (BusinessException e) {
            return Response.serverError().build();
        }
    }

    private String issueToken(User user) {

        SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET_KEY));

        return Jwts.builder()
                .setSubject(user.getLogin())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(20)))
                .claim("role", user.getRole())
                .signWith(key)
                .compact();
    }

}
