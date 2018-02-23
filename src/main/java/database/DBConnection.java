package database;

import model.Category;
import model.Offer;
import model.User;

import java.io.File;
import java.io.InputStream;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Date;

public class DBConnection {

    private Connection conn = null;
    private ResultSet rs = null;

    private static PreparedStatement getAllOffers;
    private static PreparedStatement insertOffering;
    private static PreparedStatement getAllCategories;
    private static PreparedStatement getCategoryById;
    private static PreparedStatement getOfferById;
    private static PreparedStatement deleteOfferById;
    private static PreparedStatement insertCategory;
    private static PreparedStatement updateCategoryWithImage;
    private static PreparedStatement updateCategoryWithoutImage;
    private static PreparedStatement updateOfferWithImage;
    private static PreparedStatement updateOfferWithoutImage;
    private static PreparedStatement insertOfferingsImage;
    private static PreparedStatement selectOfferingsImage;
    private static PreparedStatement getOffersByCategoryId;
    private static PreparedStatement getUserByUserName;


    public DBConnection(String url, String user, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //conn = DriverManager.getConnection(url, user, password);
            conn = DriverManager.getConnection(url+"?user="+user+"&password="+password+"&characterEncoding=UTF-8");
            loadPreparedStatements();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException - > DBConnection(String url); " + e.getMessage());
        }
    }

    private void loadPreparedStatements() {
        try {
            getAllOffers = conn.prepareStatement("SELECT * FROM offering");
            getOffersByCategoryId = conn.prepareStatement("SELECT * FROM offering where `category_id`=?");
            insertOffering = conn.prepareStatement("INSERT INTO offering SET `offering_name`=?, `offering_description`=?, `offer_image_name`=?, `image_nat`=?, `category_id`=?");
            getAllCategories = conn.prepareStatement("SELECT * FROM category");
            getCategoryById = conn.prepareStatement("SELECT * FROM category where `category_id`=?");
            getOfferById = conn.prepareStatement("SELECT * FROM offering where `offering_id`=?");
            deleteOfferById = conn.prepareStatement("DELETE FROM offering where `offering_id`=?");
            insertCategory = conn.prepareStatement("INSERT INTO category SET `category_name`=?, `category_description`=?, `category_order`=?, `category_image`=?");
            updateCategoryWithImage = conn.prepareStatement("update category set `category_name`=?, `category_description`=?, `category_order`=?, `category_image`=? where `category_id`=?");
            updateCategoryWithoutImage = conn.prepareStatement("update category set `category_name`=?, `category_description`=?, `category_order`=? where `category_id`=?");
            updateOfferWithImage = conn.prepareStatement("update offering set `offering_name`=?, `offering_description`=?, `offer_image_name`=?, `image_nat`=?, `category_id`=? where `offering_id`=?");
            updateOfferWithoutImage = conn.prepareStatement("update offering set `offering_name`=?, `offering_description`=?, `offer_image_name`=?, `category_id`=? where `offering_id`=?");
            insertOfferingsImage = conn.prepareStatement("INSERT INTO images SET `image_name`=?, `offer_id`=?, `image_nat`=?");
            selectOfferingsImage = conn.prepareStatement("SELECT * FROM images where `offer_id`=?");
            getUserByUserName = conn.prepareStatement("SELECT * FROM users where `user_name`=?");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void closePreparedStatements() {
        try {
            getAllOffers.close();
            getOffersByCategoryId.close();
            insertOffering.close();
            getAllCategories.close();
            getCategoryById.close();
            getOfferById.close();
            deleteOfferById.close();
            insertCategory.close();
            updateCategoryWithImage.close();
            updateCategoryWithoutImage.close();
            updateOfferWithImage.close();
            updateOfferWithoutImage.close();
            insertOfferingsImage.close();
            selectOfferingsImage.close();
            getUserByUserName.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Offer> getAllOffers() {
        rs = null;
        List<Offer> result = new ArrayList<>();
        try {
            rs = getAllOffers.executeQuery();
            while (rs.next()) {
                Offer offer = new Offer();
                offer.setId(rs.getInt("offering_id"));
                offer.setOfferName(rs.getString("offering_name"));
                offer.setOfferDescription(rs.getString("offering_description"));
                offer.setOfferMainImage((com.mysql.jdbc.Blob) rs.getBlob("image_nat"));
                result.add(offer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Offer> getOffersByCategoryId(int categoryId) {
        rs = null;
        List<Offer> result = new ArrayList<>();
        try {
            getOffersByCategoryId.setInt(1,categoryId);
            rs = getOffersByCategoryId.executeQuery();
            while (rs.next()) {
                Offer offer = new Offer();
                offer.setId(rs.getInt("offering_id"));
                offer.setOfferName(rs.getString("offering_name"));
                offer.setOfferDescription(rs.getString("offering_description"));
                offer.setOfferMainImage((com.mysql.jdbc.Blob) rs.getBlob("image_nat"));
                result.add(offer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int insertOffer(Offer offer) throws SQLException {
        try {
            insertOffering.setString(1, offer.getOfferName());
            insertOffering.setString(2, offer.getOfferDescription());
            //insertOffering.setArray(3,offer.getOfferCategories());
            insertOffering.setString(3, offer.getOfferImageName());
            return insertOffering.executeUpdate();
        } catch (SQLException e) {
            System.out.println("insertOffer - SQLException"+e.getMessage());
            return -1;
        }
    }

    public int insertOffer(Offer offer, InputStream input, long len) throws SQLException {
        try {
            insertOffering.setString(1, offer.getOfferName());
            insertOffering.setString(2, offer.getOfferDescription());
            //insertOffering.setArray(3,offer.getOfferCategories());
            insertOffering.setString(3, offer.getOfferImageName());
            insertOffering.setBinaryStream(4, input, len);
            insertOffering.setInt(5, offer.getOfferCategory());
            return insertOffering.executeUpdate();
        } catch (SQLException e) {
            System.out.println("insertOffer with image - SQLException"+e.getMessage());
            return -1;
        }
    }

    public int insertOfferingsImage(String image_name, int offer_id, InputStream input, long len) throws SQLException {
        try {
            insertOfferingsImage.setString(1, image_name);
            insertOfferingsImage.setInt(2, offer_id);
            insertOfferingsImage.setBinaryStream(3, input, len);
            return insertOfferingsImage.executeUpdate();
        } catch (SQLException e) {
            System.out.println("insertOfferingsImage - SQLException"+e.getMessage());
            return -1;
        }
    }

    public List<Blob> selectOfferingsImage(int offer_id) throws SQLException {
        rs = null;
        List<Blob> blobs = new ArrayList<>();
        try{
            selectOfferingsImage.setInt(1, offer_id);
            rs = selectOfferingsImage.executeQuery();
            while(rs.next()){
                Blob blob;
                blob = rs.getBlob("image_nat");
                blobs.add(blob);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return blobs;
    }


    public List<Category> getAllCategories() {
        rs = null;
        List<Category> result = new ArrayList<>();
        try {
            rs = getAllCategories.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getLong("category_id"));
                category.setCategoryName(rs.getString("category_name"));
                category.setCategoryDescription(rs.getString("category_description"));
                category.setCategoryOrder(rs.getString("category_order"));
                category.setCategoryMainImage((com.mysql.jdbc.Blob) rs.getBlob("category_image"));
                result.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Category getCategoryById(int categoryId){
        rs = null;
        Category catg = new Category();
        try {
            getCategoryById.setInt(1, categoryId);
            rs = getCategoryById.executeQuery();
            while (rs.next()){
                catg.setId(rs.getLong("category_id"));
                catg.setCategoryName(rs.getString("category_name"));
                catg.setCategoryDescription(rs.getString("category_description"));
                catg.setCategoryOrder(rs.getString("category_order"));
                catg.setCategoryMainImage((com.mysql.jdbc.Blob) rs.getBlob("category_image"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return catg;
    }

    public Offer getOfferById(int offerId){
        rs = null;
        Offer offer = new Offer();
        try {
            getOfferById.setInt(1, offerId);
            rs = getOfferById.executeQuery();
            while (rs.next()){
                offer.setId(rs.getInt("offering_id"));
                offer.setOfferName(rs.getString("offering_name"));
                offer.setOfferDescription(rs.getString("offering_description"));
                offer.setOfferCategory(rs.getInt("category_id"));
                offer.setOfferMainImage((com.mysql.jdbc.Blob) rs.getBlob("image_nat"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return offer;
    }

    public int deleteOfferById(int offerId) throws SQLException {
        try {
            deleteOfferById.setInt(1, offerId);
            rs = deleteOfferById.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int insertCategory(Category category, InputStream input, long len) throws SQLException {
        try {
            insertCategory.setString(1, category.getCategoryName());
            insertCategory.setString(2, category.getCategoryDescription());
            insertCategory.setString(3, category.getCategoryOrder());
            insertCategory.setBinaryStream(4, input, len);
            return insertCategory.executeUpdate();
        } catch (SQLException e) {
            System.out.println("insertCategory with image - SQLException"+e.getMessage());
            return -1;
        }
    }

    public int updateCategoryWithImage(Category category, InputStream input, long len, int category_id) throws SQLException {
        try {
            updateCategoryWithImage.setString(1, category.getCategoryName());
            updateCategoryWithImage.setString(2, category.getCategoryDescription());
            updateCategoryWithImage.setString(3, category.getCategoryOrder());
            updateCategoryWithImage.setBinaryStream(4, input, len);
            updateCategoryWithImage.setInt(5, category_id);
            return updateCategoryWithImage.executeUpdate();
        } catch (SQLException e) {
            System.out.println("updateCategoryWithImage with image - SQLException"+e.getMessage());
            return -1;
        }
    }

    public int updateCategoryWithoutImage(Category category, int category_id) throws SQLException {
        try {
            updateCategoryWithoutImage.setString(1, category.getCategoryName());
            updateCategoryWithoutImage.setString(2, category.getCategoryDescription());
            updateCategoryWithoutImage.setString(3, category.getCategoryOrder());
            updateCategoryWithoutImage.setInt(4, category_id);
            return updateCategoryWithoutImage.executeUpdate();
        } catch (SQLException e) {
            System.out.println("updateCategoryWithoutImage with image - SQLException"+e.getMessage());
            return -1;
        }
    }

    public int updateOfferWithImage(Offer offer, InputStream input, long len, int category_id) throws SQLException {
        try {
            updateOfferWithImage.setString(1, offer.getOfferName());
            updateOfferWithImage.setString(2, offer.getOfferDescription());
            updateOfferWithImage.setString(3, offer.getOfferImageName());
            updateOfferWithImage.setBinaryStream(4, input, len);
            updateOfferWithImage.setInt(5, category_id);
            updateOfferWithImage.setInt(6, offer.getId());
            return updateOfferWithImage.executeUpdate();
        } catch (SQLException e) {
            System.out.println("updateOfferWithImage with image - SQLException"+e.getMessage());
            return -1;
        }
    }

    public int updateOfferWithoutImage(Offer offer, int category_id) throws SQLException {

        System.out.println("getId "+offer.getId());
        System.out.println("getOfferName "+offer.getOfferName());
        System.out.println("getOfferDescription "+offer.getOfferDescription());
        System.out.println("category_id "+category_id);

        try {
            updateOfferWithoutImage.setString(1, offer.getOfferName());
            updateOfferWithoutImage.setString(2, offer.getOfferDescription());
            updateOfferWithoutImage.setString(3, offer.getOfferImageName());
            updateOfferWithoutImage.setInt(4, category_id);
            updateOfferWithoutImage.setInt(5, offer.getId());
            return updateOfferWithoutImage.executeUpdate();
        } catch (SQLException e) {
            System.out.println("updateOfferWithoutImage with image - SQLException"+e.getMessage());
            return -1;
        }
    }

    public User getUserByUserName(String username) {
        rs = null;
        User user = new User();
        try {
            getUserByUserName.setString(1,username);
            rs = getUserByUserName.executeQuery();
            while (rs.next()) {
                user.setUser_id(rs.getInt("user_id"));
                user.setUsername(rs.getString("user_name"));
                //passw
            }
        } catch (SQLException e) {
            System.out.println("insertCategory with image - SQLException"+e.getMessage());
        }
        return user;
    }
}
