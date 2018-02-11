package database;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import constants.Constants;
import model.Category;
import model.Offer;
import model.User;

public class DataService {

    private static List<DBConnection> connectionPool = new ArrayList<DBConnection>();
    private static Object monitor = new Object();

    public boolean init() {
        try{
            for (int i = 0; i < 5; i++){
                newConnection();
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public DBConnection getDBConnection() {
        synchronized (monitor) {
            if (connectionPool.isEmpty()){
                newConnection();
            }
            DBConnection conn = connectionPool.remove(0);
            return conn;
        }
    }

    public void putDBConnection(DBConnection conn) {
        synchronized (monitor){
            connectionPool.add(conn);
        }
    }

    private void newConnection() {
        DBConnection conn = new DBConnection(Constants.CONNECTING_URL, Constants.CONNECTING_USER, Constants.CONNECTING_PASSWORD);
        synchronized (monitor) {
            connectionPool.add(conn);
        }
    }

    public List<Offer> getAllOffers(){
        DBConnection conn = getDBConnection();
        List <Offer> result = conn.getAllOffers();
        this.putDBConnection(conn);
        return result;
    }

    public List<Offer> getOffersByCategoryId(int categoryId) {
        DBConnection conn = getDBConnection();
        List <Offer> result = conn.getOffersByCategoryId(categoryId);
        this.putDBConnection(conn);
        return result;
    }

    public int insertOffer(Offer offer) throws SQLException {
        DBConnection conn = getDBConnection();
        int result = conn.insertOffer(offer);
        this.putDBConnection(conn);
        return result;
    }

    public int insertOffer(Offer offer, InputStream input, long len) throws SQLException {
        DBConnection conn = getDBConnection();
        int result = conn.insertOffer(offer,input,len);
        this.putDBConnection(conn);
        return result;
    }

    public List<Category> getAllCategories(){
        DBConnection conn = getDBConnection();
        List <Category> result = conn.getAllCategories();
        this.putDBConnection(conn);
        return result;
    }

    public int insertCategory(Category category, InputStream input, long len) throws SQLException {
        DBConnection conn = getDBConnection();
        int result = conn.insertCategory(category,input,len);
        this.putDBConnection(conn);
        return result;
    }

    public int insertOfferingsImage(String image_name, int offer_id, InputStream input, long len) throws SQLException {
        DBConnection conn = getDBConnection();
        int result = conn.insertOfferingsImage(image_name, offer_id, input, len);
        this.putDBConnection(conn);
        return result;
    }

    public List<Blob> selectOfferingsImage(int offer_id) throws SQLException {
        DBConnection conn = getDBConnection();
        List<Blob> blobs = conn.selectOfferingsImage(offer_id);
        this.putDBConnection(conn);
        return blobs;
    }

    public User getUserByUserName(String user_name) throws SQLException {
        DBConnection conn = getDBConnection();
        User user = conn.getUserByUserName(user_name);
        this.putDBConnection(conn);
        return user;
    }

    public void close() {
    }

}
