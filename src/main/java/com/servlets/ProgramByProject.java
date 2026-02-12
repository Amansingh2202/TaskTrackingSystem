package com.servlets;

import com.google.gson.Gson;
import com.pojo.Programs;
import com.pojo.ProgramsBaseFile;
import com.pojo.Projects;
import com.util.GlobalData;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/programByProject")
public class ProgramByProject extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {

            String requestProjectName = request.getParameter("projectName");

            if (requestProjectName == null || requestProjectName.isEmpty()) {
                response.setStatus(400);
                response.getWriter().write("projectName parameter required");
                return;
            }

            if (GlobalData.Data == null) {
                response.setStatus(400);
                response.getWriter().write("Data not initialized");
                return;
            }
            boolean check=false;

            String foundProgramName = null;

            ProgramsBaseFile data = GlobalData.Data;
            List<Programs> programsList = data.getPrograms();

            for (int i = 0; i < programsList.size(); i++) {

                Programs programs = programsList.get(i);
                List<Projects> projectsList = programs.getProjects();

                for (int j = 0; j < projectsList.size(); j++) {

                    Projects projects = projectsList.get(j);

                    if (requestProjectName.equals(projects.getProjectName())) {
                        foundProgramName = programs.getProgramName();
                        check=true;
                        break;
                    }
                }


            }
            if(!check)
            {
                response.setStatus(400);
                response.getWriter().write("Wrong programName");
                return ;
            }


            Gson gson = new Gson();

            response.setStatus(200);
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(foundProgramName));

        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write("Server error: " + e.getMessage());
        }
    }
}
