package net.ent.etrs.ski.utils;

import com.google.common.hash.Hashing;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Hash {
    
    public static String sha512(String s) {
        return Hashing.sha512().hashString(s, StandardCharsets.UTF_8).toString();
    }
}
