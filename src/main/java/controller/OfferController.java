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

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by incrit.com on 8/21/17.
 */
public class OfferController extends HttpServlet{

    //int BUFFER_LENGTH = 4096;

    public OfferController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String category_id_str = req.getParameter("category_id");
        OfferDAO offerDAO = new OfferDAOImpl();
        List<Offer> offerList = offerDAO.getOffersByCategoryId(Integer.parseInt(category_id_str));
        List<Category> categoryList = offerDAO.getAllCategories();
        Category categoryById = new Category();
        try {
            categoryById = offerDAO.getCategoryById(Integer.parseInt(category_id_str));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("categoryList", categoryList);
        req.setAttribute("offerList", offerList);
        req.setAttribute("categoryById", categoryById);
        req.setAttribute("category_id", Integer.parseInt(category_id_str));
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/jsp/offers.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!ServletFileUpload.isMultipartContent(req)) {
            System.out.println("Nothing to upload");
            return;
        }
        FileItemFactory itemFactory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(itemFactory);
        ArrayList pathList = new ArrayList();
        try {
            List<FileItem> items = upload.parseRequest(req);
            for (FileItem item : items) {
                String contentType = item.getContentType();
                if (contentType != null) {
                    if (!contentType.equals("image/png")) {
                        System.out.println("Error. Only png or jpg format image files supported");
                        continue;
                    }
                    File uploadDir = new File("/opt/app-root/src/src/main/webapp/images");
                    File file = File.createTempFile("img", ".png", uploadDir);
                    item.write(file);
                    pathList.add(file.getPath());
                    System.out.println(file.getPath());
                    try (FileInputStream input = new FileInputStream(file);
                         OutputStream output = new FileOutputStream(System.getenv("HOME") + "/src/main/webapp/images/" + file.getName())) {

                        byte[] bytes = new byte[(int) file.length()];
                        int read = 0;
                        while ((read = input.read(bytes, 0, (int) file.length())) != -1) {
                            output.write(bytes, 0, read);
                            //output.flush();
                        }
                    }
                    System.out.println("file.getName() " + file.getName());
                    System.out.println("System.getenv(HOME) + file.getName() " + System.getenv("HOME") + "/images/" + file.getName());
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
