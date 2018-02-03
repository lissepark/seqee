package services;

import dao.OfferDAO;
import daoImpl.OfferDAOImpl;
import model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

public class ServiceUser implements UserDetailsService {

    OfferDAO offerDAO = new OfferDAOImpl();

    public ServiceUser() {

    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User result = null;
        try {
            result = (User) offerDAO.getUserByUserName(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (result == null) throw new UsernameNotFoundException("User with username: "+username+" was not found");
        return result;
    }
}
