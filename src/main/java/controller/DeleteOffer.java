package controller;

import dao.OfferDAO;
import daoImpl.OfferDAOImpl;
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

        ApplicationContext actx = new ClassPathXmlApplicationContext("beans.xml");
        OfferDAO offerDAO = (OfferDAO) actx.getBean("daoImpl");
        try {
            offerDAO.deleteOfferById(Integer.parseInt(offer_id_str));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Offer> offerList = offerDAO.getOffersByCategoryId(catid);
        request.setAttribute("offerList", offerList);
        request.setAttribute("category_id", catid);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/offers");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    }
