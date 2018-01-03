package controller;

import dao.OfferDAO;
import daoImpl.OfferDAOImpl;
import model.Offer;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by incrit.com on 8/21/17.
 */
public class OfferController extends HttpServlet{

    public OfferController() {
        super();
    }
/*
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        OfferDAO categoryDao = new OfferDAOImpl();
        // calling DAO method to retrieve offerList from Database
        List<Category> categoryList = categoryDao.findAllCategories();
        ServletContext context = config.getServletContext();
        context.setAttribute("categoryList", categoryList);
    }
*/
    private void getAllOffers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        OfferDAO offerDAO = new OfferDAOImpl();
        List<Offer> offerList = offerDAO.getAllOffers();
        req.setAttribute("offerList", offerList);
    }
/*
    private void findAllCategories(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        OfferDAO offerDAO = new OfferDAOImpl();
        List<Category> categoryList = offerDAO.findAllCategories();
        request.setAttribute("categoryList", categoryList);
    }*/

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        getAllOffers(req, resp);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/jsp/offers.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        //String url = "/jsp/offers.jsp";
        //String action = req.getParameter("action");
        //String category = req.getParameter("category");
        //if (action != null) {
            //switch (action) {
                //case "allOffers":
                    //findAllOffers(req, resp);
                    //break;
               /* case "category":
                    findAllCategories(req, resp);
                    url = base + "category.jsp?category=" + category;
                    break;*/
                //}
        doGet(req,resp);
        //RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/jsp/offers.jsp");
        //requestDispatcher.forward(req, resp);
    }
}
