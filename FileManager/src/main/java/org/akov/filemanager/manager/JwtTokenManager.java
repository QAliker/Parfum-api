package org.akov.filemanager.manager;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Calendar;

/**
 * Cette classe va générer et vérifier un token
 */
public class JwtTokenManager {

    /**
     * Méthode qui permet de crypter l'id user
     * @param idUser
     * @return
     */
    public static String generateToken(Integer idUser) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 24);

        return Jwts.builder()
                .subject(String.valueOf(idUser))
                .expiration(calendar.getTime())
                .signWith(secretKey(), Jwts.SIG.HS256)
                .compact();
    }

    public static Integer getIdUser(String token) throws Exception {
        Claims claims = Jwts.parser().verifyWith(secretKey()).build().parseSignedClaims(token).getPayload();

        return Integer.parseInt(claims.getSubject());
    }

    private static SecretKey secretKey(){
        String cleCryptage = "wzUpGa9k4LTV3QHuY8qVrt6wOENkvdes5vLHVc1ex6581IiQ";
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(cleCryptage));
    }
}
