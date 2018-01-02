package daoImpl;

import dao.OfferDAO;
import database.DataService;
import model.Offer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by incrit.com on 8/20/17.
 */
public class OfferDAOImpl implements OfferDAO {

    private DataService dataService = new DataService();

    public List<Offer> getAllOffers() {
        return dataService.getAllOffers();
    }

    public List<Offer>searchOffersByKeyword(String keyWord) {
        return null;
    }

    public void insertOffer(Offer offering) throws SQLException {
    }
    public void update(Offer offering) {
    }
    public void delete(Long offerId) {
    }
}
