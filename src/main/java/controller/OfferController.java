package controller;

import com.mysql.jdbc.Blob;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by incrit.com on 8/21/17.
 */
public class OfferController extends HttpServlet{

    int BUFFER_LENGTH = 4096;

    public OfferController() {
        super();
    }

    private void getAllOffers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        OfferDAO offerDAO = new OfferDAOImpl();
        List<Offer> offerList = offerDAO.getAllOffers();

        int offer_id = 0;
        Iterator<Offer> iter = offerList.iterator();
        while (iter.hasNext()) {
            Offer offer = (Offer) iter.next();
            offer_id = offer.getId().intValue();
            List<java.sql.Blob> blobs = new ArrayList<>();
            try {
                blobs =  offerDAO.selectOfferingsImage(offer_id);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            File file = new File("/opt/app-root/src/src/main/webapp/images/image1.png");
            FileOutputStream fos = new FileOutputStream(file);

            Blob blob;
            Iterator<java.sql.Blob> iterBlob = blobs.iterator();
            while (iterBlob.hasNext()) {
                blob = (Blob) iterBlob.next();
                byte b[] = new byte[0];
                try {
                    b = new byte[(int) blob.length()];
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    b = blob.getBytes(1, (int) blob.length());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                fos.write(b);
            }
            fos.close();
        }


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
                    //resp.setContentType(new MimetypesFileTypeMap().getContentType(file));

                    OutputStream output = new FileOutputStream(System.getenv("HOME") + "/src/main/webapp/images/" + file.getName());
                    byte[] bytes = new byte[BUFFER_LENGTH];
                    int read = 0;
                    while ((read = input.read(bytes, 0, BUFFER_LENGTH)) != -1) {
                        output.write(bytes, 0, read);
                        //output.flush();
                    }
                    input.close();
                    output.close();

                    System.out.println("file.getName() " + file.getName());
                    System.out.println("System.getenv(HOME) + file.getName() " + System.getenv("HOME") + "/images/" + file.getName());
                    //end try to write permanently
                }
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
