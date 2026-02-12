package com.servlets.createservlets;

import com.pojo.*;
import com.util.DataSaver;

import com.util.GlobalData;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/createIndividual")
public class CreateIndividual extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        try {

            String projectId = req.getParameter("projectId");
            String userId = req.getParameter("userId");
            String name = req.getParameter("name");

            if (projectId == null || projectId.isEmpty()||
                    name == null || name.isEmpty()||userId==null||userId.isEmpty()) {
                res.setStatus(400);
                res.getWriter().write(" parameters required");
                return;
            }

            if (GlobalData.Data == null) {
                res.setStatus(400);
                res.getWriter().write("Data not initialized");
                return;
            }

            EnrolledIndividuals person = new EnrolledIndividuals();
            person.setUserId(userId);
            person.setName(name);

            boolean check = false;

            ProgramsBaseFile data = GlobalData.Data;
            List<Programs> programsList = data.getPrograms();

            for (int i = 0; i < programsList.size(); i++) {

                List<Projects> projectsList =
                        programsList.get(i).getProjects();

                for (int j = 0; j < projectsList.size(); j++) {

                    Projects project = projectsList.get(j);

                    if (projectId.equals(project.getProjectId())) {

                        project.getEnrolledIndividuals().add(person);
                        check = true;
                        break;
                    }
                }
            }

            if (!check) {
                res.setStatus(404);
                res.getWriter().write("Project not found");
                return;
            }
            //savinf data  programData.json
            DataSaver.saveToFile();

            res.setStatus(200);
            res.getWriter().write("Individual enrolled");

        } catch (Exception e) {
            res.setStatus(500);
            res.getWriter().write(e.getMessage());
        }
    }
}
