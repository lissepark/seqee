package daoImpl;

import dao.OfferDAO;
import database.DataService;
import model.Offer;

import java.io.InputStream;
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
        dataService.insertOffer(offering);
    }
    public void insertOffer(Offer offering,InputStream input, long len) throws SQLException {
        dataService.insertOffer(offering,input, len);
    }
    public void update(Offer offering) {
    }
    public void delete(Long offerId) {
    }

    public int insertOfferingsImage(String image_name, int offer_id, InputStream input, long len) throws SQLException {
        return dataService.insertOfferingsImage(image_name, offer_id, input, len);
    }

    public List<Blob> selectOfferingsImage(int offer_id) throws SQLException {
        return dataService.selectOfferingsImage(offer_id);
    }
}
