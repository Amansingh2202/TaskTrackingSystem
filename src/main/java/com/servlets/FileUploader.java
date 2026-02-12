package com.servlets;

import com.util.DataInitializer;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet("/uploadFile")
@MultipartConfig
public class FileUploader extends HttpServlet {

    String finalFilePath="C:\\Users\\Axeno\\Desktop\\Final_Java_Project\\TaskTrackingSystem\\src\\main\\webapp\\WEB-INF\\data\\programData.json";

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {

            // Getting uploaded file from api request
            Part filePart = req.getPart("file");

            if (filePart == null || filePart.getSize() == 0) {
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                res.getWriter().write("No file uploaded");
                return;
            }

            // copying  file
            Files.copy(filePart.getInputStream(),
                    Paths.get(finalFilePath),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING);


            // calling data initializer class
            DataInitializer.initializeData();


            res.setStatus(HttpServletResponse.SC_OK);
            res.getWriter().write("Your Data has been Uploaded Successfully");

        } catch (Exception e) {

            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            res.getWriter().write("Import failed: " + e.getMessage());
        }
    }
}
