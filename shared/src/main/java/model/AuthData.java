package model;

import java.security.SecureRandom;
import java.util.Base64;

public record AuthData(String authToken, String username) {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();

    /**
     * Randomly generates a secure auth token.
     * This occurs by generating a random string of bytes, then pulling from that for the token
     * @return String - Random identifier
     */
    public static String createToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}