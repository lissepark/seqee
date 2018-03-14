package controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OfferCard extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String offer_id_str = request.getParameter("offer_id");

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/offercard.jsp");
        requestDispatcher.forward(request, response);

    }

}
