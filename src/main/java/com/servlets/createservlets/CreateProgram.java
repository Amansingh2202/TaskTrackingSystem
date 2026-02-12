package com.servlets.createservlets;

import com.pojo.Programs;
import com.util.DataSaver;
import com.util.GlobalData;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/createProgram")
public class CreateProgram extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {

            String id = request.getParameter("programId");
            String name = request.getParameter("programName");

            if (id == null || id.isEmpty()||name==null||name.isEmpty()) {
                response.setStatus(400);
                response.getWriter().write(" parameters required");
                return;
            }

            Programs p = new Programs();
            p.setProgramId(id);
            p.setProgramName(name);
            p.setProjects(new ArrayList<>());

            GlobalData.Data.getPrograms().add(p);

            DataSaver.saveToFile();

            response.getWriter().write("Program created");

        } catch (Exception e) {
            response.setStatus(500);
            response .getWriter().write(e.getMessage());
        }
    }
}
