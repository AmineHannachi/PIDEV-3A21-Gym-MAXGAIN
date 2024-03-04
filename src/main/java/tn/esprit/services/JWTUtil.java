package tn.esprit.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JWTUtil {

    // Générez une clé secrète de la bonne taille
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Méthode pour générer un JWT après l'authentification de l'utilisateur
    public static String generateJWT(String username) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(nowMillis + 3600000)) // Expiration en une heure
                .signWith(SECRET_KEY)
                .compact();
    }

    // Méthode pour vérifier un JWT pour chaque requête authentifiée
    public static boolean verifyJWT(String jwt) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwt);
            return true; // Le JWT est valide
        } catch (Exception e) {
            return false; // Le JWT est invalide ou a expiré
        }
    }
}
