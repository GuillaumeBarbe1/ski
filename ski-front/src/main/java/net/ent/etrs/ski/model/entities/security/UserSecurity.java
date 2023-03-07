package net.ent.etrs.ski.model.entities.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import lombok.*;
import net.ent.etrs.ski.model.entities.references.Role;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserSecurity {
    
    @Getter
    private String login;
    
    @Getter
    private Role role;
    
    @Getter
    private String token;
    
    public UserSecurity(String pToken) {
        this.token = pToken;
        
        String tkn = token.split(" ")[1];
        tkn = String.format("%s.%s.", tkn.split("\\.")[0], tkn.split("\\.")[1]);
    
        Jwt<Header, Claims> t = Jwts.parserBuilder().build().parseClaimsJwt(tkn);
        
        this.login = t.getBody().getSubject();
        this.role = Role.valueOf((String) t.getBody().get("role"));
    }
    
}
