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
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class AddCategory extends HttpServlet {
    OfferDAO offerDAO = new OfferDAOImpl();
    Category category = new Category();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = null;
        int categId = 0;
        int isHide = 0;
        String description = null;
        int success = 0;
        FileItemFactory itemFactory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(itemFactory);
        String imgName = "";
        try {
            List<FileItem> items = upload.parseRequest(request);
            Iterator<FileItem> iter = items.iterator();
            File file = null;
            //InputStream input = null;
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
                    }else if ((item.getFieldName()).equals("categoryId")){
                        categId = Integer.parseInt(item.getString());
                    }else if ((item.getFieldName()).equals("isCategoryHide")){
                        if ((Integer.parseInt(item.getString())) == 1) {
                            isHide = Integer.parseInt(item.getString());
                        }else {
                            isHide = 0;
                        }
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
                            //input = new FileInputStream(file);
                            leng = file.length();
                        }
                    }
                    category.setCategoryName(name);
                    category.setCategoryDescription(description);
                    try (FileInputStream input = new FileInputStream(file)) {
                        try {
                            success = offerDAO.insertCategory(category, input, leng, categId, isHide);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        try {
                            success = offerDAO.insertCategory(category, null, leng, categId, isHide);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    } catch (IOException e) {
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
        List<Category> categoryList = offerDAO.getAllCategories();
        request.setAttribute("categoryList", categoryList);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/addcategory.jsp");
        requestDispatcher.forward(request, response);
    }
}
