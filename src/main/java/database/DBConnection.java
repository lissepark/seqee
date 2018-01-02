package database;

import model.Offer;
import org.apache.log4j.Logger;

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
    private ResultSet rs2 = null;
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
            conn = DriverManager.getConnection(url, user, password);
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
            insertOffering = conn.prepareStatement("INSERT INTO offering SET `offering_name`=?, `offering_description`=?, `offer_image_path`=?");
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
        //getListDisciplines
        rs = null;
        List<Offer> result = new ArrayList<>();
        try {
            rs = getAllOffers.executeQuery();
            while (rs.next()) {
                Offer offer = new Offer();
                offer.setId(rs.getLong("offering_id"));
                offer.setOfferName(rs.getString("offering_name"));
                offer.setOfferDescription(rs.getString("offering_description"));
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
            insertOffering.setString(3, offer.getOfferImagePath());
            return insertOffering.executeUpdate();
        } catch (SQLException e) {
            LOGGER.debug("insertDiscipline - SQLException");
            return -1;
        }
    }

}
