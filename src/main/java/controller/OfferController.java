package controller;

import dao.OfferDAO;
import daoImpl.OfferDAOImpl;
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
import javax.servlet.http.Part;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by incrit.com on 8/21/17.
 */
public class OfferController extends HttpServlet{

    int BUFFER_LENGTH = 4096;

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
/*
        String filePath = req.getRequestURI();
        System.out.println("req.getRequestURI() "+req.getRequestURI());
        File file = new File(System.getenv("HOME") + filePath.replace("/offers",""));
        System.out.println("System.getenv(HOME) "+System.getenv("HOME") + filePath.replace("/offers",""));
        InputStream input = new FileInputStream(file);

        resp.setContentLength((int) file.length());
        System.out.println("file.length() "+file.length());
        resp.setContentType(new MimetypesFileTypeMap().getContentType(file));

        OutputStream output = resp.getOutputStream();
        byte[] bytes = new byte[BUFFER_LENGTH];
        int read = 0;
        while ((read = input.read(bytes, 0, BUFFER_LENGTH)) != -1) {
            output.write(bytes, 0, read);
            output.flush();
        }

        input.close();
        output.close();
*/
        getAllOffers(req, resp);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/jsp/offers.jsp");
        requestDispatcher.forward(req, resp);
    }
/*
    private String getFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim()
                        .replace("\"", "");
            }
        }
        return null;
    }
*/
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
        if (!ServletFileUpload.isMultipartContent(req)) {
            System.out.println("Nothing to upload");
            //doGet(req,resp);
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

                    //try to write permanently
                    InputStream input = new FileInputStream(file);
                    //resp.setContentLength((int) file.length());
                    System.out.println("file.length() " + file.length());
                    resp.setContentType(new MimetypesFileTypeMap().getContentType(file));

                    OutputStream output = resp.getOutputStream();
                    byte[] bytes = new byte[BUFFER_LENGTH];
                    int read = 0;
                    while ((read = input.read(bytes, 0, BUFFER_LENGTH)) != -1) {
                        output.write(bytes, 0, read);
                        //output.flush();
                    }
                    input.close();
                    output.close();

                    InputStream is = req.getInputStream();
                    System.out.println("file.getName() " + file.getName());
                    FileOutputStream os = new FileOutputStream(System.getenv("HOME") + "/src/main/webapp/images/" + file.getName());
                    System.out.println("System.getenv(HOME) + file.getName() " + System.getenv("HOME") + "/src/main/webapp/images/" + file.getName());
                    read = 0;
                    while ((read = is.read(bytes, 0, BUFFER_LENGTH)) != -1) {
                        os.write(bytes, 0, read);
                    }
                    //os.flush();
                    is.close();
                    os.close();
                    //end try to write permanently
                }
            }
        } catch (FileUploadException e) {
            System.out.println("FileUpload Exception");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Other Exception in doPost of Analysis servlet");
        }
/*
        PrintWriter out = resp.getWriter();
        for (Part part : req.getParts()) {
            InputStream is = req.getPart(part.getName()).getInputStream();
            System.out.println("part.getName() "+part.getName());
            String fileName = getFileName(part);
            FileOutputStream os = new FileOutputStream(System.getenv("HOME") + fileName);
            byte[] bytes = new byte[BUFFER_LENGTH];
            int read = 0;
            while ((read = is.read(bytes, 0, BUFFER_LENGTH)) != -1) {
                os.write(bytes, 0, read);
            }
            os.flush();
            is.close();
            os.close();
            out.println(fileName + " was uploaded to " + System.getenv("HOME"));
        }
*/
        doGet(req,resp);
        //RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/jsp/offers.jsp");
        //requestDispatcher.forward(req, resp);
    }
}
