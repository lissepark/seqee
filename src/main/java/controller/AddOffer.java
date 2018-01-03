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
import java.util.Iterator;
import java.util.List;

public class AddOffer extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = null;
        String description = null;

        if (!ServletFileUpload.isMultipartContent(request)) {
            System.out.println("Nothing to upload");
            //doGet(req,resp);
            return;
        }

        FileItemFactory itemFactory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(itemFactory);
        ArrayList pathList = new ArrayList();
        String imgPath = "";
        try {
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (item.isFormField()) {
                    System.out.println("field name " + item.getFieldName());
                    System.out.println("field value " + item.getString());
                    if ((item.getFieldName()) == "offerName"){
                        name = item.getString();
                    } else if ((item.getFieldName()) == "offerDescription"){
                        description = item.getString();
                    }
                } else {
                    String contentType = item.getContentType();
                    if (contentType != null) {
                        if (contentType.equals("image/png")) {
                            File uploadDir = new File("/opt/app-root/src/src/main/webapp/images");
                            File file = File.createTempFile("img", ".png", uploadDir);
                            item.write(file);
                            pathList.add(file.getPath());
                            //imgPath = file.getPath();
                        }
                    }
                }
            }
        } catch (FileUploadException e) {
            System.out.println("FileUpload Exception");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Other Exception in doPost of Analysis servlet");
        }

        if (!pathList.isEmpty()) {
            imgPath = (String) pathList.get(0);
            //set cycle if images are few
            System.out.println("imgPath" + imgPath);
        }

        System.out.println("name" + name);
        System.out.println("description" + description);

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
