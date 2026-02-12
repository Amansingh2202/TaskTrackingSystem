package com.servlets.createservlets;

import com.pojo.Programs;
import com.pojo.Projects;
import com.util.DataSaver;
import com.util.GlobalData;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/createProject")
public class CreateProject extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        try {

            String programId = req.getParameter("programId");
            String projectId = req.getParameter("projectId");
            String projectName = req.getParameter("projectName");


            if (projectId == null || projectId.isEmpty()|| programId == null || programId.isEmpty()||
                    projectName == null || projectName.isEmpty()) {
                res.setStatus(400);
                res.getWriter().write(" parameters required");
                return;
            }
            boolean check=false;

            Projects pr = new Projects();
            pr.setProjectId(projectId);
            pr.setProjectName(projectName);
            pr.setTasks(new ArrayList<>());
            pr.setEnrolledIndividuals(new ArrayList<>());

            List<Programs> list = GlobalData.Data.getPrograms();

            for (int i = 0; i < list.size(); i++) {
                if (programId.equals(list.get(i).getProgramId())) {
                    list.get(i).getProjects().add(pr);
                    check=true;
                }
            }
            if(!check)
            {
                res.setStatus(400);
                res.getWriter().write("Wrong Input Fields ");
            }

            DataSaver.saveToFile();
            res.getWriter().write("Project created");

        } catch (Exception e) {
            res.setStatus(500);
            res.getWriter().write(e.getMessage());
        }
    }
}
