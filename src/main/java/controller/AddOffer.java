package controller;

import dao.OfferDAO;
import daoImpl.OfferDAOImpl;
import model.Offer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AddOffer extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("offerName");
        String description = request.getParameter("offerDescription");
        String imgPath = "testPath";
        Offer offer = new Offer();
        offer.setOfferName(name);
        offer.setOfferDescription(description);
        offer.setOfferImagePath(imgPath);
        //String group = request.getParameter("group");
        //String date = request.getParameter("date");
        OfferDAO offerDAO = new OfferDAOImpl();
        try {
            offerDAO.insertOffer(offer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/addoffer.jsp");
        requestDispatcher.forward(request, response);
    }
}
