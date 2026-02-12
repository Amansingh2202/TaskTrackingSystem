package com.servlets;

import com.google.gson.Gson;
import com.pojo.*;
import com.util.GlobalData;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/userPendingTasks")
public class IndividualPendingTask extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {

            String requestUserId = request.getParameter("userId");

            if (requestUserId == null || requestUserId.isEmpty()) {
                response.setStatus(400);
                response.getWriter().write("userId parameter required");
                return;
            }

            if (GlobalData.Data == null) {
                response.setStatus(400);
                response.getWriter().write("Data not initialized");
                return;
            }
            boolean check=false;

            List<Tasks> pendingTasks = new ArrayList<>();

            ProgramsBaseFile data = GlobalData.Data;
            List<Programs> programsList = data.getPrograms();

            for (int i = 0; i < programsList.size(); i++) {

                Programs programs = programsList.get(i);
                List<Projects> projectsList = programs.getProjects();

                for (int j = 0; j < projectsList.size(); j++) {

                    Projects projects = projectsList.get(j);
                    List<Tasks> taskList = projects.getTasks();

                    for (int k = 0; k < taskList.size(); k++) {

                        Tasks task = taskList.get(k);


                        if (requestUserId.equals(task.getOwnerId())
                                && "PENDING".equalsIgnoreCase(task.getStatus())) {

                            pendingTasks.add(task);
                            check=true;
                        }
                    }
                }
            }

            if(!check)
            {
                response.setStatus(400);
                response.getWriter().write("Wrong UserId");
                return ;
            }

            Gson gson = new Gson();
            response.setStatus(200);
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(pendingTasks));

        } catch (Exception e) {

            response.setStatus(500);
            response.getWriter().write("Server error: " + e.getMessage());
        }
    }
}
