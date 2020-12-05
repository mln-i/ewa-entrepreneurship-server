package hva.ewa.Entrepreneurship.service;

import hva.ewa.Entrepreneurship.model.User;
import hva.ewa.Entrepreneurship.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * The user is searched for by their username which in this case is their email address. An existing
     * user (has an existing email address in the database) gets returned with all of their information.
     * An exception is thrown if user doesn't exist (email address can't be found).
     *
     * @param userEmail
     * @return details of user including their username, password and role
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUserEmail(userEmail);
        
        if (user == null) {
            throw new UsernameNotFoundException("user with the email " + userEmail + " doesn't exist");
        }
        else {
            return userDetails(user);
        }
    }

    /**
     * Build a profile of user with the neccessary credentials which in this case is their email address (as their username),
     * password and role.
     *
     * @param user
     * @return user's credentials which includes their email address (username), password and role
     */
    private UserDetails userDetails(User user) {
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority(user.getRole()));
        //return org.springframework.security.core.userdetails.User.withUsername(user.getEmail()).password("{noop}" + user.getPassword()).roles(user.getRole()).build();
        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail()).password(user.getPassword()).authorities(list).build();
    }
}
