package daoImpl;

import dao.OfferDAO;
import database.DataService;
import model.Category;
import model.Offer;
import model.User;

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

    public List<Offer> getOffersByCategoryId(int categoryId) {
        return dataService.getOffersByCategoryId(categoryId);
    }

    public List<Offer>searchOffersByKeyword(String keyWord) {
        return null;
    }

    public int insertOffer(Offer offering) throws SQLException {
        return dataService.insertOffer(offering);
    }
    public int insertOffer(Offer offering,InputStream input, long len) throws SQLException {
        return dataService.insertOffer(offering,input, len);
    }

    public List<Category>getAllCategories(){
        return dataService.getAllCategories();
    }

    public int insertCategory(Category category, InputStream input, long len, int parent_category_id, int is_hide) throws SQLException{
        return dataService.insertCategory(category,input,len,parent_category_id,is_hide);
    }

    public int updateCategoryWithImage(Category category, InputStream input, long len, int category_id) throws SQLException {
        return dataService.updateCategoryWithImage(category, input, len, category_id);
    }

    public int updateCategoryWithoutImage(Category category, int category_id) throws SQLException {
        return dataService.updateCategoryWithoutImage(category, category_id);
    }

    public int updateOfferWithImage(Offer offer, InputStream input, long len, int category_id) throws SQLException {
        return dataService.updateOfferWithImage(offer, input, len, category_id);
    }

    public int updateOfferWithoutImage(Offer offer, int category_id) throws SQLException {
        return dataService.updateOfferWithoutImage(offer, category_id);
    }

    public Category getCategoryById(int categoryId) throws SQLException {
        return dataService.getCategoryById(categoryId);
    }

    public Offer getOfferById(int offerId) throws SQLException {
        return dataService.getOfferById(offerId);
    }

    public int deleteOfferById(int offerId) throws SQLException {
        return dataService.deleteOfferById(offerId);
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

    public User getUserByUserName(String user_name) throws SQLException {
        return dataService.getUserByUserName(user_name);
    };
}
