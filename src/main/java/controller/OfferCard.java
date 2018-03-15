package controller;

import dao.OfferDAO;
import daoImpl.OfferDAOImpl;
import database.DataService;
import model.Category;
import model.Offer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class OfferCard extends HttpServlet {

    OfferDAO offerDAO = new OfferDAOImpl();
    Offer offer = new Offer();
    Category categById = new Category();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String offer_id_str = request.getParameter("offer_id");

        try {
            offer = offerDAO.getOfferById(Integer.parseInt(offer_id_str));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            categById = (Category) offerDAO.getCategoryById(offer.getOfferCategory());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int cid = (categById.getId()).intValue();
        String breadcrumb = "<a href=http://www.fjwsequoia.com>Главная</a> ";
        String path1 = "";

        String pathLast = " / <a href=/offers?category_id="+categById.getId()+"> "+categById.getCategoryName()+"</a>";
        while (categById.getParentCategory() != 0) {
            try {
            categById = (new DataService()).getCategoryById(categById.getParentCategory());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            path1 = " / <a href=/offers?category_id="+categById.getId()+">" + categById.getCategoryName()+"</a>" + path1;
        }
        breadcrumb = breadcrumb + path1 + pathLast;

        request.setAttribute("offer", offer);
        request.setAttribute("breadcrumb", breadcrumb);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/offercard.jsp");
        requestDispatcher.forward(request, response);

    }

}
