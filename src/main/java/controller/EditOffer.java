package controller;

import com.mysql.jdbc.Blob;
import dao.OfferDAO;
import daoImpl.OfferDAOImpl;
import model.Category;
import model.Offer;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.activation.MimetypesFileTypeMap;
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

/**
 * Created by incrit.com on 8/21/17.
 */
public class EditOffer extends HttpServlet{
    OfferDAO offerDAO = new OfferDAOImpl();
    Offer offer = new Offer();

    //private void getAllCategories(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
    //    List<Category> categoryList = offerDAO.getAllCategories();
    //    request.setAttribute("categoryList", categoryList);
    //}

    public EditOffer() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ApplicationContext actx = new ClassPathXmlApplicationContext("beans.xml");
        OfferDAO offerDAO = (OfferDAO) actx.getBean("daoImpl");

        List<Category> categoryList = offerDAO.getAllCategories();
        req.setAttribute("categoryList", categoryList);

        Offer offerById = new Offer();
        String offer_id_str = req.getParameter("offer_id");
        try {
            offerById = (Offer) offerDAO.getOfferById(Integer.parseInt(offer_id_str));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("offerById", offerById);
        RequestDispatcher requestDispatcher;
        requestDispatcher = req.getRequestDispatcher("/WEB-INF/jsp/editoffer.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = null;
        String description = null;
        int categId = 0;
        int offerId = 0;
        FileItemFactory itemFactory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(itemFactory);
        String imgName = "";
        try {
            List<FileItem> items = upload.parseRequest(req);
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
                        categId = Integer.parseInt(item.getString());
                    } else if ((item.getFieldName()).equals("offering_id")){
                        offerId = Integer.parseInt(item.getString());
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
                    offer.setOfferCategory(categId);
                    offer.setId(offerId);
                    try {
                        if (leng == 0) {
                            offerDAO.updateOfferWithoutImage(offer, categId);
                        } else if (leng != 0) {
                            offerDAO.updateOfferWithImage(offer, input, leng, categId);
                        }
                        System.out.println(leng);
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

        doGet(req,resp);
    }
}
