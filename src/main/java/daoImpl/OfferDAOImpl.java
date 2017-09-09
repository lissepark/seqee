package daoImpl;

import dao.OfferDAO;
import model.Offer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by incrit.com on 8/20/17.
 */
public class OfferDAOImpl implements OfferDAO {
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
        }
    }
    private Connection getConnection() throws SQLException {
        String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
        String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
        String url = String.format("jdbc:mysql://%s:%s/sequoia", host, port);
        return DriverManager.getConnection(url,
                "adminavnVA73", "enCFJVl8g98w");
        //return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bookstore","root","");
    }
    private void closeConnection(Connection connection) {
        if (connection == null)
            return;
        try {
            connection.close();
        } catch (SQLException ex) {
        }
    }
    public List<Offer> findAllOffers() {
        List<Offer> result = new ArrayList<>();
        String sql = "select * from offering";
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Offer offer = new Offer();
                offer.setId(resultSet.getLong("offering_id"));
                offer.setOfferName(resultSet.getString("offering_name"));
                offer.setOfferDescription(resultSet.getString("offering_description"));
                result.add(offer);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return result;
    }

    public List<Offer>searchOffersByKeyword(String keyWord) {
        return null;
    }

/*
    public List<Category> findAllCategories() {
        List<Category> result = new ArrayList<>();
        String sql = "select * from category";
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getLong("category_id"));
                category.setCategoryName(resultSet
                        .getString("category_name"));
                category.setCategoryDescription(resultSet
                        .getString("category_description"));
                result.add(category);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return result;
    }*/
    public void insert(Offer offering) {
    }
    public void update(Offer offering) {
    }
    public void delete(Long offerId) {
    }
}
