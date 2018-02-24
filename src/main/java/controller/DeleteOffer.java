package controller;

import dao.OfferDAO;
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

public class DeleteOffer extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cat_id_str = request.getParameter("category_id");
        int catid = Integer.parseInt(cat_id_str);
        String offer_id_str = request.getParameter("offer_id");
        //ApplicationContext actx = new ClassPathXmlApplicationContext("beans.xml");
        //OfferDAO offerDAO = (OfferDAO) actx.getBean("daoImpl");
        //try {
        //    offerDAO.deleteOfferById(Integer.parseInt(offer_id_str));
        //} catch (SQLException e) {
        //    e.printStackTrace();
        //}
        request.setAttribute("category_id", cat_id_str);
        RequestDispatcher requestDispatcher;
        requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/offers");
        requestDispatcher.forward(request, response);
    }

    }
