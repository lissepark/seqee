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
    private void findAllOffers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        OfferDAO offerDAO = new OfferDAOImpl();
        List<Offer> offerList = offerDAO.findAllOffers();
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
        findAllOffers(req, resp);

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
				
				
		if (!ServletFileUpload.isMultipartContent(request)) {
            System.out.println("Nothing to upload");
			//doGet(req,resp);
            return;
        }
        FileItemFactory itemFactory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(itemFactory);
        //ArrayList pathList = new ArrayList();
        try {
            List<FileItem> items = upload.parseRequest(request);
            for (FileItem item : items) {
                String contentType = item.getContentType();
                if (!contentType.equals("image/png")) {
                        System.out.println("Error. Only png or jpg format image files supported");
                        continue;
                }
                File uploadDir = new File("/opt/app-root/src/src/main/webapp/images");
                File file = File.createTempFile("img", ".png", uploadDir);
                item.write(file);
                //pathList.add(file.getPath());
                System.out.println(file.getPath());
            }
        } catch (FileUploadException e) {
            System.out.println("FileUpload Exception");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Other Exception in doPost of Analysis servlet");
        }
		
        doGet(req,resp);
        //RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/jsp/offers.jsp");
        //requestDispatcher.forward(req, resp);
    }
}
