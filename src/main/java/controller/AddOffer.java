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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddOffer extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!ServletFileUpload.isMultipartContent(request)) {
            System.out.println("Nothing to upload");
            //doGet(req,resp);
            return;
        }
        FileItemFactory itemFactory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(itemFactory);
        ArrayList pathList = new ArrayList();
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
                pathList.add(file.getPath());
                System.out.println(file.getPath());
            }
        } catch (FileUploadException e) {
            System.out.println("FileUpload Exception");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Other Exception in doPost of Analysis servlet");
        }

        String name = request.getParameter("offerName");
        String description = request.getParameter("offerDescription");
        String imgPath = (String) pathList.get(0);
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
