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
 * Created by sergii on 1/9/18.
 */
public class Categories extends HttpServlet {
    int BUFFER_LENGTH = 4096;

    public Categories() {
        super();
    }

    private void getAllOffers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        OfferDAO offerDAO = new OfferDAOImpl();
        List<Offer> offerList = offerDAO.getAllOffers();
        int offer_n =0;
        int offer_id = 0;
        int blob_n = 0;
        Iterator<Offer> iter = offerList.iterator();
        while (iter.hasNext()) {
            Offer offer = (Offer) iter.next();
            offer_id = offer.getId();
            Blob blob;
            blob_n = 0;

            blob_n++;
            offer_n++;
        }
        req.setAttribute("offerList", offerList);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            getAllOffers(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/jsp/categories.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
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
                    InputStream input = new FileInputStream(file);
                    System.out.println("file.length() " + file.length());

                    OutputStream output = new FileOutputStream(System.getenv("HOME") + "/src/main/webapp/images/" + file.getName());
                    byte[] bytes = new byte[BUFFER_LENGTH];
                    int read = 0;
                    while ((read = input.read(bytes, 0, BUFFER_LENGTH)) != -1) {
                        output.write(bytes, 0, read);
                    }
                    input.close();
                    output.close();

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
