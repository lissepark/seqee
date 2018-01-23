package controller;

import com.mysql.jdbc.Blob;
import dao.OfferDAO;
import daoImpl.OfferDAOImpl;
import model.Category;
import model.Offer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ApplicationContext actx = new ClassPathXmlApplicationContext("/WEB-INF/beans.xml");

        OfferDAO offerDAO = new OfferDAOImpl();
        List<Category> categoryList = offerDAO.getAllCategories();
        req.setAttribute("categoryList", categoryList);

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
