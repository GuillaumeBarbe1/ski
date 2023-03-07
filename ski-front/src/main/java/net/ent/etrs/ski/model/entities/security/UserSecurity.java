package net.ent.etrs.ski.model.entities.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.Setter;
import net.ent.etrs.ski.model.entities.references.Role;

public class UserSecurity {

    // Lombok
    @Getter @Setter
    private String login;

    @Getter @Setter
    private Role role;
    private String token;


    public UserSecurity(String pToken) {
        this.token = pToken;

        String tkn = token.split(" ")[1];
        tkn = String.format("%s.%s", tkn.split("\\.")[0], tkn.split("\\.")[1]);

        Jwt<Header, Claims> t = Jwts.parserBuilder().build().parseClaimsJwt(tkn);

        this.login = t.getBody().getSubject();
        this.role = Role.valueOf((String) t.getBody().get("role"));
    }
}
