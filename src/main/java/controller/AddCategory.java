package controller;

import dao.OfferDAO;
import daoImpl.OfferDAOImpl;
import model.Category;
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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class AddCategory extends HttpServlet {
    OfferDAO offerDAO = new OfferDAOImpl();
    Category category = new Category();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = null;
        String description = null;
        FileItemFactory itemFactory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(itemFactory);
        String imgName = "";
        try {
            List<FileItem> items = upload.parseRequest(request);
            Iterator<FileItem> iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (item.isFormField()) {
                    if ((item.getFieldName()).equals("categoryName")){
                        name = item.getString();
                    } else if ((item.getFieldName()).equals("categoryDescription")){
                        description = item.getString();
                    }
                } else {
                    String contentType = item.getContentType();
                    if (contentType != null) {
                        if (!contentType.equals("image/png") && !contentType.equals("image/jpeg")) {
                            System.out.println("Error. Only png or jpg format image files supported");
                            continue;
                        }
                        if (contentType.equals("image/png") || contentType.equals("image/jpeg")) {
                            File uploadDir = new File("/opt/app-root/src/src/main/webapp/images");
                            File file = File.createTempFile("img", ".png", uploadDir);
                            item.write(file);
                            InputStream input = new FileInputStream(file);

                            if (!file.getName().isEmpty()) {
                                imgName = (String) file.getName();
                            }
                            category.setCategoryName(name);
                            category.setCategoryDescription(description);
                            try {
                                offerDAO.insertCategory(category,input,file.length());
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
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

        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/addcategory.jsp");
        requestDispatcher.forward(request, response);
    }
}