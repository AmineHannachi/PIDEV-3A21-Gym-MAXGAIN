package tn.esprit.services;

public class SessionManager {
    private static String jwt;

    public static String getJWT() {
        return jwt;
    }

    public static void setJWT(String jwt) {
        SessionManager.jwt = jwt;
    }
}
