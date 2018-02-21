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
public class EditCategory extends HttpServlet{

    public EditCategory() {
        super();
    }

    OfferDAO offerDAO = new OfferDAOImpl();
    Category category = new Category();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ApplicationContext actx = new ClassPathXmlApplicationContext("beans.xml");
        OfferDAO offerDAO = (OfferDAO) actx.getBean("daoImpl");
        Category categoryById = new Category();
        String category_id_str = req.getParameter("category_id");
        try {
            categoryById = (Category) offerDAO.getCategoryById(Integer.parseInt(category_id_str));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("categoryById", categoryById);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/jsp/editcategory.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = null;
        String description = null;
        int catg_id = 0;
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
                    if ((item.getFieldName()).equals("categoryName")){
                        name = item.getString();
                        name = new String(name.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                    } else if ((item.getFieldName()).equals("categoryDescription")){
                        description = item.getString();
                        description = new String(description.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                    } else if ((item.getFieldName()).equals("category_id")){
                        catg_id = Integer.parseInt(item.getString());
                    }
                } else {
                    String contentType = item.getContentType();
                    if (!contentType.equals("application/octet-stream")) {
                        if (!contentType.equals("image/png") && !contentType.equals("image/jpeg")) {
                            System.out.println("Error. Only png or jpg format image files supported");
                            continue;
                        }
                        if (contentType.equals("image/png") || contentType.equals("image/jpeg")) {
                            File uploadDir = new File("/opt/app-root/src/src/main/webapp/images");
                            file = File.createTempFile("img", ".png", uploadDir);
                            item.write(file);
                            input = new FileInputStream(file);
                            leng = file.length();
                        }
                    }
                    category.setCategoryName(name);
                    category.setCategoryDescription(description);
                    try {
                        if (leng == 0) {
                            offerDAO.updateCategoryWithoutImage(category,catg_id);
                        } else if (leng != 0) {
                            offerDAO.updateCategoryWithImage(category, input, leng,catg_id);
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
            System.out.println("Other Exception in doPost of EditCategory servlet");
        }
        doGet(req, resp);
        //RequestDispatcher requestDispatcher;
        //requestDispatcher = req.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
        //requestDispatcher.forward(req, resp);
    }
}