package services;

import model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public class ServiceUser implements UserDetailsService {

    public ServiceUser() {

    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User result = new User();//remove after query execution

        //User result = (User) query to get user by username
        if (result == null) throw new UsernameNotFoundException("User with username: "+username+" was not found");

        return result;
    }
}
