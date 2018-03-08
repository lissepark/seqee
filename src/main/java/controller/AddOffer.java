package controller;

import dao.OfferDAO;
import daoImpl.OfferDAOImpl;
import model.Category;
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
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AddOffer extends HttpServlet {

    int BUFFER_LENGTH = 4096;
    OfferDAO offerDAO = new OfferDAOImpl();
    Offer offer = new Offer();

    private void getAllCategories(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        OfferDAO offerDAO = new OfferDAOImpl();
        List<Category> categoryList = offerDAO.getAllCategories();
        request.setAttribute("categoryList", categoryList);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = null;
        String description = null;
        String categId = null;
        int success = 0;
        FileItemFactory itemFactory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(itemFactory);
        String imgName = "";
        try {
            List<FileItem> items = upload.parseRequest(request);
            Iterator<FileItem> iter = items.iterator();
            File file = null;
            InputStream input = null;
            long leng = 0;
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (item.isFormField()) {
                    if ((item.getFieldName()).equals("offerName")){
                        name = item.getString();
                        name = new String(name.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                    } else if ((item.getFieldName()).equals("offerDescription")){
                        description = item.getString();
                        description = new String(description.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                    } else if ((item.getFieldName()).equals("categoryId")){
                        categId = item.getString();
                    }
                } else {
                    String contentType = item.getContentType();
                    if (!contentType.equals("application/octet-stream")) {
                        if (!contentType.equals("image/png") && !contentType.equals("image/jpeg")) {
                            System.out.println("Error. Only png or jpg format image files supported");
                            continue;
                        }
                        if (contentType.equals("image/png") || contentType.equals("image/jpeg")) {
                            //File uploadDir = new File("/home/sergii/Documents/");//for localhost
                            File uploadDir = new File("/opt/app-root/src/src/main/webapp/images");
                            file = File.createTempFile("img", ".png", uploadDir);
                            item.write(file);
                            input = new FileInputStream(file);
                            leng = file.length();
                        }
                    }
                    offer.setOfferName(name);
                    offer.setOfferDescription(description);
                    offer.setOfferCategory(Integer.parseInt(categId));
                    try {
                        success = offerDAO.insertOffer(offer,input,leng);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (FileUploadException e) {
            System.out.println("FileUpload Exception");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Other Exception in doPost of Analysis servlet");
        }
        request.setAttribute("success", success);
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            getAllCategories(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/addoffer.jsp");
        requestDispatcher.forward(request, response);
    }
}
