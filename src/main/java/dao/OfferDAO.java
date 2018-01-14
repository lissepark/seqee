package dao;

import model.Category;
import model.Offer;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by incrit.com on 8/20/17.
 */
public interface OfferDAO {
    public List<Offer> getAllOffers();
    public void insertOffer(Offer offer) throws SQLException;
    public void insertOffer(Offer offer, InputStream input, long len) throws SQLException;
    public List<Category> getAllCategories();
    public void insertCategory(Category category, InputStream input, long len) throws SQLException;
    public void update(Offer offer);
    public void delete(Long offerId);
    public int insertOfferingsImage(String image_name, int offer_id, InputStream input, long len) throws SQLException;
    public List<Blob> selectOfferingsImage(int offer_id) throws SQLException;
    public List<Offer>searchOffersByKeyword(String keyWord);
}
