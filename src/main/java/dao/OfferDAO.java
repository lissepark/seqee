package dao;

import model.Category;
import model.Offer;
import model.User;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by incrit.com on 8/20/17.
 */
public interface OfferDAO {
    public List<Offer> getAllOffers();
    public List<Offer> getOffersByCategoryId(int categoryId);
    public void insertOffer(Offer offer) throws SQLException;
    public void insertOffer(Offer offer, InputStream input, long len) throws SQLException;
    public List<Category> getAllCategories();
    public Category getCategoryById(int categoryId) throws SQLException;
    public Offer getOfferById(int offerId) throws SQLException;
    public int deleteOfferById(int offerId) throws SQLException;
    public void insertCategory(Category category, InputStream input, long len, int parent_category_id, int is_hide) throws SQLException;
    public int updateCategoryWithImage(Category category, InputStream input, long len, int category_id) throws SQLException;
    public int updateCategoryWithoutImage(Category category, int category_id) throws SQLException;
    public int updateOfferWithImage(Offer offer, InputStream input, long len, int category_id) throws SQLException;
    public int updateOfferWithoutImage(Offer offer, int category_id) throws SQLException;
    public void update(Offer offer);
    public void delete(Long offerId);
    public int insertOfferingsImage(String image_name, int offer_id, InputStream input, long len) throws SQLException;
    public List<Blob> selectOfferingsImage(int offer_id) throws SQLException;
    public List<Offer>searchOffersByKeyword(String keyWord);
    public User getUserByUserName(String user_name) throws SQLException;

}
