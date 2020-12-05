package hva.ewa.Entrepreneurship.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import hva.ewa.Entrepreneurship.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

import static hva.ewa.Entrepreneurship.security.TokenConstants.*;

/**
 * Verifies/authenticates whether user logging in exists and contains the right credentials. For succesful
 * logins, JWT token will be created for user.
 */
public class TokenAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    //constructor
    public TokenAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Username and password is retrieved from request to check whether user logging in exists.
     * An existing user will lead to a succesful authentication.
     *
     * @param request
     * @param response
     * @return a succesful authentication if user exists and an unsuccesful authentication if user can't be found with login inputs.
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A check is already done on whether user logging in exists. Afterwards, token will be created
     * and added to the header of the response.
     *
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        String authorities = authResult.getAuthorities().stream().map(GrantedAuthority :: getAuthority).collect(Collectors.joining(", "));

        String JWT = Jwts.builder().setSubject(((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername())
                .claim(AUTHORITIES, authorities)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + JWT);

    }
}
