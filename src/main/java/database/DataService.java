package database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import constants.Constants;
import model.Offer;
import org.apache.log4j.Logger;

public class DataService {

    private static final Logger LOGGER = Logger.getLogger(DataService.class);
    private static List<DBConnection> connectionPool = new ArrayList<DBConnection>();
    private static Object monitor = new Object();

    public boolean init() {
        try{
            LOGGER.info("init database");
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


/*
    public List<Discipline> getAllDisciplines(){
        DBConnection conn = getDBConnection();
        List <Discipline> result = conn.getAllDisciplines();
        this.putDBConnection(conn);
        return result;
    }

    public int insertDiscipline(Discipline discipline) throws SQLException {
        DBConnection conn = getDBConnection();
        int result = conn.insertDiscipline(discipline);
        this.putDBConnection(conn);
        return result;
    }

    public int changeDiscipline(Discipline discipline) throws SQLException {
        DBConnection conn = getDBConnection();
        int result = conn.changeDiscipline(discipline);
        this.putDBConnection(conn);
        return result;
    }

    public Discipline selectDisciplineById(int id) throws SQLException {
        DBConnection conn = getDBConnection();
        Discipline result = conn.selectDisciplineById(id);
        this.putDBConnection(conn);
        return result;
    }

    public int deleteDiscipline(Discipline discipline) throws SQLException {
        DBConnection conn = getDBConnection();
        int result = conn.deleteDiscipline(discipline);
        this.putDBConnection(conn);
        return result;
    }
*/
    public void close() {
    }

}
