package database;

import model.Category;
import model.Offer;
import org.apache.log4j.Logger;

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

    private static final Logger LOGGER = Logger.getLogger(DBConnection.class);
    private Connection conn = null;
    private ResultSet rs = null;
/*
    private static PreparedStatement loadAllRoles;
    private static PreparedStatement loadRolesById;
    private static PreparedStatement loadAllLogins;
    private static PreparedStatement loadAccountByLogin;
    private static PreparedStatement getIdAccountRoles;
    private static PreparedStatement getListStudents;
    private static PreparedStatement changeStudent;
    private static PreparedStatement insertStudent;
    private static PreparedStatement deleteStudent;
    private static PreparedStatement selectStudentById;
    private static PreparedStatement getListTerms;
    private static PreparedStatement insertTerm;
    private static PreparedStatement getIdTermDiscipline;
    private static PreparedStatement getTermById;
    private static PreparedStatement deleteTerm;
    private static PreparedStatement changeTerm;
    private static PreparedStatement changeDiscipline;
    */
    private static PreparedStatement getAllOffers;
    private static PreparedStatement insertOffering;
    private static PreparedStatement getAllCategories;
    private static PreparedStatement insertCategory;
    private static PreparedStatement insertOfferingsImage;
    private static PreparedStatement selectOfferingsImage;
    /*
    private static PreparedStatement deleteDiscipline;
    private static PreparedStatement selectDisciplineById;
    private static PreparedStatement getTermByName;
    private static PreparedStatement insertTermDiscipline;
    private static PreparedStatement deleteTermDiscipline;
    private static PreparedStatement insertMark;
    private static PreparedStatement getMarksDisciplines;
    private static PreparedStatement getDisciplinesByIdTermIdStudent;
*/


    public DBConnection(String url, String user, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //conn = DriverManager.getConnection(url, user, password);
            conn = DriverManager.getConnection(url+"?user="+user+"&password="+password+"&characterEncoding=UTF-8");
            loadPreparedStatements();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            LOGGER.error("ClassNotFoundException - > DBConnection(String url); " + e);
        }
    }

    private void loadPreparedStatements() {
        try {
            /*
            loadAllRoles = conn.prepareStatement("SELECT * FROM `roles`");
            loadAllLogins = conn.prepareStatement("SELECT login, id FROM account");
            loadAccountByLogin = conn.prepareStatement("SELECT * FROM account WHERE login = ?");
            loadRolesById = conn.prepareStatement("SELECT * FROM account_roles WHERE id =?");
            getIdAccountRoles = conn.prepareStatement("SELECT id_roles FROM account_roles WHERE id_account = ?");
            getListStudents = conn.prepareStatement("SELECT * FROM students");
            changeStudent = conn.prepareStatement("UPDATE `students` SET `name`=?,`lastname`=?,`group`=?,`date`=? WHERE `id`=?");
            insertStudent = conn.prepareStatement("INSERT INTO `students` SET `name`=?, `lastname`=?, `group`=?, `date`=?");
            deleteStudent = conn.prepareStatement("DELETE FROM `students` WHERE `id`=?");
            selectStudentById = conn.prepareStatement("SELECT * FROM students WHERE id = ?");
            getListTerms = conn.prepareStatement("SELECT * FROM term");
            changeDiscipline = conn.prepareStatement("UPDATE `discipline` SET `name`=? WHERE `id`=?");
            */
            getAllOffers = conn.prepareStatement("SELECT * FROM offering");
            insertOffering = conn.prepareStatement("INSERT INTO offering SET `offering_name`=?, `offering_description`=?, `offer_image_name`=?, `image_nat`=?");
            getAllCategories = conn.prepareStatement("SELECT * FROM category");
            insertCategory = conn.prepareStatement("INSERT INTO category SET `category_name`=?, `category_description`=?, `category_order`=?, `category_image`=?");
            insertOfferingsImage = conn.prepareStatement("INSERT INTO images SET `image_name`=?, `offer_id`=?, `image_nat`=?");
            selectOfferingsImage = conn.prepareStatement("SELECT * FROM images where `offer_id`=?");
            /*
            deleteDiscipline = conn.prepareStatement("DELETE FROM `discipline` WHERE `id`=?");
            selectDisciplineById = conn.prepareStatement("SELECT * FROM discipline WHERE id = ?");
            getIdTermDiscipline = conn.prepareStatement("SELECT id_discipline FROM term_discipline WHERE id_term = ?");
            insertTerm = conn.prepareStatement("INSERT INTO `term` SET `duration`=?, `name`=?");
            getTermById = conn.prepareStatement("SELECT * FROM term WHERE id=?");
            getTermByName = conn.prepareStatement("SELECT * FROM term WHERE `name`=?");
            insertTermDiscipline = conn.prepareStatement("INSERT INTO term_discipline SET `id_term`=?, `id_discipline`=?");
            deleteTerm = conn.prepareStatement("DELETE FROM `term` WHERE `id`=?");
            changeTerm = conn.prepareStatement("UPDATE `term` SET `duration`=?,`name`=? WHERE `id`=?");
            deleteTermDiscipline = conn.prepareStatement("DELETE FROM `term_discipline` WHERE `id_term`=?");
            insertMark = conn.prepareStatement("INSERT INTO progress SET `id_student`=?, `id_term_discipline`=?, `mark`=?, `id_term`=?");
            getMarksDisciplines=conn.prepareStatement("SELECT mark FROM progress WHERE id_term_discipline = ? AND id_term = ? AND id_student = ?");
            getDisciplinesByIdTermIdStudent=conn.prepareStatement("SELECT * FROM progress WHERE id_term = ? AND id_student = ?");
            */
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void closePreparedStatements() {
        try {
            /*
            loadAllRoles.close();
            loadAllLogins.close();
            loadAccountByLogin.close();
            loadRolesById.close();
            getIdAccountRoles.close();
            getListStudents.close();
            getListTerms.close();
            */
            getAllOffers.close();
            insertOffering.close();
            insertOfferingsImage.close();
            selectOfferingsImage.close();
            /*
            selectDisciplineById.close();
            changeDiscipline.close();
            changeStudent.close();
            insertStudent.close();
            selectStudentById.close();
            deleteDiscipline.close();
            deleteStudent.close();
            getIdTermDiscipline.close();
            insertTerm.close();
            getTermById.close();
            getTermByName.close();
            insertTermDiscipline.close();
            deleteTerm.close();
            changeTerm.close();
            deleteTermDiscipline.close();
            insertMark.close();
            getMarksDisciplines.close();
            getDisciplinesByIdTermIdStudent.close();
            */
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
                offer.setId(rs.getLong("offering_id"));
                offer.setOfferName(rs.getString("offering_name"));
                offer.setOfferDescription(rs.getString("offering_description"));
                offer.setOfferImageName(rs.getString("offer_image_name"));
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
            LOGGER.debug("insertOffer - SQLException");
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
            return insertOffering.executeUpdate();
        } catch (SQLException e) {
            LOGGER.debug("insertOffer with image - SQLException");
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
            LOGGER.debug("insertOfferingsImage - SQLException");
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
            LOGGER.error("selectOfferingsImage - > Error; " + e);
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

    public int insertCategory(Category category, InputStream input, long len) throws SQLException {
        try {
            insertCategory.setString(1, category.getCategoryName());
            insertCategory.setString(2, category.getCategoryDescription());
            insertCategory.setString(3, category.getCategoryOrder());
            if (input != null) {
                insertCategory.setBinaryStream(4, input, len);
            }
            return insertCategory.executeUpdate();
        } catch (SQLException e) {
            LOGGER.debug("insertCategory with image - SQLException");
            return -1;
        }
    }

}
