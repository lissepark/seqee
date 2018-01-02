package dao;

import model.Offer;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by incrit.com on 8/20/17.
 */
public interface OfferDAO {
    public List<Offer> getAllOffers();
    public List<Offer>searchOffersByKeyword(String keyWord);
    //public List<Category>findAllCategories();
    public void insertOffer(Offer offer) throws SQLException;
    public void update(Offer offer);
    public void delete(Long offerId);
}
