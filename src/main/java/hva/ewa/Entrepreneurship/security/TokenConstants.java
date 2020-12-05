package hva.ewa.Entrepreneurship.security;

public class TokenConstants {
    static final String SECRET = "secret";
    static final String AUTHORITIES = "authorities";
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
    static final long EXPIRATION_TIME = 864_000_000; //10 days
}
