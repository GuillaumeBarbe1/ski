package net.ent.etrs.ski.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Hash {
    
    public static String hash(String password) {
        String salt = BCrypt.gensalt();
        String passHash = BCrypt.hashpw(password, salt);
        return passHash;
    }
    
    public static boolean checkPassword(String pass, String hash) {
        return BCrypt.checkpw(pass, hash);
    }
}
