package controller;

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
import java.util.List;

public class DeleteOffer extends HttpServlet {

    public DeleteOffer() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cat_id_str = request.getParameter("category_id");
        String offer_id_str = request.getParameter("offer_id");
        int catid = Integer.parseInt(cat_id_str);
        Category category = null;
        Offer offer = null;
        String offerNameToDelete = "";

        ApplicationContext actx = new ClassPathXmlApplicationContext("beans.xml");
        OfferDAO offerDAO = (OfferDAO) actx.getBean("daoImpl");
        List<Offer> offerList = offerDAO.getOffersByCategoryId(catid);
        try {
            category = offerDAO.getCategoryById(catid);
            offer = offerDAO.getOfferById(Integer.parseInt(offer_id_str));
            offerNameToDelete = offer.getOfferName();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("offerList", offerList);
        request.setAttribute("category_id", catid);
        request.setAttribute("category", category);
        request.setAttribute("offerNameToDelete", offerNameToDelete);

        try {
            offerDAO.deleteOfferById(Integer.parseInt(offer_id_str));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        RequestDispatcher requestDispatcher;
        requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/deleteoffer");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    }
