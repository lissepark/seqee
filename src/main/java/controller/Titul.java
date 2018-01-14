package controller;

import com.mysql.jdbc.Blob;
import dao.OfferDAO;
import daoImpl.OfferDAOImpl;
import model.Category;
import model.Offer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sergii on 9/9/17.
 */
public class Titul extends HttpServlet {

    private void getAllCategories(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        OfferDAO offerDAO = new OfferDAOImpl();
        List<Category> categoryList = offerDAO.getAllCategories();
        req.setAttribute("categoryList", categoryList);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            getAllCategories(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req,resp);
        //RequestDispatcher requestDispatcher;
        //requestDispatcher = req.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
        //requestDispatcher.forward(req, resp);
    }
}
